<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>空间时间走势</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" type="text/css"
			href="/tools/gt-grid/gt_grid.css" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/maincontent.css" />
		<link type="text/css" rel="stylesheet" href="/css/dialog.css" />
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
			type="text/css"></link>
		<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
		<!-- 列表工具栏 -->
		<script type="text/javascript"
			src="/js/jquery/extend.tab/js/Toolbar.js"></script>
		<script type="text/javascript" src="/js/common_grid.js"></script>
		<!-- OFC图表 -->
		<script type="text/javascript" src="/flash/js/swfobject.js"></script>
		<script type="text/javascript" src="/flash/js/json/json2.js"></script>
		<script type="text/javascript" src="/flash/js/BarLineChartUtil.js"></script>
		<script type="text/javascript" src="/flash/js/ParseUtils.js"></script>
		<!-- 自动完成下拉框 -->
		<script type='text/javascript'
			src='/tools/autocomplete/lib/jquery.bgiframe.min.js'></script>
		<script type='text/javascript'
			src='/tools/autocomplete/lib/thickbox-compressed.js'></script>
		<script type='text/javascript'
			src='/tools/autocomplete/jquery.autocomplete.js'></script>
		<link rel="stylesheet" type="text/css"
			href="/tools/autocomplete/jquery.autocomplete.css" />
	</head>
	<body>
		<table id="maincontent" cellspacing="0" cellpadding="0" border="0"
			bgcolor="#D3E0F2" width="100%" style="height: 100%;">
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
										<td>
											<table cellspacing="0" cellpadding="0" border="0"
												width="100%">
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
										</td>
									</tr>
									<tr valign="top">
										<td width="100%" class="condition_down">
											<table id="query_condition" cellspacing="0px"
												cellpadding="0px" border="0">
												<tr valign="middle">
													<td class="condition_name">
														时间粒度：
													</td>
													<td class="condition_value">
														<select name="timeLevel" id="timeLevel"
															onchange="changeTimeLevel()"
															style="height: 21px; width: 125px">
															<option value="1" selected>
																小时
															</option>
															<option value="2">
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
														时间跨度:
													</td>
													<td class="condition_value" id="dateStartId">
														<input type="text" class="Wdate"
															style="display: block; height: 17px;"
															onclick="getStartDate()" id="queryDateStart"
															name="queryDateStart" />
													</td>
													<td style="width: 48px;">
														&nbsp;&nbsp;&nbsp;&nbsp;到&nbsp;&nbsp;
													</td>
													<td class="condition_value" id="dateEndId">
														<input type="text" class="Wdate"
															style="display: block; height: 17px;"
															onclick="getEndDate()" id="queryDateEnd"
															name="queryDateEnd" />
													</td>
													<td width="200px">
														<div id="mainbtn" style="margin-left: -6px">
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
													<td class="condition_name">
														区域：
													</td>
													<td style="width: 450px" colspan="4">
														<select name="area_type_select" id="area_type_select"
															style="height: 21px; width: 125px;"
															onchange="changeAreaType()">
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
															style="height: 21px; width: 110px;">
															<option value="">
																--请选择--
															</option>
														</select>
														<input type="text"
															style="font-size: 11px; width: 150px; height: 16px;"
															value="输入以快速查找" name="tttt" id="tttt" size="25"
															onblur="checkBlur('tttt');" onclick="checkFocus('tttt');" />
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
											<div class="middletitle" id="headTitle">
												空间时间走势图形
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
												空间时间走势列表
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
<script>

var date=new Date();
date=date.valueOf();
date=date-24*60*60*1000;
date=new Date(date); 
var initTime =date.getYear()+'-'+''+((date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1)))+'-'+''+(date.getDate()>9?date.getDate():("0"+date.getDate())); //查询开始时间
var timeLevel = '1';
var queryDateStart=initTime+" 00:00";
var queryDateEnd = initTime+" 23:00";
var areaType='1';
var areaName = "SZ27B";
var areaValue='SZ27B';
var queryYear='';
var queryMonth='';
var queryDay='';
var queryWeek='';
var queryHour='';


var dsConfig= {
	uniqueField : 'no' ,
	fields :[
		{name : 'fullDate'        },
		{name : 'upFlushMB'         },
		{name : 'downFlushMB'      },
		{name : 'totalFlushMB'      },
		{name : 'upFlushKB'    },
		{name : 'downFlushKB'   },
		{name : 'totalFlushKB'    },
		{name : 'upFlushGB'    },
		{name : 'downFlushGB'   },
		{name : 'totalFlushGB'   },
				{name : 'nmAveFlushKB'   },
		{name : 'intImsis'    } 
	]
};

function renderInit(value ,record,columnObj,grid,colNo,rowNo){	
}

var colsConfig = [
		{ id : 'fullDate'      , header : "时间",width : 150   },
		{ id : 'upFlushMB'    , header : "上行流量(MB)" ,headAlign:'right' ,align:'right' ,exportnumber:true, renderer:renderFormatDataInit2},
		{ id : 'downFlushMB'     , header : "下行流量(MB)" ,headAlign:'right' ,align:'right' ,exportnumber:true , renderer:renderFormatDataInit2},
		{ id : 'totalFlushMB'  , header : "总流量(MB)",headAlign:'right' ,align:'right' ,exportnumber:true, renderer:renderFormatDataInit2},
		{ id : 'upFlushKB'    , header : "上行流量(KB)",hidden:true,headAlign:'right' ,align:'right' ,exportnumber:true , renderer:renderFormatDataInit2 },
		{ id : 'downFlushKB'     , header : "下行流量(KB)",hidden:true,headAlign:'right' ,align:'right' ,exportnumber:true , renderer:renderFormatDataInit2  },
		{ id : 'totalFlushKB'  , header : "总流量(KB)",hidden:true,headAlign:'right' ,align:'right',exportnumber:true, renderer:renderFormatDataInit2},
		{ id : 'upFlushGB'    , header : "上行流量(GB)",hidden:true,headAlign:'right' ,align:'right' ,exportnumber:true , renderer:renderFormatDataInit2 },
		{ id : 'downFlushGB'     , header : "下行流量(GB)",hidden:true,headAlign:'right' ,align:'right' ,exportnumber:true  , renderer:renderFormatDataInit2 },
		{ id : 'totalFlushGB'  , header : "总流量(GB)",hidden:true,headAlign:'right' ,align:'right',exportnumber:true, renderer:renderFormatDataInit2},
		{ id : 'intImsis' , header : "用户数",headAlign:'right' ,align:'right'  ,exportnumber:true} ,
		{ id : 'nmAveFlushKB'  , header : "平均流量(KB/人)",headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 }
];

var gridConfig={
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/spaceDistributeTrend_query.do?1=1',
	exportURL :'/spaceDistributeTrend_export.do?1=1' ,
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
	return 25;
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
		showunit = buildPortraitUnitF();
		sortInit = false;
	}
	
	//初始化grid
	//initColumnWidth(grid, width, hideColumn, checkWidth)
	//initColumnWidthWithPop(datagrid, 940, 6, 0);
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
	
	getHeadTitle();
	
}

function getHeadTitle(){
	//$('#headTitle').html('【'+queryDateStart+'】到【'+queryDateEnd+'】【'+areaValue +'】 空间时间走势图');
}
var charinit = true;
function getAjaxChartXmlData(){
var param = {
	 	timeLevel:timeLevel,
	 	areaType:areaType,
	 	areaValue:areaValue,
	 	startTime_trend:queryDateStart,
	 	endTime_trend:queryDateEnd
	 };
charinit = false;
	$.ajax({
		type : "post",
		dataType: 'json',
		url : "spaceDistributeTrend_getAjaxChartXmlData.do?1=1",
		data : param,
		success : function(sjson) {
			//alert("sjson:"+sjson);
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

/*图表是否已经初始化*/
var chartinit = false;
/*组装图形数据*/
function buildChartData(json){

	var title = queryDateStart+"至"+queryDateEnd+" 流量|用户数走势图";
	
	var xlabels = [] ;
	var datas1 = [] ;
	var datas2 = [] ;
	
	if(json!=''&&json.length>0){
		for(var i = 0 ; i<json.length;i++){	
			var record = json[i];	 	
		 	xlabels[i] = record.fullDate; 
		 	if( 'MB' == showunit){
				datas1[i] = StringToFloat(record.totalFlushMB);
		 	}else if( 'KB' == showunit){
				datas1[i] = StringToFloat(record.totalFlushKB);
		 	}else if( 'GB' == showunit){
				datas1[i] = StringToFloat(record.totalFlushGB);
		 	}	
			datas2[i] = StringToFloat(record.intImsis);			
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
	//格式化X轴时间
	switch(timeLevel)
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
	//alert(robj.datas);
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
/*初始化图表*/
function open_flash_chart_data(id)
{
	return document.getElementById("chartxmldata").value;
}
/*图表回调函数*/
function callback(label){
	//alert("label:"+label);
}

var hasinit = false;

function query(){
	//重置分页数据
	datagrid.setTotalRowNum(-1);
	var param = buildCondition();
	
	
	
	if( '' == queryDateStart){
		alert('请输入查询开始时间！');
		return ;
	}
	if( '' == queryDateEnd){
		alert('请输入查询结束时间！');
		return ;
	}
	
	if( '' == areaValue){
		alert('请选择区域！');
		return;
	}
	hasinit = false;
	//alert(timeLevel+'>[timeLevel]=='+areaType+'>[areaType]=='+areaValue+'>[areaValue]=='
	//+queryYear+'>[queryYear]=='+queryMonth+'>[queryMonth]=='+queryDay+'>[queryDay]=='+queryHour+'>[queryHour]=='+queryWeek+'>[queryWeek]');

	 GT.$grid('datagrid').query( param );
	 
	 getHeadTitle();

}
function buildCondition(){
	 timeLevel = $('#timeLevel option:selected').val();
	queryDateStart= $('#queryDateStart').attr('value');
	queryDateEnd = $('#queryDateEnd').attr('value');
	

	 areaType =$("#area_type_select option:selected").val();
	 areaValue = $("#area_title").attr("value");
	 
	 var param = {
	 	timeLevel:timeLevel,
	 	areaType:areaType,
	 	areaValue:areaValue,
	 	startTime_trend:queryDateStart,
	 	endTime_trend:queryDateEnd
	 };
	 
	 return param;  
}

function reset(){
	$("#timeLevel").get(0).selectedIndex=0;
	$('#queryDateStart').attr('value','');
	$('#queryDateEnd').attr('value','');

 $("#areaIds").attr('value','');
$("#area").attr('value','');

}

function changeTimeLevel(){
	$('#queryDateStart').attr('value','');
	$('#queryDateEnd').attr('value','');
}
var areaids=[];	
var areanames=[];
var areas=[];
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
				alert("服务器出错,请联系管理员!");
			}
		});		
});

//-----------------------------------------------------------------------------------------------------------------//
 function initArea(index){
 	areas = [];

	var aids = areaids[index].split("\,");
	var anames = areanames[index].split("\,");
	//alert(aids);
	//alert(anames);
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
	resetautocomplete(areas,'tttt','area_title');
}
//-----------------------------------------------------------------------------------------------------------------//

 //---------------------------------------------------------------------
function changeAreaType(){
	var si = $("#area_type_select").val();
	//var si = $("#area_type_select").attr("selectedIndex");
	initArea(si);
}
$(document).ready(function(){
	//getAllApnType();
	$('#queryDateStart').attr('value',queryDateStart);
	$('#queryDateEnd').attr('value',queryDateEnd);
		 
});

//-----------------------所有区域信息结束---------------------------------

//时间选择事件
function getStartDate(){
	var ts = $("#timeLevel option:selected").val();
	switch(ts)
	   {
	   case '1':
	   	 var a = new WdatePicker({maxDate:'#F{$dp.$D(\'queryDateEnd\')}',dateFmt:'yyyy-MM-dd HH:00'});
	     break;
	   case '2':
	   	 new WdatePicker({maxDate:'#F{$dp.$D(\'queryDateEnd\')}',dateFmt:'yyyy-MM-dd'});
	     break;
	   case '3':
	     new WdatePicker({maxDate:'#F{$dp.$D(\'queryDateEnd\')}',dateFmt:'yyyy-MM-dd'});
	     break;
	   case '4':
	   	  new WdatePicker({maxDate:'#F{$dp.$D(\'queryDateEnd\')}',dateFmt:'yyyy-MM'});
	     break;
	 }
}
function getEndDate(){
	var ts = $("#timeLevel option:selected").val();
	switch(ts)
	   {
	   case '1':
	   	 new WdatePicker({minDate:'#F{$dp.$D(\'queryDateStart\')}',dateFmt:'yyyy-MM-dd HH:00:00'});
	     break;
	   case '2':
	   	 new WdatePicker({minDate:'#F{$dp.$D(\'queryDateStart\')}',dateFmt:'yyyy-MM-dd'});
	     break;
	   case '3':
	     new WdatePicker({minDate:'#F{$dp.$D(\'queryDateStart\')}',dateFmt:'yyyy-MM-dd'});
	     break;
	   case '4':
	   	 new WdatePicker({minDate:'#F{$dp.$D(\'queryDateStart\')}',dateFmt:'yyyy-MM'});
	     break;
	 }
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
          handler :exportToXls
        }]
      });
	  toolbar.render();
});
 function exportToXls(){
	//导出
	var fileObj = new Object();
	fileObj.fileName='空间'+areaValue+'时间走势数据';
	fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
	fileObj.gridObj = datagrid;//当前grid对象
	fileObj.params ={
	 	timeLevel:timeLevel,
	 	areaType:areaType,
	 	areaValue:areaValue,
	 	startTime_trend:queryDateStart,
	 	endTime_trend:queryDateEnd
	 };
	exportFile(fileObj);
 }
/**/
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