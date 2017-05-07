<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script>
   		var urls = '${sessionScope.urls}';
   		console.log(urls);
   </script>
   <c:if test="${fn:contains(sessionScope.urls,'/role/update.jsp') }">
   <script>
   		$.canUpdate = true;
   </script>
   </c:if>
   <c:if test="${fn:contains(sessionScope.urls,'/role/del') }">
   <script>
   		$.canDel = true;
   </script>
   </c:if>
   <c:if test="${fn:contains(sessionScope.urls,'/role/delbatch') }">
   </c:if>
   <c:if test="${fn:contains(sessionScope.urls,'/role/grant.jsp') }">
   <script>
   		$.canGrant = true;
   </script>
   </c:if>
    <!-- 片断页面 -->
	<style>
		.btn{
			display: inline-block;
			width:16px;
			height:16px;
			text-indent: -999px;
		}
		.btn-edit{
			background:url(img/edit.png) no-repeat;
		}
		.btn-remove{
			background:url(img/remove.png) no-repeat;
		}
		.bdyc-role-toolbar{
			padding-top:10px;
		}
		.bdyc-role-form{
			margin-top:10px;
		}
	</style>
	
	<!-- 角色数据表格 -->
	<table id="bdyc-role-datagrid"></table> 
	<!-- 工具条 -->
	<div id="bdyc-role-toolbar" class="bdyc-role-toolbar">
		<a onclick="addRole()" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加角色</a>
		<a onclick="delbatchRole()" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-clear'">批量角色</a>
		<form id="bdyc-role-form" class="bdyc-role-form">
			角色名: <input type="text" id="rolename" class="easyui-textbox" data-options="iconCls:'icon-man',prompt:'请输入角色名'" name="rolename"/>
			<a href="javascript:;" onclick="searchRole()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
		</form>
	</div>
	<script>
	function searchRole(){
		//使用参数查询数据
		 $("#bdyc-role-datagrid").datagrid("load",{
			 	rolename:$("#rolename").val(),
			 	
			 });
	}
	
	/* 批量删除 */
	function delbatchRole(){
		var objs = $("#bdyc-role-datagrid").datagrid("getChecked");
		if(objs.length>0){
			$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
			    if (r){  
			    	var roleIds = "";
			    	//each循环 objs是选中的记录,也就是循环目标,index为元素下标,ele是一条记录
			    	$.each(objs,function(index,ele){
			    		//如果是最后一条的话则不加逗号分割
			    		if(index == objs.length -1){
			    			roleIds += ele.roleId;
			    		}else{
			    			roleIds += ele.roleId+",";
			    		}
			    	});
			    	var url= "role/RoleServlet?method=delbatch";  
			        $.get(url,"roleIds="+roleIds,function(data){
			        	var obj = $.parseJSON(data);
			        	if(obj.success){
			        		//刷新datagrid
			        		$("#bdyc-role-datagrid").datagrid("reload");
			        	}
			        	//提示信息
			        	$.messager.show({
							title:'我的消息',
							msg:obj.msg,
							timeout:2000,
							showType:'slide'
						});
			        });
			    }    
			}); 
		}else{
			$.messager.alert('警告','请至少选中一条数据进行删除','warning'); 
		}
	}
	
	
		/* 添加角色模态框 */
		function addRole(){
			//弹出添加模态框
			parent.addDialog=$('<div/>').dialog({    
			    title: '添加角色',
			    iconCls:'icon-add',
			    width: 600,    
			    height: 400,    
			    href: 'pages/role/add.jsp',    
			    modal: true   
			});   
		}
		//修改角色
		function updateRole(roleId){
			//弹出添加模态框
			parent.updateDialog = $('<div/>').dialog({    
			    title: '修改角色',
			    iconCls:'icon-edit',
			    width: 500,    
			    height: 350,    
			    href: 'pages/role/update.jsp',    
			    modal: true,
			    onBeforeOpen:function(){
			    	//将roleId传输给update.jsp
			    	parent.updateRoleId = roleId;
			    }  
			});   
		}
		
		//删除角色
		function delRole(roleId){
			$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
			    if (r){   
			    	var url="role/RoleServlet?method=delRole";
			    	$.get(url,"roleId="+roleId,function(data){
			    		var obj = $.parseJSON(data);
			    		if(obj.success){
			    			//刷新
			    			$("#bdyc-role-datagrid").datagrid("reload");
			    		}
			    		//提示信息
			        	$.messager.alert('警告',obj.msg,'warning');
			    	});		
			    }    
			});  
		}
		//给角色授权
		function shouquan(roleId){
			//弹出添加模态框  对话框
			parent.grantDialog = $('<div/>').dialog({    
			    title: '授权',
			    iconCls:'icon-edit',
			    width: 350,    
			    height: 500,    
			    href: 'pages/role/grant.jsp',    
			    modal: true,
			    onBeforeOpen:function(){
			    	//将roleId传输给update.jsp
			    	parent.grantRoleId = roleId;
			    }  
			});
		}
		$(function(){
			parent.roleDatagrid=$("#bdyc-role-datagrid").datagrid({
				url:"role/RoleServlet?method=list",
				fitColumns:true,//自适应列宽
				fit:true,//自适应屏幕
				pagination:true,//是否显示分页工具条
				rownumbers:true,//是否显示行号
				toolbar:"#bdyc-role-toolbar",//设置工具条
				columns:[[    
			        {field:'roleId',checkbox:true},    
			        {field:'roleName',title:'角色名',width:100,align:'center'},    
			        {field:'roleDesc',title:'角色描述',width:30,align:'center'},
			        {field:'oper',title:'操作',width:100,align:'center',
			        formatter:function(value,row,index){
			        	var html = "";
			        	if($.canUpdate){
			        		
			        	html +='<a onclick="updateRole('+row.roleId+')" class="btn btn-edit" href="javascript:;">修改</a>';
			        	html +='&nbsp;&nbsp;|&nbsp;&nbsp;';
			        	}
			        	if($.canDel){
			        		
			        	html +='<a onclick="delRole('+row.roleId+')" class="btn btn-remove" href="javascript:;">删除</a>';
			        	html +='&nbsp;&nbsp;|&nbsp;&nbsp;';
			        	}
			        	if($.canDel){
			        		html +='<a onclick="shouquan('+row.roleId+')" class="" href="javascript:;">授权</a>';
				        	return  html;
			        	}
			        
			        }}
			    ]] 
			});
		});
		
		
	</script>
