package uo.sdi.business.impl.task.command.task;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.business.impl.util.TaskCheck;
import uo.sdi.dto.CategoryDTO;
import uo.sdi.dto.TaskDTO;
import uo.sdi.model.Category;
import uo.sdi.model.Task;
import uo.sdi.persistence.CategoryFinder;
import uo.sdi.persistence.TaskFinder;

public class UpdateTaskCommand implements Command<Void> {

    private TaskDTO taskDTO;

    public UpdateTaskCommand(TaskDTO taskDTO) {
	this.taskDTO = taskDTO;
    }

    @Override
    public Void execute() throws BusinessException {
	TaskCheck.titleIsNotNull(taskDTO);
	TaskCheck.titleIsNotEmpty(taskDTO);

	Task task = TaskFinder.findById(taskDTO.getId());

	usuarioCoincideTarea(task);

	BusinessCheck.isNotNull(task, "La tarea [id=" + taskDTO.getId()
		+ "] cuyos datos se intentan modificar no existe",
		"error_creacion_edicion_tarea__no_existe");

	task.setTittle(taskDTO.getTitle());
	task.setComments(taskDTO.getComments());

	if (distintaCategoria(taskDTO, task)) {
	    Category categ = null;

	    if (taskDTO.getCategory() != null
		    && taskDTO.getCategory().getId() != null) {

		categ = CategoryFinder.findById(taskDTO.getCategory().getId());

		BusinessCheck.isNotNull(categ, "La categoria que se pretende"
			+ " asociar a la tarea [id=" + taskDTO.getId() + "] no"
			+ " existe",
			"error_creacion_edicion_tarea__categoria_no_existe");
	    }

	    task.setCategory(categ);
	}

	task.setPlanned(taskDTO.getPlanned());

	return null;
    }

    private void usuarioCoincideTarea(Task task) throws BusinessException {
	BusinessCheck.isTrue(task.getId().equals(taskDTO.getId()),
		"El usuario de la tarea que se intenta modificar no coincide"
			+ " con el usuario de la tarea que está almacenado en"
			+ " la base de datos.",
		"error_editar__tarea_usuario_no_coincide");
    }

    private boolean distintaCategoria(TaskDTO task1, Task task2) {
	CategoryDTO categ1 = task1.getCategory();
	Category categ2 = task2.getCategory();

	if (categ1 == null && categ2 == null) {
	    return false;
	}

	// Al menos una no es null
	else {
	    // Una es null y la otra no
	    if ((categ1 == null && categ2 != null)
		    || (categ1 != null && categ2 == null)) {
		return true;
	    }

	    // Las dos != null pero iguales
	    else if (categ2.getId().equals(categ1.getId())) {
		return false;
	    }

	    // Las dos != null pero diferentes
	    return true;
	}
    }

}