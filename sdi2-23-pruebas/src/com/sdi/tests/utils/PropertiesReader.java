package com.sdi.tests.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    /**
     * Returns the value of the key that is indicated as parameter. The location
     * of the file where the value will be searched must be indicated.
     * 
     * @param fileLocale
     *            Indicates in which language file the key value must be
     *            searched
     * @param key
     *            Key to look for the value
     * 
     * @return value of that key, null if not found
     * 
     */
    public String getValueOf(String fileLocale, String key) {
	try {
	    Properties properties = new Properties();

	    InputStream stream;

	    stream = this.getClass().getClassLoader()
		    .getResourceAsStream(getFileName(fileLocale));
	    properties.load(stream);

	    return properties.getProperty(key);
	}

	catch (IOException | IllegalArgumentException | NullPointerException ex) {
	    Log.error("Ha ocurrido un error a leer el fichero de propiedades "
		    + "[%s]", getFileName(fileLocale));

	    throw new RuntimeException("Ocurrió un error al leer el fichero de"
		    + "propiedades [" + getFileName(fileLocale) + "]");
	}
    }

    private String getFileName(String locale) {
	return "messages_" + locale + ".properties";
    }

}