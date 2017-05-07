package com.bdyc.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.bdyc.dao.ResourceDaoImpl;
import com.bdyc.model.DataGridModel;
import com.bdyc.model.Resource;
import com.bdyc.model.TreeGrid;
import com.bdyc.service.ResourceService;
import com.bdyc.service.ResourceServiceImpl;

public class ResourceServlet extends BaseServlet {
	ResourceService resourceService = new ResourceServiceImpl();
	//获取所有树形资源  授权时使用
	public void getTree(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			List<TreeGrid> listTree = resourceService.findAllTree();
			
			String json = JSON.toJSONString(listTree,true);
			response.getWriter().write(json);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//资源列表，显示为树形表格 点击资源管理是使用
	public void treegrid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Resource> list = new ArrayList<Resource>();
		DataGridModel dgm = null;
		try { 
			// 调用全查方法
			list=resourceService.findAll();
			dgm = new DataGridModel(list.size(), list);
			String json = JSON.toJSONString(dgm).replaceAll("parentId", "_parentId");
			response.getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
