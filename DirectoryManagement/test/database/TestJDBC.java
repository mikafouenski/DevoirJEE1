package database;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
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
	
	@Test
	public void TestMultiConnection() throws InterruptedException {
		Runnable work = () -> {
			try(Connection c = jdbc.newConnection()) {
				Thread.sleep(5000);
			} catch (Exception e) {
			}
		};
		ExecutorService exec = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 5; i++) {
			exec.execute(work);
		}
		exec.shutdown();
		exec.awaitTermination(5, TimeUnit.HOURS);
	}
	
	@Test
	public void TestMultiConnectionFail() throws InterruptedException {
		long debut = System.currentTimeMillis();
		Runnable work = () -> {
			try(Connection c = jdbc.newConnection()) {
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
		assertTrue(val <= 12000 && val >= 10000);
	}
	
}
