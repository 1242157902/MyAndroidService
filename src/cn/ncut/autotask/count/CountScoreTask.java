package cn.ncut.autotask.count;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.ncut.autotask.domain.device;
import cn.ncut.autotask.utils.DateUtil;
import cn.ncut.autotask.utils.JdbcUtils;
import cn.ncut.autotask.utils.JdbcUtils02;
import lombok.extern.slf4j.Slf4j;
/*
 * 完成次数、积分的统计count_nums()
 * 完成积分过期检查is_scoreoutlife()
 * 完成优惠券的过期检查is_codeoutlife（）
 * */
@Slf4j
public class CountScoreTask{

	/**
	 * 次数统计，积分统计
	 * */
	public static void count_nums(){
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null;
	    List<device> lists=get_deviceopeninfo();
		device d=new device();
		
		log.info("List<device>所有长度"+lists.size());
		try {
			conn=new JdbcUtils().getConnection();
		
		for (int i=0;i<lists.size();i++) { 
			d=lists.get(i);
			String device_number=d.getDevice_number();
			log.info(i+"phone"+device_number);
			if(device_number==""||device_number==null){
				continue;
			}else{
				int	nums= d.getNums();
				int scores=d.getScores(); 
				
				sql="select * from tab_wxcounts where device_number='"+device_number+"'";
				conn=new JdbcUtils().getConnection();	
				st = conn.prepareStatement(sql);
				ResultSet is_rs = st.executeQuery();
				if(is_rs.next()){
					//已经存在，则更新总次数number_count和总积分score_count
					JdbcUtils.release(conn, st, rs);
					
					if(scores>100){
						scores=100;
					}
					sql="update tab_wxcounts set number_count=number_count+"+nums
					+",score_count=score_count+"+scores 
					+",in_count=in_count+"+scores 
					+" where device_number='"+device_number+"'";
					conn=new JdbcUtils().getConnection();
					st=conn.prepareStatement(sql);
					st.executeUpdate();
					JdbcUtils.release(conn, st, rs);
					log.info(device_number+"更新总次数成功  incount="+scores);
					//添加交易记录
					add_scoretrade(scores,device_number,0);
					//添加收支表
					add_inrecode(scores,device_number);
				}else{
					//若不存在，则插入
					int inital_score=100;//注册赠送100
					if(scores>100){
						scores=100;
					}
					int total_score=scores+100;
					sql="insert into tab_wxcounts(device_number,number_count,score_count,in_count) values('"
						+device_number+"',"
						+nums+","+total_score+","+total_score+")";
					conn=new JdbcUtils().getConnection();
					st=conn.prepareStatement(sql);
					st.executeUpdate();
					JdbcUtils.release(conn, st, rs);
					//添加交易记录 注册赠送
					add_scoretrade(inital_score,device_number,1);
					add_scoretrade(scores,device_number,0);
					log.info("插入总次数成功  "+device_number);
					//添加收支表
					add_inrecode(total_score,device_number);
				}
				JdbcUtils.release(conn, st, rs);
			}
		}
		} catch (SQLException e) {
		
			log.error("ffedef"+e.toString());
		}finally{
			JdbcUtils.release(conn, st, rs);
		}
		
	}
	
	/**
	 * 将每日收入积分添加到收入表中
	 * */
	private static void add_inrecode(int scores, String device_number){
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql;
		String start_date=DateUtil.getSpecifiedDayBefore();
		String end_date=DateUtil.getSpecifiedDay();
		//默认积分状态为未使用：0
		sql="insert into tab_wxscoreintrade(device_number,score,start_date,end_date,status) " +
				"values('"+device_number+"','"+scores+"','"+start_date+"','"+end_date+"',0)";
		
		try {
			conn=new JdbcUtils().getConnection(); 
			st=conn.prepareStatement(sql);
			st.executeUpdate();
			
			log.info("添加收支表成功  "+device_number);
		} catch (SQLException e) {
			log.error(e.toString());
		}finally{
			JdbcUtils.release(conn, st, rs);
		}
		
	}
	/*
	 * 积分的有效期
	 * */
	/*private int life_time(){
		int month=12;
		return month;
	}*/
	/**
	 * 将每天的收入积分记录到交易表中
	 * */
	private static void add_scoretrade(int scores,String device_number,int status) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null;
		//用户每日滑屏次数收入状态是0
		String date=DateUtil.getSpecifiedDayBefore();
		sql="insert into tab_wxscoretrade(device_number,score,date,status) " +
				"values('"+device_number+"','"+scores+"','"+date+"',"+status+")";
		try {
			conn=new JdbcUtils().getConnection();
			st=conn.prepareStatement(sql);
			st.executeUpdate();
			log.info("添加交易表成功  "+device_number);
		} catch (SQLException e) {
			log.error(e.toString());
		}finally{
			JdbcUtils.release(conn, st, rs);
		}
		
	}
	/**
	 * 积分次数换算
	 * */
	private static int convent(String device_number,String tabName,String start_time,String end_time){
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null;
		int scores=0;//总的积分
		sql="SELECT COUNT(*) pic_nums,pic_name,pic_score  FROM " +
				tabName+" WHERE device_number='"
				+device_number
				+"'and slide_time between '"
				+start_time
				+"'and'"
				+end_time
				+"' GROUP BY pic_name";
		try {
			conn=new JdbcUtils02().getConnection();
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			int pic_num=0;//每张图片出现的次数
			String pic_name="";//图片的名称
			int pic_score=1;//图片的权重
			int score;//每张图片兑换的总积分
			while(rs.next()){
				pic_num=rs.getInt("pic_nums");
				pic_name=rs.getString("pic_name");
				pic_score=pic_weight(pic_name);
				pic_score=rs.getInt("pic_score");
				score=pic_num*pic_score;
				scores+=score;
			}
		
			log.info("积分  "+scores+device_number);
			return scores;
		} catch (SQLException e) {
		   log.error(e.toString());
		}finally{
			JdbcUtils02.release(conn, st, rs);
		}
		return scores;
		
	}
	
	/**
	 * 
	 * 图片对应的权重
	 * */
	private static int pic_weight(String pic){
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null;
		int weight=1;
		sql="SELECT sort FROM `tab_pushcontent` where namex='"+pic+"'";
		try {
			conn = new JdbcUtils().getConnection();
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			if(rs.next()){
				weight=rs.getInt("sort");
			}
		} catch (SQLException e) {
			log.error(e.toString());
		}finally{

			JdbcUtils.release(conn, st, rs);
			
		}
		return weight;
	}
	
	/**
	 * 判断积分是否失效，或即将过期
	 * */
	public static void is_scoreoutlife() throws ParseException{
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null;
		//仅判断tab_inrecode表即可，
		sql="select * from tab_wxscoreintrade where status=0 or status=3";
		try {
			conn = new JdbcUtils().getConnection();
		
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			
			String end_date;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	        Date end;  
	        String cur_date=DateUtil.getSpecifiedDayBefore(); 
	        Date cur=df.parse(cur_date);
	        long days=0;   
			while(rs.next()){
				
				end_date=rs.getString("end_date");
				if(end_date==""||end_date==null)
					continue;
				else{
					end=(Date) df.parse(end_date);
					long curtime = cur.getTime();  
		            long endtime = end.getTime();  
		            long diff ; 
		            diff=endtime-curtime;
		            log.info("结束日期"+end+"当前日期"+cur+"相差"+diff);
		            if(diff<=0) {  
		                //若结束日期小于当前日期的前一天，则作废
		            	sql="update tab_wxscoreintrade set status=2 where end_date='"+end_date+"'";
		            	conn = new JdbcUtils().getConnection();
		            	st = conn.prepareStatement(sql);
		        		st.executeUpdate();
		        		JdbcUtils.release(conn, st, rs);
		            } else {  
		            	//否则，计算diff，判断是否即将过期
		            	int day = (int) (diff / (1000 * 60 * 60 * 24)); 
		            	System.out.print("相差的天数"+day);
		            	if(day<=30){
		            		sql="update tab_wxscoreintrade set status=3 where end_date='"+end_date+"'";
		            		conn = new JdbcUtils().getConnection();
		            		st = conn.prepareStatement(sql);
		            		st.executeUpdate();
		            		JdbcUtils.release(conn, st, rs);
		            	}
		            }  
		           
				}
			}
		} catch (SQLException e) {
			log.error(e.toString());
		}finally{
			JdbcUtils.release(conn, st, rs);
		}
	}
	/*
	 * 判断优惠券是否失效，
	 * */
	public static void is_codeoutlife() throws ParseException{
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null;
		//仅判断tab_codeinfo，
		sql="select * from tab_wxcodeinfo where status=0 or status=3";
		try {
			conn = new JdbcUtils().getConnection();
			
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			
			String end_date;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	        Date end;  
	        String cur_date=DateUtil.getSpecifiedDayBefore(); 
	        Date cur=df.parse(cur_date);  
			while(rs.next()){
				
				end_date=rs.getString("end_date");
				if(end_date==""||end_date==null||end_date=="NULL")
					continue;
				end=(Date) df.parse(end_date);
				long curtime = cur.getTime();  
	            long endtime = end.getTime();  
	            long diff ; 
	            diff=endtime-curtime;
	            if(diff<=0) {  
	                //若结束日期小于当前日期的前一天，则作废
	            	sql="update tab_wxcodeinfo set status=2 where end_date='"+end_date+"'";
	            	conn = new JdbcUtils().getConnection();
	            	st = conn.prepareStatement(sql);
	        		st.executeUpdate();
	        		JdbcUtils.release(conn, st, rs);
	            	
	            } else {  
	            	//否则，计算diff，判断是否即将过期
	            	
	            	int day = (int) (diff / (1000 * 60 * 60 * 24));  
	            	if(day<=7){
	            		sql="update tab_wxcodeinfo set status=3 where end_date='"+end_date+"'";
	            		conn = new JdbcUtils().getConnection();
	            		st = conn.prepareStatement(sql);
	            		st.executeUpdate();
	            		JdbcUtils.release(conn, st, rs);
	            	}else{
	            		continue;
	            	}
	            }  
	           
			}
		} catch (SQLException e) {
			log.error(e.toString());
		}finally{
			JdbcUtils.release(conn, st, rs);
			log.info("遍历积分券成功");
		}
		
	}
	public static List<device> get_deviceopeninfo(){
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
    	List<device> list=new ArrayList<device>();
		String tab_name="tab_slideinfo_"+year+month;
		
			sql="SELECT device_number,COUNT(*) nums FROM "
					+tab_name 
					+" WHERE slide_time BETWEEN '"
					+startTime
					+"' AND '"
					+endTime
					+"' GROUP BY device_number ";
			try {
				conn = new JdbcUtils02().getConnection();
				st = conn.prepareStatement(sql);
				rs = st.executeQuery();
				String device_number="";
				int nums=0;
				int scores=0; 
				
				while(rs.next()){
				//更新统计表的次数
					device d=new device();
						
						nums=rs.getInt("nums");
						System.out.println("erro:"+device_number);
						 
						device_number= rs.getString("device_number");
							scores=convent(device_number,tab_name,startTime, endTime);
						    d.setDevice_number(device_number); 
							d.setNums(nums);
							d.setScores(scores);
							list.add(d);
				}
				
				log.info("the length of "+tab_name+":"+list.size());
				log.info("遍历"+tab_name+"结束");
			} catch (SQLException e) {
				log.error(e.toString());
			}
				JdbcUtils02.release(conn, st, rs);
		log.info("the length"+list.size());
		return list;
	
	}

}
