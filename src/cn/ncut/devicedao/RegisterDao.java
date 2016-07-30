package cn.ncut.devicedao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.ncut.utils.JdbcUtils;
import cn.ncut.utils.JdbcUtils02;

/**
 * @author wzq
 * 
 *         version 1.0 2014-12-13 上午10:48:53
 */
public class RegisterDao {

	/**
	 * @param phoneimei
	 * @return
	 */
	public String getnumbyimei(String phoneimei) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select * from tab_deviceinfo where device_imei=?";
			st = conn.prepareStatement(sql);
			st.setString(1, phoneimei);

			rs = st.executeQuery();

			if (rs.next()) {
				return rs.getString("device_number");

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}

		return "";
	}

	/**
	 * @param imei
	 * @param comno
	 * @param shopno
	 * @param empno
	 * @return
	 */
	public boolean addempinfo(String imei, String comno, String shopno,
			String empno) {

		Connection conn = null;
		PreparedStatement st = null;

		PhoneDao phoneDao = new PhoneDao();
		String sql = null;

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = new Date();
		String regtime = format.format(d1);

		try {
			boolean isexist = phoneDao.isexist(imei);

			if (isexist) {
				sql = "update tab_deviceinfo set seller_depid=?,seller_empid=?,device_seller=?,user_regtime=? where device_imei=?";
			} else {

				sql = "insert into tab_deviceinfo(seller_depid,seller_empid,device_seller,user_regtime,device_imei) values(?,?,?,?,?)";
			}

			conn = JdbcUtils.getConnection();
			st = conn.prepareStatement(sql);
			st.setInt(1, Integer.parseInt(shopno));
			st.setInt(2, Integer.parseInt(empno));
			st.setInt(3, Integer.parseInt(comno));
			st.setString(4, regtime);
			st.setString(5, imei);
			st.executeUpdate();

			return true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, null);
		}

		return false;
	}

	/**
	 * @param phoneimei
	 * @param comno
	 * @return
	 */
	public boolean updateunit(String phoneimei, String comno) {

		Connection conn = null;
		PreparedStatement st = null;
		PhoneDao phoneDao = new PhoneDao();
		String sql = null;

		try {

			boolean isexist = phoneDao.isexist(phoneimei);
			conn = JdbcUtils.getConnection();
			if (isexist) {
				sql = "update tab_deviceinfo set user_unit=? where device_imei=?";
			} else {
				sql = "insert into tab_deviceinfo(device_imei,user_unit) values(?,?)";
			}

			if (sql.startsWith("update")) {
				st = conn.prepareStatement(sql);
				st.setInt(1, Integer.parseInt(comno));
				st.setString(2, phoneimei);

			} else {
				st = conn.prepareStatement(sql);
				st.setString(1, phoneimei);
				st.setInt(2, Integer.parseInt(comno));
			}

			st.executeUpdate();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, null);
		}

		return false;
	}

	/**
	 * @param phoneimei
	 * @param comno
	 * @param shopno
	 * @param empno
	 * @return
	 */
	public boolean isexist(String phoneimei, String comno, String shopno,
			String empno) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select * from tab_deviceinfo where device_imei=? and device_seller=? and seller_depid=? and seller_empid=?";

			st = conn.prepareStatement(sql);
			st.setString(1, phoneimei);
			st.setInt(2, Integer.parseInt(comno));
			st.setInt(3, Integer.parseInt(shopno));
			st.setInt(4, Integer.parseInt(empno));

			rs = st.executeQuery();

			if (rs.next()) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}

		return false;
	}

	/**
	 * @param phoneimei
	 * @param comno
	 * @return
	 */
	public boolean isexist(String phoneimei, String comno) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select * from tab_deviceinfo where device_imei=? and user_unit=?";

			st = conn.prepareStatement(sql);
			st.setString(1, phoneimei);
			st.setInt(2, Integer.parseInt(comno));
			rs = st.executeQuery();

			if (rs.next()) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}

		return false;
	}

	/**
	 * @param imei
	 * @param age
	 * @param sex
	 * @param phonenum
	 * @return
	 * 
	 *         保存用户注册信息
	 */
	public boolean register(String imei, int age, String sex, String phonenum,String nickname) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = new Date();
		String regtime = format.format(d1);
		try {
			conn = JdbcUtils.getConnection();

			PhoneDao dao = new PhoneDao();
			boolean isexist = dao.isexist(imei);
			String sql = null;
			if (isexist) {
				sql = "  update   tab_deviceinfo set user_regtime='"+regtime+"',user_birth=?,user_sex=?,device_number=? ,nick_name=? where device_imei=?";
			} else {

				sql = "insert into tab_deviceinfo(user_birth,user_sex,device_number,nick_name,device_imei,enter_time) values(?,?,?,?,?,'"+regtime+"')";
			}

			st = conn.prepareStatement(sql);
			st.setString(1, age + "");
			st.setString(2, sex);
			st.setString(3, phonenum);
			st.setString(4, nickname);
			st.setString(5, imei);
		    st.executeUpdate();

			return true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}

		return false;
	}

	/**
	 * @param imei
	 * @param phonenum
	 * @param appnames
	 */
	public void saveappnames(String imei, String phonenum, String appnames) {

		boolean isexists = findappnames(imei);
		String sql = null;
		Connection conn = null;
		PreparedStatement st = null;

		try {
			conn = JdbcUtils02.getConnection();

			if (!isexists) {

				sql = "insert into tab_apps(device_imei,device_number,app_names) values(?,?,?)";
				st = conn.prepareStatement(sql);
				st.setString(1, imei);
				st.setString(2, phonenum);
				st.setString(3, appnames);

			} else {

				sql = "update tab_apps set app_names=? where device_imei=?";
				st = conn.prepareStatement(sql);
				st.setString(1, appnames);
				st.setString(2, imei);
			}

			st.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, null);
		}

	}

	/**
	 * @param imei
	 * @return 查找是否已经存在设备apps信息 如不存在则插入 存在则更新
	 */
	private boolean findappnames(String imei) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils02.getConnection();

			String sql = "select * from tab_apps where device_imei=?";
			st = conn.prepareStatement(sql);
			st.setString(1, imei);
			rs = st.executeQuery();
			while (rs.next()) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		return false;
	}

	
	/**获取tab_deviceinfo表中update_key字段的值
	 * @param phoneimei
	 * @return
	 */
	public int getUpdateKey(String phoneimei) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select update_key from tab_deviceinfo where device_imei=?";
			st = conn.prepareStatement(sql);
			st.setString(1, phoneimei);

			rs = st.executeQuery();

			if (rs.next()) {
				return rs.getInt("update_key");

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}

		return -1;
	}
	
	
	
	
	
	/**
	 * @param phoneimei
	 * @param comno
	 * @return
	 */
	public int ModifyUpdateKey(String phoneimei, int update_key) {

		int rowcount=0;//更新操作影响的行的个数
		Connection conn = null;
		PreparedStatement st = null;
		PhoneDao phoneDao = new PhoneDao();
		String sql = null;

		try {

			boolean isexist = phoneDao.isexist(phoneimei);
			conn = JdbcUtils.getConnection();
			if (isexist) {
				sql = "update tab_deviceinfo set update_key=? where device_imei=?";
			} else {
				System.out.println("tab_deviceinfo表中不存在imei="+phoneimei+"对应的手机设备记录！！");
			}

			st = conn.prepareStatement(sql);
			st.setInt(1, update_key);
			st.setString(2, phoneimei);

			rowcount=st.executeUpdate();
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, null);
		}

		return rowcount;
	}

	
	
	/**
	 * @param phoneimei
	 * @param comno
	 * @return
	 */
	//更新tab_deviceinfo表中update_key字段为1
	public int ModifyAllUpdateKey(int update_key) {

		int rowcount=0;//更新操作影响行的个数
		Connection conn = null;
		PreparedStatement st = null;
		PhoneDao phoneDao = new PhoneDao();
		String sql = null;

		try {

			boolean isexist = phoneDao.isexistDeviceRecord();
			conn = JdbcUtils.getConnection();
			if (isexist) {
				sql = "update tab_deviceinfo set update_key=?";
			} else {
				System.out.println("tab_deviceinfo表中不存在手机设备记录！！");
			}

			st = conn.prepareStatement(sql);
			st.setInt(1, update_key);

			rowcount=st.executeUpdate();
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, null);
		}

		return rowcount;
	}
	
	
	
	
}
