<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<form id="user-form-updatepassword">
 <input type="hidden" name="method" value="updatePassword"/>
    	<div class="form-group">
    		<label>用户名</label>
    		<input type="text" value="${sessionScope.userInfo.username}" name="username" class="easyui-textbox" data-options="iconCls:'icon-man',prompt:'请输入用户名',required:true,missingMessage:'用户名不能为空',editable:false"/>
    	</div>
    	<div class="form-group">
    	<label>请输入旧密码</label>
 		<input id="oldpassword" name="oldpwd" onblur="checkOldPwd()" type="password"/>   
			<span id="oldPwdInfo" style="color:red;font-size:12px"></span>
		
		</div>
    	<div class="form-group">
    	<label>请输入新密码</label>
 		<input id="pwd" name="pwd" type="password" class="easyui-validatebox" data-options="required:true" />   
		</div>
		<div class="form-group">
		<label>请确认密码</label>
		<input id="rpwd" name="rpwd" type="password" class="easyui-validatebox"     
    required="required" validType="equals['#pwd']" />  
    </div>
    	<div class="form-group">
    		<label></label>
    		<a onclick="submitUpdatePasswordForm()" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改</a>
    		<a onclick="closeUpdatepasswordDialog()" href="javascript:;"  class="easyui-linkbutton" data-options="iconCls:'icon-clear'">关闭</a>
    	</div>
    </form>
    <script>
    //验证两次输入的密码
    $.extend($.fn.validatebox.defaults.rules, {    
        equals: {    
            validator: function(value,param){    
                return value == $(param[0]).val();    
            },    
            message: '两次输入的密码不一样'
        }    
    });
    //检查旧密码是否正确
    function checkOldPwd(){
		 var old = $("#oldpassword").val();
		 var oldPwd=${sessionScope.userInfo.password};
		 if(old!=oldPwd){
			 $("#oldPwdInfo").css({"color":"red"});
			 $("#oldPwdInfo").html("抱歉，您输入的密码有误");
			 $("#oldPwdInfo").focus();
			 return false;
		 }else{
			 $("#oldPwdInfo").css({"color":"green"});
			 $("#oldPwdInfo").html("正确，您可以继续填写以下项");
			 return true;
		 }
	 };	 
 	function submitUpdatePasswordForm(){
 		$("#user-form-updatepassword").form("submit",{
			url:"user/UserServlet",
			onSubmit:function(){
				//验证
				return $("#user-form-updatepassword").form("validate");
			},
			success:function(data){
				var obj = $.parseJSON(data);
				if(obj.success){
					//关闭(销毁)对话框
					parent.updatePasswordDialog.dialog("destroy");
					//重新登录
					alert("修改密码成功，请重新登录");
					window.location.href="${pageContext.request.contextPath }/login.jsp";
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
			parent.updatePasswordDialog.dialog("destroy");
		});
    	/* 修改密码的关闭按钮事件为销毁 */
    	function closeUpdatepasswordDialog(){
    		parent.updatePasswordDialog.dialog("destroy");
    	}
    </script>
    