package br.com.zup.teste.xyinc.model.dao.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.TransientDataAccessResourceException;

import br.com.zup.teste.xyinc.model.dao.ModelDAO;
import br.com.zup.teste.xyinc.model.dao.ModelFieldDAO;
import br.com.zup.teste.xyinc.model.entity.ModelField;

/**
 * ModelDAOTest
 * Unit tests for ModelDAO class.
 *
 * @author lucasottoni
 *
 */
public class ModelFieldDAOTest extends SetupTest {

	@Autowired
	private ModelDAO dao;

	@Autowired
	private ModelFieldDAO modelFieldDAO;

	private synchronized void prepare() {
		if (!prepared) {
			fullModel = createCompleteModel(dao, modelFieldDAO);
		}
		prepared = true;
	}

	@Test
	public void testInsertModelField() {
		prepare();
	}

	@Test(expected = TransientDataAccessResourceException.class)
	public void testInsertModelFieldFail1() {
		prepare();
		modelFieldDAO.insertModelField(createModelField(fullModel, "1"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInsertModelFieldFail2() {
		prepare();
		modelFieldDAO.insertModelField(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInsertModelFieldFail3() {
		prepare();
		ModelField modelField = new ModelField();
		modelField.setModel(null);
		modelFieldDAO.insertModelField(modelField);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInsertModelFieldFail4() {
		prepare();
		ModelField modelField = new ModelField();
		modelField.setModel(fullModel);
		modelField.setName(null);
		modelFieldDAO.insertModelField(modelField);
	}

	@Test
	public void testFindByModel() {
		prepare();
		List<ModelField> list = modelFieldDAO.findByModel(fullModel.getId());
		Assert.assertNotNull(list);
		Assert.assertEquals(2, list.size());
	}

	@Test
	public void testFindByModelFail1() {
		prepare();
		List<ModelField> list = modelFieldDAO.findByModel(0);
		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindByModelFail2() {
		prepare();
		modelFieldDAO.findByModel(null);
	}

}
