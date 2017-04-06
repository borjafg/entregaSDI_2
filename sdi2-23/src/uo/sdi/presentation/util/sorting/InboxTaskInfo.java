package uo.sdi.presentation.util.sorting;

import java.io.Serializable;
import java.util.Date;

import uo.sdi.dto.TaskDTO;

/**
 * It is used in inbox to sort tasks by default when loading datatable content.<br />
 * <br />
 * This class is necessary because all the columns of the table use the task
 * object to be ordered, which is why that object can not be used directly for
 * the default ordering, since the table would not know by which of all the
 * columns do the ordering.
 * 
 */
public class InboxTaskInfo implements Serializable {

    private static final long serialVersionUID = -2084353774872914L;

    private String title;
    private Date finished;

    public InboxTaskInfo(TaskDTO task) {
	this.title = task.getTitle();
	this.finished = task.getFinished();
    }

    public String getTitle() {
	return title;
    }

    public Date getFinished() {
	return finished;
    }

}