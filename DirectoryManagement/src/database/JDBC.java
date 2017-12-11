package database;

import java.sql.Connection;

import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Service;

@Service
public class JDBC implements IDatabase {
	
	private static final int MAX_CONNEXION = 5;

	private String url = "jdbc:mysql://mysql-mickaestnul.alwaysdata.net/mickaestnul_b13";//"jdbc:mysql://127.0.0.1:9999/b13002175";
	private String id = "148878"; //b13002175";
	private String password = "root";//"gRAYUM!.";
	private BasicDataSource bds;
						
	/**
	 * Initialise la datasource avec les credentials
	 * @author Bernardini Mickael De Barros Sylvain
	 */
	public JDBC() {
		bds = new BasicDataSource();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		bds.setUrl(url);
		bds.setPassword(password);
		bds.setUsername(id);
		bds.setInitialSize(MAX_CONNEXION);
		bds.setMaxTotal(MAX_CONNEXION);
	}

	/**
	 * Fournie une connection à la base de donnée
	 * @author Bernardini Mickael De Barros Sylvain
	 * @return une Connection à la BDD
	 * @exception SQLException si la requete n'a pas fonctionée
	 */
	public Connection getConnection() throws SQLException {
		return bds.getConnection();
	}
}
