package com.bdyc.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import com.bdyc.dao.RoleDao;
import com.bdyc.dao.RoleDaoImpl;
import com.bdyc.dao.UserDao;
import com.bdyc.dao.UserDaoImpl;
import com.bdyc.model.EcharModel;
import com.bdyc.model.Role;
import com.bdyc.model.User;

public class RoleServiceImpl implements RoleService {
	private RoleDao roleDao = new RoleDaoImpl();
	private UserDao userDao = new UserDaoImpl();
	//添加角色
	@Override
	public void addRole(Role role) throws SQLException {
		roleDao.add(role);
	}
	//无条件查询全部
	@Override
	public List<Role> findAll() throws SQLException {
		return roleDao.findAll();
	}
	//删除一个角色
	@Override
	public void delRole(Integer roleId) throws SQLException {
		/*1.清除用户和角色的关系
		 * 判断此角色下是否有关联的用户
		 * 如果有，则抛出异常不能删除
		 * 2.清除角色与资源的关系*/
		//根据roleId查询User的Id
		List<Integer> userIdslist = roleDao.findUserIdsByroleId(roleId);
		if(userIdslist!=null && userIdslist.size()>0){
			throw new RuntimeException("当前角色下有关联的用户，您不能删除");
		}
		roleDao.delRoleResource(roleId);
		roleDao.del(roleId);
	}
	//根据Id查询用户
	@Override
	public Role findById(int roleId) throws SQLException {
		return roleDao.findById(roleId);
	}
	//修改用户
	@Override
	public void updateRole(Role role) throws SQLException {
		roleDao.update(role);
	}
	//批量删除
	@Override
	public void delBatchRole(String ids) throws SQLException {
		//先批量删除角色和资源的关系
		roleDao.delbatchRoleResource(ids);
		roleDao.delBatch(ids);
	}
	//获取总记录条数
	@Override
	public int getcount(Map<String, Object> params) throws SQLException {
		return roleDao.getcount(params);
	}
	//分页查询或模糊分页查询
	@Override
	public List<Role> findAllByPage(int currentPage,int pageSize,Map<String,Object> params) throws SQLException {
		return roleDao.findAllByPage(currentPage,pageSize,params);
	}
	//根据用户名查询,实现查重的功能
	@Override
	public Role findByRoleName(String rolename) throws SQLException {
		return roleDao.findByRoleName(rolename);
	}
	//授权
	@Override
	public void grant(String roleId, String[] resourceIdsArr) throws SQLException {
		//删除当前角色历史权限
		roleDao.delRoleResource(roleId);
		//添加最新权限
		if(resourceIdsArr != null){
			for (int i = 0; i < resourceIdsArr.length; i++) {
				roleDao.addRoleResource(roleId,Integer.valueOf(resourceIdsArr[i]));
			}
		}
	}
	@Override
	public List<Integer> findResourceIdsById(int roleId) throws SQLException {
		return roleDao.findResourceIdsById(roleId);
	}
	@Override
	public List<EcharModel> getRoleChart() throws SQLException {
		List<EcharModel> chartModelList = new ArrayList<EcharModel>();
		//获取所有角色的名称
		List<String> roleNameList = roleDao.findAllRoleName();
		//获取角色对应的用户人数
		for (String roleName : roleNameList) {
			EcharModel em = new EcharModel();
			long num = roleDao.getCountByRoleName(roleName);
			em.setRoleName(roleName);
			em.setUserSum(num);			
			chartModelList.add(em);
		}
		
		return chartModelList;
	}


}
