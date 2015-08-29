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
					$("#vcMenuName").focus();
					//为beanForm注册validate函数
					$("#beanForm").validate({
					rules: { 
					"entity.vcPermSymbol": { 
        				required: true, 
        				remote: "menu_checkUnique.do?oldName="+encodeURIComponent('${entity.vcPermSymbol}')
        				},
        			"entity.vcMenuName":	"required"
    			},
				messages: {
					"entity.vcPermSymbol": {
						remote: "模块标识符不能重复"
					}
				}});
		});
</script>
		<base target="_self" />
	</head>
	<body style="overflow-x: hidden">
		<form name="beanView" action="/menu_update.do" id="beanForm"
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
										编辑模块
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
										模块名称：
										<font color="red">*</font>
									</th>
									<td>
										<input type="text" name="entity.vcMenuName" id="vcMenuName"
											value="${entity.vcMenuName }" style="width:270px"/>
										<input type="hidden" name="entity.id" value="${entity.id}" />
									</td>

								</tr>
								<tr>
									<th>
										模块标识：
										<font color="red">*</font>
									</th>
									<td>

										<input name="entity.vcPermSymbol" type="hidden"
											id="vcPermSymbol" value="${entity.vcPermSymbol}" style="width:270px" />
										<input name="entity.btDeleted" type="hidden" id="btDeleted"
											value="${entity.btDeleted}" />
										${entity.vcPermSymbol}
									</td>
								</tr>
								<tr>
									<th>
										排序号：
										<font color="red">&nbsp;</font>
									</th>
									<td>
										<input type="text" name="entity.nmOrderNum" id="nmOrderNum"
											value="${entity.nmOrderNum }" style="width:270px" />
									</td>

								</tr>
								<tr>
									<th></th>
									<td>
										<span><font color="red">注：</font><font color="blue">排序号为非负整数，数值越小就越靠前。</font>
										</span>
									</td>
								</tr>
								<tr>
									<th>
										父模块名称：
										<font color="red">&nbsp;</font>
									</th>
									<td>
										<tree:ModuleTreeTag title="--系统管理--" name="entity.vcParentId"
											selectID="${entity.vcParentId}" action="" searchFlag="0" />
									</td>

								</tr>
								<tr>
									<th>
										模块类型：
										<font color="red">&nbsp;</font>
									</th>
									<td>
										<select name="entity.tiPermType" id="tiPermType" style="width:276px">
											<option value="1"
												<c:if test="${entity.tiPermType==1}">selected</c:if>>
												菜单
											</option>
											<option value="2"
												<c:if test="${entity.tiPermType ==2}">selected</c:if>>
												按钮
											</option>
											<option value="3"
												<c:if test="${entity.tiPermType ==3}">selected</c:if>>
												二级菜单
											</option>
											<option value="4"
												<c:if test="${entity.tiPermType ==4}">selected</c:if>>
												三级菜单
											</option>
										</select>
									</td>

								</tr>
								<tr>
									<th>
										映射地址：
										<font color="red">&nbsp;</font>
									</th>
									<td>
										<input type="text" name="entity.vcRedirectUrl"
											id="vcRedirectUrl" value="${entity.vcRedirectUrl}" style="width:270px" />
									</td>
								</tr>

								<tr>
									<th>
										是否可见：
										<font color="red">&nbsp;</font>
									</th>
									<td>
										<select name="entity.btShow" id="btShow" style="width:276px">
											<option value="1"
												<c:if test="${entity.btShow=='1'}">selected</c:if>>
												是
											</option>
											<option value="0"
												<c:if test="${entity.btShow=='0'}">selected</c:if>>
												否
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
										<textarea rows="5" name="entity.txtPermMemo" style="width:270px">
								<c:out value="${entity.txtPermMemo}"></c:out>
							</textarea>

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
//查询条件样式(注意:只有这样才可以操作)
$(document).ready(function(){
	$(document.getElementById("entity.vcParentId")).attr("style","width:276px");
});
</script>