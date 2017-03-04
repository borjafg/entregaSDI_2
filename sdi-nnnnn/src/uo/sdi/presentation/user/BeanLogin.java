package uo.sdi.presentation.user;

import java.io.Serializable;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.dto.UserDTO;
import uo.sdi.model.types.UserStatus;
import uo.sdi.presentation.util.MessageManager;
import alb.util.log.Log;

@ManagedBean(name = "beanLogin")
@RequestScoped
public class BeanLogin implements Serializable {

    private static final long serialVersionUID = -6023176887921451L;

    private String login = "";
    private String password = "";

    // ==============================
    // Métodos de lógica
    // ==============================

    public String login() {
	FacesContext contexto = FacesContext.getCurrentInstance();
	UserService userServ = Services.getUserService();
	UserDTO user = null;

	try {
	    user = userServ.findLoggableUser(login, password);
	}

	catch (Exception excep) { // Error al comprobar los datos
	    Log.error("Ha ocurrido un error al intentar comprobar los datos del"
		    + " usuario");

//	    MessageManager.error(contexto, );

	    return "error";
	}

	if (user != null && user.getStatus().equals(UserStatus.ENABLED)) {
	    putUserInSession(user);

	    if (user.getIsAdmin() == true) {
		Log.info("El administrador con login [%s] ha iniciado sesión.",
			user.getLogin());

		return "admin";
	    }

	    else {
		return "usuario";
	    }
	}

	else {

//	    MessageManager.register(contexto, "j_idt9:loginButton",
//		    "loginError_sendButton", FacesMessage.SEVERITY_ERROR);

	    return "fallo";
	}
    }

    public void logout() {
	HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
		.getExternalContext().getSession(true);

	session.invalidate();

	Log.debug("Terminada la sesion del usuario");
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