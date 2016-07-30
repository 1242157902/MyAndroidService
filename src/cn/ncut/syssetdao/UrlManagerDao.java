package cn.ncut.syssetdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.ncut.syssetdomain.UrlInfo;
import cn.ncut.syssetdomain.User;
import cn.ncut.utils.JdbcUtils;

public class UrlManagerDao {
	
public void updateurl(String oldurl, String newurl) {
		
		Connection conn = null;
		PreparedStatement statement = null;

	try {
		conn = JdbcUtils.getConnection();

		String sql = "update tab_url set old_url=?,new_url=? where id=1";

		statement = conn.prepareStatement(sql);

		statement.setString(1,oldurl);
		statement.setString(2,newurl);
		
		statement.executeUpdate();
	} catch (SQLException e) {

		e.printStackTrace();
	} finally {

		JdbcUtils.release(conn, statement, null);

	}
		
		
		
		
	}

public UrlInfo getUrlById() {
	Connection conn = null;
	PreparedStatement statement = null;
	ResultSet result = null;
	try {
		conn = JdbcUtils.getConnection();

		String sql = "select * from tab_url where id=1";
		

		statement = conn.prepareStatement(sql);

		result = statement.executeQuery();

	  UrlInfo url=new UrlInfo();
		while (result.next()) {

			String oldUrl = result.getString("old_url");
			String newUrl = result.getString("new_url");
			int id=result.getInt("id");
			url.setId(id);
			url.setNewUrl(newUrl);
			url.setOldUrl(oldUrl);
		}

		return url;

	} catch (SQLException e) {

		e.printStackTrace();
	} finally {

		JdbcUtils.release(conn, statement, result);

	}

	return null;
}
	
}
