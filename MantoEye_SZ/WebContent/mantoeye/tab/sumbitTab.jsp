<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/include/session.jsp"%>
<%@ include file="/include/setcache.jsp"%>
<html>
	<head>
		<title>无标题文档</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	</head>

	<body>
		<form action="/role_list.do?1=1&permId=SYSTEM_MANAGER_ROLE" name="searchForm"
			id="mainForm" method="post">
			<ul>
				<li>
					<a href="javascript:submitForm();"><span>添加</span> </a>
				</li>
			</ul>
			<input type="button" value="button" id="submit_id"
				onclick="submitForm()" />
			<input type="submit" value="submit" />
			<img style="cursor: hand"
				src="/skin/Default/images/icon/16/delete_file.gif"
				onclick="submitForm()" />
		</form>
	</body>
</html>
<script language="javascript">
	 function submitForm(){
	     //document.tthis.myForm.submit();
	     //alert(top.frames[1].document.getElementsByName("ii")[0].id);
	     //top.frames[1].document.forms[0].submit();
	     //top.frames[1].document.getElementsByName("ii")[0].contentWindow.document.getElementById("myForm").submit();
	    searchForm.submit();
	 }
	 
</script>