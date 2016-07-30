package cn.ncut.autotask.count;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import cn.ncut.autotask.dao.SIMinfoDao;
import cn.ncut.autotask.domain.SIMinfo;
import cn.ncut.autotask.utils.DateUtil;
import cn.ncut.autotask.utils.JdbcUtils;
import cn.ncut.autotask.utils.JdbcUtils02;
import cn.ncut.autotask.utils.JdbcUtils03;

/**
 * @author wzq
 * 
 * version 1.0 2014-12-24 下午12:01:19
 */
@Slf4j
public class SlideCountTask {
	List<String> nums = new ArrayList<String>();
   public static void docounttask() throws Exception{
		
	List<String> nums = getphonenumber();
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null;
		String num = null;

		SIMinfoDao siMinfoDao = new SIMinfoDao();

		for (int i = 0; i < nums.size(); i++) {

			num = nums.get(i);
			

			/************************************************************************************************************/

			if (num != null && num.length() > 7) {
				
				SIMinfo siMinfo = siMinfoDao.getSIMinfo(num.substring(0, 7));

				String entertime = DateUtil.getSpecifiedDayBefore();
				
			    String[] strs = entertime.split("-");
				String year = strs[0];
				String month = strs[1];
				String day = strs[2];

				String timeString = year + "-" + month + "-" + day;
				
				
				
				String  tablename="tab_slideinfo_"+year+month;
				
				
            	sql = "select count(slide_time) from "+tablename+" where slide_time between '"
							+ timeString
							+ " 00:00:00'"
							+ " and '"
							+ timeString
							+ " 23:59:59'"
							+ " and device_number="
							+ "'"
							+ num
							+ "'";
					conn = new JdbcUtils02().getConnection();
					st = conn.prepareStatement(sql);
					rs = st.executeQuery();
					rs.next();
                    int count = rs.getInt(1);
                 
					JdbcUtils02.release(conn, st, rs);
					
					log.info("统计sql语句:"+sql);
				
					sql = "select * from tab_slide_count where device_number='"
							+ num + "' and dateym='" + year + month + "'";
					conn = new JdbcUtils03().getConnection();
					st = conn.prepareStatement(sql);
					rs = st.executeQuery();
					if (rs.next()) {
						sql = "update tab_slide_count set day" + day + "="
								+ count + " where device_number='" + num
								+ "' and dateym='" + year + month + "'";
						st = conn.prepareStatement(sql);
						st.executeUpdate();
						
						JdbcUtils03.release(conn, st, rs);
						log.info("num:"+num+"日期"+year+"-"+month+"-"+day+" 次数:"+count);
						
          		   } else {

						sql = "insert into tab_slide_count (device_number,dateym,day"
								+ day
								+ ")"
								+ " values ('"
								+ num
								+ "','"
								+ year
								+ month + "'," + count + ")";
						st = conn.prepareStatement(sql);
						st.executeUpdate();
						JdbcUtils03.release(conn, st, rs);
						log.info("num:"+num+"日期"+year+"-"+month+"-"+day+" 次数:"+count);
					}

				
					

			}

		}

	}

   
  
public static List<String> getphonenumber() {

		List<String> nums = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = null;
		String num = null;

		try {
			conn = new JdbcUtils().getConnection();
			sql = "select * from tab_deviceinfo";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();

			while (rs.next()) {
				num = rs.getString("device_number");
				nums.add(num);
			}
			return nums;
		} catch (Exception e) {
			  e.printStackTrace();
		} finally {

			JdbcUtils.release(conn, st, rs);
			System.out.println("JdbcUtils.release(conn, st, rs) numline 253");
		}
                return null;
	}

}
