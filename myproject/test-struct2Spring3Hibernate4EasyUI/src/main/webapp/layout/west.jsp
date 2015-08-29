<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>西部導航</title>
<script type="text/javascript">
	$(function() {
		$('#index_memuTree').tree(
				{
					url : '/memuAction_getAllTreeNodes.do',
					lines : true,
					parentField : 'pid',
					textFiled : 'vcTitle',
					onLoadSuccess : function(node, data) {
						$(this).tree('collapseAll');
					},
					onClick : function(node) {
						addTab({
							title : node.text,
							closable : true,
							href : node.attributes.vcUrl,
							param : "?intYear=" + node.attributes.intYear
									+ "&intMonth=" + node.attributes.intMonth
						});
					}
				});
	});
</script>
</head>
<body>
	<div class="easyui-panel"
		data-options="title:'功能導航',border:false,fit:true">
		<div class="easyui-accordion" data-options="border:false,fit:true">
			<div title="賬務列表" style="overflow: auto; padding: 10px;">
				<ul id="index_memuTree"></ul>
			</div>
		</div>
	</div>
</body>
</html>