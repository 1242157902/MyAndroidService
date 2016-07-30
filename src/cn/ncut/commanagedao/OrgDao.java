package cn.ncut.commanagedao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.math.RandomUtils;

import cn.ncut.commanagedomain.Org;
import cn.ncut.utils.JdbcUtils;

public class OrgDao {

	public List<Org> findList(String id, String comno) {

		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;

		List<Org> rlist;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "";
			if ("".equals(id) || id == null) {
				sql = "select * from tab_org where org_id =" + comno;
				
				
				
			} else {
				sql = "select * from tab_org where org_parent_id = " + id;
			}

			statement = conn.prepareStatement(sql);
			rs = statement.executeQuery();

			rlist = new ArrayList<Org>();
			while (rs.next()) {
				Org org = new Org();
				org.setId(rs.getInt("org_id"));
				org.setOrgname(rs.getString("org_name"));
				org.setOrglinkmanname(rs.getString("org_linkman_name"));
				org.setOrglinkmanphonenum(rs.getString("org_linkman_phonenum"));
				org.setOrglinkmanemail(rs.getString("org_linkman_email"));
				org.setOrgaddress(rs.getString("org_address"));
				org.setOrgstatus(rs.getString("org_status"));
				org.setDescription(rs.getString("org_description"));
				org.setOrgarea(rs.getString("depart_area"));
				org.setOrgregion(rs.getString("depart_region"));
				org.setOrgidcard(rs.getString("org_idcard"));

				if (getChildren(org.getId()).size() > 0) {
					org.setState("closed");
				}

				rlist.add(org);
			}

			return rlist;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, rs);

		}

		return null;

	}

	public List<Org> getChildren(int id) {

		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			String sql = "";
			sql = "select * from tab_department where com_no = " + id;
			conn = JdbcUtils.getConnection();
			statement = conn.prepareStatement(sql);
			rs = statement.executeQuery();

			List<Org> rlist = new ArrayList<Org>();
			while (rs.next()) {
				Org org = new Org();
				org.setId(rs.getInt("depart_no"));
				org.setOrgname(rs.getString("depart_name"));
				org.setOrglinkmanname(rs.getString("depart_linkman_name"));
				org.setOrglinkmanphonenum(rs.getString("depart_linkman_phonenum"));
				org.setOrglinkmanemail(rs.getString("depart_linkman_email"));
				org.setOrgaddress(rs.getString("depart_address"));
				org.setOrgstatus(rs.getString("depart_status"));
				org.setDescription(rs.getString("depart_description"));
				org.setOrgarea(rs.getString("depart_area"));
				org.setOrgregion(rs.getString("depart_region"));
				org.setOrgidcard(rs.getString("depart_cardid"));

				rlist.add(org);
			}
			return rlist;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, rs);

		}

		return null;

	}

	public void save(Org org) {

		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = JdbcUtils.getConnection();
			
			
           if (org.getComno().equals("100000")) {
        
        	String sql = "insert into tab_company(com_name,com_linkman_name,com_linkman_phonenum,com_linkman_email,com_address,org_description,org_cardid) values(?,?,?,?,?,?,?)";
   			statement = conn.prepareStatement(sql);
   			statement.setString(1, org.getOrgname());
   			statement.setString(2, org.getOrglinkmanname());
   			statement.setString(3, org.getOrglinkmanphonenum());
   			statement.setString(4, org.getOrglinkmanemail());
   			statement.setString(5, org.getOrgaddress());
   			statement.setString(6, org.getDescription());
            statement.setString(7, org.getOrgidcard());

   			statement.executeUpdate();
        	
		    }else {
				
		   	String sql = "insert into tab_department(depart_name,depart_linkman_name,depart_linkman_phonenum,depart_linkman_email,depart_address,depart_description,com_no,depart_area,depart_region,depart_cardid) values(?,?,?,?,?,?,?,?,?,?)";
				statement = conn.prepareStatement(sql);
				statement.setString(1, org.getOrgname());
				statement.setString(2, org.getOrglinkmanname());
				statement.setString(3, org.getOrglinkmanphonenum());
				statement.setString(4, org.getOrglinkmanemail());
				statement.setString(5, org.getOrgaddress());
				statement.setString(6, org.getDescription());
				statement.setString(7, org.getComno());
				statement.setString(8, org.getOrgarea());
				statement.setString(9, org.getOrgregion());
				statement.setString(10, org.getOrgidcard());

				statement.executeUpdate();
		  	
			}
           
           
           
           
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, null);

		}

	}

	public Org findById(int id) {

		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Org org;
		try {
			String sql = "";
			sql = "select * from tab_org where org_id = " + id;
			conn = JdbcUtils.getConnection();
			statement = conn.prepareStatement(sql);
			rs = statement.executeQuery();

			org = new Org();

			while (rs.next()) {

				org.setId(rs.getInt("org_id"));
				org.setOrgname(rs.getString("org_name"));
				org.setOrglinkmanname(rs.getString("org_linkman_name"));
				org.setOrglinkmanphonenum(rs.getString("org_linkman_phonenum"));
				org.setOrglinkmanemail(rs.getString("org_linkman_email"));
				org.setOrgaddress(rs.getString("org_address"));
				org.setOrgstatus(rs.getString("org_status"));
				org.setDescription(rs.getString("org_description"));
				org.setOrgarea(rs.getString("depart_area"));
				org.setOrgregion(rs.getString("depart_region"));

			}

			return org;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, rs);

		}

		return null;

	}

	public void update(Org org) {

		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = JdbcUtils.getConnection();
			
			
			if (org.getComno().equals("100000")) {
				String sql = "update tab_company set com_name=?,com_linkman_name=?,com_linkman_phonenum=?,com_linkman_email=?,com_address=?,com_description=?,com_idcard=? where com_no=?";
				statement = conn.prepareStatement(sql);
				statement.setString(1, org.getOrgname());
				statement.setString(2, org.getOrglinkmanname());
				statement.setString(3, org.getOrglinkmanphonenum());
				statement.setString(4, org.getOrglinkmanemail());
				statement.setString(5, org.getOrgaddress());
				statement.setString(6, org.getDescription());
				statement.setString(7, org.getOrgidcard());
				statement.setInt(8, org.getId());

				statement.executeUpdate();
				
			}else {
				
				String sql = "update tab_department set depart_name=?,depart_linkman_name=?,depart_linkman_phonenum=?,depart_linkman_email=?,depart_address=?,depart_description=?,depart_area=?,depart_region=?,depart_cardid=? where depart_no=?";
				statement = conn.prepareStatement(sql);
				statement.setString(1, org.getOrgname());
				statement.setString(2, org.getOrglinkmanname());
				statement.setString(3, org.getOrglinkmanphonenum());
				statement.setString(4, org.getOrglinkmanemail());
				statement.setString(5, org.getOrgaddress());
				statement.setString(6, org.getDescription());
				statement.setString(7, org.getOrgarea());
				statement.setString(8, org.getOrgregion());
				statement.setString(9, org.getOrgidcard());
				statement.setInt(10, org.getId());

				statement.executeUpdate();
				
				
				
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, null);

		}

	}

	public void delete(int cid,String comno) {
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = JdbcUtils.getConnection();
			if (comno.equals("100000")) {
				
				String sql = "update tab_company set com_stauts=? where com_no=?";
				statement = conn.prepareStatement(sql);
				statement.setString(1, "已注销");

				statement.setInt(2, cid);

				statement.executeUpdate();
				
				
				
				sql = "update tab_department set depart_stauts=? where com_no=?";
				statement = conn.prepareStatement(sql);
				statement.setString(1, "已注销");

				statement.setInt(2, cid);

				statement.executeUpdate();
				
				
			}else {
				
				String sql = "update tab_department set depart_status=? where depart_no=?";
				statement = conn.prepareStatement(sql);
				statement.setString(1, "已注销");

				statement.setInt(2, cid);

				statement.executeUpdate();
				
				
				
			}
			
			

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, null);

		}

	}

	/**
	 * @param orgname
	 * @return
	 */
	public int getorgid(String orgname) {

		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		int orgid = 0;
		try {
			String sql = "";
			sql = "select * from tab_department where depart_name =?";

			conn = JdbcUtils.getConnection();
			statement = conn.prepareStatement(sql);
			statement.setString(1, orgname);
			rs = statement.executeQuery();
			while (rs.next()) {

				orgid = rs.getInt("depart_no");

			}

			return orgid;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, rs);

		}

		return 0;
	}

	/**
	 * @param orgarea
	 * @return
	 */
	public List<String> getorgregions(String orgarea) {

		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<String> orgregions = new ArrayList<String>();
		try {
			String sql = "";
			sql = "select * from tab_area_region where depart_area =?";

			conn = JdbcUtils.getConnection();
			statement = conn.prepareStatement(sql);
			statement.setString(1, orgarea);
			rs = statement.executeQuery();
			while (rs.next()) {
				orgregions.add(rs.getString("depart_region"));

			}

			return orgregions;
		} catch (SQLException e) {
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
	public List<String> getorgarea(String comno) {

		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<String> orgareas = new ArrayList<String>();
		try {
			String sql = "";

			sql = "select DISTINCT depart_area from tab_area_region where com_no=?";

			conn = JdbcUtils.getConnection();
			statement = conn.prepareStatement(sql);
			statement.setString(1, comno);
			rs = statement.executeQuery();
			while (rs.next()) {
				orgareas.add(rs.getString("depart_area"));

			}

			return orgareas;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, rs);

		}

		return null;

	}

	/**
	 * @param id
	 * @return
	 */
	public int getTotal(String comno,Map<String, String>m) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;

		
		List<Org> rlist;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "";
			int intcomno=Integer.parseInt(comno);
		   if (intcomno==100000) {
				sql = "select count(*) from tab_company where 1=1";
				
				
				 Set<Entry<String, String>> set = m.entrySet();
					
					Iterator it = set.iterator();
					
					while (it.hasNext()) {
						Map.Entry<String, String> me = (Map.Entry<String, String>) it.next();
						if("com_name".equals(me.getKey()) && !"".equals(me.getValue())){
							sql += " and " + me.getKey() + " like '%"+ me.getValue()  +"%'" ;
						}
						
						if("com_cardid".equals(me.getKey()) && !"".equals(me.getValue())){
							sql += " and " + me.getKey() + " like '%"+ me.getValue()  +"%'" ;
						
					      }
					}
				
			
			
				
			} else {
				sql = "select count(*) from tab_department where com_no = " + comno;
				
				
				 Set<Entry<String, String>> set = m.entrySet();
					
					Iterator it = set.iterator();
					
					while (it.hasNext()) {
						Map.Entry<String, String> me = (Map.Entry<String, String>) it.next();
						if("depart_name".equals(me.getKey()) && !"".equals(me.getValue())){
							sql += " and " + me.getKey() + " like '%"+ me.getValue()  +"%'" ;
						}
						
						if("depart_cardid".equals(me.getKey()) && !"".equals(me.getValue())){
							sql += " and " + me.getKey() + " like '%"+ me.getValue()  +"%'" ;
						
					      }
					}
				
				
				
			
			}

		   conn = JdbcUtils.getConnection();
			statement = conn.prepareStatement(sql);
			
			rs = statement.executeQuery();
			while (rs.next()) {
				
			   return  rs.getInt(1);

			}

		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, rs);

		}

		return 0;
	   
	}

	/**
	 * @param id
	 * @param comno
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<Org> findList(String id, String comno, int currentPage,
			int pageSize,Map<String, String> m) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;

		
		List<Org> rlist;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "";
			int intcomno=Integer.parseInt(comno);
			
			rlist = new ArrayList<Org>();
			if (intcomno==100000) {
			sql = "select * from tab_company where 1=1";
				
				
			 Set<Entry<String, String>> set = m.entrySet();
				
				Iterator it = set.iterator();
				
				while (it.hasNext()) {
					Map.Entry<String, String> me = (Map.Entry<String, String>) it.next();
					if("com_name".equals(me.getKey()) && !"".equals(me.getValue())){
						sql += " and " + me.getKey() + " like '%"+ me.getValue()  +"%'" ;
					}
					
					if("com_cardid".equals(me.getKey()) && !"".equals(me.getValue())){
						sql += " and " + me.getKey() + " like '%"+ me.getValue()  +"%'" ;
					
				      }
				}
			     sql=sql+"  limit " + (currentPage-1)*pageSize +" , "  + pageSize;
				
				statement = conn.prepareStatement(sql);
				rs = statement.executeQuery();

				
				while (rs.next()) {
					Org org = new Org();
					org.setId(rs.getInt("com_no"));
					org.setOrgname(rs.getString("com_name"));
					org.setOrglinkmanname(rs.getString("com_linkman_name"));
					org.setOrglinkmanphonenum(rs.getString("com_linkman_phonenum"));
					org.setOrglinkmanemail(rs.getString("com_linkman_email"));
					org.setOrgaddress(rs.getString("com_address"));
					org.setOrgstatus(rs.getString("com_status"));
					org.setDescription(rs.getString("com_description"));
					org.setOrgarea("");
					org.setOrgregion("");
					org.setOrgidcard(rs.getString("com_cardid"));
                    rlist.add(org);
				}
				
				
				
				
				
				
				
			} else {
				
				
				
				
				sql = "select * from tab_department where com_no = " + comno ;
				
				 Set<Entry<String, String>> set = m.entrySet();
					
					Iterator it = set.iterator();
					
					while (it.hasNext()) {
						Map.Entry<String, String> me = (Map.Entry<String, String>) it.next();
						if("depart_name".equals(me.getKey()) && !"".equals(me.getValue())){
							sql += " and " + me.getKey() + " like '%"+ me.getValue()  +"%'" ;
						}
						
						if("depart_cardid".equals(me.getKey()) && !"".equals(me.getValue())){
							sql += " and " + me.getKey() + " like '%"+ me.getValue()  +"%'" ;
						
					      }
					}
				
				
				
				sql=sql+" limit " + (currentPage-1)*pageSize +" , "  + pageSize;
			
				statement = conn.prepareStatement(sql);
				rs = statement.executeQuery();

				
				while (rs.next()) {
					Org org = new Org();
					org.setId(rs.getInt("depart_no"));
					org.setOrgname(rs.getString("depart_name"));
					org.setOrglinkmanname(rs.getString("depart_linkman_name"));
					org.setOrglinkmanphonenum(rs.getString("depart_linkman_phonenum"));
					org.setOrglinkmanemail(rs.getString("depart_linkman_email"));
					org.setOrgaddress(rs.getString("depart_address"));
					org.setOrgstatus(rs.getString("depart_status"));
					org.setDescription(rs.getString("depart_description"));
					org.setOrgarea(rs.getString("depart_area"));
					org.setOrgregion(rs.getString("depart_region"));
					org.setOrgidcard(rs.getString("depart_cardid"));

					

					rlist.add(org);
				}
			
			
			
			}

			

			return rlist;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, statement, rs);

		}

		return null;
	}

}
