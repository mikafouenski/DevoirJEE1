 package tomcatControleur;

import static org.junit.Assert.assertEquals;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import business.IDirectoryManager;
import business.User;
import business.exception.PersonNotFoundException;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import validator.ValidatorConnection;
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
	
	/**
	 *  Teste si la page retourner est bien connection/connection 
	 *  si l'utilisateur n'est pas identifie
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void TestloginPrompt() {
		Connection co = new Connection();
		new Expectations() {{
			request.getSession().getAttribute("user");    	
    	}};
		String result = connectionController.loginPrompt(co, 
				request);
		
    	assertEquals("connection/connection", result);
	}
	
	/**
	 *  Teste si la page retourner est bien redirect:/groups/list 
	 *  si l'utilisateur est  identifie
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
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
	
	/**
	 *  Teste du login d'un utilisateur avec un bon mot de passe et un bon id
	 *  Regarde si la page retourné est bien redirect:/groups/list
	 *  Regarde aussi si l'utilisateur est bien enregistrer en session   
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test	
	public void Testlogin() throws PersonNotFoundException {
		Connection co = new Connection();
		co.setMail("email");
		co.setPassword("juste");
		new Expectations() {{
			validator.validate(co, (Errors) any);
			bindingresult.hasErrors(); result = false;
			manager.login((User)any,"email","juste"); result = true;
		}};
		String retour = connectionController.login(co, bindingresult, request);
		assertEquals(retour, "redirect:/groups/list");
		new Verifications() {{
			request.getSession().setAttribute("user", any); times = 1;
		}};
	}
	
	/**
	 *  Teste du login d'un utilisateur avec un id inconue
	 *  Regarde si la page retourné est bien connection/connection
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test	
	public void TestloginFail() throws PersonNotFoundException {
		Connection co = new Connection();
		co.setMail("email");
		co.setPassword("juste");
		new Expectations() {{
			validator.validate(co, (Errors) any);
			bindingresult.hasErrors(); result = false;
			manager.login((User)any,"email","juste"); result = new PersonNotFoundException();
		}};
		String retour = connectionController.login(co, bindingresult, request);
		assertEquals(retour, "connection/connection");
		
	}
	
	/**
	 *  Teste du login d'un utilisateur avec un mot de passe incorect
	 *  Regarde si la page retourné est bien connection/connection
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test	
	public void TestloginFalse() throws PersonNotFoundException {
		Connection co = new Connection();
		co.setMail("email");
		co.setPassword("juste");
		new Expectations() {{
			validator.validate(co, (Errors) any);
			bindingresult.hasErrors(); result = false;
			manager.login((User)any,"email","juste"); result = false;
		}};
		String retour = connectionController.login(co, bindingresult, request);
		assertEquals(retour, "connection/connection");
		
	}
	/**
	 *  Teste du login d'un utilisateur avec un echec au niveau de la vérification 
	 *  Regarde si la page retourné est bien connection/connection
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test	
	public void TestloginFailVerification() throws PersonNotFoundException {
		Connection co = new Connection();
		co.setMail("email");
		co.setPassword("");
		new Expectations() {{
			validator.validate(co, (Errors) any);
			bindingresult.hasErrors(); result = true;
		}};
		String retour = connectionController.login(co, bindingresult, request);
		assertEquals(retour, "connection/connection");
	}
	
	/**
	 *  Teste du logout d'un utilisateur
	 *  Regarde si il y a bien eu un appel à invalidate sur la session
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void TestLogout() {
		String retour = connectionController.logout(new Connection(),bindingresult, request);
		assertEquals("redirect:/login",retour);
		new Verifications() {{
			request.getSession().invalidate(); times = 1;
		}};
	}
}
