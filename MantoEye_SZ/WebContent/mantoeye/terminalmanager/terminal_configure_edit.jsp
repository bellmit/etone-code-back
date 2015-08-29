<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>编辑终端配置</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/modedialog.css" />
		<script src="/js/paging.js"></script>
		<script src="/js/nav.js"></script>
		<script language="javascript" src="/js/script.js"></script>
		<script type="text/javascript">
	$(document).ready(function(){
			//聚焦第一个输入框
			$("#vcTerminalTac").focus();
			var obj = window.dialogArguments;
			$('#vcTerminalTac').attr('value',obj.vcTerminalTac);
			$('#vcTerminalBrand').attr('value',obj.vcTerminalBrand);	
			$('#vcTerminalName').attr('value',obj.vcTerminalName);
			$('#nmTerminalId').attr('value',obj.nmTerminalId);
			//为beanForm注册validate函数
			$("#beanForm").validate({
				rules : {
					"vcTerminalTac" :{
						required: true,
						remote:{
        					url:"terminalConfigure_checkUnique.do",
        					type:"post" ,
        					data:{"vcTerminalTac":function (){return $('#vcTerminalTac').attr('value')},
        							"nmTerminalId":obj.nmTerminalId
        					}
        					}
					},
					"vcTerminalBrand" :"required",
					"vcTerminalName" :"required"
				},
				messages: {
					"vcTerminalTac": {
						remote: "终端识别前缀已存在"
					}
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
		<base target="_self" />
	</head>
	<body style="overflow-x: hidden">
		<form name="beanView" action="/terminalConfigure_updateTerminal.do" id="beanForm"
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
										编辑终端配置
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
										终端识别前缀：
										<font color="red">*</font>
									</th>
									<td>
										<input type="text" name="vcTerminalTac" id="vcTerminalTac"/>
									</td>
								</tr>
								<tr>
									<th>
										终端品牌：
										<font color="red">*</font>
									</th>
									<td>
										<input type="text" name="vcTerminalBrand" id="vcTerminalBrand"/>
									</td>
								</tr>
								<tr>
									<th>
										终端型号：
										<font color="red">*</font>
									</th>
									<td>
										<input type="text" name="vcTerminalName" id="vcTerminalName"/>
									</td>
								</tr>
							<input type="hidden" name="nmTerminalId" id="nmTerminalId"/>
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
