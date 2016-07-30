package cn.ncut.syssetdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.ncut.syssetdomain.Privilege;
import cn.ncut.syssetdomain.Role;
import cn.ncut.utils.JdbcUtils;

/**
 * @author wzq
 * 
 *         version 1.0 2014-10-14 下午5:13:11
 */
public class RoleManagerDao {

	public List<Role> getAllRole() {

		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<Role> roles = new ArrayList<Role>();

		try {
			conn = JdbcUtils.getConnection();

			String sql = "select * from tab_role";

			statement = conn.prepareStatement(sql);

			result = statement.executeQuery();
			while (result.next()) {
				Role role = new Role();
				String name = result.getString("role_name");
				String description = result.getString("role_description");
				String id = result.getString("role_id");

				try {
					List<Privilege> privileges = getroleprivileges(id);
					StringBuilder stringBuilder = new StringBuilder();

					for (Privilege privilege : privileges) {
						stringBuilder.append(privilege.getName());
						stringBuilder.append(" ,");
					}

					stringBuilder.deleteCharAt(stringBuilder.length() - 1);

					String pnames = stringBuilder.toString();

					role.setPrivileges(pnames);
				} catch (Exception e) {

				}

				role.setDescription(description);
				role.setId(id);
				role.setName(name);

				roles.add(role);

			}

			return roles;

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, null);

		}

		return null;

	}

	public void addrole(String name, String description) {

		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = JdbcUtils.getConnection();

			String sql = "insert into tab_role (role_name,role_description) values(?,?)";

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

	
	
	
	
	
	
	public void deleterole(String id) {
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = JdbcUtils.getConnection();

			String sql = "delete from tab_role where role_id=?";

			statement = conn.prepareStatement(sql);

			statement.setString(1, id);

			statement.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, null);

		}

	}

	public Role findrolebyid(String id) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = JdbcUtils.getConnection();

			String sql = "select * from tab_role where role_id=?";

			statement = conn.prepareStatement(sql);
			statement.setString(1, id);

			result = statement.executeQuery();

			Role role = new Role();
			while (result.next()) {

				String name = result.getString("role_name");
				String description = result.getString("role_description");
				role.setDescription(description);
				role.setId(id);
				role.setName(name);
			}

			return role;

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, result);

		}

		return null;
	}

	public List<Privilege> getroleprivileges(String id) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = JdbcUtils.getConnection();

			String sql = "select p.* from tab_role_privilege rp,tab_privilege p where rp.role_id=? and rp.privilege_id=p.privilege_id";

			statement = conn.prepareStatement(sql);
			statement.setString(1, id);

			result = statement.executeQuery();

			List<Privilege> privileges = new ArrayList<Privilege>();
			while (result.next()) {
				Privilege privilege = new Privilege();
				String name = result.getString("privilege_name");
				String description = result.getString("privilege_description");
				String id2 = result.getString("privilege_id");
				privilege.setDescription(description);
				privilege.setId(id2);
				privilege.setName(name);

				privileges.add(privilege);

			}

			return privileges;

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, null);

		}

		return null;
	}

	public void updateroleprivilege(String role_id, List<Privilege> privileges) {

		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = JdbcUtils.getConnection();
			// 先删除角色所有权限
			String sql = "delete from tab_role_privilege where role_id=?";

			statement = conn.prepareStatement(sql);
			statement.setString(1, role_id);
			statement.executeUpdate();

			// 给角色赋予新的权限

			for (Privilege privilege : privileges) {
				sql = "insert into tab_role_privilege(role_id,privilege_id)values(?,?)";
				statement = conn.prepareStatement(sql);
				statement.setString(1, role_id);
				statement.setString(2, privilege.getId());
				statement.executeUpdate();

			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, null);

		}

	}

}
