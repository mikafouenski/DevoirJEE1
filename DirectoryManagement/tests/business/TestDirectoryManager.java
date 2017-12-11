package business;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

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
import hachage.HachageSha3;
import business.exception.PersonNotFoundException;
import business.exception.UserNotLoggedException;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")
public class TestDirectoryManager {
    
    @Tested
    IDirectoryManager manager;
    
    @Injectable
    IDaoPerson dao;
    
    /**
	 *  Teste si la méthode new User renvoie un utilisateur 
	 *  non authentifié 
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
    @Test
    public void testNewUser() {
        User toto = manager.newUser();
        assertTrue(toto.isAnonymous());
    }
    
    /**
   	 *  Vérifie que la méthode findPerson appelle la méthode findPerson de la dao 
   	 *  @author Bernardini Mickael De Barros Sylvain
   	 */
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
    
    /**
   	 *  Vérifie que la méthode findGroup appelle la méthode findGroup de la dao 
   	 *  @author Bernardini Mickael De Barros Sylvain
   	 */
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
    
    /**
   	 *  Test si la méthode login authentifie bien l'user
   	 *  si le mot de passe et l'id sont juste 
   	 *  @author Bernardini Mickael De Barros Sylvain
   	 */
    @Test
    public void testLoginIsLog() throws PersonNotFoundException {
        new Expectations() {{
        	Person p = new Person();
        	p.setPassword(HachageSha3.digest("juste"));
        	p.setIdGroup(12L);
            dao.findPerson(1); result = p;
        }};
        User user = new User();
        manager.login(user, 1, "juste");
        assertFalse(user.isAnonymous());
        new Verifications() {{ dao.findPerson(1); times = 1; }};
    }
    
    /**
   	 *  Test si la méthode login met le bon idGroup dans user
   	 *  si le mot de passe et l'id sont juste 
   	 *  @author Bernardini Mickael De Barros Sylvain
   	 */
    @Test
    public void testLoginGroup() throws PersonNotFoundException {
        new Expectations() {{
        	Person p = new Person();
        	p.setPassword(HachageSha3.digest("juste"));
        	p.setIdGroup(12L);
            dao.findPerson(1); result = p;
        }};
        User user = new User();
        manager.login(user, 1, "juste");
        assertEquals(12L, user.getGroupId());
        new Verifications() {{ dao.findPerson(1); times = 1; }};
    }
    
    /**
   	 *  Vérifie que la méthode logout rend l'user anonyme
   	 *  @author Bernardini Mickael De Barros Sylvain
   	 */
    @Test
	public void testLogout() {
    	User test = new User();
    	test.setAnonymous(false);
    	manager.logout(test);
    	assertTrue(test.isAnonymous());
	}
    
    /**
   	 *  Vérifie que la méthode laisse l'user anonyme
   	 *  @author Bernardini Mickael De Barros Sylvain
   	 */
    @Test
  	public void testLogoutOnAnonymous() {
      	User test = new User();
      	test.setAnonymous(true);
      	manager.logout(test);
      	assertTrue(test.isAnonymous());
  	}
    
    /**
   	 *  Vérifie que la méthode SavePerson apelle la méthode savePerson de la dao
   	 *  avec la meme person
   	 *  @author Bernardini Mickael De Barros Sylvain
   	 */
    @Test
  	public void testSavePerson() throws UserNotLoggedException {
    	Person p = new Person();
    	new Expectations() {{
            dao.savePerson(p); 
        }};
    	User user = new User();
    	user.setAnonymous(false);
    	manager.savePerson(user, p);
    	new Verifications() {{  dao.savePerson(p); times = 1; }};
    }
    
    /**
   	 *  Vérifie que la méthode SaveGroup apelle la méthode saveGroup de la dao
   	 *  avec le meme groupe
   	 *  @author Bernardini Mickael De Barros Sylvain
   	 */
    @Test
  	public void testSaveGroup() throws UserNotLoggedException {
    	Group g = new Group();
    	new Expectations() {{
            dao.saveGroup(g); 
        }};
    	User user = new User();
    	user.setAnonymous(false);
    	manager.saveGroup(user, g);
    	new Verifications() {{  dao.saveGroup(g); times = 1; }};
    }
    
    /**
   	 *  Vérifie que la méthode login ne connecte pas l'utilsateur
   	 *  si sont mot de passse est éroné et renvoie bien false
   	 *  @author Bernardini Mickael De Barros Sylvain
   	 */
    @Test
    public void testLoginFail() throws PersonNotFoundException {
        new Expectations() {{
        	Person p = new Person();
        	p.setPassword("juste");
        	p.setIdGroup(12L);
            dao.findPerson(1); result = p;
        }};
        User user = new User();
        boolean isCo = manager.login(user, 1, "false");
        assertFalse(isCo || !user.isAnonymous());
        new Verifications() {{ dao.findPerson(1); times = 1; }};
    }
    /**
   	 *  Vérifie que la méthode login renvoie une execption si l'id est inconnue
   	 *  @author Bernardini Mickael De Barros Sylvain
   	 */
    @Test(expected = PersonNotFoundException.class)
    public void testLoginFailId() throws PersonNotFoundException {
        new Expectations() {{;
            dao.findPerson(1); result = null;
        }};
        User user = new User();
        manager.login(user, 1, "false");
        new Verifications() {{ dao.findPerson(1); times = 1; }};
    }
    
    
    /**
   	 *  Vérifie que la méthode renvoie une exeption si un User non connecté l'appelle
   	 *  @author Bernardini Mickael De Barros Sylvain
   	 */
    @Test(expected = UserNotLoggedException.class)
    public void testsavePersonFailled() throws UserNotLoggedException {
        manager.savePerson(new User(),new Person());
    }
   
    /**
   	 *  Vérifie que la méthode renvoie une exeption si un User non connecté l'appelle
   	 *  @author Bernardini Mickael De Barros Sylvain
   	 */
    @Test(expected = UserNotLoggedException.class)
    public void testFindGroupFailled() throws UserNotLoggedException {
        manager.findGroup(new User(),1);
    }
    
    /**
   	 *  Vérifie que la méthode renvoie une exeption si un User non connecté l'appelle
   	 *  @author Bernardini Mickael De Barros Sylvain
   	 */
    @Test(expected = UserNotLoggedException.class)
    public void testFindPersonFailled() throws UserNotLoggedException {
        manager.findPerson(new User(),1);
    }
    
    /**
   	 *  Vérifie que la méthode findGroups(0, 10) renvoie une collection de meme taille
   	 *  que celle renvoyé par la métode dao findGroups(0, 10)
   	 *  @author Bernardini Mickael De Barros Sylvain
   	 */
    @Test
	public void testFindGroupsPaginated() throws Exception {
    	User user = new User();
		user.setAnonymous(false);
		Collection<Group> groupsExpected = new ArrayList<Group>();
		for (long i = 0; i < 10; i++)
			groupsExpected.add(new Group());
		 new Expectations() {{;
	         dao.findGroups(0, 10); result = groupsExpected;
	     }};
	     Collection<Group> groups = manager.findGroups(user, 0, 10);
	     assertEquals(10, groups.size());
	}
    
    /**
   	 *  Vérifie que la méthode findPerson(0, 10) renvoie une collection de meme taille
   	 *  que celle renvoyé par la métode dao findPerson(0, 10)
   	 *  @author Bernardini Mickael De Barros Sylvain
   	 */
    @Test
	public void testFindPersonsPaginated() throws Exception {
    	User user = new User();
		user.setAnonymous(false);
		Collection<Person> personsExpected = new ArrayList<Person>();
		for (long i = 0; i < 10; i++)
			personsExpected.add(new Person());
		 new Expectations() {{;
	         dao.findPersons(1, 0, 10); result = personsExpected;
	     }};
	     Collection<Person> persons = manager.findPersons(user, 1, 0, 10);
	     assertEquals(10, persons.size());
	}
    
    /**
   	 *  Vérifie que la méthode findPersons(user, "test", "test") renvoie une collection de meme taille
   	 *  que celle renvoyé par la métode dao findPersons(user, "test", "test")
   	 *  @author Bernardini Mickael De Barros Sylvain
   	 */
    @Test
   	public void testFindPersonsSearch() throws Exception {
       	User user = new User();
   		user.setAnonymous(false);
   		Collection<Person> personsExpected = new ArrayList<Person>();
   		for (long i = 0; i < 10; i++)
   			personsExpected.add(new Person());
   		 new Expectations() {{;
   	         dao.findPersons("test", "test"); result = personsExpected;
   	     }};
   	     Collection<Person> persons = manager.findPersons(user, "test", "test");
   	     assertEquals(10, persons.size());
   	}
    
    /**
   	 *  Vérifie que la méthode findGroups("test") renvoie une collection de meme taille
   	 *  que celle renvoyé par la métode dao findGroups("test")
   	 *  @author Bernardini Mickael De Barros Sylvain
   	 */
    @Test
	public void testFindGroupsSearch() throws Exception {
    	User user = new User();
		user.setAnonymous(false);
		Collection<Group> groupsExpected = new ArrayList<Group>();
		for (long i = 0; i < 10; i++)
			groupsExpected.add(new Group());
		 new Expectations() {{;
	         dao.findGroups("test"); result = groupsExpected;
	     }};
	     Collection<Group> groups = manager.findGroups(user, "test");
	     assertEquals(10, groups.size());
	}
    
    /**
   	 *  Vérifie que la méthode nbGroups() renvoie le meme nombre
   	 *  que celle renvoyé par la métode dao getNbGroups() 
   	 *  @author Bernardini Mickael De Barros Sylvain
   	 */
    @Test
	public void testNbGroup() throws Exception {
    	User user = new User();
		user.setAnonymous(false);
		 new Expectations() {{;
		 	dao.getNbGroups(); result = 10;
	     }};
	     assertEquals(10, manager.nbGroups(user));
	}
    
    /**
   	 *  Vérifie que la méthode nbPersons(user,1) renvoie le meme nombre
   	 *  que celle renvoyé par la métode dao getNbPersons(1) 
   	 *  @author Bernardini Mickael De Barros Sylvain
   	 */
    @Test
	public void testNbPerson() throws Exception {
    	User user = new User();
		user.setAnonymous(false);
		 new Expectations() {{;
		 	dao.getNbPersons(1); result = 10;
	     }};
	     assertEquals(10, manager.nbPersons(user, 1));
	}
    
}
