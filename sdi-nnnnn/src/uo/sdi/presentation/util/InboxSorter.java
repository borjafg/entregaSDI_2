package uo.sdi.presentation.util;

import java.util.Date;

import uo.sdi.dto.TaskDTO;

/* 
 * date1.compareTo(date2) < 0 ----> Date1 es anterior Date2
 * 
 * date1.compareTo(date2) == 0 ---> Date1 es igual Date2
 * 
 * date1.compareTo(date2) > 0 ----> Date1 es posterior Date2
 */
public class InboxSorter {

    /**
     * Ordena las tareas según su título. Las tareas finalizadas aparecerán al
     * final del listado.
     * 
     * @param task_1
     *            primera tarea que hay que comparar
     * @param task_2
     *            segunda tarea que hay que comparar
     * 
     * @return ==> -1 si task_1.title es menor a task_2.title --- Ejemplo:
     *         cobrar < comprar<br/>
     * <br/>
     *         ==> 0 si task_1.title es igual que task_2.title --- Ejemplo:
     *         Entrenar = entrenar <br />
     * <br />
     *         ==> +1 si task_1.title es posterior a task_2.title --- Ejemplo:
     *         visitar > estudiar
     * 
     */
    public int sortByTitle(Object task_1, Object task_2) {
	TaskDTO task1 = (TaskDTO) task_1;
	TaskDTO task2 = (TaskDTO) task_2;

	if (task1.getFinished() != null || task2.getFinished() != null) {
	    // Tareas finalizadas al final
	    return sortByFinished(task1, task2);
	}

	else { // Tareas no finalizadas
	    return task1.getTitle().compareToIgnoreCase(task2.getTitle());
	}
    }

    /**
     * Ordena las tareas según sus comentarios. Las tareas finalizadas
     * aparecerán al final del listado.
     * 
     * @param task_1
     *            primera tarea que hay que comparar
     * @param task_2
     *            segunda tarea que hay que comparar
     * 
     * @return ==> -1 si task_1.comments es menor a task_2.comments --- Ejemplo:
     *         cobrar < comprar<br/>
     * <br/>
     *         ==> 0 si task_1.comments es igual que task_2.comments ---
     *         Ejemplo: Entrenar = entrenar <br />
     * <br />
     *         ==> +1 si task_1.comments es posterior a task_2.comments ---
     *         Ejemplo: visitar > estudiar
     * 
     */
    public int sortByComments(Object task_1, Object task_2) {
	TaskDTO task1 = (TaskDTO) task_1;
	TaskDTO task2 = (TaskDTO) task_2;

	String comments1 = task1.getComments();
	String comments2 = task2.getComments();

	if (task1.getFinished() != null || task2.getFinished() != null) {
	    // Tareas finalizadas al final
	    return sortByFinished(task1, task2);
	}

	else { // Tareas no finalizadas
	    if (comments1 == null) {
		if (comments2 == null) {
		    return 0; // comentarios iguales
		}

		return 1; // null posterior a comment2
	    }

	    else { // task1.getComments() != null
		if (comments2 == null) {
		    return -1;
		}

		return comments1.compareToIgnoreCase(comments2);
	    }
	}
    }

    /**
     * Ordena las tareas según su fecha de creación. Las tareas finalizadas
     * aparecerán al final del listado.
     * 
     * @param task_1
     *            primera tarea que hay que comparar
     * @param task_2
     *            segunda tarea que hay que comparar
     * 
     * @return ==> -1 si task_1.created es anterior a task_2.created<br/>
     * <br/>
     *         ==> 0 si task_1.created es igual que task_2.created<br />
     * <br />
     *         ==> +1 si task_1.created es posterior a task_2.created
     * 
     */
    public int sortByCreated(Object task_1, Object task_2) {
	TaskDTO task1 = (TaskDTO) task_1;
	TaskDTO task2 = (TaskDTO) task_2;

	Date created1 = task1.getFinished();
	Date created2 = task2.getFinished();

	if (task1.getFinished() != null || task2.getFinished() != null) {
	    // Tareas finalizadas al final
	    return sortByFinished(task1, task2);
	}

	else { // Tareas no finalizadas
	    if (created1 == null) {
		if (created2 == null) {
		    return 0; // fechas iguales
		}

		return 1; // null posterior a created2
	    }

	    else { // created1 != null
		if (created2 == null) {
		    return -1; // created1 anterior a null
		}

		return created1.compareTo(created2);
	    }
	}
    }

    /**
     * Ordena las tareas según su fecha planeada. Las tareas finalizadas
     * aparecerán al final del listado.
     * 
     * @param task_1
     *            primera tarea que hay que comparar
     * @param task_2
     *            segunda tarea que hay que comparar
     * 
     * @return ==> -1 si task_1.created es anterior a task_2.created<br/>
     * <br/>
     *         ==> 0 si task_1.created es igual que task_2.created<br />
     * <br />
     *         ==> +1 si task_1.created es posterior a task_2.created
     * 
     */
    public int sortByPlanned(Object task_1, Object task_2) {
	TaskDTO task1 = (TaskDTO) task_1;
	TaskDTO task2 = (TaskDTO) task_2;

	Date planned1 = task1.getPlanned();
	Date planned2 = task2.getPlanned();

	if (task1.getFinished() != null || task2.getFinished() != null) {
	    // Tareas finalizadas al final
	    return sortByFinished(task1, task2);
	}

	else { // Tareas no finalizadas
	    if (planned1 == null) {
		if (planned2 == null) {
		    return 0; // fechas iguales
		}

		return 1; // null posterior a planned2
	    }

	    else { // planned1 != null
		if (planned2 == null) {
		    return -1; // planned1 anterior a null
		}

		return planned1.compareTo(planned2);
	    }
	}
    }

    /**
     * Ordena las tareas según su fecha de finalización. Las tareas finalizadas
     * aparecerán al final del listado.
     * 
     * @param task_1
     *            primera tarea que hay que comparar
     * @param task_2
     *            segunda tarea que hay que comparar
     * 
     * @return ==> -1 si task_1.finished es anterior a task_2.finished<br/>
     * <br/>
     *         ==> 0 si task_1.finished es igual que task_2.finished<br />
     * <br />
     *         ==> +1 si task_1.finished es posterior a task_2.finished
     * 
     */
    public int sortByFinished(Object task_1, Object task_2) {
	TaskDTO task1 = (TaskDTO) task_1;
	TaskDTO task2 = (TaskDTO) task_2;
	
	Date finished1 = task1.getFinished();
	Date finished2 = task2.getFinished();

	if (finished1 == null) {
	    if (finished2 == null) {
		return 0; // iguales
	    }

	    return -1; // null anterior a date2
	}

	else { // task1 != null
	    if (finished2 == null) {
		return 1; // date1 posterior a null
	    }

	    return finished1.compareTo(finished2);
	}
    }

}