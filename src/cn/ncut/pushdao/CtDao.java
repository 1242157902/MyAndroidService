package cn.ncut.pushdao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.ncut.pushdomain.PushContent;
import cn.ncut.syssetdao.UserManagerDao;
import cn.ncut.utils.JdbcUtils;

public class CtDao {
	UserManagerDao userdao=new UserManagerDao();
	public List<PushContent> GetContentList(int currentPage, int pageSize,String oper) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<PushContent> CtList = new ArrayList<PushContent>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select cid,sort,classify,classifyName,title,operator,namex,size,operdate,link,scolor, func_iconstr(icon)  as icon from view_pushcontent "+oper+" ORDER BY operdate DESC limit ?,?";
			statement = conn.prepareStatement(sql);
			statement.setInt(1, (currentPage - 1) * pageSize);
			statement.setInt(2, pageSize);
			result = statement.executeQuery();
			while (result.next()) {
				PushContent Content = new PushContent();
				Content.setCid(result.getInt("cid"));
				Content.setSort(result.getInt("sort"));
				Content.setClassify(result.getString("classify"));
				Content.setClassifyName(result.getString("classifyName"));
				Content.setTitle(result.getString("title"));
				Content.setOperator(userdao.finduserbyid(result.getString("operator")).getUsername());
				Content.setNamex(result.getString("namex"));
				Content.setSize(result.getInt("size"));
				Content.setOperdate(result.getString("operdate"));
				Content.setLink(result.getString("link"));
				Content.setIconstr(result.getString("icon"));
				Content.setScolor(result.getString("scolor"));
				CtList.add(Content);
			}
			return CtList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, null);
		}
		return null;
	}

	public int GetCtTotal(String oper) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select count(*) from view_pushcontent "+oper;
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

	public void AddContent(PushContent newCt) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement st = null;
		try {
				conn = JdbcUtils.getConnection();
				String sql = "Call InsertPushContent(?,?,?,?,?,?,?,?,?,?)";
				st = conn.prepareStatement(sql);
				st.setString(1, newCt.getTitle());
				st.setInt(2, newCt.getSort());
				st.setString(3, newCt.getOperator());
				st.setString(4, newCt.getNamex());
				st.setInt(5, newCt.getSize());
				st.setString(6, newCt.getOperdate());
				st.setString(7, newCt.getLink());
				st.setString(8, newCt.getIconstr());
				st.setString(9, newCt.getScolor());
				st.setString(10, newCt.getClassify());
				st.executeUpdate();
			} catch (SQLException e) {
					e.printStackTrace();
			} finally {
					JdbcUtils.release(conn, st, null);
            }		
	}

	public void DeleteContent(int cid) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement st = null;
		try {
				conn = JdbcUtils.getConnection();
				//String sql = "delete from tab_pushcontent where cid=?";
				String sql = "update tab_pushcontent set flag='0'  where cid=?";
				st = conn.prepareStatement(sql);
				st.setInt(1,cid);
				st.executeUpdate();
			} catch (SQLException e) {
					e.printStackTrace();
			} finally {
					JdbcUtils.release(conn, st, null);
            }		
	}

	public void UpdateContent(PushContent theCt) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
				conn = JdbcUtils.getConnection();
				String sql = "UPDATE tab_pushcontent SET title='"+theCt.getTitle()+"',sort="+theCt.getSort()+",operator="+theCt.getOperator()+",namex='"+theCt.getNamex()+"',size="+theCt.getSize()+",operdate=now(),link='"+theCt.getLink()+"',icon='"+ theCt.getIconstr()+"',scolor='"+theCt.getScolor()+"',classify='"+theCt.getClassify()+"' WHERE cid="+ theCt.getCid();
				st = conn.prepareStatement(sql);
				st.executeUpdate();
			} catch (SQLException e) {
					e.printStackTrace();
			} finally {
					JdbcUtils.release(conn, st, null);
            }		
	}

	public String GetContentName(int cid) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select namex from tab_pushcontent where cid=?";			
			st = conn.prepareStatement(sql);
			st.setInt(1,cid);
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

	public int CheckCtInAtom(int cid) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select count(*) from tab_pushatom where cid=?";
			st = conn.prepareStatement(sql);
			st.setInt(1,cid);
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
