<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ page import="java.util.*"%>
	<%@ page import="com.symbol.wp.core.entity.*"%>
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
		<style>
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
	background: url(/skin/Default/images/MantoEye/btn/btn_right.gif)
		no-repeat right top;
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
	background: url(/skin/Default/images/MantoEye/btn/btn_left_no.gif)
		no-repeat left top;
}

#mainbtn a:hover span {
	background: url(/skin/Default/images/MantoEye/btn/btn_right_no.gif)
		no-repeat right top;
}
</style>
		<base target="_self" />
	</head>
	<body style="overflow-x: hidden">
		<form name="beanView" action="/user_insertPermis.do?permId=${permId}"
			method="post">
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
							<table width="100%" border="0" cellpadding="0" cellspacing="0"
								bgcolor="#CEE6F5">
								<tr>
									<td width="40%" height="20">
										隶属角色：
									</td>
									<td width="60%">
										权限预览：
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<table width="100%" border="0" cellspacing="1" cellpadding="0">
											<tr>
												<td width="40%" align="left" valign="top" bgcolor="#FFFFFF"
													height="100%">

													<select name="vcRoleNo" size="15" id="vcRoleNo"
														style="width: 100%;"
														onclick="javascript:permisPreview(this,'${userId}');">
														<%
															List rolesIdList = (List) request.getAttribute("rolesIdList");

															List listdata = (List) request.getAttribute("listdata");
															if (listdata != null && !listdata.isEmpty()) {
																for (Iterator it = listdata.iterator(); it.hasNext();) {
																	TbBaseRoles baseRoles = (TbBaseRoles) it.next();
														%>
														<option value="<%=baseRoles.getId()%>"
															<%if (rolesIdList!=null&&rolesIdList.indexOf(baseRoles.getId())!=-1) out.print("selected");%>>
															<%=baseRoles.getVcRolesName()%>
														</option>
														<%
															}

															}
														%>
													</select>
												</td>
												<td width="60%" align="left" valign="top" bgcolor="#FFFFFF" >
													<div class="dtree"
														style="padding: 10px; overflow-y: hidden ;">
														<tree:perviewPermisTree title="开放权限"></tree:perviewPermisTree>
													</div>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
				</div>
				</td>
				<input type="hidden" id="id" name="id" value='${userId}' />
				<input type="hidden" id="vcUserNo" name="vcUserNo"
					value='${vcUserNo}' />
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
												<a href="#" onclick=" javascript:save();"><span>保存</span>
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
<script language="javascript">
function permisPreview(o, uid){
     $.ajax({
		type : "post",
		url : "user_getAjaxRoleMenuIds.do?1=1",
		data : {
			user_id:uid,
			role_id:o.value
		},
		success : function(msg) {
			var flag = false;
			var data = msg.split(':');
			var $checkObjs = $(".dtree :checkbox");
			$checkObjs.each(function(i){
				$(this).attr("checked",'');
			});
			if(data.length>1){
				for(var j = 0 ; j<data.length;j++){
					$('#cbd'+data[j]).attr("checked",true);//打勾
				}
				flag = true;
			}
			if(flag){
				$('#cbdBASE').attr("checked",true);
			}
		},
		error : function() {
			//alert('服务器出错,请联系管理员!');
		}
	});
}

function save(){
$('#saveId').css('display','none');
beanView.submit();
}
</script>