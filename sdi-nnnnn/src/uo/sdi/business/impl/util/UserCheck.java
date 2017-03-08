package uo.sdi.business.impl.util;

import java.util.regex.Pattern;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.UserDTO;
import uo.sdi.model.User;
import uo.sdi.persistence.UserFinder;

public class UserCheck {

    public static void isNotAdmin(UserDTO user) throws BusinessException {
	String check = "Un nuevo admin no puede ser registrado";
	BusinessCheck.isFalse(user.getIsAdmin(), check);
    }

    public static void isValidEmailSyntax(UserDTO user)
	    throws BusinessException {
	String check = "El email no es válido";
	BusinessCheck.isTrue(isValidEmail(user.getEmail()), check);
    }

    public static void minLoginLength(UserDTO user) throws BusinessException {
	String check = "El login debe tener 3 caracteres como mínimo";
	BusinessCheck.isTrue(user.getLogin().length() >= 3, check);
    }

    public static void minPasswordLength(UserDTO user) throws BusinessException {
	String check = "La contraseña debe tener al menos 8 caracteres";
	BusinessCheck.isTrue(user.getPassword().length() >= 8, check);
    }

    public static void notRepeatedLogin(UserDTO user) throws BusinessException {
	User u = UserFinder.findByLogin(user.getLogin());
	BusinessCheck.isNull(u, "Ese login ya está registrado");
    }

    public static void isValidPassword(UserDTO user) throws BusinessException {
	String check = "La contraseña debe tener letras y numeros";
	BusinessCheck.isTrue(isPasswordTypeCorrect(user.getPassword()), check);
    }

    public static void isSamePassword(UserDTO user) throws BusinessException {
	String check = "Las contraseña tienen que ser iguales";
	BusinessCheck.isTrue(user.getPassword()
		.equals(user.getRepeatPassword()), check);
    }

    private static boolean isPasswordTypeCorrect(String password) {
	String passPattern = "[a-zA-Z-0-9]+";
	return Pattern.compile(passPattern).matcher(password).matches();
    }

    private static boolean isValidEmail(String email) {
	String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}"
		+ "\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])"
		+ "|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

	return Pattern.compile(ePattern).matcher(email).matches();
    }

}