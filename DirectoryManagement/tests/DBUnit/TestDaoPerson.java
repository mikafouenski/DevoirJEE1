package DBUnit;

import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
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
import database.DaoException;
import database.IDaoPerson;
import database.IDatabase;
import database.JDBC;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")
public class TestDaoPerson extends DBTestCase {

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
		Collection<Person> c = daoPerson.findPersons(1);
		Assert.assertEquals(3, c.size());
	}
	
	@Test
	public void testFindPersonsPaginated() throws Exception {
		Collection<Person> c = daoPerson.findPersons(1, 0, 2);
		Assert.assertEquals(2, c.size());
	}
	
	@Test(expected = DaoException.class)
	public void testFindPersonsPaginatedFailConnection() throws SQLException {
		daoPerson.findPersons(-1, -1, 1);
	}
	
	@Test
	public void testFindAllPersonNameFirstname() {
		Collection<Person> c = daoPerson.findPersons("Wi","Can");
		Assert.assertEquals(1, c.size());
	}
	
	@Test 
	public void testFindPerson() {
		Person c = daoPerson.findPerson(1);
		Assert.assertNotNull(c);
	}
	
	@Test 
	public void testFindGroup() {
		Group c = daoPerson.findGroup(1);
		Assert.assertNotNull(c);
	}
	
	@Test
	public void testFindAllGroup() {
		Collection<Group> c = daoPerson.findGroups();
		Assert.assertEquals(5, c.size());
	}
	
	@Test
	public void testFindGroupsPaginated() throws Exception {
		Collection<Group> c = daoPerson.findGroups(0, 2);
		Assert.assertEquals(2, c.size());
	}
	
	@Test
	public void testFindAllGroupName() { 
		Collection<Group> c = daoPerson.findGroups("Magna") ;
		Assert.assertEquals( 1, c.size());
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
	
	@Test
	public void testNbPersons() throws Exception {
		long i = daoPerson.getNbPersons(1);
		Assert.assertEquals(3, i);
	}
	
	@Test
	public void testNbGroups() throws Exception {
		long i = daoPerson.getNbGroups();
		Assert.assertEquals(5, i);
	}

	private IDataSet getDataSet(String str) throws Exception {
		return new FlatXmlDataSetBuilder().build(new File(str));
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return null;
	}
	
}