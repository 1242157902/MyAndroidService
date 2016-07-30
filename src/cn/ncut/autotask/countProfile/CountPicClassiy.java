package cn.ncut.autotask.countProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
 
import cn.ncut.autotask.utils.DateUtil;
import cn.ncut.utils.DateUtils;
import cn.ncut.utils.JdbcUtils02;
@Slf4j
public class CountPicClassiy {
	public static void main(String[] args) throws ParseException 
	{
		/**
		 * 1、先获取当天的所有手机号
		 * 2、再获取每个手机号，浏览的标签类别，并拼接到一起，封装到DayClasssifys
		 * 3、插入到表中		 *
		 */
 
			countPicClassiy();
	}
	public static void countPicClassiy() {
		String date=DateUtil.getSpecifiedDayBefore();;
		List<DayClassifys> dayClassifysList= getDayClassfiys(date,"tab_picprefer");
		log.info("统计分类条数为："+dayClassifysList.size());
		insertDayClassifys(dayClassifysList);
	}
	public static void insertDayClassifys(List<DayClassifys> dayClassifysList)   
	{
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null; 
		try{
			conn = new JdbcUtils02().getConnection();
			 for (DayClassifys dayClassifys : dayClassifysList) 
			 {
				sql = "insert into tab_day_classifys(phone_num,date,classifys) values(?,?,?)";
				st = conn.prepareStatement(sql);
				st.setString(1, dayClassifys.getPhoneNumber());
				st.setString(2, dayClassifys.getDate());
				st.setString(3, dayClassifys.getClassifys());
				st.executeUpdate(); 
			}
		}catch(SQLException se)
		{
			log.error("插入分类统计表失败"+se);
		}finally
		{
			JdbcUtils02.release(conn, st, rs); 
		}
	
	}
	/**
	 * 
	 * @return:       List<DayClassifys> 
	 * @param timeString
	 * @param tab_name
	 * @return
	 * @throws SQLException
	 * <p>Description:获取当天用户的所有浏览的标签 <p>
	 * @date:          2016年3月7日下午9:05:08
	 * @author         ysl
	 */
	public static List<DayClassifys> getDayClassfiys(String timeString,String tab_name) 
	{
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null; 
		String startTime=timeString;
		String endTime= DateUtils.dateToString(getSevenDay(DateUtils.strToDate(timeString)));
		List<DayClassifys>  dayClassifysList = new ArrayList<DayClassifys>();
		//1、得到手机号
		List<String> phoneList = getphonenumber(timeString, tab_name);
		try{
		conn = new JdbcUtils02().getConnection(); 
		for (String phone : phoneList)
		{
			sql = "select distinct(classify)  from  "+ tab_name +" where    device_number= '"+phone+"' and date between '"+endTime+"' and '"+startTime+"'";
			
				st = conn.prepareStatement(sql);
				rs = st.executeQuery();
				DayClassifys dc = new DayClassifys();
				StringBuffer classifys = new StringBuffer();
				while(rs.next())
				{
					classifys.append(rs.getString("classify"));
					classifys.append(",");
				}
				String classifysStr=classifys.substring(0,classifys.length()-1);
				dc.setClassifys(classifysStr);
				dc.setDate(timeString);
				dc.setPhoneNumber(phone);
				dayClassifysList.add(dc);
				
		}
		return dayClassifysList;
		}catch(SQLException se)
		{
			log.error("统计手机利列表失败"+se);
		}finally{
			JdbcUtils02.release(conn, st, rs); 
		}
		return dayClassifysList;
	}
	
	/**
	 * 
	 * @return:       Date 
	 * @param date
	 * @return
	 * <p>Description: 当前的日期较少七天，返回七天以前的日期类型<p>
	 * @date:          2016年3月8日下午4:18:34
	 * @author         ysl
	 */
	public static Date getSevenDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -15);//+1今天的时间加一天
		date =   calendar.getTime();
		return date;
	}
	/**
	 * 
	 * @return:       List<String> 
	 * @param timeString
	 * @param tab_name
	 * @return
	 * <p>Description: 获取表中七天的所有手机号<p>
	 * @date:          2016年3月7日下午8:03:17
	 * @author         ysl
	 */
	public  static   List<String> getphonenumber(String timeString,String tab_name) {

		List<String> list = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null; 
		String startTime=timeString;
		String endTime= DateUtils.dateToString(getSevenDay(DateUtils.strToDate(timeString)));
			sql="SELECT distinct(device_number) FROM "
					+tab_name 
					+" WHERE date  between  '"+endTime+"'  and '"+startTime+"'";
		
			try {
				conn = new JdbcUtils02().getConnection();
				st = conn.prepareStatement(sql);
				rs = st.executeQuery();
				String device_number=""; 
				while(rs.next()){
				//更新统计表的次数 
					device_number= rs.getString("device_number");
					if(device_number != null && device_number.length() != 0){
						 list.add(device_number);
					}	
				} 
			} catch (SQLException e) {
				log.error("统计手机号失败"+e);
			}finally{
				JdbcUtils02.release(conn, st, rs); 
			}
			return list;
	}
}
