<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>重点业务分析</title>
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
<script language="javascript" type="text/javascript" src="/js/nav.js"></script>
		<!-- OFC图表 -->
		<script type="text/javascript" src="/flash/js/swfobject.js"></script>
		<script type="text/javascript" src="/flash/js/json/json2.js"></script>
		<script type="text/javascript" src="/flash/js/BarLineChartUtil.js"></script>
		<script type="text/javascript" src="/flash/js/ParseUtils.js"></script>
		<!-- 自动补全JS --> 
		<script type='text/javascript'
			src='/tools/autocomplete/lib/jquery.bgiframe.min.js'></script>
		<script type='text/javascript'
			src='/tools/autocomplete/lib/thickbox-compressed.js'></script>
		<script type='text/javascript'
			src='/tools/autocomplete/jquery.autocomplete.js'></script>
		<link rel="stylesheet" type="text/css"
			href="/tools/autocomplete/jquery.autocomplete.css" />
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
														<select name="spaceLevel_search" id="spaceLevel_search" style="height: 21px">
															<option value="1" selected>
																BSC
															</option>
															<option value="2">
																SGSN
															</option>
															  <option value="5">
																分公司
															</option>
															<option value="4">
																营销片区
															</option>
															<option value="3">
																街道办
															</option>
															<option value="6">
																全网
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
								<tr>
													<td class="condition_name">
														重点业务：
													</td>
													<td style="width: 450px" colspan="4">
														<select name="area_title" id="area_title"
															style="height: 21px; width: 115px;">
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
										</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td height="5px" bgcolor="#D3E0F2" width="100%"></td>
						</tr>
	<%--<tr>
							<td>
								<table cellspacing="0" cellpadding="0" border="0" width="100%"
									class="menubar">
									<tr valign="top">
										<td width="4px" height="24px">
											<div class="lefttitle"></div>
										</td>
										<td width="100%" height="24px">
											<div class="middletitle" id="headTitle">
												重点业务分析图形
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

						--%><tr>
							<td>
								<table cellspacing="0" cellpadding="0" border="0" width="100%"
									class="menubar">
									<tr valign="top">
										<td width="4px" height="24px">
											<div class="lefttitle"></div>
										</td>
										<td width="100%" height="24px">
											<div class="middletitle">
												重点业务分析数据
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
var dataType_search = 1 ; //页面显示数据类型
var timeLevel_search =2;
var defaulettime =date.getYear()+'-'+''+((date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1)))+'-'+''+(date.getDate()>9?date.getDate():("0"+date.getDate())); //查询开始时间
var time_search = defaulettime;
var spaceLevel_search = '1';//区域维度(BSC:1 SGSN:2 街道办:3 营销片区:4 分公司:5	)
var vcBussinessName='';
var bussinessId = '';


var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :[
		{name : 'fullDate'        },
		{name : 'area'      },
		//{name : 'businessName'      },
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
		{name : 'imsisRate'   },
		{name : 'totalFlushRate'   },
		{name : 'detail' },
	]
};

function renderInit(value ,record,columnObj,grid,colNo,rowNo){
	var vcName = record.area;
	var businessName = record.businessName;
	var thtml = "<table><tr>";
	thtml = thtml +'<td><img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/business.gif" alt="选择竞争对手分析" onclick="openDistributeBusiness(1,\''+businessName+'\',\'' +vcName+'\');" /></td>';
	//thtml = thtml +'</td><td></div><img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/wap.gif" alt="查看终端型号" onclick="openDistributeBusiness(2,\''+businessName+'\');" /></td>';
	thtml = thtml +  '</tr></table>';
	return thtml;
}

var colsConfig = [
		{ id : 'fullDate'      , header : "时间" ,sortable:false    },
		{ id : 'area'      , header : "区域" ,sortable:true   },
		//{ id : 'businessName'      , header : "业务名称" ,sortable:true   },
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
		{ id : 'imsisRate'  , header : "用户占比（全网）",headAlign:'right' ,align:'right',exportnumber:true},
		{ id : 'totalFlushRate'  , header : "业务占比（全网）",headAlign:'right' ,align:'right',exportnumber:true},
		{ id : 'detail'   , header : "操作" , sortable:false, exportable:false,
			renderer:renderInit
		}
];
var gridConfig={
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	autoLoad:false,
	loadURL :'/ownBusinessAnlyse_query.do?1=1',
	exportURL :'/ownBusinessAnlyse_export.do?1=1' ,
	dataset : dsConfig ,
	columns : colsConfig ,
	width:780,
	height:500,
	resizable : false,
	beforeLoad:checkLogon,
	container : 'data_container', 
	beforeLoad:checkLogon,
	toolbarContent : 'nav | goto | pagesize | state' ,
	pageSize :  getLongPageSize(),
	remoteSort : true ,
	remotePaging:true,
	pageSizeList :   [10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
	onComplete:loadComplate
};

function getLongPageSize(){
	var size = getDispalyPageSize(1,1);
	if(size<20){
		size = 20;
	}
	return size;
}
var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
} );

//页面呈现的单位
var showunit = 'MB';
var firstInit=true;

function loadComplate(){
	//grid列表初始化信息
	var obj = new Object ();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.hideColumn = 6 ;//隐藏的列数目
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
	
	//buildChartData(datagrid.getAllRows().length);	
	
}


/*图表是否已经初始化*/
var chartinit = false;
/*组装图形数据*/
function buildChartData(totalRecords){

	var title = time_search+" 流量|用户数走势图";
	
	var xlabels = [] ;
	var datas1 = [] ;
	var datas2 = [] ;
	
	if(typeof(datagrid.getRecord(0))!="undefined"){
		for(var i = 0 ; i<totalRecords;i++){	
			var record = datagrid.getRecord(i);	 	
			if(i<20){//图形只显示前10 的数据
				xlabels[i] = record.area; 	
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
	vcBussinessName = $('#area_title option:selected').text();
	bussinessId = $('#area_title').attr('value');
	pageinfo.pageNum = 1;
	datagrid.setPageInfo(pageinfo);
	//查询条件
	timeLevel_search = $("#timeLevel_search option:selected").val();
	time_search = $('#time_search').attr("value");
	spaceLevel_search = $("#spaceLevel_search option:selected").val();
	if(time_search==''){
		alert('请选择时间!');
		return ;
	}
	var param={
		dataType_search:dataType_search,
		timeLevel_search:timeLevel_search,
		time_search:time_search,
		spaceLevel_search:spaceLevel_search,
		vcBussinessName:vcBussinessName,
		bussinessId:bussinessId
	};
	hasinit = false;
	GT.$grid('datagrid').query( param );
}

//设置页面显示的数据类型
function showDataType(type){
	dataType_search = type ;
}




//初始化查询时间
$(document).ready(function(){
		$('#time_search').attr('value',time_search);//从菜单中打开tab
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
				spaceLevel_search:spaceLevel_search,
				vcBussinessName:vcBussinessName,
				bussinessId:bussinessId
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName='重点业务数据';
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

//列表链接操作
function openDistributeBusiness(flag,businessName,vcName){
	var obj =new Object();
	obj.time_search = time_search ;
	obj.dataType_search = dataType_search ;
	obj.timeLevel_search = timeLevel_search;
	obj.businessName = businessName;
	obj.spaceLevel_search=spaceLevel_search;
	obj.vcName = vcName;
	obj.bussinessId = bussinessId;
	if(flag==1){
		//showMMDialog("/mantoeye/data_analyse/own_business/competitor_business.jsp",obj,'505px','470px');
		window.showModalDialog("/mantoeye/data_analyse/own_business/competitor_business.jsp",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
	}
	if(flag==2){
		window.showModalDialog("/mantoeye/data_analyse/own_business/own_business_terminal.jsp",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
	}
}

var areaids=[];	
var areanames=[];
var areas=[];
$(document).ready(function(){
	//所有区域
	$.ajax({
			type:"post", 
			url:"ownBusinessAnlyse_initAreaDialogData.do", 
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
		var ids = aids[i].split(":");
		$("#area_title").append("<option value='" +ids[1] + "'>" + anames[i] + "</option>");	
	
		areas[i] = {name:anames[i],id:ids[1]}
	}
	resetautocomplete(areas,'tttt','area_title');
	//默认查询第一个区域
	hasinit = false;
	vcBussinessName = anames[0];
	var id = aids[0].split(":");
	bussinessId = id[1];
	var param = {
			dataType_search:dataType_search,
			timeLevel_search:timeLevel_search,
			time_search:time_search,
			spaceLevel_search:spaceLevel_search,
			bussinessId:bussinessId
	 };
	 GT.$grid('datagrid').query( param );
}
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



