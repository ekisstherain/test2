<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>top</title>
<link href="${pageContext.request.contextPath}/resource/css/top.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-1.8.3.min.js" /></script>
<script type="text/javascript">
	$(function() {
		$.post("${pageContext.request.contextPath}/getUserInfo.do", function(data) {
			if (data) {
				$("#userName").html(data.name);
			}
		}, "json");
	});
</script>
</head>
<body>
	<div class="top">
		<div class="title">
			<span id="userName"></span>,欢迎您！
		</div>
	</div>
</body>
</html>