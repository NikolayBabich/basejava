package ru.javaops.basejava.webapp.model;

public class TextSection implements Section<String> {
    private final SectionType type;
    private String content;

    public TextSection(SectionType type) {
        this.type = type;
    }

    public SectionType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection that = (TextSection) o;

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
