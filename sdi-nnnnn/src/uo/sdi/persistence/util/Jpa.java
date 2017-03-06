package uo.sdi.persistence.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Jpa {

    private static EntityManagerFactory emf = null;
    private static ThreadLocal<EntityManager> emThread = new ThreadLocal<EntityManager>();

    public static EntityManager createEntityManager() {
	EntityManager entityManager = getEmf().createEntityManager();
	emThread.set(entityManager);

	return entityManager;
    }

    public static EntityManager getManager() {
	return emThread.get();
    }

    private static EntityManagerFactory getEmf() {
	if (emf == null) {
	    emf = jndiFind("java:jboss/entityManagerFactories/hibernateFactory");
	}

	return emf;
    }

    private static EntityManagerFactory jndiFind(String name) {
	Context ctx;

	try {
	    ctx = new InitialContext();

	    return (EntityManagerFactory) ctx.lookup(name);
	}

	catch (NamingException e) {
	    throw new RuntimeException(e);
	}

    }

    public static void close() {
	EntityManager em = getManager();

	if (em == null)
	    return;

	em.close();
	emThread.set(null);
    }

}