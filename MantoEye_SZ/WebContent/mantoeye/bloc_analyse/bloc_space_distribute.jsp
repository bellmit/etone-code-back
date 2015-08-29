<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/include/session.jsp"%>
<%@ include file="/include/setcache.jsp"%>
<html>
	<head>
		<title>空间分布</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/style.css" />
		<script src="/js/paging.js"></script>
		<script src="/js/nav.js"></script>
		<script language="javascript" src="/js/script.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
		<script type="text/javascript" src="/common/dtree/dltree.js"></script>
	</head>
	<body id="master" onload="changeSpaceView(${spaceLevel });">
		<form action="/blocSpaceDistribute_list.do?1=1" name="searchForm"
			id="mainForm" method="post">
			<div class="header">
				<div class="hand"></div>
				<div class="local">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<th>
								<ul>
									<li>
										您的位置: 业务分析&nbsp;&gt;&nbsp;
									</li>
									<li>
										集团业务分析&nbsp;&gt;&nbsp;
									</li>
									<li>
										空间分布
									</li>
								</ul>
							</th>
							<td class="rtd">
								&nbsp;<jsp:include page="/include/pageparm.jsp"></jsp:include>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div id="mainareacontent">
				<div id="query_area">
					<div id="query_upimg">
						<div class="upleftcircle"></div>
						<div class="upmiddlecircle"></div>
						<div class="uprightcircle"></div>
					</div>
					<div id="query_condition">
						<table width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="form">
									<table cellpadding="0" cellspacing="0" width="100%">
										<tr>
											<th>
												<input type="radio" id="viewtype1" value="2" name="viewtype"
													style="width: 20px" checked
													onclick="changeView(this.value)">
												地图
												<input type="radio" id="viewtype" value="1" name="viewtype"
													style="width: 20px" onclick="changeView(this.value)">
												列表
											</th>
											<td>
												&nbsp;
											</td>
											<th>
												集团APN：
											</th>
											<td>
												<select name="blocApn" id="blocApn">
													<option value="apn1"
														<c:if test="${fn:contains(blocApn,'apn1') }">selected</c:if>>
														APN1
													</option>
													<option value="apn2"
														<c:if test="${fn:contains(blocApn,'apn2') }">selected</c:if>>
														APN2
													</option>
													<option value="apn3"
														<c:if test="${fn:contains(blocApn,'apn3') }">selected</c:if>>
														APN3
													</option>
													<option value="apn4"
														<c:if test="${fn:contains(blocApn,'apn4') }">selected</c:if>>
														APN4
													</option>
												</select>
											</td>
											<th>
												时间粒度：
											</th>
											<td>
												<select name="timeLevel" id="timeLevel">
													<option value="month"
														<c:if test="${fn:contains(timeLevel,'month') }">selected</c:if>>
														月
													</option>
													<option value="week"
														<c:if test="${fn:contains(timeLevel,'week') }">selected</c:if>>
														周（周日~周六）
													</option>
													<option value="day"
														<c:if test="${fn:contains(timeLevel,'day') }">selected</c:if>>
														天
													</option>
													<option value="hour"
														<c:if test="${fn:contains(timeLevel,'hour') }">selected</c:if>>
														小时
													</option>
												</select>
											</td>
											<th>
												时间：
											</th>
											<td>
												<input type="text" class="Wdate"
													onclick="new WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00'})"
													name="searchDateStart" value="${searchDateStart}"
													id="searchDateStart">
											</td>
											<th>
												区域维度：
											</th>
											<td>
												<select name="spaceLevel" id="spaceLevel">
													<option value="bsc"
														<c:if test="${fn:contains(spaceLevel,'bsc') }">selected</c:if>>
														BSC
													</option>
													<option value="sgsn"
														<c:if test="${fn:contains(spaceLevel,'sgsn') }">selected</c:if>>
														SGSN
													</option>
													<option value="street"
														<c:if test="${fn:contains(spaceLevel,'street') }">selected</c:if>>
														街道办
													</option>
													<option value="marea"
														<c:if test="${fn:contains(spaceLevel,'marea') }">selected</c:if>>
														营销片区
													</option>
													<option value="branch"
														<c:if test="${fn:contains(spaceLevel,'branch') }">selected</c:if>>
														分公司
													</option>
												</select>
											</td>
											<td>
												<div id="mainbtn">
													<ul>
														<li>
															<a href="#" onclick="searchForm.submit();" 　title="查询"><span>查询</span>
															</a>
														</li>
														<!--  
														<li>
															<a href="#" onclick="searchForm.reset();" title="重置"><span>重置</span>
															</a>
														</li>
														-->
													</ul>
												</div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</div>
					<div id="query_downimg">
						<div class="downleftcircle"></div>
						<div class="downmiddlecircle"></div>
						<div class="downrightcircle"></div>
					</div>
				</div>

				<div class="mainarea">
					<!------------ 按钮列表 ------------------>
					<fieldset>
						<legend align="left">
							<div id="mainbtn">
								<ul>
									<li>
										<a href="#" style="width: 46px;"><span id="export_id">导出</span>
										</a>
									</li>
								</ul>
							</div>
						</legend>
						<!----------- 数据列表 ------------------>
						<div id="datagrid" class="content" style="display: none">
							<table width="100%" cellpadding="0" cellspacing="1"
								class="dg_borderstyle">
								<tr>
									<th width="*" id="spaceview">
										时间
									</th>
									<th width="*" id="spaceview">
										区域维度
									</th>
									<th width="*">
										用户数
									</th>
									<th width="*">
										总流量
									</th>
									<th width="*">
										上行流量
									</th>
									<th width="*">
										下行流量
									</th>
									<th width="*">
										访问次数
									</th>
									<th width="*">
										查看
									</th>
								</tr>
								<c:choose>
									<c:when test="${fn:length(dataList)>0}">
										<%
											int totalRow = 1;
										%>
										<c:forEach items="${dataList}" var="bean">
											<%
												if (totalRow / 2 * 2 == totalRow) {
											%>
											<tr class="dg_itemstyle">
												<%
													} else {
												%>
											
											<tr class="dg_alternatingitemstyle">
												<%
													}
												%>
												<td>
													${bean[0]}
												</td>
												<td>
													${bean[1]}
												</td>
												<td>
													${bean[2]}
												</td>
												<td>
													${bean[3]}
												</td>
												<td>
													${bean[4]}
												</td>
												<td>
													${bean[5]}
												</td>
												<td>
													${bean[6]}
												</td>
												<td>
													<img style="cursor: hand"
														src="/skin/Default/images/MantoEye/icon/yw.png" alt="业务构成"
														onclick="distribute('2','${bean[0]}')" />
												</td>
											</tr>
											<%
												totalRow++;
											%>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr class="dg_itemstyle">
											<td colspan=5 align='center'>
												<b>&nbsp; 抱歉! 没有找到相关的记录!</b>
											</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</table>
							<!--<page:pageButton checkboxName="ck" tbClassName="dg_pagestyle"
								tdClassName="pageinfo" tdClassName1="pagebutton" onsubmit="1"
								url="/blocSpaceDistribute_list.do?1=1" />-->
						</div>
						<div id="datachart" class="content" style="display: block">
							<table width="100%" cellpadding="0" cellspacing="1"
								class="dg_borderstyle">
								<tr>
									<td>
										<input type="hidden" id="chartXml" value="${xml }">
										<iframe name="frm3" src="/flash/MantoEye_Main.html?1=1"
											scrolling="yes" id="frm3"
											style="HEIGHT: 354px; VISIBILITY: inherit; width: 1030px; Z-INDEX: 1"
											marginwidth="1" marginheight="1" border="0" frameborder="0"
											align="center"></iframe>
									</td>
								</tr>
							</table>
						</div>
					</fieldset>
				</div>
			</div>
		</form>
	</body>
</html>

<script>
//导出文件事件 
$(function() {
	$("#export_id").click(function(){
		
	})
})
function changeView(value){
   	if(value=="1"){
   		document.getElementById("datagrid").style["display"] = "block";
   		document.getElementById("datachart").style["display"] = "none";
   	}else{
   		document.getElementById("datagrid").style["display"] = "none";
   		document.getElementById("datachart").style["display"] = "block";
   	}
}
function distribute(flag,time){
   	//alert(${xml});	
	if(flag=="2"){
		//searchForm.action="/totalDistribute_list.do?time="+time;
	}	
	//searchForm.submit();
}
function changeSpaceView(value){
    var showTitel = "BSC";
    if(value=="sgsn"){
    showTitel = "SGSN";
    }
    if(value=="street"){
    showTitel = "街道办";
    }
    if(value=="marea"){
    showTitel = "营销片区";
    }
    if(value=="branch"){
    showTitel = "分公司";
    }
	spaceview.innerText = showTitel;
}
function initChart(){
	return "TEST_WEB_DISTRIBUTE_AREA_DAY|1|2008-1-1 00:00:00|全局数据";
}
function initChartData(flag){
	//alert("----"+document.getElementById("chartXml").value);
	return document.getElementById("chartXml").value;
}
function flashDbClick(name){
	//alert(name);
	//searchForm.action="/totalDistribute_list.do?time="+time;	
	//searchForm.submit();
}
</script>
