package br.com.zup.teste.xyinc.business.bo.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.zup.teste.xyinc.business.bo.ModelBO;
import br.com.zup.teste.xyinc.business.bo.TableBO;
import br.com.zup.teste.xyinc.business.message.MessageResponse;
import br.com.zup.teste.xyinc.model.entity.Model;
import br.com.zup.teste.xyinc.model.entity.ModelField;
import br.com.zup.teste.xyinc.model.enums.DataTypeEnum;

public class TableBOTest extends SetupTest {

	private static final String FULL_TEST = "FULL_TEST";

	@Autowired
	private TableBO bo;

	@Autowired
	private ModelBO modelBO;

	private Model fullModel;

	private static Boolean prepared = false;

	private synchronized void prepare() {
		if (!prepared) {
			this.fullModel = createFullModel();
			// Inserir 2, um vai ser deletado no teste
			bo.insertRow(this.fullModel);
			bo.insertRow(this.fullModel);
		}
		prepared = true;
	}

	@Test
	public void testSelectAllFromTable() {
		prepare();
		MessageResponse<Model> modelList = bo.selectAllFromTable(FULL_TEST);
		Assert.assertNotNull(modelList);
		Assert.assertNotEquals(0, modelList.getResultList().size());
		System.out.println(modelList);
	}

	@Test
	public void testSelectAllFromTableFail1() {
		prepare();
		MessageResponse<Model> modelList = bo.selectAllFromTable(null);
		Assert.assertNotNull(modelList);
		Assert.assertEquals(true, modelList.isError());
	}

	@Test
	public void testSelectFromTable() {
		prepare();
		MessageResponse<Model> response = bo.selectOneFromTable(FULL_TEST, 1);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getResult());
		System.out.println(response);
	}

	@Test
	public void testSelectFromTableFail1() {
		prepare();
		MessageResponse<Model> response = bo.selectOneFromTable(FULL_TEST, null);
		Assert.assertNotNull(response);
		Assert.assertEquals(true, response.isError());
	}

	@Test
	public void testUpdateFromTable() {
		prepare();
		MessageResponse<Model> response = bo.selectOneFromTable(FULL_TEST, 1);
		Assert.assertNotNull(response);
		Model modelBase = response.getResult();
		Assert.assertNotNull(modelBase);
		for (ModelField modelField : modelBase.getFields()) {
			if (modelField.getType().equals(DataTypeEnum.VARCHAR) || modelField.getType().equals(DataTypeEnum.TEXT)) {
				modelField.setValue(modelField.getValue() + "XXXX");
			}
		}
		MessageResponse<Model> response2 = bo.updateRow(modelBase);
		Assert.assertNotNull(response2);
		Assert.assertNotNull(response2.getResult());
		Assert.assertNotNull(response2.getResult().getId());
	}

	@Test
	public void testDeleteFromTable() {
		prepare();
		MessageResponse<Model> response = bo.selectOneFromTable(FULL_TEST, 2);
		Model model = response.getResult();
		Assert.assertNotNull(model);
		Assert.assertNotNull(model.getId());
		System.out.println(model);
		bo.deleteRow(FULL_TEST, 2);
		response = bo.selectOneFromTable(FULL_TEST, 2);
		model = response.getResult();
		Assert.assertNull(model);
	}

	private Model createFullModel() {
		Model model = createModel(FULL_TEST);
		List<ModelField> fields = new ArrayList<ModelField>();
		ModelField field1 = createModelField(model, "1", DataTypeEnum.TEXT);
		field1.setValue("TESTE DE TEXTO COM CARACTERES ESPECIAIS '%'''%%___*");
		fields.add(field1);
		ModelField field2 = createModelField(model, "2", DataTypeEnum.VARCHAR);
		field2.setValue("TESTE DE TEXTO COM CARACTERES ESPECIAIS '%'''%%___*");
		fields.add(field2);
		ModelField field3 = createModelField(model, "3", DataTypeEnum.DATE);
		field3.setValue(new java.sql.Date(System.currentTimeMillis()));
		fields.add(field3);
		ModelField field4 = createModelField(model, "4", DataTypeEnum.INTEGER);
		field4.setValue(20);
		fields.add(field4);
		ModelField field5 = createModelField(model, "5", DataTypeEnum.DOUBLE);
		field5.setValue(3.14);
		fields.add(field5);
		ModelField field6 = createModelField(model, "6", DataTypeEnum.DECIMAL);
		field6.setValue(new BigDecimal(3.14157));
		fields.add(field6);
		ModelField field7 = createModelField(model, "7", DataTypeEnum.CURRENCY);
		field7.setValue(new BigDecimal(3.14));
		fields.add(field7);
		model.setFields(fields);
		modelBO.insertModel(model);
		return model;
	}
}
