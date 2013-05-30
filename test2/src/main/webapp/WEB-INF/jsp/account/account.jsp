<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>显示账单信息</title>
<link href="${pageContext.request.contextPath}/resource/css/table.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-1.8.3.min.js" /></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/common.js" /></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/generateTable.js" /></script>
<script type="text/javascript">
	var statusList = [ {
		name : "未结算",
		id : 'unbalanced'
	}, {
		name : "已结算",
		id : 'balanced'
	} ];
	var selectedData = [ {
		name : '晚餐',
		id : 'dinner'
	}, {
		name : '午餐',
		id : 'lunch'
	}, {
		name : '午晚餐',
		id : 'lunchDinner'
	}, {
		name : '公用',
		id : 'publicUse'
	} ];

	var header = [ [ {
		label : '付费人',
		name : 'payorName',
		width : '10%',
		rowSpan : 1,
		colSpan : 1
	}, {
		label : '付费金额',
		name : 'pay',
		width : '10%',
		rowSpan : 1,
		colSpan : 1
	}, {
		label : '使用类型',
		name : 'useType',
		width : '10%',
		rowSpan : 1,
		colSpan : 1,
		dataType : 'custom',
		format : function(data) {
			$.each(selectedData, function(key, value) {
				if (value.id === data) {
					data = value.name;
					return false;
				}
			});
			return data;
		}
	}, {
		label : '使用日期',
		name : 'useDate',
		width : '10%',
		rowSpan : 1,
		colSpan : 1,
		dataType : 'date',
		format : "yyyy-MM-dd"
	}, {
		label : '消费者',
		name : 'customersName',
		width : '20%',
		rowSpan : 1,
		colSpan : 1
	}, {
		label : '备注',
		name : 'remark',
		width : '10%',
		rowSpan : 1,
		colSpan : 1
	}, {
		label : '状态',
		name : 'status',
		width : '10%',
		rowSpan : 1,
		colSpan : 1,
		dataType : 'custom',
		format : function(data) {
			$.each(statusList, function(key, value) {
				if (value.id === data) {
					data = value.name;
					return false;
				}
			});
			return data;
		}
	} ] ];

	// 记得添加ready模块
	$(function() {
		initDataList();
	});

	// 初始化数据列表
	function initDataList() {
		var param = {
			conditions : $("#conditions").val()
		};
		// 数据配置
		var cfg = {
			url : '${pageContext.request.contextPath}/account/getAccountList.do',
			param : param,
			header : header,
			rowDblClickEvent : function() {
				modify();
			},
			isShowCheckBox : true
		};
		$("#grid").gridGen(cfg); // 生成表格
	}

	/**
	 * 编辑信息事件
	 */
	function modify() {
		var selected = $('#grid input[type="checkbox"]:checked');// 获取选中的数据
		if (selected.length > 0) {
			location.href = '${pageContext.request.contextPath}/account/editAccount.do?operate=modify&id=' + selected[0].id;
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
		查询条件：
		<input type="text" id="conditions" name="conditions" />
		<input type="button" value="查询" class="btn70" onclick="initDataList();" />
		<input type="button" value="添加" class="btn70" onclick="location.href='${pageContext.request.contextPath}/account/editAccount.do?operate=add';" />
		<input type="button" value="编辑" class="btn70" onclick="modify();" />
		<input type="button" value="统计" class="btn70" onclick="location.href='${pageContext.request.contextPath}/account/statAccount.do;'" />
	</div>
	<div class="table_layout">
		<p class="table_p"></p>
		<div id="grid"></div>
	</div>
</body>
</html>