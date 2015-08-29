<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	var dielectricList_datagrid;
	$(function() {
		var currentChoseRow = undefined;
		dielectricList_datagrid = $('#dielectricList_datagrid').datagrid({
			title : '我的錢包',
			url : '${pageContext.request.contextPath}/spendingAction_dielList.zzw',
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 15, 20, 25, 100 ],
			border : false,
			fit : true,
			fitColumns : true, //動態改變列的寬度,適用於列不是很多的情況下，true : 不出現滾動條
			idField : 'intDielectricId',
			sortName : 'vcNote',
			sortOrder : 'asc',
			frozenColumns : [ [ {
				field : 'intDielectricId',
				title : '編號',
				checkbox : true,
				width : 60,
				align : 'center'
			}, {
				field : 'vcNote',
				title : '錢包',
				width : 260,
				sortable : true,
				align : 'right',
				halign : 'center',
				editor : {
					type : 'validatebox',
					options : {
						required : true
					}
				}
			} ] ],//fitColumns : true時不起作用
			columns : [ [ {
				field : 'nmBalances',
				title : '餘額',
				width : 180,
				sortable : true,
				align : 'right',
				halign : 'center',
				formatter : function(val, row) {
					if (val < 0) {
						return '<span style="color:red;">(' + val + ')</span>';
					} else {
						return val;
					}
				},
				editor : {
					type : 'numberbox',
					options : {
						required : true
					}
				}
			} ] ],
			toolbar : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					if (currentChoseRow != undefined) {
						dielectricList_datagrid.datagrid('endEdit', currentChoseRow);
					} else {
						dielectricList_datagrid.datagrid('insertRow', {
							index : 0,
							row : {
								vcNote : '',
								nmBalances : 0.0
							}
						});
						dielectricList_datagrid.datagrid('beginEdit', 0);
						currentChoseRow = 0;
					}
				}
			}, '-', {
				text : '刪除',
				iconCls : 'icon-remove',
				handler : function() {
					var rows = dielectricList_datagrid.datagrid('getSelections');

					if (rows.length > 0) {
						$.messager.confirm('請確認', '確定要刪除所選的記錄？', function(y) {
							if (y) {
								//QUERY_t#id_S_EQ
								var ids = [];
								for ( var i = 0; i < rows.length; i++) {
									ids.push(rows[i].intDielectricId);
								}
								$.ajax({
									url : '${pageContext.request.contextPath}/spendingAction_delBalances.zzw',
									data : {
										'QUERY_t#intDielectricId_A|I_IN' : ids.join(',')
									},
									dataType : 'json',
									error : function(r) {
										$.messager.alert('提示', r.statusText, 'error');
									},
									success : function(r) {
										if (r && r.success) {
											dielectricList_datagrid.datagrid('load');
											$.messager.show({
												msg : '刪除成功',
												title : '提示'
											});
										} else {
											dielectricList_datagrid.datagrid('rejectChanges');
											$.messager.alert('提示', '刪除失敗', 'error');
										}
										currentChoseRow = undefined;
										dielectricList_datagrid.datagrid('unselectAll');
									}
								});
							}
						});
					} else {
						$.messager.alert('提示', '請選擇要選擇的記錄', 'error');
					}
				}
			}, '-', {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					dielectricList_datagrid.datagrid('endEdit', currentChoseRow);
				}
			}, '-', {
				text : '取消操作',
				iconCls : 'icon-redo',
				handler : function() {
					dielectricList_datagrid.datagrid('rejectChanges');
					dielectricList_datagrid.datagrid('unselectAll');
					currentChoseRow = undefined;
				}
			}, '-' ],
			onAfterEdit : function(rowIndex, rowData, changes) {
				var inserted = dielectricList_datagrid.datagrid('getChanges', 'inserted');
				var updated = dielectricList_datagrid.datagrid('getChanges', 'updated');

				if (inserted.length < 1 && updated.length < 1) {
					currentChoseRow = undefined;
					dielectricList_datagrid.datagrid('unselectAll');
					return;
				}

				var url = '';
				if (inserted.length > 0) {
					url = '${pageContext.request.contextPath}/spendingAction_addBalances.zzw';
				}

				if (updated.length > 0) {
					url = '${pageContext.request.contextPath}/spendingAction_editBalances.zzw';
				}

				$.ajax({
					url : url,
					data : rowData,
					dataType : 'json',
					success : function(r) {
						if (r && r.success) {
							dielectricList_datagrid.datagrid('acceptChanges');
							$.messager.show({
								msg : r.msg,
								title : '成功'
							});
						} else {
							dielectricList_datagrid.datagrid('rejectChanges');
							$.messager.alert('錯誤', r.msg, 'error');
						}
						currentChoseRow = undefined;
						dielectricList_datagrid.datagrid('unselectAll');
					}
				});
			},
			onDblClickRow : function(rowIndex, rowData) {
				if (currentChoseRow != undefined) {
					dielectricList_datagrid.datagrid('endEdit', currentChoseRow);
				} else {
					dielectricList_datagrid.datagrid('beginEdit', rowIndex);
					currentChoseRow = rowIndex;
				}
			},
			onLoadSuccess : function(r) {
				if (!r.success) {
					$.messager.show({
						msg : r.msg,
						title : '錯誤',
						timeout : 0
					});
				}
			},
			onLoadError : function(r) {
				$.messager.show({
					msg : r.statusText + ' ' + r.status,
					title : '錯誤',
					timeout : 0
				});
			}
		});
	});
</script>
<table id="dielectricList_datagrid"></table>
