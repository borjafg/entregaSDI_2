package uo.sdi.presentation.task;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.CategoryDTO;
import uo.sdi.dto.TaskDTO;
import uo.sdi.infrastructure.Services;
import uo.sdi.presentation.util.MessageManager;
import uo.sdi.presentation.util.UserInfo;
import alb.util.log.Log;

@ManagedBean(name = "bean_new_task")
@ViewScoped
public class BeanCreateTask implements Serializable {

    private static final long serialVersionUID = -655967066031189L;

    private String name;
    private String comments;
    private Long category; // identificador de la categoría
    private Map<String, Long> categories;
    private Date planned;

    private Date today = new Date();

    // =============================
    // Inicialización
    // =============================

    @PostConstruct
    public void init() {
	cargarCategorias();
    }

    private void cargarCategorias() {
	UserInfo user = (UserInfo) FacesContext.getCurrentInstance()
		.getExternalContext().getSessionMap().get("user");

	try {
	    TaskService taskServ = Services.getTaskService();

	    List<CategoryDTO> categs = taskServ.findCategoriesByUserId(user
		    .getId());

	    categories = new HashMap<String, Long>();
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
    // Métodos
    // =============================

    public String crearTarea() {
	FacesContext contexto = FacesContext.getCurrentInstance();
	UserInfo user = (UserInfo) contexto.getExternalContext()
		.getSessionMap().get("user");

	try {
	    TaskDTO newTask = new TaskDTO(name, category, user.getId());

	    newTask.setComments(comments);
	    newTask.setPlanned(planned);

	    Services.getTaskService().createTask(newTask);

	    Log.debug("Se ha creado una nueva tarea para el usuario [%s]. "
		    + "Datos de la tarea - [nombre: %s, comentarios: %s, "
		    + "categoría: %s, planeada para: %5$td/%5$tm/%5$tY]",
		    user.getLogin(), name, comments, category, planned);

	    MessageManager.info(contexto, "mensajes_usuario",
		    "crear_tarea__exito");

	    contexto.getExternalContext().getFlash().setKeepMessages(true);

	    return "exito";
	}

	catch (BusinessException bs) {
	    Log.error("No se ha podido crear la nueva tarea para el usuario "
		    + "[%s]. Causa: %s", user.getLogin(), bs.getMessage());

	    cargarCategorias();

	    MessageManager.warning(contexto, "mensajes_usuario",
		    bs.getClaveFicheroMensajes());

	    return "fallo";
	}

	catch (Exception ex) {
	    Log.error("Ha ocurrido un error al intentar crear una nueva tarea "
		    + "para el usuario [%s]", user.getLogin());
	    Log.error(ex);

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

    // =============================
    // toString
    // =============================

    @Override
    public String toString() {
	return "BeanCreateTask [name=" + name + ", comments=" + comments
		+ ", category=" + category + ", planned=" + planned + "]";
    }

}