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
import database.daoUtils.DaoUtils;
import database.daoUtils.DaoUtilsGroup;
import database.daoUtils.DaoUtilsPerson;

@Service
public class DaoPerson implements IDaoPerson {

	@Autowired
	IDatabase db;

	private <T> Collection<T> findBeans(DaoUtils<T> utils, T template, int start, int end) throws SQLException {
		Collection<T> array = new ArrayList<T>();
		try (Connection c = db.getConnection();
				PreparedStatement prep = utils.createTableViewList(c, template, start, end);
				ResultSet rs = prep.executeQuery();) {
			array = new ArrayList<T>();
			while (rs.next()) {
				array.add(utils.toBean(rs));
			}
			return array;
		}
	}
	
	private <T> Collection<T> findBeans(DaoUtils<T> utils, T template, String param1, String param2) throws SQLException {
		Collection<T> array = new ArrayList<T>();
		try (Connection c = db.getConnection();
				PreparedStatement prep = utils.createSearch(c,template,param1,param2) ;
				ResultSet rs = prep.executeQuery();) {
			array = new ArrayList<T>();
			while (rs.next()) {
				array.add(utils.toBean(rs));
			}
			return array;
		}
	}
	private <T> Collection<T> findBeans(DaoUtils<T> utils, T template) throws SQLException {
		Collection<T> array = new ArrayList<T>();
		try (Connection c = db.getConnection();
				PreparedStatement prep = utils.createTableViewList(c, template);
				ResultSet rs = prep.executeQuery();) {
			array = new ArrayList<T>();
			while (rs.next()) {
				array.add(utils.toBean(rs));
			}
			return array;
		}
	}
	
	private <T> T findBean(DaoUtils<T> utils, T template) throws SQLException {
		Collection<T> array = new ArrayList<T>();
		try (Connection c = db.getConnection();
				PreparedStatement prep = utils.createTableViewSingleton(c, template);
				ResultSet rs = prep.executeQuery();) {
			array = new ArrayList<T>();
			while (rs.next()) {
				array.add(utils.toBean(rs));
			}
			return array.iterator().next();
		}
	}

	private <T> void updateBean(DaoUtils<T> utils, T template) throws DaoException {
		try (Connection c = db.getConnection();
				PreparedStatement prep = utils.createTableViewSingleton(c, template);
				ResultSet result = prep.executeQuery()) {
			utils.resultSetUpdate(result, template);
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	private <T> long insertBean(DaoUtils<T> utils, T template) throws DaoException {
		long id = 0;
		try (Connection c = db.getConnection();
				PreparedStatement prep = utils.createTableViewList(c, template);
				ResultSet result = prep.executeQuery();) {
			id = utils.resultSetInsert(result, template);
		} catch (SQLException e) {
			throw new DaoException();
		}
		return id;
	}

	@Override
	public Collection<Group> findGroups() throws DaoException {
		Collection<Group> groups = null;
		try {
			groups = findBeans(new DaoUtilsGroup(), new Group());
		} catch (SQLException e) {
			throw new DaoException();
		}
		return groups;
	}
	
	@Override
	public Collection<Group> findGroups(String name) throws DaoException {
		Collection<Group> groups = null;
		try {
			groups = findBeans(new DaoUtilsGroup(), new Group(), name, new String());
		} catch (SQLException e) {
			throw new DaoException();
		}
		return groups;
	}
	
	@Override
	public Collection<Group> findGroups(int start, int end) throws DaoException {
		Collection<Group> groups = null;
		try {
			groups = findBeans(new DaoUtilsGroup(), new Group(), start, end);
		} catch (SQLException e) {
			throw new DaoException();
		}
		return groups;
	}

	@Override
	public Collection<Person> findPersons(long groupId) throws DaoException {
		Collection<Person> persons = null;
		try {
			Person p = new Person();
			p.setIdGroup(groupId);
			persons = findBeans(new DaoUtilsPerson(), p);
		} catch (SQLException e) {
			throw new DaoException();
		}
		return persons;
	}
	
	@Override
	public Collection<Person> findPersons(long groupId, int start, int end) throws DaoException {
		Collection<Person> persons = null;
		try {
			Person p = new Person();
			p.setIdGroup(groupId);
			persons = findBeans(new DaoUtilsPerson(), p, start, end);
		} catch (SQLException e) {
			throw new DaoException();
		}
		return persons;
	}
	
	@Override
	public Person findPerson(long id) throws DaoException {
		Person person = null;
		try {
			Person p = new Person();
			p.setId(id);
			person = findBean(new DaoUtilsPerson(), p);
		} catch (SQLException e) {
			throw new DaoException();
		}
		return person;
	}
	
	@Override
	public Group findGroup(long id) throws DaoException {
		Group group = null;
		try {
			Group g = new Group();
			g.setId(id);
			group = findBean(new DaoUtilsGroup(), g);
		} catch (SQLException e) {
			throw new DaoException();
		}
		return group;
	}
	
	

	@Override
	public void savePerson(Person p) throws DaoException {
		if (p.getId() == null) {
			long newId = insertBean(new DaoUtilsPerson(), p);
			p.setId(newId);
		} else {
			updateBean(new DaoUtilsPerson(), p);
		}
	}

	@Override
	public void saveGroup(Group g) throws DaoException {
		if (g.getId() == null) {
			long newId = insertBean(new DaoUtilsGroup(), g);
			g.setId(newId);
		} else {
			updateBean(new DaoUtilsGroup(), g);
		}
	}

	@Override
	public long getNbGroups() throws DaoException {
		DaoUtilsGroup utilsGroup = new DaoUtilsGroup();
		try (Connection c = db.getConnection()) {
			return utilsGroup.size(c, new Group());
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	@Override
	public long getNbPersons(long id) throws DaoException {
		DaoUtilsPerson utilsPerson = new DaoUtilsPerson();
		try (Connection c = db.getConnection()) {
			Person p = new Person();
			p.setIdGroup(id);
			return utilsPerson.size(c, p);
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

}
