package uo.sdi.business.impl.task.command.category;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.model.Category;
import uo.sdi.model.Task;
import uo.sdi.model.User;
import uo.sdi.persistence.CategoryFinder;
import uo.sdi.persistence.UserFinder;

public class DeleteCategoryCommand implements Command<Void> {

    private Long catId;
    private Long idUSer;

    public DeleteCategoryCommand(Long catId, Long idUser) {
	this.catId = catId;
	this.idUSer = idUser;
    }

    @Override
    public Void execute() throws BusinessException {
	Category category = CategoryFinder.findById(catId);

	User user = UserFinder.findById(idUSer);

	// Eliminar tareas del usuario que pertenezcan a esa categoria
	for (Task tarea : category.getTasks()) {
	    user.eliminarTarea(tarea);
	}

	user.eliminarCategoria(category);

	return null;
    }

}