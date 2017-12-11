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
    <h1>Reset Password</h1>
    <h3><c:out value="${qs.question}"></c:out> </h3>
    <form:form method="POST" modelAttribute="passqs" action="${pageContext.request.contextPath}/resetpasswordid?id=${qs.id}">
        <form:errors path="*" cssClass="alert alert-danger" element="div" />
        <div class="form-group">
            <label for="reponse">Reponse :</label>
            <form:input path="reponse" class="form-control" />
            <form:errors path="reponse" cssClass="alert alert-warning" element="div" />
        </div>
        <div class="form-group">
            <label for="newPass">Nouveau password :</label>
            <form:input path="newPass" class="form-control" />
            <form:errors path="newPass" cssClass="alert alert-warning" element="div" />
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-info">Valider</button>
        </div>
    </form:form>
</body>