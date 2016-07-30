package cn.ncut.deviceservlet;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;


import net.sf.json.JSONArray;

import cn.ncut.devicedomain.MobileModel;
import cn.ncut.devicedomain.ScorePie;
import cn.ncut.devicedomain.SlideModel;
import cn.ncut.deviceservice.MobileService;
import cn.ncut.syssetdomain.User;
import cn.ncut.utils.JdbcUtils;

public class MobileServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	MobileService service = new MobileService();

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if (method.equals("GetMobileList")) {
			GetMobileList(request, response);
		}
		if (method.equals("GetSlideList")) {
			GetSlideList(request, response);
		} else if (method.equals("update")) {
			update(request, response);
		} else if ("Scorepie".equals(method)) {

			Scorepie(request, response);
		}else if ("BaoBiao".equals(method)) {
			BaoBiao(request,response);
		}
	}

	/**
	 * @param request
	 * @param response
	 */
	private void BaoBiao(HttpServletRequest request,
			HttpServletResponse response) {
		
		
		String path=request.getRealPath("/Files");
		
		
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = JdbcUtils.getConnection();
			String sqlString="select nick_name,enter_time from tab_deviceinfo  where user_unit=?";
			statement = conn.prepareStatement(sqlString);
		   statement.setString(1, "100002");
		   
		   
			result = statement.executeQuery();
			
			
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("员工注册信息表");
			
			HSSFRow row= sheet.createRow((short)0);;
			//为设置字体加粗准备
					Font font = workbook.createFont();
					HSSFCellStyle style = workbook.createCellStyle();
					font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
				    style.setFont(font);
				 

				    HSSFCell cell = row.createCell(0);
				    
				    
					cell.setCellValue("员工工号");
				    cell.setCellStyle(style);
					cell = row.createCell(1);
					
					cell.setCellValue("注册时间");
				    cell.setCellStyle(style);
					cell = row.createCell(2);
					
					
			
			
			 int i=1;
			
			while (result.next()) {
				
				String nick_name=result.getString("nick_name");
				
			    if (!"".equals(nick_name)&&nick_name!=null) {
				
			          row = sheet.createRow(i);
			          
			           row.createCell(0).setCellValue(nick_name);
			           row.createCell(1).setCellValue((result.getString("enter_time")).substring(0, 19));
			          
				       
			           i++;
					}
					 
		
			}
			
			FileOutputStream fOut = new FileOutputStream(path+"\\Sheet.xls");
			
			   workbook.write(fOut);
			   
			   fOut.flush();
			   
			   fOut.close();
			
			
		
			   request.getRequestDispatcher("/Sheet.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(conn, statement, result);
		}
		
		
		
		
	}

	/**
	 * @param request
	 * @param response
	 */
	private void Scorepie(HttpServletRequest request,
			HttpServletResponse response) {
		/*
		 * 
		 * ['Firefox', 45.0], ['IE', 26.8], ['Safari', 8.5], ['Opera', 6.2],
		 * ['Others', 0.7]
		 */

		try {
			String begintime = request.getParameter("begintime");
			String endtime = request.getParameter("endtime");

			ScorePie scorePies = service.getscorepie(begintime, endtime);
			// ScorePie(count500, count1000, count1500, count2000, count2500,
			// count3000, countup3000, count)

			DecimalFormat df = new java.text.DecimalFormat("###0.0");
			StringBuilder sbBuilder = new StringBuilder();

			sbBuilder
					.append("[")
					.append("'")
					.append("积分小于500")
					.append("',")
					.append(df.format(100*scorePies.getCount500()
							/ scorePies.getCount())).append("],");
			sbBuilder
					.append("[")
					.append("'")
					.append("积分大于500小于1000")
					.append("',")
					.append(df.format(100*scorePies.getCount1000()
							/ scorePies.getCount())).append("],");
			sbBuilder
					.append("[")
					.append("'")
					.append("积分大于1000小于1500")
					.append("',")
					.append(df.format(100*scorePies.getCount1500()
							/ scorePies.getCount())).append("],");
			sbBuilder
					.append("[")
					.append("'")
					.append("积分大于1500小于2000")
					.append("',")
					.append(df.format(100*scorePies.getCount2000()
							/ scorePies.getCount())).append("],");
			sbBuilder
					.append("[")
					.append("'")
					.append("积分大于2000小于2500")
					.append("',")
					.append(df.format(100*scorePies.getCount2500()
							/ scorePies.getCount())).append("],");
			sbBuilder
					.append("[")
					.append("'")
					.append("积分大于2500小于3000")
					.append("',")
					.append(df.format(100*scorePies.getCount3000()
							/ scorePies.getCount())).append("],");
			sbBuilder
					.append("[")
					.append("'")
					.append("积分大于3000")
					.append("',")
					.append(df.format(100*scorePies.getCountup3000()
							/ scorePies.getCount())).append("]");

			String str = sbBuilder.toString();

			request.setAttribute("str", str);
			
			request.setAttribute("count",scorePies.getCount());
			
			System.out.println(str);

			request.getRequestDispatcher("/pages/device/Scorepie.jsp").forward(
					request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void update(HttpServletRequest request, HttpServletResponse response) {

		String id = request.getParameter("imei");
		String unit = request.getParameter("company");
		String company = request.getParameter("seller");
		String theme = request.getParameter("theme");
		String themestatus = request.getParameter("themestatus");

		if ("北方工业大学".equals(unit)) {
			unit = "100001";
		} else if ("迪信通北京分公司".equals(unit)) {
			unit = "100002";
		} else if ("".equals(unit.trim())) {
			unit = "0";
		}

		if ("迪信通北京分公司".equals(company)) {
			company = "100002";
		} else if ("".equals(company.trim())) {
			company = "0";
		}

		User user = (User) request.getSession().getAttribute("user");

		service.update(id, unit, company, theme, themestatus, user.getId());

	}

	private void GetMobileList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			int currentPage = Integer.parseInt(request.getParameter("page"));

			int pageSize = Integer.parseInt(request.getParameter("rows"));

			String orderstrString = request.getParameter("order");
			if (orderstrString == null) {
				orderstrString = "desc";
			}

			MobileModel qrc = new MobileModel();
			
			

			User user = (User) request.getSession().getAttribute("user");
			
			//2016-4-25添加
			String mbinfo_serverkeys_String=request.getParameter("mbinfo_serverkeys");
			if(mbinfo_serverkeys_String==null||mbinfo_serverkeys_String.equals("-5,0,1")){
				
				qrc.setUpdate_key(-5);
			}else{
				
				qrc.setUpdate_key(Integer.parseInt(mbinfo_serverkeys_String));
			}
			
			
			
			
			qrc.setCompany(request.getParameter("mbinfo_company"));
			qrc.setImei(request.getParameter("device_imei"));

			qrc.setSeller(request.getParameter("mbinfo_seller"));

			// if(!"100000".equals(user.getComno())&&user.getIsseller()==1)
			// qrc.setSeller(user.getComno());

			if (!"100000".equals(user.getComno()))
				qrc.setCompany(user.getComno());

			qrc.setMbno(request.getParameter("mbinfo_no"));

			qrc.setGender(request.getParameter("mbinfo_sex"));

			qrc.setBirth(request.getParameter("mbinfo_age"));

			qrc.setMbos(request.getParameter("mbinfo_os"));

			qrc.setProvider(request.getParameter("mbinfo_provi"));

			qrc.setArea(request.getParameter("mbinfo_area"));

			qrc.setMbtype(request.getParameter("mbinfo_brand"));

			String start = "null";

			String end = "null";

			if (request.getParameter("mbinfo_regstart") != null
					&& !request.getParameter("mbinfo_regstart").equals(""))

				start = request.getParameter("mbinfo_regstart");

			if (request.getParameter("mbinfo_regend") != null
					&& !request.getParameter("mbinfo_regend").equals(""))

				end = request.getParameter("mbinfo_regend");

			qrc.setRegtime(start + "*" + end);

			// 返回设备信息
			List<MobileModel> mblist = service.GetMobileList(currentPage,
					pageSize, qrc, orderstrString);

			// 返回设备总数
			int total = service.GetMobileTotal(qrc);

			String json = "{\"total\":" + total + " , \"rows\":"
					+ JSONArray.fromObject(mblist).toString() + "}";

			response.getWriter().write(json);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void GetSlideList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			int currentPage = Integer.parseInt(request.getParameter("page"));

			int pageSize = Integer.parseInt(request.getParameter("rows"));

			String orderstrString = request.getParameter("order");
			if (orderstrString == null) {
				orderstrString = "desc";
			}

			SlideModel qrc = new SlideModel();

			User user = (User) request.getSession().getAttribute("user");

			qrc.setCompany(request.getParameter("mbinfo_company"));
			qrc.setImei(request.getParameter("device_imei"));

			qrc.setCompany(user.getComno());

			/*
			 * qrc.setSeller(request.getParameter("mbinfo_seller"));
			 * 
			 * if(!"100000".equals(user.getComno())&&user.getIsseller()==1)
			 * qrc.setSeller(user.getComno());
			 * 
			 * if(!"100000".equals(user.getComno())&&user.getIsseller()==0)
			 * qrc.setCompany(user.getComno());
			 * 
			 * 
			 * qrc.setMbos(request.getParameter("mbinfo_os"));
			 * 
			 * qrc.setProvider(request.getParameter("mbinfo_provi"));
			 * 
			 * qrc.setArea(request.getParameter("mbinfo_area"));
			 * 
			 * qrc.setMbtype(request.getParameter("mbinfo_brand"));
			 */

			qrc.setMbno(request.getParameter("mbinfo_no"));

			String start = "null";

			String end = "null";

			if (request.getParameter("mbinfo_regstart") != null
					&& !request.getParameter("mbinfo_regstart").equals(""))

				start = request.getParameter("mbinfo_regstart");

			if (request.getParameter("mbinfo_regend") != null
					&& !request.getParameter("mbinfo_regend").equals(""))

				end = request.getParameter("mbinfo_regend");

			if (start == null || end == null) {

				return;
			}

			qrc.setRegtime(start + "*" + end);

			String tablenames[] = new String[3];

			tablenames[0] = "";
			tablenames[1] = "";
			tablenames[2] = "";

			// 2015-07-07 10:29:24
			if (start != null && !"null".equals(start.trim()) && end != null
					&& !"null".equals(end.trim())) {

				String[] strartstrs = start.split("-");
				String[] endstrs = end.split("-");

				int startint = Integer.parseInt(strartstrs[1]);
				int endint = Integer.parseInt(endstrs[1]);
				if (strartstrs[0].equals(endstrs[0])) {// 如果所查询的是同一年的

					for (int i = startint, j = 0; i <= endint && j <= 2; i++, j++) {
						if (i < 10) {

							tablenames[j] = "tab_slideinfo_" + strartstrs[0]
									+ "0" + i;
						} else {

							tablenames[j] = "tab_slideinfo_" + strartstrs[0]
									+ i;
						}

					}

				} else {

					int between = 12 - startint;

					if (between >= 3) {

						for (int i = startint, j = 0; i <= 12 && j <= 2; i++, j++) {
							if (i < 10) {

								tablenames[j] = "tab_slideinfo_"
										+ strartstrs[0] + "0" + i;

							} else {

								tablenames[j] = "tab_slideinfo_"
										+ strartstrs[0] + i;
							}

						}

					} else {

						int i = 0, j = 0;
						for (i = startint, j = 0; i <= 12 && j <= 2; i++, j++) {
							if (i < 10) {

								tablenames[j] = "tab_slideinfo_"
										+ strartstrs[0] + "0" + i;
							} else {

								tablenames[j] = "tab_slideinfo_"
										+ strartstrs[0] + i;
							}

						}

						for (i = 1; i <= endint && j <= 2; i++, j++) {
							if (i < 10) {

								tablenames[j] = "tab_slideinfo_" + endstrs[0]
										+ "0" + i;
							} else {

								tablenames[j] = "tab_slideinfo_" + endstrs[0]
										+ i;
							}

						}

					}

				}

			}
			int total = 0;
			List<SlideModel> allsModels = new ArrayList<SlideModel>();

			int size = 0;
			for (int i = 0; i < tablenames.length; i++) {

				if (!tablenames[i].equals("")) {
					size++;
				}
			}

			int count1 = 0, count2 = 0, count3 = 0;

			int offset = (currentPage - 1) * pageSize;
			
			if (offset<0) {
				
				offset=0;
			}

			if (size == 1) {

				count1 = service.GetSlideTotal(qrc, tablenames[0]);

				allsModels.addAll(service.GetSlideList(offset, pageSize, qrc,
						tablenames[0], orderstrString));

			} else if (size == 2) {

				count1 = service.GetSlideTotal(qrc, tablenames[0]);
				count2 = service.GetSlideTotal(qrc, tablenames[1]);

				if (offset + pageSize <= count1 - 1)// 如果偏移量加上页面大小小于count1-1那么结果集全部落在第一张表里直接查询第一张表即可

				{

					allsModels.addAll(service.GetSlideList(offset, pageSize,
							qrc, tablenames[0], orderstrString));

				}

				if (offset <= count1 - 1 && offset + pageSize > count1 - 1
						&& offset + pageSize <= count1 + count2 - 1)

				// 如果偏移量小于count1-1，偏移量加上页面大小大于count1+count2-1，偏移量加上页面小于count1+count2-1，那么结果集将部分落在tab_searchlogs_01中部分落在tab_searchlogs_02中。

				{
					// Select * from tab_01 where 查询条件 limit offset，pagesize;

					allsModels.addAll(service.GetSlideList(offset, pageSize,
							qrc, tablenames[0], orderstrString));

					// Select * from tab_02 where 查询条件 limit
					// 0，(offset+pagesize)-count1+1;
					allsModels.addAll(service.GetSlideList(0,
							((offset + pageSize) - count1), qrc, tablenames[1],
							orderstrString));
				}

				if (offset > count1 - 1) {

					allsModels.addAll(service.GetSlideList(offset - count1,
							pageSize, qrc, tablenames[1], orderstrString));
				}

			} else {

				count1 = service.GetSlideTotal(qrc, tablenames[0]);
				count2 = service.GetSlideTotal(qrc, tablenames[1]);
				count3 = service.GetSlideTotal(qrc, tablenames[2]);

				if (offset + pageSize <= count1 - 1)// 如果偏移量加上页面大小小于count1-1那么结果集全部落在第一张表里直接查询第一张表即可

				{

					allsModels.addAll(service.GetSlideList(offset, pageSize,
							qrc, tablenames[0], orderstrString));

				}

				if (offset <= count1 - 1 && offset + pageSize > count1 - 1
						&& offset + pageSize <= count1 + count2 - 1)

				// 如果偏移量小于count1-1，偏移量加上页面大小大于count1+count2-1，偏移量加上页面小于count1+count2-1，那么结果集将部分落在tab_searchlogs_01中部分落在tab_searchlogs_02中。

				{
					// Select * from tab_01 where 查询条件 limit offset，pagesize;

					allsModels.addAll(service.GetSlideList(offset, pageSize,
							qrc, tablenames[0], orderstrString));

					// Select * from tab_02 where 查询条件 limit
					// 0，(offset+pagesize)-count1+1;
					allsModels.addAll(service.GetSlideList(0,
							((offset + pageSize) - count1 - 1), qrc,
							tablenames[1], orderstrString));

				}

				if (offset <= count1 - 1
						&& (offset + pageSize) > (count1 + count2 - 1))

				// 如果偏移量小于count1-1，偏移量加上页面大于count1+count2-1，那么结果集将分别落在tab_searchlogs_01、tab_searchlogs_02、tab_searchlogs_03中。

				{
					// Select * from tab_01 where 查询条件 limit offset，pagesize;
					allsModels.addAll(service.GetSlideList(offset, pageSize,
							qrc, tablenames[0], orderstrString));
					// Select * from tab_02 where 查询条件 limit 0，pagesize;

					allsModels.addAll(service.GetSlideList(0, pageSize, qrc,
							tablenames[1], orderstrString));

					// Select * from tab_03 where 查询条件 limit 0，pagesize;
					allsModels.addAll(service.GetSlideList(0, pageSize, qrc,
							tablenames[2], orderstrString));
				}

				if (offset > count1 - 1
						&& offset + pageSize <= count1 + count2 - 1)
				// 如果偏移量大于count1-1 offset+pagesize<=count1+count2-1
				// 那么结果集将落在tag_test02中

				{

					// Select * from tab_02where 查询条件 limit
					// (offset-count1)，pagesize;
					allsModels.addAll(service.GetSlideList((offset - count1),
							pageSize, qrc, tablenames[1], orderstrString));

					// 由于此处结果集的下标又从0开始所以偏移量需要减去count1
				}

				if (offset > count1 - 1
						&& offset + pageSize > count1 + count2 - 1
						&& offset + pageSize < count1 + count2 + count3 - 1)

				// 结果将落在第二张表和第三张表中

				{

					// Select * from tab_02 where 查询条件 limit
					// (offset-count1)，pagesize;

					allsModels.addAll(service.GetSlideList((offset - count1),
							pageSize, qrc, tablenames[1], orderstrString));

					// Select * from tab_03 where 查询条件 limit 0，pagesize;

					allsModels.addAll(service.GetSlideList(0, pageSize, qrc,
							tablenames[2], orderstrString));
				}

				if (offset > count1 + count2 - 1)
				// 结果将落在第三张表中
				{

					// Select * from tab_03where 查询条件 limit
					// (offset-count1-count2)，pagesize;

					allsModels.addAll(service.GetSlideList((offset - count1
							- count2 - 1), pageSize, qrc, tablenames[2],
							orderstrString));
				}

			}

			total = count1 + count2 + count3;

			String json = "{\"total\":" + total + " , \"rows\":"
					+ JSONArray.fromObject(allsModels).toString() + "}";
			response.getWriter().write(json);
		} catch (Exception e) {

		}
	}
}
