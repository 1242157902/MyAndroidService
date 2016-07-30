package cn.ncut.pushservlet;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.zip.ZipEntry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.ncut.pushdomain.Appstore;
import cn.ncut.pushdomain.ClientAppstore;
import cn.ncut.pushdomain.PushContent;
import cn.ncut.pushservice.ApkService;
import cn.ncut.pushservice.AppService;
import cn.ncut.syssetdomain.User;

public class AppServlet extends HttpServlet {
	private static final long serialVersionUID = 4L;
	AppService service = new AppService();
	ApkService apkservice = new ApkService();
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
		String method = request.getParameter("method");
		if (method.equals("GetAppList")) {
			GetAppList(request, response);
		}
		if (method.equals("GetClientAppList")) {
			GetClientAppList(request, response);
		}
		if (method.equals("AddApp")) {
			AddApp(request, response);
		}
		if (method.equals("EditApp")) {
			EditApp(request, response);
		}
		if (method.equals("DeleteApp")) {
			DeleteApp(request, response);
		}
		if (method.equals("CommitDownum")) {
			CommitDownum(request, response);
		}
	}
	public void GetClientAppList(HttpServletRequest request,
			HttpServletResponse response) {
		int appid = Integer.parseInt(request.getParameter("id"));
		String keyword= request.getParameter("key");
		List<ClientAppstore> list = service.GetClientAppList(appid,keyword);
		String json =  JSONArray.fromObject(list).toString() ;
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void GetAppList(HttpServletRequest request,	HttpServletResponse response) {	
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		User user = (User) request.getSession().getAttribute("user");
		List<Appstore> CtList = service.GetAppList(currentPage, pageSize,user.getId());
		int total = service.GetAppTotal(user.getId());
		String json = "{\"total\":" + total + " ,\"rows\":"
				+ JSONArray.fromObject(CtList).toString() + "}";
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	private void AddApp(HttpServletRequest request, HttpServletResponse response) {	
		Appstore App = new  Appstore();
		String aaptPath=this.getServletContext().getRealPath("/appres")+"\\"+"aapt.exe";
		String filesavepath = this.getServletContext().getRealPath("/appres/app_files");
		String iconsavepath = this.getServletContext().getRealPath("/appres/app_icons");
		User user = (User) request.getSession().getAttribute("user");		
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			@SuppressWarnings("unchecked")
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString("utf-8");
					BeanUtils.setProperty(App, name, value);
				} 
				else { 
					String savename= UUID.randomUUID().toString();
					String savefilename =savename+".apk";
					String saveiconname =savename+".png";
					FileOutputStream out = new FileOutputStream(filesavepath+"\\"+savefilename);
					InputStream in=item.getInputStream();
					int len=0;
					byte buffer[]=new byte[1024];
					while ((len=in.read(buffer))>0) {
						out.write(buffer,0,len);
				    }
					in.close();
					out.close();
					item.delete();
					File f= new File(filesavepath+"\\"+savefilename);
					BeanUtils.setProperty(App, "app_size",f.length());
					BeanUtils.setProperty(App, "app_name",savefilename);
					BeanUtils.setProperty(App, "app_downurl","http://www.hp698.com:8080/MyAndroidService/appres/");
					BeanUtils.setProperty(App, "app_icon",ApkService.GetApkIcon(aaptPath,filesavepath+"\\"+savefilename,iconsavepath,saveiconname));
				}	
			}
			App.setUser_id(user.getId());
			service.AddApp(App);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	private String DeleteApp(HttpServletRequest request,HttpServletResponse response) {
		try{
			service.DeleteApp(request.getParameter("id"),request.getParameter("icon"),request.getParameter("apk"));
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "failure";
		}	
		
	}	
	
	public void CommitDownum(HttpServletRequest request,
			HttpServletResponse response) {
		try{
			service.CommitDownum(request.getParameter("id"));
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	private void EditApp(HttpServletRequest request,HttpServletResponse response) {
		Appstore App = new  Appstore();
		String aaptPath=this.getServletContext().getRealPath("/appres")+"\\"+"aapt.exe";
		String filesavepath = this.getServletContext().getRealPath("/appres/app_files");
		String iconsavepath = this.getServletContext().getRealPath("/appres/app_icons");	
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			@SuppressWarnings("unchecked")
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString("utf-8");
					BeanUtils.setProperty(App, name, value);
				} 
				else { 
					if(item.getSize()>0){
						String savename= UUID.randomUUID().toString();
						String savefilename =savename+".apk";
						String saveiconname =savename+".png";
						FileOutputStream out = new FileOutputStream(filesavepath+"\\"+savefilename);
						InputStream in=item.getInputStream();
						int len=0;
						byte buffer[]=new byte[1024];
						while ((len=in.read(buffer))>0) {
							out.write(buffer,0,len);
					    }
						in.close();
						out.close();
						item.delete();
						File f= new File(filesavepath+"\\"+savefilename);
						BeanUtils.setProperty(App, "app_size",f.length());
						BeanUtils.setProperty(App, "app_name",savefilename);
						BeanUtils.setProperty(App, "app_downurl","http://www.hp698.com:8080/MyAndroidService/appres/");
						BeanUtils.setProperty(App, "app_icon",ApkService.GetApkIcon(aaptPath,filesavepath+"\\"+savefilename,iconsavepath,saveiconname));
					}
					else BeanUtils.setProperty(App, "app_size",0);
				}	
			}
			service.EditApp(App);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
