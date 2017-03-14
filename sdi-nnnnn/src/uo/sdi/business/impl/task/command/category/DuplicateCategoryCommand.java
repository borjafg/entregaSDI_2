package uo.sdi.business.impl.task.command.category;

import java.util.Set;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.model.Category;
import uo.sdi.model.Task;
import uo.sdi.model.User;
import uo.sdi.model.types.UserStatus;
import uo.sdi.persistence.CategoryFinder;
import uo.sdi.persistence.UserFinder;
import uo.sdi.persistence.util.Jpa;

public class DuplicateCategoryCommand implements Command<Void> {

    private Long origId;

    public DuplicateCategoryCommand(Long id) {
	this.origId = id;
    }

    @Override
    public Void execute() throws BusinessException {
	Category original = CategoryFinder.findById(origId);

	BusinessCheck.isNotNull(original, "La categoria no existe",
		"errores_categoria_no_exite");
	checkUserNotDisabled(original);
	BusinessCheck.isNull(CategoryFinder.findByUserIdAndName(original
		.getUser().getId(), original.getName() + " - copy"),
		"error_categoria_copiada_renombrar");

	Category copyCat = duplicateCategory(original);
	duplicateTasks(original, copyCat);

	return null;
    }

    /**
     * Comprueba que el usuario al que pertenece la categoria que hay que copiar
     * esta habilitado.
     * 
     * @param categ
     *            categoria que hay que copiar
     * 
     * @throws BusinessException
     *             el usuario esta deshabilitado
     * 
     */
    private void checkUserNotDisabled(Category categ) throws BusinessException {
	User u = UserFinder.findById(categ.getUser().getId());

	BusinessCheck.isTrue(u.getStatus().equals(UserStatus.ENABLED),
		"El usuario esta deshabilitado, la categoria no "
			+ "puede ser copiada.",
		"error_categoria_copia_usuario_deshabilitado");
    }

    /**
     * Crea en la base de datos una copia de la categoria que se le pasa como
     * parametro.
     * 
     * @param original
     *            categoria que hay que copiar
     * 
     * @return copia de la categoria que se copio en la base de datos
     * 
     */
    private Category duplicateCategory(Category original) {
	Category categCopy = original.copiar();

	Jpa.getManager().persist(categCopy);

	return categCopy;
    }

    /**
     * Crea en la base de datos una copia de las tareas de la categoria que se
     * le pasa como parametro. Estas tareas estaran estaran asignadas a la copia
     * de la categoria original.
     * 
     * @param original
     *            categoria que hay que copiar
     * 
     * @param copyCat
     *            categoria en la que se copiaran las tareas
     * 
     */
    private void duplicateTasks(Category original, Category copyCat) {
	Set<Task> tasks = original.getTasks();

	for (Task task : tasks) {
	    Task taskCopy = task.copiar();

	    taskCopy.setCategory(copyCat);

	    Jpa.getManager().persist(taskCopy);
	}
    }

}