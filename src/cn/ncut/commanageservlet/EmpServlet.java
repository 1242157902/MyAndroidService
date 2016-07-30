package cn.ncut.commanageservlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.ncut.commanagedomain.Emp;
import cn.ncut.commanagedomain.Org;
import cn.ncut.commanageservice.EmpService;
import cn.ncut.commanageservice.OrgService;
import cn.ncut.syssetdomain.User;
import cn.ncut.utils.TwoDimensionCode;

import net.sf.json.JSONArray;

/**
 * @author wzq
 * 
 *         version 1.0 2015-1-11 上午11:16:55
 */
public class EmpServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	EmpService service=new EmpService();
	

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String method = request.getParameter("method");
		if (method.equals("save")) {
			save(request, response);
		} else if (method.equals("update")) {
			update(request, response);
		} else if (method.equals("delete")) {
			delete(request, response);
		} else if (method.equals("getlist")) {
			getlist(request, response);
		}

	}

	/**
	 * @param request
	 * @param response
	 */
	private void getlist(HttpServletRequest request,
			HttpServletResponse response) {
		try {

			HttpSession session = request.getSession();
			
			User user = (User) session.getAttribute("user");
			String comno = null;

			if (user == null) {
				
					response.getWriter().write("会话过期，请重新登录！");
				
			}

			if (user != null) {
				comno = user.getComno();
			}


			int currentPage = Integer.parseInt(request.getParameter("page"));
			int pageSize = Integer.parseInt(request.getParameter("rows"));

			String orgarea = request.getParameter("orgarea") == null ? "" : request.getParameter("orgarea");
			String orgregion = request.getParameter("orgregion") == null ? "" : request.getParameter("orgregion");
			String departname2 = request.getParameter("departname2") == null ? "" : request.getParameter("departname2");
			String empno = request.getParameter("empno") == null ? "" : request.getParameter("empno");
			
			Map<String, String> m = new HashMap<String, String>();
			m.put("depart_area", orgarea);
			m.put("depart_region", orgregion.trim());
			m.put("depart_name", departname2);
			m.put("emp_id", empno.trim());
			
			
			
			

			List<Emp> ulist = service.findByPagination(currentPage ,pageSize ,m,comno);
			int total = service.getTotal(m,comno);
			response.setContentType("text/html;charset=utf-8");
		
			 String json =
			 "{\"total\":"+total+" , \"rows\":"+JSONArray.fromObject(ulist).toString()+"}";
			response.getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param request
	 * @param response
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response) {
		
		
		
		
		try {
			String[] ids = request.getParameter("ids").split(",");
			for (int i = 0; i < ids.length; i++) {
				service.zhuxiao(ids[i]);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		

	}

	/**
	 * @param request
	 * @param response
	 */
	private void update(HttpServletRequest request, HttpServletResponse response) {
		
		
		 String id= request.getParameter("id");
		String departname = request.getParameter("departname");//100002-计算机学院
		String orgno=null;
		String orgname=null;
		try {
			if (departname!=null&&!"".equals(departname)) {
				String[] strs=departname.split("-");
				orgno=strs[0];
				orgname=strs[1];
			}
			
		} catch (Exception e) {
			
		}
	
		String empphonenum = request.getParameter("empphonenum");
		String empemail = request.getParameter("empemail");
		String empsex = request.getParameter("empsex");
		String empage = request.getParameter("empage");
		String empname = request.getParameter("empname");
		String empduty = request.getParameter("empduty");
		String empid = request.getParameter("empid");		
		
		

		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		Date d1 = new Date();

		String year = format.format(d1);

		int birthyear = 0;

		try {
			int yearint = Integer.parseInt(year);

			int ageint =Integer.parseInt(empage);

			birthyear = yearint - ageint;
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		
		
		
		
		Emp emp=new Emp();
		
         emp.setId(Integer.parseInt(id));
         emp.setEmpid(empid);
         emp.setDepartno(orgno);
         emp.setDepartname(orgname);
         emp.setPhonenum(empphonenum);
         emp.setEmail(empemail);
         emp.setSex(empsex);
         emp.setAge(birthyear+"");
         emp.setEmpduty(empduty);
		emp.setEmpname(empname);
		
		service.update(emp);
		
		
		
	
		

	}

	/**
	 * 保存员工信息
	 * 
	 * @param request
	 * @param response
	 */
	private void save(HttpServletRequest request, HttpServletResponse response) {
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

		String departname = request.getParameter("departname");//100002-计算机学院
		String orgno=null;
		String orgname=null;
		try {
			
			if (departname!=null&&!"".equals(departname)) {
				String[] strs=departname.split("-");
				orgno=strs[0];
				orgname=strs[1];
			}
			
		  } catch (Exception e) {
			
		}
	
		String empphonenum = request.getParameter("empphonenum");
		String empemail = request.getParameter("empemail");
		String empsex = request.getParameter("empsex");
		String empage = request.getParameter("empage");
		String name = request.getParameter("empname");
		String empduty = request.getParameter("empduty");
		String empid=request.getParameter("empid");
		
		
		
		

		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		Date d1 = new Date();

		String year = format.format(d1);

		int birthyear = 0;

		try {
			int yearint = Integer.parseInt(year);

			int ageint =Integer.parseInt(empage);

			birthyear = yearint - ageint;
		} catch (Exception e) {
			e.printStackTrace();
		}

		OrgService orgService=new OrgService();
		Org org=new Org();
		try {
			org = orgService.findById(Integer.parseInt(orgno));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		Emp emp=new Emp();
		emp.setEmpid(empid);
		
         emp.setComno(comno);
         emp.setDepartno(orgno);
         emp.setDepartname(orgname);
         emp.setPhonenum(empphonenum);
         emp.setEmail(empemail);
         emp.setSex(empsex);
         emp.setAge(birthyear+"");
         emp.setEmpname(name);
         emp.setDepart_area(org.getOrgarea());
         emp.setDepart_region(org.getOrgregion());
         emp.setEmpduty(empduty);
		
		
		 service.save(emp);
		
		//查询刚刚生成的员工编号
		
		int id=service.query(emp);
		
		
		
         String encoderContent="yihua-"+comno+"-"+orgno+"-"+id;
		
		String imgPath = this.getServletContext().getRealPath("/images");;
		 String imagename=encoderContent+".png";
         imgPath=imgPath+"/erweima/"+imagename;
        TwoDimensionCode handler = new TwoDimensionCode();  
        handler.encoderQRCode(encoderContent, imgPath, "png"); 
        
        
        service.updateemppicname(encoderContent,id);
		
		
		
		
	

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
