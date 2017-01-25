package br.com.zup.teste.xyinc.business.exception;

import org.apache.commons.lang3.ArrayUtils;

import br.com.zup.teste.xyinc.model.enums.MessageKeyEnum;

/**
 * AbstractI18NException
 * Exception that supports I18N.
 *
 * @author lucasottoni
 */
public abstract class AbstractI18NException extends RuntimeException {

	private static final long serialVersionUID = 857129814247522388L;

	private String message;

	private MessageKeyEnum messageKey;

	private transient Object[] messageParams;

	protected AbstractI18NException(Throwable cause, MessageKeyEnum messageKey, Object... messageParams) {
		super(cause);
		this.messageKey = messageKey;
		this.messageParams = ArrayUtils.clone(messageParams);
	}

	protected AbstractI18NException(Throwable cause, String message) {
		super(cause);
		this.message = message;
	}

	protected AbstractI18NException(String message) {
		this.message = message;
	}

	protected AbstractI18NException(MessageKeyEnum messageKey, Object... messageParams) {
		this.messageKey = messageKey;
		this.messageParams = ArrayUtils.clone(messageParams);
	}

	protected AbstractI18NException(AbstractI18NException exception) {
		super(exception);
		this.messageKey = exception.getMessageKey();
		this.messageParams = ArrayUtils.clone(exception.getMessageParams());
	}

	protected AbstractI18NException(Throwable e) {
		super(e);
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MessageKeyEnum getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(MessageKeyEnum messageKey) {
		this.messageKey = messageKey;
	}

	public Object[] getMessageParams() {
		return messageParams;
	}

	public void setMessageParams(Object[] messageParams) {
		this.messageParams = messageParams;
	}

}
