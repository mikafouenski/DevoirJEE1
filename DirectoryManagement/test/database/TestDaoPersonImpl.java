package database;

import java.util.Collection;
import java.util.Date;

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
	
	@Test
	public void a() {
		Person person = new Person();
		person.setName("test");
		person.setFirstname("test");
		person.setMail("a@a.com");
		person.setPassword("test");
		person.setIdGroup(2);
		person.setWebsite("a.fr");
		person.setBirthdate(new Date());
		dao.savePerson(person);
		// HAHA ca marche ! dodo maintenant ^^
	}
	
	@Test
	public void tesFindAllGroup() {
		Collection<Group> c = dao.findAllGroups();
		Assert.assertEquals(5, c.size());
	}
	
	@Test
	public void tesFindAllPerson() {
		Collection<Person> c = dao.findAllPersons(1);
		Assert.assertEquals(5, c.size());
	}
}
