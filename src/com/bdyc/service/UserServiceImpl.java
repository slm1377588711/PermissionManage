package com.bdyc.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bdyc.dao.ResourceDao;
import com.bdyc.dao.ResourceDaoImpl;
import com.bdyc.dao.UserDao;
import com.bdyc.dao.UserDaoImpl;
import com.bdyc.model.Resource;
import com.bdyc.model.Role;
import com.bdyc.model.TreeGrid;
import com.bdyc.model.User;
import com.bdyc.util.Page;

public class UserServiceImpl implements UserService {
	private UserDao userDao = new UserDaoImpl();
	private ResourceDao resourceDao = new ResourceDaoImpl();
	//用户登录  根据用户名和密码查询
	@Override
	public User login(String username, String password) throws SQLException {
		User user = userDao.findByUsernameAndPassword(username,password);
		return user;
	}
	//添加用户
	@Override
	public void addUser(User user,String[] roleIds) throws SQLException {
		int userId = userDao.add(user);
		//添加用户和角色关系
		if(roleIds != null && roleIds.length > 0){
			for (String roleId : roleIds) {
				userDao.addUserRole(userId,Integer.valueOf(roleId));
			}
		}
	}
	//无条件查询全部
	@Override
	public List<User> findAll() throws SQLException {
		return userDao.findAll();
	}
	//删除一个用户
	@Override
	public void delUser(Integer userId) throws SQLException {
		/*1.清除用户与角色的关系*/
		userDao.delUserRole(userId);
		userDao.del(userId);
	}
	//根据Id查询用户
	@Override
	public User findById(int userId) throws SQLException {
		System.out.println("---------------------------------");
		//根据用户Id查到拥有得角色Id
		List<Role> roleIds = userDao.findUserRoleByUserId(userId); 
		User user = new User();
		//查到用户的信息
		user=userDao.findById(userId);
		//把查到的角色Id给User
		user.setRolelist(roleIds);
		//userRoleId.add(user);
		return user;
	}
	//修改用户
	@Override
	public void updateUser(User user,String[] roleIds) throws SQLException {
		//清除用户与角色的关系
		userDao.delUserRole(user.getUserId());
		//根据用户Id修改用户角色
		userDao.update(user);
		if(roleIds != null && roleIds.length > 0){
			for (String roleId : roleIds) {
				userDao.addUserRole(user.getUserId(), Integer.valueOf(roleId));
			}
		}
		
	
	}
	//批量删除
	@Override
	public void delBatchUser(String ids) throws SQLException {
		//批量删除用户与角色的关系
		userDao.delBatchUserRole(ids);
		userDao.delBatch(ids);
	}
	//获取总记录条数
	@Override
	public int getcount(Map<String, Object> params) throws SQLException {
		return userDao.getcount(params);
	}
	//分页查询或模糊分页查询
	@Override
	public List<User> findAllByPage(int currentPage,int pageSize,Map<String,Object> params) throws SQLException {
		List<User> userList =  userDao.findAllByPage(currentPage,pageSize,params);
		List<User> userRoleList = new ArrayList<User>();
		for (User user : userList) {
			//根据用户id查询用户拥有的角色
			List<Role> userRoles = userDao.findUserRoleByUserId(user.getUserId());
			user.setRolelist(userRoles);
			
			userRoleList.add(user);
		}
		
		return userRoleList;
	}
	//根据用户名查询,实现查重的功能
	@Override
	public User findByUsername(String username) throws SQLException {
		return userDao.findByUsername(username);
	}
	//获取一级二级导航树
	@Override
	public List<TreeGrid> getNavTree() throws SQLException {
		System.out.println("加载系统导航信息。。。");
		List<Resource> rootResourceList = resourceDao.findByParentId(null);
		List<TreeGrid> rootTreeList = new ArrayList<TreeGrid>();
		//将Resource模型转换TreeModel模型
		for (Resource rootResource : rootResourceList) {
			TreeGrid rootTree = new TreeGrid();
			rootTree.setId(rootResource.getResourceId());
			rootTree.setText(rootResource.getResourceName());
			rootTree.setState("open");
			rootTree.setIconCls(rootResource.getIconCls());
			rootTree.getAttributes().put("url", rootResource.getUrl());
			
			
			
			//根据parentId查询当前父节点的子节点
			List<Resource> secondResourceList = resourceDao.findByParentId(rootResource.getResourceId());
			List<TreeGrid> secondTreeeModelList = new ArrayList<TreeGrid>();
			for (Resource secondResource : secondResourceList) {
				TreeGrid secondTreeModel = new TreeGrid();
				secondTreeModel.setId(secondResource.getResourceId());
				secondTreeModel.setText(secondResource.getResourceName());
				secondTreeModel.setState("open");
				secondTreeModel.setIconCls(secondResource.getIconCls());
				secondTreeModel.getAttributes().put("url", secondResource.getUrl());
				
				secondTreeeModelList.add(secondTreeModel);
			}
			rootTree.setChildren(secondTreeeModelList);
			
			rootTreeList.add(rootTree);
		}
		return rootTreeList;
	}
	//查询当前用户拥有的权限
	@Override
	public List<String> findUrlsByUserId(Integer userId) throws SQLException {
		return resourceDao.findUrlsByUserId(userId);
	}
	//根据用户名修改密码
	@Override
	public void updatePassword(String username, String password)
			throws SQLException {
		userDao.updatePassword(username,password);
	}

}
