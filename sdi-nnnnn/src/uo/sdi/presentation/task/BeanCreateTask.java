package uo.sdi.presentation.task;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.CategoryDTO;
import uo.sdi.infrastructure.Services;
import uo.sdi.presentation.util.UserInfo;
import alb.util.log.Log;

@ManagedBean(name = "bean_new_task")
@ViewScoped
public class BeanCreateTask implements Serializable {

    private static final long serialVersionUID = -655967066031189L;

    private String name;
    private String comments;
    private String category; // nombre de la categoría
    private Map<String, CategoryDTO> categories;
    private Date planned;

    // =============================
    // Inicialización
    // =============================

    @PostConstruct
    public void init() {
	UserInfo user = (UserInfo) FacesContext.getCurrentInstance()
		.getExternalContext().getSessionMap().get("user");

	try {
	    categories = new HashMap<String, CategoryDTO>();
	    // cargar lista de categorias

	    categories.put("--------", null);
	    categories.put("dfge", null);
	    categories.put("7654", null);

	    Log.debug("Cargada lista de categorías del usuario [%s] para que "
		    + "pueda decidir la categoría de la tarea que va a crear",
		    user.getLogin());
	}

	catch (Exception excep) {
	    Log.error("Ha ocurrido un error al listar las categorías del "
		    + "usuario [%s]", user.getLogin());
	}
    }

    // =============================
    // Métodos
    // =============================

    public String crearTarea() {
	try {
	    UserInfo user = (UserInfo) FacesContext.getCurrentInstance()
		    .getExternalContext().getSessionMap().get("user");

	    Services.getTaskService().createTask(null);

	    Log.debug("Se ha creado una nueva tarea para el usuario [%s]. "
		    + "Datos de la tarea - [nombre: %s, comentarios: %s, "
		    + "categoría: %s, planeada para: %D]", user.getLogin(),
		    comments, category, planned);

	    return "exito";
	}

	catch (BusinessException bs) {
	    Log.error("No se ha podido crear la nueva tarea. Causa: %s",
		    bs.getMessage());

	    return "fallo";
	}

	catch (Exception ex) {
	    return "error";
	}
    }

    // =============================
    // Getters y Setters
    // =============================

    public void setName(String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public void setComments(String comments) {
	this.comments = comments;
    }

    public String getComments() {
	return comments;
    }

    public void setCategory(String category) {
	this.category = category;
    }

    public String getCategory() {
	return category;
    }

    public Map<String, CategoryDTO> getCategories() {
	return categories;
    }

    public void setPlanned(Date planned) {
	this.planned = planned;
    }

    public Date getPlanned() {
	return planned;
    }

    // =============================
    // toString
    // =============================

    @Override
    public String toString() {
	return "BeanCreateTask [name=" + name + ", comments=" + comments
		+ ", category=" + category + ", planned=" + planned + "]";
    }

}