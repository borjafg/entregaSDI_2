package uo.sdi.presentation.util;

import java.io.Serializable;

import uo.sdi.dto.UserDTO;
import uo.sdi.model.types.UserStatus;

public class UserInfo implements Serializable {

    private static final long serialVersionUID = -347020645785881L;

    private Long id;

    private String login;
    private String email;
    private Boolean isAdmin;

    private UserStatus status;

    public UserInfo(UserDTO user) {
	this.id = user.getId();

	this.login = user.getLogin();
	this.email = user.getEmail();
	this.isAdmin = user.getIsAdmin();

	this.status = user.getStatus();
    }

    public UserInfo(String login) {
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

    public boolean getIsAdmin() {
	return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
	this.isAdmin = isAdmin;
    }

    public UserStatus getStatus() {
	return status;
    }

    public void setStatus(UserStatus status) {
	this.status = status;
    }

    @Override
    public String toString() {
	return "UserInfo [id=" + id + ", login=" + login + ", email=" + email
		+ ", isAdmin=" + isAdmin + ", status=" + status + "]";
    }

}