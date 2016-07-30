package cn.ncut.syssetservlet;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import cn.ncut.syssetdomain.Privilege;
import cn.ncut.syssetdomain.User;
import cn.ncut.syssetservice.PrivilegeManagerService;

/**
 * @author wzq
 *
 * version 1.0 2014-10-14 下午4:29:58
 */
public class PrivilegeManagerServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;
  PrivilegeManagerService service=new PrivilegeManagerService();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	   String method=request.getParameter("method");
	   if (method.equals("listprivilege")) {
		   listprivilege(request,response);
	}else if (method.equals("addprivilege")) {
		addprivilege(request,response);
	}else if (method.equals("deleteprivilege")) {
		deleteprivilege(request,response);
	}
		
		
		
	}

	
 //删除权限
	private void deleteprivilege(HttpServletRequest request,
			HttpServletResponse response) {
		String id=request.getParameter("id");
		service.deleteprivilege(id);
		request.setAttribute("message", "权限删除成功！");
		try {
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}


	//添加权限
	private void addprivilege(HttpServletRequest request,
			HttpServletResponse response) {
		String name=request.getParameter("name");
		String description=request.getParameter("description");
		
		
		service.addprivilege(description,name);
		

		request.setAttribute("message", "权限添加成功！");
		try {
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		
	}


	//获取所有权限
	private void listprivilege(HttpServletRequest request,
			HttpServletResponse response) {
		List<Privilege> privileges= service.getAllPrivilege();
		request.setAttribute("privileges",privileges );
		
		
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
		
		
		 }

		try {
			request.getRequestDispatcher("/usermanager/listprivilege.jsp").forward(
					request, response);
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		
		
		
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	   doGet(request, response);
	}

}
