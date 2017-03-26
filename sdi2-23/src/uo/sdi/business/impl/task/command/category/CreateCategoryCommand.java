package uo.sdi.business.impl.task.command.category;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.business.impl.util.CategoryCheck;
import uo.sdi.dto.CategoryDTO;
import uo.sdi.model.Category;
import uo.sdi.model.User;
import uo.sdi.persistence.UserFinder;
import uo.sdi.persistence.util.Jpa;

public class CreateCategoryCommand implements Command<Void> {

    private CategoryDTO categoryDTO;

    public CreateCategoryCommand(CategoryDTO categoryDTO) {
	this.categoryDTO = categoryDTO;
    }

    @Override
    public Void execute() throws BusinessException {
	User user = UserFinder.findById(categoryDTO.getUserId());

	CategoryCheck.isValidUser(user);

	CategoryCheck.nameIsNotNull(categoryDTO);
	CategoryCheck.nameIsNotEmpty(categoryDTO);

	CategoryCheck.isUniqueName(categoryDTO.getName(),
		categoryDTO.getUserId());

	Category category = new Category(user);
	category.setName(categoryDTO.getName());

	Jpa.getManager().persist(category);

	return null;
    }

}