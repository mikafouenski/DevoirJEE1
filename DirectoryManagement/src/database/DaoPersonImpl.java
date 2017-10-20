package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.PreparedStatement;

import beans.Group;
import beans.Person;

@Service
public class DaoPersonImpl implements DaoPerson {

	@Autowired
	IDatabase db;
	
	public <T> Collection<T> findBeans(String sql, ResultSetToBean<T> mapper) throws SQLException {
		Collection<T> array = null;
		try (Connection c = db.newConnection();
			 Statement st = c.createStatement();
			 ResultSet rs = st.executeQuery(sql);) {
			array = new ArrayList<T>();
			while (rs.next()) {
				array.add(mapper.toBean(rs));
			}
			return array;
		}
	}
	
	@Override
	public Collection<Group> findAllGroups() {
		ResultSetToBean<Group> t = (rs) -> {
			Group gr = new Group(); 
			gr.setId(rs.getInt("idGRP")); 
			gr.setName(rs.getString("name"));
			return gr;
		};
		Collection<Group> c = null;
		try {
			c = findBeans("Select idGRP,name FROM GROUPS",t);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}

	@Override
	public Collection<Person> findAllPersons(long groupId) {
		ResultSetToBean<Person> t = (rs) -> {
			Person prs = new Person(); 
			prs.setId(rs.getInt("idPER")); 
			prs.setName(rs.getString("name"));
			prs.setFirstname(rs.getString("firstname"));
			prs.setMail(rs.getString("mail"));
			prs.setWebsite(rs.getString("website"));
			prs.setPassword(rs.getString("password"));
			prs.setBirthdate(rs.getDate("birthdate"));
			prs.setIdGroup(rs.getInt("idGRP"));
			return prs;
		};
		Collection c = null;
		try {
			c = findBeans("Select idPER,name,firstname,mail,website,password,birthdate,idGRP FROM PERSON WHERE idGRP = " + groupId,t);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
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
