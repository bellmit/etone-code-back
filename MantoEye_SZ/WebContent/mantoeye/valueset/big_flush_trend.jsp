<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>大流量用户流量走势</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" type="text/css" href="/tools/gt-grid/gt_grid.css" />
		<link type="text/css" rel="stylesheet" href="/skin/Default/css/maincontent.css" />
		<link type="text/css" rel="stylesheet" href="/css/dialog.css" />
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css" type="text/css"></link>
		<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js"></script>
		<script language="javascript" type="text/javascript" src="/tools/datepicker/WdatePicker.js"></script>
		<!-- 列表工具栏 -->		
		<script type="text/javascript" src="/js/jquery/extend.tab/js/Toolbar.js"></script>
		<script type="text/javascript" src="/js/common_grid.js"></script>
		<script type="text/javascript" src="/mantoeye/dialog/ControlSelector.js"></script>
		<script type="text/javascript" src="/tools/jquery/jquery.treeview.js"></script>
		<!-- OFC图表 -->
		<script type="text/javascript" src="/flash/js/swfobject.js"></script>
		<script type="text/javascript" src="/flash/js/json/json2.js"></script>
		<script type="text/javascript" src="/flash/js/LineChartUtil.js"></script>
		<script type="text/javascript" src="/flash/js/ParseUtils.js"></script>
	</head>
	<body style="overflow-x: hidden">
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
							<td></td>
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
												时间走势图形
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
								
							<div id="imgareas" style="width: 99%; height: 100%;border: 2px solid #008BD1;">
							<div id="imgarea1" style="width: 99%; height: 100%"></div>
							</div>
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
												时间走势列表
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
//var obj =parent.terminalObj;
var obj = window.dialogArguments ;
var msisdn_trend = obj.msisdn;
var startTime_search = obj.searchDateStart;
var endTime_search = obj.searchDateEnd;
var hasinit = false;//第一次初始化图形数据标识
var datachange = false;//第N次查询初始化数据标识
var dataType_search = 1 ; //页面显示数据类型
var timeLevel_search ='1';//查询时间粒度

	//初始化查询时间
var date=new Date();
date=date.valueOf();
date=date-24*60*60*1000;
date=new Date(date); 
var yesterday=date.getYear()+'-'+''+((date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1)))+'-'+''+(date.getDate()>9?date.getDate():("0"+date.getDate())); //查询开始时间
if(startTime_search==null) startTime_search =yesterday;//查询结束时间
if(endTime_search==null) endTime_search=yesterday;//查询开始时间
if(msisdn_trend==null)msisdn_trend="0";
//初始化查询时间
$(document).ready(function(){

	$('#startTime_search').attr('value',startTime_search);
	$('#endTime_search').attr('value',endTime_search);
	$('#msisdn_title').attr('value',msisdn_trend);
});	
var dsConfig= {	
	uniqueField : 'id' ,
	fields :[
		{name : 'time'        },
		{name : 'imsi'      },
		{name : 'imei'      },
		{name : 'cellName'      },
		{name : 'branchName'      },
		{name : 'strMissdn'        },
		{name : 'isBlackUser'      },
		{name : 'upFlushMB'      },
		{name : 'downFlushMB'      },
		{name : 'totalFlushMB'      }
	]
};

function renderInit(value ,record,columnObj,grid,colNo,rowNo){	
}

function rendererDate(value ,record,columnObj,grid,colNo,rowNo){
	return (value+"").split(" ")[0];
}	
var colsConfig = [
		{ id : 'time'      , header : "时间"  ,sortable:false ,renderer:rendererDate  },
		//{ id : 'intImsis'    , header : "IMSI"  ,hidden:true },
		//{ id : 'strMissdn'      , header : "号码" ,hidden:true   },
		//{ id : 'imei'    , header : "IMEI" ,  sortable:false ,hidden:true },
		{ id : 'upFlushMB'      , header : "上行流量(MB)",headAlign:'right' ,sortable:false ,align:'right'  ,exportnumber:true ,renderer:renderFormatDataInit2 },
		{ id : 'downFlushMB'    , header : "下行流量(MB)" ,headAlign:'right' ,sortable:false ,align:'right',exportnumber:true ,renderer:renderFormatDataInit2 },
		{ id : 'totalFlushMB'    , header : "总流量(MB)" ,headAlign:'right' ,sortable:false ,align:'right',exportnumber:true ,renderer:renderFormatDataInit2 }
];

var gridConfig={
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/bigflushdisplay_queryTrend.do?msisdn_trend='+msisdn_trend+'&startTime_trend='+startTime_search+'&endTime_trend='+endTime_search,
	exportURL :'/bigflushdisplay_exportTrend.do?1=1' ,
	dataset : dsConfig ,
	columns : colsConfig ,
	width:780,
	height:500,
	resizable : false,
	showGridMenu : false,
	allowCustomSkin : false,
	allowFreeze : false,
	allowGroup : false,
	allowHide : false,
	container : 'data_container', 
	beforeLoad:checkLogon,
	toolbarContent : 'nav | goto | pagesize | state' ,
	pageSize :  getLongPageSize(),
	remoteSort : true ,
	pageSizeList :   [10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
	onComplete:loadComplate
};
function getLongPageSize(){
	var size = getDispalyPageSize(1,1);
	if(size<25){
		size = 25;
	}
	return size;
}
var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
});
//页面呈现的单位
var showunit = 'MB';
var firstInit=true;
//gird回调函数
function loadComplate(){
	var  obj = new Object ();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.hideColumn = 0 ;//隐藏的列数目
	obj.isCheckbox =false;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度
	obj.otherWidth =0;
	
	//判断是否查询到数据
	judgeData(datagrid,1);
	
	//初始化grid
	//initGridInfo(obj);
	/*	*/
	var totalRecords = datagrid.getAllRows().length;//获取加载到的数据记录数目
	var width=window.screen.availWidth;
	//根据分辨率初始化grid列宽
	if(!columninit){
		if(width==1024){
			initColumnWidthWithPop(datagrid,792,0);
			
		}else{
			initColumnWidthWithPop(datagrid,973,0);
			
		}
	}
	if(firstInit==true){
		var pageSize=25;
		var index=0;
		$(".gt-pagesize-select option").each(function(i){
			if(this.text==pageSize){
				index=i;
			}
		})
		firstInit=false;
		$(".gt-pagesize-select").get(0).selectedIndex=index;
	}
	
	//初始化图表
	if(!hasinit){
		getAjaxChartXmlData();
	}
	
}
///////////////////////////////////////////

///////////////////图表////////////////////////

/*图表是否已经初始化*/
var chartinit = false;
/*组装图形数据*/
function buildChartData(json){

	var title ="用户[" +msisdn_trend+"]"+startTime_search+"至"+endTime_search+" 流量走势图";
	if(startTime_search==''&&endTime_search==''){
		title ="用户[" +msisdn_trend+"] 流量走势图";
	}	
	var xlabels = [] ;
	var datas1 = [] ;
	
	if(json!=''&&json.length>0){
		for(var i = 0 ; i<json.length;i++){	
			var record = json[i];	 	
		 	xlabels[i] = record.time; 	
			datas1[i] = StringToFloat(record.totalFlushMB);		
		}
	}
	 var showxlabels = xlabels;

	 xlabels = formatXDateLables(xlabels,15,"day");
	    
	 var tdatas = [];
	
	  //格式化Y轴数据（流量）
	var lobj = formatDataBy1024(datas1,"MB");
	var lunit="流量";
	if(lobj.unit!=""){
		lunit = lunit +"@@"+lobj.unit;
	}
	tdatas[0] = lobj.datas;
	
	
	 //如果有单位  则label与单位间用@@分开
	var labels = [lunit];	
	tdatas[0] = buildLineChartTip(lobj.datas,showxlabels,lunit); 
	var jsons1 = buildSingleChartDataPre(title,labels,xlabels,tdatas);

	var str1 = JSON.stringify(jsons1);
	if(!chartinit){
		chartinit = true;
		document.getElementById("chartxmldata").value=str1;
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=flush", "imgarea1", "99%", "99%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
	}else{
	  	var tmp1 = findSWF("imgarea1");	  	
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


//设置页面显示的数据类型
function showDataType(type){
	dataType_search = type ;
}

//获取服务器端图形数据

function getAjaxChartXmlData(){
	$.ajax({
		type : "post",
		dataType: 'json',
		url : "bigflushdisplay_getAjaxTrendChartData.do?1=1",
		data : {
				protocolType:2,
				dataType_trend:dataType_search,//TD标识
				startTime_trend:startTime_search,//开始时间
				endTime_trend:endTime_search,//结束时间
				msisdn_trend:msisdn_trend
		},
		success : function(sjson) {
			if(sjson!=null){		
				//组装图形数据
				buildChartData(sjson);	
			}
			hasinit = true;
		},
		error : function() {
			//alert('查询数据出错!');
		}
	});
}

//初始化图形列表
/*
function initChartData(param){
	var xml =document.getElementById("chartxmldata").value;		
	return xml;
}
*/
//初始化grid工具栏
$(document).ready(function(){
      var toolbar = new Toolbar({
        renderTo : 'toolbar',
        items : [{
          type : 'button',
          text : '导出',
          bodyStyle : 'xls',
          useable : 'T',
          handler : function(){
           //参数
			var params={
				protocolType:2,
				hasinit:hasinit,
				dataType_trend:dataType_search,//TD标识
				startTime_trend:startTime_search,//开始时间
				endTime_trend:endTime_search,//结束时间
				msisdn_trend:msisdn_trend
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName='用户'+msisdn_trend+'流量走势数据';//('+startTime_search+'至'+endTime_search+')';//文件名称
			fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
			fileObj.gridObj = datagrid;//当前grid对象
			fileObj.params = params;
			exportFile(fileObj);
			/*if(changeshow){
				showUnit(showunit);
			}*/
          }
        }]
      });
	  toolbar.render();
});

/**
*
*/
$(document).ready(function(){
	window.onerror = killErrors;
})
function killErrors() {
	return true;
}


</script>


