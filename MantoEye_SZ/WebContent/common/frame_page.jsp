<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/include/session.jsp"%>
<%@ include file="/include/setcache.jsp"%>


<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<title></title>
		<link rel="stylesheet" type="text/css" href="/css/content_default.css">
		<link rel="stylesheet" type="text/css"
			href="/css/content_component.css">
		<link rel="stylesheet" type="text/css" href="/css/cssbtn.css">
		<link rel="stylesheet" type="text/css" href="/css/csstab.css">
		<script src="/js/paging.js"></script>
		<script src="/js/nav.js"></script>
		<script src="/js/map.js"></script>
	</head>
	<body>
		<table width="100%" height="24" background="/images/tab/line.gif"
			border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="10">
					<img src="/images/tab/line.gif" width="6" height="24"
						align="middle">
				</td>
				<td>
					<div id="tab120">
						<ul>
							<li>
								<a name="pageLink"
									href="/marketing_do.jhtml?method=associateHE&flag=W&id=${id}"
									onclick="pageItem(this,'pageLink')"><b>定制事件</b> </a>
							</li>
							<li>
								<a name="pageLink"
									href="/marketing_do.jhtml?method=associateHE&flag=R&id=${id}"
									onclick="pageItem(this,'pageLink')">已定制事件 </a>
							</li>
						</ul>
					</div>
				</td>
			</tr>
		</table>
		<iframe name="ifrDialogContent" src="" frameborder="0" width="100%"
			height="100%"></iframe>
	</body>
</html>
<script language="javascript">
function associate(id){
	var countNum = getSelectCount(id);
	if(countNum<1){
		alert("请选择项！");
	}else{
		if(confirm("你确定要关联？")){
			var arrayId = getSelectValue(id);
			document.location.href="/marketing_do.jhtml?method=insertHERelationRecord&flag=W&keys="+arrayId+"&id=${id}";
		}
		//return false;	
	}
}
</script>