<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {

		var currentChoseRow = undefined;
		var systemlist_datagrid = $('#systemlist_datagrid').datagrid();
		systemlist_datagrid
				.datagrid({
					title : '賬單列表',
					url : 'systemDataAction_query.do',
					pagination : true,
					pageSize : 13,
					pageList : [ 13,20, 50, 80, 100, 200 ],
					border : false,
					fit : true,
					fitColumns : false, //動態改變列的寬度,適用於列不是很多的情況下，true : 不出現滾動條
					nowarp : false,//自動折行，true:不折行
					idField : 'id',
					sortName : 'dataDate',
					sortOrder  : 'ase'
					frozenColumns : [ [ {
						field : 'id',
						title : '編號',
						checkbox : true,
						width : 60,
						align : 'center'
					}, {
						field : 'dataDate',
						title : '時間',
						width : 160,
						sortable : true,
						align : 'center',
						editor : {
							type : 'datebox',
							options : {
								required : true,
								editable : false,
								formatter : function(date) {
									var y = date.getFullYear();
									var m = date.getMonth() + 1;
									var d = date.getDate();
									return (y + '-' + m + '-' + d);
								}
							}
						}
					} ] ],//fitColumns : true時不起作用
					columns : [ [
							{
								field : 'shouzhi',
								title : '收支',
								width : 60,
								sortable : true,
								align : 'center',
								editor : {
									type : 'validatebox',
									options : {
										required : true
									}
								}
							},
							{
								field : 'jiezhi',
								title : '介質',
								width : 88,
								sortable : true,
								editor : {
									type : 'validatebox',
									options : {
										required : true
									}
								},
								formatter : function(value, row, index) {
									if (row.jiezhi) {
										return '<span title="'+row.jiezhi+'">'
												+ row.jiezhi + '</span>';
									} else {
										return '<span title="'+value+'">'
												+ value + '</span>';
									}
								}
							}, {
								field : 'leixing',
								title : '類型',
								width : 128,
								sortable : true,
								editor : {
									type : 'validatebox',
									options : {
										required : true
									}
								}
							}, {
								field : 'xingzhi',
								title : '性質',
								width : 128,
								sortable : true,
								editor : {
									type : 'validatebox',
									options : {
										required : true
									}
								}
							}, {
								field : 'money',
								title : '金額',
								width : 100,
								sortable : true,
								align : 'right',
								editor : {
									type : 'validatebox',
									options : {
										required : true
									}
								}
							}, {
								field : 'note',
								title : '說明',
								width : 300,
								sortable : true,
								editor : {
									type : 'validatebox',
									options : {
										required : false
									}
								}
							} ] ],
					toolbar : [
							{
								text : '增加',
								iconCls : 'icon-add',
								handler : function() {
									if (currentChoseRow != undefined) {
										systemlist_datagrid.datagrid('endEdit',
												currentChoseRow);
									} else {
										systemlist_datagrid
												.datagrid(
														'insertRow',
														{
															index : 0,
															row : {
																id : '',
																dataDate : function() {
																	var date = (new Date());
																	var y = date
																			.getFullYear();
																	var m = date
																			.getMonth() + 1;
																	var d = date
																			.getDate();
																	return (y
																			+ '-'
																			+ m
																			+ '-' + d);
																},
																note : ''
															}
														});
										systemlist_datagrid.datagrid(
												'beginEdit', 0);
										currentChoseRow = 0;
									}
								}
							},
							'-',
							{
								text : '刪除',
								iconCls : 'icon-remove',
								handler : function() {
									var rows = systemlist_datagrid
											.datagrid('getSelections');

									if (rows.length > 0) {
										$.messager
												.confirm(
														'請確認',
														'確定要刪除所選的記錄？',
														function(y) {
															if (y) {
																var ids = [];
																for ( var i = 0; i < rows.length; i++) {
																	ids
																			.push(rows[i].id);
																}
																$
																		.ajax({
																			url : 'systemDataAction_delete.do',
																			data : {
																				ids : ids
																						.join(',')
																			},
																			dataType : 'json',
																			success : function(
																					r) {
																				if (r
																						&& r.success) {
																					systemlist_datagrid
																							.datagrid('load');
																					$.messager
																							.show({
																								msg : '刪除成功',
																								title : '提示'
																							});
																				} else {
																					systemlist_datagrid
																							.datagrid('rejectChanges');
																					$.messager
																							.alert(
																									'提示',
																									'刪除失敗',
																									'error');
																				}
																				currentChoseRow = undefined;
																				systemlist_datagrid
																						.datagrid('unselectAll');
																			}
																		});
															}
														});
									} else {
										$.messager.alert('提示', '請選擇要選擇的記錄',
												'error');
									}
								}
							},
							'-',
							{
								text : '保存',
								iconCls : 'icon-save',
								handler : function() {
									systemlist_datagrid.datagrid('endEdit',
											currentChoseRow);
								}
							},
							'-',
							{
								text : '取消操作',
								iconCls : 'icon-redo',
								handler : function() {
									systemlist_datagrid
											.datagrid('rejectChanges');
									systemlist_datagrid.datagrid('unselectAll');
									currentChoseRow = undefined;
								}
							}, '-' ],
					onAfterEdit : function(rowIndex, rowData, changes) {
						var inserted = systemlist_datagrid.datagrid(
								'getChanges', 'inserted');
						var updated = systemlist_datagrid.datagrid(
								'getChanges', 'updated');

						if (inserted.length < 1 && updated.length < 1) {
							currentChoseRow = undefined;
							systemlist_datagrid.datagrid('unselectAll');
							return;
						}

						var url = '';
						if (inserted.length > 0) {
							url = 'systemDataAction_add.do';
						}

						if (updated.length > 0) {
							url = 'systemDataAction_edit.do';
						}

						$.ajax({
							url : url,
							data : rowData,
							dataType : 'json',
							success : function(r) {
								if (r && r.success) {
									systemlist_datagrid
											.datagrid('acceptChanges');
									$.messager.show({
										msg : r.msg,
										title : '成功'
									});
								} else {
									systemlist_datagrid
											.datagrid('rejectChanges');
									$.messager.alert('錯誤', r.msg, 'error');
								}
								currentChoseRow = undefined;
								systemlist_datagrid.datagrid('unselectAll');
							}
						});
					},
					onDblClickRow : function(rowIndex, rowData) {
						if (currentChoseRow != undefined) {
							systemlist_datagrid.datagrid('endEdit',
									currentChoseRow);
						} else {
							systemlist_datagrid.datagrid('beginEdit', rowIndex);
							currentChoseRow = rowIndex;
						}
					}
				});
		$('.datagrid-header div').css('textAlign', 'center');

	});
</script>
<table id="systemlist_datagrid"></table>
