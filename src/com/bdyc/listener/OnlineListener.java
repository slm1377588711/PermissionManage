package com.bdyc.listener;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.bdyc.model.User;

public class OnlineListener implements HttpSessionListener,HttpSessionAttributeListener,ServletContextListener{
	@Override
	public void attributeAdded(HttpSessionBindingEvent se) {
		Enumeration<String> names = se.getSession().getAttributeNames();
		
		if(names != null){
			while(names.hasMoreElements()){
				String name = names.nextElement();
				if("user".equals(name)){
					//登录时添加对象到在线列表
					User user = (User) se.getSession().getAttribute("user");
					//获取到Application作用域中的空在线人数集合
					List<User> onlineList = (List<User>) se.getSession().getServletContext().getAttribute("online");
					onlineList.add(user);
					//再讲新集合重新添加到Application作用域
					//se.getSession().getServletContext().setAttribute("online", onlineList);
				}
				
			}
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent se) {
		
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent se) {
		
	}

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		//退出时移除在线列表中的 用户对象
		User user = (User) se.getSession().getAttribute("user");
		//获取到Application作用域中的空在线人数集合
		List<User> onlineList = (List<User>) se.getSession().getServletContext().getAttribute("online");
		onlineList.remove(user);
		//再讲新集合重新添加到Application作用域
		//se.getSession().getServletContext().setAttribute("online", onlineList);
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		List<User> onlineList  = new ArrayList<User>();
		sce.getServletContext().setAttribute("online", onlineList);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
