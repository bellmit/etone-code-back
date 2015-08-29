<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.symbol.*"%>
<%@ include file="/include/session.jsp"%>
<%@ include file="/include/setcache.jsp"%>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title></title>
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/style.css" />
		<script src="/js/paging.js"></script>
		<script src="/js/nav.js"></script>
		<script language="javascript" src="/js/script.js"></script>
		<script language="javascript">
		 function checkTwoPwdIsEqual()
        {
            var pwd1 = document.getElementById("userpwd");
            var pwd2 = document.getElementById("userpwd1");
            var msg = document.getElementById("pwd_msg");

            if (pwd1.value == "" && pwd2.value == "")
            {
                msg.innerHTML = "(����У��)";
                return true;
            }
            if (pwd1.value.length < 6)
            {
                msg.innerHTML = "<span style='color:#FF0000'>���������6λ�����룡��</span>";
                return false;
            }
            if (pwd1.value != pwd2.value)
            {
                msg.innerHTML = "<span style='color:#FF0000'>��������ȷ��ȷ�����룡��</span>";
                return false;
            }
            msg.innerHTML = "<span style='color:#0000FF'>��������������Ч��</span>";
            return true;
        }
         function checkForm()
        {            
            obj = document.beanForm.userpwd1;
            if (!checkTwoPwdIsEqual())
            {
                alert("�������������У�鲻ƥ�䣬���������룡");
                obj.focus();
                obj.select();
                return false;
            }
            if(document.beanForm.onsubmit()){
               document.beanForm.submit()
            }
            return false; 
        }
		</script>
	</head>
<body id="master">
		<form name="beanView" action="/user_saved.do?1=1&permId=${permId}"
			id="beanForm" method="post"
			onSubmit="return Validator.Validate(document.beanForm,3)">
			<div id="mainareacontent">
				<div class="mainarea">
					<!----------- ����Ϣ ------------------>
					<table width="100%" cellpadding="0" cellspacing="1"
						class="formitem">
						<caption>
							������Ϣ
						</caption>
						<tr>						
							<th>
								<font color = red>*</font>�û���:
							</th>
							<td>						
								<input type="text" style="width: 66%" name="beanView.vcUserName"
									value="${beanView.vcUserName}" require="true" datatype="Require"
									msg="�����Բ���Ϊ��!">
								<input type="hidden" name="beanView.userType" value="2">
							</td>
							<th>
								<font color = red>*</font>��¼��:
							</th>
							<td>
								<input type="text" style="width: 66%" name="beanView.vcLoginName"
									value="${beanView.vcLoginName}" require="true" datatype="Require"
									msg="�����Բ���Ϊ��!">
							
							</td>
						</tr>
						<tr>
							<th>
								<font color = red>*</font>����:
							</th>
							<td>
								<input type="password" style="width: 66%"
									name="userpwd"  id="userpwd"
									require="true" datatype="Require" msg="�����Բ���Ϊ��!"
									onKeyUp="checkTwoPwdIsEqual()">
							</td>
							<th>
								<font color = red>*</font>ȷ������:
							</th>
							<td>
								<input type="password" style="width: 66%" name="userpwd1"
								id = "userpwd1"
									onKeyUp="checkTwoPwdIsEqual()">
							</td>
						</tr>
						<tr>
								<th align="right" valign="top" bgcolor="#FFFFFF">
									&nbsp;
								</th>
								<td valign="top" bgcolor="#FFFFFF">
									<span id="pwd_msg">(��������У��)</span>
								</td>
								<th align="right" valign="top" bgcolor="#FFFFFF">
									&nbsp;
								</th>
								<td valign="top" bgcolor="#FFFFFF" colspan="">
									&nbsp;
								</td>
							</tr>
						<tr>
							<th>
								����:
							</th>
							<td>
								<input type="text" style="width: 66%"
									name="" value=""
									require="true" datatype="false"
									msg="�����Բ���Ϊ��!">
							</td>
							<th>
								����:
							</th>
							<td>
								<input type="text" style="width: 66%"
									name="beanView.vcEmail"
									value="${beanView.vcEmail}" require="false"
									datatype="Email" msg="�����ʽ����ȷ!">
							</td>
						</tr>
						<tr>
							<th>
								�ֻ�:
							</th>
							<td>
								<input type="text" style="width: 66%" name="beanView.vcMsisnd"
									value="${beanView.vcMsisnd}" require="false" datatype="Mobile"
									msg="�ֻ���ʽ����ȷ!">
							</td>
							<th>
								�绰:
							</th>
							<td>
								<input id="Text11" type="text" 
									name="beanView.vcPhone" value="${beanView.vcPhone}">
							</td>
						</tr>
						
					</table>
					<table width="100%" cellpadding="0" cellspacing="0"
						class="formitem_pagestyle">
						<tr>
							<td class="pagebutton">
								<%--<s:submit type="image" src="/skin/Default/btn_submit.gif"></s:submit>--%>
								<a href="#a" onclick="checkForm();"><img src="/skin/Default/btn_submit.gif"></a>
								 <a href="/user_query.do?1=1&permId=${permId}"><img src="/skin/Default/btn_close.gif"></a> 
							</td>
						</tr>
					</table>
				</div>
			</div>
		</form>
	</body>
</html>
