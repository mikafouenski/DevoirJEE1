package DBUnit;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import database.IDatabase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")
public class TestJDBCDBunit {

	@Autowired
	IDatabase jdbc;
	
//	@BeforeClass
//	public static void beforeClass() throws Exception {
//		InteractDBU.extract(jdbc.newConnection(), "back.xml");
//	}
//	
//	@AfterClass
//	public static void afterClass() throws Exception {
//		InteractDBU.inject(jdbc.newConnection(), "back.xml");
//	}
//	
//	@Before
//	public void before() throws Exception {
//		InteractDBU.inject(jdbc.newConnection(), "testinit.xml");
//	}
	
	@Test
	public void a() throws Exception {
	}
	
	
}