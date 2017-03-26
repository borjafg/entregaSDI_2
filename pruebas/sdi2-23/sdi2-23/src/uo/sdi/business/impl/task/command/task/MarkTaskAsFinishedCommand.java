package uo.sdi.business.impl.task.command.task;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.model.Task;
import uo.sdi.persistence.TaskFinder;
import alb.util.date.DateUtil;

public class MarkTaskAsFinishedCommand implements Command<Void> {

    private Long id;

    public MarkTaskAsFinishedCommand(Long id) {
	this.id = id;
    }

    @Override
    public Void execute() throws BusinessException {
	Task t = TaskFinder.findById(id);

	BusinessCheck.isNotNull(t, "La tarea que se pretende marcar como "
		+ "finalizada no existe", "error_tarea_no_existe");

	t.setFinished(DateUtil.today()); // Estado persistent

	return null;
    }

}