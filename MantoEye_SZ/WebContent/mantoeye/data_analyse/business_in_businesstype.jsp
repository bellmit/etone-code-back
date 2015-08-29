<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>业务类型  业务分布</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" type="text/css"
			href="/tools/gt-grid/gt_grid.css" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/maincontent.css" />
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
		<script type="text/javascript" src="/flash/js/PieChartUtil.js"></script>
		<script type="text/javascript" src="/flash/js/ParseUtils.js"></script>
		<script type="text/javascript" src="/flash/js/PercentBarChartUtils.js"></script>	
	</head>
	<body  style="overflow-x:hidden">
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
															业务分布图形
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
										<td height="250px" width="100%"
											style="padding: 15px 20px; background: #ffffff">
											<div id="imgareas" style="width: 99%; height: 100%;border: 2px solid #008BD1;">
							<div id="imgarea1" style="width: 0%; height: 0%"></div>
							<div id="imgarea2" style="width: 0%; height: 0%"></div>
							<div id="imgarea3" style="width: 0%; height: 0%"></div>
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
															业务分布数据
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
	</body>
</html>
<script type="text/javascript">
var obj = window.dialogArguments ;
var dataTypeValue=obj.dataTypeValue;
var searchDateStart=obj.searchDateStart ;
var timeLevel_search=obj.timeLevel_search;
var businessTypeName=obj.businessTypeName;
var totalImsis = obj.totalImsis;

$('#distribute_data_div').html(businessTypeName+'分布数据');
$('#distribute_chart_div').html(businessTypeName+'分布图形');

var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :[
		{name : 'fullDate'        },
		{name : 'business'      },
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
		{ id : 'fullDate'      , header : "时间"     ,sortable:false  },
		{ id : 'business'      , header : "业务"     ,sortable:false  },
		{ id : 'upFlushMB'    , header : "上行流量(MB)" ,sortable:false ,headAlign:'right' ,align:'right'  ,exportnumber:true ,renderer:renderFormatDataInit2  },
		{ id : 'downFlushMB'     , header : "下行流量(MB)" ,sortable:false ,headAlign:'right' ,align:'right'   ,exportnumber:true ,renderer:renderFormatDataInit2  },
		{ id : 'totalFlushMB'  , header : "总流量(MB)" ,sortable:false ,headAlign:'right' ,align:'right' ,exportnumber:true ,renderer:renderFormatDataInit2 },
		{ id : 'upFlushKB'    , header : "上行流量(KB)" ,sortable:false ,headAlign:'right' ,align:'right' ,exportnumber:true ,renderer:renderFormatDataInit2  },
		{ id : 'downFlushKB'     , header : "下行流量(KB)" ,sortable:false ,headAlign:'right' ,align:'right' ,exportnumber:true ,renderer:renderFormatDataInit2   },
		{ id : 'totalFlushKB'  , header : "总流量(KB)" ,sortable:false ,headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'upFlushGB'    , header : "上行流量(GB)" ,sortable:false ,headAlign:'right' ,align:'right' ,exportnumber:true ,renderer:renderFormatDataInit2  },
		{ id : 'downFlushGB'     , header : "下行流量(GB)" ,sortable:false ,headAlign:'right' ,align:'right' ,exportnumber:true ,renderer:renderFormatDataInit2   },
		{ id : 'totalFlushGB'  , header : "总流量(GB)" ,sortable:false ,headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'intImsis' , header : "用户数"  ,sortable:false ,headAlign:'right' ,align:'right'  ,exportnumber:true  },
		{ id : 'nmAveFlushKB'  , header : "平均流量(KB/人)",sortable:false,headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'visit' , header : "访问次数"  ,sortable:false ,hidden:true,headAlign:'right' ,align:'right'  ,exportnumber:true  }
];

var gridConfig={
	id : "datagrid",
	loadURL :'/businessinbusinesstype_queryBusinessDataByType.do?businessTypeName='+obj.businessTypeName+'&dataType='+dataTypeValue+'&searchDateStart='+searchDateStart+'&timeLevel_search='+obj.timeLevel_search,
	exportURL :'/businessinbusinesstype_export.do?1=1' ,
	dataset : dsConfig ,
	columns : colsConfig ,
	width:780,
	height:500,
	resizable : false,
	showGridMenu : false,
	allowCustomSkin : true,
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
	onComplete:loadComplate
};

var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
} );

//grid回调函数
function loadComplate(){
	var  obj = new Object ();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.hideColumn = 7 ;//隐藏的列数目
	obj.isCheckbox = false;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度

	var totalRecords = datagrid.getAllRows().length;//获取加载到的数据记录数目
	var width=window.screen.availWidth;
	
	//判断是否查询到数据
	judgeData(datagrid);
	if(!initHasData){
		sortInit = true;
	}
	
	//选择呈现单位 
	if(sortInit && initHasData){
		 buildUnit();
		sortInit = false;
	}
	//初始化grid
	initGridInfo(obj);
/*	
	if(!hasinit){
		getAjaxChartXmlData();
	}*/
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
	var label3 = '访问次数';
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
		 		text1 = chartTime+' 流量占比图';
		 		text2 = chartTime+' 用户数占比图';
		 		text3 = chartTime+' 访问次数占比图';
		 	}	
		 	if(i<10){ 	
			datas1[i] = {"label":record.business,"value":StringToFloat(record.totalFlushMB)};
			//datas2[i] = {"label":record.business,"value":StringToFloat(record.intImsis)};
			datas3[i] = {"label":record.business,"value":StringToFloat(record.visit)};
			}else{
				otherflush = otherflush + StringToFloat(record.totalFlushMB);
				//otherimsis = otherimsis + StringToFloat(record.intImsis);
				othercount = othercount + StringToFloat(record.visit);
			}	
			
			datas2[i] = parseInt((record.intImsis/totalImsis)*10000+0.5)/100;
			xlabels2[i] = record.business;	
			showxlabels[i]=record.business;	
			showxdatas [i] =record.intImsis;		
		}
		//alert(otherflush);
		//数据超出10条 多余的用其他
		if(otherflush!=0){
			datas1[10] = {"label":"本页非前10合计","value":otherflush};
			datas2[10] = {"label":"本页非前10合计","value":otherimsis};
			datas3[10] = {"label":"本页非前10合计","value":othercount};
		}
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


/*
var hasinit=false;//第一次是否初始化图形
var initChartAgain = false;//判断是否再次初始化图形

*/
/*****************图形****************************/
/*
//获取服务器端图形数据
function getAjaxChartXmlData(){
	$.ajax({
		type : "post",
		url : "businessinbusinesstype_getAjaxChartXmlData.do?1=1",
		data : {
			dataTypeValue:dataTypeValue,//TD标识
			searchDateStart:searchDateStart,//开始时间
			timeLevel_search:timeLevel_search,//时间粒度  1:小时 2:天 3:周 4:月
			businessTypeName:businessTypeName//业务

		},
		success : function(xml) {
			document.getElementById("chartxmldata").value=xml;
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

function initChartWidth(){
 	var width=window.screen.availWidth;
	var availW=1014;
	var availH=230;
	if(width==1024){
		availW=758;
	}else{
		availW=990;
	}
 	return availW+"|230";
}
function charItemClick(label){
}
*/
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
			businessTypeName:businessTypeName//业务
			};
			//导出
			var exportbn = businessTypeName.length>10?businessTypeName.substring(0,9):businessTypeName;
			var fileObj = new Object();
			fileObj.fileName=exportbn+'数据';//('+startTime_search+'至'+endTime_search+')';//文件名称
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
	
	parentHeight = 710;
		
	var $toolbarObj = $("#toolbar");//toolbar对象
	var tHeight = $toolbarObj.offset().top + 25;//toolbar相对位置
	var IEHeight = 23-otherHeight;
	if (checkIE() == "IE6") {
		IEHeight = -23-otherHeight;
	}
	var totalRecords = datagrid.getAllRows().length;
	var dWidth = (totalRecords) * 22 +55 ;
	if (width == 1024) {
		var width_1024 = parentHeight - tHeight - IEHeight - 165 ;
		datagrid.setSize(796, dWidth>width_1024?dWidth:width_1024);//设置列表的宽高
		$toolbarObj.css("width", "796px");//设置列表toolbar的宽高
		realWidth = 796;
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
	realWidth = realWidth - 6;
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
		var $tr = $('<tr><td height="30px" style="font-size:14px" align="middle" bgcolor="#EEEEEE">没有数据显示!</td></tr>');
		$tr.appendTo($o);
		initHasData = false;
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
	} else {
		return "IE7";
	}
}

function   formatNumber(eValue)
{  
      var   intPart   =   '';  
      var   decPart   =   '';  
      if   (eValue.indexOf(',')   >=   0)
      {  
            eValue=eValue.replace(/,/g,'');  
      }  
            //判断是否包含'.'  
      if   (eValue.indexOf('.')>=0)
      {  
            intPart   =   eValue.split('.')[0];  
            decPart   =   eValue.split('.')[1];  
      }else
      {  
            intPart   =   eValue;  
      }  
      var   num     =     intPart   +   '';
      var   re   =   /(-?\d+)(\d{3})/
      while(re.test(num))
      {
            num   =   num.replace(re,	'$1,$2')
      }
      if   (eValue.indexOf(".")   >=   0)
      {  
            eValue   =   num   +   "."   +   decPart;  
      }
      else
      {  
            eValue   =   num ;  
      }  
      return   eValue;  
} 
var test =false;
function renderNumberFormat(value ,record,columnObj,grid,colNo,rowNo){

	if(!test){
		test = true;
	}
	return formatNumber(value);
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



