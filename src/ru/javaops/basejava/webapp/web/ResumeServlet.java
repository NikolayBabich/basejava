package ru.javaops.basejava.webapp.web;

import ru.javaops.basejava.webapp.Config;
import ru.javaops.basejava.webapp.model.Resume;
import ru.javaops.basejava.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/resume")
public class ResumeServlet extends HttpServlet {
    private static final String HTML_HEAD = "" +
            "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <title>Resume storage WebApp</title>\n" +
            "    <style>\n" +
            "        table, th, td {\n" +
            "            border: 1px solid black;\n" +
            "            border-collapse: collapse;\n" +
            "        }\n" +
            "        th, td {\n" +
            "            padding: 5px;\n" +
            "            text-align: left;\n" +
            "        }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "<table>\n" +
            "    <tr>\n" +
            "        <th>uuid</th>\n" +
            "        <th>Full Name</th>\n" +
            "    </tr>\n";

    private static final String HTML_TAIL = "" +
            "</table>\n" +
            "<a href=\"javascript:history.back()\">Go Back</a>" +
            "</body>\n" +
            "</html>";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        Storage storage = Config.get().getSqlStorage();
        String uuid = request.getParameter("uuid");
        String html = (uuid == null) ? getAllResumes(storage) : getConcreteResume(storage, uuid);
        response.getWriter().write(html);
    }

    private static String getConcreteResume(Storage storage, String uuid) {
        Resume resume = storage.get(uuid);
        String row = getHtmlRow(resume);
        return HTML_HEAD + row + HTML_TAIL;
    }

    private static String getAllResumes(Storage storage) {
        StringBuilder sb = new StringBuilder(HTML_HEAD);
        for (Resume resume : storage.getAllSorted()) {
            sb.append(getHtmlRow(resume));
        }
        return sb.append(HTML_TAIL).toString();
    }

    private static String getHtmlRow(Resume resume) {
        return "    <tr>\n" +
                "        <td>" + resume.getUuid() + "</td>\n" +
                "        <td>" + resume.getFullName() + "</td>\n" +
                "    </tr>\n";
    }
}
