package com.bdyc.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.bdyc.model.Role;
import com.bdyc.model.User;
import com.bdyc.util.JdbcUtil;
import com.bdyc.util.Page;

public class UserDaoImpl implements UserDao {

	//添加用户
	@Override
	public int add(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "insert into t_user(username,password,deptName,province,city,county,createtime,updatetime) values(?,?,?,?,?,?,?,?)";
		Object[] params = {user.getUsername(),user.getPassword(),user.getDeptName(),user.getProvince(),user.getCity(),user.getCounty(),user.getCreatetime(),user.getUpdatetime()};
							
		qr.update(sql,params);
		//查找新添加的用户ID，因为用户Id是自增的，所以，最新添加的用户的Id就是Id的最大值将userId返回
		String sql1 = "select max(userId) from t_user";
		return qr.query(sql1, new ScalarHandler<Integer>());
	}

	//修改用户
	@Override
	public void update(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		System.out.println("修改用户---------");
		String sql = "update t_user set username = ?,password = ?,deptName = ?,province = ?,city = ?,county=?,updatetime  = ? where userId = ?";
		Object[] params = {user.getUsername(),user.getPassword(),user.getDeptName(),user.getProvince(),user.getCity(),user.getCounty(),user.getUpdatetime(),user.getUserId()};
		qr.update(sql,params);
	}
	//删除一条记录
	@Override
	public void del(Integer userId) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "delete from t_user where userId = ?";
		qr.update(sql,userId);
	}

	//根据Id查询
	@Override
	public User findById(Integer userId) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "select userId,username,password,deptName,province,city,county,createtime,updatetime from t_user where userId = ?";
		return qr.query(sql, new BeanHandler<User>(User.class), userId);
	}

	//查询全部
	@Override
	public List<User> findAll() throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "select userId,username,password,deptName,province,city,county,createtime,updatetime from t_user where username != 'admin'";
		return qr.query(sql, new BeanListHandler<User>(User.class));
	}
	//根据用户名和密码查询
	@Override
	public User findByUsernameAndPassword(String username, String password) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "select userId,username,password,deptName,province,city,county,createtime,updatetime from t_user where username =? and password = ?";
		return qr.query(sql, new BeanHandler<User>(User.class), username,password);
	}
	//批量删除
	@Override
	public void delBatch(String ids) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "delete from t_user where userId in ("+ids+")";
		System.out.println(sql);
		qr.update(sql);
	}

	
	//查询全部或模糊查询并分页
	@Override
	public List<User> findAllByPage(int currentPage,int pageSize,Map<String, Object> params) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource()); //
		String sql = "select userId,username,password,deptName,province,city,county,createtime,updatetime from t_user where username != 'admin'  ";
		//拼接模糊查询条件
		sql = joinSql(params, sql);
		
		//拼接分页
		sql+=" limit ?,?";
		return qr.query(sql, new BeanListHandler<User>(User.class),(currentPage-1)*pageSize,pageSize);
	}
	//获取总记录数量
	@Override
	public int getcount(Map<String, Object> params) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "select count(userId) from t_user  where username != 'admin' ";
		//拼接模糊查询条件
		sql = joinSql(params, sql);
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
		if(params.get("username") != null && !"".equals(params.get("username"))){
			//拼接用户名
			sql +=" and username like '%"+params.get("username")+"%'";
		}
		//所在单位
		if(params.get("deptName") != null && !"".equals(params.get("deptName"))){
			//拼接电话
			sql +=" and  deptName like '%"+params.get("deptName")+"%'";
		}
		//省份
		if(params.get("province") != null && !"".equals(params.get("province"))){
			//拼接生日
			sql +=" and  province like '%"+params.get("province")+"%'";
		}
		//市
		if(params.get("city") != null && !"".equals(params.get("city"))){
			//拼接生日
			sql +=" and  city like '%"+params.get("city")+"%'";
		}
		//县
		if(params.get("county") != null && !"".equals(params.get("county"))){
			//拼接生日
			sql +=" and  county like '%"+params.get("county")+"%'";
		}
		return sql;
	}

	//根据用户名查询
	@Override
	public User findByUsername(String username) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "select userId,username,password,deptName,province,city,county,createtime,updatetime from t_user where username =?";
		return qr.query(sql, new BeanHandler<User>(User.class), username);
	}

	//添加用户角色
	@Override
	public void addUserRole(int userId, Integer roleId) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "insert into t_user_role(userId,roleId) values(?,?)";
		qr.update(sql,userId,roleId);
	}
	//根据用户Id查找 用户角色
	@Override
	public List<Role> findUserRoleByUserId(Integer userId) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "select r.roleId,r.roleName,r.roleDesc  from t_role r,t_user_role ur, t_user u where r.roleId = ur.roleId and u.userId = ur.userId and u.userId = ?";
		return qr.query(sql, new BeanListHandler<Role>(Role.class), userId);
	}
	//根据用户Id删除用户角色关系
	@Override
	public void delUserRole(Integer userId) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "delete from t_user_role where userId = ?";
		System.out.println("删除用户角色的关系"+sql);
		qr.update(sql, userId);
		
	}
	//根据用户Id批量删除用户和角色的关系
	@Override
	public void delBatchUserRole(String ids) throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "delete from t_user_role where userId in("+ids+")";
		qr.update(sql);
		
	}
	//根据用户Id修改用户角色
	@Override
	public void updateUserRoleByUserId(Integer roleIds, Integer userId)
			throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql="UPDATE t_user_role SET roleId=? WHERE userId=?";
		Object[] params = {roleIds,userId};
		qr.update(sql,params);
	}

	@Override
	public void updatePassword(String username, String password)
			throws SQLException {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql="UPDATE t_user SET password=? WHERE username=?";
		Object[] params = {password,username};
		qr.update(sql,params);
		
		
	}

 

}
