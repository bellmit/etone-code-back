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
		
		$(document).ready(function(){
			//聚焦第一个输入框
			$("#vcName").focus();
			//为beanForm注册validate函数
			$("#beanForm").validate({
				rules: { 
					"entity.vcName": { 
        				required: true, 
        				remote: "rolegroup_checkUnique.do?vcOldName="+encodeURIComponent(encodeURIComponent('${entity.vcName }'))
        				}
    			},
				messages: {
					"entity.vcName": {
						remote: "角色组名称已存在"
					}
				}
			});
		});
		
		
	</script>
	</head>
	<body id="master">
		<form name="beanView" action="/rolegroup_save.do?1=1&permId=${permId}"
			id="beanForm" method="post">
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
										添加角色组
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
							<input type="text" name="entity.vcName" id="entity.vcName">
						</td>
					</tr>
					<tr>
						<th>
							父角色组：
							<font color="red">&nbsp;</font>
						</th>
						<td>
							<tree:RoleGroupTag title="--父角色组选择--" name="entity.vcParentId"
								selectID="${old_vcParentId}" action="" />
						</td>
					</tr>

					<tr>
						<th>
							说明：
							<font color="red">&nbsp;</font>
						</th>
						<td>
							<textarea name="entity.vcGroupMemo" id="entity.vcGroupMemo"
								rows="3" cols="48"></textarea>
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
