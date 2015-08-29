<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>编辑角色</title>
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
			$("#vcRolesName").focus();
			//为beanForm注册validate函数
			$("#beanForm").validate({
				rules: { 
					"entity.vcRolesName": { 
        				required: true, 
        				remote: "role_checkUnique.do?vcOldRolesName="+encodeURIComponent(encodeURIComponent('${Entity.vcRolesName}'))
        				}
    			},
				messages: {
					"entity.vcRolesName": {
						remote: "角色名称已存在"
					}
				}
			});
		});
</script>
		<base target="_self" />
	</head>
	<body style="overflow-x: hidden">
		<form name="beanView" action="/role_update.do" id="beanForm"
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
										编辑角色
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
										角色名称：
										<font color="red">*</font>
									</th>

									<td>
										<input type="text" name="entity.vcRolesName" id="vcRolesName"
											value="${entity.vcRolesName }" />
										<input type="hidden" name="entity.id" id="entity.id"
											value="${entity.id }" />
									</td>
								</tr>
								<tr>
									<th>
										角色级别：
										<font color="red">*</font>
									</th>
									<td>
										<select name="entity.nmRolesLevel">
											<option value="0"
												<c:if test="${entity.nmRolesLevel==0}">selected</c:if>>
												0--超级管理员
											</option>
											<option value="1"
												<c:if test="${entity.nmRolesLevel==1}">selected</c:if>>
												1--管理员
											</option>
											<option value="2"
												<c:if test="${entity.nmRolesLevel==2}">selected</c:if>>
												2--普通用户
											</option>
											<option value="3"
												<c:if test="${entity.nmRolesLevel==3}">selected</c:if>>
												3--访客
											</option>
										</select>
									</td>
								</tr>
								<tr>
									<th>
										说明：
										<font color="red">&nbsp;</font>
									</th>
									<td>
										<textarea name="entity.txtRolesMemo" id="txtRolesMemo"
											rows="3" cols="48">${entity.txtRolesMemo }></textarea>
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
