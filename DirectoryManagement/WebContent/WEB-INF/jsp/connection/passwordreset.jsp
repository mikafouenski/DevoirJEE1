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
    <h1>Quel est l'identifiant</h1>
    <form:form method="POST" modelAttribute="passid">
        <form:errors path="*" cssClass="alert alert-danger" element="div" />
        <div class="form-group">
            <label for="mail">Email : </label>
            <form:input type="mail" path="mail" class="form-control" />
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-info">Valider</button>
        </div>
    </form:form>
</body>