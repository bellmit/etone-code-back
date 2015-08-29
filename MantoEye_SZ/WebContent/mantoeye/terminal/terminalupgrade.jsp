<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>终端升级</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" type="text/css"
			href="/tools/gt-grid/gt_grid.css" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/maincontent.css" />
		<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
		<!-- 列表工具栏 -->
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
			type="text/css"></link>
		<script type="text/javascript"
			src="/js/jquery/extend.tab/js/Toolbar.js"></script>
		<script type="text/javascript" src="/js/common_grid.js"></script>
		<!-- OFC图表 -->
		<script type="text/javascript" src="/flash/js/swfobject.js"></script>
		<script type="text/javascript" src="/flash/js/json/json2.js"></script>
		<script type="text/javascript" src="/flash/js/BarChartUtil.js"></script>
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
													<td class="condition_name">
														时间粒度：
													</td>
													<td class="condition_value">
														<select name="timeLevel_search" id="timeLevel_search"
															onchange="changeTimeLevel()" style="height: 21px">
															<option value="2">
																天
															</option>
														
															<option value="4">
																月
															</option>

														</select>
													</td>
													<td class="condition_name">
														时间：
													</td>
													<td class="condition_value" id="time_space_start">
														<input type="text" class="Wdate"
															style="display: block; height: 17px;"
															onclick="selectStartTime()" id="startTime_search"
															name="startTime_search" />
													</td>
											<!-- 	<td style="width: 5px; dispaly: none;">
														&nbsp;到&nbsp;
													</td>
													<td class="condition_value" id="time_space_end"
														style="dispaly: none;">  -->
														<input type="text" class="Wdate"
															style="display:none; height: 17px;"
															onclick="selectEndTime()" id="endTime_search"
															name="endTime_search" />
													<!--  	</td>  -->
													<td class="condition_name">
														区域：
													</td>
													<td class="condition_value">
																<select name="area" id="area"
																style="height: 21px; width: 120px;">
																<option value="0">
																全网
																</option>
																<option value="1">
																	BSC
																</option>
															
																<option value="2">
																	街道办
																</option>
																	<option value="3">
																	分公司
																</option>
																<option value="4">
																	营销片区
																</option>
																	<option value="5">
																	SGSN
																</option>
														</select>				
													</td>


													<td width="300px">


														<div id="mainbtn" style="width: 300px">
															<ul>
																<li>
																	<a href="javascript:query();" 　title="查

询"><span>查询</span>
																	</a>
																</li>

															</ul>
														</div>
													</td>
												</tr>
												<!-- <tr valign="middle">
													<td class="condition_name">
														变更人数：
													</td>
													<td class="condition_value">
														<input type="text"
															style="display: block; height: 17px;"
														name="changUsers_search" id="changUsers_search" />
													</td>
													<td class="condition_name">
														到
													</td>
													<td class="condition_value">
														<input type="text" 
															style="display: block; height: 17px;"
														name="changUsers_end" id="changUsers_end" />
													</td>
												</tr> -->	
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td height="5px" bgcolor="#D3E0F2" width="100%"></td>
						</tr>


				<!-- 		<tr>
							<td>
								<table cellspacing="0" cellpadding="0" border="0" width="100%"
									class="menubar">
									<tr valign="top">
										<td width="4px" height="24px">
											<div class="lefttitle"></div>
										</td>
										<td width="100%" height="24px">
											<div class="middletitle">
												终端升级图形
											</div> -->
											<input type="hidden" value="" id="chartxmldata" />
										<!--	</td>
										<td width="4px">
											<div class="righttitle"></div>
										</td>
									</tr>
								</table>
							</td>
						</tr>

						<tr>
							<td height="230" width="100%"
								style="padding: 15px 20px; background: #ffffff">-->

								<div id="imgareas"
									style="width: 99%; height: 100%; border: 2px solid #008BD1;display:none">
									<div id="imgarea" style="width: 100%; height: 100%;display:none"></div>
								</div>
						<!-- 	</td>
						</tr> -->

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
												终端升级列表
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

var date=new Date();
date=date.valueOf();
date=date-24*60*60*1000;
date=new Date(date); 
var dataType_search = 1 ; //页面显示数据类型

var defaulettime =date.getYear()+'-'+''+((date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1)))+'-'+''+(date.getDate()>9?date.getDate():("0"+date.getDate())); //查询开始时间

var timeLevel =2;//时间粒度
var startTime=defaulettime;//开始时间
var endTime = defaulettime;//结束时间
var area=0;//区域
//var changUsers_search;//变更人数跨度
//var changUsers_end;//变更人数跨度


var dsConfig = {
	uniqueField : 'null',
	fields : [ {
		name : 'statdate'
	},{
		name : 'area'
	},{
		name : 'OTreminal'
	}, {
		name : 'NTerminal'
	},{
		name : 'nmUsers'
	},{
		name:'nmTerminalChangeId'
	},{
		name:'dataType'
	}]
};


function renderInit(value, record, columnObj, grid, colNo, rowNo) {

	//var date = record.statdate.toString();

	var name = record.nmTerminalChangeId.toString();
var dataType=record.dataType.toString();
//	if (date.indexOf('~') != -1) {//如果是周统计粒度
	//	date = date.split('~')[0];
	//}
	var thtml = "<table><tr>"
	thtml = thtml
			+ '<td><img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/info.png" alt="任务定制" onclick="openDistribute(\''
			+ name + '\',\''+ dataType + '\');" /></td>';
	thtml = thtml + '</tr></table>';

	return thtml;

}

var w="1000px";
var h="600px";
if(width==1024){
	w="815px";
	h="600px";
}


//列表链接操作
function openDistribute( date,dataType) {

	parent.killTab('TERMINAL_UPGRADETASK');
		parent.openTab1('/mantoeye/terminal/terminalupgrade_Task.jsp?nmTerminalChangeId='+date+'&dataType='+dataType+'&permId=TERMINAL_UPGRADETASK','TERMINAL_UPGRADETASK','终端升级任务定制');
}


var colsConfig= [
	{
	id : 'statdate',	header : "时间"

},{
	id : 'area',header : "区域"

},{
	id : 'OTreminal',	header : "旧终端"

},{
	id : 'NTerminal',header : "新终端"

},{
	id : 'nmUsers',headAlign:'right' ,align:'right',
	header : "更换人数"
}
,{
	id : 'state',
	header : "操作",	renderer:renderInit,sortable:false, exportable:false

}];



var gridConfig={
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
loadURL : '/terminalupgrade_query.do?1=1',
	exportURL : '/terminalupgrade_export.do?1=1',
	dataset : dsConfig ,
	columns : colsConfig ,
	width:780,
	height:500,
	resizable : false,
	beforeLoad:checkLogon,
	container : 'data_container', 
	beforeLoad:checkLogon,
	toolbarContent : 'nav | goto | pagesize | state' ,
	pageSize :  getLongPageSize(),
	remoteSort : true ,
	remotePaging:true,
	pageSizeList :   [10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
	onComplete:loadComplate
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

//页面呈现的单位
var showunit = 'MB';
var firstInit=true;
function loadComplate(){
	//grid列表初始化信息
	var obj = new Object ();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.hideColumn = 0 ;//隐藏的列数目
	obj.isCheckbox = false;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度

	//判断是否查询到数据
	judgeData(datagrid);
	//初始化grid
	initGridInfo(obj);

	if(firstInit==true){
		//var pageSize=getDispalyPageSize(1,1);
		var pageSize=20;
		var index=0;
		$(".gt-pagesize-select option").each(function(i){
			if(this.text==pageSize){
				index=i;
			}
		})
		firstInit=false;
		$(".gt-pagesize-select").get(0).selectedIndex=index;
	}

//	buildChartData(datagrid.getAllRows().length);	
	
}



var hasinit = false;


//grid查询
function query() {
	//重置分页数据
	var pageinfo = datagrid.getPageInfo();

	pageinfo.pageNum = 1;
	datagrid.setPageInfo(pageinfo);
	//查询条件
	timeLevel = $("#timeLevel_search option:selected").val();
	startTime = $('#startTime_search').attr("value");
	endTime = $('#endTime_search').attr("value");
	area= $("#area option:selected").val();
	//changUsers_search=$('#changUsers_search').attr("value");
//	changUsers_end=$('#changUsers_end').attr("value");
	if(startTime=='' ){
		alert('请选择时间跨度!');
		return ;
	}
	var param={
		timeLevel:timeLevel,
		startTime:startTime,
		endTime:endTime,
		area:area
		//changUsers_search:changUsers_search,
		//changUsers_end:changUsers_end
	};
	hasinit = false;
	GT.$grid('datagrid').query( param );
}




//时间改变事件
function changeTimeLevel() {
	$('#startTime_search').attr("value", "");
	$('#endTime_search').attr("value", "");
}

//初始化查询时间
$(document).ready(function() {
	$('#startTime_search').attr('value', startTime);
	$('#endTime_search').attr('value', endTime);
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


//初始化toolbar
$(document).ready(function(){
	
      var toolbar = new Toolbar({
        renderTo : 'toolbar',
		//border: 'top',
        items : [{
          type : 'button',
          text : '导出',
          bodyStyle : 'xls',
          useable : 'T',
          handler : function(){
             //参数
			var params={
				dataType_search:dataType_search,
				timeLevel:timeLevel,
				startTime:startTime,
				endTime:endTime,
				area:area
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName='终端升级数据';
			fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
			fileObj.gridObj = datagrid;//当前grid对象
			fileObj.params = params;
			exportFile(fileObj);
          }
        }]
      });
	  toolbar.render();
    });
    


/*图表是否已经初始化*/
var chartinit = false;
/*组装图形数据*/
function buildChartData(totalRecords){

	//var title = startTime+"至"+endTime+" 变更用户数走势图";
	var title = startTime+" 变更用户数走势图";
	var xlabels = [] ;
	var datas1 = [] ;
	var showxlabels =[];
	
	if(typeof(datagrid.getRecord(0))!="undefined"){
		for(var i = 0 ; i<totalRecords;i++){	
			var record = datagrid.getRecord(i);	 	
			if(i<10){//图形只显示前10 的数据
			xlabels[i] = record.area; 	
				showxlabels[i]=record.OTreminal+">"+record.NTerminal;
					datas1[i] = StringToFloat(record.nmUsers);
				
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

	 var tdatas = [];
	 
	  //格式化Y轴数据（流量）
	   var showunit="KB";
	var obj = formatObjNumberByWan(datas1);
		
	var lobj =  obj
	var lunit="用户数";
	if(lobj.unit!=""){
		lunit = lunit +"@@"+lobj.unit;
	}
	tdatas[0] = lobj.datas;
	
	
	 //如果有单位  则label与单位间用@@分开
	var labels = [lunit];	

	tdatas[0] = buildBarChartTip(lobj.datas,showxlabels,lunit);
	var jsons1 = buildSingleChartData(title,labels,xlabels,tdatas);

	var str1 = JSON.stringify(jsons1);
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
///////////////////////////////////////////




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


