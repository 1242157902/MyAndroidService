package cn.ncut.pushdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.ncut.pushdomain.JsonModel;
import cn.ncut.pushdomain.PushAccepter;
import cn.ncut.utils.JdbcUtils;

public class JsonDao {

	public List<JsonModel> GetCompanyList() {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<JsonModel> comList = new ArrayList<JsonModel>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select * from tab_company";
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			while (result.next()) {
				JsonModel comItem = new JsonModel();
				comItem.setId(result.getString("com_no"));
				comItem.setName(result.getString("com_name"));
				comList.add(comItem);
			}
			return comList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, null);
		}
		return null;
	}
	public List<JsonModel> GetBigBrandList() {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<JsonModel> comList = new ArrayList<JsonModel>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select * from tab_pushmbtype where id REGEXP '^[0-9]{2}$' and id <>'99'";
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			while (result.next()) {
				JsonModel comItem = new JsonModel();
				comItem.setId(result.getString("id"));
				comItem.setName(result.getString("name"));
				comList.add(comItem);
			}
			return comList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, null);
		}
		return null;
	}
	public List<JsonModel> GetBigAreaList() {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<JsonModel> comList = new ArrayList<JsonModel>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select * from tab_pusharea where id REGEXP '^[0-9][0-9]$' or id='0101' or id='0201' or id='0301' or id='0401' or name like '%香港%' or name like '%澳门%' or name like '%台湾%'";
			//String sql = "select left(id,2) as id,name from tab_pusharea where id REGEXP '^[0-9][0-9]$' or id='0101' or id='0201' or id='0301' or id='0401' or name like '%香港%' or name like '%澳门%' or name like '%台湾%'";
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			while (result.next()) {
				JsonModel comItem = new JsonModel();
				comItem.setId(result.getString("id"));
				comItem.setName(result.getString("name"));
				comList.add(comItem);
			}
			return comList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, null);
		}
		return null;
	}
	public List<JsonModel> GetAreaList() {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<JsonModel> comList = new ArrayList<JsonModel>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select * from tab_pusharea order by id asc";
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			while (result.next()) {
				JsonModel comItem = new JsonModel();
				comItem.setId(result.getString("id"));
				comItem.setName(result.getString("name"));
				comList.add(comItem);
			}
			return comList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, null);
		}
		return null;
	}

	public List<JsonModel> GetAppClassList() {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<JsonModel> comList = new ArrayList<JsonModel>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select * from tab_appclass order by id asc";
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			while (result.next()) {
				JsonModel comItem = new JsonModel();
				comItem.setId(result.getString("id"));
				comItem.setName(result.getString("name"));
				comList.add(comItem);
			}
			return comList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, null);
		}
		return null;
	}
	
	public List<JsonModel> GetContentClassList() {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<JsonModel> comList = new ArrayList<JsonModel>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select code as id,`name` from tab_pushcontentclass where code REGEXP CONCAT('^',(select code from tab_pushcontentclass where value='003' ),'[0-9][0-9][0-9]-') order by code asc";
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			while (result.next()) {
				JsonModel comItem = new JsonModel();
				comItem.setId(result.getString("id"));
				comItem.setName(result.getString("name"));
				comList.add(comItem);
			}
			return comList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, null);
		}
		return null;
	}
	
	public List<JsonModel> GetBrandList() {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<JsonModel> comList = new ArrayList<JsonModel>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select * from tab_pushmbtype order by id asc";
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			while (result.next()) {
				JsonModel comItem = new JsonModel();
				comItem.setId(result.getString("id"));
				comItem.setName(result.getString("name"));
				comList.add(comItem);
			}
			return comList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, null);
		}
		return null;
	}

	public List<JsonModel> GetMbOsList() {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<JsonModel> comList = new ArrayList<JsonModel>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select * from tab_pushmbos order by id asc";
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			while (result.next()) {
				JsonModel comItem = new JsonModel();
				comItem.setId(result.getString("id"));
				comItem.setName(result.getString("name"));
				comList.add(comItem);
			}
			return comList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, null);
		}
		return null;
	}

	public void RegisterAccepter(PushAccepter phac) {//存储过程  有更新用户资料 没有插入用户资料
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "Call RegisterAccepter(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			st = conn.prepareStatement(sql);
			st.setString(1,phac.getImei());
			st.setString(2,phac.getMbno());
			st.setString(3,phac.getCompany());
			st.setString(4,phac.getSeller());
			st.setInt(5,phac.getGender());
			st.setString(6,phac.getBirth());
			st.setString(7,phac.getMbos());
			st.setString(8,phac.getArea());
			st.setString(9,phac.getProvider());
			st.setString(10,phac.getMbtype());
			st.setString(11, phac.getImsi());
			st.setString(12, phac.getRegtime());
			st.setString(13, phac.getSeller_depid());
			st.setString(14, phac.getSeller_stuffid());
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, null);
        }	
	}


	public String GetProvider(String mbno) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select mobile_type from tab_pushmbno where LOCATE(mobile_num,?)=1";
			st = conn.prepareStatement(sql);
			st.setString(1,mbno);
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

	public String GetArea(String mbno) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select mobile_area from tab_pushmbno where LOCATE(mobile_num,?)=1";
			st = conn.prepareStatement(sql);
			st.setString(1,mbno);
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

	public String EnMbtype(String mbtype) {//更新tab_pushmbtype 返回id
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "Call UpdateMbType(?)";
			st = conn.prepareStatement(sql);
			st.setString(1,mbtype);
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

	public String EnMbos(String mbos) {//更新tab_pushmbos 返回id
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "Call UpdateMbOs(?)";
			st = conn.prepareStatement(sql);
			st.setString(1,mbos);
			st.execute();
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

	public String ConfirmPics(String imei, String pics) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "Call UpdateAccepterFmPics(?,?)";
			st = conn.prepareStatement(sql);
			st.setString(1,imei);
			st.setString(2,pics);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
			return "1";
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
	}

	public List<JsonModel> GetSinglePositionList(String comId) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<JsonModel> comList = new ArrayList<JsonModel>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select concat(com_no,'_',emp_duty) as id,emp_duty as name from tab_employee  where com_no="+comId+" and emp_duty is not null GROUP BY name";
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			while (result.next()) {
				JsonModel comItem = new JsonModel();
				comItem.setId(result.getString("id"));
				comItem.setName(result.getString("name"));
				comList.add(comItem);
			}
			return comList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, null);
		}
		return null;
	}
	
	public List<JsonModel> GetDoublePositionList(String comId) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<JsonModel> comList = new ArrayList<JsonModel>();
		try {
			conn = JdbcUtils.getConnection();
			String str="";
			if(comId!="")  str="and locate(CONCAT(',',SUBSTRING_INDEX(id,'_',1),','),',"+comId+",')>0 ";
			String sql = "select * from (" +
					"(select concat(CONVERT(com_no,char),'_') as id,com_name as name from tab_company)" +
					"UNION ALL " +
					"(select concat(CONVERT(com_no,char),'_',CONVERT(emp_duty,char)) as id,emp_duty as name from tab_employee GROUP BY name)" +
					") tab where id is not null "+str+" ORDER BY id";
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			while (result.next()) {
				JsonModel comItem = new JsonModel();
				comItem.setId(result.getString("id"));
				comItem.setName(result.getString("name"));
				comList.add(comItem);
			}
			return comList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, null);
		}
		return null;
	}

}
