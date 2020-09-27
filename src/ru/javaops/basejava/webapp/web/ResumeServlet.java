package ru.javaops.basejava.webapp.web;

import ru.javaops.basejava.webapp.Config;
import ru.javaops.basejava.webapp.model.ContactType;
import ru.javaops.basejava.webapp.model.Resume;
import ru.javaops.basejava.webapp.storage.Storage;
import ru.javaops.basejava.webapp.util.HtmlUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/resume")
public class ResumeServlet extends HttpServlet {
    private final Storage storage = Config.get().getSqlStorage();

    @Override
    protected final void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume resume = storage.get(uuid);
        resume.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && !value.trim().isEmpty()) {
                resume.setContact(type, HtmlUtil.convertValueToLink(type, value));
            } else {
                resume.getContacts().remove(type);
            }
        }
        storage.update(resume);
        response.sendRedirect("resume");
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
                resume = storage.get(uuid);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}
