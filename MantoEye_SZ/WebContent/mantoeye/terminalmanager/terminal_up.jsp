<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>终端上传</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/modedialog.css" />
		<script src="/js/paging.js"></script>
		<script src="/js/nav.js"></script>
		<script language="javascript" src="/js/script.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
		<script type="text/javascript">
		
</script>
		<base target="_self" />
	</head>
	<body style="overflow-x: hidden">
		<form name="beanForm" action="/terminalmanager_upFile.do"
						id="beanForm" method="post" enctype="multipart/form-data">
			<table id="maincontent" cellspacing="0" cellpadding="0" border="0"
				width="100%" height="100%">
				<tr>
					<td>
						<table cellspacing="0" cellpadding="0" border="0" width="100%"
							class="header">
							<tr>
								<td width="100%" height="24px">
									<div class="title">
										终端上传
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
							<td colspan="2" height="30px">
								<span style="color:#0000FF;">提示:号码文件的第一行必须为标题，即终端的格式【号码+品牌+型号】，中间以Tab分隔，不同记录以换行分隔，号码为不包含86的11位手机号码，文件类型应为dat结尾的txt类型的文件。</span>
							</td></tr>
								<tr>
									<th>
										终端文件：
										<font color="red">*</font>
									</th>
									<td>
											<input type="file" name="file" id="file" style="border: 1px solid #999999; height: 20px;width: 360px;"/>
											
									</td>									
							</tr>	
			</table>
			<div class="dialog_msg_class" style="margin-top:80px;margin-left:70px;" id="showmsg" name="showmsg" style="display:none">
										<img src="/skin/Default/images/icon/16/loading.gif">
										</img>操作进行中,请稍后...</div>
			<table width="66%" cellpadding="0" cellspacing="0"
				class="formitem_pagestyle" style="padding-top: 40px;">
				<tr>
					<td colspan="2">
						<table align="right">
						
							<tr>
								<td width="50px;"></td>
								<td align="center">
								
									<div id="mainbtn">
										<ul>
											<li>
												<a href="#"
													onclick="submitFrom();"  id="saveBtn" name="saveBtn"><span>保存</span>
												</a>
											</li>
											<li>
												<a href="#"
													onclick="window.close();"  id="cancelBtn" name="cancelBtn"><span>关闭</span>
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
			
	</body>
</html>

<script type="text/javascript">
	function showMsg() {   
	var ml = (parseInt(window.dialogWidth)-250)/2;
	var mt = (parseInt(window.dialogHeight)-55)/2;
	$('#showmsg').css({"margin-top":0,"margin-left":ml,"display":"block"});
	$('#saveBtn').attr('disabled','disabled');	
	$('#cancelBtn').attr('disabled','disabled');	
}   
	function submitFrom(){
		if(document.getElementById("file").value==''){
			alert("请选择文件!");
			//showMsg();
		}else {
			var fs = (document.getElementById("file").value).split("\.");
			//alert(fs[0]+"--"+fs[1]);
			//alert((fs[fs.length-1]!='dat')+"--"+(fs[fs.length-1]!='DAT'));
			if((fs.length<2)||((fs[fs.length-1]!='dat')&&(fs[fs.length-1]!='DAT'))){
				alert("文件后缀名必须为dat");
			}else{
				showMsg();
				document.beanForm.submit();
			}
		}
	}
</script>