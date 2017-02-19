<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="uo.sdi.dto.Task" import="java.util.Map"%>
<!DOCTYPE html>
<html>
<head>
<title>TaskManager - Eliminar categoria</title>
<%@ include file="styles.jsp"%>
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

	<h1>Eliminas Categoria</h1>
	<h2>
		Usuario
		<jsp:getProperty property="login" name="user" /></h2>
	<form action="eliminar2Categoria" method="POST">
		<table>
			<tr>
				<td>Esta seguro que quiere elimnar esta tarea</td>
			</tr>
			<tr>
				<td><input type="hidden" name="id" size="15"
					value="<jsp:getProperty property="id" name="categoria" />"></td>
			</tr>
			<tr>
				<td>Title: ${categoria.name}</td>
			</tr>

			<tr>
				<td><input class="button danger" type="submit" value="Eliminar"></td>
			</tr>
		</table>
	</form>
	<br />
	<%@ include file="mostrarTareas.jsp"%>
	<br />
	<%@ include file="cerrarSesion.jsp"%>

	<%@ include file="pieDePagina.jsp"%>
</body>
</html>
