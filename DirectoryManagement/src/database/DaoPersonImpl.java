package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	private final String FIND_ALL_PERSON = "SELECT idPER,name,firstname,mail,website,password,birthdate,idGRP FROM PERSON";
	private final String FIND_ALL_GROUP = "SELECT idGRP,name FROM GROUPS";
	
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
	
	private void updateBean(ToPrepareStatement beanToPrep, ResultUpdate resultUpt) {
		try (Connection c = db.newConnection();
				PreparedStatement prep = beanToPrep.createPrep(c);
				ResultSet result = prep.executeQuery()
			){
			resultUpt.resultSetUpdate(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private long insertBean(ToPrepareStatement beanToPrep,ResultInsert resultInser) {
		long id = 0;
		try (Connection c = db.newConnection();
			 PreparedStatement prep = beanToPrep.createPrep(c);
			 ResultSet result = prep.executeQuery();
			){
			id = resultInser.resultSetInsert(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public Collection<Group> findAllGroups() throws DaoException {
		
		ResultSetToBean<Group> toBean = (rs) -> {
			Group gr = new Group();
			gr.setId(rs.getLong("idGRP"));
			gr.setName(rs.getString("name"));
			return gr;
		};
		ToPrepareStatement p = (c) -> {
			PreparedStatement prep = c.prepareStatement(FIND_ALL_GROUP);
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

	private ResultInsert resultInsertPerson(Person p) {
		ResultInsert resultInsert = (rs) -> {
			rs.moveToInsertRow();
			rs.updateString("name", p.getName());
			rs.updateString("FirstName", p.getFirstname());
			rs.updateString("mail", p.getMail());
			rs.updateString("website", p.getWebsite());
			rs.updateDate("birthdate", p.getBirthdate());
			rs.updateString("password", p.getPassword());
			rs.updateLong("idGRP", p.getIdGroup());
			rs.insertRow();
			rs.last();
			return rs.getLong("idPER");
		};
		return resultInsert;
	}
	
	private void createPerson(Person p) {
		ToPrepareStatement pr = (c) -> {
			PreparedStatement prep = c.prepareStatement(FIND_ALL_PERSON,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE );
			return prep;};
		long newId = insertBean(pr,resultInsertPerson(p));
		p.setId(newId);
	}

	
	private ResultUpdate resultUpdatePerson(Person p) {
		ResultUpdate ru = (rs) -> {
			rs.first();
			rs.updateString("name", p.getName());
			rs.updateString("FirstName", p.getFirstname());
			rs.updateString("mail", p.getMail());
			rs.updateString("website", p.getWebsite());
			rs.updateDate("birthdate", p.getBirthdate());
			rs.updateString("password", p.getPassword());
			rs.updateLong("idGRP", p.getIdGroup());
			rs.updateRow();
		};
		return ru;
	}
	
	private void updatePerson(Person p) {
		ToPrepareStatement pr = (c) -> {
			PreparedStatement prep = c.prepareStatement("SELECT idPER,name,firstname,mail,website,password,birthdate,idGRP"
					+ " FROM PERSON WHERE idPER = ?"
					,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			prep.setLong (1, p.getId());
			return prep;
		};
		updateBean(pr,resultUpdatePerson(p));
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
			PreparedStatement prep = c.prepareStatement(FIND_ALL_GROUP,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE );
			return prep;};
		ResultInsert rsi = (rs) -> {
			rs.moveToInsertRow();
			rs.updateString("name", g.getName());
			rs.insertRow();
			rs.last();
			return rs.getLong("idGRP");
		};
		long newId = insertBean(pr,rsi);
		g.setId(newId);
	}

	private void updateGroup(Group g) {
		ToPrepareStatement pr = (c) -> {
			PreparedStatement prep = c.prepareStatement("SELECT idGRP,name FROM GROUPS WHERE idGRP = ?"
					,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			prep.setLong (1, g.getId());
			return prep;
		};
		ResultUpdate Ru = (rs) -> {
			rs.first();
			rs.updateString("name", g.getName());
			rs.updateRow();
		};
		updateBean(pr,Ru);
	}
	
	public void saveGroup(Group g) {
		if (g.getId() == null) {
			createGroup(g);
		} else {
			updateGroup(g);
		}
	}

}
