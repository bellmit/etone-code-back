<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.symbol.wp.core.constants.*"%>
<%@ page import="org.springframework.web.util.WebUtils"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<%--<link rel="stylesheet" type="text/css" href="/css/ext-all.css">
	<script type='text/javascript' src='/js/ext-base.js'></script>
	<script type='text/javascript' src='/js/ext-all.js'></script>
	<script type="text/javascript" src="lhgdialog.js"></script> --%>
<script src="/js/table.js" type="text/javascript"></script>

<body>
<form id="" method="post"><jsp:include
	page="/include/pageparm.jsp"></jsp:include></form>
</body>
</html>
<%
	Map<String, String> filterParamMap = (Map<String, String>) WebUtils
			.getParametersStartingWith(request, "filter_");
	request.setAttribute("param", filterParamMap);
	String permId = (String) request.getParameter("permId");
	String succ_code = (String) request
			.getAttribute(VarConstants.SUCC_CODE);
	String url = (String) request.getAttribute(VarConstants.URL);
	url = url + "&permId=" + permId;

	String Warm = (String) request.getAttribute("Warm");
	String msg = (String) request.getAttribute("message");
%>

<%
	if (succ_code.equals("00001")) {
%>
<script language="javascript">
	alert("数据添加成功！");	
//	lhgdialog.opendlg( '数据添加成功', '_content/content1.html', 300, 150, true );
	document.forms[0].action='<%=url%>';
	document.forms[0].submit();		
</script>
<%
	}
	if (succ_code.equals("00002")) {
%>
<script language="javascript">
	alert("数据更新成功！");
	document.forms[0].action='<%=url%>';
	document.forms[0].submit();	
</script>
<%
	}
	if (succ_code.equals("00003")) {
%>
<script language="javascript">
	alert("密码初始化为'000000'成功！");
    document.forms[0].action='<%=url%>';
	document.forms[0].submit();	
</script>
<%
	}
	if (succ_code.equals("00004")) {
%>
<script language="javascript">
//	alert("添加"++"条记录成功");
	document.location.href='<%=url%>';
</script>
<%
	}
	if (succ_code.equals("00005")) {
%>
<script language="javascript">
	alert("更新用户权限成功!");
	document.forms[0].action='<%=url%>';
	document.forms[0].submit();	
</script>
<%
	}
	if (succ_code.equals("00007")) {
%>
<script language="javascript">
	alert("角色关联用户成功");
	document.forms[0].action='<%=url%>';
	document.forms[0].submit();
</script>
<%
	}
	if (succ_code.equals("00008")) {
%>
<script language="javascript">
	alert("删除数据成功");
	document.forms[0].action='<%=url%>';
	document.forms[0].submit();	
</script>
<%
	}
	if (succ_code.equals("00026")) {
%>
<script language="javascript">
 alert("密码修改成功！");
	document.forms[0].action='<%=url%>';
	document.forms[0].submit();	
</script>
<%
	}
	if (succ_code.equals("00027")) {
%>
<script language="javascript">
	alert("删除角色用户成功!");
	document.forms[0].action='<%=url%>';
	document.forms[0].submit();	
</script>
<%
	}
	if (succ_code.equals("00028")) {
%>
<script language="javascript">
	alert("关联角色用户成功!");
	document.forms[0].action='<%=url%>';
	document.forms[0].submit();	
</script>

<%
	}
	if (succ_code.equals("00029")) {
%>
<script language="javascript">
	alert("角色分配权限成功!");
	document.forms[0].action='<%=url%>';
	document.forms[0].submit();	
</script>

<%
	}
	if (succ_code.equals("00038")) {
%>
<script language="javascript">
	alert('<%=Warm%>');
	document.location.href='<%=url%>';
</script>

<%
	}
	if (succ_code.equals("00101")) {
%>
<script language="javascript">
	alert("添加成功!");
	window.returnValue ='add'; //设置模式返回值为添加状态，如果是添加成功则刷新grid列表数据
	window.close();
	//parent.reloadData();
</script>
<%
	}
	if (succ_code.equals("00102")) {
%>
<script language="javascript">
	alert("编辑成功!");	
	window.returnValue ='edit'; 
	window.close();
	//parent.reloadData();
</script>
<%
	}
	if (succ_code.equals("00202")) {
%>
<script language="javascript">
	alert("编辑失败,该任务已经开始!");	
	window.returnValue ='edit'; 
	window.close();
	//parent.reloadData();
</script>
<%
	}
	if (succ_code.equals("00104")) {
%>
<script language="javascript">
	alert("权限设置成功!");	
	window.returnValue ='role'; 
	window.close();
</script>
<%
	}
	if (succ_code.equals("00105")) {
%>
<script language="javascript">
	alert("回复成功!");	
	window.returnValue ='reply'; 
	window.close();
</script>
<%
	}
	if (succ_code.equals("00106")) {
%>
<script language="javascript">
	alert("修改密码成功!");	
	window.returnValue ='modifyPW'; 
	window.close();
</script>
<%
	}
	if (succ_code.equals("00107")) {
%>
<script language="javascript">
	alert("导入成功!");	
	window.returnValue ='upload'; 
	window.close();
</script>
<%
	}
	if (succ_code.equals("00006")) {
%>

<script language="javascript">
	alert("文件上传成功!");	
	window.returnValue ='up'; 
	window.close();
	//parent.reloadData();
</script>
<%
	}
	if (succ_code.equals("00103")) {
%>
<script language="javascript">
	alert("编辑成功!");	
	window.returnValue ='up'; 
	window.close();
	//parent.reloadData();
</script>

<%
	}
	if (succ_code.equals("0000110")) {
%>

<script language="javascript">
	var ms='<%=msg%>';
	window.returnValue = ms;
	window.close();
	//parent.reloadData();
</script>

<%
	}
	if (succ_code.equals("10000")) {
%>

<script language="javascript">
alert('取消告警操作完成！');
	window.returnValue = 'SUCC_CODE_10000';
	window.close();
</script>
<%
	}
%>

