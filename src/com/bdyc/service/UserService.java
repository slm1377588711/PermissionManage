package com.bdyc.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.bdyc.model.TreeGrid;
import com.bdyc.model.User;
import com.bdyc.util.Page;

public interface UserService {
	//用户登录
	public User login(String username,String password) throws SQLException;
	//添加用户
	public void addUser(User user,String[] roleIds) throws SQLException;
	//修改用户
	public void updateUser(User user,String[] roleIds)throws SQLException;
	//删除一个用户
	public void delUser(Integer userId) throws SQLException;
	//根据Id查询用户
	public User findById(int userId) throws SQLException;
	//无条件查询全部
	public List<User> findAll() throws SQLException;///
	//批量删除
	public void delBatchUser(String ids)throws SQLException;
	//获取总记录条数
	public int getcount(Map<String, Object> params)throws SQLException;
	//分页查询或模糊分页查询
	public List<User> findAllByPage(int currentPage,int pageSize, Map<String, Object> params)throws SQLException;
	//根据用户名查询,实现查重的功能
	public User findByUsername(String username)throws SQLException;
	//获取导航树
	public List<TreeGrid> getNavTree()throws SQLException;
	//查询当前用户拥有的权限
	public List<String> findUrlsByUserId(Integer userId)throws SQLException;
	//根据用户名修改密码
	public void updatePassword(String username,String password)throws SQLException;
}
