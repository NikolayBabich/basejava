package ru.javaops.basejava.webapp.web;

import ru.javaops.basejava.webapp.Config;
import ru.javaops.basejava.webapp.model.ContactType;
import ru.javaops.basejava.webapp.model.Link;
import ru.javaops.basejava.webapp.model.Organization;
import ru.javaops.basejava.webapp.model.OrganizationSection;
import ru.javaops.basejava.webapp.model.Resume;
import ru.javaops.basejava.webapp.model.SectionType;
import ru.javaops.basejava.webapp.storage.Storage;
import ru.javaops.basejava.webapp.util.HtmlUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.javaops.basejava.webapp.util.HtmlUtil.convertHtmlToDate;

@WebServlet("/resume")
public class ResumeServlet extends HttpServlet {
    private final Storage storage = Config.get().getSqlStorage();

    @Override
    protected final void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        boolean isCreate = "true".equals(request.getParameter("create"));
        Resume resume;
        if (isCreate) {
            resume = new Resume(fullName);
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        }

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value == null || value.trim().isEmpty()) {
                resume.getContacts().remove(type);
            } else {
                resume.setContact(type, HtmlUtil.convertValueToLink(type, value));
            }
        }

        for (SectionType type : SectionType.values()) {
            if (type != SectionType.EXPERIENCE && type != SectionType.EDUCATION) {
                String content = request.getParameter(type.name());
                if (content == null || content.trim().isEmpty()) {
                    resume.getSections().remove(type);
                } else {
                    resume.setSection(type, HtmlUtil.convertContentToSection(type, content.trim()));
                }
            } else {
                setOrganizationSection(request, resume, type);
            }
        }

        if (isCreate) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }
        response.sendRedirect("resume");
    }

    private static void setOrganizationSection(HttpServletRequest request, Resume resume, SectionType type) {
        List<Organization> organizations = new ArrayList<>();
        int experienceIdx = 0;
        String[] parameters = getParams(request, type, "organization");
        if (parameters != null) {
            for (int i = 0; i < parameters.length; i++) {
                List<Organization.Experience> experiences = new ArrayList<>();
                int experiencesSize = Integer.parseInt(getParams(request, type, "expSize")[i]);
                for (int j = 0; j < experiencesSize; j++) {
                    experiences.add(new Organization.Experience(
                            convertHtmlToDate(getParams(request, type, "startDate")[experienceIdx]),
                            convertHtmlToDate(getParams(request, type, "finishDate")[experienceIdx]),
                            getParams(request, type, "title")[experienceIdx],
                            getParams(request, type, "description")[experienceIdx++]));
                }
                organizations.add(new Organization(
                        new Link(getParams(request, type, "organization")[i], getParams(request, type, "url")[i]),
                        experiences));
            }
        }
        resume.setSection(type, new OrganizationSection(organizations));
    }

    private static String[] getParams(HttpServletRequest req, SectionType type, String s) {
        return req.getParameterValues(type.name() + "." + s);
    }

    @Override
    protected final void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                if ("0".equals(uuid)) {
                    resume = new Resume("");
                    request.setAttribute("create", true);
                } else {
                    resume = storage.get(uuid);
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}
