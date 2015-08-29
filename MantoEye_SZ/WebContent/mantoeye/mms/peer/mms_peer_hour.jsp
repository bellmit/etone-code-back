<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>点对点彩信时段分布</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" type="text/css"
			href="/tools/gt-grid/gt_grid.css" />
		<link type="text/css" rel="stylesheet"
			href="/skin//Default/css/maincontent.css" />
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
			type="text/css"></link>
		<script type="text/javascript" src="/js/common.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
			<script type="text/javascript" src="/js/common_grid.js"></script>
			<!-- 列表工具栏 -->
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
			type="text/css"></link>
		<script type="text/javascript"
			src="/js/jquery/extend.tab/js/Toolbar.js"></script>
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
												cellpadding="0px;" border="0">
												<tr valign="middle">												
													<td style="width: 72px" style="display:none">
														<input type="radio" name="d_raittype" id="d_raittype"
															value="1" checked style="width: 20px; border: 0px;" />
														GPRS
													</td>
													<td style="width: 72px" style="display:none">
														<input type="radio" name="d_raittype" value="2"
															id="d_raittype1" style="width: 20px; border: 0px;" />
														TD
													</td>
												<%-- 	<td class="condition_name">
														时间粒度：
													</td>
													<td>
														<div class="select">
															<div>
																<select  style="height: 21px" onchange="selChange();"
																name="timeLevel" id="d_timeLevel">																	
																	<option value="2" selected
																		<c:if test="${fn:contains(timeLevel,'2') }">selected</c:if>>
																		天
																	</option>

																	<option value="3"
																		<c:if test="${fn:contains(timeLevel,'3') }">selected</c:if>>
																		周（周日~周六）
																	</option>
																	<option value="4"
																		<c:if test="${fn:contains(timeLevel,'4') }">selected</c:if>>
																		月
																	</option>
																</select>
															</div>
														</div>
													</td>--%>
													<td class="condition_name">
														时间：
													</td>
													<td class="condition_value" id="time_space_start">
														<input type="text" class="Wdate"
															style="display: block; height: 17px;"
															onclick="new WdatePicker({maxDate:'#F{$dp.$D(\'endTime_search\')}',dateFmt:'yyyy-MM-dd HH:00'})" 
															id="startTime_search"
															name="startTime_search" />
													</td>
													<td style="width: 5px;">
														&nbsp;到&nbsp;
													</td>
													<td class="condition_value" id="time_space_end">
														<input type="text" class="Wdate"
															style="display: block; height: 17px;"
															onclick="new WdatePicker({minDate:'#F{$dp.$D(\'startTime_search\')}',dateFmt:'yyyy-MM-dd HH:00'})" 
															id="endTime_search"
															name="endTime_search" />
													<td>
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
												点对点彩信时段分布图形
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
								<table cellspacing="0" cellpadding="0" border="0" width="100%">
									<tr>
										<td width="4px" height="24px;">
											<img
												src="/skin/Default/images/MantoEye/maincontent/lefttitle.gif" />
										</td>
										<td width="100%"
											style="background: url(/skin/Default/images/MantoEye/maincontent/middletitle.gif) repeat-x; padding-left: 10px; font-size: 12px; font-weight: bold; color: #FFFFFF; vertical-align: middle;">
											点对点彩信时段分布列表
										</td>
										<td width="4px">
											<img
												src="/skin/Default/images/MantoEye/maincontent/righttitle.gif" />
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
				<td width="2"></td>
			</tr>
		</table>
	</body>
</html>

<script type="text/javascript">
//初始化查询时间
var date=new Date();
date=date.valueOf();
date=date-24*60*60*1000;
date=new Date(date); 
var hasinit = false;//第一次初始化图形数据标识
var timeLevel_search ='1';//查询时间粒度
var initChartAgain = false;//判断是否再次初始化图形
var endTime_search =date.getYear()+'-'+((date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1)))+'-'+(date.getDate()>9?date.getDate():("0"+date.getDate()))+" 23:00";//查询结束时间
var startTime_search=date.getYear()+'-'+((date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1)))+'-'+(date.getDate()>9?date.getDate():("0"+date.getDate()))+" 00:00";//查询开始时间
//重置查询条件
function reset(){
	$('#startTime_search').attr("value","");
	$('#endTime_search').attr("value","");
//	$("#d_timeLevel").get(0).selectedIndex=1;
//	$('#d_raittype').attr("checked","true");//设置radio默认值
}
//初始化查询时间
$(document).ready(function(){
	$('#startTime_search').attr('value',startTime_search);
	$('#endTime_search').attr('value',endTime_search);
});

var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :[
		{name : 'upCount'          },
		{name : 'percentUpCount'          },
		{name : 'downCount'        },
		{name : 'percentDownCount'        },
		{name : 'totalCount'      },
		{name : 'percentTotalCount'      },
		{name : 'fullDate'    },
		{name : 'hourName'    }
	]
};
var colsConfig = [
		{ id : 'fullDate'      , header : "时间"  ,hidden:true   },
		{ id : 'hourName'      , header : "时段"     },
		{ id : 'upCount'      , header : "上行发送量(条)"  ,headAlign:'right' ,align:'right' ,exportnumber:true  },
		{ id : 'percentUpCount'      , header : "上行占比(%)"   ,headAlign:'right'  ,align:'right'  ,renderer:renderFormatDataInit2 },
		{ id : 'downCount'    , header : "下行发送量(条)"  ,headAlign:'right' ,align:'right' ,exportnumber:true },
		{ id : 'percentDownCount'     , header : "下行占比(%)"    ,headAlign:'right' ,align:'right',renderer:renderFormatDataInit2   },
		{ id : 'totalCount'  , header : "总发送量(条)"  ,headAlign:'right' ,align:'right' ,exportnumber:true},
		{ id : 'percentTotalCount' , header : "总占比(%)"  ,headAlign:'right'  ,align:'right' ,renderer:renderFormatDataInit2  }
];

var gridConfig={
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/peerMmsHour_query.do?1=1',
	exportURL :'/peerMmsHour_export.do?1=1' ,
	dataset : dsConfig ,
	columns : colsConfig ,
	resizable : false,
	showGridMenu : false,
	container : 'data_container', 
	beforeLoad:checkLogon,
	toolbarPosition : 'bottom',
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
} );

var hasinit = false;
var firstInit=true;
function loadComplate(){
	//grid列表初始化信息
	var obj = new Object ();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.hideColumn = 1 ;//隐藏的列数目
	obj.isCheckbox = false;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度
	//判断是否查询到数据
	judgeData(datagrid);
	//初始化grid
	initGridInfo(obj);
	
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
	//buildChartData(datagrid.getAllRows().length);
}
/*
function packChartData(totalRecords){
	var chartxmldata = '';
	var chartTime = $('#startTime_search').attr('value')+"~"+ $('#endTime_search').attr('value');
	chartxmldata = '<root><chart id="1" name="'+chartTime+' SP彩信分时段统计分布图" fields="发送量|">';
	var chartBody = '';
	if(totalRecords>1){
		for(var i = 0 ; i<totalRecords;i++){
			var record = datagrid.getRecord(i);
			chartBody=chartBody+'<data label="'+record.hour+'" shortlabel="'+record.hour+'" total="'+record.totalCount+'"/>';
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
}*/
///////////////////图表////////////////////////
//获取服务器端图形数据
var rrrrtype = '1';
function getAjaxChartXmlData(){
	$.ajax({
		type : "post",
		dataType: 'json',
		url : "peerMmsHour_getAjaxChartData.do?1=1",
		data : {
			startdate : startTime_search,
			enddate : endTime_search,
			raittype : rrrrtype
		},
		success : function(sjson) {
			//alert("sjson:"+sjson);
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
/*图表是否已经初始化*/
var chartinit = false;
/*组装图形数据*/
function buildChartData(json){

	var title1 =  startTime_search +"至" +endTime_search + " 统计分布图";

	var xlabels = [] ;
	var datas1 = [] ;//	

	if(json!=''&&json.length>0){
		for(var i = 0 ; i<json.length;i++){	
				var record = json[i];	
			//if(i<50){//图形只显示前50 的数据
				xlabels[i] = record.dimensionName; 	
				datas1[i] = StringToFloat(record.totalCountFormat);
			//} 			
		}
	
	var showxlabels = xlabels;
	
	var tdatas1 = [];
	//格式化X轴数据（发送量）
	xlabels = formatXDateLables(xlabels,12,"hour");
	  //格式化Y轴数据（发送量）
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

//// 查询表单的查询函数 ////
function query() {
	//重置分页数据
	datagrid.setTotalRowNum(-1);

	hasinit = false;
	initChartAgain = true;
	var istd = GT.U.getValue(GT.$('d_raittype'));
	var istd1 = GT.U.getValue(GT.$('d_raittype1'));
	startTime_search = $('#startTime_search').attr("value");
	endTime_search = $('#endTime_search').attr("value");	//设置图形标志
	rrrrtype = istd||istd1;
	if(startTime_search=='' || endTime_search==''){
		alert('请选择时间跨度!');
		return ;
	}
	var param={
		startdate : startTime_search,
		enddate : endTime_search,
		raittype : rrrrtype
	};
	GT.$grid('datagrid').query( param );
}
$(document).ready(function(){
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
//初始化toolbar
$(document).ready(function(){
      var toolbar = new Toolbar({
        renderTo : 'toolbar',
		//border: 'top',
        items : [{
          type : 'button',
          text : '导出',
          bodyStyle : 'xls',
          useable : 'T',
          handler : exportToXls
        }]
      });
	  toolbar.render();
    });
 function exportToXls(){
	var params={
		startdate : startTime_search,
		enddate : endTime_search,
		raittype : rrrrtype
	};
			//导出
	var fileObj = new Object();
	fileObj.fileName='点对点彩信时段分布';//('+startTime_search+'至'+endTime_search+')';//文件名称
	fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
	fileObj.gridObj = datagrid;//当前grid对象
	fileObj.params = params;
	exportFile(fileObj);
 }
/**
脚本不出错

$(document).ready(function(){
	window.onerror = killErrors;
})
function killErrors() {
	return true;
}*/
</script>


