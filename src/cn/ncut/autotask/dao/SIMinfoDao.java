package cn.ncut.autotask.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import cn.ncut.autotask.domain.SIMinfo;
import cn.ncut.autotask.utils.JdbcUtils;

/**
 * @author wzq
 *
 *version 1.0 2014-12-20 下午8:49:24
 */
public class SIMinfoDao {
	public SIMinfo getSIMinfo(String num) {
			Connection conn = null;
			PreparedStatement st = null;
			ResultSet rs = null;
		    String mobilearea=null;
			String mobiletype=null;
			String areacode=null;
			SIMinfo siMinfo=new SIMinfo();
			
			
			try {
				conn = new JdbcUtils().getConnection();
				String sql = "select * from tab_mobileinfo where mobile_num=?";
				st = conn.prepareStatement(sql);
				st.setString(1, num);
	
				rs = st.executeQuery();
	
				if (rs.next()) {
					mobilearea=rs.getString("mobile_area");
					mobiletype=rs.getString("mobile_type");
					areacode=rs.getString("area_code");
					siMinfo.setMobilearea(mobilearea);
					siMinfo.setMobiletype(mobiletype);
					siMinfo.setAreacode(areacode);
				
				}
				
				return siMinfo;
			} catch (Exception e) {
	
				e.printStackTrace();
			} finally {
				JdbcUtils.release(conn, st, rs);
			}
	            return null;
		}
}

