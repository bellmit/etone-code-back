<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>传输协议分布</title>
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
		<script type="text/javascript" src="/flash/js/PieChartUtil.js"></script>
		<script type="text/javascript" src="/flash/js/ParseUtils.js"></script>
		<script type="text/javascript" src="/flash/js/PercentBarChartUtils.js"></script>
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
															onclick='showDataType(1)' value="1" checked />
														GPRS
														<input type="radio" name="dataTypeRadio" value="2"
															style="width: 20px; border: 0px;"
															onclick='showDataType(2)' />
														TD
													</td>
													<td class="condition_name">
														时间粒度：
													</td>
													<td class="condition_value">
														<select name="timeLevel_search" id="timeLevel_search"
															onchange="changeTimeLevel()" style="height: 21px">
															<option value="1">
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
														时间：
													</td>
													<td class="condition_value">
														<input type="text" class="Wdate"
															style="display: block; height: 17px;"
															onclick="selectTime()" id="time_search" />
													</td>
													<td width="300px">
														<div id="mainbtn" style="width:300px">
															<ul>
																<li>
																	<a href="javascript:query();" 　title="查询"><span>查询</span>
																	</a>
																</li>
																<li>
																	<a href="javascript:parent.openTab('/mantoeye/data_trend/tcp_trend.jsp','FLUSH_TCP_DATE','传输协议时间走势');" 
																		style="width:90px;"  title="时间走势"><span><img onclick="parent.openTab('/mantoeye/data_trend/tcp_trend.jsp','FLUSH_TCP_DATE','传输协议时间走势');" 
																		src="/skin/Default/images/MantoEye/btn/trend.png" alt="" />时间走势</span>
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
												传输协议分布图形
											</div>
											<input type="hidden" value="" id="chartxmldata1" />
											<input type="hidden" value="" id="chartxmldata2" />
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
							<div id="imgarea1" style="width: 0%; height: 0%"></div>
							<div id="imgarea2" style="width: 0%; height: 0%"></div>
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
												传输协议分布列表
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
var initChartAgain = false;//判断是否再次初始化图形
var dataType_search = 1 ; //页面显示数据类型
var timeLevel_search =parent.total_obj.total_time_level==null?'2':parent.total_obj.total_time_level;//查询时间粒度
var defaulettime =date.getYear()+'-'+''+((date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1)))+'-'+''+(date.getDate()>9?date.getDate():("0"+date.getDate())); //查询开始时间
var time_search = parent.total_obj.total_time_search==null?defaulettime:parent.total_obj.total_time_search;
var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :[
		{name : 'totalFlushMB'        },
		{name : 'intImsis'        },
		{name : 'fullDate'        },
		{name : 'businessName'      },
		{name : 'upFlushMB'     },
		{name : 'downFlushMB'   },
		{name : 'totalFlushMB'   },
		{name : 'upFlushKB'    },
		{name : 'downFlushKB'   },
		{name : 'totalFlushKB'    },
		{name : 'upFlushGB'    },
		{name : 'downFlushGB'   },
		{name : 'totalFlushGB'   },
		{name : 'nmAveFlushKB'   },
		{name : 'intImsis'}
	]
};
var colsConfig = [
		{ id : 'fullDate'      , header : "时间"    ,sortable:false   },
		{ id : 'businessName'      , header : "传输协议"    ,sortable:false   },
		{ id : 'upFlushMB'    , header : "上行流量(MB)"   ,headAlign:'right' ,align:'right',exportnumber:true ,renderer:renderFormatDataInit2 },
		{ id : 'downFlushMB'     , header : "下行流量(MB)"    ,headAlign:'right' ,align:'right' ,exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'totalFlushMB'  , header : "总流量(MB)"  ,headAlign:'right' ,align:'right' ,exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'upFlushKB'    , header : "上行流量(KB)",headAlign:'right' ,align:'right' ,exportnumber:true  ,renderer:renderFormatDataInit2 },
		{ id : 'downFlushKB'     , header : "下行流量(KB)",headAlign:'right' ,align:'right' ,exportnumber:true  ,renderer:renderFormatDataInit2  },
		{ id : 'totalFlushKB'  , header : "总流量(KB)",headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'upFlushGB'    , header : "上行流量(GB)",headAlign:'right' ,align:'right' ,exportnumber:true ,renderer:renderFormatDataInit2  },
		{ id : 'downFlushGB'     , header : "下行流量(GB)",headAlign:'right' ,align:'right' ,exportnumber:true ,renderer:renderFormatDataInit2   },
		{ id : 'totalFlushGB'  , header : "总流量(GB)",headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'intImsis' , header : "用户数"    ,headAlign:'right' ,align:'right',exportnumber:true},
		{ id : 'nmAveFlushKB'  , header : "平均流量(KB/人)",headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 }
		
];

var gridConfig={
	id : "datagrid",
	loadURL :'/protocolDistribute_query.do?protocolType=2&total_time_level='+parent.total_obj.total_time_level+'&total_time_search='+parent.total_obj.total_time_search,
	exportURL :'/protocolDistribute_export.do?1=1' ,
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
	pageSize : 10 ,
	remoteSort : false ,
	pageSizeList : [10,20,50,100],
//	defaultRecord :  { no : 111 , name :'', age : '', gender : 'U' , english : 50 , math :50 },
//	customHead : 'myHead',
	onComplete:loadComplate
};

var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
} );


//grid回调函数
function loadComplate(){
	var  obj = new Object ();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.hideColumn = 6 ;//隐藏的列数目
	obj.isCheckbox = false;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度
	//判断是否查询到数据
	judgeData(datagrid);
	if(!initHasData){
		sortInit = true;
	}
	
	//选择呈现单位 
	if(sortInit && initHasData){
		 buildUnit();
		sortInit = false;
	}
	//初始化grid
	initGridInfo(obj);
	
	//组装图形数据
	buildChartData(datagrid.getAllRows().length);
	
}

///////////////////图表////////////////////////

/*图表是否已经初始化*/
var chartinit = false;
/*组装图形数据*/
function buildChartData(totalRecords){
	var chartTime ='';
	var text1 = '';
	//如果有单位  则label与单位间用@@分开
	var label1 = '流量@@MB';
	//var text2 = '';
	//var label2 = '用户数';
	var datas1 = [] ;
	//var datas2 = [] ;
	var showlabel="";
	
	var text2 = '';
	var xlabels2 = [] ;
		var label2 = '占比@@%';
	var label22 = '用户数';
	var showxdatas = [];
	var showxlabels = [];
	var datas2 = [] ;
	
	
	if(typeof(datagrid.getRecord(0))!="undefined"){
		for(var i = 0 ; i<totalRecords;i++){	
			var record = datagrid.getRecord(i);	 	
		 	if(text1==''){
		 		chartTime = record.fullDate;
		 		text1 = chartTime+' 流量占比图';
		 		text2 = chartTime+' 用户数占比图';
		 	}	
		 	//if(i<10){ 	
			datas1[i] = {"label":record.businessName,"value":StringToFloat(record.totalFlushMB)};
			//datas2[i] = {"label":record.businessName,"value":StringToFloat(record.intImsis)};
			//}
			datas2[i] = record.imsisRate;
			xlabels2[i] = record.businessName;	
			showxlabels[i]=record.businessName;	
		showxdatas [i] =record.intImsis;			
		}
		datas2 = buildHBarChartTip(datas2,showxlabels,label2,showxdatas,label22);
	
	var jsons1 = buildPieChartData(text1,label1,datas1);
	var str1 = JSON.stringify(jsons1);
	//var jsons2 = buildPieChartData(text2,label2,datas2);
	var jsons2 = buildPercentChartData(text2,[label2],xlabels2,[datas2]);
	var str2 = JSON.stringify(jsons2);
	if(!chartinit){
		chartinit = true;
		document.getElementById("chartxmldata1").value=str1;
		document.getElementById("chartxmldata2").value=str2;
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=flush", "imgarea1", "40%", "100%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=imsis", "imgarea2", "59%", "100%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
	}else{
	  	var tmp1 = findSWF("imgarea1");
	  	var tmp2 = findSWF("imgarea2");
  		tmp1.load(str1);
  		tmp2.load(str2);
	}
	}else{
		chartinit = false;
		var str1 = JSON.stringify(buildEmptyAxisChart());
		document.getElementById("chartxmldata1").value=str1;
		document.getElementById("chartxmldata2").value=str1;
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=imsis", "imgarea2", "0%", "0%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=flush", "imgarea1", "100%", "100%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
		return;
	}
}
/*初始化图表*/
function open_flash_chart_data(id)
{
	if(id=='flush'){
		return document.getElementById("chartxmldata1").value;
	}else{
		return document.getElementById("chartxmldata2").value;
	}
}
/*图表回调函数*/
function callback(label){
	//alert("label:"+label);
}

///////////////////////////////////////////


//grid查询
function query() {
	//点击查询保证不再是从全网跳转过来
	parent.total_obj.total_time_level = null;
	parent.total_obj.total_time_search = null;
	timeLevel_search = $("#timeLevel_search option:selected").val();
	datagrid.loadURL ='/protocolDistribute_query.do?protocolType=2&total_time_level='+parent.total_obj.total_time_level+'&total_time_search='+parent.total_obj.total_time_search;
	initChartAgain = true;
	time_search = $('#time_search').attr("value");
	if(time_search=='' ){
		alert('请选择时间!');
		return ;
	}
	var param={
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
	var tls = $("#timeLevel_search option:selected").val();
	switch(tls)
	   {
	   case '1':
	   	 new WdatePicker({dateFmt:'yyyy-MM-dd HH:00:00'});
	     break;
	   case '2':
	   	 new WdatePicker({dateFmt:'yyyy-MM-dd'});
	     break;
	   case '3':
	   	 new WdatePicker({dateFmt:'yyyy-MM-dd'});
	     break;
	   case '4':
	   	 new WdatePicker({dateFmt:'yyyy-MM'});
	     break;
	 }
}
function testccc(){
	var s11 = datagrid.getColumnValue("totalFlushGB",0);
	var s12 = datagrid.getColumnValue("totalFlushGB",1);
	var s13 = datagrid.getColumnValue("totalFlushGB",2);
	var s14 = datagrid.getColumnValue("totalFlushGB",5);
	datagrid.setColumnValue("totalFlushGB",0,123.44);
	datagrid.refresh();
}
//重置查询条件
function reset(){
	$('#dataType_search_td_id input:first').attr("checked","true");//设置radio默认值
	$("#timeLevel_search").get(0).selectedIndex=1;
	$('#time_search').attr("value","");
}

//初始化查询时间
$(document).ready(function(){
	if(parent.total_obj.total_time_search!=null){
		var total_select_index = 0 ;
		$('#timeLevel_search option').each(function (i){
			if($(this).val()==parent.total_obj.total_time_level){
				total_select_index = i ;
			}
		});
		$("#timeLevel_search").get(0).selectedIndex=total_select_index;
		$('#time_search').attr('value',parent.total_obj.total_time_search);//从全网跳转过来打开
	}else{
		$('#time_search').attr('value',time_search);//从菜单中打开tab
	}
});
/*****************查询条件结束**********************/


/*****************图形****************************/
/*
function initChartData(param){
	return document.getElementById("chartxmldata").value;			
}
function changData(){
	var xml = document.getElementById("chartxmldata").value;
	document.frm.LineColumnChart.jsChangeData(xml);
}
function initChartWidth(){
 	var width=window.screen.availWidth;
	var availW=1014;
	var availH=230;
	if(width==1024){
		availW=760;
	}else{
		availW=1014;
	}
 	return availW+"|230";
}
function charItemClick(label){
}
*/
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
				protocolType:2,
				dataType_search:dataType_search,
				timeLevel_search:timeLevel_search,
				time_search:time_search
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName='传输协议数据';
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

$(document).ready(function(){
	window.onerror = killErrors;
})
function killErrors() {
	return true;
}
*/


</script>



