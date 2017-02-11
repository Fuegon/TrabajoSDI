package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;
import uo.sdi.dto.util.Cloner;
import alb.util.log.Log;

public class CrearUsuarioAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado="EXITO";

		//Extraer datos de la peticion
		String usuario=request.getParameter("usuario");
		String email=request.getParameter("email");
		String newPass = request.getParameter("newPass");
		String newPassAgain = request.getParameter("newPassAgain");
		User user= new User();

		try {
			UserService userService = Services.getUserService();
	
			HttpSession session=request.getSession();
			boolean esta = userService.isLogin(usuario);
			if(usuario.isEmpty()||usuario.length()<4
					||esta)
			{
				Log.debug("El usuario no es correcto");
				resultado= "FRACASO";
				return resultado;
			}
			user.setLogin(usuario);
			
			if(!email.contains("@")||!email.contains("."))
			{
				Log.debug("El email no es correcto");
				resultado= "FRACASO";
				return resultado;
			}
			user.setEmail(email);
			
			if(!newPass.equals(newPassAgain)||newPass.length()<4)
			{
				Log.debug("El password no es correcto");
				resultado= "FRACASO";
				return resultado;
			}
			user.setPassword(newPass);
			
			userService.registerUser(user);
			
			session.invalidate();
			}
			catch (BusinessException b) {
				Log.debug("Algo ha ocurrido actualizando los datos de [%s]: %s", 
						user.getLogin(),b.getMessage());
				resultado="FRACASO";
			}
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}
}
