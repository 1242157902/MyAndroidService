package cn.ncut.syssetdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import cn.ncut.devicedomain.Interval;
import cn.ncut.utils.JdbcUtils;

/**
 * @author wzq
 *
 * version 1.0 2014-10-18 下午3:45:05
 */
public class TimeDao {

	
	public void update(String intervaltime, String updatepictime) {
		
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = JdbcUtils.getConnection();

			String sql = "update tab_time set interval_time=?,update_pic_time=? where id=1" ;

			statement = conn.prepareStatement(sql);

			statement.setString(1, intervaltime);
			
			statement.setString(2, updatepictime);
			

			statement.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, null);

		}
		
		
		
		
		
	}
	

	
public Interval gettime() {
		
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			conn = JdbcUtils.getConnection();

			String sql = "select * from tab_time where id=1" ;

			statement = conn.prepareStatement(sql);
			
			result=statement.executeQuery();
			
			while (result.next()) {
				
				Interval interval=new Interval();
				
				
				String intervaltime=result.getString("interval_time");
				String location_interval=result.getString("location_interval");
				interval.setIntervaltime(intervaltime);
				interval.setLocation_interval(location_interval);
				return interval;
				
			}

		
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, result);

		}
		
		
		
		return null;
		
	}



/**
 * @param location_interval
 */
public void update(String location_interval) {
	Connection conn = null;
	PreparedStatement statement = null;

	try {
		conn = JdbcUtils.getConnection();

		String sql = "update tab_time set location_interval=? where id=1" ;

		statement = conn.prepareStatement(sql);

		statement.setString(1, location_interval);
		
		
		

		statement.executeUpdate();
	} catch (SQLException e) {

		e.printStackTrace();
	} finally {

		JdbcUtils.release(conn, statement, null);

	}
	
}
	
	

}
