package cn.ncut.wxdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.ncut.devicedomain.MobileModel;
import cn.ncut.utils.JdbcUtils;

 /**
  * 
  * <p>Title：        DeviceInforDao<p>
  * <p>Description: 获取设备相关信息Dao <p>
  * @date:           2016年3月20日下午3:00:38
  * @author:         ysl
  * @version         1.0
  */
public class DeviceInforDao 
{
	public static void main(String[] args) {
		DeviceInforDao did = new DeviceInforDao();
		System.out.println(did.getMobileModelByPhone("13001962817").getBirth());
	}
	/**
	 * 
	 * @return:       String 
	 * @param phone
	 * @return
	 * <p>Description: 通过手机号获得设备的imei号<p>
	 * @date:          2016年3月20日下午3:04:25
	 * @author         ysl
	 */
	 public String getDeviceImeiByPhone(String phone)
	 {
			Connection conn = null;
			PreparedStatement statement = null;
			ResultSet result = null;
			String imei = null;
			try {
				conn = JdbcUtils.getConnection();
				String sqlString="select * from tab_deviceinfo  where device_number=?";
				statement = conn.prepareStatement(sqlString);
				statement.setString(1,phone);
				
				result = statement.executeQuery();
				
				
				while (result.next()) 
				{
					imei = result.getString("device_imei");
				}
				
				return imei;
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JdbcUtils.release(conn, statement, result);
			}
			
			return null;
	 }
	 /**
	  * 
	  * @return:       MobileModel 
	  * @param phone
	  * @return
	  * <p>Description: 获取设备的所有信息<p>
	  * @date:          2016年4月7日下午5:11:42
	  * @author         ysl
	  */
	 public MobileModel getMobileModelByPhone(String phone)
	 {
		 Connection conn = null;
		 PreparedStatement statement = null;
		 ResultSet result = null;
		 String imei = null;
		 try {
			 conn = JdbcUtils.getConnection();
			 String sqlString="select * from tab_deviceinfo  where device_number=?";
			 statement = conn.prepareStatement(sqlString);
			 statement.setString(1,phone);
			 
			 result = statement.executeQuery();
			 
			 MobileModel mm = new MobileModel();
			 while (result.next()) 
			 {
				 imei = result.getString("device_imei");
				 String birthday = result.getString("user_birth");
				 Integer sex = result.getInt("user_sex");
				 mm.setImei(imei);
				 mm.setBirth(birthday);
				 mm.setGender(sex.toString());
			 }
			 
			 return mm;
			 
		 } catch (SQLException e) {
			 e.printStackTrace();
		 } finally {
			 JdbcUtils.release(conn, statement, result);
		 }
		 
		 return null;
	 }
}
