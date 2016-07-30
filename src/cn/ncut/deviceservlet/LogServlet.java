package cn.ncut.deviceservlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import cn.ncut.devicedomain.ApkInfo;
import cn.ncut.devicedomain.ApkLog;
import cn.ncut.deviceservice.LogService;
/**保存历史信息 设备更新apk记录 以及设备获取图片的记录
 * @author wzq
 *
 *version 1.0 2015-1-28 下午5:36:34
 */
public class LogServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;
	
	
	LogService logService=new LogService();


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String method=request.getParameter("method");
		if ("getphoneupdateapkinfo".equals(method)) {
			getphoneupdateapkinfo(request,response);
		}
		

		
	}

	
	/**获取手机更新apk信息
	 * @param request
	 * @param response
	 */
	private void getphoneupdateapkinfo(HttpServletRequest request,
			HttpServletResponse response) {
		
		int currentPage;
		try {
			currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e1) {
		     currentPage=1;
		}
		int pageSize;
		try {
			pageSize = Integer.parseInt(request.getParameter("rows"));
		} catch (NumberFormatException e1) {
			pageSize=10;
		}
		String phonenum=(request.getParameter("mbinfo_no"));
		String imei=(request.getParameter("device_imei"));
		
		List<ApkLog> apkLogs=logService.getphoneupdateapkinfo(currentPage,pageSize,phonenum,imei);
		
		
		int total = logService.getphoneApkupdateinfoTotal(phonenum,imei);

		response.setContentType("text/html;charset=utf-8");

		String json = "{\"total\":" + total + " , \"rows\":"
				+ JSONArray.fromObject(apkLogs).toString() + "}";
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
               doGet(request, response);
		
	}

}
