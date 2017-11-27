package DBUnit;

import java.io.File;
import java.sql.Date;
import java.util.Collection;

import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import beans.Group;
import beans.Person;
import database.IDaoPerson;
import database.IDatabase;
import database.JDBC;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")
public class TestJDBCDBunit extends DBTestCase {

	@Autowired
	IDaoPerson daoPerson;

	static IDatabase jdbc = new JDBC();

	@BeforeClass
	public static void beforeClass() throws Exception {
		InteractDBU.extract(jdbc.getConnection(), "dbunit/back.xml");
	}

	@AfterClass
	public static void afterClass() throws Exception {
		InteractDBU.inject(jdbc.getConnection(), "dbunit/back.xml");
	}

	@Before
	public void before() throws Exception {
		InteractDBU.inject(jdbc.getConnection(), "dbunit/testinit.xml");
	}
	
	private void testDbunitAssert(String tableName, String filename) throws Exception {
		// DBunit
		InteractDBU.extract(jdbc.getConnection(), filename);
		IDataSet dataSet1 = getDataSet(filename);
		ITable table1 = dataSet1.getTable(tableName);

		IDataSet dataSet2 = getDataSet("dbunit/expected-" + filename);
		ITable table2 = dataSet2.getTable(tableName);
		ITable filteredTable = DefaultColumnFilter.includedColumnsTable(table1, table2.getTableMetaData().getColumns());
		Assertion.assertEquals(filteredTable, table2);

		// Clean up
		new File(filename).delete();
	}

	@Test
	public void insertPerson() throws Exception {
		String str = "insert-person.xml";
		
		Person person = new Person();
		person.setName("coucou2");
		person.setFirstname("test");
		person.setMail("a@a.com");
		person.setBirthdate(Date.valueOf("2015-03-31"));
		person.setPassword("test");
		person.setIdGroup(new Long(2));
		person.setWebsite("a.fr");
		daoPerson.savePerson(person);
		
		// DBUNIT
		testDbunitAssert("PERSON", str);
	}

	@Test
	public void updatePerson() throws Exception {
		String str = "update-person.xml";
		
		Person person = new Person();
		person.setName("coucou2");
		person.setFirstname("test");
		person.setMail("a@a.com");
		person.setBirthdate(Date.valueOf("2015-03-31"));
		person.setPassword("test");
		person.setIdGroup(new Long(2));
		person.setWebsite("a.fr");
		person.setId(1L);
		daoPerson.savePerson(person);

		// DBUNIT
		testDbunitAssert("PERSON", str);
	}
	
	@Test
	public void testFindAllPerson() {
		Collection<Person> c = daoPerson.findAllPersons(1);
		Assert.assertEquals(3, c.size());
	}
	
	@Test
	public void testFindPerson() {
		Person c = daoPerson.findPerson(1);
		Assert.assertNotNull(c);
	}
	
	@Test
	public void testFindAllGroup() {
		Collection<Group> c = daoPerson.findAllGroups();
		Assert.assertEquals(5, c.size());
	}
	
	@Test
	public void insertGroup() throws Exception {
		String str = "insert-group.xml";
		
		Group g = new Group();
		g.setName("Nouveau");
		daoPerson.saveGroup(g);

		// DBUNIT
		testDbunitAssert("GROUPS", str);
	}
	
	@Test
	public void updateGroup() throws Exception {
		String str = "update-group.xml";
		
		Group g = new Group();
		g.setName("Nouveau");
		g.setId(1L);
		daoPerson.saveGroup(g);

		// DBUNIT
		testDbunitAssert("GROUPS", str);
	}

	private IDataSet getDataSet(String str) throws Exception {
		return new FlatXmlDataSetBuilder().build(new File(str));
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return null;
	}
}