<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.chat.util.Utilitaire" %>
<%@ page import="com.chat.util.DataException" %>
<jsp:useBean id="util" scope="application"
             class="com.chat.util.Utilitaire"/>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<%@ page import="org.springframework.beans.factory.annotation.Autowired" %>
<%@ page import="org.springframework.web.context.support.SpringBeanAutowiringSupport" %>
<%@ page import="com.chat.service.ChatMessageService" %>

<%!
    public void jspInit() {
        ServletConfig config = getServletConfig();

        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Autowired
    private ChatMessageService gestion;
%>
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

<%
    String salon = (String) session.getAttribute("salon");

    String methode = request.getMethod();
    String nomCookie = "lastModifiedChatRoom";
    Cookie tmpCookie = Utilitaire.getCookie(request.getCookies(), nomCookie);

    int nbMessClient;
    int nbMessServeur;

    //teste de l'existence du cookie, création si nécessaire
    if (tmpCookie == null) {

        Cookie creation = new Cookie(nomCookie, "0");
        creation.setMaxAge(60);
        response.addCookie(creation);
        tmpCookie = creation;
    } else {
        if (tmpCookie.getMaxAge() == 0) {
            tmpCookie.setMaxAge(60);
        }

    }
    //teste la méthode utilisée
    if (methode.equalsIgnoreCase("get")) {

        //nb messages côté client
        nbMessClient = Integer.parseInt(tmpCookie.getValue());

        try {
            //nb messages côté serveur
            nbMessServeur = gestion.nombreMessage(salon);

            /*
            * Comparaison du nombre de messages, client/serveur.
            * Si < est vrai, alors on va chercher les nouveaux messages,
            * sinon on dit au client qu'il n'y a pas de nouveau contenu à récupérer (304)
            */

            if (gestion.nombreMessage(salon) != 0) {
                if (nbMessClient < nbMessServeur) {
                    response.addCookie(new Cookie(nomCookie, "" + gestion.nombreMessage(salon)));
                } else if (nbMessClient == nbMessServeur) {
                    response.setStatus(304);
                } else {
                    Cookie creation = new Cookie(nomCookie, "0");
                    creation.setMaxAge(60);
                    response.addCookie(creation);
                }

            } else {
                Cookie creation = new Cookie(nomCookie, "0");
                creation.setMaxAge(60);
                response.addCookie(creation);
            }
        } catch (DataException e) {
            /* TODO voir comment ameliorer cette partie là*/
            e.printStackTrace();
        }

    }
%>

<div class='row bgall'>
<div class='col-xs-12 col-md-8 col-sm-8 col-lg-8'>
<div class='panel panel-info bgelement'>
<div class='panel-heading'><h3 class='panel-title'>Listes des messages</h3>
</div>
<div class='panel-body' id="scroll_body">
<br>
<c:choose>
    <c:when test="${not empty messages}">
        <c:forEach items="${messages}" var="message">
            <div class='block_message triangle-border left'>
            <h4 class='user_user'><i class='glyphicon glyphicon-user'></i>  ${message.user.pseudo} a dit :</h4>
            <div class='date_user'> ${message.getHourFormatted()}</div>
            <div style="clear:both"></div>
            <h5 class='message_user'>${message.contenu}</h5>
            </div>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <h4>Bienvenue au salon ${salon} Dorenavant vous avez la possibilité de discuter avec tous les
        membres du salon </h4>
    </c:otherwise>
</c:choose>
</div>
</div>
</div>
<div class='col-xs-12 col-md-4 col-sm-4 col-lg-4'>
<iframe src="${pageContext.request.contextPath}/back-office/user/${salon}" name="user" style="height: 100%;"></iframe>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/script.js"></script>
</body>
</html>