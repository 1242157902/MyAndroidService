package cn.ncut.autotask.countProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import cn.ncut.autotask.utils.DateUtil;
import cn.ncut.utils.JdbcUtils02;
import cn.ncut.utils.mapItem;
import cn.ncut.wxdomain.MapItem;

/**
 * 统计地理位置
 * @author hjf
 *
 */
@Slf4j
public class CountLocPrefer {
	/**
	 * 按天统计每天去的地方的次数
	 * @throws SQLException
	 */
	public static void countMap(){
		List<MapItem> list=getMapList();
		Connection conn = null;
		PreparedStatement st = null;
	  	ResultSet rs = null;
		String sql = null;
		String num = null;
		try {conn = new JdbcUtils02().getConnection();
		
		for(MapItem item:list){
			//
			String phone_num=item.getPhone_num();
			String date=item.getDate(); 
			date=date.substring(0,10);
			String address=item.getAddress();
			System.out.println(address+date);
			sql="select * from tab_locationcount where phone_num='"
					+phone_num
					+"' and locationtime like '"+date+"%' and address='"+address+"'";
			
			
				
				st = conn.prepareStatement(sql);
				rs = st.executeQuery();
				if(rs.next()){
					//若存在，则在此数据上的count+1
					sql="update tab_locationcount set count=count+1 where phone_num='"
						+phone_num
						+"' and locationtime like '"+date+"%' and address='"+address+"'";
					st = conn.prepareStatement(sql);
					st.executeUpdate();
				}else{
					//若不存在，则insert
					sql="insert into  tab_locationcount(phone_num,locationtime,address,count) values(?,?,?,?)";
						st = conn.prepareStatement(sql);
						st.setString(1, phone_num);
						st.setString(2, date);
						st.setString(3, address);
						st.setInt(4, 1);
						st.executeUpdate();
				}
		}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				log.error("插入tab_locationcount异常"+e);
			}finally{
				JdbcUtils02.release(conn, st,rs);
			}
	}
	/**
	 * 获得地址列表
	 * @return
	 * @throws SQLException 
	 */
	public static List<MapItem> getMapList(){
		 Connection conn = null;
			PreparedStatement st = null;
		  	ResultSet rs = null;
			String sql = null;
			String num = null;
			List<MapItem> list=new ArrayList();
			String date=DateUtil.getSpecifiedDayBefore();
			sql="SELECT * from tab_location where locationtime like '"+date+"%'";
			try {
				conn = new JdbcUtils02().getConnection();
				st = conn.prepareStatement(sql);
				rs = st.executeQuery();  
				while(rs.next()){
				//更新统计表的次数
					MapItem m=new MapItem(); 
					    
					    String lat=rs.getString("latitude");
					    String lng=rs.getString("longitude");
					    m.setLatitude(lat);
					    m.setLongitude(lng);
					    m.setPhone_num(rs.getString("phone_Num"));
					    m.setAddress(MapConvertAPI.getAddress(lng,lat));
					    m.setDate(rs.getString("locationtime"));
						list.add(m);
				}
				 return list;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				log.error("获得数据列表异常"+e);
			}finally{
				JdbcUtils02.release(conn, st, rs);
			}
		  return list;
	}
	
}
