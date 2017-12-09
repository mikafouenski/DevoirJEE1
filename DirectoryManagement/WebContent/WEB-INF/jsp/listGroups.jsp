<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Recherche Groupe</title>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/search.jsp"%>	
	<table>
		<c:forEach items="${groups}" var="group">
			<tr>
				<td><c:out value="${group.id}"></c:out></td>
				<td><c:out value="${group.name}"></c:out></td>
				<td><a href="${pageContext.request.contextPath}/persons/list?id=${group.id}">Lister</a></td>
			</tr>
		</c:forEach>
	</table>
	<p>
		<c:forEach begin="0" end="${nbPage }" varStatus="loop">
			<a href="?page=${loop.index}">${loop.index}</a>
			<c:out value="   "></c:out>
		</c:forEach>
	</p>
	<%@ include file="/WEB-INF/jsp/logout.jsp"%>
</body>
</html>