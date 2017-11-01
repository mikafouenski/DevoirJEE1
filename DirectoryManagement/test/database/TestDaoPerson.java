package database;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Collection;
import java.sql.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import beans.Group;
import beans.Person;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")
public class TestDaoPerson {

	@Autowired
	IDaoPerson dao;
	
	/*
	 * INIT DATABASE
	 */
	
	@Before
	public void initDatabase() {
		new JDBC(true);
	}

	/*
	 * PERSON
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
		person.setBirthdate(new Date(10000000000000L));
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
		assertTrue(dao.findPerson(p.getId()).equals(p));
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
		assertTrue(dao.findGroup(g.getId()).equals(g));
	}

}
