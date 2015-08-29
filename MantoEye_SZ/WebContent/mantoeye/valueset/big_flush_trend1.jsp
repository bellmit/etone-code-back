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
							<td><div style="display:none">
								<table cellspacing="0" cellpadding="0" border="0" width="100%" >
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
													<td class="condition_name">
														时间跨度：
													</td>
													<td class="condition_value" id="time_space_start">
														<input type="text" class="Wdate"
															style="display: block; height: 17px;"
															onclick="selectStartTime()" id="startTime_search"
															name="startTime_search" />
													</td>
													<td style="width: 80px;">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;到&nbsp;&nbsp;
													</td>
													<td class="condition_value" id="time_space_end">
														<input type="text" class="Wdate"
															style="display: block; height: 17px;"
															onclick="selectEndTime()" id="endTime_search"
															name="endTime_search" />
													</td>
													<td style="width: 60px;">
														&nbsp;
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
											</table>
											<table style="margin-left: -1px">
												<tr valign="middle">
													<td
														style="width: 72px; height: 22px; cursor: default; padding: 0px 1px 0px 18px; color: #ffffff; font-size: 11px;">
														用户号码：
													</td>
													<td style="width: 2px"></td>
													<td height="22px;">
														<input type="text" value="" id="msisdn_title" readonly
															onclick="showDialog('msisdn')"
															style="width: 345px; height: 16px; border: 1px solid #FFDDEE; font-size: 11px; color: #163877; display: block;" />
														<div id="msisdn_title_dialog" class="dialog_main_class"
															style="display: none;">
															<div class="dialog_head_class">
																<div class="dialog_head_icon_class">

																</div>
																<div class="dialog_head_title_class">
																	用户号码选择对话框
																</div>
															</div>
															<table class="dialog_table_class">
																<tr>
																	<td>
																		查询号码：
																		<input id="msisdn_title_search" />
																		<img src="/skin/Default/images/form/16/search.gif"
																			alt="点击查询大流量用户" onclick="searchMsisdn()"></img>
																	</td>
																</tr>
																<tr>
																	<td>
																		<span><font color="red">注：</font><font
																			color="blue">由于数据过多，请输入查询关键字过滤数据!</font></span>
																	</td>
																</tr>
																<tr>
																	<td style="height: 20px"></td>
																</tr>
																<tr>
																	<td>
																		可选号码：
																		<select id="msisdn_select">

																		</select>
																	</td>
																</tr>
																<tr>
																	<td style="height: 40px"></td>
																</tr>
															</table>
															<div class="dialog_button_class">
																<button class="dialog_button" onclick="confirmDialog('msisdn')">
																	确定
																</button>
																<button class="dialog_button" onclick="closeDialog('msisdn')">
																	关闭
																</button>
															</div>
														</div>
														<div class="dialog_msg_class" id="dialog_div_id">
															<img src="/skin/Default/images/icon/16/loading.gif"></img>
															操作进行中,请稍后...
														</div>
													</td>
												</tr>
											</table>
										</td>
										
									</tr>
								</table>
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
		{ id : 'imsi'    , header : "IMSI"  ,hidden:true },
		{ id : 'strMissdn'      , header : "号码" ,hidden:true   },
		{ id : 'imei'    , header : "IMEI" ,  sortable:false ,hidden:true },
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
	obj.hideColumn = 3 ;//隐藏的列数目
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


//grid查询
function query() {
	//重置分页数据
	datagrid.setTotalRowNum(-1);
	datagrid.loadURL = "/bigflushdisplay_queryTrend.do?1=1";
	//查询条件
	timeLevel_search = $("#timeLevel_search option:selected").val();
	startTime_search = $('#startTime_search').attr("value");
	endTime_search = $('#endTime_search').attr("value");	//设置图形标志
	msisdn_trend = $("#msisdn_select").attr("value");
	if(startTime_search=='' || endTime_search==''){
		alert('请选择时间跨度!');
		return ;
	}
	if(msisdn_trend==""){
		alert("请选择号码!");
		return;
	}
	hasinit = false;
	datachange = true;
	//传递给后台参数
	var param={
		protocolType:2,
		hasinit:hasinit,
		dataType_trend:dataType_search,//TD标识
		startTime_trend:startTime_search,//开始时间
		endTime_trend:endTime_search,//结束时间
		msisdn_trend:msisdn_trend
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
	new WdatePicker({maxDate:'#F{$dp.$D(\'endTime_search\')}',dateFmt:'yyyy-MM-dd'});
}
function selectEndTime(){
	new WdatePicker({minDate:'#F{$dp.$D(\'startTime_search\')}',dateFmt:'yyyy-MM-dd'});
}
//重置查询条件
function reset(){
	$("#msisdn_search").attr("value","");
	$('#startTime_search').attr("value","");
	$('#endTime_search').attr("value","");
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
			fileObj.fileName='传输协议['+tcpName+']时间走势数据列表';//('+startTime_search+'至'+endTime_search+')';//文件名称
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

$(document).ready(function(){
	window.onerror = killErrors;
})
function killErrors() {
	return true;
}
*/
//彩信主题事件
function searchMsisdn(){
	if($('#startTime_search').attr("value")=='' ||$('#endTime_search').attr("value")==''){
		alert('请选择时间!');
		return ;
	}
	var like_msisdn_title_search=$('#msisdn_title_search').attr('value');
	like_msisdn_title_search = like_msisdn_title_search.trim();
	if(like_msisdn_title_search==''){
		alert('请输入手机号码的部分!');
		return ;
	}
	
	var $msgObj = $('#dialog_div_id');
	$msgObj.css({'display':'block','margin-left':'50px','margin-top':'40px'});
	$('#msisdn_select option').remove();
	$.ajax({
		type:"post", 
		url:"bigflushdisplay_queryMsisdn.do", 
		data:{
			dataType_search:dataType_search, 
			searchDateStart:$('#startTime_search').attr("value"), 
			searchDateEnd:$('#endTime_search').attr("value"), 
			like_msisdn_search:like_msisdn_title_search
		}, 
		success:function (message) {
			if (message != null && message != "") {
				if(message.indexOf('ERROR:')!=-1){
					$msgObj.css({'display':'none'});
					alert('用户数多于100，请设置更详细的过滤条件!');
				}else{			
					var ts = message.split("&&&&&");
					if (ts.length > 0) {
						for (var i = 0; i < ts.length; i++) {
							$("#msisdn_select").append("<option value='" + ts[i] + "'>" + ts[i] + "</option>");
						}
					}
				}
			}else{
				$msgObj.css({'display':'none'});
				alert('没有查询到大流量用户!');
			}
			$msgObj.css({'display':'none'});
		}, error:function () {
			$msgObj.css({'display':'none'});
			alert("查询出错,请重试!");
		}
	});
}

</script>


