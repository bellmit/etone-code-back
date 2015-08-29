<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="scripts/jquery.min.js"
	language="UTF-8"></script>
<script type="text/javascript" src="scripts/jquery.easyui.min.js"
	language="UTF-8"></script>
<!-- <script type="text/javascript" src="scripts/easyui-lang-zh_CN.js" -->
<script type="text/javascript" src="scripts/easyui-lang-zh_TW.js"
	language="UTF-8"></script>
<link rel="stylesheet" href="themes/icon.css" lang="UTF-8" />
<link rel="stylesheet" href="themes/default/easyui.css" lang="UTF-8" />
<title>EasyUI Study</title>
<script type="text/javascript">
	$(function() {

	});
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false"
		style="height: 60px; background: #B3DFDA; padding: 10px"></div>
	<div data-options="region:'west',split:true" title="功能列表"
		style="width: 300px;"></div>
	<div data-options="region:'center',title:'數據列表',href:'center.jsp'"
		style="overflow: hidden;"></div>
</body>

</html>