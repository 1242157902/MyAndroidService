package cn.ncut.wxservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import cn.ncut.wxdomain.DXT; 
import cn.ncut.wxservice.ShowDxtService;

public class ShowDXTServlet extends HttpServlet {

	ShowDxtService service=new ShowDxtService();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String method = request.getParameter("method"); 
		if (method.equals("showTotalDxt")) {
			showTotalDxt(request, response);
		}
		
		if (method.equals("saveDxt")) {
			saveDxt(request, response);
		}
		if (method.equals("showWeekDxt")) {
			showWeekDxt(request, response);
		}
		if (method.equals("showWeekPrintedDxt")) {
			showWeekPrintedDxt(request, response);
		}
	}

	








	/**
	 * 得到所有的迪信通员工信息列表
	 * @param request
	 * @param response
	 */
	private void showTotalDxt(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		List<DXT> list=new ArrayList();
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		
		int score_limit =  (Integer) (request.getParameter("score_limit") == null ? 0
				: Integer.parseInt(request.getParameter("score_limit")));
		 
		list=service.getDxt(score_limit, currentPage, pageSize);
		int total=service.getDxtTotal(score_limit);
		String json = "{\"total\":" + total + " ,\"rows\":"
				+ JSONArray.fromObject(list).toString() + "}";
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 点击打印后，将对应数据库中的数据总积分减去对应吸纳之，且nums+1
	 * @param request
	 * @param response
	 */

	private void saveDxt(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		int score_limit = Integer.parseInt(request.getParameter("score_limit"));
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		System.out.print("score_limit");
		boolean flag=service.savePrint(score_limit,currentPage,pageSize);
		String json="";
		if(flag){
			json="success";
		}else{
			json="false";
		}
		try {
			response.getWriter().write("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 得到某一周内的积分总数
	 * @param request
	 * @param response
	 */
	private void showWeekDxt(HttpServletRequest request,
			HttpServletResponse response) {
		List<DXT> list=new ArrayList();
		List<DXT> sublist=new ArrayList();
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		
		String start_date =  request.getParameter("start_date") == null ? ""
				: request.getParameter("start_date");
		String end_date =  request.getParameter("end_date") == null ? ""
				: request.getParameter("end_date");
		int total=0;
		if(start_date.length()==0&&end_date.length()==0){
			sublist=service.getDxt(0, currentPage, pageSize);
			total=service.getDxtTotal(0);
		}else{
			System.out.println(start_date+"---"+end_date);
			list=service.getWeekDxt(start_date,end_date);
			total=list.size();
			if(total==0){
				sublist=list;
			}else{
				PageModel pm = new PageModel(list, pageSize);
		     sublist = pm.getObjects(currentPage);
		     for(int i = 0; i < sublist.size(); i++) {
		          System.out.println(sublist.get(i));
		      }
			}
			
			//	total=service.getDxtTotal(start_date,end_date);
		} 
		
		String json = "{\"total\":" + total + " ,\"rows\":"
				+ JSONArray.fromObject(sublist).toString() + "}";
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 显示已换购
	 * @param request
	 * @param response
	 */
	private void showWeekPrintedDxt(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		List<DXT> list=new ArrayList();
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		
		String start_date =  request.getParameter("start_date") == null ? ""
				: request.getParameter("start_date");
		String end_date =  request.getParameter("end_date") == null ? ""
				: request.getParameter("end_date");
		
		list=service.getDxtPrinted(start_date,end_date, currentPage, pageSize);
		int total=service.getDxtPrintedTotal(start_date,end_date);
		String json = "{\"total\":" + total + " ,\"rows\":"
				+ JSONArray.fromObject(list).toString() + "}";
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request,response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
