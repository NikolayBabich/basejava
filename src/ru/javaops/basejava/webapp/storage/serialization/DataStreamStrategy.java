package ru.javaops.basejava.webapp.storage.serialization;

import ru.javaops.basejava.webapp.model.AbstractSection;
import ru.javaops.basejava.webapp.model.ContactType;
import ru.javaops.basejava.webapp.model.Link;
import ru.javaops.basejava.webapp.model.ListSection;
import ru.javaops.basejava.webapp.model.Organization;
import ru.javaops.basejava.webapp.model.OrganizationSection;
import ru.javaops.basejava.webapp.model.Resume;
import ru.javaops.basejava.webapp.model.SectionType;
import ru.javaops.basejava.webapp.model.TextSection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class DataStreamStrategy implements SerializationStrategy {
    private static final String LIST_DELIMITER = "<-->";
    private static final String ORGANIZATION_DELIMITER = "<!>";
    private static final int FIRST_DATA_INDEX = 3;
    private static final int OFFSET_INDEX = 4;

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, Link> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, Link> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue().getText());
                String url = entry.getValue().getUrl();
                dos.writeUTF((url != null) ? url : "null");
            }

            Map<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                String keyName = entry.getKey().name();
                dos.writeUTF(keyName);
                AbstractSection section = entry.getValue();
                dos.writeUTF(getSerializedContent(section));
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            int contactsSize = dis.readInt();
            for (int i = 0; i < contactsSize; i++) {
                String type = dis.readUTF();
                String textLink = dis.readUTF();
                String urlLink = dis.readUTF();
                if ("null".equals(urlLink)) {
                    urlLink = null;
                }
                resume.setContact(ContactType.valueOf(type), new Link(textLink, urlLink));
            }

            int sectionsSize = dis.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                SectionType type = SectionType.valueOf(dis.readUTF());
                AbstractSection section;
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        section = new TextSection();
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        section = new ListSection();
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        section = new OrganizationSection();
                        break;
                    default:
                        throw new AssertionError("Should not get here");
                }
                setDeserializedContent(section, dis.readUTF());
                resume.setSection(type, section);
            }
            return resume;
        }
    }

    private static String getSerializedContent(AbstractSection section) {
        if (section.getClass() == TextSection.class) {
            return ((TextSection) section).getContent();
        }
        StringBuilder sb = new StringBuilder();
        if (section.getClass() == ListSection.class) {
            List<String> listSectionContent = ((ListSection) section).getContent();
            sb.append(listSectionContent.size()).append(LIST_DELIMITER);
            listSectionContent.forEach(e -> sb.append(e).append(LIST_DELIMITER));
        } else if (section.getClass() == OrganizationSection.class) {
            List<Organization> orgSectionContent = ((OrganizationSection) section).getContent();
            sb.append(orgSectionContent.size()).append(LIST_DELIMITER);
            orgSectionContent.forEach(o -> sb.append(getSerialized(o)).append(LIST_DELIMITER));
        }
        return sb.toString();
    }

    private static String getSerialized(Organization organization) {
        StringBuilder sb = new StringBuilder();
        Link homePage = organization.getHomePage();
        List<Organization.Experience> experiences = organization.getExperiences();
        appendWithDelimiter(sb, homePage.getText(), homePage.getUrl(), String.valueOf(experiences.size()));
        for (Organization.Experience exp : experiences) {
            appendWithDelimiter(sb,
                                exp.getStartDate().toString(), exp.getFinishDate().toString(),
                                exp.getTitle(), exp.getDescription());
        }
        return sb.toString();
    }

    private static void appendWithDelimiter(StringBuilder sb, String... lines) {
        Arrays.asList(lines).forEach(s -> sb.append(s).append(ORGANIZATION_DELIMITER));
    }

    private static void setDeserializedContent(AbstractSection section, String serializedContent) {
        if (section.getClass() == TextSection.class) {
            ((TextSection) section).setContent(serializedContent);
            return;
        }
        String[] lines = serializedContent.split(LIST_DELIMITER);
        int size = Integer.parseInt(lines[0]);
        if (section.getClass() == ListSection.class) {
            List<String> content = new ArrayList<>(Arrays.asList(lines).subList(1, size + 1));
            ((ListSection) section).setContent(content);
        } else if (section.getClass() == OrganizationSection.class) {
            List<Organization> content = new ArrayList<>(size);
            for (int i = 1; i <= size; i++) {
                Organization organization = new Organization();
                setDeserialized(organization, lines[i]);
                content.add(organization);
            }
            ((OrganizationSection) section).setContent(content);
        }
    }

    private static void setDeserialized(Organization organization, String line) {
        String[] lines = line.split(ORGANIZATION_DELIMITER);

        String textLink = lines[0];
        String urlLink = "null".equals(lines[1]) ? null : lines[1];
        organization.setHomePage(new Link(textLink, urlLink));

        int size = Integer.parseInt(lines[2]);
        List<Organization.Experience> experiences = new ArrayList<>();
        for (int count = 0, idx = FIRST_DATA_INDEX; count < size; count++, idx += OFFSET_INDEX) {
            String title = lines[idx + 2];
            String description = "null".equals(lines[idx + 3]) ? null : lines[idx + 3];
            experiences.add(new Organization.Experience(
                    LocalDate.parse(lines[idx]), LocalDate.parse(lines[idx + 1]),
                    title, description
            ));
        }
        organization.setExperiences(experiences);
    }
}
