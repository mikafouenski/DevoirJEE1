package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;

import group.Group;
import person.Person;

public class DaoPersonJDBC implements DaoPerson {
	
	private String url = "";
	private String id = "";
	private String password = "";
	
	// ---------------------------- JDBC ----------------------------------
	
	public Connection newConnection() throws SQLException {
		Connection c = DriverManager.getConnection(url, id, password);
		return c;
	}

	public void quietClose(Connection c) {
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// ---------------------------- JDBC ----------------------------------

	@Override
	public Collection<Group> findAllGroups() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Person> findAllPersons(long groupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Person findPerson(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void savePerson(Person p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveGroup(Group g) {
		// TODO Auto-generated method stub

	}

}
