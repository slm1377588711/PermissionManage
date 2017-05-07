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
    
    <form id="role-form-add" method="post">
    	<div class="form-group">
    		<label>角色名</label>
    		<input type="text" id="rolenameadd" name="rolename" class="easyui-textbox" data-options="iconCls:'icon-man',prompt:'请输入角色名',required:true,missingMessage:'角色名不能为空'"/>
    	</div>
    	<div class="form-group">
    		<label>角色描述</label>
    		<input type="text" name="roleDesc" class="easyui-textbox" data-options="height:80,multiline:true" />
    	</div>
    	<div class="form-group">
    		<label></label>
    		<a onclick="submitAddRole()" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>
    		<a onclick="closeAddDialog()" href="javascript:;"  class="easyui-linkbutton" data-options="iconCls:'icon-clear'">关闭</a>
    	</div>
    </form>
    <script>
    	/* 验证角色名是否重复 */
    	$(function(){
    		$("#rolenameadd").textbox({
    			validType:"remote['role/RoleServlet?method=checkRoleName','rolename']",
    			invalidMessage:"角色名无效"
    		});
    	});
    	/* 添加按钮的点击事件 */
    	function submitAddRole(){
    		$("#role-form-add").form("submit",{
    			url:"role/RoleServlet?method=addRole",
    			onSubmit:function(){
    				//验证
    				return $("#role-form-add").form("validate");
    			},
    			success:function(data){
    				var obj = $.parseJSON(data);
    				if(obj.success){
    					//关闭(销毁)对话框
    					parent.addDialog.dialog("destroy");
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
			parent.addDialog.dialog("destroy");
		});
    	/* 添加用户的关闭按钮事件为销毁 */
    	function closeAddDialog(){
    		parent.addDialog.dialog("destroy");
    	}
    </script>