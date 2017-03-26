package uo.sdi.business.impl.util;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.TaskDTO;
import uo.sdi.model.Category;
import uo.sdi.model.User;
import uo.sdi.model.types.UserStatus;

public class TaskCheck {

    public static void isUserValid(User user) throws BusinessException {
	BusinessCheck.isNotNull(user, "El usuario al que se pretende asignar "
		+ "la tarea no existe.",
		"error_creacion_tarea__asignar_usuario_existente");

	BusinessCheck.isTrue(user.getStatus().equals(UserStatus.ENABLED),
		"El usuario al que se pretende asignar la tarea está "
			+ "deshabilitado.",
		"error_creacion_tarea__asignar_usuario_habilitado");

	BusinessCheck.isFalse(user.getIsAdmin(), "El usuario al que se asigne "
		+ "la tarea no puede ser un administrador.",
		"error_creacion_tarea__asignar_admin");
    }

    public static void titleIsNotNull(TaskDTO task) throws BusinessException {
	BusinessCheck.isTrue(task.getTitle() != null, "Falta indicar el "
		+ "título de la tarea que se va a crear.",
		"error_creacion_edicion_tarea__titulo_requerido");
    }

    public static void titleIsNotEmpty(TaskDTO task) throws BusinessException {
	BusinessCheck.isTrue(!"".equals(task.getTitle()),
		"El titulo de la tarea no puede estar vacío",
		"error_creacion_edicion_tarea__titulo_requerido");
    }

    public static void categoryExists(Category c) throws BusinessException {
	BusinessCheck.isNotNull(c, "La categoria a la que se pretende asignar "
		+ "la tarea no existe.",
		"error_creacion_edicion_tarea__categoria_no_existe");
    }

}