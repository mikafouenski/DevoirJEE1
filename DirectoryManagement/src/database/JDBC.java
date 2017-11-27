package database;

import java.sql.Connection;

import java.sql.SQLException;
import java.sql.Statement;


import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Service;

@Service
public class JDBC implements IDatabase {
	
	private static final int MAX_CONNEXION = 5;

	private String url = "jdbc:mysql://127.0.0.1:9999/b13002175";
	private String id = "b13002175";
	private String password = "gRAYUM!.";
	private BasicDataSource bds;

	public JDBC() {
		initBasicDataSource();
	}
	
	public JDBC(boolean init) {
		initBasicDataSource();
		if (init)
			initDatabase();
	}

	private void initBasicDataSource() {
		bds = new BasicDataSource();
		bds.setUrl(url);
		bds.setPassword(password);
		bds.setUsername(id);
		bds.setInitialSize(MAX_CONNEXION);
		bds.setMaxTotal(MAX_CONNEXION);
	}

	private void initDatabase() {
		String createGroups = "CREATE TABLE IF NOT EXISTS `GROUPS` (" + 
			"  `idGRP` int(11) NOT NULL AUTO_INCREMENT," + 
			"  `name` varchar(50) NOT NULL," + 
			"  PRIMARY KEY (`idGRP`)" + 
			") ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;";
		String insertGroups = "INSERT INTO `GROUPS` (`idGRP`, `name`) VALUES" + 
			"(1, 'Ut Sagittis Lobortis LLP')," + 
			"(2, 'Magna Suspendisse PC')," + 
			"(3, 'Mi Ac Corp.')," + 
			"(4, 'Vitae Foundation')," + 
			"(5, 'At Lacus Quisque Institute')";
		String createPerson = "CREATE TABLE IF NOT EXISTS `PERSON` (" + 
			"  `idPER` int(11) NOT NULL AUTO_INCREMENT," + 
			"  `name` varchar(50) NOT NULL," + 
			"  `firstname` varchar(50) NOT NULL," + 
			"  `mail` varchar(50) NOT NULL," + 
			"  `website` varchar(50) NOT NULL," + 
			"  `birthdate` date DEFAULT NULL," + 
			"  `password` varchar(50) NOT NULL," + 
			"  `idGRP` int(11) DEFAULT NULL," + 
			"  PRIMARY KEY (`idPER`)," + 
			"  KEY `idGRP` (`idGRP`)" + 
			") ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11";
		String insertPerson = "INSERT INTO `PERSON` (`idPER`, `name`, `firstname`, `mail`, `website`, `birthdate`, `password`, `idGRP`) VALUES" + 
			"(1, 'Witt', 'Candice', 'sit.amet@libero.edu', 'ligula.Aenean@temporestac.com', '2017-10-12', 'massa.', 1)," + 
			"(2, 'Kelley', 'Emma', 'pharetra.Quisque@Curabiturvellectus.co.uk', 'nec.urna@ultricesposuerecubilia.co.uk', '2017-10-12', 'Morbi', 1)," + 
			"(3, 'Newman', 'Joy', 'odio@dignissim.org', 'nulla.Cras.eu@aliquetdiam.edu', '2017-10-12', 'natoque', 1),\n" + 
			"(4, 'Mcneil', 'Karly', 'consectetuer.adipiscing@egetipsumSuspendisse.org', 'sapien@rutrumnonhendrerit.co.uk', '2017-10-12', 'vestibulum,', 2)," + 
			"(5, 'Rollins', 'Britanney', 'risus.quis.diam@neque.co.uk', 'Nullam.suscipit.est@Cumsociis.edu', '2017-10-12', 'sapien,', 2)," + 
			"(6, 'Kennedy', 'Candice', 'netus.et.malesuada@ornaresagittis.org', 'mauris.Integer.sem@mi.org', '2017-10-12', 'euismod', 2)," + 
			"(7, 'Hammond', 'Keely', 'id@porttitor.org', 'dis.parturient.montes@Nullam.net', '2017-10-12', 'luctus,', 3)," + 
			"(8, 'Aguilar', 'Jessica', 'enim.commodo@Namtempor.edu', 'Vivamus.sit@anteVivamus.com', '2017-10-12', 'Phasellus', 3)," + 
			"(9, 'Stewart', 'Xena', 'nulla@ligulatortor.net', 'euismod@varius.co.uk', '2017-10-12', 'enim', 4)," + 
			"(10, 'Jensen', 'Brielle', 'consequat.lectus@feugiatnecdiam.com', 'tempus.scelerisque@dolorQuisque.ca', '2017-10-12', 'tempor', 5)";
		try (Connection c = bds.getConnection(); Statement sta = c.createStatement()) {
			sta.executeUpdate(createGroups);
			sta.executeUpdate(createPerson);
			sta.executeUpdate(insertGroups);
			sta.executeUpdate(insertPerson);
		} catch (SQLException e) {
		}
	}

	public Connection getConnection() throws SQLException {
		return bds.getConnection();
	}

	public void quietClose(Connection c) {
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
}
