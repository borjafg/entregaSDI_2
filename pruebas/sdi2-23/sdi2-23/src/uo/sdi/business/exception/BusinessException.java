package uo.sdi.business.exception;

public class BusinessException extends Exception {

    private static final long serialVersionUID = 4001710687990554589L;

    private String claveFicheroMensajes = "";

    public String getClaveFicheroMensajes() {
	return claveFicheroMensajes;
    }

    public BusinessException() {
    }

    public BusinessException(String message, String clave) {
	super(message);
	this.claveFicheroMensajes = clave;
    }

    public BusinessException(Throwable cause) {
	super(cause);
    }

    public BusinessException(String message, Throwable cause) {
	super(message, cause);
    }

}