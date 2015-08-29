<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/include/session.jsp"%>
<%@ include file="/include/setcache.jsp"%>
	<head>
		<title></title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/modedialog.css" />
		<script src="/js/paging.js"></script>
		<script src="/js/nav.js"></script>
		<script language="javascript" src="/js/script.js"></script>

		<script type="text/javascript">
	//验证表单信息
	$(document).ready( function() {
		//聚焦第一个输入框
			$("#entity.vcBussinessName").focus();
			//为beanForm注册validate函数
			$("#beanForm").validate( {
				rules : {
					"entity.vcBussinessName" :{ 
        				required: true, 
        				remote: "businessSite_checkUnique.do?vcBussinessName="+encodeURIComponent(encodeURIComponent('${entity.vcBussinessName}'))
    				},
					"busiTypeId" :"required",
					"busiCompanyId" :"required"
				},
				messages: {
					"entity.vcBussinessName": {
						remote: "该名称的业务已经存在"
					}
				},
				submitHandler: function(form) {   
					//alert(form);
					var ml = (parseInt(window.dialogWidth)-250)/2;
					var mt = (parseInt(window.dialogHeight)-55)/2;
					$('#showmsg').css({"margin-top":mt,"margin-left":ml,"display":"block"});
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
		<form name="beanView" action="/businessSite_update.do" id="beanForm"
			method="post">
			<table id="maincontent" cellspacing="0" cellpadding="0" border="0"
				style="width:100%;height:100%">
				<tr>
					<td>
						<table cellspacing="0" cellpadding="0" border="0" width="100%"
							class="header">
							<tr>
								<td width="100%" height="24px">
									<div class="title">
										编辑业务
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
										业务名称：
										<font color="red">*</font>
									</th>
									<td>
										<input type="text" name="entity.vcBussinessName"
											id="entity.vcBussinessName"
											value="${entity.vcBussinessName }"/>
										<input type="hidden" name="entity.nmBussinessId"
											id="entity.nmBussinessId" value="${entity.nmBussinessId }"/>
									</td>
								</tr>

								<tr>
									<th>
										业务类型：
										<font color="red">*</font>
									</th>
									<td>
										<select id="busiTypeId" name="busiTypeId"
											onchange="busiTypeChange(this.value)">
											<option value="">
												--请选择--
											</option>
											<c:forEach items="${busiTypeList}" var="busitype">
												<option value="${busitype.nmBussinessTypeId }"
													<c:if
														test="${entity.dtbBusinessType.nmBussinessTypeId==busitype.nmBussinessTypeId }">selected</c:if>>
													${busitype.vcBussinessTypeName }
												</option>
											</c:forEach>
											<option value="-1">
												--添加新类型--
											</option>
										</select>
									</td>
								</tr>
								<tr id="busiTypeNameTr" style="display: none">
									<th>
										业务类型名称：
										<font color="red">*</font>
									</th>
									<td>
										<input type="text" name="busiTypeName" id="busiTypeName">
									</td>
								</tr>
								<tr>
									<th>
										公司：
										<font color="red">*</font>
									</th>
									<td>
										<select id="busiCompanyId" name="busiCompanyId"
											onchange="companyChange(this.value)">
											<option value="">
												--请选择--
											</option>
											<c:forEach items="${busiCompanyList}" var="busiCompany">
												<option value="${busiCompany.nmCompanyId }"
													<c:if
														test="${entity.dtbBusinessCompany.nmCompanyId==busiCompany.nmCompanyId }">selected</c:if>>
													${busiCompany.vcName }
												</option>
											</c:forEach>
											<option value="-1">
												--添加新公司--
											</option>
										</select>
									</td>
								</tr>
								<tr id="companyNameTr" style="display: none">
									<th>
										公司名称：
										<font color="red">*</font>
									</th>
									<td>
										<input type="text" name="companyName" id="companyName">
									</td>
								</tr>

								<tr>
									<th>
										业务描述：
									</th>
									<td>
										<textarea name="entity.vcBussinessNote"
											id="entity.vcBussinessNote" rows="4" cols="48"
											><c:out value="${entity.vcBussinessNote}"
											></c:out></textarea>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td>
					<div class="dialog_msg_class" style="margin-top:120px;margin-left:70px;" id="showmsg" name="showmsg" style="display:none">
										<img src="/skin/Default/images/icon/16/loading.gif">
										</img>操作进行中,请稍后...</div>
							<jsp:include page="/include/submit.jsp"></jsp:include>
					<div style="display: none;">
						<s:submit id="btn_commit"></s:submit>
						<button id="btn_cancel" onclick="window.close()"></button>
					</div>
					</td>
				</tr>
				</form>
	</body>
</html>
<script>
	function companyChange(value) {
	
		//alert(document.getElementById("entity.nmBussinessId").value);
		if (value == '-1') {
			document.getElementById("companyNameTr").style["display"] = "block";
		} else {
			document.getElementById("companyName").value = "";
			document.getElementById("companyNameTr").style["display"] = "none";
		}
	}
	function busiTypeChange(value) {
		//alert(value);
		if (value == '-1') {
			document.getElementById("busiTypeNameTr").style["display"] = "block";
		} else {
			document.getElementById("busiTypeName").value = "";
			document.getElementById("busiTypeNameTr").style["display"] = "none";
		}
	}
</script>
