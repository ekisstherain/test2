<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>历史记录</title>
<link href="${pageContext.request.contextPath}/resource/css/table.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-1.8.3.min.js" /></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/common.js" /></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/generateTable.js" /></script>

<script type="text/javascript">
	var header = [ [ {
		label : '操作人',
		name : 'operateUserName',
		width : '10%',
		rowSpan : 1,
		colSpan : 1
	}, {
		label : '操作明细',
		name : 'detail',
		width : '10%',
		rowSpan : 1,
		colSpan : 1
	}, {
		label : '操作时间',
		name : 'operateTime',
		width : '10%',
		rowSpan : 1,
		colSpan : 1,
		dataType : 'date',
		format : "yyyy-MM-dd"
	} ] ];

	// 记得添加ready模块
	$(function() {
		// 数据配置
		var cfg = {
			url : '${pageContext.request.contextPath}/history/getHistoryList.do',
			header : header,
			isShowCheckBox : false,
			pagination : true, // 是否分页
			pageSize : 50
		};
		$("#grid").gridGen(cfg); // 生成表格
	});
</script>
</head>
<body>
	<div class="table_layout">
		<p class="table_p"></p>
		<div id="grid"></div>
	</div>
</body>
</html>