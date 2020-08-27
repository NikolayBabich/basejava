package ru.javaops.basejava.webapp.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public final class Resume implements Comparable<Resume>, Serializable {
    private final String uuid;
    private final String fullName;
    private final Map<ContactType, Link> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, AbstractSection<?>> sections;

    {
        Map<SectionType, AbstractSection<?>> tempSections = new EnumMap<>(SectionType.class);
        initializeSections(tempSections);
        this.sections = Collections.unmodifiableMap(tempSections);
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString().substring(0, 8), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    private void initializeSections(Map<SectionType, AbstractSection<?>> sections) {
        sections.put(SectionType.OBJECTIVE, new TextSection());
        sections.put(SectionType.PERSONAL, new TextSection());
        sections.put(SectionType.ACHIEVEMENT, new ListSection());
        sections.put(SectionType.QUALIFICATIONS, new ListSection());
        sections.put(SectionType.EXPERIENCE, new OrganizationSection());
        sections.put(SectionType.EDUCATION, new OrganizationSection());
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<ContactType, Link> getContacts() {
        return contacts;
    }

    public Map<SectionType, AbstractSection<?>> getSections() {
        return sections;
    }

    public Link getContact(ContactType type) {
        return contacts.get(type);
    }

    public void setContact(ContactType type, Link contact) {
        contacts.put(type, contact);
    }

    public AbstractSection<?> getSection(SectionType type) {
        return sections.get(type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        if (!fullName.equals(resume.fullName)) return false;
        if (!contacts.equals(resume.contacts)) return false;
        return sections.equals(resume.sections);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        result = 31 * result + contacts.hashCode();
        result = 31 * result + sections.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Resume #" + uuid +
                ", fullName='" + fullName + "'\n\n" +
                contacts + "\n\n" +
                sections;
    }

    @Override
    public int compareTo(Resume o) {
        return uuid.compareTo(o.getUuid());
    }
}
