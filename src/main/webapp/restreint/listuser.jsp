<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="util" scope="application"
             class="com.chat.util.Utilitaire"/>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<!DOCTYPE html5">
<html>
<head>
<!-- Encodage CSS BOOSTRAP et personalisé -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Refresh" content="5">
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
<title>Affichage des messages</title>
</head>
<body class="body_display">

    <div class="all-users  bgelement">
        <div class ="panel-info">
            <div class='panel-heading'>
                <h3 class='panel-title'>Listes d'utilisateurs dans ce salon</h3>
            </div>
            <ul>
            <c:forEach items="${users}" var="user">
                <li>
                <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                ${user.pseudo}
                </li>
            </c:forEach>
            </ul>
        </div>
    </div>
</body>
</html>