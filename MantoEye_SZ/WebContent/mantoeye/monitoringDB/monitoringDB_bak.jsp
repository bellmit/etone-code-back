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
	font-size: 13px;
	line-height: 2.5em;
	padding-left: 16px;
}

.img_content {
	height: 200px;
	width: 100%;
	text-align: center;
	vertical-align: middle;
	background-image: url(noData.JPG);
	background-repeat: no-repeat;
}
</style>
</head>
<body>
<div id="main_content">
<div id="header_line"></div>
<div id="content_left">
<div class="img_content"><img id="ov_img" class="data_img"
	src="monitoringDB_dial.do?type=ov&curValue=${monitoringBean.otherVersionGB}&threshold=200G" alt="Other Version实时监控" /></div>
<div class="data_bgImg">数据库Other Version</div>
<div id="ov_data" class="main_data">监控时间：<span
	class="monitoringTime"></span><br />
Other Version值：${monitoringBean.otherVersionGB}G<br />
Other Version率阀值：200G<br />
告警级别：正常</div>
</div>
<div id="content_center">
<div class="img_content"><img id="conn_img" class="data_img"
	src="monitoringDB_dial.do?type=conn&curValue=${monitoringBean.curConn}&maxValue=${monitoringBean.connMax}&threshold=80%" alt="数据库连接数实时监控" /></div>
<div class="data_bgImg">数据库连接数</div>
<div id="conn_data" class="main_data">监控时间：<span
	class="monitoringTime"></span><br />
当前连接数：${monitoringBean.curConn}个<br />
允许最大连接数:${monitoringBean.connMax}个<br />
连接数阀值：80%<br />
告警级别：<font color="red">异常</font></div>
</div>
<div id="content_right">
<div class="img_content"><img id="space_img" class="data_img"
	src="monitoringDB_dial.do?type=space&curValue=${monitoringBean.usedMainSpaceSizeGB}&maxValue=${monitoringBean.mainSpaceSizeGB}&threshold=65%" alt="数据库存储空间实时监控" /></div>
<div class="data_bgImg">数据库存储空间</div>
<div id="space_data" class="main_data">监控时间：<span
	class="monitoringTime"></span><br />
主存储空间：${monitoringBean.mainSpaceSizeGB}G<br />
已利用存储空间：${monitoringBean.usedMainSpaceSizeGB}G<br />
存储空间利用率：${monitoringBean.otherVersionGB}%<br />
利用率阀值：64%<br />
告警级别：正常</div>
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

	$(document).ready(function() {
		$('.monitoringTime').html(currentDate);

		/**
		var nodata = '{nodata}';
		if (null != nodata && 'NODATA' == nodata) {
			$('.data_img').css('display', 'none');
		} else {
			$('.data_img').css('display', 'block');
		}
		*/
	});
</script>