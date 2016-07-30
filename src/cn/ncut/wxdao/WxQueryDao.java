package cn.ncut.wxdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import cn.ncut.utils.JdbcUtils;

/**
 * @author wzq
 *
 * version 1.0 2014-12-2 下午8:23:50
 */
public class WxQueryDao {

	/**
	 * @param num
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String, String> countbyday(String num, String year, String month) {
		Map<String, String> map=new HashMap<String, String>();
		
		
		int nowmonth=Integer.parseInt(month);
		int premonth=nowmonth-1;
		int nextmonth=nowmonth+1;
		
		String begintime=year+"-"+premonth+"-"+"00"+" "+"00:00:00";
		String endtime=year+"-"+nextmonth+"-"+"00"+" "+"00:00:00";
		
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select DAYOFMONTH(open_time) as day,count(*) as cc from tab_deviceopeninfo where device_number=? and  open_time between ? and ? GROUP BY DAYOFMONTH(open_time)";
		    statement = conn.prepareStatement(sql);
		    statement.setString(1, num);
		    statement.setString(2,begintime);
		    statement.setString(3, endtime);
		    
			result = statement.executeQuery();
			while (result.next()) {
				map.put(result.getString("day"),result.getString("cc"));
				
			}
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, result);
		}
		return null;
	}
		

}
