<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>集团号码</title>
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
									<tr valign="top" style="font-size:1px;">
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
													<td class="condition_name" style="display: none">
														时间粒度：
													</td>
													<td class="condition_value" style="display: none">
																<select name="timeLevel_search" id="timeLevel_search"
															onchange="changeTimeLevel()" style="height: 21px">
															<option value="1" selected>
																小时
															</option>
															<option value="2">
																天
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
													<td style="width: 5px;">
														&nbsp;到&nbsp;
													</td>
													<td class="condition_value" id="time_space_end">
														<input type="text" class="Wdate"
															style="display: block; height: 17px;"
															onclick="selectEndTime()" id="endTime_search"
															name="endTime_search" />
													</td>
														<td class="condition_name">
														集团名称：
													</td>
													<td class="condition_value">
														<input type="text" name="CustomerName" id="CustomerName"
															style="height: 17px; width: 115px" />
													</td>
													<td class="condition_name">
														集团号码：
													</td>
													<td class="condition_value">
														<input type="text" name="number_search" id="number_search"
															style="height: 17px; width: 115px" />
													</td>
													
													
													<td width="300px">
														<div id="mainbtn" style="width:300px">
															<ul>
																<li>
																	<a href="javascript:query();" 　title="查询"><span>查询</span>
																	</a>
																</li>
																<li>
																	<a href="javascript:parent.openTab1('/mantoeye/sports/aggregateNum/aggregatenum-index.jsp?permId=GROUP_CUSTOMER_NUMBER','GROUP_CUSTOMER_NUMBER','集团客户号码');" 
																		style="width:80px;"  title="添加号码"><span>添加号码</span>
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
												集团客户列表
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

var hasinit = false;//第一次初始化图形数据标识
var datachange = false;//第N次查询初始化数据标识
var dataType_search = 1; //页面显示数据类型
var timeLevel_search = '1';//查询时间粒度
date = date.valueOf();
date = date - 24 * 60 * 60 * 1000;
date = new Date(date);
var yesterday = date.getYear()
		+ '-'
		+ ''
		+ ((date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : ("0" + (date
				.getMonth() + 1))) + '-' + ''
		+ (date.getDate() > 9 ? date.getDate() : ("0" + date.getDate())); //查询开始时间
var endTime_search = yesterday + " 23:00";//查询结束时间

var startTime_search = yesterday + " 00:00"//查询开始时间
var number_search="";
var CustomerName="";
var dsConfig = {
	//data : data1 ,
	uniqueField : 'null',
	fields : [  {
		name : 'statdate'
	},{
		name : 'vcNote'
	},{
		name : 'context1'
	}, {
		name : 'context2'
	} ]
};



var colsConfig = [  {
	id : 'statdate',
	header : "时间"

},{
	id : 'vcNote',
	header : "集团名称",width:350,sortable: false

},{
	id : 'context1',
	header : "客户号码",headAlign:'right' ,align:'right'

},{
	id : 'context2',headAlign:'right' ,align:'right',sortable: false,
	header : "成功率(%)"

} ];

var gridConfig = {
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL : '/sportcustomer_query.do?1=1',
	exportURL : '/sportcustomer_export.do?1=1',
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
	pageSizeList : [5, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,
			24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
			45, 50, 100 ],
	onComplete : loadComplate
};
function getLongPageSize() {
	var size = getDispalyPageSize(1, 1);
	if (size < 20) {
		size = 20;
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
	obj.hideColumn = 0;//隐藏的列数目
	obj.isCheckbox = false;//是否可选择
	obj.otherHeight = 0; //必须定义,因为有些页面不同，所以此处指定预留高度
	//obj.otherWidth = 160;

	//判断是否查询到数据
	judgeData(datagrid);


	//初始化grid
	initGridInfo(obj);
	/*	*/
	if (firstInit == true) {
		//var pageSize=getDispalyPageSize(1,1);
		var pageSize = 20;
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


//grid查询
function query() {
	
	//重置分页数据
	var pageinfo = datagrid.getPageInfo();
	pageinfo.pageNum = 1;
	datagrid.setPageInfo(pageinfo);
	//查询条件
	timeLevel_search = $("#timeLevel_search option:selected").val();
	startTime_search = $('#startTime_search').attr("value");
	endTime_search = $('#endTime_search').attr("value"); //设置图形标志
	number_search=$('#number_search').attr("value");
	CustomerName=$('#CustomerName').attr("value");
	if(startTime_search=='' || endTime_search=='' || startTime_search==null || endTime_search==null){
		alert('请选择时间跨度!');
		return ;
	}

	hasinit = false;
	datachange = true;
	//传递给后台参数
	var param = {
		hasinit : hasinit,
	//	dataType_search : dataType_search,//TD标识
		timeLevel_search : timeLevel_search,//时间粒度  1:小时 2:天 3:周 4:月
		startTime_search : startTime_search,//开始时间
		endTime_search : endTime_search,
		number_search:number_search,
		CustomerName:CustomerName
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
$(document).ready(
		function() {		
	  var permflag = '<%=btnperm%>';
		  var bflags = initBtnPerm('CUSTOMER_DOWN,GROUP_CUSTOMER_BTN_IMPORT',permflag);
			var toolbar = new Toolbar( {
				renderTo : 'toolbar',
				//border: 'top',
				items : [
						{
							type : 'button',
							text : '导出',
							bodyStyle : 'xls',
							useable : 'T',
							  //  useable : bflags[0],
							handler : function() {
								//参数
							var params = {
								hasinit : hasinit,
								dataType_search : dataType_search,//TD标识
								timeLevel_search : timeLevel_search,//时间粒度  1:小时 2:天 3:周 4:月
								startTime_search : startTime_search,//开始时间
								endTime_search : endTime_search,
								number_search:number_search,
								CustomerName:CustomerName
							//结束时间
							};
							//导出
							var fileObj = new Object();
							fileObj.fileName = '集团客户数据';//('+startTime_search+'至'+endTime_search+')';//文件名称
							fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
							fileObj.gridObj = datagrid;//当前grid对象
							fileObj.params = params;
							exportFile(fileObj);
						}
						}/*,
						{
							type : 'button',
							text : '号码导入',
							bodyStyle : 'new',
						    useable : bflags[1],
							handler : function() {
								var mm = showMMDialog(
										'/mantoeye/sports/Number_import.jsp', '号码上传',
										'540px', '300px');
								if (typeof (mm) != "undefined") {
									var flag = window.confirm(mm);
									if (flag) {
										var mmwidth = window.screen.availWidth;//屏幕宽度
										var ml = 378;
										var mt = 200;
										if (mmwidth == 1024) {
											ml = 275;
										}
										alert("正在上传号码……，请稍后一段时间再查看！");						
										savaUpFile();
						
									}
								}
							}

						} */]
			});
			toolbar.render();
		});

function savaUpFile() {
	$.ajax( {
		type : "post",
		url : "/sportcustomer_saveUpFile.do",
		data : {},
		success : function(xml) {
			
		},
		error : function() {
			alert('上传文件出错!');
		}
	});
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


