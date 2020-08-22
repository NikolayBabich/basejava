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
        return null;
    }

    @Override
    public List<DatedEntry> getContent() {
        return null;
    }

    @Override
    public void setContent(List<DatedEntry> content) {

    }
}
