package com.bdyc.install;

import java.sql.SQLException;

import com.bdyc.model.Resource;
import com.bdyc.service.ResourceService;
import com.bdyc.service.ResourceServiceImpl;

public class IntallResource {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("初始化开始....");
		ResourceService resourceService = new ResourceServiceImpl();
		//系统管理:顶层菜单					parentId,resourceName,url,iconCls,type,orederno	
		// 系统                                                 资源IDnull 父ID 资源名称 资源路劲   资源图标  资源类型(1:菜单,0:按钮)  排序
		try {
			Resource xtgl = new Resource(null, null, "系统管理", null, "icon-sys", 1, 1);
			resourceService.addResource(xtgl);//xtgl：1
			//二级菜单 					 资源IDnull 父ID 资源名称        资源路劲                                资源图标      资源类型(1:菜单,0:按钮)  排序
			Resource yhgl = new Resource(null, 1, "用户管理","/user/list.jsp","icon-man", 1,11);
			resourceService.addResource(yhgl);//yhgl：2
			
			//二级菜单 					 资源IDnull 父ID 资源名称        资源路劲                                资源图标          资源类型(1:菜单,0:按钮)  排序
			Resource jsgl = new Resource(null, 1, "角色管理","/role/list.jsp","icon-role", 1, 12);
			resourceService.addResource(jsgl);//jsgl：3
			
			Resource zygl = new Resource(null, 1, "资源管理", "/resource/list.jsp","icon-resource", 1,  13);
			resourceService.addResource(zygl);//zygl：4
			
			//三级按钮
			Resource yhtj = new Resource(null, 2, "用户添加","/user/add.jsp", "icon-add", 0,  21);
			resourceService.addResource(yhtj);//yhtj：5
			Resource yhxg = new Resource(null, 2, "用户修改","/user/update.jsp", "icon-edit", 0,  22);
			resourceService.addResource(yhxg);//yhtj：5
			Resource yhsc = new Resource(null, 2, "用户删除","/user/del", "icon-clear", 0,  23);
			resourceService.addResource(yhsc);//yhtj：5
			Resource yhplsc = new Resource(null, 2, "用户批量删除", "/user/delbatch", "icon-remove", 0, 24);
			resourceService.addResource(yhplsc);//yhtj：5
			Resource yhcx = new Resource(null, 2, "用户模糊查询",  "/user/search","icon-search", 0, 25);
			resourceService.addResource(yhcx);//yhtj：5
			
			Resource jstj = new Resource(null, 3, "角色添加","/role/add.jsp", "icon-add", 0,  31);
			resourceService.addResource(jstj);//jstj：5
			Resource jsxg = new Resource(null, 3, "角色修改", "/role/update.jsp","icon-edit", 0,  32);
			resourceService.addResource(jsxg);//yhtj：5
			Resource jssc = new Resource(null, 3, "角色删除", "/role/del","icon-clear", 0,  33);
			resourceService.addResource(jssc);//yhtj：5
			Resource jscx = new Resource(null, 3, "角色模糊查询","/role/search", "icon-search", 0,  34);
			resourceService.addResource(jscx);//yhtj：5
			Resource jssq = new Resource(null, 3, "角色授权","/role/grant.jsp",  "icon-search", 0, 35);
			resourceService.addResource(jssq);//yhtj：5
			
			Resource zytj = new Resource(null, 4, "资源添加", "/resource/add.jsp","icon-add", 0,  41);
			resourceService.addResource(zytj);//jstj：5
			Resource zyxg = new Resource(null, 4, "资源修改", "/resource/update.jsp", "icon-add", 0, 42);
			resourceService.addResource(zyxg);//jstj：5
			Resource zysc = new Resource(null, 4, "资源删除", "/resource/del",  "icon-add", 0,43);
			resourceService.addResource(zysc);//jstj：5
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		System.out.println("初始化结束....");
		
	}

}
