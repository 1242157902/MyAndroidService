package cn.ncut.syssetdao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.ncut.syssetdomain.Privilege;
import cn.ncut.utils.JdbcUtils;

/**
 * @author wzq
 * 
 *         version 1.0 2014-10-14 下午4:31:15
 */
public class PrivilegeManagerDao {

	public List<Privilege> getAllPrivilege() {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<Privilege> privileges = new ArrayList<Privilege>();

		try {
			conn = JdbcUtils.getConnection();

			String sql = "select * from tab_privilege";

			statement = conn.prepareStatement(sql);

			result = statement.executeQuery();
			while (result.next()) {
				Privilege privilege = new Privilege();
				String id = result.getString("privilege_id");
				String name = result.getString("privilege_name");
				String description = result.getString("privilege_description");
				privilege.setId(id);
				privilege.setDescription(description);
				privilege.setName(name);
				privileges.add(privilege);

			}

			return privileges;

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, result);

		}

		return null;
	}

	public void addprivilege( String description, String name) {
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = JdbcUtils.getConnection();

			String sql = "insert into tab_privilege (privilege_name,privilege_description) values(?,?)";

			statement = conn.prepareStatement(sql);

		
			statement.setString(1, name);
			statement.setString(2, description);

			statement.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, null);

		}
	}

	public void deleteprivilege(String id) {
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = JdbcUtils.getConnection();

			String sql = "delete from tab_privilege where privilege_id=?";

			statement = conn.prepareStatement(sql);

			statement.setString(1, id);

			statement.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, null);

		}
	}

	
	public Privilege getprivilegebyid(String id) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = JdbcUtils.getConnection();

			String sql = "select * from tab_privilege  where privilege_id=?";

			statement = conn.prepareStatement(sql);
			statement.setString(1, id);

			result = statement.executeQuery();

			Privilege privilege = new Privilege();
			while (result.next()) {
				String name = result.getString("privilege_name");
				String description = result.getString("privilege_description");
				String id2 = result.getString("privilege_id");
				privilege.setDescription(description);
				privilege.setId(id2);
				privilege.setName(name);

				

			}

			return privilege;

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, null);

		}

		return null;
	}

}
