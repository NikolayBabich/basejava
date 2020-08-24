package ru.javaops.basejava.webapp.model;

import java.util.List;

public class ListSection implements Section<List <String>> {
    private final SectionType type;
    private List<String> content;

    public ListSection(SectionType type) {
        this.type = type;
    }

    public SectionType getType() {
        return type;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

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
