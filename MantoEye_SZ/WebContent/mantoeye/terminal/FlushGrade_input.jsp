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
		<script src="/js/paging.js"></script>
		<script src="/js/nav.js"></script>
		<script language="javascript" src="/js/script.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
		<script type="text/javascript">
	$(document).ready( function() {
		var intDownFlush = $("#intDownFlush").attr("value");
		if(intDownFlush==-1){
			$('#mess').css({'display':'block'});
		}
		$('#note').css({'display':'block'});
		//聚焦第一个输入框
			$("#intUpFlush").focus();
			//为beanForm注册validate函数
			$("#beanForm").validate({
				rules : {				
					"intDownFlush": {
						number:true  
    				},"intUpFlush":{
    					required: true,number:true  
    				}
				},messages: {
					
				},
				submitHandler: function(form) { 
					//alert(form.action);  
					var ml = (parseInt(window.dialogWidth)-250)/2;
					var mt = (parseInt(window.dialogHeight)-55)/2;
					$('#showmsg').css({"margin-top":0,"margin-left":ml,"display":"block"});
					$('#saveBtn').attr('disabled','disabled');	
					$('#cancelBtn').attr('disabled','disabled');	
					var intDownFlush = parseFloat($("#intDownFlush").attr("value"));
					var intUpFlush = parseFloat($("#intUpFlush").attr("value"));
					if(intDownFlush==-1){
						alert("不能继续添加，存在无穷大的档次，请删除该档次！");
						return false;
					}
					if(intDownFlush>=intUpFlush && intUpFlush!=-1){
						alert("后者必须大于前者的值");
						return false;
					}
					form.submit(); 
					document.getElementById('btn_commit').disabled=true;
				}  
				   
			});
		});
		
		
		function changeBitType(){
				var bitType = $("#bitType option:selected").val();
				$.ajax({
				type : "post",
				url : "flushgrade_changeFlushConfigure.do",
				data : {bitType:bitType},
				success : function(msg) {
					$("#intDownFlush").attr("value",msg);
					if(msg==-1){
						$('#mess').css({'display':'block'});
					}else{
						$('#mess').css({'display':'none'});
					}
			},
		error : function() {
			alert('服务器出错,请联系管理员!');
		}
	});
		}
</script>
		<base target="_self" />
	</head>
	<body style="overflow-x: hidden">
		<form name="beanView" action="/flushgrade_saveFlushConfigure.do" id="beanForm"
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
														<select name="bitType" id="bitType" onchange="changeBitType()" disabled="disabled"
															 style="height: 25px;width:295px">														
															<option value="0" selected>
																天
															</option>
															<option value="1">
																月
															</option>
														</select>
													</td>
									
									
								</tr>
								<tr>
									<th>
										流量层次(KB)：
										<font color="red">*</font>
									</th>
									<td>
									<input type="text" name="intDownFlush" id="intDownFlush"
															style="height: 18px; width:130px" readonly="readonly" value="${intDownFlush}"/>
								&nbsp;到&nbsp;
								<input type="text" name="intUpFlush" id="intUpFlush"
															style="height: 18px; width: 130px" />
																<span><font color="blue">("-1"代表无穷大)</font>
									</td>
								</tr>
						
								<tr>
									<th></th>
									<td>
									<div id="mess" style="display: none"><font color="red">注：</font><font color="red">不能继续添加，存在无穷大的档次，请删除该档次！</font></div>
									</td>
								</tr>
							
							</table>
						</div>
					</td>
					<div style="display: none;">
						<s:submit id="btn_commit" ></s:submit>
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