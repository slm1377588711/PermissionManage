package com.bdyc.listener;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.bdyc.model.TreeGrid;
import com.bdyc.service.UserService;
import com.bdyc.service.UserServiceImpl;

/**
 * @author wolf
 * 监听Application对象的监听器：此监听器监听方法会随着系统的启动自动执行
 * 1、加载JdbcUtil类，进而加载数据库连接池配置文件，创建数据源
 */
public class ApplicationListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//加载JdbcUtil类
		try {
			Class.forName("com.bdyc.util.JdbcUtil");
			
			UserService userService = new UserServiceImpl();
			//加载导航菜单数据：application
			List<TreeGrid> navTreeList = userService.getNavTree();
			
			sce.getServletContext().setAttribute("navList", navTreeList);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("系统配置文件初始化异常");
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("初始化异常");
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}





