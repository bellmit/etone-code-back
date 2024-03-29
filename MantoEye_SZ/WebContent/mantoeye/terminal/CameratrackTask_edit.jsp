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
		<script src="/js/paging.js"></script>
		<script src="/js/nav.js"></script>
		<script language="javascript" src="/js/script.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
			<script type="text/javascript" src="/js/common_grid.js">
		<!-- 自动补全JS --> 
		</script><script type="text/javascript" src="/js/common.js"></script>
		<script type="text/javascript">
	$(document).ready( function() {
			$("#entity.vcTaskName").focus();
		//	var obj = window.dialogArguments;
		//	$('#OldTaskType').attr('value',obj.intTaskType);	
			$("#beanForm").validate( {
				rules : {
					"entity.vcTaskName" :"required",
					"entity.dtStartTime" :"required",
					"entity.dtEndTime" :"required",
					"entity.bitTaskActiveFlag" :"required"
				},
				submitHandler: function(form) {   
					var ml = (parseInt(window.dialogWidth)-250)/2;
					var mt = (parseInt(window.dialogHeight)-55)/2;
					$('#showmsg').css({"margin-top":0,"margin-left":ml,"display":"block"});
					$('#saveBtn').attr('disabled','disabled');	
					$('#cancelBtn').attr('disabled','disabled');	
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
		<form name="beanView" action="/cameratrack_saveDataOutput.do"
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
											value="${entity.vcTaskName}" readonly />
										<input type="hidden" name="entity.nmDataOutPutTaskId"
											id="entity.nmDataOutPutTaskId"
											value="${entity.nmDataOutPutTaskId}" />
										<input type="hidden" name="entity.intTaskDelong"
											id="entity.intTaskDelong" value="${entity.intTaskDelong}" />
									
										<input type="hidden" name="entity.vcTaskOrder"
											id="entity.vcTaskOrder" value="${entity.vcTaskOrder}" />
										<input type="hidden" name="entity.dtOrderTime"
											id="entity.dtOrderTime" value="${entity.dtOrderTime}" />
										<input type="hidden" name="entity.intTaskStatus"
											id="entity.intTaskStatus" value="${entity.intTaskStatus}" />
											
											<input type="hidden" name="oldTaskType" id="oldTaskType" value="${entity.intTaskType}" /> 
									</td>
								</tr>
									<tr>
									<th>
										任务类型：
										<font color="red">*</font>
									</th>
									<td>
										<select name="entity.intTaskType" id="intTaskType"
											style="width: 356px;">
											<option value="1" <c:if test="${entity.intTaskType==1}">selected</c:if>>
											通用拍照指定号码
											</option>
											<option value="2"<c:if test="${entity.intTaskType==2}">selected</c:if>>
												通用拍照指定终端
											</option>
											<option value="4"<c:if test="${entity.intTaskType==4}">selected</c:if>>
												通用拍照指定区域
											</option>
											<option value="5"<c:if test="${entity.intTaskType==5}">selected</c:if>>
												通用拍照指定业务
											</option>
											<option value="6"<c:if test="${entity.intTaskType==6}">selected</c:if>>
											通用拍照指定APN
											</option>
											<!--<option value="7"<c:if test="${entity.intTaskType==7}">selected</c:if>>
										通用拍照用户归属
											</option>-->
										</select>
									</td>
								</tr>
								
								
								
								
								<tr>
									<th>
										开始时间：
										<font color="red">*</font>
									</th>
									<td>
										<input type="text" class="Wdate" style="width: 352px;"
											readonly
											onclick="showStartForm()"
											name="entity.dtStartTime" id="entity.dtStartTime"
											value="${entity.dtStartTime}" />
									</td>
								</tr>
								<tr>
									<th>
										结束时间：
										<font color="red">*</font>
									</th>
									<td>
										<input type="text" class="Wdate" style="width: 352px;"
											readonly
											onclick="showEndForm()"
											name="entity.dtEndTime" id="entity.dtEndTime"
											value="${entity.dtEndTime}" />
									</td>
								</tr>
								<tr>
									<th>
										是否激活：
										<font color="red">*</font>
									</th>
									<td>
										<select name="entity.bitTaskActiveFlag" id="bitTaskActiveFlag" disabled="disabled"
											style="width: 356px;">
											<option value="true"
												>
												是
											</option>
											<option value="false"
												selected>
												否
											</option>
										</select>
									</td>
								</tr>
								<tr>
									<th>
										提示：
										
									</th>
									<td>
										<font color="red">请添加任务条件，再激活任务！</font>
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
			<div class="dialog_msg_class" style="margin-top:120px;margin-left:70px;" id="showmsg" name="showmsg" style="display:none">
										<img src="/skin/Default/images/icon/16/loading.gif">
										</img>操作进行中,请稍后...</div>
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
													onclick="document.getElementById('btn_commit').click();"  id="saveBtn" name="saveBtn"><span>保存</span>
												</a>
											</li>
											<li>
												<a href="#"
													onclick="document.getElementById('btn_cancel').click();"  id="cancelBtn" name="cancelBtn"><span>关闭</span>
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
function showStartForm(){
		var eed = document.getElementById('entity.dtEndTime').value;
		if(eed==""){
			return new WdatePicker({minDate:'%y-%M-{%d-30} 00:00:00',maxDate:'%y-%M-%d %H:%m:00',dateFmt:'yyyy-MM-dd HH:mm:00'});
		}else{
			return  new WdatePicker({minDate:'#F{$dp.$D(\'entity.dtEndTime\',{d:-7})||\'%y-%M-{%d-30} 00:00:00\'}',maxDate:'#F{$dp.$D(\'entity.dtEndTime\')||\'%y-%M-%d %H:%m:%s\'}',dateFmt:'yyyy-MM-dd HH:mm:00'});
		}
}
function showEndForm(){
		var ssd = document.getElementById('entity.dtStartTime').value;
		if(ssd==""){
			return new WdatePicker({minDate:'%y-%M-{%d-30} 00:00:00',maxDate:'%y-%M-%d %H:%m:00',dateFmt:'yyyy-MM-dd HH:mm:00'});			
		}else{
			return new WdatePicker({minDate:'#F{$dp.$D(\'entity.dtStartTime\')||\'%y-%M-{%d-30} 00:00:00\'}',maxDate:'#F{$dp.$D(\'entity.dtStartTime\',{d:7})||\'%y-%M-%d %H:%m:%s\'}',dateFmt:'yyyy-MM-dd HH:mm:00'});
		}
}
/**
脚本不出错
*/
$(document).ready(function(){
	window.onerror = killErrors;
})
function killErrors() {
	return true;
}


</script>