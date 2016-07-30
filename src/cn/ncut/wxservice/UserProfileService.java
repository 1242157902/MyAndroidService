package cn.ncut.wxservice;

import java.sql.SQLException;
import java.util.List;

import cn.ncut.devicedomain.MobileModel;
import cn.ncut.pushdomain.JsonModel;
import cn.ncut.wxdao.UserProfileDao;
import cn.ncut.wxdomain.Basic;
import cn.ncut.wxdomain.HabitCount;
import cn.ncut.wxdomain.HabitItem;
import cn.ncut.wxdomain.MapItem;
import cn.ncut.wxdomain.Prefer;
import cn.ncut.wxdomain.UserMapItem;
import cn.ncut.wxdomain.UserProfile;
import cn.ncut.wxdomain.UsersApp;
import cn.ncut.wxdomain.UsersPrefer;

public class UserProfileService {

	UserProfileDao dao=new UserProfileDao();
	public List<MapItem> GetSingleMapList(String phone_num, String date) throws SQLException{
		// TODO Auto-generated method stub
		return dao.GetSingleMapList(phone_num, date);
	}
	public List<UserMapItem> GetSingleUserMonthMap(String phone_num, String date) throws SQLException {
		// TODO Auto-generated method stub
		return dao.GetSingleUserMonthMap(phone_num, date);
	}
	public List<MapItem> GetMapList(String startDate, String endDate) throws SQLException {
		// TODO Auto-generated method stub
		return dao.GetMapList(startDate,endDate);
	}
	public List<HabitCount> getUserHabit(String device_number, String date) throws SQLException {
		// TODO Auto-generated method stub
		return dao.getUserHabit(device_number,date);
	}
	public List<HabitItem> getUserHourHabit(String device_number, String date) throws SQLException {
		// TODO Auto-generated method stub
		return dao.getUserHourHabit(device_number,date);
	}
	public List<Prefer> getUserPrefer(String device_number,
			String startdate, String enddate) {
		// TODO Auto-generated method stub
		return dao.getUserPrefer(device_number,startdate,enddate);
	}
	public List<Basic> getUsersBasic() {
		// TODO Auto-generated method stub
		return dao.getUsersBasic();
	}
	public List<HabitItem> GetUsersList(MobileModel qrc, String date) {
		// TODO Auto-generated method stub
		return dao.GetUsersList(MobileQuery(qrc),date);
	}
	public List<HabitCount> GetUsersUseList(MobileModel qrc, String dates) {
		// TODO Auto-generated method stub
		return dao.GetUsersUseList(MobileQuery(qrc),dates);
	}
	public List<Prefer> getUserApp(String device_number, String startdate,
			String enddate) {
		// TODO Auto-generated method stub
		return dao.getUserApp(device_number,startdate,enddate);
	}
	public List<UsersApp> GetUsersAppList(MobileModel qrc, String date) {
		// TODO Auto-generated method stub
		return dao.GetUsersAppList(MobileQuery(qrc),date);
	}
	public List<UsersApp> GetUsersPicList(MobileModel qrc, String date) {
		// TODO Auto-generated method stub
		return dao.GetUsersPicList(MobileQuery(qrc),date);
	}
	public UserProfile getUserProfile(String device_number,
			String date) {
		// TODO Auto-generated method stub
		return dao.GetUserProfileNew(device_number,date);
	}
	public List<MobileModel> GetPreferUsersList(int currentPage,
			int pageSize, MobileModel up, String classify, float weight) {
		// TODO Auto-generated method stub
		return dao.GetPreferUsersList(currentPage,pageSize,MobileQuery(up), classify,weight);
	}
	public int getTotalList(MobileModel up, String classify, float weight) {
		// TODO Auto-generated method stub
		return dao.getTotalList(MobileQuery(up), classify,weight);
	}
	//拼接sql
		private String MobileQuery(MobileModel qrc) {
			
			int i;
			String sql="";
			String[] temp;
			String tempsql;		
		
			if(qrc.getMbno()!=null&&!"".equals(qrc.getMbno().trim())) 
			{
				if(qrc.getMbno().trim().matches("\\d{1,11}"))
					
					sql+="and device_number like '%"+qrc.getMbno().trim()+"%' ";
				
				else 
					
					sql+="and device_number in ("+qrc.getMbno().trim()+") ";	
			}
			
			if(qrc.getGender()!=null&&!"".equals(qrc.getGender())) 
				
				sql+="and user_sex="+qrc.getGender()+" ";	
			if(qrc.getProvider()!=null&&!"".equals(qrc.getProvider())) 
			{
				temp=qrc.getProvider().split(",");
				
				tempsql="";
				
				for(i=0;i<temp.length;i++)
				{
					tempsql+="or mobile_type like '"+temp[i]+"%' ";
				}
				
				sql+="and ("+tempsql.substring(2)+") ";
				
			}
			
			if(qrc.getMbtype()!=null&&!"".equals(qrc.getMbtype())) 
				
				sql+="and (device_type in ("+qrc.getMbtype()+") or  left(device_type,2) in ("+qrc.getMbtype()+")) ";
			
			
			if(qrc.getBirth()!=null&&!qrc.getBirth().equals("")) 
			{
				temp=qrc.getBirth().split(",");
				
				tempsql="";
				
				for(i=0;i<temp.length;i++)
				{
					tempsql+="or ("+RangeAge(temp[i])+") ";
				}
				
				sql+="and ("+tempsql.substring(2)+") ";
				
			}
	
			if(qrc.getArea()!=null&&!"".equals(qrc.getArea()))
				
				sql+="and (mobile_area in ("+qrc.getArea()+") or  left(mobile_area,2) in ("+qrc.getArea()+")) ";
			
		
			
			if(sql.length()>0)
				
				return "where "+sql.substring(3);
			
			
			else 
				
				return "";
		}
		
		private String RangeAge(String agerange) {
			String[] age=agerange.split("-");
			if(age[0].equals("null"))  return " YEAR(NOW())-CAST(user_birth AS SIGNED)<"+age[1]+" ";
			else if(age[1].equals("null")) return " Year(NOW())-YEAR(NOW())-CAST(user_birth AS SIGNED)>"+age[0]+" ";
			else return " YEAR(NOW())-CAST(user_birth AS SIGNED)>"+age[0]+" and "+" YEAR(NOW())-CAST(user_birth AS SIGNED)<"+age[1]+" ";
		}
		public List<JsonModel> GetContentClassList() {
			// TODO Auto-generated method stub
			return dao.GetContentClassList();
		}
		
		
		
		
		
		
		
		
}
