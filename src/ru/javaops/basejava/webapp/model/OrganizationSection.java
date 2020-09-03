package ru.javaops.basejava.webapp.model;

import java.util.List;

public final class OrganizationSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<Organization> content;

    private OrganizationSection() {
    }

    public OrganizationSection(List<Organization> content) {
        this.content = content;
    }

    public List<Organization> getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return content != null ? content.equals(that.content) : that.content == null;
    }

    @Override
    public int hashCode() {
        return content != null ? content.hashCode() : 0;
    }

    @Override
    public String toString() {
        return (content == null) ? "" : content.toString();
    }
}
