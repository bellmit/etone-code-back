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
}

#main_content {
	background-color: white;
	width: 100%;
	height: 100%;
}

#header_line {
	background-color: #D3E0F2;
	height: 1px;
	width: 100%;
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
	background-image: url('/skin/Default/images/bg/tableHeaderBG.gif');
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
	margin: auto;
	text-align: center;
	vertical-align: middle;
	background-image: url(noData.JPG);
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
<div id="header_line"></div>
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
<p class="info">other version值反应了多机（multiplex）数据库<br />
的版本变化情况。一个版本表示某个连接的操作情况，<br />
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
主存储空间：${monitoringBean.mainSpaceSizeGB}G<br />
已利用存储空间：${monitoringBean.usedMainSpaceSizeGB}G<br />
存储空间利用率：${monitoringBean.mainSpaceRate}%<br />
利用率阀值：64%<br />
告警级别：<font id="monitoringColorSpace">正常</font></div>
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

	$(document).ready(function() {
		$('.monitoringTime').html(currentDate);

		getMonitoringColor();
		buildLineImg();
		buildPieImg2();
		buildPieImg();
	});

	function getMonitoringColor() {
		var ovv = '${monitoringBean.otherVersionGB}';
		var thv_ov = 200;
		var connv = '${monitoringBean.curConn}';
		var thv_conn = eval('${monitoringBean.connMax}') * 0.8;
		var usv = '${monitoringBean.mainSpaceRate}';
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
		var m = '${monitoringBean.connMax}';
		var um = '${monitoringBean.curConn}';
		var mum = eval(m) - eval(um);
		var connv = parseInt(mum);

		var chart = {
			"elements" : [ {
				"type" : "pie",
				"colours" : [ "#200ae9", "#F347EB" ],
				"tip" : "#label# : #val#个 \n(共#total#个 占比：#percent#) ",
				"alpha" : 0.7,
				"start-angle" : 35,
				"font-size" : 12,
				"values" : [ {
					"value" : '${monitoringBean.curConn}',
					"label" : "当前连接数",
					"color" : "#200ae9"
				}, {
					"value" : connv,
					"label" : "空闲连接数",
					"color" : "#F347EB"
				} ]
			} ]
		};

		var s = JSON.stringify(chart);

		document.getElementById("connImgArea").value = s;
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=conn",
				"connImgContent", "88%", "99%", "9.0.0", "expressInstall.swf",
				'', {
					"wmode" : "opaque"
				});
	}

	function buildPieImg() {
		var m = '${monitoringBean.mainSpaceSizeGB}';
		var um = '${monitoringBean.usedMainSpaceSizeGB}';
		var mum = eval(m) - eval(um);
		var connv = parseFloat(mum);

		var chart = {
			"elements" : [ {
				"type" : "pie",
				"colours" : [ "#200ae9", "#F347EB" ],
				"tip" : "#label# : #val#G \n(共#total#G 占比：#percent#) ",
				"alpha" : 0.7,
				"start-angle" : 35,
				"font-size" : 12,
				"values" : [ {
					"value" : '${monitoringBean.usedMainSpaceSizeGB}',
					"label" : "已利用空间",
					"color" : "#200ae9"
				}, {
					"value" : connv,
					"label" : "未利用空间",
					"color" : "#F347EB"
				} ]
			} ]
		};

		var s = JSON.stringify(chart);

		document.getElementById("spaceImgArea").value = s;
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=space",
				"spaceImgContent", "88%", "97%", "9.0.0", "expressInstall.swf",
				'', {
					"wmode" : "opaque"
				});
	}

	function buildLineImg() {
		var ovg = parseFloat('${monitoringBean.otherVersionGB}');
		var max = 400;

		if( 0 == ovg){
			max = 400;
		}else{
			max = ovg * 2;
			while( max <= 200 ){
				max = max * 2;
			}
		}

		var chart = {
			"title" : {
				"text" : "",
				"style" : "{font-size: 20px; color:#0000ff; font-family: Verdana; text-align: center;}"
			},
			"elements" : [ {
				"type" : "bar",
				"alpha" : 0.5,
				"colour" : "#FF0000",
				"text" : "阀值(G)",
				"font-size" : 13,
				"tip" : " Other Version阀值 ：#val#G\n",
				"values" : [ 200 ]
			}, {
				"type" : "bar",
				"alpha" : 0.5,
				"colour" : "#0000FF",
				"text" : "实时监控(G)",
				"font-size" : 13,
				"tip" : currentDay + "\nOther Version值 ：#val#G\n",
				"values" : [ ovg ]
			} ],

			"x_axis" : {
				"stroke" : 2,
				"tick_height" : 3,
				"colour" : "#d000d0",
				"grid_colour" : "#00ff00",
				"labels" : {
					"align" : "center",
					"labels" : [ currentDay ]
				}
			},

			"y_axis" : {
				"stroke" : 2,
				"tick_length" : 3,
				"colour" : "#d000d0",
				"grid_colour" : "#00ff00",
				"max" : max
			}
		}

		var s = JSON.stringify(chart);

		document.getElementById("ovImgArea").value = s;
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=ov",
				"ovImgContent", "88%", "99%", "9.0.0", "expressInstall.swf",
				'', {
					"wmode" : "opaque"
				});
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