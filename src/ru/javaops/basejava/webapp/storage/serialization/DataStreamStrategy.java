package ru.javaops.basejava.webapp.storage.serialization;

import ru.javaops.basejava.webapp.model.AbstractSection;
import ru.javaops.basejava.webapp.model.ContactType;
import ru.javaops.basejava.webapp.model.Link;
import ru.javaops.basejava.webapp.model.ListSection;
import ru.javaops.basejava.webapp.model.OrganizationSection;
import ru.javaops.basejava.webapp.model.Resume;
import ru.javaops.basejava.webapp.model.SectionType;
import ru.javaops.basejava.webapp.model.TextSection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public final class DataStreamStrategy implements SerializationStrategy {

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

            Map<SectionType, AbstractSection<?>> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection<?>> entry : sections.entrySet()) {
                String keyName = entry.getKey().name();
                dos.writeUTF(keyName);
                AbstractSection<?> section = entry.getValue();
                dos.writeUTF(section.getSerializedContent());
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
                AbstractSection<?> section;
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
                section.setDeserializedContent(dis.readUTF());
                resume.setSection(type, section);
            }

            return resume;
        }
    }
}
