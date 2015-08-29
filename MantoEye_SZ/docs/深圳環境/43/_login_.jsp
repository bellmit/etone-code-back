<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="shortcut icon" href="/images/icon/icon.ico"
			type="image/x-icon" />

		<style type="text/css">
body {
	width: 1020px;
	height: 590px;
	font: 12px 宋体, tahoma, arial, helvetica, sans-serif;
	margin-top: 2px;
	margin-left: auto;
	margin-right: auto;
	border: solid 1px #A6C7F3;
}

#logleft {
	margin-left: 0;
	margin-top: 5px;
	width: 202px;
	height: 55px;
	background-image: url(skin/Default/images/MantoEye/u30.png);
	float: left;
}

#logright {
	margin-left: 10px;
	margin-top: 25px;
	font-size: 28px;
	font-weight: 700;
	clear: right;
	color: blue;
}

#contentarea {
	width: 100%;
	height: 400px;
	margin: 0;
}

#main {
	height: 400px;
	width: 745px;
	float: left;
	margin: 0;
}

#maintitle {
	height: 40px;
	width: 730px;
	font-size: 20px;
	font-weight: 700;
	margin: 20px 0 0 10px;
}

#leftdescript {
	width: 730px;
	height: 50px;
	font-size: 12px;
	margin: 10px 0 0 10px;
}

#leftcontent {
	width: 730px;
	height: 300px;
	margin: 20px 0 0 10px;
}

#leftpic {
	width: 360px;
	height: 200px;
	border: solid 1px #A6C7F3;
	float: left;
	margin: 0;
}

#leftpic img {
	max-width: 360px;
	width: expression(this . width > 360 ? "360px" : this . width + "px");
}

#middledrecript {
	width: 365px;
	height: 300px;
	margin: 0;
}

.title {
	font-size: 12px;
	color: #000;
	font-weight: 700;
	padding-left: 10px;
}

.content {
	font-size: 12px;
	font-weight: 400;
	color: #000;
	padding-left: 10px;
}

#login {
	width: 260px;
	height: 300px;
	border: solid 1px #A6C7F3;
	margin-left: 10px;
	margin-top: 20px;
}

#loigncontent {
	width: 250px;
	height: 290px;
	background-color: #E8EEFA;
	margin: 5px;
}

table {
	font-size: 12px;
}

#footer {
	font-size: 12px;
	width: 100%;
	height: 40px;
	margin-top: 100px;
	margin-left: 400px;
}

a:link {
	color: #000;
	text-decoration: none;
}
</style>

		<script type="text/javascript">
			function fillAccount(){
				var u = document.getElementById('userid');
				u.value = "";
				var p = document.getElementById('userpwd');
				p.value ="";
			};
		</script>
	</head>
	<body onload="fillAccount();">
		<div id="logleft"></div>
		<div id="logright">
			深圳移动本地数据业务分析挖掘系统
		</div>
		<div id="contentarea">
			<div id="main">
				<div id="maintitle">
					Manto Eye分析平台，从海量矿物中凭着一双慧眼发掘出耀眼的金矿
				</div>

				<div id="leftdescript">
					您可以从数据业务分析平台中简单地对各种数据进行业务识别分析及挖掘分析，将隐藏在海量数据中的矿金信息通过科学的技术与方法转
					<br />
					变为直接的市场价值，同时通过对信令追踪、错误码分析方法为故障维护提供信令层面的辅助手段。
				</div>

				<div id="leftcontent">
					<div id="leftpic">
						<img src="skin/Default/images/MantoEye/4.jpg" />
					</div>
					<div id="middledrecript">
						<div class="title">
							通用业务
						</div>
						<div class="content">
							数据业务信息识别，对即时通信软件、音频软件、视频软件、证
							<br />
							券软件、彩信业务等业务数据的挖掘分析，并对区域流量、时段
							<br />
							流量、区域用户数、时段用户数的维度分布进行统计分析，挖掘
							<br />
							Top 100高流量用户信息。
						</div>
						<br />
						<br />


						<div class="title">
							专项业务
						</div>
						<div class="content">
							专项业务是对blackberry、彩信、10大网站的专项业务分析,对
							<br />
							blackberry用户的区域分布、运营商分布分析,提取活跃用户信
							<br />
							息彩信分析包括SP彩信和点对点彩信分析,分析其时段发送量,主
							<br />
							题,区域,号段分布等。10大网站(腾讯、3G门户、移动梦网、易
							<br />
							查、乐讯、百度、新浪、动感论坛、空中网、友度)的用户数,流
							<br />
							量分析,以及各网站的栏目占比。
						</div>
						<br />
						<br />

						<div class="title">
							数据提取
						</div>
						<div class="content">
							数据提取包括原始数据定制提取和会话数据提取功能,通过对用
							<br />
							户信令的追踪采集、用户会话记录的分析，为故障排查提供有
							<br />
							效的分析工具.

						</div>
					</div>
				</div>

			</div>

			<div id="login">
				<div id="loigncontent">
					<form id="loginForm" method="post" action="/login_in.do"
						name="loginForm">
						<table width="100%" cellspacing="5px">
							<tr>
								<td colspan="2" align="center">
									<img src="skin/Default/images/MantoEye/u30.png" width="202"
										height="55" />
								</td>
							</tr>
							<tr>
								<td colspan="2" style="height: 20px"></td>
							</tr>
							<tr>
								<td width="60" align="center">
									用户名:
								</td>
								<td align="center">
									<input type="text" style="width: 140px" id="userid"
										name="loginForm.loginName" value="" />
								</td>
							</tr>

							<tr>
								<td width="60" align="center">
									密&nbsp;&nbsp;码:
								</td>
								<td align="center">
									<input type="password" style="width: 140px" id="userpwd"
										name="loginForm.password" value="" />
									<input type="hidden" name="loginForm.usertype" value="0" />
								</td>
							</tr>


							<tr>
								<td width="60" align="center">
									验证码:
								</td>
								<td align="center">
									<input id="validate_code" name="loginForm.validatecCode"
										type="text" style="width: 64px" size="6" maxlength="8" />
									<img src="/servlet/ValidateCode" width="70px" height="22px"
										align="absmiddle" onclick="this.src='/servlet/ValidateCode'"
										style="cursor: pointer" alt="看不清楚,点击换一张." />
								</td>
							</tr>

							<tr>
								<td colspan="2" style="height: 15px"></td>
							</tr>

							<tr>
								<td colspan="2" align="center">
									<input type="checkbox" />
									在此计算机保存我的信息
								</td>
							</tr>
							<tr>
								<td colspan="2" style="height: 10px"></td>
							</tr>
							<tr valign="middle">
								<td width="60"></td>
								<td align="left"
									style="background-image: url(skin/Default/images/MantoEye/u22.png); background-repeat: no-repeat; height: 31px;">
									<font style="font-size: 14px; font-weight: bold;">&nbsp;&nbsp;&nbsp;&nbsp;<a
										href="#" onclick="document.loginForm.submit();">登录</a> </font>
								</td>

							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
		<div id="footer">
			@2009 Symbol -
			<a href="#">Symbol主页</a> -
			<a href="#">问题反馈</a> -
			<a href="#">帮助</a>
		</div>
	</body>
</html>

