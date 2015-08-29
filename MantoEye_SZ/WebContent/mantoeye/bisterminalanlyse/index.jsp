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
		<form action="/bisterminalanlyse_list.do?1=1" name="searchForm"
			id="mainForm" method="post">
			<div class="header">
				<div class="hand"></div>
				<div class="local">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<th>
								<ul>
									<li>
										您的位置: 终端分析&nbsp;&gt;&nbsp;
									</li>
									<li>
										业务终端分析
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
											<th style="width: 100px;">
												请选择终端：
											</th>
											<td style="width: 200px; align: middle">
												<input id="phoneModel" type="text" name="filter_LIKE_vcName"
													style="width: 200px;" onclick="showDialog()"
													value="${param['filter_LIKE_vcName']}"
													style="border:1px solid #999999;height:20px;" />
											</td>
											<th style="width: 120px;">
												&nbsp;&nbsp;&nbsp;&nbsp;请选择业务：
											</th>
											<td style="width: 200px; align: middle">
												<input type="text" name="filter_LIKE_vcNumber" id="business"
													style="width: 200px;"
													value="${param['filter_LIKE_vcNumber']}"
													onclick="showBusinessDialog()"
													style="border:1px solid #999999;height:20px;" />
											</td>
											<td style="width: 100px;">
												&nbsp;&nbsp;&nbsp;&nbsp;请选择周：
											</td>
											<td>
												<input type="text" class="Wdate"
													onclick="new WdatePicker({maxDate:'#F{$dp.$D(\'end\')}',dateFmt:'yyyy-MM-dd 00:00:00'})"
													name="filter_GE|DATE_dtRecordTime"
													value="${param['filter_GE|DATE_dtRecordTime']}" id="start">
												<input type="hidden" id="end">
											</td>
											<td>
												<div id="mainbtn" style="padding-top: 3px;">
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
										<a href="#" style="width: 46px;"><span>导出</span> </a>
									</li>
								</ul>
							</div>
						</legend>
						<!----------- 数据列表 ------------------>
						<div id="cc"
							style="height: 100%; width: 100%; overflow-x: scroll; overflow-y: hidden;">
							<table id="datatable"
								style="width: auto; border-collapse: collapse; margin-top: 3px"
								cellpadding="0" cellspacing="1">
								<%
									List<List> list = (List) request.getAttribute("dataList");
									List list1 = list.get(0);
								%>
								<tr id="datatr">
									<%
										for (int k = 0; k < list1.size(); k++) {
											String str = (String) list1.get(k);
									%>
									<th width="125" nowrap=true
										style="height: 24px; background-color: #DFEAFB; color: #1C568A; font-weight: bold; padding-left: 2px; text-align: center;"><%=str%></th>
									<%
										}
									%>
								</tr>
								<%
									for (int i = 1; i < list.size(); i++) {
										List listdata = list.get(i);
										if (i % 2 == 0) {
								%>
								<tr class='dg_itemstyle'>
									<%
										for (int j = 0; j < listdata.size(); j++) {
													String str = (String) listdata.get(j);
									%>
									<td style="width: auto"><%=str%></td>
									<%
										}
									%>
								</tr>
								<%
									} else {
								%>
								<tr class='dg_alternatingitemstyle'>
									<%
										for (int j = 0; j < listdata.size(); j++) {
													String str = (String) listdata.get(j);
									%>
									<td style="width: auto"><%=str%></td>
									<%
										}
									%>
								</tr>
								<%
									}
									}
								%>

							</table>
							<page:pageButton checkboxName="ck" tbClassName="dg_pagestyle"
								tdClassName="pageinfo" tdClassName1="pagebutton" onsubmit="1"
								url="/bisterminalanlyse_list.do?1=1" />

							<div>
					</fieldset>
				</div>
			</div>
		</form>
	</body>
</html>

<script language="javascript">

	function showDialog(){
	var value = window.showModalDialog("../test/multiDialog_initPhone.do","","dialogWidth=680px;dialogHeight=420px;scroll=no;center=yes");
	if(value!=''){
		$('#phoneModel').attr("value",value);
	}
}

function showBusinessDialog(){
	var value = window.showModalDialog("/multiDialog_initBusiness.do","","dialogWidth=680px;dialogHeight=420px;scroll=no;center=yes");
	if(value!=''){
		$('#business').attr("value",value);
	}
}

function changesize(){
		var $tr=$("#datatr");
		
	}
	
</script>
