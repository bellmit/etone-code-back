<%@ page contentType="text/html;charset=GBK"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=gb2312" />
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<script language="javascript" src="/js/nav.js"></script>
		<script language="javascript">
        function showMsg(msg,w,h){
        	document.getElementById("CollectLayerMsg_Div2").style.display="";
			document.getElementById("CollectMsg_Div2").style.display="";
			//建议:w:322 h:14
			document.getElementById("CollectLayerMsg_Div2").style.width=w;
			document.getElementById("CollectLayerMsg_Div2").style.height=h;
			document.getElementById("CollectLayerMsg_Div2").style.top=(document.body.clientHeight-h)/2;
			document.getElementById("CollectLayerMsg_Div2").style.left=(document.body.clientWidth-w)/2;
			document.getElementById("abc").style.top=(document.body.clientHeight-h)/2+30;
			document.getElementById("abc").style.left=(document.body.clientWidth-w)/2+120;
            //var url = window.location.href;
            document.getElementById("msgContent").innerHTML=msg;
        }
        function closeMsg(){
			document.getElementById('CollectLayerMsg_Div2').style.display='none';
			document.getElementById('CollectMsg_Div2').style.display='none';
			document.getElementById("msgContent").innerHTML="";
			//document.ifrMain.ifrContent.location.reload();
		}
		 var moveable2 = false;
		 function startgrap2(obj){                     
            if(event.button==1){
	            obj.setCapture();
	            //记录鼠标和层位置;
                x0 = event.clientX;
                y0 = event.clientY;
                x1 = parseInt(obj.style.left);
                y1 = parseInt(obj.style.top);
                moveable2 = true;            
            }
         }
        function stopgrap2(obj){
            if(moveable2){
                obj.releaseCapture();//用来释放对鼠标的捕捉
                moveable2 = false;
            }
        }
        function grap2(obj){
             if(moveable2){ 
               obj.style.left = x1 + event.clientX - x0;
               obj.style.top  = y1 + event.clientY - y0;
            }
        }
    </script>
		<style type="text/css">
#CollectMsg_Div2 {
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

.ListTable_Alpha2 {
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

#CollectLayerMsg_Div2 {
	position: absolute;
	z-index: 999;
	filter: alpha(opacity = 100);
	CURSOR: hand;
}

.CircelTable_Alpha2 {
	filter: alpha(opacity = 100);
	background-color: #f2f3f4;
	margin-right: 5px;
	margin-left: 5px;
	width: 99%;
	border: 2px solid #6B708F;
}

.ListFootTD_Alpha2 {
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
		<div id="CollectMsg_Div2" style="display: 'none'"></div>
		<!-- 
		<div id="CollectLayerMsg_Div2" style="display:'none'" onMouseDown="startgrap2(this);" onMouseUp="stopgrap2(this);" onMouseMove="grap2(this);">
			<table width="100%" height="100%" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td valign="top">
						<table width="100%" height="100%" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td colspan="3">
									<div id="msgContent"></div></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		 -->
		<div id="CollectLayerMsg_Div2"
			style="position: absolute; border: 1 #707888 solid; overflow: hidden">
			<div style="position: absolute; top: -1; left: 0" id="pimg">
			</div>
		</div>
		<div id="abc"
			style="position: absolute; font-size: 9pt; color: #f4f4f4">
			<div id="msgContent"></div>
		</div>
		<script>
s=new Array();
s[0]="#050626";
s[1]="#0a0b44";
s[2]="#0f1165";
s[3]="#1a1d95";
s[4]="#1c1fa7";
s[5]="#1c20c8";
s[6]="#060cff";
s[7]="#2963f8";
var pimg = document.getElementById("pimg")
function ls() {
	pimg.innerHTML = "";
	for (i = 0; i < 9; i++) {
		pimg.innerHTML += "<input style=\"width:15;height:10;border:0;background:" + s[i] + ";margin:1\">";
	}
}
function rs() {
	pimg.innerHTML = "";
	for (i = 9; i > -1; i--) {
		pimg.innerHTML += "<input style=\"width:15;height:10;border:0;background:" + s[i] + ";margin:1\">";
	}
}
ls();
var g = 0;
sped = 0;
function str() {
	if (pimg.style.pixelLeft < 350 && g == 0) {
		if (sped == 0) {
			ls();
			sped = 1;
		}
		pimg.style.pixelLeft += 2;
		setTimeout("str()", 1);
		return;
	}
	g = 1;
	if (pimg.style.pixelLeft > -200 && g == 1) {
		if (sped == 1) {
			rs();
			sped = 0;
		}
		pimg.style.pixelLeft -= 2;
		setTimeout("str()", 1);
		return;
	}
	g = 0;
	str();
}
function flashs() {
	if (document.getElementById("abc").style.color == "#ffffff") {
		document.getElementById("abc").style.color = "#707888";
		setTimeout("flashs()", 500);
	} else {
		document.getElementById("abc").style.color = "#ffffff";
		setTimeout("flashs()", 500);
	}
}
flashs();
str();
</script>
	</body>
</html>
