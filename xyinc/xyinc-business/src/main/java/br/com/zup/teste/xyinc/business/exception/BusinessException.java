package br.com.zup.teste.xyinc.business.exception;

import br.com.zup.teste.xyinc.model.enums.MessageKeyEnum;

/**
 * BusinessException
 * Specialized exception for business methods.
 *
 * @author lucasottoni
 *
 */
public class BusinessException extends AbstractI18NException {

	private static final long serialVersionUID = -1775962489288116557L;

	public BusinessException(Throwable cause, MessageKeyEnum message, Object... params) {
		super(cause, message, params);
	}

	public BusinessException(Throwable cause, String message) {
		super(cause, message);
	}

	public BusinessException(MessageKeyEnum message, Object... params) {
		super(message, params);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String message) {
		super(message);
	}
}
