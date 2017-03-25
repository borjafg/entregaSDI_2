package uo.sdi.presentation.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class MessageManager {

    /**
     * Registra un mensaje informativo relacionado con un componente para que
     * sea mostrado en la vista correspondiente.
     * 
     * @param context
     *            se usará para almacenar el mensaje de información.
     * 
     * @param componentIdent
     *            indentificador del componente sobre el que hay que registrar
     *            el mensaje. Se corresponde con el valor del atributo
     *            <i>"for"</i> del componente message o messages del xhtml de
     *            destino, que recogerá el mensaje y se lo mostrará al usuario.
     * 
     * @param messageKey
     *            identificador que permite obtener el mensaje a mostrar de
     *            entre los que hay en un fichero de properties.
     * 
     */
    public static void info(FacesContext context, String componentID,
	    String messageKey) {

	String cabecera = Messages.getMessage(context, "cabecera_info");

	guardar(context, componentID, cabecera, messageKey,
		FacesMessage.SEVERITY_INFO);
    }

    /**
     * Registra un mensaje de error relacionado con un componente para que sea
     * mostrado en la vista correspondiente.
     * 
     * @param context
     *            se usara para almacenar el mensaje de error.
     * 
     * @param componentID
     *            indentificador del componente sobre el que hay que registrar
     *            el mensaje. Se corresponde con el valor del atributo
     *            <i>"for"</i> del componente message o messages del xhtml de
     *            destino, que recogerá el mensaje y se lo mostrará al usuario.
     * 
     * @param messageKey
     *            identificador que permite obtener el mensaje a mostrar de
     *            entre los que hay en un fichero de properties.
     * 
     */
    public static void warning(FacesContext context, String componentID,
	    String messageKey) {

	String cabecera = Messages.getMessage(context, "cabecera_warning");

	guardar(context, componentID, cabecera, messageKey,
		FacesMessage.SEVERITY_WARN);
    }

    /**
     * Guarda un mensaje en el contexto. Es usado por los demás métodos para
     * evitar la duplicación de código.
     * 
     * @param context
     *            se usará para almacenar el mensaje.
     * @param idComponente
     *            identificador del componente al que se asociará el mensaje.
     * @param cabecera
     *            texto que se escribirá en la cabecerá del mensaje
     * @param idMensaje
     *            identificador del texto que se escribirá en el cuerpo del
     *            mensaje que se mostrará al usuario. Se extraerá de un fichero
     *            de propiedades.
     * @param gravedadMensaje
     *            indica si el mensaje es de información, de advertencia, de
     *            error
     * 
     */
    private static void guardar(FacesContext contexto, String idComponente,
	    String cabecera, String idMensaje, Severity gravedadMensaje) {

	String cuerpo = Messages.getMessage(contexto, idMensaje);

	contexto.addMessage(idComponente, new FacesMessage(gravedadMensaje,
		cabecera, cuerpo));
    }

}