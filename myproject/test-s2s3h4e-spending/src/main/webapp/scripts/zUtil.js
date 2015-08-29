var zzw = zzw || {};
/**
 * 
 */
$.fn.tree.defaults.loadFilter = function(data, parent) {
	var opt = $(this).data().tree.options;
	var idFiled, textFiled, parentField;
	if (opt.parentField) {
		idFiled = opt.idFiled || 'id';
		textFiled = opt.textFiled || 'text';
		parentField = opt.parentField;
		var i, l, treeData = [], tmpMap = [];
		for (i = 0, l = data.length; i < l; i++) {
			tmpMap[data[i][idFiled]] = data[i];
		}
		for (i = 0, l = data.length; i < l; i++) {
			if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
				if (!tmpMap[data[i][parentField]]['children'])
					tmpMap[data[i][parentField]]['children'] = [];
				data[i]['text'] = data[i][textFiled];
				tmpMap[data[i][parentField]]['children'].push(data[i]);
			} else {
				data[i]['text'] = data[i][textFiled];
				treeData.push(data[i]);
			}
		}
		return treeData;
	}
	return data;
};

/**
 * 
 * @requires jQuery,EasyUI
 * 
 * 防止panel/window/dialog组件超出浏览器边界
 * @param left
 * @param top
 */
zzw.onMove = {
	onMove : function(left, top) {
		var l = left;
		var t = top;
		if (l < 1) {
			l = 1;
		}
		if (t < 1) {
			t = 1;
		}
		var width = parseInt($(this).parent().css('width')) + 14;
		var height = parseInt($(this).parent().css('height')) + 14;
		var right = l + width;
		var buttom = t + height;
		var browserWidth = $(window).width();
		var browserHeight = $(window).height();
		if (right > browserWidth) {
			l = browserWidth - width;
		}
		if (buttom > browserHeight) {
			t = browserHeight - height;
		}
		$(this).parent().css({/* 修正面板位置 */
			left : l,
			top : t
		});
	}
};
$.extend($.fn.dialog.defaults, zzw.onMove);
$.extend($.fn.window.defaults, zzw.onMove);
$.extend($.fn.panel.defaults, zzw.onMove);

/**
 * 通用加載錯誤提示
 */
zzw.onLoadError = {
	onLoadError : function(XMLHttpRequest) {
		$.messager.show({
			msg : XMLHttpRequest.statusText + ' ' + XMLHttpRequest.status + '[' + XMLHttpRequest.responseText + ']',
			title : '錯誤',
			timeout : 0
		});
	}
};
$.extend($.fn.datagrid.defaults, zzw.onLoadError);
$.extend($.fn.treegrid.defaults, zzw.onLoadError);
$.extend($.fn.tree.defaults, zzw.onLoadError);
$.extend($.fn.combogrid.defaults, zzw.onLoadError);
$.extend($.fn.combobox.defaults, zzw.onLoadError);
$.extend($.fn.form.defaults, zzw.onLoadError);

$.extend($.fn.datagrid.defaults.editors, {
	combogrid : {
		init : function(container, options) {
			var input = $('<input type="text" class="datagrid-editable-input">').appendTo(container);
			input.combogrid(options);
			return input;
		},
		destroy : function(target) {
			$(target).combogrid('destroy');
		},
		getValue : function(target) {
			return $(target).combogrid('getValue');
		},
		setValue : function(target, value) {
			$(target).combogrid('setValue', value);
		},
		resize : function(target, width) {
			$(target).combogrid('resize', width);
		}
	}
});