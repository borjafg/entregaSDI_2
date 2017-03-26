package uo.sdi.business.impl.admin;

import java.util.List;

import uo.sdi.business.AdminService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.admin.command.DeepDeleteUserCommand;
import uo.sdi.business.impl.admin.command.DisableUserCommand;
import uo.sdi.business.impl.admin.command.EnableUserCommand;
import uo.sdi.business.impl.admin.command.FindAllUsers;
import uo.sdi.business.impl.admin.command.RestartDatabase;
import uo.sdi.business.impl.command.Command;
import uo.sdi.business.impl.command.CommandExecutor;
import uo.sdi.dto.DTOadapter;
import uo.sdi.dto.UserDTO;
import uo.sdi.persistence.UserFinder;

public class AdminServiceImpl implements AdminService {

    @Override
    public void deepDeleteUser(final Long id) throws BusinessException {
	new CommandExecutor<Void>().execute(new DeepDeleteUserCommand(id));
    }

    @Override
    public void restartDatabase() throws BusinessException {
	new CommandExecutor<Void>().execute(new RestartDatabase());
    }

    @Override
    public void disableUser(final Long id) throws BusinessException {
	new CommandExecutor<Void>().execute(new DisableUserCommand(id));
    }

    @Override
    public void enableUser(final Long id) throws BusinessException {
	new CommandExecutor<Void>().execute(new EnableUserCommand(id));
    }

    @Override
    public List<UserDTO> findAllUsers() throws BusinessException {
	return new CommandExecutor<List<UserDTO>>().execute(new FindAllUsers());
    }

    @Override
    public UserDTO findUserById(final Long id) throws BusinessException {
	return new CommandExecutor<UserDTO>().execute(new Command<UserDTO>() {

	    @Override
	    public UserDTO execute() throws BusinessException {
		return DTOadapter.userToDTO(UserFinder.findById(id));
	    }
	});
    }

}