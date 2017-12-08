package database.daoUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import beans.Group;

public class DaoUtilsGroup implements DaoUtils<Group> {

	private final String FIND_GROUP_BY_ID = "SELECT idGRP,name FROM GROUPS WHERE idGRP = ?";
	private final String COUNT_GROUPS = "SELECT COUNT(idGRP) as nb FROM GROUPS";
	private final String LIST_GROUPS_FULL = "SELECT idGRP,name FROM GROUPS";
	private final String LIST_GROUPS_RECORD = "SELECT idGRP,name FROM GROUPS LIMIT ? , ?";
	private final String LIST_GROUPS_SEARCH = "SELECT idGRP,name FROM GROUPS WHERE DIFERENCE(name,?) > 5";

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
	public long size(Connection c, Group g) throws SQLException {
		try (PreparedStatement prep = c.prepareStatement(COUNT_GROUPS, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = prep.executeQuery();) {
			rs.last();
			return rs.getLong("nb");
		}
	}
	
	@Override
	public PreparedStatement createTableViewList(Connection c, Group g) throws SQLException {
		return c.prepareStatement(LIST_GROUPS_FULL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	}
	
	@Override
	public PreparedStatement createSearch(Connection c, Group g, String param1, String param2) throws SQLException {
		PreparedStatement prep = c.prepareStatement(LIST_GROUPS_SEARCH, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		prep.setString(1,param1);
		return prep;
	}

	@Override
	public PreparedStatement createTableViewList(Connection c, Group g, int start, int end)
			throws SQLException {
		PreparedStatement prep = c.prepareStatement(LIST_GROUPS_RECORD, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		prep.setInt(1, start);
		prep.setInt(2, end);
		return prep;
	}

	@Override
	public PreparedStatement createTableViewSingleton(Connection c, Group g) throws SQLException {
		PreparedStatement prep = c.prepareStatement(FIND_GROUP_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		prep.setLong(1, g.getId());
		return prep;
	}

}
