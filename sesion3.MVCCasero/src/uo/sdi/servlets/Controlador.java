package uo.sdi.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;
import uo.sdi.acciones.*;
import uo.sdi.dto.User;
import uo.sdi.persistence.PersistenceException;

public class Controlador extends javax.servlet.http.HttpServlet {

	private static final long serialVersionUID = 1L;
	private Map<String, Map<String, Accion>> mapaDeAcciones; // <rol, <opcion,
	// objeto
	// Accion>>
	private Map<String, Map<String, Map<String, String>>> mapaDeNavegacion; // <rol,

	// <opcion,
	// <resultado,
	// JSP>>>

	public void init() throws ServletException {
		crearMapaAcciones();
		crearMapaDeNavegacion();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String accionNavegadorUsuario, resultado, jspSiguiente;
		Accion objetoAccion;
		String rolAntes, rolDespues;

		try {
			accionNavegadorUsuario = request.getServletPath().replace("/", "");
			// Obtener el string que hay a la derecha de la última /

			rolAntes = obtenerRolDeSesion(request);

			objetoAccion = buscarObjetoAccionParaAccionNavegador(rolAntes,
					accionNavegadorUsuario);

			resultado = objetoAccion.execute(request, response);

			rolDespues = obtenerRolDeSesion(request);

			jspSiguiente = buscarJSPEnMapaNavegacionSegun(rolDespues,
					accionNavegadorUsuario, resultado);

			request.setAttribute("jspSiguiente", jspSiguiente);

		} catch (PersistenceException e) {

			request.getSession().invalidate();

			Log.error(
					"Se ha producido alguna excepción relacionada con la persistencia [%s]",
					e.getMessage());
			request.setAttribute("mensajeParaElUsuario",
					"Error irrecuperable: contacte con el responsable de la aplicación");
			jspSiguiente = "/login.jsp";

		} catch (Exception e) {

			request.getSession().invalidate();

			Log.error("Se ha producido alguna excepción no manejada [%s]",
					e.getMessage());
			request.setAttribute("mensajeParaElUsuario",
					"Error irrecuperable: contacte con el responsable de la aplicación");
			jspSiguiente = "/login.jsp";
		}

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(jspSiguiente);

		dispatcher.forward(request, response);
		request.removeAttribute("mensajeParaElUsuario");
	}

	private String obtenerRolDeSesion(HttpServletRequest req) {
		HttpSession sesion = req.getSession();
		if (sesion.getAttribute("user") == null)
			return "ANONIMO";
		else if (((User) sesion.getAttribute("user")).getIsAdmin())
			return "ADMIN";
		else
			return "USUARIO";
	}

	// Obtiene un objeto accion en funci�n de la opci�n
	// enviada desde el navegador
	private Accion buscarObjetoAccionParaAccionNavegador(String rol,
			String opcion) {

		Accion accion = mapaDeAcciones.get(rol).get(opcion);
		Log.debug("Elegida acción [%s] para opción [%s] y rol [%s]", accion,
				opcion, rol);
		return accion;
	}

	// Obtiene la p�gina JSP a la que habr� que entregar el
	// control el funci�n de la opci�n enviada desde el navegador
	// y el resultado de la ejecuci�n de la acci�n asociada
	private String buscarJSPEnMapaNavegacionSegun(String rol, String opcion,
			String resultado) {

		String jspSiguiente = mapaDeNavegacion.get(rol).get(opcion)
				.get(resultado);
		Log.debug(
				"Elegida página siguiente [%s] para el resultado [%s] tras realizar [%s] con rol [%s]",
				jspSiguiente, resultado, opcion, rol);
		return jspSiguiente;
	}

	private void crearMapaAcciones() {

		mapaDeAcciones = new HashMap<String, Map<String, Accion>>();

		Map<String, Accion> mapaPublico = new HashMap<String, Accion>();
		mapaPublico.put("crearCuenta", new CrearCuentaAction());
		mapaPublico.put("validarse", new ValidarseAction());
		mapaPublico.put("crearUsuario", new CrearUsuarioAction());
		mapaDeAcciones.put("ANONIMO", mapaPublico);

		Map<String, Accion> mapaRegistrado = new HashMap<String, Accion>();
		mapaRegistrado.put("modificarDatos", new ModificarDatosAction());
		mapaRegistrado.put("cerrarSesion", new CerrarSesionAction());
		mapaRegistrado.put("tareas", new AccederTareasAction());
		mapaRegistrado.put("mostrarLista", new AccederTareasAction());
		mapaRegistrado.put("tareaCategoria", new ListarTareasCategoriaAction());
		mapaRegistrado.put("hoy", new ListarTareasHoyAction());
		mapaRegistrado.put("inbox", new ListarTareasInboxAction());
		mapaRegistrado.put("semana", new ListarTareasSemanaAction());
		mapaRegistrado.put("newTarea", new CrearTareaAction());
		mapaRegistrado.put("newCategory", new CrearCategoriaAction());
		mapaRegistrado.put("modificarTarea", new ObtenerTareaAction());
		mapaRegistrado.put("modificarTarea2", new ModificarTareaAction());
		mapaRegistrado.put("eliminarTarea", new EliminarTareaAction());
		mapaRegistrado.put("finalizarTarea", new FinalizarTareaAction());
		mapaRegistrado.put("duplicarCategoria", new DuplicarCategoriaAction());
		mapaRegistrado.put("modificarCategoria", new ObtenerCategoriaAction());
		mapaRegistrado.put("modificar2Categoria", new ModificarCategoriaAction());
		mapaRegistrado.put("eliminarCategoria", new ObtenerCategoria2Action());
		mapaRegistrado.put("eliminar2Categoria", new EliminarCategoriaAction());
		mapaRegistrado.put("inbox_terminada", new ListarTareasInboxTerminadasAction());
		mapaRegistrado.put("tareaCategoriaTerminada", new ListarTareasTerminadasCategoriaAction());

		
		mapaDeAcciones.put("USUARIO", mapaRegistrado);

		Map<String, Accion> mapaAdmin = new HashMap<String, Accion>();
		mapaAdmin.put("modificarDatos", new ModificarDatosAction());
		mapaAdmin.put("adminUsers", new AdministrarUsuariosAction());
		mapaAdmin.put("enableUser", new HabilitarUsuarioAction());
		mapaAdmin.put("disableUser", new DeshabilitarUsuarioAction());
		mapaAdmin.put("deleteUser", new TryBorrarUsuarioAction());
		mapaAdmin.put("deleteUserConfirm", new BorrarUsuarioAction());
		mapaAdmin.put("cerrarSesion", new CerrarSesionAction());
		mapaDeAcciones.put("ADMIN", mapaAdmin);
	}

	private void crearMapaDeNavegacion() {

		mapaDeNavegacion = new HashMap<String, Map<String, Map<String, String>>>();

		// Crear mapas auxiliares vacíos
		Map<String, Map<String, String>> opcionResultadoYJSP = new HashMap<String, Map<String, String>>();
		Map<String, String> resultadoYJSP = new HashMap<String, String>();

		// Mapa de navegación de anónimo
		
		//Crear Usuario
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/login.jsp");
		resultadoYJSP.put("FRACASO", "/crearCuenta.jsp");
		opcionResultadoYJSP.put("crearUsuario", resultadoYJSP);
		
		//Crear Cuenta
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/crearCuenta.jsp");
		resultadoYJSP.put("FRACASO", "/crearCuenta.jsp");
		opcionResultadoYJSP.put("crearCuenta", resultadoYJSP);
		
		// Validarse
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("FRACASO", "/login.jsp");
		opcionResultadoYJSP.put("validarse", resultadoYJSP);

		// Cerrar sesion
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/login.jsp");
		opcionResultadoYJSP.put("cerrarSesion", resultadoYJSP);

		mapaDeNavegacion.put("ANONIMO", opcionResultadoYJSP);

		// Crear mapas auxiliares vacíos
		opcionResultadoYJSP = new HashMap<String, Map<String, String>>();
		resultadoYJSP = new HashMap<String, String>();

		// Mapa de navegación de usuarios normales
		// Validarse
		resultadoYJSP.put("EXITO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("validarse", resultadoYJSP);

		// Modificar datos
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/principalUsuario.jsp");
		resultadoYJSP.put("FRACASO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("modificarDatos", resultadoYJSP);
		
		// Mostrar panel de tareas
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listaTareas.jsp");
		opcionResultadoYJSP.put("tareas", resultadoYJSP);
		
		// Mostrar de tareas hoy
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listaTareas.jsp");
		opcionResultadoYJSP.put("hoy", resultadoYJSP);
		
		// Mostrar de tareas hoy
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listaTareas.jsp");
		opcionResultadoYJSP.put("semana", resultadoYJSP);
		
		// Mostrar de tareas hoy
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listaTareas.jsp");
		opcionResultadoYJSP.put("inbox", resultadoYJSP);

		// Mostrar de categoria
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listaTareas.jsp");
		opcionResultadoYJSP.put("tareaCategoria", resultadoYJSP);

		// Crear tarea hoy
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listaTareas.jsp");
		resultadoYJSP.put("FRACASO", "/inbox");
		opcionResultadoYJSP.put("newTarea", resultadoYJSP);

		// Crear categoria
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listaTareas.jsp");
		resultadoYJSP.put("FRACASO", "/inbox");
		opcionResultadoYJSP.put("newCategory", resultadoYJSP);

		// Modificar tarea
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/modificarTareas.jsp");
		opcionResultadoYJSP.put("modificarTarea", resultadoYJSP);

		// Modificar tarea
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listaTareas.jsp");
		resultadoYJSP.put("FRACASO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("modificarTarea2", resultadoYJSP);
		
		// Eliminar tarea
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listaTareas.jsp");
		resultadoYJSP.put("FRACASO", "/listaTareas.jsp");
		opcionResultadoYJSP.put("eliminarTarea", resultadoYJSP);

		// Finalizar tarea 
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listaTareas.jsp");
		resultadoYJSP.put("FRACASO", "/listaTareas.jsp");
		opcionResultadoYJSP.put("finalizarTarea", resultadoYJSP);
		
		// Duplicar categoria 
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listaTareas.jsp");
		resultadoYJSP.put("FRACASO", "/listaTareas.jsp");
		opcionResultadoYJSP.put("duplicarCategoria", resultadoYJSP);
		
		// Obtener categoria 
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/modificarCategoria.jsp");
		resultadoYJSP.put("FRACASO", "/listaTareas.jsp");
		opcionResultadoYJSP.put("modificarCategoria", resultadoYJSP);
		
		// Modificar categoria 
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listaTareas.jsp");
		resultadoYJSP.put("FRACASO", "/listaTareas.jsp");
		opcionResultadoYJSP.put("modificar2Categoria", resultadoYJSP);
		
		// Obtener categoria 
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/eliminarCategoria.jsp");
		resultadoYJSP.put("FRACASO", "/listaTareas.jsp");
		opcionResultadoYJSP.put("eliminarCategoria", resultadoYJSP);
				
		// Modificar categoria 
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listaTareas.jsp");
		resultadoYJSP.put("FRACASO", "/listaTareas.jsp");
		opcionResultadoYJSP.put("eliminar2Categoria", resultadoYJSP);
		
		// Mostrar de tareas inbox terminadas
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listaTareas.jsp");
		opcionResultadoYJSP.put("inbox_terminada", resultadoYJSP);
		
		// Mostrar de categoria
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listaTareas.jsp");
		opcionResultadoYJSP.put("tareaCategoriaTerminada", resultadoYJSP);
		
		mapaDeNavegacion.put("USUARIO", opcionResultadoYJSP);

		// Mapa de navegación del ADMINISTRADOR
		// Validarse
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("validarse", resultadoYJSP);

		// Modificar datos
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/principalUsuario.jsp");
		resultadoYJSP.put("FRACASO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("modificarDatos", resultadoYJSP);

		// Listar usuarios
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listadoUsuarios.jsp");
		resultadoYJSP.put("FRACASO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("adminUsers", resultadoYJSP);

		// Hablitar usuarios
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/adminUsers");
		resultadoYJSP.put("FRACASO", "/adminUsers");
		opcionResultadoYJSP.put("enableUser", resultadoYJSP);

		// Deshablitar usuarios
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/adminUsers");
		resultadoYJSP.put("FRACASO", "/adminUsers");
		opcionResultadoYJSP.put("disableUser", resultadoYJSP);

		// Try borrar usuarios
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/eliminarUsuario.jsp");
		resultadoYJSP.put("FRACASO", "/adminUsers");
		opcionResultadoYJSP.put("deleteUser", resultadoYJSP);
		
		//Borrar usuario
		resultadoYJSP = new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/adminUsers");
		resultadoYJSP.put("FRACASO", "/adminUsers");
		opcionResultadoYJSP.put("deleteUserConfirm", resultadoYJSP);
		
		mapaDeNavegacion.put("ADMIN", opcionResultadoYJSP);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {

		doGet(req, res);
	}

}