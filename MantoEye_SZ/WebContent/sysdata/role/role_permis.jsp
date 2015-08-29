<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>权限设置</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<script src="/js/paging.js"></script>
		<script src="/js/nav.js"></script>
		<script language="javascript" src="/js/script.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>  
		<script language="javascript" src="/js/script.js"></script>
		<script language="javascript" src="/common/dtree/dtree.js"></script>
		<link rel="StyleSheet" href="/common/dtree/dtree.css" type="text/css" />
		<script type="text/javascript">
	 function checkForm(o)
			 {
			 
			     var obj = o.getElementsByTagName("input");
		
			     var layers,mytest;
			     var markCount = obj.length;
			     var chkObj = null;
			     var hdnObj = null;
			     var names = ",";
			     for (i = 1; i < markCount; i++)
			     {
			         chkObj = obj[i];
			         try
			         {
			             mytest += chkObj.value + ",";
			         }
			         catch(e)
			         {
			         }
			         if (chkObj.type.toUpperCase() == "CHECKBOX") {
			             if (chkObj.checked == true)
			             {
			                 names += chkObj.value + ",";
			             }
			         }
			         else if (chkObj.name.toUpperCase() == "LAYERS")
			         {
			             layers = chkObj;
			         }
			     }
			     layers.value = names;

			    // document.getElementById("submit_button").disabled = true;
				 $('#saveId').css('display','none');
			     return true;
			}	
</script>
<style>
@charset "utf-8";

body {
	margin: 0px;
	padding: 0px;
	font: normal 12px arial, tahoma, helvetica, sans-serif;
}



#maincontent {
	width: 100%;
	height: 100%;
}


/*button Start*/
#mainbtn {
	float: left;
	FONT: 9pt 宋体, arial, helvetica, sans-serif;
	letter-spacing: 3pt;
}

#mainbtn ul {
	margin: 0;
	padding: 0px;
	list-style: none;
	float: left;
}

#mainbtn li {
	display: inline;
	padding-top: 0px;
	padding-bottom: 2px;
	margin: 0px;
}

#mainbtn a {
	float: left;
	width: 46px;
	background: url(../images/MantoEye/btn/btn_left.gif) no-repeat left top;
	height: 18px;
	margin-left: 5px;
	padding: 0px 0px 0px 2px;
	text-decoration: none;
	cursor: pointer;
}

#mainbtn a span {
	float: left;
	display: block;
	background: url(/skin/Default/images/MantoEye/btn/btn_right.gif) no-repeat right
		top;
	height: 12px;
	padding: 4px 3px 5px 8px;
	color: #143262;
}

/* Commented Backslash Hack hides rule from IE5-Mac \*/
#mainbtn a span {
	float: none;
}

/* End IE5-Mac hack */
#mainbtn a:hover span {
	color: #003399;
}

#mainbtn a:hover {
	background: url(/skin/Default/images/MantoEye/btn/btn_left_no.gif) no-repeat left
		top;
}

#mainbtn a:hover span {
	background: url(/skin/Default/images/MantoEye/btn/btn_right_no.gif) no-repeat right
		top;
}

</style>
		<base target="_self" />
	</head>
	<body style="overflow-x: hidden">
		<form name="beanView"
			action="/role_setRolePermis.do?1=1&permId=${permId}" method="post">
			<table id="maincontent" cellspacing="0" cellpadding="0" border="0"
				width="100%" height="100%">
				<tr>
					<td>
						<table cellspacing="0" cellpadding="0" border="0" width="100%"
							class="header">
							<tr>
								<td width="100%" height="24px">
									<div class="title"
										style="height: 17px; background: url(/skin/Default/images/MantoEye/maincontent/middletitle.gif) repeat-x; padding-left: 30px; padding-top: 7px; font-size: 13px; color: #FFFFFF;; font-weight: bold;">
										权限设置
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
							<table cellpadding="0" cellspacing="1" class="formitem">
								<tr>
									<td>
										<tree:roleMenuTree role_id="${baseRoles.id}"
											roleNo="${tbRoles.id }" disabled="0" title="开放菜单"></tree:roleMenuTree>
										<input type="hidden" id="vcRoleNo" name="vcRoleNo"
											value='${tbRoles.id}' />
										<input type="hidden" id="layers" name="layers" value="" />
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
												<a href="#" onclick="checkForm(beanView);beanView.submit();"><span>保存</span>
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
