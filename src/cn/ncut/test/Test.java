package cn.ncut.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.ncut.devicedao.PhoneDao;
import cn.ncut.devicedomain.UserClickTime;
import cn.ncut.deviceservice.PhoneService;
import cn.ncut.pushdao.PhDao;

/**
 * @author wzq
 *
 *version 1.0 2015-6-25 上午10:41:18
 */
public class Test {
	
	public static void main (String[] args)throws Exception {
		
		String phonetype="ZTE 1111";
		
		PhoneDao dao=new PhoneDao();
		String phonetypeno = dao.getdevtypeno(phonetype);
		if (phonetypeno == null) {
			int max = dao.getmaxdevtypeno();
			max = max + 1;
			phonetypeno = max + "";
			dao.adddevtypeno(max, phonetype);
		}
//(String phoneimei, String phoneimsi,String phonenumber,
		//String phonetype, String phoneversion, String curr_version,int update_key)		
		dao.savedeviceinfo("865774023338142", "460025105012087", "18310556695", "ZTE NBY", "Android 4.2", "1.35", 1);
	
		
		
	}
	
	@org.junit.Test
	public void testjson()throws Exception {
		
		String json="{app_stat:[\"QQ\",\"微信\",\"逗地主\"]}";
		JSONObject object=new JSONObject(json);
		
		JSONArray	app_starts=object.getJSONArray("app_stat");
		StringBuilder sbBuilder=new StringBuilder();
		for (int j = 0; j < app_starts.length(); j++) {
			String app_name=(String)app_starts.get(j);
			sbBuilder.append(app_name).append(",");
			//System.out.println(app_name);
		}
		
		sbBuilder.deleteCharAt(sbBuilder.lastIndexOf(","));
		System.out.println(sbBuilder.toString());
	}
	
	@org.junit.Test
	public void testphdao(){
		
		PhDao dao=new PhDao();
		dao.ClientRecInfo("865774023338142", "c_1", "2015-07-23 10:22:22");
		
	}

	@org.junit.Test
	public void testjson2() throws Exception{
		

		 
		 //  app_flow:{"QQ":500,"易划"：600，"逗地主"：2000} 单位：b
		
		String json="{app_flow:{\"QQ\":500,\"易划\":600,\"逗地主\":2000}}";
		JSONObject jsonObject=new JSONObject(json);

		 JSONObject app_flow = jsonObject.getJSONObject("app_flow");
		 
		           Iterator<String> it= app_flow.keys();
		           
		          while (it.hasNext()) {
					 String string=app_flow.getString(it.next());
		        	  System.out.println(string);
				   }
		           
	}
	
	
	@org.junit.Test
	public void testjson3() throws Exception{
		
		String json="{phone_num:\"\",phone_imsi:\"460025106232654\",sequence:\"c_24\",receive_time:\"2015-07-27 15:25:32\",mobile_current_score:5,phone_imei:\"865774023338142\",phone_version:\"4.4.4\",phone_type:\"N958St\",curr_version:\"1.2.22\",location:[{longitude:116.21453,latitude:39.933347,time:\"2015-07-27 18:54:42\"}],pics_lack:0,model_flag:0,screen_onoff:[{0:\"2015-07-27 18:54:11\"},{1:\"2015-07-27 18:54:11\"},{0:\"2015-07-27 18:54:14\"},{1:\"2015-07-27 18:54:14\"},{0:\"2015-07-27 18:54:19\"},{1:\"2015-07-27 18:54:19\"}],icons_url:[[[],[],[],[]],[[],[],[],[]],[[],[],[],[]]],app_flow:{\"易划\":5785553}}";
		
		PhoneService service=new PhoneService();
		service.SaveOpenInfo(json);
		
		
	}
	
	@org.junit.Test
	public void testjson4() throws Exception{
		PhoneDao dao=new PhoneDao();
		String json="{icons_url:" +
				"[" +
				
				"[[\"2015-08-07 12:13:48\"],[],[],[]]," +
				"[[\"2015-08-07 12:13:48\"],[],[],[]]," +
				"[[],[\"2015-08-07 12:13:48\"],[],[]]," +
				"[[],[],[],[\"2015-08-07 12:13:48\"]]" +
				
				"]," +
				"pics_url:" +
				"[" +
				
				"[\"http://www.sina.com\"]," +
				"[\"http://www.sina.com\",\"http://www.10010.com\"]," +
				"[\"http://www.sina.com\",\"http://www.10010.com\",\"http://m.sohu.com\"]," +
				"[\"http://www.sina.com\",\"http://www.10010.com\",\"http://m.sohu.com\",\"http://m.baidu.com\"]" +
				
				"]" +
				
				"}";
		
		JSONObject jsonObject=new JSONObject(json);
		
		JSONArray myarray=jsonObject.getJSONArray("icons_url");
		JSONArray array=dao.getArray("22");
		
		List<UserClickTime> userClickTimes=new ArrayList<UserClickTime>();
		
		for (int i = 0; i < myarray.length(); i++) {
			
			 JSONArray myarray2=myarray.getJSONArray(i);//图片 
				
			 System.out.println(myarray2.toString());
				
				
				for (int j = 0; j <myarray2.length(); j++) {
					JSONArray myarray3=myarray2.getJSONArray(j);//图标
					System.out.println(myarray3.toString());
					
					
					for (int k = 0; k < myarray3.length(); k++) {
						String str=myarray3.getString(k);
						System.out.println(str);//时间
						
						String url=null;
						
						if (array.length()>i) {
							
							JSONArray array2=array.getJSONArray(i);
							
							if (array2.length()>j) {
								
								 url=array2.getString(j);
							}
						}
						
					
					
					System.err.println("url:"+url+"  time:"+str);
					UserClickTime userClickTime=new UserClickTime("111111", "18333110", url, str);
  					
  					userClickTimes.add(userClickTime);
						
					}
				    
				}
				
		}
		
		
	   
		  dao.saveUserClickTime(userClickTimes);
		  
		  
		 
	}
}
