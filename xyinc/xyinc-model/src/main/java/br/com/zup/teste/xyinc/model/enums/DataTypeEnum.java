package br.com.zup.teste.xyinc.model.enums;

/**
 * DataTypeEnum
 * Available datatypes for user selection.
 *
 * @author lucasottoni
 *
 */
public enum DataTypeEnum {

	VARCHAR("VARCHAR(200)"), INTEGER("INTEGER"), DOUBLE("DOUBLE"), DECIMAL("DECIMAL(12,5)"), DATE("DATE"),
	TEXT("LONGVARCHAR"), CURRENCY("DECIMAL(10,2)");

	private String type;

	DataTypeEnum(String type) {
		this.type = type;
	}

	public String type() {
		return type;
	}

	public static DataTypeEnum fromString(String text) {
		if (text != null) {
			for (DataTypeEnum b : DataTypeEnum.values()) {
				if (text.equals(b.type())) {
					return b;
				}
			}
		}
		return null;
	}

}
