package validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.Errors;

import mockit.Mocked;
import mockit.Tested;
import web.connection.Connection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")
public class TestValidatorConnection {
	
	@Tested
	ValidatorConnection validate;
	
	@Mocked
	Errors errors;
	
	@Test
	public void testValidate() {
		Connection co = new Connection();
		co.setId(0);
		co.setPassword("juste");
		validate.validate(co, errors);
		assertFalse(errors.hasErrors());
	}
	
	@Test
	public void testValidateFail() {
		Connection co = new Connection();
		co.setId(0);
		co.setPassword("");
		validate.validate(co, errors);
		assertTrue(errors.hasErrors());
	}
	
	
	
}
