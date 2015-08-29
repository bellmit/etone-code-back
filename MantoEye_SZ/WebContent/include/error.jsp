<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="com.symbol.wp.core.constants.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><meta
	http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<%
	String url = (String) request.getAttribute("url");
	String e_code = (String) request
			.getAttribute(VarConstants.ERROR_CODE);
	String se_msg = MsgConstants.LOGIN_ERROR_SSOCNT_MSG;
	String timeoutmsg = MsgConstants.LOGIN_TIMEOUT_MSG;
	String inerror = "您的登录已超时，请重新登入!";
	//String action = (String) request.getAttribute("action");
	if (e_code == null) {
		e_code = inerror;
	}

	if (MsgConstants.ERROR_CODE_10000.equals(e_code)) {
%>
<script type="text/javascript">
	alert('<%=MsgConstants.ERROR_CODE_10000%>');
	window.returnValue = 'ERROR_CODE_10000';
	window.close();
</script>
<%
	} else {
%>
<script language="javascript">
		alert("<%=e_code%>");
		//top.location.href="/test_login.jsp";
		//window.close();
	<%//if(action != null){
				//	out.print("window.opener=null;window.close();");
				//}else{
				if (e_code.equals(timeoutmsg) || e_code.equals(se_msg)
						|| e_code.equals(inerror)) {%>
			//top.location.href="/test_login.jsp";
			window.close();
	<%} else {
					out.print("history.go(-1);");

				}
			}
			//}
			//%>
	window.close();
	//history.go(-1);
</script>
