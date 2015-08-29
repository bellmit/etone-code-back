<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/include/function.jsp"%>
<html>
	<head>
		<%@ include file="/include/setcache.jsp"%>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title id="title">&nbsp;</title>
		<link rel="stylesheet" type="text/css" href="/css/default.css">
		<link rel="stylesheet" type="text/css" href="/css/component.css">
		<script language="javascript">
        function updateImageTitle(t)
        {
            var obj = document.getElementById("ContentPageTitle");
            if (obj != null)
            {
                obj.style.color = "#FF8800";
                obj.innerHTML = t;
            }
        }
        function showLoading()
        {
            var obj = document.getElementById("ContentPageTitle");
            if (obj != null)
            {
                obj.style.color = "#00AA00";
                obj.innerHTML = "页面加载中，请稍候……";
            }
        }

        function loadDialogContent()
        {
            var url = window.location.href;
            var qIndex = url.indexOf("?");

            if (qIndex != -1)
                ifrDialogContent.location.href = url.substring(qIndex + 1, url.length);
        }
    </script>
	</head>
	<body onLoad="loadDialogContent()">
		<table width="100%" height="100%" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="8" background="/images/spliter/sbg.gif">
					<table width="100%" height="100%" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="33%" valign="top">
								<img src="/images/spliter/s1.gif" width="8" height="33">
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td height="33%" valign="bottom">
								<img src="/images/spliter/s2.gif" width="8" height="6">
							</td>
						</tr>
					</table>
				</td>
				<td valign="top">
					<table width="100%" height="100%" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="7" background="/images/title/t3.gif">
								<img src="/images/title/t3.gif" width="8" height="7">
							</td>
							<td background="/images/menu/m2.gif">
								<img src="/images/menu/m2.gif" width="7" height="7">
							</td>
							<td background="/images/title/t4.gif">
								<img src="/images/title/t4.gif" width="8" height="7">
							</td>
							<td height="7" background="/images/title/s1.gif">
								<img src="/images/title/s1.gif" width="7" height="7">
							</td>
						</tr>
						<tr>
							<td width="8" height="33">
								<img src="/images/title/t1.gif" width="8" height="33">
							</td>
							<td background="/images/title/tbg.gif">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<img src="/images/title/li.gif" width="20" height="21"
												align="middle">
											&nbsp;
											<span id="ContentPageTitle"
												style="font-size: 16px; font-weight: bold; color: #FF8800;"></span>
										</td>
										<td align="right">
											<a href="#a" style="cursor: pointer"
												onClick="document.ifrDialogContent.location.reload();"><img
													src="/images/title/refresh.gif" alt="刷新页面" width="15"
													height="16" border="0" align="middle">&nbsp;刷新页面</a>
										</td>
									</tr>
								</table>
							</td>
							<td width="8">
								<img src="/images/title/t2.gif" width="8" height="33">
							</td>
							<td width="7" valign="top" background="/images/title/sbg2.gif">
								<img src="/images/title/s2.gif" width="7" height="7">
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
							<td bgcolor="#FFFFFF">
								<iframe name="ifrDialogContent" src="" frameborder="0"
									width="100%" height="100%"></iframe>
							</td>
							<td>
								&nbsp;
							</td>
							<td background="/images/title/sbg2.gif">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td height="6" background="/images/menu/m5.gif">
								<img src="/images/menu/m5.gif" width="6" height="6">
							</td>
							<td background="/images/menu/m5.gif" bgcolor="#FFFFFF">
								<img src="/images/menu/m5.gif" width="6" height="6">
							</td>
							<td background="/images/menu/m5.gif">
								<img src="/images/menu/m5.gif" width="6" height="6">
							</td>
							<td background="/images/title/s3.gif">
								<img src="/images/title/s3.gif" width="7" height="6">
							</td>
						</tr>

					</table>
				</td>
			</tr>
			<tr>
				<td background="/images/cpr_bg.gif">
					&nbsp;
				</td>
				<td height="21" align="center" background="/images/cpr_bg.gif">
					(C)2004,2006. 广州市宜通世纪科技有限公司 版本所有
				</td>
			</tr>
		</table>
		<script language="javascript">
    showLoading();
</script>
	</body>
</html>
