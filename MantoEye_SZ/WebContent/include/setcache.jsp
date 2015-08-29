
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<%@ page isELIgnored="false"%>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/jstl_tld/c.tld"%>
<%@ taglib prefix="fn" uri="/WEB-INF/jstl_tld/fn.tld"%>
<%@ taglib prefix="tree" uri="/WEB-INF/tag/tree.tld"%>
<%@ taglib prefix="page" uri="/WEB-INF/tag/page.tld"%>
<%@ taglib prefix="b" uri="/WEB-INF/tag/button.tld"%>
<script type="text/javascript" src="/common/dtree/dltree.js"></script>
<script src="/js/table.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/jquery/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="/js/multiDialog.js"></script>
<!-- validate -->
<link href="/tools/validate/validate/jquery.validate.css"
	type="text/css" rel="stylesheet" />
<script src="/tools/validate/validate/jquery.validate.js"
	type="text/javascript"></script>
<script src="/tools/validate/validate/messages_cn.js"
	type="text/javascript"></script>


