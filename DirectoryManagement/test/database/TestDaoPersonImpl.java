package database;

import java.util.Collection;

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
	public void tesFindAllGroup() {
		Collection<Group> c = dao.findAllGroups();
		Assert.assertEquals(5, c.size());
	}
	
	@Test
	public void tesFindAllPerson() {
		Collection<Group> c = dao.findAllPersons();
		Assert.assertEquals(5, c.size());
	}
}
