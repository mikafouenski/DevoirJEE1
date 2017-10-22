package database;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
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
	
	/*
	 *  PERSON
	 */
	
	@Test
	public void testFindAllPerson() {
		Collection<Person> c = dao.findAllPersons(1);
		Assert.assertEquals(3, c.size());
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
		person.setBirthdate(new Date(Calendar.getInstance().getTime().getTime()));
		dao.savePerson(person);
		Person person2 = dao.findPerson(person.getId());
		assertTrue(person.equals(person2));
	}
	
	@Test
	public void testUpdatePerson() {
		Person p = new Person();
		p.setId(new Long(2));
		java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		p.setBirthdate(sqlDate);
		p.setMail("Mimidu13@hotmail.fr");
		p.setPassword("azerty");
		p.setWebsite("skyblogMimi.fr");
		p.setFirstname("Mickah");
		p.setName("Bearnordinie");
		p.setIdGroup(new Long(1));
		dao.savePerson(p);
		boolean isPresent = false;
		Collection<Person> c = dao.findAllPersons(p.getIdGroup());
		for (Iterator<Person> iterator = c.iterator(); iterator.hasNext();) {
			Person person = (Person) iterator.next();
			if(person.equals(p))
				isPresent = true;
		}
		assertTrue(isPresent);
	}
	
	/*
	 * GROUP 
	 */
	
	@Test
	public void testFindAllGroup() {
		Collection<Group> c = dao.findAllGroups();
		Assert.assertEquals(5, c.size());
	}
	
	@Test
	public void testUpdateGroup() {
		Group g = new Group(new Long(1), "Oui j'ai tuer ton nommmmmmm", null);
		dao.saveGroup(g);
		Group g2 = dao.findGroup(g.getId());
		assertTrue(g.equals(g2));
	}
	
	@Test
	public void testInsertGroup() {
		Group g = new Group();
		g.setName("Nouveau");
		dao.saveGroup(g);
		Collection<Group> c = dao.findAllGroups();
		boolean isPresent = false;
		for (Iterator<Group> iterator = c.iterator(); iterator.hasNext();) {
			Group group = iterator.next();
			if(group.equals(g))
				isPresent = true;
			
		}
		assertTrue(isPresent);
	}
	
	
}
