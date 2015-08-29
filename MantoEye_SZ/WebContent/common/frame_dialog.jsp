<%@ page contentType="text/html;charset=GBK"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=gb2312" />
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<title></title>
		<link rel="stylesheet" type="text/css" href="/css/default.css">
		<link rel="stylesheet" type="text/css" href="/css/component.css">
		<script language="javascript" src="/js/nav.js"></script>
		<script language="javascript">
        function updateImageDialogTitle(t)
        {
            var obj = document.getElementById("ContentDialogPageTitle");
            if (obj != null)
            {
                obj.style.color = "#FF8800";
                obj.innerHTML = t;
            }
        }
        function showLoading()
        {
            var obj = document.getElementById("ContentDialogPageTitle");
            if (obj != null)
            {
                obj.style.color = "#00AA00";
                obj.innerHTML = "页面加载中，请稍候……";
            }
        }

        function loadDialogContent(url,w,h){
        	document.getElementById("CollectLayerMsg_Div").style.display="";
			document.getElementById("CollectMsg_Div").style.display="";
			document.getElementById("CollectLayerMsg_Div").style.width=w;
			document.getElementById("CollectLayerMsg_Div").style.height=h;
			document.getElementById("CollectLayerMsg_Div").style.top=(document.body.clientHeight-h)/2;
			document.getElementById("CollectLayerMsg_Div").style.left=(document.body.clientWidth-w)/2;
            //var url = window.location.href;
            var qIndex = url.indexOf("?");
            if (qIndex != -1)
                ifrDialogContent.location.href = url.substring(qIndex + 1, url.length);
        }
        function closeWindow(){
			document.getElementById('CollectLayerMsg_Div').style.display='none';
			document.getElementById('CollectMsg_Div').style.display='none';
			//document.ifrMain.ifrContent.location.reload();
		}
		 var moveable = false;
		 function startgrap(obj){                     
            if(event.button==1){
	            obj.setCapture();
	            //记录鼠标和层位置;
                x0 = event.clientX;
                y0 = event.clientY;
                x1 = parseInt(obj.style.left);
                y1 = parseInt(obj.style.top);
                moveable = true;            
            }
         }
        function stopgrap(obj){
            if(moveable){
                obj.releaseCapture();//用来释放对鼠标的捕捉
                moveable = false;
            }
        }
        function grap(obj){
             if(moveable){ 
               obj.style.left = x1 + event.clientX - x0;
               obj.style.top  = y1 + event.clientY - y0;
            }
        }
    </script>
		<style type="text/css">
#CollectMsg_Div {
	z-index: 10;
	height: 100%;
	width: 100%;
	filter: alpha(opacity = 10);
	background-color: #000000;
	BORDER-TOP: #000000 1px solid;
	BORDER-LEFT: #000000 1px solid;
	position: absolute;
	left: 0px;
	top: 0px;
}

.ListTable_Alpha {
	filter: alpha(opacity = 100);
	background-color: #f2f3f4;
	margin-right: 5px;
	margin-left: 5px;
	width: 99%;
	border-right-width: 1px;
	border-left-width: 1px;
	border-top-style: none;
	border-right-style: none;
	border-bottom-style: none;
	border-left-style: none;
	border-top-width: 1px;
	border-bottom-width: 1px;
}

#CollectLayerMsg_Div {
	position: absolute;
	z-index: 999;
	filter: alpha(opacity = 100);
	CURSOR: hand;
}

.CircelTable_Alpha {
	filter: alpha(opacity = 100);
	background-color: #f2f3f4;
	margin-right: 5px;
	margin-left: 5px;
	width: 99%;
	border: 2px solid #6B708F;
}

.ListFootTD_Alpha {
	filter: alpha(opacity = 100);
	color: #f2f3f4;
	height: 35px;
	border-top-width: 1px;
	border-top-style: none;
	border-top-color: #999999;
	background-repeat: repeat-x;
	left: 10px;
	right: 10px;
	bottom: 1px;
	background-position: top;
	top: 1px;
}
</style>
	</head>
	<body>
		<div id="CollectMsg_Div" style="display: 'none'"></div>
		<div id="CollectLayerMsg_Div" style="display: 'none'"
			onMouseDown="startgrap(this);" onMouseUp="stopgrap(this);"
			onMouseMove="grap(this);">
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
								<td width="8" height="33" background="/images/title/tbg.gif">
									<img src="/images/title/t1.gif" height="33">
								</td>
								<td background="/images/title/tbg.gif" bgcolor="#FFFFFF">
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td>
												<img src="/images/title/li.gif" width="20" height="21"
													align="middle">
												&nbsp;
												<span id="ContentDialogPageTitle"
													style="font-size: 16px; font-weight: bold; color: #FF8800;"></span>
											</td>
											<td align="right">
												<a href="#a" style="cursor: pointer"
													onClick="closeWindow();"><img
														src="/images/title/close.bmp" alt="关闭" width="15"
														height="16" border="0" align="middle">
												</a>
												<a href="#a" style="cursor: pointer"
													onClick="document.ifrDialogContent.location.reload();"><img
														src="/images/title/refresh.gif" alt="刷新页面" width="15"
														height="16" border="0" align="middle">&nbsp;刷新页面</a>
											</td>
										</tr>
									</table>
								</td>
								<td width="8" background="/images/title/tbg.gif">
									&nbsp;
								</td>
								<td width="7" valign="top" background="/images/title/sbg2.gif"
									bgcolor="#FFFFFF">
									<img src="/images/title/s2.gif" width="7" height="7">
								</td>
							</tr>
							<tr>
								<td bgcolor="#FFFFFF" colspan="3">
									<iframe name="ifrDialogContent" src="" frameborder="0"
										width="100%" height="100%"></iframe>
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
		</div>
	</body>
	<script language="javascript">
    	showLoading();
	</script>
</html>
