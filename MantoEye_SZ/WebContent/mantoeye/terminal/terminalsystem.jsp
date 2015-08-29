<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>智能终端系统分析</title>
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
			<!-- OFC图表 -->
		<script type="text/javascript" src="/flash/js/swfobject.js"></script>
		<script type="text/javascript" src="/flash/js/json/json2.js"></script>
		<script type="text/javascript" src="/flash/js/BarLineChartUtil.js"></script>
		<script type="text/javascript" src="/flash/js/ParseUtils.js"></script>
	</head>
	<body>
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
													<td class="condition_name" style="display: block">
														时间粒度：
													</td>
													<td class="condition_value">
														<select name="timeLevel" id="timeLevel"
															onchange="changeTimeLevel()" style="height: 21px">
															<option value="1" >
																小时
															</option>
															<option value="2" selected>
																天
															</option>
															<option value="3">
																周（周日~周六）
															</option>
															<option value="4">
																月
															</option>
														</select>
													</td>
													<td class="condition_name">
														时间跨度：
													</td>
													<td class="condition_value" id="time_space_start">
														<input type="text" class="Wdate"
															style="display: block; height: 17px;"
															onclick="selectStartTime()" id="startTime"
															name="startTime" />
													</td>
													<!-- 	<td style="width: 5px;">
														&nbsp;到&nbsp;
													</td>
													<td class="condition_value" id="time_space_end"> -->
													<input type="text" class="Wdate"
														style="display: none; height: 17px;"
														onclick="selectEndTime()" id="endTime_search"
														name="endTime_search" />
													<!-- 		</td> -->
													<td class="condition_name">
														区域维度：
													</td>
													<td class="condition_value">
														<select name="areaType" id="areaType" style="height: 21px">
															<option value="1" selected>
																BSC
															</option>
															<option value="2">
																SGSN
															</option>
															<option value="3">
																分公司
															</option>
															<option value="4">
																营销片区
															</option>
															<option value="5">
																街道办
															</option>
															<option value="6" >
																全网
															</option>
														</select>
													</td>


													<td width="300px">
														<div id="mainbtn" style="width: 300px">
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
												智能终端系统图形
											</div>
											<input type="hidden" value="" id="chartxmldata" />
										</td>
										<td width="4px">
											<div class="righttitle"></div>
										</td>
									</tr>
								</table>
							</td>
						</tr>

						<tr>
					<td height="230" width="100%"
								style="padding: 15px 20px; background: #ffffff">

								<div id="imgareas"
									style="width: 100%; height: 100%; border: 2px solid #008BD1;">
									<div id="imgarea" style="width: 100%; height: 100%"></div>
								</div>
							</td>
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
												智能终端系统列表
											</div>
										</td>
										<td width="4px">
											<div class="righttitle"></div>
										</td>
									</tr>
								</table>
							</td>
						</tr>

						<tr>
							<td>
								<div id="toolbar" style="height: 25px"></div>
								<div class="gt-panel"
									style="width: 100%; margin: 0px; margin-bottom: 2px;">
									<div id="data_container"></div>
								</div>
							</td>
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

//初始化查询时间
var date = new Date();
var sortnmFlush = false;
var hasinit = false;//第一次初始化图形数据标识
var datachange = false;//第N次查询初始化数据标识
var timeLevel = '1';//查询时间粒度
var areaType = 6;

date = date.valueOf();
date = date - 24 * 60 * 60 * 1000;
date = new Date(date);
var yesterday = date.getYear()
		+ '-'
		+ ''
		+ ((date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : ("0" + (date
				.getMonth() + 1))) + '-' + ''
		+ (date.getDate() > 9 ? date.getDate() : ("0" + date.getDate())); //查询开始时间
var endTime_search = yesterday ;//查询结束时间

var startTime = yesterday;//查询开始时间

var dsConfig = {
	uniqueField : 'null',
	fields : [ {
		name : 'fullDate'
	}, {
		name : 'terminalBrand'
	}, {
		name : 'area'
	}, {
		name : 'intImsis'
	}, {
		name : 'upFlushKB'
	}, {
		name : 'downFlushKB'
	}, {
		name : 'totalFlushKB'
	}, {
		name : 'upFlushMB'
	}, {
		name : 'downFlushMB'
	}, {
		name : 'totalFlushMB'
	}, {
		name : 'upFlushGB'
	}, {
		name : 'downFlushGB'
	}, {
		name : 'totalFlushGB'
	}, {
		name : 'nmTerminalId'
	}, {
		name : 'intYear'
	}, {
		name : 'nmAreaId'
	}]
};

function renderInit(value, record, columnObj, grid, colNo, rowNo) {

	var nmTerminalId = record.nmTerminalId.toString();
	var nmAreaId = record.nmAreaId.toString();


	var thtml = "<table><tr>"
	// thtml = thtml +  '<td><img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/map.png" alt="查看业务" onclick="openDistribute(1,\''+name+'\');" /></td>';
	thtml = thtml
			+ '<td><img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/business.gif" alt="查看业务分布" onclick="openDistribute(1,\''+ nmAreaId + '\',\'' + nmTerminalId + '\');" /></td>';
	thtml = thtml + '</tr></table>';

	return thtml;
	//return '<img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/business.gif" alt="查看业务分布" onclick="openDistribute(\''+businessName+'\');" />'
}

var w = "1000px";
var h = "600px";
if (width == 1024) {
	w = "815px";
	h = "600px";
}

function openDistribute(flag, nmAreaId, nmTerminalId) {
	var obj = new Object();
		obj.nmAreaId = nmAreaId;
		obj.nmTerminalId = nmTerminalId;
		obj.areaType=areaType;
		obj.timeLevel=timeLevel;
		obj.startTime=startTime;
		window.showModalDialog("/mantoeye/terminal/terminalsystem_business.jsp",obj,"dialogWidth="+ w+ ";dialogHeight="+ h+ ";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");

	
}

var colsConfig = [ {
	id : 'fullDate',
	width : 250,
	header : "时间"

}, {
	id : 'terminalBrand',
	header : "操作系统名称",
	width : 250

}, {
	id : 'area',
	width : 250,
	header : "区域"

}, {
	id : 'intImsis',
	header : "总用户数",
	width : 250,
	headAlign : 'right',
	align : 'right'

}, {
	id : 'upFlushKB',
	width : 250,
	headAlign : 'right',
	align : 'right',
	renderer : renderFormatDataInit2,
	header : "上行流量(KB)",
	hidden : true

}, {
	id : 'downFlushKB',
	hidden : true,
	header : "下行流量 (KB)",
	width : 250,
	headAlign : 'right',
	align : 'right',
	renderer : renderFormatDataInit2

}, {
	id : 'totalFlushKB',
	width : 250,
	headAlign : 'right',
	align : 'right',
	renderer : renderFormatDataInit2,
	header : "总流量(KB)",
	hidden : true

}, {
	id : 'upFlushMB',
	width : 250,
	headAlign : 'right',
	align : 'right',
	renderer : renderFormatDataInit2,
	header : "上行流量(MB)"

}, {
	id : 'downFlushMB',
	header : "下行流量 (MB)",
	width : 250,
	headAlign : 'right',
	align : 'right',
	renderer : renderFormatDataInit2

}, {
	id : 'totalFlushMB',
	width : 250,
	headAlign : 'right',
	align : 'right',
	renderer : renderFormatDataInit2,
	header : "总流量(MB)"

}, {
	id : 'upFlushGB',
	width : 250,
	headAlign : 'right',
	align : 'right',
	renderer : renderFormatDataInit2,
	header : "上行流量(GB)",
	hidden : true

}, {
	id : 'downFlushGB',
	hidden : true,
	header : "下行流量 (GB)",
	width : 250,
	headAlign : 'right',
	align : 'right',
	renderer : renderFormatDataInit2

}, {
	id : 'totalFlushGB',
	width : 250,
	headAlign : 'right',
	align : 'right',
	renderer : renderFormatDataInit2,
	header : "总流量(GB)",
	hidden : true

}, {
	id : 'intYear',
	header : "前十业务",

	exportnumber : true,
	renderer : renderInit
} ];

var gridConfig = {
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL : '/terminalsystem_query.do?1=1',
	exportURL : '/terminalsystem_export.do?1=1',
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
	datagrid.render();
});
//页面呈现的单位

var firstInit = true;
var showunit = 'MB';
//gird回调函数
function loadComplate() {
	var obj = new Object();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.hideColumn = 6;//隐藏的列数目
	obj.isCheckbox = false;//是否可选择
	obj.otherHeight = 0; //必须定义,因为有些页面不同，所以此处指定预留高度

	//判断是否查询到数据
	judgeData(datagrid);
	if (initHasData) {
		sortnmFlush = true;
	}
	if (sortnmFlush) {

		showunit = buildUnit();
		sortnmFlush = false;
	}

	//初始化grid
	initGridInfo(obj);

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
	buildChartData(datagrid.getAllRows().length);
}

//grid查询
function query() {
	//重置分页数据
	var pageinfo = datagrid.getPageInfo();
	pageinfo.pageNum = 1;
	datagrid.setPageInfo(pageinfo);
	sortnmFlush = true;

	//查询条件
	timeLevel = $("#timeLevel option:selected").val();
	startTime = $('#startTime').attr("value");
	areaType = $("#areaType option:selected").val();
	if (startTime == '') {
		alert('请选择时间跨度!');
		return;
	}

	hasinit = false;
	datachange = true;
	//传递给后台参数
	var param = {
		hasinit : hasinit,
		timeLevel : timeLevel,//时间粒度  1:小时 2:天 3:周 4:月
		startTime : startTime,//开始时间
		areaType : areaType
	};
	GT.$grid('datagrid').query(param);
}

//设置页面显示的数据类型
function showDataType(type) {
	dataType_search = type;
}

//时间改变事件
function changeTimeLevel() {
	$('#startTime').attr("value", "");
	$('#endTime_search').attr("value", "");
}

//初始化查询时间
$(document).ready(function() {
	$('#startTime').attr('value', startTime);
});
//时间选择事件
function selectStartTime() {
	var ts = $("#timeLevel option:selected").val();
	switch (ts) {
	case '1':
		var a = new WdatePicker( {
			maxDate : '#F{$dp.$D(\'endTime_search\')}',
			dateFmt : 'yyyy-MM-dd HH:00'
		});
		break;
	case '2':
		new WdatePicker( {
			maxDate : '#F{$dp.$D(\'endTime_search\')}',
			dateFmt : 'yyyy-MM-dd'
		});
		break;
	case '3':
		new WdatePicker( {
			maxDate : '#F{$dp.$D(\'endTime_search\')}',
			dateFmt : 'yyyy-MM-dd'
		});
		break;
	case '4':
		new WdatePicker( {
			maxDate : '#F{$dp.$D(\'endTime_search\')}',
			dateFmt : 'yyyy-MM'
		});
		break;
	}
}
function selectEndTime() {
	var ts1 = $("#timeLevel option:selected").val();
	switch (ts1) {
	case '1':
		new WdatePicker( {
			minDate : '#F{$dp.$D(\'startTime\')}',
			dateFmt : 'yyyy-MM-dd HH:00'
		});
		break;
	case '2':
		new WdatePicker( {
			minDate : '#F{$dp.$D(\'startTime\')}',
			dateFmt : 'yyyy-MM-dd'
		});
		break;
	case '3':
		new WdatePicker( {
			minDate : '#F{$dp.$D(\'startTime\')}',
			dateFmt : 'yyyy-MM-dd'
		});
		break;
	case '4':
		new WdatePicker( {
			minDate : '#F{$dp.$D(\'startTime\')}',
			dateFmt : 'yyyy-MM'
		});
		break;
	}
}
//重置查询条件
function reset() {
	$('#dataType_search_td_id input:first').attr("checked", "true");//设置radio默认值
	$("#timeLevel").get(0).selectedIndex = 1;
	$('#startTime').attr("value", "");
	$('#endTime_search').attr("value", "");
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
				hasinit : hasinit,
				timeLevel : timeLevel,
				startTime : startTime,
				areaType : areaType

			};
			//导出
			var fileObj = new Object();
			fileObj.fileName = '智能终端系统数据';
			fileObj.fileFormat = 'xls';
			fileObj.gridObj = datagrid;
			fileObj.params = params;
			exportFile(fileObj);
		}
		} ]
	});
	toolbar.render();
});

///////////////////图表////////////////////////

/*图表是否已经初始化*/
var chartinit = false;
/*组装图形数据*/
function buildChartData(totalRecords) {

	var title = startTime+" 流量|用户数区域分析图";
	
	var xlabels = [] ;
	var datas1 = [] ;
	var datas2 = [] ;
	
	if(typeof(datagrid.getRecord(0))!="undefined"){
		for(var i = 0 ; i<totalRecords;i++){	
			var record = datagrid.getRecord(i);	 	
			if(i<20){//图形只显示前20 的数据
				xlabels[i] =record.area;	
				if( 'MB' == showunit){
					datas1[i] = StringToFloat(record.totalFlushMB);
		 		}else if( 'KB' == showunit){
					datas1[i] = StringToFloat(record.totalFlushKB);
		 		}else if( 'GB' == showunit){
					datas1[i] = StringToFloat(record.totalFlushGB);
		 		}	
				datas2[i] = StringToFloat(record.intImsis);	
			} 		
		}
	}else{
		var str1 = JSON.stringify(buildEmptyAxisChart());
		if(!chartinit){
			chartinit = true;
			document.getElementById("chartxmldata").value=str1;
			swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=flush", "imgarea", "100%", "100%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
		}else{
	  		var tmp1 = findSWF("imgarea");	  	
  			tmp1.load(str1);	
		}
		return;
	}
	var showxlabels = xlabels;

	
	 var tdatas = [];
	 
	  //格式化Y轴数据（流量）
	  
	var obj = new Object();
		obj.datas = datas1;
		obj.unit = showunit;
		
	var lobj =  obj
	var lunit="流量";
	if(lobj.unit!=""){
		lunit = lunit +"@@"+lobj.unit;
	}
	tdatas[0] = lobj.datas;
	 //格式化Y轴数据（用户数）
	var robj = formatNumberByWan(datas2);
	var runit="用户数";
	if(robj.unit!=""){
		runit = runit +"@@"+robj.unit;
	}
	//alert(robj.datas);
	tdatas[1] = robj.datas;
	
	 //如果有单位  则label与单位间用@@分开
	var labels = [lunit,runit];	

	tdatas[0] = buildBarChartTip(lobj.datas,showxlabels,lunit);
	tdatas[1] = buildLineChartTip(robj.datas,showxlabels,runit); 
	var jsons1 = buildMutileChartData(title,labels,xlabels,tdatas);

	var str1 = JSON.stringify(jsons1);
	//alert("-str1-"+str1);
	if(!chartinit){
		chartinit = true;
		document.getElementById("chartxmldata").value=str1;
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=flush", "imgarea", "100%", "100%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
	}else{
	  	var tmp1 = findSWF("imgarea");	  	
  		tmp1.load(str1);	
	}
	

}
/*初始化图表*/
function open_flash_chart_data(id)
{
	return document.getElementById("chartxmldata").value;
}
/*图表回调函数*/
function callback(label){
	//alert("label:"+label);
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


