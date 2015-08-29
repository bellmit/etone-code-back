<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/include/session.jsp"%>
<%@ include file="/include/setcache.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="com.opensymphony.xwork2.ActionContext"%><html
	xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>告警展示页面</title>
<link type="text/css" rel="stylesheet"
	href="/skin/Default/css/maincontent.css" />
<link rel="stylesheet" type="text/css" href="/tools/gt-grid/gt_grid.css" />
<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
	type="text/css"></link>
<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js"></script>
<script type="text/javascript" src="/js/common_grid.js"></script>
<script type="text/javascript" src="/js/jquery/extend.tab/js/Toolbar.js"></script>
<script type="text/javascript" src="/flash/js/swfobject.js"></script>
<script type="text/javascript" src="/flash/js/json/json2.js"></script>

<style type="text/css">
.ImgHeader {
	height: 20px;
	background-color: #CACACA;
	background-image: url('/skin/Default/images/bg/tableHeaderBG.gif');
	background-repeat: repeat-x;
}

.ImgTable {
	width: 100%;
	border-collapse: separate;
	border-style: solid;
	border-width: 1px;
	border-color: #CACACA;
}
</style>
</head>
<body>
<table id="maincontent" cellspacing="0" cellpadding="0" border="0"
	width="100%">
	<tr style="height: 4px;">
		<td style="background-color: #D3E0F2"></td>
	</tr>
	<tr>
		<td>
		<table cellspacing="0" cellpadding="0" border="0" width="100%">
			<tr>
				<td width="4px" height="24px;"><img
					src="/skin/Default/images/MantoEye/maincontent/lefttitle.gif" /></td>
				<td width="100%" id="title"
					style="background: url(/skin/Default/images/MantoEye/maincontent/middletitle.gif) repeat-x; padding-left: 10px; font-size: 12px; font-weight: bold; color: #FFFFFF; vertical-align: middle;">
				识别率告警示意图</td>
				<td width="4px"><img
					src="/skin/Default/images/MantoEye/maincontent/righttitle.gif" /></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr valign="top" style="height: 53%;">
		<td>
		<table style="width: 100%;">
			<tr>
				<td align="center" valign="middle" style="width: 80%;">
				<table style="width: 100%;">
					<tr style="height: 100%;">
						<td style="width: 20%;">
						<table class="ImgTable">
							<tr class="ImgHeader" align="center" valign="middle">
								<td id="bscTitle">BSC识别率告警示意图</td>
							</tr>
							<tr style="height: 260px; text-align: center;" align="center"
								valign="middle">
								<td align="center" valign="middle" id="bscImg"
									style="width: 100%; height: 250px;"><input type='hidden'
									id='bscImgArea' />
								<div id="bscImgContent"></div>
								</td>
							</tr>
							<tr>
								<td>
								<table>
									<tr>
										<td style="padding-left: 35px;">未识别率：</td>
										<td style="padding-left: 20px;"><span
											style="cursor: pointer; width: 70px; text-align: center; color: red;">${bscBean.reAlarmRatio}%</span></td>
									</tr>
									<tr>
										<td style="padding-left: 35px;">阀&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;值：</td>
										<td style="padding-left: 20px;"><span id="bscConfig"
											style="cursor: pointer; width: 70px; text-align: center;"></td>
									</tr>
									<tr>
										<td style="padding-left: 35px;">告警级别：</td>
										<td style="padding-left: 20px;"><span
											style="cursor: pointer; width: 70px; text-align: center;"
											id="bscConfigV"></span></td>
									</tr>
									<tr align="center" valign="middle">
										<td colspan="2">
										<div id="mainbtn" style="padding-left: 56px; letter-spacing:1pt;">
										<ul>
											<li><a href="javascript:showHistoryWarn('BSC');"
												style="width: 96px;" title="查看历史识别率"><span>历史识别率</span> </a></li>
										</ul>
										</div>
									</tr>
								</table>
								</td>
							</tr>
						</table>
						</td>
						<td style="width: 20%;">
						<table class="ImgTable">
							<tr class="ImgHeader" align="center" valign="middle">
								<td id="cgiTitle">CGI识别率告警示意图</td>
							</tr>
							<tr style="height: 260px; text-align: center;" align="center"
								valign="middle">
								<td align="center" valign="middle" id="bscImg"
									style="width: 100%; height: 250px;"><input type='hidden'
									id='cgiImgArea' />
								<div id="cgiImgContent"></div>
								</td>
							</tr>
							<tr>
								<td>
								<table>
									<tr>
										<td style="padding-left: 35px;">未识别率：</td>
										<td style="padding-left: 20px;"><span
											style="cursor: pointer; width: 70px; text-align: center; color: red;">${cgiBean.reAlarmRatio}%</span></td>
									</tr>
									<tr>
										<td style="padding-left: 35px;">阀&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;值：</td>
										<td style="padding-left: 20px;"><span id="cgiConfig"
											style="cursor: pointer; width: 70px; text-align: center;"></td>
									</tr>
									<tr>
										<td style="padding-left: 35px;">告警级别：</td>
										<td style="padding-left: 20px;"><span
											style="cursor: pointer; width: 70px; text-align: center;"
											id="cgiConfigV"></span></td>
									</tr>
									<tr align="center" valign="middle">
										<td colspan="2">
										<div id="mainbtn" style="padding-left:56px;letter-spacing: 1pt;">
										<ul>
											<li><a href="javascript:showHistoryWarn('CGI');"
												style="width: 96px;" title="查看历史告警"><span>历史识别率</span> </a></li>
										</ul>
										</div>
										</td>
									</tr>
								</table>
								</td>
							</tr>
						</table>
						</td>
						<td style="width: 20%;">
						<table class="ImgTable">
							<tr class="ImgHeader" align="center" valign="middle">
								<td id="sgsnTitle">SGSN识别率告警示意图</td>
							</tr>
							<tr style="height: 260px; text-align: center;" align="center"
								valign="middle">
								<td align="center" valign="middle" id="bscImg"
									style="width: 100%; height: 250px;"><input type='hidden'
									id='sgsnImgArea' />
								<div id="sgsnImgContent"></div>
								</td>
							</tr>
							<tr>
								<td>
								<table>
									<tr>
										<td style="padding-left: 35px;">未识别率：</td>
										<td style="padding-left: 20px;"><span
											style="cursor: pointer; width: 70px; text-align: center; color: red;">${sgsnBean.reAlarmRatio}%</span></td>
									</tr>
									<tr>
										<td style="padding-left: 35px;">阀&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;值：</td>
										<td style="padding-left: 20px;"><span id="sgsnConfig"
											style="cursor: pointer; width: 70px; text-align: center;"></td>
									</tr>
									<tr>
										<td style="padding-left: 35px;">告警级别：</td>
										<td style="padding-left: 20px;"><span
											style="cursor: pointer; width: 70px; text-align: center;"
											id="sgsnConfigV"></span></td>
									</tr>
									<tr align="center" valign="middle">
										<td colspan="2">
										<div id="mainbtn" style="padding-left: 56px;letter-spacing: 1pt;">
										<ul>
											<li><a href="javascript:showHistoryWarn('SGSN');"
												style="width: 96px;" title="查看历史告警"><span>历史识别率</span> </a></li>
										</ul>
										</div>
										</td>
									</tr>
								</table>
								</td>
							</tr>
						</table>
						</td>
						<td style="width: 20%;">
						<table class="ImgTable">
							<tr class="ImgHeader" align="center" valign="middle">
								<td id="busiTitle">业务识别率告警示意图</td>
							</tr>
							<tr style="height: 260px; text-align: center;" align="center"
								valign="middle">
								<td align="center" valign="middle" id="bscImg"
									style="width: 100%; height: 250px;"><input type='hidden'
									id='busImgArea' />
								<div id="busImgContent"></div>
								</td>
							</tr>
							<tr>
								<td>
								<table>
									<tr>
										<td style="padding-left: 35px;">未识别率：</td>
										<td style="padding-left: 20px;"><span
											style="cursor: pointer; width: 70px; text-align: center; color: red;">${busiBean.reAlarmRatio}%</span></td>
									</tr>
									<tr>
										<td style="padding-left: 35px;">阀&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;值：</td>
										<td style="padding-left: 20px;"><span id="busConfig"
											style="cursor: pointer; width: 70px; text-align: center;"></td>
									</tr>
									<tr>
										<td style="padding-left: 35px;">告警级别：</td>
										<td style="padding-left: 20px;"><span
											style="cursor: pointer; width: 70px; text-align: center;"
											id="busConfigV"></span></td>
									</tr>
									<tr align="center" valign="middle">
										<td colspan="2">
										<div id="mainbtn" style="padding-left: 56px;letter-spacing: 1pt;">
										<ul>
											<li><a href="javascript:showHistoryWarn('业务');"
												style="width: 96px;" title="查看历史告警"><span>历史识别率</span> </a></li>
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
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>
		<table cellspacing="0" cellpadding="0" border="0" width="100%">
			<tr>
				<td width="4px" height="24px;"><img
					src="/skin/Default/images/MantoEye/maincontent/lefttitle.gif" /></td>
				<td width="100%" id="title"
					style="background: url(/skin/Default/images/MantoEye/maincontent/middletitle.gif) repeat-x; padding-left: 10px; font-size: 12px; font-weight: bold; color: #FFFFFF; vertical-align: middle;">
				流量告警数据</td>
				<td width="4px"><img
					src="/skin/Default/images/MantoEye/maincontent/righttitle.gif" /></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr valign="top">
		<td>
		<table cellspacing="0" cellpadding="0" border="0" bgcolor="#ffffff"
			style="width: 100%">
			<tr>
				<td>
				<div class="gt-panel"
					style="width: 100%; margin: 0px; margin-bottom: 2px;">
				<div id="data_container"></div>
				</div>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>
<script type="text/javascript">

var date = new Date();
var year = date.getYear();
var m = date.getMonth();
var month = (m+1) > 9 ? (m+1) + '' : '0' + (m+1);
var d = date.getDate();
d -= 1;
var day = d > 9 ? d + '' : '0' + d;

var dddddddd = year + '-' + month + '-' + day;

$('#bscTitle').html('${yestoday}BSC识别率');
$('#cgiTitle').html('${yestoday}CGI识别率');
$('#sgsnTitle').html('${yestoday}SGSN识别率');
$('#busiTitle').html('${yestoday}业务识别率');
/*
$('#bscTitle').html(dddddddd+'BSC识别率');
$('#cgiTitle').html(dddddddd+'CGI识别率');
$('#sgsnTitle').html(dddddddd+'SGSN识别率');
$('#busiTitle').html(dddddddd+'业务识别率');
*/

	var dsConfig = {
		//data : data1,
		uniqueField : 'id',
		fields : [ {
			name : 'typeName'
		}, {
			name : 'countLow'
		}, {
			name : 'countIncrease'
		}, {
			name : 'countDecrease'
		}, {
			name : 'detail'
		}]
	};

	var colsConfig = [ {
		id : 'typeName',
		header : "类型",
		width:188
	}, {
		id : 'countIncrease',
		header : "流量激增(个)",
		headAlign:'right' ,align:'right',
		width:168,
		renderer : rendDetail1
	}, {
		id : 'countDecrease',
		header : "流量激减(个)",
		headAlign:'right' ,align:'right',
		width:168,
		renderer : rendDetail3
	}, {
		id : 'countLow',
		header : "流量异常(个)",
		headAlign:'right' ,align:'right',
		width:168,
		renderer : rendDetail2
	},{
		id : 'detail',
		header : "操作",
		sortable : false,
		width:340,
		renderer : rendDetail
	}

	];

	function rendDetail1(value ,record,columnObj,grid,colNo,rowNo){
		var v = record.countIncrease;
		var type = record.typeName;
		if(v>=25 && v < 50){
			return '<font color="#2040c0"><a href="javascript:showMoreInfoAdd(\''+type+'\')" style="cursor:pointer;color:#2040c0;">'
			+v+'<img src="/mantoeye/monitoring/img/light.gif" alt="中等严重"/></a></font>';
		}else if(v>=25 && v < 75){
			return '<font color="#c06040"><a href="javascript:showMoreInfoAdd(\''+type+'\')" style="cursor:pointer;color:#c06040;">'
			+v+'<img src="/mantoeye/monitoring/img/light.gif" alt="严重"/></a></font>';
		}else if(v>=75){
			return '<font color="#ff0000"><a href="javascript:showMoreInfoAdd(\''+type+'\')" style="cursor:pointer;color:#ff0000;">'
			+v+'</a><img src="/mantoeye/monitoring/img/light.gif" alt="非常严重"/></font>';
		}else{
			if( 0 == v){
				return v;
			}else{
				return '<font color="#000000"><a href="javascript:showMoreInfoAdd(\''+type+'\')" style="cursor:pointer;color:#000000"><span>'+v+'</span></a></font>';
			}
		}
	}

	function showMoreInfoAdd(type){
		//alert(type+'==ADD');
		var obj = new Object();
			obj.type = type;
			obj.typeName=type;
			obj.yestoday = '${yestoday}';

		w = '550px';
		h = '520px';
 
	 	var value = window.showModalDialog("/mantoeye/monitoring/flushAlarmListDay2.jsp?1=1",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
		
	}
	function showMoreInfoMv(type){
		var obj = new Object();
		obj.type = type;
		obj.typeName=type;
		obj.yestoday = '${yestoday}';

	w = '550px';
	h = '520px';

 	var value = window.showModalDialog("/mantoeye/monitoring/flushAlarmListDay3.jsp?1=1",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");

	}
	function showMoreInfoWarm(type){
		var obj = new Object();
		obj.type = type;
		obj.typeName=type;
		obj.yestoday = '${yestoday}';

	w = '550px';
	h = '520px';

 	var value = window.showModalDialog("/mantoeye/monitoring/flushAlarmListDay4.jsp?1=1",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");

	}
	
	function rendDetail3(value ,record,columnObj,grid,colNo,rowNo){
		var v = record.countDecrease;
		var type = record.typeName;
		if(v>=25 && v < 50){
			return '<font color="#2040c0"><a href="javascript:showMoreInfoMv(\''+type+'\')" style="cursor:pointer;color:#2040c0;">'
			+v+'<img src="/mantoeye/monitoring/img/light.gif" alt="中等严重"/></a></font>';
		}else if(v>=25 && v < 75){
			return '<font color="#c06040"><a href="javascript:showMoreInfoMv(\''+type+'\')" style="cursor:pointer;color:#c06040;">'
			+v+'<img src="/mantoeye/monitoring/img/light.gif" alt="严重"/></a></font>';
		}else if(v>=75){
			return '<font color="#ff0000"><a href="javascript:showMoreInfoMv(\''+type+'\')" style="cursor:pointer;color:#ff0000;">'
			+v+'</a><img src="/mantoeye/monitoring/img/light.gif" alt="非常严重"/></font>';
		}else{
			if( 0 == v){
				return v;
			}else{
				return '<font color="#000000"><a href="javascript:showMoreInfoMv(\''+type+'\')" style="cursor:pointer;color:#000000"><span>'+v+'</span></a></font>';
			}
		}
	}
	function rendDetail2(value ,record,columnObj,grid,colNo,rowNo){
		var v = record.countLow;
		var type = record.typeName;
		if(v>=25 && v < 50){
			return '<font color="#2040c0"><a href="javascript:showMoreInfoWarm(\''+type+'\')" style="cursor:pointer;color:#2040c0;">'
			+v+'<img src="/mantoeye/monitoring/img/light.gif" alt="中等严重"/></a></font>';
		}else if(v>=25 && v < 75){
			return '<font color="#c06040"><a href="javascript:showMoreInfoWarm(\''+type+'\')" style="cursor:pointer;color:#c06040;">'
			+v+'<img src="/mantoeye/monitoring/img/light.gif" alt="严重"/></a></font>';
		}else if(v>=75){
			return '<font color="#ff0000"><a href="javascript:showMoreInfoWarm(\''+type+'\')" style="cursor:pointer;color:#ff0000;">'
			+v+'</a><img src="/mantoeye/monitoring/img/light.gif" alt="非常严重"/></font>';
		}else{
			if( 0 == v){
				return v;
			}else{
				return '<font color="#000000"><a href="javascript:showMoreInfoWarm(\''+type+'\')" style="cursor:pointer;color:#000000"><span>'+v+'</span></a></font>';
			}
		}
	}

	function rendDetail(value ,record,columnObj,grid,colNo,rowNo){
		var typeName = record.typeName;
		return '<div id="mainbtn" style="padding-left: 66px;">'
			+'<ul><li><a href="javascript:changeThresholdValue(\''+typeName+'\');"'
			+'style="width: 86px;" 　title="阀值设定"><span>阀值设定</span> </a></li>'
			//+'<li><a href="javascript:doWarn(\''+typeName+'\');"'
			//+'style="width: 86px;" title="详细流量告警"><span>详细信息</span> </a></li>'
			+'<li><a href="javascript:seeMoreInfos(\''+typeName+'\');"'
			+'style="width: 86px;" title="历史流量告警"><span>历史告警</span> </a></li>'
			+'</ul></div>';
				 
	}

	function seeMoreInfos(type) {
		var obj = new Object();
		if('CGI' == type){
			obj.type = type;
			obj.typeName=type;
		}else if('BSC' == type){
			obj.type = type;
			obj.typeName=type;
		}else if('SGSN' == type){
			obj.type = type;
			obj.typeName=type;
		}else{
			obj.type = type;
			obj.typeName=type;
		}

		w = '880px';
		h = '520px';
 
	 	var value = window.showModalDialog("/mantoeye/monitoring/flushAlarmList.jsp?1=1",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
		
	}

	function changeThresholdValue(type) {

		var obj = new Object();
		if('CGI' == type){
			obj.type = type;
			obj.typeName=type;
		}else if('BSC' == type){
			obj.type = type;
			obj.typeName=type;
		}else if('SGSN' == type){
			obj.type = type;
			obj.typeName=type;
		}else{
			obj.type = type;
			obj.typeName=type;
		}

		w = '600px';
		h = '320px';
 
		var value = window.showModalDialog("alarmLimitConfig_initQuery2.do?1=1&type="+type,obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
		//var value = window.showModalDialog("/mantoeye/monitoring/changeThresholdValue.jsp?1=1",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
		
	}
	function doWarn(type) {
		var obj = new Object();
		if('CGI' == type){
			obj.type = type;
			obj.typeName=type;
		}else if('BSC' == type){
			obj.type = type;
			obj.typeName=type;
		}else if('SGSN' == type){
			obj.type = type;
			obj.typeName=type;
		}else{
			obj.type = type;
			obj.typeName=type;
		}

		w = '880px';
		h = '520px';
 
	 	var value = window.showModalDialog("/mantoeye/monitoring/flushAlarmListDay.jsp?1=1",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
		
	}

	var w = '600px';
	var h = '520px';
	function showHistoryWarn(type) {
		var obj = new Object();
		if('CGI' == type){
			obj.type = 1;
			obj.typeName=type;
		}else if('BSC' == type){
			obj.type = 2;
			obj.typeName=type;
		}else if('SGSN' == type){
			obj.type = 3;
			obj.typeName=type;
		}else{
			obj.type = 4;
			obj.typeName=type;
		}

		 w = '600px';
		 h = '520px';
	 	var value = window.showModalDialog("/mantoeye/monitoring/distinguishRatioAlarmList.jsp",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
	
	}
	
	var gridConfig = {
		id : "datagrid",
		loadURL : '/flushAlarm_initQuery.do?1=1&ddd='+dddddddd,
		dataset : dsConfig ,
		columns : colsConfig ,
		width:390,
		height:180,
		resizable : false,
		showGridMenu : false,
		container : 'data_container', 
		beforeLoad:checkLogon,
		toolbarContent : 'nav | goto | pagesize | state' ,
		pageSize : 100 ,
		remoteSort : false ,
		pageSizeList : [10,20,50,100],
		onComplete:loadComplate
	};

	var datagrid = new GT.Grid(gridConfig);
	GT.Utils.onLoad(function() {
		datagrid.render();
	});

	function loadComplate() {
		var obj = new Object();
		obj.datagrid = datagrid;
		obj.hideColumn = 0;
		obj.isCheckbox = false;
		obj.otherHeight = 0;

		judgeData(datagrid);

		columninit = true;
		initGridInfo(obj);

		buildPie();
	}
	function buildPie(){
	  buildPieImg('bsc','${bscBean.alarmRatio}','${bscBean.reAlarmRatio}');
	  buildPieImg('cgi','${cgiBean.alarmRatio}','${cgiBean.reAlarmRatio}');
	  buildPieImg('sgsn','${sgsnBean.alarmRatio}','${sgsnBean.reAlarmRatio}');
	  buildPieImg('bus','${busiBean.alarmRatio}','${busiBean.reAlarmRatio}');
		
	}

	function buildPieImg(type,alarmRatio,reAlarmRatio){
		var alv = parseInt(alarmRatio);
		var alAv = parseInt(reAlarmRatio);

		var t = {
				  "elements":[
				    {
				      "type":      "pie",
				      "colours":   ["#200ae9","#fa2710"],
				      "tip" : "#label# #percent#",
				      "alpha":     0.8,
				      "border":    2,
				      "start-angle": 35,
				      "values" :   [ 
								{"value":alv,"label":"识别率","color":"#200ae9"},
								{"value":alAv,"label":"未识别率","color":"#fa2710"} 
				      ]
				    }
				  ]
			};
		

		var s = JSON.stringify(t);
		document.getElementById(type+"ImgArea").value = s;
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id="+type, type+"ImgContent", "100%",
				"100%", "9.0.0", "expressInstall.swf", '', {
					"wmode" : "opaque"
				});
	}

	function open_flash_chart_data(id) {
		if( id == 'bsc'){
			return document.getElementById("bscImgArea").value;
		}else if( id == 'cgi'){
			return document.getElementById("cgiImgArea").value;
		}else if( id == 'sgsn'){
			return document.getElementById("sgsnImgArea").value;
		}else{
			return document.getElementById("busImgArea").value;
		}
	}
</script>


<script type="text/javascript">
<!--

$(document).ready(function(){
	getInfo('${bscBean.reAlarmRatio}','bscConfig','bscConfigV');
	getInfo('${cgiBean.reAlarmRatio}','cgiConfig','cgiConfigV');
	getInfo('${sgsnBean.reAlarmRatio}','sgsnConfig','sgsnConfigV');
	getInfo('${busiBean.reAlarmRatio}','busConfig','busConfigV');
}); 

function getInfo(configValue,c,cv){
	if(eval(configValue) >= 67 && eval(configValue) <= 100 ){
		$('#'+c).html('67%~100%');
		$('#'+cv).html(' 严重告警');
		$('#'+cv).css("color","red"); 
	}else if(eval(configValue) >=34 && eval(configValue) <= 66 ){
		$('#'+cv).html('中等告警');	
		$('#'+c).html('34%~66%');
		$('#'+cv).css('color','green');	
	}else{
		$('#'+cv).html('正常');
		$('#'+c).html('0%~33%');	
		$('#'+cv).css('color','blue');		
	}	

	$('#'+c).attr('title','[正常:0%~33%][中等告警:34%~66%][严重告警:67%~100%]');	
}

 
//-->
</script>
