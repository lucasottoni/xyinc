package br.com.zup.teste.xyinc.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.teste.xyinc.business.bo.ModelBO;
import br.com.zup.teste.xyinc.business.bo.TableBO;
import br.com.zup.teste.xyinc.business.message.MessageResponse;
import br.com.zup.teste.xyinc.model.entity.Model;
import br.com.zup.teste.xyinc.model.entity.ModelField;

/**
 * TableController
 * Rest controller that provides services to insert/update/delete/search objects.
 *
 * @author lucasottoni
 *
 */
@RestController
public class TableController {

	@Autowired
	private TableBO bo;

	@Autowired
	private ModelBO modelBo;

	/**
	 * Retrieves all objects from one table on database.
	 *
	 * @param name - Table name where objects should be retrieved.
	 * @return MessageResponse - Messages and status. Serialized to Application/JSON.
	 */
	@RequestMapping(value = "/{name}/", method = RequestMethod.GET)
	public @ResponseBody MessageResponse<Model> retrieve(@PathVariable("name") String name) {
		MessageResponse<Model> response = bo.selectAllFromTable(name);
		response.setEncapsulatedException(null);
		System.out.println(response.getResultList());
		return response;
	}

	/**
	 * Retrieves one object from database.
	 *
	 * @param name - Table name where the object should be retrieved.
	 * @param id - Id of the object to be retrieved.
	 * @return MessageResponse - Messages and status. Serialized to Application/JSON.
	 */
	@RequestMapping(value = "/{name}/{id}", method = RequestMethod.GET)
	public @ResponseBody MessageResponse<Model> retrieve(@PathVariable("name") String name,
			@PathVariable("id") Integer id) {
		MessageResponse<Model> response = bo.selectOneFromTable(name, id);
		response.setEncapsulatedException(null);
		System.out.println(response.getResult());
		return response;
	}

	/**
	 * Insert one object on database.
	 *
	 * @param name - Table name where the object should be inserted.
	 * @param model - {@link Model} Represents an object on database. Expects Application/JSON.
	 * @return MessageResponse - Messages and status. Serialized to Application/JSON.
	 */
	@RequestMapping(value = "/{name}/", method = RequestMethod.POST)
	public @ResponseBody MessageResponse<Model> insert(@PathVariable("name") String name, @RequestBody Model model) {
		MessageResponse<Model> responseModel = modelBo.findByName(name);
		Model modelFilled = responseModel.getResult();
		for (ModelField mf : model.getFields()) {
			for (ModelField mfx : modelFilled.getFields()) {
				if (mfx.getName().equalsIgnoreCase(mf.getName())) {
					mfx.setValue(mf.getValue());
					break;
				}
			}
		}
		MessageResponse<Model> response = bo.insertRow(modelFilled);
		response.setEncapsulatedException(null);
		System.out.println(response.getResult());
		return response;
	}

	/**
	 * Updates an object.
	 *
	 * @param name - Table name where the object should be updated.
	 * @param id - Id of the object to be updated.
	 * @param model - {@link Model} Represents an object on database. Expects Application/JSON.
	 * @return MessageResponse - Messages and status. Serialized to Application/JSON.
	 */
	@RequestMapping(value = "/{name}/{id}", method = RequestMethod.PUT)
	public @ResponseBody MessageResponse<Model> update(@PathVariable("name") String name,
			@PathVariable("id") Integer id, @RequestBody Model model) {
		MessageResponse<Model> responseModel = modelBo.findByName(name);
		Model modelFilled = responseModel.getResult();
		for (ModelField mf : model.getFields()) {
			for (ModelField mfx : modelFilled.getFields()) {
				if (mfx.getName().equals(mf.getName())) {
					mfx.setValue(mf.getValue());
					break;
				}
			}
		}
		modelFilled.setValue(id);
		MessageResponse<Model> response = bo.updateRow(modelFilled);
		response.setEncapsulatedException(null);
		System.out.println(response.getResult());
		return response;
	}

	/**
	 * Deletes an object.
	 *
	 * @param name - Table name where the object should be deleted.
	 * @param id - Id of the object to be deleted.
	 * @return MessageResponse - Messages and status. Serialized to Application/JSON.
	 */
	@RequestMapping(value = "/{name}/{id}", method = RequestMethod.DELETE)
	public @ResponseBody MessageResponse<Model> delete(@PathVariable("name") String name,
			@PathVariable("id") Integer id) {
		MessageResponse<Boolean> response = bo.deleteRow(name, id);
		MessageResponse<Model> responseFinal = new MessageResponse<Model>();
		if (response.isSuccess()) {
			responseFinal.addSuccessMessage();
		} else {
			responseFinal.setMessageList(response.getMessageList());
		}
		responseFinal.setEncapsulatedException(null);
		return responseFinal;
	}

}
