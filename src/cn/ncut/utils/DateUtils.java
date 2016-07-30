package cn.ncut.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /** 
     * @param args 
     * @throws ParseException  
     */  
    public static void main(String[] args) throws ParseException {  
        // TODO Auto-generated method stub  
      /*  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date d1=sdf.parse("2012-09-08 10:10:10");  
        Date d2=sdf.parse("2012-09-15 00:00:00");  
        System.out.println(daysBetween(d1,d2));  */
  
        System.out.println(getAge("1988-06-17"));  
    }  
      
    /**  
     * 计算两个日期格式类型的日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }    
      
    /**
     * 
     * @return:       int 
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     * <p>Description:计算两个字符串的日期格式之间相差的天数 <p>
     * @date:          2016年3月8日下午4:46:26
     * @author         ysl
     */
    public static int daysBetween(String smdate,String bdate) throws ParseException{  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(smdate));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(bdate));    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));     
    }  
    
   /**
    * 
    * @return:       Date 
    * @param strdate
    * @return
    * <p>Description: 将String型的日期 转换为 Date格式类型的<p>
    * @date:          2016年3月8日下午4:45:54
    * @author         ysl
    */
    public static  Date strToDate(String strdate){

	    DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
	    Date date = null;   
	    try {
	    	date = format.parse(strdate);
	    } catch (ParseException e) {
	    	e.printStackTrace();
	    }
	    	//System.out.println("date:"+date);
	    return date;
    }
    /**
     * 
     * @return:       String 
     * @param date
     * @return
     * <p>Description:将日期类型的格式日期转换为字符串类型 <p>
     * @date:          2016年3月8日下午4:48:36
     * @author         ysl
     */
    public static String dateToString(Date date)
    {
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
    	String str = format.format(date);   
    	//System.out.println("str:"+str); 
    	return str;
    }
	/**
	 * 
	 * @return:       Date 
	 * @param date
	 * @return
	 * <p>Description: 返回日期增加一天以后得日期<p>
	 * @date:          2016年3月8日下午5:08:08
	 * @author         ysl
	 */
	public static Date getNextDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, +1);//+1今天的时间加一天
		date =   calendar.getTime();
		return date;
	}
	
	/**
	 * 
	 * @return:       String 
	 * @param date
	 * @param d
	 * @return
	 * <p>Description: 在日期的基础上加上多少小时<p>
	 * @date:          2016年3月20日下午3:29:43
	 * @author         ysl
	 */
	public static String  getTimeOfDay(Date date,int d) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, d);//+1今天的时间加一天
		date =   calendar.getTime();
		return sdf.format(date);
	}
	
	/**
	 * 
	 * @return:       String 
	 * @return
	 * <p>Description:获得当天日期 <p>
	 * @date:          2016年3月20日下午2:45:55
	 * @author         ysl
	 */
	public static String getCurrentDate()
	{
		Date date = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		return sdf.format(date);
	}
	/**
	 * 
	 * @return:       int 
	 * @param strDate
	 * @return
	 * <p>Description: 获取年龄大小<p>
	 * @date:          2016年4月7日下午5:23:29
	 * @author         ysl
	 */
	public static int getAge(String strDate)
	{
		int age = 0;
		if (strDate!=null) 
		{
			String currentDate = getCurrentDate();
			int currentAge = new Integer(currentDate.substring(0, 4));
			int oldAge = new Integer(strDate.substring(0, 4));
			age = currentAge - oldAge;
			System.out.println(age); 
		} 
		return age;
		
	}
}
