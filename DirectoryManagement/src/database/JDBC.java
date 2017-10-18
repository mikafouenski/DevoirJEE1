package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Service;

@Service
public class JDBC implements IDatabase {

	private String url = "jdbc:mysql://127.0.0.1:9999/b13002175";
	private String id = "b13002175";
	private String password = "gRAYUM!.";
	private BasicDataSource bds;
	
	@PostConstruct
	public void init() {
		initBasicDataSource();
	}
	
	private void initBasicDataSource() {
		bds = new BasicDataSource();
		bds.setUrl(url);
		bds.setPassword(password);
		bds.setUsername(id);
		bds.setInitialSize(5);
		bds.setMaxTotal(5);
	}
	
	public void initDatabase() {
		String create = "create table person ("
				+ "id int(6) auto_increment primary key,"
				+ "name varchar(50) not null,"
				+ "firstname varchar(50) not null,"
				+ "mail varchar(50) not null,"
				+ "website varchar(50) not null,"
				+ "birthdate date,"
				+ "password varchar(50) not null);";
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
