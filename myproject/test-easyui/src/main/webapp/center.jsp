<script type="text/javascript" language="UTF-8">
<!--
	var dataGrid;
	$(function() {
		dataGrid = $("#dataGrid");
		datagrid.datagrid({
			url : '',
			titile : '10月份賬單',
			iconCls : 'icon-save',
			pagination : true,
			pageSize : 20,
			pageList : [ 20, 40, 60, 100, 200 ],
			fit : true,
			fitColumns : true,
			nowrap : false,

		});
	});
//-->
</script>
<div class="easyui-tabs" fit="true" border="false">
	<div title="10" boder="false">
		<table id="dataGrid"></table>
	</div>
</div>
