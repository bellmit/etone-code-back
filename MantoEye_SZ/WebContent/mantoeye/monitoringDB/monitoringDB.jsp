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
<script type="text/javascript" src="/flash/js/swfobject.js"></script>
<script type="text/javascript" src="/flash/js/json/json2.js"></script>
<style type="text/css">
body {
	margin: 0px;
	padding: 0px;
	background-color: white;
}

#main_content {
	width: 100%;
	height: 100%;
}

#header_line {
	background-color: #D3E0F2;
	width: 100%;
	padding-top:4px;
	font-size: 12px;
	font-family: serif;
	font-weight: bold;
	color: white;
}

#header_line .title-left {
	background-image:
		url(/skin/Default/images/MantoEye/maincontent/lefttitle.gif);
	background-repeat: no-repeat;
	height: 27px;
	width: 3px;
	float: left;
}

#header_line .title_center {
	background-image:
		url(/skin/Default/images/MantoEye/maincontent/middletitle.gif);
	background-repeat: repeat-x;
	height: 27px;
	width: 99.3%;
	float: left;
	padding-top: 7px;
	padding-left: 6px;
}

#header_line .title_right {
	background-image:
		url(/skin/Default/images/MantoEye/maincontent/righttitle.gif);
	background-repeat: no-repeat;
	height: 27px;
	width: 3px;
	float: right;
}

#content_left {
	width: 33%;
	float: left;
}

#content_left #ov_img {
	width: 88%;
	height: 150%;
}

#content_center {
	width: 33%;
	float: left;
	margin-left: 5px;
	border-left: 2px solid #D3E0F2;
	border-right: 2px solid #D3E0F2;
	height: 100%;
}

#content_center #conn_img {
	width: 88%;
	height: 150%;
}

#content_right {
	width: 33%;
	float: right;
}

#content_right #space_img {
	width: 88%;
	height: 150%;
}

.data_bgImg {
	margin-top: 12px;
	background-image: url('/skin/Default/images/bg/tableHeaderBG.gif');
	width: 100%;
	background-repeat: repeat-x;
	height: 20px;
	line-height: 2em;
	text-align: center;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
}

.main_data {
	text-align: left;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 14px;
	line-height: 2.5em;
	padding-left: 16px;
}

.img_content {
	height: 260px;
	width: 100%;
	padding-top: 8px;
	background-repeat: no-repeat;
}

.info {
	font-size: 13px;
	line-height: 2em;
	font-family: "黑体"
}
}
</style>
</head>
<body>
<div id="main_content">
<div id="header_line"><span class="title-left"></span> <span
	class="title_center">数据库实时告警示意图 </span><span class="title_right"></span></div>
<div id="content_left">
<div class="img_content"><input type='hidden' id='ovImgArea' />
<div id="ovImgContent"></div>
</div>
<div class="data_bgImg">数据库Other Version</div>
<div id="ov_data" class="main_data">监控时间：<span
	class="monitoringTime"></span><br />
Other Version值：${monitoringBean.otherVersionGB}G<br />
Other Version率阀值：200G<br />
告警级别：<font id="monitoringColorOv">正常</font><br />
<p class="info">Other Version值反应了多机数据库
的版本变化情况。一个版本表示某个连接的操作情况,
并保存在数据库中。</p>
</div>
</div>
<div id="content_center">
<div class="img_content"><input type='hidden' id='connImgArea' />
<div id="connImgContent"></div>
<div class="data_bgImg">数据库连接数</div>
<div id="conn_data" class="main_data">监控时间：<span
	class="monitoringTime"></span><br />
当前连接数：${monitoringBean.curConn}个<br />
允许最大连接数:${monitoringBean.connMax}个<br />
连接数阀值：80%<br />
告警级别：<font id="monitoringColorConn">正常</font></div>
</div>
</div>
<div id="content_right">
<div class="img_content"><input type='hidden' id='spaceImgArea' />
<div id="spaceImgContent"></div>
<div class="data_bgImg">数据库存储空间</div>
<div id="space_data" class="main_data">监控时间：<span
	class="monitoringTime"></span><br />
主存储空间：${monitoringBean.mainSpaceSizeGB}T<br />
已利用存储空间：${monitoringBean.usedMainSpaceSizeGB}T<br />
存储空间利用率：${monitoringBean.mainSpaceRate}%<br />
利用率阀值：64%<br />
告警级别：<font id="monitoringColorSpace">正常</font></div>
</div>
</div>
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

	var noData = '${nodata}';
	var isNoData = false;

	$(document).ready(function() {
		$('.monitoringTime').html('${monitoringBean.currentTime}');
		//$('.monitoringTime').html(currentDate);

			if ('NODATA' == noData) {
				isNoData = true;
			} else {
				isNoData = false;
			}

			getMonitoringColor();
			buildLineImg();
			buildPieImg2();
			buildPieImg();
		});

	function getMonitoringColor() {

		var ovv = isNoData ? '0' : '${monitoringBean.otherVersionGB}';
		var thv_ov = 200;
		var connv = isNoData ? '0' : '${monitoringBean.curConn}';
		var thv_conn = eval(isNoData ? '0' : '${monitoringBean.connMax}') * 0.8;
		var usv = isNoData ? '0' : '${monitoringBean.mainSpaceRate}';
		var thv_space = 64;

		if (eval(ovv) > thv_ov) {
			$('#monitoringColorOv').css('color', 'red');
			$('#monitoringColorOv').html('异常');
		} else {
			$('#monitoringColorOv').css('color', 'black');
			$('#monitoringColorOv').html('正常');
		}
		if (eval(connv) > thv_conn) {
			$('#monitoringColorConn').html('异常');
			$('#monitoringColorConn').css('color', 'red');
		} else {
			$('#monitoringColorConn').css('color', 'black');
			$('#monitoringColorConn').html('正常');
		}
		if (eval(usv) > eval(thv_space)) {
			$('#monitoringColorSpace').html('异常');
			$('#monitoringColorSpace').css('color', 'red');
		} else {
			$('#monitoringColorSpace').css('color', 'black');
			$('#monitoringColorSpace').html('正常');
		}

	}

	function buildPieImg2() {
		var m = isNoData ? '0' : '${monitoringBean.connMax}';
		var um = isNoData ? '0' : '${monitoringBean.curConn}';
		var mum = eval(m) - eval(um);
		var connv = parseInt(mum);

		var chart = {
			"elements" : [ {
				"type" : "pie",
				"colours" : [ "#336699", "#88AACC" ],
				"tip" : "#label# : #val#个 (共#total#个 )\n 占比：#percent#",
				"alpha" : 0.9,
				"start-angle" : 35,
				"font-size" : 12,
				"values" : [ {
					"value" : '${monitoringBean.curConn}',
					"label" : "当前连接数",
					"color" : "#336699"
				}, {
					"value" : connv,
					"label" : "空闲连接数",
					"color" : "#88AACC"
				} ]
			} ]
		};

		var s = JSON.stringify(chart);
		/*if (isNoData) {
			$('#connImgArea').css('display', 'none');
			$('#connImgArea').css('height', '260px');
		} else {*/
		document.getElementById("connImgArea").value = s;
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=conn",
				"connImgContent", "100%", "99%", "9.0.0", "expressInstall.swf",
				'', {
					"wmode" : "opaque"
				});
		//}
	}

	function buildPieImg() {
		var m = isNoData ? '0' : '${monitoringBean.mainSpaceSizeGB}';
		var um = isNoData ? '0' : '${monitoringBean.usedMainSpaceSizeGB}';
		var mum = eval(m) - eval(um);
		var connv = parseFloat(mum);

		var chart = {
			"elements" : [ {
				"type" : "pie",
				"colours" : [ "#336699", "#88AACC" ],
				"tip" : "#label# : #val#T (共#total#T )\n 占比：#percent#",
				"alpha" : 0.9,
				"start-angle" : 35,
				"font-size" : 12,
				"values" : [ {
					"value" : '${monitoringBean.usedMainSpaceSizeGB}',
					"label" : "已利用空间",
					"color" : "#336699"
				}, {
					"value" : connv,
					"label" : "未利用空间",
					"color" : "#88AACC"
				} ]
			} ]
		};

		var s = JSON.stringify(chart);
		/*if (isNoData) {
			$('#spaceImgArea').css('display', 'none');
		} else {*/
		document.getElementById("spaceImgArea").value = s;
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=space",
				"spaceImgContent", "100%", "97%", "9.0.0",
				"expressInstall.swf", '', {
					"wmode" : "opaque"
				});
		//}
	}

	function buildLineImg() {
		var ovg = parseFloat(isNoData ? '0'
				: '${monitoringBean.otherVersionGB}');
		var max = 210;

		if (200 >= ovg) {
			max = 210;
		} else {
			max = ovg + 60;
		}

		var chart = {
			"title" : {
				"text" : "",
				"style" : "{font-size: 20px; color:#0000ff; font-family: Verdana; text-align: center;}"
			},
			"elements" : [ {
				"type" : "bar",
				"alpha" : 0.9,
				"font-size" : 13,
				"values" : [ {
					"top" : 200,
					"colour" : "#1B95D9",
					"font-size" : 13,
					"tip" : "Other Version阀值 ：#val#G\n"
				}, {
					"top" : ovg,
					"colour" : "#FF0000",
					"font-size" : 13,
					"tip" : currentDay + "\nOther Version值 ：#val#G\n"
				} ]
			} ],

			"x_axis" : {
				"stroke" : 2,
				"tick_height" : 3,
				"colour" : "#909090",
				"grid_colour" : "#333333",
				"labels" : {
					"align" : "center",
					"labels" : [ "阀值(G)", "实时监控(G)" ]
				}
			},

			"y_axis" : {
				"stroke" : 2,
				"tick_length" : 3,
				"colour" : "#909090",
				"grid_colour" : "#333333",
				"max" : max
			}
		}

		var s = JSON.stringify(chart);
		/*if (isNoData) {
			$('#ovImgArea').css('display', 'none');
		} else {*/
		document.getElementById("ovImgArea").value = s;
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=ov",
				"ovImgContent", "100%", "99%", "9.0.0", "expressInstall.swf",
				'', {
					"wmode" : "opaque"
				});
		//}
	}

	function open_flash_chart_data(id) {
		if (id == 'ov') {
			return document.getElementById("ovImgArea").value;
		} else if (id == 'conn') {
			return document.getElementById("connImgArea").value;
		} else {
			return document.getElementById("spaceImgArea").value;
		}
	}
</script>