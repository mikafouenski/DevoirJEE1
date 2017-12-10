package tomcatControleur;

import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import beans.Group;
import business.IDirectoryManager;
import business.User;
import business.exception.UserNotLoggedException;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import web.group.GroupEditController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")
public class TestGroupEditController {
	
	@Tested
	GroupEditController groupEditController;
	
	@Injectable
	IDirectoryManager manager;
	
	@Mocked
	HttpServletRequest request;
	
	@Mocked
	HttpServletResponse response;
	
	@Mocked
	BindingResult bindingresult;
	
	/**
	 * Teste si l'utilateur authentifié veut édider un group
	 * Vérifie que la page renvoyée vaut group/groupEdit
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testEditGroupDetail() throws UserNotLoggedException {
		User user = new User();
		user.setAnonymous(false);
		Group g = new Group();
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user; 
			manager.findGroup(user, 1); result = g;
    	}};
    	ModelAndView actual = groupEditController.editGroupDetail(1,request);
    	assertEquals("group/groupEdit", actual.getViewName());
	}
	
	/**
	 * Teste si l'utilateur authentifié veut édider un group
	 * Vérifie que le groupe renvoyé dans la page est celui renvoyé par la base de donnée
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testEditGroupDetailReturnGroup() throws UserNotLoggedException {
		User user = new User();
		user.setAnonymous(false);
		Group g = new Group();
		
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user; 
			manager.findGroup(user, 1); result = g;
    	}};
    	ModelAndView actual = groupEditController.editGroupDetail(1,request);
    	assertEquals(g, actual.getModelMap().get("group"));
	}
	
	/**
	 * Teste si l'utilateur non authentifié veut édité un groupe
	 * Vérifie que la page renvoyée est bien redirect:/login
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testEditGroupDetailUserNotLog() throws UserNotLoggedException {
		User user = new User();
		user.setAnonymous(true);		
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user; 
			manager.findGroup(user, 1); result = new UserNotLoggedException();
    	}};
    	ModelAndView actual = groupEditController.editGroupDetail(1,request);
    	assertEquals("redirect:/login", actual.getViewName());
	}
	
	/**
	 * Teste si l'utilateur authentifié renvoie un formulaire d'édition de groupe correct
	 * Vérifie que la page renvoyée est bien redirect:/groups/list?id=1
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testEditGroupForm( ) throws UserNotLoggedException {
		Group g = new Group();
		g.setId(1L);
		g.setName("nom");
		User user = new User();
		user.setAnonymous(false);
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user;
			manager.saveGroup(user, g);
    	}};
    	ModelAndView actual = groupEditController.editGroupForm(g,bindingresult,request);
    	assertEquals(actual.getViewName(),"redirect:/groups/list?id=1");
	}
	
	/**
	 * Teste si l'utilateur authentifié renvoie un formulaire d'édition faux
	 * Vérifie que la page renvoyée est bien group/groupEdit
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testEditGroupFormError( ) throws UserNotLoggedException {
		Group g = new Group();
		g.setId(1L);
		g.setName("");
		User user = new User();
		user.setAnonymous(false);
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user;
			bindingresult.hasErrors(); result = true;
    	}};
    	ModelAndView actual = groupEditController.editGroupForm(g,bindingresult,request);
    	assertEquals(actual.getViewName(),"group/groupEdit");
	}
	
	/**
	 * Teste si l'utilateur non authentifié renvoie un formulaire d'édition
	 * Vérifie que la page renvoyée est bien redirect:/login
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testEditGroupFormUser( ) throws UserNotLoggedException {
		Group g = new Group();
		g.setId(1L);
		g.setName("");
		User user = new User();
		user.setAnonymous(true);
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user;
			bindingresult.hasErrors(); result = false;
			manager.saveGroup(user, g); result = new UserNotLoggedException();
    	}};
    	ModelAndView actual = groupEditController.editGroupForm(g,bindingresult,request);
    	assertEquals(actual.getViewName(),"redirect:/login");
	}
	
}
