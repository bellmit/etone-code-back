<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/include/session.jsp"%>
<%@ include file="/include/setcache.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="com.opensymphony.xwork2.ActionContext"%><html
	xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>告警展示页面</title>
 
</head>
<body> 
</body>
</html>
<script type="text/javascript">

var date = new Date();
var year = date.getYear();
var m = date.getMonth();
var month = (m+1) > 9 ? (m+1) + '' : '0' + (m+1);
var d = date.getDate();
d -= 1;
var day = d > 9 ? d + '' : '0' + d;

var dddddddd = year + '-' + month + '-' + day;
$(document).ready(function(){
	$.ajax({
		type : "post",
		url : "distinguishRatioAlarm_pieQuery.do?1=1",
		data : {
			date : dddddddd
		},
		error : function() {
			alert('服务器出错,请联系管理员!');
		}
	});	
});
</script>
