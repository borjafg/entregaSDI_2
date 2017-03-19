package uo.sdi.dto;

import java.io.Serializable;
import java.util.Date;

import uo.sdi.model.Task;

public class TaskDTO implements Serializable {

    private static final long serialVersionUID = -4452890417360L;

    private Long id;

    private String title;
    private String comments;
    private Date created;
    private Date planned;
    private Date finished;

    private CategoryDTO category;
    private Long userId;

    public TaskDTO(Task tarea) {
	this.id = tarea.getId();

	this.title = tarea.getTitle();
	this.comments = tarea.getComments();
	this.created = tarea.getCreated();
	this.planned = tarea.getPlanned();
	this.category = null;

	if (tarea.getCategory() != null) {
	    this.category = new CategoryDTO(tarea.getCategory());
	}

	this.userId = tarea.getUser().getId();
    }

    public TaskDTO(String title, Long categoryId, Long userId) {
	this.title = title;
	this.category = new CategoryDTO(categoryId);
	this.userId = userId;
    }

    public Long getId() {
	return id;
    }

    public String getTitle() {
	return title;
    }

    public String getComments() {
	return comments;
    }

    public Date getCreated() {
	return created;
    }

    public Date getPlanned() {
	return planned;
    }

    public Date getFinished() {
	return finished;
    }

    public CategoryDTO getCategory() {
	return category;
    }

    public Long getUserId() {
	return userId;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;

	result = prime * result + ((id == null) ? 0 : id.hashCode());

	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;

	if (obj == null)
	    return false;

	if (getClass() != obj.getClass())
	    return false;

	TaskDTO other = (TaskDTO) obj;

	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;

	return true;
    }

    @Override
    public String toString() {
	return "TaskDTO [id=" + id + ", title=" + title + ", comments="
		+ comments + ", created=" + created + ", planned=" + planned
		+ ", finished=" + finished + ", category=" + category
		+ ", userId=" + userId + "]";
    }

}