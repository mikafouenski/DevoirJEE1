package tomcatControleur;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;

import business.IDirectoryManager;
import business.User;
import business.exception.PersonNotFoundException;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import validor.ValidatorConnection;
import web.connection.Connection;
import web.connection.ConnectionController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")
public class TestConnectionController {
	
	@Tested
	ConnectionController connectionController;
	
	@Injectable
	IDirectoryManager manager;
	
	@Injectable
	ValidatorConnection validator;
	
	@Mocked
	HttpServletRequest request;
	
	@Mocked
	HttpServletResponse response;
		
	@Mocked
	BindingResult bindingresult;
	
	@Test
	public void TestloginPrompt() {
		Connection co = new Connection();
		new Expectations() {{
			request.getSession().getAttribute("user");    	
    	}};
		String result = connectionController.loginPrompt(co, 
				request);
		
    	assertEquals("connection", result);
	}
	
	@Test
	public void TestloginPromptUserCO() {
		Connection co = new Connection();
		User user = new User();
		user.setAnonymous(false);
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user;    	
    	}};
    	String result = connectionController.loginPrompt(co, 
				request);
    	assertEquals("redirect:/groups/list", result);
	}
	
	@Test	
	public void Testlogin() throws PersonNotFoundException {
		Connection co = new Connection();
		co.setId(1);
		co.setPassword("juste");
		new Expectations() {{
			validator.validate(co, (Errors) any);
			bindingresult.hasErrors(); result = false;
			manager.login((User)any,1,"juste"); result = true;
		}};
		String retour = connectionController.login(co, bindingresult, request);
		assertEquals(retour, "redirect:/groups/list");
		new Verifications() {{
			request.getSession().setAttribute("user", any); times = 1;
		}};
	}
	
	@Test	
	public void TestloginFail() throws PersonNotFoundException {
		Connection co = new Connection();
		co.setId(1);
		co.setPassword("juste");
		new Expectations() {{
			validator.validate(co, (Errors) any);
			bindingresult.hasErrors(); result = false;
			manager.login((User)any,1,"juste"); result = new PersonNotFoundException();
		}};
		String retour = connectionController.login(co, bindingresult, request);
		assertEquals(retour, "connection");
		
	}
	
	@Test	
	public void TestloginFalse() throws PersonNotFoundException {
		Connection co = new Connection();
		co.setId(1);
		co.setPassword("juste");
		new Expectations() {{
			validator.validate(co, (Errors) any);
			bindingresult.hasErrors(); result = false;
			manager.login((User)any,1,"juste"); result = false;
		}};
		String retour = connectionController.login(co, bindingresult, request);
		assertEquals(retour, "connection");
		
	}
	
	@Test	
	public void TestloginFailVerification() throws PersonNotFoundException {
		Connection co = new Connection();
		co.setId(1);
		co.setPassword("juste");
		new Expectations() {{
			validator.validate(co, (Errors) any);
			bindingresult.hasErrors(); result = true;
		}};
		String retour = connectionController.login(co, bindingresult, request);
		assertEquals(retour, "connection");
		
	}
	
	@Test
	public void TestLogout() {
		String retour = connectionController.logout(new Connection(),bindingresult, request);
		assertEquals("redirect:/login",retour);
		new Verifications() {{
			request.getSession().invalidate(); times = 1;
		}};
	}
}
