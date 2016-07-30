package cn.ncut.autotask.count;

import java.sql.Connection;
import java.sql.PreparedStatement;

import cn.ncut.autotask.utils.JdbcUtils;

import lombok.extern.slf4j.Slf4j;
/**
 * @author wzq
 *
 *version 1.0 2015-5-10 下午12:13:53
 */
@Slf4j
public class ThemeTask  {
	
	public static  void ExecuteThemeTask() {
		 Connection conn = null;
		PreparedStatement st = null;
		     JdbcUtils jdbcUtils=new JdbcUtils();
		
		try {
			conn = jdbcUtils.getConnection();
			String sql = "update tab_pushdevice,tab_pushthemetask  set tab_pushdevice.theme=tab_pushthemetask.theme where  func_taskpri(tab_pushdevice.themetask)=tab_pushthemetask.tid";
			st = conn.prepareStatement(sql);
			st.executeUpdate();
		} catch (Exception e) {
			log.error(e.toString());
		} finally {
				JdbcUtils.release(conn, st, null);
        }		
	}

	
	

}
