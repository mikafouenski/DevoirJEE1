package database;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")
public class TestJDBC {

	@Autowired
	IDatabase jdbc;
	
	/**
	 * Teste si la recupération de connection ne renvoie pas un object null
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void testNewDefaultConnection() throws SQLException {
		Connection c = jdbc.getConnection();
		assertNotNull(c);
	}

	/**
	 * Teste la possibilité de multiple connection 
	 * pour vérifier que le serveur gère le multithreading
	 *  @author Bernardini Mickael De Barros Sylvain
	 */
	@Test
	public void TestMultiConnection() throws InterruptedException {
		long debut = System.currentTimeMillis();
		Runnable work = () -> {
			try (Connection c = jdbc.getConnection()) {
				Thread.sleep(5000);
			} catch (Exception e) {
			}
		};
		ExecutorService exec = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10; i++) {
			exec.execute(work);
		}
		exec.shutdown();
		exec.awaitTermination(10, TimeUnit.HOURS);
		long fin = System.currentTimeMillis();
		long val = (fin - debut);
		assertTrue("val = " + val, val <= 16000 && val >= 10000);
	}

}
