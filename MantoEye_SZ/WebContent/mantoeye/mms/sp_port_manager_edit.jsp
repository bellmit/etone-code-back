<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<%@taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/style.css" />
		<title>彩信端口信息添加页面</title>
		<script>
		 
		function checkInput()
        {            
            var ml = (parseInt(window.dialogWidth)-250)/2;
					var mt = (parseInt(window.dialogHeight)-55)/2;
					$('#showmsg').css({"margin-top":mt,"margin-left":ml,"display":"block"});
					$('#saveBtn').attr('disabled','disabled');	
					$('#cancelBtn').attr('disabled','disabled');	
            beanView.submit();
        }
        
       
		</script>
		<base target="_self" />
	</head>

	<body id="master">
		<form name="beanView"
			action="/spPortControl_edit.do?1=1&permId=${permId }" id="beanForm"
			method="post" onsubmit="return true;">
			<div id="mainareacontent">
				<div class="mainarea">
					<table width="100%" cellpadding="0" cellspacing="1"
						class="formitem">
						<caption>
							基本信息
						</caption>
						<tr>
							<th>
								彩信端口号:
							</th>
							<td>
								<input type="text" style="width: 66%" name="spPortBean.spPort"
									id="spPort" readonly="readonly" value="${spPortBean.spPort }" />
							</td>
						</tr>
						<tr>
							<th>
								彩信所属:
							</th>
							<td>
								<input type="text" style="width: 66%" name="spPortBean.beLong" value="${spPortBean.beLong }" />
							</td>
						</tr>
						<tr>
							<th>
								公司名称:
							</th>
							<td>
								<input type="text" style="width: 66%"
									name="spPortBean.companyName" value="${spPortBean.companyName }" />
							</td>
						</tr>
						<tr align="center">
									<td align="center" id="showmsg" name="showmsg" colspan="2"
										style="text-align: center; display: none;">
										<img src="/skin/Default/images/icon/16/loading.gif"></img>
										操作进行中,请稍后...
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
														<a href="#" onclick="checkInput()"  id="saveBtn" name="saveBtn"><span>保存</span> </a>
													</li>
													<li>
														<a href="#" onclick="window.close();"  id="cancelBtn" name="cancelBtn"><span>关闭</span>
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
				</div>
			</div>
		</form>
	</body>
</html>
