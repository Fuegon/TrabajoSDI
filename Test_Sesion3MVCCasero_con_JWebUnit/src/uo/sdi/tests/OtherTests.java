package uo.sdi.tests;

import net.sourceforge.jwebunit.junit.WebTester;
import org.junit.*;

public class OtherTests {

	private WebTester usuario1;


	@Before
	public void prepare() {
		usuario1=new WebTester();
		usuario1.setBaseUrl("http://localhost:8280/sesion3.MVCCasero");
	}

	@Test
	public void testIniciarSesionSinExito() {
		WebTester browser=new WebTester();
		browser.setBaseUrl("http://localhost:8280/sesion3.MVCCasero");        
		browser.beginAt("/");  // Navegar a la URL
		browser.setTextField("nombreUsuario", "noExisto"); // Rellenar primer campo de formulario
		browser.submit(); // Enviar formulario
		browser.assertTitleEquals("TaskManager - Inicie sesión");  // Comprobar título de la página
		browser.assertTextPresent("El usuario [noExisto] no está registrado");
	}
	@Test
	public void testIniciarSesionConExito() {
		usuario1.beginAt("/");  // Navegar a la URL
		usuario1.assertFormPresent("validarse_form_name");  // Comprobar formulario está presente
		usuario1.setTextField("nombreUsuario", "usuario1"); // Rellenar primer campo de formulario
		usuario1.setTextField("password", "usuario1"); // Rellenar primer campo de formulario
		usuario1.submit(); // Enviar formulario
		usuario1.assertTitleEquals("TaskManager - Página principal del usuario");  // Comprobar título de la página
		usuario1.assertTextInElement("login", "usuario1");  // Comprobar cierto elemento contiene cierto texto
		usuario1.assertTextInElement("id", "2");  // Comprobar cierto elemento contiene cierto texto
		usuario1.assertTextPresent("Iniciaste sesión el"); // Comprobar cierto texto está presente
	}

	@Test
	public void testCrearTarea() {
		usuario1.beginAt("/");  // Navegar a la URL
		validarUsuario("usuario1", "usuario1");
		usuario1.clickLink("mostrarLista_lin_id");
		usuario1.setTextField("newTarea", "Cosa nueva");
		usuario1.submit();
		usuario1.assertTextPresent("Cosa nueva");
	}
	
	@Test
	public void testCrearCategoria() {
		usuario1.beginAt("/");  // Navegar a la URL
		validarUsuario("usuario1", "usuario1");
		usuario1.clickLink("mostrarLista_lin_id");
		usuario1.setTextField("newCategory", "Cat nueva");
		usuario1.submit();
		usuario1.assertTextPresent("Cat nueva");
	}

	@Test
	public void testModificarTarea() {
		usuario1.beginAt("/");
		validarUsuario("usuario1", "usuario1");

		usuario1.clickLink("mostrarLista_lin_id");
		usuario1.setTextField("newTarea", "Cosa nueva");
		usuario1.submit();
		//TODO: Saber como clickar el link
		usuario1.assertTextPresent("Cosa nueva");
	}
	
	private void validarUsuario(String login, String password){
		usuario1.assertFormPresent("validarse_form_name");
		usuario1.setTextField("nombreUsuario", login);
		usuario1.setTextField("password", password);
		usuario1.submit();
	}

}