<%@ page contentType="text/html;charset=utf-8"%>
<HTML>
	<HEAD>
		<TITLE></TITLE>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<META content="text/html; charset=GBK" http-equiv=Content-Type>
		<STYLE type=text/css>
		.cMenu {
		    FILTER: alpha(opacity=0);
		    BACKGROUND-COLOR: #f2f3f4;
		    BORDER-BOTTOM: #666666 2px solid; 
		    BORDER-LEFT: #E4E4E4 2px solid; 
		    BORDER-RIGHT: #666666 2px solid;
		    BORDER-TOP: #E4E4E4 2px solid; 
		    COLOR: #000000; 
		    CURSOR: default; 
		    FONT-SIZE: 9pt;
		    color:#000000;
		    FONT-WEIGHT: normal; 
		    LINE-HEIGHT: 20px; 
		    POSITION: absolute; 
		    VISIBILITY: hidden; 
		    WIDTH: 150px
		}
		.menuitems {
		    font-size:9pt;
		    MARGIN: 2px;
		    PADDING-BOTTOM: 0px;
			PADDING-LEFT: 15px;
			PADDING-RIGHT: 3px;
			PADDING-TOP: 0px;
		}
		</STYLE>
	</HEAD>
	<BODY>
	<!-- 
		<DIV class=cMenu id=ie5menu onclick=jumptoie5() onmouseout=lowlightie5() 
		onmouseover=highlightie5()>
		<DIV class=menuitems url="javascript:alert()">后退</DIV>
		<DIV class=menuitems url="javascript:click_obj(1)">前进</DIV>
		<DIV class=menuhr><hr style="width:100%"></DIV>
		<DIV class=menuitems url="javascript:click_obj(2)">刷新</DIV>
		<DIV class=menuitems url="javascript:click_obj(3)">加入收藏夹</DIV>
		<DIV class=menuitems url="javascript:click_obj(4)">查看源文件</DIV>
		<DIV class=menuhr><hr style="width:100%"></DIV>
		<DIV class=menuitems url="javascript:click_obj(5)">属性</DIV>
		</DIV>
		<SCRIPT language=JavaScript>
		if (document.all&&window.print){
		    ie5menu.className="cMenu"
		    //document.oncontextmenu=showmenuie5
		    document.body.onclick=hidemenuie5
		}
		</SCRIPT>
	 -->
	</BODY>
</HTML>
<SCRIPT language=JavaScript>
		<!-- // RightClickMenu
		var intDelay=10; //设置菜单显示速度，越大越慢
		var intInterval=5; //每次更改的透明度
		function showmenuie5(){
		   var rightedge=document.body.clientWidth-event.clientX
		   var bottomedge=document.body.clientHeight-event.clientY
		   if (rightedge<ie5menu.offsetWidth)
		       ie5menu.style.left=document.body.scrollLeft+event.clientX-ie5menu.offsetWidth
		   else
		       ie5menu.style.left=document.body.scrollLeft+event.clientX
		   if (bottomedge<ie5menu.offsetHeight)
		       ie5menu.style.top=document.body.scrollTop+event.clientY-ie5menu.offsetHeight
		   else
		       ie5menu.style.top=document.body.scrollTop+event.clientY
		   ie5menu.style.visibility="visible"
		   ie5menu.filters.alpha.opacity=0
		   GradientShow()
		   return false
		}
		
		function hidemenuie5(){
		   //ie5menu.style.visibility="hidden"
		   GradientClose()
		}
		function highlightie5(){
		   if (event.srcElement.className=="menuitems"){
		       event.srcElement.style.backgroundColor="highlight"
		       event.srcElement.style.color="white"
		   }
		}
		function lowlightie5(){
		   if (event.srcElement.className=="menuitems"){
		       event.srcElement.style.backgroundColor=""
		       event.srcElement.style.color="#000000"
		   }
		} 
		function jumptoie5(){
		   if (event.srcElement.className=="menuitems"){
		       if (event.srcElement.url != ''){
		           if (event.srcElement.getAttribute("target")!=null)
		               window.open(event.srcElement.url,event.srcElement.getAttribute("target"))
		           else
		               window.location=event.srcElement.url
		       }
		   }
		}
		function GradientShow() //实现淡入的函数 
		{ 
		    ie5menu.filters.alpha.opacity+=intInterval 
		    if (ie5menu.filters.alpha.opacity<100) setTimeout("GradientShow()",intDelay)
		}   
		function GradientClose() //实现淡出的函数 
		{
			if(ie5menu!=null){
				ie5menu.filters.alpha.opacity-=intInterval 
			    if (ie5menu.filters.alpha.opacity>0) { 
			     	setTimeout("GradientClose()",intDelay) 
			    } 
			    else { 
			     	ie5menu.style.visibility="hidden"
			    }
			}
		     
		}
		function ChangeBG() //改变菜单项的背景颜色，这里的两种颜色值可以改为你需要的 
		{
		    oEl=event.srcElement 
		    if (oEl.style.background!="navy") { 
		        oEl.style.background="navy" 
		    } 
		    else { 
		        oEl.style.background="#cccccc" 
		    } 
		} 
		// -->
		</SCRIPT>




