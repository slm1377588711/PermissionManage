package com.bdyc.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.bdyc.model.Role;
import com.bdyc.model.User;
import com.bdyc.util.Page;

public interface UserDao {
	//用户登录  根据用户名和密码查询
	public User findByUsernameAndPassword(String username, String password) throws SQLException ;
	//添加用户
	public int add(User user) throws SQLException ;
	//修改用户
	public void update(User user) throws SQLException ;
	//删除一个用户
	public void del(Integer userId) throws SQLException ;
	//根据Id查询用户
	public User findById(Integer userId) throws SQLException ;
	//无条件查询全部
	public List<User> findAll() throws SQLException ;
	//批量删除
	public void delBatch(String ids) throws SQLException ;
	//获取总记录条数
	public int getcount(Map<String, Object> params) throws SQLException ;
	//分页查询或模糊分页查询
	public List<User> findAllByPage(int currentPage,int pageSize,Map<String, Object> params) throws SQLException ;
	//根据用户名查询,实现查重的功能
	public User findByUsername(String username)throws SQLException ;
	//添加用户角色
	public void addUserRole(int userId, Integer roleId) throws SQLException ;
	//根据用户ID查找用户角色
	public List<Role> findUserRoleByUserId(Integer userId) throws SQLException ;
	//根据用户Id删除用户和角色的关系
	public void delUserRole(Integer userId) throws SQLException;
	//根据用户Id批量删除用户和角色的关系
	public void delBatchUserRole(String ids) throws SQLException;
	//根据用户Id修改用户角色
	public void updateUserRoleByUserId(Integer roleIds,Integer userId) throws SQLException;
	//根据用户名修改密码
	public void updatePassword(String username, String password)throws SQLException;
}
