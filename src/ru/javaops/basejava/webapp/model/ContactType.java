package ru.javaops.basejava.webapp.model;

import java.net.URL;

public class ContactType {
    private final String name;
    private final String linkText;
    private final URL linkUrl;

    public ContactType(String name, String linkText, URL linkUrl) {
        this.name = name;
        this.linkText = linkText;
        this.linkUrl = linkUrl;
    }
}
