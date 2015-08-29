<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>导入彩信端口信息</title>
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
		<form name="beanForm" action="/spPortControl_importXlsFile.do"
			id="beanForm" method="post" enctype="multipart/form-data">
			<iframe id="download" name="download" height="0px" width="0px"></iframe><!--用iframe模拟文件下载-->
			<table id="maincontent" cellspacing="0" cellpadding="0" border="0"
				width="100%" style="height: 100%;">
				<tr>
					<td>
						<table cellspacing="0" cellpadding="0" border="0" width="100%"
							class="header">
							<tr>
								<td width="100%" height="24px">
									<div class="title">
										导入彩信端口信息
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr align="center">
					<td width="100%" align="center">
						<div>
							<!----------- 表单信息 ------------------>
							<table cellpadding="0" cellspacing="1" class="formitem"
								border="0">
								<tr>
									<td style="height: 20px;" colspan="2">
										&nbsp;
									</td>
								</tr>
								<tr>
									<th>
										彩信端口信息文件：
										<font color="red">*</font>
									</th>
									<td>
										<input type="file" name="file" id="file"
											style="border: 1px solid #999999; height: 20px; width: 360px;" />

									</td>
								</tr>
								<tr>
									<td style="height: 20px;" colspan="2">
										<span>导入文件必须为包含‘端口’‘彩信所属’‘公司’的excel文件<a href="javascript:openUrl('/spPortControl_downloadTemplate.do?1=1')">模板下载</a></span>
									</td>
								</tr>
								<tr>
									<td style="height: 20px;" colspan="2">
										&nbsp;
									</td>
								</tr>
								<tr align="center">
									<td align="center" id="showmsg" name="showmsg" colspan="2"
										style="text-align: center; display: none;">
										<img src="/skin/Default/images/icon/16/loading.gif"></img>
										操作进行中,请稍后...
									</td>
								</tr>
							</table>
							<table width="66%" cellpadding="0" cellspacing="0"
								class="formitem_pagestyle" style="padding-top: 40px;">
								<tr>
									<td colspan="2">
										<table align="center">
											<tr>
												<td width="50px;"></td>
												<td align="center">

													<div id="mainbtn">
														<ul>
															<li>
																<a href="#" onclick="submitFrom();" id="saveBtn"
																	name="saveBtn"><span>保存</span> </a>
															</li>
															<li>
																<a href="#" onclick="window.close();" id="cancelBtn"
																	name="cancelBtn"><span>关闭</span> </a>
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
						</div>
					</td>
				</tr>
			</table>
		</form>
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
			if((fs.length<2)||(fs[fs.length-1]!='xls')){
				alert("文件后缀名必须为xls");
			}else{
				showMsg();
				document.beanForm.submit();
			}
		}
	}
//showModalDialog session 丢失
function openUrl(urlstr)
{
   	var obj=document.getElementById('download');
   	obj.contentWindow.location.href=urlstr;
   	   	
}	
</script>