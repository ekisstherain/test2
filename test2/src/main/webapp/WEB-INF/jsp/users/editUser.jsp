<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑用户</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-1.8.3.min.js" /></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jqueryExtend.js" /></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/common.js" /></script>
<script type="text/javascript">
	//验证
	function validate() {
		if (!$("#name").val()) {
			return "请输入用户名!";
		}
		if (!$("#account").val()) {
			return "请输入帐号!";
		}
		if (!$("#password").val()) {
			return "请输入密码!";
		}
		if ($("#password2").val() !== $("#password").val()) {
			return "两次输入密码不一致!";
		}
		return true;
	}

	$(function() {
		var operate = getUrlParam('operate');
		if (operate == 'modify') { // 编辑处理
			$('#tableTitle').html('编辑用户信息');
			$('#save').attr('value', '修改');
			$('#save').bind('click', modifyUsers);
			$.when(initRoleList()).done(initModifyUser);
		} else { // 添加处理
			initRoleList();
			$('#tableTitle').html('创建用户信息');
			$('#save').attr('value', '创建');
			$('#save').bind('click', addUsers);
		}
	});

	/**
	 * 添加用户
	 */
	function addUsers() {
		var res = validate();
		if (res !== true) {
			alert(res);
			return;
		}
		var param = $("form").serializeObject();
		param.role = $('#roleList').val();
		$.post('${pageContext.request.contextPath}/users/addUsers.do', param, function(res) {
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
	function initModifyUser() {
		var id = getUrlParam('id');
		if (id) {
			$.post('${pageContext.request.contextPath}/users/getUserById.do', {
				id : id
			}, function(res) {
				if (res) {
					//$("#name").val(res.name).attr("disabled", "disabled"); // 姓名
					$("#name").val(res.name);
					$("#account").val(res.account).attr("disabled", "disabled"); // 账号
					$("#password").val('********').attr("disabled", "disabled"); // 密码
					$("#password2").val('********').attr("disabled", "disabled"); // 确认密码
					$("#roleList").val(res.role);
				}
			}, "json");
		}
	}

	/**
	 * 修改用户数据
	 */
	function modifyUsers() {
		var param = $("form").serializeObject();
		param.id = getUrlParam('id');
		param.role = $('#roleList').val();
		$.post('${pageContext.request.contextPath}/users/modifyUser.do', param, function(res) {
			if (res) {
				alert('修改成功!');
				window.history.back(-1);
			} else {
				alert('修改失败');
			}
		}, "json");
	}

	// 初始化角色列表
	function initRoleList() {
		return setSelectListData({
			isHasDefaultValue : false,
			url : '${pageContext.request.contextPath}/users/getRoleList.do',
			select : $('#roleList')
		});
	}
</script>
</head>
<body>
	<%-- <div class="manage_title">
		<img src="${pageContext.request.contextPath}/resource/images/manage_03.gif" />
	</div> --%>
	<div style="padding-top: 10px; padding-left: 10px;">
		<form>
			<table width="400" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
				<tr>
					<td height="40" colspan="2" align="center" bgcolor="#B0B7D2" style="font-size: 14px;" id="tableTitle">创建用户</td>
				</tr>
				<tr>
					<td width="100" height="35" align="right">用户名：</td>
					<td width="250"><input id="name" name="name" type="text" style="width: 200px;" /></td>
				</tr>
				<tr>
					<td height="35" align="right">账号：</td>
					<td><input id="account" name="account" type="text" style="width: 200px;" /></td>
				</tr>
				<tr>
					<td height="35" align="right">密码：</td>
					<td><input id="password" name="password" type="password" style="width: 200px;" /></td>
				</tr>
				<tr>
					<td height="35" align="right">确认密码：</td>
					<td><input id="password2" name="password2" type="password" style="width: 200px;" /></td>
				</tr>
				<tr>
					<td height="35" align="right">角色：</td>
					<td><select id="roleList" style="width: 200px;"></select></td>
				</tr>
				<tr>
					<td height="40" align="center" colspan="2"><input id="save" type="button" class="btn55" value="确定" /> <input id="cancel" onclick="window.history.back(-1);" type="button" class="btn55" value="取消" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>