package cn.ncut.pushservice;

import java.util.List;

import cn.ncut.pushdao.PlDao;
import cn.ncut.pushdomain.ComBox;
import cn.ncut.pushdomain.ErrorLog;
import cn.ncut.pushdomain.PushAccepter;
import cn.ncut.pushdomain.PushLog;
import cn.ncut.pushdomain.QueryRecCon;
import cn.ncut.pushdomain.RcvLog;
import cn.ncut.pushdomain.ThemeLog;
import cn.ncut.pushdomain.ThemeTask;

public class PlService {

	PlDao dao = new PlDao();
	
	public List<PushLog> GetPushLogList(int currentPage, int pageSize, PushLog plg) {
		String startmonth="000000";
		String endmonth="999999";
		if(!plg.getOpertime().equals("null*null")) 
		{
			String[] temp=plg.getOpertime().split("\\*");
			if(!temp[0].equals("null")) startmonth=temp[0].substring(0, 4)+temp[0].substring(5,7);
			if(!temp[1].equals("null")) endmonth=temp[1].substring(0, 4)+temp[1].substring(5,7);
		}
		return dao.GetPushLogList(currentPage,pageSize,PushLogQuery(plg),startmonth,endmonth);
	}
	
	public int GetPushLogTotal(PushLog plg) {
		String startmonth="000000";
		String endmonth="999999";
		if(!plg.getOpertime().equals("null*null")) 
		{
			String[] temp=plg.getOpertime().split("\\*");
			if(!temp[0].equals("null")) startmonth=temp[0].substring(0, 4)+temp[0].substring(5,7);
			if(!temp[1].equals("null")) endmonth=temp[1].substring(0, 4)+temp[1].substring(5,7);
		}
		return dao.GetPushLogTotal(PushLogQuery(plg),startmonth,endmonth);
	}
	
	public List<RcvLog> GetRcvLogList(int currentPage, int pageSize, RcvLog plg) {
		String startmonth="000000";
		String endmonth="999999";
		if(!plg.getAcctime().equals("null*null")) 
		{
			String[] temp=plg.getAcctime().split("\\*");
			if(!temp[0].equals("null")) startmonth=temp[0].substring(0, 4)+temp[0].substring(5,7);
			if(!temp[1].equals("null")) endmonth=temp[1].substring(0, 4)+temp[1].substring(5,7);
		}
		return dao.GetRcvLogList(currentPage,pageSize,RcvLogQuery(plg),startmonth,endmonth);
	}
	
	public int GetRcvLogTotal(RcvLog plg) {
		String startmonth="000000";
		String endmonth="999999";
		if(!plg.getAcctime().equals("null*null")) 
		{
			String[] temp=plg.getAcctime().split("\\*");
			if(!temp[0].equals("null")) startmonth=temp[0].substring(0, 4)+temp[0].substring(5,7);
			if(!temp[1].equals("null")) endmonth=temp[1].substring(0, 4)+temp[1].substring(5,7);
		}
		return dao.GetRcvLogTotal(RcvLogQuery(plg),startmonth,endmonth);
	}
	public String RcvLogQuery(RcvLog plg)
	{
		int i;
		String sql="";
		String[] temp;
		if(plg.getMbno()!=null&&!plg.getMbno().trim().equals("")) 
		{
			sql+="and b.device_number like '%"+plg.getMbno().trim()+"%' ";	
		}
		if(plg.getImei()!=null&&!plg.getImei().trim().equals("")) 
		{
			sql+="and a.imei like '%"+plg.getImei().trim()+"%' ";	
		}
		if(plg.getType()!=null&&!plg.getType().equals("")) sql+="and a.type in ("+plg.getType()+") ";	
		
		if(plg.getPhmsg()!=null&&!plg.getPhmsg().equals("")) sql+="and a.queid in ("+plg.getPhmsg()+") ";	
		if(!plg.getAcctime().equals("null*null")) 
		{
			temp=plg.getAcctime().split("\\*");
			if(!temp[0].equals("null")) sql+="and a.acctime>='"+temp[0]+"' ";
			if(!temp[1].equals("null")) sql+="and a.acctime<='"+temp[1]+"' ";
		}
		if(sql.length()>0) return "where"+sql.substring(3);
		else return "";
	}
	
	public String  PushLogQuery(PushLog plg)
	{
		int i;
		String sql="";
		String[] temp;
		if(plg.getMbno()!=null&&!plg.getMbno().trim().equals("")) 
		{
			sql+="and b.device_number like '%"+plg.getMbno().trim()+"%' ";	
		}
		if(plg.getImei()!=null&&!plg.getImei().trim().equals("")) 
		{
			sql+="and a.imei like '%"+plg.getImei().trim()+"%' ";	
		}
		if(plg.getOper()!=null&&!plg.getOper().equals("")) sql+="and a.pop="+plg.getOper()+" ";	
		if(plg.getPhmsg()!=null&&!plg.getPhmsg().equals("")) sql+="and a.puid in ("+plg.getPhmsg()+") ";	
		if(!plg.getOpertime().equals("null*null")) 
		{
			temp=plg.getOpertime().split("\\*");
			if(!temp[0].equals("null")) sql+="and a.htime>='"+temp[0]+"' ";
			if(!temp[1].equals("null")) sql+="and a.htime<='"+temp[1]+"' ";
		}
		if(sql.length()>0) return "where"+sql.substring(3);
		else return "";
	}
	
	public String  ThemeLogQuery(ThemeLog tlg)
	{
		int i;
		String sql="";
		String[] temp;
		if(tlg.getMbno()!=null&&!tlg.getMbno().trim().equals("")) 
		{
			sql+="and a.mbno like '%"+tlg.getMbno().trim()+"%' ";	
		}
		if(tlg.getImei()!=null&&!tlg.getImei().trim().equals("")) 
		{
			sql+="and a.imei like '%"+tlg.getImei().trim()+"%' ";	
		}
		if(tlg.getCurtheme()!=null&&!tlg.getCurtheme().equals("")) sql+="and a.curtheme="+tlg.getCurtheme()+" ";	
		if(tlg.getExptheme()!=null&&!tlg.getExptheme().equals("")) sql+="and a.exptheme="+tlg.getExptheme()+"  ";	
		if(tlg.getOpersrc()!=null&&!tlg.getOpersrc().equals("")) sql+="and a.opersrc='"+tlg.getOpersrc()+"' ";	
		if(!tlg.getOpertime().equals("null*null")) 
		{
			temp=tlg.getOpertime().split("\\*");
			if(!temp[0].equals("null")) sql+="and a.opertime>='"+temp[0]+"' ";
			if(!temp[1].equals("null")) sql+="and a.opertime<='"+temp[1]+"' ";
		}
		if(sql.length()>0) return "where"+sql.substring(3);
		else return "";
	}
	
	public String  ErrorLogQuery(ErrorLog qrc)
	{
		String sql="";
		String[] temp;
		if(qrc.getImei()!=null&&!qrc.getImei().trim().equals("")) 
		{
			sql+="and imei like '%"+qrc.getImei().trim()+"%' ";	
		}
		if(qrc.getErrtype()!=null&&!qrc.getErrtype().trim().equals("")) 
		{
			sql+="and LOCATE(type,'"+qrc.getErrtype()+"')>0 ";	
		}
		if(!qrc.getErrtime().equals("null*null")) 
		{
			temp=qrc.getErrtime().split("\\*");
			if(!temp[0].equals("null")) sql+="and time>='"+temp[0]+"' ";
			if(!temp[1].equals("null")) sql+="and time<='"+temp[1]+"' ";
		}
		if(sql.length()>0) return "where"+sql.substring(3);
		else return "";
	}
	
	public List<ComBox> GetPushMsgList() {
		return dao.GetPushMsgList();
	}
	public List<ComBox> GetErrorTypeList() {
		return dao.GetErrorTypeList();
	}
	public List<ComBox> GetPushQueList() {
		return dao.GetPushQueList();
	}

	public List<ThemeTask> GetThemeTaskList(int currentPage, int pageSize,String operId) {
		return dao.GetThemeTaskList(currentPage, pageSize, operId);
	}
	public int GetThemeTaskTotal(String operId) {
		return dao.GetThemeTaskTotal(operId);
	}

	public void AddMbthemeItem(ThemeTask item) {
		dao.AddMbthemeItem(item);		
	}

	public void UpdateMbthemeItem(ThemeTask item) {
		dao.UpdateMbthemeItem(item);
	}

	public void DeleteMbthemeItem(int tid) {
		dao.DeleteMbthemeInDetail(tid);	
		dao.DeleteMbthemeItem(tid);		
	}

	public List<PushAccepter> GetAccepterList(int currentPage, int pageSize, QueryRecCon qrc) {
	   return dao.GetAccepterList(currentPage, pageSize,PushDetailQRC(qrc),qrc.getPtlid());		
	}

	public int GetAccepterTotal(QueryRecCon qrc) {
	   return dao.GetAccepterTotal(PushDetailQRC(qrc));
	}

	public void SingleOkPush(String imei, String pid,String operId) {
		dao.SingleOkPush(imei,pid,operId);
	}	

	public void SingleCancelPush(String imei, String pid,String operId) {
		dao.SingleCancelPush(imei,pid,operId);
	}
	
	public void BatchOkPush(String imeis, String pid,String operId) {
		dao.BatchOkPush(imeis,pid,operId);
	}

	public void BatchCancelPush(String imeis, String pid,String operId) {
		dao.BatchCancelPush(imeis,pid,operId);
	}
	
	
	public String PushDetailQRC(QueryRecCon qrc)
	{		
		int i;
		String sql="";
		String[] temp;
		String tempsql;		
		if(qrc.getMbno()!=null&&!qrc.getMbno().trim().equals("")) 
		{
			if(qrc.getMbno().trim().matches("\\d{1,11}")) sql+="and device_number like '%"+qrc.getMbno().trim()+"%' ";
			else sql+="and mbno in ("+qrc.getMbno().trim()+") ";	
		}
		if(qrc.getGender()!=null&&!qrc.getGender().equals("")) sql+="and user_sex="+qrc.getGender()+" ";	
		if(qrc.getCompany()!=null&&!qrc.getCompany().equals("")) sql+="and user_unit in ("+qrc.getCompany()+") ";	
		if(qrc.getSeller()!=null&&!qrc.getSeller().equals("")) sql+="and device_seller in ("+qrc.getSeller()+") ";	
		if(qrc.getMbos()!=null&&!qrc.getMbos().equals(""))  sql+="and (device_os_version in ("+qrc.getMbos()+") or  left(device_os_version,1) in ("+qrc.getMbos()+")) ";
		if(qrc.getPhstatus()!=null&&!qrc.getPhstatus().equals("")) 
		{
			temp=qrc.getPhstatus().split(",");
			tempsql="";
			for(i=0;i<temp.length;i++)
			{
				if(temp[i].equals("0")) tempsql+="or IFNULL(modechage_task,'') not REGEXP '(^|,)"+qrc.getPtlid()+",'  ";
				if(temp[i].equals("1")) tempsql+="or IFNULL(modechage_task,'') REGEXP '(^|,)"+qrc.getPtlid()+",'  ";
			}
			sql+="and ("+tempsql.substring(2)+") ";
		}
		if(qrc.getProvider()!=null&&!qrc.getProvider().equals("")) 
		{
			temp=qrc.getProvider().split(",");
			tempsql="";
			for(i=0;i<temp.length;i++)
			{
				tempsql+="or mobile_type like '"+temp[i]+"%' ";
			}
			sql+="and ("+tempsql.substring(2)+") ";
		}
		if(qrc.getMbtype()!=null&&!qrc.getMbtype().equals(""))  sql+="and (device_type in ("+qrc.getMbtype()+") or  left(device_type,2) in ("+qrc.getMbtype()+")) ";
		if(qrc.getAge()!=null&&!qrc.getAge().equals("")) 
		{
			temp=qrc.getAge().split(",");
			tempsql="";
			for(i=0;i<temp.length;i++)
			{
				tempsql+="or ("+RangeAge(temp[i])+") ";
			}
			sql+="and ("+tempsql.substring(2)+") ";
		}
		if(qrc.getArea()!=null&&!qrc.getArea().equals(""))  sql+="and (mobile_area in ("+qrc.getArea()+") or  left(mobile_area,2) in ("+qrc.getArea()+")) ";
		if(sql.length()>0) return "where"+sql.substring(3);
		else return "";
	}


	private String RangeAge(String agerange) {
		String[] age=agerange.split("-");
		if(age[0].equals("null"))  return " Year(NOW())-Year(user_birth)<"+age[1]+" ";
		else if(age[1].equals("null")) return " Year(NOW())-Year(user_birth)>"+age[0]+" ";
		else return " Year(NOW())-Year(birth)>="+age[0]+" and "+" Year(NOW())-Year(user_birth)<="+age[1]+" ";
	}

	public List<ThemeLog> GetThemeLogList(int currentPage, int pageSize,ThemeLog tlg)
	{
		String startmonth="000000";
		String endmonth="999999";
		if(!tlg.getOpertime().equals("null*null")) 
		{
		    String[] temp=tlg.getOpertime().split("\\*");
		    if(!temp[0].equals("null")) startmonth=temp[0].substring(0, 4)+temp[0].substring(5,7);
		    if(!temp[1].equals("null")) endmonth=temp[1].substring(0, 4)+temp[1].substring(5,7);
		}
		return dao.GetThemeLogList(currentPage,pageSize,ThemeLogQuery(tlg),startmonth,endmonth);
	}

	public int GetThemeLogTotal(ThemeLog tlg) {
		String startmonth="000000";
		String endmonth="999999";
		if(!tlg.getOpertime().equals("null*null")) 
		{
			String[] temp=tlg.getOpertime().split("\\*");
			if(!temp[0].equals("null")) startmonth=temp[0].substring(0, 4)+temp[0].substring(5,7);
			if(!temp[1].equals("null")) endmonth=temp[1].substring(0, 4)+temp[1].substring(5,7);
		}
		return dao.GetThemeLogTotal(ThemeLogQuery(tlg),startmonth,endmonth);
	}

	public List<ErrorLog> GetErrorLogList(int currentPage, int pageSize,
			ErrorLog qrc) {
		return dao.GetErrorLogList(currentPage,pageSize,ErrorLogQuery(qrc));
	}

	public int GetErrorLogTotal(ErrorLog qrc) {
		return dao.GetErrorLogTotal(ErrorLogQuery(qrc));
	}
}
