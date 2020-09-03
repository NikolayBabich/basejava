package ru.javaops.basejava.webapp.model;

import ru.javaops.basejava.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public final class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private Link homePage;
    private List<Experience> experiences;

    public Organization() {
    }

    public Organization(String textLink, String urlLink, Experience... experiences) {
        this(new Link(textLink, urlLink), Arrays.asList(experiences));
    }

    public Organization(Link homePage, List<Experience> experiences) {
        Objects.requireNonNull(experiences, "experiences must not be null");
        this.homePage = homePage;
        this.experiences = experiences;
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return experiences.equals(that.experiences);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + experiences.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "\n\n" + homePage + '\n' + experiences;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static final class Experience implements Serializable {
        private static final long serialVersionUID = 1L;

        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate finishDate;
        private String title;
        private String description;

        private Experience() {
        }

        public Experience(LocalDate startDate, LocalDate finishDate, String title, String description) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(finishDate, "finishDate must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.startDate = startDate;
            this.finishDate = finishDate;
            this.title = title;
            this.description = description;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getFinishDate() {
            return finishDate;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Experience that = (Experience) o;

            if (!startDate.equals(that.startDate)) return false;
            if (!finishDate.equals(that.finishDate)) return false;
            if (!title.equals(that.title)) return false;
            return description != null ? description.equals(that.description) : that.description == null;
        }

        @Override
        public int hashCode() {
            int result = startDate.hashCode();
            result = 31 * result + finishDate.hashCode();
            result = 31 * result + title.hashCode();
            result = 31 * result + (description != null ? description.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            String startDateText =
                    String.format("%02d\\%04d", startDate.getMonthValue(), startDate.getYear());
            String finishDateText = (finishDate.isAfter(LocalDate.now()))
                                    ? "Сейчас"
                                    : String.format("%02d\\%04d", finishDate.getMonthValue(), finishDate.getYear());
            String descriptionText = (description == null) ? "" : description;

            return startDateText + " - " + finishDateText + "\t\t" +
                    title + '\n' + descriptionText;
        }
    }
}
