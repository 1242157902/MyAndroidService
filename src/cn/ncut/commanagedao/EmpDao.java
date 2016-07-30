package cn.ncut.commanagedao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import cn.ncut.commanagedomain.Emp;
import cn.ncut.commanagedomain.Org;
import cn.ncut.utils.JdbcUtils;

/**
 * @author wzq
 * 
 *         version 1.0 2015-1-11 下午3:42:10
 */
public class EmpDao {

	/**
	 * @param emp
	 */
	public void save(Emp emp) {

		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = JdbcUtils.getConnection();

			String sql = "insert into tab_employee(com_no,depart_no,depart_name,emp_phonenum,emp_email,emp_age,emp_sex,emp_name,depart_area,depart_region,emp_duty,emp_id) values(?,?,?,?,?,?,?,?,?,?,?,?)";
			statement = conn.prepareStatement(sql);

			statement.setString(1, emp.getComno());
			statement.setString(2, emp.getDepartno());
			statement.setString(3, emp.getDepartname());
			statement.setString(4, emp.getPhonenum());
			statement.setString(5, emp.getEmail());
			statement.setString(6, emp.getAge());
			statement.setString(7, emp.getSex());
			statement.setString(8, emp.getEmpname());
			statement.setString(9, emp.getDepart_area());
			statement.setString(10, emp.getDepart_region());
			statement.setString(11, emp.getEmpduty());
			statement.setString(12, emp.getEmpid());

			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, null);

		}

	}

	/**
	 * @param currentPage
	 * @param pageSize
	 * @param comno
	 * @return
	 */
	public List<Emp> findByPagination(int currentPage, int pageSize,
			Map<String, String> m,String comno) {

		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;

		List<Emp> rlist;
		try {
			conn = JdbcUtils.getConnection();

			String sql = "select * from tab_employee where com_no="+comno;
		if ("100000".equals(comno)) {
				 sql = "select * from tab_employee where 1=1";
			}
			
			
      Set<Entry<String, String>> set = m.entrySet();
			
			Iterator it = set.iterator();
			
			while (it.hasNext()) {
				Map.Entry<String, String> me = (Map.Entry<String, String>) it.next();
				if("depart_area".equals(me.getKey()) && !"".equals(me.getValue())){
					sql += " and " + me.getKey() + " like '%"+ me.getValue()  +"%'" ;
				}
				
				if("depart_name".equals(me.getKey()) && !"".equals(me.getValue())){
					sql += " and " + me.getKey() + " like '%"+ me.getValue()  +"%'" ;
				}
				if("depart_region".equals(me.getKey()) && !"".equals(me.getValue())){
					sql += " and " + me.getKey() + " like '%"+ me.getValue()  +"%'" ;
				}
				
				if("emp_id".equals(me.getKey()) && !"".equals(me.getValue())){
					sql += " and " + me.getKey() + " like '%"+ me.getValue()  +"%'" ;
				}
				
							
			}
			sql = sql+" limit " + (currentPage-1)*pageSize +" , "  + pageSize ;
			
			
			

			statement = conn.prepareStatement(sql);
			
			rs = statement.executeQuery();

			rlist = new ArrayList<Emp>();
			while (rs.next()) {
				Emp emp = new Emp();

				emp.setId(rs.getInt("emp_no"));
				emp.setEmpname(rs.getString("emp_name"));

				emp.setPhonenum(rs.getString("emp_phonenum"));

				emp.setEmail(rs.getString("emp_email"));

				emp.setDepartno(rs.getString("depart_no"));

				emp.setDepartname(rs.getString("depart_name"));
				emp.setEmpid(rs.getString("emp_id"));
				
				String age= rs.getString("emp_age");
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy");
				Date d1 = new Date();

				String year = format.format(d1);
				
				int intage;
				try {
					intage = Integer.parseInt(year)-Integer.parseInt(age);
				} catch (Exception e) {
					intage=25;
				}
				
				

				emp.setAge(intage+"");

				emp.setSex(rs.getString("emp_sex"));

				emp.setStatus(rs.getInt("emp_status"));
				emp.setPicname(rs.getString("emp_picname"));
				emp.setDepart_area(rs.getString("depart_area"));
				emp.setDepart_region(rs.getString("depart_region"));
				emp.setEmpduty(rs.getString("emp_duty"));
				emp.setComno(rs.getString("com_no"));
				

				rlist.add(emp);
			}

			return rlist;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, rs);

		}

		return null;

	}

	/**
	 * @param comno
	 * @return
	 */
	public int getTotal(Map<String, String> m,String comno) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;

		int count = 0;
		try {
			conn = JdbcUtils.getConnection();

			
			
			String sql = "select count(*) from tab_employee where com_no="+comno;
			if ("100000".equals(comno)) {
				 sql = "select count(*) from tab_employee where 1=1";
			}
			
    Set<Entry<String, String>> set = m.entrySet();
			
			Iterator it = set.iterator();
			
			while (it.hasNext()) {
				Map.Entry<String, String> me = (Map.Entry<String, String>) it.next();
				if("depart_area".equals(me.getKey()) && !"".equals(me.getValue())){
					sql += " and " + me.getKey() + " like '%"+ me.getValue()  +"%'" ;
				}
				
				if("depart_name".equals(me.getKey()) && !"".equals(me.getValue())){
					sql += " and " + me.getKey() + " like '%"+ me.getValue()  +"%'" ;
				}
				if("depart_region".equals(me.getKey()) && !"".equals(me.getValue())){
					sql += " and " + me.getKey() + " like '%"+ me.getValue()  +"%'" ;
				}
				
				if("emp_no".equals(me.getKey()) && !"".equals(me.getValue())){
					sql += " and " + me.getKey() + " like '%"+ me.getValue()  +"%'" ;
				}
						
			 }
			

			statement = conn.prepareStatement(sql);
			
			rs = statement.executeQuery();

			while (rs.next()) {

				count = rs.getInt(1);

			}

			return count;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, rs);

		}

		return 0;
	}

	/**
	 * @param emp
	 *            emp.setId(Integer.parseInt(id)); emp.setDepartno(orgno);
	 *            emp.setDepartname(orgname); emp.setPhonenum(empphonenum);
	 *            emp.setEmail(empemail); emp.setSex(empsex);
	 *            emp.setAge(birthyear+"");
	 * 
	 * 
	 */
	public void update(Emp emp) {

		Connection conn = null;
		PreparedStatement statement = null;
	
		try {
			conn = JdbcUtils.getConnection();

			String sql = "update tab_employee set depart_no=?,depart_name=?,emp_phonenum=?,emp_email=?,emp_sex=?,emp_age=?,emp_name=?,emp_duty=?,emp_id=? where emp_no=?";

			statement = conn.prepareStatement(sql);
			statement.setString(1,emp.getDepartno());
			statement.setString(2,emp.getDepartname());
			statement.setString(3,emp.getPhonenum());
			statement.setString(4,emp.getEmail());
			statement.setString(5,emp.getSex());
			statement.setString(6,emp.getAge());
			statement.setString(7,emp.getEmpname());
			statement.setString(8, emp.getEmpduty());
			statement.setString(9, emp.getEmpid());
			
			statement.setInt(10,emp.getId());
		   
			statement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, null);

		}

	}

	/**
	 * @param id
	 */
	public void zhuxiao(String id) {
		
		Connection conn = null;
		PreparedStatement statement = null;
	
		try {
			conn = JdbcUtils.getConnection();

			String sql = "update tab_employee set emp_status=?  where emp_no=?";

			statement = conn.prepareStatement(sql);
			statement.setInt(1, 0);
		    statement.setInt(2,Integer.parseInt(id));
			
			statement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, null);

		}

		
		
		
	}

	/**
	 * @param emp
	 * @return
	 */
	public int query(Emp emp) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs=null;

		try {
			conn = JdbcUtils.getConnection();

			String sql = "select emp_no from tab_employee where emp_phonenum=? and emp_email=? and emp_name=?";
			statement = conn.prepareStatement(sql);

			statement.setString(1, emp.getPhonenum());
			statement.setString(2, emp.getEmail());
			statement.setString(3, emp.getEmpname());
			

			rs=statement.executeQuery();
			while (rs.next()) {
			return rs.getInt("emp_no");
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, rs);

		}
		
		
		
		return 0;
	}

	/**
	 * @param encoderContent
	 */
	public void updateemppicname(String encoderContent,int id) {
	
		Connection conn = null;
		PreparedStatement statement = null;
	
		try {
			conn = JdbcUtils.getConnection();

			String sql = "update tab_employee set emp_picname=?  where emp_no=?";

			statement = conn.prepareStatement(sql);
			statement.setString(1, encoderContent);
		    statement.setInt(2,id);
			
			statement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, null);

		}
	}

}
