<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>添加模块</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/modedialog.css" />
		<script src="/js/paging.js">
</script>
		<script src="/js/nav.js">
</script>
		<script language="javascript" src="/js/script.js">
</script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js">
</script>
		<script type="text/javascript">
$(document).ready(function() {
	//聚焦第一个输入框
		$("#vcNode").focus();
		var obj = window.dialogArguments;
		$('#LFlushLevel').attr('value', obj.LFlushLevel);
		$('#RFlushLevel').attr('value', obj.RFlushLevel);
			$('#OLFlushLevel').attr('value', obj.LFlushLevel);
		$('#ORFlushLevel').attr('value', obj.RFlushLevel);
		$('#vcNote').attr('value', obj.vcNote);
		$('#id').attr('value', obj.id);
		//为beanForm注册validate函数
		$("#beanForm").validate( {
			rules : {
				"LFlushLevel" : {
					required : true,number:true  
				},
				"RFlushLevel" : {
				number:true  
				}
			},
			messages : {

			},
			submitHandler : function(form) {
				//alert(form.action);  
			var ml = (parseInt(window.dialogWidth) - 250) / 2;
			var mt = (parseInt(window.dialogHeight) - 55) / 2;
			$('#showmsg').css( {
				"margin-top" : 0,
				"margin-left" : ml,
				"display" : "block"
			});
			$('#saveBtn').attr('disabled', 'disabled');
			$('#cancelBtn').attr('disabled', 'disabled');
			form.submit();
		}

		});
	});
</script>
		<base target="_self" />
	</head>
	<body style="overflow-x: hidden">
		<form name="beanView" action="/flushgrade_saveModify.do" id="beanForm"
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
										添加流量层次
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
										时间粒度：
									</th>
									
									<td>
														<select name="timeLevel" id="timeLevel"
															 style="height: 25px;width:295px">														
															<option value="2" selected>
																天
															</option>
															<option value="4">
																月
															</option>
														</select>
													</td>
									
									
								</tr>
								<tr>
									<th>
										流量层次(K)：
										<font color="red">*</font>
									</th>
									<td>
										<input type="text" name="LFlushLevel" id="LFlushLevel"
											style="height: 18px; width: 130px" />
										&nbsp;到&nbsp;
										<input type="text" name="RFlushLevel" id="RFlushLevel"
											style="height: 18px; width: 130px" />
											<span><font color="blue">("-1"代表无上限)</font>
									</td>
								</tr>
								<tr>
									<th>
										备注：
										<font color="red">*</font>
									</th>
									<td>
										<input type="text" name="vcNote" id="vcNote"
											style="height: 19px; width: 290px" />
										<!-- 	<textarea name="nmMsisdn" id="nmMsisdn" rows="6"
											class="replyTextClass" style="overflow-y: auto;width: 290px;"></textarea> -->
									</td>
								</tr>
								<tr>
									<th></th>
									<td>
							<span><font color="red">注：</font><font color="blue">1.若已存在无上限数据，则不能编辑档次。</font>
										</span> 
										<input type="hidden" name="OLFlushLevel" id="OLFlushLevel"
											style="height: 25px; width: 290px" />
										<input type="hidden" name="ORFlushLevel" id="ORFlushLevel"
											style="height: 25px; width: 290px" />
										<input type="hidden" name="id" id="id"
											style="height: 25px; width: 290px" />
									</td>
								</tr>
<tr>
									<th></th>
									<td>
									<span><font color="blue">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.不能于以存在的档次流量范围中编辑档次</font>
									</td>
								</tr>
								<tr>
									<th></th>
									<td>
									<span><font color="blue">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.初始值必须大于已存在的档次的最大值</font>
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