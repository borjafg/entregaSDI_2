package uo.sdi.presentation.util;

import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;

public abstract class Messages implements Validator {

    /**
     * Obtiene un mensaje contenido en un fichero "messages_**.properties". <br />
     * <br />
     * El valor cambiará según la localización asociada a la sesión del usuario.
     * 
     * @param context
     *            Contexto de la aplicación
     * @param key
     *            clave que identifica el mensaje que se quiere obtener
     * 
     * @return mensaje cuya clave fue indicada
     * 
     */
    public static String getMessage(FacesContext context, String key) {
	ResourceBundle bundle = context.getApplication().getResourceBundle(
		context, "msgs");

	return bundle.getString(key);
    }

}