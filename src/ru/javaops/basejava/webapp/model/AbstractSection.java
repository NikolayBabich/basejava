package ru.javaops.basejava.webapp.model;

public abstract class AbstractSection<T> implements Section<T> {
    final SectionType type;
    T content;

    public AbstractSection(SectionType type) {
        this.type = type;
    }

    @Override
    public SectionType getType() {
        return type;
    }

    @Override
    public T getContent() {
        return content;
    }

    @Override
    public void setContent(T content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractSection)) return false;

        AbstractSection<?> that = (AbstractSection<?>) o;

        if (type != that.type) return false;
        return content != null ? content.equals(that.content) : that.content == null;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
