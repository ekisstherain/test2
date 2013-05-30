<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>编辑账单</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/themes/default/jquery.mobile-1.3.1.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/jqm-datebox.min.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-1.8.3.min.js" /></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery.mobile-1.3.1.min.js" /></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jqm-datebox.core.min.js" /></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jqm-datebox.mode.calbox.min.js" /></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery.mobile.datebox.i18n.zh-CN.utf8.js" /></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jqueryExtend.js" /></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/common.js" /></script>

<script type="text/javascript">
	var userTypeDef = [ {
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

	// 选中的消费者集合
	var selectedCustomers = [];

	$(function() {
		initTitle();
		var operate = getUrlParam('operate');
		if (operate == 'modify') { // 编辑处理
			$("#title").html("编辑账单");
			initUseTypeList();
			$.when(initCustomerList()).done(initModify);
			$('#save').bind('click', modify);
		} else { // 添加处理
			initAdd();
			$('#save').bind('click', add);
		}
		$('#save').trigger('create');
	});

	// 初始化标题
	function initTitle() {
		$.post("${pageContext.request.contextPath}/getUserInfo.do", function(data) {
			if (data) {
				$("#userName").html("(" + data.name + ", 欢迎你)");
			}
		}, "json");
	}

	// 初始化添加
	function initAdd() {
		$("#title").html("添加账单");
		$("#useDate").attr("value", new Date().format('yyyy-MM-dd'));
		initUseTypeList();
		initCustomerList();
	}

	// 初始化修改
	function initModify() {
		var id = getUrlParam('id');
		if (id) {
			$.post('${pageContext.request.contextPath}/account/getAccountById.do', {
				id : id
			}, function(res) {
				if (res) {
					$("#payor").val(res.payorName).attr("disabled", "disabled");
					$("#pay").val(res.pay); // 账号
					$("#useDate").val(new Date(res.useDate).format("yyyy-MM-dd"));
					$("#useType").val(res.useType).selectmenu('refresh'); // 设置后刷新显示的值
					$("#remark").val(res.remark);
					$.each($('input[type="checkbox"]'), function(key, value) {
						$.each(res.detailKeys, function(k1, v1) {
							if (value.id === v1.customerId) {
								value.checked = true;
							}
						});
					});
					$("#customerList").trigger('create');
				}
			}, "json");
		} else {
			alert('参数错误!');
		}
	}

	/**
	 * 修改账单
	 */
	function modify() {
		$("#save").addClass('ui-disabled'); // 禁止重复点击事件
		var res = validate();
		if (res !== true) {
			alert(res);
			return;
		}
		var param = $("form").serializeObject();
		param.useDate = new Date(param.useDate);
		param.pay = parseFloat(param.pay);
		param.customers = selectedCustomers;
		param.id = getUrlParam('id');
		$.post('${pageContext.request.contextPath}/account/modify.do', param, function(res) {
			if (res) {
				alert('修改成功!');
				window.location.href = '${pageContext.request.contextPath}/phone/phoneIndex.do?time=' + new Date();
			} else {
				alert('修改失败');
			}
		}, "json");
	}

	//验证
	function validate() {
		var floatRep = /^\d+(\.\d+)?$/; // 验证输入是否为浮点数
		var res = true;
		if (!floatRep.test($("#pay").val())) {
			res = "请输入消费金额，必须是数字!";
		}
		if (!$("#useDate").val()) {
			res = "请选择使用日期!";
		}
		selectedCustomers = [];
		$.each($('input[type="checkbox"]'), function(key, value) {
			if (value.checked) {
				selectedCustomers.push(value.id);
			}
		});
		if (selectedCustomers.length == 0) {
			res = "至少选择一位消费者!";
		}
		if (res !== true) {
			$("#save").removeClass('ui-disabled'); // 验证不通过，重新启用按钮
		}
		return res;
	}

	/**
	 * 添加账单
	 */
	function add() {
		$("#save").addClass('ui-disabled'); // 禁止重复点击事件
		var res = validate();
		if (res !== true) {
			$("#error").html(res);
			window.location.href = "#alerts";
			return;
		}
		var param = $("form").serializeObject();
		param.useDate = new Date(param.useDate);
		param.pay = parseFloat(param.pay);
		param.customers = selectedCustomers;
		$.post('${pageContext.request.contextPath}/account/add.do', param, function(res) {
			if (res.success) {
				alert('创建成功!');
				window.location.href = '${pageContext.request.contextPath}/phone/phoneIndex.do?time=' + new Date();
			} else {
				alert(res.message);
			}
		}, "json");
	}

	// 初始化使用类型列表
	function initUseTypeList() {
		var useTypeList = $("#useTypeList");
		var select = '<select name="useType" id="useType">{0}</select>';
		var optons = '';
		$.each(userTypeDef, function(key, value) {
			optons += formatString('<option value="{0}">{1}</option>', value.id, value.name);
		});
		$("#useTypeList").html(formatString(select, optons)).trigger("create");
	}

	// 初始化消费者列表
	function initCustomerList() {
		var html = '';
		var fieldSet = '<fieldset data-role="controlgroup" data-type="horizontal">{0}</fieldset>';
		var checkBoxTag = '<input id="{0}" type="checkbox" value="{1}" ><label for="{2}">{3}</label>';
		$.post('${pageContext.request.contextPath}/account/getCustomer.do', function(res) {
			if (res) {
				$.each(res, function(key, value) {
					html += formatString(checkBoxTag, value.id, value.id, value.id, value.name);
				});
				$("#customerList").html(formatString(fieldSet, html)).trigger("create");
			}
		}, "json");
	}
</script>
</head>
<body>
	<div data-role="page" id="editAccount">
		<div data-role="header" data-position="inline">
			<a href="${pageContext.request.contextPath}/phone/phoneIndex.do" rel="external" data-icon="delete">取消</a>
			<h1>
				<span id="title"></span> <span id="userName" style="font-size: 12px;"></span>
			</h1>
			<a id="save" href="#" data-icon="plus">保存</a>
		</div>
		<div data-role="content">
			<form>
				<fieldset class="ui-grid-a">
					<div class="ui-block-a" style="width: 30%; text-align: right;">
						<label for="payor" style="padding-top: 15px;">付&nbsp;&nbsp;费&nbsp;人：</label>
					</div>
					<div class="ui-block-b" style="width: 70%;">
						<input id="payor" type="text" disabled="disabled" value="${user.name}">
					</div>
				</fieldset>
				<fieldset class="ui-grid-a">
					<div class="ui-block-a" style="width: 30%; text-align: right;">
						<label for="pay" style="padding-top: 15px;">付费金额： </label>
					</div>
					<div class="ui-block-b" style="width: 70%;">
						<input id="pay" name="pay" type="number" data-clear-btn="true">
					</div>
				</fieldset>
				<fieldset class="ui-grid-a">
					<div class="ui-block-a" style="width: 30%; text-align: right;">
						<label for="useDate" style="padding-top: 15px;">使用日期： </label>
					</div>
					<div class="ui-block-b" style="width: 70%;">
						<input id="useDate" name="useDate" type="text" data-role="datebox" data-options='{"mode": "calbox"}'>
					</div>
				</fieldset>

				<fieldset class="ui-grid-a">
					<div class="ui-block-a" style="width: 30%; text-align: right; padding-top: 18px;">
						<label>使用类型： </label>
					</div>
					<div id="useTypeList" class="ui-block-b" style="width: 70%;"></div>
				</fieldset>

				<fieldset class="ui-grid-a">
					<div class="ui-block-a" style="width: 30%; text-align: right;">
						<label for="remark" style="padding-top: 15px;">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注： </label>
					</div>
					<div class="ui-block-b" style="width: 70%;">
						<textarea id="remark" name="remark"></textarea>
					</div>
				</fieldset>

				<fieldset class="ui-grid-a">
					<div class="ui-block-a" style="width: 30%; text-align: right; margin-top: 17px;">
						<label>消&nbsp;&nbsp;费&nbsp;者： </label>
					</div>
					<div id="customerList" class="ui-block-b" style="width: 70%;"></div>
				</fieldset>
			</form>

		</div>

		<div id="footer" data-role="footer" data-position="fixed" style="text-align: center;">
			<h4>Copyright 2013 ::801</h4>
		</div>
	</div>

	<div id="alerts" data-role="dialog" data-close-btn="right">
		<div data-role="header">
			<h3>错误信息</h3>
		</div>
		<div id="error" data-role="content" style="text-align: center; vertical-align: middle;"></div>
	</div>
</body>
</html>
