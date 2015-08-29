<?xml version="1.0" encoding="utf-8" ?>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
	<head>
		<title>即席查询帮助</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<script language="javascript" src="/js/script.js"></script>
		<link type="text/css" rel="Stylesheet"
			href="/skin/Default/css/style.css" />
	</head>
	<body>
		<br/>
		<br/>
		<div style="padding-left: 20px">
		<p>
			即席查询说明：
		</p>
		<p>
		==================================================================
		</p>
		<p>
		查询步骤：
		</p>
		<p>
		1.选择业务类型。
		</p>
		<p>
		2.选择表类型(小时表、天表、周表、月表)。
		</p>
		<p>
		3.选择维度，选择后会查询出所有包含所选维度的表。
		</p>
		<p>
		4.选择需要查询的表：页面会列表显示该表所有可供即席查询的字段。
		</p>
		<p>
		5.设置需要输出的字段：勾选需要输出的字段对应的‘设置输出’列的选择框。
		</p>
		<p>
		6.设置过滤条件：勾选需要作为条件的字段‘设为条件’列的选择框，选择其后的‘操作符一’并在‘条件值一’内填入条件。</p>
		<p>&nbsp;&nbsp;可以通过‘操作符一’和‘操作符二’实现区间查询，如果‘操作符一’选择了‘=’则‘操作符二’可以不选。
		</p>
		<p>
		7.设置完成后，点击‘查询’按钮进行查询。
		</p>
		<p>
		输入说明：
		</p>
		<p>
		1.‘字段类型’为‘数字类型’时，条件值内只能填入数字。
		</p>
		<p>
		2.字段年、月、日、小时、周的‘条件值’内需要填入适当的数字。
		</p>
		<p>
		3.日期类型的数据输入格式示例（2010-03-20 10:00:00）。
		</p>
		</div>
	</body>
</html>
<script language="javascript">
</script>