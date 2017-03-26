package uo.sdi.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import alb.util.date.DateUtil;

@Entity
@Table(name = "TCategories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created = DateUtil.now();

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private Set<Task> tasks = new HashSet<Task>();

    Category() {

    }

    public Category(User user) {
	Association.Organizes.link(user, this);
    }

    // =============================
    // ===== Logica de negocio =====
    // =============================

    public Category copiar() {
	Category categCopy = new Category(user);
	categCopy.setName(name + " - copy");

	return categCopy;
    }

    // =============================
    // ===== Getters y Setters =====
    // =============================

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

    public User getUser() {
	return user;
    }

    void _setUser(User user) {
	this.user = user;
    }

    public Set<Task> getTasks() {
	return new HashSet<Task>(tasks);
    }

    Set<Task> _getTasks() {
	return tasks;
    }

    // =========================================
    // ====== hashCode, equals y toString ======
    // =========================================

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;

	result = prime * result + ((created == null) ? 0 : created.hashCode());
	result = prime * result + ((user == null) ? 0 : user.hashCode());

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

	Category other = (Category) obj;

	if (created == null) {
	    if (other.created != null)
		return false;
	} else if (!created.equals(other.created))
	    return false;

	if (user == null) {
	    if (other.user != null)
		return false;
	} else if (!user.equals(other.user))
	    return false;

	return true;
    }

    @Override
    public String toString() {
	return "Category [id=" + id + ", name=" + name + ", created=" + created
		+ ", user=" + user + "]";
    }

}