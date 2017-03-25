package uo.sdi.presentation.task;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import uo.sdi.business.TaskService;
import uo.sdi.dto.CategoryDTO;
import uo.sdi.infrastructure.Services;
import uo.sdi.presentation.util.UserInfo;
import alb.util.log.Log;

public class AbstractBeanModifyTasks {

    protected String name;
    protected String comments;
    protected Long category; // identificador de la categoría
    protected Map<String, Long> categories;
    protected Date planned;

    protected Date today = new Date();

    // =============================
    // Inicialización
    // =============================

    protected void cargarCategorias() {
	UserInfo user = (UserInfo) FacesContext.getCurrentInstance()
		.getExternalContext().getSessionMap().get("user");

	try {
	    TaskService taskServ = Services.getTaskService();

	    List<CategoryDTO> categs = taskServ.findCategoriesByUserId(user
		    .getId());

	    categories = new LinkedHashMap<String, Long>();
	    // cargar lista de categorias

	    for (CategoryDTO categ : categs) {
		categories.put(categ.getName(), categ.getId());
	    }

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

    public void setCategory(Long category) {
	this.category = category;
    }

    public Long getCategory() {
	return category;
    }

    public Map<String, Long> getCategories() {
	return categories;
    }

    public void setPlanned(Date planned) {
	this.planned = planned;
    }

    public Date getPlanned() {
	return planned;
    }

    public Date getToday() {
	return today;
    }

}