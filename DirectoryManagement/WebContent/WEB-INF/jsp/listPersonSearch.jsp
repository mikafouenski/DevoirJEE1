<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/search.jsp"%>	
	<table border="1">
		<tr>
			<td>id</td>
			<td>name</td>
			<td>firstname</td>
			<td>mail</td>
			<td>website</td>
			<td>birthdate</td>
		</tr>
		<c:forEach items="${persons}" var="p">
			<tr>
				<td><c:out value="${p.id}"></c:out></td>
				<td><c:out value="${p.name}"></c:out></td>
				<td><c:out value="${p.firstname}"></c:out></td>
				<td><c:out value="${p.mail}"></c:out></td>
				<td><c:out value="${p.website}"></c:out></td>
				<td><c:out value="${p.birthdate}"></c:out></td>
			</tr>
		</c:forEach>
	</table>
	
	<%@ include file="/WEB-INF/jsp/logout.jsp"%>
</body>
</html>