package cn.ncut.syssetservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.ncut.syssetdao.RoleManagerDao;
import cn.ncut.syssetdomain.Role;
import cn.ncut.syssetdomain.User;
import cn.ncut.syssetservice.RoleManagerService;
import cn.ncut.syssetservice.UserManagerService;

/**
 * @author wzq
 * 
 *         version 1.0 2014-10-14 下午3:28:17
 */
public class UserManagerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	UserManagerService service = new UserManagerService();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		

		
		if (method.equals("listuser")) {

			listuser(request, response);
		}else if (method.equals("adduser")) {
			adduser(request,response);
		}else if (method.equals("deleteuser")) {
			deleteuser(request,response);
		}else if (method.equals("userrole")) {
			userrole(request,response);
			
		}else if (method.equals("adduserrole")) {
			
			adduserrole(request,response);
			
		}else if (method.equals("updateuser")) {
			updateuser(request,response);
		}else if (method.equals("firstloginupdate")) {
			firstloginupdate(request,response);
		}

	}

 /**
	 * @param request
	 * @param response
	 */
	private void firstloginupdate(HttpServletRequest request,
			HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		
		 User user=(User) session.getAttribute("user");
		 String id=null;
		 
		 if (user==null) {
			 try {
					response.getWriter().write("会话过期，请重新登录！");
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
		}
		 
		 if (user!=null) {
			id=user.getId();
		 }	
		 
		 String password=request.getParameter("password");
		
          service.updateuser(id, password);
		
		
		try {
			request.setAttribute("errormessage", "<script> alert('密码修改完成，请重新登录！')</script>");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
	}

	//更新用户密码
	private void updateuser(HttpServletRequest request,
			HttpServletResponse response) {
		
		String id=request.getParameter("id");
		String password=request.getParameter("password");
		if (id==""||id==null) {
			try {
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
		
		service.updateuser(id,password);
		
		request.setAttribute("message", "密码修改成功！");
		
		try {
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
	}

	//为用户添加角色
	private void adduserrole(HttpServletRequest request,
			HttpServletResponse response) {
		
		try {
			String user_id=request.getParameter("user_id");
			
			//得到所有授权角色的id
			String role_ids[]=request.getParameterValues("role_id");
			
			service.updateuserrole(user_id,role_ids);
			
			request.setAttribute("message", "授予成功！");
			request.setAttribute("url", "/servlet/UserManagerServlet?method=listuser");
			
			
		} catch (Exception e) {
			request.setAttribute("message", "授予失败！");
			e.printStackTrace();
		}
		
		try {
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		} catch (Exception e) {
		
			e.printStackTrace();
		} 
		
		
	}

	//显示用户角色管理页面
	private void userrole(HttpServletRequest request,
			HttpServletResponse response) {
		
		
		String id=request.getParameter("id");
		
		User user=service.finduserbyid(id);
		//获取用户拥有的角色
		List<Role> user_roles=service.getUserRoles(id);
				
		//获取所有的角色
		RoleManagerService roleManagerService=new RoleManagerService();
		
		List<Role>system_roles=roleManagerService.getAllRole();
		
		
		
		
		
		
		request.setAttribute("user", user);
		request.setAttribute("user_roles", user_roles);
		request.setAttribute("system_roles", system_roles);
		
		try {
			request.getRequestDispatcher("/usermanager/addUserRole.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
		
		
		
	}

	//删除用户
	private void deleteuser(HttpServletRequest request,
			HttpServletResponse response) {
		String id=request.getParameter("id");
		
		service.deleteuser(id);
		request.setAttribute("message", "删除用户成功！");
		try {
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

	//添加用户
	private void adduser(HttpServletRequest request,
			HttpServletResponse response) {
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String comname=request.getParameter("comname");
		String comno=null;
		if (comname==null) {
			HttpSession session = request.getSession();
			User user=(User) session.getAttribute("user");
			try {
				comno=user.getComno();
			} catch (Exception e) {
				try {
					response.getWriter().write("会话过期，请重新登录！");
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
			}	
		}else {
			comno=service.addcompany(comname);
		}
		
		
		
		
		service.adduser(username,password,comno);
		
		request.setAttribute("message", "用户添加成功！");
		request.setAttribute("url", "servlet/UserManagerServlet?method=listuser");
		try {
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
	}

	// 获取所有用户信息
	private void listuser(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		
		 User user=(User) session.getAttribute("user");
		 String comno=null;
		 
		 if (user==null) {
			 try {
					response.getWriter().write("会话过期，请重新登录！");
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
		}
		 
		 if (user!=null) {
			comno=user.getComno();
		}
		 
		 

		List<User> users = service.getAllUser(comno);
		request.setAttribute("users", users);

		try {
			request.getRequestDispatcher("/usermanager/listuser.jsp").forward(
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
