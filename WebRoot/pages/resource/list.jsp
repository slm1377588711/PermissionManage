<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!-- 角色数据表格 -->
	<table id="resource-treedatagrid" style="width:600px;height:400px"></table>  
	<script>
	$(function(){
		$('#resource-treedatagrid').treegrid({    
		    url:'resource/ResourceServlet?method=treegrid',    
		    fit:true,
		    fitColumns:true,
		    rownumbers:true,   
		    idField:'resourceId',    
		    treeField:'resourceName',    
		    columns:[[    
		        {field:'resourceName',title:'资源名称',width:180},    
		        {field:'url',title:'资源地址',width:60,align:'right'},    
		        {field:'iconCls',title:'资源图标',width:80},    
		        {field:'type',title:'类型',width:80},
		        {field:'orederno',title:'排序号',width:80}   
		    ]]    
		}); 	
	});
	</script>
