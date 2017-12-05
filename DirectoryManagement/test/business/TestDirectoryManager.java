package business;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import beans.Person;
import business.IDirectoryManager;
import business.User;
import business.exception.UserNotLoggedException;
import database.IDaoPerson;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")
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
	
}
