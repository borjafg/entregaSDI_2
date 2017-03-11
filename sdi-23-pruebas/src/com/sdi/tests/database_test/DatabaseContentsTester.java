package com.sdi.tests.database_test;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sdi.tests.database_test.util.ScriptReader;

public class DatabaseContentsTester {

    private ScriptReader reader;

    // -----------------------------
    // Datos de conexión
    // -----------------------------

    private final String URL = "jdbc:hsqldb:hsql://localhost/task_manager_db";
    private final String USER_NAME = "sa";
    private final String PASSWORD = "";

    // ------------------------------
    // Método de test
    // ------------------------------

    /**
     * Comprueba que en la base de datos hay los datos correctos.
     * 
     * @throws SQLException
     * 
     */
    public void test() throws SQLException {
	Connection conexion = DriverManager.getConnection(URL, USER_NAME,
		PASSWORD);

	PreparedStatement prep;
	ResultSet rs;
	String query;

	reader = new ScriptReader("queries.script");
	query = reader.readLine();

	while (query != null) {
	    prep = conexion.prepareStatement(query);
	    rs = prep.executeQuery();

	    assertTrue(rs.next()); // Se encuentra el objeto correspondiente

	    prep.close();
	    rs.close();

	    query = reader.readLine();
	}

	conexion.rollback();
	conexion.close();
    }

}