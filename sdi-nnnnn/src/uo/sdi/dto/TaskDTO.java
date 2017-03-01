package uo.sdi.dto;

import java.util.Date;

import uo.sdi.model.Task;

public class TaskDTO {

    private Long id;

    private String title;
    private String comments;
    private Date created;
    private Date planned;
    private Date finished;

    private Long categoryId;
    private Long userId;

    public TaskDTO(Task tarea) {
	this.id = tarea.getId();

	this.title = tarea.getTitle();
	this.comments = tarea.getComments();
	this.created = tarea.getCreated();
	this.planned = tarea.getPlanned();

	this.categoryId = tarea.getCategory().getId();
	this.userId = tarea.getUser().getId();
    }

    public TaskDTO(String title, Long categoryId, Long userId) {
	this.title = title;
	this.categoryId = categoryId;
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

    public Long getCategoryId() {
	return categoryId;
    }

    public Long getUserId() {
	return userId;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;

	result = prime * result + ((created == null) ? 0 : created.hashCode());
	result = prime * result + ((userId == null) ? 0 : userId.hashCode());

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

	if (created == null) {
	    if (other.created != null)
		return false;
	} else if (!created.equals(other.created))
	    return false;

	if (userId == null) {
	    if (other.userId != null)
		return false;
	} else if (!userId.equals(other.userId))
	    return false;

	return true;
    }

    @Override
    public String toString() {
	return "TaskDTO [id=" + id + ", title=" + title + ", comments="
		+ comments + ", created=" + created + ", planned=" + planned
		+ ", finished=" + finished + ", categoryId=" + categoryId
		+ ", userId=" + userId + "]";
    }

}