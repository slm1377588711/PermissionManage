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
    
    <form id="user-form-add" method="post">
    	<div class="form-group">
    		<label>用户名</label>
    		<input type="text" id="usernameadd" name="usernameadd" class="easyui-textbox" data-options="iconCls:'icon-man',prompt:'请输入用户名',required:true,missingMessage:'用户名不能为空'"/>
    	</div>
    	<div class="form-group">
    		<label>密码</label>
    		<input type="password" id="addpassword" name="addpassword" class="easyui-passwordbox" prompt="[a-z0-9A-Z]" iconWidth="28" data-options="required:true,missingMessage:'密码不能为空', validType:'length[6,12]'"> 
    	</div>
       <div class="form-group">
    		<label>所在单位</label>
    		<input type="text" id="adddeptName" name="adddeptName">
    	</div>
    	<div class="form-group">
	    	<div data-toggle="distpicker">
				  省:<select  name="addprovince" data-province="选择省"></select>
				  市:<select  name="addcity" data-city="选择市 "></select>
				  县:<select  name="addcounty" data-district="选择区 "></select>
			</div>
		</div>
		<div class="form-group">
    		<label>用户角色</label>
    		<input class="easyui-combobox" name="roleId"   
    		data-options="multiple:true,valueField:'roleId',textField:'roleName',url:'role/RoleServlet?method=getRoles'" />  
    		
    	</div>
    	<div class="form-group">
    		<label></label>
    		<a onclick="submitAddForm()" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>
    		<a onclick="closeAddDialog()" href="javascript:;"  class="easyui-linkbutton" data-options="iconCls:'icon-clear'">关闭</a>
    	</div>
    </form>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/distpicker.data.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/distpicker.js"></script>
    <script>
    	/* 验证用户名是否重复 */
    	$(function(){
    		$("#username").textbox({
    			validType:"remote['user/UserServlet?method=checkUserName','username']",
    			invalidMessage:"用户名无效"
    		});
    	});
    	/* 添加按钮的点击事件 */
    	function submitAddForm(){
    		$("#user-form-add").form("submit",{
    			url:"user/UserServlet?method=addUser",
    			onSubmit:function(){
    				//验证
    				return $("#user-form-add").form("validate");
    			},
    			success:function(data){
    				var obj = $.parseJSON(data);
    				if(obj.success){
    					//关闭(销毁)对话框
    					parent.addDialog.dialog("destroy");
    					//刷新datagrid表格
    					parent.userDatagrid.datagrid("reload");	
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