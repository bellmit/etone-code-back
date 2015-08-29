<%--<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:tvns>
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>布局页面</title>
		<link type="text/css" rel="stylesheet"
			href="/skin//Default/css/newstyle.css" />
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/TabPanel.css"
			type="text/css"></link>
		<script type="text/javascript"
			src="/js/jquery/extend.tab/js/TabPanel.js"></script>

	</head>


	<body id="master" onload="initMenu();">
		<div id="mainarea">
			<div id="leftpanl">
				<div class="userinfo">
					<div class="headimg"></div>
					<div class="userorle">
						<span>管理员</span>
					</div>
				</div>
				<!-- <div style="width:204px;height:2px; margin:0px;padding:0px;border-left: 1px solid #8DB2E3;border-bottom: 1px solid 
#8DB2E3;background-color:#DEECFD;"> -->

				<div class="fullinsert"></div>

				<div class="submenu">
					<h2 onclick="changeView(this.id)" id="submenuh2_1">
						业务分析
						<img src="/skin/Default/images/MantoEye/submenu/ico_nohave.gif"
							id="ico_submenuh2_1" />
					</h2>
					<div class="navigation" id="div_submenuh2_1"
						style="display: block;">
						<?IMPORT NAMESPACE = TVNS IMPLEMENTATION = "/skin/Default/images/MantoEye/submenu/treeview.htc" />
						<tvns:treeview
							systemimagespath="/skin/Default/images/MantoEye/submenu/TreeImages/"
							selectednodeindex="0">
							<tvns:treenode navigateurl="#" expanded="true" nodedata="2"
								target="">
                                   全网业务分析 
                                <tvns:treenode
									target="/mantoeye/data_analyse/total_distribute.jsp"
									expanded="false" nodedata="2" onclick="newTab(false,this)"
									id="totalflush" name="全网业务分布 ">
                                   全网业务分布 
                                </tvns:treenode>

								<tvns:treenode
									target="/mantoeye/data_analyse/space_distribute.jsp"
									expanded="false" nodedata="2" onclick="newTab(false,this)"
									id="spaceFlushDistribute" name="空间分布 ">
                                              空间分布
                                </tvns:treenode>

								<tvns:treenode
									target="/mantoeye/data_analyse/business_distribute.jsp"
									expanded="false" nodedata="2" id="businessFlushDistribute"
									name="业务分布" onclick="newTab(false,this)">
                                   	 业务分布
                                </tvns:treenode>

								<tvns:treenode target="/mantoeye/data_analyse/apn.jsp"
									expanded="false" nodedata="2" onclick="newTab(false,this)"
									id="apnFlushDistribute" name="APN分布 ">
                                     APN分布
                                </tvns:treenode>

								<tvns:treenode target="/mantoeye/data_analyse/wap.jsp"
									expanded="false" nodedata="2" onclick="newTab(false,this)"
									id="wapFlushDistribute" name="应用协议分布 ">
                                   	 应用协议分布
                                </tvns:treenode>

								<tvns:treenode target="/mantoeye/data_analyse/tcp.jsp"
									expanded="false" nodedata="2" onclick="newTab(false,this)"
									id="tcpFlushDistribute" name="传输协议分布 ">
                                            传输协议分布
                                </tvns:treenode>

								<tvns:treenode target="/mantoeye/data_analyse/userAttach.jsp"
									expanded="false" nodedata="2" onclick="newTab(false,this)"
									id="userAttachFlushDistribute" name="用户归属分布 ">
                                           用户归属分布
                                </tvns:treenode>

								<tvns:treenode target="/mantoeye/data_analyse/topuser.jsp"
									expanded="false" nodedata="2" onclick="newTab(false,this)"
									id="topUserFlushDistribute" name="用户数Top100小区 ">
                                          用户数Top100小区
                                </tvns:treenode>
								<tvns:treenode target="/mantoeye/data_analyse/topflush.jsp"
									expanded="false" nodedata="2" onclick="newTab(false,this)"
									id="topFlushFlushDistribute" name="流量Top100小区 ">
                                          流量Top100小区
                                </tvns:treenode>

							</tvns:treenode>
							<tvns:treenode navigateurl="#" expanded="true" nodedata="2"
								target="frmmain">
                                   集团业务分析  
                                   
                                	<tvns:treenode
									target="/mantoeye/groupBisness/areadistribute.jsp"
									expanded="false" nodedata="2" onclick="newTab(false,this)"
									id="apnAreaDistribute" name="集团APN空间分布 ">
                                            APN空间分布 
                                </tvns:treenode>

								<tvns:treenode target="/mantoeye/bloc_analyse/bloc_apn.jsp"
									expanded="false" nodedata="2" onclick="newTab(false,this)"
									id="blocApnDistribute" name="集团APN分布 ">
                                              APN分布 
                                </tvns:treenode>

								<tvns:treenode
									target="/mantoeye/groupBisness/groupbisuserbelong.jsp"
									expanded="false" nodedata="2" onclick="newTab(false,this)"
									id="apnUserBelong" name="APN用户归属 ">
                                            用户归属分布
                                </tvns:treenode>
							</tvns:treenode>

							<tvns:treenode navigateurl="#" expanded="true" nodedata="2"
								target="frmmain">
                                   大流量行为分析  
                                <tvns:treenode
									target="/mantoeye/valueset/big_flush_display.jsp"
									expanded="false" nodedata="2" expanded="false" nodedata="2"
									onclick="newTab(false,this)" id="bigflush" name="大流量用户">
										大流量用户
									</tvns:treenode>

								<tvns:treenode target="/mantoeye/valueset/blackuser.jsp"
									expanded="false" nodedata="2" expanded="false" nodedata="2"
									onclick="newTab(false,this)" id="blackuser" name="黑名单管理">
										黑名单管理
									</tvns:treenode>
								<tvns:treenode target="/mantoeye/valueset/limitvalue.jsp"
									expanded="false" nodedata="2" expanded="false" nodedata="2"
									onclick="newTab(false,this)" id="bigFlushValse" name="大流量阀值">
											大流量阀值设置

									</tvns:treenode>
							</tvns:treenode>
							<tvns:treenode navigateurl="#" expanded="true" nodedata="2"
								target="frmmain">
                                  用户数据提取
                                  
                                  <tvns:treenode
									target="/mantoeye/data_catch/data_catch_index.jsp"
									expanded="false" nodedata="2" onclick="newTab(false,this)"
									id="historyDataCatch" name="历史原始记录提取">
                                              历史原始记录提取 
                                </tvns:treenode>

								<tvns:treenode
									target="/mantoeye/data_catch/data_catch_index.jsp"
									expanded="false" nodedata="2" onclick="newTab(false,this)"
									id="realTimeDataCatch" name="实时原始数据提取">
                                               实时原始数据提取 
                                </tvns:treenode>
								<tvns:treenode
									target="/mantoeye/data_catch/data_catch_index.jsp"
									expanded="false" nodedata="2" onclick="newTab(false,this)"
									id="historyAnalyseDataCatch" name="历史解析纪录提取">
                                               历史解析纪录提取 
                                </tvns:treenode>

								<tvns:treenode
									target="/mantoeye/data_catch/data_catch_index.jsp"
									expanded="false" nodedata="2" onclick="newTab(false,this)"
									id="userNumberDataCatch" name="用户号码提取">
                                                用户号码提取
                                </tvns:treenode>

							</tvns:treenode>
						</tvns:treeview>
					</div>
					<h2 onclick="changeView(this.id)" id="submenuh2_2">
						终端分析
						<img src="/skin/Default/images/MantoEye/submenu/ico_nohave.gif"
							id="ico_submenuh2_2" />
					</h2>
					<div class="navigation" id="div_submenuh2_2"
						style="display: block;"">
						<?IMPORT NAMESPACE = TVNS IMPLEMENTATION = "/skin/Default/images/MantoEye/submenu/treeview.htc" />
						<tvns:treeview
							systemimagespath="/skin/Default/images/MantoEye/submenu/TreeImages/"
							selectednodeindex="0">
							
                             <tvns:treenode
								target="/mantoeye/terminalbisanayse/index.jsp"
								expanded="false" nodedata="2" id="terminalanalyse"
								name="终端业务分析" onclick="newTab(false,this)">终端业务分析 
                             </tvns:treenode>
                             
                              <tvns:treenode
								target="/mantoeye/terminalbisanayse/index.jsp"
								expanded="false" nodedata="2" id="terminalanalyse"
								name="业务终端分析" onclick="newTab(false,this)">业务终端分析
                             </tvns:treenode>
							  <tvns:treenode
								target="/mantoeye/terminalmanager/index.jsp"
								expanded="false" nodedata="2" id="terminalmanager"
								name="终端管理" onclick="newTab(false,this)">终端管理
                             </tvns:treenode>
						</tvns:treeview>
					</div>
					<h2 onclick="changeView(this.id)" id="submenuh2_3">
						规则管理
						<img src="/skin/Default/images/MantoEye/submenu/ico_nohave.gif"
							id="ico_submenuh2_3" />
					</h2>
					<div class="navigation" id="div_submenuh2_3"
						style="display: block;">
						<?IMPORT NAMESPACE = TVNS IMPLEMENTATION = "/skin/Default/images/MantoEye/submenu/treeview.htc" />
						<tvns:treeview
							systemimagespath="/skin/Default/images/MantoEye/submenu/TreeImages/"
							selectednodeindex="0">
							<tvns:treenode expanded="false" nodedata="2"
								id="businessSiteMana" name="业务配置"
								target="/mantoeye/business_rules/business_site/index.jsp"
								onclick="newTab(false,this)">
                                 业务配置
                                </tvns:treenode>
							<tvns:treenode expanded="false" nodedata="2"
								id="businessVetorMane" name=" 规则配置"
								target="/mantoeye/business_rules/vetor/index.jsp"
								onclick="newTab(false,this)">
                                规则配置
                                </tvns:treenode>
							<tvns:treenode nexpanded="false" nodedata="2"
								id="businessRuleTest" name="业务规则拨测维护"
								onclick="newTab(false,this)"
								target="/mantoeye/business_rules/ruletest/index.jsp"> 
                                   业务规则拨测维护 
                         </tvns:treenode>
							<tvns:treenode nexpanded="false" nodedata="2"
								id="businessMainflush" name="流量碰撞率异常统计"
								onclick="newTab(false,this)"
								target="/mantoeye/business_rules/mainflush/index.jsp">
                                 流量碰撞率异常统计  
                                </tvns:treenode>
							<tvns:treenode nexpanded="false" nodedata="2"
								id="businessAssistStat" name="辅向量不匹配统计"
								onclick="newTab(false,this)"
								target="/mantoeye/business_rules/assiststat/index.jsp">
                                辅向量不匹配统计
                                </tvns:treenode>
							<tvns:treenode nexpanded="false" nodedata="2"
								id="businessRecognise" name="全量业务自发现"
								onclick="newTab(false,this)"
								target="/mantoeye/business_rules/recognise/index.jsp">
                               全量业务自发现
                                </tvns:treenode>
							<tvns:treenode nexpanded="false" nodedata="2"
								id="businessTopflush" name="业务流量统计排名"
								onclick="newTab(false,this)"
								target="/mantoeye/business_rules/topflush/index.jsp">
                                 业务流量统计排名
                                </tvns:treenode>
						</tvns:treeview>
					</div>

					<h2 onclick="changeView(this.id)" id="submenuh2_4">
						系统管理
						<img src="/skin/Default/images/MantoEye/submenu/ico_nohave.gif"
							id="ico_submenuh2_4" />
					</h2>
					<div class="navigation" id="div_submenuh2_4"
						style="display: block;">
						<?IMPORT NAMESPACE = TVNS IMPLEMENTATION = "/skin/Default/images/MantoEye/submenu/treeview.htc" />
						<tvns:treeview
							systemimagespath="/skin/Default/images/MantoEye/submenu/TreeImages/"
							selectednodeindex="0">
					<!--  	<tvns:treenode
								navigateurl="/dept_list.do?1=1&permId=SYSTEM_MANAGER_DEPT"
								expanded="true" nodedata="2" target="frmmain">
                                   部门管理
                            	</tvns:treenode>-->	
                            	<tvns:treenode nexpanded="false" nodedata="2"
								id="usermana" name=" 用户管理"
								onclick="newTab(false,this)"
								target="/user_list.do?1=1&permId=SYSTEM_MANAGER_ROLE">
                                 用户管理
                                </tvns:treenode>
						<tvns:treenode nexpanded="false" nodedata="2"
								id="rolemana" name="角色管理"
								onclick="newTab(false,this)"
								target="/role_list.do?1=1&permId=SYSTEM_MANAGER_ROLE">
                                 角色管理
                                </tvns:treenode>
                                		<tvns:treenode nexpanded="false" nodedata="2"
								id="memnmana" name="模块配置"
								onclick="newTab(false,this)"
								target="/menu_list.do?1=1&permId=CONFIG_MANAGER_MEMU">
                                   模块配置
                                </tvns:treenode>
                                		<tvns:treenode nexpanded="false" nodedata="2"
								id="logmana" name="系统日志"
								onclick="newTab(false,this)"
								target="/log_list.do?1=1&permId=SYSTEM_MANAGER_OPLOG">
                                系统日志
                                </tvns:treenode>
			<!-- 				<tvns:treenode
								navigateurl="/rolegroup_list.do?1=1&permId=SYSTEM_MANAGER_ROLEGROUP"
								expanded="false" nodedata="2" target="frmmain">
                                    角色组管理
                                </tvns:treenode>
							<tvns:treenode
								navigateurl="/log_list.do?1=1&permId=SYSTEM_MANAGER_OPLOG"
								expanded="false" nodedata="2" target="frmmain">
                                   系统日志
                                </tvns:treenode>
							<tvns:treenode
								navigateurl="/menu_list.do?1=1&permId=CONFIG_MANAGER_MEMU"
								expanded="false" nodedata="2" target="frmmain">
                                    模块配置
                                </tvns:treenode> -->
						</tvns:treeview>
					</div>

				<!--  	<h2 onclick="changeView(this.id)" id="submenuh2_5">
						测&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;试
						<img src="/skin/Default/images/MantoEye/submenu/ico_nohave.gif"
							id="ico_submenuh2_5" />
					</h2>
					<div class="navigation" id="div_submenuh2_5"
						style="display: block;">
						<?IMPORT NAMESPACE = TVNS IMPLEMENTATION = "/skin/Default/images/MantoEye/submenu/treeview.htc" />
						<tvns:treeview
							systemimagespath="/skin/Default/images/MantoEye/submenu/TreeImages/"
							selectednodeindex="0">
							<tvns:treenode navigateurl="/mantoeye/test/test.jsp"
								expanded="false" nodedata="2" target="frmmain">
                                   起草新合同  
                                </tvns:treenode>
							<tvns:treenode id="flashchart" name="flash图表"
								target="/mantoeye/test/flash/chart.jsp?1=1&permId=CONFIG_MANAGER_MEMU"
								expanded="false" nodedata="2" onclick="newTab(false,this)">
                                    flash图表
                                </tvns:treenode>
							<tvns:treenode navigateurl="/mantoeye/test/flash/map.jsp"
								expanded="false" nodedata="2" target="frmmain">
                                   flash地图
                                </tvns:treenode>
							</tvns:treenode>
							<tvns:treenode navigateurl="#" expanded="true" nodedata="2">
                                   终端复选框 
                           </tvns:treenode>

							<tvns:treenode expanded="false" nodedata="2" id="apn"
								name="APN分布" target="/apnDistribute_list.do"
								onclick="newTab(false,this)">
                                   (tab1)APN分布
                           </tvns:treenode>
							<tvns:treenode expanded="false" nodedata="2" id="wap"
								name="应用协议分布" target="/wapDistribute_list.do"
								onclick="newTab(false,this)">
                                   (tab2)应用协议分布
                           </tvns:treenode>
							<tvns:treenode expanded="false" nodedata="2" id="tcp"
								name="传输协议分布" target="/tcpDistribute_list.do"
								onclick="newTab(false,this)">
                                   (tab3)传输协议分布
                           </tvns:treenode>
							</tvns:treenode>
							<tvns:treenode expanded="false" nodedata="2" id="role"
								name="角色管理"
								target="/role_list.do?1=1&permId=SYSTEM_MANAGER_ROLE"
								onclick="newTab(false,this)">
                                   角色管理
                                </tvns:treenode>
							<tvns:treenode expanded="false" nodedata="2" id="tab" name="tab"
								target="/mantoeye/tab/sumbitTab.jsp"
								onclick="newTab(false,this)">
                                   tab
                                </tvns:treenode>
							<tvns:treenode expanded="false" nodedata="2" id="tab" name="tab"
								target="/mantoeye/tab/sumbitTab.jsp"
								onclick="newTab(false,this)">
                                   tab
                                </tvns:treenode>
							<tvns:treenode expanded="false" nodedata="2" id="datagrid1"
								name="tab" target="/mantoeye/test/flexigrid/test.jsp"
								onclick="newTab(false,this)">
                                   数据列表显示样式
                                </tvns:treenode>
							<tvns:treenode expanded="false" nodedata="2" id="datagrid2"
								name="业务分布" target="/mantoeye/test/gtgrid/test3.jsp"
								onclick="newTab(false,this)">
                                  数据列表显示样式1
                                </tvns:treenode>
							<tvns:treenode expanded="false" nodedata="2" id="dialog_test2"
								name="弹出复选框" target="/mantoeye/dialog/dialog.jsp"
								onclick="newTab(false,this)">
							弹出复选框
                                </tvns:treenode>
							<tvns:treenode expanded="false" nodedata="2" id="datagrid3"
								name="全网流量" target="/test.jsp" onclick="newTab(false,this)">
                                  整体布局
                                </tvns:treenode>
							<tvns:treenode expanded="false" nodedata="2" id="dateeagrid3"
								name="TOOLBAR" target="/mantoeye/tab/toolbar.jsp"
								onclick="newTab(false,this)">
                                  TOOLBAR
                                </tvns:treenode>
						</tvns:treeview>
					</div>-->

				</div>
				<div class="middleline"></div>
			</div>
			<div style="height: 640px; _height: 690px;" id="container_content"></div>

		</div>
	</body>
</html>

<script language="javascript">
var tabpanel;
$(document).ready(function(){
	tabpanel = new TabPanel({
		renderTo:'container_content',
		//width:'300px', //如果设置了宽度,就不会自动改变宽度
		//height:'400px',
		//border:'none',
		active : 0,
		maxLength: 10,
		items : [{
			id:'system_main_id',
			title:'系统首页',
			html:'<iframe src="/info.jsp" width="100%" height="100%" frameborder="0"></iframe>',
			closable: false
		}]
	});
	
	if(window.screen.availWidth==1024){
		$("#container_content").height(610);
		
	}else{
		$("#container_content").height(630);
	}
	
});

/**
打开新tab
	(注意:ID必须唯一)
	空间分布:spaceFlushDistribute 
	业务分布:businessFlushDistribute
	APN分布:apnFlushDistribute
	应用协议分布:wapFlushDistribute
	传输协议分布:businessFlushDistribute
	用户归属分布:userAttachFlushDistribute
*/
var tabId;//用于其他页面判断tabid
function newTab(flag,obj,date){
	if(flag){//若从全网统计过来的数据,则判断,如是已激活的tab,则先关闭
		for(var i = 0 ; i<tabpanel.tabs.length;i++){
			if(obj.id==tabpanel.tabs[i].id){
				tabpanel.kill(obj.id);//存在则关掉此tab,然后重新打开
				break;
			}
		}
	}
	tabId = obj.id;
//	obj.target=obj.target+"?1=1";
	tabpanel.addTab({title:obj.name,id:obj.id, html:'<iframe src="'+obj.target+'" width="100%" height="100%" frameborder="0"></iframe>'});
}


function feedback(){
	tabpanel.addTab({title:'问题反馈',id:'feedback', html:'<iframe id="feedback" src="/feedback_list.do?1=1" width="100%" height="100%" frameborder="0"></iframe>'});	
}

function changeView(obj) {
	var y = document.body.clientHeight;
	if (document.all("div_" + obj).style.display == "block") {
		document.all("div_" + obj).style.display = "none";
		document.all("ico_" + obj).src = "/skin/Default/images/MantoEye/submenu/ico_have.gif";
	} else {
		var $crrentObj = $("#div_" + obj);
		var $divobj = $(".navigation");
		$divobj.each(function (i) {
			if (this.id != "div_" + obj) {
				$(this).hide();
			} else {
				var divCount = $divobj.length;
				var divHeight = $(this).height();
				var heightJX;
				if(navigator.userAgent.indexOf("MSIE 6.0")>0){
					heightJX=50;
				}else{
					heightJX=39;
				}
				var y_last = y - divCount * 30-heightJX;
				$(this).css("height", y_last + "px");
				if (divHeight > y_last) {
					$(this).css("overflow-y", "scroll");
				}
				$(this).show();
			}
		});
		$imgObj = $("img");
		$imgObj.each(function (i) {
			if (this.id != "ico_" + obj) {
				$(this).attr("src", "/skin/Default/images/MantoEye/submenu/ico_have.gif");
			} else {
				$(this).attr("src", "/skin/Default/images/MantoEye/submenu/ico_nohave.gif");
			}
		});
	}
	defaultView = obj;
}
function initMenu() {
	var y = document.body.clientHeight;
	var $obj1 = $("h2");
	$obj1.each(function (i) {
		i++;
		if (i != 1) {
			$("#div_submenuh2_" + i).hide();
		} else {
			var $first = $("#div_submenuh2_" + 1);
			var divCount = $obj1.length;
			var divHeight = $first.height();
			if(navigator.userAgent.indexOf("MSIE 6.0")>0){
					heightJX=50;
				}else{
					heightJX=39;
				}
			var y_last = y - divCount * 30-heightJX;
			$first.css("height", y_last + "px");
			if (divHeight > y_last) {
				$first.css("overflow-y", "scroll");
			}
		}
	});
	var $img = $("img");
	$obj1.each(function (i) {
		i++;
		if (i != 1) {
			$("#ico_submenuh2_" + i).attr("src", "/skin/Default/images/MantoEye/submenu/ico_have.gif");
		}
	});
}

</script>
 --%>


