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
<title>消除告警</title>
<link type="text/css" rel="stylesheet"
	href="/skin/Default/css/maincontent.css" />
	<base target="_self" />
	<script type="text/javascript">
		function ok(){
			var alarmInfo = $('#alarmInfo').val();
			if( '' == alarmInfo){
				alert('请填写处理告警信息!');
				return;
			}

			var flag = window.confirm("确定消告警吗?");
			if(flag){
				beanForm.submit();
			}			
		}
	</script>
</head>
<body>
<form action="/flushAlarm_cancelWarn.do" method="post" name="beanForm" id="beanForm">
<table cellspacing="0" cellpadding="0" border="0"
	style="width: 100%; height: 100%;">
	<tr align="center" style="width: 100%;">
		<td width="4px" height="24px;"><img
			src="/skin/Default/images/MantoEye/maincontent/lefttitle.gif" /></td>
		<td width="100%" id="title"
			style="background: url(/skin/Default/images/MantoEye/maincontent/middletitle.gif) repeat-x; padding-left: 10px; font-size: 12px; font-weight: bold; color: #FFFFFF; vertical-align: middle;">
		告警处理备注信息</td>
		<td width="4px"><img
			src="/skin/Default/images/MantoEye/maincontent/righttitle.gif" /></td>
	</tr>
	<tr align="center">
		<td colspan="3"><textarea rows="9" cols="39" name="alarmInfo" id="alarmInfo"></textarea><input
			type="hidden" name="typeName" id="typeName" />
			<input type="hidden" id="type" name="type"/>
			<input type="hidden" id="typeId" name="typeId"/>
			<input type="hidden" id="warmDate" name="warmDate"/>
			</td>
	</tr>
	<tr align="center">
		<td colspan="3">
		<div id="mainbtn" style="padding-left: 136px;">
		<ul>
			<li><a href="#"
				onclick="javascript:ok();"><span>提交</span>
			</a></li>
			<li><a href="#"
				onclick="javascript:window.close();"><span>关闭</span>
			</a></li>
		</ul>
		</div>
		</td>
	</tr>
</table>
</form>
</body>
</html>

<script>
	var obj = window.dialogArguments;
	var typeName = obj.typeName;
	var type = obj.type;
	var warmDate = obj.warmDate;
	//alert(typeName+'--'+type+'--'+warmDate);
	$('#typeName').attr('value', typeName);
	$('#typeId').attr('value', typeName);
	$('#warmDate').attr('value', warmDate);
	$('#type').attr('value', type);
</script>