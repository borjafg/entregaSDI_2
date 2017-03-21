package uo.sdi.presentation.task;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "bean_categ")
@ViewScoped
public class BeanCategories implements Serializable {

    private static final long serialVersionUID = -571806116577352L;

    private Boolean mostrarFinalizadas = false;

    public BeanCategories() {

    }

    public void setMostrarFinalizadas(Boolean mostrarFinalizadas) {
	this.mostrarFinalizadas = mostrarFinalizadas;
    }

    public Boolean getMostrarFinalizadas() {
	return mostrarFinalizadas;
    }

    public String goToInbox() {
	FacesContext.getCurrentInstance().getExternalContext().getFlash()
		.put("mostrarFinalizadas", new Boolean(mostrarFinalizadas));

	return "inbox";
    }

}