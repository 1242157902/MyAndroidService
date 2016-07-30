package cn.ncut.pushservlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.ncut.pushdomain.JsonModel;
import cn.ncut.pushdomain.PushAccepter;
import cn.ncut.pushservice.JsonService;

public class JsonServlet extends HttpServlet {
	private static final long serialVersionUID = 4L;
	JsonService service = new JsonService();

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
		String method = request.getParameter("method");
		if (method.equals("GetCompanyList")) {
			GetCompanyList(request, response);
		}
		if (method.equals("GetBigBrandList")) {
			GetBigBrandList(request, response);
		}
		if (method.equals("GetPositionList")) {
			GetPositionList(request, response);
		}
		if (method.equals("GetAreaList")) {
			GetAreaList(request, response);
		}
		if (method.equals("GetBigAreaList")) {
			GetBigAreaList(request, response);
		}
		if (method.equals("GetAppClassList")) {
			GetAppClassList(request, response);
		}
		if (method.equals("GetContentClassList")) {
			GetContentClassList(request, response);
		}
		if (method.equals("GetBrandList")) {
			GetBrandList(request, response);
		}
		if (method.equals("GetMbOsList")) {
			GetMbOsList(request, response);
		}
		if (method.equals("RegisterAccepter")) {
			RegisterAccepter(request, response);
		}
	}
	private void GetPositionList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String ComId=request.getParameter("comid");
			String json ="";
			if(ComId.length()<7&&ComId.length()>0)//单个单位
			{
				List<JsonModel> com=service.GetSinglePositionList(ComId);
				if(com.size()>0) json="[{\"id\":0,\"value\":\"*\",\"text\":\"全部\",\"iconCls\":\"no-icon\",\"children\":[";	
				for (int i = 0 ;i<com.size();i++) { 
                   if(i>0) json+=",";
                   json+="{\"id\":"+(i+1)+",\"value\":\"'"+com.get(i).getId()+"'\",\"text\":\""+com.get(i).getName()+"\",\"iconCls\":\"no-icon\"}";
				}
				if(com.size()>0) json += "]}]";
			}
			else{ //多个单位
				List<JsonModel> com=service.GetDoublePositionList(ComId);
				String code="";
				if(com.size()>0) 
				{
				   json="[{\"id\":0,\"value\":\"*\",\"text\":\"全部\",\"iconCls\":\"no-icon\",\"children\":[";
				   code=com.get(0).getId().substring(0,com.get(0).getId().indexOf("_"));
				}
				int start=0;	
				for (int i = 0 ;i<com.size();i++) {
					 if(code.equals(com.get(i).getId().substring(0,com.get(i).getId().indexOf("_")))) continue;
					 else {
						 json+=JsonDatas(com,start,i-1);
						 start=i;
						 code=com.get(i).getId().substring(0,com.get(i).getId().indexOf("_"));
				     }
				}				
				if(com.size()>0) {json+=JsonDatas(com,start,com.size()-1);json += "]}]";}
			}
			if(json.equals("")) response.getWriter().write("[]");
			else response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	private String JsonDatas(List<JsonModel> com, int start, int end) {
		String json ="";
		if(start>0) json+=",";	
		if(start==end)  json+="{\"id\":"+(start+1)+",\"value\":\"'"+com.get(start).getId()+"'\",\"text\":\""+com.get(start).getName()+"\",\"iconCls\":\"no-icon\"}";
		else {
			json+="{\"id\":"+(start+1)+",\"value\":\"'"+com.get(start).getId()+"'\",\"text\":\""+com.get(start).getName()+"\",\"iconCls\":\"no-icon\",\"children\":[";	
			for (int i = start+1 ;i<=end;i++) { 
               if(i>start+1) json+=",";
               json+="{\"id\":"+(i+1)+",\"value\":\"'"+com.get(i).getId()+"'\",\"text\":\""+com.get(i).getName()+"\",\"iconCls\":\"no-icon\"}";
			}
			if(com.size()>0) json += "]}";
		}
		return json;
	}
	private void RegisterAccepter(HttpServletRequest request,
			HttpServletResponse response) {
		PushAccepter phapk=new PushAccepter();
		phapk.setImei(request.getParameter("imei"));
		service.RegisterAccepter(phapk);
	}
	private void GetMbOsList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<JsonModel> com=service.GetMbOsList();
			String json ="";
			if(com.size()>0) json="[{\"id\":0,\"value\":\"*\",\"text\":\"全部\",\"iconCls\":\"no-icon\",\"children\":[";
			int start=0;	
			List<JsonModel> jm = new ArrayList<JsonModel>();
			for (int i = 0 ;i<com.size();i++) { 
				 if(com.get(i).getId().substring(0,1).equals(com.get(start).getId().substring(0,1))) jm.add(com.get(i));
				 else {if(start>0) json+=",";json+=jsonData(jm);start=i;jm.clear();jm.add(com.get(i));}
				 if(i==com.size()-1) {if(start>0) json+=",";json+=jsonData(jm);}
			}
			if(com!=null) json += "]}]";
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	private void GetBrandList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<JsonModel> com=service.GetBrandList();
			String json ="";
			if(com.size()>0) json="[{\"id\":0,\"value\":\"*\",\"text\":\"全部\",\"iconCls\":\"no-icon\",\"children\":[";
			int start=0;	
			List<JsonModel> jm = new ArrayList<JsonModel>();
			for (int i = 0 ;i<com.size();i++) { 
				 if(com.get(i).getId().substring(0,2).equals(com.get(start).getId().substring(0,2))) jm.add(com.get(i));
				 else {if(start>0) json+=",";json+=jsonData(jm);start=i;jm.clear();jm.add(com.get(i));}
				 if(i==com.size()-1) {if(start>0) json+=",";json+=jsonData(jm);}
			}
			if(com!=null) json += "]}]";
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	private void GetAreaList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<JsonModel> com=service.GetAreaList();
			String json ="";
			if(com.size()>0) json="[{\"id\":0,\"value\":\"*\",\"text\":\"全部\",\"iconCls\":\"no-icon\",\"children\":[";
			int start=0;	
			List<JsonModel> jm = new ArrayList<JsonModel>();
			for (int i = 0 ;i<com.size();i++) { 
				 if(com.get(i).getId().substring(0,2).equals(com.get(start).getId().substring(0,2))) jm.add(com.get(i));
				 else {if(start>0) json+=",";json+=jsonData(jm);start=i;jm.clear();jm.add(com.get(i));}
				 if(i==com.size()-1) {if(start>0) json+=",";json+=jsonData(jm);}
			}
			if(com!=null) json += "]}]";
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void GetAppClassList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<JsonModel> com=service.GetAppClassList();
			String json ="[";
			int start=0;	
			List<JsonModel> jm = new ArrayList<JsonModel>();
			for (int i = 0 ;i<com.size();i++) { 
				 if(com.get(i).getId().substring(0,3).equals(com.get(start).getId().substring(0,3))) jm.add(com.get(i));
				 else {if(start>0) json+=",";json+=jsonDatax(jm);start=i;jm.clear();jm.add(com.get(i));}
				 if(i==com.size()-1) {if(start>0) json+=",";json+=jsonDatax(jm);}
			}
			json += "]";
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void GetContentClassList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<JsonModel> com=service.GetContentClassList();
			String json ="[";
			int start=0;	
			List<JsonModel> jm = new ArrayList<JsonModel>();
			for (int i = 0 ;i<com.size();i++) { 
				 if(com.get(i).getId().substring(0,8).equals(com.get(start).getId().substring(0,8))) jm.add(com.get(i));
				 else {if(start>0) json+=",";json+=jsonDatax(jm);start=i;jm.clear();jm.add(com.get(i));}
				 if(i==com.size()-1) {if(start>0) json+=",";json+=jsonDatax(jm);}
			}
			json += "]";
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String jsonDatax(List<JsonModel> com)
	{
		String json ="";		
		boolean spli=false;
		int start=0;	
		if(com.size()>1) json="{\"id\":\""+com.get(start).getId()+"\",\"value\":\""+com.get(start).getId()+"\",\"text\":\""+com.get(start).getName()+"\",\"state\":\"closed\",\"children\":[";
		else json="{\"id\":\""+com.get(start).getId()+"\",\"value\":\""+com.get(start).getId()+"\",\"text\":\""+com.get(start).getName()+"\"}";
		List<JsonModel> jm = new ArrayList<JsonModel>();
		for (int i = 1 ;i<com.size();i++) { 
			if(com.get(i).getId().length()>com.get(start).getId().length()) {jm.add(com.get(i));start=i;}
			else {if(spli) json+=",";json+=jsonDatax(jm);start=i;spli=true;jm.clear();jm.add(com.get(i));}
			if(i==com.size()-1) {if(spli) json+=",";json+=jsonDatax(jm);}
		}
		if(com.size()>1) json += "]}";
		return json;
	}
	private void GetCompanyList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<JsonModel> com=service.GetCompanyList();
			String json ="";
			if(com.size()>0) json="[{\"id\":0,\"value\":\"*\",\"text\":\"全部\",\"iconCls\":\"no-icon\",\"children\":[";
			for (int i = 0 ;i<com.size();i++) { 
				 if(i>0) json+=",";
			     json+="{\"id\":"+(i+1)+",\"value\":\""+com.get(i).getId()+"\",\"text\":\""+com.get(i).getName()+"\",\"iconCls\":\"no-icon\"}"; 
			 }
			if(com!=null) json += "]}]";
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void GetBigBrandList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<JsonModel> com=service.GetBigBrandList();
			String json ="";
			if(com.size()>0) json="[{\"id\":0,\"value\":\"*\",\"text\":\"全部\",\"iconCls\":\"no-icon\",\"children\":[";
			for (int i = 0 ;i<com.size();i++) { 
				 if(i>0) json+=",";
			     json+="{\"id\":"+(i+1)+",\"value\":\""+com.get(i).getId()+"\",\"text\":\""+com.get(i).getName()+"\",\"iconCls\":\"no-icon\"}"; 
			 }
			if(com!=null) json += "]}]";
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void GetBigAreaList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<JsonModel> com=service.GetBigAreaList();
			String json ="";
			if(com.size()>0) json="[{\"id\":0,\"value\":\"*\",\"text\":\"全部\",\"iconCls\":\"no-icon\",\"children\":[";
			for (int i = 0 ;i<com.size();i++) { 
				 if(i>0) json+=",";
			     json+="{\"id\":"+(i+1)+",\"value\":\""+com.get(i).getId()+"\",\"text\":\""+com.get(i).getName()+"\",\"iconCls\":\"no-icon\"}"; 
			 }
			if(com!=null) json += "]}]";
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private String jsonData(List<JsonModel> com)
	{
		String json ="";		
		boolean spli=false;
		int start=0;	
		if(com.size()>1) json="{\"id\":\""+com.get(start).getId()+"\",\"value\":\""+com.get(start).getId()+"\",\"text\":\""+com.get(start).getName()+"\",\"state\":\"closed\",\"iconCls\":\"no-icon\",\"children\":[";
		else json="{\"id\":\""+com.get(start).getId()+"\",\"value\":\""+com.get(start).getId()+"\",\"text\":\""+com.get(start).getName()+"\",\"iconCls\":\"no-icon\"}";
		List<JsonModel> jm = new ArrayList<JsonModel>();
		for (int i = 1 ;i<com.size();i++) { 
			if(com.get(i).getId().length()>com.get(start).getId().length()) {jm.add(com.get(i));start=i;}
			else {if(spli) json+=",";json+=jsonData(jm);start=i;spli=true;jm.clear();jm.add(com.get(i));}
			if(i==com.size()-1) {if(spli) json+=",";json+=jsonData(jm);}
		}
		if(com.size()>1) json += "]}";
		return json;
	}
}
