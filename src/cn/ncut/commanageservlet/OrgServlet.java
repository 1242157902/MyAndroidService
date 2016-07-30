package cn.ncut.commanageservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import net.sf.json.JSONArray;
import cn.ncut.commanagedomain.Org;
import cn.ncut.commanageservice.OrgService;
import cn.ncut.syssetdomain.User;
import cn.ncut.syssetservice.UserManagerService;

/**
 * 组织机构管理
 * 
 * @author wzq
 * 
 *         version 1.0 2015-1-9 上午11:49:41
 */
public class OrgServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private OrgService service = new OrgService();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String method = request.getParameter("method");
		if ("getOrgList".equals(method)) {
			getOrgList(request, response);
		} else if ("save".equals(method)) {
			save(request, response);
		} else if ("update".equals(method)) {
			update(request, response);
		} else if ("delete".equals(method)) {
			delete(request, response);
		} else if ("getdepart".equals(method)) {
			getdepart(request, response);
		} else if ("getorgregions".equals(method)) {

			getorgregions(request, response);

		} else if ("getorgarea".equals(method)) {
			getorgarea(request, response);
		}else if ("getOrgList2".equals(method)) {
			getOrgList2(request,response);
		}
		
		
		
	}

	/**由于企业下属机构过多导致树形结构不能快速加载所以改成此模式
	 * @param request
	 * @param response
	 */
	private void getOrgList2(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String id = request.getParameter("id");
            HttpSession session = request.getSession();

			User user = (User) session.getAttribute("user");
			String comno = null;

			if (user == null) {
				try {
					response.getWriter().write("会话过期，请重新登录！");
				} catch (IOException e1) {

					e1.printStackTrace();
				}
			}

			if (user != null) {
				comno = user.getComno();
			}

			int currentPage=1;
			try {
				currentPage = Integer.parseInt(request.getParameter("page"));
			} catch (NumberFormatException e1) {
			     currentPage=1;
			}
			int pageSize=10;
			try {
				pageSize = Integer.parseInt(request.getParameter("rows"));
			} catch (NumberFormatException e1) {
				pageSize=10;
			}
			
			
			String orgname = request.getParameter("searchorgidcard") == null ? "" : request.getParameter("searchorgname");
			String orgregion = request.getParameter("searchorgidcard") == null ? "" : request.getParameter("searchorgidcard");
			
			
			Map<String, String> m = new HashMap<String, String>();
			
			
			if (comno.equals("100000")) {
				m.put("com_name", orgname.trim());
				m.put("com_cardid", orgregion.trim());
			}else {
				m.put("depart_name", orgname.trim());
				m.put("depart_cardid", orgregion.trim());
			}
		
			List<Org> orgList = service.findList(id, comno,currentPage,pageSize,m);
			
			
			int total=service.getTotal(comno,m);
			response.setContentType("text/html;charset=utf-8");
			
			String json = "{\"total\":" + total + " , \"rows\":"
				+ JSONArray.fromObject(orgList).toString() + "}";
			
			response.getWriter()
					.write(json);
			// System.out.println(JSONArray.fromObject(ogrList).toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @param request
	 * @param response
	 */
	private void getorgarea(HttpServletRequest request,
			HttpServletResponse response) {

		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");
		String comno = null;

		if (user == null) {
			try {
				response.getWriter().write("会话过期，请重新登录！");
			} catch (IOException e1) {

				e1.printStackTrace();
			}
		}

		if (user != null) {
			comno = user.getComno();
		}

		List<String> orgareas = service.getorgarea(comno);

		StringBuilder sBuilder = new StringBuilder();

		sBuilder.append("{\"orgareas\":[");

		if (orgareas.size() > 0) {
			for (String orgarea : orgareas) {

				sBuilder.append("{");
				System.out.println(orgarea);
				if (orgarea == null) {
					orgarea = " ";
				}

				sBuilder.append("\"orgarea\":").append("\"" + orgarea + "\"");

				sBuilder.append("}").append(",");

			}

		}
		sBuilder.deleteCharAt(sBuilder.length() - 1);
		sBuilder.append("]}");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		writer.write(sBuilder.toString());
		
	}

	/**
	 * @param request
	 * @param response
	 *            获取所属地区的所有区域
	 * @throws UnsupportedEncodingException 
	 */
	private void getorgregions(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		String orgareaString = request.getParameter("orgarea");
		String orgarea=null;
		try {
			orgarea = java.net.URLDecoder.decode(orgareaString, "utf-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}

		
		
		

		 System.out.println(new String(orgarea.getBytes("iso8859-1"),"UTF-8")+"####################");

		List<String> orgregions = service.getorgregions(new String(orgarea.getBytes("iso8859-1"),"UTF-8"));

		StringBuilder sBuilder = new StringBuilder();

		sBuilder.append("{\"orgregions\":[");

		if (orgregions.size() > 0) {
			for (String orgregion : orgregions) {

				sBuilder.append("{");
				System.out.println(orgregion);
				if (orgregion == null) {
					orgregion = " ";
				}

				sBuilder.append("\"orgregion\":").append(
						"\"" + orgregion + "\"");

				sBuilder.append("}").append(",");

			}
		}

		sBuilder.deleteCharAt(sBuilder.length() - 1);
		sBuilder.append("]}");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(sBuilder.toString());

		writer.write(sBuilder.toString());

	}

	/**
	 * 获取单位信息
	 */
	private void getdepart(HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");
		String comno = null;

		if (user == null) {
			try {
				response.getWriter().write("会话过期，请重新登录！");
			} catch (IOException e1) {

				e1.printStackTrace();
			}
		}

		if (user != null) {
			comno = user.getComno();
		}

		List<Org> list = new ArrayList<Org>();

		try {
			list = service.getChildren(Integer.parseInt(comno));
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (list.size() > 0) {

			StringBuilder sBuilder = new StringBuilder();

			sBuilder.append("{\"orgs\":[");

			for (Org org : list) {

				sBuilder.append("{");

				sBuilder.append("\"id\":").append("\"" + org.getId() + "\"")
						.append(",");
				sBuilder.append("\"orgname\":").append(
						"\"" + org.getOrgname() + "\"");
				sBuilder.append("}").append(",");

			}

			sBuilder.deleteCharAt(sBuilder.length() - 1);
			sBuilder.append("]}");

			
			

			System.out.println(sBuilder.toString());

			writer.write(sBuilder.toString());

		}else {
			writer.write("{\"orgs\":[{\"id\":\"\",\"orgname\":\"\"}]}");
		}

	}

	/**
	 * 获取组织机构数据
	 * 
	 * @param request
	 * @param response
	 */
	private void getOrgList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String id = request.getParameter("id");

			HttpSession session = request.getSession();

			User user = (User) session.getAttribute("user");
			String comno = null;

			if (user == null) {
				try {
					response.getWriter().write("会话过期，请重新登录！");
				} catch (IOException e1) {

					e1.printStackTrace();
				}
			}

			if (user != null) {
				comno = user.getComno();
			}

			
			/*int currentPage=1;
			try {
				currentPage = Integer.parseInt(request.getParameter("page"));
			} catch (NumberFormatException e1) {
			     currentPage=1;
			}
			int pageSize=10;
			try {
				pageSize = Integer.parseInt(request.getParameter("rows"));
			} catch (NumberFormatException e1) {
				pageSize=10;
			}
			*/
			
			List<Org> orgList = service.findList(id, comno);
			
			
			//int total=service.getTotal(id);
			response.setContentType("text/html;charset=utf-8");
			
			//String json = "{\"total\":" + total + " , \"rows\":"
				//	+ JSONArray.fromObject(orgList).toString() + "}";
			
			response.getWriter()
					.write(JSONArray.fromObject(orgList).toString());
			// System.out.println(JSONArray.fromObject(ogrList).toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 保存方法
	 * 
	 * @param request
	 * @param response
	 */
	private void save(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			String orgname = request.getParameter("orgname");
			String orglinkmanname = request.getParameter("orglinkmanname");
			String orglinkmanphonenum = request
					.getParameter("orglinkmanphonenum");
			String orglinkmanemail = request.getParameter("orglinkmanemail");
			String orgaddress = request.getParameter("orgaddress");
			String description = request.getParameter("description");
			String orgarea = request.getParameter("orgarea");
			String orgregion = request.getParameter("orgregion");
			
			String orgidcard=request.getParameter("orgidcard");
			
			

			Org org = new Org();

			
			
			org.setOrgidcard(orgidcard);

			
			org.setOrgname(orgname);
			org.setOrglinkmanname(orglinkmanname);
			org.setOrglinkmanphonenum(orglinkmanphonenum);
			org.setOrglinkmanemail(orglinkmanemail);
			org.setOrgaddress(orgaddress);
			org.setDescription(description);
			org.setOrgarea(orgarea);
			org.setOrgregion(orgregion);
			
			
			
			HttpSession session = request.getSession();

			User user = (User) session.getAttribute("user");
			String comno = null;

			if (user == null) {
				try {
					response.getWriter().write("会话过期，请重新登录！");
				} catch (IOException e1) {

					e1.printStackTrace();
				}
			}

			if (user != null) {
				comno = user.getComno();
			}

			
			
			org.setComno(comno);

			this.service.save(org);
			
			
			

			if ("100000".equals(comno)) {

				int orgid = service.getorgid(orgname);

				String orgidstring = orgid + "";

				// 自动为企业分配账号
				UserManagerService umservice = new UserManagerService();

				umservice.adduser(orgidstring, orgidstring, orgidstring);
				// 自动为企业分配权限

				String user_id = (umservice.finduser(orgidstring, orgidstring))
						.getId();

				String[] idsString = { 1 + "" };
				umservice.updateuserrole(user_id, idsString);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改方法
	 * 
	 * @param request
	 * @param response
	 */
	private void update(HttpServletRequest request, HttpServletResponse response) {
		try {

			String id = request.getParameter("id");
			Org org = new Org();

			String orgname = request.getParameter("orgname");
			String orglinkmanname = request.getParameter("orglinkmanname");
			String orglinkmanphonenum = request
					.getParameter("orglinkmanphonenum");
			String orglinkmanemail = request.getParameter("orglinkmanemail");
			String orgaddress = request.getParameter("orgaddress");
			String description = request.getParameter("description");
			
			String orgarea = request.getParameter("orgarea");
			String orgregion = request.getParameter("orgregion");
			String orgidcard=request.getParameter("orgidcard");
			
			
			HttpSession session = request.getSession();

			User user = (User) session.getAttribute("user");
			String comno = null;

			if (user == null) {
				try {
					response.getWriter().write("会话过期，请重新登录！");
				} catch (IOException e1) {

					e1.printStackTrace();
				}
			}

			if (user != null) {
				comno = user.getComno();
			}

			
			
			
			
			
			
			
			
			org.setOrgidcard(orgidcard);
			org.setId(Integer.parseInt(id));

			org.setOrgname(orgname);
			org.setOrglinkmanname(orglinkmanname);
			org.setOrglinkmanphonenum(orglinkmanphonenum);
			org.setOrglinkmanemail(orglinkmanemail);
			org.setOrgaddress(orgaddress);
			org.setDescription(description);
			org.setOrgarea(orgarea);
			org.setOrgregion(orgregion);
			
			org.setComno(comno);

			this.service.update(org);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除方法
	 * 
	 * @param request
	 * @param response
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response) {

		try {
			
			HttpSession session = request.getSession();

			User user = (User) session.getAttribute("user");
			String comno = null;

			if (user == null) {
				try {
					response.getWriter().write("会话过期，请重新登录！");
				} catch (IOException e1) {

					e1.printStackTrace();
				}
			}

			if (user != null) {
				comno = user.getComno();
			}

			String[] ids = request.getParameter("ids").split(",");
			for (int i = 0; i < ids.length; i++) {
				service.delete(Integer.parseInt(ids[i]),comno);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	/*private void deleteNodes(int id) throws Exception {

		List<Org> olist = this.service.getChildren(id);
		for (Iterator iterator = olist.iterator(); iterator.hasNext();) {
			Org org = (Org) iterator.next();
			int cid = org.getId();
			this.service.delete(cid);
			deleteNodes(cid);
		}
		this.service.delete(id);
	}*/

}
