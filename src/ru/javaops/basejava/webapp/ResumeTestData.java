package ru.javaops.basejava.webapp;

import ru.javaops.basejava.webapp.model.ContactType;
import ru.javaops.basejava.webapp.model.Experience;
import ru.javaops.basejava.webapp.model.Link;
import ru.javaops.basejava.webapp.model.ListSection;
import ru.javaops.basejava.webapp.model.OrganizationSection;
import ru.javaops.basejava.webapp.model.Resume;
import ru.javaops.basejava.webapp.model.SectionType;
import ru.javaops.basejava.webapp.model.TextSection;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");

        Map<ContactType, Link> contacts = resume.getContacts();
        contacts.put(ContactType.PHONE_NUMBER, new Link(
                "+7(921) 855-0482",
                null)
        );
        contacts.put(ContactType.SKYPE, new Link(
                "grigory.kislin",
                "skype:grigory.kislin")
        );
        contacts.put(ContactType.EMAIL, new Link(
                "gkislin@yandex.ru",
                "mailto:gkislin@yandex.ru"));
        contacts.put(ContactType.LINKEDIN, new Link(
                "Профиль LinkedIn",
                "https://www.linkedin.com/in/gkislin")
        );
        contacts.put(ContactType.GITHUB, new Link(
                "Профиль GitHub",
                "https://github.com/gkislin")
        );
        contacts.put(ContactType.STACKOVERFLOW, new Link(
                "Профиль Stackoverflow",
                "https://stackoverflow.com/users/548473")
        );
        contacts.put(ContactType.HOME_PAGE, new Link(
                "Домашняя страница",
                "http://gkislin.ru/")
        );

        TextSection objective = (TextSection) resume.getSections().get(SectionType.OBJECTIVE);
        objective.setContent("Ведущий стажировок и корпоративного обучения " +
                "по Java Web и Enterprise технологиям");

        TextSection personal = (TextSection) resume.getSections().get(SectionType.PERSONAL);
        personal.setContent("Аналитический склад ума, сильная логика, креативность, " +
                "инициативность. Пурист кода и архитектуры.");

        ListSection achievement = (ListSection) resume.getSections().get(SectionType.ACHIEVEMENT);
        List<String> content = new ArrayList<>();
        content.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java " +
                "Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб " +
                "сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация " +
                "онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        content.add("Реализация двухфакторной аутентификации для онлайн платформы управления " +
                "проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, " +
                "Zendesk.");
        content.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River " +
                "BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления " +
                "окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и " +
                "авторизации различных ERP модулей, интеграция CIFS/SMB java сервера");
        achievement.setContent(content);

        ListSection qualifications = (ListSection) resume.getSections().get(SectionType.QUALIFICATIONS);
        content = new ArrayList<>();
        content.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        content.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        content.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");
        content.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        content.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualifications.setContent(content);

        OrganizationSection experience = (OrganizationSection) resume.getSections().get(SectionType.EXPERIENCE);
        List<Experience> timeContent = new ArrayList<>();
        timeContent.add(new Experience(
                new Link("Java Online Projects", "http://javaops.ru/"),
                LocalDate.of(2013, 10, 1),
                LocalDate.MAX,
                "Автор проекта.\n" +
                        "Создание, организация и проведение Java онлайн проектов и стажировок.")
        );
        timeContent.add(new Experience(
                new Link("Wrike", "https://www.wrike.com/"),
                LocalDate.of(2014, 10, 1),
                LocalDate.of(2016, 1, 1),
                "Старший разработчик (backend)\n" +
                        "Проектирование и разработка онлайн платформы управления проектами " +
                        "Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, " +
                        "Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")
        );
        timeContent.add(new Experience(
                new Link("RIT Center", null),
                LocalDate.of(2012, 4, 1),
                LocalDate.of(2014, 10, 1),
                "Java архитектор\n" +
                        "Организация процесса разработки системы ERP для разных окружений: релизная " +
                        "политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация " +
                        "Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура " +
                        "БД и серверной части системы.")
        );
        timeContent.add(new Experience(
                new Link("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/"),
                LocalDate.of(2010, 12, 1),
                LocalDate.of(2012, 4, 1),
                "Ведущий программист\n" +
                        "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, " +
                        "SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. " +
                        "Реализация RIA-приложения для администрирования, мониторинга и анализа результатов " +
                        "в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), " +
                        "Highstock, Commet, HTML5.")
        );
        experience.setContent(timeContent);

        OrganizationSection education = (OrganizationSection) resume.getSections().get(SectionType.EDUCATION);
        timeContent = new ArrayList<>();
        timeContent.add(new Experience(
                new Link("Coursera", "https://www.coursera.org/course/progfun"),
                LocalDate.of(2013, 3, 1),
                LocalDate.of(2013, 5, 1),
                "\"Functional Programming Principles in Scala\" by Martin Odersky")
        );
        timeContent.add(new Experience(
                new Link("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366"),
                LocalDate.of(2011, 3, 1),
                LocalDate.of(2011, 4, 1),
                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"")
        );
        education.setContent(timeContent);

        System.out.println(resume);
    }
}
