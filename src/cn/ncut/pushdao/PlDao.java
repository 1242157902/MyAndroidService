package cn.ncut.pushdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.ncut.pushdomain.ComBox;
import cn.ncut.pushdomain.ErrorLog;
import cn.ncut.pushdomain.PushAccepter;
import cn.ncut.pushdomain.PushLog;
import cn.ncut.pushdomain.RcvLog;
import cn.ncut.pushdomain.ThemeLog;
import cn.ncut.pushdomain.ThemeTask;
import cn.ncut.utils.JdbcUtils;

public class PlDao {

	public List<PushLog> GetPushLogList(int currentPage, int pageSize, String QueryCondition,String startmonth,String endmonth) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<PushLog> list = new ArrayList<PushLog>();
		try {
			conn = JdbcUtils.getConnection();
			statement = conn.prepareStatement("Call SelectPushLog(?,?,?,?,?)");
			statement.setString(1, QueryCondition);
			statement.setString(2, startmonth);
			statement.setString(3, endmonth);
			statement.setInt(4, (currentPage - 1) * pageSize);
			statement.setInt(5, pageSize);
			result = statement.executeQuery();
			while (result.next()) {
				if(result.getString("imei").equals("fyy")) break;
				PushLog item = new PushLog();
				item.setHid(result.getString("hid"));
				item.setImei(result.getString("imei"));
				item.setMbno(result.getString("mbno"));
                item.setOper(result.getString("pop"));
                item.setPhmsg(result.getString("phmsg"));
                item.setOpertime(result.getString("htime"));
                item.setOperator(result.getString("oper"));
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

	
	public int GetPushLogTotal(String pushLogQuery, String startmonth,
			String endmonth) {
			Connection conn = null;
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				conn = JdbcUtils.getConnection();
				st = conn.prepareStatement("Call SelectPushLogTotal(?,?,?)");
				st.setString(1,pushLogQuery);
				st.setString(2,startmonth);
				st.setString(3,endmonth);
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


	public List<RcvLog> GetRcvLogList(int currentPage, int pageSize, String QueryCondition,String startmonth,String endmonth) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<RcvLog> list = new ArrayList<RcvLog>();
		try {
			conn = JdbcUtils.getConnection();
			statement = conn.prepareStatement("Call SelectRcvLog(?,?,?,?,?)");
			statement.setString(1, QueryCondition);
			statement.setString(2, startmonth);
			statement.setString(3, endmonth);
			statement.setInt(4, (currentPage - 1) * pageSize);
			statement.setInt(5, pageSize);
			result = statement.executeQuery();
			while (result.next()) {
				if(result.getString("imei").equals("fyy")) break;
				RcvLog item = new RcvLog();
				item.setRid(result.getString("rid"));
				item.setImei(result.getString("imei"));
				item.setMbno(result.getString("mbno"));
                item.setType(result.getString("type"));
                item.setPhmsg(result.getString("phmsg"));
                item.setAcctime(result.getString("acctime"));
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

	
	public int GetRcvLogTotal(String pushLogQuery, String startmonth,
			String endmonth) {
			Connection conn = null;
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				conn = JdbcUtils.getConnection();
				st = conn.prepareStatement("Call SelectRcvLogTotal(?,?,?)");
				st.setString(1,pushLogQuery);
				st.setString(2,startmonth);
				st.setString(3,endmonth);
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

	
	public List<ComBox> GetPushMsgList() {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<ComBox> list = new ArrayList<ComBox>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select puid,title from tab_pushlist";
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			while (result.next()) {
				ComBox item = new ComBox();
				item.setId(result.getString("puid"));
				item.setName(result.getString("title"));
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
	public List<ComBox> GetPushQueList() {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<ComBox> list = new ArrayList<ComBox>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select qid,title from tab_pushque";
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			while (result.next()) {
				ComBox item = new ComBox();
				item.setId(result.getString("qid"));
				item.setName(result.getString("title"));
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

	public List<ThemeTask> GetThemeTaskList(int currentPage, int pageSize, String operId) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<ThemeTask> list = new ArrayList<ThemeTask>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select tid,tname,IFNULL(theme,0) as theme,ttime,opertime ,(select username from tab_user where user_id=tab_pushthemetask.operator) as operator from tab_pushthemetask where operator=? ORDER BY opertime DESC limit ?,?";
			statement = conn.prepareStatement(sql);
			statement.setString(1,operId);
			statement.setInt(2, (currentPage - 1) * pageSize);
			statement.setInt(3, pageSize);
			result = statement.executeQuery();
			while (result.next()) {
				ThemeTask item = new ThemeTask();
				item.setTid(result.getInt("tid"));
				item.setTname(result.getString("tname"));
				item.setTheme(result.getString("theme"));
				item.setTtime(result.getString("ttime"));
				item.setOpertime(result.getString("opertime"));
				item.setOperator(result.getString("operator"));
				list.add(item);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement,result);
		}
		return null;
	}

	public int GetThemeTaskTotal(String operId) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select count(*) from tab_pushthemetask where operator='"+operId+"'";
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


	public void AddMbthemeItem(ThemeTask item) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "Call InsertMbthemeItem(?,?,?,?)";
			st = conn.prepareStatement(sql);
			st.setString(1, item.getTname());
			st.setInt(2,Integer.parseInt(item.getTheme()));
			st.setString(3,item.getTtime());
			st.setString(4, item.getOperator());
			st.executeUpdate();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				JdbcUtils.release(conn, st, null);
        }	
	}


	public void UpdateMbthemeItem(ThemeTask item) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "UPDATE tab_pushthemetask SET tname=?,theme=?,ttime=?,opertime=NOW(),operator=? WHERE tid=?";
			st = conn.prepareStatement(sql);
			st.setString(1, item.getTname());
			st.setInt(2,Integer.parseInt(item.getTheme()));
			st.setString(3,item.getTtime());
			st.setString(4, item.getOperator());
			st.setInt(5, item.getTid());
			st.executeUpdate();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				JdbcUtils.release(conn, st, null);
        }		
	}


	public void DeleteMbthemeItem(int tid) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "delete from tab_pushthemetask where tid=?";
			st = conn.prepareStatement(sql);
			st.setInt(1,tid);
			st.executeUpdate();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				JdbcUtils.release(conn, st, null);
        }		
	}


	public void DeleteMbthemeInDetail(int tid) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "update tab_deviceinfo set modechage_task=SUBSTRING(REPLACE(CONCAT(',',modechage_task), ',"+tid+",', ','),2) where LOCATE(',"+tid+",',ifnull(modechage_task,''))>0 ";
			st = conn.prepareStatement(sql);
			st.executeUpdate();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				JdbcUtils.release(conn, st, null);
        }	
	}


	public List<PushAccepter> GetAccepterList(int currentPage, int pageSize, String qrcsql,String ptlid) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<PushAccepter> RecList = new ArrayList<PushAccepter>();
		try {
			conn = JdbcUtils.getConnection();
			statement = conn.prepareStatement("select device_imei,modechage_task,device_number,EnCompany(user_unit) as company,EnCompany(device_seller) as seller,user_sex,user_birth,EnMbOs(device_os_version) as mbos,EnMbArea(mobile_area) as area,EnMbProvider(mobile_type) as provider,EnMbType(device_type) as mbtype from tab_deviceinfo "+qrcsql+"limit ?,?");
			statement.setInt(1, (currentPage - 1) * pageSize);
			statement.setInt(2, pageSize);
			result = statement.executeQuery();
			while (result.next()) {
				PushAccepter Rec = new PushAccepter();
				Rec.setImei(result.getString("device_imei"));
				String str = result.getString("modechage_task");
				if(str==null) str="";
				boolean  b = str.matches("(((.*),"+ptlid+",(.*))|("+ptlid+",(.*)))");
				if(b) Rec.setAllque("1");
				else Rec.setAllque("0");
				Rec.setMbno(result.getString("device_number"));
				Rec.setCompany(result.getString("company"));
				Rec.setSeller(result.getString("seller"));
				Rec.setGender(result.getInt("user_sex"));
				Rec.setBirth(result.getString("user_birth"));
				Rec.setMbos(result.getString("mbos"));
				Rec.setArea(result.getString("area"));
				Rec.setProvider(result.getString("provider"));
				Rec.setMbtype(result.getString("mbtype"));
				RecList.add(Rec);
			}
			return RecList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, null);
		}
		return null;
	}

	public int GetAccepterTotal(String qrcsql) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			st = conn.prepareStatement("select count(*) from tab_deviceinfo "+qrcsql);
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

	public void SingleOkPush(String imei, String pid,String operId) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
				conn = JdbcUtils.getConnection();
				String sql = "update tab_deviceinfo set modechage_task=CONCAT(IFNULL(modechage_task,''),'"+pid+",') where device_imei='"+imei+"'";
				st = conn.prepareStatement(sql);
				st.executeUpdate();
			} catch (SQLException e) {
					e.printStackTrace();
			} finally {
					JdbcUtils.release(conn, st, null);
            }		
	}

	public void SingleCancelPush(String imei, String pid,String operId) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
				conn = JdbcUtils.getConnection();
				String sql = "update tab_deviceinfo set themetask=SUBSTRING(REPLACE(CONCAT(',',modechage_task), ',"+pid+",', ','),2) where device_imei='"+imei+"'";
				st = conn.prepareStatement(sql);
				st.executeUpdate();
			} catch (SQLException e) {
					e.printStackTrace();
			} finally {
					JdbcUtils.release(conn, st, null);
            }	
	}

	public void BatchOkPush(String imeis, String pid,String operId){
		Connection conn = null;
		PreparedStatement st = null;
		try {
				conn = JdbcUtils.getConnection();
				String sql = "update tab_deviceinfo set modechage_task=CONCAT(IFNULL(modechage_task,''),'"+pid+",') where device_imei in "+imeis;
				st = conn.prepareStatement(sql);
				st.executeUpdate();
			} catch (SQLException e) {
					e.printStackTrace();
			} finally {
					JdbcUtils.release(conn, st, null);
            }		
	}

	public void BatchCancelPush(String imeis, String pid,String operId) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
				conn = JdbcUtils.getConnection();
				String sql = "update tab_deviceinfo set modechage_task=SUBSTRING(REPLACE(CONCAT(',',modechage_task), ',"+pid+",', ','),2) where device_imei in "+imeis;
				st = conn.prepareStatement(sql);
				st.executeUpdate();
			} catch (SQLException e) {
					e.printStackTrace();
			} finally {
					JdbcUtils.release(conn, st, null);
            }	
	}
	
	public void ExecuteThemeTask() {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql="InsertThmChgLogByBatch(NOW())";
			//String sql = "update tab_pushdevice,tab_pushthemetask  set tab_pushdevice.theme=tab_pushthemetask.theme,tab_pushdevice.themestatus=0 where  func_taskpri(tab_pushdevice.themetask)=tab_pushthemetask.tid and tab_pushdevice.status<>0";
			st = conn.prepareStatement(sql);
			//st.setInt(1,tid);
			st.executeUpdate();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				JdbcUtils.release(conn, st, null);
        }		
	}


	public List<ThemeLog> GetThemeLogList(int currentPage, int pageSize,String QueryCondition, String startmonth, String endmonth) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<ThemeLog> list = new ArrayList<ThemeLog>();
		try {
			conn = JdbcUtils.getConnection();
			statement = conn.prepareStatement("Call SelectThemeLog(?,?,?,?,?)");
			statement.setString(1, QueryCondition);
			statement.setString(2, startmonth);
			statement.setString(3, endmonth);
			statement.setInt(4, (currentPage - 1) * pageSize);
			statement.setInt(5, pageSize);
			result = statement.executeQuery();
			while (result.next()) {
				if(result.getString("imei").equals("fyy")) break;
				ThemeLog item = new ThemeLog();
				item.setTid(result.getString("tid"));
				item.setImei(result.getString("imei"));
				item.setMbno(result.getString("mbno"));
                item.setCurtheme(result.getString("curtheme"));
                item.setExptheme(result.getString("exptheme"));
                item.setOpersrc(result.getString("opersrc"));
                item.setOperator(result.getString("operator"));
                item.setOpertime(result.getString("opertime"));
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

	
	public int GetThemeLogTotal(String pushLogQuery, String startmonth,
			String endmonth) {
			Connection conn = null;
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				conn = JdbcUtils.getConnection();
				st = conn.prepareStatement("Call SelectThemeLogTotal(?,?,?)");
				st.setString(1,pushLogQuery);
				st.setString(2,startmonth);
				st.setString(3,endmonth);
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


	public List<ComBox> GetErrorTypeList() {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<ComBox> list = new ArrayList<ComBox>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select DISTINCT type from tab_pusherror";
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			while (result.next()) {
				ComBox item = new ComBox();
				item.setId(result.getString("type"));
				item.setName(result.getString("type"));
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


	public List<ErrorLog> GetErrorLogList(int currentPage, int pageSize,String errorLogQuery) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<ErrorLog> list = new ArrayList<ErrorLog>();
		try {
			conn = JdbcUtils.getConnection();
			statement = conn.prepareStatement("select * from ( select *,(select type from tab_pusherror where id=errid) as type from tab_pusherrorlog  ) tab "+errorLogQuery+" limit ?,?");
			statement.setInt(1, (currentPage - 1) * pageSize);
			statement.setInt(2, pageSize);
			result = statement.executeQuery();
			while (result.next()) {
				ErrorLog item = new ErrorLog ();
				item.setEid(result.getInt("eid"));
				item.setErrtype(result.getString("type"));
                item.setErrtxt(result.getString("errtxt"));
                item.setErrmethod(result.getString("errmethod"));
				item.setImei(result.getString("imei"));
                item.setInmsg(result.getString("inmsg"));
                item.setOutmsg(result.getString("outmsg"));
                item.setErrtime(result.getString("time"));
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


	public int GetErrorLogTotal(String errorLogQuery) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			st = conn.prepareStatement("select count(*) from ( select *,(select type from tab_pusherror where id=errid) as type from tab_pusherrorlog ) tab "+errorLogQuery);
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
}

