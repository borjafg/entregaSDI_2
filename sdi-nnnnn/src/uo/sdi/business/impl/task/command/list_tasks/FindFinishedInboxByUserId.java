package uo.sdi.business.impl.task.command.list_tasks;

import java.util.List;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.business.impl.util.TypeConverter;
import uo.sdi.dto.TaskDTO;
import uo.sdi.persistence.TaskFinder;

public class FindFinishedInboxByUserId implements Command<List<TaskDTO>> {

    private Long userId;

    public FindFinishedInboxByUserId(Long userId) {
	this.userId = userId;
    }

    @Override
    public List<TaskDTO> execute() throws BusinessException {
	return TypeConverter.convertTasks(TaskFinder
		.findFinishedTasksInboxByUserId(userId));
    }

}