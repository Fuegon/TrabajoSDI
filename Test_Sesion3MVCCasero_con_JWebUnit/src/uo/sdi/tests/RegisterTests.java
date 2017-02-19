package uo.sdi.tests;

import java.util.UUID;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.*;

public class RegisterTests {

	private WebTester usuario1;


	@Before
	public void prepare() {
		usuario1=new WebTester();
		usuario1.setBaseUrl("http://localhost:8280/sesion3.MVCCasero");
	}

	@Test
	public void testRegistrarseCorrecto(){
		usuario1.beginAt("/");
		String randomUser = getRandomUser();
		usuario1.clickLink("crearCuenta");
		usuario1.assertFormPresent("crearCuenta");
		usuario1.assertTitleEquals("TaskManager - Crear nueva cuenta");
		usuario1.setTextField("usuario", randomUser);
		usuario1.setTextField("email", randomUser+"@email.com");
		usuario1.setTextField("newPass", randomUser);
		usuario1.setTextField("newPassAgain", randomUser);
		usuario1.submit();
		usuario1.assertTextPresent("Cuenta " + randomUser + " creada con exito");
		validarUsuario(randomUser, randomUser);
		usuario1.assertTitleEquals("TaskManager - Página principal del usuario");
		usuario1.assertTextPresent(randomUser);
		usuario1.clickLink("cerrarSesion_link_id");
		usuario1.assertTitleEquals("TaskManager - Inicie sesión");
	}

	@Test
	public void testRegistrarseLoginDuplicado(){
		usuario1.beginAt("/");
		String randomUser = getRandomUser();
		usuario1.clickLink("crearCuenta");
		usuario1.assertFormPresent("crearCuenta");
		usuario1.assertTitleEquals("TaskManager - Crear nueva cuenta");
		usuario1.setTextField("usuario", randomUser);
		usuario1.setTextField("email", randomUser+"@email.com");
		usuario1.setTextField("newPass", randomUser);
		usuario1.setTextField("newPassAgain", randomUser);
		usuario1.submit();
		usuario1.assertTextPresent("Cuenta " + randomUser + " creada con exito");

		usuario1.clickLink("crearCuenta");
		usuario1.assertFormPresent("crearCuenta");
		usuario1.assertTitleEquals("TaskManager - Crear nueva cuenta");
		usuario1.setTextField("usuario", randomUser);
		usuario1.setTextField("email", randomUser+"@email.com");
		usuario1.setTextField("newPass", randomUser);
		usuario1.setTextField("newPassAgain", randomUser);
		usuario1.submit();
		usuario1.assertTitleEquals("TaskManager - Crear nueva cuenta");
		usuario1.assertTextPresent("The login is already used");


	}

	@Test
	public void testRegistrarseLoginVacio(){
		usuario1.beginAt("/");
		String randomUser = getRandomUser();
		usuario1.clickLink("crearCuenta");
		usuario1.assertFormPresent("crearCuenta");
		usuario1.assertTitleEquals("TaskManager - Crear nueva cuenta");
		usuario1.setTextField("email", randomUser+"@email.com");
		usuario1.setTextField("newPass", randomUser);
		usuario1.setTextField("newPassAgain", randomUser);
		usuario1.submit();
		usuario1.assertTitleEquals("TaskManager - Crear nueva cuenta");
		usuario1.assertTextPresent("The login must be at least 3 chars long");		
	}

	@Test
	public void testRegistrarseContraseñaVacia(){
		usuario1.beginAt("/");
		String randomUser = getRandomUser();
		usuario1.clickLink("crearCuenta");
		usuario1.assertFormPresent("crearCuenta");
		usuario1.assertTitleEquals("TaskManager - Crear nueva cuenta");
		usuario1.setTextField("usuario", randomUser);
		usuario1.setTextField("email", randomUser+"@email.com");
		usuario1.submit();
		usuario1.assertTitleEquals("TaskManager - Crear nueva cuenta");
		usuario1.assertTextPresent("The password must be at least 6 chars long");		
	}

	@Test
	public void testRegistrarseContraseñasDistintas(){
		usuario1.beginAt("/");
		String randomUser = getRandomUser();
		usuario1.clickLink("crearCuenta");
		usuario1.assertFormPresent("crearCuenta");
		usuario1.assertTitleEquals("TaskManager - Crear nueva cuenta");
		usuario1.setTextField("usuario", randomUser);
		usuario1.setTextField("email", randomUser+"@email.com");
		usuario1.setTextField("newPass", randomUser);
		usuario1.setTextField("newPassAgain", "otraCosa");
		usuario1.submit();
		usuario1.assertTitleEquals("TaskManager - Crear nueva cuenta");
		usuario1.assertTextPresent("Las contraseñas no coinciden");		
	}
	
	@Test
	public void testRegistrarseEmailIncorrecto(){
		usuario1.beginAt("/");
		String randomUser = getRandomUser();
		usuario1.clickLink("crearCuenta");
		usuario1.assertFormPresent("crearCuenta");
		usuario1.assertTitleEquals("TaskManager - Crear nueva cuenta");
		usuario1.setTextField("usuario", randomUser);
		usuario1.setTextField("email", randomUser);
		usuario1.setTextField("newPass", randomUser);
		usuario1.setTextField("newPassAgain", randomUser);
		usuario1.submit();
		usuario1.assertTitleEquals("TaskManager - Crear nueva cuenta");
		usuario1.assertTextPresent("Not a valid email");		
	}
	
	@Test
	public void testRegistrarseEmailVacio(){
		usuario1.beginAt("/");
		String randomUser = getRandomUser();
		usuario1.clickLink("crearCuenta");
		usuario1.assertFormPresent("crearCuenta");
		usuario1.assertTitleEquals("TaskManager - Crear nueva cuenta");
		usuario1.setTextField("usuario", randomUser);
		usuario1.setTextField("newPass", randomUser);
		usuario1.setTextField("newPassAgain", randomUser);
		usuario1.submit();
		usuario1.assertTitleEquals("TaskManager - Crear nueva cuenta");
		usuario1.assertTextPresent("Not a valid email");		
	}
	
	private void validarUsuario(String login, String password){
		usuario1.assertFormPresent("validarse_form_name");
		usuario1.setTextField("nombreUsuario", login);
		usuario1.setTextField("password", password);
		usuario1.submit();
	}
	
	private String getRandomUser(){
		return UUID.randomUUID().toString().substring(0, 10);
	}

}