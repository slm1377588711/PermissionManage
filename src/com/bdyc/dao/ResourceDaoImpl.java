package com.bdyc.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.junit.Test;

import com.bdyc.model.Resource;
import com.bdyc.util.JdbcUtil;

public class ResourceDaoImpl implements ResourceDao {

	//添加资源
	@Override
	public void add(Resource resource) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "insert into t_resource(parentId,resourceName,url,iconCls,type,orederno) values(?,?,?,?,?,?)";
		Object[] params = {resource.getParentId(),resource.getResourceName(),resource.getUrl(),resource.getIconCls(),resource.getType(),resource.getOrederno()};
		qr.update(sql,params);
	}

	//修改资源
	@Override
	public void update(Resource resource) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "update t_resource set parentId = ?,resourceName = ?,url = ?,iconCls = ?,type = ?,orederno = ? where resourceId = ?";
		Object[] params = {resource.getParentId(),resource.getResourceName(),resource.getUrl(),resource.getIconCls(),resource.getType(),resource.getOrederno()};
		qr.update(sql,params);
	}
	//删除一条记录
	@Override
	public void del(Integer resourceId) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "delete from t_resource where resourceId = ?";
		qr.update(sql,resourceId);
	}

	//根据Id查询
	@Override
	public Resource findById(Integer resourceId) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "select resourceId,parentId,resourceName,url,iconCls,type,orederno from t_resource where resourceId = ?";
		return qr.query(sql, new BeanHandler<Resource>(Resource.class), resourceId);
	}
	
	
	
	//查询全部
	@Override
	public List<Resource> findAll() throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "select resourceId, parentId,resourceName,url,iconCls,type,orederno from t_resource order by orederno asc";
		return qr.query(sql, new BeanListHandler<Resource>(Resource.class));
	}
	//根据父ID查询
	@Override
	public List<Resource> findByParentId(Integer parentId) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "select resourceId,parentId,resourceName,url,iconCls,type,orederno from t_resource where";
		if(parentId==null){
			sql+=" parentId is ?";
		}else{
			sql+=" parentId = ?";
		}
		sql+=" order by orederno asc";
		
		System.out.println("根据父Id查询的sql语句"+sql);
		return qr.query(sql, new BeanListHandler<Resource>(Resource.class), parentId);
	
	}
	//查询当前用户拥有的权限
	@Override
	public List<String> findUrlsByUserId(Integer userId) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "select DISTINCT r.url from t_resource r,role_resource ur where r.resourceId = ur.resourceId and ur.roleId in" +
				"(select roleId from t_user_role where userId = ?)";
		return qr.query(sql, new ColumnListHandler<String>("url"),userId);
	}
}
