package uo.sdi.business.impl.task.command.category;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.business.impl.util.CategoryCheck;
import uo.sdi.dto.CategoryDTO;
import uo.sdi.model.Category;
import uo.sdi.persistence.CategoryFinder;

public class UpdateCategoryCommand implements Command<Void> {

    private CategoryDTO categ;

    public UpdateCategoryCommand(CategoryDTO categ) {
	this.categ = categ;
    }

    @Override
    public Void execute() throws BusinessException {
	Category category = CategoryFinder.findById(categ.getId());

	BusinessCheck.isNotNull(category, "La categoria no existe",
		"errores_usuario_no_existe");

	CategoryCheck.nameIsNotNull(categ);
	CategoryCheck.nameIsNotEmpty(categ);

	if (!category.getName().equals(categ.getName())) {
	    CategoryCheck.isUniqueName(categ.getName(), category.getUser()
		    .getId());
	}

	category.setName(categ.getName());

	return null;
    }

}