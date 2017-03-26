package uo.sdi.business.impl.task.command.list_tasks;

import java.util.List;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.dto.DTOadapter;
import uo.sdi.dto.TaskDTO;
import uo.sdi.persistence.TaskFinder;

public class FindInboxTaskByUserId implements Command<List<TaskDTO>> {

    private Long userId;

    public FindInboxTaskByUserId(Long userId) {
	this.userId = userId;
    }

    @Override
    public List<TaskDTO> execute() throws BusinessException {
	return DTOadapter.tasksToDTO(TaskFinder.findInboxTasksByUserId(userId));
    }

}