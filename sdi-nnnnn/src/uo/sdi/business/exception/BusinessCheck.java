package uo.sdi.business.exception;

public class BusinessCheck {

    public static void isNull(Object o, String clave)
	    throws BusinessException {
	isTrue(o == null, clave);
    }

    public static void isNull(Object o) throws BusinessException {
	isTrue(o == null, o.getClass().getName() + " must be null here");
    }

    public static void isNotNull(Object o, String errorMsg,String clave)
	    throws BusinessException {
	isTrue(o != null, errorMsg,clave);
    }

    public static void isNotNull(Object o) throws BusinessException {
	isTrue(o != null, o.getClass().getName() + " cannot be null here");
    }

    public static void isFalse(boolean condition) throws BusinessException {
	isTrue(!condition, "Invalid assertion");
    }

    public static void isFalse(boolean condition, String clave)
	    throws BusinessException {
	isTrue(!condition, clave);
    }

    public static void isTrue(boolean condition,String clave) throws BusinessException {
	isTrue(condition, "Invalid assertion", clave);
    }

    public static void isTrue(boolean condition, String errorMsg,String clave)
	    throws BusinessException {
	if (condition == true)
	    return;
	throw new BusinessException(errorMsg,clave);
    }

}
