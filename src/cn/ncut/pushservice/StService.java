package cn.ncut.pushservice;
import java.util.List;

import javax.xml.crypto.Data;

import cn.ncut.pushdao.StDao;
import cn.ncut.pushdomain.ComBox;
import cn.ncut.pushdomain.Employee;
import cn.ncut.pushdomain.PushAccepter;
import cn.ncut.pushdomain.PushContent;
import cn.ncut.pushdomain.StatsModel;

public class StService {
	
	StDao dao = new StDao();

	public List<ComBox> GetComNameList(String comno) {
		return dao.GetComNameList(comno);
	}

	public List<ComBox> GetDeptNameList(String cid) {
		if(cid==null||cid.equals("")) return null;
		else return dao.GetDeptNameList(cid);
	}

	public List<Employee> GetStuffList(int currentPage, int pageSize,
			String cid,String did) {
		return dao.GetStuffList(currentPage,pageSize,cid,did);
	}

	public int GetStuffTotal(String cid,String did) {
		String sql = "select count(*) from tab_employee ";
		if(!cid.equals("all")) sql+="where com_no="+cid;
		if(did!=null&&!did.equals("")) sql+=" and depart_no="+did;
		return dao.GetInt(sql);
	}    
	public int GetDeviceSum(String start,String end,String con){
		String sql = "select COUNT(*) from tab_deviceinfo where left(enter_time,10) between '"+start+"' and '"+end+"' "+con;
		return dao.GetInt(sql);
	}
	public int GetSlideCountSum(String start,String end,String con){
		String sql = "select sum(num) from view_slide_count where left(dat,10) between '"+start+"' and '"+end+"' "+con;
		return dao.GetInt(sql);
	}
	public int GetSlideScoreSum(String start,String end,String con){
		String sql = "select sum(score) from tab_wxscoretrade where left(date,10) between '"+start+"' and '"+end+"' "+con;
		return dao.GetInt(sql);
	}
	public int GetBeforeDeviceSum(String start,String con){
		String sql = "select COUNT(*) from tab_deviceinfo where left(enter_time,10) <'"+start+"' "+con;
		return dao.GetInt(sql);
	}
	public int GetBeforeSlideCountSum(String start,String con){
		String sql = "select sum(num) from view_slide_count where left(dat,10) <'"+start+"' "+con;
		return dao.GetInt(sql);
	}
	public int GetBeforeSlideScoreSum(String start,String con){
		String sql = "select sum(score) from tab_wxscoretrade where left(date,10) <'"+start+"' "+con;
		return dao.GetInt(sql);
	}
	public String GetDeviceNums(PushAccepter item){
		//String sql="select GROUP_CONCAT(device_number) as x from tab_deviceinfo where TRIM(device_number)<>''";
		String sql="select device_number from tab_deviceinfo where TRIM(device_number)<>''";
		if(item.getMbno()!=null&&!item.getMbno().equals(""))  sql+=" and device_number like '%"+item.getMbno()+"%'" ;
		if(item.getCurque()!=null&&!item.getCurque().equals(""))  sql+=" and user_sex="+item.getCurque()+" ";	
		if(item.getCompany()!=null&&!item.getCompany().equals("")) sql+=" and user_unit in ("+item.getCompany()+") ";	
		if(item.getSeller()!=null&&!item.getSeller().equals(""))  sql+=" and device_seller in ("+item.getSeller()+") ";
		if(item.getArea()!=null&&!item.getArea().equals(""))  sql+=" and (mobile_area in ("+item.getArea()+") or  left(mobile_area,2) in ("+item.getArea()+")) ";
		if(item.getMbtype()!=null&&!item.getMbtype().equals(""))  sql+=" and (device_type in ("+item.getMbtype()+") or  left(device_type,2) in ("+item.getMbtype()+")) ";
		if(item.getProvider()!=null&&!item.getProvider().equals(""))  sql+=" and left(mobile_type,1) in ("+item.getProvider()+") ";
		if(item.getBirth()!=null&&!item.getBirth().equals("")) 
		{
			String[] temp=item.getBirth().split(",");
			String tempsql="";
			for(int i=0;i<temp.length;i++)
			{
				tempsql+="or ("+RangeAge(temp[i])+") ";
			}
			sql+=" and ("+tempsql.substring(2)+") ";
		}	
		return sql;
		//return dao.GetString(sql);
	}
	private String RangeAge(String agerange) {
		String[] age=agerange.split("-");
		if(age[0].equals("null"))  return " Year(NOW())-user_birth<"+age[1]+" ";
		else if(age[1].equals("null")) return " Year(NOW())-user_birth>"+age[0]+" ";
		else return " Year(NOW())-user_birth>="+age[0]+" and "+" Year(NOW())-user_birth<="+age[1]+" ";
	}

	/********************/
	public  List<StatsModel> GetStatsDayData(String start,String end,String con) {
		String sql="select temp1.daydate as name,ifnull(temp2.daynum,0) as num from ("
			+DateService.daysSql(start,end)
			+") temp1 LEFT JOIN ("
			+"select left(enter_time,10) as daydate, count(*) as  daynum from tab_deviceinfo where left(enter_time,10) BETWEEN '"+start+"' AND '"+end+"' "+con+" group by left(enter_time,10)"
			+") temp2 on temp1.daydate= temp2.daydate";
		return dao.GetStatsData(sql);
	}
	
	public  List<StatsModel> GetStatsMonthData(String start,String end,String con) {
		String sql="select temp1.monthdate as name,ifnull(temp2.monthnum,0) as num from ("
			+DateService.monthsSql(start,end)
			+") temp1 LEFT JOIN ("
			+"select left(enter_time,7) as monthdate, count(*) as  monthnum from tab_deviceinfo where left(enter_time,10) BETWEEN '"+start+"' AND '"+end+"' "+con+" group by left(enter_time,7)"
			+") temp2 on temp1.monthdate= temp2.monthdate";
		return dao.GetStatsData(sql);
	}
		
	
	public  List<StatsModel> GetStatsYearData(String start,String end,String con) {
		String sql="select temp1.yeardate as name,ifnull(temp2.yearnum,0) as num from ("
			+DateService.yearsSql(start,end)
			+") temp1 LEFT JOIN ("
			+"select left(enter_time,4) as yeardate, count(*) as  yearnum from tab_deviceinfo where left(enter_time,10) BETWEEN '"+start+"' AND '"+end+"' "+con+" group by left(enter_time,4)"
			+") temp2 on temp1.yeardate= temp2.yeardate";
		return dao.GetStatsData(sql);
	}
	
	
	/******slideCount*********/
	public  List<StatsModel> GetStatsDaySlideCountData(String start,String end,String con) {
		String sql="select temp1.daydate as name,ifnull(temp2.daynum,0) as num from ("
			+DateService.daysSql(start,end)
			+") temp1 LEFT JOIN ("
			+"select left(dat,10) as daydate, sum(num) as  daynum from view_slide_count  where left(dat,10) BETWEEN '"+start+"' AND '"+end+"' "+con+" group by left(dat,10)"
			+") temp2 on temp1.daydate=temp2.daydate";
		return dao.GetStatsData(sql);
		//String sql=DateService.daysSql(start,end);
		//return dao.GetStatsDaySlideCountData(sql,con);
	}
	
	public  List<StatsModel> GetStatsMonthSlideCountData(String start,String end,String con) {
		String sql="select temp1.monthdate as name,ifnull(temp2.monthnum,0) as num from ("
			+DateService.monthsSql(start,end)
			+") temp1 LEFT JOIN ("
			+"select left(dat,7) as monthdate,sum(num) as  monthnum from view_slide_count  where left(dat,10) BETWEEN '"+start+"' AND '"+end+"' "+con+" group by left(dat,7)"
			+") temp2 on temp1.monthdate=temp2.monthdate";
		/*String sql="select temp1.monthdate as name,ifnull(temp2.monthnum,0) as num from ("
			+DateService.monthsSql(start,end)
			+") temp1 LEFT JOIN ("
			+"select monthdate,sum(monthnum) as monthnum from ( select CONCAT(left(dateym,4),'-',right(dateym,2)) as monthdate,device_number,(day01+day02+day03+day04+day05+day06+day07+day08+day09+day10+day11+day12+day13+day14+day15+day16+day17+day18+day19+day20+day21+day22+day23+day21+day21+day21+day24+day25+day26+day27+day28+day29+day30+day31) as monthnum from tab_slide_count where dateym>='"+start.substring(0,4)+"' and dateym<='"+end.substring(5,7)+"' "+con+") tab GROUP BY monthdate"
			+") temp2 on temp1.monthdate= temp2.monthdate";*/
		return dao.GetStatsData(sql);
	}
		
	
	public  List<StatsModel> GetStatsYearSlideCountData(String start,String end,String con) {
		String sql="select temp1.yeardate as name,ifnull(temp2.yearnum,0) as num from ("
			+DateService.yearsSql(start,end)
			+") temp1 LEFT JOIN ("
			+"select left(dat,4) as yeardate, sum(num) as  yearnum from view_slide_count where left(dat,10) BETWEEN '"+start+"' AND '"+end+"' "+con+" group by left(dat,4)"
			+") temp2 on temp1.yeardate=temp2.yeardate";
		/*String sql="select temp1.yeardate as name,ifnull(temp2.yearnum,0) as num from ("
			+DateService.yearsSql(start,end)
			+") temp1 LEFT JOIN ("
			+"select left(monthdate,4) as yeardate,sum(monthnum) as monthnum from ( select CONCAT(left(dateym,4),'-',right(dateym,2)) as monthdate,device_number,(day01+day02+day03+day04+day05+day06+day07+day08+day09+day10+day11+day12+day13+day14+day15+day16+day17+day18+day19+day20+day21+day22+day23+day21+day21+day21+day24+day25+day26+day27+day28+day29+day30+day31) as monthnum from tab_slide_count where dateym>='"+start.substring(0,4)+"' and dateym<='"+end.substring(5,7)+"' "+con+") tab GROUP BY left(monthdate,4)"
			+") temp2 on temp1.yeardate= temp2.yeardate";*/
		return dao.GetStatsData(sql);
	}

	/******slideScore*********/
	public  List<StatsModel> GetStatsDaySlideScoreData(String start,String end,String con) {
		String sql="select temp1.daydate as name,ifnull(temp2.daynum,0) as num from ("
			+DateService.daysSql(start,end)
			+") temp1 LEFT JOIN ("
			+"select left(date,10) as daydate, sum(score) as  daynum from tab_wxscoretrade  where left(date,10) BETWEEN '"+start+"' AND '"+end+"' "+con+" group by left(date,10)"
			+") temp2 on temp1.daydate=temp2.daydate";
		return dao.GetStatsData(sql);
	}
	
	public  List<StatsModel> GetStatsMonthSlideScoreData(String start,String end,String con) {
		String sql="select temp1.monthdate as name,ifnull(temp2.monthnum,0) as num from ("
			+DateService.monthsSql(start,end)
			+") temp1 LEFT JOIN ("
			+"select left(date,7) as monthdate,sum(score) as  monthnum from tab_wxscoretrade  where left(date,10) BETWEEN '"+start+"' AND '"+end+"' "+con+" group by left(date,7)"
			+") temp2 on temp1.monthdate=temp2.monthdate";
		return dao.GetStatsData(sql);
	}
		
	
	public  List<StatsModel> GetStatsYearSlideScoreData(String start,String end,String con) {
		String sql="select temp1.yeardate as name,ifnull(temp2.yearnum,0) as num from ("
			+DateService.yearsSql(start,end)
			+") temp1 LEFT JOIN ("
			+"select left(date,4) as yeardate, sum(score) as  yearnum from tab_wxscoretrade where left(date,10) BETWEEN '"+start+"' AND '"+end+"' "+con+" group by left(date,4)"
			+") temp2 on temp1.yeardate=temp2.yeardate";
		return dao.GetStatsData(sql);
	}
	
}
