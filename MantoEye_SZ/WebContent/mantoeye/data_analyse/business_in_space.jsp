<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>具体业务 空间分布</title>
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
	<body style="overflow-x: hidden">
		<table id="maincontent" cellspacing="0" cellpadding="0" border="0"
			bgcolor="#D3E0F2" width="100%" >
			<tr>
				<td colspan="2" height="5px"></td>
			</tr>
			<tr valign="top">
				<td>
					<table cellspacing="0" cellpadding="0" border="0" width="100%">
						<tr valign="top">
							<td><table cellspacing="0" cellpadding="0" border="0" width="100%">
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
							</table></td>
						</tr>
						<tr valign="top">
							<td width="100%" class="condition_down">
								<table id="query_condition" cellspacing="0px"
									cellpadding="0px;boder="0">
									<tr valign="middle">
										<td class="condition_name">
											区域维度：
										</td>
										<td>
											<select class="condition_value" name="spaceLevel_search"
												style="height: 20px" id="spaceLevel_search"
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
										<td width="*"></td>
										<td width="200px">
											<div id="mainbtn">
												<ul>
													<li>
														<a href="#" onclick="query()" 　title="查询"><span>查询</span>
														</a>
													</li>
													<!--
													<li>
														<a href="#" onclick="reset();" title="重置"><span>重置</span>
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
				<td>
					<table cellspacing="0" cellpadding="0" border="0" bgcolor="#ffffff"
						style="width: 100%;">
						<tr valign="top">
							<td>
								<table cellspacing="0" cellpadding="0" border="0" width="100%">
									<tr>
										<td height="5px" bgcolor="#D3E0F2" width="100%"></td>
									</tr>

									<tr>
										<td>
											<table cellspacing="0" cellpadding="0" border="0"
												width="100%" class="menubar">
												<tr valign="top">
													<td width="4px" height="24px">
														<div class="lefttitle"></div>
													</td>
													<td width="100%" height="24px">
														<div class="middletitle" id="distribute_chart_div">
															分布图形
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
										<td id="td_map" height="100%" width="100%"
											style="padding: 5px 20px; background: #ffffff">
												<input type="checkbox" id="total_flush" class="checkboxclass"
									onclick='changeChartType()' checked />
								总流量
								<input type="checkbox" id="imsis_count" class="checkboxclass"
									onclick='changeChartType()' />
								用户数
								<input type="checkbox" id="active_count" class="checkboxclass"
									onclick='changeChartType()' style="display: none;"/>
								<!-- 访问次数 -->
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
								
								<div id="active_count_image" style="width: 0; height: 0;margin-left:-13px;overflow:hidden;">
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
											<table cellspacing="0" cellpadding="0" border="0"
												width="100%" class="menubar">
												<tr valign="top">
													<td width="4px" height="24px">
														<div class="lefttitle"></div>
													</td>
													<td width="100%" height="24px">
														<div class="middletitle" id="distribute_data_div">
															分布数据
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
							<td width="2px" style="background: #D3E0F2"></td>
						</tr>
						<tr>
							<td colspan="2" height="5px"></td>
						</tr>
					</table>
	</td></tr></table>
	</body>
</html>
<script type="text/javascript">
var obj = window.dialogArguments ;
var dataTypeValue=obj.dataTypeValue;
var searchDateStart=obj.searchDateStart ;
var timeLevel_search=obj.timeLevel_search;
var businessName=obj.businessName;
var date=new Date().valueOf();

var chartFlag = 'bsc';//地图显示标识
var spaceType=1;//区域维度(BSC:1 SGSN:2 街道办:3 营销片区:4 分公司:5	)
var datachange = false;//第N次查询初始化数据标识
$('#distribute_data_div').html(businessName+'分布数据');
$('#distribute_chart_div').html(businessName+'分布图形');

var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :[
		{name : 'fullDate'        },
		{name : 'businessName'      },
		{name : 'upFlushMB'       },
		{name : 'downFlushMB'     },
		{name : 'totalFlushMB'     },
		{name : 'upFlushKB'    },
		{name : 'downFlushKB'   },
		{name : 'totalFlushKB'    },
		{name : 'upFlushGB'    },
		{name : 'downFlushGB'   },
		{name : 'totalFlushGB'   },
				{name : 'nmAveFlushKB'   },
		{name : 'intImsis'  },
		{name : 'visit'  }
	]
};
var colsConfig = [
		{ id : 'fullDate'      , header : "时间" ,width:150    ,sortable:false  },
		{ id : 'businessName'      , header : "空间名称" ,width:150    ,sortable:false  },
		{ id : 'upFlushMB'    , header : "上行流量(MB)" ,sortable:false  ,width:150,headAlign:'right' ,align:'right' ,exportnumber:true ,renderer:renderFormatDataInit2  },
		{ id : 'downFlushMB'     , header : "下行流量(MB)" ,sortable:false ,width:150 ,headAlign:'right' ,align:'right'  ,exportnumber:true ,renderer:renderFormatDataInit2  },
		{ id : 'totalFlushMB'  , header : "总流量(MB)" ,sortable:false ,width:150 ,headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2  },
		{ id : 'upFlushKB'    , header : "上行流量(KB)" ,sortable:false ,headAlign:'right' ,align:'right' ,exportnumber:true ,renderer:renderFormatDataInit2  },
		{ id : 'downFlushKB'     , header : "下行流量(KB)" ,sortable:false ,headAlign:'right' ,align:'right' ,exportnumber:true   ,renderer:renderFormatDataInit2 },
		{ id : 'totalFlushKB'  , header : "总流量(KB)" ,sortable:false ,headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'upFlushGB'    , header : "上行流量(GB)" ,sortable:false ,headAlign:'right' ,align:'right' ,exportnumber:true ,renderer:renderFormatDataInit2  },
		{ id : 'downFlushGB'     , header : "下行流量(GB)" ,sortable:false ,headAlign:'right' ,align:'right' ,exportnumber:true ,renderer:renderFormatDataInit2   },
		{ id : 'totalFlushGB'  , header : "总流量(GB)" ,sortable:false ,headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'intImsis' , header : "用户数" ,width:130  ,sortable:false ,headAlign:'right' ,align:'right' ,exportnumber:true  },
		{ id : 'nmAveFlushKB'  , header : "平均流量(KB/人)",headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 ,sortable:false },
		{ id : 'visit' , header : "访问次数" ,hidden:true,width:130 ,headAlign:'right' ,align:'right' ,exportnumber:true  }
];

var gridConfig={
	id : "datagrid",
	loadURL :'/businessinspace_queryBusinessDataInSpaceDistribute.do?businessName='+obj.businessName+'&dataType='+dataTypeValue+'&searchDateStart='+searchDateStart+'&timeLevel_search='+obj.timeLevel_search+'&date='+date,
	exportURL :'/businessinspace_export.do?1=1' ,
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
	pageSize : 10 ,
	remoteSort : true ,
	pageSizeList : [10,20,50,100],
	defaultRecord :  { no : 111 , name :'', age : '', gender : 'U' , english : 50 , math :50 },
//	customHead : 'myHead',
	onComplete:loadComplate,
	onCellClick : showMapArea
};
//点击区域时，在地图上加亮显示该区域
function showMapArea(value, record , cell,row, colNO, rowNO,column,event){
	/*if(value==record.spaceName){
		//alert(record.businessName);
		document.frm.MantoEye_Main.areaActive(value);
		document.frm1.MantoEye_Main.areaActive(value);
	}else{
		document.frm.MantoEye_Main.areaActive("-");
		document.frm1.MantoEye_Main.areaActive("-");
	}*/
}
var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
} );


//grid回调函数
function loadComplate(){
	var  obj = new Object ();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.hideColumn = 8 ;//隐藏的列数目
	obj.isCheckbox = false;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度
		
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
	//初始化grid
		
	initGridInfo(obj);
	
	if(!hasinit){
		getAjaxChartXmlDataSpace();
	}
}

var hasinit=false;//第一次是否初始化图形
var initChartAgain = false;//判断是否再次初始化图形

/*****************地图定义******************/
//var centerclick = false;
//var rightclick = false;
//改变地图事件
function changeChartType(){
	var mapflag = 'bsc';

	$('.checkboxclass"').each(function(){
		if(this.checked==true){
			/*if(this.id=='imsis_count'&&!centerclick){
				centerclick = true;
				document.frm1.MantoEye_Main.changeData(chartFlag);
			}else if(this.id=='active_count'&&!rightclick){
				rightclick = true;
				document.frm2.MantoEye_Main.changeData(chartFlag);
			}*/
			$('#'+this.id+"_image").get(0).style["width"]="100%"	
			$('#'+this.id+"_image").get(0).style["height"]="100%"			
		}else{
			$('#'+this.id+"_image").get(0).style["width"]="0"	
			$('#'+this.id+"_image").get(0).style["height"]="0"	
		}
	})
	
}

//grid查询
function query() {
	showAreaType();
	//查询条件
	spaceType=$("#spaceLevel_search option:selected").val(); 
	hasinit = false;
	datachange = true;
	
	var param={
		spaceType:spaceType
	};
	GT.$grid('datagrid').query( param );
}


/*****************图形****************************/

//获取服务器端图形数据
function getAjaxChartXmlDataSpace(){
	$.ajax({
		type : "post",
		url : "businessinspace_getAjaxChartXmlSpaceData.do?1=1",
		data : {
			dataType:dataTypeValue,//TD标识
			searchDateStart:searchDateStart,//开始时间
			timeLevel_search:timeLevel_search,//时间粒度  1:小时 2:天 3:周 4:月
			businessName:businessName,//业务
			spaceType:spaceType
		},
		success : function(xml) {
			document.getElementById("chartxmldata").value=xml;
			//alert('1');
			if(!hasinit && datachange){
				document.frm.MantoEye_Main.changeData(chartFlag);
				//alert(document.frm1.MantoEye_Main);
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
function initChartData(param){
	
	return document.getElementById("chartxmldata").value;			
}

//显示的区域维度
function showAreaType(){
	spaceType=$("#spaceLevel_search option:selected").val(); 
	switch(spaceType)
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

//初始化地图
function initSize(){
	var width=window.screen.availWidth;
	var availW=960;
	var availH=320;
	if(width==1024){
		availW=730;
	}
	return availW+"|"+availH;
}
var hasgetdata = false;
//加载图形数据入口
function loadDataString(){	
	//alert("--");
	var xml =document.getElementById("chartxmldata").value;	
	if(xml!=null&&xml!=''){
		hasgetdata = true;
	}
	return xml;
}


/*****************图形结束****************************/

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
				dataType:dataTypeValue,//TD标识
			searchDateStart:searchDateStart,//开始时间
			timeLevel_search:timeLevel_search,//时间粒度  1:小时 2:天 3:周 4:月
			businessName:businessName,//业务
			spaceType:spaceType
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName='业务空间分布数据';//('+startTime_search+'至'+endTime_search+')';//文件名称
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

//重置查询条件
function reset(){
	$("#timeLevel_search").get(0).selectedIndex=1;
}



//初始化toolbar
$(document).ready(function(){
	var obj = window.dialogArguments ;
	//alert(obj.businessId+'  '+ obj.time);
});

</script>

<script type="text/javascript">

var width = window.screen.availWidth;//屏幕宽度
var columninit = false;//初始化grid列宽

//根据分辨率初始化grid列宽
function initColumnWidth(grid, width, hideColumn, checkWidth) {//checkWidth  表示checkBox宽度,表示存在checkBox操作
	var cs = grid.columns.length - hideColumn;//如有隐藏列,则减去隐藏列
	if (typeof (checkWidth) != "undefined") {
		width = width - 40;//为checkbox预留空间
		cs = cs - 1;
	}
	width = width - 10;
	var w = parseInt(width / cs);
	var isCheck = false;
	for (var j in datagrid.columnMap) {
		if (typeof (checkWidth) != "undefined" && !isCheck) {
			//datagrid.getColumn(j).setWidth(10);//checkbox宽度为默认值, 并且必须定义在第一列
			isCheck = true;
		} else {
			datagrid.getColumn(j).setWidth(w);
		}
	}
	columninit = true;
}

//初始化grid高宽等信息,使高宽满屏
function initGridInfo(obj) {
	var datagrid = obj.datagrid;
	var hideColumn = obj.hideColumn;//隐藏列
	var isCheckbox = obj.isCheckbox;//是否可选择
	var otherHeight = obj.otherHeight;//预留高度
	var realWidth = 1045;
	
	//初始化高度满屏
	
	parentHeight = 488;
		
	var $toolbarObj = $("#toolbar");//toolbar对象
	var tHeight = $toolbarObj.offset().top + 25;//toolbar相对位置
	var IEHeight = 23-otherHeight;
	if (checkIE() == "IE6") {
		IEHeight = -23-otherHeight;
	}
	var totalRecords = datagrid.getAllRows().length;
	var dWidth = (totalRecords) * 22 +70 ;
	if (width == 1024) {
		var width_1024 = parentHeight - tHeight - IEHeight - 3 ;
		datagrid.setSize(810, dWidth>width_1024?dWidth:width_1024);//设置列表的宽高
		$toolbarObj.css("width", "796px");//设置列表toolbar的宽高
		realWidth = 810;
	} else {
		var width_1280 = parentHeight - tHeight - IEHeight ;//满屏高度
		datagrid.setSize(1033, dWidth>width_1280?dWidth:width_1280);
		$toolbarObj.css("width", "1033px");
		realWidth = 1030;
	}
	
	if(columninit)//如果已经初始化宽度，则不再执行
		return ;
	//初始化宽度满屏
	var cs = datagrid.columns.length - hideColumn;//如有隐藏列,则减去隐藏列
	if (isCheckbox) {
		realWidth = realWidth - 40;//为checkbox预留空间
		cs = cs - 1;
	}
	
	if(width==1024){
		realWidth = realWidth - 140;
	}else{
		if(checkIE()=="IE6"){
			realWidth = realWidth - 155;
		}else{
			
			realWidth = realWidth - 170;
		}
		
	}
	var w = realWidth / cs;
	var isCheck = false;
	for (var j in datagrid.columnMap) {
		if (isCheckbox && !isCheck) {
			//datagrid.getColumn(j).setWidth(10);//checkbox宽度为默认值, 并且必须定义在第一列
			isCheck = true;
		} else {
			datagrid.getColumn(j).setWidth(w);
		}
	}
	columninit = true;
}

//判断grid查询是否有数据，没有则提示
function judgeData(datagrid){
	var totalRecords = datagrid.getAllRows().length;//获取加载到的数据记录数目
	if(typeof(datagrid.getRecord(0))=="undefined"){
		var $o = $('#'+datagrid.id+'_bodyDiv').find('table').eq(0).find('tbody').eq(0);//获取对象
		$o.find('tr').eq(0).remove();//由于控件存在bug，所以需要删除第一行
		var $tr = $('<tr><td height="20px" style="font-size:14px" align="middle" bgcolor="#EEEEEE">没有数据显示!</td></tr>');
		$tr.appendTo($o);initHasData = false;
	}
}
//导出文件
function exportFile(obj){
	var fileName = obj.fileName;//文件名称
	var fileFormat = obj.fileFormat;//文件格式,后台暂支持xls格式
	var gridObj = obj.gridObj ;//当前grid对象
	var params = obj.params;
	var url = gridObj.exportURL;	//导出URL临时参数
	gridObj.exportURL = bindUrlParameter(gridObj.exportURL, params);//绑定参数到URL中
	gridObj.exportGrid(fileFormat, fileName);
	gridObj.exportURL = url;	//URL恢复原样
}

//URL绑定参数
function bindUrlParameter(url,params){
	if(typeof(params)!='undefined'){
		for( var k in params){
			url = url + '&'+k +'='+(encodeURIComponent((params)[k]));
		}
	}
	return url;
}

//根据分辨率初始化grid列宽
// (pwidth为百分比参数,以逗号分开,如'20,30,40,10',
// haschk 是否有复选框列,如果有，则pwidth参数的第一列设置为0,如果该列默认隐藏,该列的宽度也设为0)
function initPercentColumnWidth(grid, width, pwidth, haschk) {
	var max = grid.columnList.length;
	var st = 0;
	var ps = pwidth.split(",");
	if (haschk) {
		st = 1;//复选框一行不用设置宽度
		width = width - 35;
	} else {
		width = width - 5;
	}
	for (var j = st; j < max; j++) {
		var w = parseInt(width * parseInt(ps[j]) / 100);
		var o = grid.columnList[j].setWidth(w);
	}
	columninit = true;
}
//获取已经选择的行数
function getSelectedCount(gridid) {
	var sy = document.getElementsByName("gt_" + gridid + "_chk_chk");
	var cnt = 0;
	for (var i = 0; i < sy.length; i++) {
		if (sy[i].checked) {
			cnt = cnt + 1;
		}
	}
	return cnt;
}
//获取选择的第一项的值
function getSelectedId(gridid) {
	var sy = document.getElementsByName("gt_" + gridid + "_chk_chk");
	var vs = "";
	for (var i = 1; i < sy.length; i++) {
		if (sy[i].checked) {
			vs = sy[i].value;
			return vs;
		}
	}
}
//获取选择的全部值的字符串
function getSelectedKeys(gridid) {
	var sy = document.getElementsByName("gt_" + gridid + "_chk_chk");
	var vs = "";
	for (var i = 1; i < sy.length; i++) {
		if (sy[i].checked) {
			vs = vs + "," + sy[i].value;
		}
	}
	return vs.substr(1, vs.length);
}

//判断ID版本
function checkIE() {
	if (navigator.userAgent.indexOf("MSIE 6.0") > 0) {
		return "IE6";
	} else  if (navigator.userAgent.indexOf("MSIE 7.0") > 0) {
		return "IE7";
	} else{
		return "IE8";
	}
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






