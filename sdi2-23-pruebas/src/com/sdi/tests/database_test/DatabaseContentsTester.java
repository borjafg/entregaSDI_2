package com.sdi.tests.database_test;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sdi.tests.database_test.util.DatabaseInfo;
import com.sdi.tests.database_test.util.ScriptReader;
import com.sdi.tests.utils.Log;

public class DatabaseContentsTester {

    /**
     * Comprueba que en la base de datos hay los datos correctos.
     * 
     * @throws SQLException
     * 
     */
    public void test() {
	PreparedStatement prep;
	ResultSet rs;
	String query;

	ScriptReader reader = new ScriptReader("prueba_reinicio.script");
	query = reader.readLine();

	Connection conexion = null;

	try {
	    conexion = DriverManager.getConnection(DatabaseInfo.URL,
		    DatabaseInfo.USER_NAME, DatabaseInfo.PASSWORD);

	    while (query != null) {
		prep = conexion.prepareStatement(query);
		rs = prep.executeQuery();

		assertTrue(rs.next()); // Se encuentra el objeto correspondiente

		prep.close();
		rs.close();

		query = reader.readLine();
	    }
	}

	catch (SQLException ex) {
	    if (conexion != null) {
		try {
		    conexion.close();
		}

		catch (SQLException e) {

		}
	    }

	    Log.error("Ha ocurrido un error al comprobar si el contenido de la"
		    + "base de datos se ha restaurado. Causa del error: %s",
		    ex.getMessage());

	    assertTrue("Error al ejecutar las consultas que comprueban "
		    + "que la base de datos tiene los datos adecuados.", false);
	}
    }

}