package database.daoUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.Group;
import beans.Person;

public class DaoUtilsPerson implements DaoUtils<Person> {

	private final String FIND_PERSON_BY_ID = "SELECT idPER,name,firstname,mail,website,password,birthdate,idGRP FROM PERSON WHERE idPER = ?";
	private final String LIST_PERSONS_BY_GROUP_ID_FULL = "SELECT idPER,name,firstname,mail,website,password,birthdate,idGRP FROM PERSON WHERE idGRP = ?";
	private final String LIST_PERSONS_BY_GROUP_ID_RECORD = "SELECT idPER,name,firstname,mail,website,password,birthdate,idGRP FROM PERSON WHERE idGRP = ? LIMIT ? , ?";
	private final String COUNT_PERSONS_BY_GROUP_ID = "SELECT COUNT(idPER) as nb FROM PERSON WHERE idGRP = ?";

	@Override
	public Long resultSetInsert(ResultSet rs, Person p) throws SQLException {
		rs.moveToInsertRow();
		rs.updateString("name", p.getName());
		rs.updateString("firstname", p.getFirstname());
		rs.updateString("mail", p.getMail());
		rs.updateString("website", p.getWebsite());
		rs.updateDate("birthdate", p.getBirthdate());
		rs.updateString("password", p.getPassword());
		rs.updateLong("idGRP", p.getIdGroup());
		rs.insertRow();
		rs.last();
		return rs.getLong("idPER");
	}

	@Override
	public Person toBean(ResultSet rs) throws SQLException {
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
	}

	@Override
	public void resultSetUpdate(ResultSet rs, Person p) throws SQLException {
		rs.first();
		rs.updateString("name", p.getName());
		rs.updateString("firstname", p.getFirstname());
		rs.updateString("mail", p.getMail());
		rs.updateString("website", p.getWebsite());
		rs.updateDate("birthdate", p.getBirthdate());
		rs.updateString("password", p.getPassword());
		rs.updateLong("idGRP", p.getIdGroup());
		rs.updateRow();
	}
	
	@Override
	public long size(Connection c, Person p) throws SQLException {
		try (PreparedStatement prep = c.prepareStatement(COUNT_PERSONS_BY_GROUP_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);) {
			ResultSet rs = prep.executeQuery();
			prep.setLong(1, p.getIdGroup());
			rs.last();
			return rs.getLong("nb");
		}
	}

	@Override
	public PreparedStatement createTableViewList(Connection c, Person p) throws SQLException {
		PreparedStatement prep = c.prepareStatement(LIST_PERSONS_BY_GROUP_ID_FULL, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		prep.setLong(1, p.getIdGroup());
		return prep;
	}

	@Override
	public PreparedStatement createTableViewList(Connection c, Person p, int start, int end) throws SQLException {
		PreparedStatement prep = c.prepareStatement(LIST_PERSONS_BY_GROUP_ID_RECORD, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		prep.setLong(1, p.getIdGroup());
		prep.setInt(2, start);
		prep.setInt(3, end);
		return prep;
	}

	@Override
	public PreparedStatement createTableViewSingleton(Connection c, Person p) throws SQLException {
		PreparedStatement prep = c.prepareStatement(FIND_PERSON_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		prep.setLong(1, p.getId());
		return prep;
	}

	@Override
	public PreparedStatement createSearch(Connection c, Person p, String param1,String param2) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
