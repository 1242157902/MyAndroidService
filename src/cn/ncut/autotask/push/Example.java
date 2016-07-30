package cn.ncut.autotask.push;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import cn.ncut.pushservice.AutoPushService;
import cn.ncut.utils.DateUtils;
import cn.ncut.utils.JdbcUtils;
import cn.ncut.utils.JdbcUtils02;
import cn.ncut.utils.MapConvertAPI;
import cn.ncut.wxdao.DeviceInforDao;
import cn.ncut.wxdao.LocationClassifyDao;
import cn.ncut.wxdomain.LoctionClassify;
import cn.ncut.wxdomain.MapItem;

/**
 * @author hjf
 *
 *version 1.0 2016-4-19 下午3:56:46
 */
@Slf4j
public class Example {

	static LocationClassifyDao lcd = new LocationClassifyDao();
	static AutoPushService aps = new AutoPushService();
	static DeviceInforDao did = new DeviceInforDao();
	public static void mytask()
	{
		Connection conn = null; 
		PreparedStatement statement = null;
		ResultSet result = null;
		String ago_time=getMiniteAgo(5); 
		
	
		try {
			conn = JdbcUtils02.getConnection();
			List<MapItem> list=getlist(ago_time,conn,statement,result);
			log.info("手机号列表长度"+list.size());
			for(MapItem map:list){
				String device_number=map.getPhone_num();
				String cur_addrees=map.getAddress();
				String ago_addrees=getlocation(device_number,ago_time,conn,statement,result);
				if(cur_addrees!=null&&ago_addrees!=""){
					if(ago_addrees==null||ago_addrees=="")
						pushByLoc(device_number, cur_addrees);
					else if(!cur_addrees.split(",")[3].equals(ago_addrees.split(",")[3])){ 
						log.info("比较"+cur_addrees.split(",")[3].equals(ago_addrees.split(",")[3]));
						pushByLoc(device_number, cur_addrees);
					 }
					
				}
					
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("mytask执行异常"+e);
		}finally{
			JdbcUtils02.release(conn, statement, result);
		}
		System.err.println("任务已执行！");
	}

	public static void pushByLoc(String device_number, String cur_addrees) {
		String imei = did.getDeviceImeiByPhone(device_number);
		
			List<LoctionClassify> lcList = lcd.getLocationClassifysByCode("001001");
			for (LoctionClassify loctionClassify : lcList){
				if(cur_addrees.contains(loctionClassify.getName())){
					if(loctionClassify.getQue()!=0){
						recommentContent(device_number,loctionClassify.getValue(),loctionClassify.getDuration(),imei);
					}
				}
			}
	}

	//1.获取5分钟内有记录的手机号Map<String,String>；2.按手机号对比5分钟之前的地理位置，
		//若跨区，则推送
	/**
	 * 获得指定时间内的手机列表
	 * @param result 
	 * @param statement 
	 */
	public static List<MapItem> getlist(String date,Connection conn, PreparedStatement statement, ResultSet result){
		
		
		
		String sql=null;
		List<MapItem> list=new ArrayList<MapItem>();
		List<String> dlist=new ArrayList<String>();
		try {
			
			sql="select distinct(phone_num) phone_num from tab_location " +
					"where locationtime >= '"+date+"' order by locationtime desc";
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			while (result.next()) {
				String d=result.getString("phone_num");
				if(d!=""||d!=null||d.length()!=0)
					dlist.add(d);
			}
			
			for(String d:dlist){
				sql="select latitude,longitude from tab_location " +
					"where phone_num ='"+d+"' and locationtime >= '"+date+"' order by locationtime desc limit 0,1";
				statement = conn.prepareStatement(sql);
				result = statement.executeQuery();
				if (result.next()) {
					MapItem mapItem = new MapItem();
					mapItem.setPhone_num(d);
					String latitude=result.getString("latitude");
					mapItem.setLatitude(latitude);
					String longitude=result.getString("longitude");
					mapItem.setLongitude(longitude);
					mapItem.setAddress(getLocDetail(latitude,longitude));
					list.add(mapItem);
				}
			}
			
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("查询手机号列表异常"+e);
		}
		return list;
	}
	/**
	 * 根据手机号获得地理位置
	 * @param device_number
	 * @param start_time 
	 * @param conn 
	 * @param result2 
	 * @param statement2 
	 * @return
	 */
	public static String getlocation(String device_number,String date,Connection conn, PreparedStatement statement, ResultSet result){
		String address=null;
		String sql="select * from tab_location " +
				"where phone_num = '"+device_number+"' and locationtime <= '"+date+"'" +
						"order by locationtime desc";
		try {
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			if (result.next()) {
				MapItem mapItem = new MapItem();
				String phone_num=result.getString("phone_num");
				mapItem.setPhone_num(phone_num);
				String latitude=result.getString("latitude");
				String longitude=result.getString("longitude");
				address=getLocDetail(latitude,longitude);
				
			}
			return address;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("根据手机号获取地理位置异常："+e);
		}
		return address;
		
	}
	/**
	  * 
	  * @return:       void 
	  * @param phone
	  * @param code
	  * @param hour：以小时为单位
	  * <p>Description: 推荐函数<p>
	  * @date:          2016年3月20日下午3:40:32
	  * @author         hjf
	 * @param imei 
	  */
	 public static void recommentContent(String phone,String code,int hour, String imei)
	 {
		 String que = null;
		 if(phone!=null&&code!=null&&hour!=0)
		 {
			 List<LoctionClassify> lcList = lcd.getLocationClassifysByCode(code);
				if(lcList.size()==1)
				{
					LoctionClassify lc = lcList.get(0);
					if(lc.getQue()!=0)
					{
						que = new Integer(lc.getQue()).toString();
						if(imei!=null)
						{
							String rs=aps.PushToSingle(DateUtils.getCurrentDate(), "2017-04-20 00:00:00", que, imei);
							log.info("调用自动推送接口情况：手机号："+phone+":"+rs);
						}
					}
				}
		 }
		 
	 }
	/**
	 * 获得当前日期的前五分钟
	 * @param min 
	 * @return
	 */
	private static String getMiniteAgo(int min) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MINUTE, -min);

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
	
	}
	/**
	 * 根据经纬度获得具体位置，详细到区
	 * @param longitude 
	 * @param device_number
	 * @return
	 */
	public static String getLocDetail(String latitude, String longitude){
		 
		try {
			return MapConvertAPI.getAddress(longitude, latitude);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("调用百度API解析地理位置异常："+e);
		}
		return "";
	}

	/**
	 * 按照偏好推送
	 * 1.中午十二点，执行
	 * 2.按照前一天最高的推送
	 */
	public static void pushByPrefer() {
		// TODO Auto-generated method stub
		getPhList();
	}
	public static void getPhList(){
		Connection conn = null; 
		PreparedStatement statement = null;
		ResultSet result = null; 
		String date=getSpecifiedDayBefore();
		DeviceInforDao did = new DeviceInforDao();
		try {
			conn = JdbcUtils02.getConnection();
			String sql="select DISTINCT(device_number) phone from tab_countapps where use_date='"+date+"'";
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			List<String> list=new ArrayList<String>();
			while (result.next()) {
				list.add(result.getString("phone"));
			}
			log.info("手机号长度"+list.size());
			if(list.size()!=0){
				for(String d:list){
					sql="select * from tab_countapps " +
							"where device_number='"+d+"' and use_date='"+date+"' " +
									"order by use_nums desc";
					statement = conn.prepareStatement(sql);
					result = statement.executeQuery();
					if (result.next()) {
						String classify=result.getString("app_classify");
						int qid=getQuiIdByCly(classify.substring(0,6));
						String que = new Integer(qid).toString();
						if(qid>0){
							String imei = did.getDeviceImeiByPhone(d);
							String rs=aps.PushToSingle(DateUtils.getCurrentDate(), "2017-04-21 00:00:00", que, imei);
							log.info("按照APP偏好推送结果："+rs);
						}
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("mytask执行异常"+e);
		}finally{
			JdbcUtils02.release(conn, statement, result);
		}
	}
	/**
	 * 根据偏好维度获取图片序列
	 * @param classify
	 * @return
	 */
	private static int getQuiIdByCly(String classify) {
		// TODO Auto-generated method stub
		
		Connection conn = null; 
		PreparedStatement statement = null;
		ResultSet result = null; 
		int queID=-1;
		try {
			conn = JdbcUtils.getConnection();
			String sql="select * from tab_pushcontentclass" +
					" where value like '"+classify+"%' " +
							"and que IS NOT NULL ORDER BY  RAND() LIMIT 1";
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			if (result.next()) {
				queID=result.getInt("que");
				if(queID>=0)
					return queID;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("mytask执行异常"+e);
		}finally{
			JdbcUtils.release(conn, statement, result);
		}
		return queID;
	}

	/**
     * 获得指定日期的前一天
     * 
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayBefore() {
    	 Date date = new Date();
         
        Calendar c = Calendar.getInstance();
        
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
                .getTime());
        return dayBefore;
    }
    public static void main(String[] args) {
		// TODO Auto-generated method stub
		mytask();
	}    
}
