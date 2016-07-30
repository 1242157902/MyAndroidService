package cn.ncut.syssetservlet;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ncut.syssetdomain.Privilege;
import cn.ncut.syssetdomain.Role;
import cn.ncut.syssetdomain.User;
import cn.ncut.syssetservice.PrivilegeManagerService;
import cn.ncut.syssetservice.RoleManagerService;

/**
 * @author wzq
 * 
 *         version 1.0 2014-10-14 下午5:08:52
 */
public class RoleManagerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	RoleManagerService service = new RoleManagerService();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if (method.equals("listrole")) {
			listrole(request, response);
		} else if (method.equals("addrole")) {
			addrole(request, response);
		} else if (method.equals("deleterole")) {
			deleterole(request, response);
		} else if (method.equals("roleprivilege")) {
			roleprivilege(request, response);
		}else if (method.equals("addroleprivilege")) {
			addroleprivilege(request,response);
		}

	}

//给角色授予权限
	private void addroleprivilege(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String role_id=request.getParameter("role_id");
			String privilege_ids[]=request.getParameterValues("privilege_id");
			service.updateroleprivilege(role_id,privilege_ids);
			request.setAttribute("message", "授权成功！");
		} catch (Exception e) {
			request.setAttribute("message", "授权失败");
			e.printStackTrace();
		}
		try {
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		
		
		
	}

	//跳转到角色管理页面
	private void roleprivilege(HttpServletRequest request,
			HttpServletResponse response) {

		String id = request.getParameter("id");
		// 得到角色信息
		Role role = service.findrolebyid(id);
		// 得到角色当前拥有的所有权限

		List<Privilege> role_privileges = service.getroleprivilege(id);

		// 得到系统所有权限
		PrivilegeManagerService privilegeManagerService = new PrivilegeManagerService();

		List<Privilege> system_Privileges = privilegeManagerService
				.getAllPrivilege();

		request.setAttribute("role", role);
		request.setAttribute("role_privileges", role_privileges);
		request.setAttribute("system_privileges", system_Privileges);

		try {
			request.getRequestDispatcher("/usermanager/addrolePrivilege.jsp")
					.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 删除角色
	private void deleterole(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");

		service.deleterole(id);

		request.setAttribute("message", "角色删除成功！");
		try {
			request.getRequestDispatcher("/message.jsp").forward(request,
					response);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	// 添加角色
	private void addrole(HttpServletRequest request,
			HttpServletResponse response) {

		String name = request.getParameter("name");
		String description = request.getParameter("description");
		

		service.addrole(name, description);
		request.setAttribute("message", "角色添加成功！");
		try {
			request.getRequestDispatcher("/message.jsp").forward(request,
					response);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	// 查询所有角色
	private void listrole(HttpServletRequest request,
			HttpServletResponse response) {

		List<Role> roles = service.getAllRole();
		request.setAttribute("roles", roles);

		try {
			request.getRequestDispatcher("/usermanager/listroles.jsp").forward(
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
