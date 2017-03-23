package uo.sdi.business.impl.user.command;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.business.impl.util.UserCheck;
import uo.sdi.dto.UserDTO;
import uo.sdi.model.User;
import uo.sdi.persistence.UserFinder;

public class UpdateUserDetailsCommand implements Command<Void> {

    private UserDTO user;

    public UpdateUserDetailsCommand(UserDTO user) {
	this.user = user;
    }

    @Override
    public Void execute() throws BusinessException {
	User previous = UserFinder.findById(user.getId());

	BusinessCheck.isNotNull(previous, "El usuario no existe",
		"error_usuario_no_existe");

	UserCheck.isValidEmailSyntax(user);
	UserCheck.minLoginLength(user);
	UserCheck.minPasswordLength(user);

	previous.setEmail(user.getEmail());
	previous.setPassword(user.getPassword());

	return null;
    }

}