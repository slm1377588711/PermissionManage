<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>系统首页</title>
    <!-- 引入easyui的核心库文件 -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/easyui/themes/default/easyui.css">   
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/easyui/themes/icon.css">   
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.11.3.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/easyui/jquery.easyui.min.js"></script>  
	<script type="text/javascript" src="${pageContext.request.contextPath }/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/plugin/plugin.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/echarts.common.min.js" ></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/index.css">   
  </head>
  
  <body class="easyui-layout">   
    <div data-options="region:'north'" style="height:52px;">
    	<div class="bdyc-north-logo">
    		<a href="index.jsp"><img style="height:50px;" src="${pageContext.request.contextPath }/img/bdyc.jpg"/></a>
    	</div>
    	<div class="bdyc-north-info">
    		欢迎：<span>${sessionScope.userInfo.username}</span>
    		<a onClick="updatePassword()" href="javascript:;">修改密码</a>
    		<a onclick="outlogin()" href="javascript:;">注销</a>
    	</div>
    </div>   
    <div data-options="region:'south',title:'&copy;版权所有2012-2020',collapsible:false,border:false"></div>   
    <div data-options="region:'west',title:'系统导航'" style="width:210px;">
    	<ul id="bdyc-west-nav"></ul> 
    </div>   
    <div data-options="region:'center',border:false">
    	<div id="bdyc-center-tabs" class="easyui-tabs" data-options="fit:true">   
		    <div title="首页" style="padding:30px;" data-options="iconCls:'icon-home'">   
		      	  <div id="index-bar-chart" style="width:510px;height:410px;margin-top:30px; border:1px solid red; float:left;"></div> 
				  <div id="index-pie-chart" style="width:510px;height:410px;margin-top:30px; border:1px solid red; float:right;"></div>   
		
		    </div>   
		</div> 
    </div>   
 	<script>
 	function outlogin(){
 		alert("注销");
 		var url="${pageContext.request.contextPath }/user/UserServlet?method=outlogin";
 		$.get(url,null,function(resp){
 			if(resp){
				window.location.href="${pageContext.request.contextPath }/login.jsp";
			}
 		});
 	}
 	//修改密码弹框
 	function updatePassword(){
 		parent.updatePasswordDialog=$('<div/>').dialog({    
		    title: '修改密码',
		    iconCls:'icon-edit',
		    width: 600,    
		    height: 400,    
		    href: 'pages/user/updatePssword.jsp',    
		    modal: true   
		});  
 	}
 	//获取报表数据
 	$(function(){
			var url = "${pageContext.request.contextPath }/role/RoleServlet?method=getRoleChart";
			var json = null;
			$.get(url,null,function(resp){
				if(resp){
					json = $.parseJSON(resp);
					charts(json);
				}
			});
	});
	
			//渲染图表
	 		function charts(json){
	 			var roleNameData = [];
	 			var roleNumData = [];
	 			var rolePrevData = [];
	 			$.each(json,function(index,ele){
	 				roleNameData.push(ele.roleName);
	 				roleNumData.push(ele.userSum);
	 				rolePrevData.push({
	 					value:ele.userSum,
	 					name:ele.roleName
	 				});
	 			});
	 			
	 		
	 			/* 渲染首页图表 */
	 			var indexBarChart =  echarts.init($("#index-bar-chart")[0]);
	 			// 指定图表的配置项和数据
		        var option = {
		            title: {
		                text: '角色用户比例图'
		            },
		            tooltip: {},
		            legend: {
		                data:['人数']
		            },
		            xAxis: {
		                data: roleNameData
		            },
		            yAxis: {},
		            series: [{
		                name: '人数',
		                type: 'bar',
		                data: roleNumData
		            }]
		        };
		        indexBarChart.setOption(option);
		        
		        /* 渲染首页图表 */
	 			var indexPieChart =  echarts.init($("#index-pie-chart")[0]);
	 			// 指定图表的配置项和数据
		        var option1 = {
				    title : {
				        text: '角色用户比例图',
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'item',
				        formatter: "{a} <br/>{b} : {c} ({d}%)"
				    },
				    legend: {
				        orient: 'vertical',
				        left: 'left',
				        data: roleNameData
				    },
				    series : [
				        {
				            name: '用户占用比',
				            type: 'pie',
				            radius : '55%',
				            center: ['50%', '60%'],
				            data:rolePrevData,
				            itemStyle: {
				                emphasis: {
				                    shadowBlur: 10,
				                    shadowOffsetX: 0,
				                    shadowColor: 'rgba(0, 0, 0, 0.5)'
				                }
				            }
				        }
				    ]
				};
		        indexPieChart.setOption(option1);
	 		}
 	
 		$(function(){
 			/* 为导航栏渲染树形结构样式 */
 			$("#bdyc-west-nav").tree({
 				url:"${pageContext.request.contextPath }/user/UserServlet?method=getNavTree",
 				onClick:function(node){
 					//判断当前用户是否拥有该权限（url）
 					var urls = '${sessionScope.urls}';
 					
 					if(urls.indexOf(node.attributes.url) == -1){
 						//给出提示
 						$.messager.alert('警告','您没有该模块操作权限，请联系管理员','warning');   
 						return;
 					}
 					//判断单击的是否父节点，node：单击的节点对象
 					if(!$("#bdyc-west-nav").tree("isLeaf",node.target)){
 						$("#bdyc-west-nav").tree("toggle",node.target);
 					}else{//叶子节点：添加选项卡
 						//判断选项卡面板中是否已经存在当前选项卡，存在：选中 不存在：添加
 						if($("#bdyc-center-tabs").tabs("exists",node.text)){
 							$("#bdyc-center-tabs").tabs("select",node.text);
 						}else{
 							$("#bdyc-center-tabs").tabs("add",{
	 							title:node.text,
	 							iconCls:node.iconCls,
	 							closable:true,
	 							//默认情况下该方式：还能加载目标地址页面中的body体中的内容
	 							href:"pages"+node.attributes.url
 							});
 						}
 						
 					}
 				}
 			});
 		});
 	</script>
 
 </body> 
</html>
