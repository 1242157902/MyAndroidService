package cn.ncut.autotask.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * @author wzq
 *
 * version 1.0 2014-7-6 上午11:37:39
 */
public class JdbcUtils03 {
private  Connection conn=null;
	
	public  Connection getConnection() throws SQLException {
    	String driver = "com.mysql.jdbc.Driver";

		// URL指向要访问的数据库名scutcs

		String url = "jdbc:mysql://127.0.0.1:3306/mydb03";

		// MySQL配置时的用户名

		String user = "root";

		// Java连接MySQL配置时的密码

		String password = "1111";

		try {

		// 加载驱动程序

		Class.forName(driver);

		// 连续数据库

		 conn = DriverManager.getConnection(url, user, password);

		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
		
	}

public static void  release(Connection conn,Statement st,ResultSet rs) {
		
		if(rs!=null){
			try{
				rs.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
			rs = null;

		}
		if(st!=null){
			try{
				st.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		if(conn!=null){
			try{
				conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		
		
	}
    
}
