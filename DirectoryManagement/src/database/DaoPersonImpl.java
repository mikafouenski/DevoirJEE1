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
	
	private final String INSERT_PERSON = "INSERT INTO PERSON (name, firstname, mail, website, birthdate, password, idGRP) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private final String UPDATE_PERSON = "UPDATE PERSON SET name = ?, firstname = ?, mail = ?, website = ?, birthdate = ?, password = ?, idGRP = ? WHERE idPER = ? ";

	private <T> Collection<T> findBeans(BeantoPrepareStatement BeanToPrep, ResultSetToBean<T> mapper)
			throws SQLException {
		Collection<T> array = null;
		try (Connection c = db.newConnection();
				PreparedStatement prep = BeanToPrep.createPrep(c);
				Statement st = c.createStatement();
				ResultSet rs = prep.executeQuery();) {
			array = new ArrayList<T>();
			while (rs.next()) {
				array.add(mapper.toBean(rs));
			}
			return array;
		}
	}

	private java.sql.Date toSqlDate(java.util.Date date) {
		return (date != null) ? new java.sql.Date(date.getTime()) : null;
	}

	@Override
	public Collection<Group> findAllGroups() throws DaoException {
		String sql = "SELECT idGRP,name FROM GROUPS ORDER BY idGRP";
		ResultSetToBean<Group> toBean = (rs) -> {
			Group gr = new Group();
			gr.setId(rs.getInt("idGRP"));
			gr.setName(rs.getString("name"));
			return gr;
		};
		BeantoPrepareStatement p = (c) -> {
			PreparedStatement prep = c.prepareStatement(sql);
			return prep;
		};
		Collection<Group> groups = null;
		try {
			groups = findBeans(p, toBean);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return groups;
	}

	private ResultSetToBean<Person> personToResult() {
		ResultSetToBean<Person> toBean = (rs) -> {
			Person prs = new Person();
			prs.setId(rs.getLong("idPER"));
			prs.setName(rs.getString("name"));
			prs.setFirstname(rs.getString("firstname"));
			prs.setMail(rs.getString("mail"));
			prs.setWebsite(rs.getString("website"));
			prs.setPassword(rs.getString("password"));
			prs.setBirthdate(rs.getDate("birthdate"));
			prs.setIdGroup(rs.getInt("idGRP"));
			return prs;
		};
		return toBean;
	}
	
	@Override
	public Collection<Person> findAllPersons(long groupId) throws DaoException {
		String sql = "SELECT idPER,name,firstname,mail,website,password,birthdate,idGRP "
				+ "FROM PERSON WHERE idGRP = ?";
		BeantoPrepareStatement p = (c) -> {
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setLong(1, groupId);
			return prep;
		};
		Collection<Person> pers = null;
		try {
			pers = findBeans(p,personToResult());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pers;
	}

	@Override
	public Person findPerson(long id) throws DaoException {
		String sql = "SELECT idPER,name,firstname,mail,website,password,birthdate,idGRP "
				+ "FROM PERSON WHERE idPER = ?";
		BeantoPrepareStatement p = (c) -> {
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setLong(1, id);
			return prep;
		};
		Collection<Person> pers = null;
		try {
			pers = findBeans(p, personToResult());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pers.iterator().next();
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

	private long insertBean(BeantoPrepareStatement BeanToPrep) {
		long id = 0;
		try (Connection c = db.newConnection();
				PreparedStatement prep = BeanToPrep.createPrep(c);
			){
			if (prep.executeUpdate() == 0)
				throw new DaoException();
			ResultSet generatedKeys = prep.getGeneratedKeys();
			if (generatedKeys.next()) {
				id = generatedKeys.getLong(1);
			}
			else {
				generatedKeys.close();
				throw new DaoException();
			}
			generatedKeys.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	
	
	
	private void createPerson(Person p) {
		Object[] values = { p.getName(), p.getFirstname(), p.getMail(), p.getWebsite(), toSqlDate(p.getBirthdate()),
				p.getPassword(), p.getIdGroup() };
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
				p.getPassword(), p.getIdGroup(), p.getId() };
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
