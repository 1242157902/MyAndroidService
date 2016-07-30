package cn.ncut.autotask.count;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import cn.ncut.autotask.utils.DateUtil;
import cn.ncut.utils.JdbcUtils02;
import cn.ncut.wxdomain.App;
@Slf4j
public class CountAppPrefer {
	/**
	 * 获得每个手机每天使用app的次数
	 * @return
	 */
	public static List<App> getUsersApp(String time){
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null;
		List<String> mbos=getphoneList();
//		String time=DateUtil.getSpecifiedDayBefore();
		List<App> list=new ArrayList<App>();
		try {
			conn = new JdbcUtils02().getConnection();
			for(int  i=0,len=mbos.size();i<len;i++){
				String device_number=mbos.get(i);
				sql="SELECT COUNT(*) count,app_name,app_flow " +
						"FROM `tab_appflow` " +
						"WHERE device_number='"+device_number+"" +
								"' and receive_time like '"+time+"%'" +
						" GROUP BY(app_name)";
				st = conn.prepareStatement(sql);
				rs = st.executeQuery(); 
				//得到当天的所有app_name,根据app_name统计次数
				while(rs.next()){ 
					App a=new App();
					String app_name=rs.getString("app_name");
					a.setApp_name(app_name);
					a.setDevice_number(device_number);
					a.setClassify(getAppNo(app_name,conn));
					a.setUse_date(time);
					a.setNums(rs.getInt("count"));
					list.add(a);
				}				
			}
			return list;
		} catch (SQLException e) {
				// TODO Auto-generated catch block
			log.error("获得用户使用APP次数异常"+e);
		}finally{
			JdbcUtils02.release(conn, st, rs);
		}
		return list;
	}
	
	/**
	 * 根据app的名称得到对应变化要分类
	 * @param app_name
	 * @param conn 
	 * @return
	 * @throws SQLException 
	 */
	private static String getAppNo(String app_name, Connection conn){
		// TODO Auto-generated method stub
		String sql="SELECT code from tab_appsdic where name='"+app_name+"'";
		PreparedStatement st;
		String code="";
		try {
			st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();  
			
			if(rs.next()){ 
				code=rs.getString("code");
			}				
			return code;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("获得用户类别异常"+e);
		}
		return code;
	}
	public static List<String> getphoneList(){
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null;
		List<String> list=new ArrayList<String>();
		sql="SELECT distinct(device_number) device_number " +
				"FROM `tab_appflow` ";
		try {
			conn = new JdbcUtils02().getConnection();
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();  
			while(rs.next()){ 
				String device_number= rs.getString("device_number");
				if(device_number != null && device_number.length() != 0){
					list.add(device_number);
				}	
					
			} 
			return list;
		} catch (SQLException e) {
			log.error("获取手机号列表异常"+e);
		}finally{
			JdbcUtils02.release(conn, st, rs); 
		} 	
		return null;	
	}
	public static void insertUsersApp(){
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null;
		String time=DateUtil.getSpecifiedDayBefore();
		List<App> list=getUsersApp(time);
		try {
			conn = new JdbcUtils02().getConnection();
			for(int i=0,len=list.size();i<len;i++){
				App app=list.get(i);
				sql="insert into tab_countapps(device_number,app_name,use_nums,app_classify,use_date) values(?,?,?,?,?)";
				st=conn.prepareStatement(sql); 
				st.setString(1, app.getDevice_number());
				st.setString(2, app.getApp_name());
				st.setInt(3, app.getNums());
				st.setString(4, app.getClassify());
				st.setString(5, app.getUse_date());
				
				st.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("插入tab_countapps异常"+e);
		}finally{
			JdbcUtils02.release(conn, st, rs); 
		}
		
		
	}
	public static void main(String argr[]){
		String time=null;
		for(int i=1;i<22;i++){
			if(i<10)
				time="2016-04-0"+i;
			else
				time="2016-04-"+i;
	//		insertUsersApp(time);
		}
	}
}
