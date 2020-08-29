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

        return content.equals(that.content);
    }

    @Override
    public final int hashCode() {
        return content.hashCode();
    }

    @Override
    public final String toString() {
        return content.toString();
    }
}
