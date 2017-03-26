package uo.sdi.management;

import uo.sdi.management.util.ScriptReader;
import uo.sdi.persistence.util.Jpa;

public class DatabaseManager {

    public static void deleteAndInsertData() {
	ScriptReader reader = new ScriptReader(
		"script_reinicio_base_datos.script");

	String sentencia = reader.readLine();

	while (sentencia != null) {
	    Jpa.getManager().createNativeQuery(sentencia).executeUpdate();
	    sentencia = reader.readLine();
	}
    }

}