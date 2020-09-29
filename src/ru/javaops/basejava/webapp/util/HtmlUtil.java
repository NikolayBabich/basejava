package ru.javaops.basejava.webapp.util;

import ru.javaops.basejava.webapp.model.AbstractSection;
import ru.javaops.basejava.webapp.model.ContactType;
import ru.javaops.basejava.webapp.model.Link;
import ru.javaops.basejava.webapp.model.ListSection;
import ru.javaops.basejava.webapp.model.Resume;
import ru.javaops.basejava.webapp.model.SectionType;
import ru.javaops.basejava.webapp.model.TextSection;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class HtmlUtil {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");
    private static final DateTimeFormatter DATE_PARSER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private HtmlUtil() {
    }

    public static Link convertValueToLink(ContactType type, String response) {
        String textLink;
        String urlLink;
        switch (type) {
            case PHONE_NUMBER:
                textLink = response;
                urlLink = "tel:" + response;
                break;
            case SKYPE:
                textLink = response;
                urlLink = "skype:" + response;
                break;
            case EMAIL:
                textLink = response;
                urlLink = "mailto:" + response;
                break;
            case LINKEDIN:
                textLink = "Профиль LinkedIn";
                urlLink = response;
                break;
            case GITHUB:
                textLink = "Профиль GitHub";
                urlLink = response;
                break;
            case STACKOVERFLOW:
                textLink = "Профиль Stackoverflow";
                urlLink = response;
                break;
            case HOME_PAGE:
                textLink = "Домашняя страница";
                urlLink = response;
                break;
            default:
                throw new AssertionError("Should not get here");
        }
        return new Link(textLink, urlLink);
    }

    public static String convertLinkToValue(Resume resume, ContactType type) {
        Link link = resume.getContact(type);
        if (link == null) {
            return "";
        }
        switch (type) {
            case PHONE_NUMBER:
            case SKYPE:
            case EMAIL:
                return link.getText();
            case LINKEDIN:
            case GITHUB:
            case STACKOVERFLOW:
            case HOME_PAGE:
                return link.getUrl();
            default:
                throw new AssertionError("Should not get here");
        }
    }

    public static String getContactImgHtml(ContactType type) {
        String imgName;
        switch (type) {
            case PHONE_NUMBER:
                imgName = "phone.png";
                break;
            case SKYPE:
                imgName = "skype.png";
                break;
            case EMAIL:
                imgName = "email.png";
                break;
            case LINKEDIN:
                imgName = "lin.png";
                break;
            case GITHUB:
                imgName = "gh.png";
                break;
            case STACKOVERFLOW:
                imgName = "so.png";
                break;
            case HOME_PAGE:
                imgName = "home.png";
                break;
            default:
                throw new AssertionError("Should not get here");
        }
        return "<img src=\"img/" + imgName + "\">";
    }

    public static AbstractSection convertContentToSection(SectionType type, String content) {
        switch (type) {
            case OBJECTIVE:
            case PERSONAL:
                return new TextSection(content);
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                List<String> lines = Arrays.stream(content.split(System.lineSeparator()))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());
                return new ListSection(lines);
            default:
                throw new AssertionError("Should not get here");
        }
    }

    public static String convertDateToHtml(LocalDate date) {
        if (LocalDate.MAX.equals(date)) {
            date = LocalDate.now();
        }
        return DATE_FORMATTER.format(date);
    }

    public static LocalDate convertHtmlToDate(String html) {
        LocalDate date = LocalDate.parse(html + "-01", DATE_PARSER);
        if (date.isAfter(LocalDate.now().minusMonths(1))) {
            date = LocalDate.MAX;
        }
        return date;
    }
}
