<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>回复反馈</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/modedialog.css" />
		<script src="/js/paging.js"></script>
		<script src="/js/nav.js"></script>
		<script language="javascript" src="/js/script.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
		<script type="text/javascript" src="/sysdata/feedback/feedback.js"></script>
		<link type="text/css" rel="stylesheet"
			href="/sysdata/feedback/feedback.css" />
		<style type="text/css">

</style>
		<script language="javascript" type="text/javascript">
		$(document).ready(function(){
			//聚焦第一个输入框
			$("#entity.clContent").focus();
			//为beanForm注册validate函数
			$("#beanForm").validate({
			rules: {
				"entity.clContent": "required",
				"entity.vcCreater": "required"
				}
			});
		});
		$(document).ready(function(){
			$('.replay_content_div').each(function (i){
				replayContent($(this));
			});
		});
		
    </script>
		<base target="_self" />
	</head>
	<body style="overflow-x: hidden">
		<table id="maincontent" cellspacing="0" cellpadding="0" border="0"
			width="100%">
			<tr>
				<td>
					<table cellspacing="0" cellpadding="0" border="0" width="100%"
						class="header">
						<tr>
							<td width="100%" height="24px">
								<div class="title">
									回复反馈
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<form name="beanView" action="/feedback_replyFeedback.do"
			id="beanForm" method="post" style="padding-left: 30px;">
			<table>
				<tr>
					<td>
						主题：${parentEntity.vcTitle}
					</td>
				</tr>
				<tr>
					<td>
						<div class="replay_content_div">
							${parentEntity.clContent}
						</div>
					</td>
				</tr>
				<tr>
					<td>
						回复内容：
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td>
						<textarea name="entity.clContent" id="entity.clContent" rows="10"
							cols="48" class="replay_content_textarea"></textarea>
					</td>
				</tr>
				<tr>
					<td>
						回复者：
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td>
						<input type="text" name="entity.vcCreater" id="entity.vcCreater"
							style="border: 1px solid #ccc;" />
						<input type="hidden" name="entity.vcParentId" id="vcParentId"
							value="${parentEntity.id }" />
					</td>
				</tr>
			</table>
			<div style="display: none;">
				<s:submit id="btn_commit"></s:submit>
				<button id="btn_cancel" onclick="window.close()"></button>
			</div>
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
													onclick="document.getElementById('btn_commit').click();"><span>保存</span>
												</a>
											</li>
											<li>
												<a href="#"
													onclick="document.getElementById('btn_cancel').click();"><span>关闭</span>
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
