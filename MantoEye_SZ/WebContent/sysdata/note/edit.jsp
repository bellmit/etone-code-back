<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/include/session.jsp"%>
<%@ include file="/include/setcache.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link type="text/css" rel="stylesheet"
	href="/skin/Default/css/modedialog.css" />
<title>首页公告栏信息</title>
<style type="text/css">
html,body {
	padding: 0px;
	margin: 0px;
	background-color: white;
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
	font-family: Georgia, "Times New Roman", Times, serif;
	font-size: 10px;
	font-style: normal;
	font-weight: bold;
}
</style>

<script type="text/javascript">
	var ids = '${indexNote.nmNoteId}';

	function submit() {
		var title = $('#vcTitle').val();
		var content = $('#vcContent').html();
		if ('' == title) {
			alert('公告标题不能为空，请输入！');
			return;
		}

		if ('' != ids && null != ids) {
			indexForm.action = '/indexNote_edit.do';
		}
		
		indexForm.submit();
	}
</script>
<base target="_self" />
</head>
<body style="overflow-x: hidden">
<div id="main_content">
<div id="titleLeft"></div>
<div id="titleCenter"><span id="title_content">首页公告栏信息</span></div>
<div id="titleRight"></div>
<form name="indexForm" action="/indexNote_add.do" method="post">
<table class="form_main">
	<tr>
		<td>公告标题</td>
		<td><input type="hidden" name="indexNote.nmNoteId"
			value="${indexNote.nmNoteId}" /> <input type="text"
			name="indexNote.vcTitle" id="vcTitle" value="${indexNote.vcTitle}"
			size="54" /></td>
	</tr>
	<tr>
		<td>公告内容</td>
		<td><textarea name="indexNote.vcContent" id="vcContent" cols="42"
			rows="12">${indexNote.vcContent}</textarea></td>
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