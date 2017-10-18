package database;


import java.sql.Connection;
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
		String create =  "CREATE OR REPLACE table PERSON ("
				+ "id int(6) auto_increment primary key,"
				+ "name varchar(50) not null,"
				+ "firstname varchar(50) not null,"
				+ "mail varchar(50) not null,"
				+ "website varchar(50) not null,"
				+ "birthdate date,"
				+ "password varchar(50) not null"
				+ "FOREIGN KEY (idGroup) REFERENCES GROUP(id));";
		
		try(Connection c = bds.getConnection();
			Statement sta = c.createStatement()){
			sta.execute(create);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initTableGroup() {
		String create = "CREATE OR REPLACE table GROUP ("
				+ "id int(6) auto_increment primary key,"
				+ "name varchar(50) not null);";
		try(Connection c = bds.getConnection();
				Statement sta = c.createStatement()){
				sta.execute(create);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	private void initDatabase() {
		initTablePerson();
		initTableGroup();
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
