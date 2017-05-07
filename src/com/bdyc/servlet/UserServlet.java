package com.bdyc.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.bdyc.model.DataGridModel;
import com.bdyc.model.Role;
import com.bdyc.model.TreeGrid;
import com.bdyc.model.User;
import com.bdyc.model.resModel;
import com.bdyc.service.UserService;
import com.bdyc.service.UserServiceImpl;

public class UserServlet extends BaseServlet {
	UserService userService = new UserServiceImpl();
	//退出登录
	public void outlogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		resModel res = new resModel();
		request.getSession().invalidate();
		res.setMsg("注销");
		res.setSuccess(true);
		response.getWriter().write(JSON.toJSONString(res));
	}
	//修改密码updatePassword
	public void updatePassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		resModel res = new resModel();
		try {
			String username = request.getParameter("username");
			username=new String(username.getBytes("iso8859-1"),"utf-8");
			String password = request.getParameter("pwd");
			System.out.println("修改密码的用户名"+username);
			
			userService.updatePassword(username,password);
			res.setMsg("修改成功");
			res.setSuccess(true);
			response.getWriter().write(JSON.toJSONString(res));
		} catch (Exception e) {
			e.printStackTrace();
			res.setMsg("修改成功");
			res.setSuccess(true);
		}
	}
	
	
	//登录
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		resModel rm = new resModel();
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			User user = userService.login(username,password);
			if(user != null){
				//将用户的基本信息存入到session
				request.getSession().setAttribute("userInfo", user);
				//查询当前用户拥有的权限
				List<String> urls = userService.findUrlsByUserId(user.getUserId());
				for(int i = 0;i<urls.size();i++){
					System.out.println(urls.get(i));
				}
				//存放在session作用域中
				request.getSession().setAttribute("urls", urls);
				
				rm.setSuccess(true);
				rm.setMsg("登录成功");
			}else{
				rm.setSuccess(false);
				rm.setMsg("用户名或密码错误");
			}
			
			response.getWriter().write(JSON.toJSONString(rm));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//获取导航树 index页面
	public void getNavTree(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//List<TreeModel> navTreeList = userService.getNavTree();
			//从Appliation作用域中获取导航信息
			List<TreeGrid> navTreeList = (List<TreeGrid>) request.getServletContext().getAttribute("navList");
			response.getWriter().write(JSON.toJSONString(navTreeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//批量删除
	public void delbatch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userIds = request.getParameter("userIds");
		resModel res = new resModel();
		try {
			//调用根据用户名查询的方法,该方法返回User对象
			userService.delBatchUser(userIds);
			res.setSuccess(true);
			res.setMsg("删除成功");
		} catch (SQLException e) {
			res.setSuccess(false);
			res.setMsg("删除失败");
		}finally{
			response.getWriter().write(JSON.toJSONString(res,true));
		}
	}
	
	
	//修改用户
	public void updateUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取Id
		int userId = Integer.valueOf(request.getParameter("userIdUpdate"));
		//获取用户名
		String username = request.getParameter("usernameUpdate");
		//转换字符编码
		username = new String(username.getBytes("iso8859-1"),"utf-8");
		String password = request.getParameter("passwordUpdate");
		String deptName = request.getParameter("deptNameUpdate");
		deptName = new String(deptName.getBytes("iso8859-1"),"utf-8");
		String province = request.getParameter("provinceUpdate");
		province = new String(province.getBytes("iso8859-1"),"utf-8");
		String city = request.getParameter("cityUpdate");
		city = new String(city.getBytes("iso8859-1"),"utf-8");
		String county =	request.getParameter("countyUpdate");
		county = new String(county.getBytes("iso8859-1"),"utf-8");
		String roleIds[] = request.getParameterValues("roleIdUpdate");
		User user = new User(userId, username, password, deptName, province, city, county, null, new Date());
		resModel res = new resModel();
		try {
			//调用根据用户名查询的方法,该方法无返回值
			 userService.updateUser(user,roleIds);
			 res.setSuccess(true);
			 res.setMsg("修改成功");
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
		int userId = Integer.valueOf(request.getParameter("userId"));
		User user=null;
		List<User> userlist = new ArrayList<User>();
		try {
			//调用根据用户名查询的方法,该方法返回User对象
			user=userService.findById(userId);
			//返回角色信息
			user.getRolelist();
			//存入list集合
			userlist.add(user);
		} catch (SQLException e) {
			
		}finally{
			response.setContentType("text/html,utf-8");
			response.getWriter().write(JSON.toJSONString(userlist,true));
		}
	}
	
	
	//删除单个用户
	public void delUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int userId = Integer.valueOf(request.getParameter("userId"));
		resModel res = new resModel();
		try {
			//调用根据用户名查询的方法,该方法返回User对象
			userService.delUser(userId);
			res.setSuccess(true);
			res.setMsg("删除成功");
		} catch (SQLException e) {
			res.setSuccess(false);
			res.setMsg("删除失败");
		}finally{
			response.getWriter().write(JSON.toJSONString(res,true));
		}
	}
	
	
	
	/*添加用户*/
	public void addUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取表单数据
		String username = request.getParameter("usernameadd");
		String password = request.getParameter("addpassword");
		String deptName = request.getParameter("adddeptName");
		String province = request.getParameter("addprovince");
		String city = request.getParameter("addcity");
		String county =	request.getParameter("addcounty");
		String roleIds[] = request.getParameterValues("roleId");
		User user = new User(null, username, password, deptName, province, city, county, new Date(), new Date());
		resModel res = new resModel();
		try {
			//调用根据用户名查询的方法,该方法无返回值
			 userService.addUser(user, roleIds);
			 res.setSuccess(true);
			 res.setMsg("添加"+username+"成功");
		} catch (SQLException e) {
			e.printStackTrace();
			 res.setSuccess(false);
			 res.setMsg("添加失败");
		}finally{
			//响应给前台数据
			response.getWriter().write(JSON.toJSONString(res,true));
			
		}
	}
	/*检查用户名是否重复*/
	public void checkUserName(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		try {
			//调用根据用户名查询的方法,该方法返回User对象
			User user = userService.findByUsername(username);
			boolean flag;
			if(user!=null){
				 flag=false;
			}else{
				flag=true;
			}
			String json = JSON.toJSONString(flag,true);
			//响应给前台数据
			response.getWriter().write(json);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 获取所有用户的数据：响应给浏览器
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		//Map集合存放模糊查询条件
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("username", username);
		System.out.println(params);
		//接受页面传过来的当前页和每页显示条数
		int currentPage = Integer.valueOf(request.getParameter("page"));
	    int pageSize = Integer.valueOf(request.getParameter("rows"));;
		try {
			//调用模糊查询分页的方法,该方法返回List集合
			List<User> userList = userService.findAllByPage(currentPage,pageSize, params);
			//实例化转换json数据的实体类
			DataGridModel dg = new DataGridModel();
			//给实体类的总条数赋值
			dg.setTotal(userService.getcount(params));
			//赋值每页显示的数据
			dg.setRows(userList);
			//转换成json数据格式 true代表 显示为json格式
			String json = JSON.toJSONString(dg,true);
			//响应给前台数据
			response.getWriter().write(json);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

}
