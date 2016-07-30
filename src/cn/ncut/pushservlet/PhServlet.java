package cn.ncut.pushservlet;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import net.sf.json.JSONArray;
import cn.ncut.pushdomain.PushAccepter;
import cn.ncut.pushdomain.ComDefQue;
import cn.ncut.pushdomain.PushItem;
import cn.ncut.pushdomain.PushQueTitle;
import cn.ncut.pushdomain.QueryRecCon;
import cn.ncut.pushservice.PhService;
import cn.ncut.syssetdomain.User;

public class PhServlet extends HttpServlet {
		private static final long serialVersionUID = 3L;
		PhService service = new PhService();
		public void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			doGet(request, response);
		}
		public void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {	
			String method = request.getParameter("method");
			if (method.equals("GetPushList")) {
				GetPushList(request, response);
			}
			if (method.equals("GetPushQueTitleList")) {
				GetPushQueTitleList(request, response);
			}
			if (method.equals("GetComDefQueList")) {
				GetComDefQueList(request, response);
			}
			if (method.equals("AddPushItem")) {
				AddPushItem(request, response);
			}
			if (method.equals("UpdatePushItem")) {
				UpdatePushItem(request, response);
			}
			if (method.equals("DeletePushItem")) {
				DeletePushItem(request, response);
			}
			if (method.equals("GetAccepterList")) {
				GetAccepterList(request, response);
			}
			if (method.equals("SingleOkPush")) {
				SingleOkPush(request, response);
			}
			if (method.equals("SingleCancelPush")) {
				SingleCancelPush(request, response);
			}
			if (method.equals("BatchOkPush")) {
				BatchOkPush(request, response);
			}
			if (method.equals("BatchCancelPush")) {
				BatchCancelPush(request, response);
			}
			if (method.equals("GetCurUserType")) {
				GetCurUserType(request, response);
			}
		}		
	
		private void DeletePushItem(HttpServletRequest request,
				HttpServletResponse response) {
			int pid = Integer.parseInt(request.getParameter("pid"));	
			try {
				service.DeletePushItem(pid);
				JSONObject result = new JSONObject();
				result.put("deletephitem", "success");			
				response.getWriter().print(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		private void BatchOkPush(HttpServletRequest request,
				HttpServletResponse response) {
		    User user = (User) request.getSession().getAttribute("user");
		    String operId=user.getId();
			String imeis = request.getParameter("imeis");	
			String pid= request.getParameter("pid");		
			try {
				service.BatchOkPush(imeis,pid,operId);
				JSONObject result = new JSONObject();
				result.put("batchokpush", "success");			
				response.getWriter().print(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		private void BatchCancelPush(HttpServletRequest request,
				HttpServletResponse response) {    
			User user = (User) request.getSession().getAttribute("user");
			String operId=user.getId();
			String imeis = request.getParameter("imeis");	
			String pid= request.getParameter("pid");		
			try {
				service.BatchCancelPush(imeis,pid,operId);
				JSONObject result = new JSONObject();
				result.put("batchcancelpush", "success");			
				response.getWriter().print(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		private void SingleOkPush(HttpServletRequest request,
				HttpServletResponse response) {
	        User user = (User) request.getSession().getAttribute("user");
			String operId=user.getId();
			String imei = request.getParameter("imei");	
			String pid= request.getParameter("pid");	
			try {
				service.SingleOkPush(imei,pid,operId);
				JSONObject result = new JSONObject();
				result.put("singleokpush", "success");			
				response.getWriter().print(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		private void SingleCancelPush(HttpServletRequest request,
				HttpServletResponse response) {   
			User user = (User) request.getSession().getAttribute("user");
			String operId=user.getId();
			String imei = request.getParameter("imei");	
			String pid= request.getParameter("pid");	
			try {
				service.SingleCancelPush(imei,pid,operId);
				JSONObject result = new JSONObject();
				result.put("singlecancelpush", "success");			
				response.getWriter().print(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		private void UpdatePushItem(HttpServletRequest request,
				HttpServletResponse response) {
	        User user = (User) request.getSession().getAttribute("user");
			int ptype=Integer.parseInt(request.getParameter("ptype"));
			String pushtime=request.getParameter("pushtime");
			PushItem phitem=new PushItem();
			String pid=request.getParameter("pid");
			phitem.setPid(Integer.parseInt(pid));
			phitem.setTitle(request.getParameter("title"));
			phitem.setQueid(Integer.parseInt(request.getParameter("queid")));
			phitem.setPriori(Integer.parseInt(request.getParameter("priori")));
			phitem.setEnddate(request.getParameter("enddate"));
			phitem.setPtype(ptype);
			if(ptype==0) 
			{
			  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			  pushtime=df.format(new Date());
			}
			phitem.setPushtime(pushtime);
			phitem.setOper(user.getId());
	        try {
	        	service.UpdatePushItem(phitem);
	 		    request.setAttribute("pid",pid);
			    request.getRequestDispatcher("/pages/push/PushUpdateSuccess.jsp").forward(request,response);
	        } catch (Exception e) {
				e.printStackTrace();
			}	
		}
		
		private void AddPushItem(HttpServletRequest request,
				HttpServletResponse response) {
	        User user = (User) request.getSession().getAttribute("user");
			int ptype=Integer.parseInt(request.getParameter("ptype"));
			String pushtime=request.getParameter("pushtime");
			PushItem phitem=new PushItem();
			phitem.setTitle(request.getParameter("title"));
			phitem.setQueid(Integer.parseInt(request.getParameter("queid")));
			phitem.setPriori(Integer.parseInt(request.getParameter("priori")));
			phitem.setEnddate(request.getParameter("enddate"));
			phitem.setPtype(ptype);
			if(ptype==0) 
			{
			  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			  pushtime=df.format(new Date());
			}
			phitem.setPushtime(pushtime);
			phitem.setOper(user.getId());
	        try {
	        	service.AddPushItem(phitem);
			    request.getRequestDispatcher("/pages/push/PushAddSuccess.jsp").forward(request,response);
	        } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
		}
		
		private void GetPushList(HttpServletRequest request,
				HttpServletResponse response) {		
			int currentPage = Integer.parseInt(request.getParameter("page"));
			int pageSize = Integer.parseInt(request.getParameter("rows"));
	        User user = (User) request.getSession().getAttribute("user");
	        String conString=" where flag='1' ";
	        if(user.getComno().length()>5&&!user.getComno().equals("100000"))  conString=conString+" and com_no='"+user.getComno()+"' ";
			List<PushItem> PushList = service.GetPushList(currentPage, pageSize,conString);
			int total = service.GetPushTotal(conString);
			String json = "{\"total\":" + total + " , \"rows\":"
					+ JSONArray.fromObject(PushList).toString() + "}";
			try {
				response.getWriter().write(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private void GetPushQueTitleList(HttpServletRequest request,
				HttpServletResponse response) {		
	        User user = (User) request.getSession().getAttribute("user");
			List<PushQueTitle> PushQueTitleList = service.GetPushQueTitleList(user.getId());
			String json = JSONArray.fromObject(PushQueTitleList).toString();
			try {
				response.getWriter().write(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		private void GetComDefQueList(HttpServletRequest request,
				HttpServletResponse response) {		
	        User user = (User) request.getSession().getAttribute("user");
			List<ComDefQue> ComDefQueList = service.GetComDefQueList(user.getComno());
			String json = JSONArray.fromObject(ComDefQueList).toString();
			try {
				response.getWriter().write(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
				
		private void GetAccepterList(HttpServletRequest request,
				HttpServletResponse response) {	
			try {
				int currentPage = Integer.parseInt(request.getParameter("page"));
				int pageSize = Integer.parseInt(request.getParameter("rows"));
				QueryRecCon qrc=new QueryRecCon();
				User user = (User) request.getSession().getAttribute("user");
				qrc.setPtlid(request.getParameter("ptlid"));
				qrc.setCompany(request.getParameter("qrccompany"));
				qrc.setSeller(request.getParameter("qrcseller"));
			    if(!"100000".equals(user.getComno())&&user.getIsseller()==1)  qrc.setSeller(user.getComno()); 
			    if(!"100000".equals(user.getComno())&&user.getIsseller()==0)  qrc.setCompany(user.getComno());
				qrc.setGender(request.getParameter("qrcsex"));
				qrc.setAge(request.getParameter("qrcage"));
				qrc.setMbno(request.getParameter("phonenum"));
				qrc.setMbos(request.getParameter("qrcphoneos"));
				qrc.setProvider(request.getParameter("qrcprovi"));
				qrc.setPhstatus(request.getParameter("qrcphstatus"));
				qrc.setRecstatus(request.getParameter("qrcrecstatus"));
				qrc.setArea(request.getParameter("qrcarea"));
				qrc.setMbtype(request.getParameter("qrcbrand"));
				qrc.setPosition(request.getParameter("qrcposition"));
			    List<PushAccepter> PhDTL =service.GetAccepterList(currentPage,pageSize,qrc);
				int total= service.GetAccepterTotal(qrc);
				String json = "{\"total\":" + total + " , \"rows\":"+ JSONArray.fromObject(PhDTL).toString() + "}";
				response.getWriter().write(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private void GetCurUserType(HttpServletRequest request,
				HttpServletResponse response) {
			try {
				String role="3";//非管理员非销售商
		        User user = (User) request.getSession().getAttribute("user");
		        if(user.getIsseller()==1) role="2";//非管理员是销售商
		        //String x= new String(service.getCompany(user.getComno()).getBytes("ISO-8859-1"),"UTF-8"); 
		        role=role+"*"+user.getComno()+"*"+service.getCompany(user.getComno()); 
		        if(user.getComno().equals("100000"))  role="1";//是管理员
		       // if(user.getComno().matches("100000(.*)")) role="1";//是管理员	
		        JSONObject result = new JSONObject();
		        result.put("role", role);	
				response.getWriter().print(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
}
