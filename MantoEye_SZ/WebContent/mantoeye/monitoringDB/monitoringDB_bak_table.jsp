<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/include/session.jsp"%>
<%@ include file="/include/setcache.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>数据库告警监控</title>
<link rel="stylesheet" type="text/css" href="/tools/gt-grid/gt_grid.css" />
<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js"></script>
<script type="text/javascript" src="/js/common_grid.js"></script>
<script type="text/javascript" src="/flash/js/ParseUtils.js"></script>
<style>
body {
	margin: 0px;
	padding: 0px;
}

#main_content {
	width: 100%;
	height: 100%;
}

#main_header {
	background-color: #D3E0F2;
	height: 2mm;
	width: 100%;
}

.content_title {
	font-size: 12px;
	font-family: serif;
	font-weight: bold;
	color: white;
}

.content_title .title-left {
	background-image:
		url(/skin/Default/images/MantoEye/maincontent/lefttitle.gif);
	background-repeat: no-repeat;
	height: 27px;
	width: 3px;
	float: left;
}

.content_title .title_center {
	background-image:
		url(/skin/Default/images/MantoEye/maincontent/middletitle.gif);
	background-repeat: repeat-x;
	height: 27px;
	width: 99.3%;
	float: left;
	padding-top: 7px;
	padding-left: 6px;
}

.content_title .title_right {
	background-image:
		url(/skin/Default/images/MantoEye/maincontent/righttitle.gif);
	background-repeat: no-repeat;
	height: 27px;
	width: 3px;
	float: right;
}

.content_info {
	color: #000000;
	font-size: 14px;
	font-family: Georgia, "Times New Roman", Times, serif;
	font-style: italic;
	display: inline;
}
</style>
</head>
<body>
<div id="main_content">
<div id="main_header"></div>
<div class="content_title"><span class="title-left"></span> <span
	class="title_center">数据库Other Version实时监控数据 </span><span
	class="title_right"></span></div>
<div class="gt-panel">
<div id="ov_container"></div>
</div>
<div class="content_title"><span class="title-left"></span> <span
	class="title_center">数据库连接数实时监控数据</span> <span class="title_right"></span></div>
<div class="gt-panel">
<div id="conn_container"></div>
</div>
<div class="content_title"><span class="title-left"></span> <span
	class="title_center">数据库存储空间实时监控数据</span> <span class="title_right"></span></div>
<div class="gt-panel">
<div id="space_container"></div>
</div>
<div class="content_info">&nbsp;&nbsp;&nbsp;指标说明：Other
Version值反应了多机（multiplex）数据库的版本变化情况。一个版本表示某个连接的操作情况，并保存在数据库中</div>
</div>
</body>
</html>
<script type="text/javascript">
	var date = new Date();
	var year = date.getYear();
	var month = date.getMonth();
	month += 1;
	var m = (month < 10) ? ("0" + month) : (month + '');
	var day = date.getDate();
	var d = (day < 10) ? ("0" + day) : (day + '');
	var hours = date.getHours();
	var h = (hours < 10) ? ("0" + hours) : (hours + '');
	var minutes = date.getMinutes();
	var min = (minutes < 10) ? ("0" + minutes) : (minutes + '');

	var currentDate = year + "-" + m + "-" + d + " " + h + ":" + min;
	var currentDay = year + "-" + m + "-" + d + " " + h + ":00";

	var data_ov = [ {
		currentTime : currentDay,
		otherVersion : '${monitoringBean.otherVersionGB}',
		threshold : 200,
		monitoring : '正常'
	} ];

	var dsConfig_ov = {
		data : data_ov,

		fields : [ {
			name : 'currentTime'
		}, {
			name : 'otherVersion'
		}, {
			name : 'threshold'
		}, {
			name : 'monitoring'
		} ]
	};

	var colsConfig_ov = [ {
		id : 'currentTime',
		header : "监控时间"
	}, {
		id : 'otherVersion',
		headAlign : 'right',
		align : 'right',
		header : "Other Version值（G）"
	}, {
		id : 'threshold',
		headAlign : 'right',
		align : 'right',
		header : "阀值（G）"
	}, {
		id : 'monitoring',
		headAlign : 'right',
		align : 'right',
		header : "告警级别",
		renderer : rendDetail_ov
	} ];

	function rendDetail_ov(value, record, columnObj, grid, colNo, rowNo) {
		var otherVersion = record.otherVersion;
		var threshold = record.threshold;
		if (eval(otherVersion) > eval(threshold)) {
			return '<font color="red">异常</font>';
		} else {
			return '<font color="black">正常</font>';
		}
	}

	var gridConfig_ov = {
		id : "ov_containerId",
		dataset : dsConfig_ov,
		columns : colsConfig_ov,
		height : 100,
		container : 'ov_container',
		toolbarPosition : 'bottom',
		toolbarContent : 'state',
		onComplete : completeOv
	};

	var ovGrid = new GT.Grid(gridConfig_ov);

	GT.Utils.onLoad(function() {
		ovGrid.render();
	});

	function completeOv() {
		initGridInfo2(ovGrid)
	}

	var data_conn = [ {
		currentTime : currentDay,
		currentConn : 5,
		connMax : 100,
		threshold : 80,
		monitoring : '正常'
	} ];

	var dsConfig_conn = {
		data : data_conn,

		fields : [ {
			name : 'currentTime'
		}, {
			name : 'currentConn'
		}, {
			name : 'connMax'
		}, {
			name : 'threshold'
		}, {
			name : 'monitoring'
		} ]
	};

	var colsConfig_conn = [ {
		id : 'currentTime',
		header : "监控时间"
	}, {
		id : 'currentConn',
		headAlign : 'right',
		align : 'right',
		header : "当前连接数（个）"
	}, {
		id : 'connMax',
		headAlign : 'right',
		align : 'right',
		header : "允许最大连接数（个）"
	}, {
		id : 'threshold',
		headAlign : 'right',
		align : 'right',
		header : "阀值（%）"
	}, {
		id : 'monitoring',
		headAlign : 'right',
		align : 'right',
		header : "告警级别",
		renderer : rendDetail_conn
	} ];

	function rendDetail_conn(value, record, columnObj, grid, colNo, rowNo) {
		var currentConn = record.currentConn;
		var threshold = record.threshold;
		var connMax = record.connMax;
		var threshold_v = eval(connMax)*( eval(threshold) / 100 );

		if (eval(currentConn) > threshold_v) {
			return '<font color="red">异常</font>';
		} else {
			return '<font color="black">正常</font>';
		}
	}

	var gridConfig_conn = {
		id : "conn_containerId",
		dataset : dsConfig_conn,
		columns : colsConfig_conn,
		height : 100,
		container : 'conn_container',
		toolbarPosition : 'bottom',
		toolbarContent : 'state',
		onComplete : completeConn
	};

	var connGrid = new GT.Grid(gridConfig_conn);

	GT.Utils.onLoad(function() {
		connGrid.render();
	});

	function completeConn() {
		initGridInfo2(connGrid)
	}

	var data_space = [ {
		currentTime : currentDay,
		mainSpace : 1024,
		usedMainSpace : 256,
		usedMainRate : 25,
		threshold : 64,
		monitoring : '正常'
	} ];

	var dsConfig_space = {
		data : data_space,

		fields : [ {
			name : 'currentTime'
		}, {
			name : 'mainSpace'
		}, {
			name : 'usedMainSpace'
		}, {
			name : 'usedMainRate'
		}, {
			name : 'threshold'
		}, {
			name : 'monitoring'
		} ]
	};

	var colsConfig_space = [ {
		id : 'currentTime',
		header : "监控时间"
	}, {
		id : 'mainSpace',
		headAlign : 'right',
		align : 'right',
		header : "主存储空间（G）"
	}, {
		id : 'usedMainSpace',
		headAlign : 'right',
		align : 'right',
		header : "已利用存储空间（G）"
	}, {
		id : 'usedMainRate',
		headAlign : 'right',
		align : 'right',
		header : "存储空间利用率（%）"
	}, {
		id : 'threshold',
		headAlign : 'right',
		align : 'right',
		header : "阀值（%）"
	}, {
		id : 'monitoring',
		headAlign : 'right',
		align : 'right',
		header : "告警级别",
		renderer : rendDetail_space
	} ];

	function rendDetail_space(value, record, columnObj, grid, colNo, rowNo) {
		var usedMainRate = record.usedMainRate;
		var threshold = record.threshold;

		if (eval(usedMainRate) > eval(threshold)) {
			return '<font color="red">异常</font>';
		} else {
			return '<font color="black">正常</font>';
		}
	}

	var gridConfig_space = {
		id : "space_containerId",
		dataset : dsConfig_space,
		columns : colsConfig_space,
		height : 100,
		container : 'space_container',
		toolbarPosition : 'bottom',
		toolbarContent : 'state',
		onComplete : completeSpace
	};

	var spaceGrid = new GT.Grid(gridConfig_space);

	GT.Utils.onLoad(function() {
		spaceGrid.render();
	});

	function completeSpace() {
		initGridInfo2(spaceGrid);
	}

	function initGridInfo2(datagrid) {
		var width = window.screen.availWidth - 190;
		width = width - 10;
		var cs = datagrid.columns.length;
		var totalRecords = datagrid.getAllRows().length;
		var dWidth = (totalRecords) * 22 + 70;
		if (checkIE() == "IE6") {
			if (window.screen.availWidth == 1024) {
				datagrid.setSize(width, 145);
			} else {
				datagrid.setSize(width, 145);
			}
			width = width - 5;
		} else {
			if (checkIE() == "IE7") {
				width = width + 5;
				datagrid.setSize(width + 5, 145);
				width = width - 11;
			} else {
				width = width + 5;
				datagrid.setSize(width, 145);
				width = width - 15;
			}
		}
		var w = parseInt(width / cs);
		for ( var j in datagrid.columnMap) {
			datagrid.getColumn(j).setWidth(w);
		}
	}
</script>
