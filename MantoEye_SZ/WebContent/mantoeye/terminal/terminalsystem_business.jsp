<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>智能系统业务分析</title>
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
							<div class="middletitle">
								智能系统业务图形
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
									智能系统业务列表
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
var objterminal = window.dialogArguments;
var hasinit = false;//第一次初始化图形数据标识
var datachange = false;//第N次查询初始化数据标识
var timeLevel = '1';//查询时间粒度
var sortnmFlush = false;
var nmAreaId = objterminal.nmAreaId;
var terminalId = objterminal.nmTerminalId;
var areaType = objterminal.areaType;
var timeLevel = objterminal.timeLevel;
var startTime = objterminal.startTime;

var dsConfig = {
	uniqueField : 'null',
	fields : [ {
		name : 'fullDate'
	}, {
		name : 'terminalBrand'
	}, {
		name : 'businessName'
	}, {
		name : 'intImsis'
	}, , {
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
	} ]
};

var colsConfig = [ {
	id : 'fullDate',
	header : "时间"

}, {
	id : 'terminalBrand',
	header : "操作系统名称"
}, {
	id : 'businessName',
	header : "业务名称"

}, {
	id : 'intImsis',
	header : "用户数",
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

} ];

var gridConfig = {
	id : "datagrid",
	loadURL : '/terminalsystem-business_query.do?areaType=' + areaType
			+ '&timeLevel=' + timeLevel + '&startTime=' + startTime
			+ '&terminalId=' + terminalId+'&nmAreaId='+nmAreaId,
	exportURL : '/terminalsystem-business_export.do?1=1',
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

	judgeData(datagrid);
if(!initHasData){
		sortInit = true;
	}
	
	if(sortInit && initHasData){
	showunit=	 buildUnit();
		sortInit = false;
	}
	var width = window.screen.availWidth;
	//根据分辨率初始化grid列宽
	if (!columninit) {
		if (width == 1024) {
			initColumnWidthWithPop(datagrid, 792, 6);

		} else {
			initColumnWidthWithPop(datagrid, 973, 6);

		}
	}


buildChartData(datagrid.getAllRows().length);
}

///////////////////图表////////////////////////

/*图表是否已经初始化*/
var chartinit = false;
/*组装图形数据 */
 function buildChartData(totalRecords){

	var title = startTime+" 流量|用户数区域分析图";
	
	var xlabels = [] ;
	var datas1 = [] ;
	var datas2 = [] ;
	
	if(typeof(datagrid.getRecord(0))!="undefined"){
		for(var i = 0 ; i<totalRecords;i++){	
			var record = datagrid.getRecord(i);	 	
			if(i<20){//图形只显示前20 的数据
				xlabels[i] = record.businessName; 
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
				timeLevel : timeLevel,//时间粒度  1:小时 2:天 3:周 4:月
				startTime : startTime,//开始时间
				areaType : areaType,
				terminalBrand : terminalBrand,
				terminalId : terminalId
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName = '智能系统业务分析数据';//('+startTime+'至'+endTime_search+')';//文件名称
			fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
			fileObj.gridObj = datagrid;//当前grid对象
			fileObj.params = params;
			exportFile(fileObj);
		}
		} ]
	});

	toolbar.render();
});
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


