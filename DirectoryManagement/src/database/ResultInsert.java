package database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultInsert {
	
	Long resultSetInsert(ResultSet rs) throws SQLException;

}
