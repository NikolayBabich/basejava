<%--suppress XmlPathReference --%>
<%@ page import="ru.javaops.basejava.webapp.model.ContactType" %>
<%@ page import="ru.javaops.basejava.webapp.model.SectionType" %>
<%@ page import="ru.javaops.basejava.webapp.util.DateUtil" %>
<%@ page import="ru.javaops.basejava.webapp.util.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
    <form id="edit" method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="create" value="${create}">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${HtmlUtil.convertLinkToValue(resume, type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:choose>
                <c:when test="${type == 'OBJECTIVE' || type == 'PERSONAL'}">
                    <dl>
                        <dt><h4>${type.title}</h4></dt>
                        <dd><input type="text" name="${type}" size=100 value="${resume.getSection(type)}"></dd>
                    </dl>
                </c:when>
                <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
                    <dl>
                        <dt><h4>${type.title}</h4></dt>
                        <dd><textarea rows="8" cols="102" name="${type}">${resume.getSection(type)}</textarea></dd>
                    </dl>
                </c:when>
                <c:when test="${type == 'EXPERIENCE' || type == 'EDUCATION'}">
                    <dt><h4>${type.title}</h4></dt>
                    <c:forEach var="organization" items="${resume.getSection(type).getContent()}">
                        <dl>
                            <dd><b><u>Организация</u></b> <input type="text" name="${type}.organization" size=40 value="${fn:escapeXml(organization.homePage.text)}"></dd>
                            <dd>сайт <input type="text" name="${type}.url" size=30 value="${organization.homePage.url}"></dd>
                            <br>
                            <input type="hidden" name="${type}.expSize" value="${organization.experiences.size()}">
                            <c:forEach var="experience" items="${organization.experiences}">
                                <dd>Начало <input type="month" name="${type}.startDate"
                                                  value="${DateUtil.convertDateToHtml(experience.startDate)}" min="1970-01" max="2021-12"></dd>
                                <dd>Конец <input type="month" name="${type}.finishDate"
                                                 value="${DateUtil.convertDateToHtml(experience.finishDate)}" min="1970-01" max="2021-12"></dd>
                                <br>
                                <dd>Позиция <input type="text" name="${type}.title" size=50 value="${fn:escapeXml(experience.title)}"></dd>
                                <dd><textarea rows="3" cols="102" name="${type}.description">${fn:escapeXml(experience.description)}</textarea></dd>
                            </c:forEach>
                        </dl>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>JSP Error!</tr>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
