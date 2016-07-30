package cn.ncut.syssetservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.ncut.syssetdomain.User;

/**
 * @author wzq
 *
 *version 1.0 2015-1-3 下午4:43:42
 */
public class AddUser extends HttpServlet {

	private static final long serialVersionUID = 1L;


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		
		 User user=(User) session.getAttribute("user");
		 
		
		 if (user==null) {
			 try {
					response.getWriter().write("会话过期，请重新登录！");
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
		}
		 
		 
		 if (user!=null) {
			 String comno=user.getComno();
			 
			 if (comno.equals("100000")) {
				
				 request.setAttribute("comno",comno);
				 
			}
			 
  request.getRequestDispatcher("/usermanager/adduser.jsp").forward(request, response); 
			 
			 
		}
		
		
		
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        doGet(request, response);
	
	}

}
