package uo.sdi.persistence;

import java.util.List;

import javax.persistence.NoResultException;

import uo.sdi.model.Category;
import uo.sdi.persistence.util.Jpa;

public class CategoryFinder {

    public static Category findById(Long id) {
	return Jpa.getManager().find(Category.class, id);
    }

    public static List<Category> findAll(Long id) {
	return Jpa.getManager()
		.createNamedQuery("Category.FindAll", Category.class)
		.getResultList();
    }

    public static Category findByUserIdAndName(Long userId, String name) {
	try {
	    return Jpa
		    .getManager()
		    .createNamedQuery("Category.findByUserIdAndName",
			    Category.class).setParameter("userId", userId)
		    .setParameter("name", name).getSingleResult();
	}

	catch (NoResultException nre) {
	    return null;
	}
    }

    public static List<Category> findByUserId(Long id) {
	return Jpa.getManager()
		.createNamedQuery("Category.findByUserId", Category.class)
		.setParameter("userId", id).getResultList();
    }

}