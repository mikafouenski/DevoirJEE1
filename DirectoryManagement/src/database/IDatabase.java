package database;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import business.IDirectoryManager;

public interface IDatabase {
	
	public Connection getConnection() throws SQLException;
	
	public void quietClose(Connection c);

}
