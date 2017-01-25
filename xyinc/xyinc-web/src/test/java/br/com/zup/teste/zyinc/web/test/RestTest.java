package br.com.zup.teste.zyinc.web.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import br.com.zup.teste.xyinc.business.bo.ModelBO;
import br.com.zup.teste.xyinc.model.entity.Model;
import br.com.zup.teste.xyinc.model.entity.ModelField;
import br.com.zup.teste.xyinc.model.enums.DataTypeEnum;
import br.com.zup.teste.xyinc.web.config.WebApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(WebApplication.class)
@WebIntegrationTest
public class RestTest {

	public static final String NAME_TEST = "TESTE";

	@Autowired
	private ModelBO modelBO;

	private RestTemplate template = new TestRestTemplate();

	private static Boolean prepared = false;

	private static final String REST_1 = "{\"name\": \"teste\"," + "\"fields\": ["
			+ "{\"name\": \"teste1\", \"value\": \"teste\"}," + "{\"name\": \"teste2\", \"value\": \"1\"}" + "]" + "}";

	private static final String REST_2 =
			"{\"name\": \"teste\"," + "\"fields\": [" + "{\"name\": \"teste1\", \"value\": \"testexxx\"},"
					+ "{\"name\": \"teste2\", \"value\": \"2\"}" + "]" + "}";

	private synchronized void prepare() {
		if (!prepared) {
			createFullModel();
		}
		prepared = true;
	}

	@Test
	public void testAll() {
		prepare();
		testInsert();
		testSelectAll();
		testSelectOne();
		testUpdate();
		testSelectOne();
		testDelete();
	}

	public void testInsert() {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(REST_1, requestHeaders);
		String response = template.postForObject("http://localhost:8080/teste/", httpEntity, String.class);
		System.out.println(response);
		Assert.assertTrue(response.indexOf("SUCCESS") >= 0);
	}

	public void testSelectAll() {
		String response = template.getForObject("http://localhost:8080/teste/", String.class);
		System.out.println(response);
		Assert.assertTrue(response.indexOf("SUCCESS") >= 0);
	}

	public void testSelectOne() {
		String response = template.getForObject("http://localhost:8080/teste/1", String.class);
		System.out.println(response);
		Assert.assertTrue(response.indexOf("SUCCESS") >= 0);
	}

	public void testUpdate() {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(REST_2, requestHeaders);
		template.put("http://localhost:8080/teste/1", httpEntity);
	}

	public void testDelete() {
		template.delete("http://localhost:8080/teste/1");
	}

	protected Model createModel(String name) {
		Model model = new Model();
		model.setName(name);
		return model;
	}

	protected ModelField createModelField(Model model, String suffix, DataTypeEnum dataType) {
		ModelField modelField = new ModelField();
		modelField.setName(NAME_TEST + suffix);
		modelField.setType(dataType);
		modelField.setModel(model);
		return modelField;
	}

	private Model createFullModel() {
		Model model = createModel(NAME_TEST);
		List<ModelField> fields = new ArrayList<ModelField>();
		ModelField field1 = createModelField(model, "1", DataTypeEnum.TEXT);
		fields.add(field1);
		ModelField field2 = createModelField(model, "2", DataTypeEnum.INTEGER);
		fields.add(field2);
		model.setFields(fields);
		modelBO.insertModel(model);
		return model;
	}

}
