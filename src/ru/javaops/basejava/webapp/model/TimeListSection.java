package ru.javaops.basejava.webapp.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class TimeListSection extends AbstractSection<List <TimeListSection.DatedEntry>> {
    public TimeListSection(SectionType type) {
        super(type);
    }

    @Override
    public String toString() {
        return type.getTitle() +
                ": " + content;
    }

    static class DatedEntry {
        private final String textLink;
        private final String urlLink;
        private final LocalDate startDate;
        private final LocalDate finishDate;
        private final String text;

        public DatedEntry(@NotNull String textLink, @Nullable String urlLink,
                          @NotNull LocalDate startDate, @Nullable LocalDate finishDate,
                          @NotNull String text) {
            Objects.requireNonNull(textLink, "textLink must not be null");
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(text, "text must not be null");
            this.textLink = textLink;
            this.urlLink = urlLink;
            this.startDate = startDate;
            this.finishDate = finishDate;
            this.text = text;
        }

        public String getTextLink() {
            return textLink;
        }

        public String getUrlLink() {
            return urlLink;
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

            DatedEntry that = (DatedEntry) o;

            if (!textLink.equals(that.textLink)) return false;
            if (urlLink != null ? !urlLink.equals(that.urlLink) : that.urlLink != null) return false;
            if (!startDate.equals(that.startDate)) return false;
            if (finishDate != null ? !finishDate.equals(that.finishDate) : that.finishDate != null) return false;
            return text.equals(that.text);
        }

        @Override
        public int hashCode() {
            int result = textLink.hashCode();
            result = 31 * result + (urlLink != null ? urlLink.hashCode() : 0);
            result = 31 * result + startDate.hashCode();
            result = 31 * result + (finishDate != null ? finishDate.hashCode() : 0);
            result = 31 * result + text.hashCode();
            return result;
        }

        @Override
        public String toString() {
            String finishDateText = (finishDate == null)
                    ? "Сейчас"
                    : "" + finishDate.getMonthValue() + '\\' + finishDate.getYear();
            return "\n\n" + textLink +
                    " <a href=\"" + urlLink + "\">\n" +
                    startDate.getMonthValue() + '\\' + startDate.getYear() +
                    " - " + finishDateText + "  " +
                    text;
        }
    }
}
