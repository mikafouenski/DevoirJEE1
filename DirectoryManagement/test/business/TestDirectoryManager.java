package business;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import beans.Group;
import beans.Person;
import business.IDirectoryManager;
import business.User;
import database.IDaoPerson;
import business.exception.UserNotLoggedException;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "spring/spring.xml")
public class TestDirectoryManager {
    
    @Autowired
    @Tested
    IDirectoryManager manager;
    
    @Injectable
    IDaoPerson dao;
    
    @Test
    public void testNewUser() {
        User toto = manager.newUser();
        assertTrue(toto.isAnonymous());
    }
    
    @Test
    public void testFindPerson() throws UserNotLoggedException {
        new Expectations() {{
            dao.findPerson(1); result = new Person();
        }};
        User login = new User();
        login.setAnonymous(false);
        manager.findPerson(login, 1);
        new Verifications() {{ dao.findPerson(1); times = 1; }};
    }
    
    @Test
    public void testFindGroup() throws UserNotLoggedException {
        new Expectations() {{
            dao.findGroup(1); result = new Group();
        }};
        User login = new User();
        login.setAnonymous(false);
        manager.findGroup(login, 1);
        new Verifications() {{ dao.findGroup(1); times = 1; }};
    }
    
    @Test(expected = UserNotLoggedException.class)
    public void testFindGroupFailled() throws UserNotLoggedException {
        manager.findGroup(new User(),1);
    }
    
}
