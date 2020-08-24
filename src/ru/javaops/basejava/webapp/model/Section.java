package ru.javaops.basejava.webapp.model;

import org.jetbrains.annotations.Nullable;

public abstract class Section<T> {
    protected T content;

    @Nullable
    public final T getContent() {
        return content;
    }

    public final void setContent(T content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Section)) return false;

        Section<?> that = (Section<?>) o;

        return content != null ? content.equals(that.content) : that.content == null;
    }

    @Override
    public int hashCode() {
        return content != null ? content.hashCode() : 0;
    }

    @Override
    public String toString() {
        return content.toString();
    }
}
