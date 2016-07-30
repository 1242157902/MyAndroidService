package cn.ncut.syssetservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.ncut.syssetdao.RoleManagerDao;
import cn.ncut.syssetdao.UserManagerDao;
import cn.ncut.syssetdomain.Privilege;
import cn.ncut.syssetdomain.Role;
import cn.ncut.syssetdomain.User;
import cn.ncut.syssetservice.UserManagerService;

/**
 * @author wzq
 *
 * version 1.0 2014-10-15 下午3:06:49
 */
public class LoginServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String  username=request.getParameter("username");
		String password=request.getParameter("password");
		
		UserManagerService service=new UserManagerService();
		//判断用户是否合法
		 User user= service.finduser(username,password);
		if (user!=null) {
			HttpSession session=request.getSession();
			
			session.setAttribute("user", user);
			session.setMaxInactiveInterval(3600);
			
			//得到用户所拥有的角色
			UserManagerDao userManagerDao=new UserManagerDao();
			List<Role>roles=userManagerDao.getUserRoles(user.getId());
			//得到用户所拥有的权限
			List<Privilege>privileges=new ArrayList<Privilege>();
			
			
		   RoleManagerDao roleManagerDao=new RoleManagerDao();
			for (Role role : roles) {
				
			privileges.addAll(roleManagerDao.getroleprivileges(role.getId()));
				
		   }
			
			StringBuilder stringBuilder=new StringBuilder();
			
			for (Privilege privilege : privileges) {
				
				stringBuilder.append(privilege.getName());
				
			}
			
			
			String sb=stringBuilder.toString();
			
			if (sb.contains("设备信息管理")) {
				request.setAttribute("sbxxgl","true");
			}
          
            if (sb.contains("内容推送管理")) {
            	request.setAttribute("nrgl","true");
			}
            
            if (sb.contains("系统用户管理")) {
            	request.setAttribute("xtsz","true");
			}
            if(sb.contains("统计分析")){
            	
            	request.setAttribute("tjfx","true");
            	
            }
            if (sb.contains("二维码管理")) {
            	request.setAttribute("ewmgl","true");
			}
            if (sb.contains("企业管理")) {
            	request.setAttribute("qygl","true");
            }
        
			
			
			if ("100000".equals(user.getComno())) {
				request.setAttribute("admin","true");
				
			}
			
			if (username.equals(password)) {
				
				
				request.getRequestDispatcher("/usermanager/firstupdate.jsp").forward(request, response);
			
			
			}else {
				request.getRequestDispatcher("/usermanager/index.jsp").forward(request, response);
			}
			
			
			
			
			
		}else {
			
			request.setAttribute("errormessage", "<script> alert('用户名或密码错误！')</script>");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			
		}
		
		
		
		
		
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
