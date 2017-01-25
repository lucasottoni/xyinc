package br.com.zup.teste.xyinc.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.zup.teste.xyinc.model.entity.ModelField;
import br.com.zup.teste.xyinc.model.enums.DataTypeEnum;

/**
 * ModelFieldMapper
 * Translates a field on the database to the entity.
 *
 * @author lucasottoni
 *
 */
public class ModelFieldMapper implements RowMapper<ModelField> {

	/*
	 * (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public ModelField mapRow(ResultSet rs, int rowNum) throws SQLException {
		ModelField modelField = new ModelField();
		modelField.setId(rs.getInt("id"));
		modelField.setName(rs.getString("name"));
		modelField.setType(DataTypeEnum.fromString(rs.getString("type")));
		return modelField;
	}

}
