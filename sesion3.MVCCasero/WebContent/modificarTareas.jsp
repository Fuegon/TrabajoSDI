<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="uo.sdi.dto.Task" import="java.util.Map"%>
<!DOCTYPE html>
<html>
<head>
<title>TaskManager - Modificar tarea</title>
<%@ include file="styles.jsp" %>
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
	<h2>
		Usuario
		<jsp:getProperty property="login" name="user" /></h2>
	<form action="modificarTarea2" method="POST">
		<table>
			<tr>
				<td><input type="hidden" name="id" size="15"
					value="<jsp:getProperty property="id" name="tarea" />"></td>
			</tr>
			<tr>
				<td><label>Title: <input type="text" name="title"
						size="15" value="${tarea.title}">
				</label></td>
			</tr>
			<tr>
				<td><label>Comment: <textarea cols="40" rows ="5" name="comment"
						placeholder="Comentario"
						><c:if test="${tarea.comments !=null}"> ${tarea.comments}</c:if></textarea>
				</label></td>
			</tr>
			<tr>
				<td><label>Planned: <input type="text" name="planned"
						size="15" placeholder="dd/mm/yyyy"
						value='<c:if test="${tarea.planned != null}"><fmt:formatDate value="${tarea.planned}" pattern="dd/MM/yyyy" /></c:if>'>
				</label></td>
			</tr>
			<tr>
				<td><label>Category:
						<select name="categoryId">
						<option value="null">(Sin categoria)</option>
							<c:forEach var="cat" items="${categorias}" varStatus="i">
								<option value="${cat.id}"
									<c:if test="${cat.id == tarea.categoryId}">
									selected
									</c:if>>
									${cat.name}</option>
							</c:forEach>
					</select>
				</label></td>
			</tr>
			<tr>
				<td><input class="button confirm" type="submit" value="Modificar"></td>
			</tr>
		</table>

	</form>
	<br />
	<%@ include file="mostrarTareas.jsp" %>
	<br />
	<%@ include file="cerrarSesion.jsp" %>

	<%@ include file="pieDePagina.jsp"%>
</body>
</html>
