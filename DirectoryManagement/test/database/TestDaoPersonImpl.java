package database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.sql.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import beans.Group;
import beans.Person;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")
public class TestDaoPersonImpl {
	
	@Autowired
	DaoPerson dao;
	
//	@Test
//	public void a() {
//		Person person = new Person();
//		person.setName("testa");
//		person.setFirstname("test");
//		person.setMail("a@a.com");
//		person.setPassword("test");
//		person.setIdGroup(new Long(2));
//		person.setWebsite("a.fr");
//		person.setBirthdate(new Date());
//		person.setIdGroup(new Long(2));
//		dao.savePerson(person);
//		// HAHA ca marche ! dodo maintenant ^^
//	}
	
	@Test
	public void testFindAllGroup() {
		Collection<Group> c = dao.findAllGroups();
		Assert.assertEquals(5, c.size());
	}
	
	@Test
	public void testFindAllPerson() {
		Collection<Person> c = dao.findAllPersons(1);
		Assert.assertEquals(5, c.size());
	}
	
	@Test
	public void testFindPerson() {
		Person c = dao.findPerson(1);
		Assert.assertNotNull(c);
	}
	
	@Test
	public void testSavePerson() {
		Person person = new Person();
		person.setName("coucou2");
		person.setFirstname("test");
		person.setMail("a@a.com");
		person.setPassword("test");
		person.setIdGroup(new Long(2));
		person.setWebsite("a.fr");
		person.setBirthdate(new Date(100000000));
		dao.savePerson(person);
		Person person2 = dao.findPerson(person.getId());
		assertTrue(person+ " = " + person2, person.equals(person2));
		assertEquals(0, person.getBirthdate().compareTo(person2.getBirthdate()));
	}
}
