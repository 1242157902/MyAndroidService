package cn.ncut.autotask.utils;

/**
 * @author wzq
 *
 *version 1.0 2014-12-22 下午1:10:59
 */
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    
    public static void main(String[] args) {
       
        System.out.println(getSpecifiedDayBefore());
        System.out.println(getSpecifiedDayAfter());
    }
    
    /**
     * 获得指定日期的前一天
     * 
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayBefore() {
    	 Date date = new Date();
         
        Calendar c = Calendar.getInstance();
        
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
                .getTime());
        return dayBefore;
    }

    /**
     * 获得指定日期的后一天
     * 
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayAfter() {
    	
    	 Date date = new Date();
         Calendar c = Calendar.getInstance();
      
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
                .format(c.getTime());
        return dayAfter;
    }
    /*
     * 获得当前日期的1年之后的日期
     * */
    public static String getSpecifiedDay() {
    	Date date = new Date();
    
   		Calendar c = Calendar.getInstance();
    
    	c.setTime(date);
    	int day = c.get(Calendar.DATE);
    	c.set(Calendar.DATE, day - 1);
        c.set(Calendar.YEAR,c.get(Calendar.YEAR)+1);
        String current_day = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return current_day;
   }
}
