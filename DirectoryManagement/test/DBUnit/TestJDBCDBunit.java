package DBUnit;

import java.util.Calendar;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import beans.Person;
import database.IDatabase;
import database.JDBC;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")
public class TestJDBCDBunit {

	
	static IDatabase jdbc = new JDBC();
	

	@BeforeClass
	public static void beforeClass() throws Exception {
		InteractDBU.extract(jdbc.newConnection(), "back.xml");
	}
	
	@AfterClass
	public static void afterClass() throws Exception {
		InteractDBU.inject(jdbc.newConnection(), "back.xml");
	}

	@Before
	public void before() throws Exception {
		InteractDBU.inject(jdbc.newConnection(), "testinit.xml");
	}
	
	@Test
	public void insertPersonn() throws Exception {
		Person person = new Person();
		Calendar calendar = new Calendar();
		calendar.set(2017, 12, 12);
		person.setName("coucou2");
		person.setFirstname("test");
		person.setMail("a@a.com");
		person.setBirthdate(new Calendar());
		person.setPassword("test");
		person.setIdGroup(new Long(2));
		person.setWebsite("a.fr");
	}
	
	
}