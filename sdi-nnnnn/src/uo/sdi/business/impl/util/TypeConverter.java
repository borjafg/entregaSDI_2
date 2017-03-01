package uo.sdi.business.impl.util;

import java.util.ArrayList;
import java.util.List;

import uo.sdi.dto.CategoryDTO;
import uo.sdi.dto.TaskDTO;
import uo.sdi.dto.UserDTO;
import uo.sdi.model.Category;
import uo.sdi.model.Task;
import uo.sdi.model.User;

/**
 * Transforma objetos del modelo de dominio en objetos DTO, que son con los que
 * trabaja la capa de presentación.
 * 
 */
public class TypeConverter {

    public static TaskDTO convertTask(Task task) {
	return new TaskDTO(task);
    }

    public static List<TaskDTO> convertTasks(Iterable<Task> tasks) {
	List<TaskDTO> lista = new ArrayList<TaskDTO>();

	for (Task tarea : tasks) {
	    lista.add(new TaskDTO(tarea));
	}

	return lista;
    }

    public static CategoryDTO convertCategory(Category category) {
	return new CategoryDTO(category);
    }

    public static List<CategoryDTO> convertCategories(Iterable<Category> categs) {
	List<CategoryDTO> lista = new ArrayList<CategoryDTO>();

	for (Category categoria : categs) {
	    lista.add(new CategoryDTO(categoria));
	}

	return lista;
    }

    public static UserDTO convertUser(User user) {
	return new UserDTO(user);
    }

    public static List<UserDTO> convertUsers(Iterable<User> users) {
	List<UserDTO> lista = new ArrayList<UserDTO>();

	for (User usuario : users) {
	    lista.add(new UserDTO(usuario));
	}

	return lista;
    }

}