//package uo.sdi.presentation.user;
//
//import java.io.Serializable;
//import java.util.Map;
//
//import javax.faces.application.FacesMessage;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.RequestScoped;
//import javax.faces.context.FacesContext;
//import javax.servlet.http.HttpSession;
//
//import uo.sdi.model.User;
//import uo.sdi.model.types.UserStatus;
//import alb.util.log.Log;
//
//@ManagedBean(name = "login")
//@RequestScoped
//public class BeanLogin__ implements Serializable {
//
//    private static final long serialVersionUID = 6L;
//
//    private String userName = "";
//    private String password = "";
//
//    /**
//     * Comprueba que el usuario y la contraseña son correctos y registra un
//     * error (FacesMessage) si no lo son o si ocurre un error durante la
//     * comprobación (por ejemplo, un fallo de conexión a la base de datos)
//     * 
//     * @return exito si son correctos, fallo en cualquier otro caso
//     * 
//     */
//    public String validar() {
//	LoginService login = Factories.services.createLoginService();
//	User user = null;
//
//	try {
//	    // Verifica que el usuario y la contraseña son correctos y la
//	    // cuenta de usuario con la que intenta loguearse no está cancelada
//	    user = login.verify(userName, password);
//	}
//
//	catch (Exception excep) { // Error al comprobar los datos
//	    Log.error("Ha ocurrido un error al intentar comprobar los datos del"
//		    + " usuario");
//
//	    return "error";
//	}
//
//	if (user != null && user.getStatus().equals(UserStatus.ACTIVE)) {
//	    putUserInSession(user);
//	    Log.info("Ha iniciado sesión el usuario: [%s]", user.getLogin());
//
//	    return "exito";
//	}
//
//	else {
//	    FacesContext contexto = FacesContext.getCurrentInstance();
//
//	    MessageManager.register(contexto, "j_idt9:loginButton",
//		    "loginError_sendButton", FacesMessage.SEVERITY_ERROR);
//
//	    return "fallo";
//	}
//    }
//
//    private void putUserInSession(User user) {
//	Map<String, Object> session = FacesContext.getCurrentInstance()
//		.getExternalContext().getSessionMap();
//
//	session.put("user", user);
//    }
//
//    public void logout() {
//	HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
//		.getExternalContext().getSession(false);
//
//	session.invalidate();
//
//	Log.debug("Terminada la sesion del usuario");
//    }
//
//    /* ============================================================== */
//    /* ============================================================== */
//    /* ============================================================== */
//
//    public String getUserName() {
//	return userName;
//    }
//
//    public void setUserName(String userName) {
//	this.userName = userName;
//    }
//
//    public String getPassword() {
//	return password;
//    }
//
//    public void setPassword(String password) {
//	this.password = password;
//    }
//}