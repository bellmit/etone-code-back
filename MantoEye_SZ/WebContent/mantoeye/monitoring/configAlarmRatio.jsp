<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/include/session.jsp"%>
<%@ include file="/include/setcache.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link type="text/css" rel="stylesheet"
	href="/skin/Default/css/modedialog.css" />
<title>阀值设置</title>
<style type="text/css">
html,body {
	padding: 0px;
	margin: 0px;
	background-color: white;
	width: 100%;
	height: 100%;
}

#titleLeft {
	background-image:
		url(/skin/Default/images/MantoEye/maincontent/lefttitle.gif);
	background-repeat: no-repeat;
	height: 27px;
	width: 4px;
	float: left;
}

#titleCenter {
	background-image:
		url(/skin/Default/images/MantoEye/maincontent/middletitle.gif);
	background-repeat: repeat-x;
	float: left;
	width: 98.6%;
	height: 27px;
	color: white;
}

#titleRight {
	background-image:
		url(/skin/Default/images/MantoEye/maincontent/righttitle.gif);
	background-repeat: no-repeat;
	height: 27px;
	width: 4px;
	float: right;
}

#title_content {
	padding-left: 6px;
	line-height: 1.6em;
	font-weight: bold;
}

.form_main {
	margin: auto;
	clear: both;
	width: 80%;
	border: #B1C1CD solid 1px;
	font-family: monospace, "Times New Roman", Times, serif;
	font-size: 10px;
	font-style: normal;
	font-weight: bold;
}
</style>

<script type="text/javascript">
	function clearNoNum(obj) {
		/**
		obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符 
		obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是. 
		//obj.value = obj.value.replace(/\.$/g,"");  //验证最后一个字符是数字而不是. 
		obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的. 
		obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
		
		 */
		//obj.value = obj.value.replace(/[^0-9]{1,2}([.]{1}[^0-9]{1,2})$/g,"");
		//obj.value = obj.value.replace(/[^\d]/g,""); //只保留第一个. 清除多余的.
		if (obj.value > 100) {
			alert('最大值不得超过100！');
			obj.focus();
			obj.value = '';
			return;
		}

		if (isNaN(obj.value)) {
			//alert('请输入1-100内的数字');
			alert('请输入1-100内的数字，如果是存在小数，小数位为2两位！(如：12.34)');
			obj.focus();
			obj.value = '';
			return;
		}
		
	}

	var obj = window.dialogArguments;
	var typeName = obj.typeName;
	var type = obj.type;

	$(document).ready(function() {
		$('#title_content').html(typeName + '阀值设置');
		$('#regnizeLimmitIntType').attr('value', type);
	});

	function submit() {
		var minLimmit = $('#minLimmit').val();
		var maxLimmit = $('#maxLimmit').val();
		if ('' == minLimmit) {
			alert('阀值下限不能为空，请输入！');
			return;
		}
		if ('' == maxLimmit) {
			alert('阀值上限不能为空，请输入！');
			return;
		}
		if (parseFloat(minLimmit) >= parseFloat(maxLimmit)) {
			alert('阀值下限不能大于阀值上限，请输入！');
			return;
		}

		indexForm.submit();
	}
</script>
<base target="_self" />
</head>
<body style="overflow-x: hidden">
<div id="main_content">
<div id="titleLeft"></div>
<div id="titleCenter"><span id="title_content">阀值设置</span></div>
<div id="titleRight"></div>
<form name="indexForm"
	action="/distinguishRatioAlarm_configAlarmRatioLimmit.do" method="post">
<table class="form_main">
	<tr>
		<td>阀值</td>
		<td><input type="hidden" name="ar.typeName"
			id="regnizeLimmitIntType" /> <input type="text" name="ar.minLimmit"
			id="minLimmit" value="${ar.minLimmit}" size="12"
			onblur="clearNoNum(this);" />&nbsp;～&nbsp;<input type="text"
			name="ar.maxLimmit" id="maxLimmit" value="${ar.maxLimmit}" size="12"
			onkeyup="clearNoNum(this);" />&nbsp;&nbsp;&nbsp;(阀值上下限值在1～100之间)</td>
	</tr>
	<tr align="center">
		<td colspan="2" align="center">
		<div id="mainbtn" style="padding-left: 180px;">
		<ul>
			<li><a href="javascript:submit();"><span>保存</span></a></li>
			<li><a href="javascript:window.close();"><span>关闭</span></a></li>
		</ul>
		</div>
		</td>
	</tr>
</table>
</form>
</div>
</body>
</html>