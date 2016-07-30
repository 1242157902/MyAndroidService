package cn.ncut.deviceservice;
import java.util.List;
import cn.ncut.devicedao.MobileDao;
import cn.ncut.devicedomain.MobileModel;
import cn.ncut.devicedomain.ScorePie;
import cn.ncut.devicedomain.SlideModel;

public class MobileService {

	MobileDao mbdao = new MobileDao();
	
	
	//返回设备信息
	
	public List<MobileModel> GetMobileList(int currentPage, int pageSize,
			MobileModel qrc,String order) {
		 return mbdao.GetMobileList(currentPage, pageSize,MobileQuery(qrc),order);		
	}
	
	//返回设备总数
	public int GetMobileTotal(MobileModel qrc) {
		
		 return mbdao.GetMobileTotal(MobileQuery(qrc));
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
		
		if (qrc.getImei()!=null&&!"".equals(qrc.getImei().trim())) {
			
			sql+="and device_imei like '%"+qrc.getImei().trim()+"%' ";
		}
		
		if(qrc.getGender()!=null&&!"".equals(qrc.getGender())) 
			
			sql+="and user_sex="+qrc.getGender()+" ";	
		
		if(qrc.getCompany()!=null&&!"".equals(qrc.getCompany()))
			
			sql+="and user_unit = '"+qrc.getCompany()+"' ";	
		

       if(qrc.getSeller()!=null&&!"".equals(qrc.getSeller()))
			
			sql+="and device_seller = '"+qrc.getSeller()+"' ";	
		
		if(qrc.getMbos()!=null&&!"".equals(qrc.getMbos())) 
			
			sql+="and (device_os_version in ("+qrc.getMbos()+") or  left(device_os_version,1) in ("+qrc.getMbos()+")) ";
		
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
		
		if(!qrc.getRegtime().equals("null*null")) 
			
		{
			temp=qrc.getRegtime().split("\\*");
			
			
			if(!temp[0].equals("null")) {
			    sql+="and enter_time>='"+temp[0]+"' ";
				
			}
			
			if(!temp[1].equals("null")){
				
			  sql+="and enter_time<='"+temp[1]+"' ";
				
		     }
		}
		
		//2016-4-25添加
		if(qrc.getUpdate_key()==0||qrc.getUpdate_key()==1) 
		{
			sql+="and update_key="+qrc.getUpdate_key()+"  ";
		}
			
		
		
		if(qrc.getArea()!=null&&!"".equals(qrc.getArea()))
			
			sql+="and (mobile_area in ("+qrc.getArea()+") or  left(mobile_area,2) in ("+qrc.getArea()+")) ";
		
	
		
		if(sql.length()>0)
			
			return "where "+sql.substring(3);
		
		
		else 
			
			return "";
	}
	
	
	
	public List<SlideModel> GetSlideList(int offset, int pageSize,
			SlideModel qrc,String tablename,String order) {
		
		return mbdao.GetSlideList(offset, pageSize,SlideQuery(qrc), tablename,order);	
	}
	public int GetSlideTotal(SlideModel qrc ,String tablename) {
		
		return mbdao.GetSlideTotal(SlideQuery(qrc),tablename);
	}
	
	//拼接sql
	private String SlideQuery(SlideModel qrc) {
		
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
		
		if (qrc.getImei()!=null&&!"".equals(qrc.getImei().trim())) {
			
			sql+="and device_imei like '%"+qrc.getImei().trim()+"%' ";
		}
		
		  MobileDao mobileDao=new MobileDao();
		      List<String> imeis= mobileDao.getImeisbyunitno(qrc.getCompany());
		      
		      StringBuilder sbBuilder=new StringBuilder();
		      sbBuilder.append("(");
		      
		      for (String str : imeis) {
				sbBuilder.append("'");
				sbBuilder.append(str);
				sbBuilder.append("'");
				sbBuilder.append(",");
		    	  
			}
		      if (sbBuilder.length()>1) {
		    	  sbBuilder.deleteCharAt(sbBuilder.lastIndexOf(","));
			}
		     
		      
		      sbBuilder.append(")");
		      String imeisstr=sbBuilder.toString();
		
		
		/*if(qrc.getCompany()!=null&&!"".equals(qrc.getCompany()))
			
			sql+="and b.user_unit in ("+qrc.getCompany()+") ";	
		
		if(qrc.getSeller()!=null&&!"".equals(qrc.getSeller())) 
			
			sql+="and b.device_seller in ("+qrc.getSeller()+") ";	
		
		
		if(qrc.getMbos()!=null&&!"".equals(qrc.getMbos())) 
			
			sql+="and (b.device_os_version in ("+qrc.getMbos()+") or  left(b.device_os_version,1) in ("+qrc.getMbos()+")) ";
		
		if(qrc.getProvider()!=null&&!"".equals(qrc.getProvider())) 
		{
			temp=qrc.getProvider().split(",");
			
			tempsql="";
			
			for(i=0;i<temp.length;i++)
			{
				tempsql+="or b.mobile_type like '"+temp[i]+"%' ";
			}
			
			sql+="and ("+tempsql.substring(2)+") ";
			
		}
		
		if(qrc.getMbtype()!=null&&!"".equals(qrc.getMbtype())) 
			
			sql+="and (b.device_type in ("+qrc.getMbtype()+") or  left(b.device_type,2) in ("+qrc.getMbtype()+")) ";
		
		*/
		if(!qrc.getRegtime().equals("null*null")) 
			
		{
			temp=qrc.getRegtime().split("\\*");
			
			
			if(!temp[0].equals("null")) {
			    sql+="and slide_time>='"+temp[0]+"' ";
				
			}
			
			if(!temp[1].equals("null")){
				
			  sql+="and slide_time<='"+temp[1]+"' ";
				
		     }
		}
		
		/*
		if(qrc.getArea()!=null&&!"".equals(qrc.getArea()))
			
			sql+="and (b.mobile_area in ("+qrc.getArea()+") or  left(b.mobile_area,2) in ("+qrc.getArea()+")) ";*/
		
		if(sql.length()>0){
			
			
			if (qrc.getCompany().equals("100000")) {
				return "  where"+sql.substring(3);
			}
			return "  where"+sql.substring(3)+" and device_imei in "+imeisstr;
		
		}
		else {
			
			if (qrc.getCompany().equals("100000")) {
				return "";
			}
			
			return "  where device_imei in "+imeisstr;
		
		}
		
	}
	private String RangeAge(String agerange) {
		String[] age=agerange.split("-");
		if(age[0].equals("null"))  return " Year(NOW())-Year(user_birth)<"+age[1]+" ";
		else if(age[1].equals("null")) return " Year(NOW())-Year(user_birth)>"+age[0]+" ";
		else return " Year(NOW())-Year(user_birth)>"+age[0]+" and "+" Year(NOW())-Year(user_birth)<"+age[1]+" ";
	}
	
	/**
	 * @param id
	 * @param unit
	 * @param company
	 */
	public void update(String id, String unit, String company,String theme,String themestatus,String userid) {
  
		mbdao.update(id,unit,company,theme,themestatus,userid);
	}

	/**
	 * @param begintime
	 * @param endtime
	 * @return 
	 */
	public ScorePie getscorepie(String begintime, String endtime) {
		
	   return	mbdao.getscorepie(begintime,endtime);
		
	}

}
