package uo.sdi.dto;

import java.io.Serializable;

import uo.sdi.model.User;
import uo.sdi.model.types.UserStatus;

public class UserDTO implements Serializable {

    private static final long serialVersionUID = -347020645785881L;

    private Long id;

    private String login;
    private String email;
    private String password;
    private Boolean isAdmin = false;

    private UserStatus status = UserStatus.ENABLED;

    public UserDTO(User user) {
	this.id = user.getId();

	this.login = user.getLogin();
	this.email = user.getEmail();
	this.password = user.getPassword();
	this.isAdmin = user.getIsAdmin();

	this.status = user.getStatus();
    }

    public UserDTO(String login) {
	this.login = login;
    }

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

    public void setPassword(String password) {
	this.password = password;
    }

    public UserStatus getStatus() {
	return status;
    }

    public void setStatus(UserStatus status) {
	this.status = status;
    }

    public boolean getIsAdmin() {
	return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
	this.isAdmin = isAdmin;
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

	UserDTO other = (UserDTO) obj;

	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;

	return true;
    }

    @Override
    public String toString() {
	return "UserDTO [id=" + id + ", login=" + login + ", email=" + email
		+ ", password=" + password + ", isAdmin=" + isAdmin
		+ ", status=" + status + "]";
    }

}