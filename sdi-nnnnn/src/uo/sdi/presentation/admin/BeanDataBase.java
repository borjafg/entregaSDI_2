package uo.sdi.presentation.admin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import uo.sdi.presentation.util.MessageManager;

@ManagedBean(name = "bean_database")
@RequestScoped
public class BeanDataBase {

    public String deleteAndInsert() {
	FacesContext contexto = FacesContext.getCurrentInstance();

	try {

	    return "exito";
	}

	catch (Exception ex) {
	    //MessageManager.error(contexto, componentID, messageKey);

	    return "fallo";
	}
    }

}