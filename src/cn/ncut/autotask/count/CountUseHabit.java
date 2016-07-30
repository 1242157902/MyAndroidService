package cn.ncut.autotask.count;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import cn.ncut.autotask.utils.DateUtil;
import cn.ncut.utils.JdbcUtils02;
import cn.ncut.wxdomain.HabitItem;
import lombok.extern.slf4j.Slf4j;
/**
 * 统计用户每天使用手机情况
 * @author hjf
 *
 */

@Slf4j
public class CountUseHabit {
	/**
	 * 按照每两个 统计次数
	 * 时长按照一天的统计
	 */
		
		/**
		 * 获得每个手机在每个时间段（按照2小时计算）内的使用手机的频次
		 * @return
		 */
		public static List<HabitItem> getDevice_number(String timeString){
				Connection conn = null;
				PreparedStatement st = null;
				ResultSet rs = null;
				String sql = null; 
				
				String startTime="";
				String endTime="";
				int j=0;
				int k=0;//第几个时段:1:00--1:59是第1个时段，一共为12个时段
				List<HabitItem> list=new ArrayList();
				for(int i=0;i<24;i++){
					k++;
					if(i<10){
						j=i;
						startTime=timeString+" 0"+i+":00:00";
						i++;
						endTime=timeString+" 0"+i+":59:59";
					}else{
						j=i;
						startTime=timeString+" "+i+":00:00";
						i++;
						endTime=timeString+" "+i+":59:59";
					}
					
					sql="SELECT device_number,count(*) nums FROM tab_screeninfo " +
							"WHERE screen_onoff=1 and operator_time BETWEEN '"
							+startTime
							+"' AND '"
							+endTime
							+"' group by device_number";
					try {
						conn = new JdbcUtils02().getConnection();
						st = conn.prepareStatement(sql);
						rs = st.executeQuery();  
						while(rs.next()){ 
							HabitItem h=new HabitItem();
							String device_number= rs.getString("device_number");
							if(device_number != null && device_number.length() != 0){
								h.setDevice_number(rs.getString("device_number"));
								h.setDate(timeString);
								h.setNums(rs.getInt("nums"));
								h.setTime(j+":00"+"~"+i+":59");
								h.setTime_id(k);
								list.add(h);
							}	
							
						} 
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						log.error("获得不同时段使用异常"+e);
					}finally{
						JdbcUtils02.release(conn, st, rs); 
					} 
				} 		
				return list;
		}
		/**
		 * 将获取到的手机号存入到数据库中
		 * @param args
		 * @throws SQLException 
		 */
		public static void updateHabits(){
			Connection conn = null;
			PreparedStatement st = null; 
			String sql = null; 
			ResultSet rs = null;
			List<HabitItem> list=new ArrayList<HabitItem>();
			String timeString =DateUtil.getSpecifiedDayBefore();;
			list=getDevice_number(timeString);
			try {
				conn = new JdbcUtils02().getConnection();
				log.info("插入tab_screen_times的条数"+list.size());
				for(HabitItem h:list){
					sql="select * from tab_screen_times where device_number=? and date=? and time_id=?";
					st = conn.prepareStatement(sql);
					st.setString(1, h.getDevice_number());
					st.setString(2, h.getDate().substring(0, 10));
					st.setInt(3, h.getTime_id()); 
					rs=st.executeQuery();
					if(rs.next()){
						sql="update tab_screen_times set nums=nums+? where device_number=? and date=? and time_id=?";
						st = conn.prepareStatement(sql);
						st.setInt(1, h.getNums());
						st.setString(2, h.getDevice_number());
						st.setString(3, h.getDate().substring(0, 10));
						st.setInt(4, h.getTime_id()); 
						st.executeUpdate();
					}else{
						sql="insert tab_screen_times(device_number,date,time,time_id,nums) values(?,?,?,?,?)";						st = conn.prepareStatement(sql);
						st.setString(1, h.getDevice_number());
						st.setString(2, h.getDate().substring(0, 10));
						st.setString(3, h.getTime());
						st.setInt(4, h.getTime_id());
						st.setInt(5, h.getNums());
						st.executeUpdate();				
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				log.error("插入tab_screen_time异常"+e);
			}finally{
				JdbcUtils02.release(conn, st, rs);
			}
		}
		public static void main(String[] args){
			updateHabits();
		}
}
