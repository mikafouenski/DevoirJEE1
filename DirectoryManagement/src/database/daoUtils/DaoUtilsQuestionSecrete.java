package database.daoUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.QuestionSecrete;

public class DaoUtilsQuestionSecrete implements IDaoUtils<QuestionSecrete> {

	private final String FIND_QUESTIONSECRETE_BY_ID = "SELECT idQS, question, reponse FROM QUESTIONSECRETE WHERE idQS = ?";
	private final String LIST_QUESTIONSECRETE_FULL = "SELECT idQS, question, reponse FROM QUESTIONSECRETE";
	
	/**
	 * Insere en base de données
	 * @param rs Le ResultSet contenant la requete
	 * @param template De type T (le bean)
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return L'identifiant BD du bean T inséré
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
	@Override
	public Long resultSetInsert(ResultSet rs, QuestionSecrete qs) throws SQLException {
		rs.moveToInsertRow();
		rs.updateString("question", qs.getQuestion());
		rs.updateString("reponse", qs.getReponse());
		rs.insertRow();
		rs.last();
		return rs.getLong("idQS");
	}

	/**
	 * Converti un ResultSet en bean T
	 * @param rs Le ResultSet contenant la requete
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return le bean du ResultSet
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
	@Override
	public QuestionSecrete toBean(ResultSet rs) throws SQLException {
		QuestionSecrete qs = new QuestionSecrete();
		qs.setId(rs.getLong("idQS"));
		qs.setQuestion(rs.getString("question"));
		qs.setReponse(rs.getString("reponse"));
		return qs;
	}

	/**
	 * Met a jour en base de données
	 * @param rs Le ResultSet contenant la requete
	 * @param template De type T (le bean)
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
	@Override
	public void resultSetUpdate(ResultSet rs, QuestionSecrete qs) throws SQLException {
		// not needed
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
	public long size(Connection c, QuestionSecrete template) throws SQLException {
		// not needed
		return 0;
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
	public PreparedStatement createTableViewList(Connection c, QuestionSecrete template) throws SQLException {
		return c.prepareStatement(LIST_QUESTIONSECRETE_FULL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
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
	public PreparedStatement createTableViewList(Connection c, QuestionSecrete template, int start, int range)
			throws SQLException {
		// not needed
		return null;
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
	public PreparedStatement createTableViewSingleton(Connection c, QuestionSecrete qs) throws SQLException {
		PreparedStatement prep = c.prepareStatement(FIND_QUESTIONSECRETE_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		prep.setLong(1, qs.getId());
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
	public PreparedStatement createSearch(Connection c, QuestionSecrete template, String param1, String param2)
			throws SQLException {
		// not needed
		return null;
	}

}
