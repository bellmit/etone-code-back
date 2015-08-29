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
<script type="text/javascript" src="scripts/easyui-lang-zh_TW.js"
	language="UTF-8"></script>
<script type="text/javascript" src="scripts/zUtil.js" language="UTF-8"></script>
<link rel="stylesheet" href="themes/icon.css" lang="UTF-8" />
<link rel="stylesheet" href="themes/default/easyui.css" lang="UTF-8" />
<title>s2s3h4e-spending</title>
<script type="text/javascript">
	$(function() {
	});
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north', border:false" style="height: 60px">s2s3h4e-spending</div>
	<div data-options="region:'west'" style="width: 300px;">
		<jsp:include page="layout/west.jsp"></jsp:include>
	</div>
	<div data-options="region:'center'" style="overflow: hidden;"><jsp:include
			page="layout/center.jsp"></jsp:include></div>
	<div data-options="region:'south',border:false" style="height: 25px;">
		<center>@copyright 2013</center>
	</div>
</body>
</html>