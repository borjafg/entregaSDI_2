package uo.sdi.business.impl.user;

import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.CommandExecutor;
import uo.sdi.business.impl.user.command.FindLoggableUSerCommand;
import uo.sdi.business.impl.user.command.RegisterUserCommand;
import uo.sdi.business.impl.user.command.UpdateUserDetailsCommand;
import uo.sdi.dto.UserDTO;

public class UserServiceImpl implements UserService {

    @Override
    public void registerUser(final UserDTO user) throws BusinessException {
	new CommandExecutor<Void>().execute(new RegisterUserCommand(user));
    }

    @Override
    public void updateUserDetails(final UserDTO user) throws BusinessException {
	new CommandExecutor<Void>().execute(new UpdateUserDetailsCommand(user));
    }

    @Override
    public UserDTO findLoggableUser(final String login, final String password)
	    throws BusinessException {

	return new CommandExecutor<UserDTO>()
		.execute(new FindLoggableUSerCommand(login, password));
    }

}