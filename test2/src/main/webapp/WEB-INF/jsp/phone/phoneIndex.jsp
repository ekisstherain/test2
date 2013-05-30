<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>手机端首页</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/themes/default/jquery.mobile-1.3.1.min.css">
<style type="text/css">
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-1.8.3.min.js" /></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery.mobile-1.3.1.min.js" /></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jqueryExtend.js" /></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/common.js" /></script>

<script type="text/javascript">
	//从新定位页脚位置，解决出现滚动条是页脚不显示的问题
	/* function repositionFooter() {
		$(".footer").css("min-height", $(window).height());
	} */

	$(function() {
		initTitle();
		getAccountList(1);
		$("#add").bind('click', add);
	});

	var statusDef = [ {
		name : "未结算",
		id : 'unbalanced'
	}, {
		name : "已结算",
		id : 'balanced'
	} ];
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

	// 获取名称
	function getName(def, id) {
		var name = '';
		$.each(def, function(key, value) {
			if (value.id === id) {
				name = value.name;
				return false;
			}
		});
		return name;
	}

	// 初始化标题
	function initTitle() {
		$.post("${pageContext.request.contextPath}/getUserInfo.do", function(data) {
			if (data) {
				$("#userName").html("(" + data.name + ", 欢迎你)");
			}
		}, "json");
	}

	function add() {
		$("#add").addClass('ui-disabled'); // 禁用
		window.location.href = '${pageContext.request.contextPath}/phone/editAccount.do?operate=add';
	}

	function edit(id) {
		$("#" + id).addClass('ui-disabled'); // 禁用
		window.location.href = '${pageContext.request.contextPath}/phone/editAccount.do?operate=modify&id=' + id;
	}

	// 获取账单列表
	function getAccountList(pageIndex) {
		var html = "";
		var ul = '<ul data-role="listview" data-inset="true">{0}</ul>';
		var liTitle = '<li data-role="list-divider"  style="height:25px;padding-top:7px;">{0}, {1}, {2} <p style="float:right;"><input onclick="{3}" id="{4}" data-role="button" type="button" data-mini="true" value="编 辑" rel="external" data-inline="true" ></p></li>';
		var liDetail = '<li click="{0}">{1}</li>';
		var lip = '<p><b>{0}：</b>{1}</p>';
		var detail = '';
		$.post('${pageContext.request.contextPath}/account/getAccountList.do', {
			pageIndex : pageIndex
		}, function(res) {
			if (res.data) {
				$.each(res.data, function(key, value) {
					html += formatString(liTitle, new Date(value.useDate).format('yyyy-MM-dd'), value.payorName, getName(statusDef, value.status), "edit('" + value.id + "');", value.id); // 标题
					// 明细
					detail = '';
					detail += formatString(lip, "付费人", value.payorName);
					detail += formatString(lip, "付费金额", value.pay);
					detail += formatString(lip, "使用类型", getName(userTypeDef, value.useType));
					detail += formatString(lip, "使用日期", new Date(value.useDate).format('yyyy-MM-dd'));
					detail += formatString(lip, "消费者", value.customersName);
					detail += formatString(lip, "备注", value.remark);
					detail += formatString(lip, "状态", getName(statusDef, value.status));
					detail += '';
					html += formatString(liDetail, "dbclick('" + value.id + "');", detail);
				});
				$("#accountList").html(formatString(ul, html)).trigger("create");
				// 组建工具栏
				$("#footerBar").html(getPageToolbarHtml(res)).trigger("create"); // 创建分页工具栏
			}
		}, "json");
	}

	// 获取分页工具栏
	function getPageToolbarHtml(res) {
		var pageLink = '<li><a href="#" data-role="button" onclick="getAccountList({0});" data-icon="{1}">{2}</a></li>';
		var html = '<div data-role="navbar" data-iconpos="left" style="text-align: center;"><ul>{0}</ul></div>';
		var pageMsg = '<span>共 {0} 条信息 &nbsp; &nbsp;第{1}页 / 共{2}页</span>';
		var first = (res.currentPage == 1) ? '' : formatString(pageLink, 1, 'back', '首页');
		var prevs = (res.currentPage < 2) ? '' : formatString(pageLink, res.currentPage - 1, 'arrow-l', '上一页');
		var next = (res.currentPage + 1 > res.totalPage) ? '' : formatString(pageLink, res.currentPage + 1, 'arrow-r', '下一页');
		var last = (res.currentPage == res.totalPage) ? '' : formatString(pageLink, res.totalPage, 'forward', '尾页');
		var refresh = formatString(pageLink, res.currentPage, 'refresh', '刷新');
		return formatString(pageMsg, res.total, res.currentPage, res.totalPage) + formatString(html, first + prevs + next + last + refresh);
	}
</script>
</head>
<body>
	<div data-role="page" id="home">
		<div data-role="header" data-position="inline">
			<a href="${pageContext.request.contextPath}/phone/logout.do" data-icon="delete" rel="external">注销</a>
			<h1>
				账单列表 <span id="userName" style="font-size: 12px;"></span>
			</h1>
			<a href="#" id="add" data-inline="true" data-icon="plus" rel="external">添加账单</a>
		</div>
		<div data-role="content">
			<div id="accountList"></div>
		</div>

		<div id="footer" data-role="footer" data-position="fixed" style="text-align: center;">
			<div id="footerBar"></div>
			<h4>Copyright 2013 ::801</h4>
		</div>
	</div>
</body>
</html>
