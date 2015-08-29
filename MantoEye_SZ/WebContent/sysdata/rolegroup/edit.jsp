<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/include/session.jsp"%>
<%@ include file="/include/setcache.jsp"%>
<html>
	<head>
		<title></title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/style.css" />
		<script src="/js/paging.js"></script>
		<script src="/js/nav.js"></script>
		<script language="javascript" src="/js/script.js"></script>
		<script language="javascript" src="/common/dtree/dtree.js"></script>
		<link rel="StyleSheet" href="/common/dtree/dtree.css" type="text/css">
		<script type="text/javascript" src="/common/dtree/dtree.js"></script>
		<script type="text/javascript" src="/common/dtree/dltree.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
		<script>
		
		
		
		
	</script>
	</head>
	<body id="master">
		<form name="beanView"
			action="/rolegroup_update.do?1=1&permId=${permId}" id="beanForm"
			method="post">

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
										角色组管理&nbsp;&gt;&nbsp;
									</li>
									<li>
										编辑角色组
									</li>
								</ul>
							</th>
						</tr>
					</table>
				</div>
			</div>
			<div id="mainarea">
				<table width="100%" cellpadding="0" cellspacing="1" class="formitem">

					<tr>
						<th>
							角色组名称：
							<font color="red">*</font>
						</th>

						<td>
							<input type="text" name="entity.vcName" id="vcName"
								value="${entity.vcName}">
							<input type="hidden" name="entity.id" id="id"
								value="${entity.id}">
						</td>
					</tr>
					<tr>
						<th>
							父角色组：
							<font color="red">&nbsp;</font>
						</th>
						<td>
							<tree:RoleGroupTag title="--父角色组选择--" name="entity.vcParentId"
								selectID="${entity.vcParentId}" action="" />
						</td>
					</tr>

					<tr>
						<th>
							说明：
							<font color="red">&nbsp;</font>
						</th>
						<td>
							<textarea name="entity.vcGroupMemo" id="vcGroupMemo" rows="3"
								cols="48">${entity.vcGroupMemo}</textarea>
						</td>
					</tr>
				</table>

				<div style="display: none;">
					<s:submit id="btn_commit"></s:submit>
					<button id="btn_cancel"
						onclick="goBack('/rolegroup_list.do?1=1&permId=${permId}&nmRolesLevel=-1')"></button>
				</div>

				<jsp:include page="/sysdata/submitTable.jsp"></jsp:include>
			</div>
		</form>
	</body>
</html>
