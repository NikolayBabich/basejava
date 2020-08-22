package ru.javaops.basejava.webapp.model;

public class ContactType {
    private final String text;
    private final String linkText;
    private final String linkUrl;

    public ContactType(String text, String linkText, String linkUrl) {
        this.text = text;
        this.linkText = linkText;
        this.linkUrl = linkUrl;
    }
}
