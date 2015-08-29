<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/include/session.jsp"%>
<%@ include file="/include/setcache.jsp"%>
<html>
	<head>
		<title>无标题文档</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<style>
html,body {
	width: 100%;
	height: 100%;
	padding: 0;
	margin: 0;
	overflow: hidden;
}
</style>
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/TabPanel.css"
			type="text/css"></link>
	</head>
	<script type="text/javascript"
		src="/js/jquery/extend.tab/js/TabPanel.js"></script>
	<script type="text/javascript" src="/js/jquery/extend.tab/js/Fader.js"></script>
	<script type="text/javascript"
		src="/js/jquery/extend.tab/js/Math.uuid.js"></script>
	<body>

		<script type="text/javascript">

/////////////////////////////////////////////////////////////////
var tabpanel;
$(document).ready(function(){
	tabpanel = new TabPanel({
		renderTo:'tab',
		//width:'100%', //如果设置了宽度,就不会自动改变宽度
		//height:400,
		//border:'none',
		active : 0,
		items : [{
			id:'toolbarPlugin',
			title:'工具栏组件',
			html:'<iframe src="./toolbar.jsp" width="100%" height="100%" frameborder="0"></iframe>',
			closable: false
		}]
	});
});
function addadd(){
	tabpanel.addTab({title:'无限添加',id:'aaa', html:'<iframe src="sumbitTab.jsp" width="100%" height="100%" frameborder="0"></iframe>'});
	//alert(tabpanel.getContent('aaa'));
}
</script>
		<div id="tab" style="width: 100%; height: 500px;"></div>
		<input type="button" value="无限添加" onclick="addadd();" />
		<input type="button" value="按照ID获得选项卡Title"
			onclick="alert(tabpanel.getTitle('toolbarPlugin'));" />
		<input type="button" value="获得被激活选项卡的Title"
			onclick="alert(tabpanel.getActiveTab().title.text());" />
	</body>
</html>