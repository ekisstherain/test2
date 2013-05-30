/**
 * 非jquery的table生成插件
 * 使用方法：
 * var grid1 = new GridGender(cfg);
 * $("#grid").html(grid1.generate());
 *
 */

/**
 * 当p和o都有属性prop时，把p对象的属性覆盖o对象的属性
 * 
 * @param o
 *            被复制对象
 * @param p
 *            复制对象
 * @returns {*} 被复制对象
 */
function extend(o, p) {
	for ( var prop in p) {
		o[prop] = p[prop];
	}
	return o;
}

/**
 * 创建表格
 */
var GridGender = (function() {
	var _displayColumnList = []; // 数据列的顺序

	// 表格数据定义
	var _cfg = {
		url : '', // 服务器地址
		// 表头格式定义
		header : [ [] ],
		rowsData : [],// 行数据集合
		rowNameSort : [],// 数据列的顺序
		style : '', // 表格样式
		cls : '', // 表格样式
		headCfg : { // 头部样式
			cls : 'table_head',
			style : ''
		},
		isShowCheckBox : true, // 是否显示选择框
		pagination : true, // 是否分页
		totalRow : 0, // 总行数
		currentPage : 1, // 当前页
		pageSize : 10, // 每页记录数
		totalPage : 1,
		rowDblClickEvent : function() {
		}
	};

	/**
	 * 对象定义
	 * 
	 * @param cfg
	 * @constructor
	 */
	function Grid(cfg) {
		extend(_cfg, cfg); // 属性复制
		_displayColumnList = _getDisplayColumnList(_cfg.header); // 获取显示的列集合
	}

	// 原型方法-----------------------------------------------------------------------------------

	/**
	 * 生成html
	 * 
	 * @constructor
	 */
	Grid.prototype.generate = function() {
		var tg = '<table class="{0}" style="{1}" width="100%" border="0" cellspacing="1" cellpadding="0">{2}</table>';
		var tableHtml = _genHead(_cfg.header) + _genRowsData(_cfg.rowsData);
		if (_cfg.pagination) { // 显示分页
			tableHtml += _genPagination();
		}
		return formatString(tg, _cfg.cls, _cfg.style, tableHtml);
	};

	// 内部方法-----------------------------------------------------------------------------------

	/**
	 * 获取显示列集合
	 * 
	 * @private
	 */
	function _getDisplayColumnList() {
		var columns = [];
		var lastHeader = _cfg.header[_cfg.header.length - 1];
		for ( var i = 0; i < lastHeader.length; i++) {
			columns.push(lastHeader[i]);
		}
		return columns;
	}

	/**
	 * 生成表头html
	 * 
	 * @private
	 */
	function _genHead() {
		var html = '';
		var row;
		var column;
		var thTag = '<td rowspan="{0}" colspan="{1}" style="width: {2}">{3}</td>';
		var isLastRow = false;
		for ( var i = 0; i < _cfg.header.length; i++) { // 行遍历
			row = _cfg.header[i];
			html += '<tr class="' + _cfg.headCfg.cls + '">';
			isLastRow = (i == (_cfg.header.length - 1));
			// 最后一行添加选择框前放置其他td
			if (isLastRow) {
				if (_cfg.isShowCheckBox) {
					html += '<td style="width: 1%"></td>';
				}
			}
			for ( var col = 0; col < row.length; col++) { // 列遍历
				column = row[col];
				html += formatString(thTag, column.rowSpan || 1, column.colSpan || 1, column.width, column.label ? column.label : column.name);
			}

			html += '</tr>';
		}
		return html;
	}

	/**
	 * 生成数据行html
	 * 
	 * @private
	 */
	function _genRowsData() {
		var html = '';
		var tdTag = '<td>{0}</td>';
		var rowTag = '<tr id="{0}" class="{1}">';
		var displayColumn; // 显示列数据定义
		var dataType; // 列数据类型
		for ( var i = 0; i < _cfg.rowsData.length; i++) { // 行遍历
			html += formatString(rowTag, ('row_' + i), (i % 2 == 0 ? 'table_tr' : '')); // 奇偶行设置
			if (_cfg.isShowCheckBox) { // 添加选择框
				html += formatString('<td style="width: 1%"><input id="{0}" name="checkbox" type="checkbox"></td>', _cfg.rowsData[i]['id']);
			}
			// 生成表格数据
			for ( var col = 0; col < _displayColumnList.length; col++) { // 列遍历
				// 格式化定义的输出
				displayColumn = _displayColumnList[col];
				dataType = displayColumn.dataType;
				if (dataType === 'date' && displayColumn.format) { // 日期处理
					html += formatString(tdTag, (new Date(_cfg.rowsData[i][displayColumn.name]).format(displayColumn.format)));
				} else if (dataType === 'custom' && typeof (displayColumn.format) === 'function') { // 其他处理
					html += formatString(tdTag, displayColumn.format(_cfg.rowsData[i][displayColumn.name]));
				} else {
					html += formatString(tdTag, _cfg.rowsData[i][displayColumn.name]);
				}
			}
			html += '</tr>';
		}
		return html;
	}

	/**
	 * 生成分页栏
	 * 
	 * @returns {string}
	 * @private
	 */
	function _genPagination() {
		var row = '';
		var totalColumns = _cfg.header[_cfg.header.length - 1].length; // 最后一行表头的列数
		totalColumns += _cfg.isShowCheckBox ? 1 : 0; // 是否显示checkbox
		row += '<tr id="pagination" class="table_tr1">';
		row += formatString('<td colspan="{0}" style="height: 40px;">', totalColumns);
		var div1 = '<div class="page1">共{0}条信息</div>'; // 总记录定义
		var div2 = '<div class="page2"> 第{0}页/共{1}页 [{2}] [{3}] [{4}] [{5}] </div>';
		var pageLink = '<a href="javascript:void(0);" pageIndex="{0}">{1}</a>'; // 连接定义
		var first = (_cfg.currentPage == 1) ? '首页' : formatString(pageLink, 1, '首页');
		var prevs = (_cfg.currentPage < 2) ? '上一页' : formatString(pageLink, _cfg.currentPage - 1, '上一页');
		var next = (_cfg.currentPage + 1 > _cfg.totalPage) ? '下一页' : formatString(pageLink, _cfg.currentPage + 1, '下一页');
		var last = (_cfg.currentPage == _cfg.totalPage) ? '尾页' : formatString(pageLink, _cfg.totalPage, '尾页');
		row += formatString(div1, _cfg.totalRow);
		row += formatString(div2, _cfg.currentPage, _cfg.totalPage, first, prevs, next, last);
		row += "</tr>";
		return row;
	}

	return Grid;
}());

// jquery Extend--------------------------------------------------------------------------------------------

(function($) {
	$.fn.extend({
		gridGen : function(cfg) {
			_genTable($(this), cfg);
		}
	});

	/**
	 * 初始化配置数据
	 * 
	 * @param cfg
	 * @private
	 */
	function _initCfg(cfg) {
		var dfd = $.Deferred();
		var param = cfg.param || {};
		param.pageIndex = cfg.currentPage;
		if (cfg.url) {
			$.post(cfg.url, param, function(res) {
				if (res && res.data) {
					cfg.rowsData = res.data;
					cfg.totalRow = res.total;
					extend(cfg, res); // 属性复制
				}
				dfd.resolve();
			}, "json");
		} else {
			dfd.resolve();
		}
		return dfd.promise();
	}

	/**
	 * 生成表格数据
	 * 
	 * @param pageIndex
	 * @private
	 */
	function _genTable(grid, cfg) {
		$.when(_initCfg(cfg)).done(function() { // 初始化配置
			var html = new GridGender(cfg).generate();
			grid.html(html);
			if (cfg.isShowCheckBox) {
				grid.find("[id^=row_]").bind("click", {
					grid : grid
				}, _rowSelected);
			}
			if (typeof cfg.rowDblClickEvent === 'function') {
				grid.bind("dblclick", cfg.rowDblClickEvent);// 绑定行点击选择事件
			}
			
			grid.find('#pagination a').bind("click", {
				grid : grid,
				cfg : cfg
			}, _pageLink); // 绑定分页事件
		});
	}

	/**
	 * 分页事件
	 * 
	 * @private
	 */
	function _pageLink(e) {
		e.data.cfg.currentPage = parseInt($(this).attr('pageIndex')); // 设置分页数据
		_genTable(e.data.grid, e.data.cfg);
	}

	/**
	 * 行选择事件
	 * 
	 * @private
	 */
	function _rowSelected(e) {
		var $checkbox = $(this).find('input[type="checkbox"]');
		e.data.grid.find('input[type="checkbox"]').each(function() {
			this.checked = false; // 取消其他的选中项目
		});
		$checkbox[0].checked = !$checkbox.is(":checked"); // $checkbox[0]表示dom对象
	}
})(jQuery);
