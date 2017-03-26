package uo.sdi.persistence;

import java.util.List;

import javax.persistence.NoResultException;

import uo.sdi.model.User;
import uo.sdi.persistence.util.Jpa;

public class UserFinder {

    public static User findById(Long id) {
	return Jpa.getManager().find(User.class, id);
    }

    public static List<User> findAll() {
	return Jpa.getManager().createNamedQuery("User.findAll", User.class)
		.getResultList();
    }

    public static User findByLogin(String login) {
	try {
	    return Jpa.getManager()
		    .createNamedQuery("User.findByLogin", User.class)
		    .setParameter("login", login).getSingleResult();
	}

	catch (NoResultException nre) {
	    return null;
	}
    }

    public static User findByLoginAndPassword(String login, String password) {
	try {
	    return Jpa
		    .getManager()
		    .createNamedQuery("User.findByLoginAndPassword", User.class)
		    .setParameter("login", login)
		    .setParameter("password", password).getSingleResult();
	}

	catch (NoResultException nre) {
	    return null;
	}
    }

}