<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Página principal del usuario</title>
</head>
<body>
	<table border="1" align="center">
			<tr>
				<th>ID</th>
				<th>Nombre</th>
			</tr>
		<c:forEach var="entry" items="${listaTasks}" varStatus="i">
			<tr id="item_${i.index}">
				<td><a href="${entry.id}">${entry.id}</a></td>
				<td>${entry.title}</td>
			</tr>
		</c:forEach>
	</table>
	<a id="cerrarSesion_link_id" href="cerrarSesion">Cerrar sesión</a>

	<%@ include file="pieDePagina.jsp"%>
</body>
</html>
