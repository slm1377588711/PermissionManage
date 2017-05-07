package com.bdyc.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.bdyc.model.Resource;
import com.bdyc.util.Page;

public interface ResourceDao {
	//添加资源
	public void add(Resource resource) throws SQLException ;
	//修改资源
	public void update(Resource resource) throws SQLException ;
	//删除一个资源
	public void del(Integer resourceId) throws SQLException ;
	//根据Id查询资源
	public Resource findById(Integer resourceId) throws SQLException ;
	//无条件查询全部
	public List<Resource> findAll() throws SQLException ;
	//根据父ID查询
	public List<Resource> findByParentId(Integer parentId)throws SQLException ;
	//查询当前用户拥有的权限
	public List<String> findUrlsByUserId(Integer userId) throws SQLException ;
}
