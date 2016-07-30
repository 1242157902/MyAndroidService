package cn.ncut.test;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;

import org.junit.Test;

import cn.ncut.deviceservice.PhoneService;
import cn.ncut.pushdao.PhDao;
import cn.ncut.pushdomain.ClientAppstore;
import cn.ncut.pushservice.AppService;
import cn.ncut.pushservice.PhService;

public class STest {
	
	/*
	public void firsttest() throws Exception {
		PhoneService x=new PhoneService();
		String json="{phone_num:\"\",phone_imsi:\"460025106232654\",mobile_current_score:620,phone_imei:\"865774023338142\"," +
				"phone_version:\"4.4.4\",phone_type:\"N958St\",curr_version:\"1.1.14\",pics_lack:0,record_count:0,pics_name:[]}";
	   System.out.println(x.SaveOpenInfo(json));
	}

	public void firsttest() throws Exception {
		PhoneService x=new PhoneService();
		String json="{phone_num:\"\",phone_imsi:\"460025013737962\",mobile_current_score:0,phone_imei:\"866184028815624\"," +
				"phone_version:\"4.4.4\",phone_type:\"ml note\",curr_version:\"1.2.1\",pics_lack:0,model_flag:5,record_count:0,iconurl:[],pics_name:[]}";
	   System.out.println(x.SaveOpenInfo(json));
	}
	*/
	
	public void regextest()
	{ 
		String curr_version="1.1x2"; 
	   if(Pattern.compile("^1\\.2.*").matcher(curr_version).matches())  {System.out.println("YES");}
	   else {  System.out.println("NO");}
	}

	@Test
	public void hehe()
	{
		String xx="123^335";
		System.out.println(xx.split("\\^")[0]);
	}


	public void secondTest()
	{
		PhoneService xx=new PhoneService();
		String str="{phone_num:\"+8618510463708\",phone_imsi:\"460010461501011\",sequence:\"b_37\",receive_time:\"2015-09-28 13:47:06\",mobile_current_score:0,phone_imei:\"356812060562699\",phone_version:\"4.4.4\",phone_type:\"HTC D820mt\",curr_version:\"1.2.34\",location:[{longitude:116.214529,latitude:39.933457,time:\"2015-09-28 13:49:36\"}],pics_lack:0,model_flag:0,screen_onoff:[{0:\"2015-09-28 13:49:32\"},{1:\"2015-09-28 13:49:32\"}],icons_url:[[[],[],[],[]],[[],[],[],[]],[[],[],[],[]],[[],[],[],[]],[[],[],[],[]],[[],[],[],[]],[[],[],[],[]],[[],[],[],[]],[[],[],[],[]],[[],[],[],[]],[[],[],[],[]],[[],[],[],[]]],app_flow:{\"易划\":3641534}}";
		xx.SaveOpenInfo(str);
		
	}
}
