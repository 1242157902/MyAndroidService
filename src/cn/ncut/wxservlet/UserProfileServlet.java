package cn.ncut.wxservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ncut.pushservice.AutoPushService;

import net.sf.json.JSONArray; 

import cn.ncut.devicedomain.MobileModel; 
import cn.ncut.pushdomain.JsonModel;
import cn.ncut.syssetdomain.User;
import cn.ncut.utils.DateUtils;
import cn.ncut.wxservice.UserProfileService;
import cn.ncut.wxdomain.Basic;
import cn.ncut.wxdomain.HabitCount;
import cn.ncut.wxdomain.HabitItem;
import cn.ncut.wxdomain.MapItem;
import cn.ncut.wxdomain.Prefer;
import cn.ncut.wxdomain.UserMapItem;
import cn.ncut.wxdomain.UserProfile;
import cn.ncut.wxdomain.UsersApp;
import cn.ncut.wxdomain.UsersPrefer;

 

public class UserProfileServlet extends HttpServlet {

	UserProfileService service=new UserProfileService();
 

  
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String method = request.getParameter("method");
		if (method.equals("SelectSingleUserMap")) {
			SelectSingleUserMap(request, response);
		}
		if (method.equals("GetSingleUserDayMap")) {
			GetSingleUserDayMap(request, response);
		}
		if (method.equals("GetSingleUserMonthMap")) {
			GetSingleUserMonthMap(request, response);
		}
		if (method.equals("SelectUserMap")) {
			SelectUserMap(request, response);
		}
		if (method.equals("GetUserHabit")) {
			GetUserHabit(request, response);
		}
		if (method.equals("GetUserHourHabit")) {
			GetUserHourHabit(request, response);
		}
		if (method.equals("GetUserPrefer")) {
			GetUserPrefer(request, response);
		}
		if (method.equals("GetUsersBasic")) {
			GetUsersBasic(request, response);
		}
		if (method.equals("GetUsersHabit")) {
			GetUsersHabit(request, response);
		}
		if (method.equals("GetUsersUseHabit")) {
			GetUsersUseHabit(request, response);
		}
		if (method.equals("GetUserApp")) {
			GetUserApp(request, response);
		}
		if (method.equals("GetUsersUsersApp")) {
			GetUsersUsersApp(request, response);
		}
		if (method.equals("GetUsersPic")) {
			GetUsersPic(request, response);
		}
		if (method.equals("GetUserProfile")) {
			GetUserProfile(request, response);
		}
		if (method.equals("GetUsersList")) {
			GetUsersList(request, response);
		}
		if (method.equals("GetClassList")) {
			GetClassList(request, response);
		}
		if (method.equals("PushLists")) {
			PushLists(request, response);
		}
	}
	/**
	 * 得到所有人一天的轨迹
	 * @param request
	 * @param response
	 */
	private void SelectUserMap(HttpServletRequest request,
			HttpServletResponse response) {
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		
		try {
			List<MapItem> mapList = service.GetMapList(startDate,endDate);
			
			for(MapItem mapitem : mapList){
				System.out.println(mapitem.getLatitude()+"--"+mapitem.getDate());
			}
			if(mapList.size()==0){
				response.getWriter().write("null");
			}
			else{ 
				String maplist = JSONArray.fromObject(mapList).toString();
				response.getWriter().write(maplist);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request,response);
	}
	/**
	 * 获取单个人每天的轨迹
	 * @param request
	 * @param response
	 */
	private void SelectSingleUserMap(HttpServletRequest request,
			HttpServletResponse response) {
		String phone_num=request.getParameter("phone_num");
		String date=request.getParameter("date");
		
		try {
			List<MapItem> mapList = service.GetSingleMapList(phone_num, date);
			
			for(MapItem mapitem : mapList){
				System.out.println(mapitem.getLatitude()+"--"+mapitem.getDate());
			}
			if(mapList.size()==0){
				response.getWriter().write("null");
			}
			else{
				//String json = "{\"total\":" + total + " , \"rows\":"
				//		+ JSONArray.fromObject(PushList).toString() + "}";
				String maplist = JSONArray.fromObject(mapList).toString();
				response.getWriter().write(maplist);
			}
		}   catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获得用户每天的每小时的轨迹
	 * @param request
	 * @param response
	 */
	private void GetSingleUserDayMap(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
	}
	 /**
	  * 当月的行为轨迹
	  * @param request
	  * @param response
	  */
	private void GetSingleUserMonthMap(HttpServletRequest request,
			HttpServletResponse response) {
		String phone_num=request.getParameter("phone_num");
		String date=request.getParameter("date"); 
		try {
			List<UserMapItem> mapList = service.GetSingleUserMonthMap(phone_num, date);
			 
			if(mapList.size()==0){
				response.getWriter().write("null");
			}
			else{
				  
				/*for(UserMapItem item:mapList){
					//统计当月去每个地方的总数
					String[] newaddress=substr(item.getAddress());
					//统计每天去某个地方的总数
				}*/
				String json = JSONArray.fromObject(mapList).toString();
				response.getWriter().write(json);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 查找某个人用户在某个月的数据
	 * @param request
	 * @param response
	 * @throws SQLException 
	 */
	private void GetUserHabit(HttpServletRequest request,
			HttpServletResponse response) {
		String device_number=request.getParameter("phone_num");
		String date=request.getParameter("date"); 
		System.out.println(device_number +" "+date );
		List<HabitCount> list;
		try {
			list = service.getUserHabit(device_number,date);
			String json="";
			if(list.size()==0){
				json="null";
			}
			else{
				json = JSONArray.fromObject(list).toString();
			}
			response.getWriter().write(json);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void GetUserHourHabit(HttpServletRequest request,
			HttpServletResponse response) {
		String device_number=request.getParameter("phone_num");
		String date=request.getParameter("date"); 
		System.out.println(device_number +" "+date );
		List<HabitItem> list;
		try {
			list = service.getUserHourHabit(device_number,date);
			String json="";
			if(list.size()==0){
				json="null";
			}
			else{
				json = JSONArray.fromObject(list).toString();
			}
			response.getWriter().write(json);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得图片点击+链接点击
	 * @param request
	 * @param response
	 */
	private void GetUserPrefer(HttpServletRequest request,
			HttpServletResponse response) {
		String device_number=request.getParameter("phone_num");
		String startdate=request.getParameter("startdate"); 
		String enddate=request.getParameter("enddate");  
		List<Prefer> list;
		try {
			list = service.getUserPrefer(device_number, startdate, enddate);
			String json="";
			System.out.println("size:"+list.size()+startdate+enddate);
			if(list.size()==0){
				json="null";
			}
			else{
				json = JSONArray.fromObject(list).toString();
			}
			response.getWriter().write(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 针对已注册用户获取用户的基本信息：性别数量、年龄数量
	 * @param request
	 * @param response
	 */
	private void GetUsersBasic(HttpServletRequest request,
			HttpServletResponse response) {
		List<Basic> list=new ArrayList<Basic>();
		list=service.getUsersBasic();
		try { 
			String json=""; 
			if(list.size()==0){
				json="null";
			}
			else{
				json = JSONArray.fromObject(list).toString();
			}
			response.getWriter().write(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 根据条件查询用户的次数频次信息
	 * @param request
	 * @param response
	 */

	private void GetUsersHabit(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		MobileModel qrc = new MobileModel();

		qrc.setGender(request.getParameter("sex"));

		
		qrc.setBirth(request.getParameter("age"));


		qrc.setProvider(request.getParameter("operator"));

		qrc.setArea(request.getParameter("area"));
		String date=request.getParameter("date").substring(0,7);

		System.out.println("sex"+request.getParameter("sex")+";age:"+request.getParameter("age"));
		String start = "null";

		String end = "null";


		// 返回设备信息
		List<HabitItem> list = service.GetUsersList(qrc,date);
		try { 
			String json=""; 
			if(list.size()==0){
				json="null";
			}
			else{
				json = JSONArray.fromObject(list).toString();
				System.out.println(json);
			}
			response.getWriter().write(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 得到用户每天使用手机总次数以及时长
	 * @param request
	 * @param response
	 */
	private void GetUsersUseHabit(HttpServletRequest request,
			HttpServletResponse response) {
		MobileModel qrc = new MobileModel();

		qrc.setGender(request.getParameter("sex"));
		qrc.setBirth(request.getParameter("age"));
		qrc.setProvider(request.getParameter("operator"));
		qrc.setArea(request.getParameter("area"));
		String dates=request.getParameter("date").substring(0,7);
		System.out.println("sex"+request.getParameter("sex")+";age:"+request.getParameter("age"));
		String start = "null";
		String end = "null";


		// 返回设备信息
		List<HabitCount> list = service.GetUsersUseList(qrc,dates);
		Map<String,HabitCount> map=new HashMap<String,HabitCount>();
		int day_dur0=0,day_times0=0;
		int day_dur1=0,day_times1=0;
		int day_dur2=0,day_times2=0;
		int day_dur3=0,day_times3=0;
		int day_dur4=0,day_times4=0;
		for(HabitCount h:list){
			HabitCount habit=new HabitCount();
			String date=h.getDate(); 
			int total_times=h.getTimes();
			int total_dur=h.getDura_time();
			if(map.containsKey(date)){
				habit.setDura_time(map.get(date).getDura_time()+total_dur);
				habit.setTimes(map.get(date).getTimes()+total_times);
				habit.setNum(map.get(date).getNum()+1);
				habit.setDate(date);
				map.put(date, habit);
				
			}else{
				habit.setDura_time(total_dur);
				habit.setNum(1);
				habit.setTimes(total_times);
				habit.setDate(h.getDate());
				map.put(date, habit);
			}
			if(total_dur<=120){
				day_dur0++;
			}else if(total_dur>120&&total_dur<=240){
				day_dur1++;
			}else if(total_dur>240&&total_dur<=300){
				day_dur2++;
			}else if(total_dur>300&&total_dur<=360){
				day_dur3++;
			}else if(total_dur>360){
				day_dur4++;
			}
			
			if(total_dur<=50){
				day_times0++;
			}else if(total_dur>50&&total_dur<=100){
				day_times1++;
			}else if(total_dur>100&&total_dur<=150){
				day_times2++;
			}else if(total_dur>150&&total_dur<=200){
				day_times3++;
			}else if(total_dur>2000){
				day_times4++;
			}
		}
/*		System.out.println("-----------总人数 "+list.size()+"----------------");
		System.out.println("---------------------------");
		System.out.println("<120分钟的人数："+day_dur0+";120< <240分钟的人数："+day_dur1+";240< <360分钟的人数："
		+day_dur2+";360< <480分钟的人数："+day_dur3+";>480分钟的人数："+day_dur4);
		System.out.println("---------------------------");
		System.out.println("<50次的人数："+day_times0+";50< <100次的人数："+day_times1+";100< <150次的人数："
				+day_times2+";150< <200次的人数："+day_times3+";>200次的人数："+day_times4);
		*/
		Object[] key = map.keySet().toArray(); 
    	Arrays.sort(key);
    	List<HabitCount> hlist=new ArrayList<HabitCount>();
    	for (int i = 0; i < key.length; i++) { 
        	hlist.add(map.get(key[i])); 
        	}
    	
		try { 
			String json=""; 
			if(list.size()==0){
				json="null";
			}
			else{
				json = JSONArray.fromObject(hlist).toString();
			} 
			response.getWriter().write(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 获取用户使用app的偏好
	 * @param request
	 * @param response
	 */
	private void GetUserApp(HttpServletRequest request,
			HttpServletResponse response) {
		String device_number=request.getParameter("phone_num");
		String startdate=request.getParameter("startdate"); 
		String enddate=request.getParameter("enddate");  
		List<Prefer> list;
		try {
			list = service.getUserApp(device_number, startdate, enddate);
			String json="";
			System.out.println("size:"+list.size()+startdate+enddate);
			if(list.size()==0){
				json="null";
			}
			else{
				json = JSONArray.fromObject(list).toString();
			}
			response.getWriter().write(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 获得指定用户群喜欢的图片类型
	 * @param request
	 * @param response
	 */
	private void GetUsersPic(HttpServletRequest request,
			HttpServletResponse response) {
		MobileModel qrc = new MobileModel();
		qrc.setGender(request.getParameter("sex"));
		qrc.setBirth(request.getParameter("age"));
		qrc.setProvider(request.getParameter("operator"));
		qrc.setArea(request.getParameter("area"));
		String date=request.getParameter("date").substring(0,7);
		// 返回设备信息
		List<UsersApp> list = service.GetUsersPicList(qrc,date);
		int size=list.size();
		try { 
			String json=""; 
			if(list.size()==0){
				json="null";
			}
			else{
				json = JSONArray.fromObject(list).toString();
			}
			System.out.println(json);
			response.getWriter().write(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 获得按类别用户使用app习惯
	 * @param request
	 * @param response
	 */
	private void GetUsersUsersApp(HttpServletRequest request,
			HttpServletResponse response) {
		MobileModel qrc = new MobileModel();

		qrc.setGender(request.getParameter("sex"));
		qrc.setBirth(request.getParameter("age"));
		qrc.setProvider(request.getParameter("operator"));
		qrc.setArea(request.getParameter("area"));
		String date=request.getParameter("date").substring(0, 7);
		String start = "null";
		String end = "null";
		// 返回设备信息
		List<UsersApp> list = service.GetUsersAppList(qrc,date);
		
		try { 
			String json=""; 
			if(list.size()==0){
				json="null";
			}
			else{
				json = JSONArray.fromObject(list).toString();
			}
			System.out.println(json);
			response.getWriter().write(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获得用户的个人画像 
	 * @param request
	 * @param response
	 */
	private void GetUserProfile(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String device_number=request.getParameter("phone_num");
		String date=request.getParameter("date");  
		UserProfile up;
		try {
			up = service.getUserProfile(device_number,date);
			String json="";
			json = JSONArray.fromObject(up).toString();
			response.getWriter().write(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获得用户列表
	 * @param request
	 * @param response
	 */
	private void GetUsersList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			int currentPage = Integer.parseInt(request.getParameter("page"));

			int pageSize = Integer.parseInt(request.getParameter("rows"));
//
			String orderstrString = orderstrString = "desc";//request.getParameter("order");
		

			MobileModel up = new MobileModel();

			User user = (User) request.getSession().getAttribute("user");

	//		up.setBirth(request.getParameter("age"));
			up.setGender(request.getParameter("sex")); 
			String classify=request.getParameter("classify") ;
			float weight=((Float) (request.getParameter("weight") == null ? 0
					: Float.parseFloat(request.getParameter("weight"))));
			// 返回设备信息
			List<MobileModel> mblist = service.GetPreferUsersList(currentPage,
					pageSize, up,classify,weight);
			int total=service.getTotalList(up,classify,weight);
			String json = "{\"total\":" + total + " ,\"rows\":"
						+ JSONArray.fromObject(mblist).toString() + "}";
			System.out.println("json"+json);	
			response.getWriter().write(json);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 获得分类标准
	 * @param request
	 * @param response
	 */
	private void GetClassList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<JsonModel> com=service.GetContentClassList();
			String json ="[";
			int start=0;	
			List<JsonModel> jm = new ArrayList<JsonModel>();
			for (int i = 0 ;i<com.size();i++) { 
				 if(com.get(i).getId().substring(0,9).equals(com.get(start).getId().substring(0,9))) jm.add(com.get(i));
				 else {if(start>0) json+=",";json+=jsonDatax(jm);start=i;jm.clear();jm.add(com.get(i));}
				 if(i==com.size()-1) {if(start>0) json+=",";json+=jsonDatax(jm);}
			}
			json += "]";
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private String jsonDatax(List<JsonModel> com)
	{
		String json ="";		
		boolean spli=false;
		int start=0;	
		if(com.size()>1) json="{\"id\":\""+com.get(start).getId()+"\",\"value\":\""+com.get(start).getId()+"\",\"text\":\""+com.get(start).getName()+"\",\"state\":\"closed\",\"children\":[";
		else json="{\"id\":\""+com.get(start).getId()+"\",\"value\":\""+com.get(start).getId()+"\",\"text\":\""+com.get(start).getName()+"\"}";
		List<JsonModel> jm = new ArrayList<JsonModel>();
		for (int i = 1 ;i<com.size();i++) { 
			if(com.get(i).getId().length()>com.get(start).getId().length()) {jm.add(com.get(i));start=i;}
			else {if(spli) json+=",";json+=jsonDatax(jm);start=i;spli=true;jm.clear();jm.add(com.get(i));}
			if(i==com.size()-1) {if(spli) json+=",";json+=jsonDatax(jm);}
		}
		if(com.size()>1) json += "]}";
		return json;
	}
	/**
	 * 批量推送
	 * @param request
	 * @param response
	 */
	private void PushLists(HttpServletRequest request,
			HttpServletResponse response) {
		String[] list=request.getParameterValues("imeis[]");
		String s=request.getParameter("imeis");
		System.out.println("list:"+list);
		String queid=request.getParameter("queid");
		AutoPushService aps = new AutoPushService();
		String result=aps.PushToSome(DateUtils.getCurrentDate(), "2017-04-19 00:00:00", queid, list);
		System.out.println(result);
	}
		/**
		 * 将字符串截取为数组
		 * @param str
		 * @return
		 */
		public static String[] substr(String str){
			String[] st = str.split(",");
			return st;
		}
}
