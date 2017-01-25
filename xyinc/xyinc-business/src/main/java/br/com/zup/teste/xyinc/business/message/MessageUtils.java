package br.com.zup.teste.xyinc.business.message;

import java.util.Locale;

import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * MessageUtils
 * Util class to process messages.
 *
 * @author lucasottoni
 *
 */
@Component
@Scope("singleton")
public final class MessageUtils {

	private MessageUtils() {

	}

	/**
	 * Given a key, retrieves its message from properties file.
	 *
	 * @param key - Key to the message.
	 * @return String - Message.
	 */
	public static String getMessage(String key) {

		try {
			ResourceBundleMessageSource bean = new ResourceBundleMessageSource();
			bean.setBasename("messages");
			return bean.getMessage(key, null, Locale.getDefault());
		} catch (Exception e) {
			return "Unresolved key: " + key;
		}

	}
}
