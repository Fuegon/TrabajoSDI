package uo.sdi.tests;

import java.util.UUID;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.*;

public class ChangeDataTests {

	private WebTester user;


	@Before
	public void prepare() {
		user=new WebTester();
		user.setBaseUrl("http://localhost:8280/sesion3.MVCCasero");
	}

	@Test
	public void testSePuedeModificar(){
		registerAndLoginRandomUser();
		user.assertFormPresent("modificarDatos");
	}
	
	@Test
	public void testModificarContraseñaCorrecto(){
		String userLogin = registerAndLoginRandomUser();
		user.setTextField("newPass", "nuevoValor");
		user.setTextField("newPassAgain", "nuevoValor");
		user.setTextField("oldPass", userLogin);
		user.submit();
		user.assertTextPresent("Datos modificados correctamente");
	}
	
	@Test
	public void testModificarContraseñaDuplicadoIncorrecto(){
		String userLogin = registerAndLoginRandomUser();
		user.setTextField("newPass", "nuevoValor");
		user.setTextField("newPassAgain", "nuevoValor2");
		user.setTextField("oldPass", userLogin);
		user.submit();
		user.assertTextPresent("Las contraseñas no coinciden");
	}
	
	@Test
	public void testModificarContraseñaIncorrecta(){
		registerAndLoginRandomUser();
		user.setTextField("newPass", "nuevoValor");
		user.setTextField("newPassAgain", "nuevoValor");
		user.setTextField("oldPass", "mal");
		user.submit();
		user.assertTextPresent("La contraseña no es correcta");
	}
	
	@Test
	public void testModificarEmailCorrecto(){
		String userLogin = registerAndLoginRandomUser();
		user.setTextField("email", userLogin+"@"+userLogin+".es");
		user.submit();
		user.assertTextNotPresent("Not a valid email");
	}
	
	@Test
	public void testModificarEmailIncorrecto(){
		String userLogin = registerAndLoginRandomUser();
		user.setTextField("email", userLogin);
		user.submit();
		user.assertTextPresent("Not a valid email");
	}
	
	private String registerAndLoginRandomUser(){
		user.beginAt("/");
		String randomUser = getRandomUser();
		user.clickLink("crearCuenta");
		user.setTextField("usuario", randomUser);
		user.setTextField("email", randomUser+"@email.com");
		user.setTextField("newPass", randomUser);
		user.setTextField("newPassAgain", randomUser);
		user.submit();
		validarUsuario(randomUser, randomUser);
		
		return randomUser;
	}
	
	private void validarUsuario(String login, String password){
		user.assertFormPresent("validarse_form_name");
		user.setTextField("nombreUsuario", login);
		user.setTextField("password", password);
		user.submit();
	}
	
	private String getRandomUser(){
		return UUID.randomUUID().toString().substring(0, 10);
	}

}