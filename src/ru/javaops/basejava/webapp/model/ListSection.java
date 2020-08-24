package ru.javaops.basejava.webapp.model;

import java.util.List;

public class ListSection extends AbstractSection<List <String>> {
    public ListSection(SectionType type) {
        super(type);
    }

    @Override
    public String toString() {
        return type.getTitle() +
                ": " + content;
    }
}
