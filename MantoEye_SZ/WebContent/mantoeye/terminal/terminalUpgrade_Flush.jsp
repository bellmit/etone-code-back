<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>终端升级流量统计</title>
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
<script type="text/javascript" src="/flash/js/PieChartUtil.js"></script>
<script type="text/javascript" src="/flash/js/DoubleBarChartUtil.js"></script>
<script type="text/javascript" src="/flash/js/PercentBarChartUtils.js"></script>
<script type="text/javascript" src="/flash/js/ParseUtils.js"></script>

	</head>
	<body>
		<table id="maincontent" cellspacing="0" cellpadding="0" border="0"
			bgcolor="#D3E0F2" width="100%" height="100%">
			<tr>
				<td colspan="2" height="5px"></td>
			</tr>
			<tr>
							<td height="5px" bgcolor="#D3E0F2" width="100%"></td>
						</tr>
		<td>
								<table cellspacing="0" cellpadding="0" border="0" width="100%"
									class="menubar">
									<tr valign="top">
										<td width="4px" height="24px">
											<div class="lefttitle"></div>
										</td>
										<td width="100%" height="24px">
											<div class="middletitle" id="headTitle">
											终端升级流量统计图形
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
							
								<div id="imgareas2" style="width: 100%; height: 100%;border: 2px solid #008BD1;">
									<div id="imgarea" style="width: 0%; height: 0%"></div>
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
										终端升级流量统计列表
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
var objterminal = window.dialogArguments;
var taskId=objterminal.taskId;
var dataType=objterminal.dataType;

var dsConfig = {
	uniqueField : 'null',
	fields : [ {
		name : 'statdate'
	},  {
		name : 'OTreminal'
	},  {
		name : 'nmOFlushGB'
	}, {
		name : 'nmOFlushMB'
	}, {
		name : 'nmOFlushKB'
	}, {
		name : 'NTerminal'
	} , {
		name : 'nmFlushGB'
	}, {
		name : 'nmFlushMB'
	}, {
		name : 'nmFlushKB'
	}]
};

var colsConfig = [ {
	id : 'statdate',
	header : "时间"

}, {
	id : 'OTreminal',

	header : "旧终端",
	align : 'right',
	headAlign : 'right',sortable:false

}, {
	id : 'nmOFlushGB',
	header : "旧终端流量(GB)",
	align : 'right',
	headAlign : 'right',
	exportnumber : true,
	renderer : renderFormatDataInit2,
	hidden : true

}, {
	id : 'nmOFlushMB',

	header : "旧终端流量(MB)",
	align : 'right',
	headAlign : 'right',
	exportnumber : true,
	renderer : renderFormatDataInit2

}, {
	id : 'nmOFlushKB',

	header : "旧终端流量(KB)",
	align : 'right',
	headAlign : 'right',
	exportnumber : true,
	renderer : renderFormatDataInit2,
	hidden : true

}, {
	id : 'NTerminal',

	header : "新终端",
	align : 'right',
	headAlign : 'right',sortable:false

}, {
	id : 'nmFlushGB',

	header : "新终端流量(GB)",
	align : 'right',
	headAlign : 'right',renderer : renderFormatDataInit2,
	hidden : true

}, {
	id : 'nmFlushMB',

	header : "新终端流量(MB)",
	align : 'right',
	headAlign : 'right',
	exportnumber : true,
	renderer : renderFormatDataInit2

}, {
	id : 'nmFlushKB',

	header : "新终端流量(KB)",
	align : 'right',
	headAlign : 'right',
	exportnumber : true,
	renderer : renderFormatDataInit2,
	hidden : true

}];

var gridConfig = {
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
		loadURL : '/terminalupgradeflush_query.do?taskId='+taskId+'&dataType='+dataType,
	exportURL : '/terminalupgradeflush_export.do?1=1',
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
var showOunit = 'KB';
var firstInit = true;
var sortnmFlush=false;
//gird回调函数
function loadComplate() {
	judgeData(datagrid);
	//选择呈现单位
	if (initHasData) {
		sortnmFlush = true;
	}
	if (sortnmFlush) {
		showunit = buildnmFlushUnit();
	//	showOunit= buildnmOFlushUnit();
		sortnmFlush = false;
	}

	//初始化grid
var width=window.screen.availWidth;
	//根据分辨率初始化grid列宽
	if(!columninit){
		if(width==1024){
			initColumnWidthWithPop(datagrid,792,4);
			
		}else{
			initColumnWidthWithPop(datagrid,973,4);
			
		}
	}
	buildBarChartData(datagrid.getAllRows().length);	
}

function buildnmFlushUnit() {

	var totalRecords = datagrid.getAllRows().length;
	var max = 0;

	if (typeof (datagrid.getRecord(0)) != "undefined") {
		for ( var i = 0; i < totalRecords; i++) {
			var record = datagrid.getRecord(i);
			var fl = record.nmFlushKB;
				var f2= record.nmOFlushKB;
			if (fl > max) 
				max = fl;
			if(f2>max)
				max=f2
		}

		var level = getViewLevel(max);
		if (level == 1) {//KB

			datagrid.getColumn("nmFlushKB").show();
			datagrid.getColumn("nmFlushGB").hide();
			datagrid.getColumn("nmFlushMB").hide();
			
			datagrid.getColumn("nmOFlushKB").show();
			datagrid.getColumn("nmOFlushGB").hide();
			datagrid.getColumn("nmOFlushMB").hide();

			showUnit = "KB";
		} else {
			if (level == 3) {//GB
				datagrid.getColumn("nmFlushKB").hide();
				datagrid.getColumn("nmFlushGB").show();
				datagrid.getColumn("nmFlushMB").hide();
				
				datagrid.getColumn("nmOFlushKB").hide();
				datagrid.getColumn("nmOFlushGB").show();
				datagrid.getColumn("nmOFlushMB").hide();
				

				showUnit = "GB";
			} else {//MB

				datagrid.getColumn("nmFlushKB").hide();
				datagrid.getColumn("nmFlushGB").hide();
				datagrid.getColumn("nmFlushMB").show();
				
				datagrid.getColumn("nmOFlushKB").hide();
				datagrid.getColumn("nmOFlushGB").hide();
				datagrid.getColumn("nmOFlushMB").show();
				showUnit = "MB";
			}
		}
	}
	return showUnit;
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
taskId:taskId,
dataType:dataType
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName = '终端升级流量统计数据';//('+startTime_search+'至'+endTime_search+')';//文件名称
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

///////////////////图表(柱状图)////////////////////////
/*图表是否已经初始化*/
var chartinit = false;
var barchartinit = false;
var currentflash="flush";
var flushchartjson="";
var firstSort = true;
/*组装图形数据*/
function buildBarChartData(totalRecords){
	var str1 =buildChartJsonStr(totalRecords);
	if(!chartinit){
		chartinit = true;
		document.getElementById("chartxmldata").value=str1;
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=business", "imgarea", "100%", "100%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
	}else{
	  	var tmp1 = findSWF("imgarea");	
  		tmp1.load(str1);	
	}

}
var sortOrder = "desc";
///////////////////////////////////////////
function buildChartJsonStr(totalRecords,flag){
	var chartTime ='';
	var title = '';
	var xlabels = [] ;
	var datas1 = [] ;
	var datas2 = [] ;
		var showxlabels =[];
	if(typeof(datagrid.getRecord(0))!="undefined"){	
		totalbar = 0;
		for(var i = 0 ; i<totalRecords;i++){
			if(totalbar==10){
				break;
			}	
			var record = datagrid.getRecord(i);	 	
			if(title==''){
		 		chartTime = record.statdate;
		 		title = chartTime+"流量对比图";
		 	}				
			totalbar++;
	 showxlabels[i] = record.OTreminal+">"+ record.NTerminal;
		 	xlabels[i] = record.OTreminal+">"+ record.NTerminal;	
		 	//如果柱状图需要添加点击，必须以如下的方法设置个点的值	
		 //	datas1[i] = addCallFunction(record.intType,StringToFloat(record.nmOFlushKB));			
	//	datas2[i] = addCallFunction(record.intType,StringToFloat(record.nmFlushKB));	

			//如果柱状图不需要点击时间，可以用以下写法 
			//datas1[i] = StringToFloat(record.totalFlushMB);	
			//datas2[i] = StringToFloat(record.intImsis);	
				if( 'MB' == showunit){
					datas2[i] = StringToFloat(record.nmFlushMB);
						datas1[i] = StringToFloat(record.nmOFlushMB);
		 		}else if( 'KB' == showunit){
					datas2[i] = StringToFloat(record.nmFlushKB);
							datas1[i] = StringToFloat(record.nmOFlushKB);
		 		}else if( 'GB' == showunit){
					datas2[i] = StringToFloat(record.nmFlushGB);
						datas1[i] = StringToFloat(record.nmOFlushGB);
		 		}	
	
		}

	
	//格式化X轴
	 xlabels = formatXDateStrLables(xlabels);
	 var tdatas = [];
	 
	  //格式化Y轴数据（流量）
//	var lobj =formatDataBy1024(datas2,"KB");
	  var obj = new Object();
		obj.datas = datas2;
		obj.unit = showunit;
			var lobj =  obj
		var lunit1="新终端流量";
	if(lobj.unit!=""){
		lunit1 = lunit1 +"@@"+lobj.unit;
	}
	tdatas[1] = lobj.datas;


//var lobj1 =formatDataBy1024(datas1,"KB");
  var obj1 = new Object();
		obj1.datas = datas1;
		obj1.unit = showunit;
			var lobj1 =  obj1
		var lunit="旧终端流量";
	if(lobj1.unit!=""){
		lunit = lunit +"@@"+lobj1.unit;
	}
	tdatas[0] = lobj1.datas;

	tdatas[1] = buildBarChartTip(lobj.datas,showxlabels,lunit1);
	tdatas[0] = buildBarChartTip(lobj1.datas,showxlabels,lunit);

	 //如果有单位  则label与单位间用@@分开
	var labels = [lunit,lunit1];	

	var jsons1 = buildDoubleBarChartDataA(title,labels,xlabels,tdatas);
	var str1 = JSON.stringify(jsons1);
		return str1;
	}else{
		var str2 = JSON.stringify(buildEmptyAxisChart());	
		return str2;	
	}
}

function buildDoubleBarChartDataA(text,labels,xlabels,datas){
	
	var title =  buildMyTitle(text);	
	var lefty = getMyYAxisUtilDataA(datas[0],datas[1]);
	var righty = getMyYAxisUtilDataA(datas[1],datas[0]);
	var cc = lefty.size-righty.size;
	if(cc!=0){
		if(cc>0){
			righty.max = righty.step*lefty.size;
		}else{
			lefty.max = lefty.step*righty.size;
		}
	}

	var chart = {
		"title" : title,
		"tooltip":{
		    "shadow":false,
		    "stroke":1,
		    "colour":"#333333",
		    "background":"#FFFFFF",
		    "title":"{font-size: 14px; color: #905050;}",
		    "body":"{font-size: 12px; font-weight: bold; color: #333333;}"
		  },
		"elements" :[{
		      	"type":      "bar",
		      	"alpha":     1.0,
		      	"colour":    "#1B95D9",
		      	"text":      buildMyLabel(labels[0]),
		      	"font-size": 10,
		      	"tip":buildMyTip(labels[0]), 
		      	"values" :   datas[0]
		    },{	  		
		    	"type":      "bar",
		      	"alpha":     1.0,
		      	"colour":    "#FF0000",
		      	"text":      buildMyLabel(labels[1]),
		      	"font-size": 10,
		      	"tip":buildMyTip(labels[1]), 
		      	"values" :   datas[1],
			  	"axis":      "right"
			 }],
		"x_axis":{
				"stroke":1,
				"tick_height":3,
				"colour":"#909090",
				"grid_colour":"#333333",
				"labels": buildMyXLables(xlabels)
				},
		"y_axis":{
			    "stroke":      1,
			    "tick_length": 3,
			    "max":   lefty.max,
			    "min":   lefty.min,
			    "steps": lefty.step,
			    "colour":      "#1B95D9",
			    "grid_colour": "#333333"
			 },
		"y_axis_right":{  
				"stroke":      1,
				"max":   righty.max,
			    "min":   righty.min,
			    "steps": righty.step,
				//"grid-visible":  true,
				"colour":		"#FF0000",
				"grid-colour":  "#333333"
			}	 
  }

  	return chart;
}


function getMyYAxisUtilDataA(datas,datas1){
	var obj = new Object();
	var max =0;
	var min =0;
	var step =1;
	if(datas.length>0){
		if(typeof (datas[0].top)!="undefined"){
			max = datas[0].top>datas1[0].top?datas[0].top:datas1[0].top;
			min = datas[0].top;
			for(var i=1;i<datas.length;i++){
				if(datas[i].top>max){
					max = datas[i].top;
					
				}
				if(datas[i].top<min){
					min = datas[i].top;
				}
			}
				for(var i=1;i<datas1.length;i++){
				if(datas1[i].top>max){
					max = datas1[i].top;
				}
				if(datas1[i].top<min){
					min = datas1[i].top;
				}
			}
		}else{
			max = datas[0];
			min = datas[0];
			for(var i=1;i<datas.length;i++){
				if(datas[i]>max){
					max = datas[i];
				}
				if(datas[i]<min){
					min = datas[i];
				}
			}
			for(var i=1;i<datas1.length;i++){
				if(datas1[i].top>max){
					max = datas1[i].top;
				}
				if(datas1[i].top<min){
					min = datas1[i].top;
				}
			}
		}		
	}
	obj.max = max;
	//图形y轴从最小值开始
	//obj.min = min;
	//图形y轴从0开始
	obj.min = 0;
	//默认y轴刻度为10左右
	var fobj = getMySteps(obj,10);
	return fobj;
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

</script>


