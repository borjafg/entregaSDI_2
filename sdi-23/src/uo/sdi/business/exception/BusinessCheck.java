package uo.sdi.business.exception;

public class BusinessCheck {

    public static void isNull(Object o, String errorMsg, String clave)
	    throws BusinessException {
	isTrue(o == null, errorMsg, clave);
    }

    public static void isNotNull(Object o, String errorMsg, String clave)
	    throws BusinessException {
	isTrue(o != null, errorMsg, clave);
    }

    public static void isFalse(boolean condition, String errorMsg, String clave)
	    throws BusinessException {
	isTrue(!condition, errorMsg, clave);
    }

    public static void isTrue(boolean condition, String errorMsg, String clave)
	    throws BusinessException {
	if (condition == true)
	    return;

	throw new BusinessException(errorMsg, clave);
    }

}