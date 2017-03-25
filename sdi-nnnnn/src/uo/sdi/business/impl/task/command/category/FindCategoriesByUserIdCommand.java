package uo.sdi.business.impl.task.command.category;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.dto.CategoryDTO;
import uo.sdi.dto.DTOadapter;
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

	BusinessCheck.isNotNull(userId, "El usuario cuyas tareas deben ser "
		+ "listadas no existe.",
		"error_listado_categorias__usuario_no_existe");

	List<CategoryDTO> lista = DTOadapter.categoriesToDTO(CategoryFinder
		.findByUserId(user.getId()));

	ordenarLista(lista);

	return lista;
    }

    private void ordenarLista(List<CategoryDTO> lista) {
	Collections.sort(lista, new Comparator<CategoryDTO>() {

	    @Override
	    public int compare(CategoryDTO o1, CategoryDTO o2) {
		return o1.getName().compareToIgnoreCase(o2.getName());
	    }

	});
    }

}