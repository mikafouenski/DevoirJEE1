package tomcatControleur;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import beans.Group;
import beans.Person;
import business.IDirectoryManager;
import business.User;
import business.exception.UserNotLoggedException;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import web.person.PersonEditController;
import web.person.PersonListController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")
public class TestPersonListController {
	
	@Tested
	PersonListController personListController;
	
	@Injectable
	IDirectoryManager manager;
	
	@Mocked
	HttpServletRequest request;
	
	@Mocked
	BindingResult bindingresult;
	
	/**
	 * Teste si l'utilateur authentifié vient consulter la liste des personnes
	 * Vérifie que la page renvoyée vaut person/listPersons
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testlistPersons() throws UserNotLoggedException {
		User user = new User();
		user.setAnonymous(false);
		Collection<Person> personsExpected = new ArrayList<Person>();
		Person p = new Person();
		p.setId(1L);
		p.setName("test");
		Person p2 = new Person();
		p2.setId(1L);
		p2.setName("test");
		personsExpected.add(p);
		personsExpected.add(p2);
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user; 
			manager.findPersons(user, 1,anyInt,anyInt); result = personsExpected;
    	}};
    	ModelAndView actual = personListController.listPersons(1,0,7,request);
    	assertEquals(actual.getViewName(), "person/listPersons");
	}
	
	/**
	 * Teste si l'utilateur authentifié vient consulter la liste des personnes
	 * Vérifie que la liste de personne renvoyéé correspond a la liste renvoyée par la base de donnée
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testlistPersonsReturn() throws UserNotLoggedException {
		User user = new User();
		user.setAnonymous(false);
		Collection<Person> personsExpected = new ArrayList<Person>();
		Person p = new Person();
		p.setId(1L);
		p.setName("test");
		Person p2 = new Person();
		p2.setId(1L);
		p2.setName("test");
		personsExpected.add(p);
		personsExpected.add(p2);
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user; 
			manager.findPersons(user, 1,anyInt,anyInt); result = personsExpected;
    	}};
    	ModelAndView actual = personListController.listPersons(1,0,7,request);
    	assertEquals(actual.getModelMap().get("persons"),personsExpected);
	}
	
	/**
	 * Teste si l'utilateur non authentifié vient consulter la liste des personnes
	 * Vérifie que la page renvoyée vaut redirect:/login
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testlistPersonsNotAuthentified() throws UserNotLoggedException {
		User user = new User();
		user.setAnonymous(true);
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user; 
			manager.findPersons(user, 1,anyInt,anyInt); result = new UserNotLoggedException();
    	}};
    	ModelAndView actual = personListController.listPersons(1,0,7,request);
    	assertEquals(actual.getViewName(), "redirect:/login");
	}
	
	@Test
	public void testListPersonPaginationNotMultiple() throws UserNotLoggedException {
		User user = new User();
		user.setAnonymous(false);
		Collection<Person> personsExpected = new ArrayList<Person>();
		for (long i = 0; i < 100; i++)
			personsExpected.add(new Person());
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user;
			manager.nbPersons(user, 1); result = 100;
			manager.findPersons(user, 1, anyInt, anyInt); result = personsExpected;
    	}};
    	ModelAndView actual = personListController.listPersons(1, 0, 7, request);
    	assertEquals(actual.getModelMap().get("nbPage"), 15);
	}
	
	@Test
	public void testListGroupPaginationMultiple() throws UserNotLoggedException {
		User user = new User();
		user.setAnonymous(false);
		Collection<Person> personsExpected = new ArrayList<Person>();
		for (long i = 0; i < 100; i++)
			personsExpected.add(new Person());
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user;
			manager.nbPersons(user, 1); result = 100;
			manager.findPersons(user, 1, anyInt, anyInt); result = personsExpected;
    	}};
    	ModelAndView actual = personListController.listPersons(1, 0, 10, request);
    	assertEquals(actual.getModelMap().get("nbPage"), 10);
	}
}
