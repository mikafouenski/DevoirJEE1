package tomcatControleur;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import beans.Group;
import business.IDirectoryManager;
import business.User;
import business.exception.UserNotLoggedException;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import web.ControllerHelpers;
import web.group.GroupListController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")
public class TestGroupListController {
	
    @Tested
    GroupListController groupController;
    
    @Injectable
    IDirectoryManager manager;
    
    @Mocked
	HttpServletRequest request;
    
    /**
	 * Teste si l'utilateur authentifié veut regarder la liste des groupes
	 * Vérifie que la page renvoyée vaut group/listGroups
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
    @Test
    public void testListGroup() throws UserNotLoggedException {
		Collection<Group> groupsExpected = new ArrayList<Group>();
		Group g = new Group();
		g.setId(1L);
		g.setName("test");
		Group g2 = new Group();
		g2.setId(1L);
		g2.setName("test");
		groupsExpected.add(g);
		groupsExpected.add(g2);
		User user = new User();
		user.setAnonymous(false);
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user;
			manager.nbGroups(user); result = 3;
			manager.findGroups(user,anyInt, anyInt); result = groupsExpected; 
    	}};
    	ModelAndView actual = groupController.listGroup(0,7,request);
    	assertEquals("group/listGroups", actual.getViewName());
    }
    /**
	 * Teste si l'utilateur authentifié veut regarder la liste des groupes
	 * Vérifie que les groupes renvoyés correspondent aux groupes renvoyés par la base de données
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
    public void testListGroupReturnGroup() throws UserNotLoggedException {
		Collection<Group> groupsExpected = new ArrayList<Group>();
		Group g = new Group();
		g.setId(1L);
		g.setName("test");
		Group g2 = new Group();
		g2.setId(1L);
		g2.setName("test");
		groupsExpected.add(g);
		groupsExpected.add(g2);
		User user = new User();
		user.setAnonymous(false);
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user;
			manager.nbGroups(user); result = 3;
			manager.findGroups(user,anyInt, anyInt); result = groupsExpected; 
    	}};
    	ModelAndView actual = groupController.listGroup(0,7,request);
    	assertEquals(actual.getModelMap().get("groups"), groupsExpected);
    }
    
	/**
	 * Teste si l'utilateur non authentifié veut regarder la liste des groupes
	 * Vérifie que la page renvoyé vaut redirect:/login
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
    public void testListGroupUserBad() throws UserNotLoggedException {
		new Expectations() {{
		    request.getSession().getAttribute("user"); result = null;
		    manager.nbGroups((User)any); result = new UserNotLoggedException();
    	}};
    	ModelAndView actual = groupController.listGroup(0,7,request);
    	assertEquals(actual.getViewName(), "redirect:/login");
    }
   
	
	@Test
	public void testListGroupPaginationNotMultiple() throws UserNotLoggedException {
		Collection<Group> groupsExpected = new ArrayList<Group>();
		for (long i = 0; i < 100; i++)
			groupsExpected.add(new Group());
		User user = new User();
		user.setAnonymous(false);
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user;
			manager.nbGroups(user); result = 100;
			manager.findGroups(user, anyInt, anyInt); result = groupsExpected; 
    	}};
    	ModelAndView actual = groupController.listGroup(0, 7, request);
    	assertEquals(actual.getModelMap().get("nbPage"), 15);
	}
	
	@Test
	public void testListGroupPaginationMultiple() throws UserNotLoggedException {
		Collection<Group> groupsExpected = new ArrayList<Group>();
		for (long i = 0; i < 100; i++)
			groupsExpected.add(new Group());
		User user = new User();
		user.setAnonymous(false);
		new Expectations() {{
			request.getSession().getAttribute("user"); result = user;
			manager.nbGroups(user); result = 100;
			manager.findGroups(user, anyInt, anyInt); result = groupsExpected; 
    	}};
    	ModelAndView actual = groupController.listGroup(0, 10, request);
    	assertEquals(actual.getModelMap().get("nbPage"), 10);
	}

}
