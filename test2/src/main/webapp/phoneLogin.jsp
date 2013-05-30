<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>手机端登录页面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/themes/default/jquery.mobile-1.3.1.min.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-1.8.3.min.js" /></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery.mobile-1.3.1.min.js" /></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jqueryExtend.js" /></script>

<script type="text/javascript">
	// 验证
	function validate() {
		var res = true;
		if (!$("#account").val()) {
			res = "请输入帐号!";
		}
		if (!$("#password").val()) {
			res = "请输入密码!";
		}
		if (res !== true) {
			$("#login").removeClass('ui-disabled'); // 启用
		}
		return res;
	}

	function login() {
		$("#login").addClass('ui-disabled'); // 禁用
		var res = validate();
		if (res === true) {
			$.post("${pageContext.request.contextPath}/login.do", $("form").serializeObject(), function(result) {
				if (result && result.success) {
					window.location.href = "${pageContext.request.contextPath}/phone/phoneIndex.do";
				} else {
					$("#error").html(result.message);
					window.location.href = "#alerts";
				}
			}, 'json');
		} else {
			$("#error").html(res);
			window.location.href = "#alerts";
		}
	}

	$(function() {
		$('body').hide();
		$.post('${pageContext.request.contextPath}/getLoginInfo.do', function(result) {
			if (result.success) {
				$('body').html('<div style="text-align: center; font-weight: bold; font-size: 16px;padding-top: 20px;"> 正在加载，请稍候...</div>').show();
				window.setTimeout("window.location.href = '${pageContext.request.contextPath}/phone/phoneIndex.do';", 500);//延时0.5秒 
			} else {
				$('body').show();
			}
		}, 'json');

		$("#login").bind("click", login);
	});
</script>
</head>
<body>
	<div data-role="page" id="home">
		<div data-role="header" data-position="inline">
			<a href="#" data-icon="delete">重 置</a>
			<h1>系统登录</h1>
			<a id="login" href="#" data-icon="check">登 录</a>
		</div>
		<div data-role="content">
			<form>
				<label for="account">帐号:</label>
				<input type="text" name="account" id="account" placeholder="请输入帐号" value="" />

				<label for="account">密码:</label>
				<input type="password" name="password" id="password" placeholder="请输入密码" value="" />
			</form>

			<!-- <fieldset data-role="controlgroup" data-type="horizontal" class="right-btn">
				<a href="#" data-role="button" data-icon="delete" data-inline="true" >重 置</a>
				<a href="#" data-role="button" data-icon="check" data-inline="true" >登 录</a>
			</fieldset> -->

		</div>
		<div data-role="footer" data-position="fixed">
			<%-- 页脚添加工具栏  --%>
			<!-- <div data-role="navbar">
				<ul>
					<li><a href="#" data-icon="delete">重置</a></li>
					<li><a href="#" data-icon="check">登录</a></li>
				</ul>
			</div> -->
			<h4>Copyright 2013 ::801</h4>
		</div>
	</div>

	<div id="alerts" data-role="dialog" data-close-btn="right">
		<div data-role="header">
			<h3>错误信息</h3>
		</div>
		<div id="error" data-role="content" style="height: 3.0em; text-align: center; vertical-align: middle;"></div>
	</div>
</body>
</html>
