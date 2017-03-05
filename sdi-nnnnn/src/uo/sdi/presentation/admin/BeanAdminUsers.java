package uo.sdi.presentation.admin;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import alb.util.log.Log;
import uo.sdi.business.Services;
import uo.sdi.dto.UserDTO;

@ManagedBean(name = "bean_admin_users")
@ViewScoped
public class BeanAdminUsers {

    private List<UserDTO> users;

    @PostConstruct
    public void init() {
	try {
	    users = Services.getAdminService().findAllUsers();
	}

	catch (Exception ex) {
	    Log.error("Ha ocurrido un error al listar todos los usuarios del "
		    + "sistema");

	    throw new RuntimeException("Error al listar los usuarios del "
		    + "sistema");
	}
    }

    public List<UserDTO> getUsers() {
	return users;
    }

}