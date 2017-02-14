package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;
import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;
import uo.sdi.dto.util.Cloner;


public class ModificarDatosAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado="EXITO";

		//Extraer datos de la peticion
		String nuevoEmail=request.getParameter("email");
		String newPass = request.getParameter("newPass");
		String newPassAgain = request.getParameter("newPassAgain");
		String oldPass = request.getParameter("oldPass");

		HttpSession session=request.getSession();
		User user=((User)session.getAttribute("user"));
		User userClone=Cloner.clone(user);
		if(!nuevoEmail.isEmpty()){
			userClone.setEmail(nuevoEmail);
			Log.debug("Modificado email de [%s] con el valor [%s]", 
					userClone.getLogin(), nuevoEmail);
		}
		if(canChangePassword(newPass, newPassAgain, oldPass, userClone)){
			userClone.setAndHashPassword(newPass);
			Log.debug("Modificada contraseña de [%s] con el valor [%s]", 
					userClone.getLogin(), newPass);
		}else{
			Log.debug("No se ha podido cambiar la contraseña");
		}
		try {
			UserService userService = Services.getUserService();
			userService.updateUserDetails(userClone);
			session.setAttribute("user",userClone);
		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido actualizando los datos de [%s]: %s", 
					user.getLogin(),b.getMessage());
			resultado="FRACASO";
		}
		return resultado;
	}

	private boolean canChangePassword(String newPass, String newPassAgain,
			String oldPass, User userClone) {
		return newPass.equals(newPassAgain) && oldPass.equals(userClone.getPassword());
	}

	@Override
	public String toString() {
		return getClass().getName();
	}


}
