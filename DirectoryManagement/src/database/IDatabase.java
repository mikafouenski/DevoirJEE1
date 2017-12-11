package database;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDatabase {
	
	/**
	 * Fournie une connection à la base de donnée
	 * @author Bernardini Mickael De Barros Sylvain
	 * @return une Connection à la BDD
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
	public Connection getConnection() throws SQLException;
	
}
