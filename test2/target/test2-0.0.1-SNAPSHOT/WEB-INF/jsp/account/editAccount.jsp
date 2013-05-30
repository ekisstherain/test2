<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑用户</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/edit.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/js/datepicker/css/jquery-ui.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-1.8.3.min.js" /></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/datepicker/js/jquery-ui-datepicker.js" /></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jqueryExtend.js" /></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/common.js" /></script>
<script type="text/javascript">
	//使用类型的数据
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

	// 选中的消费者集合
	var selectedCustomers = [];

	//验证
	function validate() {
		var floatRep = /^\d+(\.\d+)?$/; // 验证输入是否为浮点数
		if (!floatRep.test($("#pay").val())) {
			return "请输入消费金额，必须是数字!";
		}
		if (!$("#useDate").val()) {
			return "请选择使用日期!";
		}
		selectedCustomers = [];
		$.each($('input[type="checkbox"]'), function(key, value) {
			if (value.checked) {
				selectedCustomers.push(value.id);
			}
		});
		if (selectedCustomers.length == 0) {
			return "至少选择一位消费者!";
		}
		return true;
	}

	$(function() {
		$("#useDate").datepicker();
		var operate = getUrlParam('operate');
		if (operate == 'modify') { // 编辑处理
			$('#tableTitle').html('编辑账单信息');
			$('#save').attr('value', '修 改');
			$('#save').bind('click', modify);
			initUseTypeList();
			$.when(initCustomerList()).done(initModify);
		} else { // 添加处理
			initUseTypeList();
			initCustomerList();
			$('#tableTitle').html('创建账单信息');
			$('#save').attr('value', '创 建');
			$('#save').bind('click', add);
		}

		// 全选
		$("#selectAll").bind("click", function() {
			var unselected = ("反 选" !== this.value);
			this.value = (unselected ? "反 选" : "全 选");
			$.each($('input[type="checkbox"]'), function(key, value) {
				value.checked = unselected;
			});
		});
	});

	/**
	 * 添加账单
	 */
	function add() {
		var res = validate();
		if (res !== true) {
			alert(res);
			return;
		}
		var param = $("form").serializeObject();
		param.useDate = new Date(param.useDate);
		param.pay = parseFloat(param.pay);
		param.customers = selectedCustomers;
		$.post(getRootName() + '/account/add.do', param, function(res) {
			if (res.success) {
				alert('创建成功!');
				window.history.back(-1);
			} else {
				alert(res.message);
			}
		}, "json");
	}

	/**
	 * 初始化修改数据
	 */
	function initModify() {
		var id = getUrlParam('id');
		if (id) {
			$.post(getRootName() + '/account/getAccountById.do', {
				id : id
			}, function(res) {
				if (res) {
					console.info(res);
					//$("#name").val(res.name).attr("disabled", "disabled"); // 姓名
					$("#payor").val(res.payorName).attr("disabled", "disabled");
					$("#pay").val(res.pay); // 账号
					$("#useDate").val(new Date(res.useDate).format("yyyy-MM-dd"));
					$("#useTypeList").val(res.useType);
					$("#remark").val(res.remark);
					$.each($('input[type="checkbox"]'), function(key, value) {
						$.each(res.detailKeys, function(k1, v1) {
							if (value.id === v1.customerId) {
								value.checked = true;
							}
						});
					});
				}
			}, "json");
		}
	}

	/**
	 * 修改账单
	 */
	function modify() {
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
		$.post(getRootName() + '/account/modify.do', param, function(res) {
			if (res) {
				alert('修改成功!');
				window.history.back(-1);
			} else {
				alert('修改失败');
			}
		}, "json");
	}

	// 初始化使用类型列表
	function initUseTypeList() {
		var useTypeList = $("#useTypeList");
		$.each(selectedData, function(key, value) {
			useTypeList.append(formatString('<option value="{0}">{1}</option>', value.id, value.name));
		});
	}

	// 初始化消费者列表
	function initCustomerList() {
		var html = '';
		var checkBoxTag = '<input id="{0}" type="checkbox" value="{1}">{2}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
		$.post(getRootName() + '/account/getCustomer.do', function(res) {
			if (res) {
				$.each(res, function(key, value) {
					html += formatString(checkBoxTag, value.id, value.id, value.name) + '<br/>'; // 换行
				});
				$("#customerList").html(html);
			}
		}, "json");
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
	<div style="padding-top: 10px; padding-left: 10px;">
		<form>
			<table width="600" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
				<tr>
					<td height="40" colspan="2" align="center" bgcolor="#B0B7D2" style="font-size: 14px;" id="tableTitle">创建账单</td>
				</tr>
				<tr>
					<td width="320" align="left" valign="top">
						<table>
							<tr>
								<td width="100" height="35" align="right">付费人：</td>
								<td width="250"><input id="payor" name="payor" type="text" style="width: 200px;" disabled="disabled" value="${user.name}" /></td>
							</tr>
							<tr>
								<td height="35" align="right">付费金额：</td>
								<td><input id="pay" name="pay" type="text" style="width: 200px;" /></td>
							</tr>
							<tr>
								<td height="35" align="right">使用日期：</td>
								<td><input id="useDate" name="useDate" type="text" readonly="readonly" style="width: 200px;" /></td>
							</tr>
							<tr>
								<td height="35" align="right">使用类型：</td>
								<td><select id="useTypeList" name="useType" style="width: 205px;"></select></td>
							</tr>
							<tr>
								<td height="35" align="right">备注：</td>
								<td><textarea id="remark" name="remark" rows="3" style="width: 200px;"></textarea></td>
							</tr>
						</table>
					</td>
					<td align="left" valign="top" style="padding-top: 8px;">
						<table>
							<tr align="left" valign="top">
								<td align="right">消费者：</td>
								<td id="customerList"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="buttons" height="40" align="center" colspan="2" bgcolor="#B0B7D2"><input id="save" type="button" value="确 定" /> <input id="cancel" onclick="window.history.back(-1);" type="button" value="取 消" /> <input id="selectAll" type="button" value="全 选" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>