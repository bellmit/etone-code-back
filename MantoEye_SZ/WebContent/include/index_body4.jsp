<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>主页面</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" type="text/css" media="screen"
			href="/tools/jqgrid/themes/redmond/jquery-ui-1.8.2.custom.css" />
		<link rel="stylesheet" type="text/css" media="screen"
			href="/tools/jqgrid/themes/ui.jqgrid.css" />
		<link rel="stylesheet" type="text/css" media="screen"
			href="/tools/jqgrid/themes/ui.multiselect.css" />
		<style>
html,body {
	margin: 0; /* Remove body margin/padding */
	padding: 0;
	overflow: hidden; /* Remove scroll bars on browser window */
	font-size: 75%;
}

/*Splitter style */
#LeftPane { /* optional, initial splitbar position */
	overflow: auto;
}

/*
 * Right-side element of the splitter.
*/
#RightPane {
	padding: 2px;
	overflow: auto;
}

.ui-tabs-nav li {
	position: relative;
}

.ui-tabs-selected a span {
	padding-right: 10px;
}

.ui-tabs-close {
	display: none;
	position: absolute;
	top: 3px;
	right: 0px;
	z-index: 800;
	width: 16px;
	height: 14px;
	font-size: 10px;
	font-style: normal;
	cursor: pointer;
}

.ui-tabs-selected .ui-tabs-close {
	display: block;
}

.ui-layout-west .ui-jqgrid tr.jqgrow td {
	border-bottom: 0px none;
}

.ui-datepicker {
	z-index: 1200;
}

.rotate { /* for Safari */
	-webkit-transform: rotate(-90deg);
	/* for Firefox */
	-moz-transform: rotate(-90deg);
	/* for Internet Explorer */
	filter: progid :   DXImageTransform.Microsoft.BasicImage (   rotation =
		  3 );
}
</style>

		<script src="/tools/jqgrid/js/jquery-1.4.2.min.js"
			type="text/javascript"></script>
		<script src="/tools/jqgrid/js/jquery-ui-1.8.2.custom.min.js"
			type="text/javascript"></script>
		<script src="/tools/jqgrid/js/jquery.layout.js" type="text/javascript"></script>
		<script src="/tools/jqgrid/js/i18n/grid.locale-en.js"
			type="text/javascript"></script>
		<script src="/tools/jqgrid/js/grid.base.js" type="text/javascript"></script>

	</head>
	<body>
	<table style="width:100%"><tr>
	<td style="width:33%"><table id="list486"></table></td>
	<td style="width:33%"><table id="list487"></table></td>
	<td style="width:33%"><table id="list488"></table></td>
	</tr></table>	
	</body>
</html>
<script type="text/javascript">
var mydata = [
		{id:"1",name:"腾讯",tax:"10444.00",total:"2111000"} ,
		{id:"2",name:"3G门户",tax:"20.00",total:"320"},
		{id:"3",name:"乐讯",tax:"30.00",total:"430"},
		{id:"4",name:"移动梦网",tax:"10.00",total:"210"},
		{id:"5",name:"新浪",tax:"20.00",total:"320"},
		{id:"6",name:"动感论坛",tax:"30.00",total:"430"},
		{id:"7",name:"易查",tax:"10.00",total:"210"},
		{id:"8",name:"空中网",tax:"21.00",total:"320"},
		{id:"9",name:"搜狐",tax:"30.00",total:"430"},
		{id:"10",name:"百度",tax:"10.00",total:"210"}
	];
jQuery("#list486").jqGrid({
	data: mydata,
	datatype: "local",
	height: '100%',
	width: '100%', 
   	colNames:[' ','业务', '流量(G)', '用户数'],
   	colModel:[
   		{name:'id',index:'id', width:12, sorttype:"int"},
   		{name:'name',index:'name', width:48,sortable:false},
   		{name:'tax',index:'tax', width:50, align:"right",sorttype:"float",formatter:"number"},
   		{name:'total',index:'total', width:56,align:"right",sorttype:"int"}
   		],
   	viewrecords: true,
   	sortname: 'id',
   	caption: "十大移动自有业务</select>"
});
</script>

