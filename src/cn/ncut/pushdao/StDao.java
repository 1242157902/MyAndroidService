package cn.ncut.pushdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.ncut.pushdomain.ComBox;
import cn.ncut.pushdomain.Employee;
import cn.ncut.pushdomain.PushContent;
import cn.ncut.pushdomain.PushItem;
import cn.ncut.pushdomain.PushQueTitle;
import cn.ncut.pushdomain.StatsModel;
import cn.ncut.utils.JdbcUtils;

public class StDao {

	public String GetString(String sql) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
			return "null";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		return "null";
	}
	
	
	public List<ComBox> GetComNameList(String comno) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<ComBox> PTList = new ArrayList<ComBox>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select com_no,com_name from tab_company";
			if(!comno.equals("100000")) sql+=" where com_no="+comno;
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			while (result.next()) {
				ComBox PTItem = new ComBox();
				PTItem.setId(result.getString("com_no"));
				PTItem.setName(result.getString("com_name"));
				PTList.add(PTItem);
			}
			return PTList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, null);
		}
		return null;
	}

	public List<ComBox> GetDeptNameList(String  cid) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<ComBox> PTList = new ArrayList<ComBox>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select depart_no, depart_name from tab_department where com_no="+cid;
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			while (result.next()) {
				ComBox PTItem = new ComBox();
				PTItem.setId(result.getString("depart_no"));
				PTItem.setName(result.getString("depart_name"));
				PTList.add(PTItem);
			}
			return PTList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, null);
		}
		return null;
	}

	public List<Employee> GetStuffList(int currentPage, int pageSize,
			String cid, String did) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<Employee> PhList = new ArrayList<Employee>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select emp_no,emp_id,emp_name,(select com_name from tab_company where com_no=tab_employee.com_no LIMIT 0,1) as com_name,"
					    +"(select depart_name from tab_department where depart_no=tab_employee.depart_no LIMIT 0,1) as depart_name,emp_duty,"
					    +"(select count(*) from tab_pushdetail where seller_empid=emp_no) as emp_sale  from tab_employee ";
			if(!cid.equals("all")) sql+="where com_no="+cid;
			if(did!=null&&!did.equals("")) sql+=" and depart_no="+did;
			sql+=" ORDER BY emp_id DESC limit ?,?";
		    statement = conn.prepareStatement(sql);
			statement.setInt(1, (currentPage - 1) * pageSize);
			statement.setInt(2, pageSize);
			result = statement.executeQuery();
			while (result.next()) {
				Employee PhItem = new Employee();
				PhItem.setEmp_no(result.getString("emp_no"));
				PhItem.setEmp_id(result.getString("emp_id"));
				PhItem.setEmp_name(result.getString("emp_name"));
				PhItem.setCom_name(result.getString("com_name"));
				PhItem.setDepart_name(result.getString("depart_name"));
				PhItem.setEmp_duty(result.getString("emp_duty"));
				PhItem.setEmp_sale(result.getString("emp_sale"));
				PhList.add(PhItem);
			}
			return PhList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, null);
		}
		return null;
	}

	public List<StatsModel> GetStatsDataOne(String start,String end,String func) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<StatsModel> list = new ArrayList<StatsModel>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "Call "+func+" ('"+start+"','"+end+"')";
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			while (result.next()) {
				StatsModel item = new StatsModel();
				item.setName(result.getString("datex"));
				item.setNum(result.getString("num"));
				list.add(item);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, null);
		}
		return null;
	}
	
	public List<StatsModel> GetStatsData(String sql) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<StatsModel> list = new ArrayList<StatsModel>();
		try {
			conn = JdbcUtils.getConnection();
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			while (result.next()) {
				StatsModel item = new StatsModel();
				item.setName(result.getString("name"));
				item.setNum(result.getString("num"));
				list.add(item);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, null);
		}
		return null;
	}
	
	/*
	public List<StatsModel> GetStatsDaySlideCountData(String sql,String con) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<StatsModel> list = new ArrayList<StatsModel>();
		try {
			conn = JdbcUtils.getConnection();
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			while (result.next()) {
				StatsModel item = new StatsModel();
				item.setName(result.getString("daydate"));
				item.setNum(GetStatsDaySlideCount(result.getString("daydate"),con)+"");
				list.add(item);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, null);
		}
		return null;
	}
	public int GetStatsDaySlideCount(String daydate,String con) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select day"+daydate.substring(8)+" from mydb03.tab_slide_count where dateym='"+daydate.substring(0,4)+daydate.substring(5,7)+"' "+con;
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
	}
	*/
	

	public int GetInt(String sql) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		return 0;
	}
	public String getComanyList() {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select  GROUP_CONCAT(com_no) from tab_company";		
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
			return "";
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
	}
	
	public String getComanyName(String Id) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select  com_name from tab_company  where com_no='"+Id+"'";		
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
			return "";
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
	}
}
