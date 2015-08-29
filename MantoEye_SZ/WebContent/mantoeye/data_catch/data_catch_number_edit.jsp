<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>编辑任务</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/modedialog.css" />
		<script src="/js/paging.js"></script>
		<script src="/js/nav.js"></script>
		<script language="javascript" src="/js/script.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
			<script type="text/javascript" src="/js/common_grid.js">
		<!-- 导入业务选择复选框 -->
		<link rel="stylesheet" href="/mantoeye/dialog/dialog.css" />
		<link rel="stylesheet" href="/tools/jquery/jquery.treeview.css" />
		<script type="text/javascript" src="/mantoeye/dialog/SymbolDialog.js"></script>
		<script type="text/javascript" src="/tools/jquery/jquery.treeview.js"></script>
		<!-- 自动补全JS --> 
		</script><script type="text/javascript" src="/js/common.js"></script>
		<script type="text/javascript">
	//验证表单信息
	$(document).ready( function() {
		//聚焦第一个输入框
			$("#entity.vcTaskName").focus();
			//为beanForm注册validate函数
			$("#beanForm").validate( {
				rules : {
					"entity.vcTaskName" :"required",
					"business" :"required",
					"area" :"required",
					"entity.dtStartTime" :"required",
					"entity.dtEndTime" :"required",
					"entity.bitTaskActiveFlag" :"required"
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
		<form name="beanView" action="/dataCatch_saveUserNumberTask.do"
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
										编辑任务
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
										任务名称：
										<font color="red">*</font>
									</th>
									<td>
										<input type="text" name="entity.vcTaskName"
											style="width: 350px;" id="entity.vcTaskName"
											value="${entity.vcTaskName}" readonly />
										<input type="hidden" name="entity.nmDataGetterTaskId"
											id="entity.nmDataGetterTaskId"
											value="${entity.nmDataGetterTaskId}" />
										<input type="hidden" name="entity.intTaskDelong"
											id="entity.intTaskDelong" value="${entity.intTaskDelong}" />
										<input type="hidden" name="entity.intTaskType"
											id="entity.intTaskType" value="${entity.intTaskType}" />
										<input type="hidden" name="entity.nmTaskOrder"
											id="entity.nmTaskOrder" value="${entity.nmTaskOrder}" />
										<input type="hidden" name="entity.dtOrderTime"
											id="entity.dtOrderTime" value="${entity.dtOrderTime}" />
										<input type="hidden" name="entity.intTaskStatus"
											id="entity.intTaskStatus" value="${entity.intTaskStatus}" />
									</td>
								</tr>
								<tr>
									<th>
										指定业务：
										<font color="red">*</font>
									</th>
									<td>
										<input type="text" id="business" name="business"
											value="${business}" readonly onclick="showBusinessDialog()"
											style="width: 350px; height: 16px;" />
										<div style="height: 0px; border: 0px; overflow: hidden"
											id="business_dialog_hidden_id"></div>
										<input id="businessIds" type="hidden" name="businessIds"
											value="${businessIds}" />
									</td>
								</tr>
								<tr>
									<th>
										指定区域：
										<font color="red">*</font>
									</th>
									<td>
										<input type="text" id="area" name="area" value="${area}"
											readonly onclick="showAreaDialog()"
											style="width: 350px; height: 16px;" />
										<div style="height: 0px; border: 0px; overflow: hidden"
											id="area_dialog_hidden_id"></div>
										<input id="areaIds" name="areaIds" value="${areaIds}"
											type="hidden" />
									</td>
								</tr>
								<tr>
									<th>
										开始时间：
										<font color="red">*</font>
									</th>
									<td>
										<input type="text" class="Wdate" style="width: 352px;"
											readonly
											onclick="showStartForm()"
											name="entity.dtStartTime" id="entity.dtStartTime"
											value="${entity.dtStartTime}" />
									</td>
								</tr>
								<tr>
									<th>
										结束时间：
										<font color="red">*</font>
									</th>
									<td>
										<input type="text" class="Wdate" style="width: 352px;"
											readonly
											onclick="showEndForm()"
											name="entity.dtEndTime" id="entity.dtEndTime"
											value="${entity.dtEndTime}" />
									</td>
								</tr>
								<tr>
									<th>
										是否激活：
										<font color="red">*</font>
									</th>
									<td>
										<select name="entity.bitTaskActiveFlag" id="bitTaskActiveFlag"
											style="width: 356px;">
											<option value="true"
												<c:if test="${entity.bitTaskActiveFlag==true}">selected</c:if>>
												是
											</option>
											<option value="false"
												<c:if test="${entity.bitTaskActiveFlag==false}">selected</c:if>>
												否
											</option>
										</select>
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
function showStartForm(){
		var eed = document.getElementById('entity.dtEndTime').value;
		if(eed==""){
			return new WdatePicker({minDate:'%y-%M-{%d-30} 00:00:00',maxDate:'%y-%M-%d %H:%m:00',dateFmt:'yyyy-MM-dd HH:mm:00'});
		}else{
			return  new WdatePicker({minDate:'#F{$dp.$D(\'entity.dtEndTime\',{d:-7})||\'%y-%M-{%d-30} 00:00:00\'}',maxDate:'#F{$dp.$D(\'entity.dtEndTime\')||\'%y-%M-%d %H:%m:%s\'}',dateFmt:'yyyy-MM-dd HH:mm:00'});
		}
}
function showEndForm(){
		var ssd = document.getElementById('entity.dtStartTime').value;
		if(ssd==""){
			return new WdatePicker({minDate:'%y-%M-{%d-30} 00:00:00',maxDate:'%y-%M-%d %H:%m:00',dateFmt:'yyyy-MM-dd HH:mm:00'});			
		}else{
			return new WdatePicker({minDate:'#F{$dp.$D(\'entity.dtStartTime\')||\'%y-%M-{%d-30} 00:00:00\'}',maxDate:'#F{$dp.$D(\'entity.dtStartTime\',{d:7})||\'%y-%M-%d %H:%m:%s\'}',dateFmt:'yyyy-MM-dd HH:mm:00'});
		}
}
/**
脚本不出错
*/
$(document).ready(function(){
	window.onerror = killErrors;
})
function killErrors() {
	return true;
}

	var businessDialog = null;
	function showBusinessDialog(){
		if(businessDialog == null){
		  businessDialog = new SymbolDialog({
	        renderTo : 'business',//点击触发对话框ID
	        renderTo2:'business_dialog_hidden_id',
	        hiddenTo:'businessIds',//页面与对话框参数传递
			id:'businessDialog',//对话框ID
			hideSelItem:{
				select1:'bitTaskActiveFlag'
			},
			showLoading:true,
			typeSearchName:'类型',
			modelSearchName:'业务',
			title:'业务复选对话框',//对话框标题
			url:'terminalDialog_initBussnessDialogData.do'//对话框加载后台数据URL
	      });
		  businessDialog.init();//初始化页面DOM对象
		  businessDialog.loadData($('#businessIds').attr('value'));//加载后台数据,并初始化
		  businessDialog.display();//显示
		}else{
		  businessDialog.display();//显示(第N次以后只显示对话框即可,提高效率)
		}
		//隐藏select
		$('#bitTaskActiveFlag').css({'display':'none'});
		
	}
	
	var areaDialog = null;
	function showAreaDialog(){
		if(areaDialog == null){
		  areaDialog = new SymbolDialog({
	        renderTo : 'area',//点击触发对话框ID
	        renderTo2:'area_dialog_hidden_id',
	        hiddenTo:'areaIds',//页面与对话框参数传递
			id:'areaDialog',//对话框ID
			hideSelItem:{
				select1:'bitTaskActiveFlag'
			},
			showLoading:true,
			typeSearchName:'类型',
			modelSearchName:'区域',
			title:'区域复选对话框',//对话框标题
			url:'terminalDialog_initAreaDialogData.do'//对话框加载后台数据URL
	      });
		  areaDialog.init();//初始化页面DOM对象
		  areaDialog.loadData($('#areaIds').attr('value'));//加载后台数据
		  areaDialog.display();//显示
		}else{
		   areaDialog.display();//显示(第N次以后只显示对话框即可,提高效率)
		}
		//隐藏select
		$('#bitTaskActiveFlag').css({'display':'none'});
	}
</script>