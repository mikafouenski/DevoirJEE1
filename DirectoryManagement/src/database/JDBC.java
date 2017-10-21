package database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Service;

@Service
public class JDBC implements IDatabase {

	private String url = "jdbc:mysql://127.0.0.1:9999/b13002175";
	private String id = "b13002175";
	private String password = "gRAYUM!.";
	private BasicDataSource bds;

	public JDBC() {
		initBasicDataSource();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Use init of database ? (y / n)");
		String input = "";
		try {
			input = br.readLine();
		} catch (IOException e) {
		}
		if (input.equals("y")) {
			initDatabase();
			insertInitData();
		}
	}

	private void initBasicDataSource() {
		bds = new BasicDataSource();
		bds.setUrl(url);
		bds.setPassword(password);
		bds.setUsername(id);
		bds.setInitialSize(5);
		bds.setMaxTotal(5);
	}

	private void insertInitData() {
		String create = "INSERT INTO `PERSON` (`idPER`,`name`,`firstname`,`mail`,`website`,`birthdate`,`password`, `idGRP`) VALUES (1,\"Witt\",\"Candice\",\"sit.amet@libero.edu\",\"ligula.Aenean@temporestac.com\",\"01/02/94\",\"massa.\"),(2,\"Kelley\",\"Emma\",\"pharetra.Quisque@Curabiturvellectus.co.uk\",\"nec.urna@ultricesposuerecubilia.co.uk\",\"10/01/12\",\"Morbi\"),(3,\"Newman\",\"Joy\",\"odio@dignissim.org\",\"nulla.Cras.eu@aliquetdiam.edu\",\"05/07/15\",\"natoque\"),(4,\"Mcneil\",\"Karly\",\"consectetuer.adipiscing@egetipsumSuspendisse.org\",\"sapien@rutrumnonhendrerit.co.uk\",\"03/05/06\",\"vestibulum,\"),(5,\"Rollins\",\"Britanney\",\"risus.quis.diam@neque.co.uk\",\"Nullam.suscipit.est@Cumsociis.edu\",\"07/19/10\",\"sapien,\"),(6,\"Kennedy\",\"Candice\",\"netus.et.malesuada@ornaresagittis.org\",\"mauris.Integer.sem@mi.org\",\"09/20/92\",\"euismod\"),(7,\"Hammond\",\"Keely\",\"id@porttitor.org\",\"dis.parturient.montes@Nullam.net\",\"02/15/08\",\"luctus,\"),(8,\"Aguilar\",\"Jessica\",\"enim.commodo@Namtempor.edu\",\"Vivamus.sit@anteVivamus.com\",\"11/02/10\",\"Phasellus\"),(9,\"Stewart\",\"Xena\",\"nulla@ligulatortor.net\",\"euismod@varius.co.uk\",\"08/10/99\",\"enim\"),(10,\"Jensen\",\"Brielle\",\"consequat.lectus@feugiatnecdiam.com\",\"tempus.scelerisque@dolorQuisque.ca\",\"12/25/94\",\"tempor\");\n" + 
				"";
		try (Connection c = bds.getConnection(); Statement sta = c.createStatement()) {
			sta.executeUpdate(create);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("table person already exist");
		}
	}

	private void initTablePerson() {
		String create = "CREATE table if not exists PERSON (" + "idPER int auto_increment, primary key (idPER), "
				+ "name varchar(50) not null, " + "firstname varchar(50) not null, " + "mail varchar(50) not null, "
				+ "website varchar(50) not null, " + "birthdate date, " + "password varchar(50) not null, "
				+ "idGRP int," + "FOREIGN KEY (idGRP) REFERENCES GROUPS(idGRP) )";

		try (Connection c = bds.getConnection(); Statement sta = c.createStatement()) {
			sta.executeUpdate(create);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("table person already exist");
		}
	}

	private void initTableGroup() {
		String create = "CREATE TABLE IF NOT EXISTS GROUPS ( " + "idGRP INT AUTO_INCREMENT, PRIMARY KEY (idGRP), "
				+ "name VARCHAR(50) NOT NULL )";
		try (Connection c = bds.getConnection(); Statement sta = c.createStatement()) {
			sta.executeUpdate(create);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("table group already exist");
		}
	}

	private void initDatabase() {
		initTableGroup();
		initTablePerson();
	}

	public Connection newConnection() throws SQLException {
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
