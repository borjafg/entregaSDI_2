package uo.sdi.presentation.util.sorting;

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

    public int sortAtInit(Object task_1, Object task_2) {
	InboxTaskInfo task1 = (InboxTaskInfo) task_1;
	InboxTaskInfo task2 = (InboxTaskInfo) task_2;

	return sortByString_finishedAtEnd(task1.getFinished(),
		task2.getFinished(), task1.getTitle(), task2.getTitle());
    }

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

	return sortByString_finishedAtEnd(task1.getFinished(),
		task2.getFinished(), task1.getTitle(), task2.getTitle());
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

	return sortByString_finishedAtEnd(task1.getFinished(),
		task2.getFinished(), task1.getComments(), task2.getComments());
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

	return sortByDate_finishedAtEnd(task1.getFinished(),
		task2.getFinished(), task1.getCreated(), task2.getCreated());
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

	return sortByDate_finishedAtEnd(task1.getFinished(),
		task2.getFinished(), task1.getPlanned(), task2.getPlanned());
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

	return sortByDate_finishedAtEnd(task1.getFinished(),
		task2.getFinished(), task1.getFinished(), task2.getFinished());
    }

    // ===================================
    // Métodos auxiliares
    // ===================================

    private int sortByString_finishedAtEnd(Date finished1, Date finished2,
	    String text1, String text2) {

	if (finished1 != null) {
	    if (finished2 != null) {
		return sortByString_aux(text1, text2);
	    }

	    else { // task2.getFinished() == null
		return 1;
	    }
	}

	else { // task1.getFinished() == null
	    if (finished2 == null) {
		return sortByString_aux(text1, text2);
	    }

	    else { // task2.getFinished() != null
		return -1;
	    }
	}
    }

    private int sortByString_aux(String text1, String text2) {
	if (text1 == null) {
	    if (text2 == null) {
		return 0; // comentarios iguales
	    }

	    return 1; // null posterior a comment2
	}

	else { // text1 != null
	    if (text2 == null) {
		return -1;
	    }

	    return text1.compareToIgnoreCase(text2);
	}
    }

    private int sortByDate_finishedAtEnd(Date finished1, Date finished2,
	    Date date1, Date date2) {

	if (finished1 != null) {
	    if (finished2 != null) {
		return sortByDate_nullAtEnd(date1, date2);
	    }

	    else { // task2.getFinished() == null
		return 1;
	    }
	}

	else { // task1.getFinished() == null
	    if (finished2 == null) {
		return sortByDate_nullAtEnd(date1, date2);
	    }

	    else { // task2.getFinished() != null
		return -1;
	    }
	}
    }

    private int sortByDate_nullAtEnd(Date date1, Date date2) {
	if (date1 == null) {
	    if (date2 == null) {
		return 0; // iguales
	    }

	    return 1; // null posterior a date2
	}

	else { // task1 != null
	    if (date2 == null) {
		return -1; // date1 anterior a null
	    }

	    return date1.compareTo(date2);
	}
    }

}