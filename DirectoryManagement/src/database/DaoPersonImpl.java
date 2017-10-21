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
	
	private final String INSERT_GROUP = "INSERT INTO GROUP (name) VALUES (?)";
	private final String UPDATE_GROUP = "UPDATE GROUP SET name = ? WHERE idGRP = ? ";

	private <T> Collection<T> findBeans(ToPrepareStatement BeanToPrep, ResultSetToBean<T> mapper)
			throws SQLException {
		Collection<T> array = new ArrayList<T>();
		try (Connection c = db.newConnection();
				PreparedStatement prep = BeanToPrep.createPrep(c);
				ResultSet rs = prep.executeQuery();) {
			array = new ArrayList<T>();
			while (rs.next()) {
				array.add(mapper.toBean(rs));
			}
			return array;
		}
	}
	
	private void updateBean(ToPrepareStatement BeanToPrep) {
		try (Connection c = db.newConnection();
				PreparedStatement prep = BeanToPrep.createPrep(c);
			){
			if (prep.executeUpdate() == 0)
				throw new DaoException();
			}catch (SQLException e) {
				throw new DaoException(e);
			}
	}
	
	private long insertBean(ToPrepareStatement BeanToPrep) {
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

	@Override
	public Collection<Group> findAllGroups() throws DaoException {
		String sql = "SELECT idGRP,name FROM GROUPS ORDER BY idGRP";
		ResultSetToBean<Group> toBean = (rs) -> {
			Group gr = new Group();
			gr.setId(rs.getLong("idGRP"));
			gr.setName(rs.getString("name"));
			return gr;
		};
		ToPrepareStatement p = (c) -> {
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
			System.out.println(rs.getDate("birthdate").getTime());
			prs.setBirthdate(rs.getDate("birthdate"));
			prs.setIdGroup(rs.getLong("idGRP"));
			return prs;
		};
		return toBean;
	}
	
	@Override
	public Collection<Person> findAllPersons(long groupId) throws DaoException {
		String sql = "SELECT idPER,name,firstname,mail,website,password,birthdate,idGRP "
				+ "FROM PERSON WHERE idGRP = ?";
		ToPrepareStatement p = (c) -> {
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setLong(1, groupId);
			return prep;
		};
		Collection<Person> pers = new ArrayList<Person>();
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
		ToPrepareStatement p = (c) -> {
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

	private ToPrepareStatement preparedStatementCreatePerson(Person p,String sql) {
		ToPrepareStatement pr = (c) -> {
			PreparedStatement prep = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			prep.setString (1, p.getName());
			prep.setString (2, p.getFirstname());
			prep.setString (3, p.getMail());
			prep.setString (4, p.getWebsite());
			System.out.println(p.getBirthdate().getTime());
			prep.setDate   (5, p.getBirthdate());
			prep.setString (6, p.getPassword());
			prep.setLong   (7, p.getIdGroup());
			return prep;
		};
		return pr;
	}
	
	private ToPrepareStatement preparedStatementUpdatePerson(Person p,String sql) {
		ToPrepareStatement pr = (c) -> {
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString (1, p.getName());
			prep.setString (2, p.getFirstname());
			prep.setString (3, p.getMail());
			prep.setString (4, p.getWebsite());
			prep.setDate   (5, p.getBirthdate());
			prep.setString (6, p.getPassword());
			prep.setLong   (7, p.getIdGroup());
			prep.setLong   (8, p.getId());
			return prep;
		};
		return pr;
	}
	
	private void createPerson(Person p) {
		long newId = insertBean(preparedStatementCreatePerson(p,INSERT_PERSON));
		p.setId(newId);
	}

	private void updatePerson(Person p) {
		updateBean(preparedStatementUpdatePerson(p,UPDATE_PERSON));
	}

	@Override
	public void savePerson(Person p) {
		if (p.getId() == null) {
			createPerson(p);
		} else {
			updatePerson(p);
		}
	}
	
	private void createGroup(Group g) {
		ToPrepareStatement pr = (c) -> {
			PreparedStatement prep = c.prepareStatement(INSERT_GROUP, Statement.RETURN_GENERATED_KEYS);
			prep.setString (1, g.getName());
			return prep;};
		long newId = insertBean(pr);
		g.setId(newId);
	}

	private void updateGroup(Group g) {
		ToPrepareStatement pr = (c) -> {
			PreparedStatement prep = c.prepareStatement(UPDATE_GROUP);
			prep.setString (1, g.getName());
			return prep;
		};
		updateBean(pr);
	}
	
	public void saveGroup(Group g) {
		if (g.getId() == null) {
			createGroup(g);
		} else {
			updateGroup(g);
		}
	}

}
