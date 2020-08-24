package ru.javaops.basejava.webapp.model;

public enum ContactType {
    PHONE_NUMBER("Тел.: "),
    SKYPE("Skype: "),
    EMAIL("Почта: "),
    LINKEDIN(""),
    GITHUB(""),
    STACKOVERFLOW(""),
    HOME_PAGE("");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
