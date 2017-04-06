package uo.sdi.presentation.task;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.TaskDTO;
import uo.sdi.infrastructure.Services;
import uo.sdi.presentation.util.UserInfo;
import uo.sdi.presentation.util.sorting.InboxSorter;
import uo.sdi.presentation.util.sorting.InboxTaskInfo;
import alb.util.log.Log;

@ManagedBean(name = "bean_inbox")
@ViewScoped
public class BeanInboxTasks extends AbstractBeanListTasks implements
	Serializable {

    private static final long serialVersionUID = 52040729138582L;

    private InboxSorter inboxSorter = new InboxSorter();

    private boolean mostrarFinalizadas = false; // por defecto filtrar

    // ============================
    // Getters y Setters
    // ============================

    public InboxSorter getInboxSorter() {
	return inboxSorter;
    }

    /**
     * Devuelve un objeto que permite establecer una ordenación por defecto en
     * la tabla independientemente de la ordenación que tengan las columnas.
     * 
     */
    public InboxTaskInfo getInfoFromTask(TaskDTO task) {
	if (task != null) {
	    return new InboxTaskInfo(task);
	}

	return null;
    }

    // ===============================
    // Inicialización y eliminación
    // ===============================

    @PostConstruct
    public void init() {
	Boolean mostrarFinalizadas = (Boolean) FacesContext
		.getCurrentInstance().getExternalContext().getFlash()
		.get("mostrarFinalizadas");

	// Si viene de la pagina principal del usuario, en lugar
	// de escribir directamente la URL en el navegador.
	//
	if (mostrarFinalizadas != null) {
	    this.mostrarFinalizadas = mostrarFinalizadas;
	}

	cargarTareas();
    }

    /**
     * Mientras siga en la misma página, si se indicó si mostrar tareas
     * finalizadas o no, guardar la elección del usuario.
     * 
     */
    public void mantenerOpcionFiltro() {
	Boolean mostrarFinalizadas = (Boolean) FacesContext
		.getCurrentInstance().getExternalContext().getFlash()
		.get("mostrarFinalizadas");

	if (mostrarFinalizadas != null) {
	    FacesContext.getCurrentInstance().getExternalContext().getFlash()
		    .put("mostrarFinalizadas", mostrarFinalizadas);
	}
    }

    // ============================
    // Métodos
    // ============================

    @Override
    protected void cargarTareas() {
	UserInfo user = (UserInfo) FacesContext.getCurrentInstance()
		.getExternalContext().getSessionMap().get("user");
	Long userId = user.getId();

	try {
	    TaskService taskServ = Services.getTaskService();

	    tasks = taskServ.findInboxTasksByUserId(userId);
	    Log.debug("Añadidas tareas sin finalizar del usuario [%s] que "
		    + "se encuentran en la categoría inbox.", user.getLogin());

	    if (mostrarFinalizadas) {
		Log.debug("Se ha desactivado el filtro de tareas finalizadas "
			+ "del usuario [%s]", user.getLogin());

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
		    + "categoría inbox del usuario " + user.getLogin());
	}
    }

}