
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title></title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/maincontent.css" />
		<script src="/js/paging.js"></script>
		<script src="/js/nav.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
		<style>
.myth {
	background-color: #FFFFFF;
	height: 22px;
	padding-left: 5px;
	width: 24%;
	text-align: right;
}

.mytd {
	padding-left: 5px;
	background-color: #FFFFFF;
	width: 76%;
}
</style>
		<base target="_self" />
	</head>
	<body style="overflow-x: hidden">
		<form name="beanView" action="/blackuser_save.do?permId=${permId}"
			id="beanForm" method="post" enctype="multipart/form-data">
			<table id="maincontent" cellspacing="0" cellpadding="0" border="0"
				bgcolor="" width="100%" height="100%">
				<tr>
					<td>
						<table cellspacing="0" cellpadding="0" border="0"
							bgcolor="#ffffff" width="100%" class="inputmenubar">
							<tr>
								<td width="100%" height="24px">
									<div class="middletitle">
										添加黑名单用户
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<div style="width: 100%; height: 20px;">
							<table width="100%" cellpadding="0" cellspacing="1"
								class="formitem">
								<tr>

									<td align="center">
										<input type="radio" name="isFile" checked value="0"
											onclick="chageState();" style="width: 20px;" />
										手动添加黑名单用户&nbsp;&nbsp;
										<input type="radio" name="isFile" onclick="chageState();"
											style="width: 20px;" value="1" />
										批量添加黑名单用户
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td width="100%">
						<div id="handle" style="width: 100%; height: auto;">
							<table width="100%" cellpadding="0" cellspacing="1"
								class="formitem">
								<tr>
									<td width="120px;" class="myth">
										号码：
										<font color="red">*</font>
									</td>
									<td align="left" class="mytd">
										<textarea rows="6" cols="30" name="msisdn" id="msisdn"
											style="border: 1px solid #999999; height: 120px;"></textarea>
										<input type="hidden" id="dmsisdn" name="dmsisdn" value=""/>	
									</td>
								</tr>
								<tr>
									<th width="120px;">
									</th>
									<td align="left">
										<span> <font color="red">注：</font><font color="blue">号码需要86开头,多个号码用换行分隔</font>
										</span>
									</td>
								</tr>
							</table>
						</div>

						<div id="file" style="display: none; width: 100%; height: auto;"
							style="margin:5px 0px">
							<table width="100%" cellpadding="0" cellspacing="1"
								class="formitem">
								<tr>
									<td width="120px;" class="myth">
										号码文件：
										<font color="red">*</font>
									</td>
									<td class="mytd">
										<input type="file" name="myFile" id="myFile"
											style="border: 1px solid #999999; width: 260px height :   20px;"
											value="打开" />
									</td>
								</tr>
								<tr>
									<td colspan="2" align="center">
										<span> <font color="red">注：</font><font color="blue">号码需要86开头,多个号码用换行分隔</font>
										</span>
									</td>
								</tr>
							</table>
						</div>

						<div style="width: 100%; height: 60px;">
							<table width="100%" cellpadding="0" cellspacing="1"
								class="formitem">
								<tr>
									<td width="120px;" class="myth">
										描述：
										<font color="red">*</font>
									</td>
									<td class="mytd">
										<input type="text" name="descrite" value="流量超标"
											style="border: 1px solid #999999; height: 18px; width: 260px;" />
									</td>
								</tr>

							</table>
							<div class="dialog_msg_class" style="margin-top:120px;margin-left:70px;" id="showmsg" name="showmsg" style="display:none">
										<img src="/skin/Default/images/icon/16/loading.gif">
										</img>操作进行中,请稍后...</div>
						</div>
					</td>
				</tr>
				<tr>
					<td align="center" style="padding-left: 180px; height: 26px;">
						<table width="76%" cellpadding="0" cellspacing="0"
							class="formitem_pagestyle" style="padding-top: 20px;">
							<tr>
								<td colspan="2">
									<table align="right">
										<tr>
											<td align="center">
												<div id="mainbtn">
													<ul>
														<li>
															<a href="#"
																onclick="checkField();"
																id="saveBtn" name="saveBtn"><span>保存</span> </a>
														</li>
														<li>
															<a href="#"
																onclick="document.getElementById('btn_cancel').click();"
																 id="cancelBtn" name="cancelBtn"><span>关闭</span> </a>
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
						<div style="display: none;">
							<s:submit id="btn_commit"></s:submit>
							<button id="btn_cancel" onclick="window.close();"></button>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
<script>
	function chageState() {
		var check = document.beanView.isFile;
		if (check[0].checked == true) {
			document.getElementById("handle").style.display = "block";
			document.getElementById("file").style.display = "none";
			$('#beanForm').attr('enctype', '');
		} else {
			document.getElementById("handle").style.display = "none";
			document.getElementById("file").style.display = "block";
		}
	}
	function checkField() {
		var check = document.beanView.isFile;
		if (check[0].checked == true) {
			var msisdns = $("#msisdn").val();
			if (msisdns==null||$("#msisdn").val()=='') {
				alert('号码不能为空');
				return;
			}else{
				var msa = msisdns.split('\n');
				var lmsa = "";
				for(var i=0;i<msa.length;i++){
					var msai = msa[i]+'';
					if(msai.length!=13||msai.indexOf('86')!=0){
						alert("号码格式错误，号码必须是以86开头的数字，多个号码间以换行分开");
						return;
					}
					if(isNaN(msai)){
						alert("号码格式错误，号码必须是数字，请修改后再提交");
						return;
					}
					lmsa = lmsa+','+msai;
				}			
				$('#dmsisdn').attr('value',lmsa.substr(1,lmsa.length));		
				//alert($('#dmsisdn').attr('value'));	
			}
		} else {
			if ($('#myFile').val() == '') {
				alert('请选择文件');
				return;
			}
		}
		document.getElementById('btn_commit').click();
		var ml = (parseInt(window.dialogWidth)-250)/2;
		var mt = (parseInt(window.dialogHeight)-55)/2;
		$('#showmsg').css({"margin-top":0,"margin-left":ml,"display":"block"});
		$('#saveBtn').attr('disabled','disabled');	
		$('#cancelBtn').attr('disabled','disabled');	
	}
$(document).ready(function(){
	window.onerror = killErrors;
})
function killErrors() {
	return true;
}	
</script>
