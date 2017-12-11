package database.daoUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IDaoUtils<T> {
	
	/**
	 * Insere en base de données
	 * @param rs Le ResultSet contenant la requete
	 * @param template De type T (le bean)
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return L'identifiant BD du bean T inséré
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
	Long resultSetInsert(ResultSet rs, T template) throws SQLException;
	
	/**
	 * Converti un ResultSet en bean T
	 * @param rs Le ResultSet contenant la requete
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return le bean du ResultSet
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
	T toBean(ResultSet rs) throws SQLException;
	
	/**
	 * Met a jour en base de données
	 * @param rs Le ResultSet contenant la requete
	 * @param template De type T (le bean)
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
	void resultSetUpdate(ResultSet rs, T template) throws SQLException;
	
	/**
	 * Calcule le nombre de beans T en base de données
	 * @param c La connection en base
	 * @param template De type T (le bean)
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return le nombre de beans T
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
	long size(Connection c, T template) throws SQLException;
	
	/**
	 * Prépare une vue des beans T en base de données
	 * @param c La connection en base
	 * @param template De type T (le bean)
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return une vue des beans T
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
	PreparedStatement createTableViewList(Connection c, T template) throws SQLException;
	
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
	PreparedStatement createTableViewList(Connection c, T template, int start, int range) throws SQLException;
	
	/**
	 * Prépare une vue du bean T en base de données
	 * @param c La connection en base
	 * @param template De type T (le bean)
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return une vue du bean T
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
	PreparedStatement createTableViewSingleton(Connection c, T template) throws SQLException;

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
	PreparedStatement createSearch(Connection c, T template, String param1,String param2) throws SQLException;
}
