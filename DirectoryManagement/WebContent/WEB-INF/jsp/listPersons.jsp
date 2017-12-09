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
		</tr>
		<c:forEach items="${persons}" var="p">
			<tr>
				<td><c:out value="${p.id}"></c:out></td>
				<td><c:out value="${p.name}"></c:out></td>
				<td><c:out value="${p.firstname}"></c:out></td>
				<td><a href="${pageContext.request.contextPath}/persons/detail?id=${p.id}">Details</a>
                </td>
				<td><a href="${pageContext.request.contextPath}/persons/edit?id=${p.id}">Edit</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<p>
		<c:forEach begin="0" end="${nbPage }" varStatus="loop">
			<a href="${list}?id=${id}&page=${loop.index}">${loop.index}</a>
			<c:out value="   "></c:out>
		</c:forEach>
	</p>
	<%@ include file="/WEB-INF/jsp/logout.jsp"%>
</body>
</html>