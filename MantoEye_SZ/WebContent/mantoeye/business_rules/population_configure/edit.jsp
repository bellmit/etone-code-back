<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>添加常驻人口配置</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/modedialog.css" />
		<script src="/js/paging.js"></script>
		<script src="/js/nav.js"></script>
		<script language="javascript" src="/js/script.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
			<script type="text/javascript" src="/js/common_grid.js"></script>
		<!-- 自动补全JS --> 
		<script type="text/javascript" src="/js/common.js"></script>

		<script type="text/javascript">
	//验证表单信息
		$(document).ready(function(){
			var obj = window.dialogArguments;
			$('#nmResidentId').attr('value',obj.nmResidentId);	
			var intType = obj.intType;
			if(intType=='周'){
				$("#intType").get(0).selectedIndex=0;
			}
			if(intType=='月'){
				$("#intType").get(0).selectedIndex=1;
			}	
			$('#intDay').attr('value',obj.intDay);	
			$('#vcNote').attr('value',obj.vcNote);	
			//聚焦第一个输入框
			$("#intType").focus();
			//为beanForm注册validate函数
			$("#beanForm").validate({
				rules: { 
          		"intDay": {
          			required: true,
          			digits:true
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
<style>
		#content_table_id  th {
			height: 22px;
			padding-left: 5px;
			width: 24%;
			text-align: right;
			font: normal 13px arial, tahoma, helvetica, sans-serif;
		}
</style>
		<base target="_self" />
</head>
	<body style="overflow-x: hidden">
		<form name="beanView" action="/populationConfigure_updateConfigure.do"
			id="beanForm" method="post" >
			<table id="maincontent" cellspacing="0" cellpadding="0" border="0"
				width="100%" height="100%">
				<tr>
					<td>
						<table cellspacing="0" cellpadding="0" border="0" width="100%"
							class="header">
							<tr>
								<td width="100%" height="24px">
									<div class="title">
										添加常驻人口配置
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td width="100%">
						<div>
							<!----------- 表单信息 ------------------>
							<table width="100%" cellpadding="0" cellspacing="1"
								id="content_table_id"
								style="width: 100%; height: auto; margin: 0px; padding: 0px;">
								<tr>

									<th>
										配置类型:
										<font color="red">*</font>
									</th>
									<td>
										<select name="intType" disabled="disabled"
											id="intType" style="width: 354px;" >
											<option value="1">
												周
											</option>
											<option value="2">
												月
											</option>
										</select>
									</td>
								</tr>
								
							
								<tr>
									<th>
										常驻频次天数:
										<font color="red">*</font>
									</th>
									<td>
										<input type="text" name="intDay" id="intDay"
											style="height: 16px; width: 349px;"  />
										<input type="hidden" name="nmResidentId" id="nmResidentId" />
									</td>
								</tr>
								

								<tr>
									<th>
										备注:
									</th>
									<td>
										<textarea rows="6" cols="30" name="vcNote"
											id="vcNote"
											style="border: 1px solid #999999; height: 120px; width: 350px;"></textarea>
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
			<div class="dialog_msg_class" style="margin-top:120px;margin-left:70px;" id="showmsg" name="showmsg" style="display:none">
										<img src="/skin/Default/images/icon/16/loading.gif">
										</img>操作进行中,请稍后...</div>
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
													onclick="document.getElementById('btn_commit').click();" id="saveBtn" name="saveBtn"><span>保存</span>
												</a>
											</li>
											<li>
												<a href="#"
													onclick="document.getElementById('btn_cancel').click();" id="cancelBtn" name="cancelBtn"><span>关闭</span>
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

<script type="text/javascript">
	/**
	脚本不出错
	*/
	$(document).ready(function(){
		window.onerror = killErrors;
	})
	function killErrors() {
		return true;
	}
		
</script>