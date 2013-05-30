<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录页面</title>
<link href="${pageContext.request.contextPath}/resource/css/login.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-1.8.3.min.js" /></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jqueryExtend.js" /></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/common.js" /></script>

<script type="text/javascript">
	if (window != top) { // session超时问题
		top.location.href = location.href;
	}
	// 验证
	function validate() {
		if (!$("#account").val()) {
			return "请输入帐号!";
		}
		if (!$("#password").val()) {
			return "请输入密码!";
		}
		return true;
	}

	function login() {
		var res = validate();
		if (res === true) {
			$.post("${pageContext.request.contextPath}/login.do", $("form").serializeObject(), function(result) {
				if (result && result.success) {
					window.location.href = "${pageContext.request.contextPath}/index.do";
				} else {
					$("#error").html(result.message).show();
				}
			}, 'json');
		} else {
			$("#error").html(res).show();
		}
	}

	$(function() {
		// 检测是否登录过了
		$('body').hide();
		$.post('${pageContext.request.contextPath}/getLoginInfo.do', function(result) {
			if (result.success) {
				$('body').html('<div style="text-align: center; font-weight: bold; font-size: 16px;padding-top: 20px;"> 正在加载，请稍候...</div>').show();
				window.setTimeout("window.location.href = '${pageContext.request.contextPath}/index.do';", 500);//延时0.5秒 
			} else {
				$('body').show();
			}
		}, 'json');

		$("#login").bind("click", login);

		$("form input").bind("focus", function() { // 输入时隐藏错误提示
			$("#error").html("").hide();
		});

		$('body').bind('keyup', function(event) { // 增加回车提交功能
			//alert("OK");
			if (event.keyCode == '13') {
				$("#login").click(); // 点击登录
			}
		});
	});
</script>
</head>
<body>
	<div id="formwrapper">
		<form>
			<fieldset>
				<legend>用户登录</legend>
				<div>
					<label for="name">帐 号:</label>
					<input id="account" name="account" type="text" />
					<br>
				</div>
				<div>
					<label for="password">密 码:</label>
					<input id="password" name="password" type="password" />
					<br>
				</div>
				<div class="cookiechk">
					<label></label>
					<input id="login" type="button" class="buttom" value="登 录" />
					<span id="error"></span>
				</div>
			</fieldset>
		</form>
	</div>
</body>
</html>