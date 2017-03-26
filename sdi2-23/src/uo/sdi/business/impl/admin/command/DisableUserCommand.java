package uo.sdi.business.impl.admin.command;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.model.User;
import uo.sdi.model.types.UserStatus;
import uo.sdi.persistence.UserFinder;

public class DisableUserCommand implements Command<Void> {

    private Long id;

    public DisableUserCommand(Long id) {
	this.id = id;
    }

    @Override
    public Void execute() throws BusinessException {
	User user = UserFinder.findById(id);

	BusinessCheck.isNotNull(user, "El usuario no existe.",
		"error_administrador_borrar_usuario_no_existe");

	BusinessCheck.isTrue(user.getStatus().equals(UserStatus.ENABLED),
		"El usuario ya estaba deshabilitado.",
		"error_administrador_cambiar_estado_usuario_ya_deshabilitado");

	user.setStatus(UserStatus.DISABLED); // Estado persistent

	return null;
    }

}