<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>编辑字段信息</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/modedialog.css" />
		<script src="/js/paging.js"></script>
		<script src="/js/nav.js"></script>
		<script language="javascript" src="/js/script.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
		<script type="text/javascript">
	//验证表单信息
		$(document).ready(function(){
			//聚焦第一个输入框
			$("#loginName").focus();
			//为beanForm注册validate函数
			$("#beanForm").validate({
				rules: { 
					"entity.vcColumnName": { 
        				required: true, 
        				remote: "tableColumnMap_checkUnique.do?oldName="+encodeURIComponent('${entity.vcTableName}')
    			},
          		"entity.vcTableNickName": "required"
				},
				messages: {
					"entity.vcTableName": {
						remote: "字段对应信息已经存在"
					}
				}
			});
		});
</script>
		<style>
</style>
		<base target="_self" />
	</head>
	<body style="overflow-x: hidden" onload="getAllTable()">
		<form name="beanView" action="/tableColumnMap_save.do" id="beanForm"
			method="post">
			<table id="maincontent" cellspacing="0" cellpadding="0" border="0"
				width="100%" height="100%">
				<tr>
					<td>
						<table cellspacing="0" cellpadding="0" border="0" width="100%"
							class="header">
							<tr>
								<td width="100%" height="24px">
									<div class="title">
										编辑字段信息
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td width="100%">
						<div class="inputmain">
							<!----------- 表单信息 ------------------>
							<table width="9%" cellpadding="0" cellspacing="1"
								class="formitem">
								<tr>
									<th>
										表名：
										<font color="red">*</font>
									</th>

									<td>
										<select name="tableId" id="tableId">
										</select>
										<input type="hidden" name="entity.nmTableColumnMapId" value="${entity.nmTableColumnMapId }" />
										<input type="hidden" name="htableId" value="htableId" />
									</td>
								</tr>
								<tr>
									<th>
										字段名：
										<font color="red">*</font>
									</th>

									<td>
										<input type="text" name="entity.vcColumnName" id="vcColumnName" value="${ entity.vcColumnName}"/>
									</td>
								</tr>
								<tr>
									<th>
										 字段别名：
										<font color="red">*</font>
									</th>
									<td>
										<input type="text" name="entity.vcColumnNickName" id="vcColumnNickName" value="${entity.vcColumnNickName }"/>
									</td>
								</tr>
								<tr>
									<th>
										字段类型：
										<font color="red">*</font>
									</th>
									<td>
										<select name="entity.intColumnType" id="intColumnType"
															style="height: 20px; width: 125px">
											<option value="1" <c:if test="${entity.intColumnType==1}">selected</c:if> >
																数字型
															</option>
															<option value="2" <c:if test="${entity.intColumnType==2}">selected</c:if> >
																字符型
															</option>
															<option value="3" <c:if test="${entity.intColumnType==3}">selected</c:if>>
																日期型
															</option>
										</select>
									</td>
								</tr>
								
							</table>
						</div>
					</td>
					<div style="display: none;">
						<s:submit id="btn_commit"></s:submit>
						<button id="btn_cancel" onclick="window.close()"></button>
					</div>
				</tr>
			</table>
			<table width="66%" cellpadding="0" cellspacing="0"
				class="formitem_pagestyle" style="padding-top: 40px;">
				<tr>
					<td colspan="2">
						<table align="right">
							<tr>
								<td align="center">
									<div id="mainbtn">
										<ul>
											<li>
												<a href="#"
													onclick="document.getElementById('btn_commit').click();"><span>保存</span>
												</a>
											</li>
											<li>
												<a href="#"
													onclick="document.getElementById('btn_cancel').click();"><span>关闭</span>
												</a>
											</li>
										</ul>
									</div>
								</td>
								<td width="10px"></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
<script>

//获取表信息数据
function getAllTable(){
	$.ajax({
		type : "post",
		url : "/tableMap_getAllTable.do",
		dataType: 'json',
		success : function(data) {
			var jsn = eval(data);
			var jn = jsn.datalist;
			var htableId = document.getElementById("htableId").value;
			 $("#tableId").empty()
			var inhtml = "";
			for(var i=0;i<jn.length;i++){
				inhtml = inhtml + '<option value ="'+jn[i].id+'"  >'+jn[i].name+'</option>'
			}
			 $(inhtml).appendTo("#tableId");//添加下拉框的option
			 $("#tableId").attr("value",htableId);
		},
		error : function() {
			alert('获取表信息数据失败!');
		}
	});
}
</script>

