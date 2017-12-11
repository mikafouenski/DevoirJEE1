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

	/**
	 * Recherche Toute les instances du Bean T entre la borne start et end en base de données, couche d'abstraction
	 * @param utils L'utilitaire du Bean T
	 * @param template De type T (le bean)
	 * @param start indice de la première colonne selectionné de recherche 
	 * @param range la taille du record
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Une Collection contenant les T trouvées 
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
	private <T> Collection<T> findBeans(DaoUtils<T> utils, T template, int start, int range) throws SQLException {
		Collection<T> array = new ArrayList<T>();
		try (Connection c = db.getConnection();
				PreparedStatement prep = utils.createTableViewList(c, template, start, range);
				ResultSet rs = prep.executeQuery();) {
			array = new ArrayList<T>();
			while (rs.next()) {
				array.add(utils.toBean(rs));
			}
			return array;
		}
	}
	
	/**
	 * Recherche Toute les instances du Bean T avec des paramètres en base de données, couche d'abstraction
	 * @param utils L'utilitaire du Bean T
	 * @param template De type T (le bean)
	 * @param param1 Paramètre de recherche 
	 * @param param2 Paramètre de recherche 
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Une Collection contenant les T trouvées 
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
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
	
	/**
	 * Recherche Toute les instances du Bean T en base de données, couche d'abstraction
	 * @param utils L'utilitaire du Bean T
	 * @param template De type T (le bean)
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Une Collection contenant les T trouvées
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
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
	
	/**
	 * Recherche Le Bean T en base de données, couche d'abstraction
	 * @param utils L'utilitaire du Bean T
	 * @param template De type T (le bean)
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Une Collection contenant les T trouvées
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
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

	/**
	 * Met a jour Le Bean T en base de données, couche d'abstraction
	 * @param utils L'utilitaire du Bean T
	 * @param template De type T (le bean)
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
	private <T> void updateBean(DaoUtils<T> utils, T template) throws DaoException {
		try (Connection c = db.getConnection();
				PreparedStatement prep = utils.createTableViewSingleton(c, template);
				ResultSet result = prep.executeQuery()) {
			utils.resultSetUpdate(result, template);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	/**
	 * Insere Le Bean T en base de données, couche d'abstraction
	 * @param utils L'utilitaire du Bean T
	 * @param template De type T (le bean)
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
	private <T> long insertBean(DaoUtils<T> utils, T template) throws DaoException {
		long id = 0;
		try (Connection c = db.getConnection();
				PreparedStatement prep = utils.createTableViewList(c, template);
				ResultSet result = prep.executeQuery();) {
			id = utils.resultSetInsert(result, template);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return id;
	}

	/**
	 * Recherche Toute les instances de Groupe en base de données 
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Une Collection contenant les groupes trouvés 
	 * @exception DaoException si la requete n'a pas fonctionée
	 */
	@Override
	public Collection<Group> findGroups() throws DaoException {
		Collection<Group> groups = null;
		try {
			groups = findBeans(new DaoUtilsGroup(), new Group());
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return groups;
	}
	
	/**
	 * Recherche les Groupe dont le nom commence par "..." en base de données 
	 * @param name Nom pouur la recherche
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Une Collection contenant les groupes trouvés 
	 * @exception DaoException si la requete n'a pas fonctionée
	 */
	@Override
	public Collection<Group> findGroups(String name) throws DaoException {
		Collection<Group> groups = null;
		try {
			groups = findBeans(new DaoUtilsGroup(), new Group(), name, new String());
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return groups;
	}
	
	/**
	 * Recherche Toute les instances de Groupe entre la borne start et end en base de données 
	 * @param start indice de la première colonne selectionné de recherche 
	 * @param range la taille du record
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Une Collection contenant les groupes trouvés 
	 * @exception DaoException si la requete n'a pas fonctionée
	 */
	@Override
	public Collection<Group> findGroups(int start, int range) throws DaoException {
		Collection<Group> groups = null;
		try {
			groups = findBeans(new DaoUtilsGroup(), new Group(), start, range);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return groups;
	}

	/**
	 * Recherche Toute les instances de personnes du groupe "..." en base de données 
	 * @param id L'identifiant du groupe
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Une Collection contenant les personnes trouvées 
	 * @exception DaoException si la requete n'a pas fonctionée
	 */
	@Override
	public Collection<Person> findPersons(long groupId) throws DaoException {
		Collection<Person> persons = null;
		try {
			Person p = new Person();
			p.setIdGroup(groupId);
			persons = findBeans(new DaoUtilsPerson(), p);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return persons;
	}
	
	/**
	 * Recherche Toute les instances de personnes entre la borne start et end en base de données 
	 * @param start indice de la première colonne selectionné de recherche 
	 * @param range la taille du record
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Une Collection contenant les personnes trouvées 
	 * @exception DaoException si la requete n'a pas fonctionée
	 */
	@Override
	public Collection<Person> findPersons(long groupId, int start, int range) throws DaoException {
		Collection<Person> persons = null;
		try {
			Person p = new Person();
			p.setIdGroup(groupId);
			persons = findBeans(new DaoUtilsPerson(), p, start, range);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return persons;
	}
	
	/**
	 * Recherche les personnes dont le nom ou prenom commence par "..." en base de données 
	 * @param name Nom pouur la recherche
	 * @param firstname Pernom pouur la recherche
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Une Collection contenant les personnes trouvées 
	 * @exception DaoException si la requete n'a pas fonctionée
	 */
	@Override
	public Collection<Person> findPersons(String name,String firstname ) throws DaoException {
		Collection<Person> persons = null;
		try {
			persons = findBeans(new DaoUtilsPerson(), new Person(), name, firstname);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return persons;
	}
	
	/**
	 * Recherche une personne en base de données 
	 * @param id L'identifiant de la personne
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Le groupe trouvé 
	 * @exception DaoException si la requete n'a pas fonctionée
	 */
	@Override
	public Person findPerson(long id) throws DaoException {
		Person person = null;
		try {
			Person p = new Person();
			p.setId(id);
			person = findBean(new DaoUtilsPerson(), p);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return person;
	}
	
	public Person findPerson(String email) throws DaoException {
		Person person = null;
		try {
			Person p = new Person();
			p.setId(-1L);
			p.setMail(email);
			person = findBean(new DaoUtilsPerson(), p);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return person;
	}
	
	/**
	 * Recherche un Groupe en base de données 
	 * @param id L'identifiant du groupe
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Le groupe trouvé 
	 * @exception DaoException si la requete n'a pas fonctionée
	 */
	@Override
	public Group findGroup(long id) throws DaoException {
		Group group = null;
		try {
			Group g = new Group();
			g.setId(id);
			group = findBean(new DaoUtilsGroup(), g);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return group;
	}
	
	/**
	 * Sauvegarde une personne en base de données 
	 * @param p La personne
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @exception DaoException si la requete n'a pas fonctionée
	 */
	@Override
	public void savePerson(Person p) throws DaoException {
		if (p.getId() == null) {
			long newId = insertBean(new DaoUtilsPerson(), p);
			p.setId(newId);
		} else {
			updateBean(new DaoUtilsPerson(), p);
		}
	}

	/**
	 * Sauvegarde un groupe en base de données 
	 * @param g Le groupe
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @exception DaoException si la requete n'a pas fonctionée
	 */
	@Override
	public void saveGroup(Group g) throws DaoException {
		if (g.getId() == null) {
			long newId = insertBean(new DaoUtilsGroup(), g);
			g.setId(newId);
		} else {
			updateBean(new DaoUtilsGroup(), g);
		}
	}

	/**
	 * calcule le nombre de groupes en base de données 
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return le nombre de groupes en base de données 
	 * @exception DaoException si la requete n'a pas fonctionée
	 */
	@Override 
	public long getNbGroups() throws DaoException {
		DaoUtilsGroup utilsGroup = new DaoUtilsGroup();
		try (Connection c = db.getConnection()) {
			return utilsGroup.size(c, new Group());
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	/**
	 * calcule le nombre de personnes dans le groupe 
	 * @param id L'identifiant du groupe
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return le nombre de personnes dans le groupe
	 * @exception DaoException si la requete n'a pas fonctionée
	 */
	@Override
	public long getNbPersons(long id) throws DaoException {
		DaoUtilsPerson utilsPerson = new DaoUtilsPerson();
		try (Connection c = db.getConnection()) {
			Person p = new Person();
			p.setIdGroup(id);
			return utilsPerson.size(c, p);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

}
