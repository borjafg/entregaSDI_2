package uo.sdi.dto;

import java.io.Serializable;
import java.util.Date;

public class TaskDTO implements Serializable {

    private static final long serialVersionUID = -4452890417360L;

    // Tiene que ser accesible a la clase
    // que convierte un task en un TaskDTO
    private Long id;

    private String title;
    private String comments;
    private Date created;
    private Date planned;
    private Date finished;

    private CategoryDTO category;
    private Long userId;

    /**
     * Se usa para transformar una categoría en un DTO
     */
    TaskDTO(Long id, Date created, Long userId) {
	this.id = id;
	this.created = created;
	this.userId = userId;
    }

    /**
     * Se usa para crear una nueva tarea
     */
    public TaskDTO(String title, Long categoryId, Long userId) {
	this.title = title;
	this.category = new CategoryDTO(categoryId);
	this.userId = userId;
    }

    // ===================================
    // Getters y Setters
    // ===================================

    public Long getId() {
	return id;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getComments() {
	return comments;
    }

    public void setComments(String comments) {
	this.comments = comments;
    }

    public Date getCreated() {
	return created;
    }

    public Date getPlanned() {
	return planned;
    }

    public void setPlanned(Date planned) {
	this.planned = planned;
    }

    public Date getFinished() {
	return finished;
    }

    public void setFinished(Date finished) {
	this.finished = finished;
    }

    public CategoryDTO getCategory() {
	return category;
    }

    public void setCategory(CategoryDTO category) {
	this.category = category;
    }

    public void setCategoryId(Long idCategory) {
	this.category = new CategoryDTO(idCategory);
    }

    public Long getUserId() {
	return userId;
    }

    // ====================================
    // HashCode, equals y toString
    // ====================================

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