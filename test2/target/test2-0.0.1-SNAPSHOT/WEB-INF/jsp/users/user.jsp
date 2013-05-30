<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>显示用户信息</title>
<link href="${pageContext.request.contextPath}/resource/css/table.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-1.8.3.min.js" /></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/common.js" /></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/generateTable.js" /></script>
<script type="text/javascript">
	var header = [ [ {
		label: '帐号',
		name : 'account',
		width : '10%',
		rowSpan : 1,
		colSpan : 1
	}, {
		label: '姓名',
		name : 'name',
		width : '10%',
		rowSpan : 1,
		colSpan : 1
	}, {
		label: '角色',
		name : 'roleName',
		width : '10%',
		rowSpan : 1,
		colSpan : 1
	}, {
		label : "创建时间",
		name : 'createdate',
		width : '10%',
		rowSpan : 1,
		colSpan : 1
	}, {
		label: '修改时间',
		name : 'lastmodifydate',
		width : '10%',
		rowSpan : 1,
		colSpan : 1
	} ] ];

	// 记得添加ready模块
	$(function() {
		// 数据配置
		var cfg = {
			url : '/users/getUserList.do',
			header : header,
			rowDblClickEvent : function() {
				modify();
			},
			isShowCheckBox : true
		};
		$("#grid").gridGen(cfg); // 生成表格
	});

	/**
	 * 编辑信息事件
	 */
	function modify() {
		var selected = $('#grid input[type="checkbox"]:checked');// 获取选中的数据
		if (selected.length > 0) {
			location.href = getRootName() + '/users/editUser.do?operate=modify&id=' + selected[0].id;
		} else {
			alert('请选择一条数据!');
		}
	}
</script>
</head>
<body>
	<%-- <div class="manage_title">
		<img src="${pageContext.request.contextPath}/resource/images/manage_03.gif" />
	</div> --%>
	<div class="manage_bg">
		<input type="button" value="添加" class="btn70" onclick="location.href=getRootName() + '/users/editUser.do?operate=add';" /> 
		<input type="button" value="编辑" class="btn70" onclick="modify();" />
	</div>
	<div class="table_layout">
		<p class="table_p"></p>
		<div id="grid"></div>
	</div>
</body>
</html>