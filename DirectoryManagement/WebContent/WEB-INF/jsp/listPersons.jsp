<%@ include file="/WEB-INF/jsp/include.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>
		Pour le groupe
		<c:out value="${id}"> :</c:out>
	</h1>
	<table border="1">
		<tr>
			<td>id</td>
			<td>name</td>
			<td>firstname</td>
			<td>mail</td>
			<td>website</td>
			<td>birthdate</td>
			<td>password</td>
			<td>idGroup</td>
		</tr>
		<c:forEach items="${persons}" var="p">
			<tr>
				<td><c:out value="${p.id}"></c:out></td>
				<td><c:out value="${p.name}"></c:out></td>
				<td><c:out value="${p.firstname}"></c:out></td>
				<td><c:out value="${p.mail}"></c:out></td>
				<td><c:out value="${p.website}"></c:out></td>
				<td><c:out value="${p.birthdate}"></c:out></td>
				<td><c:out value="${p.password}"></c:out></td>
				<td><c:out value="${p.idGroup}"></c:out></td>
				<td><a href="${edit}?id=${group.id}">Editer</a></td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>