package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public interface BeantoPrepareStatement {
	
	PreparedStatement createPrep(Connection c) throws SQLException;

}