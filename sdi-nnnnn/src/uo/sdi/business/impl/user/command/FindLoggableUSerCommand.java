package uo.sdi.business.impl.user.command;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.business.impl.util.DTOadapter;
import uo.sdi.dto.UserDTO;
import uo.sdi.model.User;
import uo.sdi.model.types.UserStatus;
import uo.sdi.persistence.UserFinder;

public class FindLoggableUSerCommand implements Command<UserDTO> {

    private String login;
    private String password;

    public FindLoggableUSerCommand(String login, String password) {
	this.login = login;
	this.password = password;
    }

    @Override
    public UserDTO execute() throws BusinessException {
	User user = UserFinder.findByLoginAndPassword(login, password);

	if (user != null) {
	    if (user.getStatus().equals(UserStatus.DISABLED)) {
		throw new BusinessException("El usuario está deshabilitado",
			"error_login_usuario_deshabilitado");
	    }

	    return DTOadapter.userToDTO(user);
	}

	throw new BusinessException("El usuario no existe o la contraseña es "
		+ "incorrecta.", "error_login_usuario_no_existe");
    }

}