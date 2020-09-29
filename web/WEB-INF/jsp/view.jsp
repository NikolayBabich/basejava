<%--suppress XmlPathReference --%>
<%@ page import="ru.javaops.basejava.webapp.util.HtmlUtil" %>
<%@ page import="ru.javaops.basejava.webapp.model.TextSection" %>
<%@ page import="ru.javaops.basejava.webapp.model.ListSection" %>
<%@ page import="ru.javaops.basejava.webapp.model.OrganizationSection" %>
<%@ page import="ru.javaops.basejava.webapp.model.Organization" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javaops.basejava.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png" alt="Изменить"></a></h2>
    <p>
        <c:forEach var="contactType" items="${resume.contacts.keySet()}">
            <jsp:useBean id="contactType" type="ru.javaops.basejava.webapp.model.ContactType"/>
            <%--<%=HtmlUtil.getContactImgHtml(contactType)%>&nbsp;--%>${resume.getContact(contactType)}<br>
        </c:forEach>
    <p>
    <hr>
    <table style="padding: 2px">
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.javaops.basejava.webapp.model.SectionType, ru.javaops.basejava.webapp.model.AbstractSection>"/>
            <tr>
                <td colspan="2"><h2><a id="type.name">${sectionEntry.key.title}</a></h2></td>
            </tr>
            <c:choose>
                <c:when test="${sectionEntry.key == 'OBJECTIVE' || sectionEntry.key == 'PERSONAL'}">
                    <tr>
                        <td colspan="2">
                                ${sectionEntry.value.getContent()}
                        </td>
                    </tr>
                </c:when>
                <c:when test="${sectionEntry.key == 'ACHIEVEMENT' || sectionEntry.key == 'QUALIFICATIONS'}">
                    <tr>
                        <td colspan="2">
                            <ul>
                                <c:forEach var="line" items="${sectionEntry.value.getContent()}">
                                    <li>${line}
                                    </li>
                                </c:forEach>
                            </ul>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${sectionEntry.key == 'EXPERIENCE' || sectionEntry.key == 'EDUCATION'}">
                    <c:forEach var="organization" items="${sectionEntry.value.getContent()}">
                        <tr>
                            <td colspan="2">
                                <h3>${organization.homePage}</h3>
                            </td>
                        </tr>
                        <c:forEach var="experience" items="${organization.experiences}">
                            <tr>
                                <td style="width: 20%; vertical-align: top">${experience.toDateString()}</td>
                                <td><b>${experience.title}</b><br>${experience.description}</td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>JSP Error!</tr>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
