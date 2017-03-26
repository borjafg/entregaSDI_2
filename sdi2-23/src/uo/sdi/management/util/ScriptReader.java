package uo.sdi.management.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import alb.util.log.Log;

public class ScriptReader {

    private BufferedReader reader;

    ScriptReader() {
	// No debe usarse este constructor
    }

    /**
     * Prepara la lectura del fichero de script cuya ruta se le indica como
     * parámetro.
     * 
     * @param fileName
     *            ruta relativa del fichero de script que hay que leer.
     * 
     * @throws RuntimeException
     *             no se ha encontrado el fichero que hay que leer
     * 
     */
    public ScriptReader(String fileName) {
	try {
	    InputStream inputStream = getClass().getClassLoader()
		    .getResourceAsStream("/" + fileName);

	    reader = new BufferedReader(new InputStreamReader(
		    inputStream));
	}

	catch (Exception fnfe) {
	    Log.error("No se ha encontrado el fichero de sentencias sql de "
		    + "nombre [%s]", fileName);

	    throw new RuntimeException("No se ha encontrado el fichero que "
		    + "contiene las sentencias que reinician la base de datos");
	}
    }

    /**
     * Lee una línea del fichero de script que contiene una sentencia válida.
     * Las lineas sin contenido o que están comentadas serán ignoradas.
     * 
     * @return siguiente línea ejecutable del fichero de script, null si no
     *         quedan lineas que leer
     * 
     * @throws RuntimeException
     *             Ha ocurrido un error al leer el contenido del fichero.
     * 
     */
    public String readLine() {
	try {
	    // Termina cuando encuentra una sentencia sql o
	    // llega al final del fichero
	    while (true) {
		String line = reader.readLine();

		// Fin de fichero
		if (line == null) {
		    reader.close();
		    return null;
		}

		if (!isEmptyLine(line)) {
		    if (!isCommented(line)) {
			return line;
		    }
		}
	    }
	}

	catch (IOException ex) {
	    Log.error("Ha ocurrido un error al leer el fichero que contiene las"
		    + " sentencias que reinician la base de datos");

	    throw new RuntimeException("Ha ocurrido un error al leer el "
		    + "contenido del fichero que contiene las sentencias que "
		    + "reinician la base de datos");
	}
    }

    private boolean isCommented(String line) {
	if (line.trim().startsWith("--")) {
	    return true;
	}

	return false;
    }

    private boolean isEmptyLine(String line) {
	if (line.isEmpty() || line.trim().equals("")
		|| line.trim().equals("\n")) {

	    return true;
	}

	return false;
    }

}