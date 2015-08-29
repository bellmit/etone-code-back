<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>集团APN分布</title><meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
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
													<td class="condition_value" id="dataType_search_td_id" style="display: none">
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
																	<a href="javascript:parent.openTab('/mantoeye/data_trend/group_apn_trend.jsp','GROUP_APN_DATE','集团APN时间走势');" 
																		style="width:90px;"  title="时间走势"><span><img onclick="parent.openTab('/mantoeye/data_trend/group_apn_trend.jsp','GROUP_APN_DATE','集团APN时间走势');" 
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
												集团APN分布图形
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
							<div id="imgarea1" style="width: 25%; height: 100%"></div>	
							<!--  						
							<div id="imgarea3" style="width: 25%; height: 100%"></div>
							-->
							<div id="imgarea2" style="width: 49%; height: 100%"></div>
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
												集团APN分布数据
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
var apnType = 3 ; //集团APN

var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :[
		{name : 'fullDate'        },
		{name : 'businessName'      },
		{name : 'lineName'      },
		{name : 'company'      },
		{name : 'upFlushMB'         },
		{name : 'downFlushMB'       },
		{name : 'totalFlushMB'      },
		{name : 'totalFlush'    },
		{name : 'upFlushKB'    },
		{name : 'downFlushKB'   },
		{name : 'totalFlushKB'    },
		{name : 'upFlushGB'    },
		{name : 'downFlushGB'   },
		{name : 'totalFlushGB'   },
		{name : 'intImsis'    },
		{name : 'nmAveFlushKB'   },
		{name : 'activeCount'    }
	]
};
var colsConfig = [
		{ id : 'fullDate'      , header : "时间" ,sortable:false      },
		{ id : 'businessName'      , header : "集团APN" ,sortable:false      },
		{ id : 'lineName'      , header : "APN简称" ,sortable:false},
		{ id : 'company'      , header : "APN名称" ,sortable:false      },
		{ id : 'upFlushMB'    , header : "上行流量(MB)"    ,headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2  },
		{ id : 'downFlushMB'     , header : "下行流量(MB)"    ,headAlign:'right' ,align:'right',exportnumber:true ,renderer:renderFormatDataInit2 },
		{ id : 'totalFlushMB'  , header : "总流量(MB)" ,headAlign:'right' ,align:'right',exportnumber:true ,renderer:renderFormatDataInit2 },
		{ id : 'upFlushKB'    , header : "上行流量(KB)",headAlign:'right' ,align:'right' ,exportnumber:true  ,renderer:renderFormatDataInit2 },
		{ id : 'downFlushKB'     , header : "下行流量(KB)",headAlign:'right' ,align:'right' ,exportnumber:true  ,renderer:renderFormatDataInit2  },
		{ id : 'totalFlushKB'  , header : "总流量(KB)",headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'upFlushGB'    , header : "上行流量(GB)",headAlign:'right' ,align:'right' ,exportnumber:true ,renderer:renderFormatDataInit2  },
		{ id : 'downFlushGB'     , header : "下行流量(GB)",headAlign:'right' ,align:'right' ,exportnumber:true  ,renderer:renderFormatDataInit2  },
		{ id : 'totalFlushGB'  , header : "总流量(GB)",headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'intImsis' , header : "用户数"   ,headAlign:'right' ,align:'right',exportnumber:true },
		{ id : 'nmAveFlushKB'  , header : "平均流量(KB/人)",headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'activeCount' , header : "激活次数"    ,headAlign:'right' ,align:'right',exportnumber:true }
];

var gridConfig={
	id : "datagrid",
	loadURL :'/blocApnDistribute_query.do?1=1',
	exportURL :'/blocApnDistribute_export.do?1=1' ,
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
	pageSize : getDispalyPageSize(1,1),
	remoteSort : false ,
	remotePaging:false,
	pageSizeList :   [10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
	onComplete:loadComplate
};

var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
} );

var chartinit = false;
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
	//buildUnit();
	if(sortInit && initHasData){
		buildUnit();
		sortInit = false;
	}
	//初始化grid
	initGridInfo(obj);
	//初始化图表
	if(!hasinit&&datachange){
		//getAjaxChartXmlData();
		buildChartData(datagrid.getAllRows().length);
		datachange = false;	
	}
	//组装图形数据
	
}
///////////////////图表////////////////////////

/*组装图形数据*/
function buildChartData(totalRecords){
	var chartTime ='';
	var text1 = '';
	//如果有单位  则label与单位间用@@分开
	var label1 = '流量@@MB';	
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
	var otherRate = 0 ;
	var totaldataa = datagrid.dataset.data;
	
	var showlabel="";
	if(totaldataa.length>0){
		for(var i = 0 ; i<totaldataa.length;i++){	
			var record = totaldataa[i];	 	
		 	if(text1==''){
		 		chartTime = record.fullDate;
		 		text1 = chartTime+' 流量占比图';
		 		text2 = chartTime+' 用户数占比图';
		 		text3 = chartTime+' 激活次数占比图';
		 	}	
		 	if(i<10){ 	
				datas1[i] = {"label":record.lineName,"value":StringToFloat(record.totalFlushMB)};
				//datas2[i] = {"label":record.lineName,"value":StringToFloat(record.intImsis)};
				datas3[i] = {"label":record.lineName,"value":StringToFloat(record.activeCount)};
			
				datas2[i] = record.imsisRate;
				xlabels2[i] = record.lineName;	
				showxlabels[i]=record.lineName;
				showxdatas [i] =record.intImsis;
			}else{
				otherflush = otherflush + StringToFloat(record.totalFlushMB);
				otherimsis = otherimsis + StringToFloat(record.intImsis);
				othercount = othercount + StringToFloat(record.activeCount);
				otherRate = otherRate + StringToFloat(record.imsisRate+"");
			}			
		}
		//alert(otherflush);
		//数据超出10条 多余的用其他
		if(otherflush!=0){
			datas1[10] = {"label":"非前10合计","value":otherflush};
			//datas2[10] = {"label":"非前10合计","value":otherimsis};
			datas3[10] = {"label":"非前10合计","value":othercount};
			
			datas2[10] = otherRate;
			xlabels2[10] = "非前10合计";	
			showxlabels[10]="非前10合计";
			showxdatas [10] =otherimsis;
		}
		datas2 = buildHBarChartTip(datas2,showxlabels,label2,showxdatas,label22);
	
	var jsons1 = buildPieChartData(text1,label1,datas1);
	var str1 = JSON.stringify(jsons1);
	var jsons2 =buildPercentChartData(text2,[label2],xlabels2,[datas2]);
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
		var str2 = JSON.stringify(buildEmptyAxisChart());	
		document.getElementById("chartxmldata1").value=str2;
		document.getElementById("chartxmldata2").value=str2;
		document.getElementById("chartxmldata3").value=str2;
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=flush", "imgarea1", "100%", "100%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=imsis", "imgarea2", "0%", "0%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
		//swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=count", "imgarea3", "0%", "0%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
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
	var obj =new Object();	
	obj.dataTypeValue = dataTypeValue;
	obj.searchDateStart = searchDateStart ;
	obj.timeLevel_search = timeLevel_search ;
    obj.apnName = label;
	var value = window.showModalDialog("/mantoeye/data_analyse/space_in_apn.jsp",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
	
}
///////////////////////////////////////////

//grid查询
function query() {
	//重置分页数据
	var pageinfo = datagrid.getPageInfo();
	pageinfo.pageNum = 1;
	datagrid.setPageInfo(pageinfo);
	
	time_search = $('#time_search').attr("value");
	timeLevel_search = $("#timeLevel_search option:selected").val();
	if(time_search=='' ){
		alert('请选择时间!');
		return ;
	}
	hasinit = false;
	datachange = true;
	var param={
		hasinit:hasinit,
		apnType:apnType,
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
				dataType_search:dataType_search,
				timeLevel_search:timeLevel_search,
				time_search:time_search
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName='集团APN数据';
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



