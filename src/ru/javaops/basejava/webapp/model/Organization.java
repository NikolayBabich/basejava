package ru.javaops.basejava.webapp.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public final class Organization {
    private final Link homePage;
    private final List<Experience> experiences;

    public Organization(@NotNull String textLink, @Nullable String urlLink,
                        @NotNull List<Experience> experiences) {
        Objects.requireNonNull(experiences, "experiences must not be null");
        this.homePage = new Link(textLink, urlLink);
        this.experiences = experiences;
    }

    @NotNull
    public Link getHomePage() {
        return homePage;
    }

    @NotNull
    public List<Experience> getExperiences() {
        return experiences;
    }

    @Override
    public String toString() {
        return "\n\n" + homePage + '\n' +
                experiences;
    }
}
