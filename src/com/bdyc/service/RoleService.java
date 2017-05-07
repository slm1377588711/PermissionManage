package com.bdyc.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.bdyc.model.EcharModel;
import com.bdyc.model.Role;
import com.bdyc.util.Page;

public interface RoleService {
	//添加角色
	public void addRole(Role role) throws SQLException;
	//修改角色
	public void updateRole(Role role)throws SQLException;
	//删除一个角色
	public void delRole(Integer roleId) throws SQLException;
	//根据Id查询角色
	public Role findById(int roleId) throws SQLException;
	//无条件查询全部
	public List<Role> findAll() throws SQLException;///
	//批量删除
	public void delBatchRole(String ids)throws SQLException;
	//获取总记录条数
	public int getcount(Map<String, Object> params)throws SQLException;
	//分页查询或模糊分页查询
	public List<Role> findAllByPage(int currentPage,int pageSize, Map<String, Object> params)throws SQLException;
	//根据用户名查询,实现查重的功能
	public Role findByRoleName(String rolename)throws SQLException;
	//授权
	public void grant(String roleId, String[] resourceIdsArr) throws SQLException;
	//根据角色Id获取资源ID
	public List<Integer> findResourceIdsById(int roleId)throws SQLException;
	//获取图标数据
	public List<EcharModel> getRoleChart() throws SQLException ;
}
