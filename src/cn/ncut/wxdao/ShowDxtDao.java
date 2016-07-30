package cn.ncut.wxdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import cn.ncut.utils.JdbcUtils;
import cn.ncut.wxdomain.DXT;

public class ShowDxtDao {

	public List<DXT> getDxt(int score_limit, int currentPage, int pageSize) {
		// TODO Auto-generated method stub

		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<DXT> list = new ArrayList<DXT>();
		try {
			conn = JdbcUtils.getConnection();

			String sql = "SELECT a.device_number device_number,a.score_count score_count,a.in_count in_count,a.nums nums,b.enter_time enter_time,b.nick_name nick_name " +
					"FROM tab_wxcounts AS a,tab_deviceinfo AS b  " +
					"WHERE a.device_number=b.device_number " +
					"AND a.score_count>=? " +
					"AND b.user_unit='100002' " +
					"order by a.score_count DESC " +
					"limit ?,?";
			 
			statement = conn.prepareStatement(sql);
			statement.setInt(1, score_limit);
			statement.setInt(2, (currentPage - 1) * pageSize);
			statement.setInt(3, pageSize);
			result = statement.executeQuery();
			while (result.next()) {
				DXT dxt= new DXT();
				String device_number = result.getString("device_number"); 
				if(device_number.length()!=0||device_number!=null){
					int score_count = result.getInt("score_count");
					String enter_time = result.getString("enter_time").substring(0,10);  
					dxt.setDevice_number(device_number);
					dxt.setScore_count(score_count);
					dxt.setEnter_time(enter_time);
					dxt.setNums(result.getInt("nums"));
					dxt.setScore_cur(score_count);
					dxt.setScore_in(result.getInt("in_count"));
					dxt.setEmployee_id(result.getString("nick_name"));
					list.add(dxt);
				}
						
						
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, result);
		}
		return null;
	}

	public int getDxtTotal(int score_limit) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		int total=0;

		try {
			conn = JdbcUtils.getConnection();

			String sql = "SELECT count(*) count " +
					"FROM tab_wxcounts AS a,tab_deviceinfo AS b " +
					" WHERE a.device_number=b.device_number and a.device_number is not null " +
					"AND a.score_count>=? " +
					"AND b.user_unit='100002'";
			statement = conn.prepareStatement(sql);
			statement.setInt(1, score_limit);
			result = statement.executeQuery();
			if (result.next()) {
				total = result.getInt("count");
				
			}

			return total;

		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, result);

		}

		return 0;
	}

	public boolean savePrint(int score_limit, int currentPage, int pageSize) {

		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<DXT> list = new ArrayList<DXT>();

		try {
			conn = JdbcUtils.getConnection();

			String sql = "SELECT a.device_number device_number,a.score_count score_count,a.nums nums,b.enter_time enter_time " +
					"FROM tab_wxcounts AS a,tab_deviceinfo AS b  " +
					"WHERE a.device_number=b.device_number AND a.score_count>=? AND b.user_unit='100002' " +
					"order by a.score_count DESC " +
					"limit ?,?";
			statement = conn.prepareStatement(sql);
			statement.setInt(1, score_limit);
			statement.setInt(2, (currentPage - 1) * pageSize);
			statement.setInt(3, pageSize);
			result = statement.executeQuery();
			while (result.next()) {
				DXT dxt= new DXT();
				String device_number = result.getString("device_number"); 
				if(device_number.length()!=0||device_number!=null){
					int score_count = result.getInt("score_count");
					String enter_time = result.getString("enter_time").substring(0,10);  
					dxt.setDevice_number(device_number);
					dxt.setScore_count(score_count);
					dxt.setEnter_time(enter_time);
					dxt.setNums(result.getInt("nums"));
					list.add(dxt);
				}
			}

			for(DXT dxt:list){
				sql = "update tab_wxcounts set score_count=score_count-"+score_limit
						+",nums=nums+1,out_count=out_count+"+score_limit+"  where device_number='"+dxt.getDevice_number()+"'";
				statement = conn.prepareStatement(sql);
				statement.executeUpdate();
				updateInTrade(dxt.getDevice_number(),score_limit);
				updateTrade(dxt.getDevice_number(),score_limit);
			}
			return true;

		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, result);

		}
 
		return false;
	}
	/**
	 * 消减收入表中的积分
	 * @param device_number
	 * @param score_limit
	 */
	private void updateInTrade(String device_number, int score_limit) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<DXT> list = new ArrayList<DXT>(); 
	    int score_count=0;
		try {
			conn = JdbcUtils.getConnection();

			String sql = "select * from tab_wxscoreintrade where device_number='"+device_number+"' and status in(0,3) order by start_date asc";
			statement = conn.prepareStatement(sql);  
			result=statement.executeQuery();
			while(result.next()){
				int score=result.getInt("score");
				int id=result.getInt("id");
				score_count+=score;
				if(score_count<=score_limit){
					//若第一个不满足
					String sql2="update tab_wxscoreintrade set status=1 where id="+id;
					statement = conn.prepareStatement(sql2);
					statement.executeUpdate();
				}else if(score_count>score_limit){
					String sql3="update tab_wxscoreintrade set status=1 where id="+id;
					statement = conn.prepareStatement(sql3);
					statement.executeUpdate();
					int score_now=score_count-score_limit;
					String start_date=result.getString("start_date");
					String end_date=result.getString("end_date"); 
					int status=result.getInt("status");
					String sql4="insert into tab_wxscoreintrade(id,device_number,score,start_date,end_date,status) " +
							"values(?,?,?,?,?,?)";
					statement = conn.prepareStatement(sql4);
					statement.setInt(1, id); 
					statement.setString(2, device_number);
					statement.setInt(3, score_now); 
					statement.setString(4, start_date);
					statement.setString(5, end_date);
					statement.setInt(6, status); 
					statement.executeUpdate();
					break;
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, result);

		}
	}

	/**
	 * 添加交易记录
	 * @param device_number
	 * @param score_limit
	 */
	public void updateTrade(String device_number, int score_limit){
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<DXT> list = new ArrayList<DXT>();
		Date dt=new Date();
	     SimpleDateFormat matter1=new SimpleDateFormat("yyyy-MM-dd");
	     String date=matter1.format(dt);
		try {
			conn = JdbcUtils.getConnection();

			String sql = "insert into tab_wxscoretrade(device_number,score,date,status) values(?,?,?,?)";
			statement = conn.prepareStatement(sql);
			statement.setString(1, device_number);
			statement.setInt(2, score_limit);
			statement.setString(3, date);
			statement.setInt(4, 3);
			statement.executeUpdate(); 
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, result);

		}
 
	}

	public List<DXT> getWeekDxt(String start_date, String end_date) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<DXT> list = new ArrayList<DXT>();
		try {
			conn = JdbcUtils.getConnection();

			String sql = "SELECT a.device_number device_number,a.score score,a.status `status`,b.nick_name nick_name " +
					"FROM tab_wxscoreintrade AS a,tab_deviceinfo AS b " +
					"WHERE a.device_number=b.device_number " +
					"AND a.start_date BETWEEN ? and ? " +
					"AND b.user_unit='100002'";// +
			//		"limit ?,?";
			 
			statement = conn.prepareStatement(sql);
			statement.setString(1, start_date);
			statement.setString(2, end_date);
	//		statement.setInt(3, (currentPage - 1) * pageSize);
	//		statement.setInt(4, pageSize);
			result = statement.executeQuery();
			Map<String,DXT> map=new HashMap<String, DXT>(); 
			while (result.next()) {
				DXT dxt=new DXT();
				String device_number = result.getString("device_number"); 
				if(device_number.length()!=0||device_number!=null){
					int score_cur=0; 
					int score = result.getInt("score");
					int score_in=getScore(device_number,start_date,end_date);
					int status=result.getInt("status");
					String employee_id=result.getString("nick_name"); 
					if(status==0||status==3){
						//未用积分
						score_cur=score;
					}
					dxt.setDevice_number(device_number);
					dxt.setEmployee_id(employee_id); 
					dxt.setScore_in(score_in);
					if(map.containsKey(device_number)){
						//若已经包含此手机号
						DXT d=map.get(device_number);
						dxt.setScore_cur(score_cur+d.getScore_cur()); 
						map.put(device_number, dxt);
					}else{
						//若没有  
						dxt.setScore_cur(score_cur);
						map.put(device_number, dxt);
					}
					 
				}		
			}
			Iterator<Map.Entry<String, DXT>> it = map.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry<String, DXT> entry = it.next();
				DXT d = entry.getValue();
				list.add(d);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, result);
		}
		return null;
	}

	public int getDxtTotal(String start_date, String end_date) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		int total=0;

		try {
			conn = JdbcUtils.getConnection();

			String sql = "SELECT COUNT(DISTINCT(a.device_number)) count " +
					"FROM tab_wxscoreintrade AS a,tab_deviceinfo AS b " +
					" WHERE a.device_number=b.device_number and a.device_number is not null " +
					"AND a.start_date between ? and ? " +
					"AND b.user_unit='100002'";
			statement = conn.prepareStatement(sql);
			statement.setString(1, start_date);
			statement.setString(2, end_date);
			result = statement.executeQuery();
			if (result.next()) {
				total = result.getInt("count");
			}

			return total;

		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, result);

		}

		return 0;
	}
    public int getScore(String device_number,String start_date,String end_date){
    	Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
    	/*总收入积分*/
		String sqls="select sum(score) score from tab_wxscoretrade where device_number=? and `date` between ? and ? and `status` IN(0,1)";
		try {
			conn = JdbcUtils.getConnection();
			statement = conn.prepareStatement(sqls);
			statement.setString(1,device_number);
			statement.setString(2, start_date);
			statement.setString(3, end_date);
			result = statement.executeQuery();
			int score_in=0;
			if(result.next()){
				score_in=result.getInt("score");
			}
			System.out.println(device_number+"----"+start_date+"-----"+end_date+"++"+score_in);
			return score_in;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {

			JdbcUtils.release(conn, statement, result);

		}
		return 0;
		
    }

	public List<DXT> getDxtPrinted(String start_date, String end_date,
			int currentPage, int pageSize) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<DXT> list = new ArrayList<DXT>();
		String sql="";
		if(start_date.length()==0){

			sql = "SELECT a.device_number device_number,a.date date,a.score score,b.nick_name nick_name " +
					"FROM tab_wxscoretrade AS a,tab_deviceinfo AS b " +
					" WHERE a.device_number=b.device_number and a.device_number is not null " +
					"AND  a.status=3 " +
					"AND b.user_unit='100002' order by date desc "+
					"limit ?,?";
		}else{

			 sql = "SELECT a.device_number device_number,a.date date,a.score score,b.nick_name nick_name " +
					"FROM tab_wxscoretrade AS a,tab_deviceinfo AS b " +
					" WHERE a.device_number=b.device_number and a.device_number is not null " +
					"AND a.date between '"+start_date+"' and '"+end_date+"' and a.status=3 " +
					"AND b.user_unit='100002' order by date desc "+
					"limit ?,?";
		}
		try {
			conn = JdbcUtils.getConnection();
			statement = conn.prepareStatement(sql);
			statement.setInt(1, (currentPage - 1) * pageSize);
			statement.setInt(2, pageSize);
			result = statement.executeQuery();
			while (result.next()) {
				DXT dxt=new DXT();
				dxt.setDevice_number(result.getString("device_number"));
				dxt.setEnter_time(result.getString("date"));
				dxt.setEmployee_id(result.getString("nick_name"));
				dxt.setScore_count(result.getInt("score"));
				list.add(dxt);
			}

			return list;

		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, result);

		}

		return null;
	}

	public int getDxtPrintedTotal(String start_date, String end_date) {
		// TODO Auto-generated method stub
				Connection conn = null;
				PreparedStatement statement = null;
				ResultSet result = null;
				int total=0;
				String sql="";
				if(start_date.length()==0){
					sql="SELECT COUNT(*) count " +
						"FROM tab_wxscoretrade AS a,tab_deviceinfo AS b " +
						" WHERE a.device_number=b.device_number and status=3 " +
						"AND b.user_unit='100002'";
					System.out.println(start_date+"--"+start_date.length());
				}else
					sql = "SELECT COUNT(*) count " +
						"FROM tab_wxscoretrade AS a,tab_deviceinfo AS b " +
						" WHERE a.device_number=b.device_number and status=3 " +
						"AND a.date between '"+start_date+"' and '"+end_date+"' " +
						"AND b.user_unit='100002'";
				try {
					conn = JdbcUtils.getConnection();

					statement = conn.prepareStatement(sql); 
					result = statement.executeQuery();
					if (result.next()) {
						total = result.getInt("count");
					}

					return total;

				} catch (Exception e) {

					e.printStackTrace();
				} finally {

					JdbcUtils.release(conn, statement, result);

				}

				return 0;
	}

}
