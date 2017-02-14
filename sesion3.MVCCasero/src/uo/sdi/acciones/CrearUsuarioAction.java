package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;
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
			
			user.setLogin(usuario);
						
			user.setEmail(email);
			
			if(!newPass.equals(newPassAgain))
			{
				Log.debug("El password no es correcto");
				resultado= "FRACASO";
				return resultado;
			}
<<<<<<< HEAD
			user.setAndHashPassword(newPass);
=======
>>>>>>> 3c077745505ac6597b1a80cb15523f5c6411d2cd
			
			user.setPassword(newPass);
			
			synchronized(request.getServletContext())  {
				userService.registerUser(user);
			}
			session.invalidate();
			}
			catch (BusinessException b) {
				Log.debug("Algo ha ocurrido creando usuarios los datos de [%s]: %s", 
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
