package ru.javaops.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;

public final class OrganizationSection extends AbstractSection<List<Organization>> {
    private static final long serialVersionUID = 1L;
    private static final String DELIMITER = "<-->";

    private List<Organization> content;

    public OrganizationSection() {
    }

    public OrganizationSection(List<Organization> content) {
        this.content = content;
    }

    @Override
    public List<Organization> getContent() {
        return content;
    }

    @Override
    void setContent(List<Organization> content) {
        this.content = content;
    }

    @Override
    public String getSerializedContent() {
        StringBuilder sb = new StringBuilder();
        sb.append(content.size()).append(DELIMITER);
        content.forEach(o -> sb.append(o.getSerialized()).append(DELIMITER));
        return sb.toString();
    }

    @Override
    public void setDeserializedContent(String serializedContent) {
        String[] lines = serializedContent.split(DELIMITER);

        content = new ArrayList<>();
        int size = Integer.parseInt(lines[0]);
        for (int i = 1; i <= size; i++) {
            Organization organization = new Organization();
            organization.setDeserialized(lines[i]);
            content.add(organization);
        }
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
