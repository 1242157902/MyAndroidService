package cn.ncut.algorim;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cn.ncut.utils.JdbcUtils02;
import cn.ncut.wxdomain.Parameters;

public class FileReader
{
	public static void main(String[] args) 
	{
		/* List<List<String>> list = getData("2015-08-01" );
		 //List<List<String>> list = getDatabase();
		 for (List<String> list2 : list) 
		 {
			for (String classify : list2)
			{
				System.out.println("------------"+classify);
			}
		}*/
		// List<LinkedList<String>> list = getData1("2015-08-01" );
		Parameters params = new Parameters();
		Map<String,Object> m1= new HashMap<String, Object>();
		 params.setPhone("13001962817");
		 params.setDate("2015-07-10");
			m1.put("date", "date");
			m1.put("vLocationTime", "2015-07-10");
			m1.put("address", "address");
			m1.put("phone_num", "phone_num");
			m1.put("tableName","tab_day_locations");
			m1.put("value", "13001962817");
		 params.setMap(m1);
		 //List<LinkedList<String>> list = getData2(params);
		 //List<List<String>> list = getDatabase();
	/*	 for (LinkedList<String> list2 : list) 
		 {
			for (String classify : list2)
			{
				System.out.println("------------"+classify);
			}
		}*/
	/*	 
		 Myfptree2 mf = new Myfptree2();
		 Map<String,Object> map = mf.getFrequentResult(params);
		 insertData1(map,params);*/
	}
	/**
	 * 
	 * @return:       void ：空返回值
	 * @param classify	:标签类别
	 * @param timeString	：时间例如：2015-08-02
	 * <p>Description: 将挖掘出来的频繁项，存储到数据库中<p>
	 * @date:          2016年3月9日下午10:13:50
	 * @author         ysl
	 */
	public static void insertData(String  classify,String timeString,double min_support  )
	{
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null; 
		sql = "insert into tab_frequent_item(classify ,date,min_support) values(?,?,?)";
		try {
			conn = new JdbcUtils02().getConnection();
			st = conn.prepareStatement(sql);
			st.setString(1, classify);
			st.setString(2, timeString);
			st.setDouble(3, min_support);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtils02.release(conn, st, rs);
		}
	}
	 
	/**
	 * 
	 * @return:       void 
	 * @param map
	 * <p>
	 * Description: 
	 * 			将结果挖掘到的结果插入到数据库中
	 * <p>
	 * @date:          2016年3月18日下午10:32:02
	 * @author         ysl
	 */
	public static void insertData1(Map<String ,Object> map,Parameters params )
	{ 
		String phone_num = null;
		String date = null;
		int num = 0;
		String locations = null;
		 Set<Entry<String,Object>> sets= map.entrySet();
		 Iterator iterator = sets.iterator();
		
		
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null; 
		if(params!=null)
		{
			 phone_num = params.getPhone();
			 date = params.getDate();
			 date = (String) date.substring(0, 7);
		}
		
		try {
			 while(iterator.hasNext())
			  {
				 sql = "insert into tab_frequent_locations(locations ,date,phone_num,num) values(?,?,?,?)";
			    	Map.Entry entry = (Entry) iterator.next();
			    	System.out.println(entry.getKey()+" ###########"+entry.getValue() );
			    	locations = (String) entry.getKey();
			    	num = (Integer) entry.getValue();
			    	conn = new JdbcUtils02().getConnection();
			    	st = conn.prepareStatement(sql);
			    	st.setString(1, locations);
					st.setString(2, date);
					st.setString(3, phone_num);
					st.setInt(4, num);
			    	st.executeUpdate();
			  }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtils02.release(conn, st, rs);
		}
	}
	/**
	 * 
	 * @return:       List<List<String>> 
	 * @param timeString
	 * @return
	 * <p>Description: 获取数据库中的数据<p>
	 * @date:          2016年3月9日下午7:06:53
	 * @author         ysl
	 */
	public static List<List<String>> getData(String timeString )
	{
		 List<List<String>> db = new ArrayList<List<String>>() ;
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null; 
		sql = "select  classifys  from tab_day_classifys  where date ='"+timeString+"'";
		try {
			conn = new JdbcUtils02().getConnection();
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next())
			{
				String classifysResult = rs.getString("classifys");
				//System.out.println("classifys--------"+classifysResult);
				List<String> strList =null;
				if(classifysResult!=null)
				{
					strList = new ArrayList<String>();	
					String[] classifys = classifysResult.split(",");
					for (int i = 0; i < classifys.length; i++) 
					{
						strList.add(classifys[i]);
					}
				}
				 db.add(strList);
			}
		} catch (SQLException e) {
			System.out.println("出现异常情况，请重新检查代码！");
			e.printStackTrace();
		}finally{
			JdbcUtils02.release(conn, st, rs);
		}
		return db;
	}
	/**
	 * 这个也是读取数据库中的数据，只不过是用于Fp-Growth算法
	 * @param timeString
	 * @return
	 */
	public static LinkedList<LinkedList<String>> getData1(String timeString )
	{
		LinkedList<LinkedList<String>> records=new LinkedList<LinkedList<String>>();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null; 
		sql = "select  classifys  from tab_day_classifys  where date ='"+timeString+"'";
		try {
			conn = new JdbcUtils02().getConnection();
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next())
			{
				String classifysResult = rs.getString("classifys");
				//System.out.println("classifys--------"+classifysResult);
				LinkedList<String> litm =null;
				if(classifysResult!=null)
				{
					litm=new LinkedList<String>();	
					String[] classifys = classifysResult.split(",");
					for (int i = 0; i < classifys.length; i++) 
					{
						litm.add(classifys[i]);
					}
				}
				records.add(litm);
			}
		} catch (SQLException e) {
			System.out.println("出现异常情况，请重新检查代码！");
			e.printStackTrace();
		}finally{
			JdbcUtils02.release(conn, st, rs);
		}
		return records;
	}
	/**
	 * 
	 * @return:       LinkedList<LinkedList<String>> 
	 * @param params
	 * @return
	 * <p>
	 * Description:
	 * 				读取数据库中的数据
	 *  <p>
	 * @date:          2016年3月18日下午9:17:04
	 * @author         ysl
	 */
	public static LinkedList<LinkedList<String>> getData2(Parameters params)
	{
		LinkedList<LinkedList<String>> records=new LinkedList<LinkedList<String>>();
		Map<String,Object> mList = null;
		String address = null;
		String phone_num = null;
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null; 
		if(params!=null)
		{
			 mList = (Map<String, Object>) params.getMap();
		}
		if(mList!=null)
		{
			
			sql = "select  "+mList.get("address")+"  from " +mList.get("tableName")+"  where "+mList.get("phone_num")+" ='"+mList.get("value")+"'"+
						" and "+mList.get("date")+" like '"+((String)mList.get("vLocationTime")).substring(0, 8)+"%'";
		}
		System.out.println(sql);
		try {
			conn = new JdbcUtils02().getConnection();
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next())
			{
				String classifysResult = rs.getString("address");
				//System.out.println("classifys--------"+classifysResult);
				LinkedList<String> litm =null;
				if(classifysResult!=null)
				{
					litm=new LinkedList<String>();	
					String[] classifys = classifysResult.split(",");
					for (int i = 0; i < classifys.length; i++) 
					{
						litm.add(classifys[i]);
					}
				}
				records.add(litm);
			}
		} catch (SQLException e) {
			System.out.println("出现异常情况，请重新检查代码！");
			e.printStackTrace();
		}finally{
			JdbcUtils02.release(conn, st, rs);
		}
		return records;
	}
	/**
	 * 
	 * @return:       List<List<String>> 
	 * @return
	 * <p>Description: 读取文件中的数据<p>
	 * @date:          2016年3月9日下午6:36:41
	 * @author         ysl
	 */
        public static List<List<String>> getDatabase()
        {
                List<List<String>> db = new ArrayList<List<String>>() ;
               
                try
                {
                        File file = new File("data.txt") ;
                        
                        if ( file.isFile() && file.exists())
                        {
                                InputStreamReader read = new InputStreamReader
                                                        (
                                                        new FileInputStream(file)
                                                                        ) ;
                                
                                BufferedReader reader = new BufferedReader( read ) ;
                                
                                
                                String line = null ;
                                
                                while ( (line = reader.readLine())!= null )
                                {
                                        String [] strToknizer = line.split("        ") ;
                                       
                                        List<String> tmpLine = new ArrayList<String>() ;
                                       
                                        for ( int i = 1 ; i < strToknizer.length ; i++ )
                                        {
                                                
                                                tmpLine.add(strToknizer[i]) ;
                                                
                                        }
                                        db.add(tmpLine) ;
                                }
                                
                                reader.close();
                        }
                        else
                        {
                                System.out.println("fail to find target file !");
                        }
                }
                catch (Exception e)
                {
                        System.out.println("fail in reading file's content");
                        e.printStackTrace();
                }
               
                return db ;
        }

}