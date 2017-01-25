package br.com.zup.teste.xyinc.model.dao.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.zup.teste.xyinc.model.dao.ModelDAO;
import br.com.zup.teste.xyinc.model.dao.ModelFieldDAO;
import br.com.zup.teste.xyinc.model.dao.TableDAO;
import br.com.zup.teste.xyinc.model.entity.Model;
import br.com.zup.teste.xyinc.model.entity.ModelField;
import br.com.zup.teste.xyinc.model.enums.DataTypeEnum;

public class TableDAOTest extends SetupTest {

	private static final String FULL_TEST = "FULL_TEST";

	@Autowired
	private TableDAO dao;

	@Autowired
	private ModelDAO modelDAO;

	@Autowired
	private ModelFieldDAO modelFieldDAO;

	private Model fullModel;

	private static Boolean preparedLocal = false;

	private synchronized void prepare() {
		if (!preparedLocal) {
			this.fullModel = createFullModel();

			dao.createTable(this.fullModel);
			// Inserir 2, um vai ser deletado no teste
			dao.insertIntoTable(this.fullModel);
			dao.insertIntoTable(this.fullModel);
		}
		preparedLocal = true;
	}

	@Test
	public void testSelectAllFromTable() {
		prepare();
		Model model = modelDAO.findByName(FULL_TEST);
		List<Model> modelList = dao.selectAllFromTable(model);
		Assert.assertNotNull(modelList);
		Assert.assertNotEquals(0, modelList.size());
		System.out.println(modelList);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSelectAllFromTableFail1() {
		prepare();
		dao.selectAllFromTable(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSelectAllFromTableFail2() {
		prepare();
		Model model = modelDAO.findByName(FULL_TEST);
		model.setName(null);
		dao.selectAllFromTable(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSelectAllFromTableFail3() {
		prepare();
		Model model = modelDAO.findByName(FULL_TEST);
		model.setFields(null);
		dao.selectAllFromTable(null);
	}

	@Test
	public void testSelectFromTable() {
		prepare();
		Model modelBase = modelDAO.findByName(FULL_TEST);
		modelBase.setValue(1);
		Model model = dao.selectFromTable(modelBase);
		Assert.assertNotNull(model);
		Assert.assertNotNull(model.getId());
		System.out.println(model);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSelectFromTableFail1() {
		prepare();
		Model modelBase = modelDAO.findByName(FULL_TEST);
		modelBase.setValue(null);
		dao.selectFromTable(modelBase);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSelectFromTableFail2() {
		prepare();
		modelDAO.findByName(FULL_TEST);
		dao.selectFromTable(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSelectFromTableFail3() {
		prepare();
		Model modelBase = modelDAO.findByName(FULL_TEST);
		modelBase.setFields(null);
		dao.selectFromTable(modelBase);
	}

	@Test
	public void testUpdateFromTable() {
		prepare();
		Model modelBase = modelDAO.findByName(FULL_TEST);
		modelBase.setValue(1);
		Model model = dao.selectFromTable(modelBase);
		Assert.assertNotNull(model);
		Assert.assertNotNull(model.getId());
		System.out.println(model);
		for (ModelField modelField : model.getFields()) {
			if (modelField.getType().equals(DataTypeEnum.VARCHAR) || modelField.getType().equals(DataTypeEnum.TEXT)) {
				modelField.setValue(modelField.getValue() + "XXXX");
			}
		}
		dao.updateTable(model);
		Model alteredModel = dao.selectFromTable(modelBase);
		Assert.assertNotNull(alteredModel);
		Assert.assertNotNull(alteredModel.getId());
		System.out.println(alteredModel);
	}

	@Test
	public void testDeleteFromTable() {
		prepare();
		Model modelBase = modelDAO.findByName(FULL_TEST);
		modelBase.setValue(2);
		Model model = dao.selectFromTable(modelBase);
		Assert.assertNotNull(model);
		Assert.assertNotNull(model.getId());
		System.out.println(model);
		dao.deleteFromTable(FULL_TEST, 2);
		model = dao.selectFromTable(modelBase);
		Assert.assertNull(model);
	}

	private Model createFullModel() {
		Model model = modelDAO.insertModel(createModel(FULL_TEST));
		List<ModelField> fields = new ArrayList<ModelField>();
		ModelField field1 = createModelField(model, "1", DataTypeEnum.TEXT);
		field1.setValue("TESTE DE TEXTO COM CARACTERES ESPECIAIS '%'''%%___*");
		modelFieldDAO.insertModelField(field1);
		fields.add(field1);
		ModelField field2 = createModelField(model, "2", DataTypeEnum.VARCHAR);
		field2.setValue("TESTE DE TEXTO COM CARACTERES ESPECIAIS '%'''%%___*");
		modelFieldDAO.insertModelField(field2);
		fields.add(field2);
		ModelField field3 = createModelField(model, "3", DataTypeEnum.DATE);
		field3.setValue(new java.sql.Date(System.currentTimeMillis()));
		modelFieldDAO.insertModelField(field3);
		fields.add(field3);
		ModelField field4 = createModelField(model, "4", DataTypeEnum.INTEGER);
		field4.setValue(20);
		modelFieldDAO.insertModelField(field4);
		fields.add(field4);
		ModelField field5 = createModelField(model, "5", DataTypeEnum.DOUBLE);
		field5.setValue(3.14);
		modelFieldDAO.insertModelField(field5);
		fields.add(field5);
		ModelField field6 = createModelField(model, "6", DataTypeEnum.DECIMAL);
		field6.setValue(new BigDecimal(3.14157));
		modelFieldDAO.insertModelField(field6);
		fields.add(field6);
		ModelField field7 = createModelField(model, "7", DataTypeEnum.CURRENCY);
		field7.setValue(new BigDecimal(3.14));
		modelFieldDAO.insertModelField(field7);
		fields.add(field7);
		model.setFields(fields);
		return model;
	}
}
