<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>添加号码</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/modedialog.css" />
		<script src="/js/paging.js"></script>
		<script src="/js/nav.js"></script>
		<script language="javascript" src="/js/script.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
			<script type="text/javascript" src="/js/common_grid.js">
		<!-- 自动补全JS --> 
		</script><script type="text/javascript" src="/js/common.js"></script>
		<script type="text/javascript">
	//验证表单信息
	$(document).ready( function() {
		jQuery.validator.addMethod("mobil2", function(value,element) {
				if(value.length==0){
					return true;
				}
					var reg0 = /^((1[1-9]{1})+\d{0,9}，)*((1[1-9]{1})+\d{0,9})$/;
					var reg1 = /^((1[1-9]{1})+\d{0,9},)*((1[1-9]{1})+\d{0,9})$/;
					var reg2 = /^((1[1-9]{1})+\d{0,9})$/;
					var flag = false;
					if (reg0.test(value)) {
						flag = true;
					}
					if (reg1.test(value)) {
						flag = true;
					}
					if (reg2.test(value)) {
						flag = true;
					}
					return flag;
		}, ' 输入非法手机号码格式');
		var obj = window.dialogArguments;	
		//聚焦第一个输入框
			$("#nmMsisdn").focus();
			$('#taskId').attr('value',obj.taskId);
			//var status = obj.status;
			//为beanForm注册validate函数
			$("#beanForm").validate({
				rules : {
					"nmMsisdn" :{
						required: true,
						mobil2:true,
						//maxlength:10,
						remote:{
        					url:"dataOutput_checkUniqueNmMsisdn.do",
        					type:"post" ,
        					data:{"nmMsisdn":function (){return $('#nmMsisdn').attr('value')},
        							"taskId":obj.taskId
        						
        					}
        					}
					}
				},messages: {
					"nmMsisdn": {
						remote: "用户号码已存在"
					}
				},
				submitHandler: function(form) {   
					//alert(form);
					var ml = (parseInt(window.dialogWidth)-250)/2;
					var mt = (parseInt(window.dialogHeight)-55)/2;
					$('#showmsg').css({"margin-top":0,"margin-left":ml,"display":"block"});
					$('#saveBtn').attr('disabled','disabled');	
					$('#cancelBtn').attr('disabled','disabled');	
					form.submit();  
				}   
			});
			
					
			
			/*if(status=='0'){
			
				$('#intMsisdnStatus1').attr('disabled','disabled');
				$('#intMsisdnStatus2').attr('disabled','disabled');
			}
			if(status=='1'){
				$('#note').css({'display':'block'});
				$('#intMsisdnStatus1').attr('checked',true);	
				$('#intMsisdnStatus1').attr('disabled','disabled');
				$('#intMsisdnStatus2').attr('disabled','disabled');
			}
			if(status=='2'){
				$('#note').css({'display':'block'});
				$('#intMsisdnStatus2').attr('checked',true);	
				$('#intMsisdnStatus1').attr('disabled','disabled');
				$('#intMsisdnStatus2').attr('disabled','disabled');
			}*/
		});
		
		
	
			
			function showDataType(value){
				if(value==1){
					$('#intMsisdnStatus').attr('value',1);	
				}
				if(value==2){
					$('#intMsisdnStatus').attr('value',2);	
				}
			}
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
		<form name="beanView" action="/dataOutput_saveNmMsisdn.do"
			id="beanForm" method="post">
			<table id="maincontent" cellspacing="0" cellpadding="0" border="0"
				width="100%" height="100%">
				
				<tr>
					<td>
						<table cellspacing="0" cellpadding="0" border="0" width="100%"
							class="header">
							<tr>
								<td width="100%" height="24px">
									<div class="title">
										添加号码
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
										用户号码：
										<font color="red">*</font>
									</th>
									<td>
										<input type="text" name="nmMsisdn"
											style="width: 300px;" id="nmMsisdn"
											value=""/>
										<input type="hidden" name='taskId' id="taskId"/>
										<input type="hidden" name='intMsisdnStatus' id="intMsisdnStatus" value="2"/>
									</td>
								</tr>
								<tr>
									<th>
										注：
									</th>
									<td >
										<font color="blue">请输入1开头的手机号码，最少2位数，若有多个号码时用","分隔</font>		
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
										<font color="red">（号码匹配模式已经存在，若需要更改，请将已存在的号码删除！）</font>
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
										<font color="red">（号码匹配模式已经存在，若需要更改，请将已存在的号码删除！）</font>
									</td>
										
								</tr>
								</c:if>
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
													onclick="document.getElementById('btn_commit').click();"  id="saveBtn" name="saveBtn"><span>保存</span>
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
<script>

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