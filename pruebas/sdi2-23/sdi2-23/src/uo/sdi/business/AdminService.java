package uo.sdi.business;

import java.util.List;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.UserDTO;

public interface AdminService {

    public void deepDeleteUser(Long id) throws BusinessException;

    public void restartDatabase() throws BusinessException;

    public void disableUser(Long id) throws BusinessException;

    public void enableUser(Long id) throws BusinessException;

    public List<UserDTO> findAllUsers() throws BusinessException;

    public UserDTO findUserById(Long id) throws BusinessException;

}
