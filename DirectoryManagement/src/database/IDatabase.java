package database;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDatabase {
	
	public Connection newConnection() throws SQLException;
	
	public void quietClose(Connection c);

}
