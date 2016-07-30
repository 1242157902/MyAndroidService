package cn.ncut.wxservice;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import cn.ncut.pushservice.AutoPushService;
import cn.ncut.utils.MapConvertAPI;

import cn.ncut.wxdao.DeviceInforDao;
import cn.ncut.wxdao.LocationClassifyDao;
import cn.ncut.wxdomain.LoctionClassify;
import cn.ncut.wxdomain.Parameters;
import cn.ncut.utils.DateUtils;

public class RecommendLocationService {

	public static void main(String[] args)
	{
		//北方工业大学
		//Parameters parameters = new Parameters("15236282824","39.933448","116.197068");
		//金鼎公寓
		//Parameters parameters = new Parameters("15236282824","39.933329","116.214437");
		//Parameters parameters = new Parameters("15236282824","39.933225","116.197557");
		RecommendLocationService rls = new RecommendLocationService();
		//LocationThread lt = new LocationThread("15236282824","39.933391","116.214436");
		//LocationThread lt = new LocationThread("13261500434","39.933225","116.197557");
		//new Thread(lt).start();
		//rls.setRecommentContent(parameters);
	}

	LocationClassifyDao lcd = new LocationClassifyDao();
	AutoPushService aps = new AutoPushService();
	DeviceInforDao did = new DeviceInforDao();
	
	
	/**
	 * 
	 * @return:       void 
	 * @param parameters
	 * <p>Description: 判断并设置推荐内容<p>
	 * @date:          2016年3月20日下午3:41:45
	 * @author         ysl
	 */
	 public  void setRecommentContent(Parameters parameters)
	 {
		 /**
		  * 1、先获取经纬度，将经纬度转化为相应的位置
		  * 2、如果该位置包含北方工业大学，金鼎公寓则返回该地区的标签
		  * 3、调用推送接口，为用户推送该地区对应的序列
		  */
		 String phone = null;
		 String longitude = null;
		 String latitude = null; 
		 String address = null;
 
		 Boolean flag = true;//用以标识位置否发生变化
		 if(parameters!=null)
		 {
			 phone = parameters.getPhone();
			 longitude = parameters.getLongitude();
			 latitude = parameters.getLatitude(); 
			 if(longitude!=null&latitude!=null&phone!=null)
			 {

				 try {
					address = MapConvertAPI.getAddress(longitude, latitude);
					System.out.println(address);
					if(address!=null)
					{ 
						List<LoctionClassify> lcList = lcd.getLocationClassifysByCode("001");
						for (LoctionClassify loctionClassify : lcList)
						{
							System.out.println(loctionClassify.getName());
							if(address.contains(loctionClassify.getName()))
				//			if(address.contains("北方工业大学")||address.contains("金鼎大学生公寓"))
							{
								System.out.println("end-----");
								if(loctionClassify.getQue()!=0)
								{
									System.out.println("##########"+loctionClassify.getValue()+loctionClassify.getQue()+loctionClassify.getDuration());
									recommentContent(phone,loctionClassify.getValue(),loctionClassify.getDuration());
								}
								System.out.println("===");
							}
						}
					}
				} catch (IOException e) {
					System.out.println("程序发生异常！！请检查代码！");
					e.printStackTrace();
				}
			 }
		 }
		 
	 }
	 public  Integer  setRecommentContentTest(Parameters parameters)
	 {
		 /**
		  * 1、先获取经纬度，将经纬度转化为相应的位置
		  * 2、如果该位置包含北方工业大学，金鼎公寓则返回该地区的标签
		  * 3、调用推送接口，为用户推送该地区对应的序列
		  */
		 String phone = null;
		 String longitude = null;
		 String latitude = null; 
		 String address = null;
		 
		 Boolean flag = true;//用以标识位置否发生变化
		 if(parameters!=null)
		 {
			 phone = parameters.getPhone();
			 longitude = parameters.getLongitude();
			 latitude = parameters.getLatitude();
			 
			 if(longitude!=null&latitude!=null&phone!=null)
			 {
				 
				 try {
					 address = MapConvertAPI.getAddress(longitude, latitude);
					 if(address!=null)
					 {
						 List<LoctionClassify> lcList = lcd.getLocationClassifysByCode("001");
						 for (LoctionClassify loctionClassify : lcList)
						 {
							 //System.out.println(loctionClassify.getName());
							 if(address.contains(loctionClassify.getName()))
							 {
								 if(loctionClassify.getQue()!=0)
								 {
									 System.out.println("##########"+loctionClassify.getValue()+loctionClassify.getQue()+loctionClassify.getDuration());
									// recommentContent(phone,loctionClassify.getValue(),loctionClassify.getDuration());
									 return loctionClassify.getQue();
								 }
							 }
						 }
					 }
				 } catch (IOException e) {
					 System.out.println("程序发生异常！！请检查代码！");
					 e.printStackTrace();
				 }
			 }
		 }
		 return null;
		 
	 }
	 
	 /**
	  * 
	  * @return:       void 
	  * @param phone
	  * @param code
	  * @param hour：以小时为单位
	  * <p>Description: 推荐函数<p>
	  * @date:          2016年3月20日下午3:40:32
	  * @author         ysl
	  */
	 public void recommentContent(String phone,String code,int hour)
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
						String imei = did.getDeviceImeiByPhone(phone);
						System.out.println(imei+"-------------");
						if(imei!=null)
						{
							System.out.println(DateUtils.getCurrentDate()+"----#---------");
							System.out.println(DateUtils.getTimeOfDay(new Date(), hour)+"-------------");
							System.out.println(imei+"$$$$$$$$$$$$"+que);
							String str=aps.PushToSingle(DateUtils.getCurrentDate(), DateUtils.getTimeOfDay(new Date(), hour), que, imei);
							System.out.println("推送结果："+str);
						}
					}
				}
		 }
		 
	 }

}
