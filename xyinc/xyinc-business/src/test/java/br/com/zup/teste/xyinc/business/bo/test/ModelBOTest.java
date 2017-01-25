package br.com.zup.teste.xyinc.business.bo.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.zup.teste.xyinc.business.bo.ModelBO;
import br.com.zup.teste.xyinc.business.message.MessageResponse;
import br.com.zup.teste.xyinc.model.entity.Model;
import br.com.zup.teste.xyinc.model.entity.ModelField;

public class ModelBOTest extends SetupTest {

	@Autowired
	private ModelBO bo;

	private static Boolean prepared = false;

	private synchronized void prepare() {
		if (!prepared) {
			createCompleteModel();
		}
		prepared = true;
	}

	@Test
	public void testInsertModel() {
		prepare();
	}

	@Test
	public void testInsertModelFail1() {
		Model model = new Model();
		MessageResponse<Model> test = bo.insertModel(model);
		Assert.assertNotNull(test);
		Assert.assertEquals(true, test.isError());
	}

	@Test
	public void testInsertModelFail2() {
		prepare();
		MessageResponse<Model> test = bo.insertModel(createModel());
		Assert.assertNotNull(test);
		Assert.assertEquals(true, test.isError());
	}

	@Test
	public void testFindByName1() {
		prepare();
		MessageResponse<Model> test = bo.findByName(NAME_TEST);
		Assert.assertNotNull(test);
		Assert.assertNotNull(test.getResult());
		Assert.assertNotNull(test.getResult().getId());
	}

	@Test
	public void testFindByName2() {
		MessageResponse<Model> test = bo.findByName(null);
		Assert.assertNotNull(test);
		Assert.assertEquals(true, test.isError());
	}

	@Test
	public void testFindByName3() {
		MessageResponse<Model> test = bo.findByName("AAA");
		Assert.assertNotNull(test);
		Assert.assertEquals(true, test.isError());
	}

	protected void createCompleteModel() {
		Model model = createModel();
		List<ModelField> listModelField = new ArrayList<ModelField>();
		listModelField.add(createModelField(model, "1"));
		listModelField.add(createModelField(model, "2"));
		model.setFields(listModelField);
		MessageResponse<Model> response = bo.insertModel(model);
		Assert.assertNotNull(response.getResult());
		Assert.assertNotNull(response.getResult().getId());
	}
}
