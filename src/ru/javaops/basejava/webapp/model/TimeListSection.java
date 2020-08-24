package ru.javaops.basejava.webapp.model;

import java.util.List;

public class TimeListSection implements Section<List <DatedEntry>> {
    private final SectionType type;
    private List<DatedEntry> content;

    public TimeListSection(SectionType type) {
        this.type = type;
    }

    @Override
    public SectionType getType() {
        return type;
    }

    @Override
    public List<DatedEntry> getContent() {
        return content;
    }

    @Override
    public void setContent(List<DatedEntry> content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeListSection that = (TimeListSection) o;

        if (type != that.type) return false;
        return content != null ? content.equals(that.content) : that.content == null;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return type.getTitle() +
                ": " + content;
    }
}
