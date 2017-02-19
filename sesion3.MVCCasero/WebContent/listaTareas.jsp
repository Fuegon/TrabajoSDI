<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Lista de tareas</title>
<%@ include file="styles.jsp"%>
</head>
<body>
	<a name="inbox_link" href="inbox">Inbox</a>
	<a name="hoy_link" href="hoy">Hoy</a>
	<a name="semana_link" href="semana">Semana</a>
	<a name="todas_link" href="tareas">Todas</a>
	<br />
	<form action="newTarea" method="POST">
		<label>Añadir tarea: <input type="text" name="newTarea"
			size="15"></label> <input class="button" type="submit" value="Añadir">
	</form>
	<form action="newCategory" method="POST">
		<label>Añadir categoria: <input type="text" name="newCategory"
			size="15"></label> <input class="button" type="submit" value="Añadir">
	</form>

	<form id="terminadas" action="inbox" method="POST">
		<label> <input type="checkbox" name="filtroTerminadas"
			value="activado"> Mostrar terminadas
		</label>
	</form>


	<br />
	<table>
		<tr>
			<td><table border="1" align="center">
					<tr>
						<th colspan="2">${titulo}</th>
					</tr>
					<tr>
						<th>ID</th>
						<th>Nombre</th>
						<th>Fecha finalizada</th>
						<th>Fecha planificada</th>
					</tr>
					<c:forEach var="entry" items="${listaTasks}" varStatus="i">
						<tr id="item_${i.index}">
							<td><a href="${entry.id}">${entry.id}</a></td>
							<td>${entry.title}</td>
							<jsp:useBean id="now" class="java.util.Date" />
							<td><fmt:formatDate
										value="${entry.finished}" pattern="dd/MM/yyyy" /></td>
							<td><span
								<c:if test="${entry.planned lt now}">class="red"</c:if>><fmt:formatDate
										value="${entry.planned}" pattern="dd/MM/yyyy" /></span></td>
							<c:if test="${entry.finished==null}">
								<td><a class="button" href="modificarTarea?Id=${entry.id}">Modificar</a></td>
								<td><a class="button" href="finalizarTarea?Id=${entry.id}">Finalizar</a></td>
							</c:if>
							<td><a class="button danger"
								href="eliminarTarea?Id=${entry.id}">Eliminar</a></td>

						</tr>
					</c:forEach>
				</table></td>
			<td><table border="1" align="center">
					<tr>
						<th colspan="2">Categorias</th>
					</tr>
					<tr>
						<th>ID</th>
						<th>Nombre</th>
					</tr>
					<c:forEach var="entry" items="${listaCategory}" varStatus="i">
						<tr id="item_${i.index}">
							<td><a href="tareaCategoria?id=${entry.id}">${entry.id}</a></td>
							<td>${entry.name}</td>
							<td><a class="button"
								href="duplicarCategoria?Id=${entry.id}">Duplicar</a></td>
							<td><a class="button"
								href="modificarCategoria?Id=${entry.id}">Modificar</a></td>
							<td><a class="button danger"
								href="eliminarCategoria?Id=${entry.id}">Eliminar</a></td>
						</tr>
					</c:forEach>
				</table></td>
		</tr>
	</table>
	<%@ include file="cerrarSesion.jsp"%>

	<%@ include file="pieDePagina.jsp"%>
</body>
</html>
