package ru.javaops.basejava.webapp.model;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Objects;

public final class Experience {
    private final Link link;
    private final LocalDate startDate;
    private final LocalDate finishDate;
    private final String text;

    public Experience(@NotNull Link link,
                      @NotNull LocalDate startDate, @NotNull LocalDate finishDate,
                      @NotNull String text) {
        Objects.requireNonNull(link, "link must not be null");
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(finishDate, "finishDate must not be null");
        Objects.requireNonNull(text, "text must not be null");
        this.link = link;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.text = text;
    }

    public Link getLink() {
        return link;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Experience that = (Experience) o;

        if (!link.equals(that.link)) return false;
        if (!startDate.equals(that.startDate)) return false;
        if (!finishDate.equals(that.finishDate)) return false;
        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        int result = link.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + finishDate.hashCode();
        result = 31 * result + text.hashCode();
        return result;
    }

    @Override
    public String toString() {
        String finishDateText = (finishDate.isAfter(LocalDate.now())) ? "Сейчас"
                : "" + finishDate.getMonthValue() + '\\' + finishDate.getYear();
        return "\n\n" + link + '\n' +
                startDate.getMonthValue() + '\\' + startDate.getYear() +
                " - " + finishDateText + "\t\t" +
                text;
    }
}
