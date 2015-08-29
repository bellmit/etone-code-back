<%@ page contentType="text/html;charset=utf-8"%>


<html>
	<head>
		<link rel="stylesheet" type="text/css"
			href="/tools/flexigrid/css/flexigrid/flexigrid.css">
		<script type="text/javascript"
			src="/tools/flexigrid/lib/jquery/jquery.js"></script>
		<script type="text/javascript" src="/tools/flexigrid/flexigrid.js"></script>
		<title>测试</title>
	</head>
	<body>
	<input type="hidden" value="${xml }">
		<table id=top_ten></table>
	</body>
	<script type="text/javascript">
 $(document).ready(function(){
 $("#top_ten").flexigrid({
 url: "/flexiGridTest_list.do?1=1",
 dataType: 'xml',
 colModel : [{display: '编号', name : 'id', width : 65, sortable : true, align: 'center'},
 {display: '内容提要', name : 'summary', width : 180, sortable : true, align: 'left'},
 {display: '内容类别', name : 'contentType', width : 60, sortable : true, align: 'left'},
 {display: '字段名', name : 'arriveRate', width : 90, sortable : true, align: 'left'},
 {display: '字段名', name : 'fitRate', width : 90, sortable : true, align: 'left'}],
 sortname: "pushCount",sortorder: "desc",usepager: true,title: '表头名称',
 useRp: true,rp: 10,showTableToggleBtn: true,width: 650,height: 300,
rpOptions: [10,15,20,25,40],pagestat: '第 {from}到 {to}条记录     共 {total} 条记录'
 });
 });
   </script>
</html>

