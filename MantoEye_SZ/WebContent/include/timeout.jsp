<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="com.symbol.wp.core.constants.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><meta
	http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<%
	String inerror = "您的登录已超时，请重新登入!";
%>
<script language="javascript">
	alert("<%=inerror%>");
	window.close();
</script>
