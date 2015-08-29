<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>空间分布</title>
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
													<td class="condition_value" id="dataType_search_td_id">
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
													<td style="width:80px;">
														<select class="condition_value" name="spaceLevel_search"
															style="height: 21px;width:80px;" id="spaceLevel_search"
															onchange="">
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
													</td>
													<td width="300px">
														<div id="mainbtn" style="width:300px">
															<ul>
																<li>
																	<a href="javascript:query();" 　title="查询"><span>查询</span>
																	</a>
																</li>
																<li>
																	<a href="javascript:parent.openTab('/mantoeye/data_trend/space_distribute_trend.jsp','FLUSH_SPACE_DATE','空间时间走势');" 
																		style="width:90px;"  title="时间走势"><span><img onclick="parent.openTab('/mantoeye/data_trend/space_distribute_trend.jsp','FLUSH_SPACE_DATE','空间时间走势');"
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
												空间分布图形
												<font color="red">
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
							<td id="areaimage" height="350px" width="100%"
								style="padding-left: 5px;padding-top:5px; background: #ffffff">
								<input type="checkbox" id="total_flush" class="checkboxclass"
									onclick='changeChartType()' checked />
								总流量
								<input type="checkbox" id="imsis_count" class="checkboxclass"
									onclick='changeChartType()' />
								用户数
								<input type="checkbox" id="active_count" class="checkboxclass"
									onclick='changeChartType()' style="display:none"/>
								<!-- 访问次数 -->
								<div id="total_flush_image" style="overflow:hidden;width: 100%; height: 100%;margin-left:-13px;">
									<iframe name="frm"
										src="/flash/MantoEye_Main.html?mapflag=bsc&dataflag=total&dataname=总流量"
										scrolling="no" id="frm"
										style="HEIGHT: 350px; VISIBILITY: inherit; width: 1038px; Z-INDEX: 1"
										marginwidth="1" marginheight="1"  frameborder="0"
										align="middle"></iframe>
								</div>
								
								<div id="imsis_count_image" style="display:block;width: 0px; height:0px;margin-left:-13px;overflow:hidden;">
									<iframe name="frm1"
										src="/flash/MantoEye_Main.html?mapflag=bsc&dataflag=imsis&dataname=用户数"
										scrolling="no" id="frm1"
										style="HEIGHT: 350px; VISIBILITY: inherit; width: 1038px; Z-INDEX: 1"
										marginwidth="1" marginheight="1"  frameborder="0"
										align="middle"></iframe>
								</div>
								
								<div id="active_count_image" style="display:block;width: 0px; height: 0px;margin-left:-13px;overflow:hidden;">
									<iframe name="frm2"
										src="/flash/MantoEye_Main.html?mapflag=bsc&dataflag=count&dataname=访问次数"
										scrolling="no" id="frm2"
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
												空间分布数据
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
var dataType_search = 2 ; //页面显示数据类型
var timeLevel_search =parent.total_obj.total_time_level==null?'2':parent.total_obj.total_time_level;//查询时间粒度
var defaulettime =date.getYear()+'-'+''+((date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1)))+'-'+''+(date.getDate()>9?date.getDate():("0"+date.getDate())); //查询开始时间
var time_search = parent.total_obj.total_time_search==null?defaulettime:parent.total_obj.total_time_search;
var spaceLevel_search = '1';//区域维度(BSC:1 SGSN:2 街道办:3 营销片区:4 分公司:5	)
var chartFlag = 'bsc';//地图显示标识


var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :[
		{name : 'business'        },
		{name : 'fullDate'        },
		{name : 'businessName'      },
		{name : 'upFlushMB'        },
		{name : 'downFlushMB'      },
		{name : 'totalFlushMB'      },
		{name : 'upFlushKB'    },
		{name : 'downFlushKB'     },
		{name : 'totalFlushKB'    },
		{name : 'upFlushGB'      },
		{name : 'downFlushGB'     },
		{name : 'totalFlushGB'    },
		{name : 'localtotalFlushMB'}, 
		{name : 'localtotalFlushKB'}, 
		{name : 'localtotalFlushGB'},
		{name : 'localintImsis'},
		{name : 'nmAveFlushKB'   },
		{name : 'intImsis'   },
		{name : 'visit'   },
		{name : 'detail' },
	]
};

function renderInit(value ,record,columnObj,grid,colNo,rowNo){
	var businessName = record.businessName;
	return '<img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/business.gif" alt="查看业务分布" onclick="openDistributeBusiness(\''+businessName+'\');" />'
}
var colsConfig = [
		{name : 'business' ,hidden:true       }	,
		{ id : 'fullDate'      , header : "时间" ,sortable:false    },
		{ id : 'businessName'      , header : "区域" ,sortable:false   },
		{ id : 'upFlushMB'    , header : "上行流量(MB)"   ,headAlign:'right' ,align:'right'  ,exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'downFlushMB'     , header : "下行流量(MB)"   ,headAlign:'right' ,align:'right'  ,exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'totalFlushMB'  , header : "总流量(MB)",headAlign:'right' ,align:'right'  ,exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'upFlushKB'    , header : "上行流量(KB)",headAlign:'right' ,align:'right' ,exportnumber:true ,renderer:renderFormatDataInit2  },
		{ id : 'downFlushKB'     , header : "下行流量(KB)",headAlign:'right' ,align:'right' ,exportnumber:true ,renderer:renderFormatDataInit2   },
		{ id : 'totalFlushKB'  , header : "总流量(KB)",headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'upFlushGB'    , header : "上行流量(GB)",headAlign:'right' ,align:'right' ,exportnumber:true ,renderer:renderFormatDataInit2  },
		{ id : 'downFlushGB'     , header : "下行流量(GB)",headAlign:'right' ,align:'right' ,exportnumber:true  ,renderer:renderFormatDataInit2  },
		{ id : 'totalFlushGB'  , header : "总流量(GB)",headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'localtotalFlushMB', header : "本地总流量(MB)", sortable:true ,headAlign : 'right', align : 'right', exportnumber : true, renderer : renderFormatDataInit2}, 
		{ id : 'localtotalFlushKB', header : "本地总流量(KB)", sortable:true ,headAlign : 'right', align : 'right', exportnumber : true, renderer : renderFormatDataInit2}, 
		{ id : 'localtotalFlushGB', header : "本地总流量(GB)", sortable:true ,headAlign : 'right', align : 'right', exportnumber : true, renderer : renderFormatDataInit2},
		{ id : 'intImsis' , header : "用户数"     ,headAlign:'right' ,align:'right'  ,exportnumber:true},
		{ id : 'localintImsis', header : "本地用户数", sortable:true ,headAlign : 'right', align : 'right', exportnumber : true},
		{ id : 'nmAveFlushKB'  , header : "平均流量(KB/人)",headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'visit' , header : "访问次数"  ,hidden:true      ,headAlign:'right' ,align:'right' ,exportnumber:true},
		{ id : 'detail'   , header : "操作" , sortable:false, exportable:false,
			renderer:renderInit
		}
];
var gridConfig={
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/spaceDistribute_query.do?total_time_level='+parent.total_obj.total_time_level+'&total_time_search='+parent.total_obj.total_time_search,
	exportURL :'/spaceDistribute_export.do?1=1' ,
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
	pageSize : getDispalyPageSize(1,1),
	remoteSort : false ,
	remotePaging:false,
	pageSizeList :   [10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
	onComplete:loadComplate,
	onCellClick : showMapArea
};
//点击区域时，在地图上加亮显示该区域
function showMapArea(value, record , cell,row, colNO, rowNO,column,event){
	if(value==record.businessName){
		//alert(record.businessName);
		document.frm.MantoEye_Main.areaActive(value);
		document.frm1.MantoEye_Main.areaActive(value);
		document.frm2.MantoEye_Main.areaActive(value);
	}else{
		document.frm.MantoEye_Main.areaActive("-");
		document.frm1.MantoEye_Main.areaActive("-");
		document.frm2.MantoEye_Main.areaActive("-");
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
	obj.hideColumn = 8 ;//隐藏的列数目
	obj.isCheckbox = false;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度
	//判断是否查询到数据
	//alert(initHasData+'===默認有數據nitHasData old');
	//alert(sortInit+'===默認第一次排序sortInit old');
	judgeData(datagrid);
	if(!initHasData){
		sortInit = true;
		//alert('第一次無數據，第N次判斷是否有數據。。。。改變sortinit的值...');
	}
	//alert(sortInit+'===是否第一次排序值改變sortInit old');

	//选择呈现单位
	if(sortInit && initHasData){
		buildUnit_new();
		sortInit = false;
	}
	//alert(sortInit+'===新的排序值sortInit new');

	//初始化grid
	initGridInfo(obj);
	/*if(firstInit==true){
		var pageSize=getDispalyPageSize(2,1);
		var index=0;
		$(".gt-pagesize-select option").each(function(i){
			if(this.text==pageSize){
				index=i;
			}
		})
		firstInit=false;
		$(".gt-pagesize-select").get(0).selectedIndex=index;
	}*/
	
	//图形数据
	if(!hasinit){
		getAjaxChartXmlData();
	}
}


/*****************地图定义******************/
//改变地图事件
function changeChartType(){
	//var mapflag = 'bsc';
	var vnum = 0;
	$('.checkboxclass"').each(function(){
		if(this.checked==true){
			$('#'+this.id+"_image").get(0).style["width"]="100%"	
			$('#'+this.id+"_image").get(0).style["height"]="100%"
			vnum++			
		}else{
			$('#'+this.id+"_image").get(0).style["width"]="0"	
			$('#'+this.id+"_image").get(0).style["height"]="0"	
		}
	})
	if(vnum==0){
		$("#areaimage").get(0).style["height"]="30px"	

	/*	var pageSize=getDispalyPageSize(0,3);
		var index=0;
		$(".gt-pagesize-select option").each(function(i){
			if(this.text==pageSize){
				index=i;
			}
		})
		$(".gt-pagesize-select").get(0).selectedIndex=index;
		alert(pageSize+"--");*/
		
	}else{
		$("#areaimage").get(0).style["height"]="350px"
		getDispalyPageSize(2,1);
	}	
}

function flashAreaDbClick(areaName){
	openDistributeBusiness(areaName);
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
		url : "spaceDistribute_getAjaxChartXmlData.do",
		data : {
			dataType_search:dataType_search,
			timeLevel_search:$("#timeLevel_search option:selected").val(),
			time_search:time_search = $('#time_search').attr("value"),
			spaceLevel_search:$("#spaceLevel_search option:selected").val()
		},
		success : function(xml) {
			document.getElementById("chartxmldata").value=xml;
			if(!hasinit && datachange){
				document.frm.MantoEye_Main.changeData(chartFlag);
				document.frm1.MantoEye_Main.changeData(chartFlag);
				document.frm2.MantoEye_Main.changeData(chartFlag);
			}else if(!hasgetdata){
				document.frm.MantoEye_Main.changeData(chartFlag);
				document.frm1.MantoEye_Main.changeData(chartFlag);
				document.frm2.MantoEye_Main.changeData(chartFlag);
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
	var pageinfo = datagrid.getPageInfo();
	pageinfo.pageNum = 1;
	datagrid.setPageInfo(pageinfo);

	//点击查询保证不再是从全网跳转过来
	parent.total_obj.total_time_level = null;
	parent.total_obj.total_time_search = null;
	showAreaType();
	datagrid.loadURL ='/spaceDistribute_query.do?total_time_level='+parent.total_obj.total_time_level+'&total_time_search='+parent.total_obj.total_time_search;
	//查询条件
	timeLevel_search = $("#timeLevel_search option:selected").val();
	time_search = $('#time_search').attr("value");
	spaceLevel_search = $("#spaceLevel_search option:selected").val();
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
	spaceLevel_search=$("#spaceLevel_search option:selected").val(); 
	switch(spaceLevel_search)
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
	$("#timeLevel_search").get(0).selectedIndex=1;
	$('#dataType_search_td_id input:first').attr("checked","true");//设置radio默认值
	$("#spaceLevel_search").get(0).selectedIndex=0;//区域维度
}

//初始化查询时间
$(document).ready(function(){
	if(parent.total_obj.total_time_search!=null){
		var total_select_index = 0 ;
		$('#timeLevel_search option').each(function (i){
			if($(this).val()==parent.total_obj.total_time_level){
				total_select_index = i ;
			}
		});
		$("#timeLevel_search").get(0).selectedIndex=total_select_index;
		$('#time_search').attr('value',parent.total_obj.total_time_search);//从全网跳转过来打开
	}else{
		$('#time_search').attr('value',time_search);//从菜单中打开tab
	}
	
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
		if(width==1024){
			$("#frm").css("width","1025px");
			$("#frm1").css("width","1025px");
			$("#frm2").css("width","1025px");
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
             //参数
			var params={
				dataType_search:dataType_search,
				timeLevel_search:timeLevel_search,
				time_search:time_search,
				spaceLevel_search:spaceLevel_search 
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName='空间分布数据';
			fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
			fileObj.gridObj = datagrid;//当前grid对象
			fileObj.params = params;
			exportFile(fileObj);
          }
        }]
      });
	  toolbar.render();
    });
    
var w="1000px";
var h="600px";
if(width==1024){
	w="815px";
	h="600px";
}

function openDistributeBusiness(businessName){
	var obj =new Object();
	obj.time_search = time_search ;
	obj.dataType_search = dataType_search ;
	obj.spaceLevel_search = spaceLevel_search ;
	obj.timeLevel_search = timeLevel_search;
	obj.businessName = businessName;
	var spaceName = '';
	switch(spaceLevel_search)
	{
	   case '1':
	   	 spaceName = 'BSC';
	     break;
	   case '2':
	   	 spaceName = 'SGSN';
	     break;
	   case '3':
	   	 spaceName = '街道办';
	     break;
	   case '4':
	   	 spaceName = '营销片区';
	     break;
	  case '5':
	   	 spaceName = '分公司';
	     break;   
	 }
	obj.spaceName = spaceName;
	window.showModalDialog("/mantoeye/data_analyse/space_distribute_business.jsp",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
}
/**
脚本不出错

$(document).ready(function(){
	window.onerror = killErrors;
})
function killErrors() {
	return true;
}
*/
</script>



