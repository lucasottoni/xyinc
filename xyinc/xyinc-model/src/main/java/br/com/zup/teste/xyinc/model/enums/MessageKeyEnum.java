package br.com.zup.teste.xyinc.model.enums;

/**
 * MessageKeyEnum
 * Keys for messages on properties file.
 *
 * @author lucasottoni
 *
 */
public enum MessageKeyEnum {

	ERROR_MODEL_NULL, ERROR_NAME_NULL, ERROR_FIELDS_EMPTY, ERROR_MODEL_FIELD_NULL, ERROR_TYPE_NULL, ERROR_ID_NULL,
	ERROR_VALUE_NULL, ERROR_FIELD_NAMED_ID;

	public String toPropertyName() {

		return this.name().toLowerCase().replace('_', '.');
	}

}
