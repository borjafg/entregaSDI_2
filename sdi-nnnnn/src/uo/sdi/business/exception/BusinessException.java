package uo.sdi.business.exception;

public class BusinessException extends Exception {

    private static final long serialVersionUID = 4001710687990554589L;
    
    String claveExcepcion = "";
    
    
    public BusinessException() {
    }

    public BusinessException(String message,String clave) {
	super(message);
	this.claveExcepcion = clave;
    }

    public BusinessException(Throwable cause) {
	super(cause);
    }

    public BusinessException(String message, Throwable cause) {
	super(message, cause);
    }

}