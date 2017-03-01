package uo.sdi.business.impl.task.command.task;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.business.impl.util.TaskCheck;
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

	BusinessCheck.isNotNull(task, "La tarea no existe");

	task.setTittle(taskDTO.getTitle());

	if (task.getId() != null && !taskDTO.getId().equals(task.getId())) {
	    Category categ = CategoryFinder.findById(taskDTO.getId());

	    BusinessCheck.isNotNull(categ, "La categoria indicada no existe");

	    task.setCategory(categ);
	}

	task.setComments(taskDTO.getComments());
	task.setPlanned(taskDTO.getPlanned());

	return null;
    }

}