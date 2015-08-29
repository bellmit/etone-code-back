<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>终端上传</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" type="text/css"
			href="/tools/gt-grid/gt_grid.css" />
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
			type="text/css"></link>
		<link type="text/css" rel="stylesheet"
			href="../../skin//Default/css/maincontent.css" />
		<!-- 请根据语言和系统编码,自行选择引入的 gt_msg_...js 文件 (默认的msg文件为UTF-8编码) -->
		<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js"></script>
		<script type="text/javascript"
			src="/js/jquery/extend.tab/js/Toolbar.js"></script>
		<script src="/js/nav.js"></script>
		<script type="text/javascript" src="/js/common_grid.js"></script>
	</head>

<base target="_self" />
	<body>
		<table id="maincontent" cellspacing="0" cellpadding="0" border="0"
			bgcolor="#D3E0F2" width="100%" height="100%">
			<tr>
				<td colspan="2" height="5px"></td>
			</tr>

			<tr>
				<td>
					<form name="beanForm" action="/terminalmanager_upFile.do"
						id="beanForm" method="post" enctype="multipart/form-data">
						<table cellspacing="0" cellpadding="0" border="0"
							bgcolor="#ffffff" style="width: 100%;">
							<tr>
								<td>
									<table cellspacing="0" cellpadding="0" border="0" width="100%"
										class="menubar">
										<tr valign="top">
											<td width="4px" height="24px">
												<div class="lefttitle"></div>
											</td>
											<td width="100%" height="24px">
												<div class="middletitle">
													终端上传
												</div>
											</td>
											<td width="4px">
												<div class="righttitle"></div>
											</td>
										</tr>

									</table>
								</td>
							</tr>

							<tr>
								<td align="center">
									<div
										style="margin-top: 10px; margin-bottom: 5px; width: 480px; heigth: 280px; border: 0px solid #3286CF">
										<table style="width: 100%; height: 100%" cellspacing="0"
											cellpadding="0">
											<tr>
												<td colspan="2" height="10px"></td>
											</tr>
											<tr>
												<th width="200px;" align="right">
													终端文件：
													<font color="red">*</font>
												</th>
												<td>
													<input type="file" name="file" id="file"
														style="border: 1px solid #999999; height: 20px; width: 360px;" />
												</td>
											</tr>
											<tr>
												<td width="200px;"></td>
												<td style="padding-left: 155px; padding-bottom: 5px;">
													<div id="mainbtn"
														style="margin-top: 5px; text-align: center;">
														<ul>
															<li>
																<a href="#" onclick="submitFrom();"><span>上传</span>
																</a>
															</li>
														</ul>
													</div>
												</td>
											</tr>
										</table>

									</div>
								</td>
							</tr>
						</table>
						</from>
				</td>
				<td width="2px"></td>
			</tr>
		</table>
	</body>
</html>
<script type="text/javascript">
	function submitFrom(){
		if(document.getElementById("file").value==''){
			alert("请选择文件!");
		}else{
			document.beanForm.submit();
		}
	}
</script>




