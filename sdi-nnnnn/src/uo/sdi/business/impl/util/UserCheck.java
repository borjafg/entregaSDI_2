package uo.sdi.business.impl.util;

import java.util.regex.Pattern;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.UserDTO;
import uo.sdi.model.User;
import uo.sdi.persistence.UserFinder;

public class UserCheck {

    public static void isNotAdmin(UserDTO user) throws BusinessException {
	String check = "error_registro_nuevo_admin";
	BusinessCheck.isFalse(user.getIsAdmin(), check);
    }

    public static void isValidEmailSyntax(UserDTO user)
	    throws BusinessException {
	String check = "error_registro_email_no_valido";
	BusinessCheck.isTrue(isValidEmail(user.getEmail()), check);
    }

    public static void minLoginLength(UserDTO user) throws BusinessException {
	String check = "error_registro_min_login_length";
	BusinessCheck.isTrue(user.getLogin().length() >= 3, check);
    }

    public static void minPasswordLength(UserDTO user) throws BusinessException {
	String check = "error_registro_password_peque";
	BusinessCheck.isTrue(user.getPassword().length() >= 8, check);
    }

    public static void notRepeatedLogin(UserDTO user) throws BusinessException {
	User u = UserFinder.findByLogin(user.getLogin());
	BusinessCheck.isNull(u, "error_registro_login_ya_existe");
    }

    public static void isValidPassword(UserDTO user) throws BusinessException {
	String check = "error_registro_password_type";
	BusinessCheck.isTrue(isPasswordTypeCorrect(user.getPassword()), check);
    }

    private static boolean isPasswordTypeCorrect(String password) {
	String passPattern = "([0-9a-zA-Z]*[a-zA-Z]+[0-9a-zA-Z]*[0-9]+[0-9a-zA-Z]*)|"
		+ "([0-9a-zA-Z]*[0-9]+[0-9a-zA-Z]*[a-zA-Z]+[0-9a-zA-Z]*)";
	return Pattern.compile(passPattern).matcher(password).matches();
    }

    private static boolean isValidEmail(String email) {
	String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}"
		+ "\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])"
		+ "|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

	return Pattern.compile(ePattern).matcher(email).matches();
    }

}