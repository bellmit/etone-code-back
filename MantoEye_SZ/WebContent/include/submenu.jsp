<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:tvns>
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<%@ page
		import="org.springframework.context.support.FileSystemXmlApplicationContext"%>
	<%@ page import="com.symbol.wp.core.service.ICommonManager"%>
	<%@ page import="org.springframework.web.context.WebApplicationContext"%>
	<%@ page
		import="org.springframework.web.context.support.WebApplicationContextUtils"%>
	<head>
		<title>布局页面</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link type="text/css" rel="stylesheet"	href="/skin/Default/css/newstyle.css" />
		<link href="/js/jquery/extend.tab/css/TabPanel.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="/js/jquery/extend.tab/js/Fader.js"></script>
		<script type="text/javascript" src="/js/jquery/extend.tab/js/TabPanel.js"></script>
		<script type="text/javascript" src="/js/jquery/extend.tab/js/Math.uuid.js"></script>
	</head>
	<body id="master" onload="initMenu();">
		<div id="mainarea">
			<div id="leftpanl">
				<div class="userinfo">
					<div class="headimg"></div>
					<div class="userorle">
						<span><%=USER_NAME%></span>
					</div>
				</div>
				<div class="fullinsert"></div>
				<div class="submenu">
					<%
						String subtree = "";
									//   Object obj = request.getParameter("tabid");
									WebApplicationContext context = WebApplicationContextUtils
											.getRequiredWebApplicationContext(this.getServletContext());
									ICommonManager commonService = (ICommonManager) context
											.getBean("commonManagerImpl");
									try {
										subtree = commonService.getSubMenu(USER_NO);
									} catch (Exception e) {
										//  e.printStackTrace();
									}
									out.print(subtree);
					%>
				</div>
				<div id="middleline" class="middleline"></div>
			</div>
			<div style="height: 645px; _height: 700px;" id="container_content"></div>

		</div>
	</body>
</html>

<script language="javascript">
	var tabpanel;
	var y = document.body.clientHeight;
	var x = window.screen.width;
	var windowHeight = window.screen.height;
	$(document)
			.ready(function() {
				var yy = window.screen.availHeight;
				var yyy = window.screenTop;
				var parentHeight = yy - yyy;

				//$('#middleline').css("height", parentHeight);
				if (navigator.userAgent.indexOf("MSIE 7.0") > 0) {
					parentHeight = parentHeight - 25;
				} else {
					parentHeight = parentHeight - 22;
				}

				//parentHeight = parentHeight - 40;

				//var tabwidth = x - 240;
					$('#middleline').css("height",parentHeight-30);
					$('#container_content').css("height", parentHeight);

					tabpanel = new TabPanel(
							{
								renderTo : 'container_content',
								//width:tabwidth, //如果设置了宽度,就不会自动改变宽度
								//height:'400px',
								//border:'none',
								active : 0,
								maxLength : 10,
								items : [{
									//id:'system_main_id',
									//title:'系统首页',
									//html:'<iframe src="../welcome.jsp" width="100%" height="100%" frameborder="0"></iframe>',
									title : '全网业务分析',
									id : 'FLUSH_TOTAL_DIST',
									html : '<iframe src="/mantoeye/data_analyse/total_distribute.jsp?1=1" width="100%" height="100%" frameborder="0"></iframe>',
									closable : true
								}]
							});

				});

	/**
	 打开新tab
	 (注意:ID必须唯一)
	 空间分布:FLUSH_SPACE_DIST 
	 业务分布:FLUSH_BUSI_DIST
	 APN分布:FLUSH_APN_DIST
	 应用协议分布:FLUSH_WAP_DIST
	 传输协议分布:FLUSH_TCP_DIST
	 用户归属分布:FLUSH_COUNTRY_DIST
	 */
	var total_obj = new Object();
	total_obj.total_time_level = null;
	total_obj.total_time_search = null;
	var tabId;//用于其他页面判断tabid
	var terminalObj = null;
	function newTab(flag, obj, date) {
		//salert('-----------------------------------------');
		terminalObj = obj;
		if (!flag) {
			total_obj.total_time_level = null;
			total_obj.total_time_search = null;
		}
		if (flag) {//若从全网统计过来的数据,则判断,如是已激活的tab,则先关闭
			for ( var i = 0; i < tabpanel.tabs.length; i++) {
				if (obj.id == tabpanel.tabs[i].id) {
					tabpanel.kill(obj.id);//存在则关掉此tab,然后重新打开
					break;
				}
			}
		}
		tabId = obj.id;
		//alert(obj.id+'==='+obj.name+'==='+obj.target);
		tabpanel
				.addTab( {
					title : obj.name,
					id : obj.id,
					html : '<iframe src="' + obj.target + '" width="100%" height="100%" frameborder="0"></iframe>'
				});
	}

	function feedback() {
		tabpanel
				.addTab( {
					title : '问题反馈',
					id : 'feedback',
					html : '<iframe id="feedback" src="/feedback_list.do?1=1" width="100%" height="100%" frameborder="0"></iframe>'
				});
	}
	function addhelp() {
		tabpanel
				.addTab( {
					title : '即席查询帮助',
					id : 'IMMEDIATELY_QUERY_HELP',
					html : '<iframe id="queryhelpframe" src="/mantoeye/immediately/help.jsp?1=1" width="100%" height="100%" frameborder="0"></iframe>'
				});
	}
	/*
	 *刷新标签
	 RULE_MA_MAMAP_MANA 关联
	 RULE_MA_MAIN_MANA   主规则
	 RULE_MA_ASSIST_MANA 辅规则
	 */
	function refrushTab(flag) {
		var frameId = flag + 'Frame';
		var iframeObj = window.frames[frameId];
		if (typeof (iframeObj) != 'undefined') {
			iframeObj.location.reload();
		}
	}

	function changeView(obj) {
		if (document.all("div_" + obj).style.display == "block") {
			document.all("div_" + obj).style.display = "none";
			document.all("ico_" + obj).src = "/skin/Default/images/MantoEye/submenu/ico_have.gif";
		} else {
			var $crrentObj = $("#div_" + obj);
			var $divobj = $(".navigation");
			$divobj.each(function(i) {
				if (this.id != "div_" + obj) {
					$(this).hide();
				} else {
					var divCount = $divobj.length;
					var divHeight = $(this).height();
					var heightJX;
					if (navigator.userAgent.indexOf("MSIE 6.0") > 0) {
						if (windowHeight == 800) {
							heightJX = 68;
						} else if (windowHeight == 1024) {
							heightJX = 52;
						} else {
							heightJX = 105;
						}
					} else {
						heightJX = 39;
					}
					var y_last = y - divCount * 30 - heightJX;
					if (windowHeight == 1024) {
						y_last = y_last + 15;
					}
					$(this).css("height", y_last + "px");
					if (divHeight > y_last) {
						$(this).css("overflow-y", "scroll");
					}
					$(this).show();
				}
			});
			$imgObj = $("img");
			$imgObj
					.each(function(i) {
						if (this.id != "ico_" + obj) {
							$(this)
									.attr("src",
											"/skin/Default/images/MantoEye/submenu/ico_have.gif");
						} else {
							$(this)
									.attr("src",
											"/skin/Default/images/MantoEye/submenu/ico_nohave.gif");
						}
					});
		}
		defaultView = obj;
	}
	function initMenu() {
		var $obj1 = $("h2");
		$obj1.each(function(i) {
			i++;
			if (i != 1) {
				$("#div_submenuh2_" + i).hide();
			} else {
				var $first = $("#div_submenuh2_" + 1);
				var divCount = $obj1.length;
				var divHeight = $first.height();
				if (navigator.userAgent.indexOf("MSIE 6.0") > 0) {
					if (windowHeight == 800) {
						heightJX = 68;
					} else if (windowHeight == 1024) {
						heightJX = 52;
					} else {
						heightJX = 105;
					}
				} else {
					heightJX = 39;
				}
				var y_last = y - divCount * 30 - heightJX;
				if (windowHeight == 1024) {
					y_last = y_last + 15;
				}
				$first.css("height", y_last + "px");
				if (divHeight > y_last) {
					$first.css("overflow-y", "scroll");
				}
			}
		});
		var $img = $("img");
		$obj1.each(function(i) {
			i++;
			if (i != 1) {
				$("#ico_submenuh2_" + i).attr("src",
						"/skin/Default/images/MantoEye/submenu/ico_have.gif");
			}
		});
	}
	function openTab(url,premId,name){
		tabpanel.addTab( {
					title : name,
					id : premId,
					html : '<iframe id="'+premId+'" src="'+url+'?1=1" width="100%" height="100%" frameborder="0"></iframe>'
				});
	}	
	//二期开发添加chenchengle
	function openTab1(url,premId,name){
		tabpanel.addTab( {
					title : name,
					id : premId,
					html : '<iframe id="'+premId+'" src="'+url+'" width="100%" height="100%" frameborder="0"></iframe>'
				});
	}
	function killTab(id){
		for ( var i = 0; i < tabpanel.tabs.length; i++) {
				if (id == tabpanel.tabs[i].id) {
					tabpanel.kill(id);//存在则关掉此tab,然后重新打开
					break;
				}
			}
	}
$(document).ready(function(){
	window.onerror = killErrors;
})
function killErrors() {
	return true;
}	
</script>



