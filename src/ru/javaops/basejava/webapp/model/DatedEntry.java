package ru.javaops.basejava.webapp.model;

import java.time.LocalDate;

class DatedEntry {
    private final String textLink;
    private final String urlLink;
    private final LocalDate startDate;
    private final LocalDate finishDate;
    private final String text;

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

    public DatedEntry(String textLink, String urlLink, LocalDate startDate, LocalDate finishDate, String text) {
        this.textLink = textLink;
        this.urlLink = urlLink;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.text = text;
    }
}
