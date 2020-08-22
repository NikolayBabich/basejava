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
}
