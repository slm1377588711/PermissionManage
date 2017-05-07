package com.bdyc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//只拦截index.jsp
		HttpServletRequest req = (HttpServletRequest)request;//utf8 
		HttpServletResponse resp = (HttpServletResponse)response;
		Object user = req.getSession().getAttribute("userInfo");
		if(user != null){
			System.out.println("放行");
			chain.doFilter(req, resp);//放行请求
		}else{
			System.out.println("拦截");
			//拦截请求：让页面跳转到登录
			resp.sendRedirect(req.getContextPath()+"/login.jsp");
		}
		
		
		/*//判断用户是否登录：session中是否存在user属性
		HttpServletRequest req = (HttpServletRequest)request;//utf8 
		HttpServletResponse resp = (HttpServletResponse)response;
		
		//放行login.jsp
		String path = req.getRequestURI();//获取浏览器地址栏地址：http://localhost:8080/bdyc/login.jsp
		//user/UserServlet?method=login
		String methodName = req.getParameter("method");
		if(path.endsWith("/login.jsp") 
				|| path.endsWith(".css") 
				|| path.endsWith(".js")
				|| path.endsWith(".jpg")
				|| path.endsWith(".png")
				|| path.endsWith(".gif")){
			chain.doFilter(req, resp);//放行请求
			
		//放行：user/UserServlet?method=login
		}else if("login".equals(methodName)){
			chain.doFilter(request, response);//放行请求
		}else{
			Object user = req.getSession().getAttribute("userInfo");
			if(user != null){
				System.out.println("放行");
				chain.doFilter(req, resp);//放行请求
			}else{
				System.out.println("拦截");
				//拦截请求：让页面跳转到登录
				resp.sendRedirect(req.getContextPath()+"/login.jsp");
			}
		}*/
		
	}

	@Override
	public void destroy() {}

}
