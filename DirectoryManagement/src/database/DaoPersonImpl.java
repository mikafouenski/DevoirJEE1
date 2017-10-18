package database;

import java.sql.SQLException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group.Group;
import person.Person;

@Service
public class DaoPersonImpl implements DaoPerson {

	@Autowired
	IDatabase db;

	@Override
	public Collection<Group> findAllGroups() {
		System.out.println("coucou");
		try {
			db.newConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
