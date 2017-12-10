package tomcatControleur;

import static org.junit.Assert.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import beans.Person;
import business.IDirectoryManager;
import business.User;
import business.exception.UserNotLoggedException;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import web.person.PersonEditController;
import web.person.PersonFormBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")
public class TestPersonEditController {

	@Tested
	PersonEditController personEditController;
	
	@Injectable
	IDirectoryManager manager;
	
	@Mocked
	HttpServletRequest request;
	
	@Mocked
	HttpServletResponse response;
	
	@Mocked
	BindingResult bindingresult;
	
	/**
	 * Teste si l'utilateur authentifié vient d'éditer une personne
	 * Vérifie que la page renvoyée vaut person/personDetail
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testEditPersonForm() throws UserNotLoggedException {
		User user = new User();
		user.setAnonymous(false);
		PersonFormBean person = new PersonFormBean();
		person.setId("1");
		person.setName("testa");
		person.setFirstname("test");
		person.setWebsite("site");
		person.setMail("mail");
		Person p = new Person();
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user; 
			manager.findPerson(user, 1); result = p;
			manager.savePerson(user, p);
    	}};
    	ModelAndView actual = personEditController.editPersonForm(person,bindingresult,request);
		assertEquals(actual.getViewName(),"person/personDetail");
	}
	
	private boolean PersonequalsPersonForm(Person p,PersonFormBean pf) {
		return p.getId().toString().equals(pf.getId()) 
			&& p.getName().equals(pf.getName())
			&& p.getFirstname().equals(pf.getFirstname())
			&& p.getMail().equals(pf.getMail())
			&& p.getWebsite().equals(pf.getWebsite());
	}
	
	/**
	 * Teste si l'utilateur authentifié vient d' éditer une personne
	 * Vérifie que la personne renvoyée correspond a bien eu les modifications
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testEditPersonFormReturn() throws UserNotLoggedException {
		User user = new User();
		user.setAnonymous(false);
		PersonFormBean person = new PersonFormBean();
		person.setId("1");
		person.setName("testa");
		person.setFirstname("test");
		person.setWebsite("site");
		person.setMail("mail");
		Person p = new Person();
		p.setId(1L);
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user; 
			manager.findPerson(user, 1); result = p;
			manager.savePerson(user, p);
    	}};
    	ModelAndView actual = personEditController.editPersonForm(person,bindingresult,request);
		assertTrue(PersonequalsPersonForm((Person)actual.getModelMap().get("person"),person));
	}
	
	/**
	 * Teste si l'utilateur non authentifié veut éditer une personne
	 * Vérifie que la page renvoyée vaut redirect:/login
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testEditPersonFormUserNotLog() throws UserNotLoggedException {
		User user = new User();
		user.setAnonymous(true);
		PersonFormBean person = new PersonFormBean();
		person.setId("1");
		person.setName("testa");
		person.setFirstname("test");
		person.setWebsite("site");
		person.setMail("mail");
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user; 
			manager.findPerson(user, 1); result = new UserNotLoggedException();
    	}};
    	ModelAndView actual = personEditController.editPersonForm(person,bindingresult,request);
    	assertEquals(actual.getViewName(),"redirect:/login");
	}
	
	/**
	 * Teste si l'utilateur authentifié veut éditer une personne
	 * Vérifie que la page renvoyée vaut person/personDetail
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testPersonDetail() throws UserNotLoggedException {
		User user = new User();
		user.setAnonymous(false);
		Person p = new Person();
		p.setId(1L);
		p.setName("testa");
		p.setFirstname("test");
		p.setWebsite("site");
		p.setMail("mail");
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user; 
			manager.findPerson((User) any, 1); result = p;
    	}};
		ModelAndView actual = personEditController.editPersonDetail(1, request);
    	assertEquals(actual.getViewName(), "person/personEdit");
	}
	
	/**
	 * Teste si l'utilateur non authentifié veut éditer une personne
	 * Vérifie que la page renvoyée vaut redirect:/login
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testPersonDetailNotLogged() throws UserNotLoggedException {
		User user = new User();
		user.setAnonymous(true);
		Person p = new Person();
		p.setId(1L);
		p.setName("testa");
		p.setFirstname("test");
		p.setWebsite("site");
		p.setMail("mail");
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user; 
			manager.findPerson((User) any, 1); result = new UserNotLoggedException();
    	}};
		ModelAndView actual = personEditController.editPersonDetail(1, request);
    	assertEquals(actual.getViewName(), "redirect:/login");
	}
	
}
