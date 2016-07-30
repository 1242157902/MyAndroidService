package cn.ncut.pushservlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.math.RandomUtils;

import net.sf.json.JSONArray;
import cn.ncut.pushdomain.IconInfo;
import cn.ncut.pushservice.IconService;
import cn.ncut.syssetdomain.User;

/**
 * @author wzq
 * 
 *         version 1.0 2015-4-3 下午3:27:08
 */
public class IconServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	IconService service=new IconService();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String method = request.getParameter("method");
		if ("GetList".equals(method)) {

			GetList(request, response);
		}else  if ("DeleteIcon".equals(method)) {
			
			DeleteIcon(request,response);
			
		}else if ("IconAdd".equals(method)) {
			
			
			IconAdd(request,response);
			
		}else if ("IconUpdate".equals("IconUpdate")) {
			IconUpdate(request,response);
		}

	}

	/**
	 * @param request
	 * @param response
	 */
	private void IconUpdate(HttpServletRequest request,
			HttpServletResponse response) {
		 try {
			 
			
				IconInfo	info = doupload(request);
			
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date d1 = new Date();
				 String entertime = format.format(d1);
				 
				 info.setUpdatetime(entertime);
				service.update(info);
				
		      
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			 try {
				request.getRequestDispatcher("/pages/push/IconUpdateSuccess.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			} 
	}

	/**
	 * @param request
	 * @param response
	 */
	private void IconAdd(HttpServletRequest request,
			HttpServletResponse response) {
		 try {
				IconInfo	info = doupload(request);
				
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date d1 = new Date();
				 String entertime = format.format(d1);
				 
				 info.setUpdatetime(entertime);
				
				
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
					
					info.setManager(comno);
				
				
				
				service.add(info);
				
				
				
				
		     
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			 try {
				request.getRequestDispatcher("/pages/push/IconAddSuccess.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		
	}

	/**
	 * @param request
	 * @return
	 */
	private IconInfo doupload(HttpServletRequest request) {
		IconInfo info=new IconInfo();
		try {
			DiskFileItemFactory factory=new DiskFileItemFactory();
			ServletFileUpload upload=new ServletFileUpload(factory);
			List<FileItem> list=upload.parseRequest(request);
			for (FileItem item:list) {
				if(item.isFormField()){
					
					String name=item.getFieldName();
					String value=item.getString("utf-8");
					BeanUtils.setProperty(info, name, value);
				}else {
					String filename=item.getName();
				    String savefilename=makeFileName(filename);//得到保存在硬盘的文件名
					String savepath=this.getServletContext().getRealPath("/images/icons");
					InputStream in=item.getInputStream();
					FileOutputStream out=new FileOutputStream(savepath+"\\"+savefilename);
					int len=0;
					byte buffer[]=new byte[1024];
					while ((len=in.read(buffer))>0) {
						out.write(buffer,0,len);
				   }
					in.close();
					out.close();
					item.delete();
					info.setIconname(savefilename);
				  }
		   }
			
			
		} catch (Exception e) {
		
			
		}
		
		return info;
		
		
		
	}
	public String  makeFileName(String filename) {
		String extString=filename.substring(filename.lastIndexOf("."));
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date d1 = new Date();
		String entertime = format.format(d1);
		
		entertime=entertime+RandomUtils.nextInt(9);
		
		
		return entertime+extString;
		}
	

	/**
	 * @param request
	 * @param response
	 */
	private void DeleteIcon(HttpServletRequest request,
			HttpServletResponse response) {
		
		String id=request.getParameter("id");
		
		try {
			service.delete(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			System.out.println("###################");
			e.printStackTrace();
		}
		
		
		
		
		
	}

	/**
	 * @param request
	 * @param response
	 */
	private void GetList(HttpServletRequest request,
			HttpServletResponse response) {

		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
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

		List<IconInfo> IconInfos = service.getlist(currentPage,
				pageSize, comno);

		int total = service.getTotal(comno);

		response.setContentType("text/html;charset=utf-8");

		String json = "{\"total\":" + total + " , \"rows\":"
				+ JSONArray.fromObject(IconInfos).toString() + "}";
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
