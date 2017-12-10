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
	HttpServletResponse response;
	
	@Mocked
	BindingResult bindingresult;
	
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
    	ModelAndView actual = personListController.listPersons(1,0,7,request,response);
    	assertEquals(actual.getViewName(), "person/listPersons");
	}
	
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
    	ModelAndView actual = personListController.listPersons(1,0,7,request,response);
    	assertEquals(actual.getModelMap().get("persons"),personsExpected);
	}
	
	@Test
	public void testlistPersonsNotAuthentified() throws UserNotLoggedException {
		User user = new User();
		user.setAnonymous(true);
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user; 
			manager.findPersons(user, 1,anyInt,anyInt); result = new UserNotLoggedException();
    	}};
    	ModelAndView actual = personListController.listPersons(1,0,7,request,response);
    	assertEquals(actual.getViewName(), "redirect:/login");
	}
	
}
