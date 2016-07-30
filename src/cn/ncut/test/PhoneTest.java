package cn.ncut.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import cn.ncut.devicedao.PhoneDao;
import cn.ncut.devicedomain.Interval;
import cn.ncut.deviceservice.PhoneService;
import cn.ncut.deviceservice.RegisterService;
import cn.ncut.pushdao.PhDao;
import cn.ncut.pushservice.PhService;
import cn.ncut.syssetdao.TimeDao;
import cn.ncut.utils.JdbcUtils;
import cn.ncut.utils.StaticTest;


/**
 * @author wzq
 *
 * version 1.0 2014-9-19 下午4:57:43
 */
public class PhoneTest {
	
	PhoneService service=new PhoneService();
	PhoneDao dao=new PhoneDao();
	@Test
	public void  addPhoneinfo() {
		
		for (int i = 0; i < 10; i++) {
			//dao.SavePhoneInfo("1503816203"+i, "三星GST756"+i,"11122212","ffd","11222","2013-20-10");
		}
	}
	@Test
	public void addOpeninfo() {
		//String string = "[{pao1.jpg:[{1:\"xx\"},{2:\"xxx\"},{3:\"xxx\"}]}]";
		String json="{phone_version:'iPhone OS7.0.6',phone_type:'iPhone',curr_version:'1.1.9',phone_num:'13910819799',phone_imsi:'',P_20150130101442_1_4.jpg:[{\"1\":\"2015-01-30 15:37:53\"},{\"4\":\"2015-01-30 15:37:55\"},{\"7\":\"2015-01-30 15:38:18\"},{\"13\":\"2015-01-30 15:38:27\"}],phone_imei:'358312050267266',pics_name:[\"P_20150130101442_1_4.jpg\"],record_count:4}";
		//json="{phone_version:'iPhone OS8.1.1',phone_type:'iPhone',curr_version:'1.0',phone_num:'',phone_imsi:'',001:['2014-12-22 21:22:23','2014-12-22 21:22:46'],pics_name:['001'],phone_imei:'358312050267266'}";
		
		try {
			System.out.println(service.SaveOpenInfo(json));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	/*public static void main(String[] args) {
		PhoneService service=new PhoneService();
        String ud= service.uncode("ww#$s?kmdvv");
       
        System.out.print(ud);
        
        
    
		
	}*/
@Test
public void testtimedao() {
	
		TimeDao dao=new TimeDao();
		
	Interval interval=dao.gettime();
		
		System.err.println(interval.toString());
		
		
}

@Test
public void testWxCountByDay() {
	/*WxQueryService service=new WxQueryService();
	 Map<String, String> map=service.countbyday("18310556695", "2014", "11");
	 Set<Entry<String, String>> entrySet = map.entrySet();
	 for (Entry<String, String> entry : entrySet) {
		
		 System.out.println(entry.getKey()+"="+entry.getValue());
		 
	}*/
	 
	String picString="{flag:2,interval_time:60,update_app:\"MyAndroidProject.apk\"}";
	 System.out.println(picString);
}

@Test
public void push(){
	
  PhService service=new PhService();
  //String string=service.PushMessage("1111");
 // System.err.println(string);
	
	
}
@Test
public void queryarea(){
	
	long begin=System.currentTimeMillis();
	Connection conn = null;
	PreparedStatement st = null;
	ResultSet rs = null;
	String num="15038162039";
	num=num.substring(0, 7);
	String mobilearea=null;
	String mobiletype=null;
	String areacode=null;
	
	try {
		conn = JdbcUtils.getConnection();
		String sql = "select * from tab_mobileinfo where mobile_num=?";
		st = conn.prepareStatement(sql);
		st.setString(1, num);

		rs = st.executeQuery();

		if (rs.next()) {
			mobilearea=rs.getString("mobile_area");
			mobiletype=rs.getString("mobile_type");
			 areacode=rs.getString("area_code");
		
		}
		
		mobilearea="黑龙江";
		
		if (mobilearea.length()>2) {
			mobilearea=mobilearea.substring(0,3);
		}
		mobilearea=mobilearea.substring(0,3);
		
		System.out.println(mobilearea+"  "+mobiletype+"   "+areacode);
		long end=System.currentTimeMillis();
		System.out.println((end-begin)/1000);
	} catch (Exception e) {

		e.printStackTrace();
	} finally {
		JdbcUtils.release(conn, st, rs);
	}
   }  

public static void main(String[] args) {
	
	
	
	try {
		//PhoneService service=new PhoneService();
		
	 // String json="{phone_num:\"\",phone_imsi:\"460025106232654\",mobile_current_score:18,phone_imei:\"865774023338142\",phone_version:\"4.4.4\",phone_type:\"N958St\",curr_version:\"1.2.7\",pics_lack:0,model_flag:0,record_count:18,pics_name:[\"P_20150522061929_1_7.jpg\",\"P_20150521092155_1_4.jpg\",\"P_20150521091858_1_7.jpg\",\"P_20150518135410_1_3.jpg\",\"P_20150518135654_1_0.jpg\",\"P_20150521131935_1_6.jpg\",\"P_20150522060834_1_4.jpg\"],P_20150522061929_1_7.jpg:[{1:\"2015-06-25 11:41:14\"},{2:\"2015-06-25 11:41:22\"}],P_20150521092155_1_4.jpg:[{3:\"2015-06-25 11:41:14\"},{4:\"2015-06-25 11:41:21\"},{5:\"2015-06-25 11:41:22\"}],P_20150521091858_1_7.jpg:[{6:\"2015-06-25 11:41:15\"},{7:\"2015-06-25 11:41:20\"},{8:\"2015-06-25 11:41:22\"}],P_20150518135410_1_3.jpg:[{9:\"2015-06-25 11:41:15\"},{10:\"2015-06-25 11:41:16\"},{11:\"2015-06-25 11:41:20\"},{12:\"2015-06-25 11:41:23\"}],P_20150518135654_1_0.jpg:[{13:\"2015-06-25 11:41:16\"},{14:\"2015-06-25 11:41:17\"},{15:\"2015-06-25 11:41:20\"}],P_20150521131935_1_6.jpg:[{16:\"2015-06-25 11:41:17\"},{17:\"2015-06-25 11:41:19\"}],P_20150522060834_1_4.jpg:[{18:\"2015-06-25 11:41:19\"}]}";
		
			//System.out.println(service.saveslideinfo(json));
		String phonenumber="+8613210556695";
		
		phonenumber=phonenumber.substring(3);
			
			System.out.println(phonenumber);
			
	} catch (Exception e) {
	
		e.printStackTrace();
	}

	
	
	/* try {
		JSONObject jsonObject=new JSONObject(json);
		
		
		 JSONArray jsonarray =jsonObject.getJSONArray("location");
		 
		 for (int i = 0; i < jsonarray.length(); i++) {
			            JSONObject object= (JSONObject) jsonarray.get(i);
			            
			            System.out.println(object.getString("time"));;
			 
			 
		}
		 
		
		
		
		
		
	} catch (JSONException e) {
		
		e.printStackTrace();
	}
	
	*/
	
	
	
   }
}
