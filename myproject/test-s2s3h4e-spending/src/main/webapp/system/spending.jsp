<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	var spendsubspendingList_datagrid;
	$(function() {
		var currentChoseRow = undefined;
		spendsubspendingList_datagrid = $('#spendsubspendingList_datagrid').datagrid({
			title : '收支管理',
			url : '${pageContext.request.contextPath}/spendingAction_rssslList.zzw',
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 15, 20, 25, 50, 100 ],
			border : false,
			fit : true,
			fitColumns : true, //動態改變列的寬度,適用於列不是很多的情況下，true : 不出現滾動條
			idField : 'rsssId',
			sortName : 'vcRNote',
			sortOrder : 'asc',
			// 			remoteSort : false,
			multiSort : true,
			columns : [ [ {
				field : 'chReveExpenId',
				title : 'R編號',
				width : 60,
				hidden : true,
				align : 'center'
			}, {
				field : 'nmSpendId',
				title : 'S編號',
				width : 60,
				hidden : true,
				align : 'center'
			}, {
				field : 'nmSubSpendId',
				title : 'SS編號',
				checkbox : true,
				width : 60,
				align : 'center'
			}, {
				field : 'vcRNote',
				title : '收支',
				width : 260,
				sortable : true,
				align : 'center',
				formatter : function(value, row) {
					if (row.positiveOrNegative == undefined)
						return row.vcRNote;
					return row.vcRNote + " (" + row.positiveOrNegative + ")";
				},
				editor : {
					type : 'combogrid',
					options : {
						panelHeight : 120,
						idField : 'vcNote',
						textField : 'vcNote',
						border : false,
						fit : true,
						fitColumns : true,
						required : true,
						url : '${pageContext.request.contextPath}/spendingAction_reveExpenList.zzw',
						columns : [ [ {
							field : 'vcNote',
							title : '性質',
							width : 160,
							align : 'center'
						}, {
							field : 'positiveOrNegative',
							title : '收支',
							width : 160,
							align : 'center'
						} ] ]
					}
				}
			}, {
				field : 'vcSNote',
				title : '項目',
				width : 260,
				sortable : true,
				align : 'center',
				editor : {
					type : 'combobox',
					options : {
						valueField : 'vcNote',
						textField : 'vcNote',
						url : '${pageContext.request.contextPath}/spendingAction_spendList.zzw',
						required : true
					}
				}
			}, {
				field : 'vcSSNote',
				title : '性質',
				width : 260,
				sortable : true,
				align : 'center',
				editor : {
					type : 'combobox',
					options : {
						valueField : 'vcNote',
						textField : 'vcNote',
						url : '${pageContext.request.contextPath}/spendingAction_subSpendList.zzw'
					}
				}
			} ] ],
			toolbar : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					if (currentChoseRow != undefined) {
						spendsubspendingList_datagrid.datagrid('endEdit', currentChoseRow);
					} else {
						spendsubspendingList_datagrid.datagrid('insertRow', {
							index : 0,
							row : {}
						});
						spendsubspendingList_datagrid.datagrid('beginEdit', 0);
						currentChoseRow = 0;
					}
				}
			}, '-', {
				text : '刪除',
				iconCls : 'icon-remove',
				handler : function() {
					var rows = spendsubspendingList_datagrid.datagrid('getSelections');
					if (rows.length > 0) {
						$.messager.confirm('請確認', '確定要刪除所選的記錄？', function(y) {
							if (y) {
								var rids = [];
								var sids = [];
								var ssids = [];
								for ( var i = 0; i < rows.length; i++) {
									if (rows[i].nmSubSpendId == -1) {
										if (rows[i].nmSpendId == -1) {
											rids.push(rows[i].chReveExpenId);
										} else
											sids.push(rows[i].nmSpendId);
									} else {
										ssids.push(rows[i].nmSubSpendId);
									}
								}
								$.ajax({
									url : '${pageContext.request.contextPath}/spendingAction_delRSSS.zzw',
									data : {
										'tr#chReveExpenId_A|I_IN' : rids.join(','),
										'ts#nmSpendId_A|L_IN' : sids.join(','),
										'tss#nmSubSpendId_A|L_IN' : ssids.join(','),
									},
									dataType : 'json',
									error : function(r) {
										$.messager.alert('提示', r.statusText, 'error');
									},
									success : function(r) {
										if (r && r.success) {
											spendsubspendingList_datagrid.datagrid('load');
											$.messager.show({
												msg : '刪除成功',
												title : '提示'
											});
										} else {
											spendsubspendingList_datagrid.datagrid('rejectChanges');
											$.messager.alert('提示', '刪除失敗', 'error');
										}
										currentChoseRow = undefined;
										spendsubspendingList_datagrid.datagrid('unselectAll');
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
					spendsubspendingList_datagrid.datagrid('endEdit', currentChoseRow);
				}
			}, '-', {
				text : '取消操作',
				iconCls : 'icon-redo',
				handler : function() {
					spendsubspendingList_datagrid.datagrid('rejectChanges');
					spendsubspendingList_datagrid.datagrid('unselectAll');
					currentChoseRow = undefined;
				}
			}, '-' ],
			onAfterEdit : function(rowIndex, rowData, changes) {
				var inserted = spendsubspendingList_datagrid.datagrid('getChanges', 'inserted');
				var updated = spendsubspendingList_datagrid.datagrid('getChanges', 'updated');

				console.info(rowIndex);
				console.info(rowData);
				console.info(changes);

				if (inserted.length < 1 && updated.length < 1) {
					currentChoseRow = undefined;
					spendsubspendingList_datagrid.datagrid('unselectAll');
					return;
				}

				var url = '';
				if (inserted.length > 0) {
					url = '${pageContext.request.contextPath}/spendingAction_addRSSS.zzw';
				}

				if (updated.length > 0) {
					url = '${pageContext.request.contextPath}/spendingAction_editRSSS.zzw';
				}

				$.ajax({
					url : url,
					data : rowData,
					type : "POST",
					dataType : 'json',
					success : function(r) {
						if (r && r.success) {
							spendsubspendingList_datagrid.datagrid('acceptChanges');
							$.messager.show({
								msg : r.msg,
								title : '成功'
							});
						} else {
							spendsubspendingList_datagrid.datagrid('rejectChanges');
							$.messager.alert('錯誤', r.msg, 'error');
						}
						currentChoseRow = undefined;
						spendsubspendingList_datagrid.datagrid('unselectAll');
						spendsubspendingList_datagrid.datagrid('reload');
					}
				});
			},
			onDblClickRow : function(rowIndex, rowData) {
				if (currentChoseRow != undefined) {
					spendsubspendingList_datagrid.datagrid('endEdit', currentChoseRow);
				} else {
					spendsubspendingList_datagrid.datagrid('beginEdit', rowIndex);
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
				// 			},
				// 			onLoadError : function(r) {
				// 				$.messager.show({
				// 					msg : r.statusText + ' ' + r.status,
				// 					title : '錯誤',
				// 					timeout : 0
				// 				});
			}
		});
	});
</script>
<table id="spendsubspendingList_datagrid"></table>
