package tomcatControleur;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import beans.Person;
import business.IDirectoryManager;
import business.User;
import business.exception.UserNotLoggedException;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import web.GroupController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")
public class TestGroupController {
	
    @Tested
    GroupController groupController;
    
    @Injectable
    IDirectoryManager manager;

//    @Test
//    public void testlistGroup() throws UserNotLoggedException {
//    	User user = new User();
//    	user.setAnonymous(false);
//    	Person toto = new Person();
//    	Collection<Person> persons = new ArrayList<Person>();
//    	persons.add(toto);
//    	HttpServletRequest r;
//    	new Expectations() {{
//    		r.getSession().getAttribute("user"); result = user;
//    		manager.findPersons(user,1); result = persons;
//        	
//    	}};
//        ModelAndView result = groupController.listGroup(1);     
//        assertEquals( result.getView().toString(),"listPersons");
//        new Verifications() {{ manager.findPersons(user,1); times = 1; }};
//    }
    
    	
    
}
