package com.bdyc.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String methodName = request.getParameter("method");
		//通过反射：reflect
		Class clz = this.getClass();
		try {
			Method method = clz.getDeclaredMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			
			
			method.invoke(this, request,response);
		}  catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		/*if("add".equals(methodName)){
			this.add(request, response);
		}else if("login".equals(methodName)){
			this.login(request, response);
		}else if("updateUI".equals(methodName)){
			this.updateUI(request, response);
		}else if("update".equals(methodName)){
			this.update(request, response);
		}else if("list".equals(methodName)){
			this.list(request, response);
		}*/
	}
}
