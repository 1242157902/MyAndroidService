package cn.ncut.pushservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.ncut.utils.JdbcUtils;
public class AutoPushService {
	
	public String PushToSingle(String startime,String endtime,String queid,String imei) {
		try{
			int val=Integer.parseInt(queid);
			String pid=InsertPushList(startime,endtime,queid);
			PushToMobile(pid,"'"+imei+"'");
			return "success";
		}
		catch(Exception e) {
		    return "failure";
		} 
	}
	
	public String PushToSome(String startime,String endtime,String queid,String[] imei) {
		try{
			int val=Integer.parseInt(queid);
			String pid=InsertPushList(startime,endtime,queid);
			String imeis="";
			for(int i=0;i<imei.length;i++){
				imeis=imeis+",'"+imei[i]+"'";	
			}
			if(imeis.length()>0) imeis=imeis.substring(1);
			PushToMobile(pid,imeis);
			return "success";
		}
		catch(Exception e) {
		    return "failure";
		} 
	}
	
	public String PushToLimit(String startime,String endtime,String queid,String query) {
		try{
			int val=Integer.parseInt(queid);
			return "success";
		}
		catch(Exception e) {
		    return "failure";
		} 
	}
	
	public String PushToAll(String startime,String endtime,String queid) {
		try{
			int val=Integer.parseInt(queid);
			String pid=InsertPushList(startime,endtime,queid);
			PushToMobile(pid,"all");
			return "success";
		}
		catch(Exception e) {
		    return "failure";
		} 
	}
	
	
	
	public String GetQueIdByPlayTime(int playtime) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql ="select que from tab_pushcontentclass  where code REGEXP CONCAT('^',(select code from tab_pushcontentclass where value='w02' ),'[0-9][0-9][0-9]-') and value<="+playtime+"order by value desc limit 0,1";
			statement = conn.prepareStatement(sql);
			result=statement.executeQuery();
			if (result.next()) {
				return result.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, result);
		}
		return "";
	}
	
	public String GetQueIdByLocation(Double Longitude,Double latitude) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql ="select que from tab_pushcontentclass  where location='"+Longitude+"'  limit 0,1";
			statement = conn.prepareStatement(sql);
			result=statement.executeQuery();
			if (result.next()) {
				return result.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, result);
		}
		return "";
	}
	
	public String GetQueIdByFlavor(String type) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql ="select que from tab_pushcontentclass  where value='"+type+"' and code REGEXP CONCAT('^',(select code from tab_pushcontentclass where value='w03' ),'[0-9][0-9][0-9]-') limit 0,1";
			statement = conn.prepareStatement(sql);
			result=statement.executeQuery();
			if (result.next()) {
				return result.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, result);
		}
		return "";
	}
		
	private String InsertPushList(String startime,String endtime,String queid) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "Call InsertPushList(?,?,?)";
			statement = conn.prepareStatement(sql);
			statement.setInt(1, Integer.parseInt(queid));
			statement.setString(2,startime);
			statement.setString(3,endtime);
			result=statement.executeQuery();
			if (result.next()) {
				return result.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, result);
		}
		return "";
	}
		
	private void PushToMobile(String pid,String imeis)
	{
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "update tab_pushinfo set allque=CONCAT(IFNULL(allque,''),'"+pid+",') where imei in (select device_imei from tab_deviceinfo where device_imei in ("+imeis+") or device_number in ("+imeis+"))";
			//String sql = "update tab_pushinfo set allque=SUBSTRING(REPLACE(CONCAT(',',allque), ',"+pid+",', ','),2) where imei in ("+imeis+")";
			if(imeis.equals("all"))
			sql = "update tab_pushinfo set allque=SUBSTRING(REPLACE(CONCAT(',',allque), ',"+pid+",', ','),2)";	
			st = conn.prepareStatement(sql);
			st.executeUpdate();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				JdbcUtils.release(conn, st, null);
        }	
	}
}
