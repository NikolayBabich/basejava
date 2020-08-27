package ru.javaops.basejava.webapp.model;

import java.util.List;

public final class OrganizationSection extends AbstractSection<List<Organization>> {
    private static final long serialVersionUID = 1L;

    public OrganizationSection(List<Organization> content) {
        super(content);
    }
}
