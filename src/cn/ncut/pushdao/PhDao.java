package cn.ncut.pushdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.ncut.pushdomain.ComDefQue;
import cn.ncut.pushdomain.PushAccepter;
import cn.ncut.pushdomain.PushItem;
import cn.ncut.pushdomain.PushQueTitle;
import cn.ncut.utils.JdbcUtils;

public class PhDao {
	public List<PushItem> GetPushList(int currentPage, int pageSize, String oper) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<PushItem> PhList = new ArrayList<PushItem>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select * from view_pushlist "+oper+" ORDER BY pid DESC limit ?,?";
			statement = conn.prepareStatement(sql);
			statement.setInt(1, (currentPage - 1) * pageSize);
			statement.setInt(2, pageSize);
			result = statement.executeQuery();
			while (result.next()) {
				PushItem PhItem = new PushItem();
				PhItem.setPid(result.getInt("pid"));
				PhItem.setTitle(result.getString("title"));
				PhItem.setQueid(result.getInt("queid"));
				PhItem.setPtype(result.getInt("ptype"));
				PhItem.setPushtime(result.getString("pushtime"));
				PhItem.setPriori(result.getInt("priori"));
				PhItem.setEnddate(result.getString("enddate"));
				PhItem.setOper(GetPushQueTitle(result.getInt("queid")));
				PhList.add(PhItem);
			}
			return PhList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, result);
		}
		return null;
	}

	public int GetPushTotal(String oper) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select count(*) from view_pushlist "+oper;
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		return 0;
	}

	public List<PushQueTitle> GetPushQueTitleList(String operId) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<PushQueTitle> PTList = new ArrayList<PushQueTitle>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select qid,title from tab_pushque where oper=?";
			statement = conn.prepareStatement(sql);
			statement.setString(1,operId);
			result = statement.executeQuery();
			while (result.next()) {
				PushQueTitle PTItem = new PushQueTitle();
				PTItem.setQid(result.getInt("qid"));
				PTItem.setTitle(result.getString("title"));
				PTList.add(PTItem);
			}
			return PTList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, null);
		}
		return null;
	}
	
	public String GetPushQueTitle(int QueId) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select title from tab_pushque  where qid=?";
			st = conn.prepareStatement(sql);
			st.setInt(1,QueId);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		return "";
	}

	public void AddPushItem(PushItem phitem) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
				conn = JdbcUtils.getConnection();
				String sql = "Call InsertPushItem(?,?,?,?,?,?,?)";
				st = conn.prepareStatement(sql);
				st.setString(1, phitem.getTitle());
				st.setInt(2, phitem.getQueid());
				st.setInt(3, phitem.getPtype());
				st.setString(4, phitem.getPushtime());
				st.setInt(5, phitem.getPriori());
				st.setString(6, phitem.getEnddate());
				st.setString(7, phitem.getOper());
				st.executeUpdate();
			} catch (SQLException e) {
					e.printStackTrace();
			} finally {
					JdbcUtils.release(conn, st, null);
            }	
	}

	public void UpdatePushItem(PushItem phitem) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
				conn = JdbcUtils.getConnection();
				String sql = "UPDATE tab_pushlist SET title=?,queid=?,ptype=?,pushtime=?,priori=?,enddate=? WHERE pid=?";
				st = conn.prepareStatement(sql);
				st.setString(1, phitem.getTitle());
				st.setInt(2, phitem.getQueid());
				st.setInt(3, phitem.getPtype());
				st.setString(4, phitem.getPushtime());
				st.setInt(5, phitem.getPriori());
				st.setString(6, phitem.getEnddate());
				st.setInt(7, phitem.getPid());
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
			statement = conn.prepareStatement("select imei,allque,curque,recstatus,mbno,EnCompany(company) as company,EnCompany(seller) as seller,gender,birth,EnMbOs(mbos) as mbos,EnMbArea(area) as area,EnMbProvider(provider) as provider,EnMbType(mbtype) as mbtype ,duty from view_deviceinfo "+qrcsql+"limit ?,?");
			statement.setInt(1, (currentPage - 1) * pageSize);
			statement.setInt(2, pageSize);
			result = statement.executeQuery();
			while (result.next()) {
				PushAccepter Rec = new PushAccepter();
				Rec.setImei(result.getString("imei"));
				
				String str = result.getString("allque");
				if(str==null) str="";
				boolean  b = str.matches("(((.*),"+ptlid+",(.*))|("+ptlid+",(.*)))");
				if(b) Rec.setAllque("1");
				else Rec.setAllque("0");
				
				Rec.setCurque(result.getString("curque"));
				
				str = result.getString("recstatus");
				if(str==null) str="";
				b = str.matches(ptlid+"&1");
				if(b) Rec.setRecstatus("1");
				else Rec.setRecstatus("0");
				
				Rec.setMbno(result.getString("mbno"));
				Rec.setCompany(result.getString("company"));
				Rec.setSeller(result.getString("seller"));
				Rec.setGender(result.getInt("gender"));
				Rec.setBirth(result.getString("birth"));
				Rec.setMbos(result.getString("mbos"));
				Rec.setArea(result.getString("area"));
				Rec.setProvider(result.getString("provider"));
				Rec.setMbtype(result.getString("mbtype"));
				Rec.setPosition(result.getString("duty"));
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
			st = conn.prepareStatement("select count(*) from view_deviceinfo "+qrcsql);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		return 0;
	}

	public void SingleOkPush(String imei, String pid,String operId) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
				conn = JdbcUtils.getConnection();
				String sql = "update tab_pushinfo set allque=CONCAT(IFNULL(allque,''),'"+pid+",') where imei='"+imei+"'";
				st = conn.prepareStatement(sql);
				st.executeUpdate();
				InsertPushLog(1,imei+",",pid,operId);
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
				String sql = "update tab_pushinfo set allque=SUBSTRING(REPLACE(CONCAT(',',allque), ',"+pid+",', ','),2) where imei='"+imei+"'";
				st = conn.prepareStatement(sql);
				st.executeUpdate();
				InsertPushLog(2,imei+",",pid,operId);
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
				String sql = "update tab_pushinfo set allque=CONCAT(IFNULL(allque,''),'"+pid+",') where imei in "+imeis;
				st = conn.prepareStatement(sql);
				st.executeUpdate();
				InsertPushLog(1,imeis.substring(1,imeis.length()-1)+",",pid,operId);
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
				String sql = "update tab_pushinfo set allque=SUBSTRING(REPLACE(CONCAT(',',allque), ',"+pid+",', ','),2) where imei in "+imeis;
				st = conn.prepareStatement(sql);
				st.executeUpdate();
				InsertPushLog(2,imeis.substring(1,imeis.length()-1)+",",pid,operId);
			} catch (SQLException e) {
					e.printStackTrace();
			} finally {
					JdbcUtils.release(conn, st, null);
            }	
	}

	public void DeletePushItemInDetail(int pid) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
				conn = JdbcUtils.getConnection();
				String sql = "update tab_pushinfo set allque=SUBSTRING(REPLACE(CONCAT(',',allque), ',"+pid+",', ','),2) , recstatus=(CASE WHEN SUBSTRING_INDEX(recstatus,'&',1)="+pid+" THEN null  ELSE recstatus END) ";
				st = conn.prepareStatement(sql);
				st.executeUpdate();
			} catch (SQLException e) {
					e.printStackTrace();
			} finally {
					JdbcUtils.release(conn, st, null);
            }	
	}

	public void DeletePushItemInList(int pid) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
				conn = JdbcUtils.getConnection();
				//String sql = "delete from tab_pushlist where pid=?";
				String sql = "update tab_pushlist set flag='0'  where pid=?";
				st = conn.prepareStatement(sql);
				st.setInt(1,pid);
				st.executeUpdate();
			} catch (SQLException e) {
					e.printStackTrace();
			} finally {
					JdbcUtils.release(conn, st, null);
            }		
	}

	public void UpdateRecStatusCt(int cid) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
				conn = JdbcUtils.getConnection();
				String sql = "Call UpdateRecFmCt(?)";
				st = conn.prepareStatement(sql);
				st.setInt(1,cid);
				st.executeUpdate();
			} catch (SQLException e) {
					e.printStackTrace();
			} finally {
					JdbcUtils.release(conn, st, null);
            }	
	}

	public void UpdateRecStatusQue(int qid) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
				conn = JdbcUtils.getConnection();
				String sql = "Call UpdateRecFmQue(?)";
				st = conn.prepareStatement(sql);
				st.setInt(1,qid);
				st.executeUpdate();
			} catch (SQLException e) {
					e.printStackTrace();
			} finally {
					JdbcUtils.release(conn, st, null);
            }	
	}

	public void UpdateRecStatusPh(int pid) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
				conn = JdbcUtils.getConnection();
				String sql = "Call UpdateRecFmPh(?)";
				st = conn.prepareStatement(sql);
				st.setInt(1,pid);
				st.executeUpdate();
			} catch (SQLException e) {
					e.printStackTrace();
			} finally {
					JdbcUtils.release(conn, st, null);
            }	
	}
	public void ResetCurQue(String imei) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "update tab_pushinfo set curque='' where imei='"+imei+"'";
			st = conn.prepareStatement(sql);
			st.executeUpdate();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				JdbcUtils.release(conn, st, null);
        }		
	}
	public String GetPushString(String imei) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "Call PushString(?)";
		    st = conn.prepareStatement(sql);
			st.setString(1,imei);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		return "";
	}
	public String GetDefalutString(String queid) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select defaultque("+queid+")";
		    st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		return "";
	}
	public String GetTotalScore(String imei) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			/*conn = JdbcUtils.getConnection();
			String sql = "select tsort from tab_pushdevice where imei=?";
		    st = conn.prepareStatement(sql);
			st.setString(1,imei);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getInt(1)+"";
			}*/
			return "0";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		return "0";
	}
	
	public String getCompany(String comno) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select com_name from tab_company  where com_no=?";
			st = conn.prepareStatement(sql);
			st.setString(1,comno);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		return "";
	}

	public List<ComDefQue> GetComDefQueList(String comno) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<ComDefQue> PTList = new ArrayList<ComDefQue>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "Call ComQueList(?)";
			statement = conn.prepareStatement(sql);
			statement.setInt(1,Integer.parseInt(comno));
			result = statement.executeQuery();
			while (result.next()) {
				ComDefQue PTItem = new ComDefQue();
				PTItem.setCom_no(result.getInt("com_no"));
				PTItem.setCom_name(result.getString("com_name"));
				PTItem.setQid(result.getInt("qid"));
				PTItem.setTitle(result.getString("title"));
				PTList.add(PTItem);
			}
			return PTList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, null);
		}
		return null;
	}

	public String GetIconStr(String str) {
        String pics_url="";
        String pics_icon="";
    	Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select namex,link,CONCAT('[',func_iconname(icon),']') as iconname,CONCAT('[',func_iconurl(icon),']') as iconurl,INSTR('"+str+"',namex) as ssort from tab_pushcontent where INSTR('"+str+"',namex)>0 order by ssort";
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			while (result.next()) {
				pics_icon=pics_icon+","+result.getString("iconname");
				if("[]".equals(result.getString("iconname")))  pics_url=pics_url+",[\""+result.getString("link")+"\"]";
				else pics_url=pics_url+","+result.getString("iconurl");
			}
			if("".equals(pics_url)||"".equals(pics_icon))  return "pic_icons:[],pics_url:[]";
			else return "pic_icons:["+pics_icon.substring(1)+"],pics_url:["+pics_url.substring(1)+"]";
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, null);
		}
		return "pic_icons:[],pics_url:[]";
	}

	public void InsertPushLog(int oper,String imei, String pid,String operId) {
		imei=imei.replace("'","");
		Connection conn = null;
		PreparedStatement st = null;
		try {
				conn = JdbcUtils.getConnection();
				String sql = "Call InsertPushLog(?,?,?,?)";
				st = conn.prepareStatement(sql);
				st.setInt(1,oper);
				st.setString(2,imei);
				st.setInt(3,Integer.parseInt(pid));
				st.setString(4,operId);
				st.executeUpdate();
			} catch (SQLException e) {
					e.printStackTrace();
			} finally {
				JdbcUtils.release(conn, st, null);
			}
	}
	
	
	public int GetErrorId(String err) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			st = conn.prepareStatement("select id from tab_pusherror where info='"+err+"'");
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		} catch (Exception e) {
		   e.printStackTrace();
		}finally {
			JdbcUtils.release(conn, st,rs);
		}
		return -1;	
	}

	public void InsertError(String errid) {	
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "Insert tab_pusherror(info) values('"+errid+"')";
			st = conn.prepareStatement(sql);	
			st.executeUpdate();
		} catch (SQLException e) {
				e.printStackTrace();
		}finally {
			JdbcUtils.release(conn, st, null);
		}
	}

	public void InsertErrorLog(int id, String errtxt, String errmethod,
			String phoneimei, String json, String picstring) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "Insert tab_pusherrorlog(errid,errtxt,errmethod,imei,inmsg,outmsg,time) values(?,?,?,?,?,?,now())";
			st = conn.prepareStatement(sql);
			st.setInt(1,id);
			st.setString(2,errtxt);
			st.setString(3,errmethod);
			st.setString(4,phoneimei);
			st.setString(5,json);
			st.setString(6,picstring);
			st.executeUpdate();
		} catch (SQLException e) {
				e.printStackTrace();
		}finally {
			JdbcUtils.release(conn, st, null);
		}
	}

	public void ClientRecInfo(String imei, String sequence, String rec_time) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			if(imei!=null&&sequence.toString().length()>=3){
				conn = JdbcUtils.getConnection();
				String a="";
				if(sequence.charAt(0)=='a') a="a";
				if(sequence.charAt(1)=='_') a=a+sequence.substring(2);
				String sql = "update tab_pushinfo set queid='"+a+"' where imei='"+imei+"'";
				st = conn.prepareStatement(sql);
				st.executeUpdate();
			}
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				JdbcUtils.release(conn, st, null);
        }		
	}

}
