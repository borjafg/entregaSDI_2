//package uo.sdi.presentation.user;
//
//import java.io.Serializable;
//
//import javax.faces.application.FacesMessage;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.RequestScoped;
//import javax.faces.context.FacesContext;
//
//import uo.sdi.model.User;
//import uo.sdi.model.types.UserStatus;
//import alb.util.log.Log;
//
//@ManagedBean(name = "userRegistry")
//@RequestScoped
//public class BeanUserRegistry implements Serializable {
//
//    private static final long serialVersionUID = -345254566L;
//
//    private String username = "";
//    private String password = "";
//    private String repeatPassword = "";
//    private String email = "";
//    private String name = "";
//    private String surname = "";
//
//    /**
//     * Comprueba que el usuario y la contraseña son correctos. <br />
//     * Registra un error (FacesMessage) si no lo son o si ocurre un error
//     * durante la comprobación (por ejemplo, un fallo de conexión a la base de
//     * datos)
//     * 
//     * @return exito si son correctos y logra almacenarlos, fallo en cualquier
//     *         otro caso
//     * 
//     */
//    public String registrar() {
//	User user = new User();
//
//	user.setLogin(username);
//	user.setPassword(password);
//	user.setEmail(email);
//	user.setName(name);
//	user.setSurname(surname);
//	user.setStatus(UserStatus.ACTIVE);
//
//	try {
//	    Factories.services.createUserService().save(user);
//
//	    Log.debug("Se ha registrado a un nuevo usuario [login = [%s], +"
//		    + "email = [%s], nombre = [%s], apellidos = [%s]]",
//		    username, email, name, surname);
//	}
//
//	catch (Exception excep) { // Error al ejecutar la comprobación
//	    FacesContext contexto = FacesContext.getCurrentInstance();
//
//	    if (excep.getMessage().equals("This login is already in use")) {
//		MessageManager.register(contexto, "j_idt9:registryButton",
//			"userRegistryError_loginExist",
//			FacesMessage.SEVERITY_ERROR);
//	    }
//
//	    else {
//		MessageManager.register(contexto, "j_idt9:registryButton",
//			"genericError", FacesMessage.SEVERITY_ERROR);
//	    }
//
//	    return "fallo";
//	}
//
//	return "exito";
//    }
//
//    public String getUsername() {
//	return username;
//    }
//
//    public void setUsername(String username) {
//	this.username = username;
//    }
//
//    public String getPassword() {
//	return password;
//    }
//
//    public void setPassword(String password) {
//	this.password = password;
//    }
//
//    public String getRepeatPassword() {
//	return repeatPassword;
//    }
//
//    public void setRepeatPassword(String repeatPassword) {
//	this.repeatPassword = repeatPassword;
//    }
//
//    public String getEmail() {
//	return email;
//    }
//
//    public void setEmail(String email) {
//	this.email = email;
//    }
//
//    public String getName() {
//	return name;
//    }
//
//    public void setName(String name) {
//	this.name = name;
//    }
//
//    public String getSurname() {
//	return surname;
//    }
//
//    public void setSurname(String surname) {
//	this.surname = surname;
//    }
//}