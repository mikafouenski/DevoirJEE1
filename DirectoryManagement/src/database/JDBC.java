package database;

import java.sql.Connection;

import java.sql.SQLException;
import java.sql.Statement;


import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Service;

@Service
public class JDBC implements IDatabase {
	
	private static final int MAX_CONNEXION = 5;

	private String url = "jdbc:mysql://mysql-mickaestnul.alwaysdata.net/mickaestnul_b13";//"jdbc:mysql://127.0.0.1:9999/b13002175";
	private String id = "148878"; //b13002175";
	private String password = "root";//"gRAYUM!.";
	private BasicDataSource bds;
						
	public JDBC() {
		initBasicDataSource();
	}
	
	/**
	 * Constructeur pouvant initialiser la BDD
	 * @param init True booleen de changement de signature 
	 * @author Bernardini Mickael De Barros Sylvain
	 */
	public JDBC(boolean init) {
		initBasicDataSource();
		if (init)
			initDatabase();
	}

	/**
	 * Initialise la datasource avec les credentials
	 * @author Bernardini Mickael De Barros Sylvain
	 */
	private void initBasicDataSource() {
		bds = new BasicDataSource();
		try {
			// TODO
			// ATTENTION ! Fix douteux venus des internets
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
	 * Creation de la structure de la base de données
	 * @author Bernardini Mickael De Barros Sylvain
	 */
	private void initDatabase() {
		String createGroups = "CREATE TABLE IF NOT EXISTS `GROUPS` (" + 
			"  `idGRP` int(11) NOT NULL AUTO_INCREMENT," + 
			"  `name` varchar(50) NOT NULL," + 
			"  PRIMARY KEY (`idGRP`)" + 
			") ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;";
		String createPerson = "CREATE TABLE IF NOT EXISTS `PERSON` (" + 
			"  `idPER` int(11) NOT NULL AUTO_INCREMENT," + 
			"  `name` varchar(50) NOT NULL," + 
			"  `firstname` varchar(50) NOT NULL," + 
			"  `mail` varchar(50) NOT NULL," + 
			"  `website` varchar(50) NOT NULL," + 
			"  `birthdate` date DEFAULT NULL," + 
			"  `password` varchar(520) NOT NULL," +
			"  `idGRP` int(11) DEFAULT NULL," + 
			"  PRIMARY KEY (`idPER`)," + 
			"  KEY `idGRP` (`idGRP`)" + 
			") ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11";
		try (Connection c = bds.getConnection(); Statement sta = c.createStatement()) {
			sta.executeUpdate(createGroups);
			sta.executeUpdate(createPerson);
		} catch (SQLException e) {
		}
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

	/**
	 * Echape toute erreur éventuelle de fermeture
	 * @author Bernardini Mickael De Barros Sylvain
	 */
	public void quietClose(Connection c) {
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
}
