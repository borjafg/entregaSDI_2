package uo.sdi.business.impl.command;

import uo.sdi.business.exception.BusinessException;

public interface Command<T> {

    T execute() throws BusinessException;

}
