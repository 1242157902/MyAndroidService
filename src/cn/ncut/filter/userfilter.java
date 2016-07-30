package cn.ncut.filter;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ncut.syssetdomain.User;
/**
 * @author wzq
 *
 * 2014-7-8
 */
public class userfilter implements Filter {


	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		
		HttpServletRequest request=(HttpServletRequest) req;
		HttpServletResponse response=(HttpServletResponse) resp;
		User user = (User) request.getSession().getAttribute("user");
		if (user!= null) {
			
			chain.doFilter(request, response);
			
		}else {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
		
		
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	

}
