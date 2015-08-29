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
var sortnmFlush=false;
var dsConfig = {
	uniqueField : 'null',
	fields : [  {
		name : 'statdate'
	},{
		name : 'NTerminal'
	}, {
		name : 'business'
	}, {
		name : 'nmUsers'
	}, {
		name : 'nmFlushKB'
	},{
		name:'nmFlushMB'
	},{
		name:'nmFlushGB'
	}]
};

var colsConfig = [ {
		id : 'statdate',width:250,
	header : "时间"

},{
	id : 'NTerminal',
	header : "终端",width:250,headAlign:'right' ,align:'right',sortable:false

},{
	id : 'business',width:250,headAlign:'right' ,align:'right',
	header : "业务",sortable:false
},{
	id : 'nmFlushGB',width:250,headAlign:'right' ,align:'right',
	header : "流量(GB)",	renderer : renderFormatDataInit2,
	hidden : true,hidden : true
},{
	id : 'nmFlushMB',width:250,headAlign:'right' ,align:'right',
	header : "流量(MB)",	renderer : renderFormatDataInit2,hidden : true
},{
	id : 'nmFlushKB',width:250,headAlign:'right' ,align:'right',
	header : "流量(KB)",	renderer : renderFormatDataInit2
	
},{
	id : 'nmUsers',width:250,headAlign:'right' ,align:'right',
	header : "人数"
}];


var gridConfig = {
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
		loadURL : '/terminalupgradebisflush_query.do?taskId='+taskId+'&dataType='+dataType,
	exportURL : '/terminalupgradebisflush_export.do?1=1',
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

	//判断是否查询到数据
	judgeData(datagrid);
	//选择呈现单位
	if (initHasData) {
		sortnmFlush = true;
	}
	if (sortnmFlush) {
		showunit = buildnmFlushUnit();
		sortnmFlush = false;
	}

	//初始化grid
var width=window.screen.availWidth;
	//根据分辨率初始化grid列宽
	if(!columninit){
		if(width==1024){
			initColumnWidthWithPop(datagrid,792,2);
			
		}else{
			initColumnWidthWithPop(datagrid,973,2);
			
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

			var params = {
taskId:taskId,
dataType:dataType
			};
			//导出
			
			var fileObj = new Object();
			fileObj.fileName = '终端升级业务流量统计数据';
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
		 		title = chartTime+"业务 流量|用户数对比图";
		 	}				
			totalbar++;
	 showxlabels[i] = record.NTerminal+"-"+ record.business;
		 	xlabels[i] = record.NTerminal+"-"+ record.business; 	
		 	//如果柱状图需要添加点击，必须以如下的方法设置个点的值	
		 //	datas1[i] = addCallFunction(record.intType,StringToFloat(record.nmUsers));			
		 	//datas2[i] = addCallFunction(record.intType,StringToFloat(record.nmFlushKB));	
		 		if( 'MB' == showunit){
					datas2[i] = StringToFloat(record.nmFlushMB);
		 		}else if( 'KB' == showunit){
					datas2[i] = StringToFloat(record.nmFlushKB);
		 		}else if( 'GB' == showunit){
					datas2[i] = StringToFloat(record.nmFlushGB);
		 		}	

			//如果柱状图不需要点击时间，可以用以下写法 
			datas1[i] = StringToFloat(record.nmUsers);	
			//datas2[i] = StringToFloat(record.nmFlushKB);		
		}
	
	
	//格式化X轴
	 xlabels = formatXDateStrLables(xlabels);
	 var tdatas = [];
	 
	  //格式化Y轴数据（流量）

	
	  var obj = new Object();
		obj.datas = datas2;
		obj.unit = showunit;
			var lobj =  obj
		var lunit="流量";
	if(lobj.unit!=""){
		lunit = lunit +"@@"+lobj.unit;
	}
	tdatas[0] = lobj.datas;
	
	
	
	
	

	 //格式化Y轴数据（用户数）
	var robj = formatObjNumberByWan(datas1);
	var runit="用户数";
	if(robj.unit!=""){
		runit = runit +"@@"+robj.unit;
	}
	//alert(robj.datas);
	tdatas[1] = robj.datas;
	
	tdatas[0] = buildBarChartTip(lobj.datas,showxlabels,lunit);
	tdatas[1] = buildLineChartTip(robj.datas,showxlabels,runit);

	 //如果有单位  则label与单位间用@@分开
	var labels = [lunit,runit];	

	var jsons1 = buildMutileChartData(title,labels,xlabels,tdatas);
	var str1 = JSON.stringify(jsons1);
		return str1;
	}else{
		var str2 = JSON.stringify(buildEmptyAxisChart());	
		return str2;	
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

</script>


