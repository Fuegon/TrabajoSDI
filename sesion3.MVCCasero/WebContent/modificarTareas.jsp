<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="uo.sdi.dto.Task" import="java.util.Map" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
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
	<jsp:useBean id="tarea" class="uo.sdi.dto.Task" scope="request" />
	
	<h1>Modificar Tarea</h1>	
	<h2>Usuario <jsp:getProperty property="login" name="user" /></h2>
	<form action="modificarTarea2" method="POST">
	<table>
		<tr><td>
			 <input type="hidden" name="id" size="15" 
						value="<jsp:getProperty property="id" name="tarea" />">
		</td></tr>
		<tr><td>
			<label>Title: <input type="text" name="title" size="15"
						value="<jsp:getProperty property="title" name="tarea"/>">
			</label> 
		</td></tr>
		<tr><td>
			<label>Comment: <input type="text" name="comment" size="15"
					value="<jsp:getProperty property="comments" name="tarea"/>">
			</label> 
		</td></tr>
		<tr><td>
			<label>Planned: <input type="text" name="planned" size="15"
						value="<jsp:getProperty property="planned" name="tarea"/>">
			</label> 
		</td></tr>
		<tr><td>
			<label>Category: <input type="text" name="categoryId" size="15"
						value="<jsp:getProperty property="categoryId" name="tarea"/>">
			</label> 
		</td></tr>
		<tr><td>
			<input type="submit" value="Modificar">
		</td></tr>
	</table>
	
	<%	
		request.setAttribute("tarea2", tarea);	
	%>
	</form>
	<br/>
	<a id="mostrarLista_lin_id" href="tareas">Mostrar tareas</a>
	<br />
	<a id="cerrarSesion_link_id" href="cerrarSesion">Cerrar sesión</a>

	<%@ include file="pieDePagina.jsp"%>
</body>
</html>
