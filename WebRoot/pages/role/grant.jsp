<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!-- 权限树形结构 -->
    <ul id="grant_tree"></ul>
    
    <a onclick="submitGrant()"  href="javascript:;" class="easyui-linkbutton">授权</a>
    <a onclick="closeGrantDialog()" href="javascript:;" class="easyui-linkbutton">关闭</a>
    

    <script>
    /* 在页面加载时就请求数据回显 */	
    $(function(){
    	$("#grant_tree").tree({
    		url:"resource/ResourceServlet?method=getTree",
    		//显示复选框
    		checkbox:true,
    		//是否层叠选中状态
			cascadeCheck:false,
			//在数据加载成功以后触发
			onLoadSuccess:function(aa, node){
				//勾选已有权限节点
				//获取当前角色已有权限节点的ID
				var url = "role/RoleServlet?method=getRoleResourceIds";
				var data = "roleId="+parent.grantRoleId;
				$.get(url,data,function(resp){
					var ownIds = $.parseJSON(resp);
					//勾选已有节点
					if(ownIds.length > 0){
						$.each(ownIds,function(index,ele){
							//根据节点的id获取到节点对象：target
							var node = $("#grant_tree").tree("find",ele);
							//节点的DOM对象 node.target
							$("#grant_tree").tree("check",node.target);
						});
					}
				});
			}
    	});
    });
    
    	/* 授权按钮的点击事件 */
    	function submitGrant(){
    		//获取到所有勾选的节点的id
    		var resourceIds = "";
    		//得到树种被选中的资源节点集合
    		var checkedTree = $("#grant_tree").tree("getChecked");
    		//循环节点集合得到每个节点
    		$.each(checkedTree,function(index,ele){
    			//得到资源节点的ID，用“,”分割
    			if(index == checkedTree.lenght-1){
    				resourceIds+=ele.id;
    			}else{
    				resourceIds+=ele.id+",";
    			}
    		});
    		//传递两个参数 角色Id,资源ID集合
    		var url = "role/RoleServlet?method=grant";
    		var data = "roleId="+parent.grantRoleId+"&resourceIds="+resourceIds;
    		$.get(url,data,function(data){
    			var obj = $.parseJSON(data);
    			if(obj.success){
    				parent.grantDialog.dialog("destroy");
    			}
    			
    			$.messager.show({
					title:'我的消息',
					msg:obj.msg,
					timeout:2000,
					showType:'slide'
				});
    			
    		});
    	}
    	/* 为添加对话框关闭按钮(X)重置单击事件为销毁 */
		$(".panel-tool-close").click(function(){
			parent.grantDialog.dialog("destroy");
		});
    	/* 添加用户的关闭按钮事件为销毁 */
    	function closeGrantDialog(){
    		parent.grantDialog.dialog("destroy");
    	}
    </script>