package cn.ncut.wxservlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import cn.ncut.algorim.myApriori;
import cn.ncut.wxdomain.Prefer;


public class DataMiningServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String method = request.getParameter("method");
		if("referenceMining".equals(method))
		{
			referenceMining(request,response);
		} 
	}
	private void referenceMining(HttpServletRequest request,
			HttpServletResponse response) {
		String minSupport = request.getParameter("minSupport");
		String currentDate = request.getParameter("currentDate");
		System.out.println("最小支持度为："+minSupport+"------,日期为："+currentDate);
		myApriori ma = new myApriori();
			try
			{
				int num = ma.isFrequentItemsExists(currentDate,minSupport);
				if(num==0)
				{
					ma.referenceDataMining(currentDate, minSupport);
				}
				List<Prefer> strList = ma.codeToName(currentDate,minSupport);
				if (strList.size()>0)
				{
						//通过标签value来获取有哪些用户
						//通过用户手机号的时候，再查询用户的信息
						//展示用户的信息
					String maplist = JSONArray.fromObject(strList).toString();
					response.setContentType("text/html;charset=utf-8");
					String json = "{\"total\":" + strList.size() + " , \"rows\":"
						+ maplist + "}";
					System.out.println(json);
					response.getWriter().write(json);
				  
				} 
				else
				{
					//考虑到该用户量不会有太多，所以就不分页了。
					response.setContentType("text/html;charset=utf-8");
					response.getWriter().write("null");
				}
			}
			catch (Exception e) 
			{
				System.out.println("程序发生异常，请检查代码！");
				 e.printStackTrace();
			}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request,response);
	}
}
