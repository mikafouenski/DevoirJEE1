<%@ include file="/WEB-INF/jsp/include.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table>
		<c:forEach items="${groups}" var="group">
			<tr>
				<td><c:out value="${group.id}"></c:out></td>
				<td><c:out value="${group.name}"></c:out></td>
				<td><a href="${edit}?id=${group.id}">Editer</a></td>
				<td><a href="${list}?id=${group.id}">Lister</a></td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/WEB-INF/jsp/logout.jsp"%>
</body>
</html>