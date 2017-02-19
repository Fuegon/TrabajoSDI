package uo.sdi.tests;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.*;

public class Sesion3Tests {

    private WebTester usuario1;


	@Before
    public void prepare() {
    	usuario1=new WebTester();
    	usuario1.setBaseUrl("http://localhost:8280/sesion3.MVCCasero");
    }

  /*  @Test
    public void testListarCategorias() {
    	john.beginAt("/");  // Navegar a la URL
    	john.assertLinkPresent("listarCategorias_link_id");  // Comprobar que existe el hipervínculo
    	john.clickLink("listarCategorias_link_id"); // Seguir el hipervínculo

    	john.assertTitleEquals("TaskManager - Listado de categorías");  // Comprobar título de la página

        // La base de datos contiene 7 categorías tal y como se entrega
        int i=0;
        for (i=0;i<7;i++)
        	john.assertElementPresent("item_"+i); // Comprobar elementos presentes en la página
        john.assertElementNotPresent("item_"+i);
    }*/

   
    
    @Test
    public void testIniciarSesionSinExito() {
    	WebTester browser=new WebTester();
    	browser.setBaseUrl("http://localhost:8280/sesion3.MVCCasero");        
    	browser.beginAt("/");  // Navegar a la URL
    	browser.setTextField("nombreUsuario", "noExisto"); // Rellenar primer campo de formulario
    	browser.submit(); // Enviar formulario
    	browser.assertTitleEquals("TaskManager - Inicie sesión");  // Comprobar título de la página
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
    public void testCrearTareaBorrarla() {
    	usuario1.beginAt("/");  // Navegar a la URL
    	usuario1.assertFormPresent("validarse_form_name");  // Comprobar formulario está presente
    	usuario1.setTextField("nombreUsuario", "usuario1"); // Rellenar primer campo de formulario
    	usuario1.setTextField("password", "usuario1"); // Rellenar primer campo de formulario
    	usuario1.submit(); // Enviar formulario
    	usuario1.clickLink("mostrarLista_lin_id");
    	usuario1.setTextField("newTarea", "Cosa nueva");
    	usuario1.assertTextPresent("Cosa nueva");
    }
    
}