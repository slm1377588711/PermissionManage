<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/echarts.common.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.11.3.js"></script>
		<style>
			.echarts{
				width: 600px;
				height: 400px;
			}
		</style>
	</head>
	<body>
		  <div id="index-bar-chart" style="width:500px;height:400px; border:1px solid red; float:left;"></div> 
		  <div id="index-pie-chart" style="width:500px;height:400px; border:1px solid red; float:right;"></div>   
		    
		<script type="text/javascript">
		$(function(){
 			var url = "${pageContext.request.contextPath }/role/RoleServlet?method=getRoleChart";
 			var json = null;
 			$.get(url,null,function(resp){
 				console.log(resp);
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
 				        subtext: '纯属虚构',
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
    </script>
	</body>
</html>
