
var width = window.screen.availWidth;//屏幕宽度
var columninit = false;//初始化grid列宽
var showUnit = 'MB'; //初始单位
var sortInit = true; //是否是初始排序，第N次排序时值为false
var initHasData = true;//初始是否有数据


//根据分辨率初始化Flex 饼图，柱状图等宽度
function initChartWidth() {
	var availW = 1018;
	var availH = 230;
	if (width == 1024) {
		if (checkIE() == "IE6") {
			availW = 763;
		} else {
			availW = 758;
		}
	} else {
		availW = 1018;
	}
	return availW + "|230";
}

//初始化地图
function initSize() {
	var availW = 1004;
	var availH = 320;
	if (width == 1024) {
		availW = 760;
	} else {
		availW = 1004;
	}
	return availW + "|" + availH;
}
function getDispalyPageSize(i, k) {
	//alert("aaaa");
	var pageSize = 10;
	var upHight = window.screen.availHeight;
	var downHight = window.screenTop;
	var middleHight = upHight - downHight;
//i  0--表示没有图形  1--表示饼图或柱状图  2--表示地图
	var spaceHight = middleHight - 500 - 300;
	if (i == 0) {
		if (k == 3) {
			spaceHight = middleHight - 140 - 300;
		} else {
			if (k == 1) {
				spaceHight = middleHight - 90 - 300;
			} else {
				spaceHight = middleHight - 112 - 300;
			}
		}
	} else {
		if (i == 1) {
			if (k == 3) {
				spaceHight = middleHight - 450 - 300;
			} else {
				if (k == 1) {
					spaceHight = middleHight - 400 - 300;
				} else {
					spaceHight = middleHight - 422 - 300;
				}
			}
		} else {
			if (k == 3) {
				spaceHight = middleHight - 540 - 300;
			} else {
				if (k == 1) {
					spaceHight = middleHight - 500 - 300;
				} else {
					spaceHight = middleHight - 522 - 300;
				}
			}
		}
	}
	if (spaceHight > 0) {
		pageSize = pageSize + Math.round(spaceHight / 22);
	}
//alert("--pageSize-"+pageSize);
	return pageSize;
}


//根据分辨率初始化grid列宽
function initColumnWidth(grid, width, hideColumn, checkWidth) {//checkWidth  表示checkBox宽度,表示存在checkBox操作
	var cs = grid.columns.length - hideColumn;//如有隐藏列,则减去隐藏列
	if (typeof (checkWidth) != "undefined") {
		width = width - 40;//为checkbox预留空间
		cs = cs - 1;
	}
	width = width - 10;
	var w = parseInt(width / cs);
	var isCheck = false;
	for (var j in datagrid.columnMap) {
		if (typeof (checkWidth) != "undefined" && !isCheck) {
			//datagrid.getColumn(j).setWidth(10);//checkbox宽度为默认值, 并且必须定义在第一列
			isCheck = true;
		} else {
			datagrid.getColumn(j).setWidth(w);
		}
	}
	columninit = true;
}

//根据分辨率初始化grid列宽
function initColumnWidthWithPop(grid, width, hideColumn, checkWidth) {//checkWidth  表示checkBox宽度,表示存在checkBox操作
	var cs = grid.columns.length - hideColumn;//如有隐藏列,则减去隐藏列
	var $toolbarObj = $("#toolbar");//toolbar对象
	if (typeof (checkWidth) != "undefined") {
		width = width - 40;//为checkbox预留空间
		cs = cs - 1;
	}
	var totalRecords = grid.getAllRows().length;
	var dWidth = (totalRecords) * 22 + 70;
	if (checkIE() == "IE6") {
		if (window.screen.availWidth == 1024) {
			//alert(width);
			datagrid.setSize(width, dWidth > 215 ? dWidth : 215);
		} else {
			datagrid.setSize(width, dWidth > 230 ? dWidth : 230);
		}
		$toolbarObj.css("width", width + "px");
		width = width - 5;
	} else {
		if (checkIE() == "IE7") {
			width = width + 5;
			datagrid.setSize(width + 5, dWidth > 260 ? dWidth : 260);
			$toolbarObj.css("width", (width + 5) + "px");
			width = width - 11;
		} else {
			width = width + 5;
			datagrid.setSize(width, dWidth > 260 ? dWidth : 260);
			$toolbarObj.css("width", width + "px");
			width = width - 15;
		}
	}
	var w = parseInt(width / cs);
	var isCheck = false;
	for (var j in datagrid.columnMap) {
		if (typeof (checkWidth) != "undefined" && !isCheck) {
			//datagrid.getColumn(j).setWidth(10);//checkbox宽度为默认值, 并且必须定义在第一列
			isCheck = true;
		} else {
			datagrid.getColumn(j).setWidth(w);
		}
	}
	columninit = true;
}


//判断在1024或1280按钮的位置
function displayOrHiddenTd() {
	/*
	if(typeof(document.getElementById("spaceTd"))!='undefined'){
	if (width == 1024) {
		document.getElementById("spaceTd").style["display"] = "none";
	} else {
		document.getElementById("spaceTd").style["display"] = "block";
	}
	}*/
}

//初始化grid高宽等信息,使高宽满屏
function initGridInfo(obj) {
	var datagrid = obj.datagrid;
	var hideColumn = obj.hideColumn;//隐藏列
	var isCheckbox = obj.isCheckbox;//是否可选择
	var otherHeight = obj.otherHeight;//预留高度
	var otherWidth = obj.otherWidth;//预留高度
	var realWidth = 1045;
	//初始化高度满屏
	var parentHeight = parent.document.getElementById("container_content").offsetHeight;//整个编辑区的高度
	var yy = window.screen.availHeight;
	var yyy = window.screenTop;
	
	/*var y = document.body.clientHeight;
	
		if(navigator.userAgent.indexOf("MSIE 6.0")>0){
						heightJX=98;
		}else{
						heightJX=105;
		}*/
	var parentHeight = yy - yyy;
	var $toolbarObj = $("#toolbar");//toolbar对象
	var tHeight = $toolbarObj.offset().top + 25;//toolbar相对位置
	var IEHeight = 23;
	if (otherHeight != undefined) {
		IEHeight = IEHeight - otherHeight;
	}
	var totalRecords = datagrid.getAllRows().length;
	var dWidth = (totalRecords) * 22 + 55;
	if (checkIE() == "IE8") {
		dWidth = (totalRecords) * 22 + 70;
	}
	if (width == 1024) {
		var width_1024 = parentHeight - tHeight - IEHeight - 3;
		datagrid.setSize(800, dWidth > width_1024 ? dWidth : width_1024);//设置列表的宽高
		$toolbarObj.css("width", "800px");//设置列表toolbar的宽高
		realWidth = 800;
	} else {
		var width_1280 = parentHeight - tHeight - IEHeight;//满屏高度
		datagrid.setSize(1056, dWidth > width_1280 ? dWidth : width_1280);
		$toolbarObj.css("width", "1056px");
		realWidth = 1056;
	}
	if (columninit) {//如果已经初始化宽度，则不再执行
		return;
	}
	//初始化宽度满屏
	var cs = datagrid.columns.length - hideColumn;//如有隐藏列,则减去隐藏列
	if (typeof (otherWidth) != "undefined") {
		realWidth = realWidth - otherWidth;
		cs = cs - 1;
	}
	if (isCheckbox) {
		realWidth = realWidth - 40;//为checkbox预留空间
		cs = cs - 1;
	}
	if (checkIE() == "IE8") {
		realWidth = realWidth - 35;
	} else {
		realWidth = realWidth - 10;
	}
	var w = realWidth / cs;
	var isCheck = false;
	for (var j in datagrid.columnMap) {
		if (isCheckbox && !isCheck) {
			//datagrid.getColumn(j).setWidth(10);//checkbox宽度为默认值, 并且必须定义在第一列
			isCheck = true;
		} else {
			if ((j == "detail" && typeof (otherWidth) != "undefined") || (j == "top" && typeof (otherWidth) != "undefined")) {
			} else {
				datagrid.getColumn(j).setWidth(w);
			}
		}
	}
	columninit = true;
}
//初始化grid高宽等信息,使高宽满屏
function initGridInfoExColumn(obj) {
	var datagrid = obj.datagrid;
	var hideColumn = obj.hideColumn;//隐藏列
	var isCheckbox = obj.isCheckbox;//是否可选择
	var otherHeight = obj.otherHeight;//预留高度
	var otherWidth = obj.otherWidth;//预留高度
	var realWidth = 1045;
	//初始化高度满屏
	var parentHeight = parent.document.getElementById("container_content").offsetHeight;//整个编辑区的高度
	var yy = window.screen.availHeight;
	var yyy = window.screenTop;
	
	var parentHeight = yy - yyy;
	var $toolbarObj = $("#toolbar");//toolbar对象
	var tHeight = $toolbarObj.offset().top + 25;//toolbar相对位置
	var IEHeight = 23;
	if (otherHeight != undefined) {
		IEHeight = IEHeight - otherHeight;
	}
	var totalRecords = datagrid.getAllRows().length;
	var dWidth = (totalRecords) * 22 + 55;
	if (checkIE() == "IE8") {
		dWidth = (totalRecords) * 22 + 70;
	}
	if (width == 1024) {
		var width_1024 = parentHeight - tHeight - IEHeight - 3;
		datagrid.setSize(800, dWidth > width_1024 ? dWidth : width_1024);//设置列表的宽高
		$toolbarObj.css("width", "800px");//设置列表toolbar的宽高
		realWidth = 800;
	} else {
		var width_1280 = parentHeight - tHeight - IEHeight;//满屏高度
		datagrid.setSize(1056, dWidth > width_1280 ? dWidth : width_1280);
		$toolbarObj.css("width", "1056px");
		realWidth = 1056;
	}
	if (columninit) {//如果已经初始化宽度，则不再执行
		return;
	}
	columninit = true;
}

//判断grid查询是否有数据，没有则提示
function judgeData(datagrid, k) {
	var totalRecords = datagrid.getAllRows().length;//获取加载到的数据记录数目
	var tdWidth = "1056px";
	if (k == 1) {
		if (width == 1024) {
			tdWidth = "792px";
		} else {
			tdWidth = "973px";
		}
	} else {
		if (width == 1024) {
			tdWidth = "800px";
		}
	}
	if (typeof (datagrid.getRecord(0)) == "undefined") {
		var $o = $("#" + datagrid.id + "_bodyDiv").find("table").eq(0).find("tbody").eq(0);//获取对象
		$o.find("tr").eq(0).remove();//由于控件存在bug，所以需要删除第一行
		var $tr = $("<tr><td height=\"20px\" width=" + tdWidth + " style=\"font-size:14px\" align=\"center\" bgcolor=\"#EEEEEE\">\u6ca1\u6709\u6570\u636e\u663e\u793a!</td></tr>");
		$tr.appendTo($o);
		initHasData = false;
	}else{
		initHasData = true;
	}
}


//根据分辨率初始化grid列宽
// (pwidth为百分比参数,以逗号分开,如'20,30,40,10',
// haschk 是否有复选框列,如果有，则pwidth参数的第一列设置为0,如果该列默认隐藏,该列的宽度也设为0)
function initPercentColumnWidth(grid, width, pwidth, haschk) {
	var max = grid.columnList.length;
	var st = 0;
	var ps = pwidth.split(",");
	if (haschk) {
		st = 1;//复选框一行不用设置宽度
		width = width - 35;
	} else {
		width = width - 5;
	}
	for (var j = st; j < max; j++) {
		var w = parseInt(width * parseInt(ps[j]) / 100);
		var o = grid.columnList[j].setWidth(w);
	}
	columninit = true;
}
//获取已经选择的行数
function getSelectedCount(gridid) {
	var sy = document.getElementsByName("gt_" + gridid + "_chk_chk");
	var cnt = 0;
	for (var i = 0; i < sy.length; i++) {
		if (sy[i].checked) {
			cnt = cnt + 1;
		}
	}
	return cnt;
}
//获取选择的第一项的值
function getSelectedId(gridid) {
	var sy = document.getElementsByName("gt_" + gridid + "_chk_chk");
	var vs = "";
	for (var i = 1; i < sy.length; i++) {
		if (sy[i].checked) {
			vs = sy[i].value;
			return vs;
		}
	}
}
//获取选择的全部值的字符串
function getSelectedKeys(gridid) {
	var sy = document.getElementsByName("gt_" + gridid + "_chk_chk");
	var vs = "";
	for (var i = 1; i < sy.length; i++) {
		if (sy[i].checked) {
			vs = vs + "," + sy[i].value;
		}
	}
	return vs.substr(1, vs.length);
}
//选择全部
function checkAll(check, gridid) {
	var sy = document.getElementsByName("gt_" + gridid + "_chk_chk");
	if (check) {
		for (var i = 0; i < sy.length; i++) {
			sy[i].checked = true;
		}
	} else {
		for (var i = 1; i < sy.length; i++) {
			sy[i].checked = false;
		}
	}
}

//获取指定选择项的值
function getColumnValueByName(gridid, columnName) {
	var sy = document.getElementsByName("gt_" + gridid + "_chk_chk");
	var vs = "";
	for (var i = 1; i < sy.length; i++) {
		if (sy[i].checked) {
			vs = sy[i];
		}
	}
	var $o = $(vs);
	var $tr = $o.parent().parent().parent();
	var $colObj = $tr.find(".gt-col-" + gridid + "-" + columnName.toLowerCase()).eq(0).find("div").eq(0);
	return $colObj.html();
}
//获取指定选择项的值(多个)
function getColumnValuesByName(gridid, columnName) {
	var sy = document.getElementsByName("gt_" + gridid + "_chk_chk");
	var result = "";
	var vs = "";
	for (var i = 1; i < sy.length; i++) {
		if (sy[i].checked) {
			vs = sy[i];
			var $o = $(vs);
			var $tr = $o.parent().parent().parent();
			var $colObj = $tr.find(".gt-col-" + gridid + "-" + columnName.toLowerCase()).eq(0).find("div").eq(0);
			result = result + "," + $colObj.html();
		}
	}
	return result.substring(1, result.length);
}

//判断ID版本
function checkIE() {
	if (navigator.userAgent.indexOf("MSIE 6.0") > 0) {
		return "IE6";
	} else {
		if (navigator.userAgent.indexOf("MSIE 7.0") > 0) {
			return "IE7";
		} else {
			if (navigator.userAgent.indexOf("MSIE 8.0") > 0) {
				return "IE8";
			}
		}
	}
}

//导出文件
function exportFile(obj) {
	var fileName = obj.fileName;//文件名称
	var fileFormat = obj.fileFormat;//文件格式,后台暂支持xls格式
	var gridObj = obj.gridObj;//当前grid对象
	var params = obj.params;
	var url = gridObj.exportURL;	//导出URL临时参数
	gridObj.exportURL = bindUrlParameter(gridObj.exportURL, params);//绑定参数到URL中
	gridObj.exportGrid(fileFormat, fileName);
	gridObj.exportURL = url;	//URL恢复原样
}

//URL绑定参数
function bindUrlParameter(url, params) {
	if (typeof (params) != "undefined") {
		for (var k in params) {
			url = url + "&" + k + "=" + (encodeURIComponent((params)[k]));
		}
	}
	return url;
}
var columninit2 = false;

//初始化grid多表头高宽等信息,使高宽满屏
function initMutriHeandGridInfo(obj) {
	var datagrid = obj.datagrid;
	var otherHeight = obj.otherHeight;//预留高度
	var otherWidth = obj.otherWidth;//预留高度
	var realWidth = 1045;
	
	//初始化高度满屏
	var parentHeight = parent.document.getElementById("container_content").offsetHeight;//整个编辑区的高度
	var yy = window.screen.availHeight;
	var yyy = window.screenTop;
	/*var y = document.body.clientHeight;
	
		if(navigator.userAgent.indexOf("MSIE 6.0")>0){
						heightJX=98;
		}else{
						heightJX=105;
		}*/
	var parentHeight = yy - yyy;
	var $toolbarObj = $("#toolbar");//toolbar对象
	var tHeight = $toolbarObj.offset().top + 25;//toolbar相对位置
	var IEHeight = 23 - otherHeight;
	if (checkIE() == "IE6") {
		width_1280 = width_1280 + 15;
	}
	var totalRecords = datagrid.getAllRows().length;
	var dWidth = (totalRecords) * 22 + 93;
	if (width == 1024) {
		var width_1024 = parentHeight - tHeight - IEHeight - 3;
		datagrid.setSize(796, dWidth > width_1024 ? dWidth : width_1024);//设置列表的宽高
		$toolbarObj.css("width", "796px");//设置列表toolbar的宽高
		realWidth = 796;
	} else {
		var width_1280 = parentHeight - tHeight - IEHeight;//满屏高度
		datagrid.setSize(1061, dWidth > width_1280 ? dWidth : width_1280);
		$toolbarObj.css("width", "1061px");
		realWidth = 1052;
	}
	if (columninit) {//如果已经初始化宽度，则不再执行
	}
	return;
	//初始化宽度满屏	
	for (var j in datagrid.columnMap) {
		datagrid.getColumn(j).setWidth(100);
	}
	columninit2 = true;
}
function formatNumber(eValue) {
	var intPart = "";
	var decPart = "";
	if (eValue.indexOf(",") >= 0) {
		eValue = eValue.replace(/,/g, "");
	}  
            //判断是否包含'.'  
	if (eValue.indexOf(".") >= 0) {
		intPart = eValue.split(".")[0];
		decPart = eValue.split(".")[1];
	} else {
		intPart = eValue;
	}
	var num = intPart + "";
	var re = /(-?\d+)(\d{3})/;
	while (re.test(num)) {
		num = num.replace(re, "$1,$2");
	}
	if (eValue.indexOf(".") >= 0) {
		eValue = num + "." + decPart;
	} else {
		eValue = num;
	}
	return eValue;
}
function renderNumberFormat(value, record, columnObj, grid, colNo, rowNo) {
	return formatNumber(value);
}

//按钮权限设置
function initBtnPerm(symbols, perms) {
	var ss = symbols.split(",");
	var btnperms = [ss.length];
	for (var i = 0; i < ss.length; i++) {
		if (perms.indexOf(ss[i]) >= 0) {
			btnperms[i] = "T";
		} else {
			btnperms[i] = "F";
		}
	}
	return btnperms;
}

function clearCHK(dataid){
	var sy = document.getElementsByName("gt_" + dataid + "_chk_chk");

	for (var i = 0; i < sy.length; i++) {
		if (!sy[0].checked) {
			//alert(sy[i].checked+'--');
			sy[i].checked=false;
			//alert(sy[i].checked+'==');
		}
	}
}

//菜单权限设置(用于每一行右侧操作区的链接菜单)
function initMenuPerm(symbols, perms) {
	var ss = symbols.split(",");
	var menuperms = [ss.length];
	for (var i = 0; i < ss.length; i++) {
		if (perms.indexOf(ss[i]) >= 0) {
			menuperms[i] = "T";
		} else {
			menuperms[i] = "F";
		}
	}
	return menuperms;
}
//判断页面的流量显示单位(KB or MB or GB)
function getViewLevel(max) {
	var level = 0;
	if (max <= 1024 * 10) {//KB
		level = 1;
	} else {
		if (1024 * 10 < max && max <= 1024 * 1024 * 10) {//MB
			level = 2;
		} else {//GB
			level = 3;
		}
	}
	return level;
}
function buildUnit_new() {
	var totalRecords = datagrid.getAllRows().length;
	var max = 0;
	if (typeof (datagrid.getRecord(0)) != "undefined") {
		for (var i = 0; i < totalRecords; i++) {
			var record = datagrid.getRecord(i);
			var fl = record.totalFlushKB;
			if (fl > max) {
				max = fl;
			}
		}
		var level = getViewLevel(max);
		if (level == 1) {//KB
			datagrid.getColumn("upFlushKB").show();
			datagrid.getColumn("downFlushKB").show();
			datagrid.getColumn("totalFlushKB").show();
			datagrid.getColumn("localtotalFlushKB").show();
			datagrid.getColumn("upFlushGB").hide();
			datagrid.getColumn("downFlushGB").hide();
			datagrid.getColumn("totalFlushGB").hide();
			datagrid.getColumn("localtotalFlushGB").hide();
			datagrid.getColumn("upFlushMB").hide();
			datagrid.getColumn("downFlushMB").hide();
			datagrid.getColumn("totalFlushMB").hide();
			datagrid.getColumn("localtotalFlushMB").hide();
			showUnit = "KB";
		} else {
			if (level == 3) {//GB
				datagrid.getColumn("upFlushKB").hide();
				datagrid.getColumn("downFlushKB").hide();
				datagrid.getColumn("totalFlushKB").hide();
				datagrid.getColumn("localtotalFlushKB").hide();
				datagrid.getColumn("upFlushGB").show();
				datagrid.getColumn("downFlushGB").show();
				datagrid.getColumn("totalFlushGB").show();
				datagrid.getColumn("localtotalFlushGB").show();
				datagrid.getColumn("upFlushMB").hide();
				datagrid.getColumn("downFlushMB").hide();
				datagrid.getColumn("totalFlushMB").hide();
				datagrid.getColumn("localtotalFlushMB").hide();
				showUnit = "GB";
			} else {//MB
				datagrid.getColumn("upFlushKB").hide();
				datagrid.getColumn("downFlushKB").hide();
				datagrid.getColumn("totalFlushKB").hide();
				datagrid.getColumn("localtotalFlushKB").hide();
				datagrid.getColumn("upFlushGB").hide();
				datagrid.getColumn("downFlushGB").hide();
				datagrid.getColumn("totalFlushGB").hide();
				datagrid.getColumn("localtotalFlushGB").hide();
				datagrid.getColumn("upFlushMB").show();
				datagrid.getColumn("downFlushMB").show();
				datagrid.getColumn("totalFlushMB").show();
				datagrid.getColumn("localtotalFlushMB").show();
				showUnit = "MB";
			}
		}
	}
	return showUnit;
}
/////////////////////////页面呈现单位判断/////////////////////////////////
function buildUnit() {
	var totalRecords = datagrid.getAllRows().length;
	var max = 0;
	if (typeof (datagrid.getRecord(0)) != "undefined") {
		for (var i = 0; i < totalRecords; i++) {
			var record = datagrid.getRecord(i);
			var fl = record.totalFlushKB;
			if (fl > max) {
				max = fl;
			}
		}
		var level = getViewLevel(max);
		if (level == 1) {//KB
			datagrid.getColumn("upFlushKB").show();
			datagrid.getColumn("downFlushKB").show();
			datagrid.getColumn("totalFlushKB").show();
			datagrid.getColumn("upFlushGB").hide();
			datagrid.getColumn("downFlushGB").hide();
			datagrid.getColumn("totalFlushGB").hide();
			datagrid.getColumn("upFlushMB").hide();
			datagrid.getColumn("downFlushMB").hide();
			datagrid.getColumn("totalFlushMB").hide();
			showUnit = "KB";
		} else {
			if (level == 3) {//GB
				datagrid.getColumn("upFlushKB").hide();
				datagrid.getColumn("downFlushKB").hide();
				datagrid.getColumn("totalFlushKB").hide();
				datagrid.getColumn("upFlushGB").show();
				datagrid.getColumn("downFlushGB").show();
				datagrid.getColumn("totalFlushGB").show();
				datagrid.getColumn("upFlushMB").hide();
				datagrid.getColumn("downFlushMB").hide();
				datagrid.getColumn("totalFlushMB").hide();
				showUnit = "GB";
			} else {//MB
				datagrid.getColumn("upFlushKB").hide();
				datagrid.getColumn("downFlushKB").hide();
				datagrid.getColumn("totalFlushKB").hide();
				datagrid.getColumn("upFlushGB").hide();
				datagrid.getColumn("downFlushGB").hide();
				datagrid.getColumn("totalFlushGB").hide();
				datagrid.getColumn("upFlushMB").show();
				datagrid.getColumn("downFlushMB").show();
				datagrid.getColumn("totalFlushMB").show();
				showUnit = "MB";
			}
		}
	}
	return showUnit;
}
function buildPortraitUnit() {
	var totalRecords = datagrid.getAllRows().length;
	var max = 0;
	if (typeof (datagrid.getRecord(0)) != "undefined") {
		for (var i = 0; i < totalRecords; i++) {
			var record = datagrid.getRecord(i);
			var fl = record.totalflushKB;
			if (fl > max) {
				max = fl;
			}
		}
		var level = getViewLevel(max);
		if (level == 1) {//KB
			datagrid.getColumn("upflushKB").show();
			datagrid.getColumn("downflushKB").show();
			datagrid.getColumn("totalflushKB").show();
			datagrid.getColumn("upflushGB").hide();
			datagrid.getColumn("downflushGB").hide();
			datagrid.getColumn("totalflushGB").hide();
			datagrid.getColumn("upflushMB").hide();
			datagrid.getColumn("downflushMB").hide();
			datagrid.getColumn("totalflushMB").hide();
			showUnit = "KB";
		} else {
			if (level == 3) {//GB
				datagrid.getColumn("upflushKB").hide();
				datagrid.getColumn("downflushKB").hide();
				datagrid.getColumn("totalflushKB").hide();
				datagrid.getColumn("upflushGB").show();
				datagrid.getColumn("downflushGB").show();
				datagrid.getColumn("totalflushGB").show();
				datagrid.getColumn("upflushMB").hide();
				datagrid.getColumn("downflushMB").hide();
				datagrid.getColumn("totalflushMB").hide();
				showUnit = "GB";
			} else {//MB
				showUnit = "MB";
				datagrid.getColumn("upflushKB").hide();
				datagrid.getColumn("downflushKB").hide();
				datagrid.getColumn("totalflushKB").hide();
				datagrid.getColumn("upflushGB").hide();
				datagrid.getColumn("downflushGB").hide();
				datagrid.getColumn("totalflushGB").hide();
				datagrid.getColumn("upflushMB").show();
				datagrid.getColumn("downflushMB").show();
				datagrid.getColumn("totalflushMB").show();
			}
		}
	}
	return showUnit;
}
 
function buildPortraitUnitF() {
	var totalRecords = datagrid.getAllRows().length;
	var max = 0;
	if (typeof (datagrid.getRecord(0)) != "undefined") {
		for (var i = 0; i < totalRecords; i++) {
			var record = datagrid.getRecord(i);
			var fl = record.totalFlushKB;
			if (fl > max) {
				max = fl;
			}
		}
		var level = getViewLevel(max);
		if (level == 1) {//KB
			datagrid.getColumn("upFlushKB").show();
			datagrid.getColumn("downFlushKB").show();
			datagrid.getColumn("totalFlushKB").show();
			datagrid.getColumn("upFlushGB").hide();
			datagrid.getColumn("downFlushGB").hide();
			datagrid.getColumn("totalFlushGB").hide();
			datagrid.getColumn("upFlushMB").hide();
			datagrid.getColumn("downFlushMB").hide();
			datagrid.getColumn("totalFlushMB").hide();
			showUnit = 'KB';
		} else {
			if (level == 3) {//GB
				datagrid.getColumn("upFlushKB").hide();
				datagrid.getColumn("downFlushKB").hide();
				datagrid.getColumn("totalFlushKB").hide();
				datagrid.getColumn("upFlushGB").show();
				datagrid.getColumn("downFlushGB").show();
				datagrid.getColumn("totalFlushGB").show();
				datagrid.getColumn("upFlushMB").hide();
				datagrid.getColumn("downFlushMB").hide();
				datagrid.getColumn("totalFlushMB").hide();
				showUnit = 'GB';
			} else {//MB
				datagrid.getColumn("upFlushKB").hide();
				datagrid.getColumn("downFlushKB").hide();
				datagrid.getColumn("totalFlushKB").hide();
				datagrid.getColumn("upFlushGB").hide();
				datagrid.getColumn("downFlushGB").hide();
				datagrid.getColumn("totalFlushGB").hide();
				datagrid.getColumn("upFlushMB").show();
				datagrid.getColumn("downFlushMB").show();
				datagrid.getColumn("totalFlushMB").show();
				showUnit = 'MB';
			}
		}
	}
	
	return showUnit;
}
function renderFormatDataInit4(value ,record,columnObj,grid,colNo,rowNo){
	return myFloatFormat(4,value);
}
function renderFormatDataInit2(value ,record,columnObj,grid,colNo,rowNo){
	return myFloatFormat(2,value);
}
//格式化月份显示
function renderFormatMonthDate(value ,record,columnObj,grid,colNo,rowNo){
	var dds = value.split('-');
	if(dds[1].length==1){
		return dds[0]+"-0"+dds[1];
	}else{
		return value;
	}
}
//小数点后末位补0
function myFloatFormat(bit,val){
	var strDecimal = (val+"").replace(/,/g,'');
	var i=0;  
    var strFill="";
    var addDecimal="0.";             
    while(i<bit)
    {           
      strFill=strFill+"0";
      if(i==bit-1)
      {            
         addDecimal=addDecimal+"1";            
      }
      else
      {            
         addDecimal=addDecimal+"0";
      }         
      i=i+1;         
    } 
    i=0;          
    var beginPlace=strDecimal.indexOf(".");
    if(beginPlace==-1)
    {
         if(bit==0)
         {
          return strDecimal;                      
         }
         return strDecimal+"."+strFill;         
    }            
    var strDecimalC=strDecimal+strFill;
    
    var str= strDecimalC.split(/[.]/);      
    var strInt=str[0];      
    var strDecimal=str[1]+strFill;
    var IntDecimal=parseFloat("0."+strDecimal);       
    var validPlace=beginPlace+bit+1;
    var validData=strDecimalC.substring(validPlace,validPlace+1);    
    if(parseInt(validData)>4)
    {  
         if(bit==0)
          {
              return parseInt(strInt)+1;                    
          }
         var differents="0."+strFill+strDecimal.substring(bit,strDecimal.length);
         IntDecimal=IntDecimal-parseFloat(differents);
         IntDecimal=IntDecimal+parseFloat(addDecimal);
   
         var DecimalValue=parseInt(strInt)+IntDecimal;
        
         if(DecimalValue.toString().indexOf(".")== -1 ) 
            DecimalValue=DecimalValue.toString()+".";
           
         strDecimalC=DecimalValue.toString(10)+strFill;
    }
    var beginPlace=strDecimalC.indexOf(".");
    var beginStr=strDecimalC.substring(0,beginPlace);
    if(bit==0)
    {      
        return beginStr;            
    }
    return strDecimalC.substring(0, beginPlace+bit+1);
}
//页面查询前通过Ajax方法检查是否已经登录超时
function checkLogon(){
	$.ajax({
		type : "post",
		url : "logonCheck_check.do?1=1",
		data : {},
		success : function(result) {
			if(result=='true'){
				return true;
			}else{
				alert('您的登录已超时,请重新登入!');
				//window.close();
			}
		},
		error : function() {
		}
	});
}
function checkPopLogon(){
	$.ajax({
		type : "post",
		url : "logonCheck_check.do?1=1",
		data : {},
		success : function(result) {
			if(result=='true'){
				return true;
			}else{
				alert('您的登录已超时,请重新登入!');
				window.close();
				parent.window.close();
			}
		},
		error : function() {
		}
	});
}
/*自动查找公用方法*/
function checkBlur(input){
	$("#"+input).attr("value","输入以快速查找");
}
function checkFocus(input){
	$("#"+input).attr("value","");
}
/*自动查找公用方法*/
function resetautocomplete(data,input,render,w){

		$("#"+input).unautocomplete();
		
		$("#"+input).autocomplete(data, {
		width: w||150,
		matchContains: true,
		max: 50,
		minChars: 0,
		formatItem: function(data, i, n, value) {
			return data.name;
		},
		formatResult: function(data, value) {
			return data.name;
		}
	});
	$("#"+input).result(function(event, data, formatted) {
		if (data)
			$("#"+render).attr("value",data.id);
	});
	checkBlur(input);
}

//二期开发：chenchengle
//处理编辑后刷新页面之后，再编辑时出现的问题
function getRightRecord(gridid,datagrid) {
	var sy = document.getElementsByName("gt_" + gridid + "_chk_chk");
	var record = "";
	for (var i = 1; i < sy.length; i++) {
		if (sy[i].checked) {		
			record = datagrid.dataset.getRecord(i-1);
			return record;
		}
	}
}