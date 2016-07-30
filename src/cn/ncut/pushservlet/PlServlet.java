package cn.ncut.pushservlet;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import net.sf.json.JSONArray;
import cn.ncut.pushdomain.ComBox;
import cn.ncut.pushdomain.ErrorLog;
import cn.ncut.pushdomain.PushAccepter;
import cn.ncut.pushdomain.PushLog;
import cn.ncut.pushdomain.QueryRecCon;
import cn.ncut.pushdomain.RcvLog;
import cn.ncut.pushdomain.ThemeLog;
import cn.ncut.pushdomain.ThemeTask;
import cn.ncut.pushservice.PlService;
import cn.ncut.syssetdomain.User;

public class PlServlet extends HttpServlet {
	private static final long serialVersionUID = 4L;
	PlService service = new PlService();

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
		String method = request.getParameter("method");
		if (method.equals("GetPushLogList")) {
			GetPushLogList(request, response);
		}
		if (method.equals("GetPushMsgList")) {
			GetPushMsgList(request, response);
		}
		if (method.equals("GetErrorTypeList")) {
			GetErrorTypeList(request, response);
		}
		if (method.equals("GetPushQueList")) {
			GetPushQueList(request, response);
		}
		if (method.equals("GetThemeTaskList")) {
			GetThemeTaskList(request, response);
		}
		if (method.equals("AddMbthemeItem")) {
			AddMbthemeItem(request, response);
		}
		if (method.equals("EditMbthemeItem")) {
			EditMbthemeItem(request, response);
		}
		if (method.equals("DeleteMbthemeItem")) {
			DeleteMbthemeItem(request, response);
		}
		if (method.equals("GetThemeLogList")) {
			GetThemeLogList(request, response);
		}
		if (method.equals("GetRcvLogList")) {
			GetRcvLogList(request, response);
		}
		if (method.equals("GetErrorLogList")) {
			GetErrorLogList(request, response);
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

	}

	private void GetErrorTypeList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<ComBox> list=service.GetErrorTypeList();
			String json ="";
			if(list.size()>0) json="[{\"id\":0,\"value\":\"*\",\"text\":\"全部\",\"iconCls\":\"no-icon\",\"children\":[";
			for (int i = 0 ;i<list.size();i++) { 
				 if(i>0) json+=",";
			     json+="{\"id\":"+(i+1)+",\"value\":\""+list.get(i).getId()+"\",\"text\":\""+list.get(i).getName()+"\",\"iconCls\":\"no-icon\"}"; 
			 }
			if(list!=null) json += "]}]";
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	private void GetErrorLogList(HttpServletRequest request,
			HttpServletResponse response) {
		try{
			int currentPage = Integer.parseInt(request.getParameter("page"));
			int pageSize = Integer.parseInt(request.getParameter("rows"));
			ErrorLog qrc=new ErrorLog();
			qrc.setImei(request.getParameter("errorlog_imei"));
			qrc.setErrtype(request.getParameter("errorlog_type"));
			String start="null";
			String end="null";
			if(request.getParameter("errorlog_start")!=null&&!request.getParameter("errorlog_start").equals(""))
				start=request.getParameter("errorlog_start");
			if(request.getParameter("errorlog_end")!=null&&!request.getParameter("errorlog_end").equals(""))
				end=request.getParameter("errorlog_end");
			qrc.setErrtime(start+"*"+end);
		    List<ErrorLog> slist =service.GetErrorLogList(currentPage,pageSize,qrc);
			int total= service.GetErrorLogTotal(qrc);
			String json = "{\"total\":" + total + " , \"rows\":"+ JSONArray.fromObject(slist).toString() + "}";
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private void GetThemeLogList(HttpServletRequest request,
			HttpServletResponse response) {
		try{
			int currentPage = Integer.parseInt(request.getParameter("page"));
			int pageSize = Integer.parseInt(request.getParameter("rows"));
			ThemeLog qrc=new ThemeLog();
			qrc.setImei(request.getParameter("themelog_imei"));
			qrc.setMbno(request.getParameter("themelog_mbno"));
			qrc.setCurtheme(request.getParameter("themelog_curtheme"));
			qrc.setExptheme(request.getParameter("themelog_exptheme"));
			qrc.setOpersrc(request.getParameter("themelog_opersrc"));
			String start="null";
			String end="null";
			if(request.getParameter("themelog_start")!=null&&!request.getParameter("themelog_start").equals(""))
				start=request.getParameter("themelog_start");
			if(request.getParameter("themelog_end")!=null&&!request.getParameter("themelog_end").equals(""))
				end=request.getParameter("themelog_end");
			qrc.setOpertime(start+"*"+end);
		    List<ThemeLog> slist =service.GetThemeLogList(currentPage,pageSize,qrc);
			int total= service.GetThemeLogTotal(qrc);
			String json = "{\"total\":" + total + " , \"rows\":"+ JSONArray.fromObject(slist).toString() + "}";
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void GetPushMsgList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<ComBox> list=service.GetPushMsgList();
			String json ="";
			if(list.size()>0) json="[{\"id\":0,\"value\":\"*\",\"text\":\"全部\",\"iconCls\":\"no-icon\",\"children\":[";
			for (int i = 0 ;i<list.size();i++) { 
				 if(i>0) json+=",";
			     json+="{\"id\":"+(i+1)+",\"value\":\""+list.get(i).getId()+"\",\"text\":\""+list.get(i).getName()+"\",\"iconCls\":\"no-icon\"}"; 
			 }
			if(list!=null) json += "]}]";
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	private void GetPushQueList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<ComBox> list=service.GetPushQueList();
			String json ="";
			if(list.size()>0) json="[{\"id\":0,\"value\":\"*\",\"text\":\"全部\",\"iconCls\":\"no-icon\",\"children\":[";
			for (int i = 0 ;i<list.size();i++) { 
				 if(i>0) json+=",";
			     json+="{\"id\":"+(i+1)+",\"value\":\""+list.get(i).getId()+"\",\"text\":\""+list.get(i).getName()+"\",\"iconCls\":\"no-icon\"}"; 
			 }
			if(list!=null) json += "]}]";
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	private void GetPushLogList(HttpServletRequest request,
			HttpServletResponse response) {
		try{
			int currentPage = Integer.parseInt(request.getParameter("page"));
			int pageSize = Integer.parseInt(request.getParameter("rows"));
			PushLog qrc=new PushLog();
			qrc.setImei(request.getParameter("pushlog_imei"));
			qrc.setMbno(request.getParameter("pushlog_mbno"));
			qrc.setOper(request.getParameter("pushlog_oper"));
			
			qrc.setPhmsg(request.getParameter("pushlog_puid"));
			String start="null";
			String end="null";
			if(request.getParameter("pushlog_start")!=null&&!request.getParameter("pushlog_start").equals(""))
				start=request.getParameter("pushlog_start");
			if(request.getParameter("pushlog_end")!=null&&!request.getParameter("pushlog_end").equals(""))
				end=request.getParameter("pushlog_end");
			qrc.setOpertime(start+"*"+end);
		    List<PushLog> slist =service.GetPushLogList(currentPage,pageSize,qrc);
			int total= service.GetPushLogTotal(qrc);
			String json = "{\"total\":" + total + " , \"rows\":"+ JSONArray.fromObject(slist).toString() + "}";
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
   private void GetRcvLogList(HttpServletRequest request,
			HttpServletResponse response) {
		try{
			int currentPage = Integer.parseInt(request.getParameter("page"));
			int pageSize = Integer.parseInt(request.getParameter("rows"));
			RcvLog rcv=new RcvLog();
			rcv.setImei(request.getParameter("rcvlog_imei"));
			rcv.setMbno(request.getParameter("rcvlog_mbno"));
			rcv.setType(request.getParameter("rcvlog_type"));
			rcv.setPhmsg(request.getParameter("rcvlog_puid"));	
			String start="null";
			String end="null";
			if(request.getParameter("rcvlog_start")!=null&&!request.getParameter("rcvlog_start").equals(""))
				start=request.getParameter("rcvlog_start");
			if(request.getParameter("rcvlog_end")!=null&&!request.getParameter("rcvlog_end").equals(""))
				end=request.getParameter("rcvlog_end");
			rcv.setAcctime(start+"*"+end);
		    List<RcvLog> slist =service.GetRcvLogList(currentPage,pageSize,rcv);
			int total=service.GetRcvLogTotal(rcv);
			String json = "{\"total\":" + total + " , \"rows\":"+ JSONArray.fromObject(slist).toString() + "}";
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void GetThemeTaskList(HttpServletRequest request,
			HttpServletResponse response) {		
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
        User user = (User) request.getSession().getAttribute("user");
		List<ThemeTask> list = service.GetThemeTaskList(currentPage, pageSize,user.getId());
		int total = service.GetThemeTaskTotal(user.getId());
		String json = "{\"total\":" + total + " , \"rows\":"
				+ JSONArray.fromObject(list).toString() + "}";
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String AddMbthemeItem(HttpServletRequest request,
			HttpServletResponse response) {
	   try {
	        User user = (User) request.getSession().getAttribute("user");
			ThemeTask item=new ThemeTask();
			item.setTname(request.getParameter("tname"));
			item.setTheme(request.getParameter("theme"));
			item.setTtime(request.getParameter("ttime"));
			item.setOperator(user.getId());
	        service.AddMbthemeItem(item);
	        return "success";
        } catch (Exception e) {
			e.printStackTrace();
	        return "failure";
		}	
	}

	private String EditMbthemeItem(HttpServletRequest request,
			HttpServletResponse response) {
        try {
	        User user = (User) request.getSession().getAttribute("user");
			ThemeTask item=new ThemeTask();
			item.setTid(Integer.parseInt(request.getParameter("tid")));
			item.setTname(request.getParameter("tname"));
			item.setTheme(request.getParameter("theme"));
			item.setTtime(request.getParameter("ttime"));
			item.setOperator(user.getId());
	        service.UpdateMbthemeItem(item);
	        return "success";
        } catch (Exception e) {
			e.printStackTrace();
	        return "failure";
		}	
	}
	
	private void DeleteMbthemeItem(HttpServletRequest request,
			HttpServletResponse response) {
		int tid = Integer.parseInt(request.getParameter("tid"));	
		try {
			service.DeleteMbthemeItem(tid);
			JSONObject result = new JSONObject();
			result.put("deleteMbThemeItem", "success");			
			response.getWriter().print(result);
		} catch (Exception e) {
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
			qrc.setPtlid(request.getParameter("mbtid"));
			qrc.setCompany(request.getParameter("mbtcompany"));
			qrc.setSeller(request.getParameter("mbtseller"));
		    if(!"100000".equals(user.getComno())&&user.getIsseller()==1)  qrc.setSeller(user.getComno()); 
		    if(!"100000".equals(user.getComno())&&user.getIsseller()==0)  qrc.setCompany(user.getComno());
			qrc.setGender(request.getParameter("mbtsex"));
			qrc.setAge(request.getParameter("mbtage"));
			qrc.setMbno(request.getParameter("mbtno"));
			qrc.setMbos(request.getParameter("mbtphoneos"));
			qrc.setProvider(request.getParameter("mbtprovi"));
			qrc.setPhstatus(request.getParameter("mbtphstatus"));
			qrc.setArea(request.getParameter("mbtarea"));
			qrc.setMbtype(request.getParameter("mbtbrand"));
		    List<PushAccepter> PhDTL =service.GetAccepterList(currentPage,pageSize,qrc);
			int total= service.GetAccepterTotal(qrc);
			String json = "{\"total\":" + total + " , \"rows\":"+ JSONArray.fromObject(PhDTL).toString() + "}";
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    private void BatchOkPush(HttpServletRequest request,
			HttpServletResponse response) {
	    User user = (User) request.getSession().getAttribute("user");
	    String operId=user.getId();
		String imeis = request.getParameter("imeis");	
		String tid= request.getParameter("tid");		
		try {
			service.BatchOkPush(imeis,tid,operId);
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
		String tid= request.getParameter("tid");		
		try {
			service.BatchCancelPush(imeis,tid,operId);
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
		String tid= request.getParameter("tid");	
		try {
			service.SingleOkPush(imei,tid,operId);
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
		String tid= request.getParameter("tid");	
		try {
			service.SingleCancelPush(imei,tid,operId);
			JSONObject result = new JSONObject();
			result.put("singlecancelpush", "success");			
			response.getWriter().print(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
