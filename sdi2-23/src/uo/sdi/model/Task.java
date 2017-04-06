package uo.sdi.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import alb.util.date.DateUtil;

@Entity
@Table(name = "TTasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String comments;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created = DateUtil.now();

    @Temporal(TemporalType.DATE)
    private Date planned;

    @Temporal(TemporalType.DATE)
    private Date finished;

    // categoria a la pertenece la tarea (la categoria tiene dentro de ella
    // a qué usuario pertenece)
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user; // Usuario al que pertenece la tarea

    Task() {

    }

    public Task(String title, User user) {
	this.title = title;

	Association.Perform.link(user, this);
    }

    // ===============================
    // ====== Logica de negocio ======
    // ===============================

    public Task copiar() {
	Task task = new Task(title, user);

	task.setComments(comments);
	task.created = created;
	task.setPlanned(planned);
	task.setFinished(finished);
	task.setCategory(category);

	return task;
    }

    // ===============================
    // ====== Getters y Setters ======
    // ===============================

    public Long getId() {
	return id;
    }

    public String getTitle() {
	return title;
    }

    public void setTittle(String title) {
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

    public Category getCategory() {
	return category;
    }

    public void setCategory(Category category) {
	// Si no está asociado a una categoría
	if (this.category == null) {

	    // Y se le quiere asociar a una categoría
	    if (category != null) {
		Association.Classifies.link(this, category);
	    }
	}

	// Si ya está asociado a una categoría (this.category != null)
	else {
	    Association.Classifies.unlink(this, this.category);

	    // Y se le quiere asociar a otra
	    if (category != null) {
		Association.Classifies.link(this, category);
	    }
	}
    }

    void _setCategory(Category category) {
	this.category = category;
    }

    public User getUser() {
	return user;
    }

    void _setUser(User user) {
	this.user = user;
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

	Task other = (Task) obj;

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
	return "Task [id=" + id + ", title=" + title + ", comments=" + comments
		+ ", created=" + created + ", planned=" + planned
		+ ", finished=" + finished + ", category=" + category
		+ ", user=" + user + "]";
    }

}