package com.bdyc.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.bdyc.dao.ResourceDao;
import com.bdyc.dao.ResourceDaoImpl;
import com.bdyc.model.Resource;
import com.bdyc.model.TreeGrid;

public class ResourceServiceImpl implements ResourceService {
	private ResourceDao resourceDao = new ResourceDaoImpl();
	//添加角色
	@Override
	public void addResource(Resource resource) throws SQLException {
		resourceDao.add(resource);
	}
	//无条件查询全部
	@Override
	public List<Resource> findAll() throws SQLException {
		return resourceDao.findAll();
	}
	//删除一个角色
	@Override
	public void delResource(Integer resourceId) throws SQLException {
		resourceDao.del(resourceId);
	}
	//根据Id查询用户
	@Override
	public Resource findById(int resourceId) throws SQLException {
		return resourceDao.findById(resourceId);
	}
	//修改用户
	@Override
	public void updateResource(Resource resource) throws SQLException {
		resourceDao.update(resource);
	}
	//根据父ID查询
	@Override
	public List<Resource> findByParentId(Integer parentId) throws SQLException {
		return resourceDao.findByParentId(parentId);
	}
	//获取所有的树形资源
	/**
	 * 一级菜单的子菜单	rootTree.setChildren(secondTreeGridList);
	 * 			二级菜单 seondTreeGrid.setChildren(ThirTreeGridList);
	 * 				三级按钮 没有子菜单
	 */
	@Override
	public List<TreeGrid> findAllTree() throws SQLException {
		System.out.println("进入findallTree方法。。。");
		
		/* 第一级菜单  parentId为null*/
		List<Resource> rootResourceList = resourceDao.findByParentId(null);
		//存放TreeGrid模型的一级菜单
		List<TreeGrid> rootTreeList = new ArrayList<TreeGrid>();
		//将Resource模型转换为TreeGrid模型 添加到存放TreeGrid模型的List里面
		for (Resource rootResource : rootResourceList) {
			TreeGrid rootTree = new TreeGrid();
			//设置ID
			rootTree.setId(rootResource.getResourceId());
			//设置标题文本
			rootTree.setText(rootResource.getResourceName());
			//设置是否打开状态
			rootTree.setState("open");
			
			/*二级菜单根据parentId查询当前父节点的子节点		*/				/*父节点下的子节点Id*/
			List<Resource> secondResourceList = resourceDao.findByParentId(rootResource.getResourceId());
			//存放TreeGrid模型的二级菜单
			List<TreeGrid> secondTreeGridList = new ArrayList<TreeGrid>();
			//循环当前父节点的子节点，加入TreeGrid模型的List
			for (Resource seondResource : secondResourceList) {
				TreeGrid seondTreeGrid = new TreeGrid();
				seondTreeGrid.setId(seondResource.getResourceId());
				seondTreeGrid.setText(seondResource.getResourceName());
				seondTreeGrid.setState("open");
				
				//继续根据parentId查询当前父节点（二级菜单）的子节点
				List<Resource> ThirResourceList = resourceDao.findByParentId(seondResource.getResourceId());
				//存放TreeGrid的三级菜单list集合里
				List<TreeGrid> ThirTreeGridList = new ArrayList<TreeGrid>();
				//循环当前父节点（二级菜单）的子节点，加入TreeGrid模型的List
				for (Resource ThirResource : ThirResourceList) {
					TreeGrid thirTreeGrid = new TreeGrid();
					thirTreeGrid.setId(ThirResource.getResourceId());
					thirTreeGrid.setText(ThirResource.getResourceName());
					thirTreeGrid.setState("open");
					ThirTreeGridList.add(thirTreeGrid);
				}
				//设置二级子菜单的子菜单
				seondTreeGrid.setChildren(ThirTreeGridList);
				//把TreeGrid类型的资源添加到 存放TreeGrid的list集合里
				secondTreeGridList.add(seondTreeGrid);
			}
			
			//设置一级的子菜单
			rootTree.setChildren(secondTreeGridList);
			//把TreeGrid类型的资源放入集合
			rootTreeList.add(rootTree);
		}
		return rootTreeList;
	}


}
