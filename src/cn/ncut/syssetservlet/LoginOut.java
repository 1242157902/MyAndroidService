package cn.ncut.syssetservlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author wzq
 *
 * version 1.0 2014-10-15 下午4:50:58
 */
public class LoginOut extends HttpServlet {

	
	private static final long serialVersionUID = 1L;


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	         HttpSession session=   request.getSession();
	         session.removeAttribute("user");
	         session.invalidate();
	         request.getRequestDispatcher("/login.jsp").forward(request, response);
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	   doGet(request, response);
	}

}
