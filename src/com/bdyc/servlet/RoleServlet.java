package com.bdyc.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.bdyc.model.DataGridModel;
import com.bdyc.model.EcharModel;
import com.bdyc.model.Role;
import com.bdyc.model.resModel;
import com.bdyc.service.RoleService;
import com.bdyc.service.RoleServiceImpl;

public class RoleServlet extends BaseServlet {
	RoleService roleService = new RoleServiceImpl();
	//获取角色拥有的一群资源ID（根据角色ID获取资源Id）
	public void getRoleResourceIds(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int roleId = Integer.valueOf(request.getParameter("roleId"));
		List<Integer> roleResourceIds = null;
		try {
			roleResourceIds = roleService.findResourceIdsById(roleId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.getWriter().write(JSON.toJSONString(roleResourceIds));
	}
	public void getRoles(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			List<Role> roleList = roleService.findAll();
			String json = JSON.toJSONString(roleList,true);
			response.getWriter().write(json);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//授权grant
	public void grant(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String roleId = request.getParameter("roleId");
		String resourceIds = request.getParameter("resourceIds");
		String[] resourceIdsArr = null;
		if(resourceIds!=null){
			resourceIdsArr = resourceIds.split(",");
		}
		resModel res = new resModel();
		try {
			//调用根据用户名查询的方法,该方法返回Role对象
			roleService.grant(roleId,resourceIdsArr);
			res.setSuccess(true);
			res.setMsg("授权成功");
		} catch (Exception e) {
			res.setSuccess(false);
			res.setMsg("授权失败");
		}finally{
			response.getWriter().write(JSON.toJSONString(res,true));
		}
	}
	
	//批量删除
	public void delbatch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String roleIds = request.getParameter("roleIds");
		resModel res = new resModel();
		try {
			//调用根据用户名查询的方法,该方法返回Role对象
			roleService.delBatchRole(roleIds);
			res.setSuccess(true);
			res.setMsg("删除成功");
		} catch (SQLException e) {
			res.setSuccess(false);
			res.setMsg("删除失败");
		}finally{
			response.getWriter().write(JSON.toJSONString(res,true));
		}
	}
	
	
	//修改角色
	public void updateRole(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int roleId = Integer.valueOf(request.getParameter("roleId"));
		System.out.println("修改提交的ID"+roleId);
		String roleName = request.getParameter("roleName");
		System.out.println("修改提交的名称"+roleName);
		String roleDesc = request.getParameter("roleDesc");
		System.out.println("修改提交的描述"+roleDesc);
	    Role role=new Role(roleId, roleName, roleDesc);
		resModel res = new resModel();
		try {
			//调用根据用户名查询的方法,该方法无返回值
			 roleService.updateRole(role);
			 res.setSuccess(true);
			 res.setMsg("修改"+roleName+"成功");
		} catch (SQLException e) {
			e.printStackTrace();
			 res.setSuccess(false);
			 res.setMsg("修改失败");
		}finally{
			//响应给前台数据
			response.getWriter().write(JSON.toJSONString(res,true));
			
		}
	}
	
	//根据ID查询,做修改时的数据回显
	public void findById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int roleId = Integer.valueOf(request.getParameter("roleId"));
		System.out.println("进入findByIdservlet");
		try {
			//调用根据用户名查询的方法,该方法返回Role对象
			Role role=roleService.findById(roleId);
			String role11=JSON.toJSONString(role);
			System.out.println(role11);
			response.getWriter().write(role11);
			/*response.getWriter().write(JSON.toJSONString(role,true));*/
		} catch (SQLException e) {
		}
	}
	
	
	//单个删除角色
	public void delRole(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int roleId = Integer.valueOf(request.getParameter("roleId"));
		resModel res = new resModel();
		try {
			//调用根据角色Id查询的方法,该方法返回Role对象
			roleService.delRole(roleId);
			res.setSuccess(true);
			res.setMsg("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setSuccess(true);
			System.out.println("错误信息"+e.getMessage());
			res.setMsg(e.getMessage());
		}finally{
			response.getWriter().write(JSON.toJSONString(res,true));
		}
	}
	
	
	
	/*添加角色*/
	public void addRole(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String rolename = request.getParameter("rolename");
		String roleDesc = request.getParameter("roleDesc");
		Role role = new Role(null,rolename,roleDesc);
		resModel res = new resModel();
		try {
			//调用根据用户名查询的方法,该方法无返回值
			 roleService.addRole(role);
			 res.setSuccess(true);
			 res.setMsg("添加"+rolename+"成功");
		} catch (SQLException e) {
			e.printStackTrace();
			 res.setSuccess(false);
			 res.setMsg("添加失败");
		}finally{
			//响应给前台数据
			response.getWriter().write(JSON.toJSONString(res,true));
			
		}
	}
	/*检查角色名是否重复*/
	public void checkRoleName(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String roleName = request.getParameter("rolename");
		try {
			//调用根据用户名查询的方法,该方法返回Role对象
			Role role = roleService.findByRoleName(roleName);
			boolean flag;
			if(role!=null){
				 flag=false;
			}else{
				flag=true;
			}
			String json = JSON.toJSONString(flag,true);
			System.out.println("是否重复"+json);
			//响应给前台数据
			response.getWriter().write(json);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 获取所有角色的数据：响应给浏览器
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String roleName = request.getParameter("rolename");
		//Map集合存放模糊查询条件
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("rolename", roleName);
		System.out.println(params);
		//接受页面传过来的当前页和每页显示条数
		int currentPage = Integer.valueOf(request.getParameter("page"));
	    int pageSize = Integer.valueOf(request.getParameter("rows"));;
		try {
			//调用模糊查询分页的方法,该方法返回List集合
			List<Role> roleList = roleService.findAllByPage(currentPage,pageSize, params);
			//实例化转换json数据的实体类
			DataGridModel dg = new DataGridModel();
			//给实体类的总条数赋值
			dg.setTotal(roleService.getcount(params));
			//赋值每页显示的数据
			dg.setRows(roleList);
			//转换成json数据格式 true代表 显示为json格式
			String json = JSON.toJSONString(dg,true);
			//响应给前台数据
			response.getWriter().write(json);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//  获取每个角色对应的人数
	public void getRoleChart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			List<EcharModel> charModelList = roleService.getRoleChart();
			response.getWriter().write(JSON.toJSONString(charModelList));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
