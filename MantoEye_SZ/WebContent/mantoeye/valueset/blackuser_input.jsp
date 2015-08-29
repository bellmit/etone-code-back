<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/include/session.jsp"%>
<%@ include file="/include/setcache.jsp"%>
<html>
	<head>
		<title></title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/maincontent.css" />
	</head>
	<body id="master">
	<table cellspacing="0" cellpadding="0" border="0" width="100%" height="100%" >
		<tr>
			<td height="5px" width="100%" colspan="2"></td>
		</tr>
		<tr>
					<td>
						<table class="menubar" cellspacing="0" cellpadding="0" border="0" width="100%" >
							
						</table>
					</td>
					<td width="2px">
					
					</td>		
		</tr>
						
						
	</table>
		<form name="beanView"
			action="/blackuser_list.do?1=1&permId=${permId}" id="beanForm"
			method="post">
			<div class="mainarea">
				<!----------- 表单信息 ------------------>
				<div style="width: 100%; height: 20px;">
					<table width="100%" cellpadding="0" cellspacing="1"
						class="formitem">
						<tr>
							<th>
							</th>
							<td>
								<input type="radio" name="check" checked onclick="chageState();" style="width:20px;">手动添加黑名单用户&nbsp;&nbsp;
								<input type="radio" name="check" onclick="chageState();" style="width:20px;">批量添加黑名单用户
							</td>
						</tr>
					</table>
				</div>
				<div id="handle" style="width: 100%; height: auto;">
					<table width="100%" cellpadding="0" cellspacing="1"
						class="formitem">
						<tr>
							<th>
								号码：
								<font color="red">*</font>
							</th>
							<td>
								<textarea rows="10" cols="20"
									style="border: 1px solid #999999; height: 120px;"></textarea>
							</td>
						</tr>
						<tr>
							<th>
							</th>
							<td>
								<span>
									<font color="red">注：</font><font color="blue">号码需要86开头,多个号码用换行分隔</font>
								</span>
							</td>
						</tr>
					</table>
				</div>

				<div id="file" style="display: none; width: 100%; height: auto;">
					<table width="100%" cellpadding="0" cellspacing="1"
						class="formitem">
						<tr>
							<th>
								号码文件：
								<font color="red">*</font>
							</th>
							<td>
								<input type="file" style="border: 1px solid #999999; height: 20px;width:200px;">
							</td>
						</tr>
						<tr>
							<th>
							</th>
							<td>
								<span>
									<font color="red">注：</font><font color="blue">号码需要86开头,多个号码用换行分隔</font>
								</span>
							</td>
						</tr>
					</table>
				</div>
				
				<div style="display: none;">
					<s:submit id="btn_commit"></s:submit>
					<button id="btn_cancel" onclick="goBack('/blackuser_list.do?1=1')"></button>
				</div>
				<jsp:include page="/sysdata/submitTable.jsp"></jsp:include>
			</div>
		</form>
	</body>

	<script type="text/javascript">
		function chageState(){
			var check=document.beanView.check;
			if(check[0].checked==true){
				document.getElementById("handle").style.display="block";
				document.getElementById("file").style.display="none";
			}else{
				document.getElementById("handle").style.display="none";
				document.getElementById("file").style.display="block";
			}
		}
	</script>
</html>
