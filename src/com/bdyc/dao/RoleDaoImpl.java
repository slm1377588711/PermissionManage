package com.bdyc.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.bdyc.model.Role;
import com.bdyc.util.JdbcUtil;
import com.bdyc.util.Page;

public class RoleDaoImpl implements RoleDao {

	//根据角色Id删除角色和资源的关系
	public void delRoleResource(int roleId) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "delete from role_resource where roleId = ?";
		System.out.println("删除角色和资源的关系"+sql);
		qr.update(sql, roleId);
	}
	
	//添加角色
	@Override
	public void add(Role role) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "insert into t_role(roleName,roleDesc) values(?,?)";
		Object[] params = {role.getRoleName(),role.getRoleDesc()};
		qr.update(sql,params);
	}

	//修改角色
	@Override
	public void update(Role role) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "update t_role set roleName = ?,roleDesc = ? where roleId = ?";
		Object[] params = {role.getRoleName(),role.getRoleDesc(),role.getRoleId()};
		System.out.println("修改语句"+sql);
		qr.update(sql,params);
	}
	//删除一条记录
	@Override
	public void del(Integer roleId) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "delete from t_role where roleId = ?";
		qr.update(sql,roleId);
	}

	//根据Id查询
	@Override
	public Role findById(Integer roleId) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "select roleId,roleName,roleDesc from t_role where roleId = ?";
		return qr.query(sql, new BeanHandler<Role>(Role.class), roleId);
	}

	//查询全部
	@Override
	public List<Role> findAll() throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "select roleId,roleName,roleDesc from t_role";
		return qr.query(sql, new BeanListHandler<Role>(Role.class));
	}
	//批量删除
	@Override
	public void delBatch(String ids) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "delete from t_role where roleId in ("+ids+")";
		System.out.println(sql);
		qr.update(sql);
	}

	
	//查询全部或模糊查询并分页
	@Override
	public List<Role> findAllByPage(int currentPage,int pageSize,Map<String, Object> params) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource()); //
		String sql = "select roleId,roleName,roleDesc from t_role where 1=1";
		//拼接模糊查询条件
		sql = joinSql(params, sql);
		
		//拼接分页
		sql+=" limit ?,?";
		System.out.println("查询内容"+sql);
		return qr.query(sql, new BeanListHandler<Role>(Role.class),(currentPage-1)*pageSize,pageSize);
	}
	//获取总记录数量
	@Override
	public int getcount(Map<String, Object> params) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "select count(roleId) from t_role where 1=1";
		//拼接模糊查询条件
		sql = joinSql(params, sql);
		System.out.println("总记录数"+sql);
		long count =  qr.query(sql, new ScalarHandler<Long>());
		return (int)count;
	}

	/**封装的模糊查询条件
	 * 拼接模糊条件
	 * @param params
	 * @param sql
	 * @return
	 */
	private String joinSql(Map<String, Object> params, String sql) {
		if(params.get("rolename") != null && !"".equals(params.get("rolename"))){
			//拼接用户名
			//rolename LIKE '%人%'
			sql +=" and roleName LIKE '%"+params.get("rolename")+"%'";
		}
		return sql;
	}

	//根据用户名查询
	@Override
	public Role findByRoleName(String rolename) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "select roleId,roleName,roleDesc from t_role where roleName =?";
		return qr.query(sql, new BeanHandler<Role>(Role.class), rolename);
	}
	//删除角色权限
	@Override
	public void delRoleResource(String roleId) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "delete from role_resource where roleId = ?";
		qr.update(sql, roleId);
	}
	//添加角色权限（给角色授权）
	@Override
	public void addRoleResource(String roleId, Integer resourceId) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "insert into role_resource(roleId,resourceId) values(?,?)";
		qr.update(sql, roleId,resourceId);
	}
	//根据角色ID获取资源ID
	@Override
	public List<Integer> findResourceIdsById(int roleId) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "select resourceId from role_resource where roleId = ? ";
		return qr.query(sql, new ColumnListHandler<Integer>("resourceId"),roleId);
	}
	//批量删除角色资源关系
	@Override
	public void delbatchRoleResource(String ids) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "delete from role_resource where roleId in("+ids+")";
		qr.update(sql);
		
	}
	//根据角色Id查找用户Id
	@Override
	public List<Integer>  findUserIdsByroleId(Integer roleId) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "select userId from t_user_role where roleId=?";
		return qr.query(sql, new ColumnListHandler<Integer>("userId"),roleId);
	}

	@Override
	public List<String> findAllRoleName() throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "select roleName from t_role";
		return qr.query(sql, new ColumnListHandler<String>("roleName"));
	}

	@Override
	public long getCountByRoleName(String roleName) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "select count(ur.userId) from t_user_role ur,t_role r where ur.roleId = r.roleId and r.roleName = ?";
		return qr.query(sql, new ScalarHandler<Long>(1),roleName);
	}

}
