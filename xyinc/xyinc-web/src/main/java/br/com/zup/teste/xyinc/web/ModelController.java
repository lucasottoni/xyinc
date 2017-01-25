package br.com.zup.teste.xyinc.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.zup.teste.xyinc.business.bo.ModelBO;
import br.com.zup.teste.xyinc.business.message.MessageResponse;
import br.com.zup.teste.xyinc.model.entity.ModelField;
import br.com.zup.teste.xyinc.model.enums.DataTypeEnum;
import br.com.zup.teste.xyinc.web.bean.ModelBean;

/**
 * ModelController
 * Inserts a model, filled on an HTML form.
 * Default URL: http://localhost:8080/
 *
 * @author lucasottoni
 *
 */
@Controller
public class ModelController {

	@Autowired
	private ModelBO bo;

	/**
	 * Inits the form.
	 * 
	 * @param model - Spring model
	 * @return String - Page cadastro.html
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String initModel(Model model) {
		model.addAttribute("modelBean", new ModelBean());
		return "cadastro";
	}

	/**
	 * Creates a model on database.
	 * 
	 * @param bean - A POJO representing the model.
	 * @param model - Spring model.
	 * @return String - Page cadastro.html
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String insertModel(@ModelAttribute ModelBean bean, Model model) {
		if (bean.getName() == null || "".equals(bean.getName())) {
			model.addAttribute("error", "Preencha o nome do modelo.");
		}
		if (bean.getFieldNames() == null || bean.getFieldNames().length == 0) {
			model.addAttribute("error", "Preencha os campos do modelo.");
		}
		boolean oneField = false;
		for (String field : bean.getFieldNames()) {
			if (field != null && !field.equals("")) {
				oneField = true;
			}
		}
		if (!oneField) {
			model.addAttribute("error", "Preencha os campos do modelo.");
		}
		br.com.zup.teste.xyinc.model.entity.Model entity = new br.com.zup.teste.xyinc.model.entity.Model();
		entity.setName(bean.getName());
		List<ModelField> list = new ArrayList<ModelField>();
		int i = 0;
		for (String field : bean.getFieldNames()) {
			if (field != null && !field.equals("")) {
				ModelField mf = new ModelField();
				mf.setName(field);
				mf.setType(DataTypeEnum.valueOf(bean.getFieldTypes()[i]));
				list.add(mf);
			}
			i++;
		}
		entity.setFields(list);
		MessageResponse<br.com.zup.teste.xyinc.model.entity.Model> response = bo.insertModel(entity);
		model.addAttribute("error", response.getMessageList().get(0).getMessage());
		model.addAttribute("modelBean", new ModelBean());
		return "cadastro";
	}

}
