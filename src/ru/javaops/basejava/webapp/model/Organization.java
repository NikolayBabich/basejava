package ru.javaops.basejava.webapp.model;

import java.util.List;
import java.util.Objects;

public final class Organization {
    private final Link homePage;
    private final List<Experience> experiences;

    public Organization(String textLink, String urlLink,
                        List<Experience> experiences) {
        Objects.requireNonNull(experiences, "experiences must not be null");
        this.homePage = new Link(textLink, urlLink);
        this.experiences = experiences;
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    @Override
    public String toString() {
        return "\n\n" + homePage + '\n' +
                experiences;
    }
}
