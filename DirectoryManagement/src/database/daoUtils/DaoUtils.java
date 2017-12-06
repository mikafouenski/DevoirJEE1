package database.daoUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DaoUtils<T> {
	// return the resultSet which insert into database
	Long resultSetInsert(ResultSet rs, T template) throws SQLException;
	// convert a resultSet into an object
	T toBean(java.sql.ResultSet rs) throws SQLException;
	// update the database
	void resultSetUpdate(ResultSet rs, T template) throws SQLException;
	// get the size of the table
	long size(Connection c, T template) throws SQLException;
	// create full view of the table
	PreparedStatement createTableViewList(Connection c, T template) throws SQLException;
	// create record view of the table
	PreparedStatement createTableViewList(Connection c, T template, int start, int end) throws SQLException;
	// create a view of the object template
	PreparedStatement createTableViewSingleton(Connection c, T template) throws SQLException;
}
