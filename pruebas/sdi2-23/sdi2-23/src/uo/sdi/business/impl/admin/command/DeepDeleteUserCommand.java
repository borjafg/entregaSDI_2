package uo.sdi.business.impl.admin.command;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.model.User;
import uo.sdi.persistence.UserFinder;
import uo.sdi.persistence.util.Jpa;

public class DeepDeleteUserCommand implements Command<Void> {

    private Long userId;

    public DeepDeleteUserCommand(Long id) {
	this.userId = id;
    }

    @Override
    public Void execute() throws BusinessException {
	User user = UserFinder.findById(userId);

	BusinessCheck.isNotNull(user, "El usuario no existe",
		"error_administrador_borrar_usuario_no_existe");

	// Borrado en cascada de las tareas y categorias
	Jpa.getManager().remove(user);

	return null;
    }

}