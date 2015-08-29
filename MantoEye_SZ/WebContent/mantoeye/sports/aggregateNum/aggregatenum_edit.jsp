<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>编辑模块</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/modedialog.css" />
		<script src="/js/paging.js"></script>
		<script src="/js/nav.js"></script>
		<script language="javascript" src="/js/script.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
	<script type="text/javascript">
$(document).ready( function() {
		//聚焦第一个输入框
			$("#vcMsisdns").focus();
			var obj = window.dialogArguments;
			$('#nmMsisdn').attr('value',obj.value);	
				$('#OldnmMsisdn').attr('value',obj.value);	
			$('#CustomerName').attr('value',obj.CustomerName);	
			$('#id').attr('value',obj.id);	
			//为beanForm注册validate函数
			$("#beanForm").validate({
				rules : {
				
					"nmMsisdn": {
						required: true,
						mobil:true,
						maxlength:11
    				}
				},messages: {
					"nmMsisdn": {
					maxlength: "号码最多为1个"
					}
				},
				submitHandler: function(form) { 
					//alert(form.action);  
					var ml = (parseInt(window.dialogWidth)-250)/2;
					var mt = (parseInt(window.dialogHeight)-55)/2;
					$('#showmsg').css({"margin-top":0,"margin-left":ml,"display":"block"});
					$('#saveBtn').attr('disabled','disabled');	
					$('#cancelBtn').attr('disabled','disabled');	
					//alert($('#beanForm').attr('action'));
					form.submit();  
				}  
				   
			});
		});
</script>
			<base target="_self" />
	</head>
	<body style="overflow-x: hidden">
		<form name="beanView" action="/aggregatenum_saveModify.do" id="beanForm"
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
										编辑号码
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
										集团名称：
										<font color="red">*</font>
									</th>
									<td>
									<input type="text" name="CustomerName" id="CustomerName"
															style="height: 25px; width: 290px" />
								 	<input type="hidden" name="id" id="id" /> 
									</td>
								</tr>
								<tr>
									<th>
										集团号码：
										<font color="red">*</font>
									</th>
									<td>
									<input type="text" name="nmMsisdn" id="nmMsisdn"
															style="height: 25px; width: 290px" />
															<input type="hidden" name="OldnmMsisdn" id="OldnmMsisdn"
															style="height: 25px; width: 290px" />
									<!-- 	<textarea name="nmMsisdn" id="nmMsisdn" rows="6"
											class="replyTextClass" style="overflow-y: auto;width: 290px;"></textarea> -->
									</td>
								</tr>
									<th></th>
									<td>
										<span><font color="red">注：</font><font color="blue">只能输入一个号码</font>
										</span>
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
