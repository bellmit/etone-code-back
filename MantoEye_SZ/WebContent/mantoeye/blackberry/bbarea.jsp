<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>BlackBerry区域分布</title><meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" type="text/css"
			href="/tools/gt-grid/gt_grid.css" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/maincontent.css" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/jquery.autocomplete.css" />
		<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
		<script type='text/javascript' src='/js/jquery.autocomplete.js'></script>
		<!-- 列表工具栏 -->
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
			type="text/css"></link>
		<script type="text/javascript"
			src="/js/jquery/extend.tab/js/Toolbar.js"></script>
		<script type="text/javascript" src="/js/common_grid.js"></script>
		<script type="text/javascript" src="/js/common.js"></script>
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
						<tr valign="top" style="font-size:1px;">
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

													<td class="condition_name">
														时间粒度:
													</td>
													<td class="condition_value">
														<div class="select">
															<div>
																<select name="timeLevel" id="d_timeLevel"
																	style="height: 21px" onchange="selChange();">
																	<option value="1"
																		<c:if test="${fn:contains(timeLevel,'1') }">selected</c:if>>
																		小时
																	</option>
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
													</td>
													<td class="condition_name" style="width:50px;">
														时间:
													</td>
													<td class="condition_value">
														<input type="text" class="Wdate" style="height: 15px;"
															onclick="preStartDate();" name="searchDateStart"
															value="${searchDateStart}" id="d_searchDateStart" />
														<input type="hidden" id="d_searchDateEnd" />
													</td>
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
														区域类型:
													</td>
													<td class="condition_value">
														<div class="select">
															<div>
																<select class="condition_value" name="spaceLevel_search"
																	style="height: 20px" id="spaceLevel_search"
																	onchange="showAreaType()">
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
															</div>
														</div>
													</td>
													<!--<td id="spaceTd" width="*"></td>
													--><td width="120px">
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
												黑莓空间分布图形
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
							<td height="100%" width="100%"
								style="padding-left: 5px; background: #ffffff">
									<input type="checkbox" id="total_flush" class="checkboxclass"
									onclick='changeChartType()' checked />
								总流量
								<input type="checkbox" id="imsis_count" class="checkboxclass"
									onclick='changeChartType()' />
								用户数
								<div id="total_flush_image" style="width: 100%; height: 100%;margin-left:-13px;overflow:hidden;">
									<iframe name="frm"
										src="/flash/MantoEye_Main.html?mapflag=bsc&dataflag=total&dataname=总流量"
										scrolling="no" id="frm"
										style="HEIGHT: 350px; VISIBILITY: inherit; width: 1038px; Z-INDEX: 1"
										marginwidth="1" marginheight="1"  frameborder="0"
										align="middle"></iframe>
								</div>
								
								<div id="imsis_count_image" style="width: 0; height: 0;margin-left:-13px;overflow:hidden;">
									<iframe name="frm1"
										src="/flash/MantoEye_Main.html?mapflag=bsc&dataflag=imsis&dataname=用户数"
										scrolling="no" id="frm1"
										style="HEIGHT: 350px; VISIBILITY: inherit; width: 1038px; Z-INDEX: 1"
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
												黑莓空间分布列表
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
var hasinit = false;//第一次初始化图形数据标识
var datachange = false;//第N次查询初始化数据标识
var dataType_search = 1 ; //页面显示数据类型
var timeLevel_search ='2';//查询时间粒度
var time_search ;//查询开始时间
var spaceLevel_search = '1';//区域维度(BSC:1 SGSN:2 街道办:3 营销片区:4 分公司:5	)
var chartFlag = 'bsc';//地图显示标识
/**************************地图定义结束***********/
var hasinit = false;//第一次初始化图形数据标识
var datachange = false;//第N次查询初始化数据标识
var dataTypeValue = 1 ; //页面显示数据类型
var timeLevel_search =2;//查询时间粒度
var d_searchDateStart ;//查询开始时间
var spaceLevel_search = 1;//区域维度(BSC:1 SGSN:2 街道办:3 营销片区:4 分公司:5	)
var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :[
		{name : 'fullDate'        },
		{name : 'businessName'      },
		{name : 'upFlushMB'       },
		{name : 'downFlushMB'     },
		{name : 'totalFlushMB'     },
		{name : 'intImsis'  },
		{name : 'nmAveFlushKB'  }
	]
};
var colsConfig = [
		{ id : 'fullDate'      , header : "时间"  ,sortable:false   },
		{ id : 'businessName'      , header : "区域维度"   ,sortable:false   },
		{ id : 'upFlushMB'    , header : "上行流量(MB)",headAlign:'right' ,align:'right' ,exportnumber:true  ,renderer:renderFormatDataInit2  },
		{ id : 'downFlushMB'     , header : "下行流量(MB)",headAlign:'right' ,align:'right' ,exportnumber:true  ,renderer:renderFormatDataInit2   },
		{ id : 'totalFlushMB'  , header : "总流量(MB)",headAlign:'right' ,align:'right',exportnumber:true ,renderer:renderFormatDataInit2 },
		{ id : 'intImsis' , header : "用户数",headAlign:'right' ,align:'right'   ,exportnumber:true  },
		{ id : 'nmAveFlushKB' , header : "平均流量(KB/人)",headAlign:'right' ,align:'right' ,exportnumber:true  ,renderer:renderFormatDataInit2   }
];
var gridConfig={
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/blackberryareadistribute_query.do?1=1',
	exportURL :'/blackberryareadistribute_export.do?1=1' ,
	//saveURL : '/flexiGridTest_list1.do?1=1' ,
	dataset : dsConfig ,
	columns : colsConfig ,
	width:780,
	height:500,
	resizable : false,
	container : 'data_container', 
	beforeLoad:checkLogon,
	toolbarContent : 'nav | goto | pagesize | state' ,
	pageSize : getDispalyPageSize(2,1) ,
	remoteSort : false ,
	remotePaging:false,
	pageSizeList :  [10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
	onComplete:loadComplate,
	onCellClick : showMapArea
};
//点击区域时，在地图上加亮显示该区域
function showMapArea(value, record , cell,row, colNO, rowNO,column,event){
	if(value==record.businessName){
		//alert(record.businessName);
		document.frm.MantoEye_Main.areaActive(value);
		document.frm1.MantoEye_Main.areaActive(value);
	}else{
		document.frm.MantoEye_Main.areaActive("-");
		document.frm1.MantoEye_Main.areaActive("-");
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
	obj.hideColumn = 0 ;//隐藏的列数目
	obj.isCheckbox = false;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度
		
	var totalRecords = datagrid.getAllRows().length;//获取加载到的数据记录数目
	var width=window.screen.availWidth;
	
	//判断是否查询到数据
	judgeData(datagrid);
	//初始化grid
	initGridInfo(obj);
	if(firstInit==true){
		var pageSize=getDispalyPageSize(2,2);
		var index=0;
		$(".gt-pagesize-select option").each(function(i){
			if(this.text==pageSize){
				index=i;
			}
		})
		firstInit=false;
		$(".gt-pagesize-select").get(0).selectedIndex=index;
	}	
	time_search = $('#d_searchDateStart').val();
	if(time_search==''){
		time_search=new Date();
		time_search=time_search.valueOf();
		time_search=time_search-24*60*60*1000;
		time_search=new Date(time_search); 
		var month=((time_search.getMonth()+1)>9?(time_search.getMonth()+1):("0"+(time_search.getMonth()+1)));
		var day=(time_search.getDate()>9?time_search.getDate():("0"+time_search.getDate()));
		$('#d_searchDateStart').val(time_search.getYear()+"-"+month+"-"+day);
		time_search = $('#d_searchDateStart').val();
	}
	//图形数据
	if(!hasinit){
		getAjaxChartXmlData();
	}
}
var hasgetdata = false;//是否有初始化图形数据
//加载图形数据入口
function loadDataString(){
	var xml =document.getElementById("chartxmldata").value;		
	if(xml!=null&&xml!=''){
		hasgetdata = true;
	}
	return xml;
}
/*****************地图定义******************/

//改变地图事件
function changeChartType(){
	var mapflag = 'bsc';
	$('.checkboxclass"').each(function(){
		if(this.checked==true){
			$('#'+this.id+"_image").get(0).style["width"]="100%"	
			$('#'+this.id+"_image").get(0).style["height"]="100%"			
		}else{
			$('#'+this.id+"_image").get(0).style["width"]="0"	
			$('#'+this.id+"_image").get(0).style["height"]="0"	
		}
	})
	
}
//获取服务器端图形数据
function getAjaxChartXmlData(){
	$.ajax({
		type : "post",
		url : "blackberryareadistribute_getAjaxChartXmlData.do",
		data : {
			dataTypeValue:1,
			timeLevel_search:timeLevel_search,
			time_search:time_search,
			spaceLevel_search:spaceLevel_search
			
		},
		success : function(xml) {
			document.getElementById("chartxmldata").value=xml;
			if(!hasinit && datachange){
				document.frm.MantoEye_Main.changeData(chartFlag);
				document.frm1.MantoEye_Main.changeData(chartFlag);
			}
			else if(!hasgetdata){
				document.frm.MantoEye_Main.changeData(chartFlag);
				document.frm1.MantoEye_Main.changeData(chartFlag);
			}
			hasinit = true;
		},
		error : function() {
			alert('服务器出错,请联系管理员!');
		}
	});
}
//// 查询表单的查询函数 ////
function query() {
	//重置分页数据
	var pageinfo = datagrid.getPageInfo();
	pageinfo.pageNum = 1;
	datagrid.setPageInfo(pageinfo);
	timeLevel_search = $("#d_timeLevel option:selected").val();
	time_search = $('#d_searchDateStart').val();
	spaceLevel_search = $('#spaceLevel_search').val();
	
	/**if(document.getElementById("d_dataType1").checked==true){
		dataTypeValue=0;
	}else{
		dataTypeValue=1;
	}*/
	if(time_search==''){
		alert('请选择时间!');
		return ;
	}
	hasinit = false;
	datachange = true;
	var param={
		dataTypeValue: dataTypeValue,
		timeLevel_search: timeLevel_search,
		time_search: time_search,
		spaceLevel_search: spaceLevel_search
		
	};
	GT.$grid('datagrid').query( param );
}
function reset(){
	$('#d_searchDateStart').attr("value","");
	$("#d_timeLevel").get(0).selectedIndex=1;
	//$('#d_dataType1').attr("checked","true");//设置radio默认值
	$("#spaceLevel_search").get(0).selectedIndex=0;
	selChange();
	$("#CityLocal").attr("value","");
	
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
//初始化grid工具栏
$(document).ready(function(){
		if(width==1024){
			$("#frm").css("width","1025px");
			$("#frm1").css("width","1025px");
		}
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
				dataTypeValue: dataTypeValue,
				timeLevel_search: timeLevel_search,
				time_search: time_search,
				spaceLevel_search: spaceLevel_search
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName='BB业务空间分布数据';//('+startTime_search+'至'+endTime_search+')';//文件名称
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


