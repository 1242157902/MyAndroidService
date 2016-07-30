package cn.ncut.pushdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import cn.ncut.pushdomain.IconInfo;
import cn.ncut.utils.JdbcUtils;

/**
 * @author wzq
 * 
 *         version 1.0 2015-4-3 下午3:35:22
 */
public class IconDao {

	/**
	 * @param currentPage
	 * @param pageSize
	 * @param comno
	 * @return
	 */
	public List<IconInfo> getlist(int currentPage, int pageSize, String comno) {

		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;

		String sql = null;
		List<IconInfo> iconInfos = new ArrayList<IconInfo>();

		if ("100000".equals(comno)) {

			sql = "select *,(select name from tab_pushcontentclass where code=icon_class) as icon_className from tab_iconinfo where flag=1";

		} else {
			sql = "select *,(select name from tab_pushcontentclass where code=icon_class) as icon_className from tab_iconinfo where icon_manager='" + comno + "' and flag=1";
		}

		try {

			conn = JdbcUtils.getConnection();
			statement = conn.prepareStatement(sql);
			rs = statement.executeQuery();

			while (rs.next()) {

				IconInfo info = new IconInfo();

				info.setId(rs.getInt("icon_id"));
				info.setTitle(rs.getString("icon_title"));
				info.setIcon_class(rs.getString("icon_class"));
				info.setIcon_className(rs.getString("icon_className"));
				info.setIconname(rs.getString("icon_name"));
				info.setPicurl(rs.getString("icon_url"));
				info.setManager(rs.getString("icon_manager"));
				info.setUpdatetime(rs.getString("icon_savetime"));

				iconInfos.add(info);

			}

			return iconInfos;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * @param comno
	 * @return
	 */
	public int getTotal(String comno) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;

		String sql = null;
		if ("100000".equals(comno)) {

			sql = "select count(*) from tab_iconinfo where flag=1";

		} else {
			sql = "select count(*) from tab_iconinfo where icon_manager='" + comno
					+ "' and flag=1";
		}

		try {
			conn = JdbcUtils.getConnection();
			statement = conn.prepareStatement(sql);
			rs = statement.executeQuery();

			while (rs.next()) {

				return rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, rs);

		}

		return 0;
	}

	/**
	 * @param id
	 */
	public void delete(int id) {

		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = JdbcUtils.getConnection();

			String sql = "update  tab_iconinfo  set flag=0 where icon_id=" + id;

			statement = conn.prepareStatement(sql);

			statement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, null);

		}

	}

	/**
	 * @param info
	 */
	public void add(IconInfo info) {
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = JdbcUtils.getConnection();

			String sql = "insert into tab_iconinfo(icon_title,icon_name,icon_url,icon_manager,icon_savetime,icon_class) values(?,?,?,?,?,?)";

			statement = conn.prepareStatement(sql);

			statement.setString(1, info.getTitle());
			statement.setString(2, info.getIconname());
			statement.setString(3, info.getPicurl());
			statement.setString(4, info.getManager());
			statement.setString(5, info.getUpdatetime());
			statement.setString(6, info.getIcon_class());

			statement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, null);
		}

	}

	/**
	 * @param info
	 */
	public void update(IconInfo info) {
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = JdbcUtils.getConnection();

			String sql = " update tab_iconinfo set icon_title=?,icon_name=?,icon_url=?,icon_savetime=?,icon_class=? where icon_id=?";

			statement = conn.prepareStatement(sql);
         	statement.setString(1, info.getTitle());
			statement.setString(2, info.getIconname());
			statement.setString(3, info.getPicurl());
			statement.setString(4, info.getUpdatetime());
			statement.setString(5, info.getIcon_class());
			statement.setInt(6, info.getId());
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, null);
		}

	}

}
