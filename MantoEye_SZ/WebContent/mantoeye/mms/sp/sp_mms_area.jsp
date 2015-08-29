<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>SP彩信按区域统计</title>
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
		<script type="text/javascript" src="/js/common_grid.js"></script>
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
													<td>
														<select class="condition_value" name="spaceLevel_search"
															style="height: 21px" id="spaceLevel_search"
															onchange="showAreaType()">
															<option value="1" selected>
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
													</td>
													
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
												SP彩信按区域统计图形
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
							<td height="350px" width="100%"
									style="padding-left: 5px;padding-top:15px; background: #ffffff">
								<div id="imgarea1" style="width: 100%; height: 100%;margin-left:-13px;">
									<iframe name="frm"
										src="/flash/MantoEye_Main.html?mapflag=bsc&dataflag=mms&dataname=彩信发送量"
										scrolling="no" id="frm"
										style="HEIGHT: 350px; VISIBILITY: inherit; width: 1035px; Z-INDEX: 1"
										marginwidth="1" marginheight="1"  frameborder="0"
										align="middle"></iframe>
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
												SP彩信按区域统计列表
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
date=date.valueOf();
date=date-24*60*60*1000;
date=new Date(date); 
var hasinit = false;//第一次初始化图形数据标识
var datachange = false;//第N次查询初始化数据标识
var dataType_search = 1 ; //页面显示数据类型
var timeLevel_search ='2';//查询时间粒度
var time_search =date.getYear()+'-'+((date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1)))+'-'+(date.getDate()>9?date.getDate():("0"+date.getDate())) ;//查询开始时间
var spaceLevel_search = '1';//区域维度(BSC:1 SGSN:2 街道办:3 营销片区:4 分公司:5	)

var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :[
		{name : 'fullDate'        },
		{name : 'businessName'    },
		{name : 'totalSendFlush'      },
		{name : 'flushRate'    },
		{name : 'allFlushRate' }
	]
};

var colsConfig = [
		{ id : 'fullDate'      , header : "时间"   ,sortable:false  },
		{ id : 'businessName'     , header : "区域"   ,sortable:false  },
		{ id : 'totalSendFlush'  , header : "发送量(条)"  ,headAlign:'right' ,align:'right' ,exportnumber:true},
		{ id : 'flushRate' , header : "SP彩信占比(%)"    ,headAlign:'right' ,align:'right',renderer:renderFormatDataInit4  },
		{ id : 'allFlushRate'  , header : "全网占比(%)" ,headAlign:'right' ,align:'right',renderer:renderFormatDataInit4  }
];

var gridConfig={
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/spMmsArea_query.do',
	exportURL :'/spMmsArea_export.do?1=1' ,
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
	pageSize : getDispalyPageSize(2,1)  ,
	remoteSort : true ,
	pageSizeList :  [10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
	defaultRecord :  { no : 111 , name :'', age : '', gender : 'U' , english : 50 , math :50 },
//	customHead : 'myHead',
	onComplete:loadComplate,
	onCellClick : showMapArea
};
//点击区域时，在地图上加亮显示该区域
function showMapArea(value, record , cell,row, colNO, rowNO,column,event){
	if(value==record.businessName){
		//alert(record.businessName);
		document.frm.MantoEye_Main.areaActive(value);
	}else{
		document.frm.MantoEye_Main.areaActive("-");
	}
}
var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
} );

var firstInit=true;

function loadComplate(){
	//grid列表初始化信息
	var obj = new Object ();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.hideColumn = 0;//隐藏的列数目
	obj.isCheckbox = false;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度
	//判断是否查询到数据
	judgeData(datagrid);
	//初始化grid
	initGridInfo(obj);
	
	if(firstInit==true){
		var pageSize=getDispalyPageSize(2,1);
		var index=0;
		$(".gt-pagesize-select option").each(function(i){
			if(this.text==pageSize){
				index=i;
			}
		})
		firstInit=false;
		$(".gt-pagesize-select").get(0).selectedIndex=index;
	}
	//图形数据
	if(!hasinit){
		getAjaxChartXmlData();
	}
}


/*****************地图定义******************/
var chartFlag = 'bsc';//地图显示标识
function flashAreaDbClick(areaName){
	//alert('双击地图');
}

//加载图形数据入口
var hasgetdata = false;
function loadDataString(){
	var xml =document.getElementById("chartxmldata").value;	
	if(xml!=null&&xml!=''){
		hasgetdata = true;
	}
	return xml;
}

//获取服务器端图形数据
function getAjaxChartXmlData(){
	$.ajax({
		type : "post",
		url : "spMmsArea_getAjaxChartXmlData.do",
		data : {
			dataType_search:dataType_search,
			timeLevel_search:timeLevel_search,
			time_search:time_search,
			spaceLevel_search:spaceLevel_search
		},
		success : function(xml) {
			document.getElementById("chartxmldata").value=xml;
			if(!hasinit && datachange){
				document.frm.MantoEye_Main.changeData(chartFlag);
			}else if(!hasgetdata){
				document.frm.MantoEye_Main.changeData(chartFlag);
			}
			hasinit = true;
		},
		error : function() {
			alert('服务器出错,请联系管理员!');
		}
	});
}

/**************************地图定义结束***********/

//grid查询
function query() {
	//重置分页数据
	datagrid.setTotalRowNum(-1);
	//查询条件
	timeLevel_search = $("#timeLevel_search option:selected").val();
	time_search = $('#time_search').attr("value");
	spaceLevel_search = $('#spaceLevel_search').attr("value");
	if(time_search=='' ){
		alert('请选择时间!');
		return ;
	}
	hasinit = false;
	datachange = true;
	var param={
		hasinit:hasinit,
		dataType_search:dataType_search,
		timeLevel_search:timeLevel_search,
		time_search:time_search,
		spaceLevel_search:spaceLevel_search
	};
	GT.$grid('datagrid').query( param );
}

//设置页面显示的数据类型
function showDataType(type){
	dataType_search = type ;
}

//显示的区域维度
function showAreaType(){
	var sls=$("#spaceLevel_search option:selected").val(); 
	
	switch(sls)
	   {
	   case '1':
	   	 chartFlag = 'bsc';
	     break;
	   case '2':
	   	 chartFlag = 'sgsn';
	     break;
	   case '3':
	   	 chartFlag = 'street';
	     break;
	   case '4':
	   	 chartFlag = 'sale';
	     break;
	  case '5':
	   	 chartFlag = 'company';
	     break;   
	 }
}

//重置查询条件
function reset(){
	$('#time_search').attr("value","");
	$("#timeLevel_search").get(0).selectedIndex=0;
	$('#dataType_search_td_id input:first').attr("checked","true");//设置radio默认值
	$("#spaceLevel_search").get(0).selectedIndex=0;//区域维度
}

//初始化查询时间
$(document).ready(function(){
	$('#time_search').attr('value',time_search);
});

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
//时间改变事件
function changeTimeLevel(){
	$('#time_search').attr("value","");
}


//初始化toolbar
$(document).ready(function(){
	displayOrHiddenTd();
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
				time_search:time_search,
				spaceLevel_search:spaceLevel_search
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName='SP彩信区域统计数据';
			fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
			fileObj.gridObj = datagrid;//当前grid对象
			fileObj.params = params;
			exportFile(fileObj);
          }
        }]
      });
	  toolbar.render();
    });
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



