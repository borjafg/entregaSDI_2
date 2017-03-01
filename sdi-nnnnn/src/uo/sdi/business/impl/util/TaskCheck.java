package uo.sdi.business.impl.util;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.TaskDTO;
import uo.sdi.model.Category;
import uo.sdi.model.Task;
import uo.sdi.model.User;
import uo.sdi.model.types.UserStatus;
import uo.sdi.persistence.CategoryFinder;

public class TaskCheck {

    public static void isUserValid(User user) throws BusinessException {
	BusinessCheck.isNotNull(user, "El usuario no existe.");

	BusinessCheck.isTrue(user.getStatus().equals(UserStatus.ENABLED),
		"El usuario está deshabilitado.");

	BusinessCheck.isFalse(user.getIsAdmin(),
		"El usuario no puede ser un administrador.");
    }

    public static void titleIsNotNull(TaskDTO task) throws BusinessException {
	BusinessCheck.isTrue(task.getTitle() != null,
		"Hay que indicar el título de la tarea");
    }

    public static void titleIsNotEmpty(TaskDTO task) throws BusinessException {
	BusinessCheck.isTrue(!"".equals(task.getTitle()),
		"El titulo de la tarea no puede estar vacío");
    }

    public static void categoryExists(Task task) throws BusinessException {
	Category c = CategoryFinder.findById(task.getCategory().getId());
	BusinessCheck.isNotNull(c, "La categoria no existe");
    }

}