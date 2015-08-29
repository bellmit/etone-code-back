<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>终端类型分析</title>
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
		<script type="text/javascript" src="/flash/js/BarLineChartUtil.js"></script>
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
														<input type="radio" name='radio'
															style="width: 20px; border: 0px;"
															onclick='showDataType(1)' checked />
														GPRS
														<input type="radio" name='radio'
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
													<td class="condition_name">
														区域维度：
													</td>
													<td class="condition_value">
														<select name="areaType" id="areaType" style="height: 21px">
															<option value="6" selected>
																全网
															</option>
															<option value="1" >
																BSC
															</option>
															<option value="2">
																SGSN
															</option>
															  <option value="3">
																分公司
															</option>
															<option value="4">
																营销片区
															</option>
															<option value="5">
																街道办
															</option>
																												
														</select>
													</td>
													<td width="300px">
														<div id="mainbtn" style="width:300px">
															<ul>
																<li>
																	<a href="javascript:query();" 　title="查询"><span>查询</span>
																	</a>
																</li>												
															</ul>
														</div>
													</td>
												</tr>
											
											</table>
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
												终端类型分析图形
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
												终端类型分析数据
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
var datachange = true;//判断是否再次初始化图形
var dataType_search = 1 ; //页面显示数据类型
var timeLevel_search ='2';//查询时间粒度
var time_search =date.getYear()+'-'+''+((date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1)))+'-'+''+(date.getDate()>9?date.getDate():("0"+date.getDate())); //查询开始时间
var areaType = 6 ; //集团APN

var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :[
		{name : 'fullDate'        },
		{name : 'vcName'      },
		{name : 'nmAreaId'      },
		{name : 'vcTerminalNetType'      },
		{name : 'upFlushMB'        },
		{name : 'downFlushMB'      },
		{name : 'totalFlushMB'      },
		{name : 'upFlushKB'    },
		{name : 'downFlushKB'     },
		{name : 'totalFlushKB'    },
		{name : 'upFlushGB'      },
		{name : 'downFlushGB'     },
		{name : 'totalFlushGB'    },
		{name : 'nmAveFlushKB'   },
		{name : 'intImsis'   },
		{name : 'detail' }
	]
};

var w="1000px";
var h="600px";
if(width==1024){
	w="815px";
	h="600px";
}

//列表链接操作
function openDistributeBusiness(flag, vcTerminalNetType, vcName,nmAreaId) {
	var obj = new Object();
	obj.areaType = areaType;
	obj.timeLevel_search = timeLevel_search;
	obj.dataType_search = dataType_search;
	obj.time_search = time_search;
	obj.vcTerminalNetType = vcTerminalNetType;
	obj.vcName = vcName;
	obj.nmAreaId = nmAreaId
	if (flag == 1) {
		window.showModalDialog("/mantoeye/terminal/terminal_type_business.jsp",obj,"dialogWidth="+ w+ ";dialogHeight="+ h+ ";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
	}
	if (flag == 2) {
		window.showModalDialog("/mantoeye/terminal/terminal_type_brand.jsp",obj,"dialogWidth="+ w+ ";dialogHeight="+ h+ ";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
	}
}

function renderInit(value ,record,columnObj,grid,colNo,rowNo){
	var vcTerminalNetType = record.vcTerminalNetType;
	var vcName = record.vcName;
	var nmAreaId = record.nmAreaId;
	var thtml = "<table><tr>";
	thtml = thtml +'<td><img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/business.gif" alt="查看终端类型业务使用情况" onclick="openDistributeBusiness(1,\''+vcTerminalNetType+'\',\''+vcName+'\','+nmAreaId+');" /></td>';
	thtml = thtml +'</td><td></div><img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/wap.gif" alt="查看终端类型品牌明细" onclick="openDistributeBusiness(2,\''+vcTerminalNetType+'\',\''+vcName+'\','+nmAreaId+');" /></td>';
	thtml = thtml +  '</tr></table>';
	return thtml;
}

var colsConfig = [
		{ id : 'fullDate'      , header : "时间" ,sortable:false    },
		{ id : 'vcName'      , header : "区域" ,sortable:true   },
		{ id : 'nmAreaId'      , header : "nmAreaId" ,sortable:true ,hidden:true  },
		{ id : 'vcTerminalNetType'      , header : "终端类型" ,sortable:true   },
		{ id : 'upFlushMB'    , header : "上行流量(MB)"   ,headAlign:'right' ,align:'right'  ,exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'downFlushMB'     , header : "下行流量(MB)"   ,headAlign:'right' ,align:'right'  ,exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'totalFlushMB'  , header : "总流量(MB)",headAlign:'right' ,align:'right'  ,exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'upFlushKB'    , header : "上行流量(KB)",headAlign:'right' ,align:'right' ,exportnumber:true ,renderer:renderFormatDataInit2  },
		{ id : 'downFlushKB'     , header : "下行流量(KB)",headAlign:'right' ,align:'right' ,exportnumber:true ,renderer:renderFormatDataInit2   },
		{ id : 'totalFlushKB'  , header : "总流量(KB)",headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'upFlushGB'    , header : "上行流量(GB)",headAlign:'right' ,align:'right' ,exportnumber:true ,renderer:renderFormatDataInit2  },
		{ id : 'downFlushGB'     , header : "下行流量(GB)",headAlign:'right' ,align:'right' ,exportnumber:true  ,renderer:renderFormatDataInit2  },
		{ id : 'totalFlushGB'  , header : "总流量(GB)",headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'intImsis' , header : "用户数"     ,headAlign:'right' ,align:'right'  ,exportnumber:true},
		{ id : 'nmAveFlushKB'  , header : "平均流量(KB/人)",headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'detail'   , header : "操作" , sortable:false, exportable:false,
			renderer:renderInit
		}
];

var gridConfig={
	id : "datagrid",
	loadURL :'/terminalTypeAnlyse_query.do?1=1',
	exportURL :'/terminalTypeAnlyse_export.do?1=1' ,
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
	pageSize : getLongPageSize(),
	remoteSort : true ,
	remotePaging:true,
	pageSizeList :   [10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
	onComplete:loadComplate
};

var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
} );

function getLongPageSize(){
	var size = getDispalyPageSize(1,1);
	if(size<20){
		size = 20;
	}
	return size;
}

//页面呈现的单位
var showunit = 'MB';
var firstInit=true;
//grid回调函数
function loadComplate(){
	//grid列表初始化信息
	var obj = new Object ();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.hideColumn = 7 ;//隐藏的列数目
	obj.isCheckbox = false;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度
	//判断是否查询到数据
	//alert(initHasData+'===默認有數據nitHasData old');
	//alert(sortInit+'===默認第一次排序sortInit old');
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
	if(firstInit==true){
		//var pageSize=getDispalyPageSize(1,1);
		var pageSize=20;
		var index=0;
		$(".gt-pagesize-select option").each(function(i){
			if(this.text==pageSize){
				index=i;
			}
		})
		firstInit=false;
		$(".gt-pagesize-select").get(0).selectedIndex=index;
	}
	
	buildChartData(datagrid.getAllRows().length);	
	
}
/*图表是否已经初始化*/
var chartinit = false;
/*组装图形数据*/
function buildChartData(totalRecords){

	var title = time_search+" 流量|用户数区域分析图";
	
	var xlabels = [] ;
	var datas1 = [] ;
	var datas2 = [] ;
	
	if(typeof(datagrid.getRecord(0))!="undefined"){
		for(var i = 0 ; i<totalRecords;i++){	
			var record = datagrid.getRecord(i);	 	
			if(i<20){//图形只显示前20 的数据
				xlabels[i] = record.vcName; 	
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

//grid查询
function query() {
	//重置分页数据
	var pageinfo = datagrid.getPageInfo();
	pageinfo.pageNum = 1;
	datagrid.setPageInfo(pageinfo);
	time_search = $('#time_search').attr("value");
	timeLevel_search = $("#timeLevel_search option:selected").val();
	areaType = $("#areaType option:selected").val();;
	if(time_search=='' ){
		alert('请选择时间!');
		return ;
	}
	hasinit = false;
	datachange = true;
	var param={
		areaType:areaType,
		dataType_search:dataType_search,
		timeLevel_search:timeLevel_search,
		time_search:time_search
	};
	GT.$grid('datagrid').query( param );
}

/*****************查询条件**********************/



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
	   	 //new WdatePicker({isShowWeek:true,onpicked:function(){$dp.$D('time_search').value=$dp.cal.getP('W','第'+'W'+'周');}})
	     new WdatePicker({dateFmt:'yyyy-MM-dd'});
	     break;
	   case '4':
	   	 new WdatePicker({dateFmt:'yyyy-MM'});
	     break;
	 }
}
//重置查询条件
function reset(){
	$('#dataType_search_td_id input:first').attr("checked","true");//设置radio默认值
	$("#timeLevel_search").get(0).selectedIndex=1;
	$('#time_search').attr("value","");
}

//初始化查询时间
$(document).ready(function(){
	$('#time_search').attr('value',time_search);
});

//获取服务器端图形数据
/*
function getAjaxChartXmlData(){
	$.ajax({
		type : "post",
		url : "blocApnDistribute_getAjaxChartXmlData.do",
		data : {
			apnType:apnType,
			dataType_search:dataType_search,
			timeLevel_search:timeLevel_search,
			time_search:time_search
		},
		success : function(xml) {
			if(!hasinit && datachange){
				document.frm.ThreeDataPieChart.jsChangeData(xml);
				datachange = false;
			}else{
				document.getElementById("chartxmldata").value=xml;
			}
			hasinit = true;
		},
		error : function() {
			alert('服务器出错,请联系管理员!');
		}
	});
}*/
/*****************查询条件结束**********************/


/*****************图形****************************/
/*
function initChartData(param){
	return document.getElementById("chartxmldata").value;			
}
function changData(){
	var xml = document.getElementById("chartxmldata").value;
	document.frm.ThreeDataPieChart.jsChangeData(xml);
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
				areaType:areaType,
				dataType_search:dataType_search,
				timeLevel_search:timeLevel_search,
				time_search:time_search
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName='终端类型分析';
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


