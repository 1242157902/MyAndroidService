package cn.ncut.pushservlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import net.sf.json.JSONArray;
import cn.ncut.pushdomain.AutoPushLog;
import cn.ncut.pushdomain.ComBox;
import cn.ncut.pushdomain.ContentClass;
import cn.ncut.pushdomain.PushQue;
import cn.ncut.pushservice.QueService;
import cn.ncut.syssetdomain.User;
import push.AutoPushService;

public class QueServlet extends HttpServlet {
	private static final long serialVersionUID = 2L;
	QueService service = new QueService();
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
		String method = request.getParameter("method");	
		if (method.equals("GetQueueList")) {
			GetQueueList(request, response);
		}
		if (method.equals("AddQueue")) {
			AddQueue(request, response);
		}
		if (method.equals("UpdateQueue")) {
			UpdateQueue(request, response);
		}
		if (method.equals("SortQueue")) {
			SortQueue(request, response);
		}
		if (method.equals("DeleteQueue")) {
			DeleteQueue(request, response);
		}	
		if (method.equals("SetDefaultQueId")) {
			SetDefaultQueId(request, response);
		}
		if (method.equals("GetContClasslist")) {
			GetContClasslist(request, response);
		}
		if (method.equals("GetWeiDuCode")) {
			GetWeiDuCode(request, response);
		}
		if (method.equals("GetContClassNamelist")) {
			GetContClassNamelist(request, response);
		}
		if (method.equals("DeleteContClass")) {
			DeleteContClass(request, response);
		}
		if (method.equals("EditContClass")) {
			EditContClass(request, response);
		}
		if (method.equals("AddContClass")) {
			AddContClass(request, response);
		}
		if (method.equals("AddPushItem")) {
			AddPushItem(request, response);
		}
		if (method.equals("GetPushItemlist")) {
			GetPushItemlist(request, response);
		}
		if (method.equals("GetPushLogUserList")) {
			GetPushLogUserList(request, response);
		}
	}
	
	private void SetDefaultQueId(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			service.SetDefaultQueId(Integer.parseInt(request.getParameter("com_no")),Integer.parseInt(request.getParameter("qid")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private void DeleteQueue(HttpServletRequest request,
			HttpServletResponse response) {
		int qid = Integer.parseInt(request.getParameter("qid"));
		try {
			String msg=service.DeleteQueue(qid);
			JSONObject result = new JSONObject();
			result.put("deletequeue",msg);			
			response.getWriter().print(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void SortQueue(HttpServletRequest request,
			HttpServletResponse response) {
		String qid=request.getParameter("qid");
		String sortque=request.getParameter("sortque");
        try {
		   service.SortQueue(qid,sortque);
		   request.setAttribute("qid",qid);
		   request.getRequestDispatcher("/pages/push/QueueSortSuccess.jsp").forward(request,response);
        } catch (Exception e) {
			e.printStackTrace();
		}		
	}

	private void UpdateQueue(HttpServletRequest request,
			HttpServletResponse response) {
		String qid=request.getParameter("qid");
		String title=request.getParameter("title");
		String queue=request.getParameter("que");
        try {
		   service.UpdateQueue(qid,title,queue);
		   request.setAttribute("qid",qid);
		   request.getRequestDispatcher("/pages/push/QueueUpdateSuccess.jsp").forward(request,response);
        } catch (Exception e) {
			e.printStackTrace();
		}		
	}

	private void AddQueue(HttpServletRequest request,
			HttpServletResponse response) {
		String title=request.getParameter("title");
		String queue=request.getParameter("que");
        User user = (User) request.getSession().getAttribute("user");
        try {
		service.AddQueue(title,queue,user.getId());
		request.getRequestDispatcher("/pages/push/QueueAddSuccess.jsp").forward(request,response);
        } catch (Exception e) {
			e.printStackTrace();
		}		
	}

	private void GetQueueList(HttpServletRequest request,
			HttpServletResponse response) {
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
        User user = (User) request.getSession().getAttribute("user");  
        String conString=" where flag='1' ";
        if(user.getComno().length()>5&&!user.getComno().equals("100000"))  conString=conString+" and com_no='"+user.getComno()+"' ";
		List<PushQue> QueList = service.GetQueList(currentPage, pageSize,conString);
		int total = service.GetQueTotal(conString);
		String json = "{\"total\":" + total + " ,\"rows\":"
				+ JSONArray.fromObject(QueList).toString() + "}";
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	private void GetContClasslist(HttpServletRequest request,
			HttpServletResponse response) {		
		List<ContentClass> list = service.GetContClasslist(request.getParameter("id"));
		String json = "{\"total\":" + list.size() + " , \"rows\":"
				+ JSONArray.fromObject(list).toString() + "}";
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void GetWeiDuCode(HttpServletRequest request,
			HttpServletResponse response) {		
		String code=request.getParameter("code");
		String json="";
		try {
			json=service.GetWeiDuCode(code);
			response.getWriter().write(json);
		} catch(Exception e) {
			json="failure";
			try {
				response.getWriter().write(json);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	private void GetContClassNamelist(HttpServletRequest request,
			HttpServletResponse response) {	
		List<ContentClass> list = service.GetContClasslistX();
		String json = "{\"total\":" + list.size() + " , \"rows\":"
				+ JSONArray.fromObject(list).toString() + "}";
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void DeleteContClass(HttpServletRequest request,
			HttpServletResponse response) {
		String code=request.getParameter("code");
        try {
		   service.DeleteContClass(code);
		   response.getWriter().write("success");
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private void EditContClass(HttpServletRequest request,
			HttpServletResponse response) {
        try {
    		ContentClass item= new ContentClass();
    		item.setId(request.getParameter("id"));
    		//item.setClass_value(request.getParameter("class_value"));
    		item.setClass_value(new String(request.getParameter("class_value").getBytes("ISO-8859-1"),"UTF-8"));
    		//item.setClass_name(request.getParameter("class_name"));
    		item.setClass_name(new String(request.getParameter("class_name").getBytes("ISO-8859-1"),"UTF-8"));
    		item.setClass_queid(request.getParameter("class_queid"));
    		item.setClass_duration(request.getParameter("class_duration"));
		   service.EditContClass(item);
		   response.getWriter().write("success");
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	

	private void AddContClass(HttpServletRequest request,
			HttpServletResponse response) {
        try {
    		ContentClass item= new ContentClass();
    		item.setId(request.getParameter("id"));
    		//item.setClass_value(request.getParameter("class_value"));
    		item.setClass_value(new String(request.getParameter("class_value").getBytes("ISO-8859-1"),"UTF-8"));
    		//item.setClass_name(request.getParameter("class_name"));
    		item.setClass_name(new String(request.getParameter("class_name").getBytes("ISO-8859-1"),"UTF-8"));
    		item.setClass_queid(request.getParameter("class_queid"));
    		item.setClass_duration(request.getParameter("class_duration"));
		   service.AddContClass(item);
		   response.getWriter().write("success");
        } catch (Exception e) {
 		   try {
			response.getWriter().write("failure");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
	}
	

	private void AddPushItem(HttpServletRequest request,
			HttpServletResponse response) {
		 try {
	    		String queid=request.getParameter("queid");
	    		String startt=request.getParameter("startt");
	    		String endt=request.getParameter("endt");
	    		String imeis=request.getParameter("imeis");
	    		AutoPushService xx=new  AutoPushService();	    		
	    		xx.PushToSome(startt,endt, queid, imeis.split(","));
	    		AutoPushLog item=new AutoPushLog();
	    		item.setQueid(queid);
	    		item.setStartt(startt);
	    		item.setEndt(endt);
	    		item.setImeis(imeis);
	    		service.AddAutoPushLog(item);
			    response.getWriter().write("success");
	        } catch (Exception e) {
				e.printStackTrace();
			}		
	}
	
	private void GetPushItemlist(HttpServletRequest request,
			HttpServletResponse response) {		
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		List<AutoPushLog> list = service.GetPushItemlist(currentPage, pageSize);
		int total = service.GetPushItemTotal();
		String json = "{\"total\":" + total + " ,\"rows\":"
				+ JSONArray.fromObject(list).toString() + "}";
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void GetPushLogUserList(HttpServletRequest request,
			HttpServletResponse response) {	
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		String logid =request.getParameter("logid");
		List<ComBox> list = service.GetPushLogUserList(currentPage, pageSize,logid);
		int total = service.GetPushLogUserTotal(logid);
		String json = "{\"total\":" + total + " ,\"rows\":"
				+ JSONArray.fromObject(list).toString() + "}";
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
