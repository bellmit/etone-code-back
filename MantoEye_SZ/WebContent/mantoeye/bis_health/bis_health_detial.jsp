<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>业务健康统计</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" type="text/css"
			href="/tools/gt-grid/gt_grid.css" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/maincontent.css" />
		<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js">
</script>
		<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js">
</script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js">
</script>
		<!-- 列表工具栏 -->
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
			type="text/css"></link>
		<script type="text/javascript"
			src="/js/jquery/extend.tab/js/Toolbar.js">
</script>
		<script type="text/javascript" src="/js/common_grid.js">
</script>

	</head>
	<body>
		<iframe id="download" name="download" height="0px" width="0px"></iframe><!--用iframe模拟文件下载-->
		<table id="maincontent" cellspacing="0" cellpadding="0" border="0"
			bgcolor="#D3E0F2" width="100%" height="100%">
			<tr>
				<td colspan="2" height="5px"></td>
			</tr>
			<tr>
				<td>
					<table cellspacing="0" cellpadding="0" border="0" bgcolor="#ffffff"
						style="width: 100%;">
						<tr valign="top">
							<td>
								<table cellspacing="0" cellpadding="0" border="0" width="100%">
									<tr valign="top" style="font-size: 1px;">
										<table cellspacing="0" cellpadding="0" border="0" width="100%">
											<tr>
												<td style="font-size: 1px; width: 4px;">
													<div class="leftcircle"></div>
												</td>
												<td width="100%">
													<div class="middlecircle"></div>
												</td>
												<td width="4px">
													<div class="rightcircle"></div>
												</td>
											</tr>
										</table>
									</tr>
									<tr valign="top">
										<td width="100%" class="condition_down">
											<table id="query_condition" cellspacing="0px"
												cellpadding="0px" boder="0">
												<tr valign="middle">

													<td class="condition_name">
														业务类型：
													</td>
													<td class="condition_value" id="number">

														<select name="taskType" id="taskType" style="height: 21px;width:130px;"
															onchange="changTaskType()">
															<option value="1" selected>
																业务连接成功率
															</option>
															<option value="2">
																业务请求成功率
															</option>
															<option value="3">
																业务连接响应
															</option>
															<option value="4">
																业务使用情况统计
															</option>
															<option value="5">
																持续发展指标统计
															</option>
															<option value="6" selected>																
统计结果
</option>
														</select>
													</td>


												
													<td>
														<div id="mainbtn" style="margin-left: -6px">
															<ul>


																<li>


																	<a href="javascript:query();" 　title="查询"><span>查询</span>


																	</a>


																</li>


															</ul>
														</div>
													</td>

												</tr>

											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>



						<tr>
							<td height="5px" bgcolor="#D3E0F2" width="100%"></td>
						</tr>


						<tr>
							<td>
								<table cellspacing="0" cellpadding="0" border="0" width="100%"
									class="menubar">
									<tr valign="top">
										<td width="4px" height="24px">
											<div class="lefttitle"></div>
										</td>
										<td width="100%" height="24px">
											<div class="middletitle">
												业务健康度详细列表
											</div>
										</td>
										<td width="4px">
											<div class="righttitle"></div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<!-- ------------------>
						<tr id="datagridtr" ><td>
					<table width="100%" id="datagridtable"  cellspacing="0px" bgcolor="#D3E0F2" 
						 cellpadding="0px" boder="0"><tr>
							<td id="datagridtd">
								<div id="toolbar" style="height: 25px"></div>
								<div class="gt-panel"
									style="width: 100%; margin: 0px; margin-bottom: 2px;">
									<div id="data_container"></div>
								</div>
								
							</td></tr>
					
							</table><tr><td id="othertd" style="height: 25px">&nbsp;</td></tr></td>
						</tr>


<tr id="networkResultTr">

								<table id="networkResultTable" width="1024px" border="1"
									cellspacing="0" bordercolordark="#F4F6F5">
									<tr bgcolor="#CDEOFF">
										<td height="24px" colspan="6">
								<div id="toolbar2" style="height: 25px"></div>			</td>
									</tr>
									<tr>
										<!-- --------------------标

题------------------------------ -->
										<td width="16%" dispaly="border:1">
											<b>业务</b>
										</td>
										<td width="20%">
											<b>时间</b>
										</td>
										<td width="16%">
											<b>KQI</b>
										</td>
										<td align="right" width="16%">
											<b>计算值</b>
										</td>
										<td align="right" width="16%">
											<b>权重</b>
										</td>
										<td align="right" width="16%">
											<b>TO值</b>
										</td>
									</tr>
									<!-- ------------------------动态显示数

据----------------------------------- -->
									<c:choose>
										<c:when test="${fn:length(networkResultList)>0}">
											<%
											int totalRow = 1;
										%>
											<c:forEach items="${networkResultList}" 

var="bean">

												<%
											
												if (totalRow / 2 * 2 == totalRow) {
											%>
												<tr>
													<%
													} else {
												%>

													<tr bgcolor="#F4F6F5">
														<%
													}
												%>
														<td>
															${bean.businessName}
														</td>
														<td>
															${bean.statdate}
														</td>
														<td>
															${bean.vcName}
														</td>
														<td align="right">
															${bean.intCount}
														</td>
														<td align="right">
															${bean.intScale}
														</td>
														<td align="right">
															${bean.intMark}
														</td>
													</tr>
													<%
												totalRow++;
											%>
												
											</c:forEach>
											<tr>
												<td colspan="5" align="center">
													<b>业务健康度</b>
												</td>
												<td align="right">
													${networkResultSum}
												</td>
											</tr>
										</c:when>
										<c:otherwise>
											<tr class="dg_itemstyle">
												<td colspan=6 align='center'>
													<b>&nbsp; 抱歉! 没有找到相

关的记录!</b>
												</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</table>
							</tr>







					</table>
				</td>
				<td width="2px"></td>
			</tr>
			<tr>
				<td colspan="2" height="5px"></td>
			</tr>
		</table>
	</body>
</html>

<script type="text/javascript">
var url = window.location.href;
var taskId = url.substring(url.indexOf('=') + 1, url.length);
var taskType = 6;
//初始化查询时间
var hasinit = false;//第一次初始化图形数据标识
var datachange = false;//第N次查询初始化数据标识
var sortnmFlush = false;
var dsConfig = {
	uniqueField : 'null',
	fields : [ {
		name : 'statdate'
	}, {
		name : 'intSuccess'
	}, {
		name : 'intTimes'
	}, {
		name : 'intAppSuccessful'
	}, {
		name : 'intAppTimes'
	}, {
		name : 'intAppAckTime'
	}, {
		name : 'intAppAckTimes'
	}, {
		name : 'intTaskType'
	}

	, {
		name : 'nmActiveUsers'
	}, {
		name : 'nmAllUsers'
	}, {
		name : 'intImsis'
	}, {
		name : 'nmUsers'
	}, {
		name : 'nmFlushKB'
	}, {
		name : 'nmFlushMB'
	}, {
		name : 'nmFlushGB'
	}, {
		name : 'nmOFlushKB'
	}, {
		name : 'nmOFlushMB'
	}, {
		name : 'nmOFlushGB'
	} ]
};

var colsConfig = [ {
	id : 'statdate',
	header : "时间"

}, {
	id : 'intSuccess',

	header : "PDP成功次数",
	headAlign : 'right',
	align : 'right'

}, {
	id : 'intTimes',

	header : "PDP成功总次数",
	headAlign : 'right',
	align : 'right'

}, {
	id : 'intAppSuccessful',

	header : "会话成功连接次数",
	hidden : true,
	headAlign : 'right',
	align : 'right'

}, {
	id : 'intAppTimes',

	header : "会话成功连接总次数",
	hidden : true,
	headAlign : 'right',
	align : 'right'

}, {
	id : 'intAppAckTime',

	header : "响应时间总和",
	hidden : true,
	headAlign : 'right',
	align : 'right'

}, {
	id : 'intAppAckTimes',

	header : "响应时间总条数",
	hidden : true,
	headAlign : 'right',
	align : 'right'

}

, {
	id : 'nmActiveUsers',

	header : "业务活跃用户数",
	hidden : true,
	headAlign : 'right',
	align : 'right'

}, {
	id : 'nmAllUsers',

	header : "业务总用户数",
	hidden : true,
	headAlign : 'right',
	align : 'right'

}, {
	id : 'intImsis',

	header : "上周期用户数",
	hidden : true,
	headAlign : 'right',
	align : 'right'

}, {
	id : 'nmUsers',

	header : "本周期用户数",
	hidden : true,
	headAlign : 'right',
	align : 'right'

}, {
	id : 'nmFlushKB',

	header : "本周业务流量(KB)",
	hidden : true,
	headAlign : 'right',
	align : 'right'

}, {
	id : 'nmFlushMB',

	header : "本周业务流量(MB)",
	hidden : true,
	headAlign : 'right',
	align : 'right'

}, {
	id : 'nmFlushGB',

	header : "本周业务流量(GB)",
	hidden : true,
	headAlign : 'right',
	align : 'right'

}, {
	id : 'nmOFlushKB',

	header : "本周全网总流量(KB)",
	hidden : true,
	headAlign : 'right',
	align : 'right'

}, {
	id : 'nmOFlushMB',

	header : "本周全网总流量(MB)",
	hidden : true,
	headAlign : 'right',
	align : 'right'

}, {
	id : 'nmOFlushGB',

	header : "本周全网总流量(GB)",
	hidden : true,
	headAlign : 'right',
	align : 'right'
}

];

var gridConfig = {
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL : '/bishealth_queryDetail.do?taskId=' + taskId,
	exportURL : '/bishealth_export.do?1=1',
	//saveURL : '/flexiGridTest_list1.do?1=1' ,
	dataset : dsConfig,
	columns : colsConfig,
	width : 780,
	height : 400,
	resizable : false,
	beforeLoad : checkLogon,
	container : 'data_container',
	beforeLoad : checkLogon,
	toolbarContent : 'nav | goto | pagesize | state',
	pageSize : getLongPageSize(),
	remoteSort : true,
	remotePaging : true,
	pageSizeList : [ 5, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,
			24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
			45, 50, 100 ],
	onComplete : loadComplate
};
function getLongPageSize() {
	var size = getDispalyPageSize(1, 1);
	if (size < 25) {
		size = 25;
	}
	return size;
}
var datagrid = new GT.Grid(gridConfig);
GT.Utils.onLoad(function() {
		$('#datagridtr').hide();
		$('#othertd').hide();
		$('#networkResultTable').show();
	datagrid.render();
});
//页面呈现的单位

var firstInit = true;
//gird回调函数
var hiddenColum=13;
function loadComplate() {
if(taskType==6)
	return;
	$('#datagridtr').show();
	$('#othertd').show();
	$('#networkResultTable').hide();

	var obj = new Object();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.isCheckbox = false;//是否可选择
	obj.otherHeight =  0; //必须定义,因为有些页面不同，所以此处指定预留高度
	obj.hideColumn = hiddenColum;
	//判断是否查询到数据
	judgeData(datagrid);
	//选择呈现单位
	if (!initHasData) {
		sortnmFlush = true;
	}
	if (sortnmFlush) {
		hiddenColum = buildColumnUnit();
	obj.hideColumn=	hiddenColum;
		sortnmFlush = false;
	}
	//初始化grid

	initGridInfo1(obj);

	/*	*/
	if (firstInit == true) {
		var pageSize = 25;
		var index = 0;
		$(".gt-pagesize-select option").each(function(i) {
			if (this.text == pageSize) {
				index = i;
			}
		})
		firstInit = false;
		$(".gt-pagesize-select").get(0).selectedIndex = index;
	}

}





function buildColumnUnit() {

			taskType =parseInt(taskType);
	var hideCount = 14;
	switch (taskType) {
	case 1:
		datagrid.getColumn("intSuccess").show();
		datagrid.getColumn("intTimes").show();
		datagrid.getColumn("intAppSuccessful").hide();
		datagrid.getColumn("intAppTimes").hide();
		datagrid.getColumn("intAppAckTime").hide();
		datagrid.getColumn("intAppAckTimes").hide();

		datagrid.getColumn("nmActiveUsers").hide();
		datagrid.getColumn("nmAllUsers").hide();
		
			datagrid.getColumn("intImsis").hide();
		datagrid.getColumn("nmUsers").hide();
		datagrid.getColumn("nmFlushKB").hide();
		datagrid.getColumn("nmFlushMB").hide();
		datagrid.getColumn("nmFlushGB").hide();
		datagrid.getColumn("nmOFlushKB").hide();
		datagrid.getColumn("nmOFlushMB").hide();
		datagrid.getColumn("nmOFlushGB").hide();

		break;
	case 2:
		datagrid.getColumn("intSuccess").hide();
		datagrid.getColumn("intTimes").hide();
		datagrid.getColumn("intAppSuccessful").show();
		datagrid.getColumn("intAppTimes").show();
		datagrid.getColumn("intAppAckTime").hide();
		datagrid.getColumn("intAppAckTimes").hide();

		datagrid.getColumn("nmActiveUsers").hide();
		datagrid.getColumn("nmAllUsers").hide();
			datagrid.getColumn("intImsis").hide();
		datagrid.getColumn("nmUsers").hide();
		datagrid.getColumn("nmFlushKB").hide();
		datagrid.getColumn("nmFlushMB").hide();
		datagrid.getColumn("nmFlushGB").hide();
		datagrid.getColumn("nmOFlushKB").hide();
		datagrid.getColumn("nmOFlushMB").hide();
		datagrid.getColumn("nmOFlushGB").hide();

		break;
	case 3:
		datagrid.getColumn("intSuccess").hide();
		datagrid.getColumn("intTimes").hide();
		datagrid.getColumn("intAppSuccessful").hide();
		datagrid.getColumn("intAppTimes").hide();
		datagrid.getColumn("intAppAckTime").show();
		datagrid.getColumn("intAppAckTimes").show();

		datagrid.getColumn("nmActiveUsers").hide();
		datagrid.getColumn("nmAllUsers").hide();
			datagrid.getColumn("intImsis").hide();
		datagrid.getColumn("nmUsers").hide();
		datagrid.getColumn("nmFlushKB").hide();
		datagrid.getColumn("nmFlushMB").hide();
		datagrid.getColumn("nmFlushGB").hide();
		datagrid.getColumn("nmOFlushKB").hide();
		datagrid.getColumn("nmOFlushMB").hide();
		datagrid.getColumn("nmOFlushGB").hide();

		break;
	case 4:
		datagrid.getColumn("intSuccess").hide();
		datagrid.getColumn("intTimes").hide();
		datagrid.getColumn("intAppSuccessful").hide();
		datagrid.getColumn("intAppTimes").hide();
		datagrid.getColumn("intAppAckTime").hide();
		datagrid.getColumn("intAppAckTimes").hide();

		datagrid.getColumn("nmActiveUsers").show();
		datagrid.getColumn("nmAllUsers").show();
			datagrid.getColumn("intImsis").hide();
		datagrid.getColumn("nmUsers").hide();
		datagrid.getColumn("nmFlushKB").hide();
		datagrid.getColumn("nmFlushMB").hide();
		datagrid.getColumn("nmFlushGB").hide();
		datagrid.getColumn("nmOFlushKB").hide();
		datagrid.getColumn("nmOFlushMB").hide();
		datagrid.getColumn("nmOFlushGB").hide();

		break;
	case 5:
		datagrid.getColumn("intSuccess").hide();
		datagrid.getColumn("intTimes").hide();
		datagrid.getColumn("intAppSuccessful").hide();
		datagrid.getColumn("intAppTimes").hide();
		datagrid.getColumn("intAppAckTime").hide();
		datagrid.getColumn("intAppAckTimes").hide();

		datagrid.getColumn("nmActiveUsers").hide();
		datagrid.getColumn("nmAllUsers").hide();
			datagrid.getColumn("intImsis").show();
		datagrid.getColumn("nmUsers").show();
		buildnmFlushUnit();
		buildnmOFlushUnit();
		hideCount = 12;
		break;

	}

	return hideCount;
}

function buildnmFlushUnit() {

	var totalRecords = datagrid.getAllRows().length;
	var max = 0;

	if (typeof (datagrid.getRecord(0)) != "undefined") {
		for ( var i = 0; i < totalRecords; i++) {
			var record = datagrid.getRecord(i);
			var fl = record.nmFlushKB;
			if (fl > max) {
				max = fl;
			}
		}
		}else
			max=1;

		var level = getViewLevel(max);
		if (level == 1) {//KB

			datagrid.getColumn("nmFlushKB").show();
			datagrid.getColumn("nmFlushGB").hide();
			datagrid.getColumn("nmFlushMB").hide();
		} else {
			if (level == 3) {//GB
				datagrid.getColumn("nmFlushKB").hide();
				datagrid.getColumn("nmFlushGB").show();
				datagrid.getColumn("nmFlushMB").hide();
			} else {//MB

				datagrid.getColumn("nmFlushKB").hide();
				datagrid.getColumn("nmFlushGB").hide();

				datagrid.getColumn("nmFlushMB").show();
			}
		}
	
}

function buildnmOFlushUnit() {

	var totalRecords = datagrid.getAllRows().length;
	var max = 0;

	if (typeof (datagrid.getRecord(0)) != "undefined") {
		for ( var i = 0; i < totalRecords; i++) {
			var record = datagrid.getRecord(i);
			var fl = record.nmOFlushKB;
			if (fl > max) {
				max = fl;
			}
		}
		}else
			max=1;

		var level = getViewLevel(max);

		if (level == 1) {//KB

			datagrid.getColumn("nmOFlushKB").show();
			datagrid.getColumn("nmOFlushGB").hide();
			datagrid.getColumn("nmOFlushMB").hide();
		} else {
			if (level == 3) {//GB
				datagrid.getColumn("nmOFlushKB").hide();
				datagrid.getColumn("nmOFlushGB").show();
				datagrid.getColumn("nmOFlushMB").hide();
			} else {//MB

				datagrid.getColumn("nmOFlushKB").hide();
				datagrid.getColumn("nmOFlushGB").hide();

				datagrid.getColumn("nmOFlushMB").show();
			}
		}
	
}
/*
 * 根据任务类型改变
 * */

function changTaskType() {
	var taskType = $("#taskType option:selected").val();
if (taskType != 6) {

		$('#mainbtn').show();
	} else {

		$('#mainbtn').hide();
		$('#datagridtr').hide();
		$('#othertd').hide();
		$('#networkResultTable').show();
	}

 	datagrid.sortInfo=null;
}


//grid查询
function query() {
	//重置分页数据
	var pageinfo = datagrid.getPageInfo();
	pageinfo.pageNum = 1;
	datagrid.setPageInfo(pageinfo);
	sortnmFlush = true;

	taskType = $("#taskType option:selected").val();

	hasinit = false;
	datachange = true;
	//传递给后台参数
	var param = {
		taskId : taskId,
		taskType : taskType

	};
	GT.$grid('datagrid').query(param);
}



//初始化grid工具栏
$(document).ready(function() {
	var toolbar = new Toolbar( {
		renderTo : 'toolbar',
		//border: 'top',
		items : [ {
			type : 'button',
			text : '导出',
			bodyStyle : 'xls',
			useable : 'T',
			handler : function() {
				//参数
			var params = {
				taskId : taskId,
				taskType : taskType
			//结束时间
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName = '任务健康查询结果';//('+startTime_search+'至'+endTime_search+')';//文件名称
			fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
			fileObj.gridObj = datagrid;//当前grid对象
			fileObj.params = params;
			exportFile(fileObj);
		}
		} ]
	});
	toolbar.render();
		var toolbar2 = new Toolbar( {
		renderTo : 'toolbar2',
		//border: 'top',
		items : [ {
			type : 'button',
			text : '导出',
			bodyStyle : 'xls',
			useable : 'T',
			handler : downloadTemplate}	]
	});
	toolbar2.render();

});

	function downloadTemplate() {
		var obj = document.getElementById('download');
		obj.contentWindow.location.href = '/bishealth_exportNetworkResult.do?taskId='+taskId;
	}

//初始化grid高宽等信息,使高宽满屏
function initGridInfo1(obj) {
	var datagrid = obj.datagrid;
	var hideColumn = obj.hideColumn;//隐藏列
	var isCheckbox = obj.isCheckbox;//是否可选择
	var otherHeight = obj.otherHeight;//预留高度
	var otherWidth = obj.otherWidth;//预留高度
	var realWidth = 1045;
	//初始化高度满屏
	var parentHeight = parent.document.getElementById("container_content").offsetHeight;//整个编辑区的高度
	var yy = window.screen.availHeight;
	var yyy = window.screenTop;

	var parentHeight = yy - yyy-30;
	var $toolbarObj = $("#toolbar");//toolbar对象
	var tHeight = $toolbarObj.offset().top + 25;//toolbar相对位置
	var IEHeight = 23;
	if (otherHeight != undefined) {
		IEHeight = IEHeight - otherHeight;
	}
	var totalRecords = datagrid.getAllRows().length;
	var dWidth = (totalRecords) * 22 + 55;
	if (checkIE() == "IE8") {
		dWidth = (totalRecords) * 22 + 70;
	}
	if (width == 1024) {
		var width_1024 = parentHeight - tHeight - IEHeight - 3;
		datagrid.setSize(800, dWidth > width_1024 ? dWidth : width_1024);//设置列表的宽高
		$toolbarObj.css("width", "800px");//设置列表toolbar的宽高
		realWidth = 800;
	} else {
		var width_1280 = parentHeight - tHeight - IEHeight;//满屏高度
		datagrid.setSize(1056, dWidth > width_1280 ? dWidth : width_1280);
		$toolbarObj.css("width", "1056px");
		realWidth = 1056;
	}
	//初始化宽度满屏
	var cs = datagrid.columns.length - hideColumn;//如有隐藏列,则减去隐藏列
	if (typeof (otherWidth) != "undefined") {
		realWidth = realWidth - otherWidth;
		cs = cs - 1;
	}
	if (isCheckbox) {
		realWidth = realWidth - 40;//为checkbox预留空间
		cs = cs - 1;
	}
	if (checkIE() == "IE8") {
		realWidth = realWidth - 35;
	} else {
		realWidth = realWidth - 10;
	}
	var w = realWidth / cs;
	var isCheck = false;
	for ( var j in datagrid.columnMap) {
		if (isCheckbox && !isCheck) {
			//datagrid.getColumn(j).setWidth(10);//checkbox宽度为默认值, 并且必须定义在第一列
			isCheck = true;
		} else {
			if ((j == "detail" && typeof (otherWidth) != "undefined")
					|| (j == "top" && typeof (otherWidth) != "undefined")) {
			} else {
				datagrid.getColumn(j).setWidth(w);
			}
		}
	}

}

/**
 *
 */
$(document).ready(function() {
	window.onerror = killErrors;
})
function killErrors() {
	return true;
}
</script>


