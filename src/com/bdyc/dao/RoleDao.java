package com.bdyc.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.bdyc.model.EcharModel;
import com.bdyc.model.Role;
import com.bdyc.util.Page;

public interface RoleDao {
	//添加角色
	public void add(Role role) throws SQLException ;
	//修改角色
	public void update(Role role) throws SQLException ;
	//删除一个角色
	public void del(Integer roleId) throws SQLException ;
	//根据Id查询角色
	public Role findById(Integer roleId) throws SQLException ;
	//无条件查询全部
	public List<Role> findAll() throws SQLException ;
	//批量删除
	public void delBatch(String ids) throws SQLException ;
	//获取总记录条数
	public int getcount(Map<String, Object> params) throws SQLException ;
	//分页查询或模糊分页查询
	public List<Role> findAllByPage(int currentPage,int pageSize,Map<String, Object> params) throws SQLException ;
	//根据角色名查询,实现查重的功能
	public Role findByRoleName(String rolename)throws SQLException ;
	//删除角色资源（权限）
	public void delRoleResource(String roleId) throws SQLException;
	//给角色添加权限
	public void addRoleResource(String roleId, Integer resourceId) throws SQLException;
	//根据角色ID获取资源ID
	public List<Integer> findResourceIdsById(int roleId)throws SQLException;
	//根据角色ID删除角色资源关系
	public void delRoleResource(int roleId) throws SQLException ;
	//批量删除角色资源关系
	public void delbatchRoleResource(String ids) throws SQLException;
	//根据角色Id查找用户Id
	public List<Integer> findUserIdsByroleId(Integer roleId)throws SQLException;
	//获取所有的角色名称
	public List<String> findAllRoleName()throws SQLException ;
	//根据角色名称获取所对应的用户人数
	public long getCountByRoleName(String roleName)throws SQLException ;
}
