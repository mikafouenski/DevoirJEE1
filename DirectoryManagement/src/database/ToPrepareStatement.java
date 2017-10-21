package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public interface ToPrepareStatement {
	
	PreparedStatement createPrep(Connection c) throws SQLException;

}