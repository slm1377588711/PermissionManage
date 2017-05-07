package com.bdyc.service;

import java.sql.SQLException;
import java.util.List;

import com.bdyc.model.Resource;
import com.bdyc.model.TreeGrid;

public interface ResourceService {
	//添加角色
	public void addResource(Resource resource) throws SQLException;
	//修改角色
	public void updateResource(Resource resource)throws SQLException;
	//删除一个角色
	public void delResource(Integer resourceId) throws SQLException;
	//根据Id查询角色
	public Resource findById(int resourceId) throws SQLException;
	//无条件查询全部
	public List<Resource> findAll() throws SQLException;///
	//根据父ID查询
	public List<Resource> findByParentId(Integer parentId)throws SQLException ;
	//获取所有树形资源
	public List<TreeGrid> findAllTree()throws SQLException ;
}
