package cn.ncut.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter; 
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.ncut.utils.JdbcUtils02;
import cn.ncut.utils.mapItem;


 

public class MapConvertAPI {
	private static String ak = "dpWxwKSSSunlPAVDussdUq7Q";
    
	/**
	 * 逆地址解析：将经纬度转换为详细地址
	 * @param lng 经度
	 * @param lng 纬度
	 * @return 返回 详细地址
	 * @throws IOException
	 */
    public static String getAddress(String lng, String lat) throws IOException {
        URL url = new URL("http://api.map.baidu.com/geocoder/v2/?ak="+ak+  
        		"&callback=renderReverse&location=" + lat
                        + "," + lng + "&output=json");
        URLConnection connection = url.openConnection();
        /**
         * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。
         * 通过把URLConnection设为输出，你可以把数据向你个Web页传送。下面是如何做：
         */
        connection.setDoOutput(true);
        OutputStreamWriter out = new OutputStreamWriter(connection
                .getOutputStream(), "utf-8");
 
        out.flush();
        out.close();
        //一旦发送成功，用以下方法就可以得到服务器的回应：
        String res;
        InputStream l_urlStream;
        l_urlStream = connection.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                l_urlStream,"UTF-8"));
        StringBuilder sb = new StringBuilder("");
        while ((res = in.readLine()) != null) {
            sb.append(res.trim());
        }
        String str = sb.toString();
        StringBuffer address=new StringBuffer();
	    if(StringUtils.isNotEmpty(str)) { 
	    	/*---country中国---*/
	    	int countryStart = str.indexOf("country\":");
	        int countryEnd = str.indexOf("\",\"direction");
	        if(countryStart > 0 && countryEnd > 0) {
	          String country = str.substring(countryStart+10, countryEnd);
	          address.append(country+",");
	        }
	        /*------province省份-------*/
		      int provinceStart = str.indexOf("province\":");
		      int provinceEnd = str.indexOf("\",\"street");
		      if(provinceStart > 0 && provinceEnd > 0) {
		        String province = str.substring(provinceStart+11, provinceEnd);
		        address.append(province+",");
		      }
	        /*------city城市-------*/
		      int addStart = str.indexOf("city\":");
		      int addEnd = str.indexOf("\",\"country");
		      if(addStart > 0 && addEnd > 0) {
		        String city = str.substring(addStart+7, addEnd);
		        address.append(city+",");
		      }
		      /*------district县城-------*/
		      int districtStart = str.indexOf("district\":");
		      int districtEnd = str.indexOf("\",\"province");
		      if(districtStart > 0 && districtEnd > 0) {
		        String district = str.substring(districtStart+11, districtEnd);
		        address.append(district+",");
		      }
		      /*------street街道-------*/
		      int streetStart = str.indexOf("street\":");
		      int streetEnd = str.indexOf("\",\"street_number");
		      if(streetStart > 0 && streetEnd > 0) {
		        String street = str.substring(streetStart+9, streetEnd);
		        address.append(street+",");
		      }
		      /*------street_number街道号-------*/
		      int street_numberStart = str.indexOf("street_number\":");
		      int street_numberEnd = str.indexOf("\",\"country_code");
		      if(street_numberStart > 0 && street_numberEnd > 0) {
		        String street_number = str.substring(street_numberStart+16, street_numberEnd);
		        address.append(street_number+",");
		      } 
		      /*------sematic_description附近-------*/
		      int sematicStart = str.indexOf("sematic_description\":");
		      int sematicEnd = str.indexOf("\",\"cityCode");
		      if(sematicStart > 0 && sematicEnd > 0) {
		        String sematic_description = str.substring(sematicStart+22, sematicEnd);
		        address.append(sematic_description);
		      } 
	    }
	    return address.toString();
    
    }
  
  public static void main(String[] args) throws IOException, SQLException {
	  updateMapList();
//	 System.out.println(getAddress("116.214437","39.933329"));
    }
  /**
   * 更新入口 
   * @throws SQLException
   * @throws IOException
   */
  public static void updateMapList() throws SQLException, IOException{
	  List<mapItem> lists=getMapList();
	  String address=null;
	  String lat=null;
	  String lng=null;
	  int id=0;
	  for(mapItem mapitem:lists){
		  lat=mapitem.getLat();
		  lng=mapitem.getLng();
		  id=mapitem.getId();
		  address=getAddress(lng,lat);
		  updateAddress(id,address);
	  }
  }
  /**
   * 更新address字段
   * @param id
   * @param address
   * @throws SQLException
   */
  public static void updateAddress(int id,String address) throws SQLException{
	  	Connection conn = null;
			PreparedStatement st = null; 
			String sql = null; 
			ResultSet rs = null;
			sql="update tab_location set address='"
			+address
			+"'where id="+id;
			conn = new JdbcUtils02().getConnection();
			st = conn.prepareStatement(sql);
			st.executeUpdate(sql);  
			JdbcUtils02.release(conn, st, rs);
  }
  /**
   * 获得所有地图列表
   * @return
 * @throws SQLException 
   */
  public static List<mapItem> getMapList() throws SQLException{
	  Connection conn = null;
		PreparedStatement st = null;
	  	ResultSet rs = null;
		String sql = null;
		String num = null;
		
		sql="SELECT id,latitude,longitude from tab_location where address is null";
		conn = new JdbcUtils02().getConnection();
		st = conn.prepareStatement(sql);
		rs = st.executeQuery();  
		List<mapItem> list=new ArrayList();
		 
		while(rs.next()){
		//更新统计表的次数
			mapItem m=new mapItem(); 
			    m.setId(rs.getInt("id"));
			    m.setLat(rs.getString("latitude"));
			    m.setLng(rs.getString("longitude"));
				list.add(m);
		}
		JdbcUtils02.release(conn, st, rs);
	  return list;
  }
}
