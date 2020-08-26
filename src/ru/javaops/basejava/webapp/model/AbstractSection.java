package ru.javaops.basejava.webapp.model;

public abstract class AbstractSection<T> {
    T content;

    public final T getContent() {
        return content;
    }

    public final void setContent(T content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractSection)) return false;

        AbstractSection<?> that = (AbstractSection<?>) o;

        return content != null ? content.equals(that.content) : that.content == null;
    }

    @Override
    public int hashCode() {
        return content != null ? content.hashCode() : 0;
    }

    @Override
    public String toString() {
        return (content == null) ? "" : content.toString();
    }
}
