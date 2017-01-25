package br.com.zup.teste.xyinc.business.message;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Message
 * Holds a message produced on business objects.
 *
 * @author lucasottoni
 *
 */
public class Message implements Serializable {

	private static final long serialVersionUID = -1193329172424626972L;

	private String messageString;
	private MessageSeverity severity;
	private Date createDate;

	public Message() {
		super();
	}

	public Message(String message, MessageSeverity severity) {
		this.messageString = message;
		this.severity = severity;
		this.createDate = new Date();
	}

	public enum MessageSeverity {
		DEBUG,

		INFO,

		WARNING,

		ERROR,

		FATAL;
	}

	public String getMessage() {
		return messageString;
	}

	public void setMessage(String message) {
		this.messageString = message;
	}

	public MessageSeverity getSeverity() {
		return severity;
	}

	public void setSeverity(MessageSeverity severity) {
		this.severity = severity;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		StringBuilder sb = new StringBuilder().append("Message severity[").append(getSeverity())
				.append("] create date[").append(sdf.format(Long.valueOf(getCreateDate().getTime()))).append("] ")
				.append("text=[").append(getMessage()).append("]");
		return sb.toString();
	}

}
