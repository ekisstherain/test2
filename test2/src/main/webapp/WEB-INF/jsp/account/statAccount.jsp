<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>统计账单</title>


<link href="${pageContext.request.contextPath}/resource/css/table.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/js/datepicker/css/jquery-ui.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-1.8.3.min.js" /></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/datepicker/js/jquery-ui-datepicker.js" /></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jqueryExtend.js" /></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/common.js" /></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/generateTable.js" /></script>
<script type="text/javascript">
	//验证
	function validate() {
		if (!$("#startDate").val()) {
			return "请选择开始日期!";
		}
		if (!$("#endDate").val()) {
			return "请选择结束日期!";
		}
		return true;
	}

	$(function() {
		$("#startDate").datepicker();
		$("#endDate").datepicker();

		$("#save").bind('click', stat);
		$("#balanceBtn").bind('click', balanced);

		// 加载结算单数据
		getBalancedList();
	});

	var header = [ [ {
		label : '姓名',
		name : 'customer',
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
		label : '消费金额',
		name : 'consume',
		width : '10%',
		rowSpan : 1,
		colSpan : 1
	}, {
		label : '应付',
		name : 'balance',
		width : '10%',
		rowSpan : 1,
		colSpan : 1
	} ] ];

	var header2 = [ [ {
		label : '姓名',
		name : 'customer',
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
		label : '消费金额',
		name : 'consume',
		width : '10%',
		rowSpan : 1,
		colSpan : 1
	}, {
		label : '应付',
		name : 'balance',
		width : '10%',
		rowSpan : 1,
		colSpan : 1
	}, {
		label : '结算日期',
		name : 'balancedDate',
		width : '10%',
		rowSpan : 1,
		colSpan : 1,
		dataType : 'date',
		format : "yyyy-MM-dd"
	} ] ];

	/**
	 * 添加账单
	 */
	function stat() {
		var res = validate();
		if (res !== true) {
			alert(res);
			return;
		}
		var param = {};
		param.startDate = new Date($("#startDate").val());
		param.endDate = new Date($("#endDate").val());

		// 数据配置
		var cfg = {
			url : '${pageContext.request.contextPath}/account/stat.do',
			param : param,
			header : header,
			isShowCheckBox : false, // 是否显示选择框
			pagination : false, // 是否分页
			rowDblClickEvent : function() {
			}
		};
		$("#grid").gridGen(cfg); // 生成表格
	}

	// 结算
	function balanced() {
		var res = validate();
		if (res !== true) {
			alert(res);
			return;
		}
		if (confirm('你确定要结算吗? 结算不可恢复!')) {
			var param = {};
			param.startDate = new Date($("#startDate").val());
			param.endDate = new Date($("#endDate").val());

			$.post('${pageContext.request.contextPath}/account/balanced.do', param, function(res) {
				if(res.success){
					alert('结算成功!');
					getBalancedList(); // 重新加载数据
				}else{
					alert(res.message);
				}
			}, "json");
		}
	}

	function getBalancedList() {
		// 数据配置
		var cfg = {
			url : '${pageContext.request.contextPath}/account/getBalancedList.do',
			header : header2,
			isShowCheckBox : false, // 是否显示选择框
			pagination : true
		};
		$("#balancedGrid").gridGen(cfg); // 生成表格
	}
</script>
<style type="text/css">
#customerList input {
	vertical-align: middle;
	width: 30px;
}

.buttons input {
	cursor: pointer;
}
</style>
</head>
<body>
	<%-- <div class="manage_title">
		<img src="${pageContext.request.contextPath}/resource/images/manage_03.gif" />
	</div> --%>
	<div class="manage_bg">
		开始日期：
		<input id="startDate" name="startDate" type="text" readonly="readonly" style="width: 200px;" />
		结束日期：
		<input id="endDate" name="endDate" type="text" readonly="readonly" style="width: 200px;" />
		<input id="save" type="button" value="统 计" />
		<input id="balanceBtn" type="button" value="结 算" />
		<input id="cancel" onclick="window.history.back(-1);" type="button" value="返 回" />
	</div>

	<div class="table_layout">
		<p class="table_p">统计数据</p>
		<div id="grid"></div>
	</div>
	<div class="table_layout">
		<p class="table_p">结算数据</p>
		<div id="balancedGrid"></div>
	</div>
</body>
</html>