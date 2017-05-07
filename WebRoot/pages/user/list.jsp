<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script>
   		var urls = '${sessionScope.urls}';
   		console.log(urls);
   </script>
   <!-- 判断用户是否拥有修改权限 -->
   <c:if test="${fn:contains(sessionScope.urls,'/user/update.jsp') }">
   <script>
   		$.canUpdate = true;
   </script>
   </c:if>
   <!-- 判断用户是否拥有删除权限 -->
   <c:if test="${fn:contains(sessionScope.urls,'/user/del') }">
   <script>
   		$.canDel = true;
   </script>
   </c:if>
   <!-- 判断用户是否拥有批量删除权限 -->
   <c:if test="${fn:contains(sessionScope.urls,'/user/delbatch') }">
    <script>
   		$.canDelbatch = true;
   </script>
   </c:if>
     <!-- 判断用户是否拥有添加用户权限 -->
   <c:if test="${fn:contains(sessionScope.urls,'/user/add.jsp') }">
    <script>
   		$.canAdd = true;
   </script>
   </c:if>
       <!-- 判断用户是否拥有查询用户权限 -->
   <c:if test="${fn:contains(sessionScope.urls,'/user/search') }">
    <script>
   		$.cansearch = true;
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
		.bdyc-user-toolbar{
			padding-top:10px;
		}
		.bdyc-user-form{
			margin-top:10px;
		}
	</style>
	
	<!-- 用户数据表格 -->
	<table id="bdyc-user-datagrid"></table> 
	<!-- 工具条 -->
	<div id="bdyc-user-toolbar" class="bdyc-user-toolbar">

		<a onclick="addUser()" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加用户</a>
		<a onclick="delbatchUser()" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-clear'">批量用户</a>
		<form id="bdyc-user-form" class="bdyc-user-form">
			用户名: <input type="text" id="usernamesel" class="easyui-textbox" data-options="iconCls:'icon-man',prompt:'请输入用户名'" name="username"/>
			<a href="javascript:;" onclick="searchUser()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
		</form>
		
	</div>
	<script>
	function searchUser(){
		//使用参数查询数据
		 $("#bdyc-user-datagrid").datagrid("load",{
			 	username:$("#usernamesel").val(),
			 });
	}
	
	/* 批量删除 */
	function delbatchUser(){
		var objs = $("#bdyc-user-datagrid").datagrid("getChecked");
		if(objs.length>0){
			$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
			    if (r){  
			    	var userIds = "";
			    	//each循环 objs是选中的记录,也就是循环目标,index为元素下标,ele是一条记录
			    	$.each(objs,function(index,ele){
			    		//如果是最后一条的话则不加逗号分割
			    		if(index == objs.length -1){
			    			userIds += ele.userId;
			    		}else{
			    			userIds += ele.userId+",";
			    		}
			    	});
			    	var url= "user/UserServlet?method=delbatch";  
			        $.get(url,"userIds="+userIds,function(data){
			        	var obj = $.parseJSON(data);
			        	if(obj.success){
			        		//刷新datagrid
			        		$("#bdyc-user-datagrid").datagrid("reload");
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
	
	
		/* 添加用户模态框 */
		function addUser(){
			//弹出添加模态框
			parent.addDialog=$('<div/>').dialog({    
			    title: '添加用户',
			    iconCls:'icon-add',
			    width: 600,    
			    height: 400,    
			    href: 'pages/user/add.jsp',    
			    modal: true   
			});   
		}
		//修改用户
		function updateUser(userId){
			//弹出添加模态框
			parent.updateDialog = $('<div/>').dialog({    
			    title: '修改用户',
			    iconCls:'icon-edit',
			    width: 500,    
			    height: 350,    
			    href: 'pages/user/update.jsp',    
			    modal: true,
			    onBeforeOpen:function(){
			    	//将userId传输给update.jsp
			    	parent.updateUserId = userId;
			    }  
			});   
		}
		
		//删除用户
		function delUser(userId){
			$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
			    if (r){   
			    	var url="user/UserServlet?method=delUser";
			    	$.get(url,"userId="+userId,function(data){
			    		var obj = $.parseJSON(data);
			    		if(obj.success){
			    			//刷新
			    			$("#bdyc-user-datagrid").datagrid("reload");
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
		}
		$(function(){
			parent.userDatagrid=$("#bdyc-user-datagrid").datagrid({
				url:"user/UserServlet?method=list",
				fitColumns:true,//自适应列宽
				fit:true,//自适应屏幕
				pagination:true,//是否显示分页工具条
				rownumbers:true,//是否显示行号
				toolbar:"#bdyc-user-toolbar",//设置工具条
				columns:[[    
			        {field:'userId',checkbox:true},    
			        {field:'username',title:'用户名',width:100,align:'center'},    
			        {field:'deptName',title:'所在单位',width:100,align:'center'},
			        {field:'rolelist',title:'拥有角色',width:100,align:'center',
				        formatter:function(value,row,index){
				        	var roleName = "";
				        	for(var i=0; i<value.length; i++){
				        		if(i == value.length-1){
				        			roleName +=value[i].roleName;
				        		}else{
				        			roleName +=value[i].roleName+",";
				        		}
				        	}
				        	return roleName;
				        }},
			        
			        {field:'province',title:'省',width:60,align:'center'},
			        {field:'city',title:'市',width:60,align:'center'},
			        {field:'county',title:'县',width:60,align:'center'},
			        {field:'createtime',title:'创建时间',width:100,align:'center',
			        	formatter:function(value,row,index){
				        	var d = new Date(value);
				        	return d.Format("yyyy-MM-dd hh:mm:ss");
				        }
			        },
			        {field:'updatetime',title:'修改时间',width:100,align:'center',
			        	formatter:function(value,row,index){
				        	var d = new Date(value);
				        	return d.Format("yyyy-MM-dd hh:mm:ss");
				        }},
			        {field:'oper',title:'操作',width:100,align:'center',
			        formatter:function(value,row,index){
			        	var html = "";
			        	if($.canUpdate){
			        		html +='<a onclick="updateUser('+row.userId+')" class="btn btn-edit" href="javascript:;">修改</a>';
				        	html +='&nbsp;&nbsp;|&nbsp;&nbsp;';
			        	}
			        	if($.canDel){
			        	html +='<a onclick="delUser('+row.userId+')" class="btn btn-remove" href="javascript:;">删除</a>';
			        	}
			        	
			        	return  html;
			        }}
			    ]] 
			});
		});
		
		
	</script>
