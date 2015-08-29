<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>任务详情</title>
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
		<style>
		.detailCss{
			border-top:0px;border-left:0px;border-right:0px;border-bottom: 1px solid #94BBE2;width:390px;
		}
		</style>
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
										任务详情
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
							<table width="99%" cellpadding="0" cellspacing="1">
								<tr>
									<td style="text-align: right;width:100px">
										任务名称：
									</td>
									<td>
									<input type="text" class='detailCss' 	
											readonly
											name="entity.vcTaskName" id="entity.vcTaskName"
											value="${entity.vcTaskName}" />
									</td>		
								</tr>
								<tr>
									<td style="text-align: right">
										开始时间：
										
									</td>
									<td>
										<input type="text" class='detailCss' 	
											readonly
											name="entity.dtStartTime" id="entity.dtStartTime"
											value="${entity.dtStartTime}" />
									</td>
								</tr>
								<tr>
									<td style="text-align: right">
										结束时间：
										
									</td>
									<td>
										<input type="text" class='detailCss' 	
											readonly
											name="entity.dtEndTime" id="entity.dtEndTime"
											value="${entity.dtEndTime}" />
									</td>
								</tr>
								<tr>
									<td style="text-align: right">
										是否激活：
										
									</td>
									<td>
											<input type="text" name="entity.bitTaskActiveFlag"
											id="entity.bitTaskActiveFlag"
											class='detailCss' 										
											value="${entity.bitTaskActiveFlag==true?'是':'否'}" readonly/>
									</td>
								</tr>
								<tr>
									<td style="text-align: right">
										业务：
										
									</td>
									<td>
									<input type="text" value="${businame}"
										id="business"  name="business"
										class='detailCss' 	/>										
									</td>
								</tr>
								<tr>
									<td style="text-align: right">
										提取号码：
										
									</td>
									<td>
										<textarea rows="1" name="vcMsisdns" 
											id="vcMsisdns" class='detailCss' 
											><c:out value="${vcMsisdns}"></c:out></textarea>
									</td>
								</tr>
								
							</table>
						</div>
					</td>
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
													onclick="window.close()" id="cancelBtn" name="cancelBtn"><span>关闭</span>
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
	*
	*/
	$(document).ready(function(){
		window.onerror = killErrors;
	})
	function killErrors() {
		return true;
	}
	$(document).ready(function(){
	var st = $('#vcMsisdns').val();
	var ms = st.split(',');
	var s = parseInt(ms.length/3);
	if(ms.length%3!=0){
		s=s+1;
	}
	$('#vcMsisdns').attr('rows',s);
});
</script>