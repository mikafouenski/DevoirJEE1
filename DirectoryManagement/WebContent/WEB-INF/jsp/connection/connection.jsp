<%@ include file="/WEB-INF/jsp/inc/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Connection</title>
<%@ include file="/WEB-INF/jsp/inc/head-bootstrap.jsp"%>
</head>
<body>
	<h1>Authentification</h1>
	<form:form method="POST" commandName="connection">
		<form:errors path="*" cssClass="alert alert-danger" element="div" />
		<div class="form-group">
			<label for="price">Identifiant :</label>
			<form:input path="mail" class="form-control" />
			<form:errors path="mail" cssClass="alert alert-warning" element="div" />
		</div>
		<div class="form-group">
			<label for="price">Mot de passe</label>
			<form:password path="password" class="form-control" />
			<form:errors path="password" cssClass="alert alert-warning"
				element="div" />
		</div>
		<div class="form-group">
			<button type="submit" class="btn btn-info">Valider</button>
		</div>
	</form:form>
	<a class="btn btn-info" href="${pageContext.request.contextPath}/resetpassword">Mot de passe oubli� ?</a>
</body>