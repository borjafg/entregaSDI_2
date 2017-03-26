package uo.sdi.model;

// Clase con ocultacion de paquete (sólo la usan
// las clases del modelo de dominio). Gestiona
// las asociaciones entre esas clases.
// 
class Association {

    /**
     * <b>User</b> <===> <b>Category</b>
     * 
     */
    static class Organizes {

	static void link(User user, Category category) {
	    category._setUser(user);
	    user._getCategories().add(category);
	}

	static void unlink(User user, Category category) {
	    user._getCategories().remove(category);
	    category._setUser(null);
	}

    }

    /**
     * <b>User</b> <===> <b>Task</b>
     * 
     */
    static class Perform {

	static void link(User user, Task task) {
	    task._setUser(user);
	    user._getTasks().add(task);
	}

	static void unlink(User user, Task task) {
	    user._getTasks().remove(task);
	    task._setUser(null);
	}

    }

    /**
     * <b>Task</b> <===> <b>Category</b>
     * 
     */
    static class Classifies {

	static void link(Task task, Category category) {
	    task._setCategory(category);
	    category._getTasks().add(task);
	}

	static void unlink(Task task, Category category) {
	    category._getTasks().remove(task);
	    task._setCategory(null);
	}

    }

}