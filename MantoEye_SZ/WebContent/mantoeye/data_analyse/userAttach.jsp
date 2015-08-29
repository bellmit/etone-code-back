<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>用户归属分布</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" type="text/css"
			href="/tools/gt-grid/gt_grid.css" />
		<link type="text/css" rel="stylesheet"
			href="/skin//Default/css/maincontent.css" />
		<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
		<!-- 列表工具栏 -->
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
			type="text/css"></link>
		<script type="text/javascript"
			src="/js/jquery/extend.tab/js/Toolbar.js"></script>
		<script type="text/javascript"
			src="/js/common.js"></script>
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
													<!--  <td style="width: 60px">
														<input type="radio" name="dataType" id="d_dataType1"
															value="1" checked style="width: 20px; border: 0px;" />
														GPRS
													</td>
													<td style="width: 60px">
														<input type="radio" name="dataType" value="2"
															id="d_dataType2" style="width: 20px; border: 0px;" />
														TD
													</td>-->
														<td class="condition_name">
														时间粒度：
													</td>
													<td class="condition_value">
														<div class="select">
															<div>
																<select name="timeLevel" id="d_timeLevel"
																	style="height: 21px" onchange="selChange();">
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
															</div>
														</div>
													</td>
													<td class="condition_name">
														时间：
													</td>
													<td class="condition_value">
														<input type="text" class="Wdate" style="height: 15px;"
															onclick="preStartDate();"
															name="searchDateStart" value="${searchDateStart}"
															id="d_searchDateStart" />
															<input type="hidden" id="d_searchDateEnd" />
													</td>
													<td width="300px">
														<div id="mainbtn" style="width:300px">
															<ul>
																<li>
																	<a href="javascript:query();" 　title="查询"><span>查询</span>
																	</a>
																</li>
																<li>
																	<a href="javascript:parent.openTab('/mantoeye/data_trend/userBelong_distribute_trend.jsp','FLUSH_COUNTRY_DATE','用户归属时间走势');" 
																		style="width:90px;"  title="时间走势"><span><img onclick="parent.openTab('/mantoeye/data_trend/userBelong_distribute_trend.jsp','FLUSH_COUNTRY_DATE','用户归属时间走势');" 
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
												用户归属分布图形
											</div>
											<input type="hidden" value="" id="chartxmldata1" />
											<input type="hidden" value="" id="chartxmldata2" />
											<input type="hidden" value="" id="chartxmldata3" />
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
							<!--  
							<div id="imgarea3" style="width: 0%; height: 0%"></div>
							-->
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
												用户归属分布列表
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
var dataTypeValue=1;
var date=new Date();
date=date.valueOf();
date=date-24*60*60*1000;
date=new Date(date); 
var initChartAgain = false;//判断是否再次初始化图形
var dataType_search = 1 ; //页面显示数据类型
var timeLevel_search =parent.total_obj.total_time_level==null?'2':parent.total_obj.total_time_level;//查询时间粒度
var defaulettime =date.getYear()+'-'+''+((date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1)))+'-'+''+(date.getDate()>9?date.getDate():("0"+date.getDate())); //查询开始时间
var searchDateStart = parent.total_obj.total_time_search==null?defaulettime:parent.total_obj.total_time_search;


var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :[
		{name : 'fullDate'        },
		{name : 'userBelong'      },
		{name : 'upFlushMB'     },
		{name : 'downFlushMB'   },
		{name : 'totalFlushMB'   },
		{name : 'upFlushKB'    },
		{name : 'downFlushKB'   },
		{name : 'totalFlushKB'    },
		{name : 'upFlushGB'    },
		{name : 'downFlushGB'   },
		{name : 'totalFlushGB'   },
		{name : 'intImsis' },
		{name : 'nmAveFlushKB'   },
		{name : 'activeCount' }
	]
};
var colsConfig = [
		{ id : 'fullDate'      , header : "时间"     ,sortable:false  },
		{ id : 'userBelong'      , header : "用户归属"     ,sortable:false  },
		{ id : 'upFlushMB'    , header : "上行流量(MB)" ,headAlign:'right' ,align:'right' ,exportnumber:true  ,renderer:renderFormatDataInit2 },
		{ id : 'downFlushMB'     , header : "下行流量(MB)" ,headAlign:'right' ,align:'right'  ,exportnumber:true  ,renderer:renderFormatDataInit2 },
		{ id : 'totalFlushMB'  , header : "总流量(MB)",headAlign:'right' ,align:'right' ,exportnumber:true ,renderer:renderFormatDataInit2 },
		{ id : 'upFlushKB'    , header : "上行流量(KB)",headAlign:'right' ,align:'right' ,exportnumber:true ,renderer:renderFormatDataInit2  },
		{ id : 'downFlushKB'     , header : "下行流量(KB)",headAlign:'right' ,align:'right' ,exportnumber:true  ,renderer:renderFormatDataInit2  },
		{ id : 'totalFlushKB'  , header : "总流量(KB)",headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'upFlushGB'    , header : "上行流量(GB)",headAlign:'right' ,align:'right' ,exportnumber:true,renderer:renderFormatDataInit2   },
		{ id : 'downFlushGB'     , header : "下行流量(GB)",headAlign:'right' ,align:'right' ,exportnumber:true ,renderer:renderFormatDataInit2   },
		{ id : 'totalFlushGB'  , header : "总流量(GB)",headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'intImsis' , header : "用户数" ,headAlign:'right' ,align:'right'  ,exportnumber:true  },
		{ id : 'nmAveFlushKB'  , header : "平均流量(KB/人)",headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'activeCount' , header : "激活次数" ,headAlign:'right' ,align:'right'  ,exportnumber:true  }
];

var gridConfig={
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/userAttachDistribute_query.do?1=1&total_time_level='+parent.total_obj.total_time_level+'&total_time_search='+parent.total_obj.total_time_search,
	exportURL :'/userAttachDistribute_export.do?1=1' ,
	dataset : dsConfig ,
	columns : colsConfig ,
	width:780,
	height:500,
	resizable : false,
	showGridMenu : false,
	allowCustomSkin : false,
	allowFreeze : true,
	allowGroup : true,
	allowHide : true,
	container : 'data_container', 
	beforeLoad:checkLogon,
	toolbarContent : 'state' ,
	pageSize : 50 ,
	remoteSort : false ,
	pageSizeList : [10,20,50,100],
//	customHead : 'myHead',
	onComplete:loadComplate
};

var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
} );
var hasinit = false;
function loadComplate(){
	var  obj = new Object ();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.hideColumn = 6 ;//隐藏的列数目
	obj.isCheckbox = false;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度
		
	//设置宽度
	var totalRecords = datagrid.getAllRows().length;//获取加载到的数据记录数目
	var width=window.screen.availWidth;
	
	//判断是否查询到数据
	judgeData(datagrid);
	//选择呈现单位 
	if(!initHasData){
		sortInit = true;
	}
	
	if(sortInit && initHasData){
		buildUnit();
		sortInit = false;
	}
		initGridInfo(obj);
	
	var date=$("#d_searchDateStart").val();
	var ld=$("#d_timeLevel").val();
	var bis=$("#d_businessType").val();
	var title;
	var strdate;
	if(parent.total_obj.total_time_level==null){
		if(date==''){
			date=new Date();
			date=date.valueOf();
			date=date-24*60*60*1000;
			date=new Date(date); 
			var month=date.getMonth()+1;
			strdate=date.getYear()+'年'+''+month+'月'+''+date.getDate()+'日';
			$('#d_searchDateStart').val(date.getYear()+'-'+''+((date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1)))+'-'+''+(date.getDate()>9?date.getDate():("0"+date.getDate())));
			
		}else{
			strdate=date.toString();
		}
	}else{
		$('#d_searchDateStart').val(parent.total_obj.total_time_search);
		$("#d_timeLevel").get(0).selectedIndex=parent.total_obj.total_time_level-1;
		strdate=parent.total_obj.total_time_search;
		parent.total_obj.total_time_level=null;
	}	

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
	var datas1 = [] ;
	
	//var text2 = '';
	//var label2 = '用户数';
	//var datas2 = [] ;
	var text2 = '';
	var xlabels2 = [] ;
		var label2 = '占比@@%';
	var label22 = '用户数';
	var showxdatas = [];
	var showxlabels = [];
	var datas2 = [] ;
	
	var text3 = '';
	var label3 = '激活次数';
	var datas3 = [] ;
	
	var showlabel="";
	if(typeof(datagrid.getRecord(0))!="undefined"){
		for(var i = 0 ; i<totalRecords;i++){	
			var record = datagrid.getRecord(i);	 	
		 	if(text1==''){
		 		chartTime = record.fullDate;
		 		text1 = chartTime+' 流量占比图';
		 		text2 = chartTime+' 用户数占比图';
		 		text3 = chartTime+' 激活次数占比图';
		 	}	
		 	//if(i<10){ 	
			datas1[i] = {"label":record.userBelong,"value":StringToFloat(record.totalFlushMB)};
			//datas2[i] = {"label":record.userBelong,"value":StringToFloat(record.intImsis)};
			datas3[i] = {"label":record.userBelong,"value":StringToFloat(record.activeCount)};
			//}
			datas2[i] = record.imsisRate;
			xlabels2[i] = record.userBelong;	
			showxlabels[i]=record.userBelong;	
			
	showxdatas [i] =record.intImsis;			
		}
		datas2 = buildHBarChartTip(datas2,showxlabels,label2,showxdatas,label22);
	var jsons1 = buildPieChartData(text1,label1,datas1);
	var str1 = JSON.stringify(jsons1);
	//var jsons2 = buildPieChartData(text2,label2,datas2);
	var jsons2 = buildPercentChartData(text2,[label2],xlabels2,[datas2]);
	var str2 = JSON.stringify(jsons2);
	var jsons3 = buildPieChartData(text3,label3,datas3);
	var str3 = JSON.stringify(jsons3);
	if(!chartinit){
		chartinit = true;
		document.getElementById("chartxmldata1").value=str1;
		document.getElementById("chartxmldata2").value=str2;
		document.getElementById("chartxmldata3").value=str3;
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=flush", "imgarea1", "40%", "100%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
		//swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=count", "imgarea3", "0%", "100%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=imsis", "imgarea2", "59%", "100%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
	}else{
	  	var tmp1 = findSWF("imgarea1");	  	
  		tmp1.load(str1);
  		var tmp2 = findSWF("imgarea2");
  		tmp2.load(str2);
  		//var tmp3 = findSWF("imgarea3");
  		//tmp3.load(str3);
  		
	}
	}else{
		chartinit = false;
		var str1 = JSON.stringify(buildEmptyAxisChart());
		document.getElementById("chartxmldata1").value=str1;
		document.getElementById("chartxmldata2").value=str1;
		document.getElementById("chartxmldata3").value=str1;
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=imsis", "imgarea2", "99%", "99%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=flush", "imgarea1", "0%", "0%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
		//swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=count", "imgarea3", "0%", "0%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
		return;
	}
}
/*初始化图表*/
function open_flash_chart_data(id)
{
	if(id=='flush'){
		return document.getElementById("chartxmldata1").value;
	}else if(id=='imsis'){
		return document.getElementById("chartxmldata2").value;
	}else{
		return document.getElementById("chartxmldata3").value;
	}
}
/*图表回调函数*/
function callback(label){
	//alert("label:"+label);
}
///////////////////////////////////////////

//// 查询表单的查询函数 ////
function query() {
	parent.total_obj.total_time_level=null;
	parent.total_obj.total_time_search=null;
	datagrid.loadURL ='/userAttachDistribute_query.do?1=1&total_time_level='+parent.total_obj.total_time_level+'&total_time_search='+parent.total_obj.total_time_search;
	hasinit=true;
	timeLevel_search = $("#d_timeLevel option:selected").val();
	/**if(document.getElementById("d_dataType1").checked==true){
		dataTypeValue=0;
	}else{
		dataTypeValue=1;
	}*/
	searchDateStart=$("#d_searchDateStart").val();
	if(searchDateStart==''){
		alert("请选择查询时间!");
		return ;
	}
	var param={
		searchDateStart : searchDateStart,
		timeLevel :timeLevel_search,
		dataType :dataTypeValue
	};
	GT.$grid('datagrid').query( param );
}

//初始化grid工具栏
$(document).ready(function(){
	var dl = parent.total_obj.total_time_level;
	if(dl==1){
		dayOrHour='hour'
	}else if(dl==2){
		dayOrHour='day'
	}else if(dl==3){
		dayOrHour='week'
	}else if(dl==4){
		dayOrHour='month'
	}else{
		dayOrHour='day'
	}
	
      var toolbar = new Toolbar({
        renderTo : 'toolbar',
		//border: 'top',
        items : [{
          type : 'button',
          text : '导出',
          bodyStyle : 'xls',
          useable : 'T',
          handler : function(){
         //	 var dataTypeValue=1;
		//	var timeLevel_search = $("#d_timeLevel option:selected").val();
		//	var searchDateStart=$("#d_searchDateStart").val();
           //参数
			var params={
				searchDateStart : searchDateStart,
				timeLevel :timeLevel_search,
				dataType :dataTypeValue
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName='用户归属数据';//('+startTime_search+'至'+endTime_search+')';//文件名称
			fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
			fileObj.gridObj = datagrid;//当前grid对象
			fileObj.params = params;
			exportFile(fileObj);
          }
        }]
      });
	  toolbar.render();
});

function reset(){
	$('#d_searchDateStart').attr("value","");
	$("#d_timeLevel").get(0).selectedIndex=1;
	//$('#d_dataType1').attr("checked","true");//设置radio默认值
	
}

/*
function initChartData(param){
	hasinit=true;
		var xml =document.getElementById("chartxmldata").value;			
	return xml;
}
function changData(){
	var xml = document.getElementById("chartxmldata").value;
	document.frm.ThreePieChart.jsChangeData(xml);
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
  //alert("**charItemClick**"+label);
}
*/
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


