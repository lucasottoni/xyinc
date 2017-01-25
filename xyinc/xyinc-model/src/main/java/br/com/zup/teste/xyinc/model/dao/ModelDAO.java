package br.com.zup.teste.xyinc.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import br.com.zup.teste.xyinc.model.entity.Model;
import br.com.zup.teste.xyinc.model.enums.MessageKeyEnum;

/**
 * Model DAO
 * Database operations on table Model.
 *
 * @author lucasottoni
 *
 */
@Repository
public class ModelDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ModelFieldDAO modelFieldDAO;

	/**
	 * Retrieves a model by its name. If no model found, throws an EmptyResultDataAccessException.
	 *
	 * @param name - Name of the model.
	 * @return Model - model found with the exact name, null otherwise.
	 */
	public Model findByName(String name) {
		Assert.notNull(name, MessageKeyEnum.ERROR_NAME_NULL.toPropertyName());
		String sqlModel = "SELECT id, name FROM model WHERE name = ?";
		Model model = jdbcTemplate.queryForObject(sqlModel, new Object[] {name.toLowerCase()},
				new BeanPropertyRowMapper<Model>(Model.class));
		model.setFields(modelFieldDAO.findByModel(model.getId()));
		return model;
	}

	/**
	 * Inserts a model on the database.
	 *
	 * @param model Model to be inserted.
	 * @return Model - model inserted with id.
	 */
	public synchronized Model insertModel(Model model) {
		Assert.notNull(model, MessageKeyEnum.ERROR_MODEL_NULL.toPropertyName());
		Assert.notNull(model.getName(), MessageKeyEnum.ERROR_NAME_NULL.toPropertyName());
		String sql = "INSERT INTO model (name) VALUES (?)";
		jdbcTemplate.update(sql, new Object[] {model.getName().toLowerCase()});
		model.setId(jdbcTemplate.queryForObject("SELECT MAX(id) FROM model", Integer.class));
		return model;
	}

}
