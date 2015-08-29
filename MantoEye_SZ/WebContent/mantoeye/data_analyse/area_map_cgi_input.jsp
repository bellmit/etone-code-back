<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>关联CGI</title>
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
			$("#vcCgiName").focus();
			//为beanForm注册validate函数
			$("#beanForm").validate({
				rules : {
					"vcCgi" :"required"
				},messages: {
					
				} 
			});
			var obj = window.dialogArguments;
			var  nmAreaId= obj.nmAreaId;
			$('#nmAreaId').attr("value",nmAreaId)
		});
</script>
		<base target="_self" />
	</head>
	<body style="overflow-x: hidden">
		<form name="beanView" action="/areaMapCell_saveAreaMapCgi.do?intType=2" id="beanForm"
			method="post">
						<table id="maincontent" cellspacing="0" cellpadding="0" border="0"
				width="100%">
				<tr>
					<td>
						<table cellspacing="0" cellpadding="0" border="0" width="100%"
							class="header">
							<tr>
								<td width="100%" height="24px">
									<div class="title">
										添加关联CGI
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
										小区名：
									</th>
									<td>
										<input type="text"  style="width: 293px;"
											name="vcCgiName" id="vcCgiName"/>
									</td>
								</tr>
								
								<tr>
									<th>
										CGI：
										<font color="red">*</font>
									</th>
									<td>
										<input type="text"  style="width: 293px;"
											name="vcCGI" id="vcCGI"/>
									</td>
								</tr>
								<input type="hidden" name="nmAreaId" id="nmAreaId"/>
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
