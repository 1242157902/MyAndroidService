
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
import lombok.extern.slf4j.Slf4j;
import com.mysql.jdbc.CallableStatement;
import cn.ncut.autotask.utils.DateUtil;
import cn.ncut.autotask.utils.JdbcUtils;
import cn.ncut.autotask.utils.JdbcUtils02;
/*
 * 完成次数、积分的统计count_nums()
 * 完成积分过期检查is_scoreoutlife()
 * 完成优惠券的过期检查is_codeoutlife（）
 * */
@Slf4j
public class PonitsInfoCount {
	/*
	 * 更新总积分、总次数、每日收入
	 * */
	static Connection conn = null;
	static PreparedStatement st = null;
	public static void update() throws SQLException{
		
		ResultSet rs = null;
		String sql = null;
	     conn = new JdbcUtils().getConnection();
		List<String> phone_list = getphonenumber();
		
		//计算前一天
		String entertime = DateUtil.getSpecifiedDayBefore();
		String[] strs = entertime.split("-");
		String year = strs[0];
		String month = strs[1];
		String day = strs[2];
		String timeString = year + "-" + month + "-" + day;
		
		String device_number=null;
		int nums=0;
		int scores=0;
		for(int i=0;i<phone_list.size();i++){
		//更新统计表的次数
			
			device_number=phone_list.get(i);
			if(device_number==null||device_number=="")
				continue;
			else{
				nums=count_number(device_number);
				scores=count_score(device_number);
				if(nums==0||scores==0){
					continue;
				}else{
					sql="select * from tab_wxcounts where device_number='"+device_number+"'";
					st = conn.prepareStatement(sql);
					ResultSet is_rs = st.executeQuery();
					if(is_rs.next()){
						//若统计表中已经存在，则更新
						sql="update tab_wxcounts set number_count="
						+nums
						+",score_count="+scores +
						" where device_number='"+device_number+"'";
						st=conn.prepareStatement(sql);
						st.executeUpdate();
						log.info("更新总次数成功  "+nums+device_number);
					}else{
						//若不存在，则插入
						sql="insert into tab_wxcounts(device_number,number_count,score_count) values('"
						+device_number+"',"
						+nums+","+scores+")";
						st=conn.prepareStatement(sql);
						st.executeUpdate();
						log.info("插入总次数成功  "+nums+device_number);
					}
					//获得每天的积分
					int cc=count_score_day(device_number,timeString);
					log.info(device_number+"的每天积分"+cc);
					if(cc==0)
						continue;
					else{
						//添加交易记录
						add_scoretrade(scores,device_number);
						//添加收支表
						add_inrecode(scores,device_number);
						
					}
					
				}
			}
		}
		JdbcUtils.release(conn, st, rs);
	}
	public static List<String> getphonenumber() {

		List<String> nums = new ArrayList<String>();
	
		ResultSet rs = null;
		String sql = null;
		String num = null;

		try {
			sql = "select * from tab_deviceinfo";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();

			while (rs.next()) {
				num = rs.getString("device_number");
				nums.add(num);
			}
			return nums;
		} catch (Exception e) {
			log.error(e.toString());
		} 

		
         return null;
	}

	/*
	 * 根据手机号统计每天的积分
	 * */
	private static int count_score_day(String device_number,String date) throws SQLException{
		int score=0;
		CallableStatement c=(CallableStatement) conn.prepareCall("{call SelectSlideDayScore(?,?)}");
		c.setString(1, device_number);
		c.setString(2, date);
		ResultSet rs=c.executeQuery();
		
		if(rs.next()){
			score=rs.getInt(1);
		}
		
		return score;
	}
	/*
	 * 根据手机号统计总次数
	 * */
	private static int count_number(String device_number) throws SQLException{
		int score=0;
		CallableStatement c=(CallableStatement) conn.prepareCall("{call SelectSlideTotalCount(?)}");
		c.setString(1, device_number);
		ResultSet rs=c.executeQuery();
		
		if(rs.next()){
			score=rs.getInt(1);
		}
	
		log.info("score:"+score);
		return score;
	}
	/*
	 * 根据手机号统计总积分
	 * */
	private static int count_score(String device_number) throws SQLException{
		int score=0;
		CallableStatement c=(CallableStatement) conn.prepareCall("{call SelectSlideTotalScore(?)}");
		c.setString(1, device_number);
		ResultSet rs=c.executeQuery();
		
		if(rs.next()){
			score=rs.getInt(1);
		}
		
		return score;
	}
	/*
	 * 将每日收入积分添加到收入表中
	 * */
	private static void add_inrecode(int scores, String device_number) throws SQLException {
		// TODO Auto-generated method stub
		ResultSet rs = null;
		String sql;
		String start_date=DateUtil.getSpecifiedDayBefore();
		String end_date=DateUtil.getSpecifiedDay();
		//默认积分状态为未使用：0
		sql="insert into tab_wxscoreintrade(device_number,score,start_date,end_date,status) " +
				"values('"+device_number+"','"+scores+"','"+start_date+"','"+end_date+"',0)";
		
		st=conn.prepareStatement(sql);
		st.executeUpdate();
		log.info("添加收支表成功  "+device_number);
	}
	/*
	 * 积分的有效期
	 * */
	/*private int life_time(){
		int month=12;
		return month;
	}*/
	/*
	 * 将每天的收入积分记录到交易表中
	 * */
	private static void add_scoretrade(int scores,String device_number) throws SQLException {
		// TODO Auto-generated method stub
		ResultSet rs = null;
		String sql = null;
		//用户每日滑屏次数收入状态是0
		String date=DateUtil.getSpecifiedDayBefore();
		sql="insert into tab_wxscoretrade(device_number,score,date,status) " +
				"values('"+device_number+"','"+scores+"','"+date+"',0)";
		st=conn.prepareStatement(sql);
		st.executeUpdate();
		log.info("添加交易表成功  "+device_number);
		
	}
	/*
	 * 积分次数换算
	 * */
	private static int convent(String device_number,String start_time,String end_time) throws SQLException{
		
		ResultSet rs = null;
		String sql = null;
		int scores=0;//总的积分
		sql="SELECT COUNT(*) pic_nums,pic_name FROM tab_deviceopeninfo WHERE device_number='"
				+device_number
				+"'and open_time between '"
				+start_time
				+"'and'"
				+end_time
				+"' GROUP BY pic_name";
		st = conn.prepareStatement(sql);
		rs = st.executeQuery();
		int pic_num=0;//每张图片出现的次数
		String pic_name="";//图片的名称
		int pic_score=0;//图片的权重
		int score;//每张图片兑换的总积分
		while(rs.next()){
			pic_num=rs.getInt("pic_nums");
			pic_name=rs.getString("pic_name");
			pic_score=pic_weight(pic_name);
			score=pic_num*pic_score;
			scores+=score;
			log.info("权重  "+pic_score+"图片名称"+pic_name);
		}
		log.info("积分  "+scores+"图片名称"+pic_name);
		return scores;
	}
	/*
	 * 图片对应的权重
	 * */
	private static int pic_weight(String pic) throws SQLException{
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null;
		int weight=1;
		sql="SELECT sort FROM `tab_pushcontent` where namex='"+pic+"'";
		conn = new JdbcUtils02().getConnection();
		st = conn.prepareStatement(sql);
		rs = st.executeQuery();
		if(rs.next()){
			weight=rs.getInt("sort");
		}
		JdbcUtils02.release(conn, st, rs);
		return weight;
	}
	/*
	 * 根据手机号，找对应的openid
	 * */
	private static String get_openid(String device_number) throws SQLException{
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null;
		String openid="";
		sql="select open_id from tab_userinfo where phone_num='"
				+device_number+"'";
		st = conn.prepareStatement(sql);
		rs = st.executeQuery();
		if(rs.next()){
			openid=rs.getString("open_id");
		}
		return openid;
	}
	/*
	 * 判断积分是否失效，或即将过期
	 * */
	public static void is_scoreoutlife() throws SQLException, ParseException{
		ResultSet rs = null;
		String sql = null;
		//仅判断tab_inrecode表即可，
		sql="select * from tab_wxscoreintrade where status=0 or status=3";
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
			end=(Date) df.parse(end_date);
			long curtime = cur.getTime();  
            long endtime = end.getTime();  
            long diff ; 
            diff=endtime-curtime;
            log.info("结束日期"+end+"当前日期"+cur+"相差"+diff);
            
            if(diff<=0) {  
                //若结束日期小于当前日期的前一天，则作废
            	sql="update tab_wxscoreintrade set status=2 where end_date='"+end_date+"'";
            	st = conn.prepareStatement(sql);
        		st.executeUpdate();
            	
            } else {  
            	//否则，计算diff，判断是否即将过期
            	int day = (int) (diff / (1000 * 60 * 60 * 24)); 
            	log.info("相差的天数"+day);
            	if(day<=30){
            		sql="update tab_wxscoreintrade set status=3 where end_date='"+end_date+"'";
                	st = conn.prepareStatement(sql);
            		st.executeUpdate();
            	}
            }  
           
		}
		JdbcUtils.release(conn, st, rs);
	}
	/*
	 * 判断优惠券是否失效，
	 * */
	public static void is_codeoutlife() throws SQLException, ParseException{
		
		ResultSet rs = null;
		String sql = null;
		//仅判断tab_codeinfo，
		sql="select * from tab_wxcodeinfo where status=0 or status=3";
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
			end=(Date) df.parse(end_date);
			long curtime = cur.getTime();  
            long endtime = end.getTime();  
            long diff ; 
            diff=endtime-curtime;
            if(diff<=0) {  
                //若结束日期小于当前日期的前一天，则作废
            	sql="update tab_wxcodeinfo set status=2 where end_date='"+end_date+"'";
            	st = conn.prepareStatement(sql);
        		st.executeUpdate();
            	
            } else {  
            	//否则，计算diff，判断是否即将过期
            	
            	int day = (int) (diff / (1000 * 60 * 60 * 24));  
            	if(day<=7){
            		sql="update tab_wxcodeinfo set status=3 where end_date='"+end_date+"'";
                	st = conn.prepareStatement(sql);
            		st.executeUpdate();
            	}else{
            		continue;
            	}
            }  
           
		}
		JdbcUtils.release(conn, st, rs);
		log.info("遍历积分券成功");
	}
}
