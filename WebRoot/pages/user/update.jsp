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
    
    <form id="user-form-update">
    <input type="hidden" name="userIdUpdate"/>
    <input type="hidden" name="method" value="updateUser"/>
    	<div class="form-group">
    		<label>用户名</label>
    		<input type="text" id="usernameUpdate" name="usernameUpdate" class="easyui-textbox" data-options="iconCls:'icon-man',prompt:'请输入用户名',required:true,missingMessage:'用户名不能为空',editable:false"/>
    	</div>
    	<div class="form-group">
    		<label>密码</label>
    		<input type="password" name="passwordUpdate" class="easyui-passwordbox"  iconWidth="28" data-options="required:true,missingMessage:'密码不能为空', validType:'length[6,12]'"> 
    	</div>
    	  <div class="form-group">
    		<label>所在单位</label>
    		<input type="text" id="deptNameUpdate" name="deptNameUpdate">
    	</div>
    	<div class="form-group">
	    	<div data-toggle="distpicker">
				  省:<select  name="provinceUpdate" data-province="选择省"></select>
				  市:<select  name="cityUpdate" data-city="选择市 "></select>
				  县:<select  name="countyUpdate" data-district="选择区 "></select>
			</div>
		</div>
		<div class="form-group">
    		<label>用户角色</label>
    		<input class="easyui-combobox" name="roleIdUpdate"   
    		data-options="multiple:true,valueField:'roleId',textField:'roleName',url:'role/RoleServlet?method=getRoles'" />  
    		
    	</div>
    	<div class="form-group">
    		<label></label>
    		<a onclick="submitUpdateuserForm()" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改</a>
    		<a onclick="closeUpdateDialog()" href="javascript:;"  class="easyui-linkbutton" data-options="iconCls:'icon-clear'">关闭</a>
    	</div>
    </form>
      <script type="text/javascript" src="${pageContext.request.contextPath }/js/distpicker.data.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/distpicker.js"></script>
    <script>
    /* 在页面加载时就请求数据回显 */	
    $(function(){
    	var url = "user/UserServlet?method=findById";
    	$.get(url,"userId="+parent.updateUserId,function(data){
    		var obj = $.parseJSON(data);
    		for(var i=0;i<obj.length;i++){
    			 var roleId = "";
    			for(var j=0; j<obj[i].rolelist.length; j++){
	        		if(j == obj[i].rolelist.length-1){
	        			roleId +=obj[i].rolelist[j].roleId;
	        		}else{
	        			roleId +=obj[i].rolelist[j].roleId+",";
	        		}
	        	} 
    			$("#user-form-update").form("load",{
        			userIdUpdate:obj[i].userId,
        			usernameUpdate:obj[i].username,
        			passwordUpdate:obj[i].password,
        			deptNameUpdate:obj[i].deptName,
        			roleIdUpdate:roleId
        		});
    			$("select[name='provinceUpdate']").val(obj[i].province);
        		$("select[name='provinceUpdate']").trigger("change");
        		$("select[name='cityUpdate']").val(obj[i].city);
        		$("select[name='cityUpdate']").trigger("change");
        		$("select[name='countyUpdate']").val(obj[i].county);
    		}
    	});
    });
    
    	/* 修改按钮的点击事件 */
    	 function submitUpdateuserForm(){
    		$("#user-form-update").form("submit",{
    			url:"user/UserServlet",
    			onSubmit:function(){
    				//验证
    				return $("#user-form-update").form("validate");
    			},
    			success:function(data){
    				var obj = $.parseJSON(data);
    				if(obj.success){
    					//关闭(销毁)对话框
    					parent.updateDialog.dialog("destroy");
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
			parent.updateDialog.dialog("destroy");
		});
    	/* 添加用户的关闭按钮事件为销毁 */
    	function closeUpdateDialog(){
    		parent.updateDialog.dialog("destroy");
    	}
    </script>