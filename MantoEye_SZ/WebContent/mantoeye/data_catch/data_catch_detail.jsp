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
		<base target="_self" />
		<style>
		.detailCss{
			border-top:0px;border-left:0px;border-right:0px;border-bottom: 1px solid #94BBE2;width:490px;
		}
		</style>
	</head>
	<body style="overflow-x: hidden">
		<form name="beanView" action=""
			id="beanForm" method="post">
			<table id="maincontent" cellspacing="0" cellpadding="0" border="0"
				width="100%">
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
						<div >
							<!----------- 表单信息 ------------------>
							<table width="99%" cellpadding="0" cellspacing="1"
								>
								<tr>
									<td style="text-align: right">
										任务名称：
									</td>
									<td style="width:550px">
										<input type="text" name="entity.vcTaskName"
											id="entity.vcTaskName"
											class='detailCss' 										
											value="${entity.vcTaskName}" readonly/>
									</td>
								</tr>
								<tr>
									<td style="text-align: right">
										提取号码：
									</td>
									<td style="width:550px">
										<textarea name="vcMsisdns" id="vcMsisdns" 
											readonly class='detailCss'>${vcMsisdns}</textarea>
									</td>
								</tr>
								<tr>
									<td style="text-align: right">
										开始时间：
									</td>
									<td style="width:550px">
										<input type="text"
											readonly class='detailCss' 
											value="${entity.dtStartTime}" />
									</td>
								</tr>
								<tr>
									<td style="text-align: right">
										结束时间：
									</td>
									<td style="width:550px">
										<input type="text"
											readonly class='detailCss' 
											value="${entity.dtEndTime}" />
									</td>
								</tr>
								<tr>
									<td style="text-align: right">
										是否激活：
									</td>
									<td style="width:550px">
									<input type="text" name="entity.bitTaskActiveFlag"
											id="entity.bitTaskActiveFlag"
											class='detailCss' 										
											value="${entity.bitTaskActiveFlag==true?'是':'否'}" readonly/>
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