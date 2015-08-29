<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@ include file="/include/session.jsp"%>
<%@ include file="/include/setcache.jsp"%>
<html>
	<head>
		<title>数据慧眼</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link id="change_style" type="text/css" rel="stylesheet" href="/skin/Default/css/header_1280.css" />
		<script type='text/javascript' src='/js/jquery.autocomplete.js'></script>
		<script src="/js/nav.js"></script>
	</head>	
	<body id="master">
		<form action="" id="headform" name="headform">
			<!----------- 页面头部 ------------------>
			<div id="header">
				<div class="logo"></div>
				<div class="backdw"></div>
				<div class="systemBtn">
					<% if(!(USER_TYPE!=null&&USER_TYPE.equals("1"))){ %>
					<img style="margin-top: 0px; height: 44px; vertical-align: top;;cursor: hand" 
					    src="/skin/Default/images/header/changepasswd.gif" alt="修改密码"
						onclick="changePwd()" />
					&nbsp;
					<%} %>
					<img style="margin-top: 0px; height: 44px; vertical-align: top;;cursor: hand"
						src="/skin/Default/images/header/change.gif" alt="返回首页"
						onclick="backindex()" />
					&nbsp;
					<img style="margin-top: 0px; height: 44px; vertical-align: top;;cursor: hand"
						src="/skin/Default/images/header/exit.gif" alt="退出"
						onclick="exitSystem()" />
				</div>
			</div>
		</form>
	</body>
</html>
<script language="javascript">
$(document).ready(function(){
	changeskin();
});
			function changeskin(){
				var x=window.screen.width;
				if(x==1024){
					document.getElementById("change_style").href="/skin/Default/css/header_1024.css";
				}else{
					document.getElementById("change_style").href="/skin/Default/css/header_1280.css";
					
				}
			}
function topmenuClick(ck){
	var exp =ck ;
	if(typeof(exp) == "undefined") {
		parent.frmmain.location.href="/index_init.do?1=1";
	}else{
		parent.frmsubmenu.location.href="/submenu.jsp?tabid="+ck;
	}
}
function topmenuClick9(){
	parent.frmmain.location.href="/welcome.jsp";
}
function topmenuClick13(){
	parent.frmmain.location.href="/mantoeye/network/test.jsp";
}

function changeMenuView(ck,size,flag){
	if(flag==1){
	    var menus;
		var menusp = document.getElementById("topmenu_a_"+ck);
		menua = document.getElementById("topmemu_span_"+ck);
		 menua.style.background = "url(../skin/Default/images/header/right_on.gif) no-repeat right top " ;
		 menusp.style.background="url(../skin/Default/images/header/left_on.gif) no-repeat left top ";
		// menusp.style.background = "url(../images/header/right_on.gif) no-repeat left top;"
		 var ul=document.getElementById("sunmenu");	
		 var j=ul.childNodes.length;
		 for(var i=0;i<j-1;i++){
				if(i!=ck){
					 var temp="topmenu_a_"+i;
					 var temp2="topmemu_span_"+i;
					var menua1=document.getElementById(temp);
					var menusp1=document.getElementById(temp2);
					 menua1.style.background = "url(../skin/Default/images/header/left.gif) no-repeat left top " ;
		 			menusp1.style.background="url(../skin/Default/images/header/right.gif) no-repeat right top ";
		 			
				}
			}
			
		}else{
			var menusp = document.getElementById("topmenu_a_"+ck);
			menua = document.getElementById("topmemu_span_"+ck);
			 menua.style.background = "url(../skin/Default/images/header/right_on.gif) no-repeat right top " ;
			 menusp.style.background="url(../skin/Default/images/header/left_on.gif) no-repeat left top ";
			 
			 var menuspindex = document.getElementById("topmenu_a_10");
			 menuaindex = document.getElementById("topmemu_span_10");
			 menuaindex.style.background = "url(../skin/Default/images/header/right.gif) no-repeat right top " ;
			 menuspindex.style.background="url(../skin/Default/images/header/left.gif) no-repeat left top ";
			 for(var i=0;i<size;i++){
				if(i!=ck){
					 var temp="topmenu_a_"+i;
					 var temp2="topmemu_span_"+i;
					var menua1=document.getElementById(temp);
					var menusp1=document.getElementById(temp2);
					 menua1.style.background = "url(../skin/Default/images/header/left.gif) no-repeat left top " ;
		 			menusp1.style.background="url(../skin/Default/images/header/right.gif) no-repeat right top ";
		 			
				}
			}
		}
}

//注销当前登陆
function logoff(){
    if (confirm("是否要注销当前登录？"))
    {
        window.open('/login_out.do', '_blank', '');
        parent.window.close();
    }
}
//退出系统
function exitSystem(){
    if (confirm("是否要退出系统？"))
         parent.window.close();
}
//修改密码
function changePwd(){ 
	showMMDialog('/sysdata/user/modify_pwd.jsp','','600px','380px');
}
//问题反馈
function feedback(){ 
	parent.frmmain.feedback();
}
function showinfo(){ 
	window.open('info.html','');
}
function backindex(){
	parent.frmheader.location.href="/include/header1.jsp";
	parent.frmmain.location.href="/include/mainindex.jsp";
}
/**
脚本不出错
*/	
$(document).ready(function(){
	window.onerror = killErrors;
})
function killErrors() {
	return true;
}
</script>