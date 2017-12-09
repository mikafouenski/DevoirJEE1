<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form:form method="POST" commandName="personFormBean">
		<form:errors path="*" cssClass="alert alert-danger" element="div" />
		<div class="form-group">
			<label for="name">Name:</label>
			<form:input class="form-control" path="name" />
			<form:errors path="name" cssClass="alert alert-warning" element="div" />
		</div>
		<div class="form-group">
			<label for="firstname">Firstname:</label>
			<form:input class="form-control" path="firstname" />
			<form:errors path="firstname" cssClass="alert alert-warning"
				element="div" />
		</div>
		<div class="form-group">
			<label for="mail">Mail:</label>
			<form:input class="form-control" path="mail" />
			<form:errors path="mail" cssClass="alert alert-warning" element="div" />
		</div>
		<div class="form-group">
			<label for="website">Website:</label>
			<form:input class="form-control" path="website" />
			<form:errors path="website" cssClass="alert alert-warning"
				element="div" />
		</div>
		<button type="submit" class="btn btn-info">Valider</button>
	</form:form>
	<%@ include file="/WEB-INF/jsp/logout.jsp"%>
</body>
</html>