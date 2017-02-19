<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="uo.sdi.dto.Task" import="java.util.Map"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="styles.jsp"%>
<title>TaskManager - Página principal del usuario</title>
</head>
<body>


	<h1>Eliminar usuario</h1>
	<h2>Usuario: ${userToDelete.login}</h2>
	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>Login</th>
				<th>Email</th>
				<th>Es administrador</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>${userToDelete.id}</td>
				<td>${userToDelete.login}</td>
				<td>${userToDelete.email}</td>
				<td>${userToDelete.isAdmin}</td>
		</tbody>
	</table>
	<form action="deleteUserConfirm" method="POST">
		¿Esta seguro que quiere eliminar a este usuario? <br/><input type="hidden"
			name="id" size="15" value="${userToDelete.id}"> <input
			id="eliminar" type="submit" class="button danger" value="Eliminar">
		<a id="cancelar" class="button neutral" href="adminUsers">Cancelar</a>


	</form>

	<%@ include file="pieDePagina.jsp"%>
</body>
</html>
