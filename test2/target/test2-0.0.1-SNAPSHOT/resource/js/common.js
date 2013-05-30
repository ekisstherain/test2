/**
 * common定义名称空间
 */
var common = (function() {
	/**
	 * 获取项目名称
	 * 
	 * @return {String}
	 */
	function _getRootName() {
		// 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
		var pathName = window.document.location.pathname;
		// 获取带"/"的项目名，如：/uimcardprj
		if(pathName.indexOf("account1") > -1){
			return "";
		}
		return pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	}

	/**
	 * 获取地址参数 key,value形式
	 */
	function _getUrlParam() {
		var url, param, paramStr;
		var paramArray = {};
		url = location.href;
		if (url.split("?").length < 2) {
			return paramArray;
		}
		paramStr = url.split("?")[1];
		param = paramStr.split("&");
		for ( var i = 0; i < param.length; i++) {
			paramArray[param[i].split("=")[0]] = param[i].split("=")[1];
		}
		return paramArray;
	}

	return {
		/**
		 * 跟路径
		 */
		rootPath : _getRootName(),
		urlParam : _getUrlParam()
	};
})();

/**
 * 获取项目名称
 * 
 * @return {String}
 */
function getRootName() {
	return common.rootPath;
}

/**
 * 增加formatString功能，使用方法：formatString('字符串{0}字符串{1}字符串','第一个变量','第二个变量');
 * 
 * @param str
 *            需要格式化的字符串
 * @returns 格式化后的字符串
 */
function formatString(str) {
	for ( var i = 0; i < arguments.length - 1; i++) {
		str = str.replace("{" + i + "}", (arguments[i + 1] != undefined && arguments[i + 1] != null) ? arguments[i + 1] : '');
	}
	return str;
};

/**
 * 根据名称获取参数
 * 
 * @param name
 * @returns
 */
function getUrlParam(name) {
	return common.urlParam[name];
}

/**
 * 设置下拉框数据
 * 
 * @param options 对象：select jq对象, url 地址,和param 地址的参数, callback 回调函数, value 后台的值, label 显示的值, isHasDefaultValue是否有默认值
 */
function setSelectListData(options) {
	var dfd = $.Deferred(); //在函数内部，新建一个Deferred对象
	if (options) {
		$.post(options.url, options.param, function(res) {
			var select = options.select;
			select.html(''); // 清空数据
			if(res){
				if(options.isHasDefaultValue){
					select.append('<option value=""></option>'); // 空选择项
				}
				var value = options.value ? options.value : 'id'; // 默认是id
				var label = options.label ? options.label : 'name'; // 默认是name
				for ( var i = 0; i < res.length; i++) {
					if(res[i]['selected']){
						select.append(formatString('<option value="{0}" selected="selected">{1}</option>', res[i][value], res[i][label]));
					}else{
						select.append(formatString('<option value="{0}">{1}</option>', res[i][value], res[i][label]));
					}
				}	
				// 执行回调函数
				if (typeof (options.callback) == 'function') {
					options.callback();
				}
			}
			dfd.resolve(); // 改变Deferred对象的执行状态
		}, "json");
	}
	return dfd.promise(); // 返回promise对象
}

/**
 * 扩展日期对象的方法
 * 
 * @param fmt
 * @returns {*}
 * @constructor
 */
Date.prototype.format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};