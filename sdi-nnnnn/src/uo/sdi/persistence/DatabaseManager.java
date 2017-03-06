package uo.sdi.persistence;

import uo.sdi.persistence.util.Jpa;

public class DatabaseManager {

    public static void deleteAndInsertData() {
	Jpa.getManager().createNativeQuery("");
    }

}