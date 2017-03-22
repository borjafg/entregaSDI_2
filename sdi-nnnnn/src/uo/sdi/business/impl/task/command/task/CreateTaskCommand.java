package uo.sdi.business.impl.task.command.task;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.business.impl.util.TaskCheck;
import uo.sdi.dto.TaskDTO;
import uo.sdi.model.Category;
import uo.sdi.model.Task;
import uo.sdi.model.User;
import uo.sdi.persistence.CategoryFinder;
import uo.sdi.persistence.UserFinder;
import uo.sdi.persistence.util.Jpa;
import alb.util.date.DateUtil;

public class CreateTaskCommand implements Command<Task> {

    private TaskDTO taskDTO;

    public CreateTaskCommand(TaskDTO taskDTO) {
	this.taskDTO = taskDTO;
    }

    @Override
    public Task execute() throws BusinessException {
	User user = UserFinder.findById(taskDTO.getId());

	TaskCheck.isUserValid(user);
	TaskCheck.titleIsNotNull(taskDTO);
	TaskCheck.titleIsNotEmpty(taskDTO);

	Task task = new Task(taskDTO.getTitle(), user);

	if (taskDTO.getCategory() != null) {
	    Category categ = CategoryFinder.findById(taskDTO.getCategory()
		    .getId());

	    BusinessCheck.isNotNull(categ, "La categoria no existe",
		    "errores_categoria_no_exite");

	    task.setCategory(categ);
	}

	task.setPlanned(DateUtil.today());
	task.setFinished(null);

	Jpa.getManager().persist(task);

	return task;
    }

}