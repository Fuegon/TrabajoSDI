<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Página principal del usuario</title>
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
	<table>
		<tr>
			<td>Id:</td>
			<td id="id"><jsp:getProperty property="id" name="user" /></td>
		</tr>
		<tr>
			<td>Datos:</td>
			<td id="datos"><form action="modificarDatos" method="POST">
					<label>Email: <input type="text" name="email" size="15"
						value="<jsp:getProperty property="email" name="user"/>"></label> <label>Nueva
						contraseña: <input type="password" name="newPass" size="15">
					</label> <label>Repita la contraseña: <input type="password"
						name="newPassAgain" size="15"></label> <label>Antigua
						contraseña: <input type="password" name="oldPass" size="15">
					</label> <input class="button" type="submit" value="Modificar">
				</form></td>
		</tr>
		<tr>
			<td>Es administrador:</td>
			<td id="isAdmin"><jsp:getProperty property="isAdmin" name="user" /></td>
		</tr>
		<tr>
			<td>Login:</td>
			<td id="login"><jsp:getProperty property="login" name="user" /></td>
		</tr>
		<tr>
			<td>Estado:</td>
			<td id="status"><jsp:getProperty property="status" name="user" /></td>
		</tr>
		<c:if test="${user.isAdmin}">
			<tr>
				<td>Opciones administrador:</td>
				<td id="adminUsers"><a class="button" href="adminUsers">Administrar
						usuarios</a></td>
			</tr>
		</c:if>
	</table>
	<c:if test="${!user.isAdmin}">
		<%@ include file="mostrarTareas.jsp"%>
	</c:if>
	<br />
	<br />
	<%@ include file="cerrarSesion.jsp"%>

	<%@ include file="pieDePagina.jsp"%>
</body>
</html>
