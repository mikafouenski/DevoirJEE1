package tomcatControleur;

import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import beans.Person;
import business.IDirectoryManager;
import business.User;
import business.exception.UserNotLoggedException;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import web.person.PersonDetailController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")
public class TestPersonDetailController {
	
	@Tested
	PersonDetailController personDetailController;
	
	@Injectable
	IDirectoryManager manager;
	
	@Mocked
	HttpServletRequest request;
	
	@Mocked
	HttpServletResponse response;
	
	/**
	 * Teste si l'utilateur authentifié veut regarder le détail d'une personne
	 * Vérifie que la page renvoyé vaut person/personDetail
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testDetailPerson() throws UserNotLoggedException {
		User user = new User();
		user.setAnonymous(false);
		Person person = new Person();
		person.setId(1L);
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user; 
			manager.findPerson(user, 1); result = person;
    	}};
    	ModelAndView actual = personDetailController.detailPerson(1,request,response);
    	assertEquals("person/personDetail", actual.getViewName());
	}
	
	/**
	 * Teste si l'utilateur authentifié veut regarder le détail d'une personne
	 * Vérifie que la personne voulu correspond à la personne renvoyée par la base de donnée
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testDetailPersonResult() throws UserNotLoggedException {
		User user = new User();
		user.setAnonymous(false);
		Person person = new Person();
		person.setId(1L);
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user; 
			manager.findPerson(user, 1); result = person;
    	}};
    	ModelAndView actual = personDetailController.detailPerson(1,request,response);
    	assertEquals(person, actual.getModelMap().get("person"));
	}
	
	/**
	 * Teste si l'utilateur non authentifié veut regarder le détail d'une personne
	 * Vérifie que la page renvoyée vaut redirect:/login
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testDetailPersonNotLogged() throws UserNotLoggedException {
		User user = new User();
		user.setAnonymous(true);
		Person person = new Person();
		person.setId(1L);
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user; 
			manager.findPerson(user, 1); result = new UserNotLoggedException();
    	}};
    	ModelAndView actual = personDetailController.detailPerson(1,request,response);
    	assertEquals("redirect:/login", actual.getViewName());
	}
	
}
