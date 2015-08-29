<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%> 
	<%@ include file="/include/setcache.jsp"%> 
	<head>
		<title>产品维度组管理</title>
		<link rel="stylesheet" type="text/css"
			href="/skin/Default/css/maincontent.css" />
		<link rel="stylesheet" href="/css/flexigrid.css" type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="/css/jquery.treeTable.css" />
		<link rel="stylesheet" type="text/css"
			href="/js/jquery/extend.tab/css/Toolbar.css" />

		<script type="text/javascript" src="/js/jquery.js">
</script>
		<script type="text/javascript" src="/js/jquery.ui.js">
</script>
		<script type="text/javascript" src="/js/jquery.form.js">
</script>
		<script type="text/javascript" src="/js/flexigrid.js">
</script>
		<script type="text/javascript" src="/js/jquery.treeTable.js">
</script>
		<script type="text/javascript" src="/js/jquery.blockUI.js">
</script>

		<script type="text/javascript">
$(function() {
	$("#addGroupForm").ajaxForm( {
		type : "POST",
		url : "/sysdata/dimenManage/dimen-manage!saveProductGroup.do",
		success : function() {
			$("#grid").hide();
			$("#group_grid").flexReload();
			$.unblockUI();
		}
	});
	$("#addDimenForm2").ajaxForm( {
		type : "POST",
		url : "/sysdata/dimenManage/dimen-manage!saveProductMap.do",
		success : function() {
			$("#dimen2_grid").flexReload();
			$.unblockUI();
		}
	});
	$("#saveGroup").bind("click", function() {
		if ($("#groupName").val() == "") {
			alert("产品组不能为空");
		} else {
			$("#addGroupForm").submit();
		}
	});
	$("#cancleGroup").bind("click", function() {
		$.unblockUI();
	});
	$("#saveMap").bind("click", function() {
		$("#addDimenForm2").submit();
	});
	$("#cancleMap").bind("click", function() {
		$.unblockUI();
	});
	groupGrid();
	productGrid();
});
</script>

		<script type="text/javascript">
function showGroup(){
	$.blockUI({ 
		message: $('#addGroup') ,
		css:{
		   top:'20%',
		   cursor:""
		}
	}); 
}
function showDimen(){
	$.blockUI({ 
		message: $('#addDimen2') ,
		css:{
		   top:'20%',
		   cursor:""
		}
	}); 
}
function groupGrid() {
	gridConfig = {
		method : 'POST',
		url : '/sysdata/dimenManage/dimen-manage!queryProductGroup.do',
		dataType : 'json',
		colModel : [ {
			display : '选择',
			name : 'checkBox',
			width : 30,
			align : 'center'
		}, {
			display : '产品组',
			name : 'groupName',
			width : 150
		}, {
			display : '操作',
			name : 'operate',
			width : 60,
			align : 'center'
		} ],
		buttons : [ {
			name : '新增组',
			bclass : 'new',
			onpress : addGroup
		}, {
			name : '修改组',
			bclass : 'edit',
			onpress : editGroup
		}, {
			name : '删除组',
			bclass : 'delete',
			onpress : delGroup
		} ],
		resizable : false,
		autoload : true,
		singleSelect : true,
		height : 500,
		width : 300
	}
	$("#group_grid").flexigrid(gridConfig);
}
function productGrid() {
	gridConfig = {
		method : 'POST',
		url : '/sysdata/dimenManage/dimen-manage!queryProductDimen2.do',
		dataType : 'json',
		colModel : [ {
			display : '选择',
			name : 'checkBox',
			width : 30,
			align : 'center'
		}, {
			display : '产品维度',
			name : 'dimensName',
			width : 150
		}, {
			display : '前置维度',
			name : 'preDimensName',
			width : 150
		}, {
			display : '维度描述',
			name : 'dimensDesc',
			width : 200
		} ],
		buttons : [ {
			name : '添加维度',
			bclass : 'new',
			onpress : addDimen2
		}, {
			name : '移除维度',
			bclass : 'delete',
			onpress : delDimen2
		} ],
		searchitems : [ {
			display: '产品维度', name : 'dimensName', isdefault: true
		} ],
		usepager : true,
		useRp : true,
		resizable : false,
		autoload : false,
		singleSelect : true,
		rp : 10,
		height : 470,
		width : 600
	}
	$("#dimen2_grid").flexigrid(gridConfig);
}
function productGrid2(groupId) {
	$("#grid").show();
	$("#groupId2").val(groupId);
	$("#dimen2_grid")
			.flexOptions(
					{
						url : '/sysdata/dimenManage/dimen-manage!queryProductDimen2.do?groupId=' + groupId
					});
	$("#dimen2_grid").flexReload();
}
function addGroup() {
	$.ajax( {
		type : "POST",
		url : "/sysdata/dimenManage/dimen-manage!addProductGroupAjax.do",
		dataType : "json",
		async : false,
		success : function(msg) {
			$("#parent").html("<option value='0'>&nbsp;</option>");
			$(msg).each(
					function() {
						$("#parent").append(
								"<option value='" + this.groupId + "'>"
										+ this.groupName + "</option>");
					});
			$("#parent").children().first().attr("selected", true);
			$("#groupId").val("0");
			$("#groupName").val("");
			showGroup();
		}
	});
}
function editGroup() {
	var checkeds = $("input[name=checkbox2]:checked").size();
	if (checkeds == 1) {
		var groupId = $("input[name=checkbox2]:checked").attr("id");
		$
				.ajax( {
					type : "POST",
					url : "/sysdata/dimenManage/dimen-manage!editProductGroup.do",
					data : "groupId=" + groupId,
					dataType : "json",
					success : function(msg) {
						$
								.ajax( {
									type : "POST",
									url : "/sysdata/dimenManage/dimen-manage!addProductGroupAjax.do",
									dataType : "json",
									async : false,
									success : function(msg) {
										$("#parent")
												.html(
														"<option value='0'>&nbsp;</option>");
										$(msg)
												.each(
														function() {
															$("#parent")
																	.append(
																			"<option value='"
																					+ this.groupId
																					+ "'>"
																					+ this.groupName
																					+ "</option>");
														});
									}
								});
						$("#groupId").val(msg.groupId);
						$("#parent").val(msg.parent);
						$("#groupName").val(msg.groupName);
						showGroup();
					}
				});
	} else {
		alert("请选择一个组");
	}
}
function delGroup() {
	var checkeds = $("input[name=checkbox2]:checked").size();
	if (checkeds > 0) {
		if (window.confirm("确定删除？")) {
			var groupIds = "";
			$("input[name=checkbox2]:checked").each(function() {
				groupIds += $(this).attr("id") + ",";
			});
			$
					.ajax( {
						type : "POST",
						url : "/sysdata/dimenManage/dimen-manage!deleteProductGroup.do",
						data : "groupIds=" + groupIds,
						success : function() {
							alert("删除组成功");
							$("#grid").hide();
							$("#group_grid").flexReload();
						}
					});
		}
	} else {
		alert("请至少选择一个组");
	}
}
function addDimen2() {
	var groupId = $("#groupId2").val();
	$.ajax( {
		type : "POST",
		url : "/sysdata/dimenManage/dimen-manage!addProductDimenAjax2.do",
		data : "groupId=" + groupId,
		dataType : "json",
		async : false,
		success : function(msg) {
			$("#dimensId3").empty();
			$(msg).each(
					function() {
						$("#dimensId3").append(
								"<option value='" + this.dimensId + "'>"
										+ this.dimensName + "</option>");
					});
			$("#dimensId3").children().first().attr("selected", true);
			showDimen();
		}
	});
}
function delDimen2() {
	var checkbox = $("#dimen2_grid").find("input[name=checkbox]:checked");
	var checkeds = checkbox.size();
	if (checkeds > 0) {
		if (window.confirm("确定删除？")) {
			var groupId = $("#groupId2").val();
			var dimensIds = "";
			checkbox.each(function() {
				dimensIds += $(this).attr("id") + ",";
			});
			$.ajax( {
				type : "POST",
				url : "/sysdata/dimenManage/dimen-manage!deleteProductMap.do",
				data : "groupId=" + groupId + "&dimensIds=" + dimensIds,
				success : function() {
					alert("移除维度成功");
					$("#dimen2_grid").flexReload();
				}
			});
		}
	} else {
		alert("请至少选择一个维度");
	}
}
</script>

	</head>
	<body style="overflow: hidden">

		<div id="addGroup" style="display:none">
			<form id="addGroupForm" name="addGroupForm" method="post" action="">
				<table border="0" cellpadding="0" cellspacing="0"
					style="width: 90%; height: 90%" align="center">
					<tr>
						<td colspan="2">
							<input id="groupId" name="groupId" type="hidden" />&nbsp;
						</td>
					</tr>
					<tr>
						<td align="center">
							产品组：
						</td>
						<td align="center">
							<input id="groupName" name="productGroup.groupName" type="text" />
						</td>
					</tr>
					<tr>
						<td align="center">
							上级组：
						</td>
						<td align="center">
							<select id="parent" name="productGroup.parent"
								style="width: 155px; height: 20px"></select>
						</td>
					</tr>
					<tr>
						<td colspan="2">&nbsp;
							
						</td>
					</tr>
					<tr>
						<td align="right">
							<input id="saveGroup" type="button" value="确定"
								style="width: 80px" />
						</td>
						<td align="center">
							<input id="cancleGroup" type="button" value="取消"
								style="width: 80px" />
						</td>
					</tr>
                    <tr>
						<td colspan="2">&nbsp;
							
						</td>
					</tr>
				</table>
			</form>
		</div>

		<div id="addDimen2" style="display:none">
			<form id="addDimenForm2" name="addDimenForm2" method="post" action="">
				<table border="0" cellpadding="0" cellspacing="0"
					style="width: 90%; height: 90%" align="center">
					<tr>
						<td colspan="2">
							<input id="groupId2" name="groupId" type="hidden" />&nbsp;
						</td>
					</tr>
					<tr>
						<td align="center">
							产品维度：
						</td>
						<td align="center">
							<select id="dimensId3" name="dimensId"
								style="width: 155px; height: 20px"></select>
						</td>
					</tr>
					<tr>
						<td colspan="2">&nbsp;
							
						</td>
					</tr>
					<tr>
						<td align="right">
							<input id="saveMap" type="button" value="确定" style="width: 80px" />
						</td>
						<td align="center">
							<input id="cancleMap" type="button" value="取消"
								style="width: 80px" />
						</td>
					</tr>
                    <tr>
						<td colspan="2">&nbsp;
							
						</td>
					</tr>
				</table>
			</form>
		</div>

		<table cellspacing="0" cellpadding="0" border="0" width="100%"
			height="100%">
			<tr>
				<td width="25%" valign="top">
					<table cellspacing="0" cellpadding="0" border="0" width="100%"
						height="100%">
						<tr>
							<td>
								<table id="group_grid" style="display: none"></table>
							</td>
						</tr>
					</table>
				</td>
				<td width="5%">&nbsp;
					
				</td>
				<td valign="top">
					<table cellspacing="0" cellpadding="0" border="0" width="100%"
						height="100%">
						<tr>
							<td id="grid" style="display: none">
								<table id="dimen2_grid" style="display: none"></table>
							</td>
						</tr>
					</table>
				</td>
				<td width="10%">&nbsp;
					
				</td>
			</tr>
		</table>
	</body>
</html>
