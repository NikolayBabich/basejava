package ru.javaops.basejava.webapp.model;

import java.util.List;

public final class OrganizationSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<Organization> organizations;

    private OrganizationSection() {
    }

    public OrganizationSection(List<Organization> content) {
        this.organizations = content;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return organizations != null ? organizations.equals(that.organizations) : that.organizations == null;
    }

    @Override
    public int hashCode() {
        return organizations != null ? organizations.hashCode() : 0;
    }
}
