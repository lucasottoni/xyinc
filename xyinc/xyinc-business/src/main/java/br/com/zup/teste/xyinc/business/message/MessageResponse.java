package br.com.zup.teste.xyinc.business.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * MessageResponse
 * Response class for every public method on business classes.
 * Contains a return object (or a list of objects), error messages and/or encapsulated exceptions.
 *
 * @author lucasottoni
 *
 * @param <T> - Return type
 */
public class MessageResponse<T extends Serializable> implements Serializable {

	private static final long serialVersionUID = 3536989465936003819L;
	private static final String SUCCESS = "SUCCESS";
	private T result;
	private List<T> resultList = new ArrayList<T>();
	private List<Message> messageList = new ArrayList<>();
	private transient Throwable encapsulatedException;

	public MessageResponse() {
		this.messageList = new ArrayList<>();
	}

	public MessageResponse(T result) {
		Message message = new Message(MessageUtils.getMessage(SUCCESS), Message.MessageSeverity.INFO);
		this.messageList.add(message);
		this.result = result;
	}

	public MessageResponse(List<T> resultList) {
		Message message = new Message(MessageUtils.getMessage(SUCCESS), Message.MessageSeverity.INFO);
		this.messageList.add(message);
		this.resultList = resultList;
	}

	public enum MessageResponseStatus {
		SUCCESS,

		WARNING,

		ERROR;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

	public List<Message> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<Message> messageList) {
		this.messageList = messageList;
	}

	public Throwable getEncapsulatedException() {
		return encapsulatedException;
	}

	public void setEncapsulatedException(Throwable encapsulatedException) {
		this.encapsulatedException = encapsulatedException;
	}

	public MessageResponseStatus getStatus() {
		if (messageList == null) {
			return MessageResponseStatus.WARNING;
		}
		MessageResponseStatus status = MessageResponseStatus.SUCCESS;
		for (Message message : messageList) {
			if (message.getSeverity().equals(Message.MessageSeverity.ERROR)
					|| message.getSeverity().equals(Message.MessageSeverity.FATAL)) {
				return MessageResponseStatus.ERROR;
			}
			if (message.getSeverity().equals(Message.MessageSeverity.WARNING)
					&& status.equals(MessageResponseStatus.SUCCESS)) {
				status = MessageResponseStatus.WARNING;
			}
		}
		return status;
	}

	public void addSuccessMessage() {
		Message message = new Message(MessageUtils.getMessage(SUCCESS), Message.MessageSeverity.INFO);
		this.messageList.add(message);
	}

	public void addSuccessMessage(String key) {
		Message message = new Message(MessageUtils.getMessage(key), Message.MessageSeverity.INFO);
		this.messageList.add(message);
	}

	public void addWarningMessage(String key) {
		Message message = new Message(MessageUtils.getMessage(key), Message.MessageSeverity.WARNING);
		this.messageList.add(message);
	}

	public void addWarningMessage(String key, Object... params) {
		Message message =
				new Message(String.format(MessageUtils.getMessage(key), params), Message.MessageSeverity.WARNING);
		this.messageList.add(message);
	}

	public void addErrorMessage(String key) {
		Message message = new Message(MessageUtils.getMessage(key), Message.MessageSeverity.ERROR);
		this.messageList.add(message);
	}

	public void addErrorMessage(String key, Object... params) {
		Message message =
				new Message(String.format(MessageUtils.getMessage(key), params), Message.MessageSeverity.ERROR);
		this.messageList.add(message);
	}

	public void addConstraintErrorMessage(String error) {
		Message message = new Message(error, Message.MessageSeverity.ERROR);
		this.messageList.add(message);
	}

	@Override
	public String toString() {
		StringBuilder sb =
				new StringBuilder().append("MessageResponse status[").append(getStatus()).append("] result[");
		if (result != null) {
			sb.append(result.toString());
		} else if (resultList != null) {
			sb.append(resultList.toString()).append("] ");
		}
		sb.append("] messages[").append(messageList.toString()).append("] ");
		if (encapsulatedException != null) {
			sb.append("exception=[").append(encapsulatedException.getMessage()).append("]");
		}
		return sb.toString();
	}

	public boolean isSuccess() {
		return this.getStatus().equals(MessageResponseStatus.SUCCESS);
	}

	public boolean isError() {
		return this.getStatus().equals(MessageResponseStatus.ERROR);
	}

	public boolean isWarning() {
		return this.getStatus().equals(MessageResponseStatus.WARNING);
	}
}
