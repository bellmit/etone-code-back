<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>添加翻译规则</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/modedialog.css" />
		<script src="/js/paging.js"></script>
		<script src="/js/nav.js"></script>
		<script language="javascript" src="/js/script.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
		<!-- 导入业务选择复选框 -->
		<link rel="stylesheet" href="/mantoeye/dialog/s_dialog.css" />
		<link rel="stylesheet" href="/tools/jquery/jquery.treeview.css" />
		<script type="text/javascript" src="/mantoeye/dialog/SingleDialog.js"></script>
		<script type="text/javascript" src="/tools/jquery/jquery.treeview.js"></script>
		<script type="text/javascript">
	//验证表单信息
	$(document).ready( function() {
		//聚焦第一个输入框
			$("#entity.vcUrl").focus();
			//为beanForm注册validate函数
			$("#beanForm").validate( {
				rules : {
					"business" :"required",
					//"entity.vcUrl" :"url",
					//"entity.vcServerIp" :"required"
					"ips" :"required"
					//"entity.intPort" :"digits"
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
		<form name="beanView" action="/businessMainVetor_mutilSave.do" id="beanForm"
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
										添加翻译规则
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
										业务：
										<font color="red">*</font>
									</th>
									<td>								
									<input type="text" value="${businame}" 
										id="business" onclick="showDialog()" name="business"
										style="display: block;font-size: 11px; color: #163877;" />										
									</td>
								</tr>
							<!-- -->	<tr style="display:none">
									<th>
										URL：
									</th>
									<td>
									<input id="businessIds" type="hidden" value="${busiid }" name="businessIds" />
										<input type="text" name="entity.vcUrl" id="entity.vcUrl" onchange="hideMsg()"/>
									</td>
								</tr> 
								<tr id="busiTypeNameTr" >
									<th>
										IP：<font color="red">*</font>
									</th>
									<td>
									<textarea rows="16" cols="10" name="ips" id="ips"></textarea>
										<!--  <input type="text" name="entity.vcServerIp" id="entity.vcServerIp" onchange="hideMsg()"/>-->
									</td>
								</tr>
								<!-- --> <tr style="display:none">
									<th>
										Port：
									</th>
									<td>
										<input type="text" name="entity.intPort" id="entity.intPort" onchange="hideMsg()"/>
									</td>
									</td>
								</tr>
								<tr id="companyNameTr" style="display:none">
									<th>
										APN：
									</th>
									<td>
										<input type="text" name="entity.vcApn" id="entity.vcApn" onchange="hideMsg()"/>
									</td>
								</tr>

								<tr style="display:none">
									<th>
										UserAgent：
									</th>
									<td>
										<input type="text" name="entity.vcUserAgent" id="entity.vcUserAgent" onchange="hideMsg()"/>
									</td>
								</tr>
								<tr>
									<th>
										&nbsp;
									</th>
									<td id="checkinfotd">
										<!--  <input type="hidden" name="checknull" id="checknull" value=""/>-->
										<span id="checkinfo" class="checkwarn"></span>
									</td>
								</tr>
								<tr>
									<th>
										&nbsp;
									</th>
									<td>
										<span style="color:#0000FF;">提示:可以同时给一个业务添加多条规则，多个IP间用换行符分隔。</span>
									</td>
								</tr>
							</table>
							<div class="dialog_msg_class" style="margin-top:120px;margin-left:70px;" id="showmsg" name="showmsg" style="display:none">
										<img src="/skin/Default/images/icon/16/loading.gif">
										</img>操作进行中,请稍后...</div>
						</div>
					</td>
					
					<div style="display: none;">
						<s:submit id="btn_commit"></s:submit>
						<button id="btn_cancel" onclick="window.close()"></button>
					</div>
				</tr>
			</table>
			<table width="66%" cellpadding="0" cellspacing="0"
				class="formitem_pagestyle" style="padding-top: 10px;">
				<tr>
					<td colspan="2">
						<table align="right">
							<tr>
								<td align="center">
									<div id="mainbtn">
										<ul>
											<li>
												<a href="#"
													onclick="checkAllNull();"   id="saveBtn" name="saveBtn"><span>保存</span>
												</a>
											</li>
											<li>
												<a href="#"
													onclick="document.getElementById('btn_cancel').click();"  id="cancelBtn" name="cancelBtn"><span>关闭</span>
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
<!-- JS -->
<script>
	var dialog = null;
	function showDialog(){
		if(dialog == null){
		  dialog = new SingleDialog({
	        renderTo : 'business',//点击触发对话框ID
	        hiddenTo:'businessIds',//页面与对话框参数传递
			id:'businessDialog',//对话框ID
			typeSearchName:'类型',
			modelSearchName:'业务',
			title:'业务选择对话框',//对话框标题
			url:'terminalDialog_initBussnessDialogData.do'//对话框加载后台数据URL
	      });
		  dialog.init();//初始化页面DOM对象
		  dialog.loadData();//加载后台数据
		  dialog.display();//显示
		}else{
		  dialog.display();//显示(第N次以后只显示对话框即可,提高效率)
		}
		hideMsg();
	}
	
	function checkAllNull(){
	   /* var allvalue = document.getElementById('entity.vcUrl').value
	    				+document.getElementById('entity.vcServerIp').value
	    				+document.getElementById('entity.intPort').value
	    				+document.getElementById('entity.vcApn').value
	    				+document.getElementById('entity.vcUserAgent').value;
		if(allvalue==''){
			document.getElementById('checkinfotd').style["display"]="block";
			document.getElementById('checkinfo').innerText = "Url/IP/Port/APN/UserAgent必须至少有一个不为空";
		}else{
			checkUniqueByAjax();
		}
		*/	
		//checkUniqueByAjax();
		document.getElementById('btn_commit').click();
	}
	
function checkUniqueByAjax(){
	$.ajax({
		type : "post",
		url : "businessMainVetor_checkUnique.do",
		data : {
			keyid:'-1',
			businessIds:$("#businessIds").attr("value"),
			ip:document.getElementById('entity.vcServerIp').value
			//port:document.getElementById('entity.intPort').value,
			//url:document.getElementById('entity.vcUrl').value,
			//apn:document.getElementById('entity.vcApn').value,
			//useragent:document.getElementById('entity.vcUserAgent').value
		},
		success : function(result) {
			if(result=='false'){
				document.getElementById('checkinfotd').style["display"]="block";
				document.getElementById('checkinfo').innerText = "该数据已经存在";
			}else{
				document.getElementById('checkinfo').innerText = "";
				document.getElementById('checkinfotd').style["display"]="none";
				document.getElementById('btn_commit').click();
			}
		},
		error : function() {
			alert('连接服务器出错!');
		}
	});
}
function hideMsg(){
	if(document.getElementById('checkinfotd').style["display"]=="block"){
			document.getElementById('checkinfo').innerText = "";
			document.getElementById('checkinfotd').style["display"]="none";
	}
}
$(document).ready(function(){
    var v = document.getElementById('businessIds').value;
	if(v!=null&&v!=''){
		//document.getElementById('entity.vcServerIp').focus();
		document.getElementById('ips').focus();
	}
});

</script>
