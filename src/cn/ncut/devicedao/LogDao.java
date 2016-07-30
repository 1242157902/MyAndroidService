package cn.ncut.devicedao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import cn.ncut.devicedomain.ApkLog;
import cn.ncut.utils.JdbcUtils;
import cn.ncut.utils.JdbcUtils02;

/**
 * @author wzq
 *
 *version 1.0 2015-1-28 下午5:38:59
 */
public class LogDao {

	/**
	 * @param apkLog
	 */
	public void saveupdateapkinfo(ApkLog apkLog) {
		
		

		PreparedStatement statement = null;
		Connection conn=null;
		try {

				String sql = "insert into tab_apkupdate_log (apk_version,device_number,device_type,device_imei,device_version,apk_updatetime) values(?,?,?,?,?,?)";
                conn=JdbcUtils02.getConnection();
				statement = conn.prepareStatement(sql);

				statement.setString(1, apkLog.getApkversion());
				statement.setString(2, apkLog.getPhonenumber());
				statement.setString(3, apkLog.getPhonetype());
				statement.setString(4, apkLog.getPhoneimei());
				statement.setString(5, apkLog.getPhoneversion());
				statement.setString(6, apkLog.getUpdatetime());
				

				statement.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {

				JdbcUtils02.release(conn, statement, null);

			}

		}

	/**
	 * @return
	 */
	public List<ApkLog> getphoneupdateapkinfo(int currentPage, int pageSize,String phonenum,String imei) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<ApkLog> apkLogs = new ArrayList<ApkLog>();

		try {
			conn = JdbcUtils02.getConnection();

			String sql = "select * from tab_apkupdate_log where 1=1  ";
			
			if(phonenum!=null&&!"".equals(phonenum)){
				
				sql=sql+"and device_number='"+phonenum+"'";
			}
        if(imei!=null&&!"".equals(imei)){
				
				sql=sql+"and device_imei='"+imei+"'";
			}   
			
					
			sql=sql+ " order by id desc limit "+ (currentPage - 1) * pageSize + " , " + pageSize;

			statement = conn.prepareStatement(sql);

			result = statement.executeQuery();
			while (result.next()) {
				ApkLog apkLog= new ApkLog();
				
				apkLog.setApkversion(result.getString("apk_version"));
				apkLog.setPhoneimei(result.getString("device_imei"));
				apkLog.setPhonenumber(result.getString("device_number"));
				apkLog.setPhonetype(result.getString("device_type"));
				apkLog.setPhoneversion(result.getString("device_version"));
				apkLog.setUpdatetime(result.getString("apk_updatetime"));
				
				
				apkLogs.add(apkLog);
				
				
			}

			return apkLogs;

		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, result);

		}

		return null;
	}

	/**
	 * @return
	 */
	public int getphoneApkupdateinfoTotal(String phonenum,String imei) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils02.getConnection();
			String sql = "select count(*) from tab_apkupdate_log where 1=1 ";
			

			if(phonenum!=null&&!"".equals(phonenum)){
				
				sql=sql+"and device_number='"+phonenum+"'";
			}
        if(imei!=null&&!"".equals(imei)){
				
				sql=sql+"and device_imei='"+imei+"'";
			}   
			
			

			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);

			}
	   } catch (Exception e) {

		e.printStackTrace();
		} finally {
			JdbcUtils02.release(conn, st, rs);

		}
		return 0;
	}
		
	

}
