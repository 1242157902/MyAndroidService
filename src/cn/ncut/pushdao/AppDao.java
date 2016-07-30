package cn.ncut.pushdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import cn.ncut.pushdomain.Appstore;
import cn.ncut.pushdomain.ClientAppstore;
import cn.ncut.pushdomain.ComBox;
import cn.ncut.pushdomain.PushContent;
import cn.ncut.pushservice.ApkService;
import cn.ncut.utils.JdbcUtils;


public class AppDao {

	public void AddApp(Appstore app) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
				conn = JdbcUtils.getConnection();
				String sql = "Insert tab_appstore(app_name,app_title,app_class,app_downurl,app_icon,app_score,app_isshow,user_id,app_size,app_uptime,app_memo)"
						+" values(?,?,?,?,?,?,?,?,?,now(),?)";
				st = conn.prepareStatement(sql);
				st.setString(1, app.getApp_name());
				st.setString(2,app.getApp_title());
				st.setString(3, app.getApp_class());
				st.setString(4, app.getApp_downurl());
				st.setString(5, app.getApp_icon());
				st.setInt(6,app.getApp_score());
				st.setBoolean(7, app.isApp_isshow());
				st.setString(8, app.getUser_id());
				st.setInt(9,app.getApp_size());
				st.setString(10,app.getApp_memo());
				st.executeUpdate();
			} catch (SQLException e) {
					e.printStackTrace();
			} finally {
					JdbcUtils.release(conn, st, null);
            }		
	}

	public List<Appstore> GetAppList(int currentPage, int pageSize,
			String operId) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<Appstore> list = new ArrayList<Appstore>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select  a.*,(select username from tab_user where user_id=a.user_id) as user_name,b.name as app_className from tab_appstore AS a left JOIN tab_appclass as b on a.app_class=b.id where a.user_id=?  ORDER BY app_uptime DESC LIMIT ?,?";
			statement = conn.prepareStatement(sql);
			statement.setString(1,operId);
			statement.setInt(2, (currentPage - 1) * pageSize);
			statement.setInt(3, pageSize);
			result = statement.executeQuery();
			while (result.next()) {
				Appstore item = new Appstore();
				item.setApp_id(result.getInt("app_id"));
				item.setApp_title(result.getString("app_title"));
				item.setApp_class(result.getString("app_class"));
				item.setApp_className(result.getString("app_className"));
				item.setApp_downurl(result.getString("app_downurl")+"app_files/"+result.getString("app_name"));
				item.setApp_icon(result.getString("app_downurl")+"app_icons/"+result.getString("app_icon"));
				item.setApp_downnum(result.getInt("app_downnum"));
				item.setApp_score(result.getInt("app_score"));
				item.setApp_isshow(result.getBoolean("app_isshow"));
				item.setApp_size(result.getInt("app_size"));
				item.setUser_id(result.getString("user_name"));
				item.setApp_uptime(result.getString("app_uptime"));
				item.setApp_memo(result.getString("app_memo"));
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

	public int GetAppTotal(String operId) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select count(*) from tab_appstore where user_id=?";
			st = conn.prepareStatement(sql);
			st.setString(1,operId);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		return 0;
	}

	public void EditApp(Appstore app) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
				conn = JdbcUtils.getConnection();
				String sql = "Update tab_appstore set app_title=?,app_class=?,app_score=?,app_isshow=?,app_memo=? where app_id=?";
				st = conn.prepareStatement(sql);
				st.setString(1, app.getApp_title());
				st.setString(2, app.getApp_class());
				st.setInt(3,app.getApp_score());
				st.setBoolean(4, app.isApp_isshow());
				st.setString(5, app.getApp_memo());
				st.setInt(6,app.getApp_id());
				st.executeUpdate();
			} catch (SQLException e) {
					e.printStackTrace();
			} finally {
					JdbcUtils.release(conn, st, null);
            }		
	}
	public void EditAppFile(Appstore app) {
		Connection conn = null;
		PreparedStatement st = null;
		try {			
				conn = JdbcUtils.getConnection();
				String sql = "Update tab_appstore set app_title=?,app_class=?,app_score=?,app_isshow=?,app_size=?,app_name=?,app_downurl=?,app_icon=?,app_memo=? where app_id=?";
				st = conn.prepareStatement(sql);
				st.setString(1, app.getApp_title());
				st.setString(2, app.getApp_class());
				st.setInt(3,app.getApp_score());
				st.setBoolean(4, app.isApp_isshow());
				st.setInt(5,app.getApp_size());
				st.setString(6, app.getApp_name());
				st.setString(7, app.getApp_downurl());
				st.setString(8, app.getApp_icon());
				st.setString(9, app.getApp_memo());
				st.setInt(10,app.getApp_id());
				st.executeUpdate();
			} catch (SQLException e) {
					e.printStackTrace();
			} finally {
					JdbcUtils.release(conn, st, null);
            }		
	}
	
	
	
	public void DeleteApp(String appid) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
				conn = JdbcUtils.getConnection();
				String sql = "delete from tab_appstore where app_id="+appid;
				st = conn.prepareStatement(sql);
				st.executeUpdate();
			} catch (SQLException e) {
					e.printStackTrace();
			} finally {
					JdbcUtils.release(conn, st, null);
            }		
	}

	public ComBox GetApkFileIcon(int appid) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			ComBox item = new  ComBox();
			conn = JdbcUtils.getConnection();
			String sql = "select  app_icon,app_downurl,app_name from tab_appstore where app_id=?";
			statement = conn.prepareStatement(sql);
			statement.setInt(1,appid);
			result = statement.executeQuery();
			while (result.next()) {
				item.setId(result.getString("app_downurl")+"app_icons/"+result.getString("app_icon"));
				item.setName(result.getString("app_downurl")+"app_files/"+result.getString("app_name"));
			}
			return item;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, null);
		}
		return null;
	}

	public List<ClientAppstore> GetClientAppList(int appid, String keyword) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<ClientAppstore> list = new ArrayList<ClientAppstore>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select  a.*,(select username from tab_user where user_id=a.user_id) as user_name,b.name as app_className from tab_appstore AS a left JOIN tab_appclass as b on a.app_class=b.id where app_isshow=1 and app_id>? ";
			if(!keyword.equals("")) sql+="and (app_title like '%"+keyword+"%' or name  like '%"+keyword+"%' or app_memo  like '%"+keyword+"%' ) ";
			sql+="limit 0,6";
			statement = conn.prepareStatement(sql);
			statement.setInt(1,appid);
			result = statement.executeQuery();
			while (result.next()) {
				ClientAppstore item = new ClientAppstore();
				item.setId(result.getInt("app_id"));
				item.setIcon(result.getString("app_icon"));
				item.setTitle(result.getString("app_title"));
				item.setNum(result.getInt("app_downnum"));
				item.setSize(result.getInt("app_size"));
				item.setApkurl(ApkService.formatSize(item.getSize()));
				item.setTip(result.getString("app_className"));
				item.setMemo(result.getString("app_memo"));
				//item.setApkurl(result.getString("app_downurl")+"app_files/"+result.getString("app_name"));
				item.setApkname(result.getString("app_name"));
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

	public void CommitDownum(String apkid) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "update tab_appstore set app_downnum=app_downnum+1 where app_id="+apkid;
			st = conn.prepareStatement(sql);
			st.executeUpdate();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				JdbcUtils.release(conn, st, null);
        }		
	}

}
