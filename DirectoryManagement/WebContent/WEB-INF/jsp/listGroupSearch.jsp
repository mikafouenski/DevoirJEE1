<%@ include file="/WEB-INF/jsp/inc/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Liste Groups</title>
<%@ include file="/WEB-INF/jsp/inc/head-bootstrap.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/search.jsp"%>	
	<table>
		<c:forEach items="${groups}" var="group">
			<tr>
				<td><c:out value="${group.id}"></c:out></td>
				<td><c:out value="${group.name}"></c:out></td>
				<td><a href="${pageContext.request.contextPath}/groups/list?id=${group.id}">Editer</a></td>
				<td><a href="${pageContext.request.contextPath}/groups/list?id=${group.id}">Lister</a></td>
			</tr>
		</c:forEach>
	</table>
	
	<%@ include file="/WEB-INF/jsp/inc/logout.jsp"%>
</body>
</html>