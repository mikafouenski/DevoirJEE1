package database.daoUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import beans.Group;

public class DaoUtilsGroup implements DaoUtils<Group> {

	private final String FIND_GROUP_BY_ID = "SELECT idGRP,name FROM GROUPS WHERE idGRP = ?";
	private final String COUNT_GROUPS = "SELECT COUNT(idGRP) as nb FROM GROUPS";
	private final String LIST_GROUPS_FULL = "SELECT idGRP,name FROM GROUPS";
	private final String LIST_GROUPS_RECORD = "SELECT idGRP,name FROM GROUPS LIMIT ? , ?";
	private final String LIST_GROUPS_SEARCH = "SELECT idGRP,name FROM GROUPS WHERE name LIKE ?\"%\"";

	/**
	 * Insere en base de données
	 * @param rs Le ResultSet contenant la requete
	 * @param template De type T (le bean)
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return L'identifiant BD du bean T inséré
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
	@Override
	public Long resultSetInsert(ResultSet rs, Group g) throws SQLException {
		rs.moveToInsertRow();
		rs.updateString("name", g.getName());
		rs.insertRow();
		rs.last();
		return rs.getLong("idGRP");
	}

	/**
	 * Converti un ResultSet en bean T
	 * @param rs Le ResultSet contenant la requete
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return le bean du ResultSet
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
	@Override
	public Group toBean(ResultSet rs) throws SQLException {
		Group gr = new Group();
		gr.setId(rs.getLong("idGRP"));
		gr.setName(rs.getString("name"));
		return gr;
	}

	/**
	 * Met a jour en base de données
	 * @param rs Le ResultSet contenant la requete
	 * @param template De type T (le bean)
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
	@Override
	public void resultSetUpdate(ResultSet rs, Group g) throws SQLException {
		rs.first();
		rs.updateString("name", g.getName());
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
	public long size(Connection c, Group g) throws SQLException {
		try (PreparedStatement prep = c.prepareStatement(COUNT_GROUPS, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = prep.executeQuery();) {
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
	public PreparedStatement createTableViewList(Connection c, Group g) throws SQLException {
		return c.prepareStatement(LIST_GROUPS_FULL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
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
	public PreparedStatement createSearch(Connection c, Group g, String param1, String param2) throws SQLException {
		PreparedStatement prep = c.prepareStatement(LIST_GROUPS_SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		prep.setString(1,param1);
		return prep;
	}

	/**
	 * Prépare une vue des bean T entre la borne start et end en base de données
	 * @param c La connection en base
	 * @param template De type T (le bean)
	 * @param start indice de la première colonne selectionné de recherche 
	 * @param range la taille du record
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return une vue des beans T
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
	@Override
	public PreparedStatement createTableViewList(Connection c, Group g, int start, int range)
			throws SQLException {
		PreparedStatement prep = c.prepareStatement(LIST_GROUPS_RECORD, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		prep.setInt(1, start);
		prep.setInt(2, range);
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
	public PreparedStatement createTableViewSingleton(Connection c, Group g) throws SQLException {
		PreparedStatement prep = c.prepareStatement(FIND_GROUP_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		prep.setLong(1, g.getId());
		return prep;
	}

}
