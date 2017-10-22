package database.daoUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.Group;

public class DaoUtilsGroup implements DaoUtils<Group> {

	private final String FIND_GROUP_BY_ID = "SELECT idGRP,name FROM GROUPS WHERE idGRP = ?";
	private final String LIST_GROUPS = "SELECT idGRP,name FROM GROUPS";

	@Override
	public Long resultSetInsert(ResultSet rs, Group g) throws SQLException {
		rs.moveToInsertRow();
		rs.updateString("name", g.getName());
		rs.insertRow();
		rs.last();
		return rs.getLong("idGRP");
	}

	@Override
	public Group toBean(ResultSet rs) throws SQLException {
		Group gr = new Group();
		gr.setId(rs.getLong("idGRP"));
		gr.setName(rs.getString("name"));
		return gr;
	}

	@Override
	public void resultSetUpdate(ResultSet rs, Group g) throws SQLException {
		rs.first();
		rs.updateString("name", g.getName());
		rs.updateRow();
	}

	@Override
	public PreparedStatement createTableViewList(Connection c, Group g) throws SQLException {
		return c.prepareStatement(LIST_GROUPS, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	}

	@Override
	public PreparedStatement createTableViewSingleton(Connection c, Group g) throws SQLException {
		PreparedStatement prep = c.prepareStatement(FIND_GROUP_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		prep.setLong(1, g.getId());
		return prep;
	}

}
