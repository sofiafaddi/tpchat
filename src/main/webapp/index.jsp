<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="gm" scope="application"
             class="com.chat.service.ChatMessageService"/>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE HTML5>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Chat online, Bienvenue</title>
    <!-- Responsive-->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 1 BOOTSTRAP / 2 Style CSS -->
    <link href="resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/style.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-xs-12 col-md-offset-2 col-md-8 col-sm-offset-2 col-sm-8 col-lg-offset-2 col-lg-8">
            <div class="image_accueil"><img alt="Image de bienvenue" src="resources/images/chatimage.png"></div>
            <h2>Bienvenue sur le chat en ligne</h2>
            <a href="AjaxHtml/index.html" class="btn btn-primary">Se connecter avec Ajax</a>
            <form method="POST" action="${pageContext.request.contextPath}/back-office/login">

                <c:choose>
                    <c:when test="${not empty param.username}">
                        <p class="bg-success">${param.msg} <strong>${param.username}</strong></p>
                    </c:when>
                    <c:otherwise>
                        <p>
                            Entrer votre name pour se connecter et choisissez un salon si vous vous voulez accèder un salon
                            existant cliquez sur la flêche"
                        </p>
                    </c:otherwise>
                </c:choose>

                <div class="col-xs-12 col-md-6 col-sm-6 col-lg-6">
                    <div class="input-group">
                        <span class="input-group-addon" id="basic-addon1">@</span>
                        <input type="text" autofocus class="form-control" placeholder="Pseudo" name="username"
                               aria-describedby="basic-addon1" value="${param.username}" required="required">
                    </div>
                </div>
                <div class="col-xs-12 col-md-6 col-sm-6 col-lg-6">
                    <div class="input-group">
                        <span class="input-group-addon" id="basic-addon2"><i
                                class="glyphicon glyphicon-home"></i></span>
                        <input id="basicaddon2input" type="text" class="form-control" placeholder="salon" name="channel"
                               aria-describedby="basic-addon2" required="required">
                        <a class="clickbt" href=#><i class="glyphicon glyphicon-play"></i></a>
                    </div>
                    <c:if test="${not empty gm.map}">
                        <select title="" class="salon form-control" name="salon">
                            <c:forEach items="${gm.map}" var="salon">
                                <option class="optionnumber" value="${salon}">${salon}
                                </option>
                            </c:forEach>
                        </select>
                    </c:if>
                </div>
                <a href="inscription.jsp" class="connex_lien">Tu n'est pas inscrit encore? Fais vite </a>
                <button type="submit" class="btn btn-primary">Se connecter</button>
            </form>

        </div>
    </div>
</div>
<script type="text/javascript" src="resources/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="resources/js/script.js"></script>
</body>
</html>