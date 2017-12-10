package tomcatControleur;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import business.IDirectoryManager;
import mockit.Injectable;
import mockit.Tested;
import web.group.GroupListController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")
public class TestGroupController {
	
    @Tested
    GroupListController groupController;
    
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
