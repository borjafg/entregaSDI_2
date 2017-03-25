package uo.sdi.presentation.task;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.TaskDTO;
import uo.sdi.infrastructure.Services;
import uo.sdi.presentation.util.MessageManager;
import uo.sdi.presentation.util.UserInfo;
import alb.util.log.Log;

@ManagedBean(name = "bean_new_task")
@ViewScoped
public class BeanCreateTask extends AbstractBeanModifyTasks implements
	Serializable {

    private static final long serialVersionUID = -655967066031189L;

    // =============================
    // Inicialización
    // =============================

    @PostConstruct
    public void init() {
	cargarCategorias();
    }

    // =============================
    // Métodos
    // =============================

    public String crearTarea() {
	FacesContext contexto = FacesContext.getCurrentInstance();
	UserInfo user = (UserInfo) contexto.getExternalContext()
		.getSessionMap().get("user");

	try {
	    TaskDTO newTask = new TaskDTO(title, category, user.getId());

	    newTask.setComments(comments);
	    newTask.setPlanned(planned);

	    Services.getTaskService().createTask(newTask);

	    Log.debug("Se ha creado una nueva tarea para el usuario [%s]. "
		    + "Datos de la tarea - [título: %s, comentarios: %s, "
		    + "categoría: %s, planeada para: %5$td/%5$tm/%5$tY]",
		    user.getLogin(), title, comments, category, planned);

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

}