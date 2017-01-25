package br.com.zup.teste.xyinc.business.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.zup.teste.xyinc.business.message.MessageResponse;
import br.com.zup.teste.xyinc.model.dao.ModelDAO;
import br.com.zup.teste.xyinc.model.dao.ModelFieldDAO;
import br.com.zup.teste.xyinc.model.dao.TableDAO;
import br.com.zup.teste.xyinc.model.entity.Model;
import br.com.zup.teste.xyinc.model.entity.ModelField;
import br.com.zup.teste.xyinc.model.enums.MessageKeyEnum;

/**
 * ModelBO
 * Business object to create and retrieve {@link Model}.
 *
 * @author lucasottoni
 *
 */
@Service
public class ModelBO {

	@Autowired
	private ModelDAO modelDAO;

	@Autowired
	private ModelFieldDAO modelFieldDAO;

	@Autowired
	private TableDAO tableDAO;

	/**
	 * Given a complete model, inserts it on database and create its table.
	 *
	 * @param model - Model to be created, with ModelFields.
	 * @return MessageResponse<Model> - Created model with ID
	 */
	public MessageResponse<Model> insertModel(Model model) {
		Assert.notNull(model, MessageKeyEnum.ERROR_MODEL_NULL.toPropertyName());
		Assert.notNull(model.getName(), MessageKeyEnum.ERROR_NAME_NULL.toPropertyName());
		Assert.notNull(model.getFields(), MessageKeyEnum.ERROR_FIELDS_EMPTY.toPropertyName());
		Assert.notEmpty(model.getFields(), MessageKeyEnum.ERROR_FIELDS_EMPTY.toPropertyName());
		model = modelDAO.insertModel(model);
		List<ModelField> fieldsInserted = new ArrayList<ModelField>();
		for (ModelField modelField : model.getFields()) {
			Assert.notNull(modelField, MessageKeyEnum.ERROR_MODEL_FIELD_NULL.toPropertyName());
			Assert.notNull(modelField.getName(), MessageKeyEnum.ERROR_NAME_NULL.toPropertyName());
			Assert.notNull(modelField.getType(), MessageKeyEnum.ERROR_TYPE_NULL.toPropertyName());
			modelField.setModel(model);
			fieldsInserted.add(modelFieldDAO.insertModelField(modelField));
		}
		model.setFields(fieldsInserted);
		tableDAO.createTable(model);
		MessageResponse<Model> messageResponse = new MessageResponse<Model>();
		messageResponse.setResult(model);
		messageResponse.addSuccessMessage();
		return messageResponse;
	}

	/**
	 * Retrieves a model, and its model fields, from database.
	 *
	 * @param name - Model name.
	 * @return MessageResponse<Model> - The retrieved model.
	 */
	public MessageResponse<Model> findByName(String name) {
		Assert.notNull(name, MessageKeyEnum.ERROR_NAME_NULL.toPropertyName());
		Model model = modelDAO.findByName(name);
		MessageResponse<Model> messageResponse = new MessageResponse<Model>();
		messageResponse.setResult(model);
		messageResponse.addSuccessMessage();
		return messageResponse;
	}
}
