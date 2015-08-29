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
.detailCss {
	border-top: 0px;
	border-left: 0px;
	border-right: 0px;
	border-bottom: 1px solid #94BBE2;
	width: 490px;
}

</style>
	</head>
	<body style="overflow-x: hidden">
		<form name="beanView" action="" id="beanForm" method="post">
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
							<table width="100%" cellpadding="0" cellspacing="1"
								id="content_table_id"
								style="width: 100%; height: auto; margin: 0px; padding: 0px;">
								<tr>
									<td style="text-align: right">
										任务名称：
									</td>
									<td>
										<input type="text" name="entity.vcTaskName" class="detailCss"
											id="entity.vcTaskName" value="${entity.vcTaskName}" readonly />
									</td>
								</tr>
								<tr>
									<td style="text-align: right">
										指定业务：
									</td>
									<td>
									<textarea name="business" id="business" rows="5"
											readonly class='detailCss'
											style="overflow-y: auto; ">${business}</textarea>
									</td>
								</tr>
								<tr>
									<td style="text-align: right">
										指定区域：
									</td>
									<td>
									<textarea name="area" id="area" rows="5"
											readonly class='detailCss'
											style="overflow-y: auto; ">${area}</textarea>
									</td>
								</tr>
								<tr>
									<td style="text-align: right">
										开始时间：
									</td>
									<td>
										<input type="text" readonly class="detailCss"
											name="entity.dtStartTime" id="entity.dtStartTime"
											value="${entity.dtStartTime}" />
									</td>
								</tr>
								<tr>
									<td style="text-align: right">
										结束时间：
									</td>
									<td>
										<input type="text" readonly class="detailCss"
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
											id="entity.bitTaskActiveFlag" class='detailCss'
											value="${entity.bitTaskActiveFlag==true?'是':'否'}" readonly />
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
												<a href="#" onclick=
	window.close();
id="cancelBtn"
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
		</form>
	</body>
</html>
<script>
	/**
	 脚本不出错
	 */
	$(document).ready(function() {
		window.onerror = killErrors;
	})
	function killErrors() {
		return true;
	}
</script>