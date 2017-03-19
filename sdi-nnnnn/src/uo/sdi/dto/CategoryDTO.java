package uo.sdi.dto;

import java.io.Serializable;
import java.util.Date;

import uo.sdi.model.Category;

public class CategoryDTO implements Serializable {

    private static final long serialVersionUID = -411924970180L;

    private Long id;
    private String name;
    private Date created;

    private Long userId;

    /**
     * Sólo se usa para asociar una tarea con su categoría.
     * 
     */
    CategoryDTO(Long id) {
	this.id = id;
    }

    public CategoryDTO(Category category) {
	this.id = category.getId();
	this.name = category.getName();
	this.created = category.getCreated();

	this.userId = category.getUser().getId();
    }

    public CategoryDTO(String name) {
	this.name = name;
    }

    public Long getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Date getCreated() {
	return created;
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

	CategoryDTO other = (CategoryDTO) obj;

	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;

	return true;
    }

    @Override
    public String toString() {
	return "CategoryDTO [id=" + id + ", name=" + name + ", created="
		+ created + ", userId=" + userId + "]";
    }

}