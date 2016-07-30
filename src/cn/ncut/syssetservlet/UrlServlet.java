package cn.ncut.syssetservlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ncut.syssetdomain.UrlInfo;
import cn.ncut.syssetservice.UrlService;

public class UrlServlet extends HttpServlet{
	
	 private UrlService service = new UrlService();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		

		
		if (method.equals("getUrl")) {

			getUrl(request, response);
		}else if(method.equals("updateUrl")){
			updateUrl(request, response);
		}

	}
	
	
	private void getUrl(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		UrlInfo url = service.getUrlById();
		request.setAttribute("url", url);
		try {
			request.getRequestDispatcher("/pages/editUrl.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void updateUrl(HttpServletRequest request, HttpServletResponse response) {
		
	
		
		String newurl = request.getParameter("newurl");
		service.updateUrl(null,newurl);
	
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
