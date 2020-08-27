package ru.javaops.basejava.webapp.model;

import java.util.List;

public final class ListSection extends AbstractSection<List <String>> {
    private static final long serialVersionUID = 1L;

    public ListSection(List<String> content) {
        super(content);
    }
}
