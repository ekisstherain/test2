<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.gdie.common.ConfigUtil,com.gdie.common.SessionInfo;"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>left</title>
<base target="main">
<link href="${pageContext.request.contextPath}/resource/css/left.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-1.8.3.min.js" /></script>
<script type="text/javascript">
	function logout() {
		if (confirm("您确定要退出控制面板吗？"))
			top.location = "${pageContext.request.contextPath}/logout.do";
		return false;
	}

	$(function() {
		$("li a").bind("click", function() {
			$("li a").css({
				"background-image" : "",
				"background-repeat" : "",
				"font-weight" : "",
				"color" : ""
			});
			$(this).css({
				"background-image" : "url(${pageContext.request.contextPath}/resource/images/menu_bg2.gif)",
				"background-repeat" : "no-repeat",
				"font-weight" : "bold",
				"color" : "#006600"
			});
		});

	});
</script>
</head>
<body>
	<%
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getProperty("sessionInfo"));
		String account = sessionInfo.getUser().getAccount();
	%>
	<div id="container">
		<h1 class="type">
			<a href="${pageContext.request.contextPath}/home.do">系统菜单</a>
		</h1>
		<div class="content">
			<img src="${pageContext.request.contextPath}/resource/images/menu_topline.gif" width="182" height="5" />
			<ul class="MM">
				<li><a href="${pageContext.request.contextPath}/home.do">欢迎界面</a></li>
				<li><a href="${pageContext.request.contextPath}/account/account.do">账单管理</a></li>
				<%
					if ("admin".equals(account)) {
				%>
				<li><a href="${pageContext.request.contextPath}/users/user.do">用户管理</a></li>
				<%
					}
				%>
				<li><a href="${pageContext.request.contextPath}/history/list.do">日志管理</a></li>
				<li><a href="javascript:void(0);" onclick="logout();">退出系统</a></li>
			</ul>
		</div>
	</div>
</body>
</html>