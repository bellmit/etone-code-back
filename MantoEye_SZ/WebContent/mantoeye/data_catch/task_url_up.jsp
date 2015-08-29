<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>导入URL</title>
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
			var obj = window.dialogArguments;			
			$('#taskId').attr('value',obj.taskId);
			/*var status = obj.status;
		if(status=='1'){
				$('#note').css({'display':'block'});
				$('#intUrlStatus1').attr('checked',true);	
				$('#intUrlStatus1').attr('disabled','disabled');
				$('#intUrlStatus2').attr('disabled','disabled');
			}
			if(status=='2'){
				$('#note').css({'display':'block'});
				$('#intUrlStatus2').attr('checked',true);	
				$('#intUrlStatus1').attr('disabled','disabled');
				$('#intUrlStatus2').attr('disabled','disabled');
			}*/
		})
			
			function showDataType(value){
				if(value==1){
					$('#intUrlStatus').attr('value',1);	
				}
				if(value==2){
					$('#intUrlStatus').attr('value',2);	
				}
			}
</script>
		<base target="_self" />
	</head>
	<body style="overflow-x: hidden">
		<form name="beanForm" action="/dataOutput_upURLFile.do?1=1"
						id="beanForm" method="post" enctype="multipart/form-data">
			<iframe id="download" name="download" height="0px" width="0px"></iframe><!--用iframe模拟文件下载-->
			<table id="maincontent" cellspacing="0" cellpadding="0" border="0"
				width="100%" height="100%">
				<tr>
					<td>
						<table cellspacing="0" cellpadding="0" border="0" width="100%"
							class="header">
							<tr>
								<td width="100%" height="24px">
									<div class="title">
										上传URL
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
										URL文件：
										<font color="red">*</font>
									</th>
									<td>
											<input type="file" name="file" id="file" style="border: 1px solid #999999; height: 20px;width: 360px;"/>
											<input type="hidden" name='taskId' id="taskId"/>
											<input type="hidden" name='intUrlStatus' id="intUrlStatus" value="2"/>
									</td>	
								</tr>
								<c:if test="${status!=1 && status!=2}">
								<tr>
									<th>
										匹配模式：
									</th>
									<td >
											
														<input type="radio" name='radio' id="intImeiStatus1" 
															style="width: 20px; border: 0px;"
															onclick='showDataType(1)'  />
														完全匹配
														<input type="radio" name='radio' id="intImeiStatus2"
															style="width: 20px; border: 0px;"
															onclick='showDataType(2)' checked/>
														模糊
												
									</td>
										
								</tr>
								
								</c:if>
								<c:if test="${status==1}">
								<tr>
									<th>
										匹配模式：
									</th>
									<td >
											
														<input type="radio" name='radio' id="intImeiStatus1"  checked="true" disabled
															style="width: 20px; border: 0px;"
															onclick='showDataType(1)'  />
														完全匹配
														<input type="radio" name='radio' id="intImeiStatus2" disabled
															style="width: 20px; border: 0px;"
															onclick='showDataType(2)'/>
														模糊
												
									</td>
										
								</tr>
								<tr id="note" style="display: block">
									<th>
									</th>
									<td >
										<font color="red">（URL匹配模式已经存在，若需要更改，请将已存在的URL删除！）</font>
									</td>
										
								</tr>
								</c:if>
								<c:if test="${status==2}">
								<tr>
									<th>
										匹配模式：
									</th>
									<td >
											
														<input type="radio" name='radio' id="intImeiStatus1"  disabled
															style="width: 20px; border: 0px;"
															onclick='showDataType(1)'  />
														完全匹配
														<input type="radio" name='radio' id="intImeiStatus2"
															style="width: 20px; border: 0px;"
															onclick='showDataType(2)' checked="true" disabled/>
														模糊
												
									</td>
										
								</tr>
								<tr id="note" style="display: block">
									<th>
									</th>
									<td >
										<font color="red">（URL匹配模式已经存在，若需要更改，请将已存在的URL删除！）</font>
									</td>
										
								</tr>
								</c:if>
							<tr>
								<th></th>								
								<td colspan="2" height="30px">
									<span style="color:#0000FF;">提示:上传文件为txt文件，文件中一行一个URL，模糊URL不能超过10个，完全匹配的不能超过1000个，</span>
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
		}else {
			var fs = (document.getElementById("file").value).split("\.");
			if((fs.length<2)||((fs[fs.length-1]!='txt')&&(fs[fs.length-1]!='txt'))){
				alert("文件后缀名必须为txt");
			}else{
				showMsg();
				document.beanForm.submit();
			}
		}
	}
	

</script>