package uo.sdi.business.impl.task.command.task;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.model.Task;
import uo.sdi.persistence.TaskFinder;
import uo.sdi.persistence.util.Jpa;

public class DeleteTaskCommand implements Command<Void> {

    private Long taskId;

    public DeleteTaskCommand(Long taskId) {
	this.taskId = taskId;
    }

    @Override
    public Void execute() throws BusinessException {
	Task task = TaskFinder.findById(taskId);

	task.getUser().eliminarTarea(task);
	Jpa.getManager().remove(task);

	return null;
    }

}