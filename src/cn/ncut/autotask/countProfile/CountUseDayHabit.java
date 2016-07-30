package cn.ncut.autotask.countProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;


import cn.ncut.autotask.utils.DateUtil;
import cn.ncut.utils.JdbcUtils02;
import cn.ncut.wxdomain.HabitCount;
@Slf4j
public class CountUseDayHabit {
	public static List<HabitCount> getHabitList(String timeString){
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null; 
 		//= year + "-" + month + "-" + day;
		String startTime=timeString+" 00:00:00";
		String endTime=timeString+" 23:59:59";
		List<String> list=getDevice_number(startTime,endTime);
		List<HabitCount> lists=new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
		conn = new JdbcUtils02().getConnection();
		for(String device_number:list){
			int longt = 0;
			int nums=0;
			sql="SELECT * FROM tab_screeninfo " 
			+"WHERE  device_number='"
			+device_number+"' and operator_time BETWEEN '"
			+startTime
			+"' AND '"
			+endTime
			+"' order by operator_time";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();  
			int i=0;
				//rs.isLast();
			HabitCount h=new HabitCount();
			while(rs.next()){ 
				int onoff=rs.getInt("screen_onoff");;
				if(rs.isFirst()&&onoff==0){
						//若果是第一行，那么判断是否是0，如果是则跳过
					continue;
				}
				if(!rs.isLast()){
						if(onoff==1){
							nums++;
					}
						//将时间字符串转换为时间戳
					String times0=rs.getString("operator_time");
					long time0=0;
						if(onoff==1)
							time0= (long) (sdf.parse(times0).getTime());
						rs.next(); 
						String times1=rs.getString("operator_time");
						int onoffnext=rs.getInt("screen_onoff");
						long time1=0;
						if(onoffnext==0){
							time1=(long) (sdf.parse(times1).getTime());
						}
						long dif=0;
						if(time1==0||time0==0){
							dif=0;
						}else{
							dif=time1-time0;
							System.out.println(device_number+":"+onoffnext+"-"+onoff+dif);
						}
						
					//	if(dif>1){//若开屏关屏之间的间隔《1分钟，则忽略
							longt+=diff(dif);
							if(onoffnext==1){
								nums++;
							}
					//	}
					}else{
						if(onoff==1){
						//	long t=(long) (sdf.parse(rs.getString("operator_time")).getTime());
						//	long endt=(long) (sdf.parse(endTime).getTime());
						//	longt+=diff((endt-t));
							nums++;
						}
					}
				}
				h.setDura_time(longt);
				h.setDevice_number(device_number);
				h.setDate(timeString);
				h.setTimes(nums);
				lists.add(h);
		}
		return lists;
		} catch (SQLException e) {
				// TODO Auto-generated catch block
			log.error("统计次数异常"+e);
		} catch (ParseException e) {
				// TODO Auto-generated catch block
			log.error("统计次数异常"+e);
			}finally{
					JdbcUtils02.release(conn, st, rs); 
			} 	
		return lists;
			
	}
	
	public static int diff(long diff){             
        int min=(int) ((diff/(60*1000)));    //以分钟为单位取整 
        System.out.println(min+" "+diff);
        if(min>=120)
        	return 120;
        return min;
	}
	/**
	 * 获取手机号
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static List<String> getDevice_number(String startTime,String endTime){

		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null;
		List<String> list=new ArrayList(); 
		sql="SELECT distinct(device_number) FROM tab_screeninfo " +
					"WHERE  operator_time BETWEEN '"
					+startTime
					+"' AND '"
					+endTime
					+"'";
		try {
			conn = new JdbcUtils02().getConnection();
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();  
			while(rs.next()){ 
					HabitCount h=new HabitCount();
					String device_number= rs.getString("device_number");
					if(device_number != null && device_number.length() != 0){
						list.add(device_number);
					}
			}	
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("获得手机号异常"+e);
		}finally{
			JdbcUtils02.release(conn, st, rs); 
		}
		return list;
	}
	public static void updateCount(){
		String timeString =DateUtil.getSpecifiedDayBefore();
		List<HabitCount> list=getHabitList(timeString);
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null; 
	
		for(HabitCount h:list){
			sql="insert into tab_screencount(device_number,dura_time,times,date) values(?,?,?,?)";
		try {
			conn = new JdbcUtils02().getConnection();
			st = conn.prepareStatement(sql);
			st.setString(1, h.getDevice_number());
			st.setInt(2, h.getDura_time());
			st.setInt(3, h.getTimes());
			st.setString(4, h.getDate());
			
			st.executeUpdate(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("获得不同时段使用异常"+e);
		}finally{
			JdbcUtils02.release(conn, st, rs); 
		}
		}
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String timeString="2015-11-";
		for(int i=1;i<10;i++){
			timeString="2015-12-0"+i;
			System.out.println(timeString);
		//	updateCount(timeString);
		}
		
	}
}
