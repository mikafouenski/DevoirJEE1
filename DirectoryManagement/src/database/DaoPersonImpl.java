package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beans.Group;
import beans.Person;

@Service
public class DaoPersonImpl implements DaoPerson {

	@Autowired
	IDatabase db;

	private final String FIND_PERSON_BY_ID = "SELECT * FROM PERSON WHERE idPER = ?";
	private final String LIST_PERSON_FROM_GROUP_ID = "SELECT * FROM PERSON WHERE idGRP = ?";
	private final String LIST_GROUP_ORDER_BY_ID = "SELECT * FROM GROUPS ORDER BY idGRP";

	private final String INSERT_PERSON = "INSERT INTO PERSON (name, firstname, mail, website, birthdate, password, idGRP) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private final String UPDATE_PERSON = "UPDATE PERSON SET name = ?, firstname = ?, mail = ?, website = ?, birthdate = ?, password = ?, idGRP = ? WHERE idPER = ? ";

	private Person mapPerson(ResultSet resultSet) throws SQLException {
		Person person = new Person();
		person.setId(resultSet.getLong("idPER"));
		person.setName(resultSet.getString("name"));
		person.setFirstname(resultSet.getString("firstname"));
		person.setMail(resultSet.getString("mail"));
		person.setWebsite(resultSet.getString("website"));
		person.setBirthdate(resultSet.getDate("birthdate"));
		person.setPassword(resultSet.getString("password"));
		person.setIdGRP(resultSet.getInt("idGRP"));
		return person;
	}

	private Group mapGroup(ResultSet resultSet) throws SQLException {
		Group group = new Group();
		group.setId(resultSet.getInt("idGRP"));
		group.setName(resultSet.getString("name"));
		group.setPersons(findAllPersons(group.getId()));
		return group;
	}

	private java.sql.Date toSqlDate(java.util.Date date) {
		return (date != null) ? new java.sql.Date(date.getTime()) : null;
	}

	@Override
	public Collection<Group> findAllGroups() throws DaoException {
		Collection<Group> groups = new ArrayList<Group>();
		try (Connection c = db.newConnection();
				PreparedStatement preparedStatement = c.prepareStatement(LIST_GROUP_ORDER_BY_ID)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				groups.add(mapGroup(resultSet));
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return groups;
	}

	@Override
	public Collection<Person> findAllPersons(long groupId) throws DaoException {
		Collection<Person> persons = new ArrayList<Person>();
		try (Connection c = db.newConnection();
				PreparedStatement preparedStatement = c.prepareStatement(LIST_PERSON_FROM_GROUP_ID)) {
			preparedStatement.setLong(1, groupId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				persons.add(mapPerson(resultSet));
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return persons;
	}

	@Override
	public Person findPerson(long id) throws DaoException {
		Person person = null;
		try (Connection c = db.newConnection();
				PreparedStatement preparedStatement = c.prepareStatement(FIND_PERSON_BY_ID)) {
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				person = mapPerson(resultSet);
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return person;
	}

	private PreparedStatement preparedStatementWithValues(Connection c, String query, Object[] values)
			throws SQLException {
		PreparedStatement preparedStatement = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		int i = 0;
		for (Object object : values) {
			preparedStatement.setObject(i + 1, object);
			i++;
		}
		return preparedStatement;
	}

	private void createPerson(Person p) {
		Object[] values = { p.getName(), p.getFirstname(), p.getMail(), p.getWebsite(), toSqlDate(p.getBirthdate()),
				p.getPassword(), p.getIdGRP() };
		try (Connection c = db.newConnection();
				PreparedStatement preparedStatement = preparedStatementWithValues(c, INSERT_PERSON, values)) {
			if (preparedStatement.executeUpdate() == 0)
				throw new DaoException();
			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				p.setId(generatedKeys.getLong(1));
			} else {
				throw new DaoException();
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	private void updatePerson(Person p) {
		Object[] values = { p.getName(), p.getFirstname(), p.getMail(), p.getWebsite(), toSqlDate(p.getBirthdate()),
				p.getPassword(), p.getIdGRP(), p.getId() };
		try (Connection c = db.newConnection();
				PreparedStatement preparedStatement = preparedStatementWithValues(c, UPDATE_PERSON, values)) {
			if (preparedStatement.executeUpdate() == 0)
				throw new DaoException();
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public void savePerson(Person p) {
		if (p.getId() == null) {
			createPerson(p);
		} else {
			updatePerson(p);
		}
	}

	@Override
	public void saveGroup(Group g) {
		// TODO Auto-generated method stub
	}

}
