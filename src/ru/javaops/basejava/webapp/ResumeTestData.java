package ru.javaops.basejava.webapp;

import ru.javaops.basejava.webapp.model.ContactType;
import ru.javaops.basejava.webapp.model.Experience;
import ru.javaops.basejava.webapp.model.Link;
import ru.javaops.basejava.webapp.model.ListSection;
import ru.javaops.basejava.webapp.model.Organization;
import ru.javaops.basejava.webapp.model.OrganizationSection;
import ru.javaops.basejava.webapp.model.Resume;
import ru.javaops.basejava.webapp.model.SectionType;
import ru.javaops.basejava.webapp.model.TextSection;
import ru.javaops.basejava.webapp.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = getTestResume("TestUuid", "Григорий Кислин");

        System.out.println("\n===GET CONTACTS===");
        System.out.println(resume.getContacts());

        System.out.println("\n===TEST SET SKYPE===");
        resume.setContact(ContactType.SKYPE, new Link("No skype!", null));
        System.out.println(ContactType.SKYPE.getTitle() + resume.getContact(ContactType.SKYPE));

        System.out.println('\n' + SectionType.OBJECTIVE.getTitle().toUpperCase());
        System.out.println(resume.getSection(SectionType.OBJECTIVE));
        System.out.println('\n' + SectionType.PERSONAL.getTitle().toUpperCase());
        System.out.println(resume.getSection(SectionType.PERSONAL));
        System.out.println('\n' + SectionType.ACHIEVEMENT.getTitle().toUpperCase());
        System.out.println(resume.getSection(SectionType.ACHIEVEMENT));
        System.out.println('\n' + SectionType.QUALIFICATIONS.getTitle().toUpperCase());
        System.out.println(resume.getSection(SectionType.QUALIFICATIONS));
        System.out.println('\n' + SectionType.EXPERIENCE.getTitle().toUpperCase());
        System.out.println(resume.getSection(SectionType.EXPERIENCE));
        System.out.println('\n' + SectionType.EDUCATION.getTitle().toUpperCase());
        System.out.println(resume.getSection(SectionType.EDUCATION));
    }

    public static Resume getTestResume(String fullName) {
        return getTestResume(UUID.randomUUID().toString().substring(0, 8), fullName);
    }

    public static Resume getTestResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

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

        ListSection qualifications =
                (ListSection) resume.getSections().get(SectionType.QUALIFICATIONS);
        content = new ArrayList<>();
        content.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        content.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        content.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");
        content.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        content.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualifications.setContent(content);

        OrganizationSection experience =
                (OrganizationSection) resume.getSections().get(SectionType.EXPERIENCE);
        List<Organization> organizationContent = new ArrayList<>();
        organizationContent.add(new Organization(
                        "Java Online Projects",
                        "http://javaops.ru/",
                        Collections.singletonList(new Experience(
                                DateUtil.of(2013, Month.OCTOBER),
                                LocalDate.MAX,
                                "Автор проекта.",
                                "Создание, организация и проведение Java онлайн " +
                                        "проектов и стажировок.")
                        )
                )
        );
        organizationContent.add(new Organization(
                        "Wrike",
                        "https://www.wrike.com/",
                        Collections.singletonList(new Experience(
                                DateUtil.of(2014, Month.OCTOBER),
                                DateUtil.of(2016, Month.JANUARY),
                                "Старший разработчик (backend)",
                                "Проектирование и разработка онлайн платформы " +
                                        "управления проектами Wrike (Java 8 API, Maven, Spring, " +
                                        "MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                                        "Двухфакторная аутентификация, авторизация " +
                                        "по OAuth1, OAuth2, JWT SSO.")
                        )
                )
        );
        organizationContent.add(new Organization(
                        "RIT Center",
                        null,
                        Collections.singletonList(new Experience(
                                DateUtil.of(2012, Month.APRIL),
                                DateUtil.of(2014, Month.OCTOBER),
                                "Java архитектор",
                                "Организация процесса разработки системы ERP для " +
                                        "разных окружений: релизная политика, версионирование, " +
                                        "ведение CI (Jenkins), миграция базы (кастомизация " +
                                        "Flyway), конфигурирование системы (pgBoucer, Nginx), " +
                                        "AAA via SSO. Архитектура БД и серверной части системы.")
                        )
                )
        );
        experience.setContent(organizationContent);

        OrganizationSection education =
                (OrganizationSection) resume.getSections().get(SectionType.EDUCATION);
        organizationContent = new ArrayList<>();
        organizationContent.add(new Organization(
                        "Coursera",
                        "https://www.coursera.org/course/progfun",
                        Collections.singletonList(new Experience(
                                DateUtil.of(2013, Month.MARCH),
                                DateUtil.of(2013, Month.MAY),
                                "\"Functional Programming Principles " +
                                        "in Scala\" by Martin Odersky",
                                null)
                        )
                )
        );
        organizationContent.add(new Organization(
                        "Siemens AG",
                        "http://www.siemens.ru/",
                        Collections.singletonList(new Experience(
                                DateUtil.of(2005, Month.JANUARY),
                                DateUtil.of(2005, Month.APRIL),
                                "3 месяца обучения мобильным IN сетям (Берлин)",
                                null)
                        )
                )
        );
        organizationContent.add(new Organization(
                        "Санкт-Петербургский национальный исследовательский университет " +
                                "информационных технологий, механики и оптики",
                        "http://www.ifmo.ru/",
                        Arrays.asList(new Experience(
                                        DateUtil.of(1993, Month.SEPTEMBER),
                                        DateUtil.of(1996, Month.JULY),
                                        "Аспирантура (программист С, С++)",
                                        null),
                                new Experience(
                                        DateUtil.of(1987, Month.SEPTEMBER),
                                        DateUtil.of(1993, Month.JULY),
                                        "Инженер (программист Fortran, C)",
                                        null)
                        )
                )
        );
        education.setContent(organizationContent);
        return resume;
    }
}
