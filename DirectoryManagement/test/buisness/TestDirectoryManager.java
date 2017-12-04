package buisness;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import business.IDirectoryManager;
import business.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")
public class TestDirectoryManager {
	
	@Autowired
	IDirectoryManager manager;
	
	@Test
	public void testNewUser() {
		User toto = manager.newUser();
		assertTrue(toto.isAnonymous());
	}
	
	@Test
	public void test() {
		User toto = manager.newUser();
		assertTrue(toto.isAnonymous());
	}
	
}
