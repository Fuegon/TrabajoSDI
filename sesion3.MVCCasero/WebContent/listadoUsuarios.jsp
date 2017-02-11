<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Listado de categorías</title>
</head>
<body>
	<table border="1" align="center">
		<tr>
			<th>id</th>
			<th>Login</th>
			<th>Email</th>
			<th>Es administrador</th>
			<th>Status</th>
			<th>Acciones</th>
		</tr>
		<c:forEach var="entry" items="${allUsers}" varStatus="i">
			<tr id="user_${i.index}">
				<td>${entry.id}</td>
				<td>${entry.login}</td>
				<td>${entry.email}</td>
				<td>${entry.isAdmin}</td>
				<td>${entry.status}</td>
				<td>
					<c:choose>
						<c:when test="${entry.status} == ENABLED">
							<a href="">Desactivar</a>
						</c:when>
						<c:otherwise>
							<a href="">Activar</a>
						</c:otherwise>
					</c:choose>
					<a href="">Borrar usuario</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="pieDePagina.jsp"%>
</body>
</html>