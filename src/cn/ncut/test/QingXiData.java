package cn.ncut.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cn.ncut.utils.JdbcUtils;
import cn.ncut.utils.JdbcUtils02;

/**
 * @author wzq
 *
 *version 1.0 2016-4-19 下午9:01:33
 */
public class QingXiData {
	public static void main(String[] args) {
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		List<Integer> ids=new ArrayList<Integer>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select device_id from tab_deviceinfo where device_type=''";
		    st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while (rs.next()) {
				ids.add(rs.getInt("device_id"));
			}
			
			JdbcUtils.release(conn, st, rs);
			List<String> nos=new ArrayList<String>();
			conn = JdbcUtils.getConnection();
			 sql = "select id  from tab_pushmbtype";
		   st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while (rs.next()) {
				
                nos.add(rs.getString("id"));
			}
			
			System.out.println(ids);
			System.out.println(nos);
			JdbcUtils.release(conn, st, rs);
			
			for (int id : ids) {
				
				conn = JdbcUtils.getConnection();
				sql = "update  tab_deviceinfo set  device_type=? where device_id=?";
			    st = conn.prepareStatement(sql);
			    st.setString(1, nos.get(new Random().nextInt(nos.size())));
			    st.setInt(2, id);
				st.executeUpdate();
				JdbcUtils.release(conn, st, rs);
				
			}
			
			
	   } catch (Exception e) {

		e.printStackTrace();
		} 
		
		
		
		
		
		
		
	}
	

}
