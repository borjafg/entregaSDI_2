package uo.sdi.presentation.task;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.TaskDTO;
import uo.sdi.presentation.util.UserInfo;
import alb.util.log.Log;

@ManagedBean(name = "bean_inbox")
@ViewScoped
public class BeanInboxTasks implements Serializable {

    private static final long serialVersionUID = 52040729138582L;

    private List<TaskDTO> tasks;

    private boolean verFinalizadas = false;

    public BeanInboxTasks() {
	cargarTareas();
    }

    private void cargarTareas() {
	UserInfo user = (UserInfo) FacesContext.getCurrentInstance()
		.getExternalContext().getSessionMap().get("user");
	Long userId = user.getId();

	try {
	    TaskService taskServ = Services.getTaskService();

	    tasks = taskServ.findInboxTasksByUserId(userId);

	    if (verFinalizadas) {
		tasks.addAll(taskServ.findFinishedInboxTasksByUserId(userId));
	    }
	}

	catch (BusinessException bs) {
	    Log.error("Ha ocurrido un error al listar las tareas de la "
		    + "categoría inbox del usuario. Causa: %s", bs.getMessage());

	    throw new RuntimeException("Error al listar las tareas de la "
		    + "categoria inbox del usuario.");
	}
    }

    public List<TaskDTO> getTasks() {
	return tasks;
    }

}