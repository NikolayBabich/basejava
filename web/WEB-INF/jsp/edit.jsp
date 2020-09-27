<%--suppress XmlPathReference --%>
<%@ page import="ru.javaops.basejava.webapp.model.ContactType" %>
<%@ page import="ru.javaops.basejava.webapp.util.HtmlUtil" %>
<%@ page import="ru.javaops.basejava.webapp.model.SectionType" %>
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
                        <dt>${type.title}</dt>
                        <dd><input type="text" name="${type}" size=100 value="${resume.getSection(type)}"></dd>
                    </dl>
                </c:when>
                <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
                    <dl>
                        <dt>${type.title}</dt>
                        <dd><textarea rows="8" cols="102" name="${type}">${resume.getSection(type)}</textarea></dd>
                    </dl>
                </c:when>
                <c:when test="${type == 'EXPERIENCE' || type == 'EDUCATION'}">

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
