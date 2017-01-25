package br.com.zup.teste.xyinc.model.dao.test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import br.com.zup.teste.xyinc.model.dao.ModelDAO;
import br.com.zup.teste.xyinc.model.dao.ModelFieldDAO;
import br.com.zup.teste.xyinc.model.entity.Model;

/**
 * ModelDAOTest
 * Unit tests for ModelDAO class.
 *
 * @author lucasottoni
 *
 */
public class ModelDAOTest extends SetupTest {

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
	public void testInsertModel() {
		prepare();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInsertModelFail1() {
		Model model = new Model();
		dao.insertModel(model);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testInsertModelFail2() {
		prepare();
		dao.insertModel(createModel());
	}

	@Test
	public void testFindByName1() {
		prepare();
		Model test = dao.findByName(NAME_TEST);
		Assert.assertNotNull(test);
		Assert.assertNotNull(test.getFields());
		Assert.assertEquals(2, test.getFields().size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindByName2() {
		dao.findByName(null);
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void testFindByName3() {
		dao.findByName("AAAA");
	}

}
