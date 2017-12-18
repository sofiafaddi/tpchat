<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Chat online, Inscription</title>
		<!-- Responsive-->
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- 1 BOOTSTRAP / 2 Style CSS -->
		 <link href="resources/css/bootstrap.min.css" rel="stylesheet">
		 <link href="resources/css/style.css" rel="stylesheet">
	</head>
</head>
<body>
	<div class="container">
			<div class="row">
			<h2>Bienvenue sur le chat en ligne</h2>
			<div class="image_accueil"><img alt="Image de bienvenue" src="resources/images/chatimage.png"></div>
				
				<form method ="POST" action="back-office/user">
					<p>Inscrivez vous en remplissons ce formulaire </p>

					<c:if test="${not empty param.msg}">
						<p class="bg-danger">${param.msg}</p>
					</c:if>

					<div class="col-xs-12 col-md-6 col-sm-6 col-lg-6">
						<div class="input-group">
			 				 <span class="input-group-addon" id="basic-addon_nom"><span class="glyphicon glyphicon-comment" aria-hidden="true"></span></span>
			  				<input type="text" autofocus class="form-control" placeholder="Nom" name="lastName" value="${param.lastName}" aria-describedby="basic-addon_nom" required="required">
			  			</div>
					</div>
					<div class="col-xs-12 col-md-6 col-sm-6 col-lg-6">
						<div class="input-group">
			 				 <span class="input-group-addon" id="basic-addon_prenom"><span class="glyphicon glyphicon-comment" aria-hidden="true"></span></span>
			  				<input type="text" class="form-control" placeholder="prenom" name="name" value="${param.name}"  aria-describedby="basic-addon_prenom" required="required">
			  			</div>
					</div>
					<div class="col-xs-12 col-md-6 col-sm-6 col-lg-6">
						<div class="input-group">
			 				 <span class="input-group-addon" id="basic-addon_email"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span></span>
			  				<input type="text" class="form-control" placeholder="Email" name="mail" value="${param.mail}" aria-describedby="basic-addon_email" required="required">
			  			</div>
					</div>
					<div class="col-xs-12 col-md-6 col-sm-6 col-lg-6">
						<div class="input-group">
			 				 <span class="input-group-addon" id="basic-addon_pseudo"><span class="glyphicon glyphicon-user" aria-hidden="true"></span></span>
			  				<input type="text" class="form-control" placeholder="pseudo" name="username" aria-describedby="basic-addon_email" required="required">
			  			</div>
					</div>

					<a href="index.jsp" class="connex_lien">Tu est déjà inscrit? Connecte-toi </a>
                    <button type="submit" class="btn btn-primary">S'enregistrer</button>
				</form>
			</div>
	</div>
</body>
</html>