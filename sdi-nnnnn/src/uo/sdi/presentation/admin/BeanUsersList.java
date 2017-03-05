package uo.sdi.presentation.admin;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import uo.sdi.business.AdminService;
import uo.sdi.business.Services;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.UserDTO;
import uo.sdi.model.types.UserStatus;
import alb.util.log.Log;

@ManagedBean(name = "bean_users_list")
@ViewScoped
public class BeanUsersList implements Serializable {

    private static final long serialVersionUID = -631257144025427L;

    private List<UserDTO> users;

    public BeanUsersList() {
	loadUsers();
    }

    // ==================================
    // Métodos
    // ==================================

    /**
     * Carga la lista de usuarios que hay en el sistema.
     * 
     */
    private void loadUsers() {
	try {
	    users = Services.getAdminService().findAllUsers();

	    Log.debug("Se ha cargado con éxito la lista de usuarios que hay "
		    + "en el sistema");
	}

	catch (Exception ex) {
	    Log.error("Ha ocurrido un error al listar todos los usuarios del "
		    + "sistema");

	    throw new RuntimeException("Error al listar los usuarios del "
		    + "sistema");
	}
    }

    /**
     * Elimina del sistema un usuario y todas las tareas y categorías que hay
     * asociadas a él. Después de eliminar al usuario recarga la lista de
     * usuarios.
     * 
     * @param user
     *            usuario que se eliminará del sistema
     * 
     * @return resultado de intentar eliminar el usuario.
     * 
     */
    public String deleteUser(UserDTO user) {
	Long id = user.getId();

	try {
	    AdminService adminServ = Services.getAdminService();

	    adminServ.deepDeleteUser(id);
	    loadUsers();

	    return "exito";
	}

	catch (BusinessException bs) {
	    Log.error("No se ha podido eliminar del sistema al usuario con "
		    + "id [%d]", id);
	    Log.error(bs);
	    loadUsers();

	    return "fallo";
	}

	catch (Exception ex) {
	    Log.error("Ha ocurrido un error al eliminar del sistema al "
		    + "usuario con id [%d]", id);
	    Log.error(ex);

	    return "error";
	}
    }

    /**
     * Cambia el estado de un usuario. Después de cambiar el estado recarga la
     * lista de usuarios.
     * 
     * @param user
     *            usuario del que hay que cambiar el estado
     * 
     * @return resultado de intentar cambiar el estado del usuario.
     * 
     */
    public String changeStatus(UserDTO user) {
	Long id = user.getId();

	try {
	    AdminService adminServ = Services.getAdminService();
	    UserDTO userInfo = adminServ.findUserById(id);

	    if (userInfo != null) {
		if (user.getStatus().equals(UserStatus.DISABLED)) {
		    adminServ.enableUser(id);
		}

		else {
		    adminServ.disableUser(id);
		}

		Log.debug("Se ha cambiado con exito el estado del usuario con "
			+ "id [%d]", id);
		loadUsers();

		return "exito";
	    }

	    else {
		Log.debug("No se ha podido cambiar el estado del usuario con "
			+ "id [%d] porque no existe.", id);
		loadUsers();

		return "fallo";
	    }
	}

	catch (BusinessException bs) {
	    Log.error("No se ha podido cambiar el estado del usuario con id "
		    + "[%d]", id);
	    Log.error(bs);
	    loadUsers();

	    return "fallo";
	}

	catch (Exception ex) {
	    Log.error("Ha ocurrido un error al intentar cambiar el estado del "
		    + "usuario con id [%d]", id);
	    Log.error(ex);

	    return "error";
	}
    }

    // ==================================
    // Getters y Setters
    // ==================================

    public List<UserDTO> getUsers() {
	return users;
    }

}