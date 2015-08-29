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

		<script type="text/javascript" src="/js/common.js"></script>


		<style>
.detailCss {
	border-top: 0px;
	border-left: 0px;
	border-right: 0px;
	border-bottom: 1px solid #94BBE2;
	width: 290px;
}

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
		<form name="beanView" action=""
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

									<th>
										任务名称：
									</th>
									<td>
										<input type="text" name="taskName" id="taskName"
											class="detailCss" readonly
											value="${taskEntity.taskName}" />
										
									</td>
								</tr>

								<tr>
									<th>
										已选择业务：
									
									</th>
									<td>
										<textarea name="business" id="business" rows="5"
											class="detailCss" readonly>${taskEntity.businessNames}</textarea>
										
									</td>

								</tr>
								<tr>

									<th>
										已选择终端：
										
									</th>

									<td>
										<textarea
											name="terminal" id="terminal" readonly rows="5"
											class="detailCss">${taskEntity.terminalNames}</textarea>
										
									</td>

								</tr>
								<tr>
									<th>
										开始时间：
										
									</th>

									<td>
										<input type="text" class="detailCss"
											readonly  name="beginTime"
											id="beginTime" value="${taskEntity.beginTime}" />
									</td>
								</tr>

								<tr>
									<th>
										结束时间：
										
									</th>
									<td>
										<input type="text" class="detailCss"
											readonly name="endTime" id="endTime"
											value="${taskEntity.endTime}" />
									</td>
								</tr>

								<tr>
									<th>
										任务描述：
									</th>
									<td>
										<textarea rows="3"  name="taskDescribe"
											id="taskDescribe" readonly
											class="detailCss">${taskEntity.taskDescribe}</textarea>
									</td>
								</tr>
								<tr style="display: none">
									<th>
										任务状态：
									</th>
									<td>
										<div class="select">
											<div>
												<select name="intTaskStatus" id="intTaskStatus"
													class="detailCss">
													<c:if test="${taskEntity.intTaskStatus==0}">
													<option value="0">
														异常
													</option>
													</c:if>
													<c:if test="${taskEntity.intTaskStatus==1}">
													<option value="1">
														未开始
													</option>
													</c:if>
													<c:if test="${taskEntity.intTaskStatus==2}">
													<option value="2">
														进行中
													</option>
													</c:if>
													<c:if test="${taskEntity.intTaskStatus==3}">
													<option value="3">
														已完成
													</option>
													</c:if>
												</select>
											</div>
										</div>
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
													onclick="window.close();"
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
		</form>
	</body>
</html>

<script type="text/javascript">
	/**
	*
	 */
	$(document).ready(function() {
		window.onerror = killErrors;
	})
	function killErrors() {
		return true;
	}
</script>