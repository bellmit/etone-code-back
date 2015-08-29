<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>无线业务</title>
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
		<script type="text/javascript" src="/flash/js/swfobject.js">
</script>
		<script type="text/javascript" src="/flash/js/json/json2.js">
</script>
		<script type="text/javascript" src="/flash/js/BarLineChartUtil.js">
</script>
		<script type="text/javascript" src="/flash/js/ParseUtils.js">
</script>


		<script src="/js/nav.js">
</script>

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

													<td class="condition_name">
														时间粒度：
													</td>
													<td class="condition_value">
														<select name="timeLevel_search" id="timeLevel_search"
															onchange="changeTimeLevel()" style="height: 21px">
															<option value="1" selected>
																小时
															</option>
															<option value="2">
																天
															</option>
															<option value="3">
																周 （周日~周六）
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
															onclick="selectStartTime()" id="startTime_search"
															name="startTime_search" />
													</td>
													<!-- 	<td 

style="width:10px;dispaly:none">
														&nbsp;到&nbsp;
													</td>
													<td style="dispaly:none" 

id="time_space_end"> -->
													<input type="text" class="Wdate"
														style="display: none; height: 17px;"
														onclick="selectEndTime()" id="endTime_search"
														name="endTime_search" />
													<!-- 	</td> -->
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
												<tr>
													<!-- 号码 -->
													<td class="condition_name">
														业务类型：
													</td>
													<td class="condition_value" id="number">
														<select name="intType" id="intType" style="height: 21px;">


															<option value="-1" selected>
																全部
															</option>
															<option value="11">
																139说客
															</option>
															<option value="12">
																红段子
															</option>
															<option value="13">
																无线城市
															</option>
															<option value="14">
																无线官网
															</option>
															<option value="15">
																飞信
															</option>
															<option value="16">
																移动MM
															</option>
															<option value="17">
																手机阅读
															</option>
                                                                                                                        <option value="18">
																新浪微博
															</option>

														</select>
													</td>
													<td class="condition_name">
														小区：
													</td>
													<td class="condition_value" id="time_space_start">
														<input type="text" id="vcCGI" value="" />

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
												无线业务列表
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

var hasinit = false;//第一次初始化图形数据标识
var datachange = false;//第N次查询初始化数据标识

var timeLevel_search = '1';//查询时间粒度
var intType = "";//业务类型
var vcCGI = "";
var sortnmFlush = false;
date = date.valueOf();
date = date - 60 * 60 * 1000;
date = new Date(date);
var yesterday = date.getYear()
		+ '-'
		+ ''
		+ ((date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : ("0" + (date
				.getMonth() + 1)))
		+ '-'
		+ ''
		+ ((date.getDate() ) > 9 ? (date.getDate()) : ("0" + (
				date.getDate())))
var endTime_search = yesterday + " 23:00";//查询结束时间
var startTime_search = yesterday + " "
		+ date.getHours()+ ":00"; //查询开始时间
//var startTime_search =" 23:00";
//var endTime_search = " 23:00";

var dsConfig = {
	//data : data1 ,
	uniqueField : 'null',
	fields : [ {
		name : 'statdate'
	}, {
		name : 'intType'
	}, {
		name : 'vcCGI'
	}, {
		name : 'nmUsers'
	}, {
		name : 'nmFlushGB'
	}, {
		name : 'nmFlushMB'
	}, {
		name : 'nmFlushKB'
	} ]
};

var colsConfig = [ {
	id : 'statdate',
	header : "时间"

}, {
	id : 'intType',

	header : "业务类型"

}, {
	id : 'vcCGI',

	header : "小区"

}, {
	id : 'nmUsers',

	header : "用户数",
	align : 'right',
	headAlign : 'right'

}, {
	id : 'nmFlushGB',

	header : "流量(GB)",
	align : 'right',
	headAlign : 'right',
	exportnumber : true,
	renderer : renderFormatDataInit2,
	hidden : true

}, {
	id : 'nmFlushMB',

	header : "流量(MB)",
	align : 'right',
	headAlign : 'right',
	exportnumber : true,
	renderer : renderFormatDataInit2

}, {
	id : 'nmFlushKB',

	header : "流量(KB)",
	align : 'right',
	headAlign : 'right',
	exportnumber : true,
	renderer : renderFormatDataInit2,
	hidden : true

} ];

var gridConfig = {
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL : '/wirelessbusiness_query.do?1=1',
	exportURL : '/wirelessbusiness_export.do?1=1',
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
	datagrid.render();
});
//页面呈现的单位
var showunit = 'KB';
var firstInit = true;
//gird回调函数
function loadComplate() {
	var obj = new Object();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.hideColumn = 2;//隐藏的列数目
	obj.isCheckbox = false;//是否可选择
	obj.otherHeight = 0; //必须定义,因为有些页面不同，所以此处指定预留高度
	//obj.otherWidth = 160;

	//判断是否查询到数据
	judgeData(datagrid);
	//选择呈现单位
	if (!initHasData) {
		sortnmFlush = true;
	}
	if (sortnmFlush) {
		showunit = buildnmFlushUnit();
		sortnmFlush = false;
	}

	//初始化grid
	initGridInfo(obj);

	/*	*/
	if (firstInit == true) {
		//var pageSize=getDispalyPageSize(1,1);
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
	vcCGI = GT.U.getValue(GT.$('vcCGI'));
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

		var level = getViewLevel(max);

		if (level == 1) {//KB

			datagrid.getColumn("nmFlushKB").show();
			datagrid.getColumn("nmFlushGB").hide();
			datagrid.getColumn("nmFlushMB").hide();

			showUnit = "KB";
		} else {
			if (level == 3) {//GB
				datagrid.getColumn("nmFlushKB").hide();
				datagrid.getColumn("nmFlushGB").show();
				datagrid.getColumn("nmFlushMB").hide();

				showUnit = "GB";
			} else {//MB

				datagrid.getColumn("nmFlushKB").hide();
				datagrid.getColumn("nmFlushGB").hide();

				datagrid.getColumn("nmFlushMB").show();
				showUnit = "MB";
			}
		}
	}
	return showUnit;
}

//grid查询
function query() {
	//重置分页数据
	var pageinfo = datagrid.getPageInfo();
	pageinfo.pageNum = 1;
	datagrid.setPageInfo(pageinfo);
	sortnmFlush = true;

	//查询条件
	timeLevel_search = $("#timeLevel_search option:selected").val();
	startTime_search = $('#startTime_search').attr("value");
	endTime_search = $('#endTime_search').attr("value"); //设置图形标志
	intType = $('#intType').attr("value");
	vcCGI = $('#vcCGI').attr("value");
	if (startTime_search == '') {
		alert('请选择时间!');
		return;

	}
	hasinit = false;
	datachange = true;
	//传递给后台参数
	var param = {
		hasinit : hasinit,

		timeLevel_search : timeLevel_search,//时间粒度  1:小时 2:天 3:周 4:月
		startTime_search : startTime_search,//开始时间
		endTime_search : endTime_search,
		intType : intType,
		vcCGI : vcCGI
	//结束时间
	};
	GT.$grid('datagrid').query(param);
}

//设置页面显示的数据类型
function showDataType(type) {
	dataType_search = type;
}

//时间改变事件
function changeTimeLevel() {
	$('#startTime_search').attr("value", "");
	$('#endTime_search').attr("value", "");
}

//初始化查询时间
$(document).ready(function() {
	$('#startTime_search').attr('value', startTime_search);
	$('#endTime_search').attr('value', endTime_search);
});
//时间选择事件
function selectStartTime() {
	var ts = $("#timeLevel_search option:selected").val();
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
	var ts1 = $("#timeLevel_search option:selected").val();
	switch (ts1) {
	case '1':
		new WdatePicker( {
			minDate : '#F{$dp.$D(\'startTime_search\')}',
			dateFmt : 'yyyy-MM-dd HH:00'
		});
		break;
	case '2':
		new WdatePicker( {
			minDate : '#F{$dp.$D(\'startTime_search\')}',
			dateFmt : 'yyyy-MM-dd'
		});
		break;
	case '3':
		//new WdatePicker({isShowWeek:true,onpicked:function(){$dp.$D('time_search').value=$dp.cal.getP('W','第'+'W'+'周');}})
		new WdatePicker( {
			minDate : '#F{$dp.$D(\'startTime_search\')}',
			dateFmt : 'yyyy-MM-dd'
		});
		break;
	case '4':
		new WdatePicker( {
			minDate : '#F{$dp.$D(\'startTime_search\')}',
			dateFmt : 'yyyy-MM'
		});
		break;
	}
}
//重置查询条件
function reset() {
	$('#dataType_search_td_id input:first').attr("checked", "true");//设置radio默认值
	$("#timeLevel_search").get(0).selectedIndex = 1;
	$('#startTime_search').attr("value", "");
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

				timeLevel_search : timeLevel_search,//时间粒度  1:小时 2:天 3:周 4:月
				startTime_search : startTime_search,//开始时间
				endTime_search : endTime_search,
				intType : intType,
				vcCGI : vcCGI
			//结束时间
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName = '无线业务数据';//('+startTime_search+'至'+endTime_search+')';//文件名称
			fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
			fileObj.gridObj = datagrid;//当前grid对象
			fileObj.params = params;
			exportFile(fileObj);
		}
		} ]
	});
	toolbar.render();

});

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


