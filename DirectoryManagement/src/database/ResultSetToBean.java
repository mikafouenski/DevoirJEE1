package database;

import java.sql.SQLException;

public interface ResultSetToBean<T> {
	
	T toBean(java.sql.ResultSet rs) throws SQLException;

}
