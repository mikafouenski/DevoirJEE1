<%@ include file="/WEB-INF/jsp/include.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>
		<c:out value="${person.firstname}  ${person.name}"></c:out>
	</h1>
	<p>
		Mail :
		<c:out value="${person.mail}"></c:out>
	</p>
	<p>
		Website :
		<c:out value="${person.website}"></c:out>
	</p>
	<p>
		<a href="${pageContext.request.contextPath}/persons/edit?id=${person.id}">Edit</a>
	</p>
	<%@ include file="/WEB-INF/jsp/logout.jsp"%>
</body>
</html>