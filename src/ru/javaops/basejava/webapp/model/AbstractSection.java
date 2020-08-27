package ru.javaops.basejava.webapp.model;

import java.io.Serializable;

public abstract class AbstractSection<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final T content;

    AbstractSection(T content) {
        this.content = content;
    }

    public final T getContent() {
        return content;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractSection)) return false;

        AbstractSection<?> that = (AbstractSection<?>) o;

        return content != null ? content.equals(that.content) : that.content == null;
    }

    @Override
    public final int hashCode() {
        return content != null ? content.hashCode() : 0;
    }

    @Override
    public final String toString() {
        return (content == null) ? "" : content.toString();
    }
}
