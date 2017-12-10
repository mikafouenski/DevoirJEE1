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
	
	/**
	 *  Crée une sauvegarde de la base de production
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@BeforeClass
	public static void beforeClass() throws Exception {
		InteractDBU.extract(jdbc.getConnection(), "dbunit/back.xml");
	}
	
	/**
	 *  Remet la base de production
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@AfterClass
	public static void afterClass() throws Exception {
		InteractDBU.inject(jdbc.getConnection(), "dbunit/back.xml");
	}
	
	/**
	 *  Injecte la base de donnée test entre chaque test
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
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
	
	/**
	 *  Verification si l'insertion d'une personne marche
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
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

	/**
	 *  Verification si la mise a jour d'une personne marche
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
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
	

	/**
	 *  Verification si la recherche de toutes les personnes renvoie le bon nombre de person
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testFindAllPerson() {
		Collection<Person> c = daoPerson.findPersons(1);
		Assert.assertEquals(3, c.size());
	}
	
	/**
	 *  Verification si la recherche d'un nombre de personne présise renvoie le bon nombre de person
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testFindPersonsPaginated() throws Exception {
		Collection<Person> c = daoPerson.findPersons(1, 0, 2);
		Assert.assertEquals(2, c.size());
	}
	
	/**
	 *  Verification si la recherche d'un nombre de personne présise
	 *   avec des paramètre éroné renvoie une exeption DaoException
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test(expected = DaoException.class)
	public void testFindPersonsPaginatedFailConnection() throws SQLException {
		daoPerson.findPersons(-1, -1, 1);
	}
	
	/**
	 *  Verification si la recherche de personne avec un nom et un prénom 
	 *  commençant par les données en paramètre renvoie le bon nombre de résultat  
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testFindAllPersonNameFirstname() {
		Collection<Person> c = daoPerson.findPersons("Wi","Can");
		Assert.assertEquals(1, c.size());
	}
	
	/**
	 *  Verification si la recherche d'un personne existante
	 *  renvoie bien une personne
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test 
	public void testFindPerson() {
		Person c = daoPerson.findPerson(1);
		Assert.assertNotNull(c);
	}
	
	/**
	 *  Verification si la recherche d'un groupe existant
	 *  renvoie bien un groupe
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test 
	public void testFindGroup() {
		Group c = daoPerson.findGroup(1);
		Assert.assertNotNull(c);
	}
	
	/**
	 *  Verification si la recherche de tout les groupes existant
	 *  renvoie bien le bon nombre
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testFindAllGroup() {
		Collection<Group> c = daoPerson.findGroups();
		Assert.assertEquals(5, c.size());
	}
	
	/**
	 *  Verification si la recherche d'un nombre voulu de groupes
	 *  renvoie bien le bon nombre
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testFindGroupsPaginated() throws Exception {
		Collection<Group> c = daoPerson.findGroups(0, 2);
		Assert.assertEquals(2, c.size());
	}
	
	/**
	 *  Verification si la recherche de groupe avec un nom
	 *  commençant par les données en paramètre renvoie le bon nombre de résultat  
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testFindAllGroupName() { 
		Collection<Group> c = daoPerson.findGroups("Magna") ;
		Assert.assertEquals( 1, c.size());
	}
	
	/**
	 *  Verification si l'insertion d'un groupe fonctionne
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void insertGroup() throws Exception {
		String str = "insert-group.xml";
		
		Group g = new Group();
		g.setName("Nouveau");
		daoPerson.saveGroup(g);

		// DBUNIT
		testDbunitAssert("GROUPS", str);
	}
	
	/**
	 *  Verification si la mise à jour d'un groupe fonctionne
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
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
	
	/**
	 *  Verification si le comptage du nombre de personne 
	 *  dans la base de donnée renvoie le bon résultat
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testNbPersons() throws Exception {
		long i = daoPerson.getNbPersons(1);
		Assert.assertEquals(3, i);
	}
	
	/**
	 *  Verification si le comptage du nombre de groupe 
	 *  dans la base de donnée renvoie le bon résultat
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
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