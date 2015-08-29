<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/include/session.jsp"%>
<%@ include file="/include/setcache.jsp"%>
<html>
	<head>
		<title>测试</title>
	</head>
	<body>
		&quot;&quot;<br>
		<br>
		手机:
		<input type="text" id="phoneModel" style="width: 220px; height: 20px"
			onclick="showPhoneDialog()" />
		<br>
		<br>
		业务:
		<input type="text" id="business" style="width: 220px; height: 20px"
			onclick="showBusinessDialog()" />
		<br>
		<br>
		区域:
		<input type="text" id="area" style="width: 220px; height: 20px"
			onclick="showAreaDialog()" />
			
		<br>
		<input type="button" value="布局"  onclick="window.open('content.jsp');">
	</body>
</html>
<script>
function showPhoneDialog(){
	var value = window.showModalDialog("/multiDialog_initPhone.do","","dialogWidth='"+dialogWidth+"';dialogHeight='"+dialogHeight+"';toolbar:no;scroll=no;center=yes");
	if(value!=''){
		$('#phoneModel').attr("value",value);
	}
}

function showBusinessDialog(){
	var value = window.showModalDialog("/multiDialog_initBusiness.do","","dialogWidth='"+dialogWidth+"';dialogHeight='"+dialogHeight+"';toolbar:no;scroll=no;center=yes");
	if(value!=''){
		$('#business').attr("value",value);
	}
}

function showAreaDialog(){
	var value = window.showModalDialog("/multiDialog_initArea.do","","dialogWidth='"+dialogWidth+"';dialogHeight='"+dialogHeight+"';toolbar:no;scroll=no;center=yes");
	if(value!=''){
		$('#area').attr("value",value);
	}
}
</script>
