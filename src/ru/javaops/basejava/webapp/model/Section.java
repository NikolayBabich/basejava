package ru.javaops.basejava.webapp.model;

public interface Section<T> {
    SectionType getType();

    T getContent();

    void setContent(T content);
}
