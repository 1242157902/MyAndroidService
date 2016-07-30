package cn.ncut.pushservice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class DateService {
	public static int daysBetween(String start,String end) {		 
		  try {
			  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			  Calendar cal = Calendar.getInstance();   
			  cal.setTime(sdf.parse(start));		
			  long time1 = cal.getTimeInMillis();                 
			  cal.setTime(sdf.parse(end));    
			  long time2 = cal.getTimeInMillis();         
			  long between_days=(time2-time1)/(1000*3600*24);  
			  return Integer.parseInt(String.valueOf(between_days));  
		  } 
		  catch (ParseException e) {
				e.printStackTrace();
				return 0;
		  }    
	}
	
	public static int monthsBetween(String start,String end) {		 
		  try {
			  return (Integer.parseInt(end.substring(0,4))-Integer.parseInt(start.substring(0,4)))*12+Integer.parseInt(end.substring(5,7))-Integer.parseInt(start.substring(5,7));
		  } 
		  catch (Exception e) {
				e.printStackTrace();
				return 0;
		  }    
	}
	public static int yearsBetween(String start,String end) {		 
		  try {
			  return Integer.parseInt(end.substring(0,4))-Integer.parseInt(start.substring(0,4));
		  } 
		  catch (Exception e) {
				e.printStackTrace();
				return 0;
		  }    
	}
	public static String daysSql(String start,String end)
	{
		String tempsql="select 0 as dayunit";
		for(int i=1;i<=daysBetween(start, end);i++)   tempsql+=" union all select "+i+" ";
		return "select date_add('"+start+"',interval dayunit day) as daydate from ("+tempsql+") daytable";
	}
	
	public static String monthsSql(String start,String end)
	{
		String tempsql="select 0 as monthunit";
		for(int i=1;i<=monthsBetween(start, end);i++)   tempsql+=" union all select "+i+" ";
		return "select left(date_add('"+start+"',interval monthunit month),7) as monthdate from ("+tempsql+") monthtable";
	}
	public static String yearsSql(String start,String end)
	{
		String tempsql="select 0 as yearunit";
		for(int i=1;i<=yearsBetween(start, end);i++)   tempsql+=" union all select "+i+" ";
		return "select left(date_add('"+start+"',interval yearunit year),4) as yeardate from ("+tempsql+") yeartable";
	}
	
}