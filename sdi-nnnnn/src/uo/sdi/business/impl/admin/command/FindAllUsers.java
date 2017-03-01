package uo.sdi.business.impl.admin.command;

import java.util.List;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.business.impl.util.TypeConverter;
import uo.sdi.dto.UserDTO;
import uo.sdi.persistence.UserFinder;

public class FindAllUsers implements Command<List<UserDTO>> {

    @Override
    public List<UserDTO> execute() throws BusinessException {
	return TypeConverter.convertUsers(UserFinder.findAll());
    }

}