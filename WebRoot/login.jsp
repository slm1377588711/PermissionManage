<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title></title>
		<link rel="shortcut icon" href="favicon.ico">
		<link rel="stylesheet" href="${pageContext.request.contextPath }/css/login.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.11.3.js"></script>
	</head>
	<body class="login_body">
		<div class="login_container">
			<div class="login_form">
				<div class="login_title">
					<div class="login_title_center">
						<img src="${pageContext.request.contextPath }/img/login_logo.png" alt="" />
						<span>博达远创考试系统</span>
					</div>
				</div>
				<div class="login_content">
					<form id="login_form">
					<div class="form-group">
						<label>用户名</label>
						<input type="text" name="username" value="${sessionScope.userInfo.username}"/>
					</div>
					<div class="form-group">
						<label>密　码</label>
						<input type="text" name="password"/>
					</div>
					<div class="form-group">
						<label>类　型</label>
						<select name="type">
							<option value="1">学员</option>
							<option value="0">管理员</option>
						</select>
						<input class="code_input" maxlength="6" type="text" name="code" />
						<img class="code_img" src="img/code.png">
					</div>
					<div class="btn">
						<a href="javascript:;" onclick="login()" class="btn_blue">登录</a>
						<a href="javascript:;" class="btn_default">注册</a>
					</div>
					</form>
				</div>
			</div>
			<div class="login_foot">
				<p>推荐使用Chrome内核、FireFox浏览器访问系统</p>
				<p>Copyright © bdyc.com  All Right Reserved 2009-2016</p>
			</div>
		</div>
		
		<script>
			function login(){
				var url = "${pageContext.request.contextPath}/user/UserServlet?method=login";
				var data = $("#login_form").serialize();
				$.post(url,data,function(resp){
					var obj = $.parseJSON(resp);
					if(obj.success){
						window.location.href="${pageContext.request.contextPath}/index.jsp";
					}else{
						alert(obj.msg);
					}
				});
			}
		</script>
	</body>
</html>
