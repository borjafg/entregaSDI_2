package uo.sdi.presentation.user;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.dto.UserDTO;
import uo.sdi.presentation.util.MessageManager;
import alb.util.log.Log;

@ManagedBean(name = "bean_registry")
@RequestScoped
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

	try {
	    userServ.registerUser(user);
	}

	catch (Exception excep) { // Error al ejecutar la comprobación
	    FacesContext contexto = FacesContext.getCurrentInstance();

	    if (excep.getMessage().equals("Ese login ya está registrado")) {
		MessageManager.warning(contexto, "panel_registry",
			"registry_login_ya_exite");
	    } else if (excep.getMessage().equals("El email no es válido")) {
		MessageManager.warning(contexto, "panel_registry",
			"registry_email_no_valido");
	    } else if (excep.getMessage().equals(
		    "Las contraseña tienen que ser iguales")) {
		MessageManager.warning(contexto, "panel_registry",
			"registry_password_iguales");
	    } else if (excep.getMessage().equals(
		    "La contraseña debe tener al menos 8 caracteres")) {
		MessageManager.warning(contexto, "panel_registry",
			"registry_password_peque");
	    } else if (excep.getMessage().equals(
		    "La contraseña debe tener letras y numeros")) {
		MessageManager.warning(contexto, "panel_registry",
			"registry_password_type");
	    }

	    return "fallo";
	}

	Log.debug("Se ha iniciado con exito la sesión del usuario: %s", login);

	return "exito";
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