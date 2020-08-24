package ru.javaops.basejava.webapp.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class Link {
    private final String text;
    private final String url;

    public Link(@NotNull String textLink, @Nullable String url) {
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