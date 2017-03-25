package uo.sdi.business.impl.util;

import java.util.regex.Pattern;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.UserDTO;
import uo.sdi.model.User;
import uo.sdi.persistence.UserFinder;

/**
 * Valida que los datos del usuario sean correctos. Se emplea en el registro de
 * nuevos usuarios y en la modificación de los datos de los usuarios.
 * 
 */
public class UserCheck {

    public static void isNotAdmin(UserDTO user) throws BusinessException {
	BusinessCheck.isFalse(user.getIsAdmin(), "No puede haber otro usuario "
		+ "administrador en el sistema", "error_registro__nuevo_admin");
    }

    // ------------------------
    // Comprobaciones de login
    // ------------------------

    public static void minLoginLength(UserDTO user) throws BusinessException {
	BusinessCheck.isTrue(user.getLogin().length() >= 3, "El login indicado"
		+ " no cumple con los requisitos de tamaño (más 3 caracteres)",
		"error_registro__login_longitud");
    }

    public static void notRepeatedLogin(UserDTO user) throws BusinessException {
	User u = UserFinder.findByLogin(user.getLogin());

	BusinessCheck
		.isNull(u,
			"Ya existe un usuario con este login ["
				+ user.getLogin() + "]",
			"error_registro__login_ya_existe");
    }

    // ------------------------
    // Comprobaciones de email
    // ------------------------

    public static void isValidEmailSyntax(UserDTO user)
	    throws BusinessException {
	BusinessCheck.isTrue(isValidEmail(user.getEmail()), "El email del "
		+ "usuario tiene un formato inválido",
		"error_registro_edicion__email_no_valido");
    }

    private static boolean isValidEmail(String email) {
	String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}"
		+ "\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])"
		+ "|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

	return Pattern.compile(ePattern).matcher(email).matches();
    }

    // ---------------------------
    // Comprobaciones de password
    // ---------------------------

    public static void minPasswordLength(UserDTO user) throws BusinessException {
	BusinessCheck.isTrue(user.getPassword().length() >= 8, "La contraseña "
		+ "indicada no cumple con los requisitos de longitud.",
		"error_registro_edicion__password_longitud");
    }

    public static void isValidPassword(UserDTO user) throws BusinessException {
	BusinessCheck.isTrue(isPasswordTypeCorrect(user.getPassword()), "La "
		+ "contraseña indicada no es válida. No cumple con los "
		+ "requisitos de complejidad.",
		"error_registro_edicion__password_contenido");
    }

    private static boolean isPasswordTypeCorrect(String password) {
	String passPattern = "([0-9a-zA-Z]*[a-zA-Z]+[0-9a-zA-Z]*[0-9]+[0-9a-zA-Z]*)|"
		+ "([0-9a-zA-Z]*[0-9]+[0-9a-zA-Z]*[a-zA-Z]+[0-9a-zA-Z]*)";
	return Pattern.compile(passPattern).matcher(password).matches();
    }

}