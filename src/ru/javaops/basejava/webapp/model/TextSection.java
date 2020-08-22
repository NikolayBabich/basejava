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
}
