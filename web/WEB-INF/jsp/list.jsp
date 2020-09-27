<%--suppress XmlPathReference --%>
<%@ page import="ru.javaops.basejava.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table>
        <caption><b><u>Список всех резюме</u></b></caption>
        <tr>
            <th>Имя</th>
            <th>Телефон</th>
            <th>E-mail</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach var="resume" items="${resumes}">
            <jsp:useBean id="resume" type="ru.javaops.basejava.webapp.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td>${resume.getContact(ContactType.PHONE_NUMBER)}</td>
                <td>${resume.getContact(ContactType.EMAIL)}</td>
                <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png" alt="Удалить"></a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png" alt="Изменить"></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
