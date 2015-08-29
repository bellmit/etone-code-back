<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>集团APN空间时间走势</title>
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
		<!-- OFC图表 -->
		<script type="text/javascript" src="/flash/js/swfobject.js"></script>
		<script type="text/javascript" src="/flash/js/json/json2.js"></script>
		<script type="text/javascript" src="/flash/js/BarLineChartUtil.js"></script>
		<script type="text/javascript" src="/flash/js/ParseUtils.js"></script>
		<!-- 自动完成下拉框 -->
		<script type='text/javascript' src='/tools/autocomplete/lib/jquery.bgiframe.min.js'></script>
		<script type='text/javascript' src='/tools/autocomplete/lib/thickbox-compressed.js'></script>
		<script type='text/javascript' src='/tools/autocomplete/jquery.autocomplete.js'></script>
		<link rel="stylesheet" type="text/css" href="/tools/autocomplete/jquery.autocomplete.css" />
	</head>
	<body>
		<table id="maincontent" cellspacing="0" cellpadding="0" border="0"
			bgcolor="#D3E0F2" width="100%">
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
									<td>
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
										</table></td>
									</tr>
									<tr valign="top">
										<td width="100%" class="condition_down">
											<table id="query_condition" cellspacing="0px"
												cellpadding="0px" border="0">
												<tr valign="middle">
																
																					
													<td style="width: 80px;">
														时间粒度：
													</td>
													<td class="condition_value">
														<select name="timeLevel_search" id="timeLevel_search"
															onchange="changeTimeLevel()" style="height: 21px;width: 120px">
															<option value="1" selected>
																小时
															</option>
															<option value="2" >
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
													<td style="width: 100px;">
														时间跨度：
													</td>
													<td  id="time_space_start" style="width: 125px;">
														<input type="text" class="Wdate"
															style="display: block; height: 17px;width: 120px;"
															onclick="selectStartTime()" id="startTime_search"
															name="startTime_search" />
													</td>
													<td style="width: 48px;">
														&nbsp;&nbsp;&nbsp;&nbsp;到&nbsp;&nbsp;
													</td>
													<td class="condition_value" style="width: 120px;" id="time_space_end">
														<input type="text" class="Wdate"
															style="display: block; height: 17px;width: 120px;"
															onclick="selectEndTime()" id="endTime_search"
															name="endTime_search" />
													</td>
													<td width="200px">
														<div id="mainbtn" style="margin-left:-6px">
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
												<tr>
												<td	style="width: 80px;">
														区域：
													</td>													
													<td style="width:400px" colspan="3">	
														<select name="area_type_select" id="area_type_select"
																style="height: 21px; width: 120px;" onchange="changeAreaType()">
																<option value="1">
																	BSC
																</option>
																<option value="2">
																	SGSN
																</option>
																<option value="3">
																	街道办
																</option>
																<option value="4">
																	营销片区
																</option>
																<option value="5">
																	分公司
																</option>
														</select>													
														<select name="area_title" id="area_title"
																style="height: 21px; width: 115px;" >
																<option value="">
																	--请选择--
																</option>
														</select>																										
														<input type="text"
															style="font-size: 11px; width: 120px; height: 16px;"
															value="输入以快速查找" name="tttt" id="tttt"
															size="25" onblur="checkBlur('tttt');"
															onclick="checkFocus('tttt');"/>
													</td>
													<td	style="width: 48px;">
														APN：
													</td>													
													<td style="width:300px" colspan="3">											
														<select name="apn_title" id="apn_title"
																style="height: 21px; width: 125px;" >
																<option value="">
																	--请选择--
																</option>
														</select>																										
														<input type="text"
															style="font-size: 11px; width: 120px; height: 16px;"
															value="输入以快速查找" name="apnt" id="apnt"
															size="25" onblur="checkBlur('apnt');"
															onclick="checkFocus('apnt');"/>
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
												集团APN空间时间走势图形
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
												集团APN空间时间走势列表
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
var date=new Date();


var hasinit = false;//第一次初始化图形数据标识
var datachange = false;//第N次查询初始化数据标识
var dataType_search = 1 ; //页面显示数据类型
var timeLevel_search ='1';//查询时间粒度
date=date.valueOf();
date=date-24*60*60*1000;
date=new Date(date); 
var yesterday=date.getYear()+'-'+''+((date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1)))+'-'+''+(date.getDate()>9?date.getDate():("0"+date.getDate())); //查询开始时间
var endTime_search =yesterday+" 23:00";//查询结束时间

var startTime_search=yesterday+" 00:00"//查询开始时间

	
var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :[
		{name : 'fullDate'        },
		{name : 'timeLevel'        },
		{name : 'upFlushMB'        },
		{name : 'downFlushMB'     },
		{name : 'totalFlushMB'     },
		{name : 'upFlushKB'    },
		{name : 'downFlushKB'   },
		{name : 'totalFlushKB'    },
		{name : 'upFlushGB'    },
		{name : 'downFlushGB'   },
		{name : 'totalFlushGB'   },
		{name : 'intImsis'   },
				{name : 'nmAveFlushKB'   },
		{name : 'detail' }
	]
};

function renderInit(value ,record,columnObj,grid,colNo,rowNo){	
}

var colsConfig = [
		{ id : 'fullDate'      , header : "时间"   },
	//	{ id : 'timeLevel'      , header : "时间粒度", hidden:true},
		{ id : 'upFlushMB'    , header : "上行流量(MB)" ,headAlign:'right' ,align:'right' ,exportnumber:true,renderer:renderFormatDataInit2},
		{ id : 'downFlushMB'     , header : "下行流量(MB)" ,headAlign:'right' ,align:'right' ,exportnumber:true ,renderer:renderFormatDataInit2},
		{ id : 'totalFlushMB'  , header : "总流量(MB)",headAlign:'right' ,align:'right' ,exportnumber:true,renderer:renderFormatDataInit2},
		{ id : 'upFlushKB'    , header : "上行流量(KB)",headAlign:'right' ,align:'right' ,exportnumber:true ,renderer:renderFormatDataInit2 },
		{ id : 'downFlushKB'     , header : "下行流量(KB)",headAlign:'right' ,align:'right' ,exportnumber:true ,renderer:renderFormatDataInit2  },
		{ id : 'totalFlushKB'  , header : "总流量(KB)",headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2},
		{ id : 'upFlushGB'    , header : "上行流量(GB)",headAlign:'right' ,align:'right' ,exportnumber:true ,renderer:renderFormatDataInit2 },
		{ id : 'downFlushGB'     , header : "下行流量(GB)",headAlign:'right' ,align:'right' ,exportnumber:true  ,renderer:renderFormatDataInit2 },
		{ id : 'totalFlushGB'  , header : "总流量(GB)",headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2},
		{ id : 'intImsis' , header : "用户数",headAlign:'right' ,align:'right'  ,exportnumber:true},
		{ id : 'nmAveFlushKB'  , header : "平均流量(KB/人)",headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 }
		
		/*{ id : 'detail'   , header : "操作" , sortable:false, width:160,exportable:false,
			renderer:renderInit
		}*/
];

var gridConfig={
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/groupbusinessareadistribute_queryTrend.do?1=1',
	exportURL :'/groupbusinessareadistribute_exportTrend.do?1=1' ,
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
var showunit = 'KB';
var firstInit=true;
//gird回调函数
function loadComplate(){
	var  obj = new Object ();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.hideColumn = 5 ;//隐藏的列数目
	obj.isCheckbox =false;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度
	obj.otherWidth =0;

	
	//判断是否查询到数据
	judgeData(datagrid);
	//选择呈现单位 
	if(!initHasData){
		sortInit = true;
	}
	

	if(sortInit && initHasData){
		showunit = buildUnit();
		sortInit = false;
	}
	
	//初始化grid
	initGridInfo(obj);
/*	*/
	if(firstInit==true){
		//var pageSize=getDispalyPageSize(1,1);
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

///////////////////图表////////////////////////

/*图表是否已经初始化*/
var chartinit = false;
/*组装图形数据*/
function buildChartData(json){

	var title = startTime_search+"至"+endTime_search+" 流量|用户数走势图";
	
	var xlabels = [] ;
	var datas1 = [] ;
	var datas2 = [] ;
	
	if(json!=''&&json.length>0){
		for(var i = 0 ; i<json.length;i++){	
			var record = json[i];	 	
		 	xlabels[i] = record.fullDate; 	
			datas1[i] = StringToFloat(record.totalFlushKB);
			datas2[i] = StringToFloat(record.intImsis);			
		}
	}else{
		var str1 = JSON.stringify(buildEmptyAxisChart());
		if(!chartinit){
		chartinit = true;
		document.getElementById("chartxmldata").value=str1;
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=flush", "imgarea1", "99%", "99%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
	}else{
	  	var tmp1 = findSWF("imgarea1");	  	
  		tmp1.load(str1);	
	}
		return;
	}
	var showxlabels = xlabels;
	//格式化X轴时间
	switch(timeLevel_search)
	   {
	   case '1':
	   	 xlabels = formatXDateLables(xlabels,12,"hour");
	     break;
	   case '2':
	   	 xlabels = formatXDateLables(xlabels,15,"day");
	     break;
	   case '3':
	     xlabels = formatXDateLables(xlabels,12,"week");
	      if(json!=''&&json.length>0){
			title =  json[0].fullDate.split('~')[0]+"至"+json[json.length-1].fullDate.split('~')[1]+" 流量|用户数走势图";		
		 }
	     break;
	   case '4':
	   	 xlabels = formatXDateLables(xlabels,12,"month");
	     break;
	 }
	 var tdatas = [];
	 
	  //格式化Y轴数据（流量）
	var lobj = formatDataBy1024(datas1,"KB");
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
///////////////////////////////////////////
var apnName = "SZPSJL.GD";
var areaType="1";
var areaName = "SZ27B";

//grid查询
function query() {
	//重置分页数据
	datagrid.setTotalRowNum(-1);	
	//查询条件
	timeLevel_search = $("#timeLevel_search option:selected").val();
	startTime_search = $('#startTime_search').attr("value");
	endTime_search = $('#endTime_search').attr("value");	//设置图形标志
	apnName = $("#apn_title").attr("value");
	areaType = $("#area_type_select option:selected").val()
	areaName = $("#area_title").attr("value");
	if(startTime_search=='' || endTime_search==''){
		alert('请选择时间跨度!');
		return ;
	}
	hasinit = false;
	datachange = true;
	//传递给后台参数
	var param={
		hasinit:hasinit,
		dataType_trend:dataType_search,//TD标识
		timeLevel_trend:timeLevel_search,//时间粒度  1:小时 2:天 3:周 4:月
		startTime_trend:startTime_search,//开始时间
		endTime_trend:endTime_search,//结束时间
		apnName_trend:apnName,
		areaType_trend:areaType,
		areaName_trend:areaName
	};
	GT.$grid('datagrid').query( param );
}

//设置页面显示的数据类型
function showDataType(type){
	dataType_search = type ;
}

//时间改变事件
function changeTimeLevel(){
	$('#startTime_search').attr("value","");
	$('#endTime_search').attr("value","");
}

//初始化查询时间
$(document).ready(function(){
	$('#startTime_search').attr('value',startTime_search);
	$('#endTime_search').attr('value',endTime_search);
});
//时间选择事件
function selectStartTime(){
	var ts = $("#timeLevel_search option:selected").val();
	switch(ts)
	   {
	   case '1':
	   	 var a = new WdatePicker({maxDate:'#F{$dp.$D(\'endTime_search\')}',dateFmt:'yyyy-MM-dd HH:00'});
	     break;
	   case '2':
	   	 new WdatePicker({maxDate:'#F{$dp.$D(\'endTime_search\')}',dateFmt:'yyyy-MM-dd'});
	     break;
	   case '3':
	     new WdatePicker({maxDate:'#F{$dp.$D(\'endTime_search\')}',dateFmt:'yyyy-MM-dd'});
	     break;
	   case '4':
	   	  new WdatePicker({maxDate:'#F{$dp.$D(\'endTime_search\')}',dateFmt:'yyyy-MM'});
	     break;
	 }
}
function selectEndTime(){
	var ts = $("#timeLevel_search option:selected").val();
	switch(ts)
	   {
	   case '1':
	   	 new WdatePicker({minDate:'#F{$dp.$D(\'startTime_search\')}',dateFmt:'yyyy-MM-dd HH:00:00'});
	     break;
	   case '2':
	   	 new WdatePicker({minDate:'#F{$dp.$D(\'startTime_search\')}',dateFmt:'yyyy-MM-dd'});
	     break;
	   case '3':
	     new WdatePicker({minDate:'#F{$dp.$D(\'startTime_search\')}',dateFmt:'yyyy-MM-dd'});
	     break;
	   case '4':
	   	 new WdatePicker({minDate:'#F{$dp.$D(\'startTime_search\')}',dateFmt:'yyyy-MM'});
	     break;
	 }
}
//重置查询条件
function reset(){
	$('#dataType_search_td_id input:first').attr("checked","true");//设置radio默认值
	$("#timeLevel_search").get(0).selectedIndex=0;
	$('#startTime_search').attr("value","");
	$('#endTime_search').attr("value","");
}

//获取服务器端图形数据

function getAjaxChartXmlData(){
	$.ajax({
		type : "post",
		dataType: 'json',
		url : "groupbusinessareadistribute_getAjaxTrendChartData.do?1=1",
		data : {
				dataType_trend:dataType_search,//TD标识
				timeLevel_trend:timeLevel_search,//时间粒度  1:小时 2:天 3:周 4:月
				startTime_trend:startTime_search,//开始时间
				endTime_trend:endTime_search,//结束时间
				apnName_trend:apnName,
				areaType_trend:areaType,
				areaName_trend:areaName
		},
		success : function(sjson) {
			//if(sjson!=null){		
				//组装图形数据
				buildChartData(sjson);	
			//}
			hasinit = true;
		},
		error : function() {
			//alert('查询数据出错!');
		}
	});
}
//初始化grid工具栏
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
				hasinit:hasinit,
				dataType_trend:dataType_search,//TD标识
				timeLevel_trend:timeLevel_search,//时间粒度  1:小时 2:天 3:周 4:月
				startTime_trend:startTime_search,//开始时间
				endTime_trend:endTime_search,//结束时间
				apnName_trend:apnName,
				areaType_trend:areaType,
				areaName_trend:areaName
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName='集团APN-'+areaName+'区域'+areaName+'时间走势数据';//('+startTime_search+'至'+endTime_search+')';//文件名称
			fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
			fileObj.gridObj = datagrid;//当前grid对象
			fileObj.params = params;
			exportFile(fileObj);			
          }
        }]
      });
	  toolbar.render();
});


var areaids=[];	
var areanames=[];
var areas=[];
var apnss = [];
$(document).ready(function(){
	//所有区域
	$.ajax({
			type:"post", 
			url:"terminalDialog_initAreaDialogData.do", 
			dataType:"xml", 
			data:{
				
			}, 
			success:function (xml) {	
				var i = 0;				
				$(xml).find("data").each(function (i) {
				var brandId = $(this).children("brandId").text();
				var brandName = $(this).children("brandName").text();
				var modelId = $(this).children("modelId").text();
				var modelName = $(this).children("modelName").text();
				areaids[brandId] = modelId;
				areanames[brandId] = modelName;
				i++;
				});
				initArea(1);
			}, error:function () {
				alert("获取区域信息失败!");
			}
		});	
	$.ajax({
			type:"post", 
			url:"groupbusinessuserbelong_getAllApnName.do", 
			data:{			
			}, 
			success:function (message) {
				if (message != null && message != "") {
					var ts = message.split(":");
					var cntt = 0;
					if (ts.length > 0) {
						for (var i = 0; i < ts.length; i++) {
							if('CMTDS,CMLAP,TDHOME,CMWAP,CMNET,其他'.indexOf(ts[i])==-1){					
								apnss[cntt++] = {name:ts[i],id:ts[i]};
							}
						}
					}
				}
				initApn();
			}, error:function () {
				alert("获取APN信息失败!");
			}
		});		
});

 function initArea(index){
 	areas = [];
 //	alert(areaids[index]+"---"+index);
	var aids = areaids[index].split("\,");
	var anames = areanames[index].split("\,");
	$("#area_title option").each(function () {
			$(this).remove();
	});
	for(var i=0;i<aids.length;i++){
		if(anames[i].indexOf(' ')!=-1){
			anames[i] = anames[i].substr(1,anames[i].length);
		}
		if(anames[i]=='SZ27B'){
			$("#area_title").append("<option value='" +anames[i] + "' selected>" + anames[i] + "</option>");	
		}else{
			$("#area_title").append("<option value='" +anames[i] + "'>" + anames[i] + "</option>");	
		}
		areas[i] = {name:anames[i],id:anames[i]}
	}
	resetautocomplete(areas,'tttt','area_title',120);
}
function changeAreaType(){
	var si = $("#area_type_select").val();
	initArea(si);
}
function initApn(){
	$("#apn_title option").each(function () {
			$(this).remove();
	});
	for(var i=0;i<apnss.length;i++){
		if(apnss[i].name=='SZPSJL.GD'){
			$("#apn_title").append("<option value='" +apnss[i].name + "' selected>" + apnss[i].name + "</option>");	
		}else{
			$("#apn_title").append("<option value='" +apnss[i].name + "'>" + apnss[i].name + "</option>");	
		}
	}
	resetautocomplete(apnss,'apnt','apn_title',120);
}

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


