<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>编辑日志</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/modedialog.css" />
		<script src="/js/paging.js"></script>
		<script src="/js/nav.js"></script>
		<script language="javascript" src="/js/script.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
		<script type="text/javascript">
	$(document).ready(function(){
			//聚焦第一个输入框
			$("#vcEventType").focus();
			//为beanForm注册validate函数
			$("#beanForm").validate({
				rules: { 
					"entity.vcEventType":"required",
					"entity.vcEventConetnt":"required"
    			}
			});
		});
</script>
		<base target="_self" />
	</head>
	<body style="overflow-x: hidden">
		<form name="beanView" action="/eventLog_update.do" id="beanForm"
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
										添加事件日志
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
							<table cellpadding="0" cellspacing="1" class="formitem">
								<tr>
									<th>
										事件类型：
										<font color="red">*</font>
									</th>
									<td>
										<input type="text" name="entity.vcEventType" id="vcEventType" value="${entity.vcEventType }"/>
										<input type="hidden" id="entity.id" name="entity.id" value="${entity.id }"/>
										<input type="hidden" id="vcRecorder" name="entity.vcRecorder" value="${entity.vcRecorder }"/>
										<input type="hidden" id="dtRecordTime" name="entity.dtRecordTime" value="${entity.dtRecordTime }"/>
									</td>
								</tr>
								
								<tr>
									<th>
										事件内容：
										<font color="red">*</font>
									</th>
									<td>
										<textarea name="entity.vcEventContent" id="vcEventContent"
											rows="3" cols="48">${ entity.vcEventContent}</textarea>
									</td>
								</tr>
								<tr>
									<th>
										事件影响：
										
									</th>
									<td>
										<textarea name="entity.vcEventAffect" id="vcEventAffect"
											rows="3" cols="48">${ entity.vcEventAffect}</textarea>
									</td>
								</tr>
							</table>
						</div>
						<div style="display: none;">
						<s:submit id="btn_commit"></s:submit>
						<button id="btn_cancel" onclick="window.close()"></button>
						</div>
					</td>
					
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
