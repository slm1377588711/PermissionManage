<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
 .form-group label{
			display:inline-block;
			width:80px;
			height:30px;
			line-height:50px;
			text-align:right;
			padding-right:20px;
}

</style>
    
    <form id="role-form-update" method="post">
    	<input type="hidden" id="roleId"  name="roleId"/>
    	<div class="form-group">
    		<label>角色名</label>
    		<input type="text" id="rolename" name="roleName" class="easyui-textbox" data-options="iconCls:'icon-man',prompt:'请输入角色名',required:true,missingMessage:'角色名不能为空'"/>
    	</div>
    	<div class="form-group">
    		<label>角色描述</label>
    		<input type="text" name="roleDesc" class="easyui-textbox" data-options="height:80,multiline:true" />
    	</div>
    	<div class="form-group">
    		<label></label>
    		<a onclick="submitUpdateForm()" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改</a>
    		<a onclick="closeUpdateDialog()" href="javascript:;"  class="easyui-linkbutton" data-options="iconCls:'icon-clear'">关闭</a>
    	</div>
    </form>
    <script>
    /* 在页面加载时就请求数据回显 */	
    $(function(){
    	var url = "role/RoleServlet?method=findById";
    	$.get(url,"roleId="+parent.updateRoleId,function(data){
    		var obj = $.parseJSON(data);
    		$("#role-form-update").form("load",{
    			roleId:obj.roleId,
    			roleName:obj.roleName,
    			roleDesc:obj.roleDesc
    		});
    	});
    });
    
    	/* 修改按钮的点击事件 */
    	function submitUpdateForm(){
    		$("#role-form-update").form("submit",{
    			url:"role/RoleServlet?method=updateRole",
    			onSubmit:function(){
    				//验证
    				return $("#role-form-update").form("validate");
    			},
    			success:function(data){
    				var obj = $.parseJSON(data);
    				if(obj.success){
    					//关闭(销毁)对话框
    					parent.updateDialog.dialog("destroy");
    					//刷新datagrid表格
    					parent.roleDatagrid.datagrid("reload");	
    				}
    				//提示信息
    				$.messager.show({
						title:'我的消息',
						msg:obj.msg,
						timeout:2000,
						showType:'slide'
					});
    			}
    		});
    	}
    	/* 为添加对话框关闭按钮(X)重置单击事件为销毁 */
		$(".panel-tool-close").click(function(){
			parent.updateDialog.dialog("destroy");
		});
    	/* 添加用户的关闭按钮事件为销毁 */
    	function closeUpdateDialog(){
    		parent.updateDialog.dialog("destroy");
    	}
    </script>