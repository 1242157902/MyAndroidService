package cn.ncut.pushservlet;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import net.sf.json.JSONArray;
import cn.ncut.pushdomain.PushContent;
import cn.ncut.pushservice.CtService;
import cn.ncut.pushservice.CutPicService;
import cn.ncut.syssetdomain.User;

public class CtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CtService service = new CtService();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
		String method = request.getParameter("method");
		if (method.equals("GetContentList")) {
			GetContentList(request, response);
		}
		if (method.equals("AddContent")) {
			AddContent(request, response);
		}
		if (method.equals("DeleteContent")) {
			DeleteContent(request, response);
		}
		if (method.equals("UpdateContent")) {
			UpdateContent(request, response);
		}
		if (method.equals("GetFileSize")) {
			GetFileSize(request, response);
		}
	}
	
	private void GetFileSize(HttpServletRequest request,
			HttpServletResponse response) {
		long size=0;
        DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
		
			@SuppressWarnings("unchecked")
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem fileItem : list) {
				if(!fileItem.isFormField()) {
					size=fileItem.getSize();break;
				}
			}
			JSONObject result = new JSONObject();
			result.put("fsize",size);			
			response.getWriter().print(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void UpdateContent(HttpServletRequest request,
			HttpServletResponse response) {
		PushContent theCt = new PushContent();
		String savepath = this.getServletContext().getRealPath("/images/pics");
		User user = (User) request.getSession().getAttribute("user");
		String orifilepath=savepath+ "\\"+request.getParameter("filenamex");
		int imgx = 0,imgy=0,imgw=0,imgh=0;
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			@SuppressWarnings("unchecked")
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString("utf-8");
					if(name.equals("imgx")||name.equals("imgy")||name.equals("imgw")||name.equals("imgh")) 
				    {
						if(name.equals("imgx")) imgx = Integer.parseInt(value);
						if(name.equals("imgy")) imgy = Integer.parseInt(value);
						if(name.equals("imgw")) imgw = Integer.parseInt(value);
						if(name.equals("imgh")) imgh = Integer.parseInt(value);
						continue;
				    }
					BeanUtils.setProperty(theCt, name, value); 
				} else {
                    if(item.getName()!="")
                    {
                    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式		
    					int rand=new Random().nextInt(10);
    					String ext=CutPicService.getFormatInFile(item.getInputStream());
    					
    					String savefilename ="P_"+df.format(new Date())+"_"+user.getId()+"_"+rand+"."+ext;
    					BeanUtils.setProperty(theCt, "namex",savefilename);
    					FileOutputStream out = new FileOutputStream(savepath + "\\"	+ savefilename);
    					CutPicService.cutImage( item.getInputStream(), out,ext,imgx,imgy,imgw,imgh);
    					out.close();
    					File f= new File(savepath + "\\"	+ savefilename);  
    					BeanUtils.setProperty(theCt, "size",f.length());	
                    }
                    else
                    {                       
                    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式		
    					int rand=new Random().nextInt(10);
    					FileInputStream in=new FileInputStream(orifilepath);
    					String ext=CutPicService.getFormatInFile(in);
    					in.close();
    					String savefilename ="P_"+df.format(new Date())+"_"+user.getId()+"_"+rand+"."+ext;
    					BeanUtils.setProperty(theCt, "namex",savefilename);
    					FileOutputStream out = new FileOutputStream(savepath + "\\"	+ savefilename);
    					FileInputStream ins=new FileInputStream(orifilepath);
    					CutPicService.cutImage(ins, out,ext,imgx,imgy,imgw,imgh);
    					ins.close();
    					out.close();
    					File f= new File(savepath+"\\"+savefilename);  
    					BeanUtils.setProperty(theCt, "size",f.length());	
                    }
				}	
				item.delete();
			}
		    File  file = new File(orifilepath); 
		    if (file.isFile() && file.exists()) file.delete();
			theCt.setOperator(user.getId());
			SimpleDateFormat operdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式	
			theCt.setOperdate(operdate.format(new Date()));
			service.UpdateContent(theCt);
			request.setAttribute("cid",theCt.getCid());
			request.getRequestDispatcher("/pages/push/ContentUpdateSuccess.jsp").forward(request,response);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private void DeleteContent(HttpServletRequest request,
			HttpServletResponse response) {
		int cid = Integer.parseInt(request.getParameter("cid"));			
		try {
			String msg=service.DeleteContent(cid,this.getServletContext().getRealPath("/images/pics"));
			JSONObject result = new JSONObject();
			result.put("deletecontent", msg);			
			response.getWriter().print(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void GetContentList(HttpServletRequest request,
			HttpServletResponse response) {		
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		User user = (User) request.getSession().getAttribute("user");
		String conString=" where flag='1' ";
	    if(user.getComno().length()>5&&!user.getComno().equals("100000"))  conString=conString+" and com_no='"+user.getComno()+"' ";
		List<PushContent> CtList = service.GetContentList(currentPage, pageSize,conString);
		int total = service.GetCtTotal(conString);
		String json = "{\"total\":" + total + " ,\"rows\":"
				+ JSONArray.fromObject(CtList).toString() + "}";
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void AddContent(HttpServletRequest request,
			HttpServletResponse response) {	
		PushContent newCt = new PushContent();
		String savepath = this.getServletContext().getRealPath("/images/pics");
		User user = (User) request.getSession().getAttribute("user");
		int imgx = 0,imgy=0,imgw=0,imgh=0;
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			@SuppressWarnings("unchecked")
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString("utf-8");
					if(name.equals("imgx")||name.equals("imgy")||name.equals("imgw")||name.equals("imgh")) 
				    {
						if(name.equals("imgx")) imgx = Integer.parseInt(value);
						if(name.equals("imgy")) imgy = Integer.parseInt(value);
						if(name.equals("imgw")) imgw = Integer.parseInt(value);
						if(name.equals("imgh")) imgh = Integer.parseInt(value);
						continue;
				    }
					BeanUtils.setProperty(newCt, name, value);
				} 
				else {
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式		
					int rand=new Random().nextInt(10);
					String ext=CutPicService.getFormatInFile(item.getInputStream());
					
					String savefilename ="P_"+df.format(new Date())+"_"+user.getId()+"_"+rand+"."+ext;
					BeanUtils.setProperty(newCt, "namex",savefilename);
					FileOutputStream out = new FileOutputStream(savepath + "\\"	+ savefilename);
					CutPicService.cutImage( item.getInputStream(), out,ext,imgx,imgy,imgw,imgh);
					out.close();
					File f= new File(savepath + "\\"	+ savefilename);  
					BeanUtils.setProperty(newCt, "size",f.length());	
				}	
				item.delete();
			}
			newCt.setOperator(user.getId());
			SimpleDateFormat operdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式	
			newCt.setOperdate(operdate.format(new Date()));
			service.AddContent(newCt);
			request.getRequestDispatcher("/pages/push/ContentAddSuccess.jsp").forward(request,response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	@SuppressWarnings("unused")
	private void heheUpdateContent(HttpServletRequest request,
			HttpServletResponse response) {
		PushContent theCt = new PushContent();
		String savepath = this.getServletContext().getRealPath("/images/pics");
		User user = (User) request.getSession().getAttribute("user");
		String orifilepath=savepath+ "\\"+request.getParameter("filenamex");
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			@SuppressWarnings("unchecked")
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString("utf-8");
					BeanUtils.setProperty(theCt, name, value); 
				} else {
                    if(item.getName()!="")
                    {
					  BeanUtils.setProperty(theCt, "size",item.getSize());
					  int rand=new Random().nextInt(10);
					  String fileName=item.getName();
					  SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式			
					  // 得到保存在硬盘的文件名,没有后缀名时会将文件名作为后缀名
					  String savefilename ="P_"+df.format(new Date())+"_"+user.getId()+"_"+rand+"."+fileName.substring(fileName.lastIndexOf(".")+1);
					  BeanUtils.setProperty(theCt, "namex",savefilename);
					  InputStream in = item.getInputStream();
					  FileOutputStream out = new FileOutputStream(savepath + "\\"	+ savefilename);
					  int len = 0;
					  byte buffer[] = new byte[1024];
					  while ((len = in.read(buffer)) > 0) {
					  	out.write(buffer, 0, len);
					  }
					  in.close();
					  out.close();
					  File  file = new File(orifilepath); 
					  if (file.isFile() && file.exists()) file.delete();
                    }
                    else
                    {
                    	BeanUtils.setProperty(theCt, "size", 0);
                    	BeanUtils.setProperty(theCt, "namex","");
                    }
				}	
				item.delete();
			}
			theCt.setOperator(user.getUsername());
			SimpleDateFormat operdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式	
			theCt.setOperdate(operdate.format(new Date()));
			service.UpdateContent(theCt);
			request.setAttribute("cid",theCt.getCid());
			request.getRequestDispatcher("/pages/push/ContentUpdateSuccess.jsp").forward(request,response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
