package cn.ncut.autotask.countProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import cn.ncut.autotask.utils.DateUtil;
import cn.ncut.autotask.utils.JdbcUtils;

import cn.ncut.utils.JdbcUtils02;
import cn.ncut.wxdomain.Prefer;

/**
 * 统计浏览的图片
 * @author hjf
 *
 */
@Slf4j
public class CountPicPrefer {
	/**
	 * 计算图片浏览次数
	 * @param conn2 
	 * */
	private static List<Prefer> convent(String device_number,String tabName,String timeString, Connection conn){
		 
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null; 
		List<Prefer> list=new ArrayList();
		String startTime=timeString+" 00:00:00";
		String endTime=timeString+" 23:59:59"; 
		sql="SELECT count(pic_name) pic_nums,pic_name,pic_score  FROM " +
				tabName+" WHERE device_number='"
				+device_number
				+"'and slide_time like '"
				+timeString
				+"%' GROUP BY (pic_name)";
		try { 
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			int pic_num=0;//每张图片出现的次数
			String pic_name="";//图片的名称 
			String pic_classify=null;;//每张图片对应的分类
			while(rs.next()){
				pic_classify=pic_classify(rs.getString("pic_name")).substring(0,9); 
				if(pic_classify!=null&&pic_classify.length()!=0){
					pic_num=rs.getInt("pic_nums");
					Prefer pr=isExsit(list,pic_classify);
					if(pr!=null){
						pr.setScan_num(pic_num+pr.getScan_num());
					}else{
						Prefer p=new Prefer();
					pic_num=rs.getInt("pic_nums");
					p.setDevice_number(device_number);
					p.setDate(endTime.substring(0,10));
					p.setClassify(pic_classify);
					p.setScan_num(pic_num);
					list.add(p);
					}	
				}
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("计算图片次数异常"+e);
		}
		return null;
		
	}

	public static Prefer isExsit(List<Prefer> list, 
			String pic_classify) {
		for(Prefer pr:list){
			if(pr.getClassify().equals(pic_classify)){
				return pr;
			}
		}
		return null;
		
	}
	
	/**
	 * 
	 * 图片对应的分类
	 * @param conn 
	 * */
	private static String pic_classify(String pic){
		Connection conn=null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null;
		String classify=null;
		sql="SELECT classify FROM `tab_pushcontent` where namex='"+pic+"'"; 
		try {
			conn=new JdbcUtils().getConnection();
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			if(rs.next()){
				classify=rs.getString("classify");
			}
			if(classify==null)
				return "003005002";
			else
				return classify.substring(0,9);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("统计图片分类异常"+e);
		}finally{
			JdbcUtils02.release(conn, st, rs);
		}
		return "003005002";
	}
	/**
	 * 获得号码列表
	 * @return
	 */
	public static List<String> getphonenumber(String timeString,String tab_name) {

		List<String> list = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null; 
		
		String startTime=timeString+" 00:00:00";
		String endTime=timeString+" 23:59:59"; 
			sql="SELECT distinct(device_number) FROM "
					+tab_name 
					+" WHERE slide_time BETWEEN '"
					+startTime
					+"' AND '"
					+endTime
					+"'";
			try {
				conn = new JdbcUtils02().getConnection();
				st = conn.prepareStatement(sql);
				rs = st.executeQuery();
				String device_number=""; 
				while(rs.next()){
				//更新统计表的次数 
					device_number= rs.getString("device_number");
					if(device_number != null && device_number.length() != 0){
						 list.add(device_number);
					}	
				} 
				return list;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				log.error("获取用户列表异常"+e);
			}finally{
				JdbcUtils02.release(conn, st, rs); 
			}
		return null;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		countPicPrefer();
	}

	public static void countPicPrefer() {
		List<String> list = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null; 
		String entertime = DateUtil.getSpecifiedDayBefore();
		
		String[] strs = entertime.split("-");
		String year = strs[0];
		String month = strs[1];
		String day = strs[2];

		String timeString = year + "-" + month + "-" + day;
		String startTime=timeString+" 00:00:00";
		String endTime=timeString+" 23:59:59";
		String tab_name="tab_slideinfo_"+year+month;
		list=getphonenumber(timeString,tab_name);
		log.info("插入tab_picPrefer的手机条数"+list.size());
		List<Prefer> plist=new ArrayList<Prefer>();
		try {
			conn = new JdbcUtils02().getConnection();
		
			for(String d:list){
				plist=convent(d,tab_name,timeString,conn);
				List<String> listcode=null;
				for(Prefer pp:plist){
					sql="insert into tab_picprefer(device_number,date,classify,pic_nums) values(?,?,?,?)";
					st = conn.prepareStatement(sql);
					st.setString(1, pp.getDevice_number());
					st.setString(2, pp.getDate());
					st.setString(3, pp.getClassify());
					st.setInt(4, pp.getScan_num());
					st.execute();
				} 
			}
			log.info("插入tab_picPrefer成功");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			log.error("插入tab_picPrefer异常"+e1);
		}finally{
			JdbcUtils02.release(conn, st, rs);
		}
	}

}
