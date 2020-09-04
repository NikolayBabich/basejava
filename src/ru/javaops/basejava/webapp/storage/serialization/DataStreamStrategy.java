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
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public final class DataStreamStrategy implements SerializationStrategy {
    private static final String NULL = "null";

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            writeCollection(dos, resume.getContacts().entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue().getText());
                dos.writeUTF(writeNullable(entry.getValue().getUrl()));
            });

            writeCollection(dos, resume.getSections().entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                writeSection(dos, entry.getKey(), entry.getValue());
            });
        }
    }

    private static void writeSection(DataOutputStream dos, SectionType type, AbstractSection section) throws IOException {
        switch (type) {
            case OBJECTIVE:
            case PERSONAL:
                dos.writeUTF(((TextSection) section).getContent());
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                writeCollection(dos, ((ListSection) section).getContent(), dos::writeUTF);
                break;
            case EXPERIENCE:
            case EDUCATION:
                writeCollection(dos, ((OrganizationSection) section).getContent(), org -> {
                    dos.writeUTF(org.getHomePage().getText());
                    dos.writeUTF(writeNullable(org.getHomePage().getUrl()));
                    writeCollection(dos, org.getExperiences(), exp -> {
                        dos.writeUTF(exp.getStartDate().toString());
                        dos.writeUTF(exp.getFinishDate().toString());
                        dos.writeUTF(exp.getTitle());
                        dos.writeUTF(writeNullable(exp.getDescription()));
                    });
                });
                break;
            default:
                throw new AssertionError("Should not get here");
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());

            processData(dis, () -> resume.setContact(ContactType.valueOf(dis.readUTF()),
                                                     new Link(dis.readUTF(), readNullable(dis.readUTF()))));
            processData(dis, () -> {
                SectionType type = SectionType.valueOf(dis.readUTF());
                resume.setSection(type, readSection(dis, type));
            });

            return resume;
        }
    }

    private static AbstractSection readSection(DataInputStream dis, SectionType type) throws IOException {
        switch (type) {
            case OBJECTIVE:
            case PERSONAL:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection(readList(dis, dis::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new OrganizationSection(readList(dis, () -> new Organization(
                        new Link(dis.readUTF(), readNullable(dis.readUTF())),
                        readList(dis, () -> new Organization.Experience(LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()),
                                                                        dis.readUTF(), readNullable(dis.readUTF()))
                        )
                )));
            default:
                throw new AssertionError("Should not get here");
        }
    }

    private static String writeNullable(String s) {
        return (s != null) ? s : NULL;
    }

    private static String readNullable(String s) {
        return (NULL.equals(s)) ? null : s;
    }

    private static <T> void writeCollection(DataOutputStream dos, Collection<T> collection, ElementWriter<T> action) throws IOException {
        Objects.requireNonNull(collection);
        Objects.requireNonNull(action);
        dos.writeInt(collection.size());
        for (T t : collection) {
            action.write(t);
        }
    }

    @FunctionalInterface
    private interface ElementWriter<T> {
        void write(T t) throws IOException;
    }

    private static void processData(DataInputStream dis, ElementProcessor action) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            action.process();
        }
    }

    @FunctionalInterface
    private interface ElementProcessor {
        void process() throws IOException;
    }

    private static <T> List<T> readList(DataInputStream dis, ElementReader<T> action) throws IOException {
        Objects.requireNonNull(action);
        int size = dis.readInt();
        List<T> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(action.read());
        }
        return result;
    }

    @FunctionalInterface
    private interface ElementReader<T> {
        T read() throws IOException;
    }
}
