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
	</head>
	<body id="master">

		<form name="beanView"
			action="/dept_update.do?1=1&edit=true&permId=${permId}" id="beanForm"
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
										部门管理&nbsp;&gt;&nbsp;
									</li>
									<li>
										编辑部门
									</li>
								</ul>
							</th>
						</tr>
					</table>
				</div>
			</div>
			<div class="mainarea">
				<!----------- 表单信息 ------------------>
				<table width="100%" cellpadding="0" cellspacing="1" class="formitem">
					<tr>
						<th>
							部门名称：
							<font color="red">&nbsp;&nbsp;</font>
						</th>
						<td>
							<input type="text" name="entity.vcDeptName" id="vcDeptName"
								value="${entity.vcDeptName }">
							<input type="hidden" name="entity.id" id="entity.id"
								value="${entity.id }">
						</td>
					</tr>
					<tr>
						<th>
							上级部门：
							<font color="red">&nbsp;&nbsp;</font>
						</th>
						<td valign="top" bgcolor="#FFFFFF">
							<tree:DeptTreeTag title="请选择上级部门" name="entity.vcParentId"
								selectID="${entity.vcParentId}" action="" />
						</td>
					</tr>
					<tr>
						<th>
							部门描述：
							<font color="red">&nbsp;&nbsp;</font>
						</th>
						<td>
							<textarea name="entity.vcDeptMemo" id="vcDeptMemo" rows="4"
								cols="48">${entity.vcDeptMemo}</textarea>
						</td>
					</tr>
				</table>

				<div style="display: none;">
					<s:submit id="btn_commit"></s:submit>
					<button id="btn_cancel"
						onclick="javascript:goBack('/dept_list.do?1=1&permId=${permId}')"></button>
				</div>

				<jsp:include page="/sysdata/submitTable.jsp"></jsp:include>
			</div>
		</form>
	</body>
</html>
