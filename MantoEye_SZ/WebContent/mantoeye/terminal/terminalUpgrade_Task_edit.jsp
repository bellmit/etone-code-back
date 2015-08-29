<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>编辑任务</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/modedialog.css" />
		<script src="/js/paging.js">
</script>
		<script src="/js/nav.js">
</script>
		<script language="javascript" src="/js/script.js">
</script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js">
</script>
		<script type="text/javascript" src="/js/common_grid.js">
</script>
		<!-- 导入业务选择复选框 -->
		<link rel="stylesheet" href="/mantoeye/dialog/dialog.css" />
		<link rel="stylesheet" href="/tools/jquery/jquery.treeview.css" />
		<script type="text/javascript" src="/mantoeye/dialog/SymbolDialog.js">
</script>
		<script type="text/javascript" src="/tools/jquery/jquery.treeview.js">
</script>

		<!-- 自动补全JS -->
		<script type="text/javascript" src="/js/common.js">
</script>
		<script type="text/javascript">
//验证表单信息
$(document).ready( function() {
		$("#entity.vcTaskName").focus();
		//为beanForm注册validate函数
		$("#beanForm").validate(
				{
					rules : {
						"entity.vcTaskName" : {
							required : true,
							remote : {
								url : "terminalupgradetask_checkUnique.do?oldTaskName="
										+ $('#oldTaskName').attr('value'),
								type : "post",
								data : {
									"vcTaskName" : function() {
										return $('#vcTaskName').attr('value')
									}
								}
							}
						},
						"entity.dtTaskStartTime" : "required",
					//	"entity.dtTaskEndTime" : "required",
						"entity.bitTaskActiveFlag" : "required"
					},
					messages : {
						"entity.vcTaskName" : {
							remote : "任务名称已存在"
						}
					},
					submitHandler : function(form) {
						//alert(form);
					var ml = (parseInt(window.dialogWidth) - 250) / 2;
					var mt = (parseInt(window.dialogHeight) - 55) / 2;
					$('#showmsg').css( {
						"margin-top" : 0,
						"margin-left" : ml,
						"display" : "block"
					});
					$('#saveBtn').attr('disabled', 'disabled');
					$('#cancelBtn').attr('disabled', 'disabled');
					form.submit();
				}
				});
	});
</script>
		<style>
#content_table_id  th {
	height: 22px;
	padding-left: 5px;
	width: 24%;
	text-align: right;
	font: normal 13px arial, tahoma, helvetica, sans-serif;
}
</style>
		<base target="_self" />
	</head>
	<body style="overflow-x: hidden">
		<form name="beanView" action="/terminalupgradetask_saveTask.do"
			id="beanForm" method="post">
			<table id="maincontent" cellspacing="0" cellpadding="0" border="0"
				width="100%" height="100%">
				<tr>
					<td>
						<table cellspacing="0" cellpadding="0" border="0" width="100%"
							class="header">
							<tr>
								<td width="100%" height="24px">
									<div class="title">
										编辑任务
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td width="100%">
						<div>
							<!----------- 表单信息 ------------------>
							<table width="100%" cellpadding="0" cellspacing="1"
								id="content_table_id"
								style="width: 100%; height: auto; margin: 0px; padding: 0px;">
								<tr>
									<th>
										任务名称：
										<font color="red">*</font>
									</th>
									<td>
								<input type="text" name="entity.vcTaskName"
											style="width: 350px;" id="entity.vcTaskName"
											value="${entity.vcTaskName}"  />
										<input type="hidden" name="entity.nmTerminalChangeIdTaskId"
											id="entity.nmTerminalChangeIdTaskId"
											value="${entity.nmTerminalChangeIdTaskId}" />
										<input type="hidden" name="oldTaskName" id="oldTaskName"
											value="${entity.vcTaskName}" />
									</td>
								</tr>
								

								<tr>
									<th>
										任务开始时间：
										<font color="red">*</font>
									</th>
									<td>
										<input type="text" name="entity.dtTaskStartTime" class="Wdate"
											style="width: 350px;" id="entity.dtTaskEndTime" readonly
											onclick="showStartForm()" value="${entity.dtTaskStartTime}" />
									</td>
								</tr>
							<!-- 	<tr>
									<th>
										结束时间：
										<font color="red">*</font>
									</th>
									<td> -->
										<input type="hidden" name="entity.dtTaskEndTime" class="Wdate"
											style="width: 350px;" id="entity.dtTaskEndTime" readonly
											 value="${entity.dtTaskEndTime}" />
								<!-- 	</td> -->
								</tr>
								
								<tr>
									<th>
										是否激活：
										<font color="red">*</font>
									</th>
									<td>
										<select name="entity.bitTaskActiveFlag" id="bitTaskActiveFlag"
											style="width: 356px;">
											<option value="true"
												<c:if test="${entity.bitTaskActiveFlag==true}">selected</c:if>>
												是
											</option>
											<option value="false"
												<c:if test="${entity.bitTaskActiveFlag==false}">selected</c:if>>
												否
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
			<div class="dialog_msg_class"
				style="margin-top: 120px; margin-left: 70px;" id="showmsg"
				name="showmsg" style="display:none">
				<img src="/skin/Default/images/icon/16/loading.gif">
				</img>
				操作进行中,请稍后...
			</div>
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
													onclick="document.getElementById('btn_commit').click();"
													id="saveBtn" name="saveBtn"><span>保存</span> </a>
											</li>
											<li>
												<a href="#"
													onclick="document.getElementById('btn_cancel').click();"
													id="cancelBtn" name="cancelBtn"><span>关闭</span> </a>
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
function showStartForm() {

	
		return new WdatePicker({minDate:'%y-%M-%d 00:00:00',maxDate:'%y-%M-{%d+30} %H:%m:00',dateFmt:'yyyy-MM-dd HH:mm:00'});
	
}
function showEndForm() {
	var ssd = document.getElementById('entity.dtTaskStartTime').value;
	if (ssd == "") {
		return new WdatePicker( {
			minDate : '%y-%M-%d',
			maxDate : '%y-%M-{%d+30}',
			dateFmt : 'yyyy-MM-dd'
		});
	} else {
		return new WdatePicker(
				{
					minDate : '#F{$dp.$D(\'entity.dtTaskStartTime\')||\'%y-%M-{%d-30}\'}',
					maxDate : '#F{$dp.$D(\'entity.dtTaskStartTime\',{d:7})||\'%y-%M-%d\'}',
					dateFmt : 'yyyy-MM-dd'
				});
	} 
}

/**
 脚本不出错
 */
$(document).ready(function() {
	window.onerror = killErrors;
})
function killErrors() {
	return true;
}
</script>