package br.com.zup.teste.xyinc.web.bean;

import java.io.Serializable;

import br.com.zup.teste.xyinc.model.entity.Model;

/**
 * ModelBean
 * Web translation of a {@link Model}.
 *
 * @author lucasottoni
 *
 */
public class ModelBean implements Serializable {

	private static final long serialVersionUID = 3891443600757460080L;
	private String name;
	private String[] fieldNames;
	private String[] fieldTypes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getFieldNames() {
		return fieldNames;
	}

	public void setFieldNames(String[] fieldNames) {
		this.fieldNames = fieldNames;
	}

	public String[] getFieldTypes() {
		return fieldTypes;
	}

	public void setFieldTypes(String[] fieldTypes) {
		this.fieldTypes = fieldTypes;
	}

}
