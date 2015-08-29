<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>编辑已有业务拨测任务</title>
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
			$("#entity.vcTaskName").focus();
			//为beanForm注册validate函数
			$("#beanForm").validate( {
				rules : {
					"business" :"required",
					"entity.vcTaskName" :{ 
        				required: true, 
        				remote: "businessRuleTest_checkUnique.do?oldName="+encodeURIComponent(encodeURIComponent('${entity.vcTaskName}'))
        			},
					"entity.dtStartTime" :"required",
					"entity.dtEndTime" :"required",
					"entity.bitTaskActiveFlag" :"required",
					"vcMsisdns": {
    					required: true,
    					mobil:true,
    					maxlength:239
    				}
				},messages: {
					"vcMsisdns": {
						maxlength: "号码最多为20个"
					},"entity.vcTaskName": {
						remote: "任务名称不能重复"
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
		});
</script>
		<base target="_self" />
	</head>
	<body style="overflow-x: hidden">
		<form name="beanView" action="/businessRuleTest_saveTask.do" id="beanForm"
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
										编辑已有业务拨测任务
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
										任务名称：
										<font color="red">*</font>
										<input type="hidden" name="entity.intTaskDelong" id="entity.intTaskDelong" value="2" />
										<input type="hidden" name="entity.intTaskType" id="entity.intTaskType" value="5" />
										<input type="hidden" name="entity.dtOrderTime" id="entity.dtOrderTime" value="${entity.dtOrderTime }" />
										<input type="hidden" name="entity.nmTaskOrder" id="entity.nmTaskOrder" value="${entity.nmTaskOrder }" />
										<input type="hidden" name="entity.intTaskStatus" id="entity.intTaskStatus" value="${entity.intTaskStatus }" />	
										<input type="hidden" name="entity.nmDataGetterTaskId" id="entity.nmDataGetterTaskId" value="${entity.nmDataGetterTaskId}" />									
									</th>
									<td>
										<%--<input type="text" name="entity.vcTaskName"
											style="width: 270px;" id="entity.vcTaskName"
											value="${entity.vcTaskName}" readonly/> --%>
											<span>${entity.vcTaskName}</span>
																			</td>
								</tr>
								<tr>
									<th>
										开始时间：
										<font color="red">*</font>
									</th>
									<td>
										<input type="text" class="Wdate" style="width: 270px;"
											readonly
											onclick="new WdatePicker({minDate:'%y-%M-%d %H:%m:%s',maxDate:'#F{$dp.$D(\'entity.dtEndTime\')}',dateFmt:'yyyy-MM-dd HH:mm:00'});"
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
										<input type="text" class="Wdate" style="width: 270px;"
											readonly
											onclick="new WdatePicker({minDate:'#F{$dp.$D(\'entity.dtStartTime\')||\'%y-%M-%d %H:%m:%s\'}',dateFmt:'yyyy-MM-dd HH:mm:00'});"
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
										<select name="entity.bitTaskActiveFlag"
											id="entity.bitTaskActiveFlag" style="width: 274px;">
											<option value="true" <c:if test="${entity.bitTaskActiveFlag}">selected</c:if>>
												是
											</option>
											<option value="false" <c:if test="${!entity.bitTaskActiveFlag}">selected</c:if>>
												否
											</option>
										</select>
									</td>
								</tr>
								<tr>
									<th>
										业务：
										<font color="red">*</font>
									</th>
									<td>
									<input id="businessIds" type="hidden" value="${busiid }" name="businessIds" />
									<input type="text" value="${businame}"
										id="business" onclick="showDialog()" name="business"
										style="display: block;font-size: 11px; color: #163877;width: 270px;" />										
									</td>
								</tr>
								<tr>
									<th>
										提取号码：
										<font color="red">*</font>
									</th>
									<td>
										<textarea rows="6" name="vcMsisdns" style="width: 270px;"
											id="vcMsisdns"
											><c:out value="${vcMsisdns}"></c:out></textarea>
									</td>
								</tr>
								<tr>
									<th></th>
									<td>
										<span><font color="red">注：</font><font color="blue">号码可输入多个,每个以逗号分隔符隔开。</font>
										</span>
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
}
	/**
	*
	*/
	$(document).ready(function(){
		window.onerror = killErrors;
	})
	function killErrors() {
		return true;
	}
</script>