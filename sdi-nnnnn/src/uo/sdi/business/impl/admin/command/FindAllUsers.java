package uo.sdi.business.impl.admin.command;

import java.util.List;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.dto.DTOadapter;
import uo.sdi.dto.UserDTO;
import uo.sdi.persistence.UserFinder;

public class FindAllUsers implements Command<List<UserDTO>> {

    @Override
    public List<UserDTO> execute() throws BusinessException {
	return DTOadapter.usersToDTO(UserFinder.findAll());
    }

}