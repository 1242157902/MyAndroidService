package cn.ncut.deviceservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.ncut.syssetdomain.User;
import cn.ncut.utils.TwoDimensionCode;

/**
 * @author wzq
 *
 *version 1.0 2015-1-3 下午10:02:29
 */
public class Erweima extends HttpServlet {

	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		
		 User user=(User) session.getAttribute("user");
		 String comno=null;
		 
		 if (user==null) {
			 try {
					response.getWriter().write("会话过期，请重新登录！");
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
		}
		 
		 if (user!=null) {
			comno=user.getComno();
		}
		 
		 String shopno =request.getParameter("shopno");
		 String empno =request.getParameter("empno");
		 
		String encoderContent=comno+"-"+shopno+"-"+empno;
		
		String imgPath = this.getServletContext().getRealPath("/images");;
		 String imagename=encoderContent+".png";
         imgPath=imgPath+"/erweima/"+imagename;
        TwoDimensionCode handler = new TwoDimensionCode();  
        handler.encoderQRCode(encoderContent, imgPath, "png"); 
        //System.out.println(imgPath);
        
        request.setAttribute("imagename", imagename);
      request.getRequestDispatcher("/pages/downloaderweima.jsp").forward(request, response);
		 
		
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	           doGet(request, response);
	}

}
