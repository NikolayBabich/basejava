package ru.javaops.basejava.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public final class Link implements Serializable {
    private static final long serialVersionUID = 1L;

    private String text;
    private String url;

    @SuppressWarnings("unused")  //  for XML unmarshalling
    private Link() {
    }

    public Link(String textLink, String url) {
        Objects.requireNonNull(textLink, "textLink must not be null");
        this.text = textLink;
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if (!text.equals(link.text)) return false;
        return url != null ? url.equals(link.url) : link.url == null;
    }

    @Override
    public int hashCode() {
        int result = text.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return (url != null) ? "<a href=\"" + url + "\">" + text + "</a>" : text;
    }
}
