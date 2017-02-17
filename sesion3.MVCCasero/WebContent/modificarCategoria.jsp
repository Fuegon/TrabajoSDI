<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="uo.sdi.dto.Task" import="java.util.Map"%>
<!DOCTYPE html>
<html>
<head>
<title>TaskManager - Página principal del usuario</title>
</head>
<body>
	<i>Iniciaste sesión el <fmt:formatDate
			pattern="dd-MM-yyyy' a las 'HH:mm"
			value="${sessionScope.fechaInicioSesion}" /> (usuario número
		${contador})
	</i>
	<br />
	<br />
	<jsp:useBean id="user" class="uo.sdi.dto.User" scope="session" />
	<jsp:useBean id="categoria" class="uo.sdi.dto.Category" scope="request" />

	<h1>Modificar Categoria</h1>
	<h2>
		Usuario
		<jsp:getProperty property="login" name="user" /></h2>
	<form action="modificar2Categoria" method="POST">
		<table>
			<tr>
				<td><input type="hidden" name="id" size="15"
					value="<jsp:getProperty property="id" name="categoria" />"></td>
			</tr>
			<tr>
				<td><label>Title: <input type="text" name="name"
						size="15" value="${categoria.name}">
				</label></td>
			</tr>
			
			<tr>
				<td><input type="submit" value="Modificar"></td>
			</tr>
		</table>

		<%
			request.setAttribute("categoria", categoria);
		%>
	</form>
	<br />
	<a id="mostrarLista_lin_id" href="tareas">Mostrar tareas</a>
	<br />
	<a id="cerrarSesion_link_id" href="cerrarSesion">Cerrar sesión</a>

	<%@ include file="pieDePagina.jsp"%>
</body>
</html>
