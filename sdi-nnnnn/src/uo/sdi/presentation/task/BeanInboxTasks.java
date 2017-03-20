package uo.sdi.presentation.task;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.TaskDTO;
import uo.sdi.presentation.util.InboxSorter;
import uo.sdi.presentation.util.UserInfo;
import alb.util.date.DateUtil;
import alb.util.log.Log;

@ManagedBean(name = "bean_inbox")
@ViewScoped
public class BeanInboxTasks implements Serializable {

    private static final long serialVersionUID = 52040729138582L;

    private List<TaskDTO> tasks;

    private InboxSorter inboxSorter = new InboxSorter();

    @ManagedProperty(value = "#{bean_categ}")
    private BeanCategories beanCateg;

    // ============================
    // Getters y Setters
    // ============================

    public BeanCategories getBeanCateg() {
	return beanCateg;
    }

    public void setBeanCateg(BeanCategories beanCateg) {
	this.beanCateg = beanCateg;
    }

    public InboxSorter getInboxSorter() {
	return inboxSorter;
    }

    public List<TaskDTO> getTasks() {
	return tasks;
    }

    // ============================
    // Inicialización
    // ============================

    @PostConstruct
    public void init() {
	// si no existe lo creamos e inicializamos
	if (beanCateg == null) {
	    Log.trace("Creando el bean_categ --> No existía");
	    beanCateg = new BeanCategories();

	    FacesContext.getCurrentInstance().getExternalContext()
		    .getSessionMap().put("bean_categ", beanCateg);
	}

	cargarTareas();
    }

    // ============================
    // Métodos
    // ============================

    private void cargarTareas() {
	UserInfo user = (UserInfo) FacesContext.getCurrentInstance()
		.getExternalContext().getSessionMap().get("user");
	Long userId = user.getId();

	try {
	    TaskService taskServ = Services.getTaskService();

	    tasks = taskServ.findInboxTasksByUserId(userId);
	    Log.debug("Añadidas tareas sin finalizar del usuario [%s] que "
		    + "se encuentran en la categoría inbox.", user.getLogin());

	    if (beanCateg.getMostrarFinalizadas()) {
		tasks.addAll(taskServ.findFinishedInboxTasksByUserId(userId));
		Log.debug("Añadidas tareas finalizadas del usuario [%s] que "
			+ "pertenecen a la categoría inbox", user.getLogin());
	    }
	}

	catch (BusinessException bs) {
	    Log.error("Ha ocurrido un error al listar las tareas de la "
		    + "categoría inbox del usuario [%s]. Causa: %s",
		    user.getLogin(), bs.getMessage());

	    throw new RuntimeException("Error al listar las tareas de la "
		    + "categoria inbox del usuario " + user.getLogin());
	}
    }

    public boolean estaRetrasada(TaskDTO tarea) {
	if (tarea.getFinished() == null
		&& DateUtil.isBefore(tarea.getPlanned(), DateUtil.today())) {
	    return true;
	}

	return false;
    }

}