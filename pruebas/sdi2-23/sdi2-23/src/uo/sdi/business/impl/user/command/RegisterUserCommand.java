package uo.sdi.business.impl.user.command;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.business.impl.util.UserCheck;
import uo.sdi.dto.UserDTO;
import uo.sdi.model.User;
import uo.sdi.persistence.util.Jpa;

public class RegisterUserCommand implements Command<Void> {

    private UserDTO user;

    public RegisterUserCommand(UserDTO user) {
	this.user = user;
    }

    @Override
    public Void execute() throws BusinessException {
	UserCheck.isNotAdmin(user);
	UserCheck.isValidEmailSyntax(user);
	UserCheck.minLoginLength(user);
	UserCheck.minPasswordLength(user);
	UserCheck.notRepeatedLogin(user);
	UserCheck.isValidPassword(user);

	User newUser = new User(user.getLogin());

	newUser.setEmail(user.getEmail());
	newUser.setPassword(user.getPassword());

	Jpa.getManager().persist(newUser);

	return null;
    }

}