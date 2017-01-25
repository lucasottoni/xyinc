package br.com.zup.teste.xyinc.model.entity;

import java.io.Serializable;

import br.com.zup.teste.xyinc.model.enums.DataTypeEnum;

/**
 * ModelField
 * Entity that represents a field (column) on database.
 *
 * @author lucasottoni
 *
 */
public class ModelField implements Serializable {

	private static final long serialVersionUID = -2658605729860485039L;
	private Integer id;
	private String name;
	private DataTypeEnum type;
	private Model model;
	private Object value;

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

	public DataTypeEnum getType() {
		return type;
	}

	public void setType(DataTypeEnum type) {
		this.type = type;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
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
		ModelField other = (ModelField)obj;
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
		return "ModelField [id=" + id + ", name=" + name + ", type=" + type + ", value=" + value + "]";
	}

}
