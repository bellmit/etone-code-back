<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/include/session.jsp"%>
<%@ include file="/include/setcache.jsp"%>
<head>
<title>内容分布</title>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<link rel="stylesheet" type="text/css" href="/tools/gt-grid/gt_grid.css" />
<link type="text/css" rel="stylesheet"
	href="/skin/Default/css/maincontent.css" />
<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js"></script>
<script language="javascript" type="text/javascript"
	src="/tools/datepicker/WdatePicker.js"></script>
<!-- 列表工具栏 -->
<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
	type="text/css"></link>
<script type="text/javascript" src="/js/jquery/extend.tab/js/Toolbar.js"></script>
<script type="text/javascript" src="/js/common.js"></script>
<script src="/tools/jquery/jquery.treeview.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/common_grid.js"></script>
<!-- 导入内容选择复选框 -->
<link rel="stylesheet" href="/mantoeye/dialog/dialog.css" />
<link rel="stylesheet" href="/tools/jquery/jquery.treeview.css" />
<script type="text/javascript" src="/mantoeye/dialog/SymbolDialog.js"></script>
<script type="text/javascript" src="/tools/jquery/jquery.treeview.js"></script>
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
		<td colspan="2" height="5px" style="font-size: 5px;"></td>
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
						<table cellspacing="0" cellpadding="0" border="0" width="100%">
							<tr style="font-size: 1px;" valign="top">
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
							style="margin-left: 1px" cellpadding="0px;boder="0">
							<tr valign="middle">
								<td style="width: 72px">
									<input type="radio" name="dataType" id="d_dataType1"
											value="0" checked
											style="width: 20px; border: 0px; background-color: transparent;" 
											onclick='showDataType(1)'/>
									GPRS
								</td>
								<td style="width: 72px">
									<input type="radio" name="dataType" id="d_dataType2"
											style="width: 20px;margin-left:2px; border: none; background-color: transparent;" 
											onclick='showDataType(2)'/>
									TD
								</td>
								<td class="condition_name">时间粒度：</td>
								<td class="condition_value">
								<div class="select">
								<div><select name="timeLevel" id="d_timeLevel"
									style="height: 21px" onchange="selChange();">
									<option value="1">小时</option>
									<option value="2" selected>天</option>

									<option value="3">周（周日~周六）</option>
									<option value="4">月</option>
								</select></div>
								</div>
								</td>
								<td class="condition_name">时间：</td>
								<td class="condition_value"><input type="text"
									class="Wdate" style="height: 17px;" onclick="preStartDate();"
									name="searchDateStart" value="${searchDateStart}"
									id="d_searchDateStart" /> <input type="hidden"
									id="d_searchDateEnd" /></td>
								<td width="300px">
								<div id="mainbtn" style="width: 300px">
								<ul>
									<li><a href="javascript:query();" 　title="查询"><span>查询</span>
									</a></li>
									<li><a
										href="javascript:parent.openTab('/mantoeye/data_trend/context_distribute_trend.jsp','FLUSH_CONTEXT_DATE','内容时间走势');"
										style="width: 90px;" title="时间走势"><span><img
										onclick="parent.openTab('/mantoeye/data_trend/context_distribute_trend.jsp','FLUSH_CONTEXT_DATE','内容时间走势');"
										src="/skin/Default/images/MantoEye/btn/trend.png" alt="" />时间走势</span>
									</a></li>
								</ul>
								</div>
								</td>
							</tr>
						</table>
						<table style="margin-left: -1px">
							<tr valign="middle">
								<td
									style="width: 80px; height: 22px; cursor: default; padding: 0px 1px 0px 10px; color: #ffffff; font-size: 11px;">
								<input type="radio" value="businesstype" checked
									name="businessType" id="d_businessType"
									onclick="selectBusiness(this);"
									style="width: 20px; border: 0px; background-color: transparent;" />
								内容类型</td>
								<td
									style="width: 72px; cursor: default; padding: 0px 1px 0px 10px; color: #ffffff; font-size: 11px;">
								<input type="radio" value="business" name="businessType"
									onclick="selectBusiness(this);"
									style="width: 20px; border: none; background-color: transparent;"
									disabled id="d_businessss" /> 内容</td>
								<td style="width: 10px"></td>
								<td height="22px;"><input type="text"
									value="--------------------------请选择内容-------------------------------------"
									id="business" onclick="showDialog()"
									style="width: 260px; height: 16px; display: block; border: 1px solid #FFDDEE; font-size: 11px; color: #163877; display: none" />
								<input id="businessIds" type="hidden" value=""
									name="businessIds" /></td>
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
						<div class="middletitle">内容分布图形</div>
						<%--饼图 --%> <input type="hidden" value="" id="chartxmldata1" /> <input
							type="hidden" value="" id="chartxmldata2" /> <input
							type="hidden" value="" id="chartxmldata3" /> <%--柱状图 --%> <input
							type="hidden" value="" id="chartxmldata9" /></td>
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
				<div id="imgareas1"
					style="width: 100%; height: 100%; border: 2px solid #008BD1; display: block;">
				<div id="imgarea1" style="width: 0%; height: 0%"></div>
				<div id="imgarea2" style="width: 0%; height: 0%"></div>
				<div id="imgarea3" style="width: 0%; height: 0%"></div>
				</div>
				<div id="imgareas2"
					style="width: 100%; height: 100%; border: 2px solid #008BD1; display: none;">
				<div id="imgarea9" style="width: 0%; height: 0%"></div>
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
						<div class="middletitle">内容分布列表</div>
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
var dataTypeValue = parent.total_obj.dataType_search==null?1:parent.total_obj.dataType_search; //数据类型标识 1GPRS、2TD类型
setDataTypeCheck(dataTypeValue);
var searchDateStart=new Date();//时间
searchDateStart=searchDateStart.valueOf();
searchDateStart=searchDateStart-24*60*60*1000;
searchDateStart=new Date(searchDateStart); 
var month=((searchDateStart.getMonth()+1)>9?(searchDateStart.getMonth()+1):("0"+(searchDateStart.getMonth()+1)));
var day=(searchDateStart.getDate()>9?searchDateStart.getDate():("0"+searchDateStart.getDate()));
var defaulettime=searchDateStart.getYear()+"-"+month+"-"+day;

var timeLevel_search =parent.total_obj.total_time_level==null?'2':parent.total_obj.total_time_level;//查询时间粒度
searchDateStart = parent.total_obj.total_time_search==null?defaulettime:parent.total_obj.total_time_search;


var business;//内容
var businessType="businesstype";//内容类型

var level = 2 ;

var pageSize=10;
var upHight = window.screen.availHeight;
var downHight = window.screenTop;
var middleHight=downHight-upHight;
var spaceHight=middleHight-296;
if(spaceHight>0){
	pageSize=pageSize+spaceHight/22;
}
var w="1052px";
var h="700px";
if(width==1024){
	w="810px";
	h="600px";
}

//设置页面显示的数据类型
function showDataType(type){
	dataTypeValue = type ;
}
//设置数据类型选择按钮
function setDataTypeCheck(dataTypeValue){
	if(dataTypeValue == 1){
		$('#d_dataType1').attr("checked","true");//设置radio默认值
	}else if(dataTypeValue == 2){
		$('#d_dataType2').attr("checked","true");//设置radio默认值
	}
}

var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :[
		{name : 'fullDate'        },
		{ name : 'business'       },
		{ name : 'businessType'   },
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
		{name : 'localtotalFlushMB'},
		{name : 'localtotalFlushKB'}, 
		{name : 'localtotalFlushGB'},
		{name : 'localintImsis'},
		{name : 'totalFlushRate'   },
		{name : 'imsisRate'   },
		{name : 'flushRate'   },
		{name : 'intImsis'},
		{name : 'visit'},
		{name : 'detail'    }
	]
};

function renderInit(value ,record,columnObj,grid,colNo,rowNo){
		var businessTypeOne = record.businessType.toString();
		var businessOne=record.business.toString();
		var totalImsis = record.intImsis;
		return '<img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/business.png" alt="查看具体内容分布情况" onclick="openSapceDistribute(2,\''+businessTypeOne+'\','+totalImsis+');" />';
}
var colsConfig = [
		{ id : 'fullDate'      , header : "时间"   ,sortable:false },
		{ id : 'business'      , header : "内容" , sortable:false  },
		{ id : 'businessType'      , header : "内容类型"  ,sortable:false  },
		{ id : 'upFlushMB'    , header : "上行流量(MB)",headAlign:'right' ,align:'right' ,exportnumber:true  ,renderer:renderFormatDataInit2 },
		{ id : 'downFlushMB'     , header : "下行流量(MB)",headAlign:'right' ,align:'right' ,exportnumber:true  ,renderer:renderFormatDataInit2  },
		{ id : 'totalFlushMB'  , header : "总流量(MB)",headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'upFlushKB'    , header : "上行流量(KB)",headAlign:'right' ,align:'right' ,exportnumber:true  ,renderer:renderFormatDataInit2 },
		{ id : 'downFlushKB'     , header : "下行流量(KB)",headAlign:'right' ,align:'right' ,exportnumber:true  ,renderer:renderFormatDataInit2  },
		{ id : 'totalFlushKB'  , header : "总流量(KB)",headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'upFlushGB'    , header : "上行流量(GB)",headAlign:'right' ,align:'right' ,exportnumber:true ,renderer:renderFormatDataInit2  },
		{ id : 'downFlushGB'     , header : "下行流量(GB)",headAlign:'right' ,align:'right' ,exportnumber:true ,renderer:renderFormatDataInit2   },
		{ id : 'totalFlushGB'  , header : "总流量(GB)",headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'localtotalFlushMB', header : "本地总流量(MB)", headAlign : 'right', align : 'right', exportnumber : true, renderer : renderFormatDataInit2}, 
		{ id : 'localtotalFlushKB', header : "本地总流量(KB)", headAlign : 'right', align : 'right', exportnumber : true, renderer : renderFormatDataInit2}, 
		{ id : 'localtotalFlushGB', header : "本地总流量(GB)", headAlign : 'right', align : 'right', exportnumber : true, renderer : renderFormatDataInit2},
		{ id : 'flushRate'  , header : "流量占比(%)",headAlign:'right' ,align:'right',renderer:renderFormatDataInit4 },
		{ id : 'intImsis' , header : "用户数",headAlign:'right' ,align:'right'   ,exportnumber:true},
		{ id : 'localintImsis', header : "本地用户数", headAlign : 'right', align : 'right', exportnumber : true},
		{ id : 'nmAveFlushKB'  , header : "平均流量(KB/人)",headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 },
		//{ id : 'totalFlushRate'  , header : "流量占比(%)",headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit4 },
		//{ id : 'imsisRate'  , header : "用户数占比(%)",headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit4 },
		{ id : 'visit' , header : "访问次数",hidden:true,headAlign:'right' ,align:'right'  ,exportnumber:true },
		{ id : 'detail'   , header : "操作" , sortable:false, width:200,exportable:false,
			renderer:renderInit
		}
];

var gridConfig={
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/contextDistribute_query.do?1=1&total_time_level='+parent.total_obj.total_time_level+'&total_time_search='+parent.total_obj.total_time_search+'&dataType='+dataTypeValue,
	exportURL :'/contextDistribute_export.do?1=1' ,
	//saveURL : '/flexiGridTest_list1.do?1=1' ,
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
	toolbarContent : 'nav | goto | pagesize | state' ,
	pageSize :  getLongPageSize(),
	remoteSort : true ,
	//remotePaging:false,
	pageSizeList :   [10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
	onComplete:loadComplate
};

var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
});

var hasinit = false;
var hasinit1 = false;
var flag=0;//标示饼图还是柱状图
function getLongPageSize(){
	var size = getDispalyPageSize(1,1);
	if(size<25){
		size = 25;
	}
	return size;
}
var sortOrder = "desc";
var sortValue ="totalFlushKB";
var lastflash="flush";
var currentflash="flush";
var flushchartjson="";
var imsischartjson="";
var firstInit=true;
var firstSort = true;
function loadComplate(){	
	/*if(datagrid.sortInfo!=null){
		sortOrder = datagrid.sortInfo[0].sortOrder;
		sortValue = datagrid.sortInfo[0].fieldName;
	}
	if(sortValue.indexOf("intImsis")!=-1){
		currentflash="imsis";
	}else{
		currentflash="flush";
	}*/
	columninit = false;//重新分配每列宽度
	var  objone = new Object ();//使用对象传参,以防以后修改
		objone.datagrid = datagrid;
		if(businessType=='businesstype'){
			objone.hideColumn = 8 ;//隐藏的列数目
		}else{
			objone.hideColumn = 9 ;//隐藏的列数目
		}
		objone.isCheckbox = false;//是否可选择
		objone.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度

	//设置宽度
	var totalRecords = datagrid.getAllRows().length;//获取加载到的数据记录数目
	//判断是否查询到数据
	judgeData(datagrid);
	if(!initHasData){
		sortInit = true;
	}
	
	//选择呈现单位 
	if(sortInit && initHasData){
		buildUnit_new();
		sortInit = false;
	}
	
	//初始化grid
	initGridInfo(objone);
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
			
	if(totalRecords==1){
		if(businessType=='businesstype'){
			datagrid.getColumn('business').hide();
			datagrid.getColumn('businessType').show();
		}else{
			datagrid.getColumn('business').show();
			datagrid.getColumn('businessType').hide();
		}
	}
	var date=$("#d_searchDateStart").val();
	var ld=$("#d_timeLevel option:selected").text();
	var title;
	var strdate;
	if(parent.total_obj.total_time_level==null){
		if(date==''){
			strdate=searchDateStart;
			$('#d_searchDateStart').val(searchDateStart);
		}else{
			strdate=date.toString();
		}
	}else{
		$('#d_searchDateStart').val(parent.total_obj.total_time_search);
		$("#d_timeLevel").get(0).selectedIndex=parent.total_obj.total_time_level-1;
		strdate=parent.total_obj.total_time_search;
		searchDateStart=parent.total_obj.total_time_search;
		timeLevel_search=parent.total_obj.total_time_level;
		parent.total_obj.total_time_level=null
	}	
	
			if(businessType=='businesstype'){
				document.getElementById("imgareas1").style["display"]="block";
				document.getElementById("imgareas2").style["display"]="none";
				/*$('.handImg1').each(function(){
						this.style["display"] = "none";
					});
					
					$('.handImg2').each(function(){
						this.style["display"] = "block";
					});*/
					datagrid.getColumn('detail').show();
					datagrid.getColumn('business').hide();
					datagrid.getColumn('businessType').show();

					buildChartData(datagrid.getAllRows().length);
					flag=0;
				
			}else{				
				document.getElementById("imgareas1").style["display"]="none";
				document.getElementById("imgareas2").style["display"]="block";
				/*$('.handImg1').each(function(){
						this.style["display"] = "block";
					});
					
					$('.handImg2').each(function(){
						this.style["display"] = "none";
					});*/
					datagrid.getColumn('detail').hide();
					datagrid.getColumn('businessType').hide();
					datagrid.getColumn('business').show();
				//alert("柱状图");
				flag=1;
					buildBarChartData(datagrid.getAllRows().length);
			}
			
}
///////////////////图表(柱状图)////////////////////////
/*图表是否已经初始化*/
var barchartinit = false;
/*组装图形数据*/
function buildBarChartData(totalRecords){
	var str1 ="";
	/*保存图形数据，排序时不用重新查询*/
	if(currentflash=='flush'){
		if(flushchartjson==""){
			flushchartjson= buildChartJsonStr(totalRecords,"flush");
		}
		str1 = flushchartjson;
	}else{
		if(imsischartjson==""){
			imsischartjson= buildChartJsonStr(totalRecords,"imsis");
		}
		str1=imsischartjson;
	}
	/*if((currentflash==lastflash)&&!firstSort){
		return ;	
	}*/	
	if(!firstSort){
		return ;	
	}
	lastflash = currentflash;
	if(!barchartinit){
		barchartinit = true;
		chartinit = false;
		document.getElementById("chartxmldata9").value=str1;
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=business", "imgarea9", "100%", "100%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
	}else{
	  	var tmp1 = findSWF("imgarea9");	  	
  		tmp1.load(str1);	
	}
	firstSort = false;
}
///////////////////////////////////////////
function buildChartJsonStr(totalRecords,flag){
	var chartTime ='';
	var title = '';
	var xlabels = [] ;
	var datas1 = [] ;
	var datas2 = [] ;
	
	if(typeof(datagrid.getRecord(0))!="undefined"){
	var totalbar = 0;
	var csortorder = sortOrder;
	if(csortorder!="desc"&&csortorder!="asc"){
		if(flag=="flush"){
			if(datagrid.getRecord(i).totalFlushMB>datagrid.getRecord(0).totalFlushMB){
				csortorder = "asc";
			}else{
				csortorder = "desc";
			}
		}else{
			if(datagrid.getRecord(i).intImsis>datagrid.getRecord(0).intImsis){
				csortorder = "asc";
			}else{
				csortorder = "desc";
			}
		}
	}
	var dtg = datagrid.getAllFreezeRows();
	if(csortorder =="desc"){
		totalbar = 0;
		for(var i = 0 ; i<totalRecords;i++){
			if(totalbar==20){
				break;
			}	
			var record = datagrid.getRecord(i);	 	
			if(title==''){
		 		chartTime = record.fullDate;
		 		title = chartTime+"内容 流量|用户数对比图";
		 	}				
			totalbar++;
		 	xlabels[i] = record.business; 	
		 	//如果柱状图需要添加点击，必须以如下的方法设置个点的值	
		 	datas1[i] = addCallFunction(record.business,StringToFloat(record.totalFlushMB));			
		 	datas2[i] = addCallFunction(record.business,StringToFloat(record.intImsis));	

			//如果柱状图不需要点击时间，可以用以下写法 
			//datas1[i] = StringToFloat(record.totalFlushMB);	
			//datas2[i] = StringToFloat(record.intImsis);		
		}
	}else{
		totalbar = 0;
		
		for(var i = (totalRecords-1) ; i>=0;i--){
			if(totalbar==20){
				break;
			}	
			var record = datagrid.getRecord(i);	 	
			if(title==''){
		 		chartTime = record.fullDate;
		 		title = chartTime+"内容 流量|用户数对比图";
		 	}				
			
		 	xlabels[totalbar] = record.business; 	
		 	//如果柱状图需要添加点击，必须以如下的方法设置个点的值	
		 	datas1[totalbar] = addCallFunction(record.business,StringToFloat(record.totalFlushMB));			
		 	datas2[totalbar] = addCallFunction(record.business,StringToFloat(record.intImsis));	
		 	totalbar++;		
		}
		}
	
	var showxlabels = xlabels;
	//格式化X轴
	 xlabels = formatXDateStrLables(xlabels);
	 var tdatas = [];
	 
	  //格式化Y轴数据（流量）
	var lobj = formatObjDataBy1024(datas1,"MB");
	var lunit="流量";
	if(lobj.unit!=""){
		lunit = lunit +"@@"+lobj.unit;
	}
	tdatas[0] = lobj.datas;
	 //格式化Y轴数据（用户数）
	var robj = formatObjNumberByWan(datas2);
	var runit="用户数";
	if(robj.unit!=""){
		runit = runit +"@@"+robj.unit;
	}
	//alert(robj.datas);
	tdatas[1] = robj.datas;
	
	tdatas[0] = buildBarChartTip(lobj.datas,showxlabels,lunit);
	tdatas[1] = buildBarChartTip(robj.datas,showxlabels,runit);
	
	 //如果有单位  则label与单位间用@@分开
	var labels = [lunit,runit];	

	var jsons1 = buildDoubleBarChartData(title,labels,xlabels,tdatas);
	var str1 = JSON.stringify(jsons1);
		return str1;
	}else{
		var str2 = JSON.stringify(buildEmptyAxisChart());	
		return str2;	
	}
}

///////////////////图表(饼图)////////////////////////

/*图表是否已经初始化*/
var chartinit = false;
/*组装图形数据*/
function buildChartData(totalRecords){
	var chartTime ='';
	var text1 = '';
	//如果有单位  则label与单位间用@@分开
	var label1 = '流量';	
	var datas1 = [] ;
	
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
	
	var otherflush = 0 ;
	var otherimsis = 0 ;
	var othercount = 0 ;
	
	var showlabel="";
	if(typeof(datagrid.getRecord(0))!="undefined"){
		for(var i = 0 ; i<totalRecords;i++){	
			var record = datagrid.getRecord(i);	 	
		 	if(text1==''){
		 		chartTime = record.fullDate;
		 		text1 = chartTime+' 内容类型流量占比图';
		 		text2 = chartTime+' 内容类型用户数占比图';
		 		text3 = chartTime+' 内容类型激活次数占比图';
		 	}	
		 		
		 	if(level==1){
		 		label1=label1+"@@KB";		 		 
		 			datas1[i] = {"label":record.businessType,"value":StringToFloat(record.totalFlushKB)};			
		 	}else if(level==3){
		 		label1=label1+"@@GB";
		 			datas1[i] = {"label":record.businessType,"value":StringToFloat(record.totalFlushGB)};
		 	}else{
		 		label1=label1+"@@MB";		 		
		 			datas1[i] = {"label":record.businessType,"value":StringToFloat(record.totalFlushMB)};
		 	}
		 	
		 	datas2[i] = record.imsisRate;
			xlabels2[i] = record.businessType;	
			showxlabels[i]=record.businessType;	
			showxdatas [i] =record.intImsis;
			
			datas3[i] = {"label":record.businessType,"value":StringToFloat(record.visit)};						
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
		barchartinit = false;
		document.getElementById("chartxmldata1").value=str1;
		document.getElementById("chartxmldata2").value=str2;
		document.getElementById("chartxmldata3").value=str3;
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=flush", "imgarea1", "40%", "100%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=imsis", "imgarea2", "59%", "100%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=count", "imgarea3", "0%", "0%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
	}else{
	  	var tmp1 = findSWF("imgarea1");	  	
  		tmp1.load(str1);
  		var tmp2 = findSWF("imgarea2");
  		tmp2.load(str2);
  		var tmp3 = findSWF("imgarea3");
  		tmp3.load(str3);
  		
	}
	}else{
		chartinit = false;
		var str1 = JSON.stringify(buildEmptyAxisChart());
		document.getElementById("chartxmldata1").value=str1;
		document.getElementById("chartxmldata2").value=str1;
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=flush", "imgarea1", "100%", "100%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
		swfobject.embedSWF("/flash/open-flash-chart.swf?id=imsis", "imgarea2", "0%", "0%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
		swfobject.embedSWF("/flash/open-flash-chart.swf?id=count", "imgarea3", "0%", "0%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
		return;
	}
}

/*初始化图表*/
function open_flash_chart_data(id)
{
	$('#d_businessss').attr('disabled','');
	if(id=='flush'){
		return document.getElementById("chartxmldata1").value;
	}else if(id=='imsis'){
		return document.getElementById("chartxmldata2").value;
	}else if(id=='count'){
		return document.getElementById("chartxmldata3").value;
	}else{
		return document.getElementById("chartxmldata9").value;
	}	
}
function getTotalImsiByBusi(busi){
	if(typeof(datagrid.getRecord(0))!="undefined"){
		for(var i = 0 ; i<datagrid.getAllRows().length;i++){	
			var record = datagrid.getRecord(i);	 
			if(record.businessType==busi){
				return record.intImsis;
			}
		}
	}
}
/*图表回调函数*/
function callback(label){
	var obj =new Object();
	if(flag==0){
		obj.dataTypeValue = dataTypeValue;
		obj.searchDateStart = searchDateStart ;
		obj.timeLevel_search = timeLevel_search ;
		obj.businessTypeName = label;
		obj.totalImsis = getTotalImsiByBusi(label);
	 	var value = window.showModalDialog("/mantoeye/data_analyse/context_in_contexttype.jsp",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
	}else{
		obj.dataTypeValue = dataTypeValue;
		obj.searchDateStart = searchDateStart ;
		obj.timeLevel_search = timeLevel_search ;
		obj.businessName = label;
		var value = window.showModalDialog("/mantoeye/data_analyse/business_in_space.jsp",obj,"dialogWidth="+w+";dialogHeight="+h+";location=yes;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
	}
}
///////////////////////////////////////////



var flagFirst=0;

//// 查询表单的查询函数 ////
function query() {
	datagrid.sortInfo = null;
	flushchartjson="";
	imsischartjson="";
	firstSort = true;
	//重置分页数据
	datagrid.setTotalRowNum(-1);
	parent.total_obj.total_time_level=null;
	parent.total_obj.total_time_search=null;
	datagrid.loadURL ='/contextDistribute_query.do?1=1&total_time_level='+parent.total_obj.total_time_level+'&total_time_search='+parent.total_obj.total_time_search;
	/**
	if(document.getElementById("d_dataType1").checked==true){
		dataTypeValue=0;
	}else{
		dataTypeValue=1;
	}*/
	timeLevel_search = $("#d_timeLevel option:selected").val();
	business=$("#business").val().trim();
	if(businessType=='business'){
		flagFirst++;
		if(flagFirst>1){
			hasinit=true;
		}
		if(business==''){
			alert('请最少选择一个内容作为查询条件!');
			return;
		}
	}else{
		hasinit1=true;
	}
	hasquery = true;
	searchDateStart=$("#d_searchDateStart").val().trim();
	if(searchDateStart==''){
		alert('请选择查询时间!');
			return;
	}
	var param={
		searchDateStart : searchDateStart,
		timeLevel :timeLevel_search,
		business : business,
		businessType : businessType,
		dataType :dataTypeValue
	};
	GT.$grid('datagrid').query( param );
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
				searchDateStart : searchDateStart,
				timeLevel :timeLevel_search,
				//business : business,
				businessType : businessType,
				dataType :dataTypeValue
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName='内容分布数据';//('+startTime_search+'至'+endTime_search+')';//文件名称
			fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
			fileObj.gridObj = datagrid;//当前grid对象
			fileObj.params = params;
			exportFile(fileObj);
          }
        }]
      });
	  toolbar.render();
});
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
	var $inputObj=$("input[type='text']").not( $(".Wdate")[0] );
	$inputObj.each(function(){
		$obj=$(this);
		$obj.bind("focus",function(){
			$(this).css("background","#F5E5E1");
		});
		$obj.bind("blur",function(){
			$(this).css("background","#ffffff");
		});
	})
})
function openSapceDistribute(num,bis,imms){
	var obj =new Object();
	if(num==2){
		obj.dataTypeValue = dataTypeValue;
		obj.searchDateStart = searchDateStart ;
		obj.timeLevel_search = timeLevel_search ;
		obj.businessTypeName = bis;
		obj.totalImsis = imms;
		
		$.ajax({
			type : "post",
			url : "contextDistribute_isExistLeaf.do?1=1",
			data : {
				groupName:bis
			},
			success : function(xml) {
				if(xml=='true'){
					var value = window.showModalDialog("/mantoeye/data_analyse/context_in_contexttype.jsp",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
				}else{
					var value = window.showModalDialog("/mantoeye/data_analyse/context_in_contexttype_v3.jsp",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
				}
			},
			error : function() {
				//alert('查询数据出错!');
			}
		});
	 	
	}else{
		obj.dataTypeValue = dataTypeValue;
		obj.searchDateStart = searchDateStart ;
		obj.timeLevel_search = timeLevel_search ;
		obj.businessName = bis;
		var value = window.showModalDialog("/mantoeye/data_analyse/business_in_context_v2.jsp",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
	}
}

    
    function selectBusiness(bis){
    	businessType=$(bis).val();
    	if(businessType=='business'){
    		document.getElementById("business").style["display"]="block";
    	}else{
    		document.getElementById("business").style["display"]="none";
    	}
    }
    
    	//重置查询条件
function reset(){
	$('#d_searchDateStart').attr("value","");
	$("#d_timeLevel").get(0).selectedIndex=1;
	//$('#d_dataType1').attr("checked","true");//设置radio默认值
	$("#d_businessType").get(0).selectedIndex=0;//内容类型
	document.getElementById("business").style["display"]="none";
	$("#business").attr("value","");//区域维度
}
	var dialog = null;
	function showDialog(){
		if(dialog == null){
		  dialog = new SymbolDialog({
	        renderTo : 'business',//点击触发对话框ID
	        hiddenTo:'businessIds',//页面与对话框参数传递
			id:'businessDialog',//对话框ID
			typeSearchName:'类型',
			showLoading:true,
			modelSearchName:'内容',
			title:'内容复选对话框',//对话框标题
			url:'contextDistribute_initBussnessDialogData.do'//对话框加载后台数据URL
	      });
		  dialog.init();//初始化页面DOM对象
		  dialog.loadData();//加载后台数据
		  dialog.display();//显示
		}else{
		  dialog.display();//显示(第N次以后只显示对话框即可,提高效率)
		}
	}
/**
脚本不出错*/
	
$(document).ready(function(){
	window.onerror = killErrors;
})
function killErrors() {
	return true;
}


</script>

