package cn.ncut.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ncut.devicedomain.MobileModel;
import cn.ncut.devicedomain.SlideModel;
import cn.ncut.utils.JdbcUtils;
import cn.ncut.utils.JdbcUtils02;
import cn.ncut.utils.JdbcUtils03;

/**
 * @author wzq
 *
 *version 1.0 2015-5-28 下午10:23:05
 */
public class SlideInfo {
        public static void main(String[] args) {
			
        	 Connection conn = null;
      		PreparedStatement st = null;
      		ResultSet rs = null;
      		
      		
      		List<SlideModel> SlideModels=new ArrayList<SlideModel>();
      		
      		
      		try {
      			conn = JdbcUtils03.getConnection();
      			String sql = "select slidetime,picname,pictitle,imei,sort from tab_slideinfo_201505 where slidetime>'2015-05-30 16:37:03'";
      			st = conn.prepareStatement(sql);
      			
      			rs = st.executeQuery();

      			while(rs.next()) {
      				
      				SlideModel slb=new SlideModel();
      				
      				slb.setSlidetime(rs.getString("slidetime"));
      			     slb.setPicname(rs.getString("picname"));
      				
      				slb.setPictitle(rs.getString("pictitle"));
      				slb.setImei(rs.getString("imei"));
      				slb.setScore(rs.getInt("sort"));
      				SlideModels.add(slb);
      				
                  
      			}

      		} catch (Exception e) {

      			e.printStackTrace();
      		} finally {
      			JdbcUtils.release(conn, st, rs);
      		}
      		
      		int i=65792;
      		
      		for (SlideModel slideModel : SlideModels) {
      			
      			try {
					
				   MobileModel mbModel=getmbmodel(slideModel.getImei());
				   
				
		  conn = JdbcUtils02.getConnection();
			String sql = "insert into tab_slideinfo_201505 values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			st = conn.prepareStatement(sql);
		   	
					System.out.println(i);
					st.setInt(1, i++);
					st.setString(2, mbModel.getImei());
					st.setString(3, mbModel.getMbno());
					st.setString(4, slideModel.getPictitle());
					st.setString(5, slideModel.getPicname());
					st.setString(6, slideModel.getSlidetime());
					try {
						st.setInt(7, Integer.parseInt(mbModel.getSeller()));
					} catch (NumberFormatException e) {
						st.setInt(7, 0);
					}
					try {
						st.setInt(8,Integer.parseInt(mbModel.getSeller()));
					} catch (NumberFormatException e) {
						st.setInt(8, 0);
					}
					try {
						st.setInt(9, Integer.parseInt(mbModel.getGender()));
					} catch (NumberFormatException e) {
						st.setInt(9, 0);
					}
					st.setString(10, mbModel.getBirth());
					st.setString(11, mbModel.getMbos());
					st.setString(12, mbModel.getArea());
					st.setString(13, mbModel.getProvider());
					st.setString(14, mbModel.getMbtype());
					st.setString(15, mbModel.getImsi());
					st.setInt(16,slideModel.getScore());
					
					st.executeUpdate();
				
					
				} catch (SQLException e) {
					
					e.printStackTrace();
				}finally{
					
					
					JdbcUtils.release(conn, st, null);
				}
      			
				
			}
      		
        	
        	
        	
		}

		/**
		 * @param imei
		 * @return
		 */
		private static MobileModel getmbmodel(String imei) {
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
	   
}
