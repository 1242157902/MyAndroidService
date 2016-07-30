package cn.ncut.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.text.StrTokenizer;

/**
 * @author 王照清 加油！
 *
 * 2014-4-20
 */
public class CharacterEncodingFilter implements Filter {

	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest) arg0;
		HttpServletResponse response=(HttpServletResponse) arg1;
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		arg2.doFilter(new Myrequest(request), response);
		

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
class Myrequest extends HttpServletRequestWrapper{

	/**
	 * @param request
	 */
	private HttpServletRequest request;
	public Myrequest(HttpServletRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
		this.request=request;
	}
	@Override
	public String getParameter(String name) {
		// TODO Auto-generated method stub
		String value=this.request.getParameter(name);
		if (value==null) {
			return  null;
			
		}
		if (!this.request.getMethod().equalsIgnoreCase("get")) {
			return value;
			
		}
	try {
		new String(	value.getBytes("iso8859-1"),"utf-8");
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		throw new RuntimeException(e);
	}
		
		
		return value;
	}
	
	
	
	
	
	
	
}
