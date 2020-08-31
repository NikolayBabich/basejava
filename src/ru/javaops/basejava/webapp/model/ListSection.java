package ru.javaops.basejava.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ListSection extends AbstractSection<List<String>> {
    private static final long serialVersionUID = 1L;
    private static final String DELIMITER = "<-->";

    private List<String> content;

    public ListSection() {
    }

    public ListSection(List<String> content) {
        this.content = content;
    }

    @Override
    public List<String> getContent() {
        return content;
    }

    @Override
    public void setContent(List<String> content) {
        this.content = content;
    }

    @Override
    public String getSerializedContent() {
        StringBuilder sb = new StringBuilder();
        sb.append(content.size()).append(DELIMITER);
        content.forEach(e -> sb.append(e).append(DELIMITER));
        return sb.toString();
    }

    @Override
    public void setDeserializedContent(String serializedContent) {
        String[] lines = serializedContent.split(DELIMITER);
        content = new ArrayList<>();
        content.addAll(Arrays.asList(lines).subList(1, Integer.parseInt(lines[0]) + 1));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

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
