package uo.sdi.business.impl.user.command;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.dto.User;
import uo.sdi.persistence.Persistence;

public class FindByLoginCommand<Boolean> implements Command<Boolean>{

	private String login;
	
	public FindByLoginCommand(String login) {
		this.login = login;
	}

	@Override
	public Boolean execute() throws BusinessException {
		// TODO Auto-generated method stub
		
		User user = Persistence.getUserDao().findByLogin(login);
		
		if(user==null)
		{
			return (Boolean) new java.lang.Boolean(false);
		}
		return (Boolean) new java.lang.Boolean(true);
	}

}
