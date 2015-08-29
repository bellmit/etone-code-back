<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>编辑用户</title>
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
          		"entity.vcUserName": "required",
    			"entity.vcEmail":"email",
    			"entity.vcMobel":"mobil",
    			"entity.vcTelephone":"phone"
				}
			});
		});
</script>
		<base target="_self" />
	</head>
	<body style="overflow-x: hidden">
		<form name="beanView" action="/user_update.do" id="beanForm"
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
										编辑用户
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
								<th>
									用户昵称：
									<font color="red">*</font>
								</th>

								<td>
									<input type="text" name="entity.vcUserName" id="vcUserName"
										value="${entity.vcUserName}" />
									<!-- 用户主键 -->
									<input type="hidden" name="entity.id" id="id"
										value="${entity.id}" />
									<!-- 用户密码 -->
									<input type="hidden" id="vcPassword" name="entity.vcPassword"
										value="${entity.vcPassword}" />
									<input type="hidden" id="tiUserType" name="entity.tiUserType"
										value="${entity.tiUserType}" />
								</td>
								<tr>
									<th>
										登录名：
										<font color="red">*</font>
									</th>
									<td>
										${entity.vcLoginName}
										<input type="hidden" name="entity.vcLoginName"
											value="${entity.vcLoginName}" id="vcLoginName" />
									</td>
								</tr>
								<!-- 
								<tr>
									<th>
										部门：
										<font color="red">&nbsp;&nbsp;</font>
									</th>
									<td valign="top">
										<tree:DeptTreeTag title="请选择部门" name="entity.vcDeptId"
											selectID="${entity.vcDeptId }" action="" />
									</td>
								</tr>
								 -->
								<tr>
									<th>
										邮箱：
										<font color="red">&nbsp;&nbsp;</font>
									</th>
									<td>
										<input type="text" name="entity.vcEmail" id="vcEmail"
											value="${entity.vcEmail}" />
									</td>
								</tr>
								<tr>
									<th>
										手机：
										<font color="red">&nbsp;&nbsp;</font>
									</th>
									<td>
										<input type="text" name="entity.vcMobel"
											value="${entity.vcMobel}" id="vcMobel" />
									</td>
								</tr>
								<tr>
									<th>
										电话：
										<font color="red">&nbsp;&nbsp;</font>
									</th>
									<td>
										<input type="text" id="vcTelephone" name="entity.vcTelephone"
											value="${entity.vcTelephone}" />
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
