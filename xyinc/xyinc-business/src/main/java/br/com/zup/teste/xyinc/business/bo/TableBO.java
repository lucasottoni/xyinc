package br.com.zup.teste.xyinc.business.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.zup.teste.xyinc.business.message.MessageResponse;
import br.com.zup.teste.xyinc.model.dao.ModelDAO;
import br.com.zup.teste.xyinc.model.dao.TableDAO;
import br.com.zup.teste.xyinc.model.entity.Model;
import br.com.zup.teste.xyinc.model.enums.MessageKeyEnum;

/**
 * TableBO
 * Business object to operate on a given table through its {@link Model}.
 *
 * @author lucasottoni
 *
 */
@Service
public class TableBO {

	@Autowired
	private TableDAO tableDAO;

	@Autowired
	private ModelDAO modelDAO;

	/**
	 * Inserts an object on its table.
	 *
	 * @param model - Row to be created, with column values.
	 * @return MessageResponse<Model> - Created row with ID
	 */
	public MessageResponse<Model> insertRow(Model model) {
		Assert.notNull(model, MessageKeyEnum.ERROR_MODEL_NULL.toPropertyName());
		Assert.notNull(model.getName(), MessageKeyEnum.ERROR_NAME_NULL.toPropertyName());
		Assert.notNull(model.getFields(), MessageKeyEnum.ERROR_FIELDS_EMPTY.toPropertyName());
		Assert.notEmpty(model.getFields(), MessageKeyEnum.ERROR_FIELDS_EMPTY.toPropertyName());
		model = tableDAO.insertIntoTable(model);
		MessageResponse<Model> messageResponse = new MessageResponse<Model>();
		messageResponse.setResult(model);
		messageResponse.addSuccessMessage();
		return messageResponse;
	}

	/**
	 * Updates an object on its table.
	 *
	 * @param model - Row to be updated, with column values.
	 * @return MessageResponse<Model> - Same row.
	 */
	public MessageResponse<Model> updateRow(Model model) {
		Assert.notNull(model, MessageKeyEnum.ERROR_MODEL_NULL.toPropertyName());
		Assert.notNull(model.getName(), MessageKeyEnum.ERROR_NAME_NULL.toPropertyName());
		Assert.notNull(model.getFields(), MessageKeyEnum.ERROR_FIELDS_EMPTY.toPropertyName());
		Assert.notEmpty(model.getFields(), MessageKeyEnum.ERROR_FIELDS_EMPTY.toPropertyName());
		tableDAO.updateTable(model);
		MessageResponse<Model> messageResponse = new MessageResponse<Model>();
		messageResponse.setResult(model);
		messageResponse.addSuccessMessage();
		return messageResponse;
	}

	/**
	 * Deletes an object from its table.
	 *
	 * @param tableName - Table where row is stored.
	 * @param id - Id of the object to be removed.
	 * @return MessageResponse<Boolean> - True if OK.
	 */
	public MessageResponse<Boolean> deleteRow(String tableName, Integer id) {
		Assert.notNull(tableName, MessageKeyEnum.ERROR_NAME_NULL.toPropertyName());
		Assert.notNull(id, MessageKeyEnum.ERROR_ID_NULL.toPropertyName());
		tableDAO.deleteFromTable(tableName, id);
		MessageResponse<Boolean> messageResponse = new MessageResponse<Boolean>();
		messageResponse.setResult(true);
		messageResponse.addSuccessMessage();
		return messageResponse;
	}

	/**
	 * Selects all rows from a table.
	 *
	 * @param tableName - Table to be retrieved.
	 * @return MessageResponse<Model> - List with all rows from the table.
	 */
	public MessageResponse<Model> selectAllFromTable(String tableName) {
		Assert.notNull(tableName, MessageKeyEnum.ERROR_MODEL_NULL.toPropertyName());
		Model model = modelDAO.findByName(tableName);
		Assert.notNull(model, MessageKeyEnum.ERROR_MODEL_NULL.toPropertyName());
		Assert.notNull(model.getName(), MessageKeyEnum.ERROR_NAME_NULL.toPropertyName());
		Assert.notNull(model.getFields(), MessageKeyEnum.ERROR_FIELDS_EMPTY.toPropertyName());
		Assert.notEmpty(model.getFields(), MessageKeyEnum.ERROR_FIELDS_EMPTY.toPropertyName());
		List<Model> list = tableDAO.selectAllFromTable(model);
		MessageResponse<Model> messageResponse = new MessageResponse<Model>();
		messageResponse.setResultList(list);
		messageResponse.addSuccessMessage();
		return messageResponse;
	}

	/**
	 * Selects one rows from a table, by id.
	 *
	 * @param tableName - Table to be retrieved.
	 * @param id - Object's identifier.
	 * @return MessageResponse<Model> - One row from the table.
	 */
	public MessageResponse<Model> selectOneFromTable(String tableName, Integer id) {
		Assert.notNull(tableName, MessageKeyEnum.ERROR_MODEL_NULL.toPropertyName());
		Model model = modelDAO.findByName(tableName);
		Assert.notNull(model, MessageKeyEnum.ERROR_MODEL_NULL.toPropertyName());
		Assert.notNull(model.getName(), MessageKeyEnum.ERROR_NAME_NULL.toPropertyName());
		Assert.notNull(model.getFields(), MessageKeyEnum.ERROR_FIELDS_EMPTY.toPropertyName());
		Assert.notEmpty(model.getFields(), MessageKeyEnum.ERROR_FIELDS_EMPTY.toPropertyName());
		model.setValue(id);
		model = tableDAO.selectFromTable(model);
		MessageResponse<Model> messageResponse = new MessageResponse<Model>();
		messageResponse.setResult(model);
		messageResponse.addSuccessMessage();
		return messageResponse;
	}
}
