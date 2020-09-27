<%--suppress XmlPathReference --%>
<%@ page import="ru.javaops.basejava.webapp.util.HtmlUtil" %>
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
<%--            <%=HtmlUtil.getContactImgHtml(contactType)%>&nbsp;--%>
                ${resume.getContact(contactType)}<br/>
        </c:forEach>
    <p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
