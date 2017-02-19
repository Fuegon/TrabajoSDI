<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Crear nueva cuenta</title>
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
	<form id="crearCuenta" action="crearUsuario" method="POST">

		<table>
			<tr>
				<td>Datos:</td>
			</tr>
			<tr>
				<td id="datos">
			<tr>
			<tr>
				<td><label>Usuario: <input type="text" name="usuario"
						size="15"></label></td>
			</tr>
			<tr>
				<td><label>Email: <input type="text" name="email"
						size="15"></label></td>
			</tr>
			<tr>
				<td><label>Nueva contraseña: <input type="password"
						name="newPass" size="15"></label></td>
			</tr>
			<tr>
				<td><label>Repita la contraseña: <input type="password"
						name="newPassAgain" size="15"></label></td>
			</tr>

			<tr>
				<td><input class="button" type="submit" value="Crear cuenta"></td>
			</tr>
		</table>
	</form>
	<a class="button" href="login.jsp">Volver</a>
	<br />

	<%@ include file="pieDePagina.jsp"%>
</body>
</html>
