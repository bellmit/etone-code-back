<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>SP彩信按流量分档统计</title>
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
												cellpadding="0px;boder="0">
												<tr valign="middle">
													<td class="condition_value" id="dataType_search_td_id"
														style="display: none">
														<input type="radio" name="dataTypeRadio"
															style="width: 20px; border: 0px;"
															onclick='showDataType(0)' value="1" checked />
														GPRS
														<input type="radio" name="dataTypeRadio" value="2"
															style="width: 20px; border: 0px;"
															onclick='showDataType(1)' />
														TD
													</td>
													<td class="condition_name">
														时间粒度：
													</td>
													<td class="condition_value">
														<select name="timeLevel_search" id="timeLevel_search"
															onchange="changeTimeLevel()" disabled style="height: 21px">
															<option value="2" selected>
																天
															</option>
														</select>
													</td>
													<td class="condition_name">
														时间：
													</td>
													<td class="condition_value">
														<input type="text" class="Wdate"
															style="display: block; height: 17px;"
															onclick="selectTime()" id="time_search" />
													</td>
													<td width="*"></td>
													<td width="200px">
														<div id="mainbtn">
															<ul>
																<li>
																	<a href="javascript:query();" 　title="查询"><span>查询</span>
																	</a>
																</li>
																<!--  
																<li>
																	<a href="javascript:reset();" title="重置"><span>重置</span>
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
												SP彩信按流量分档统计图形
											</div>
											<input type="hidden" value="" id="chartxmldata1" />
										</td>
										<td width="4px">
											<div class="righttitle"></div>
										</td>
									</tr>
								</table>
							</td>
						</tr>

						<tr>
							<td height="230px" width="100%"
								style="padding: 15px 20px; background: #ffffff">
								<div id="imgareas" style="width: 100%; height: 100%;border: 2px solid #008BD1;">
									<div id="imgarea1" style="width: 100%; height: 100%"></div>
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
												SP彩信按流量分档统计列表
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
var hasinit=false;//第一次是否初始化图形
var initChartAgain = false;//判断是否再次初始化图形
var dataType_search = 1 ; //页面显示数据类型
var timeLevel_search ='2';//查询时间粒度
var time_search =date.getYear()+'-'+((date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1)))+'-'+(date.getDate()>9?date.getDate():("0"+date.getDate())) ;//查询开始时间

var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :[
		{name : 'fullDate'        },
		{name : 'businessName'      },
		{name : 'totalSendFlush'}
	]
};
var colsConfig = [
		{ id : 'fullDate'      , header : "时间" ,sortable:false},
		{ id : 'businessName'      , header : "分档" },
		{ id : 'totalSendFlush' , header : "发送量(条)" ,headAlign:'right' ,align:'right',exportnumber:true }
];

var gridConfig={
	id : "datagrid",
	loadURL :'/spMmsFlushLevel_query.do',
	exportURL :'/spMmsFlushLevel_export.do?1=1' ,
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
	toolbarContent : 'state' ,
	pageSize : 50 ,
	remoteSort : false ,
	pageSizeList : [10,20,50,100],
	//defaultRecord :  { no : 111 , name :'', age : '', gender : 'U' , english : 50 , math :50 },
	onComplete:loadComplate
};

var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
} );

//grid回调函数
function loadComplate(){
	
	//判断是否查询到数据
	judgeData(datagrid);
	
	//初始化grid
	//if(!columninit){
		var  obj = new Object ();//使用对象传参,以防以后修改
		obj.datagrid = datagrid;
		obj.hideColumn = 0 ;//隐藏的列数目
		obj.isCheckbox = false;//是否可选择
		obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度
	//	initGridInfo(obj);
	//}
	//组装图形数据
	buildChartData(datagrid.getAllRows().length);
	//初始化grid(不设置行宽度)
	initGridInfoExColumn(obj);
	
	var width=window.screen.availWidth;
	var ttotalwidth;
	if (width == 1024) {
		ttotalwidth = 800;
	//重新设置宽度11
	datagrid.getColumn('fullDate').setWidth(300);
	datagrid.getColumn('businessName').setWidth(200);
	datagrid.getColumn('totalSendFlush').setWidth(300);
	
	} else {	
		ttotalwidth = 1056;
		//重新设置宽度11
	datagrid.getColumn('fullDate').setWidth(350);
	datagrid.getColumn('businessName').setWidth(350);
	datagrid.getColumn('totalSendFlush').setWidth(350);

	}
	
	
}
/*
//组装图形数据
function packChartData(totalRecords){
	var chartxmldata = '';
	var chartTime = $('#time_search').attr('value');
	chartxmldata = '<root><chart id="1" name="'+chartTime+' SP彩信按流量分档统计分布图" fields="发送量|">';
	var chartBody = '';
	if(totalRecords>1){
		for(var i = 0 ; i<totalRecords;i++){
			var record = datagrid.getRecord(i);
			chartBody=chartBody+'<data label="'+record.businessName+'" shortlabel="'+record.businessName+'" total="'+record.totalSendFlush+'"/>';
		}
		chartxmldata = chartxmldata + chartBody;
	}
	chartxmldata=chartxmldata+'</chart></root>';
	document.getElementById("chartxmldata").value=chartxmldata;
	//判断是否再次初始化图形数据
	if(initChartAgain){
		document.frm.BaseColumnChart.jsChangeData(chartxmldata);
		initChartAgain = false;
	}
	//组装图形数据
	
}*/
///////////////////图表////////////////////////

/*图表是否已经初始化*/
var chartinit = false;
/*组装图形数据*/
function buildChartData(totalRecords){

	var title1 =  time_search  + " 统计分布图";

	var xlabels = [] ;
	var datas1 = [] ;//	

	
	if(typeof(datagrid.getRecord(0))!="undefined"){
		for(var i = 0 ; i<totalRecords;i++){	
			var record = datagrid.getRecord(i);	
			//if(i<10){//图形只显示前10 的数据
				xlabels[i] = record.businessName; 	
				datas1[i] = StringToFloat(record.totalSendFlush);
			//} 			
		}
	
	
	var tdatas1 = [];
	var showxlabels = xlabels;
	
	
	  //格式化Y轴数据（流量）
	var lobj = formatNumberByWan(datas1);
	var lunit="发送量";
	if(lobj.unit!=""){
		lunit = lunit +"@@"+lobj.unit;
	}
	tdatas1[0] = lobj.datas;
	
	 //如果有单位  则label与单位间用@@分开
	var labels1 = [lunit];	
	tdatas1[0] = buildBarChartTip(lobj.datas,showxlabels,lunit);
	
	var jsons1 = buildSingleChartData(title1,labels1,xlabels,tdatas1);
	var str1 = JSON.stringify(jsons1);
	
	
	//alert(str3);
	if(!chartinit){
		chartinit = true;
		document.getElementById("chartxmldata1").value=str1;
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=send", "imgarea1", "100%", "100%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
	}else{
	  	var tmp1 = findSWF("imgarea1");	 
	  	tmp1.load(str1);	
	}
	}else{
		var str1 = JSON.stringify(buildEmptyAxisChart());
		if(!chartinit){
		chartinit = true;
		document.getElementById("chartxmldata1").value=str1;
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=send", "imgarea1", "100%", "100%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
	}else{
	  	var tmp1 = findSWF("imgarea1");	 
	  	tmp1.load(str1);	
	}
		return;
	}
	
}
/*初始化图表*/
function open_flash_chart_data(id)
{
	return document.getElementById("chartxmldata1").value;
}
/*图表回调函数*/
function callback(label){
	//alert("label:"+label);
}
///////////////////////////////////////////
//grid查询
function query() {
	//重置分页数据
	datagrid.setTotalRowNum(-1);

	initChartAgain = true;
	time_search = $('#time_search').attr("value");
	if(time_search=='' ){
		alert('请选择时间!');
		return ;
	}
	var param={
		hasinit:hasinit,
		dataType_search:dataType_search,
		timeLevel_search:timeLevel_search,
		time_search:time_search
	};
	GT.$grid('datagrid').query( param );
}

/*****************查询条件**********************/
//设置页面显示的数据类型
function showDataType(type){
	dataType_search = type ;
}
//时间改变事件
function changeTimeLevel(){
	$('#time_search').attr("value","");
}
//时间选择事件
function selectTime(){
	new WdatePicker({dateFmt:'yyyy-MM-dd'});
}
//重置查询条件
function reset(){
	$('#dataType_search_td_id input:first').attr("checked","true");//设置radio默认值
	$("#timeLevel_search").get(0).selectedIndex=0;
	$('#time_search').attr("value","");
}

//初始化查询时间
$(document).ready(function(){
	$('#time_search').attr('value',time_search);
});
/*****************查询条件结束**********************/


/*****************图形****************************/
/*
function initChartData(param){
	return document.getElementById("chartxmldata").value;			
}

function initChartWidth(){
 	var width=window.screen.availWidth;
	var availW=1014;
	var availH=230;
	if(width==1024){
		availW=765;
	}else{
		availW=1014;
	}
 	return availW+"|230";
}
function charItemClick(label){
}*/
/*****************图形结束****************************/

/***************初始化toolbar***************************/
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
				timeLevel_search:timeLevel_search,
				time_search:time_search 
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName='SP彩信流量分档数据';
			fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
			fileObj.gridObj = datagrid;//当前grid对象
			fileObj.params = params;
			exportFile(fileObj);
          }
        }]
      });
	  toolbar.render();
    });
/***************初始化toolbar结束***************************/


/**
脚本不出错
*/
$(document).ready(function(){
	window.onerror = killErrors;
})
function killErrors() {
	return true;
}



</script>



