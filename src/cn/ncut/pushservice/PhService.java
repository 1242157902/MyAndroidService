package cn.ncut.pushservice;

import java.util.List;

import cn.ncut.deviceservice.MobileService;
import cn.ncut.pushdao.JsonDao;
import cn.ncut.pushdao.PhDao;
import cn.ncut.pushdomain.ComDefQue;
import cn.ncut.pushdomain.PushAccepter;
import cn.ncut.pushdomain.PushItem;
import cn.ncut.pushdomain.PushQueTitle;
import cn.ncut.pushdomain.QueryRecCon;

public class PhService {	
	PhDao dao = new PhDao();
	JsonDao jsdao = new JsonDao();
	JsonService jservice=new JsonService();
	MobileService mbservice=new MobileService();
	public List<PushItem> GetPushList(int currentPage, int pageSize, String operId) {
		return dao.GetPushList(currentPage,pageSize,operId);
	}

	public int GetPushTotal(String operId) {
		return dao.GetPushTotal(operId);
	}

	public List<PushQueTitle> GetPushQueTitleList(String operId) {
		return dao.GetPushQueTitleList(operId);
	}

	public void AddPushItem(PushItem phitem) {
		dao.AddPushItem(phitem);
	}

	public void UpdatePushItem(PushItem phitem) {
		dao.UpdatePushItem(phitem);
		dao.UpdateRecStatusPh(phitem.getPid());
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
    public void ClientRecInfo(String imei,String sequence,String rec_time){
    	dao.ClientRecInfo(imei,sequence,rec_time);
    }
	public String PushDetailQRC(QueryRecCon qrc)
	{		
		int i;
		String sql="";
		String[] temp;
		String tempsql;		
		if(qrc.getMbno()!=null&&!qrc.getMbno().trim().equals("")) 
		{
			if(qrc.getMbno().trim().matches("\\d{1,11}")) sql+="and mbno like '%"+qrc.getMbno().trim()+"%' ";
			else sql+="and mbno in ("+qrc.getMbno().trim()+") ";	
		}
		if(qrc.getGender()!=null&&!qrc.getGender().equals("")) sql+="and gender="+qrc.getGender()+" ";	
		if(qrc.getCompany()!=null&&!qrc.getCompany().equals("")) sql+="and company in ("+qrc.getCompany()+") ";	
		if(qrc.getSeller()!=null&&!qrc.getSeller().equals("")) sql+="and (seller in ("+qrc.getSeller()+") or seller='' or seller is null) ";	
		if(qrc.getMbos()!=null&&!qrc.getMbos().equals(""))  sql+="and (mbos in ("+qrc.getMbos()+") or  left(mbos,1) in ("+qrc.getMbos()+")) ";
		if(qrc.getPhstatus()!=null&&!qrc.getPhstatus().equals("")) 
		{
			temp=qrc.getPhstatus().split(",");
			tempsql="";
			for(i=0;i<temp.length;i++)
			{
				if(temp[i].equals("0")) tempsql+="or IFNULL(allque,'') not REGEXP '(^|,)"+qrc.getPtlid()+",'  ";
				if(temp[i].equals("1")) tempsql+="or IFNULL(allque,'') REGEXP '(^|,)"+qrc.getPtlid()+",'  ";
			}
			sql+="and ("+tempsql.substring(2)+") ";
		}
		if(qrc.getRecstatus()!=null&&!qrc.getRecstatus().equals("")) 
		{
			temp=qrc.getRecstatus().split(",");
			tempsql="";
			for(i=0;i<temp.length;i++)
			{
				if(temp[i].equals("0")) tempsql+="or IFNULL(recstatus,'')<>'"+qrc.getPtlid()+"&1' ";
				if(temp[i].equals("1")) tempsql+="or IFNULL(recstatus,'')='"+qrc.getPtlid()+"&1' ";			
			}
			sql+="and ("+tempsql.substring(2)+") ";
		}
		if(qrc.getProvider()!=null&&!qrc.getProvider().equals("")) 
		{
			temp=qrc.getProvider().split(",");
			tempsql="";
			for(i=0;i<temp.length;i++)
			{
				tempsql+="or provider like '"+temp[i]+"%' ";
			}
			sql+="and ("+tempsql.substring(2)+") ";
		}
		if(qrc.getMbtype()!=null&&!qrc.getMbtype().equals(""))  sql+="and (mbtype in ("+qrc.getMbtype()+") or  left(mbtype,2) in ("+qrc.getMbtype()+")) ";
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
		if(qrc.getArea()!=null&&!qrc.getArea().equals(""))  sql+="and (area in ("+qrc.getArea()+") or  left(area,2) in ("+qrc.getArea()+")) ";
		if(qrc.getPosition()!=null&&!qrc.getPosition().equals("")) sql+="and position in ("+qrc.getPosition()+") or SUBSTRING_INDEX(position,'_',1) in ("+qrc.getPosition()+") ";
		if(sql.length()>0) return "where"+sql.substring(3);
		else return "";
	}

	private String RangeAge(String agerange) {
		String[] age=agerange.split("-");
		if(age[0].equals("null"))  return " Year(NOW())-Year(birth)<"+age[1]+" ";
		else if(age[1].equals("null")) return " Year(NOW())-Year(birth)>"+age[0]+" ";
		else return " Year(NOW())-Year(birth)>="+age[0]+" and "+" Year(NOW())-Year(birth)<="+age[1]+" ";
	}

	public void DeletePushItem(int pid) {
		dao.DeletePushItemInDetail(pid);	
		dao.DeletePushItemInList(pid);	
	}

    public String[] GetPushString(String imei)
    {
      String[] result=new String[3];
      String isholiday="false";
      String finalstr="";
      String initstr=dao.GetPushString(imei);
      String[] temp=initstr.split("\\*");
      if(temp.length>3)
      {
          String pics_name="";
    	  String pics_time="";
          String display_order="";
          String pics_url="";
          String pics_weight="";
          String pics_rgb="";
    	  for(int i=3;i<temp.length;i++)
    	  {
    		  String[] atom=temp[i].split(",");
    		  pics_time+=","+atom[1];
    		  display_order+=",\""+atom[0]+"\"";
    		  if(pics_name.indexOf(atom[0])==-1) {pics_name+=",\""+atom[0]+"\"";pics_url+=",\""+atom[3]+"\"";pics_weight+=","+atom[4];pics_rgb+=",\"#"+atom[5]+"\"";}
    	  }
    	 // finalstr="pics_name:["+pics_name.substring(1)+"],pics_url:["+pics_url.substring(1)+"],pics_weight:["+pics_weight.substring(1)+"],pics_rgb:["+pics_rgb.substring(1)+"],pics_time:["+pics_time.substring(1)+"],display_order:["+display_order.substring(1)+"]";
    	  finalstr="sequence:\""+temp[0]+"\",pics_name:["+pics_name.substring(1)+"]," 
    	           + dao.GetIconStr(pics_name.substring(1))
    	  	       + ",pics_weight:["+pics_weight.substring(1)+"],pics_rgb:["+pics_rgb.substring(1)+"],pics_time:["+pics_time.substring(1)+"],display_order:["+display_order.substring(1)+"]";
      	
    	  /* pic_icons:[ [1.png,2.png],[ ],[2.png,5.png]]*/
    	  if(temp[0].substring(0,1).equals("c")) {finalstr+=",begin_time:\""+temp[1]+"\",end_time:\""+temp[2]+"\"";isholiday="true";}
      }
      result[0]=finalstr;
      result[1]=isholiday;
      result[2]=dao.GetTotalScore(imei);   
      return  result; 
    }
    
	public void ResetCurQue(String imei) {
		 dao.ResetCurQue(imei);
	}
	
	public String getCompany(String comno) {
		return dao.getCompany(comno);
	}
	

	public List<ComDefQue> GetComDefQueList(String comno) {
		return dao.GetComDefQueList(comno);
	}

	public void InsertErrorLog(String errid, String errtxt, String errmethod,
			String phoneimei, String json, String picstring) {
		int id=dao.GetErrorId(errid);
		if(id==0) dao.InsertError(errid);
		if(id>=0) dao.InsertErrorLog(id,errtxt,errmethod,phoneimei,json,picstring);		
	}
	
}
