package uo.sdi.presentation.user;

import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.UserDTO;
import uo.sdi.presentation.util.MessageManager;
import alb.util.log.Log;

@ManagedBean(name = "bean_login")
@RequestScoped
public class BeanLogin implements Serializable {

    private static final long serialVersionUID = -6023176887921451L;

    private String login = "";
    private String password = "";

    // ==============================
    // Métodos
    // ==============================

    public String dologin() {
	FacesContext contexto = FacesContext.getCurrentInstance();
	UserService userServ = Services.getUserService();
	UserDTO user = null;

	try {
	    user = userServ.findLoggableUser(login, password);
	}

	// La cuenta se encuentra deshabilitada
	catch (BusinessException bs) {
	    Log.error("Se ha intentado iniciar sesión con una cuenta de "
		    + "usuario deshabilitada: [%s]", login);
	    Log.error("Error: %s", bs.getMessage());

	    MessageManager.warning(contexto, "panel_login",
		    "login_usuario_deshabilitado");

	    return "fallo";
	}

	catch (Exception excep) { // Error al comprobar los datos
	    Log.error("Ha ocurrido un error al intentar comprobar los datos "
		    + "del usuario [%s]", login);

	    return "error";
	}

	// (1) Se ha encontrado el usuario
	if (user != null) {
	    putUserInSession(user);

	    // (2) El usuario es administrador
	    if (user.getIsAdmin() == true) {
		Log.info("El administrador con login [%s] ha iniciado "
			+ "sesión.", user.getLogin());

		return "admin";
	    }

	    // (2) El usuario no es un administrador
	    else {
		Log.info("El usuario con login [%s] ha iniciado sesión.",
			user.getLogin());

		return "usuario";
	    }
	}

	// (3) No se ha encontrado el usuario
	else {
	    Log.debug("Se ha intentado iniciar sesión (sin éxito) con el "
		    + "siguiente usuario: [%s]", login);

	    MessageManager.warning(contexto, "panel_login",
		    "login_usuario_no_existe");

	    return "fallo";
	}
    }

    public String dologout() {
	HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
		.getExternalContext().getSession(true);

	session.invalidate();

	Log.debug("Terminada la sesion del usuario");

	return "exito";
    }

    private void putUserInSession(UserDTO user) {
	Map<String, Object> session = FacesContext.getCurrentInstance()
		.getExternalContext().getSessionMap();

	session.put("user", user);
    }

    // ==============================
    // Getters y Setters
    // ==============================

    public String getLogin() {
	return login;
    }

    public void setLogin(String login) {
	this.login = login;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

}