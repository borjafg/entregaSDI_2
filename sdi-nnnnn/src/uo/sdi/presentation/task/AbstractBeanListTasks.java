package uo.sdi.presentation.task;

import java.util.List;

import javax.faces.context.FacesContext;

import alb.util.date.DateUtil;
import alb.util.log.Log;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.TaskDTO;
import uo.sdi.infrastructure.Services;
import uo.sdi.presentation.util.MessageManager;

public abstract class AbstractBeanListTasks {

    protected List<TaskDTO> tasks;

    public List<TaskDTO> getTasks() {
	return tasks;
    }

    protected abstract void cargarTareas();

    public String terminarTarea(Long idTarea) {
	FacesContext context = FacesContext.getCurrentInstance();

	try {
	    TaskService taskServ = Services.getTaskService();
	    taskServ.markTaskAsFinished(idTarea);

	    Log.debug("Se ha marcado como finalizada la tarea con id [%d]",
		    idTarea);

	    cargarTareas();

	    MessageManager.info(context, "mensajes_usuario",
		    "listar_tareas__exito_finalizar");

	    FacesContext.getCurrentInstance().getExternalContext().getFlash()
		    .setKeepMessages(true);

	    return "exito";
	}

	catch (BusinessException bs) {
	    Log.error("No se ha podido finalizar la tarea con id [%d]. "
		    + "Causa: %s", idTarea, bs.getMessage());

	    MessageManager.warning(context, "mensajes_usuario",
		    bs.getClaveFicheroMensajes());

	    FacesContext.getCurrentInstance().getExternalContext().getFlash()
		    .setKeepMessages(true);

	    return "fallo";
	}

	catch (Exception ex) {
	    Log.error("Ha ocurrido un error al marcar como finalizada la tarea"
		    + " con id [%d]", idTarea);
	    Log.error(ex);

	    return "error";
	}
    }

    public void editarTarea(Long id) {
	FacesContext.getCurrentInstance().getExternalContext().getFlash()
		.put("idTarea", id);
    }

    public boolean estaRetrasada(TaskDTO tarea) {
	if (tarea.getFinished() == null) {
	    if (tarea.getPlanned() == null) {
		return false;
	    }

	    if (DateUtil.isBefore(tarea.getPlanned(), DateUtil.today())) {
		return true;
	    }

	    return false;
	}

	return false;
    }

}