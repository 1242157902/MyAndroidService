package cn.ncut.syssetdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import cn.ncut.syssetdomain.Role;
import cn.ncut.syssetdomain.User;
import cn.ncut.utils.JdbcUtils;

/**
 * @author wzq
 * 
 *         version 1.0 2014-10-14 下午3:35:40
 */
public class UserManagerDao {

	public List<User> getAllUser(String comno) {

		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<User> users = new ArrayList<User>();

		try {
			conn = JdbcUtils.getConnection();

			String sql = "select * from tab_user";
			if (comno!=null&&!comno.equals("100000")) {
				sql=sql+" where com_no="+"'"+comno+"'";
			}

			statement = conn.prepareStatement(sql);

			result = statement.executeQuery();
		
			while (result.next()) {
				User user = new User();
				String username = result.getString("username");
				String password = result.getString("password");
				String id = result.getString("user_id");
				comno=result.getString("com_no");
				
				
				String comname=getcomnamebycomno(comno);
				
				
				user.setComname(comname);
				
				user.setPassword(password);
				user.setUsername(username);
				user.setId(id);
				users.add(user);

			}

			return users;

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, result);

		}

		return null;

	}

	/**
	 * @param comno
	 * @return
	 */
	private String getcomnamebycomno(String comno) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		String comname=null;
		try {
			String sql="select * from tab_company where com_no="+comno;
			conn=JdbcUtils.getConnection();
			statement = conn.prepareStatement(sql);
            result = statement.executeQuery();
			
            while (result.next()) {
				comname=result.getString("com_name");
			}
            
			
			return comname;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			
			JdbcUtils.release(conn, statement, result);
			
			
		}
		
		
		return null;
	}

	public void adduser( String username, String password,String cmpno) {
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = JdbcUtils.getConnection();

			String sql = "insert into tab_user (username,password,com_no) values(?,?,?)";

			statement = conn.prepareStatement(sql);

			
			statement.setString(1, username);
			statement.setString(2, password);
			statement.setString(3, cmpno);

			statement.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, null);

		}

	}

	public void deleteuser(String id) {
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = JdbcUtils.getConnection();

			String sql = "delete from tab_user where user_id=?";

			statement = conn.prepareStatement(sql);

			statement.setString(1, id);

			statement.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, null);

		}
	}

	public User finduserbyid(String id) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = JdbcUtils.getConnection();

			String sql = "select * from tab_user where user_id=?";

			statement = conn.prepareStatement(sql);
			statement.setString(1, id);

			result = statement.executeQuery();

			User user = new User();
			while (result.next()) {

				String username = result.getString("username");
				String password = result.getString("password");
				String id2 = result.getString("user_id");
				user.setId(id2);
				user.setUsername(username);
				user.setPassword(password);

			}

			return user;

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, result);

		}

		return null;
	}

	public List<Role> getUserRoles(String id) {

		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = JdbcUtils.getConnection();

			String sql = "select r.* from tab_user_role ur,tab_role r where ur.user_id=? and ur.role_id=r.role_id";

			statement = conn.prepareStatement(sql);
			statement.setString(1, id);

			result = statement.executeQuery();

			List<Role> roles = new ArrayList<Role>();
			while (result.next()) {
				Role role = new Role();
				String name = result.getString("role_name");
				String description = result.getString("role_description");
				String id2 = result.getString("role_id");
				role.setId(id2);
				role.setName(name);
				role.setDescription(description);
				roles.add(role);
			}

			return roles;

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, result);

		}

		return null;
	}

	public void updatesuser(String user_id, List<Role> roles) {

		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = JdbcUtils.getConnection();

			String sql = "delete from tab_user_role where user_id=?";
			// 删除用户拥有的角色
			statement = conn.prepareStatement(sql);
			statement.setString(1, user_id);

			statement.executeUpdate();

			// 更新用户角色

			for (int i = 0; i < roles.size(); i++) {
				Role role = roles.get(i);

				sql = "insert into tab_user_role(user_id,role_id)values(?,?)";
				statement = conn.prepareStatement(sql);
				statement.setString(1, user_id);
				statement.setString(2, role.getId());

				statement.executeUpdate();

			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, null);

		}

	}

	public User finduser(String username, String password) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = JdbcUtils.getConnection();

			String sql = "select * from tab_user where username=? and password=? and user_status="+1;

			statement = conn.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);

			result = statement.executeQuery();
			User user = null;
			while (result.next()) {
				user = new User();
				String id = result.getString("user_id");
				user.setId(id);
				user.setUsername(username);
				user.setPassword(password);
				user.setComno(result.getString("com_no"));
				user.setIsseller(result.getInt("is_seller"));
				user.setUserstatus(result.getInt("user_status"));
			

			}

			return user;

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, result);

		}

		return null;
	}

	public void updateuser(String id, String password) {
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = JdbcUtils.getConnection();

			String sql = "update tab_user set password=? where user_id=?";

			statement = conn.prepareStatement(sql);

			statement.setString(1, password);
			statement.setString(2, id);

			statement.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, null);

		}

	}

	/**
	 * @param cmpname
	 * @return
	 */
	public String addcompany(String comname) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = JdbcUtils.getConnection();

			String sql = "insert into tab_company(com_name) values(?)";

			statement = conn.prepareStatement(sql);
			statement.setString(1, comname);
			
            statement.executeUpdate();
			
            String  comno = null;
            
            sql="select com_no from tab_company where com_name=?";
            statement=conn.prepareStatement(sql);
            statement.setString(1, comname);
             result=statement.executeQuery();
            
			while (result.next()) {
				
              comno=result.getString("com_no");
			}
         
			return comno;

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, result);

		}

		return null;
	}

}
