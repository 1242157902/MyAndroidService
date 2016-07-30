package cn.ncut.wxdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.ncut.utils.JdbcUtils;
import cn.ncut.wxdomain.LoctionClassify;

 

/**
 * 
 * <p>Title：        LocationClassifyDao<p>
 * <p>
 * Description:  
 * 				获得对应的标签。
 * <p>
 * @date:           2016年3月20日下午1:47:56
 * @author:         ysl
 * @version         1.0
 */
public class LocationClassifyDao {
	public static void main(String[] args) 
	{
		LocationClassifyDao lcd = new LocationClassifyDao();
		List<LoctionClassify> list =  lcd.getLocationClassifysByCode("003") ;
		for (LoctionClassify loctionClassify : list) 
		{
			System.out.println(loctionClassify.getName());
		}
	}

	/**
	 * 
	 * @return:       List<LoctionClassify> 
	 * @param value
	 * @return
	 * <p>
	 * Description: 
	 * 				通过value值来查询其下的所有标签类别
	 * <p>
	 * @date:          2016年3月20日下午2:02:31
	 * @author         ysl
	 */
	public List<LoctionClassify> getLocationClassifysByCode(String value) 
	{

		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<LoctionClassify> lcList = new ArrayList<LoctionClassify>();

		if(value!=null)
		{
			try {
				conn = JdbcUtils.getConnection();

				String sql = "select * from tab_pushcontentclass ";
				if (value!=null&&!"".equals(value))
				{
					sql=sql+" where value like"+"'"+value+"%'";
				}

				statement = conn.prepareStatement(sql);

				result = statement.executeQuery();
			
				while (result.next()) 
				{
					LoctionClassify  lc = new LoctionClassify();
					lc.setCode(result.getString("code"));
					lc.setDuration(result.getInt("duration"));
					lc.setValue(result.getString("value"));
					lc.setName(result.getString("name"));
					lc.setQue(result.getInt("que"));
					lcList.add(lc);
				}

				return lcList;

			} catch (SQLException e) {

				e.printStackTrace();
			} finally {

				JdbcUtils.release(conn, statement, result);

			}
		}
		return null;

	}
	/**
	 * 
	 * @return:       List<LoctionClassify> 
	 * @param value
	 * @return
	 * <p>Description: 精确查找通过value值<p>
	 * @date:          2016年3月23日下午10:26:45
	 * @author         ysl
	 */
	public LoctionClassify getLocationClassifysByvalue(String value) 
	{
		
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		
		if(value!=null)
		{
			try {
				conn = JdbcUtils.getConnection();
				
				String sql = "select * from tab_pushcontentclass ";
				if (value!=null&&!"".equals(value))
				{
					sql=sql+" where value ="+"'"+value+"'";
				}
				
				statement = conn.prepareStatement(sql);
				
				result = statement.executeQuery();
				
				LoctionClassify  lc = new LoctionClassify();
				while (result.next()) 
				{
					lc.setCode(result.getString("code"));
					lc.setDuration(result.getInt("duration"));
					lc.setValue(result.getString("value"));
					lc.setName(result.getString("name"));
					lc.setQue(result.getInt("que"));
				}
				
				return lc;
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			} finally {
				
				JdbcUtils.release(conn, statement, result);
				
			}
		}
		return null;
		
	}
}
