package cn.ncut.wxdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

 
import cn.ncut.devicedomain.MobileModel;
import cn.ncut.pushdomain.JsonModel;
import cn.ncut.utils.JdbcUtils;
import cn.ncut.utils.JdbcUtils02;
import cn.ncut.utils.MobileInfo;
import cn.ncut.wxdomain.App;
import cn.ncut.wxdomain.Basic;
import cn.ncut.wxdomain.HabitCount;
import cn.ncut.wxdomain.HabitItem;
import cn.ncut.wxdomain.MapItem;
import cn.ncut.wxdomain.Prefer;
import cn.ncut.wxdomain.UserMapItem;
import cn.ncut.wxdomain.UserProfile;
import cn.ncut.wxdomain.UsersApp;
import cn.ncut.wxdomain.UsersPrefer;

public class UserProfileDao {

	/**
	 * 根据手机号、日期获取单个人的单天的经纬度
	 * @param phone_num
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	public List<MapItem> GetSingleMapList(String phone_num, String date){
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<MapItem> lists = new ArrayList();
		String sql=null;
		try {
			conn = JdbcUtils02.getConnection();
			//查找经纬度
			sql="SELECT * FROM tab_location  WHERE phone_num='"
			+phone_num
			+"' AND locationtime LIKE '"
			+date
			+"%' ";
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			while (result.next()) {
				MapItem mapItem = new MapItem();
				String phone_num1=result.getString("phone_num");
				mapItem.setPhone_num(phone_num1);
				String latitude=result.getString("latitude");
				mapItem.setLatitude(latitude);
				String longitude=result.getString("longitude");
				mapItem.setLongitude(longitude);
				mapItem.setDate(result.getString("locationtime"));
		//		mapItem.setAddress(result.getString("address"));
				lists.add(mapItem);
			}
			
			return lists;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils02.release(conn, statement, result);
		}
		return lists;
	}
	
	/**
	 * 获得单独用户一个月的轨迹
	 * @param phone_num
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	public List<UserMapItem> GetSingleUserMonthMap(String phone_num, String date) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<UserMapItem> lists = new ArrayList();
		
		try {
			conn = JdbcUtils02.getConnection();
			//查找经纬度
			
			String sql="SELECT SUM(COUNT) c,address,locationtime FROM tab_locationcount  WHERE phone_num='"
			+phone_num
			+"' AND locationtime like '"
			+date
			+"%' GROUP BY (address) order by c desc";
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			while (result.next()) {
				UserMapItem mapItem = new UserMapItem(); 
				mapItem.setPhone_num(phone_num); 
				mapItem.setAddress(result.getString("address")); 
				mapItem.setDate(result.getString("locationtime"));
				mapItem.setCount(result.getInt("c"));
				lists.add(mapItem);
			}
			
			return lists;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils02.release(conn, statement, result);
		}
		return lists;
	}

	public List<MapItem> GetMapList(String startDate, String endDate){
		// TODO Auto-generated method stub
				Connection conn = null;
				PreparedStatement statement = null;
				ResultSet result = null;
				List<MapItem> lists = new ArrayList();
				String sql=null;
				try {
					conn = JdbcUtils02.getConnection();
					//查找经纬度
					sql="SELECT * FROM tab_location  WHERE locationtime between '"
					+startDate
					+" 00:00:00' and '"
					+endDate
					+" 23:59:59'";
					statement = conn.prepareStatement(sql);
					result = statement.executeQuery();
					while (result.next()) {
						MapItem mapItem = new MapItem();
						String phone_num1=result.getString("phone_num");
						mapItem.setPhone_num(phone_num1);
						String latitude=result.getString("latitude");
						mapItem.setLatitude(latitude);
						String longitude=result.getString("longitude");
						mapItem.setLongitude(longitude);
						mapItem.setDate(result.getString("locationtime"));
						mapItem.setAddress(result.getString("address"));
						lists.add(mapItem);
					}
					
					return lists;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					JdbcUtils02.release(conn, statement, result);
				}
			return lists;	
	}

	/**
	 * 获取单个用户某个月的数据
	 * 时间维度
	 * @param device_number
	 * @param date
	 * @return
	 * @throws SQLException 
	 */
	public List<HabitCount> getUserHabit(String device_number, String date){
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<HabitCount> list = new ArrayList();
		String sql=null;
		try {
			conn = JdbcUtils02.getConnection();
			sql="SELECT * FROM tab_screencount  WHERE device_number='"
					+device_number
					+"' and date like'"
					+date
					+"%'";
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			while(result.next()){
				HabitCount h=new HabitCount();
				h.setDevice_number(result.getString("device_number"));
				h.setDate(result.getString("date"));
				h.setDura_time(result.getInt("dura_time"));
				h.setTimes(result.getInt("times"));
				System.out.println(h.getDevice_number()+h.getDate());
				list.add(h);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils02.release(conn, statement, result);
		}
		
	
		return list;
	}

	/**
	 * 时间维度
	 * 获取当月每两个小时之间的使用手机次数
	 * @param device_number
	 * @param date
	 * @return
	 * @throws SQLException 
	 */
	public List<HabitItem> getUserHourHabit(String device_number, String date) throws SQLException {
		// TODO Auto-generated method stub 
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<HabitItem> list = new ArrayList();
		String sql=null;
		conn = JdbcUtils02.getConnection();
		sql="SELECT * FROM tab_screen_times  WHERE device_number='"
						+device_number
						+"' and date like '"
						+date
						+"%' order by time_id asc";
		statement = conn.prepareStatement(sql);
		result = statement.executeQuery();
		while(result.next()){
			HabitItem h=new HabitItem();
			h.setDevice_number(result.getString("device_number"));
			h.setDate(result.getString("date"));
			h.setTime_id(result.getInt("time_id"));
			h.setTime(result.getString("time"));
			h.setNums(result.getInt("nums"));
			System.out.println(h.getDevice_number()+h.getDate());
			list.add(h);
		}
			
		return list;
	}
	/**
	 * 图片
	 * 按照日期获取用户的查看图片类型
	 * @param device_number
	 * @param startdate
	 * @param enddate
	 * @return 
	 */
	public List<Prefer> getUserPrefer(String device_number,
		String startdate, String enddate) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<Prefer> list = new ArrayList();
		String sql=null;
		try {
			conn = JdbcUtils02.getConnection();
			sql="SELECT * FROM tab_picprefer  WHERE device_number='"
					+device_number
					+"' and date between ? and ? order by date asc";
			statement = conn.prepareStatement(sql);
			statement.setString(1, startdate);
			statement.setString(2, enddate);
			result = statement.executeQuery();
			while(result.next()){
				String cl_num=getPicClassify(result.getString("classify"));
				System.out.println("cl_num:"+cl_num.length());
				if(cl_num != null && cl_num.length() != 0){
					Prefer h=new Prefer();
					h.setDevice_number(result.getString("device_number"));
					h.setDate(result.getString("date"));
					h.setClassify(cl_num);
					h.setScan_num(result.getInt("pic_nums"));
					System.out.println(h.getDevice_number()+h.getDate());
					list.add(h);
				}
				
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils02.release(conn, statement, result);
		}
		return list;
		
	}
	public String getPicClassify(String classify_num){
		Connection conn=null;
		PreparedStatement statement=null;
		ResultSet result=null;
		try {
			conn = JdbcUtils.getConnection();
			String sql1="select name from tab_pushcontentclass where value='"+classify_num+"'";
			statement = conn.prepareStatement(sql1); 
			result = statement.executeQuery();
			String classify=null;
			if(result.next()){
				classify=result.getString("name");
			}
			return classify;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.release(conn, statement, result);
		}
		
		
		return "股票";
	}
	/**
	 * 获得用户的基本信息
	 * @return
	 */
	public List<Basic> getUsersBasic() {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<Basic> list = new ArrayList();
		String sql=null;
		try {
			conn = JdbcUtils.getConnection();
			sql="SELECT device_number,device_type,mobile_area,mobile_type,user_birth,user_sex" +
					" FROM tab_deviceinfo where device_number is not null";
			
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			while(result.next()){
				String device_number=result.getString("device_number"); 
				if(device_number != null && device_number.length() != 0){
					Basic b=new Basic();
					b.setSex(result.getInt("user_sex"));
					b.setAge(getAgeType(result.getString("user_birth")));
					b.setDevice_number(device_number);
					b.setArea(getMbarea(result.getString("mobile_area")));
					String stype=result.getString("device_type");
					String type=getDeviceType(stype);
					b.setMobile(getDeviceType(result.getString("device_type")));
					b.setOperator(getMbtype(device_number));
					list.add(b);
				}
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtils.release(conn, statement, result);
		}
		return list;
	}

	/**
	 * 计算并划分年龄
	 * @param result
	 * @throws SQLException
	 */
	private int getAgeType(String user_birth){
		//根据出生年份算用户年龄
		if (user_birth!=null&&user_birth.length()>=4) {
			 String birth=user_birth.substring(0, 4);
			 Date date=new Date();
			 int nowyear=date.getYear()+1900;
		     int age=nowyear-Integer.parseInt(birth);
		   
		     if(age>18&&age<40)
		    	 return 1;
		     else if(age>=40&&age<65)
		    	 return 2;
		     else if(age>65)
		    	 return 3;
		     else
		    	 return 0;
		 }else {
			return 0;
		} 
	}
	/**
	 * @param string
	 * 获取归属地
	 */
	public String getMbarea(String str) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			
			String sqlString="select name from tab_pusharea where id=?";
			st = conn.prepareStatement(sqlString);
			st.setString(1, str);
			rs = st.executeQuery();
			
			if (rs.next()) {
				return rs.getString("name");
			 }
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		return "未知";
	}
	/**
	 * @param string
	 * 获取手机类型
	 */
	public String getDeviceType(String str) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		if(str==""||str.length()==0)
			return "其他";
		String s=str.substring(0,2);
	//	if(s.equals("99"))
	//		s=str; 
		try {
			conn = JdbcUtils.getConnection();
			
			String sqlString="select name from tab_pushmbtype where id=?";
			st = conn.prepareStatement(sqlString);
			st.setString(1, s);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getString("name");
			 }else{
				 System.out.println(str+"--"+s);
				 return "其他";
			 }
				 
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		 
	}
	/**
	 * @param string
	 * 获取手机卡类型
	 */
	public String getMbtype(String str) {
		
/*		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			
			String sqlString="select name from tab_pushprovider where id=?";
			st = conn.prepareStatement(sqlString);
			st.setString(1, str);
			rs = st.executeQuery();
			
			if (rs.next()) {
				String name=rs.getString("name");
				if(name.contains("移动"))
					return "中国移动";
				else if(name.contains("联通"))
					return "中国移动";
				else if(name.contains("电信"))
					return "中国电信";
			 }
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		return "未知";*/
		MobileInfo util=new MobileInfo();
		return util.validateMobile(str);
	}

	public List<HabitItem> GetUsersList(String mobileQuery, String date) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null; 
		List<HabitItem> RecList = new ArrayList<HabitItem>();
		List<String> dlist=getPhoneList(mobileQuery);
		try {
			conn= JdbcUtils02.getConnection();
			for(String d:dlist){
				String sql="SELECT * FROM tab_screen_times  WHERE device_number='"
						+d
						+"' and date like '"+date+"%' order by time_id asc";
				statement = conn.prepareStatement(sql);
				result = statement.executeQuery();
				while(result.next()){
					HabitItem h=new HabitItem();
					h.setDevice_number(d);
					h.setDate(result.getString("date"));
					h.setTime_id(result.getInt("time_id"));
					h.setTime(result.getString("time"));
					h.setNums(result.getInt("nums")); 
					RecList.add(h);
				}
			}
			return RecList;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JdbcUtils02.release(conn, statement, result);
			}
			return null;
			/*		try {
			conn= JdbcUtils02.getConnection(); 
			String sql="SELECT * FROM tab_screen_times  WHERE date like '"+date+"%' order by time_id asc";
				statement = conn.prepareStatement(sql);
				result = statement.executeQuery();
				while(result.next()){
					HabitItem h=new HabitItem();
					h.setDate(result.getString("date"));
					h.setTime_id(result.getInt("time_id"));
					h.setTime(result.getString("time"));
					h.setNums(result.getInt("nums")); 
					RecList.add(h);
				}
			return RecList;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JdbcUtils02.release(conn, statement, result);
			}
			return null;*/ 
	}

	/**
	 * 获取用户的每天使用手机时长  计算这一组人每天使用手机时长、次数、每次使用手机时长
	 * @param mobileQuery
	 * @param dates 
	 * @return
	 */
	public List<HabitCount> GetUsersUseList(String mobileQuery, String dates) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null; 
		List<HabitCount> RecList = new ArrayList<HabitCount>();
		List<String> dlist=getPhoneList(mobileQuery);
		try {
			conn= JdbcUtils02.getConnection();
			for(String d:dlist){
				String sql="SELECT * FROM tab_screencount  WHERE device_number='"
						+d
						+"' and date like '"+dates+"%' order by date";
				statement = conn.prepareStatement(sql);
				result = statement.executeQuery();
				while(result.next()){
					HabitCount h=new HabitCount();
					h.setDevice_number(d);
					h.setDate(result.getString("date"));
					h.setDura_time(result.getInt("dura_time"));
					h.setTimes(result.getInt("times")); 
					RecList.add(h);
				}
			}
			return RecList;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JdbcUtils02.release(conn, statement, result);
			}
			return null;
	}
	/**
	 * 获取满足条件的手机号码
	 * @param mobileQuery
	 * @return
	 */
	public List<String> getPhoneList(String mobileQuery){
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<String> dlist=new ArrayList<String>();
		try {
			conn = JdbcUtils.getConnection();
			String sqlString="select device_number " +
					"from tab_deviceinfo "+mobileQuery ;
			System.out.println(mobileQuery);
			statement = conn.prepareStatement(sqlString);
			result = statement.executeQuery();
			while (result.next()) {
				String device_number=result.getString("device_number");
				if(device_number.length()!=0&&device_number!=null){
					dlist.add(device_number);
					}
			}
			return dlist;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.release(conn, statement, result);
		}
		return null;
	}

	/**
	 * 获取用户使用app的情况
	 * @param device_number
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	public List<Prefer> getUserApp(String device_number, String startdate,
			String enddate) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<Prefer> list = new ArrayList();
		String sql=null;
		try {
			conn = JdbcUtils02.getConnection();
			sql="SELECT * FROM tab_countapps  WHERE device_number='"
					+device_number
					+"' and use_date between ? and ? order by use_date asc";
			statement = conn.prepareStatement(sql);
			statement.setString(1, startdate);
			statement.setString(2, enddate);
			result = statement.executeQuery();
			while(result.next()){
				String classify=formartAppClas(result.getString("app_classify"),conn); 
				if(classify != null && classify.length() != 0){
					Prefer h=new Prefer();
					h.setDevice_number(result.getString("device_number"));
					h.setDate(result.getString("use_date"));
					h.setClassify(classify);
					h.setScan_num(result.getInt("use_nums"));
					System.out.println(h.getDevice_number()+h.getDate());
					list.add(h);
				}
			}  
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.release(conn, statement, result);
		}
		return list;
	}
	public String formartAppClas(String appcode,Connection conn){
	
		String sql1="select category from tab_app_classify where app_code='"+appcode+"'";
		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql1);
			ResultSet result = statement.executeQuery();
			String classify=null;
			if(result.next()){
				classify=result.getString("category");
			}
			return classify;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return "桌面美化";
	}

	/**
	 * 获得不同类别用户使用app的洗好
	 * @param mobileQuery
	 * @param date 
	 * @return
	 */
	public List<UsersApp> GetUsersAppList(String mobileQuery, String date) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null; 
		List<UsersApp> RecList = new ArrayList<UsersApp>();
		List<String> dlist=getPhoneList(mobileQuery);
		try {
			conn= JdbcUtils02.getConnection();
			for(String d:dlist){
				String sql="SELECT * FROM tab_countapps  WHERE device_number='"
						+d
						+"' and use_date like '"+date+"%'";
				statement = conn.prepareStatement(sql);
				result = statement.executeQuery();
				while(result.next()){
					String classify=formartAppClas(result.getString("app_classify"),conn);
					if(classify != null && classify.length() != 0){
						UsersApp h=new UsersApp();
						h.setDevice_number(result.getString("device_number"));
						h.setDate(result.getString("use_date"));
						h.setClassify(classify);
						h.setNums(result.getInt("use_nums"));
						System.out.println(h.getDevice_number()+h.getDate());
						RecList.add(h);
					}
				}
			}
			return RecList;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JdbcUtils02.release(conn, statement, result);
			}
			return null;
	}
	
	public List<UsersApp> GetUsersPicList(String mobileQuery, String date) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null; 
		List<UsersApp> RecList = new ArrayList<UsersApp>();
		List<String> dlist=getPhoneList(mobileQuery);
		int size=dlist.size();
		try {
			conn= JdbcUtils02.getConnection();
			for(String d:dlist){
				String sql="SELECT * FROM tab_picprefer  WHERE device_number='"
						+d
						+"' and date like '"+date+"%'";
				statement = conn.prepareStatement(sql);
				result = statement.executeQuery();
				while(result.next()){
					String classify=getPicClassify(result.getString("classify"));
					if(classify != null && classify.length() != 0){
						UsersApp h=new UsersApp();
						h.setDevice_number(result.getString("device_number"));
						h.setDate(result.getString("date"));
						h.setClassify(classify);
						h.setNums(result.getInt("pic_nums"));
						System.out.println("+++++");
						RecList.add(h);
					}
				}
			}
			return RecList;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JdbcUtils02.release(conn, statement, result);
			}
			return null;
	}
/**
 * 获取用户画像
 * @param device_number
 * @param date
 * @return
 */
	public UserProfile GetUserProfile(String device_number, String date) {
		// TODO Auto-generated method stub
		//按照手机号码获取用户的基本信息
		UserProfile up=new UserProfile();
		Basic user=getBasic(device_number);
		//按照手机号、日期获取图片偏好
		String startDate=date+"-01";
		String endDate=date+"-31";
		List<Prefer> listp=getPic(device_number,startDate,endDate);
		//根据手机号日期获取用户app
		List<App> lista=getApp(device_number, startDate, endDate); 
		//根据手机号、月份获取手机使用习惯
			List<HabitCount> listh= getUserHabit(device_number,date);
			up.setHabit(listh);
		//根据手机号日期统计用户地理位置
			List<UserMapItem> listd=GetSingleUserMonthMap(device_number,date);
			up.setMap(listd);
		up.setBasic(user);
		up.setPrefer(listp);
		up.setApp(lista);
		
		return up;
	}
	public UserProfile GetUserProfileNew(String device_number, String date) {
		// TODO Auto-generated method stub
		//按照手机号码获取用户的基本信息
		UserProfile up=new UserProfile();
		Basic user=getBasic(device_number);
		//按照手机号、日期获取图片偏好
		List<Prefer> listp=getPic1(device_number,date);
		//根据手机号日期获取用户app
		List<App> lista=getApp1(device_number,date); 
		//根据手机号、月份获取手机使用习惯
			List<HabitCount> listh= getUserHabit(device_number,date);
			up.setHabit(listh);
		//根据手机号日期统计用户地理位置
			List<UserMapItem> listd=GetSingleUserMonthMap(device_number,date);
			up.setMap(listd);
		up.setBasic(user);
		up.setPrefer(listp);
		up.setApp(lista);
		
		return up;
	}
	private List<App> getApp1(String device_number, String date) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<App> list = new ArrayList();
		String sql=null;
		String dateAgo=getOneMonthAgo(date);

		try {
			conn = JdbcUtils02.getConnection();
			sql="SELECT classify FROM tab_userprofile  WHERE device_number='"
				+device_number
				+"' and date = '"+date+"'";
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			if(result.next()){
				String[] classifys=result.getString("classify").split(";");
				for(int i=0;i<classifys.length;i++){
					String classify=classifys[i].split(":")[0];
					if(classify.startsWith("004")){
						App h=new App();
						float weight=Float.parseFloat(classifys[i].split(":")[1]);
						float weightAgo=getLastWeight(classify,dateAgo,device_number);
						if(weightAgo==-1||weight>=weightAgo)
							h.setNums(0);//0表示增加，-1表示减少
						else
							h.setNums(-1); 
						h.setClassify(formartAppClas(classify,conn));
						list.add(h);
										
					}
				}
			}
			return list;
		} catch (SQLException e) {
						// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils02.release(conn, statement, result);
		}
		return list;
	}

	/**
	 * 从userprofile表获取用户的图片
	 * @param device_number
	 * @param date
	 * @return
	 */
	private List<Prefer> getPic1(String device_number, String date) {
		// TODO Auto-generated method stub
			Connection conn = null;
			PreparedStatement statement = null;
			ResultSet result = null;
			List<Prefer> list = new ArrayList();
			String sql=null;
			String dateAgo=getOneMonthAgo(date);

			try {
				conn = JdbcUtils02.getConnection();
				sql="SELECT classify FROM tab_userprofile  WHERE device_number='"
						+device_number
						+"' and date = '"+date+"'";
				statement = conn.prepareStatement(sql);
				result = statement.executeQuery();
				if(result.next()){
					String[] classifys=result.getString("classify").split(";");
					for(int i=0;i<classifys.length;i++){
						String classify=classifys[i].split(":")[0];
						if(classify.startsWith("003")){
							Prefer h=new Prefer();
							float weight=Float.parseFloat(classifys[i].split(":")[1]);
							float weightAgo=getLastWeight(classify,dateAgo,device_number);
							if(weightAgo==-1||weight>=weightAgo)
								h.setScan_num(0);//0表示增加，-1表示减少
							else
								h.setScan_num(-1);
							String c=getPicClassify(classify);
							h.setClassify(getPicClassify(classify));
							list.add(h);	
						}
					}
				}
				return list;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				JdbcUtils02.release(conn, statement, result);
			}
			return list;
		}
	/**
	 * 根据手机号获取上个月的权重
	 * @param classify
	 * @param dateAgo
	 * @param device_number
	 * @return
	 */
	private float getLastWeight(String classify, String dateAgo, String device_number) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<Prefer> list = new ArrayList();
		String sql=null;

		try {
			conn = JdbcUtils02.getConnection();
			sql="SELECT classify FROM tab_userprofile  WHERE device_number='"
					+device_number
					+"' and date = '"+dateAgo+"'";
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			if(result.next()){
				String[] classifys=result.getString("classify").split(";");
				for(int i=0;i<classifys.length;i++){
					String classifyA=classifys[i].split(":")[0];
					if(classify.equals(classifyA)){
						float weight=Float.parseFloat(classifys[i].split(":")[1]);
						return weight;
						
					}
			}
				return -1;		
					
					}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils02.release(conn, statement, result);
		}
		return -1;
	}

	/**
	 * 获得上个月份
	 * @param min 
	 * @return
	 */
	private static String getOneMonthAgo(String date) {
		// TODO Auto-generated method stub 
        String year=date.split("-")[0];
        int month=Integer.parseInt(date.split("-")[1]);
        if(month!=1){
        	month=month-1;
        	if(month<10)
        		return year+"-0"+month;
        	else
        		return year+"-"+month;
        }else
        	return ""+(Integer.parseInt(year)-1)+"-12";
	}
	/**
	 * 根据手机号获取用户的基本信息
	 * @param device_number
	 * @return
	 */
	public Basic getBasic(String device_number){
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql=null;
		//按照手机号码获取用户的基本信息
		Basic user=new Basic();
		try {
			conn = JdbcUtils.getConnection();
			sql="SELECT device_type,mobile_area,mobile_type,user_birth,user_sex FROM tab_deviceinfo where device_number = '"+device_number+"'";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			if(rs.next()){
				user.setSex(rs.getInt("user_sex"));
				user.setAge(getAgeType(rs.getString("user_birth")));
				user.setDevice_number(device_number);
				user.setArea(getMbarea(rs.getString("mobile_area")));
				user.setMobile(getDeviceType(rs.getString("device_type")));
				user.setOperator(getMbtype(device_number));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtils.release(conn, st, rs);
		}
		return user;
	}
	/**
	 * 获取用户使用app的情况
	 * @param device_number
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	public List<App> getApp(String device_number, String startdate,
			String enddate) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<App> list = new ArrayList();
		String sql=null;
		try {
			conn = JdbcUtils02.getConnection();
			sql="SELECT app_name, SUM(use_nums) c ,app_classify " +
					"FROM tab_countapps  WHERE device_number='"
					+device_number
					+"' and use_date between ? and ?  GROUP BY (app_classify) order by c DESC";
			statement = conn.prepareStatement(sql);
			statement.setString(1, startdate);
			statement.setString(2, enddate);
			result = statement.executeQuery();
			while(result.next()){
				String classify=formartAppClas(result.getString("app_classify"),conn); 
				if(classify != null && classify.length() != 0){
					App h=new App();
					h.setDevice_number(device_number);
					h.setApp_name(result.getString("app_name"));
					h.setClassify(classify);
					h.setNums(result.getInt("c")); 
					list.add(h);
				}
			}  
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.release(conn, statement, result);
		}
		return list;
	}
	/**
	 * 图片
	 * 按照日期获取用户的查看图片类型
	 * @param device_number
	 * @param startdate
	 * @param enddate
	 * @return 
	 */
	public List<Prefer> getPic(String device_number,
		String startdate, String enddate) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<Prefer> list = new ArrayList();
		String sql=null;

		try {
			conn = JdbcUtils02.getConnection();
			sql="SELECT SUM(pic_nums) c ,classify FROM tab_picprefer  WHERE device_number='"
					+device_number
					+"' and date between ? and ? GROUP BY (classify) ORDER BY c DESC";
			statement = conn.prepareStatement(sql);
			statement.setString(1, startdate);
			statement.setString(2, enddate);
			result = statement.executeQuery();
			while(result.next()){
				String cl_num=getPicClassify(result.getString("classify"));
				 
				if(cl_num != null && cl_num.length() != 0){
					Prefer h=new Prefer();
					h.setDevice_number(device_number);
					 
					h.setClassify(cl_num);
					h.setScan_num(result.getInt("c")); 
					list.add(h);
				}
				
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils02.release(conn, statement, result);
		}
		return list;
		
	}

	/**
	 * 获得满足偏好权重的手机号列表
	 * @param up
	 * @return
	 */
	public List<String> getprferList(String classify,float weight){
		List<String> list =new ArrayList<String>();
			Connection conn = null;
			PreparedStatement statement = null;
			ResultSet result = null; 
			String sql=null;

			try {
				conn = JdbcUtils02.getConnection();
				sql="SELECT device_number,classify from tab_userprofile";
				statement = conn.prepareStatement(sql);
				result = statement.executeQuery();
				while(result.next()){
					String classifys=result.getString("classify");
					if(isFit(classifys,classify,weight)){
						String device_number=result.getString("device_number");
						if(!list.contains(device_number))
							System.out.println("满足条"+classify+weight+"件的手机"+device_number);
							list.add(device_number);
					}
				}
				return list;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				JdbcUtils02.release(conn, statement, result);
			}
			return list;
	}

	/**
	 * 判断用户偏好是否满足条件
	 * @param classifys
	 * @param weight 
	 * @param classify 
	 * @return
	 */
	private boolean isFit(String classifys, String classify, float weight) {
		// TODO Auto-generated method stub
			if(!classifys.contains(classify))
				return false;
			else{
				String[] str=classifys.split(";");
				for(String s:str){
					System.out.println(s+"+");
					if(s.contains(classify)){
						float w=Float.parseFloat(s.split(":")[1]);
						if(w>=weight)
							return true;
						else
							return false;
					}
				}
			}
		return false;
	}
	public List<JsonModel> GetContentClassList() {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<JsonModel> comList = new ArrayList<JsonModel>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select value as id,`name` from tab_pushcontentclass where code REGEXP CONCAT('^',(select code from tab_pushcontentclass where value='003' ),'[0-9][0-9][0-9]-') order by code asc";
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			while (result.next()) {
				JsonModel comItem = new JsonModel();
				String id=result.getString("id");
				if(id.length()<=9){
					comItem.setId(id);
					comItem.setName(result.getString("name"));
					comList.add(comItem);
				}
			}
			return comList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, null);
		}
		return null;
	}

	/**
	 * 获取满足条件的用户
	 * @param up 
	 * @param currentPage
	 * @param pageSize
	 * @param up
	 * @param orderstrString
	 * @return
	 */
	public List<MobileModel> GetPreferUsersList(int currentPage, int pageSize,
			String mobileQuery, String classify, float weight) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null; 
		String sql=null;
		List<MobileModel> list=new ArrayList<MobileModel>();
		System.out.println("classify:"+classify);
		if(classify==null){
			sql="SELECT * from tab_deviceinfo "+mobileQuery+" limit ?,?";
		}else{
			List<String> plist=getprferList(classify,weight);
			if(plist.size()!=0){
				StringBuffer sb=new StringBuffer("(");
				for(String d:plist){
					if(d!=plist.get(plist.size()-1))
						sb.append("'"+d+"',");
					else
						sb.append("'"+d+"'");
				}
				sb.append(')');
				System.out.print(sb.toString());
				if(mobileQuery.length()==0)
					sql="SELECT * from tab_deviceinfo where device_number in "+sb.toString()+" limit ?,?";
				else
				sql="SELECT * from tab_deviceinfo "+mobileQuery+" and device_number in "+sb.toString()+" limit ?,?";
		
			}else
				return list;
		}
			
		
		System.out.print(sql);
		try {
			conn = JdbcUtils.getConnection();
		/*	StringBuffer sb=new StringBuffer("SELECT * from tab_deviceinfo where device_number=?");
			String sex=up.getGender();String age=up.getAge(); 
			if(sex!=null&&!"".equals(sex))
				sb.append(" and sex='"+sex+"'");
			if(age!=null&&!"".equals(age))
				sb.append(" and age='"+age+"'");
			sql=sb.toString();*/
			
			statement = conn.prepareStatement(sql);
			statement.setInt(1, (currentPage - 1) * pageSize);
			statement.setInt(2, pageSize);
			result = statement.executeQuery();
			while(result.next()){
				MobileModel Rec=new MobileModel();
		
				String imei=result.getString("device_imei");
				Rec.setImei(imei);
				Rec.setImsi(result.getString("device_imsi"));
				Rec.setMbno(result.getString("device_number"));
				Rec.setGender(result.getInt("user_sex")+"");
				//根据出生年份算用户年龄
				if (result.getString("user_birth")!=null&&result.getString("user_birth").length()>=4) {
					 String birth=result.getString("user_birth").substring(0, 4);
					 Date date=new Date();
					 int nowyear=date.getYear()+1900;
				     Rec.setBirth((nowyear-Integer.parseInt(birth))+"");
				   }else {
					  Rec.setBirth("");
				}
			    Rec.setMbos(getOsVserion(result.getInt("device_os_version")+""));
			   Rec.setArea(getMbarea(result.getString("mobile_area")));
			   Rec.setProvider( getMbtype(result.getString("mobile_type")));
				Rec.setMbtype(getDeviceType(result.getString("device_type")));
				
				int depid=result.getInt("seller_depid");
				
				if (depid==0) {
					Rec.setSeller_depid("");
				}else {
					Rec.setSeller_depid(result.getInt("seller_depid")+"");
				}
				int empid=result.getInt("seller_empid");
				
				if (empid==0) {
					Rec.setSeller_stuffid("");
				}else {
					Rec.setSeller_stuffid(result.getInt("seller_empid")+"");
				}
				 String entertime=result.getString("enter_time");
				
				if (entertime!=null&&entertime.length()>=19) {
					Rec.setRegtime(entertime.substring(0, 19));
					
				}
				Rec.setTheme(result.getInt("app_mode"));
				Rec.setThemestatus(result.getInt("app_modechanged_status"));
				list.add(Rec);
			}
			/*		for(String d:plist){
				statement = conn.prepareStatement(sql);
				statement.setString(1, d);
				result = statement.executeQuery();
				if(result.next()){
					MobileModel m=new MobileModel();
					m.setMbno(d);
					m.setImei(result.getString("imei"));
					m.setImsi(result.getString("imsi"));
					m.setMbtype(result.getString("mbtype"));
					m.setGender(result.getString("gender"));
					m.setProvider(result.getString("provider"));
					m.setArea(result.getString("area"));
					m.setMbos(result.getString("mbos"));
					list.add(m);
				}
			}*/
			
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.release(conn, statement, result);
		}
		return null;
	}
	public int getTotalList(String mobileQuery, String classify, float weight) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null; 
		String sql=null;
		int num=0;
		List<MobileModel> list=new ArrayList<MobileModel>();
		System.out.println("classify:"+classify);
		if(classify==null){
			sql="SELECT * from tab_deviceinfo "+mobileQuery;
		}else{
			List<String> plist=getprferList(classify,weight);
			if(plist.size()!=0){
				StringBuffer sb=new StringBuffer("(");
				for(String d:plist){
					if(d!=plist.get(plist.size()-1))
						sb.append("'"+d+"',");
					else
						sb.append("'"+d+"'");
				}
				sb.append(')');
				System.out.print(sb.toString());
				if(mobileQuery.length()==0)
					sql="SELECT * from tab_deviceinfo where device_number in "+sb.toString();
				else
				sql="SELECT * from tab_deviceinfo "+mobileQuery+" and device_number in "+sb.toString();
		
			}else
				return 0;
		}
		System.out.print(sql);
		try {
			conn = JdbcUtils.getConnection();
		
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			while(result.next()){
				num++;
			}
			return num;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.release(conn, statement, result);
		}
		return num;
	}

	/**
	 *获取操作系统版本号
	 */
	public String getOsVserion(String str) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			
			String sqlString="select name from tab_pushmbos where id=?";
			st = conn.prepareStatement(sqlString);
			st.setString(1, str);
			rs = st.executeQuery();
			
			if (rs.next()) {
				return rs.getString("name");
			 }
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		
		return "";
	}

	
	


}
