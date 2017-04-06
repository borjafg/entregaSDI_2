package uo.sdi.presentation.user;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.UserDTO;
import uo.sdi.infrastructure.Services;
import uo.sdi.presentation.util.MessageManager;
import alb.util.log.Log;

@ManagedBean(name = "bean_registry")
@ViewScoped
public class BeanUserRegistry implements Serializable {

    private static final long serialVersionUID = -345254566L;

    private String login = "";
    private String password = "";
    private String repeatPassword = "";
    private String email = "";

    /**
     * Comprueba que el usuario y la contraseña son correctos. <br />
     * Registra un error (FacesMessage) si no lo son o si ocurre un error
     * durante la comprobación (por ejemplo, un fallo de conexión a la base de
     * datos)
     * 
     * @return exito si son correctos y logra almacenarlos, fallo en cualquier
     *         otro caso
     * 
     */
    public String registrar() {
	UserDTO user = new UserDTO(login);

	user.setEmail(email);
	user.setPassword(password);

	UserService userServ = Services.getUserService();
	FacesContext contexto = FacesContext.getCurrentInstance();

	try {
	    Log.debug("Vamos a registrar al usuario");
	    userServ.registerUser(user);

	    contexto.getExternalContext().getFlash().setKeepMessages(true);

	    Log.debug("Se ha registrado con exito al usuario [%s]", login);
	    MessageManager.info(contexto, "panel_login", "registro__exito");

	    return "exito";
	}

	catch (BusinessException be) {
	    MessageManager.warning(contexto, "panel_registro",
		    be.getClaveFicheroMensajes());

	    Log.error("Ha ocurrido un error durante el registro de un nuevo "
		    + "usuario. Causa del error: %s", be.getMessage());

	    return "fallo";
	}

	catch (Exception excep) { // Error al ejecutar la comprobación
	    Log.error("Excepcion generada durante la creacion de un usario");
	    Log.error(excep);

	    return "error";
	}
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

    public String getRepeatPassword() {
	return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
	this.repeatPassword = repeatPassword;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

}