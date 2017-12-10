package database.daoUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.Person;

public class DaoUtilsPerson implements DaoUtils<Person> {

	private final String FIND_PERSON_BY_ID = "SELECT idPER,name,firstname,mail,website,password,birthdate,idGRP FROM PERSON WHERE idPER = ?";
	private final String LIST_PERSONS_BY_GROUP_ID_FULL = "SELECT idPER,name,firstname,mail,website,password,birthdate,idGRP FROM PERSON WHERE idGRP = ?";
	private final String LIST_PERSONS_BY_GROUP_ID_RECORD = "SELECT idPER,name,firstname,mail,website,password,birthdate,idGRP FROM PERSON WHERE idGRP = ? LIMIT ? , ?";
	private final String COUNT_PERSONS_BY_GROUP_ID = "SELECT COUNT(idPER) as nb FROM PERSON WHERE idGRP = ?";
	private final String LIST_PERSONS_SEARCH = "SELECT idPER,name,firstname,mail,website,password,birthdate,idGRP FROM PERSON WHERE name LIKE ?\"%\" and firstname LIKE ?\"%\"";
	
	/**
	 * Insere en base de données
	 * @param rs Le ResultSet contenant la requete
	 * @param template De type T (le bean)
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return L'identifiant BD du bean T inséré
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
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

	/**
	 * Converti un ResultSet en bean T
	 * @param rs Le ResultSet contenant la requete
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return le bean du ResultSet
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
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

	/**
	 * Met a jour en base de données
	 * @param rs Le ResultSet contenant la requete
	 * @param template De type T (le bean)
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
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
	
	/**
	 * Calcule le nombre de beans T en base de données
	 * @param c La connection en base
	 * @param template De type T (le bean)
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return le nombre de beans T
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
	@Override
	public long size(Connection c, Person p) throws SQLException {
		try (PreparedStatement prep = c.prepareStatement(COUNT_PERSONS_BY_GROUP_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);) {
			prep.setLong(1, p.getIdGroup());
			ResultSet rs = prep.executeQuery();
			rs.last();
			return rs.getLong("nb");
		}
	}

	/**
	 * Prépare une vue des beans T en base de données
	 * @param c La connection en base
	 * @param template De type T (le bean)
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return une vue des beans T
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
	@Override
	public PreparedStatement createTableViewList(Connection c, Person p) throws SQLException {
		PreparedStatement prep = c.prepareStatement(LIST_PERSONS_BY_GROUP_ID_FULL, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		prep.setLong(1, p.getIdGroup());
		return prep;
	}

	/**
	 * Prépare une vue des bean T entre la borne start et end en base de données
	 * @param c La connection en base
	 * @param template De type T (le bean)
	 * @param start indice de la première colonne selectionné de recherche 
	 * @param end indice de la dernière colonne selectionné de recherche 
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return une vue des beans T
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
	@Override
	public PreparedStatement createTableViewList(Connection c, Person p, int start, int end) throws SQLException {
		PreparedStatement prep = c.prepareStatement(LIST_PERSONS_BY_GROUP_ID_RECORD, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		prep.setLong(1, p.getIdGroup());
		prep.setInt(2, start);
		prep.setInt(3, end);
		return prep;
	}

	/**
	 * Prépare une vue du bean T en base de données
	 * @param c La connection en base
	 * @param template De type T (le bean)
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return une vue du bean T
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
	@Override
	public PreparedStatement createTableViewSingleton(Connection c, Person p) throws SQLException {
		PreparedStatement prep = c.prepareStatement(FIND_PERSON_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		prep.setLong(1, p.getId());
		return prep;
	}

	/**
	 * Prépare une requete de recherche des beans T en base de données
	 * @param c La connection en base
	 * @param template De type T (le bean)
	 * @param param1 Un parramètre de recherche
	 * @param param2 Un parramètre de recherche
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return une requete de recherche des beans T
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
	@Override
	public PreparedStatement createSearch(Connection c, Person p, String param1,String param2) throws SQLException {
		PreparedStatement prep = c.prepareStatement(LIST_PERSONS_SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		prep.setString(1,param1);
		prep.setString(2,param2);
		return prep;
	}
	

}
