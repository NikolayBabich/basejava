package ru.javaops.basejava.webapp.model;

import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public final class Resume implements Comparable<Resume> {
    private final String uuid;
    private final String fullName;
    private final Map<String, ContactType> contacts;
    private final Map<SectionType, Section<?>> sections;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString().substring(0, 8), fullName);
    }

    public Resume(@NotNull String uuid, @NotNull String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
        contacts = new HashMap<>();
        sections = new EnumMap<>(SectionType.class);
        initializeSections(sections);
    }

    private void initializeSections(Map<SectionType, Section<?>> sections) {
        sections.put(SectionType.OBJECTIVE, new TextSection(SectionType.OBJECTIVE));
        sections.put(SectionType.PERSONAL, new TextSection(SectionType.PERSONAL));
        sections.put(SectionType.ACHIEVEMENT, new ListSection(SectionType.ACHIEVEMENT));
        sections.put(SectionType.QUALIFICATIONS, new ListSection(SectionType.QUALIFICATIONS));
        sections.put(SectionType.EXPERIENCE, new TimeListSection(SectionType.EXPERIENCE));
        sections.put(SectionType.EDUCATION, new TimeListSection(SectionType.EDUCATION));
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<String, ContactType> getContacts() {
        return contacts;
    }

    public Map<SectionType, Section<?>> getSections() {
        return sections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }

    @Override
    public int compareTo(@NotNull Resume o) {
        return uuid.compareTo(o.getUuid());
    }
}
