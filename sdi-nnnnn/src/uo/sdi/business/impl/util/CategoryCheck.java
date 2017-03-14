package uo.sdi.business.impl.util;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.CategoryDTO;
import uo.sdi.model.Category;
import uo.sdi.model.User;
import uo.sdi.model.types.UserStatus;
import uo.sdi.persistence.CategoryFinder;

public class CategoryCheck {

    public static void nameIsNotNull(CategoryDTO category)
	    throws BusinessException {
	BusinessCheck.isNotNull(category.getName(),
		"Debe indicarse el nombre de la categoria",
		"error_debe_indicarse_el_nombre_categoria");
    }

    public static void nameIsNotEmpty(CategoryDTO category)
	    throws BusinessException {
	BusinessCheck.isFalse(category.getName().length() == 0,
		"error_debe_indicarse_el_nombre_categoria");
    }

    public static void isValidUser(User user) throws BusinessException {
	BusinessCheck.isNotNull(user, "Una categoria debe asignarse a "
		+ "un usuario existente",
		"error_categoria_asignarse_usuario_existente");

	BusinessCheck.isTrue(user.getStatus().equals(UserStatus.ENABLED),
		"Una categoria debe asignarse a un usuario habilitado",
		"error_categoria_asignada_usuario_habilitado");

	BusinessCheck.isFalse(user.getIsAdmin(), "error_categoria_admin");
    }

    public static void isUniqueName(String category, Long idUser)
	    throws BusinessException {
	Category other = CategoryFinder.findByUserIdAndName(idUser, category);

	BusinessCheck.isNull(other,
		"error_nombre_categoria_no_repetido_usuario");
    }

}