package cn.ncut.devicedao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.json.JSONArray;
import org.json.JSONObject;


import cn.ncut.devicedomain.ApkInfo;
import cn.ncut.devicedomain.Location;
import cn.ncut.devicedomain.MobileModel;
import cn.ncut.devicedomain.ScreenOnOff;
import cn.ncut.devicedomain.SlideModel;
import cn.ncut.devicedomain.UserClickTime;
import cn.ncut.pushdao.PhDao;
import cn.ncut.pushdomain.AppFlow;
import cn.ncut.utils.ECC_SHA;
import cn.ncut.utils.JdbcUtils;
import cn.ncut.utils.JdbcUtils02;

/**
 * @author wzq
 * 
 *         version 1.0 2014-9-18 下午10:31:59
 */
@Slf4j
public class PhoneDao {
	/**
	 * 存储开锁时间
	 * 
	 * @param devicenumber
	 * @param opentime
	 */
	public void SaveOpenTime(List<SlideModel> slideModels) {

		PreparedStatement statement = null;
		Connection conn = null;

		String tablename = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		Date d1 = new Date();
		tablename = format.format(d1);

		tablename = "tab_slideinfo_" + tablename;
		MobileDao mobileDao = new MobileDao();

		try {
			conn = JdbcUtils02.getConnection();
			conn.setAutoCommit(false);
			
			for (SlideModel slideModel : slideModels) {
				String sql = "insert into "
						+ tablename
						+ " values(null,?,?,?,?,?,?)";
				statement = conn.prepareStatement(sql);
				String[] scoreandtitle = mobileDao.getpicscore(slideModel.getPicname());
            	statement.setString(1, slideModel.getImei());
				statement.setString(2, slideModel.getMbno());
				statement.setString(3, scoreandtitle[1]);
				statement.setString(4, slideModel.getPicname());
				statement.setString(5,slideModel.getSlidetime());
				statement.setString(6, scoreandtitle[0]);
				statement.executeUpdate();
			}

			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
			   e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {

			JdbcUtils02.release(conn, statement, null);

		}

	}

	/**
	 * 判断手机信息是否存在于手机信息表中
	 * 
	 * @param deviceimei
	 * @param devicenumber
	 * @param devicetype
	 * @return
	 */
	public boolean isexist(String deviceimei) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select * from tab_deviceinfo where device_imei=?";
			st = conn.prepareStatement(sql);
			st.setString(1, deviceimei);

			rs = st.executeQuery();

			if (rs.next()) {
				return true;

			}

		} catch (Exception e) {

			throw new RuntimeException(e);
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		return false;
	}
	public boolean isexistfyy(String deviceimei) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select * from tab_pushinfo where imei=?";
			st = conn.prepareStatement(sql);
			st.setString(1, deviceimei);

			rs = st.executeQuery();

			if (rs.next()) {
				return true;

			}

		} catch (Exception e) {

			throw new RuntimeException(e);
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		return false;
	}
	// 返回Apk版本号
	public String getcurrversion() {

		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		String version = null;

		try {
			conn = JdbcUtils.getConnection();

			String sql = "select * from tab_apkinfo order by id DESC";

			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			result.next();
			version = result.getString("apk_version");

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, null);

		}

		return version;
	}

	/**
	 * @param apkInfo
	 */
	public void updateapk(ApkInfo apkInfo) {

		Connection conn = null;
		PreparedStatement statement = null;

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = new Date();

		String updatetime = format.format(d1);

		try {
			conn = JdbcUtils.getConnection();

			String sql = "insert into tab_apkinfo(apk_name,apk_version,apk_updatetime) values (?,?,?)";

			statement = conn.prepareStatement(sql);

			statement.setString(1, apkInfo.getName());
			statement.setString(2, apkInfo.getVersion());
			statement.setString(3, updatetime);

			statement.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, null);

		}
	}
	
	/**
	 * 判断是否存在对应的表
	 * 
	 * @param tablename
	 * @return
	 */
	public boolean isexisttab(String tablename) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select tab_id from tab_slidetabs_info where tab_name=?";
			st = conn.prepareStatement(sql);
			st.setString(1, tablename);

			rs = st.executeQuery();

			if (rs.next()) {
				return true;

			}

		} catch (Exception e) {

			throw new RuntimeException(e);
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		return false;
	}

	/**
	 *
	 */
	public void savetableinfo(String tablename) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "insert into tab_slidetabs_info (tab_name) values (?)";
			st = conn.prepareStatement(sql);
			st.setString(1, tablename);
		

			st.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, null);
		}

	}

	/**
	 * @return
	 */
	public int getApkTotal() {

		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select count(*) from tab_apkinfo";

			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);

			}
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);

		}
		return 0;
	}

	/**
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<ApkInfo> getapkinfo(int currentPage, int pageSize) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<ApkInfo> apkInfos = new ArrayList<ApkInfo>();

		try {
			conn = JdbcUtils.getConnection();

			String sql = "select * from tab_apkinfo " + " limit "
					+ (currentPage - 1) * pageSize + " , " + pageSize;

			statement = conn.prepareStatement(sql);

			result = statement.executeQuery();
			while (result.next()) {

				ApkInfo apkInfo = new ApkInfo();
				apkInfo.setId(result.getInt("id"));
				apkInfo.setName(result.getString("apk_name"));
				apkInfo.setVersion(result.getString("apk_version"));
				apkInfo.setUpdatetime(result.getString("apk_updatetime"));

				apkInfos.add(apkInfo);

			}

			return apkInfos;

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, result);

		}

		return null;
	}

	public void appSettingConfirm(String imei, String setting) {
		Connection conn = null;
		PreparedStatement st = null;

		try {
			conn = JdbcUtils.getConnection();
			String sql = "update tab_deviceinfo set app_mode=?,app_modechanged_status=? where device_imei=?";
			st = conn.prepareStatement(sql);
			st.setInt(1, Integer.parseInt(setting));
			st.setInt(2, 1);

			st.setString(3, imei);
			st.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, null);
		}
	}

	public void appSettingReset(String imei, String setting) {
		Connection conn = null;
		PreparedStatement st = null;

		try {
			conn = JdbcUtils.getConnection();
			String sql = "update tab_deviceinfo set app_mode=?,app_modechanged_status=? where device_imei=?";
			st = conn.prepareStatement(sql);
			st.setInt(1, Integer.parseInt(setting));
			st.setInt(2, 0);

			st.setString(3, imei);
			st.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, null);
		}
	}

	public void InsertThmChgLogByClient(String imei, String setting) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			/*conn = JdbcUtils.getConnection();
			String sql = "InsertThmChgLogByClient(?,?)";
			st = conn.prepareStatement(sql);
			st.setString(1, imei);
			st.setInt(2, Integer.parseInt(setting));
			st.executeUpdate();
*/		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, null);
		}
	}

	public String GetSlideTheme(String imei) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select ifnull(app_mode,0) as theme from tab_deviceinfo where device_imei='"
					+ imei + "'";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		return "0";
	}

	public String GetSlideThemeStatus(String imei) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select ifnull(app_modechanged_status,0) as themestatus from tab_deviceinfo where device_imei='"
					+ imei + "'";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		return "0";
	}

	 /*
	     保存设备信息(如果不存在则插入，如果存在则更新)
	 */
	public void savedeviceinfo(String phoneimei, String phoneimsi,String phonenumber,
			String phonetype, String phoneversion, String curr_version,int update_key) {
		// 获取手机类型编号
		String phonetypeno = getdevtypeno(phonetype);
		if (phonetypeno == null) {
			int max = getmaxdevtypeno();
			max = max + 1;
			phonetypeno = max + "";
			adddevtypeno(max, phonetype);
		}
		// 获取操作系统编号
		String osno = getosno(phoneversion);
		if (osno == null) {
			int max = getmaxosno();
			max = max + 1;
			osno = max + "";
			addosno(max, phoneversion);
		}
		//插入或更新设备表
		Connection conn = null;
		PreparedStatement st = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = new Date();
		String entertime = format.format(d1);
		try {
			 boolean isexists =isexist(phoneimei);
			 if (!isexists) {
				 conn = JdbcUtils.getConnection();
				 String sqlString = "insert into  tab_deviceinfo(device_type,device_imei,device_imsi,device_os_version,mobile_area,mobile_type,enter_time,update_key) values(?,?,?,?,?,?,?,?)";
                    st = conn.prepareStatement(sqlString);
                    st.setString(1, phonetypeno);
					st.setString(2, phoneimei);
					st.setString(3, phoneimsi);
					st.setString(4, osno);
					st.setString(5, getareano(phonenumber));
					st.setString(6, getsimtypeno(phonenumber));
					st.setString(7, entertime);
					st.setInt(8, update_key);
					
					st.executeUpdate();
			}
			 else {
				 conn = JdbcUtils.getConnection();
				 String sqlString = "update tab_deviceinfo set device_imsi=?,device_os_version=?,mobile_area=?,mobile_type=? where device_imei=?";
                    st = conn.prepareStatement(sqlString);
					st.setString(1, phoneimsi);
					st.setString(2, osno);
					st.setString(3, getareano(phonenumber));
					st.setString(4, getsimtypeno(phonenumber));
					st.setString(5, phoneimei);
					st.executeUpdate();
			 }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, null);
		}
		//插入推送表
		try {
			 boolean isexists =isexistfyy(phoneimei);
			 if (!isexists) {
				    conn = JdbcUtils.getConnection();
					String sqlString = "insert into  tab_pushinfo(imei) values(?)";
					st = conn.prepareStatement(sqlString);
					st.setString(1, phoneimei);
					st.executeUpdate();
			 }
		} catch (Exception e) {
			log.error("插入推送表出现异常(FYY):"+e);
		} finally {
			JdbcUtils.release(conn, st, null);
		}

	}

	/**
	 * @param mobilearea
	 * @return 获取手机归属地编号
	 */
	private String getareano(String phonenumber) {
		
		if(phonenumber==null||phonenumber.length()<7) return "";
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();

			String sqlString = "select mobile_area from tab_pushmbno where mobile_num=?";
			st = conn.prepareStatement(sqlString);
			st.setString(1, phonenumber.substring(0, 7));
			rs = st.executeQuery();

			if (rs.next()) {
				return rs.getString("mobile_area");
			}

		} catch (Exception e) {
			log.error("获取手机归属地编号异常:"+e);
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		return "未知";
	}

	/**
	 * @param mobiletype
	 * @return 获取手机卡类型编号
	 */
	private String getsimtypeno(String phonenumber) {
		if(phonenumber==null||phonenumber.length()<7) return "";
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();

			String sqlString = "select mobile_type from tab_pushmbno where  mobile_num=?";
			st = conn.prepareStatement(sqlString);
			st.setString(1, phonenumber.substring(0, 7));
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getString("mobile_type");
			}
		} catch (Exception e) {
			log.error("获取手机卡类型编号出现异常:"+e);
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		return "未知";
	}

	/**
	 * @param max
	 * @param phonetype
	 * 添加新的系统编号
	 */
	private void addosno(int max, String ostype) {
		Connection conn = null;
		PreparedStatement st = null;

		try {
			conn = JdbcUtils.getConnection();

			String sqlString = "insert into  tab_pushmbos values(?,?)";
			st = conn.prepareStatement(sqlString);
			st.setString(1, max + "");
			st.setString(2, ostype);
			st.executeUpdate();

		} catch (Exception e) {
			log.error("添加新的系统编号出错:"+e);
		} finally {
			JdbcUtils.release(conn, st, null);
		}

	}

	/**
	 * @return
	 */
	private int getmaxosno() {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();

			String sqlString = "select max(id) from tab_pushmbos";
			st = conn.prepareStatement(sqlString);

			rs = st.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}

		} catch (Exception e) {
			log.error("获取最大操作系统号异常:"+e);
		} finally {
			JdbcUtils.release(conn, st, rs);
		}

		return 0;
	}

	/**
	 * @param phoneversion
	 * @return 获取操作系统编号
	 */
	private String getosno(String phoneversion) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();

			String sqlString = "select id from tab_pushmbos where name=?";
			st = conn.prepareStatement(sqlString);
			st.setString(1, phoneversion);
			rs = st.executeQuery();

			if (rs.next()) {
				return rs.getString("id");
			}

		} catch (Exception e) {
		   log.error("获取操作系统编号异常:"+e);
		} finally {
			JdbcUtils.release(conn, st, rs);
		}

		return null;
	}

	/**
	 * @param max
	 * @param phonetype
	 *            添加新的设备类型
	 */
	public void adddevtypeno(int max, String phonetype) {
		Connection conn = null;
		PreparedStatement st = null;

		try {
			conn = JdbcUtils.getConnection();

			String sqlString = "insert into tab_pushmbtype  values(?,?)";
			st = conn.prepareStatement(sqlString);
			st.setString(1, max + "");
			st.setString(2, phonetype);
			st.executeUpdate();

		} catch (Exception e) {
			log.error(" 添加新的设备类型异常:"+e);
		} finally {
			JdbcUtils.release(conn, st, null);
		}

	}

	/**
	 * @return 获取设备类型最大编号
	 */
	public int getmaxdevtypeno() {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();

			String sqlString = "select max(id) from tab_pushmbtype ";
			st = conn.prepareStatement(sqlString);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}

		} catch (Exception e) {
			log.error("获取设备类型最大编号出錯:"+e);
		} finally {
			JdbcUtils.release(conn, st, rs);
		}

		return 0;
	}

	/**
	 * @param phonetype
	 * @return获取手机类型编号
	 */
	public String getdevtypeno(String phonetype) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();

			String sqlString = "select id from tab_pushmbtype where name=?";
			st = conn.prepareStatement(sqlString);
			st.setString(1, phonetype);
			rs = st.executeQuery();

			if (rs.next()) {
				return rs.getString("id");
			}

		} catch (Exception e) {
			log.error("获取手机类型编号出錯:"+e);
		} finally {
			JdbcUtils.release(conn, st, rs);
		}

		return null;
	}

	/**
	 * @param tablename
	 * @return
	 * 
	 *         创建表
	 */
	public boolean createtable(String tablename, int id) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = JdbcUtils02.getConnection();

			String sqlString = "CREATE TABLE `"+tablename+"` ("
					+ "`slideinfo_id` int(11) NOT NULL AUTO_INCREMENT,"
					+ " `device_imei` varchar(50) DEFAULT NULL,"
					+ " `device_number` char(11) DEFAULT NULL,"
					+ "`pic_title` varchar(50) DEFAULT NULL,"
					+ "`pic_name` varchar(50) DEFAULT NULL,"
					+ "`slide_time` timestamp NULL DEFAULT NULL,"
				    + " `pic_score` tinyint(11) DEFAULT '0',"
				   + " PRIMARY KEY (`slideinfo_id`),"
					+ " KEY `imei` (`device_imei`) USING HASH,"
					+ "  KEY `slidetime` (`slide_time`) USING BTREE"
					+ ") ENGINE=MyISAM AUTO_INCREMENT=" + id
					+ " DEFAULT CHARSET=utf8";
			st = conn.prepareStatement(sqlString);
			 boolean success=st.execute();
			 
			 savetableinfo(tablename);
			  return success;

		} catch (Exception e) {
			return false;
		} finally {
			JdbcUtils.release(conn, st, null);
		}

	}

	/**
	 * @return
	 * 
	 *         获取上一月的最大id
	 */
	public int getpretabmaxid() {

		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String tab_name = null;
		try {
			conn = JdbcUtils.getConnection();

			String sqlString = "select tab_name from tab_slidetabs_info order by tab_id DESC";
			st = conn.prepareStatement(sqlString);

			rs = st.executeQuery();

			if (rs.next()) {
				tab_name = rs.getString("tab_name");
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.release(conn, st, rs);
		}

		try {
			conn = JdbcUtils02.getConnection();

			String sqlString = "select max(slideinfo_id) from " + tab_name;
			st = conn.prepareStatement(sqlString);

			rs = st.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}

		} catch (Exception e) {
			return 0;
		} finally {
			JdbcUtils.release(conn, st, rs);
		}

		return 0;
	}

	/**
	 * @param locations
	 * 
	 * 保存地理位置信息
	 */
	public void savelocationinfo(List<Location> locations) {
		PreparedStatement statement = null;
		Connection conn = null;

		try {
			conn = JdbcUtils02.getConnection();
			conn.setAutoCommit(false);
			
			for (Location location : locations) {
				String sql = "insert into tab_location values(null,?,?,?,?,?)";

				statement = conn.prepareStatement(sql);

				statement.setString(1, location.getLongitude()+"");
				statement.setString(2, location.getLatitude()+"");
				statement.setString(3, location.getPhonenum());
				statement.setString(4, location.getImei());
				statement.setString(5, location.getLocationtime());
				
				statement.executeUpdate();
			}

			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
			   e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {

			JdbcUtils02.release(conn, statement, null);

		}

		
	}

	/**
	 * @param screenonoffs
	 */
	public void savescreenonoffinfo(List<ScreenOnOff> screenonoffs) {
		PreparedStatement statement = null;
		Connection conn = null;

		try {
			conn = JdbcUtils02.getConnection();
			conn.setAutoCommit(false);
			
			for (ScreenOnOff screenOnOff : screenonoffs) {
				String sql = "insert into tab_screeninfo(screen_onoff,operator_time,device_imei,device_number) values(?,?,?,?)";

				statement = conn.prepareStatement(sql);

				statement.setInt(1, screenOnOff.getOnoff());
				statement.setString(2,screenOnOff.getTime());
				statement.setString(3, screenOnOff.getImei());
				statement.setString(4, screenOnOff.getPhonenumber());
				
				
				statement.executeUpdate();
			}

			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
			   e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {

			JdbcUtils02.release(conn, statement, null);

		}

		
	}

	/**
	 * @param appFlows
	 */
	public void saveappflows(List<AppFlow> appFlows) {
		
		String entertime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		PreparedStatement statement = null;
		Connection conn = null;
		
		try {
			conn = JdbcUtils02.getConnection();
			conn.setAutoCommit(false);
			
			for (AppFlow appFlow : appFlows) {
				
				String sql = "insert into tab_appflow(device_number,device_imei,app_name,app_flow,receive_time) values(?,?,?,?,?)";

				statement = conn.prepareStatement(sql);
				
				statement.setString(1, appFlow.getPhonenumber());
				statement.setString(2, appFlow.getImei());
				statement.setString(3, appFlow.getAppname());
				statement.setInt(4, appFlow.getFlow());
				statement.setString(5, entertime);
                statement.executeUpdate();
			}

			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
			   e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {

			JdbcUtils02.release(conn, statement, null);

		}
		
		
	}

	/**
	 * @param imei
	 * @return
	 */
	public String getUnitno(String imei) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String unitno = null;
		try {
			conn = JdbcUtils.getConnection();

			String sqlString = "select user_unit from tab_deviceinfo where device_imei='"+imei+"'";
			st = conn.prepareStatement(sqlString);
            
			rs = st.executeQuery();
	    

			while(rs.next()) {
				unitno = rs.getString("user_unit");
			 }
			

		 return unitno;
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		return null;
	}

	/**
	 * @param userClickTimes
	 */
	public void saveUserClickTime(List<UserClickTime> userClickTimes) {
		PreparedStatement statement = null;
		Connection conn = null;
		
		try {
			conn = JdbcUtils02.getConnection();
			conn.setAutoCommit(false);
			
			for (UserClickTime userclicktime : userClickTimes) {
				
				String sql = "insert into tab_url_clicktime(device_number,device_imei,click_url,click_time) values(?,?,?,?)";

				statement = conn.prepareStatement(sql);
				
				statement.setString(1, userclicktime.getPhonenum());
				statement.setString(2, userclicktime.getImei());
				statement.setString(3, userclicktime.getUrl());
				statement.setString(4, userclicktime.getClicktime());
                statement.executeUpdate();
			}

			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
			   e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {

			JdbcUtils02.release(conn, statement, null);

		}
		
	}

	/**
	 * @param seq
	 * @return
	 * 返回pics_url
	 */
	public JSONArray getArray(String seq) throws Exception{
		
	
		/*	
		
		"pics_url:" +
				"[" +
				
				"[\"http://www.sina.com\"]," +
				"[\"http://www.sina.com\",\"http://www.10010.com\"]," +
				"[\"http://www.sina.com\",\"http://www.10010.com\",\"http://m.sohu.com\"]," +
				"[\"http://www.sina.com\",\"http://www.10010.com\",\"http://m.sohu.com\",\"http://m.baidu.com\"]" +
				
				"]"
		*/
		
			 int queid=getqueid(seq);
			
			 String  picsid=getpicsid(queid);
			 
			 
			 String picids[]=picsid.split(",");
			 StringBuilder sbBuilder=new StringBuilder();
			 
			 sbBuilder.append("{").append("\"pics_url\":").append("[");
			 
			 for (int i = 0; i < picids.length; i++) {
				
				 
				    sbBuilder.append(getpicsUrl(picids[i])).append(",");
				 
			 }
			
			 sbBuilder.deleteCharAt(sbBuilder.lastIndexOf(","));
			 
			 sbBuilder.append("]").append("}");
			 
			// System.out.println(sbBuilder.toString());
			 
			JSONObject jsonObject = new JSONObject(sbBuilder.toString());
	
		return  jsonObject.getJSONArray("pics_url");
	}

	/**
	 * @param string
	 */
	private String getpicsUrl(String picid) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		picid=getrealpicid(picid);
		
		try{
		
		conn = JdbcUtils.getConnection();

		String sqlString = "select link,icon from tab_pushcontent where cid="+picid;
		st = conn.prepareStatement(sqlString);
        
		rs = st.executeQuery();
    

		while(rs.next()) {
			
			String link=rs.getString("link");
			String iconS=rs.getString("icon");
			if (iconS==null||"".equals(iconS)) {
				
				return "[\""+link+"\"]";
			}
			
			
			
			StringBuilder builder=new StringBuilder(iconS);
			
			builder.deleteCharAt(builder.lastIndexOf(","));
			
		   iconS=builder.toString();
		   
			String iconids[]=iconS.split(",");
			
			
			StringBuilder sbBuilde=new StringBuilder();
			
			sbBuilde.append("[");
			for (int i = 0; i < iconids.length; i++) {
				
				sbBuilde.append("\"").append(geticonurl(iconids[i])).append("\"").append(",");
			}
			sbBuilde.deleteCharAt(sbBuilde.lastIndexOf(","));
		    sbBuilde.append("]");
		  
			
			
			return sbBuilde.toString();
			
		 }
	
			} catch (Exception e) {
					e.printStackTrace();
				} finally {
					JdbcUtils.release(conn, st, rs);
					}
		return null;
	}

	/**
	 * @param picid
	 * @return
	 */
	private String getrealpicid(String picid) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();

			String sqlString = "select cid from tab_pushatom where aid="+picid;
			st = conn.prepareStatement(sqlString);
            
			rs = st.executeQuery();
	    

			while(rs.next()) {
				
				return rs.getString("cid");
			 }
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
	
		return null;
	}

	/**
	 * @param string
	 * @return
	 */
	private String  geticonurl(String iconid) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();

			String sqlString = "select icon_url from tab_iconinfo where icon_id="+iconid;
			st = conn.prepareStatement(sqlString);
            
			rs = st.executeQuery();
	    

			while(rs.next()) {
				
				return rs.getString("icon_url");
			 }
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
	
		return null;
		
	}

	/**
	 * @param queid
	 * @return
	 */
	private String getpicsid(int queid) {
		
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();

			String sqlString = "select que from tab_pushque where qid="+queid;
			st = conn.prepareStatement(sqlString);
            
			rs = st.executeQuery();
	    

			while(rs.next()) {
				
				String que=rs.getString("que");
				
				StringBuilder builder=new StringBuilder(que);
				
				builder.deleteCharAt(builder.lastIndexOf(","));
				
				return builder.toString();
			 }
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
	
		return null;
		
	}

	/**
	 * @param seq
	 * @return
	 */
	private int getqueid(String seq) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();

			String sqlString = "select queid from tab_pushlist where pid="+seq;
			st = conn.prepareStatement(sqlString);
            
			rs = st.executeQuery();
	    	while(rs.next()) {
				return  rs.getInt("queid");
			 }
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
	
		return -1;
	}

	public String[]  getPushAutoScreen(String imei,String tempstrx) {
	      String finalstr="";
		  String[] result=new String[3];
	      String[] ttemp=tempstrx.split("\\^");
    	  PhDao tempDao=new PhDao();
	      String initstr=tempDao.GetDefalutString(ttemp[0]);
	      String[] temp=initstr.split("\\*");
	      if(temp.length>3)
	      {
	          String pics_name="";
	    	  String pics_time="";
	          String display_order="";
	          String pics_url="";
	          String pics_weight="";
	          String pics_rgb="";
	    	  for(int i=3;i<temp.length;i++)
	    	  {
	    		  String[] atom=temp[i].split(",");
	    		  pics_time+=","+atom[1];
	    		  display_order+=",\""+atom[0]+"\"";
	    		  if(pics_name.indexOf(atom[0])==-1) {pics_name+=",\""+atom[0]+"\"";pics_url+=",\""+atom[3]+"\"";pics_weight+=","+atom[4];pics_rgb+=",\"#"+atom[5]+"\"";}
	    	  }
	    	  finalstr="sequence:\""+temp[0].replace('a','d')+"\",pics_name:["+pics_name.substring(1)+"]," 
	    	           + tempDao.GetIconStr(pics_name.substring(1))
	    	  	       + ",pics_weight:["+pics_weight.substring(1)+"],pics_rgb:["+pics_rgb.substring(1)+"],pics_time:["+pics_time.substring(1)+"],display_order:["+display_order.substring(1)+"]"
	      	           + ",begin_time:\""+ttemp[1]+"\",end_time:\""+ttemp[2]+"\"";
	      }
	      result[0]=finalstr;
	      result[1]="true";
	      result[2]=tempDao.GetTotalScore(imei);   
	      return  result; 	     
	}

	public int getScreenTotalMint(String phoneimei) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils02.getConnection();

			String sqlString = "select sum(cc) as ee FROM ("
				+"select (select timestampdiff(second,aa.operator_time,bb.operator_time) from tab_screeninfo as bb where operator_time>=aa.operator_time and bb.screen_onoff=0 and left(bb.operator_time,10)=left(Now(),10) limit 0,1) as cc "
				+"from tab_screeninfo as aa where device_imei='"+phoneimei+"' and left(operator_time,10)=left(Now(),10) and screen_onoff=1 order by operator_time"
				+") as dd where cc is not null";
			st = conn.prepareStatement(sqlString);            
			rs = st.executeQuery();
	    	while(rs.next()) {
				return  rs.getInt("ee");
			 }		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils02.release(conn, st, rs);
		}
		return 0;
	}

	public String getPushAutoScreenQue(int screenStart, int screenEnd,String imei) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String nowstr=df.format(new Date());
			String sqlString = "select CONCAT(que,'^','"+nowstr+"','^',DATE_ADD('"+nowstr+"',INTERVAL duration SECOND)) as autostr,DATE_ADD('"+nowstr+"',INTERVAL duration SECOND) as xx from tab_pushauto_screen where mini>="+screenStart+" and mini<="+screenEnd;
			st = conn.prepareStatement(sqlString);            
			rs = st.executeQuery();	    
			while(rs.next()) {
				String xxx=rs.getString("autostr");
				if(xxx!=null&&!xxx.equals(""))  ReplaceIntoAuto(imei,rs.getString("xx"));
				return  xxx;
			 }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}	
		return "";
	}
	
	public String getPushAutoScreenQueId(int screenStart, int screenEnd,String imei) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sqlString = "select que from tab_pushauto_screen where mini>="+screenStart+" and mini<="+screenEnd;
			st = conn.prepareStatement(sqlString);            
			rs = st.executeQuery();	    
			while(rs.next()) {
				return  rs.getString("que");
			 }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}	
		return "";
	}
	
	public void ReplaceIntoAuto(String imei ,String nowstr) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement st = null;
		try {
				conn = JdbcUtils.getConnection();
				//String sql = "delete from tab_pushcontent where cid=?";
				String sql = "replace into tab_pushauto_time(imei,exptime) value('"+imei+"','"+nowstr+"')";
				st = conn.prepareStatement(sql);
				st.executeUpdate();
			} catch (SQLException e) {
					e.printStackTrace();
			} finally {
					JdbcUtils.release(conn, st, null);
            }		
	}
	
	
	
	/*
    保存或更新手机端公钥(如果不存在则插入，如果存在则更新)
*/
public static int saveOrUpdateMobilePublicKey(String imei, String publickey) {
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date d1 = new Date();
	String currenttime = format.format(d1);
	int row_count=0;//表示sql语句执行影响到的行数
	Connection conn = null;
	PreparedStatement st = null;
	try {
		 boolean isexists =isExistMobilePublicKeyForCertainImei(imei);
		 if (!isexists) {
			 conn = JdbcUtils.getConnection();
			 String sqlString = "insert into  tab_mobilepublickey(imei,public_key,save_time) values(?,?,?)";
             st = conn.prepareStatement(sqlString);
             st.setString(1, imei);
		     st.setString(2, publickey);
			 st.setString(3, currenttime);
			 row_count=st.executeUpdate();//语句执行影响到的行数
			 System.out.println("tab_mobilepublickey添加记录，影响的行数："+row_count);
		}else {
			 conn = JdbcUtils.getConnection();
			 String sqlString = "update tab_mobilepublickey set public_key=?,update_time=? where imei=?";
             st = conn.prepareStatement(sqlString);
			 st.setString(1, publickey);
			 st.setString(2, currenttime);
			 st.setString(3, imei);
			 row_count=st.executeUpdate();//语句执行影响到的行数
			 System.out.println("tab_mobilepublickey更新记录，影响的行数："+row_count);
		 }
	} catch (Exception e) {
		throw new RuntimeException(e);
	} finally {
		JdbcUtils.release(conn, st, null);
	}
	
	return row_count;
	
}


/**
 * 判断指定imei对应的手机终端公钥在tab_mobilepublickey表中是否存在
 */
public static boolean isExistMobilePublicKeyForCertainImei(String imei) {
	Connection conn = null;
	PreparedStatement st = null;
	ResultSet rs = null;
	try {
		conn = JdbcUtils.getConnection();
		String sql = "select * from tab_mobilepublickey where imei=?";
		st = conn.prepareStatement(sql);
		st.setString(1, imei);

		rs = st.executeQuery();

		if (rs.next()) {
			return true;

		}

	} catch (Exception e) {

		throw new RuntimeException(e);
	} finally {
		JdbcUtils.release(conn, st, rs);
	}
	return false;
}

/**
* 获取指定imei手机终端的publickey
*/
public static String getPublicKeyForCertainImei(String imei) {
	
	Connection conn = null;
	PreparedStatement st = null;
	ResultSet rs = null;
	try {
		conn = JdbcUtils.getConnection();
		String sqlString = "select public_key from tab_mobilepublickey where imei=?";
		st = conn.prepareStatement(sqlString);
		st.setString(1,imei);
		rs = st.executeQuery();

		if (rs.next()) {
			return rs.getString("public_key");
		}

	} catch (Exception e) {
		throw new RuntimeException(e);
	} finally {
		JdbcUtils.release(conn, st, rs);
	}
	return null;
}



/*
保存或更新共享密钥(如果不存在则插入，如果存在则更新)
*/
public static int saveOrUpdateShareKey(String imei, String share_key) {

SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Date d1 = new Date();
String currenttime = format.format(d1);
int row_count=0;//表示sql语句执行影响到的行数
Connection conn = null;
PreparedStatement st = null;
try {
	 boolean isexists =isExistShareKeyForCertainImei(imei);
	 if (!isexists) {
		 conn = JdbcUtils.getConnection();
		 String sqlString = "insert into  tab_sharekey(imei,share_key,save_time) values(?,?,?)";
         st = conn.prepareStatement(sqlString);
         st.setString(1, imei);
	     st.setString(2, share_key);
		 st.setString(3, currenttime);
		 row_count=st.executeUpdate();//语句执行影响到的行数
		 System.out.println("tab_sharekey添加记录，影响的行数："+row_count);
	}else {
		 conn = JdbcUtils.getConnection();
		 String sqlString = "update tab_sharekey set share_key=?,update_time=? where imei=?";
         st = conn.prepareStatement(sqlString);
		 st.setString(1, share_key);
		 st.setString(2, currenttime);
		 st.setString(3, imei);
		 row_count=st.executeUpdate();//语句执行影响到的行数
		 System.out.println("tab_sharekey更新记录，影响的行数："+row_count);
	 }
} catch (Exception e) {
	throw new RuntimeException(e);
} finally {
	JdbcUtils.release(conn, st, null);
}

return row_count;

}


/**
* 判断指定imei对应的共享密钥在tab_sharekey表中是否存在
*/
public static boolean isExistShareKeyForCertainImei(String imei) {
Connection conn = null;
PreparedStatement st = null;
ResultSet rs = null;
try {
	conn = JdbcUtils.getConnection();
	String sql = "select * from tab_sharekey where imei=?";
	st = conn.prepareStatement(sql);
	st.setString(1, imei);

	rs = st.executeQuery();

	if (rs.next()) {
		return true;

	}

} catch (Exception e) {

	throw new RuntimeException(e);
} finally {
	JdbcUtils.release(conn, st, rs);
}
return false;
}

/**
* 获取指定imei手机终端与服务器的共享密钥
*/
public static String getShareKeyForCertainImei(String imei) {

Connection conn = null;
PreparedStatement st = null;
ResultSet rs = null;
String shareKeyEncryptBase64=null;
String shareKey=null;
try {
	conn = JdbcUtils.getConnection();
	String sqlString = "select share_key from tab_sharekey where imei=?";
	st = conn.prepareStatement(sqlString);
	st.setString(1,imei);
	rs = st.executeQuery();

	if (rs.next()) {
		shareKeyEncryptBase64=rs.getString("share_key");
		shareKey= new String(ECC_SHA.decrypt(ECC_SHA.decryptBASE64(shareKeyEncryptBase64),getServerPrivateKey()));//使用Server端私钥解密共享密钥	
		return shareKey;
	}

} catch (Exception e) {
	throw new RuntimeException(e);
} finally {
	JdbcUtils.release(conn, st, rs);
}
return null;
}


/*
保存或更新Server端公钥和私钥(如果不存在则插入，如果存在则更新)
*/
public static int saveOrUpdatePublicPrivateKey(String privateKey, String publicKey) {

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date d1 = new Date();
	String currenttime = format.format(d1);
	int row_count=0;//表示sql语句执行影响到的行数
	Connection conn = null;
	PreparedStatement st = null;
	try {
		 boolean isexists =isExistPublicPrivateKey();
		 if (!isexists) {
			 conn = JdbcUtils.getConnection();
			 String sqlString = "insert into  tab_serverkeys(private_key,public_key,save_time) values(?,?,?)";
	         st = conn.prepareStatement(sqlString);
	         st.setString(1, privateKey);
		     st.setString(2, publicKey);
			 st.setString(3, currenttime);
			 row_count=st.executeUpdate();//语句执行影响到的行数
			 System.out.println("tab_serverkeys添加记录，影响的行数："+row_count);
		}else {
			 conn = JdbcUtils.getConnection();
			 String sqlString = "update tab_serverkeys set private_key=?,public_key=?,update_time=?";
	         st = conn.prepareStatement(sqlString);
			 st.setString(1, privateKey);
			 st.setString(2, publicKey);
			 st.setString(3, currenttime);
			 row_count=st.executeUpdate();//语句执行影响到的行数
			 System.out.println("tab_serverkeys更新记录，影响的行数："+row_count);
		 }
	} catch (Exception e) {
		throw new RuntimeException(e);
	} finally {
		JdbcUtils.release(conn, st, null);
	}
	
	return row_count;

}


/**
* 判断在tab_sharekey表中是否存在Server端公钥和私钥记录
*/
public static boolean isExistPublicPrivateKey() {

	Connection conn = null;
	PreparedStatement st = null;
	ResultSet rs = null;
	try {
		conn = JdbcUtils.getConnection();
		String sql = "select * from tab_serverkeys";
		st = conn.prepareStatement(sql);
	
		rs = st.executeQuery();
	
		if (rs.next()) {
			return true;
	
		}
	
	} catch (Exception e) {
	
		throw new RuntimeException(e);
	} finally {
		JdbcUtils.release(conn, st, rs);
	}
	return false;
}

/**
* 获取Server端的私钥
*/
public static String getServerPrivateKey() {

	Connection conn = null;
	PreparedStatement st = null;
	ResultSet rs = null;
	String privateKeyBase64=null;
	
	try {
		conn = JdbcUtils.getConnection();
		String sqlString = "select private_key from tab_serverkeys";
		st = conn.prepareStatement(sqlString);
		rs = st.executeQuery();
	
		if (rs.next()) {
			privateKeyBase64=rs.getString("private_key");
			return privateKeyBase64;
		}
	
	} catch (Exception e) {
		throw new RuntimeException(e);
	} finally {
		JdbcUtils.release(conn, st, rs);
	}
	return null;
}


/**
* 获取Server端的公钥
*/
public static String getServerPublicKey() {

	Connection conn = null;
	PreparedStatement st = null;
	ResultSet rs = null;
	String publicKeyBase64=null;
	
	try {
		conn = JdbcUtils.getConnection();
		String sqlString = "select public_key from tab_serverkeys";
		st = conn.prepareStatement(sqlString);
		rs = st.executeQuery();
	
		if (rs.next()) {
			publicKeyBase64=rs.getString("public_key");
			return publicKeyBase64;
		}
	
	} catch (Exception e) {
		throw new RuntimeException(e);
	} finally {
		JdbcUtils.release(conn, st, rs);
	}
	return null;
}

/**
 * 判断tab_deviceinfo表中是否存在于手机信息记录
 * 
 * @param deviceimei
 * @param devicenumber
 * @param devicetype
 * @return
 */
public boolean isexistDeviceRecord() {
	Connection conn = null;
	PreparedStatement st = null;
	ResultSet rs = null;
	try {
		conn = JdbcUtils.getConnection();
		String sql = "select * from tab_deviceinfo ";
		st = conn.prepareStatement(sql);
		

		rs = st.executeQuery();

		if (rs.next()) {
			return true;

		}

	} catch (Exception e) {

		throw new RuntimeException(e);
	} finally {
		JdbcUtils.release(conn, st, rs);
	}
	return false;
}

public static boolean useNewServerPublicKeyDealOldShareKey(String newPublicKey ){
	
	boolean flag=true;
	String oldPrivateKey=getServerPrivateKey();
	Connection conn = null;
	PreparedStatement st = null;
	ResultSet rs = null;
	try {
		conn = JdbcUtils.getConnection();
		String sql = "select imei,share_key from tab_sharekey ";
		st = conn.prepareStatement(sql);
		rs = st.executeQuery();

		while(rs.next()) {
			
			String imei=rs.getString("imei");
			String initialData=new String(ECC_SHA.decrypt(ECC_SHA.decryptBASE64(rs.getString("share_key")), oldPrivateKey));
			String newStr = new String(initialData.getBytes(), "UTF-8");  
			System.out.println("tab_sharekey中"+imei+"对应的的共享密钥是："+newStr);
			int rowcount=PhoneDao.saveOrUpdateShareKey(imei,ECC_SHA.encryptBASE64(ECC_SHA.encrypt(newStr.getBytes(), newPublicKey)));//保存或更新此imei对应的共享密钥
			if(rowcount>=1){
				System.out.println("使用新的服务器端公钥，加密已经存在的共享密钥成功");
			}else{
				System.out.println("使用新的服务器端公钥，加密已经存在的共享密钥失败!");
				flag=false;
			}
		}

	} catch (Exception e) {

		throw new RuntimeException(e);
		
		
	} finally {
		JdbcUtils.release(conn, st, rs);
	}
	
	return flag;
}


	
}
