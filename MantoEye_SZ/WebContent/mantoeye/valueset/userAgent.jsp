<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>TOP5UserAgent分布</title>
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
		<script type="text/javascript" src="/flash/js/PieChartUtil.js"></script>
		<script type="text/javascript" src="/flash/js/ParseUtils.js"></script>
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
															分布图形
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
										<td height="220px" width="100%"
											style="padding: 15px 20px; background: #ffffff">
											<div id="imgareas" style="width: 99%; height: 100%;border: 2px solid #008BD1;">
											<div id="imgarea1" style="width: 33%; height: 100%"></div>
											<div id="imgarea2" style="width: 33%; height: 100%"></div>
											<div id="imgarea3" style="width: 33%; height: 100%"></div>
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
							<td width="2px" bgcolor="#D3E0F2"></td>
						</tr>
						<tr>
							<td colspan="2" height="5px"></td>
						</tr>
					</table>
	</body>
</html>
<script type="text/javascript">
var obj = window.dialogArguments ;
var name=obj.name;
var date=obj.date;
var msisdn=obj.msisdn;
var totalflush = obj.totalflush;
var upflush = obj.upflush;
var downflush = obj.downflush;
$('#distribute_data_div').html("用户["+msisdn+"]TOP5 UserAgent"+'分布数据');
$('#distribute_chart_div').html("用户["+msisdn+"]TOP5 UserAgent"+'分布图形');

var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :[
		{name : 'time'        },
		{name : 'upFlushMB'      },
		{name : 'downFlushMB'      },
		{name : 'totalFlushMB'    },
		{name : 'userAgent' }
	]
};
var colsConfig = [
		{ id : 'time'      , header : "时间"    ,renderer:rendererDate  },
		{ id : 'userAgent' , header : "UserAgent"  ,renderer:rendererKillNull    },
		{ id : 'totalFlushMB'  , header : "总流量(MB)" ,exportnumber:true },
		{ id : 'upFlushMB'    , header : "上行流量(MB)"  ,exportnumber:true  },
		{ id : 'downFlushMB'     , header : "下行流量(MB)"   ,exportnumber:true  }
		
		
];
function rendererDate(value ,record,columnObj,grid,colNo,rowNo){
	return value.split(' ')[0];
}
function rendererKillNull (value ,record,columnObj,grid,colNo,rowNo){
	return  buildNull(value);
}
function buildNull(value){
	if(value==null||value==''){
	return '空';
	}else{
	return value;
	}
}
var gridConfig={
	id : "datagrid",
	loadURL :'/bigflushdisplay_distinationDisplay.do?name='+obj.name+'&msisdn='+obj.msisdn+'&date='+date,
	exportURL :'/bigflushdisplay_exportDistination.do?1=1&name='+obj.name+'&msisdn='+obj.msisdn+'&date='+date ,
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
	toolbarContent : 'nav | goto | pagesize | state' ,
	pageSize : 10 ,
	remoteSort : false ,
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
	//判断是否查询到数据
	judgeData(datagrid,1);
	
	var totalRecords = datagrid.getAllRows().length;//获取加载到的数据记录数目
	var width=window.screen.availWidth;
	//根据分辨率初始化grid列宽
	if(!columninit){
		if(width==1024){
			initColumnWidthWithPop(datagrid,792,0);
			
		}else{
			initColumnWidthWithPop(datagrid,973,0);
			
		}
	}
	/*
	var title;
	
	if(typeof(datagrid.getRecord(0))!="undefined"){
			var chartxmldata;
					title=msisdn+' #FIELD#UserAgent占比图';
					chartxmldata='<root><chart id="1" name="'+title+'" fields="总流量|上流量|下流量">';
					for(var i = 0 ; i<totalRecords;i++){
						var record = datagrid.getRecord(i);
						chartxmldata=chartxmldata+'<data label="'+record.userAgent+'" total="'+record.totalFlushMB+'"  imsis ="'+record.upFlushMB+'" count="'+record.downFlushMB+'"/>';
						
					}
			chartxmldata=chartxmldata+"</chart></root>";
			document.getElementById("chartxmldata").value=chartxmldata;
			
	}
	*/
		//组装图形数据
	buildChartData(datagrid.getAllRows().length);
}

var hasinit=false;//第一次是否初始化图形
var initChartAgain = false;//判断是否再次初始化图形
///////////////////图表////////////////////////

/*图表是否已经初始化*/
var chartinit = false;
/*组装图形数据*/
function buildChartData(totalRecords){
//	var chartTime ='';
	var text1 = '';
	//如果有单位  则label与单位间用@@分开
	var label1 = '总流量@@MB';	
	var datas1 = [] ;
	
	var text2 = '';
	var label2 = '上行流量@@MB';
	var datas2 = [] ;
	
	var text3 = '';
	var label3 = '下行流量@@MB';
	var datas3 = [] ;
	
	var othertotal = totalflush;
	var otherup = upflush;
	var otherdown = downflush;
	var morethan = false;
	//如果记录数位5条 则有可能有非前五的数据
	if(totalRecords==5){
		morethan = true;
	}
	
//	alert(othertotal+"-"+otherup+"-"+otherdown);
	if(typeof(datagrid.getRecord(0))!="undefined"){
		for(var i = 0 ; i<totalRecords;i++){	
			var record = datagrid.getRecord(i);	 	
		 	if(text1==''){
		 		chartTime = record.fullDate;
		 		text1 = ' 总流量占比图';
		 		text2 = ' 上行流量占比图';
		 		text3 = ' 下行流量占比图';
		 	}	
		 	if(i<5){ 	
		 	othertotal = othertotal - record.totalFlushMB;
		 	otherup = otherup -record.upFlushMB;
		 	otherdown = otherdown - record.downFlushMB;
		 	var chartlabel = buildNull(record.userAgent);
			datas1[i] = {"label":chartlabel,"value":record.totalFlushMB};
			datas2[i] = {"label":chartlabel,"value":record.upFlushMB};
			datas3[i] = {"label":chartlabel,"value":record.downFlushMB};
			}
		}
		if(morethan){
			othertotal = othertotal<0?0:othertotal;
			otherup = otherup<0?0:otherup;
			otherdown = otherdown<0?0:otherdown;
			datas1[totalRecords] = {"label":"非TOP5合计","value":othertotal};
			datas2[totalRecords] = {"label":"非TOP5合计","value":otherup};
			datas3[totalRecords] = {"label":"非TOP5合计","value":otherdown};
		}
	}
	var jsons1 = buildPieChartData(text1,label1,datas1);
	var str1 = JSON.stringify(jsons1);
	var jsons2 = buildPieChartData(text2,label2,datas2);
	var str2 = JSON.stringify(jsons2);
	var jsons3 = buildPieChartData(text3,label3,datas3);
	var str3 = JSON.stringify(jsons3);
	if(!chartinit){
		chartinit = true;
		document.getElementById("chartxmldata1").value=str1;
		document.getElementById("chartxmldata2").value=str2;
		document.getElementById("chartxmldata3").value=str3;
		swfobject.embedSWF("/flash/open-flash-chart.swf?id=flush", "imgarea1", "33%", "100%", "9.0.0","expressInstall.swf");
		swfobject.embedSWF("/flash/open-flash-chart.swf?id=up", "imgarea2", "33%", "100%", "9.0.0","expressInstall.swf");
		swfobject.embedSWF("/flash/open-flash-chart.swf?id=down", "imgarea3", "33%", "100%", "9.0.0","expressInstall.swf");
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
	}else if(id=='up'){
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

function initChartData(param){
	hasinit=true;
	var xml =document.getElementById("chartxmldata").value;			
	return xml;
}
function changPieData(){
	var xml = document.getElementById("chartxmldata").value;
	document.frm.ThreePieChart.jsChangeData(xml);
}

function initChartWidth(){
 	var width=window.screen.availWidth;
	var availW=935;
	var availH=230;
	if(width==1024){
		availW=745;
	}
 	return availW+"|220";
}
function charItemClick(label){
}
*/
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
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName='Top5UserAgent分布数据';//('+startTime_search+'至'+endTime_search+')';//文件名称
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

//初始化toolbar
$(document).ready(function(){
	var obj = window.dialogArguments ;
	//alert(obj.businessId+'  '+ obj.time);
});


</script>



