<%@ page import="java.math.BigInteger"%>
<%@ page import="com.symbol.wp.core.listener.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.symbol.wp.core.util.Common"%>
<%@ page import="com.symbol.wp.core.dao.common.SqlBaseDao"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="com.symbol.wp.core.constants.VarConstants"%>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<%
	SqlBaseDao sqlBaseDAO = new SqlBaseDao();
	String strSQL = "";
	ResultSet rs = null;

	LoginListener loginListener = (LoginListener) session
			.getAttribute(VarConstants.LOGIN_LISTENER_KEY);
	if (loginListener == null) {
		out.println("<script language='javascript'>");
		out.println("window.close();");
		out.println("</script>");
		return;
	}
	SessionContainer sessionContainer = loginListener
			.getSessionContainer();
	Map<String, String> permmap = sessionContainer.getBtnPerm();
	String USER_NO = sessionContainer.getUserNo();
	String USER_NAME = sessionContainer.getUserName();
	String USER_TYPE = sessionContainer.getUserType();
	String permId = request.getParameter("permId");
	String btnperm = permmap.get(permId);
	String menuperm = sessionContainer.getMenuPerm();
	request.setAttribute("permId", permId);
%>
<script type="text/javascript"></script>

