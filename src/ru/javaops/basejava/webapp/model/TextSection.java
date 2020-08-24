package ru.javaops.basejava.webapp.model;

public class TextSection extends AbstractSection<String> {
    public TextSection(SectionType type) {
        super(type);
    }

    @Override
    public String toString() {
        return type.getTitle() +
                ": " + content;
    }
}
