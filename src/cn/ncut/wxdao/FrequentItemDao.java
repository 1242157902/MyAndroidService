package cn.ncut.wxdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.ncut.utils.JdbcUtils;
import cn.ncut.utils.JdbcUtils02;

/**
 * 
 * <p>Title：        FrequentItemDao<p>
 * <p>Description: 查询项目的频繁项 <p>
 * @date:           2016年4月19日下午8:46:26
 * @author:         ysl
 * @version         1.0
 */
public class FrequentItemDao {

	public static void main(String[] args) 
	{
		FrequentItemDao fid = new FrequentItemDao();
	/*	List<String> strList = fid.getFrequentItemsOfDay("2015-08-01");
		for (String string : strList) 
		{
			System.out.println(string);
		}*/
		int num = fid.isFrequentItemsExists("2015-08-07","0.5");
		System.out.println(num);
	}
	
	/**
	 * 
	 * @return:       List<String> 
	 * @param searchDay
	 * @return
	 * <p>Description:获得频繁项集 <p>
	 * @date:          2016年4月19日下午8:49:58
	 * @author         ysl
	 */
	public List<String> getFrequentItemsOfDay(String searchDay,String minSupport)
	{
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<String> dlist=new ArrayList<String>();
		try {
			conn = new JdbcUtils02().getConnection();
			String sqlString="select classify " +
					"from tab_frequent_item  where date = '"+searchDay+"' and min_support='"+minSupport+"'" ;
			statement = conn.prepareStatement(sqlString);
			result = statement.executeQuery();
			while (result.next())
			{
				String classifys=result.getString("classify");
				if(classifys.length()!=0&&classifys!=null)
				{
					dlist.add(classifys);
				}
			}
			return dlist;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtils.release(conn, statement, result);
		}
		return null;
		
	}
	/**
	 * 
	 * @return:       int 
	 * @param searchDay
	 * @return
	 * <p>Description:用于判断挖掘的数据是否已经存在过了 <p>
	 * @date:          2016年4月20日上午9:21:34
	 * @author         ysl
	 */
	public int isFrequentItemsExists(String searchDay,String minSupport)
	{
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<String> dlist=new ArrayList<String>();
		try {
			conn = new JdbcUtils02().getConnection();
			String sqlString="select count(*) num " +
					"from tab_frequent_item  where date = '"+searchDay+"' and min_support='"+minSupport+"'";
			statement = conn.prepareStatement(sqlString);
			result = statement.executeQuery();
			while (result.next())
			{
				int num=result.getInt("num");
				return num;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtils.release(conn, statement, result);
		}
		return 0;
		
	}
}
