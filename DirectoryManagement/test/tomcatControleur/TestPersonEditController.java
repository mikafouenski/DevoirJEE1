package tomcatControleur;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    	assertEquals(actual.getViewName(),"person/personEdit");
	}
	
}
