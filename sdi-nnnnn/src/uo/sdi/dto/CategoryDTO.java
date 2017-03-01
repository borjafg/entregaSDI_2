package uo.sdi.dto;

import java.util.Date;

import uo.sdi.model.Category;

public class CategoryDTO {

    private Long id;
    private String name;
    private Date created;

    private Long userId;

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

	CategoryDTO other = (CategoryDTO) obj;

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
	return "CategoryDTO [id=" + id + ", name=" + name + ", created="
		+ created + ", userId=" + userId + "]";
    }

}