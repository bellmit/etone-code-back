<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	
</script>
<table class="easyui-datagrid" title="Footer Rows in DataGrid"
	data-options="
url: 'system/datagrid_data1.json',	border : false,
method: 'get',pagination : true,pageSize : 10,
			pageList : [ 10, 15, 20, 25, 50, 100 ],
fit : true,
fitColumns: true,
singleSelect: true,
showFooter: true,
remoteSort:false,
multiSort:true,
onLoadError : function(r) {
console.info(r);
					$.messager.show({
						msg : r.statusText+' '+r.status,
						title : '錯誤',
						timeout : 0
					});
			}
">
	<thead>
		<tr>
			<th data-options="field:'itemid',width:80,sortable:true">Item ID</th>
			<th data-options="field:'productid',width:120,sortable:true">Product
				ID</th>
			<th data-options="field:'attr1',width:250,sortable:true">Attribute</th>
			<th data-options="field:'status',width:60,align:'center'">Status</th>
			<th
				data-options="field:'listprice',width:80,align:'right',sortable:true">List
				Price</th>
			<th
				data-options="field:'unitcost',width:80,align:'right',sortable:true">Unit
				Cost</th>
		</tr>
	</thead>
</table>
