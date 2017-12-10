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
	 * Teste l'édition d'un retourner est bien connection/connection 
	 *  si l'utilisateur n'est pas identifie
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
    	ModelAndView actual = groupEditController.editPersonDetail(1,request);
    	assertEquals("group/groupEdit", actual.getViewName());
	}
	
	@Test
	public void testEditPersonDetailReturnGroup() throws UserNotLoggedException {
		User user = new User();
		user.setAnonymous(false);
		Group g = new Group();
		
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user; 
			manager.findGroup(user, 1); result = g;
    	}};
    	ModelAndView actual = groupEditController.editPersonDetail(1,request);
    	assertEquals(g, actual.getModelMap().get("group"));
	}
	
	@Test
	public void testEditPersonDetailUserNotLog() throws UserNotLoggedException {
		User user = new User();
		user.setAnonymous(true);		
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user; 
			manager.findGroup(user, 1); result = new UserNotLoggedException();
    	}};
    	ModelAndView actual = groupEditController.editPersonDetail(1,request);
    	assertEquals("redirect:/login", actual.getViewName());
	}
	
	@Test
	public void testEditPersonForm( ) throws UserNotLoggedException {
		Group g = new Group();
		g.setId(1L);
		g.setName("nom");
		User user = new User();
		user.setAnonymous(false);
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user;
			manager.saveGroup(user, g);
    	}};
    	ModelAndView actual = groupEditController.editPersonForm(g,bindingresult,request);
    	assertEquals(actual.getViewName(),"redirect:/groups/list?id=1");
	}
	
	@Test
	public void testEditPersonFormError( ) throws UserNotLoggedException {
		Group g = new Group();
		g.setId(1L);
		g.setName("");
		User user = new User();
		user.setAnonymous(false);
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user;
			bindingresult.hasErrors(); result = true;
    	}};
    	ModelAndView actual = groupEditController.editPersonForm(g,bindingresult,request);
    	assertEquals(actual.getViewName(),"group/groupEdit");
	}
	
	@Test
	public void testEditPersonFormUser( ) throws UserNotLoggedException {
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
    	ModelAndView actual = groupEditController.editPersonForm(g,bindingresult,request);
    	assertEquals(actual.getViewName(),"redirect:/login");
	}
	
}
