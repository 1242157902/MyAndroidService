package cn.ncut.pushservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;

import cn.ncut.utils.JdbcUtils;


public class SwitchService {
	public static String SexTitle(String code)
	{
		if(code.equals("1")) return "男";
		else if(code.equals("2")) return "女";
		else return "error";
	}
	
	public static String AgeTitle(String code)
	{
		if(code.equals("null-17")) return "18岁以下";
		else if(code.equals("18-22")) return "18岁到22岁";
		else if(code.equals("23-26")) return "23岁到26岁";
		else if(code.equals("27-35")) return "27岁到35岁";
		else if(code.equals("36-55")) return "36岁到55岁";
		else if(code.equals("56-null")) return "55岁以上";
		else return "error";
	}
	
	public static String AgeSql(String code)
	{
		Date today= new Date();
		int year=today.getYear()+1900;
		if(code.equals("null-17")) return " and user_birth>="+(year-17)+" ";
		else if(code.equals("18-22")) return " and user_birth>="+(year-22)+" and user_birth<="+(year-18)+" ";
		else if(code.equals("23-26")) return " and user_birth>="+(year-26)+" and user_birth<="+(year-23)+" ";
		else if(code.equals("27-35")) return " and user_birth>="+(year-35)+" and user_birth<="+(year-27)+" ";
		else if(code.equals("36-55")) return " and user_birth>="+(year-55)+" and user_birth<="+(year-36)+" ";
		else if(code.equals("56-null")) return " and user_birth<="+(year-56)+" ";
		else return "";
	}
	
	public static String ProviderTitle(String code)
	{
		if(code.equals("1")) return "移动";
		else if(code.equals("2")) return "联通";
		else if(code.equals("3")) return "电信";
		else return "其他";
	}
	
	public static String ProviderSql(String code)
	{		
		if(code.equals("1")||code.equals("2")||code.equals("3")) return " and mobile_type like '"+code+"%' ";
		else return "";
	}
	public static String UnitSql(String code)
	{		
		if(!code.equals("")) return " and user_unit='"+code+"' ";
		else return "";
	}
	public static String SellerSql(String code)
	{		
		if(!code.equals("")) return " and device_seller='"+code+"' ";
		else return "";
	}
	public static String BrandSql(String code)
	{		
		if(!code.equals("")) return " and mobile_type like '"+code+"%' ";
		else return "";
	}
	public static String AreaSql(String code)
	{		
		if(!code.equals("")) return " and mobile_area like '"+code+"%' ";
		else return "";
	}
	public static String getAreaList() {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select GROUP_CONCAT(id)  from tab_pusharea where id REGEXP '^[0-9][0-9]$' or id='0101' or id='0201' or id='0301' or id='0401' or name like '%香港%' or name like '%澳门%' or name like '%台湾%'";		
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
	
	public static String getBrandList() {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select GROUP_CONCAT(id) from tab_pushmbtype where id REGEXP '^[0-9]{2}$' and id <>'99'";		
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
	
	public static String getComanyList() {
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
	
	public  static  String getComanyName(String Id) {
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
	
	
	public  static  String getBrandName(String Id) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select  name from tab_pushmbtype  where id='"+Id+"'";		
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
	
	
	public  static  String getAreaName(String Id) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select  name from tab_pusharea  where id='"+Id+"'";		
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