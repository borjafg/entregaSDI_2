package uo.sdi.business;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.UserDTO;

public interface UserService {

    public void registerUser(UserDTO user) throws BusinessException;

    public void updateUserDetails(UserDTO user) throws BusinessException;

    public UserDTO findLoggableUser(String login, String password)
	    throws BusinessException;

}