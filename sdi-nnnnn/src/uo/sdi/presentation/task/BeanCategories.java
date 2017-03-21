package uo.sdi.presentation.task;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "bean_categ")
@SessionScoped
public class BeanCategories implements Serializable {

    private static final long serialVersionUID = -571806116577352L;

    private Boolean mostrarFinalizadas;

    public BeanCategories() {

    }

    public void setMostrarFinalizadas(Boolean mostrarFinalizadas) {
	this.mostrarFinalizadas = mostrarFinalizadas;
    }

    public Boolean getMostrarFinalizadas() {
	return mostrarFinalizadas;
    }

    public String goToInbox() {
	return "inbox";
    }

}