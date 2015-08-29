<%@ page import="org.springframework.web.util.WebUtils"%>
<%@ page import="java.util.*"%>
<input type="hidden" name="page.pageNo" id="pageNo"
	value="${page.pageNo }" />
<input type="hidden" name="page.orderBy" id="orderBy"
	value="${page.orderBy }" />
<input type="hidden" name="page.order" id="order" value="${page.order }" />
<input type="hidden" name="page.pageSize" id="pageSize"
	value="${page.pageSize }" />
<input type="hidden" id="successCode" name="successCode"
	value="${successCode}" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<script language="javascript">
//showMessage();
function showMessage()
{
	var code = document.getElementById("successCode").value;
	alert(code);
}
</script>
