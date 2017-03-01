package uo.sdi.business.impl.task.command.list_tasks;

import java.util.List;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.business.impl.util.TypeConverter;
import uo.sdi.dto.TaskDTO;
import uo.sdi.model.Category;
import uo.sdi.persistence.CategoryFinder;

public class FindTasksByCategoryId implements Command<List<TaskDTO>> {

    private Long categId;

    public FindTasksByCategoryId(Long categId) {
	this.categId = categId;
    }

    @Override
    public List<TaskDTO> execute() throws BusinessException {
	Category categ = CategoryFinder.findById(categId);

	BusinessCheck.isNotNull(categ, "La categoria no existe.");

	return TypeConverter.convertTasks(categ.getTasks());
    }

}