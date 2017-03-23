package uo.sdi.business.impl.util;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.CategoryDTO;
import uo.sdi.model.Category;
import uo.sdi.model.User;
import uo.sdi.model.types.UserStatus;
import uo.sdi.persistence.CategoryFinder;

public class CategoryCheck {

    public static void isValidUser(User user) throws BusinessException {
	BusinessCheck.isNotNull(user, "Una categoria debe asignarse a un "
		+ "usuario existente.",
		"error_creacion_categoria_asignar_usuario_existente");

	BusinessCheck.isTrue(user.getStatus().equals(UserStatus.ENABLED),
		"Una categoria debe asignarse a un usuario habilitado.",
		"error_creacion_categoria_asignar_usuario_habilitado");

	BusinessCheck.isFalse(user.getIsAdmin(),
		"El usuario al que se asigna una categoría no puede ser el "
			+ "administrador.",
		"error_creacion_categoria_asignar_admin");
    }

    public static void nameIsNotNull(CategoryDTO category)
	    throws BusinessException {
	BusinessCheck.isNotNull(category.getName(),
		"Falta indicar el nombre de la categoría que se va a crear.",
		"error_creacion_categoria_nombre_requerido");
    }

    public static void nameIsNotEmpty(CategoryDTO category)
	    throws BusinessException {
	BusinessCheck.isFalse(category.getName().trim().length() == 0,
		"Falta indicar el nombre de la categoría que se va a crear.",
		"error_creacion_edicion_categoria_nombre_requerido");
    }

    public static void isUniqueName(String category, Long idUser)
	    throws BusinessException {
	Category other = CategoryFinder.findByUserIdAndName(idUser, category);

	BusinessCheck.isNull(other, "No puede haber dos categorías con el "
		+ "mismo nombre para el mismo usuario",
		"error_creacion_edicion_categoria_nombre_repetido");
    }

}