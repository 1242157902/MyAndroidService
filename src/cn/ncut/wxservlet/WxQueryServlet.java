package cn.ncut.wxservlet;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ncut.wxservice.WxQueryService;

/**
 * @author wzq
 * 
 *         version 1.0 2014-12-2 下午8:08:51
 */
public class WxQueryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	WxQueryService service=new WxQueryService();
 
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method=request.getParameter("method");
		if (method.equals("countbyday")) {
			countbyday(request,response);
		}

	}

	/**按月统计每日滑屏次数 柱状图
	 * @param request
	 * @param response
	 */
	private void countbyday(HttpServletRequest request,
			HttpServletResponse response) {
		
		String num=request.getParameter("phonenum");
		String year=request.getParameter("year");
		String month=request.getParameter("month");
		
		 Map<String, String> map =service.countbyday(num,year,month);
		
		
		
		
		
		
		
		
		
	}

	

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
