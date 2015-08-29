<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>编辑表信息</title>
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
					"entity.vcTableName": { 
        				required: true, 
        				remote: "tableMap_checkUnique.do?oldName="+encodeURIComponent('${entity.vcTableName}')
    			},
          		"entity.vcTableNickName": "required"
				},
				messages: {
					"entity.vcTableName": {
						remote: "表对应信息已经存在"
					}
				}
			});
		});
</script>
		<style>
</style>
		<base target="_self" />
	</head>
	<body style="overflow-x: hidden">
		<form name="beanView" action="/tableMap_save.do" id="beanForm"
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
										编辑表信息
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
										表名：
										<font color="red">*</font>
									</th>

									<td>
										<input type="text" name="entity.vcTableName" value="${entity.vcTableName }" id="vcTableName" />
										<input type="hidden" name="entity.nmTableMapId"  value="${entity.nmTableMapId }"/>
									</td>
								</tr>
								<tr>
									<th>
										 表别名：
										<font color="red">*</font>
									</th>
									<td>
										<input type="text" name="entity.vcTableNickName" value="${entity.vcTableNickName }" id="vcTableNickName" />
									</td>
								</tr>
								<tr>
									<th>
										表类型：
										<font color="red">*</font>
									</th>
									<td>
										<select name="entity.intTableType" id="intTableType"
															style="height: 20px; width: 125px">
															<option value="1"
															<c:if test="${entity.intTableType== 1}">selected</c:if>>
																小时表
															</option>
															<option value="2" 
															<c:if test="${entity.intTableType== 2}">selected</c:if>>
																月表
															</option>
															<option value="3"
															<c:if test="${entity.intTableType== 3}">selected</c:if>>
																天表
															</option>
															<option value="4"
															<c:if test="${entity.intTableType==4 }">selected</c:if>>
																周表
															</option>
															<option value="5"
															<c:if test="${entity.intTableType==5 }">selected</c:if>>
																原始表
															</option>
														</select>
									</td>
								</tr>
								<tr>
									<th>
										业务类型：
										<font color="red">*</font>
									</th>
									<td>
										<select name="entity.intBusinessType" id="intBusinessType"
															style="height: 20px; width: 125px" >
															<option value="0"
															<c:if test="${entity.intBusinessType== 0}">selected</c:if>>
																不属于任何业务
															</option>
															<option value="1"
															<c:if test="${entity.intBusinessType==1 }">selected</c:if>>
																数据业务分析
															</option>
															<option value="2"
															<c:if test="${entity.intBusinessType== 2}">selected</c:if>>
																大流量用户
															</option>
															<option value="3"
															<c:if test="${entity.intBusinessType== 3}">selected</c:if>>
																彩信业务
															</option>
															<option value="4"
															<c:if test="${entity.intBusinessType== 4}">selected</c:if>>
																黑莓业务
															</option>
															<option value="5"
															<c:if test="${entity.intBusinessType== 5}">selected</c:if>>
																终端分析业务
															</option>
														</select>
									</td>
								</tr>
								
								<tr>
									<th>
										是否视图：
									</th>
									<td>
										<input type="radio" style="width:32px" name="entity.intViewFlag"  id="intViewFlag_table" value="1"
										<c:if test="${entity.intBusinessType==1 }">checked</c:if>/>表<input
										 type="radio" name="entity.intViewFlag" style="width:32px"  id="intViewFlag_view"  
										<c:if test="${entity.intBusinessType==2 }">checked</c:if>
										value="2" />视图
									</td>
								</tr>
								<tr>
									<th>
										是否即席查询呈现：
									</th>
									<td>
										<input style="width:32px" type="radio" name="entity.intIsShow"  
										<c:if test="${entity.intIsShow==1 }">checked</c:if>
										 id="intViewFlag_table"  value="1"
										checked/>呈现<input style="width:32px" type="radio" name="entity.intIsShow" 
										<c:if test="${entity.intIsShow==0 }">checked</c:if>
										 id="intViewFlag_view" 
										value="0" />不呈现
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
