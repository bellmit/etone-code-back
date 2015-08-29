<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/include/session.jsp"%>
<%@ include file="/include/setcache.jsp"%>
<html>
	<head>
		<title></title>
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
	<body id="master">
		<form action="/blocUserAttach_list.do?1=1" name="searchForm"
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
										用户归属分布
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
									<table cellpadding="0" cellspacing="0" width="100%"
										id="condition_table">
										<tr>
											<th>
												<input type="radio" id="viewtype" value="1" name="viewtype"
													style="width: 20px" onclick="changeView(this.value)">
												列表
												<input type="radio" id="viewtype1" value="2" name="viewtype"
													style="width: 20px" onclick="changeView(this.value)"
													checked>
												图表
											</th>
											<td>
												&nbsp;
											</td>
											<th>
												时间粒度：
											</th>
											<td>
												<select name="timeLevel" id="timeLevel"
													onchange="changeTime()">
													<option value="day">
														天
													</option>
													<option value="week">
														周（周日~周六）
													</option>
													<option value="month">
														月
													</option>
													<option value="hour">
														小时
													</option>
												</select>
											</td>
											<th>
												时间：
											</th>
											<td>
												<input type="text" class="Wdate" style="display: block"
													onclick="new WdatePicker({dateFmt:'yyyy-MM-dd'})"
													id="timeDay" name="timeDay" value="2009-10-22">
												<input type="text" class="Wdate" style="display: none"
													id="timeWeek" name="timeWeek"
													onclick="new WdatePicker({isShowWeek:true,onpicked:function(){$dp.$('timeWeek').value=$dp.cal.getP('W','第'+'W'+'周');}})"
													value="第10周">
												<input type="text" class="Wdate" style="display: none"
													onclick="new WdatePicker({dateFmt:'yyyy-MM'})"
													id="timeMonth" name="timeMonth" value="2009-10">
												<input type="text" class="Wdate" style="display: none"
													onclick="new WdatePicker({dateFmt:'yyyy-MM-dd HH:00:00'})"
													id="timeHour" name="timeHour" value="2009-10-22 01:00:00">
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
									<th width="*">
										时间
									</th>
									<th width="*">
										用户归属
									</th>
									<th width="*">
										用户数
									</th>
									<th width="*">
										总流量(MB)
									</th>
									<th width="*">
										上行流量(MB)
									</th>
									<th width="*">
										下行流量(MB)
									</th>
									<th width="*">
										激活次数
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
											</tr>
											<%
												totalRow++;
											%>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr class="dg_itemstyle">
											<td colspan="5" align='center'>
												<b>&nbsp; 抱歉! 没有找到相关的记录!</b>
											</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</table>
						</div>
						<div id="datachart" class="content" style="display: block">
							<table width="100%" cellpadding="0" cellspacing="1"
								class="dg_borderstyle">
								<tr>
									<td align="center">
										<input type="hidden" id="chartXml" value="${xml }">
										<iframe id="frm" name="frm"
											src="/flash/BaseChart.html?style=base_pie_1280&subcharts=1_2_5"
											scrolling="no"
											style="HEIGHT: 350px; VISIBILITY: inherit; width: 620px; Z-INDEX: 1"
											marginwidth="1" marginheight="1" border="0" frameborder="0"
											align="left"></iframe>
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
//时间选择事件
function changeTime(){
	$('#condition_table td:eq(2) input').css({display:"none"});
	var timeLevel = $("#timeLevel option:selected").val();
	if(timeLevel=='day'){
		$('#timeDay').css({display:"block"});
	}else if(timeLevel=='week'){
		$('#timeWeek').css({display:"block"});
	}else if(timeLevel=='month'){
		$('#timeMonth').css({display:"block"});
	}else if(timeLevel=='hour'){
		$('#timeHour').css({display:"block"});
	}
}
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
	
}
function initChart(){
	return "TEST_WEB_DISTRIBUTE_AREA_DAY|1|2008-1-1 00:00:00|全局数据";
}
function initChartData(flag){
	return document.getElementById("chartXml").value;
}
</script>
