package br.com.zup.teste.xyinc.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Model
 * Entity that represents a table on database.
 *
 * @author lucasottoni
 *
 */
public class Model implements Serializable {

	private static final long serialVersionUID = -6221745092320746419L;
	private Integer id;
	private String name;
	private Integer value;
	private List<ModelField> fields;

	public Model() {
		super();
	}

	public Model(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public List<ModelField> getFields() {
		return fields;
	}

	public void setFields(List<ModelField> fields) {
		this.fields = fields;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Model other = (Model)obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Model [id=" + id + ", name=" + name + ", value=" + value + ", fields=" + fields + "]";
	}

}
