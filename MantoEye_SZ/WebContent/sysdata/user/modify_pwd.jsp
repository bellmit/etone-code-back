<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>修改密码</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/modedialog.css" />
		<script src="/js/paging.js"></script>
		<script src="/js/nav.js"></script>
		<script language="javascript" src="/js/script.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
		<script type="text/javascript">
	  //jquery验证
		    $(document).ready(function() {
			// 聚焦第一个输入框
			$("#loginName").focus();
			// 为beanForm注册validate函数
			$("#beanForm").validate({
				rules : {
					oldPwd : {
						required : true,
						remote : "user_checkPwd.do?oldLoginName="
								+ encodeURIComponent('${vcLoginName}')
					},
					newPwd:{
		    			required: true,
		    			minlength:6
		    		}, 
		    		newPwd2: {
		    			equalTo:"#newPwd"
		    		}
				},
				messages : {
					oldPwd : {
						remote : "输入原密码不正确"
					},
					newPwd2: {
						equalTo: "请输入相同的密码"
					}
				}
			});
		});
    </script>
		<base target="_self" />
	</head>
	<body style="overflow-x: hidden">
		<form name="beanView" action="/user_updatePwd.do?1=1" id="beanForm"
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
										修改密码
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
							<table width="100%" cellpadding="0" cellspacing="1"
								class="formitem">
								<tr>
									<th>
										原密码:
										<font color="red">*</font>
									</th>
									<td>
										<input name="oldPwd" type="password" id="oldPwd" style="width:210px" />
									</td>
								</tr>
								<tr>
									<th>
										新密码:
										<font color="red">*</font>
									</th>
									<td>
										<input name="newPwd" type="password" id="newPwd" style="width:210px" />
									</td>

								</tr>
								<tr>
									<th>
										确认密码:
										<font color="red">*</font>
									</th>
									<td colspan="4">
										<input name="newPwd2" type="password" id="newPwd2" style="width:210px" />
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
