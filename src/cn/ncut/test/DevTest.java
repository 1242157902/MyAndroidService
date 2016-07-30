package cn.ncut.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import cn.ncut.utils.JdbcUtils;
import cn.ncut.utils.JdbcUtils02;

/**
 * @author wzq
 *
 *version 1.0 2015-5-28 上午10:43:42
 */
public class DevTest {
	
	public static void main(String[] args) throws Exception{
		
         
         
      Connection conn = null;
 		PreparedStatement st = null;
 		ResultSet rs = null;
 		
 		Map<String, String>map=new HashMap<String, String>();
 		
 		try {
 			conn = JdbcUtils02.getConnection();
 			String sql = "select device_imei,enter_time from tab_pushdevice";
 			st = conn.prepareStatement(sql);
 			
 			rs = st.executeQuery();

 			while(rs.next()) {
 				map.put(rs.getString("device_imei"), rs.getString("enter_time"));
             
 			}

 		} catch (Exception e) {

 			e.printStackTrace();
 		} finally {
 			JdbcUtils.release(conn, st, rs);
 		}
 		
 		
 		try {
 			conn = JdbcUtils.getConnection();
 			
 			 Set<Entry<String, String>>    myentry=  map.entrySet();
 			 
 			 for (Entry<String, String> entry : myentry) {
 				 
 				 String id=entry.getKey();
 				 String time=entry.getValue();
 				 
 				
 				 
 				 
 				String sql = "update  tab_deviceinfo set enter_time=? where device_imei=?";
 	 			st = conn.prepareStatement(sql);
 	 			st.setString(1, time);
 	 			st.setString(2, id);
 	 		   st.executeUpdate();
				
			}
 		
 			

 			

 		} catch (Exception e) {

 			e.printStackTrace();
 		} 
 		
 		
 		
 		
        
	
		//System.out.println(System.currentTimeMillis());
		
	
		
	}
	
	

}
