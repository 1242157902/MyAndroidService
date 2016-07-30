package cn.ncut.pushdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.ncut.pushdomain.AutoPushLog;
import cn.ncut.pushdomain.ContentClass;
import cn.ncut.pushdomain.PushAtom;
import cn.ncut.pushdomain.PushQue;
import cn.ncut.pushdomain.ComBox;
import cn.ncut.utils.JdbcUtils;

public class QueDao {

	public List<PushQue> GetQueList(int currentPage, int pageSize,String oper) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<PushQue> QueList = new ArrayList<PushQue>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select * from view_pushque  "+oper+" ORDER BY qid DESC limit ?,?";
			statement = conn.prepareStatement(sql);
			statement.setInt(1, (currentPage - 1) * pageSize);
			statement.setInt(2, pageSize);
			result = statement.executeQuery();
			while (result.next()) {
				PushQue phque = new PushQue();
				phque.setQid(result.getInt("qid"));
				phque.setTitle(result.getString("title"));
				phque.setQue(result.getString("que"));
				QueList.add(phque);
			}
			return QueList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, null);
		}
		return null;
	}

	public int GetQueTotal(String oper) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select count(*) from view_pushque "+oper;
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

	public String getFileNamex(int aid) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select namex from tab_pushcontent where cid = (select cid from tab_pushatom where aid=?)";			
			st = conn.prepareStatement(sql);
			st.setInt(1,aid);
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

	public String getQueAtom(int aid) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select cid,showtime,enddate from tab_pushatom where aid=?";			
			st = conn.prepareStatement(sql);
			st.setInt(1,aid);
			rs = st.executeQuery();
			if (rs.next()) {
				return aid+"*"+rs.getString("cid")+"*"+rs.getString("showtime")+"*"+rs.getString("enddate");
			}
			return "";
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
	}

	public String StoreAtoms(List<PushAtom> atoms) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String queue="";
		try {  
			   conn = JdbcUtils.getConnection();			   
		       for(PushAtom atom : atoms){ 
		         st= conn.prepareCall("Call InsertPushAtom(?,?,?)");  
		         st.setInt(1, atom.getCid());  
		         st.setFloat(2,atom.getShowtime());  
		         st.setString(3,atom.getEnddate());  
		         rs = st.executeQuery();
			     if (rs.next()) {
					queue+= rs.getString(1)+",";
				 }
			   } 
		 }catch (SQLException e) {
						e.printStackTrace();
				} finally {
				JdbcUtils.release(conn, st, null);
	        }		
		 return queue;  
	}

	public void InsertQueue(PushQue pq) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
				conn = JdbcUtils.getConnection();
				String sql = "Call InsertPushQueue(?,?,?)";
				st = conn.prepareStatement(sql);
				st.setString(1, pq.getTitle());
				st.setString(2, pq.getQue());
				st.setString(3, pq.getOper());
				st.executeUpdate();
			} catch (SQLException e) {
					e.printStackTrace();
			} finally {
					JdbcUtils.release(conn, st, null);
            }		
	}

	public String UpdateAtoms(List<PushAtom> atoms) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String queue="";
		try {  
	    	   conn = JdbcUtils.getConnection();	
		       for(PushAtom atom : atoms){ 
		    	 String sql="Call UpdatePushAtom( "+atom.getAid()+","+atom.getCid()+","+atom.getShowtime()+",'"+atom.getEnddate()+"')";
			     st= conn.prepareCall(sql); 	 
		        // st.setInt(1, atom.getAid()); 
		        // st.setInt(2, atom.getCid());  
		        // st.setFloat(3,atom.getShowtime());   
		        // st.setString(4,atom.getEnddate());  
		         rs = st.executeQuery();
			     if (rs.next()) {
					queue+= rs.getString(1)+",";
				 }
			   } 	
		 }catch (SQLException e) {
						e.printStackTrace();
				} finally {
				JdbcUtils.release(conn, st, null);
	        }		
		 return queue;  
	}

	public void UpdateQueue(PushQue pq) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
				conn = JdbcUtils.getConnection();
				String sql = "Call UpdatePushQueue(?,?,?)";
				st = conn.prepareStatement(sql);
				st.setInt(1, pq.getQid());
				st.setString(2, pq.getTitle());
				st.setString(3, pq.getQue());
				st.executeUpdate();
			} catch (SQLException e) {
					e.printStackTrace();
			} finally {
					JdbcUtils.release(conn, st, null);
            }		
	}

	public void SortQueue(String qid, String sortque) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
				conn = JdbcUtils.getConnection();
				String sql = "update tab_pushque set que=? where qid=?";
				st = conn.prepareStatement(sql);
				st.setString(1, sortque);
				st.setInt(2, Integer.parseInt(qid));
				st.executeUpdate();
			} catch (SQLException e) {
					e.printStackTrace();
			} finally {
					JdbcUtils.release(conn, st, null);
            }		
	}

	public void DeleteQueue(int qid) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
				conn = JdbcUtils.getConnection();
				//String sql = "Call DeletePushQueue(?)";
				String sql = "update tab_pushque set flag='0'  where qid=?";
				st = conn.prepareStatement(sql);
				st.setInt(1,qid);
				st.executeUpdate();
			} catch (SQLException e) {
					e.printStackTrace();
			} finally {
					JdbcUtils.release(conn, st, null);
            }		
	}

	public int CheckQueInPhlist(int qid) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select count(*) from tab_pushlist where queid=?";
			st = conn.prepareStatement(sql);
			st.setInt(1,qid);
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

	public void SetDefaultQueId(int com_no, int qid) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
				conn = JdbcUtils.getConnection();
				String sql = "update tab_company set queid=? where com_no=?";
				st = conn.prepareStatement(sql);
				st.setInt(1, qid);
				st.setInt(2, com_no);
				st.executeUpdate();
			} catch (SQLException e) {
					e.printStackTrace();
			} finally {
					JdbcUtils.release(conn, st, null);
            }		
	}

	public List<ContentClass> GetContClasslist(String id) {
			Connection conn = null;
			PreparedStatement statement = null;
			ResultSet result = null;
			List<ContentClass> list = new ArrayList<ContentClass>();
			try {
				conn = JdbcUtils.getConnection();
				String sql = "select *,(select title from tab_pushque where qid=tabx.que) as quename,(select count(*) from tab_pushcontentclass where code REGEXP  concat('^',tabx.code,'[0-9][0-9][0-9]-$')) as num from tab_pushcontentclass as tabx where code  REGEXP '^"+id+"[0-9][0-9][0-9]-$' order by code";
				statement = conn.prepareStatement(sql);
				result = statement.executeQuery();
				while (result.next()) {
					ContentClass item = new ContentClass();
					item.setId(result.getString("code")); 
					if (!result.getString("num").equals("0")) { item.setIconCls("icon-folder");item.setState("closed");}
					item.setClass_value(result.getString("value"));
					item.setClass_name(result.getString("name"));
					item.setClass_queid(result.getString("que"));
					item.setClass_quename(result.getString("quename"));
					item.setClass_duration(result.getString("duration").equals("0")?"":result.getString("duration"));
					if (item.getId().length()>4) item.set_parentId(item.getId().substring(0, item.getId().length() - 4));
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
	
	public List<ContentClass> GetContClasslistX() {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<ContentClass> list = new ArrayList<ContentClass>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select *,(select title from tab_pushque where qid=tab_pushcontentclass.que) as quename from tab_pushcontentclass  order by code";
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			while (result.next()) {
				ContentClass item = new ContentClass();
				item.setId(result.getString("code"));
				item.setClass_value(result.getString("value"));
				item.setClass_name(result.getString("name"));
				item.setClass_queid(result.getString("que"));
				item.setClass_quename(result.getString("quename"));
				item.setClass_duration(result.getString("duration").equals("0")?"":result.getString("duration"));
				if (item.getId().length()>4) item.set_parentId(item.getId().substring(0, item.getId().length() - 4));
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

	public void DeleteContClass(String code) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "delete from tab_pushcontentclass where code like '"+code+"%'";
			st = conn.prepareStatement(sql);
			st.executeUpdate();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				JdbcUtils.release(conn, st, null);
        }		
	}

	public void EditContClass(ContentClass item) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "update tab_pushcontentclass set value=?,name=?,que=?,duration=? where code=?";
			st = conn.prepareStatement(sql);
			st.setString(1,item.getClass_value());
			st.setString(2,item.getClass_name());
			st.setString(3,item.getClass_queid().equals("")?null:item.getClass_queid());
			st.setString(4,item.getClass_duration().equals("")?"0":item.getClass_duration());
			st.setString(5,item.getId());
			st.executeUpdate();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				JdbcUtils.release(conn, st, null);
        }		
	}

	public void AddContClass(ContentClass item) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs=null;
        try {
			conn = JdbcUtils.getConnection();
			String sql="select count(*) from tab_pushcontentclass where code REGEXP '^"+item.getId()+"[0-9][0-9][0-9]-$'";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			if (rs.next()) {
				if(rs.getInt(1)==0) sql="insert into tab_pushcontentclass(value,code,name,que,duration) values(?,'"+item.getId()+"001-',?,?,?)";
				if(rs.getInt(1)>0) sql = "insert into tab_pushcontentclass(value,code,name,que,duration) " +
						"select ?,concat('"+item.getId()+"',min(codeid)),?,?,? from tab_pushcode  where codeid not in (select right(code,4) from tab_pushcontentclass where code REGEXP '^"+item.getId()+"[0-9][0-9][0-9]-$' )";						
				st = conn.prepareStatement(sql);
				st.setString(1,item.getClass_value());
				st.setString(2,item.getClass_name());
				st.setString(3,item.getClass_queid().equals("")?null:item.getClass_queid());
				st.setString(4,item.getClass_duration().equals("")?"0":item.getClass_duration());
				st.executeUpdate();
			}
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				JdbcUtils.release(conn, st, rs);
        }		
	}

	public void AddAutoPushLog(AutoPushLog item) {
		Connection conn = null;
		PreparedStatement st = null;
        try {
			conn = JdbcUtils.getConnection();
			String sql="insert into tab_autopushlog(queid,starttime,endtime,pushtime,users) "
			+"select "+item.getQueid()+",'"+item.getStartt()+"','"+item.getEndt()+"',NOW(),GROUP_CONCAT(device_id) from tab_deviceinfo where device_imei in ("+item.getImeis()+") or device_number in ("+item.getImeis()+")";
			st = conn.prepareStatement(sql);
			st.executeUpdate();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				JdbcUtils.release(conn, st,null);
        }		
	}

	public List<AutoPushLog> GetPushItemlist(int currentPage,int pageSize) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<AutoPushLog> list = new ArrayList<AutoPushLog>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select logid,(select title from tab_pushque where qid=queid) as quename,starttime,endtime,pushtime from tab_autopushlog ORDER BY pushtime DESC limit ?,?";
			statement = conn.prepareStatement(sql);
			statement.setInt(1, (currentPage - 1) * pageSize);
			statement.setInt(2, pageSize);
			result = statement.executeQuery();
			while (result.next()) {
				AutoPushLog item = new AutoPushLog();
				item.setId(result.getString("logid"));
				item.setQueid(result.getString("quename"));
				item.setStartt(result.getString("starttime"));
				item.setEndt(result.getString("endtime"));
				item.setPusht(result.getString("pushtime"));
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

	public List<ComBox> GetPushLogUserList(int currentPage,int pageSize, String logid) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<ComBox> list = new ArrayList<ComBox>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select device_imei as id,device_number as name from tab_deviceinfo where LOCATE(CONCAT(',',device_id,','),CONCAT(',',(select users  from tab_autopushlog where logid="+logid+"),','))>0 limit ?,?";
			statement = conn.prepareStatement(sql);
			statement.setInt(1, (currentPage - 1) * pageSize);
			statement.setInt(2, pageSize);
			result = statement.executeQuery();
			while (result.next()) {
				ComBox item = new ComBox();
				item.setId(result.getString("id"));
				item.setName(result.getString("name"));				
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

	public int GetPushItemTotal() {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select count(*) from tab_autopushlog";
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

	public int GetPushLogUserTotal(String logid) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select count(*) from tab_deviceinfo where LOCATE(CONCAT(',',device_id,','),CONCAT(',',(select users  from tab_autopushlog where logid="+logid+"),','))>0";
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

	public String GetWeiDuCode(String code) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs=null;
        try {
			conn = JdbcUtils.getConnection();
			String sql="select count(*) from tab_pushcontentclass where code REGEXP '^"+code+"[0-9][0-9][0-9]-$'";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			if (rs.next()) {
				if(rs.getInt(1)==0) return code+"001";
				else if(rs.getInt(1)>0) 
				{
				   sql ="select concat('"+code+"',min(codeid)) from tab_pushcode  where codeid not in (select right(code,4) from tab_pushcontentclass where code REGEXP '^"+code+"[0-9][0-9][0-9]-$' )";
				   st = conn.prepareStatement(sql);
				   rs = st.executeQuery();
				   if (rs.next()) return rs.getString(1);
				}
			}
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				JdbcUtils.release(conn, st, rs);
        }
        return "failure";
	}
}
