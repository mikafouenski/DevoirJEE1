package tomcatControleur;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
import validator.ValidatorConnection;
import web.SearchControleur;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")
public class TestSearchController {

	
	@Tested
	SearchControleur searchController;
	
	@Injectable
	IDirectoryManager manager;
	
	@Injectable
	ValidatorConnection validator;
	
	@Mocked
	HttpServletRequest request;
	
	/**
	 * Teste si l'utilateur authentifié vient consulter la page de recherche 
	 * Vérifie que la page renvoyée est listGroupSearch
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testSearchGroupView() throws UserNotLoggedException {
		Group g = new Group();
		g.setId(1L);
		g.setName("test");
		User user = new User();
		user.setAnonymous(false);
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user; 
			request.getParameter("name"); result = "test";
			manager.findGroups(user,"test"); result = g;
    	}};
    	ModelAndView resultat = searchController.searchGroup(request);
    	
    	assertEquals(resultat.getViewName(), "listGroupSearch");
	 }
	
	/**
	 * Teste si l'utilateur authentifié vient consulter la page de recherche 
	 * Vérifie que la liste de groupe renvoyée correspond a celle renvoyée par la base de donnée
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testSearchGroupModel() throws UserNotLoggedException {
		Collection<Group> groupsExpected = new ArrayList<Group>();
		Group g = new Group();
		g.setId(1L);
		g.setName("test");
		groupsExpected.add(g);
		User user = new User();
		user.setAnonymous(false);
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user; 
			request.getParameter("name"); result = "test";
			manager.findGroups(user,"test"); result = groupsExpected;
    	}};
    	ModelAndView resultat = searchController.searchGroup(request);
    	assertEquals(resultat.getModelMap().get("groups"), groupsExpected);
	 }
	/**
	 * Teste si l'utilateur non authentifié vient consulter la page de recherche 
	 * Vérifie que la page renvoyée vaut redirect:/login
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testSearchGroupUserFail() throws UserNotLoggedException {
		new Expectations() {{
			request.getSession().getAttribute("user"); result = null; 
			request.getParameter("name"); result = "test";
			manager.findGroups((User)any,"test"); result = new UserNotLoggedException();
    	}};
    	ModelAndView resultat = searchController.searchGroup(request);
    	assertEquals(resultat.getViewName(),"redirect:/login");
	 }
	
	/**
	 * Teste si l'utilateur authentifié vient consulter la page de recherche 
	 * Vérifie que la page renvoyée est listPersonSearch
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testSearchPersonView() throws UserNotLoggedException {
		Collection<Person> personsExpected = new ArrayList<Person>();
		Person p = new Person();
		p.setId(1L);
		p.setName("test");
		p.setFirstname("test");
		Person p2 = new Person();
		p2.setId(1L);
		p2.setName("testa");
		p2.setFirstname("testa");
		personsExpected.add(p);
		User user = new User();
		user.setAnonymous(false);
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user; 
			request.getParameter("name"); result = "test";
			request.getParameter("firstname"); result = "test";
			manager.findPersons(user,"test","test"); result = personsExpected;
    	}};
    	ModelAndView resultat = searchController.searchPerson(request);
    	
    	assertEquals(resultat.getViewName(), "listPersonSearch");
	 }
	
	/**
	 * Teste si l'utilateur authentifié vient consulter la page de recherche 
	 * Vérifie que la liste de personne renvoyée correspond a celle renvoyée par la base de donnée
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testSearchPersonModel() throws UserNotLoggedException {
		Collection<Person> personsExpected = new ArrayList<Person>();
		Person p = new Person();
		p.setId(1L);
		p.setName("test");
		p.setFirstname("test");
		Person p2 = new Person();
		p2.setId(1L);
		p2.setName("testa");
		p2.setFirstname("testa");
		personsExpected.add(p);
		User user = new User();
		user.setAnonymous(false);
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user; 
			request.getParameter("name"); result = "test";
			request.getParameter("firstname"); result = "test";
			manager.findPersons(user,"test","test"); result = personsExpected;
    	}};
    	ModelAndView resultat = searchController.searchPerson(request);	
    	assertEquals(resultat.getModelMap().get("persons"), personsExpected);
	 }
	
	/**
	 * Teste si l'utilateur non authentifié vient consulter la page de recherche 
	 * Vérifie que la page renvoyée vaut redirect:/login
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testSearchPersonUserFail() throws UserNotLoggedException {
		new Expectations() {{
			request.getSession().getAttribute("user"); result = null; 
			request.getParameter("name"); result = "test";
			request.getParameter("firstname"); result = "test";
			manager.findPersons((User)any,"test","test"); result = new UserNotLoggedException();
    	}};
    	ModelAndView resultat = searchController.searchPerson(request);
    	assertEquals(resultat.getViewName(),"redirect:/login");
	}
}
