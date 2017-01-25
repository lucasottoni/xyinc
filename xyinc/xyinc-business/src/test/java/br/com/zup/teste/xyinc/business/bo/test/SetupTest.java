package br.com.zup.teste.xyinc.business.bo.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import br.com.zup.teste.xyinc.business.config.ContextConfig;
import br.com.zup.teste.xyinc.model.entity.Model;
import br.com.zup.teste.xyinc.model.entity.ModelField;
import br.com.zup.teste.xyinc.model.enums.DataTypeEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = ContextConfig.class)
public class SetupTest {

	public static final String NAME_TEST = "TESTE";

	@Test
	public void test() {
		Assert.assertTrue(true);
	}

	protected Model createModel() {
		Model model = new Model();
		model.setName(NAME_TEST);
		return model;
	}

	protected Model createModel(String name) {
		Model model = new Model();
		model.setName(name);
		return model;
	}

	protected ModelField createModelField(Model model, String suffix) {
		return createModelField(model, suffix, DataTypeEnum.TEXT);
	}

	protected ModelField createModelField(Model model, String suffix, DataTypeEnum dataType) {
		ModelField modelField = new ModelField();
		modelField.setName(NAME_TEST + suffix);
		modelField.setType(dataType);
		modelField.setModel(model);
		return modelField;
	}

}
