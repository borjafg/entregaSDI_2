package uo.sdi.business.impl.task.command.category;

import java.util.List;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.business.impl.util.TypeConverter;
import uo.sdi.dto.CategoryDTO;
import uo.sdi.model.User;
import uo.sdi.persistence.CategoryFinder;
import uo.sdi.persistence.UserFinder;

public class FindCategoriesByUserIdCommand implements
	Command<List<CategoryDTO>> {

    private Long userId;

    public FindCategoriesByUserIdCommand(Long userId) {
	this.userId = userId;
    }

    @Override
    public List<CategoryDTO> execute() throws BusinessException {
	User user = UserFinder.findById(userId);

	BusinessCheck.isNotNull(userId, "El usuario no existe");

	return TypeConverter.convertCategories(CategoryFinder.findByUserId(user
		.getId()));
    }

}