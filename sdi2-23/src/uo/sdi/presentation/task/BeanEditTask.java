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
import uo.sdi.presentation.util.MessageManager;
import uo.sdi.presentation.util.UserInfo;
import alb.util.log.Log;

@ManagedBean(name = "bean_edit_task")
@ViewScoped
public class BeanEditTask extends AbstractBeanModifyTasks implements
	Serializable {

    private static final long serialVersionUID = -655967066031189L;

    private Long idTarea;
    private TaskDTO task;

    // =============================
    // Inicialización
    // =============================

    @PostConstruct
    public void init() {
	cargarCategorias();
	cargarIdTarea();
	cargarDatosTarea();
    }

    private void cargarDatosTarea() {
	UserInfo user = (UserInfo) FacesContext.getCurrentInstance()
		.getExternalContext().getSessionMap().get("user");

	try {
	    TaskService taskServ = Services.getTaskService();

	    task = taskServ.findTaskById(idTarea);

	    title = task.getTitle();
	    comments = task.getComments();

	    if (task.getCategory() != null) {
		category = task.getCategory().getId();
	    }

	    planned = task.getPlanned();

	    Log.debug("Cargados datos de la tarea [id = %d] del usuario [%s] "
		    + "para su posterior modificación.", idTarea,
		    user.getLogin());
	}

	catch (Exception excep) {
	    Log.error("Ha ocurrido un error al listar las categorías del "
		    + "usuario [%s]", user.getLogin());
	    Log.error(excep);
	}
    }

    public void cargarIdTarea() {
	FacesContext contexto = FacesContext.getCurrentInstance();

	idTarea = (Long) contexto.getExternalContext().getFlash()
		.get("idTarea");

	if (idTarea != null) {
	    contexto.getExternalContext().getFlash().put("idTarea", idTarea);
	}

	else {
	    throw new RuntimeException(
		    "El usuario ha intentado acceder a la página de edición de"
			    + " tareas directamente. Al no saber cual es la "
			    + "tarea que se quiere editar se le redireccionará"
			    + "a la página de error.");
	}
    }

    // =============================
    // Métodos
    // =============================

    public String editarTarea() {
	FacesContext contexto = FacesContext.getCurrentInstance();
	UserInfo user = (UserInfo) contexto.getExternalContext()
		.getSessionMap().get("user");

	try {
	    task.setTitle(title);
	    task.setComments(comments);
	    task.setCategoryId(category);
	    task.setPlanned(planned);

	    Services.getTaskService().updateTask(task);

	    Log.debug("Se ha modificado la tarea [id = %d] del usuario [%s]. "
		    + "Nuevos datos de la tarea - [título: %s, "
		    + "comentarios: %s, categoría: %s, "
		    + "planeada para: %6$td/%6$tm/%6$tY]", idTarea,
		    user.getLogin(), title, comments, category, planned);

	    MessageManager.info(contexto, "mensajes_usuario",
		    "editar_tarea__exito");

	    contexto.getExternalContext().getFlash().setKeepMessages(true);

	    return "exito";
	}

	catch (BusinessException bs) {
	    Log.error("No se ha podido editar la tarea [id = %d] del usuario "
		    + "[%s]. Causa: %s", idTarea, user.getLogin(),
		    bs.getMessage());

	    cargarCategorias();

	    MessageManager.warning(contexto, "mensajes_usuario",
		    bs.getClaveFicheroMensajes());

	    return "fallo";
	}

	catch (Exception ex) {
	    Log.error("Ha ocurrido un error al intentar editar una tarea "
		    + "[id = %d] del usuario [%s]", idTarea, user.getLogin());
	    Log.error(ex);

	    return "error";
	}
    }

}