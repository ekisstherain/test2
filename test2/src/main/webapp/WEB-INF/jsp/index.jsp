<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统首页</title>
</head>
<frameset rows="64,*" frameborder="no">
	<frame src="top.do" noresize="noresize" name="topFrame" scrolling="no" marginwidth="0" marginheight="0" />
	<frameset cols="200,*" id="frame" style="overflow: auto;" frameborder="yes">
		<frame src="left.do" name="leftFrame" noresize="noresize" scrolling="no" />
		<frame src="home.do"  name="main" marginwidth="0" marginheight="0" frameborder="0" scrolling="auto" />
	</frameset>
</frameset>
<noframes>
	<body></body>
</noframes>
</html>