package uo.sdi.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.model.types.UserStatus;

@Entity
@Table(name = "TUsers")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String email;
    private String password;
    private Boolean isAdmin = false;

    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.ENABLED;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Category> categories = new HashSet<Category>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Task> tasks = new HashSet<Task>();

    User() {

    }

    public User(String login) {
	this.login = login;
    }

    // =============================
    // ===== Logica de negocio =====
    // =============================

    /**
     * Desvincula una tarea de su categoria y de su usuario para que pueda ser
     * eliminada de la base de datos.
     * 
     * @param tarea
     *            tarea que se debe eliminar
     * 
     * @throws BusinessException
     *             el usuario no tiene asignada esa tarea
     * 
     */
    public void eliminarTarea(Task tarea) throws BusinessException {
	if (!tasks.contains(tarea)) {
	    throw new BusinessException("El usuario no tiene asignada esa "
		    + "tarea.", "errores_usuario_no_tiene_tarea_asignada");
	}

	Association.Classifies.unlink(tarea, tarea.getCategory());
	tasks.remove(tarea);
    }

    /**
     * Desvincula un una tarea de su categoria del usuario que la creo para que
     * pueda ser eliminada de la base de datos.
     * 
     * @param categoria
     *            categoria que se debe eliminar
     * 
     * @throws BusinessException
     *             la categoria tiene tareas asignadas o no pertenece al usuario
     *             (se tendrán que haber desvinculado antes para que puedan ser
     *             eliminadas)
     * 
     */
    public void eliminarCategoria(Category categoria) throws BusinessException {

	if (!categories.contains(categoria)) {
	    throw new BusinessException("La categoria no pertenece al usuario",
		    "");
	}

	if (categoria.getTasks().size() > 0) {
	    throw new BusinessException("No se puede eliminar una categoria "
		    + "que tiene tareas asociadas.", "");
	}

	categories.remove(categoria);
    }

    // =============================
    // ===== Getters y Setters =====
    // =============================

    public Long getId() {
	return id;
    }

    public String getLogin() {
	return login;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getPassword() {
	return password;
    }

    public User setPassword(String password) {
	this.password = password;
	return this;
    }

    public Boolean getIsAdmin() {
	return isAdmin;
    }

    public UserStatus getStatus() {
	return status;
    }

    public void setStatus(UserStatus status) {
	this.status = status;
    }

    public Set<Category> getCategories() {
	return new HashSet<Category>(categories);
    }

    Set<Category> _getCategories() {
	return categories;
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

	result = prime * result + ((login == null) ? 0 : login.hashCode());

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

	User other = (User) obj;

	if (login == null) {
	    if (other.login != null)
		return false;
	} else if (!login.equals(other.login))
	    return false;

	return true;
    }

    @Override
    public String toString() {
	return "User [id=" + id + ", login=" + login + ", email=" + email
		+ ", password=" + password + ", isAdmin=" + isAdmin
		+ ", status=" + status + "]";
    }

}