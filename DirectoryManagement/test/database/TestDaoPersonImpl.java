package database;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")
public class TestDaoPersonImpl {
	
	@Autowired
	DaoPerson dao;
	
	@Test
	public void a() {
		dao.findAllGroups();
	}
}
