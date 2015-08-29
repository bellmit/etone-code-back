<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.symbol.wp.core.entity.*"%>
<%@ include file="/include/session.jsp"%>
<%@ include file="/include/setcache.jsp"%>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title></title>
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/style.css" />
		<link rel="stylesheet" type="text/css" href="/css/content_default.css">
		<link rel="stylesheet" type="text/css"
			href="/css/content_component.css">
		<link rel="stylesheet" type="text/css" href="/css/cssbtn.css">
		<link rel="stylesheet" type="text/css" href="/css/csstab.css">
		<link rel="StyleSheet" href="/common/dtree/dtree.css" type="text/css">
		<script type="text/javascript" src="/common/dtree/dtree.js"></script>
		<script type="text/javascript" src="/common/dtree/dltree.js"></script>
		<script src="/js/paging.js"></script>
		<script src="/js/nav.js"></script>
	</head>
	<body>
		<form name="SubForm" id="SubForm"
			action="/user_insertPermis.do?permId=${permId}" method="post">
			<div class="header">
				<div class="hand"></div>
				<div class="local">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<th>
								<ul>
									<li>
										您的位置: 系统管理&nbsp;&gt;&nbsp;
									</li>
									<li>
										用户管理&nbsp;&gt;&nbsp;
									</li>
									<li>
										权限分配
									</li>
								</ul>
							</th>
						</tr>
					</table>
				</div>
			</div>
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				bgcolor="#CEE6F5">
				<tr>
					<td width="40%" height="25">
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

									<select name="vcRoleNo" size="28" id="vcRoleNo"
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
								<td width="60%" align="left" valign="top" bgcolor="#FFFFFF">
									<div class="dtree"
										style="padding: 10px; overflow-y: scroll; height: 100%;">
										<tree:perviewPermisTree title="开放权限"></tree:perviewPermisTree>
									</div>
								</td>
							</tr>
						</table>
				</tr>
			</table>

			<div style="display: none;">
				<s:submit id="btn_commit"></s:submit>
				<button id="btn_cancel"
					onclick="javascript:goBack('/user_list.do?1=1&permId=${permId}')"></button>
			</div>

			<jsp:include page="/sysdata/submitTable.jsp"></jsp:include>

			<input type="hidden" id="id" name="id" value='${userId}'>
			<input type="hidden" id="vcUserNo" name="vcUserNo"
				value='${vcUserNo}'>
		</form>
	</body>
</html>
<script language="javascript">
function permisPreview(o, uid){
    if (window.event.shiftKey || window.event.ctrlKey){
        var len = o.options.length;
        var list = [];
        for (var i = 0; i < len; i++){
            if (o.options[i].selected){
                list[list.length] = o.options[i].value;
            }
        }
        document.onkeyup = function(){
            location.href = "user_openPermis.do?1=1&permId=${permId}&id=" + uid + "&role_id=" + list.toString();
        }
    }
    else{
       // location.href = "user_openPermis.do?1=1&permId=${permId}&id=" + uid + "&role_id=" + o.value;
       	 document.getElementById("id").value=uid;
         document.SubForm.action= "user_openPermis.do?1=1&permId=${permId}&role_id=" + o.value;
    	 SubForm.submit();
    }
}
</script>