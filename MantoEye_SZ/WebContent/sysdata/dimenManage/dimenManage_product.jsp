<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
 	<%@ include file="/include/session.jsp"%> 
 	<%@ include file="/include/setcache.jsp"%> 
	<head>
		<title>产品维度管理</title>
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
	$("#addDimenForm").ajaxForm( {
		type : "POST",
		url : "/sysdata/dimenManage/dimen-manage!saveProductDimen.do",
		success : function() {
			$("#dimen_grid").flexReload();
			$.unblockUI();
		}
	});
	$("#addDefForm").ajaxForm( {
		type : "POST",
		url : "/sysdata/dimenManage/dimen-manage!saveProductDef.do",
		success : function(msg) {
			//$("#dimen_grid").flexReload();
			$.unblockUI();
			
			var ip;
			if($("#port").val() == 0){
				ip = $("#serverIp").val();
			}else{
				ip = $("#serverIp").val() + ":" + $("#port").val();
			}
			var url = $("#url").val();
			if(msg > 0){
				var tr = "<tr style='display: table-row;'>" + 
					"<td align='center'><div style='text-align: center; width: 30px;'>&nbsp;</div></td>" + 
					"<td><div style=​'width:​ 150px;'>&nbsp;​​</div></td>" + 
					"<td><div style=​'width:​ 150px;'>​​&nbsp;</div></td>" + 
					"<td><div style=​'width:​ 200px;'>​​</div></td>" + 
					"<td><div style=​'width:​ 200px;'>​" + ip + "​</div></td>" + 
					"<td><div style='width: 200px;'>" + url + "</div></td>" + 
					"<td><div style='text-align: center; width: 60px;'>" + 
					"<a href='javascript:editDef(" + msg + "," + i + "," + j + ")'>修改</a>&nbsp;&nbsp;&nbsp;" + 
					"<a href='javascript:delDef(" + msg + "," + i+ "," + j + ")'>删除</a></div></td></tr>";
				if(node.nextAll(".hasChildren").size() == 0){
					node.nextAll().last().after(tr);
				}else{
					node.nextAll(".hasChildren").first().before(tr);
				}
			}else{
				$("#row"+i).nextAll().eq(j).html("<td align='center'>" + 
					"<div style='text-align: center; width: 30px;'>&nbsp;</div></td>" + 
					"<td><div style=​'width:​ 150px;'>&nbsp;​​</div></td>" + 
					"<td><div style=​'width:​ 150px;'>​​&nbsp;</div></td>" + 
					"<td><div style=​'width:​ 200px;'>​​</div></td>" + 
					"<td><div style=​'width:​ 200px;'>​" + ip + "​</div></td>" + 
					"<td><div style='width: 200px;'>" + url + "</div></td>" + 
					"<td><div style='text-align: center; width: 60px;'>" + 
					"<a href='javascript:editDef(" + id + "," + i + "," + j + ")'>修改</a>&nbsp;&nbsp;&nbsp;" + 
					"<a href='javascript:delDef(" + id + "," + i+ "," + j + ")'>删除</a></div></td>");
			}
			
		}
	});
	$("#saveDimen").bind("click", function() {
		if ($("#dimensName").val() == "") {
			alert("产品维度不能为空");
		} else {
			$("#addDimenForm").submit();
		}
	});
	$("#cancleDimen").bind("click", function() {
		$.unblockUI();
	});
	$("#saveDef").bind("click", function() {
		if ($("#serverIp").val() == "" && $("#url").val() == "") {
			alert("规则不能同时为空");
		} else {
			var port = $("#port").val();
			if (port == "" || /\D/.test(port)) {
				$("#port").val("0");
			}
			$("#addDefForm").submit();
		}
	});
	$("#cancleDef").bind("click", function() {
		$.unblockUI();
	});
	productGrid();
});
</script>

		<script type="text/javascript">
var node;
var id;
var i;
var j;
function showDimen(){
	$.blockUI({ 
		message: $('#addDimen') ,
		css:{
		   top:'20%',
		   cursor:""
		}
	}); 
}
function showDef(){
	$.blockUI({ 
		message: $('#addDef') ,
		css:{
		   top:'20%',
		   cursor:""
		}
	}); 
}
function productGrid() {
	gridConfig = {
		method : 'POST',
		url : '/sysdata/dimenManage/dimen-manage!queryProductDimen.do',
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
		}, {
			display : 'IP规则',
			name : 'ip',
			width : 200
		}, {
			display : 'URL规则',
			name : 'url',
			width : 200
		}, {
			display : '操作',
			name : 'operate',
			width : 60,
			align : 'center'
		} ],
		buttons : [ {
			name : '新增维度',
			bclass : 'new',
			onpress : addDimen
		}, {
			name : '修改维度',
			bclass : 'edit',
			onpress : editDimen
		}, {
			name : '删除维度',
			bclass : 'delete',
			onpress : delDimen
		}
// 		, {
// 			name : '导入',
// 			bclass : 'view',
// 			onpress : importView
// 		} 
		
		],
		searchitems : [ {
			display: '产品维度', name : 'dimensName', isdefault: true
		} ],
		usepager : true,
		useRp : true,
		resizable : false,
		autoload : true,
		singleSelect : true,
		rp : 10,
		height : 470,
		width : 1100
	}
	$("#dimen_grid").flexigrid(gridConfig);
}
function addDimen() {
	$.ajax( {
		type : "POST",
		url : "/sysdata/dimenManage/dimen-manage!addProductDimenAjax.do",
		dataType : "json",
		async : false,
		success : function(msg) {
			$("#preDimens").html("<option value='0'>&nbsp;</option>");
			$(msg).each(
					function() {
						$("#preDimens").append(
								"<option value='" + this.dimensId + "'>"
										+ this.dimensName + "</option>");
					});
			$("#preDimens").children().first().attr("selected", true);
			$("#dimensId").val("0");
			$("#dimensName").val("");
			$("#dimensDesc").val("");
			showDimen();
		}
	});
}
function editDimen() {
	var checkbox = $("#dimen_grid").find("input[name=checkbox]:checked");
	var checkeds = checkbox.size();
	if (checkeds == 1) {
		var dimensId = $("input[name=checkbox]:checked").attr("id");
		$
				.ajax( {
					type : "POST",
					url : "/sysdata/dimenManage/dimen-manage!editProductDimen.do",
					data : "dimensId=" + dimensId,
					dataType : "json",
					success : function(msg) {
						$
								.ajax( {
									type : "POST",
									url : "/sysdata/dimenManage/dimen-manage!addProductDimenAjax.do",
									dataType : "json",
									async : false,
									success : function(msg) {
										$("#preDimens")
												.html(
														"<option value='0'>&nbsp;</option>");
										$(msg)
												.each(
														function() {
															$("#preDimens")
																	.append(
																			"<option value='"
																					+ this.dimensId
																					+ "'>"
																					+ this.dimensName
																					+ "</option>");
														});
									}
								});
						$("#dimensId").val(msg.dimensId);
						$("#dimensName").val(msg.dimensName);
						$("#preDimens").val(msg.preDimens);
						$("#dimensDesc").val(msg.dimensDesc);
						showDimen();
					}
				});
	} else {
		alert("请选择一个维度");
	}
}
function delDimen() {
	var checkbox = $("#dimen_grid").find("input[name=checkbox]:checked");
	var checkeds = checkbox.size();
	if (checkeds > 0) {
		if (window.confirm("确定删除？")) {
			var dimensIds = "";
			checkbox.each(function() {
				dimensIds += $(this).attr("id") + ",";
			});
			$
					.ajax( {
						type : "POST",
						url : "/sysdata/dimenManage/dimen-manage!deleteProductDimen.do",
						data : "dimensIds=" + dimensIds,
						success : function() {
							alert("删除维度成功");
							$("#dimen_grid").flexReload();
						}
					});
		}
	} else {
		alert("请至少选择一个维度");
	}
}
function addDef(dimId,index) {
	node=$("#row"+index);
	i=index;
	j=$("#row"+index).nextUntil(".hasChildren").size();
	$("#recordId").val("0");
	$("#dimensId2").val(dimId);
	$("#serverIp").val("");
	$("#port").val("");
	$("#url").val("");
	showDef();
}
function editDef(defId,index,num) {
	id=defId;
	i=index;
	j=num;
	$.ajax( {
		type : "POST",
		url : "/sysdata/dimenManage/dimen-manage!editProductDef.do",
		data : "recordId=" + defId,
		dataType : "json",
		success : function(msg) {
			$("#recordId").val(defId);
			$("#dimensId2").val(msg.dimensId);
			$("#url").val(msg.url);
			$("#serverIp").val(msg.serverIp);
			if (msg.serverIp == "" || msg.port == 0) {
				$("#port").val("");
			} else {
				$("#port").val(msg.port);
			}
			showDef();
		}
	});
}
function delDef(defId,index,num) {
	if (window.confirm("确定删除？")) {
		$.ajax( {
			type : "POST",
			url : "/sysdata/dimenManage/dimen-manage!deleteProductDef.do",
			data : "recordId=" + defId,
			success : function() {
				alert("删除规则成功");
				//$("#dimen_grid").flexReload();
				
				$("#row"+index).nextAll().eq(num).remove();
				
			}
		});
	}
}

function importView(){

	$.blockUI({ 
		message: $('#import_view') ,
		 css:{
			 top:'20%',
			 cursor:""
		 }
		
	}); 
}
function closeImportView(){

	$.unBlockUI();
}



</script>

	</head>
	<body style="overflow: hidden">

		<div id="addDimen" style="display:none">
			<form id="addDimenForm" name="addDimenForm" method="post" action="">
				<table border="0" cellpadding="0" cellspacing="0"
					style="width: 90%; height: 90%" align="center">
					<tr>
						<td colspan="2">
							<input id="dimensId" name="dimensId" type="hidden" />&nbsp;
						</td>
					</tr>
					<tr>
						<td align="center">
							产品维度：
						</td>
						<td align="center">
							<input id="dimensName" name="product.dimensName" type="text" />
						</td>
					</tr>
					<tr>
						<td align="center">
							前置维度：
						</td>
						<td align="center">
							<select id="preDimens" name="product.preDimens"
								style="width: 155px; height: 20px"></select>
						</td>
					</tr>
					<tr>
						<td align="center">
							维度描述：
						</td>
						<td align="center">
							<textarea id="dimensDesc" name="product.dimensDesc" cols="17"
								rows="3" style="resize:none"></textarea>
						</td>
					</tr>
					<tr>
						<td colspan="2">&nbsp;
							
						</td>
					</tr>
					<tr>
						<td align="right">
							<input id="saveDimen" type="button" value="确定"
								style="width: 80px" />
						</td>
						<td align="center">
							<input id="cancleDimen" type="button" value="取消"
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

		<div id="addDef" style="display:none">
			<form id="addDefForm" name="addDefForm" method="post" action="">
				<table border="0" cellpadding="0" cellspacing="0"
					style="width: 90%; height: 90%" align="center">
					<tr>
						<td colspan="2">
							<input id="recordId" name="recordId" type="hidden" />
							<input id="dimensId2" name="product.dimensId" type="hidden" />&nbsp;
						</td>
					</tr>
					<tr>
						<td align="center">
							IP规则：
						</td>
						<td align="center">
							<input id="serverIp" name="product.serverIp" type="text" />
						</td>
					</tr>
					<tr>
						<td align="center">
							IP端口：
						</td>
						<td align="center">
							<input id="port" name="product.port" type="text" />
						</td>
					</tr>
					<tr>
						<td align="center">
							URL规则：
						</td>
						<td align="center">
							<input id="url" name="product.url" type="text" />
						</td>
					</tr>
					<tr>
						<td colspan="2">&nbsp;
							
						</td>
					</tr>
					<tr>
						<td align="right">
							<input id="saveDef" type="button" value="确定" style="width: 80px" />
						</td>
						<td align="center">
							<input id="cancleDef" type="button" value="取消"
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
				<td>
					<table id="dimen_grid" style="display: none"></table>
				</td>
			</tr>
		</table>
		
		<div id="import_view" style="display:none ;heigth:50px "> 
			<div>
				<label>请选择导入文件</label>
				<form id="importForm" action="/sysdata/dimenManage/dimen-manage!importProductDimen.do" method="post" enctype="multipart/form-data">
<!-- 					<s:file name="myFile"  label="File" /> -->
<input type="file" name="myFile" value="" id="myFile">
					<br/>	
					<input type="submit" value="确定" />
					<input type="button" value="取消" onclick="closeImportView();"/>
				</form>
			</div>
		</div>
	</body>
</html>
