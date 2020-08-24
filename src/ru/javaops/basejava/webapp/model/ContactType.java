package ru.javaops.basejava.webapp.model;

import java.util.Objects;

public class ContactType {
    private final String text;
    private final String linkText;
    private final String linkUrl;

    public ContactType(String text, String linkText, String linkUrl) {
        Objects.requireNonNull(text, "text must not be null");
        Objects.requireNonNull(linkText, "linkText must not be null");
        this.text = text;
        this.linkText = linkText;
        this.linkUrl = linkUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactType that = (ContactType) o;

        if (!text.equals(that.text)) return false;
        if (!linkText.equals(that.linkText)) return false;
        return linkUrl != null ? linkUrl.equals(that.linkUrl) : that.linkUrl == null;
    }

    @Override
    public int hashCode() {
        int result = text.hashCode();
        result = 31 * result + linkText.hashCode();
        result = 31 * result + (linkUrl != null ? linkUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return text +
                "<a href=\"" + linkUrl + "\">" +
                linkText + "</a>\n";
    }
}
