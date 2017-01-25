package br.com.zup.teste.xyinc.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import br.com.zup.teste.xyinc.model.entity.ModelField;
import br.com.zup.teste.xyinc.model.enums.MessageKeyEnum;
import br.com.zup.teste.xyinc.model.mapper.ModelFieldMapper;

/**
 * ModelFieldDAO
 * Database operations on table model_field.
 *
 * @author lucasottoni
 *
 */
@Repository
public class ModelFieldDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Given a model, retrieves all of its fields.
	 *
	 * @param idModel - Model to get fields.
	 * @return List<ModelFields> - List of fields from given Model.
	 */
	public List<ModelField> findByModel(Integer idModel) {
		Assert.notNull(idModel, MessageKeyEnum.ERROR_ID_NULL.toPropertyName());
		String sqlModelField = "SELECT id, name, type FROM model_field WHERE id_model = ?";
		return jdbcTemplate.query(sqlModelField, new Object[] {idModel}, new ModelFieldMapper());
	}

	/**
	 * Insert a field for a given Model.
	 *
	 * @param modelField - ModelField to be inserted.
	 * @return ModelField - The ModelField with id.
	 */
	public synchronized ModelField insertModelField(ModelField modelField) {
		Assert.notNull(modelField, MessageKeyEnum.ERROR_MODEL_FIELD_NULL.toPropertyName());
		Assert.notNull(modelField.getModel(), MessageKeyEnum.ERROR_MODEL_NULL.toPropertyName());
		Assert.notNull(modelField.getName(), MessageKeyEnum.ERROR_NAME_NULL.toPropertyName());
		Assert.notNull(modelField.getType(), MessageKeyEnum.ERROR_TYPE_NULL.toPropertyName());
		Assert.isTrue(!modelField.getName().equalsIgnoreCase("id"),
				MessageKeyEnum.ERROR_FIELD_NAMED_ID.toPropertyName());
		String sql = "INSERT INTO model_field (id_model, name, type) VALUES (?, ?, ?)";
		jdbcTemplate.update(sql, new Object[] {modelField.getModel().getId(), modelField.getName().toLowerCase(),
				modelField.getType().type()});
		modelField.setId(jdbcTemplate.queryForObject("SELECT MAX(id) FROM model_field", Integer.class));
		return modelField;
	}

}
