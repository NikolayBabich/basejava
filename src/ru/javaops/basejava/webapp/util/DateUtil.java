package ru.javaops.basejava.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public final class DateUtil {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");
    private static final DateTimeFormatter DATE_PARSER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private DateUtil() {
    }

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String convertDateToHtml(LocalDate date) {
        if (LocalDate.MAX.equals(date)) {
            date = LocalDate.now();
        }
        return DATE_FORMATTER.format(date);
    }

    public static LocalDate convertHtmlToDate(String html) {
        if (html.isEmpty()) {
            return LocalDate.of(1970, 1, 1);
        }
        LocalDate date = LocalDate.parse(html + "-01", DATE_PARSER);
        if (date.isAfter(LocalDate.now().minusMonths(1))) {
            date = LocalDate.MAX;
        }
        return date;
    }
}