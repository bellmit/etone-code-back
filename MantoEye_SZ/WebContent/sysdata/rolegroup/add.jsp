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
                msg.innerHTML = "(密码校验)";
                return true;
            }
            if (pwd1.value.length < 6)
            {
                msg.innerHTML = "<span style='color:#FF0000'>请输入大于6位的密码！！</span>";
                return false;
            }
            if (pwd1.value != pwd2.value)
            {
                msg.innerHTML = "<span style='color:#FF0000'>请输入正确的确认密码！！</span>";
                return false;
            }
            msg.innerHTML = "<span style='color:#0000FF'>两次输入密码有效！</span>";
            return true;
        }
         function checkForm()
        {            
            obj = document.beanForm.userpwd1;
            if (!checkTwoPwdIsEqual())
            {
                alert("您输入的新密码校验不匹配，请重新输入！");
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
					<!----------- 表单信息 ------------------>
					<table width="100%" cellpadding="0" cellspacing="1"
						class="formitem">
						<caption>
							基本信息
						</caption>
						<tr>						
							<th>
								<font color = red>*</font>用户名:
							</th>
							<td>						
								<input type="text" style="width: 66%" name="beanView.vcUserName"
									value="${beanView.vcUserName}" require="true" datatype="Require"
									msg="该属性不能为空!">
								<input type="hidden" name="beanView.userType" value="2">
							</td>
							<th>
								<font color = red>*</font>登录名:
							</th>
							<td>
								<input type="text" style="width: 66%" name="beanView.vcLoginName"
									value="${beanView.vcLoginName}" require="true" datatype="Require"
									msg="该属性不能为空!">
							
							</td>
						</tr>
						<tr>
							<th>
								<font color = red>*</font>密码:
							</th>
							<td>
								<input type="password" style="width: 66%"
									name="userpwd"  id="userpwd"
									require="true" datatype="Require" msg="该属性不能为空!"
									onKeyUp="checkTwoPwdIsEqual()">
							</td>
							<th>
								<font color = red>*</font>确认密码:
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
									<span id="pwd_msg">(密码输入校验)</span>
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
								部门:
							</th>
							<td>
								<input type="text" style="width: 66%"
									name="" value=""
									require="true" datatype="false"
									msg="该属性不能为空!">
							</td>
							<th>
								邮箱:
							</th>
							<td>
								<input type="text" style="width: 66%"
									name="beanView.vcEmail"
									value="${beanView.vcEmail}" require="false"
									datatype="Email" msg="邮箱格式不正确!">
							</td>
						</tr>
						<tr>
							<th>
								手机:
							</th>
							<td>
								<input type="text" style="width: 66%" name="beanView.vcMsisnd"
									value="${beanView.vcMsisnd}" require="false" datatype="Mobile"
									msg="手机格式不正确!">
							</td>
							<th>
								电话:
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
