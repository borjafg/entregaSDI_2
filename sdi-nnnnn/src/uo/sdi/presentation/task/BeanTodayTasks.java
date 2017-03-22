package uo.sdi.presentation.task;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.infrastructure.Services;
import uo.sdi.presentation.util.UserInfo;
import alb.util.log.Log;

@ManagedBean(name = "bean_today")
@ViewScoped
public class BeanTodayTasks extends AbstractBeanTasks implements Serializable {

    private static final long serialVersionUID = -10248859391L;

    @PostConstruct
    public void init() {
	cargarTareas();
    }

    @Override
    protected void cargarTareas() {
	UserInfo user = (UserInfo) FacesContext.getCurrentInstance()
		.getExternalContext().getSessionMap().get("user");
	Long userId = user.getId();

	try {
	    TaskService taskServ = Services.getTaskService();

	    tasks = taskServ.findTodayTasksByUserId(userId);

	    Log.debug("Añadidas tareas sin finalizar del usuario [%s] que "
		    + "están planificadas para hoy.", user.getLogin());
	}

	catch (BusinessException bs) {
	    Log.error("Ha ocurrido un error al listar las tareas de la "
		    + "categoría hoy del usuario [%s]. Causa: %s",
		    user.getLogin(), bs.getMessage());

	    throw new RuntimeException("Error al listar las tareas de la "
		    + "categoría hoy del usuario " + user.getLogin());
	}
    }

}