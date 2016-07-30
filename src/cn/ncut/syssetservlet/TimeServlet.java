package cn.ncut.syssetservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ncut.devicedao.RegisterDao;
import cn.ncut.syssetdao.TimeDao;
import cn.ncut.utils.ECC_SHA;

/**
 * @author wzq
 *
 * version 1.0 2014-10-18 下午3:34:12
 */
public class TimeServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
   //间隔访问时间
		 String intervaltime=request.getParameter("intervaltime");
		
		 //图片更新间隔时间
		 String updatepictime=request.getParameter("updatepictime");
		 String flag=request.getParameter("flag");
		 TimeDao dao=new TimeDao();
		 if("flag".equals(flag))
		 {
			 
			 String location_interval=request.getParameter("location_interval");
			 dao.update(location_interval);
			 request.setAttribute("message", "更新成功！");
			 request.getRequestDispatcher("/message.jsp").forward(request, response);
			 
			 
		 }else if("flag_serverkeys".equals(flag)){//生成服务器端公钥和私钥，并保存到数据库中。同时，更改tab_deviceinfo表中update_key字段的值为1
			 
			 if(ECC_SHA.generateAndSavePublicPrivateKeys()){
				 RegisterDao rd=new RegisterDao();
				 rd.ModifyAllUpdateKey(1);
				 request.setAttribute("message", "生成服务器端公钥和私钥成功");
				 request.getRequestDispatcher("/message.jsp").forward(request, response);
			 }else{
				 request.setAttribute("message", "生成服务器端公钥和私钥失败");
				 request.getRequestDispatcher("/message.jsp").forward(request, response);
			 }
			 
			 
		}else{
			dao.update(intervaltime,updatepictime);
			 request.setAttribute("message", "更新成功！");
			 request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
		 
		
		
		
		
		
		
	
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
                   doGet(request, response);
		
	}

}
