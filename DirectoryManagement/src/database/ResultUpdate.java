package database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultUpdate {
	
	void resultSetUpdate(ResultSet rs) throws SQLException;
	
}
