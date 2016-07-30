package cn.ncut.devicedao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import cn.ncut.devicedomain.MobileModel;
import cn.ncut.devicedomain.ScorePie;
import cn.ncut.devicedomain.SlideModel;
import cn.ncut.utils.JdbcUtils;
import cn.ncut.utils.JdbcUtils02;

public class MobileDao {

	//获取设备信息
	public List<MobileModel> GetMobileList(int currentPage, int pageSize,
			String mobileQuery,String order) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<MobileModel> RecList = new ArrayList<MobileModel>();
		
		try {
			conn = JdbcUtils.getConnection();
			String sqlString="select * from tab_deviceinfo "+mobileQuery+"order by enter_time "+order+" limit ?,?";
			statement = conn.prepareStatement(sqlString);
			statement.setInt(1, (currentPage - 1) * pageSize);
			statement.setInt(2, pageSize);
			result = statement.executeQuery();
			
			while (result.next()) {
				MobileModel Rec = new MobileModel();
				
				
				String imei=result.getString("device_imei");
				
				Rec.setUpdate_key(result.getInt("update_key"));
				
				Rec.setImei(imei);
				
				Rec.setImsi(result.getString("device_imsi"));
				
				Rec.setMbno(result.getString("device_number"));
				
				
				Rec.setCompany(getunitorseller(result.getInt("user_unit")));
				
				Rec.setSeller(getunitorseller(result.getInt("device_seller")));
				
				
				Rec.setGender(result.getInt("user_sex")+"");
				
				//根据出生年份算用户年龄
				if (result.getString("user_birth")!=null&&result.getString("user_birth").length()>=4) {
					 String birth=result.getString("user_birth").substring(0, 4);
					 Date date=new Date();
					 int nowyear=date.getYear()+1900;
				     Rec.setBirth((nowyear-Integer.parseInt(birth))+"");
				   }else {
					  Rec.setBirth("");
				}
			
			    Rec.setMbos(getOsVserion(result.getInt("device_os_version")+""));
				
				
				
			   Rec.setArea(getMbarea(result.getString("mobile_area")));
			   
			  
			   Rec.setProvider( getMbtype(result.getString("mobile_type")));
				
				
			 
			   
				Rec.setMbtype(getDeviceType(result.getString("device_type")));
				
				int depid=result.getInt("seller_depid");
				
				if (depid==0) {
					Rec.setSeller_depid("");
				}else {
					Rec.setSeller_depid(result.getInt("seller_depid")+"");
				}
				
				
				int empid=result.getInt("seller_empid");
				
				if (empid==0) {
					Rec.setSeller_stuffid("");
				}else {
					Rec.setSeller_stuffid(result.getInt("seller_empid")+"");
				}
				
				
				 String entertime=result.getString("enter_time");
				
				if (entertime!=null&&entertime.length()>=19) {
					Rec.setRegtime(entertime.substring(0, 19));
					
				}
				
				
				
				
				
				Rec.setRecque(getRecque(imei));
				
				
				Rec.setTheme(result.getInt("app_mode"));
				
				
				Rec.setThemestatus(result.getInt("app_modechanged_status"));
				
				
				RecList.add(Rec);
			}
			return RecList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, result);
		}
		return null;
	}

	/**
	 * @param imei
	 * 返回序列名称
	 */
	public String getRecque(String imei) {
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			
			//String sqlString="select recstatus from tab_pushinfo where imei=?";
			String sqlString="select queid from tab_pushinfo where imei=?";
			st = conn.prepareStatement(sqlString);
			st.setString(1, imei);
			rs = st.executeQuery();
			
			if (rs.next()) {
				
				
				String rec=rs.getString("queid");
				
				if (rec!=null&&!"".equals(rec.trim())) {
					
					
					String [] strs=rec.split("&");
					
					if (strs[0].startsWith("a")) {
						
						return getRecque01(strs[0].substring(1));
						
					}else {
						
						
						return getRecque02(strs[0]);
					}
					
				}
				
				
			 }
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
	    return "默认序列";
	
	}

	/**
	 * @param string
	 * @return
	 */
	public String getRecque02(String string) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			
			String sqlString="select title from tab_pushque where qid=(select queid from tab_pushlist where pid="+string+")";
			st = conn.prepareStatement(sqlString);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getString("title");
			 }
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
	
		return "默认序列";
	}

	/**
	 * @param substring
	 * @return
	 */
	public String getRecque01(String substring) {
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			
			String sqlString="select title from tab_pushque where qid="+substring;
			st = conn.prepareStatement(sqlString);
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getString("title");
			 }
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
	
		return "默认序列";
	}

	/**
	 * @param string
	 * 获取手机类型
	 */
	public String getDeviceType(String str) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			
			String sqlString="select name from tab_pushmbtype where id=?";
			st = conn.prepareStatement(sqlString);
			st.setString(1, str);
			rs = st.executeQuery();
			
			if (rs.next()) {
				return rs.getString("name");
			 }
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		
		
		return "";
		
	}

	/**
	 * @param string
	 * 获取手机卡类型
	 */
	public String getMbtype(String str) {
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			
			String sqlString="select name from tab_pushprovider where id=?";
			st = conn.prepareStatement(sqlString);
			st.setString(1, str);
			rs = st.executeQuery();
			
			if (rs.next()) {
				return rs.getString("name");
			 }
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		
		
		return "";
	}

	/**
	 * @param string
	 * 获取归属地
	 */
	public String getMbarea(String str) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			
			String sqlString="select name from tab_pusharea where id=?";
			st = conn.prepareStatement(sqlString);
			st.setString(1, str);
			rs = st.executeQuery();
			
			if (rs.next()) {
				return rs.getString("name");
			 }
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		
		
		return "";
	}

	/**
	 *获取操作系统版本号
	 */
	public String getOsVserion(String str) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			
			String sqlString="select name from tab_pushmbos where id=?";
			st = conn.prepareStatement(sqlString);
			st.setString(1, str);
			rs = st.executeQuery();
			
			if (rs.next()) {
				return rs.getString("name");
			 }
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		
		return "";
	}

	/**
	 * @param int1
	 * 根据编号获取归属单位或者销售单位名称
	 */
	public String  getunitorseller(int id) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			
			String sqlString="select com_name from tab_company where com_no=?";
			st = conn.prepareStatement(sqlString);
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (rs.next()) {
				return rs.getString("com_name");
			 }
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		
		return "";
		
	}

	public int GetMobileTotal(String mobileQuery) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			
			String sqlString="select count(device_id) from tab_deviceinfo "+mobileQuery;
			st = conn.prepareStatement(sqlString);
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
	
	/*
	 * 获取滑屏信息记录
	 */
	public List<SlideModel> GetSlideList(int offset, int pageSize,
			String slideQuery,String tablename,String order) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		List<SlideModel> RecList = new ArrayList<SlideModel>();
		if (tablename == null || "".equals(tablename)) {

			return RecList;
		}
		try {	
		conn = JdbcUtils02.getConnection();
		String sqlString="select * from "+tablename+slideQuery+"  order by slide_time "+order+" limit ?,?";
		statement = conn.prepareStatement(sqlString);
		statement.setInt(1, offset);
		statement.setInt(2, pageSize);
		result = statement.executeQuery();
		
		while (result.next()) {
			SlideModel Rec = new SlideModel();
			String imei=result.getString("device_imei");
			
			Rec.setImei(imei);
			
			
			MobileModel mobileModel=getdevicebyimei(imei);
			
			Rec.setImsi(mobileModel.getImsi());
			
			Rec.setMbno(result.getString("device_number"));
			
			
			try {
				Rec.setSeller(getunitorseller(Integer.parseInt(mobileModel.getSeller())));
				
				Rec.setCompany(getunitorseller(Integer.parseInt(mobileModel.getCompany())));
			} catch (NumberFormatException e) {
				
				e.printStackTrace();
			}
			
			
		/*	Rec.setGender(result.getInt("user_sex")+"");
			
			//根据出生年份算用户年龄
			if (result.getString("user_birth")!=null&&result.getString("user_birth").length()>=4) {
				 String birth=result.getString("user_birth").substring(0, 4);
				 Date date=new Date();
				 int nowyear=date.getYear()+1900;
			     Rec.setBirth((nowyear-Integer.parseInt(birth))+"");
			   }else {
				  Rec.setBirth("");
			}*/
		
		   Rec.setMbos(getOsVserion(mobileModel.getMbos()));
			
			
			
		   Rec.setArea(getMbarea(mobileModel.getArea()));
		   
		  
		   Rec.setProvider( getMbtype(mobileModel.getProvider()));
			
			
		 
		   
			Rec.setMbtype(getDeviceType(mobileModel.getMbtype()));
			
			
			
		
			
			 String slidetime=result.getString("slide_time");
			
			if (slidetime!=null&&slidetime.length()>=19) {
				Rec.setSlidetime(slidetime.substring(0, 19));
				
			}
		    Rec.setPicname(result.getString("pic_name"));
			Rec.setPictitle(result.getString("pic_title"));
			Rec.setScore(result.getInt("pic_score"));
		
			RecList.add(Rec);
		}
		return RecList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, result);
		}
		return null;
	}

	public int GetSlideTotal(String slideQuery,String tablename) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		if (tablename == null || "".equals(tablename)) {

			return 0;
		}
		try {
			conn = JdbcUtils02.getConnection();
			String sql="select count(*) from "+ tablename +slideQuery;
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

	public void InsertThmChgLog(String imei, int theme, String userid) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
				conn = JdbcUtils.getConnection();
				String sql = "Call InsertThmChgLogBySingle(?,?,?)";
				st = conn.prepareStatement(sql);
				st.setString(1,imei);
				st.setInt(2,theme);
				st.setString(3,userid);			
				st.executeUpdate();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				JdbcUtils.release(conn, st, null);
        }	
	}

	/**
	 * @param id
	 * @param unit
	 * @param company
	 * 更换模式
	 */
	public void update(String id, String unit, String company,String theme,String themestatus,String userid) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
				conn = JdbcUtils.getConnection();
				String sql = "update tab_deviceinfo set user_unit=? ,device_seller=?,app_mode=?,app_modechanged_status=? ,mode_change_type=1 where device_imei=?";					
				st = conn.prepareStatement(sql);
				st.setInt(1,Integer.parseInt(unit));
				st.setInt(2,Integer.parseInt(company));
				int oldtheme=getthemebyid(id);
				
				
				try {
					st.setInt(3,Integer.parseInt(theme));
					if (Integer.parseInt(theme)!=oldtheme) {
						themestatus="0";
						
						//记录更新日志
						InsertThmChgLog(id,Integer.parseInt(theme),userid);
					}
					
				} catch (NumberFormatException e) {
					st.setInt(3,0);
				}
				try {
					st.setInt(4,Integer.parseInt(themestatus));
				} catch (NumberFormatException e) {
					st.setInt(4,0);
				}
			st.setString(5,id);
			st.executeUpdate();
			
			//及时把序列推送给手机
			st = conn.prepareStatement("update tab_pushinfo set curque='fyy' where imei=?");
			st.setString(1,id);
			st.executeUpdate();
			
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				JdbcUtils.release(conn, st, null);
        }	
		
	}

	

	/**
	 * @param id
	 */
	public int  getthemebyid(String id) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
				conn = JdbcUtils.getConnection();
				String sql = "select app_mode from tab_deviceinfo  where device_imei=?";
				st = conn.prepareStatement(sql);
				st.setString(1, id);
				
				rs =st.executeQuery();
				while (rs.next()) {
					
					return rs.getInt("app_mode");
				}
				
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				JdbcUtils.release(conn, st, null);
        }	
		return -1;
	}

	/**
	 * @param deviceimei
	 * @return
	 */
	public MobileModel getdevicebyimei(String imei) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = JdbcUtils.getConnection();
			String sqlString="select * from tab_deviceinfo  where device_imei=?";
			statement = conn.prepareStatement(sqlString);
			statement.setString(1,imei);
			
			result = statement.executeQuery();
			
			MobileModel Rec = new MobileModel();
			
			while (result.next()) {
				
			    Rec.setImei(imei);
				Rec.setImsi(result.getString("device_imsi"));
				Rec.setMbno(result.getString("device_number"));
				Rec.setCompany(result.getInt("user_unit")+"");
				Rec.setSeller(result.getInt("device_seller")+"");
			
				Rec.setGender(result.getInt("user_sex")+"");
				
			   Rec.setBirth(result.getString("user_birth"));
			
			    Rec.setMbos(result.getInt("device_os_version")+"");
			
			   Rec.setArea(result.getString("mobile_area"));
			 
			   Rec.setProvider( result.getString("mobile_type"));
			   Rec.setMbtype(result.getString("device_type"));
				
			
			}
			
			return Rec;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, result);
		}
		
		return null;
		
		
	}

	/**
	 * @param picname
	 * @return
	 */
	public String[] getpicscore(String picname) {
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String []strs=new String[2];
		try {
			conn = JdbcUtils.getConnection();
			
			String sql = "select sort,title from tab_pushcontent where namex=?";
			
			st = conn.prepareStatement(sql);
			st.setString(1, picname);

			rs = st.executeQuery();
			
			

			if (rs.next()) {
				
				strs[0]=rs.getString("sort");
				strs[1]=rs.getString("title");
		   }
           return strs;
		} catch (Exception e) {

			throw new RuntimeException(e);
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		
	}

	/**
	 * @param company
	 * @return
	 */
	public List<String> getImeisbyunitno(String company) {
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List<String> imeis=new ArrayList<String>();
	
		try {
			conn = JdbcUtils.getConnection();
			
			String sql = "select device_imei from tab_deviceinfo where user_unit=? or device_seller=?";
			
			st = conn.prepareStatement(sql);
			st.setString(1, company);
			st.setString(2, company);

			rs = st.executeQuery();
			
		  while(rs.next()) {
				imeis.add(rs.getString("device_imei"));
		     }
           return imeis;
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		
		
		return null;
	}

	/**
	 * @param begintime
	 * @param endtime
	 * @return
	 */
	public ScorePie getscorepie(String begintime, String endtime) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			
			String sql = "SELECT sum(score) as total ,device_number as num  from tab_wxscoreintrade  where  '"+begintime+"'<=start_date and start_date<='"+endtime+"' and  status=1 or status=0  group by num;";
			
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			
			  /*| total | num         |
			
			  | 842   | 12345123451 |
			  | 593   | 12345678912 |
			 */
		int count500=0,count1000=0,count1500=0,count2000=0,count2500=0,count3000=0,countup3000=0,count=0;
		
		  while(rs.next()) {
			
			String number=rs.getString("num");
			
			 String unitno=getunitnobyphonenum(number);
			  
			if (!"100002".equals(unitno)) {
				
				 continue;
		        } 
			 
			 int total=rs.getInt("total");
			 
			 count++;
			 
			 
			 
			 if (total<=500) {
				 
				count500++;
			}
			 
			 if (500<total&&total<=1000) {
				
				  count1000++;
			  }
			 
			 if (1000<total&&total<=1500) {
				
				 count1500++;
			}
			  
			 if (1500<total&&total<=2000) {
					
				 count2000++;
			 }
			  
			 
			 if (2000<total&&total<=2500) {
					
				 count2500++;
			}
			 
			 
			 if (2500<total&&total<=3000) {
					
				 count3000++;
			}
			 if (total>3000) {
			
				 countup3000++;
			}
			 
			 
			 
		     }
          
		  return new ScorePie(count500, count1000, count1500, count2000, count2500, count3000, countup3000, count);
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, st, rs);
		}
		return null;
	}

	/**
	 * @param number
	 * @return
	 */
	private String getunitnobyphonenum(String number) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		
		if (number==null||"".equals(number)) {
			
			return null;
		}
		try {
			conn = JdbcUtils.getConnection();
			String sqlString="select user_unit from tab_deviceinfo  where device_number=?";
			statement = conn.prepareStatement(sqlString);
			statement.setString(1,number);
			
			result = statement.executeQuery();
	    	while (result.next()) {
				
			  
				return result.getString("user_unit");
			
			}
			
		
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, result);
		}
		
		return null;
	}

}
