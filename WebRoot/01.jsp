<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<!-- 引入easyui的核心库文件 -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/easyui/themes/default/easyui.css">   
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/easyui/themes/icon.css">   
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.11.3.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/easyui/jquery.easyui.min.js"></script>  
</head>
<body>
	<div class="easyui-panel" 
	data-options="
		title:'我的面板',
		collapsible:true,
		minimizable:true,
		maximizable:true,
		closable:true,
		width:400,
		height:500,
		onBeforeClose:function(){
			alert('关闭了');
		}" >
		fasdfasd
	</div>
	<hr>
	<div id="myPanel">
	
	</div>
	<a href="javascript:;" onclick="closePanel()" class="easyui-linkbutton" data-options="iconCls:'icon-clear'">关闭</a>
	<a href="javascript:;" onclick="openPanel()" class="easyui-linkbutton" data-options="iconCls:'icon-open'">打开</a>
	
	<div id="pp" class="easyui-pagination" data-options="total:2000,pageSize:10" style="background:#efefef;border:1px solid #ccc;"></div> 
	
	<script>
		function openPanel(){
			$("#myPanel").panel("open");
		}
		function closePanel(){
			$("#myPanel").panel("close");
		}
	
	
		$(function(){
				$('#pp').pagination({
					layout:['first','links','last']
				});
			
			
		
		
			$("#myPanel").panel({
				title:'我的面板',
				collapsible:true,
				minimizable:true,
				maximizable:true,
				closable:true,
				width:400,
				height:500,
				onBeforeClose:function(){
					alert('关闭了');
				}
			});
		});
	</script>
</body>
</html>