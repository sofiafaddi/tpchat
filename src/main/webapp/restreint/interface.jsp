<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html5">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Page des Messages</title>
		<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
		<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
	</head>
<body>
<div class="container">
	<iframe src="${pageContext.request.contextPath}/back-office/${salon}" name="Affichageframe"></iframe>
	<form action="${pageContext.request.contextPath}/Stockage" class="formmessage" method="POST">
		<label style="margin:30px 0;">Message: </label><br/>
	<textarea class="form-control" autofocus rows="3" name="contenu"></textarea>
	<div class='rg'>
		<c:choose>
			<c:when test="${entre}">
				<label>
				<input id='r1' type='checkbox' checked name="entre" value="${entre}"/>
				Cliquer pour envoyer les messages seulement avec la touche entrer
				</label>
			</c:when>
			<c:otherwise>
				<label>
				<input id='r1' type='checkbox' name="entre" value=""/>
				Cliquer pour envoyer les messages seulement avec la touche entrer
				</label>
			</c:otherwise>
		</c:choose>
	</div>
		<input type="submit" class="btn btn-primary" value="envoyer">
	</form>

	<!--Récupération de l'objet depuis la session -->
    <!--  Bouton se déconnecter avec le parametre name deco  -->
	<a data-method="get" class='btn btn-success' href="${pageContext.request.contextPath}/back-office/logout" data-parameter-name="deco" data-parameter-value="true">Déconnexion</a>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/script.js"></script>
</body>
</html>