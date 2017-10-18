package database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Service;

@Service
public class JDBC implements IDatabase {

	private String url = "jdbc:mysql://127.0.0.1:9999/b13002175";
	private String id = "b13002175";
	private String password = "gRAYUM!.";
	private BasicDataSource bds;
	
	
	private void initBasicDataSource() {
		bds = new BasicDataSource();
		bds.setUrl(url);
		bds.setPassword(password);
		bds.setUsername(id);
		bds.setInitialSize(5);
		bds.setMaxTotal(5);
	}
	
	private void initTablePerson() {
		String create =  "CREATE table if not exists PERSON ("
				+ "idPER int auto_increment, primary key (idPER), "
				+ "name varchar(50) not null, "
				+ "firstname varchar(50) not null, "
				+ "mail varchar(50) not null, "
				+ "website varchar(50) not null, "
				+ "birthdate date, "
				+ "password varchar(50) not null, "
				+ "idGRP int,"
				+ "FOREIGN KEY (idGRP) REFERENCES GROUPS(idGRP) )";
		
		try(Connection c = bds.getConnection();
			Statement sta = c.createStatement()){
			sta.executeUpdate(create);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("table person already exist");
		}
	} 
	
	private void initTableGroup() {
		String create = "CREATE TABLE IF NOT EXISTS GROUPS ( "
				+ "idGRP INT AUTO_INCREMENT, PRIMARY KEY (idGRP), "
				+ "name VARCHAR(50) NOT NULL )";
		try(Connection c = bds.getConnection();
				Statement sta = c.createStatement()){
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
	
	@PostConstruct
	public void init() {
		initBasicDataSource();
		initDatabase();
		
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
