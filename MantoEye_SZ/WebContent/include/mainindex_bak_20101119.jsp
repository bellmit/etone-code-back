<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:tvns>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/include/session.jsp"%>
<%@ include file="/include/setcache.jsp"%>
<head>
<title>主页页面</title>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<link type="text/css" rel="stylesheet"
	href="/skin/Default/css/newstyle.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="/tools/jqgrid/themes/redmond/jquery-ui-1.8.2.custom.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="/tools/jqgrid/themes/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="/tools/jqgrid/themes/ui.multiselect.css" />
<link id="change_style" type="text/css" rel="stylesheet"
	href="/skin/Default/css/mainindex_1280.css" />
<style>
html,body {
	margin: 0; /* Remove body margin/padding */
	padding: 0;
	overflow: auto;
}

.myfullinsert {
	width: 100%;
	height: 2px;
	font-size: 0px;
	background-color: #DEECFD;
	border-bottom: 1px solid #8DB2E3 ;
}

#RightPane {
	padding: 0px;
	overflow: auto;
}

.ui-tabs-nav li {
	position: relative;
}

.ui-tabs-selected a span {
	padding-right: 1px;
}

.ui-tabs-close {
	display: none;
	position: absolute;
	top: 3px;
	right: 0px;
	z-index: 800;
	width: 16px;
	height: 14px;
	font-size: 10px;
	font-style: normal;
	cursor: pointer;
}

.ui-tabs-selected .ui-tabs-close {
	display: block;
}

.ui-layout-west .ui-jqgrid tr.jqgrow td {
	border-bottom: 0px none;
}

.ui-datepicker {
	z-index: 1200;
}

.rotate { /* for Safari */
	-webkit-transform: rotate(-90deg);
	/* for Firefox */
	-moz-transform: rotate(-90deg);
	/* for Internet Explorer */
	filter: progid :             DXImageTransform.Microsoft.BasicImage ( 
		      
		    rotation =     
		       3 );
}
</style>

<script src="/tools/jqgrid/js/jquery-1.4.2.min.js"
	type="text/javascript"></script>
<script src="/tools/jqgrid/js/jquery-ui-1.8.2.custom.min.js"
	type="text/javascript"></script>
<script src="/tools/jqgrid/js/jquery.layout.js" type="text/javascript"></script>
<script src="/tools/jqgrid/js/i18n/grid.locale-cn.js"
	type="text/javascript"></script>
<script src="/tools/jqgrid/js/grid.base.js" type="text/javascript"></script>
<script src="/tools/floatbox/jquery.floatbox.1.0.8.js"
	type="text/javascript"></script>

<!-- 图标 -->
<!-- OFC图表 -->
<script type="text/javascript" src="/flash/js/swfobject.js"></script>
<script type="text/javascript" src="/flash/js/json/json2.js"></script>
<script type="text/javascript" src="/flash/js/DoubleLineChartUtil.js"></script>
<script type="text/javascript" src="/flash/js/BarLineChartUtil.js"></script>
<script type="text/javascript" src="/flash/js/PercentBarChartUtils.js"></script>
<script type="text/javascript" src="/flash/js/ParseUtils.js"></script>
<script type="text/javascript" src="/js/chip.js"></script>
<script type="text/javascript" src="/js/mainindexdata.js"></script>
</head>
<body id="master">
<div id="mainarea" style="overflow: hidden;">
<div class="myfullinsert"></div>
<table>
	<tr>
		<td id="td11"
			style="width: 50%; height: 278px; border: 1px solid #8DB2E3;">
		<table style="width: 100%;">
			<tr>
				<td>
				<div id="RightPane1" style="width: 100%;"><!-- Tabs pane -->
				<div id="tabs1">
				<ul>
					<li><a href="#imgareas9">全网业务</a></li>
					<li><a href="#imgareas10">十大业务</a></li>
					<li><a href="#imgareas11">十大WAP网站</a></li>
					<li><a href="#imgareas12">十大移动自有业务</a></li>
				</ul>
				<div id="imgareas9" class="tablt"><input type="hidden"
					value="" id="chartxmldata9" />
				<div id="imgarea9" style="width: 100%; height: 100%"></div>
				</div>
				<div id="imgareas10" class="tablt"><input type="hidden"
					value="" id="chartxmldata10" />
				<div id="imgarea10" style="width: 100%; height: 100%"></div>
				</div>
				<div id="imgareas11" class="tablt"><input type="hidden"
					value="" id="chartxmldata11" />
				<div id="imgarea11" style="width: 100%; height: 100%"></div>
				</div>
				<div id="imgareas12" class="tablt"><input type="hidden"
					value="" id="chartxmldata12" />
				<div id="imgarea12" style="width: 100%; height: 100%"></div>
				</div>
				</div>
				</div>
				</td>
			</tr>
		</table>
		</td>
		<td id="td12"
			style="width: 50%; height: 278px; border: 1px solid #8DB2E3">
		<%-- <table>
							<tr>
								<td>
									<div id="RightPane2">
										<!-- Tabs pane -->
										<div id="tabs2">
											<ul>
												<li>
													<a href="#imgareas20">系统公告</a>
												</li>
																							
											</ul>
											<div id="imgareas20" style="width: 460px; height: 230px;">												
											<div style="width: 460px; height: 230px;">
											<table id="list20"></table>
											<div id="plist20"></div>
											</div>
											</div>
										</div>
									</div>
						</td></tr></table> --%>
		<table id="list20" style="width: 50%;"></table>
		<div id="plist20" style="width: 50%;"></div>
		</td>
	</tr>
	<tr>
		<td id="td21"
			style="width: 50%; height: 278px; border: 1px solid #8DB2E3;">
		<table style="width: 100%;">
			<tr style="width: 100%;">
				<td>
				<div id="RightPane3" style="width: 100%;"><!-- Tabs pane -->
				<div id="tabs3">
				<ul>
					<li><a href="#imgareas30" id="subFlushTrend">全网业务—＞日走势</a></li>
				</ul>
				<div id="imgareas30" class="tablt"><input type="hidden"
					value="" id="chartjsonxdatax30" />
				<div id="imgarea30" style="width: 0%; height: 0%"></div>
				</div>
				</div>
				</div>
				</td>
			</tr>
		</table>
		</td>
		<td id="td22"
			style="width: 50%; height: 278px; border: 1px solid #8DB2E3">
		<table style="width: 100%;">
			<tr>
				<td>
				<div id="RightPane4" style="width: 100%;"><!-- Tabs pane -->
				<div id="tabs4">
				<ul>
					<li><a href="#imgareas40" id="subFlushSpace">全网业务—＞日空间分布</a></li>
					<%-- <li>
													<a href="#imgareas41">天用户数日空间分布</a>
												</li>	--%>
					<li style="width: 16px;"></li>
					<select id="chdata40" onchange="dataTypeChange();"
						style="height: 22px">
						<option value="1" selected>流量</option>
						<option value="2">用户数</option>
					</select>
					<select id="space40" onchange="spaceTypeChange();"
						style="height: 22px">
						<option value="1" selected>BSC</option>
						<option value="2">SGSN</option>
						<option value="3">街道办</option>
						<option value="4">营销片区</option>
						<option value="5">分公司</option>
					</select>
				</ul>
				<div id="imgareas40" class="tabmap"><input type="hidden"
					value="" id="spacexml40" /> <input type="hidden" value=""
					id="defaultchartjson" /> <iframe name="frm40"
					src="/flash/MantoEye_Mini.html?mapflag=bsc&dataflag=total&dataname=流量"
					scrolling="no" id="frm40"
					style="HEIGHT: 100%; VISIBILITY: inherit; width: 100%; Z-INDEX: 1"
					marginwidth="1" marginheight="1" frameborder="0" align="middle"></iframe>
				</div>
				<%--<div id="imgareas41" class="tabmap">
												<input type="hidden" value="" id="spacexml41" />
												<iframe name="frm41"
													src="/flash/MantoEye_Mini.html?mapflag=bsc&dataflag=imsis&dataname=用户数"
													scrolling="no" id="frm41" 
													style="HEIGHT: 100%; VISIBILITY: inherit; width: 100%; Z-INDEX: 1"
													marginwidth="1" marginheight="1" frameborder="0"
													align="middle"></iframe>
											</div>											
										--%></div>
				</div>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
</body>
</html>
<script language="javascript">	
jQuery(document).ready(function(){
	changewh();
	//initEmptyChart();
});
function changewh(){
	var x=window.screen.width;
	if(x=='1280'){
		document.getElementById("change_style").href="/skin/Default/css/mainindex_1280.css";
	}else{		
		document.getElementById("change_style").href="/skin/Default/css/mainindex_1024.css";					
	}
}
function initEmptyChart(){
	var str1 = JSON.stringify(buildEmptyAxisChart());
	document.getElementById("defaultchartjson").value = str1;
	/*swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=init","imgarea10", "100%", "100%", "9.0.0",
						"expressInstall.swf", '', {"wmode" : "opaque"});
	swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=init","imgarea11", "100%", "100%", "9.0.0",
						"expressInstall.swf", '', {"wmode" : "opaque"});
	swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=init","imgarea12", "100%", "100%", "9.0.0",
						"expressInstall.swf", '', {"wmode" : "opaque"});
	swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=init","imgarea30", "100%", "100%", "9.0.0",
						"expressInstall.swf", '', {"wmode" : "opaque"});*/											
}
jQuery(document).ready(function(){
	 
	 activebusinn = "全网";
	 getAjaxFlushData('total','9');
	 getAjaxLineChartData('全网');
	 getAjaxSpaceXmlData('全网'); 
	 
});
var secondinit0 = false;
var secondinit1 = false;
var secondinit2 = false;
/////////////////////////////TAB标签页///////////////////////////////////////
function firstAjaxLine(businame){
	$.ajax({
		type : "post",
		url : "/mainIndex_queryHourData2.do",
		dataType: 'json',
		data : {
			searchDateStart:ydate,
			totalFlushDate:ydate,
			busiName:businame
		},
		success : function(data) {
			buildMyLineChartData(data,businame);
		},
		error : function() {
			alert('获取数据失败!');
		}
	});
}

function firstAjaxSpace(businame){
	$.ajax({
		type : "post",
		url : "/mainIndex_querySpaceData2.do",
		data : {
			showunit:showunit,
			searchDateStart:ydate,
			totalFlushDate:ydate,
			busiName:businame,
			spaceType:$('#space40').val()
		},
		success : function(xml) {
			$('#spacexml40').attr('value',xml)	;	
			var s1 = document.frm40.MantoEye_Mini.changeData(chartFlag);
		},
		error : function() {
			alert('服务器出错,请联系管理员!');
		}
	});
}
jQuery(document).ready(function(){
    //$('#switcher').themeswitcher();
	var maintab1 =jQuery('#tabs1','#RightPane1').tabs({
          select: function(event, ui) {
        	if(ui.panel.id=='imgareas9'){
        		activebusinn = "全网";
        		getAjaxFlushData('total','9');
        		 getAjaxLineChartData('全网');
        		 getAjaxSpaceXmlData('全网'); 
	        }else if(ui.panel.id=='imgareas10'){  
        			activebusinn = businn10;
	        	 if(!secondinit0){
        			getAjaxFlushData('flush','10');   
        			secondinit0 = true;
        		 }
	        	 firstAjaxLine('flush');
	        	  firstAjaxSpace('flush');      		 					
          	}else if(ui.panel.id=='imgareas11'){
          		 activebusinn = businn20;  
          		 if(!secondinit1){
          		  	getAjaxFlushData('wap','11');  
          		  	secondinit1 = true;
          		 }          
          		 firstAjaxLine('wap');
	        	  firstAjaxSpace('wap');  		     		       		
          	}else{
          		 activebusinn = businn30; 
          		 if(!secondinit2){
          		  	getAjaxFlushData('cmcc','12');        		         		
          		  	secondinit2 = true;
          		 } 
          		 firstAjaxLine('cmcc');
	        	  firstAjaxSpace('cmcc');  
          	}
          }
    });
   /* var maintab2 =jQuery('#tabs2','#RightPane2').tabs({
        select: function(event, ui) {
          }
    });*/
    var maintab3 =jQuery('#tabs3','#RightPane3').tabs({
        select: function(event, ui) {
        if(ui.panel.id=='imgareas32'){
          		$('#space30').css('display','block');
          	}else{
          		$('#space30').css('display','none')
          	}
          }
    });
    var maintab4 =jQuery('#tabs4','#RightPane4').tabs({
        select: function(event, ui) {
        if(ui.panel.id=='imgareas42'){
          		//$('#space40').css('display','block');
          	}else{
          		//$('#space40').css('display','none')
          	}
        }
    });
});	
 
	//var chip0 = new floatchip('enter','/welcome.jsp','',900, 80, 140, 50, 5, 3, 3, 100,'进入系统 '); 
	//初始化查询时间
	var date=new Date();
	date=date.valueOf();
	date=date-24*60*60*1000;
	date=new Date(date); 
	var ydate =date.getYear()+'-'+''+((date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1)))+'-'+''+(date.getDate()>9?date.getDate():("0"+date.getDate())); //查询开始时间

	var datetotal = new Date();
	var m = datetotal.getMonth();
	var year = datetotal.getYear();
	var d = datetotal.getDate();
	var month = m > 9 ? m+'' : '0'+m;
	var day = d > 9 ? d+'' : '0'+d;
	//var totalFlushDateF =  year+'-'+month;
	var totalFlushDateF =  year+'-'+month+'-'+day;

	/*$(document).ready(function(){
		alert(totalFlushDateF+'=='+ydate);
	});*/

	//test
	//ydate = '2010-08-01';
	
	var chartFlag = 'bsc';
	var businn9 ="";
	var businn10 ="";
	var businn20 ="";
	var businn30 ="";
	var bar0init = false;
	var bar1init = false;
	var bar2init = false;
	var bar3init = false;
	
	var activebusinn = "--";
	
	var changedSpace = "10";
	
	///////////////////图表(1)////////////////////////
	/*组装图形数据*/
	function buildMyPercentChartData(json,divid) {
		var chartid= "imgarea"+divid;
	
		var label1 = '流量占比@@%';
		var datas1 = [];
		var label11 = '流量';
		var showxdatas11 = [];
	
		var text2 = '';
		var xlabels2 = [] ;
		var label2 = '用户数占比@@%';
		var label22 = '用户数';
		var showxdatas = [];
		var showxlabels = [];
		var datas2 = [] ;
		
		var text = '';	
		var xlabels = [];
		var showxlabels = [];

		var showlabel = "";
		
		var max = 100;
		var step = 20;
		if (json.length>0) {
			if(divid=='10'){
				businn10 = json[0].name;
				bar1init = true;
			}else if(divid=='11'){
				businn20 = json[0].name;
				bar2init = true;
			}else{
				businn30 = json[0].name;
				bar3init = true;
			}
		
			for ( var i = 0; i < json.length; i++) {
				var record = json[i];
				if (i == 0) {
					text = ydate + '  流量|用户数占比图';
					if(record.flushRate<1){
						max = 1;
						step = 1;
					}else if(record.flushRate<2){
						max = 2;
						step = 1;
					}else if(record.flushRate<5){
						max = 5;
						step = 1;
					}

					//alert(record.flushRate+'==');
				}

				var typeTemp = '十大业务';
				if(divid == '10'){
					typeTemp = '十大业务';
				}else if(divid == '11'){
					typeTemp = '十大WAP网站';
				}else{
					typeTemp = '十大移动自有业务';
				}
				
				datas1[i] = {"right":record.flushRate, "on-click": "callback('"+record.name+"','"+typeTemp+"')"};
				datas2[i] = {"right":record.imsisRate, "on-click": "callback('"+record.name+"','"+typeTemp+"')"};	
							
				
				xlabels[i] = record.name;
				showxlabels[i] = record.name;
				showxdatas [i] =record.imsis;
				showxdatas11[i]	= record.flushGB;	
			}
			
			datas2 = buildHBarChartTip(datas2,showxlabels,label2,showxdatas,label22);
			datas1 = buildHBarChartTip(datas1, xlabels, label1,showxdatas11,label11);

			var jsons1 = buildPChart(text, [ label1, label2 ],
					xlabels, [ datas1, datas2 ],max,step);
			var str1 = JSON.stringify(jsons1);

			document.getElementById("chartxmldata"+divid).value = str1;
			swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id="+divid,
						chartid, "100%", "100%", "9.0.0",
						"expressInstall.swf", '', {
							"wmode" : "opaque"
						});	
		/*	if(divid=='10'){
				 activebusinn = businn10;
				 getAjaxLineChartData(businn10);
				 getAjaxSpaceXmlData(businn10);
			}	*/									
		} else {
			var str1 = JSON.stringify(buildEmptyAxisChart());
			document.getElementById("chartxmldata"+divid).value = str1;
			swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id="+divid, chartid,
					"100%", "100%", "9.0.0", "expressInstall.swf", '', {
						"wmode" : "opaque"
					});
			return;
		}
	}
	///////////////////图表(2)////////////////////////
	/*组装图形数据*/
	var busiNameOut = '全网';
	function buildMyLineChartData(json,busiName) {
		var chartid= "imgarea30";
		//var title =  ydate+"小时走势图";
		busiNameOut = busiName;
		 var title = '';
		if('全网' == busiName){
			title = /*busiName +*/ totalFlushDate+"  时段走势图";
		}else{
			title = /*busiName +*/ ydate+"  时段走势图";
		} 

		var xlabels = [];
		var datas1 = [];
		var datas2 = [];
		var dddatas = json.datalist;
		var titleName = '';

		var typeTemp = '十大业务';
		if(busiName == 'flush'){
			typeTemp = '十大业务';
		}else if(busiName == 'wap'){
			typeTemp = '十大WAP网站';
		}else{
			typeTemp = '十大移动自有业务';
		}

		if (dddatas != '' && dddatas.length > 0) {
			for ( var i = 0; i < dddatas.length; i++) {
				var record = dddatas[i];
				var dddds = record.fulldate.split(":");
				xlabels[i] = dddds[0]+":00";
				datas1[i] = record.flushKB;
				datas2[i] = record.imsis;
				titleName = record.name;
			}
			if('全网' != busiName){
				$('#subFlushTrend').html(typeTemp+"—＞"+titleName+' 日时段走势');
				$('#subFlushSpace').html(typeTemp+"—＞"+titleName+' 日空间分布');
			}else{
				$('#subFlushTrend').html('全网业务—＞日时段走势');
				$('#subFlushSpace').html('全网业务—＞日空间分布');
			}
		} else {
			var str1 = JSON.stringify(buildEmptyAxisChart());

				document.getElementById("chartjsonxdatax30").value = str1;
				swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=xdatax30",
						chartid, "100%", "100%", "9.0.0",
						"expressInstall.swf", '', {
							"wmode" : "opaque"
						});	
				if('全网' != busiName){
					$('#subFlushTrend').html(typeTemp+"—＞"+'日时段走势');
					$('#subFlushSpace').html(typeTemp+"—＞"+'日空间分布');
				}else{
					$('#subFlushTrend').html('全网业务—＞日时段走势');
					$('#subFlushSpace').html('全网业务—＞日空间分布');
				}		
			return;
		}

		var showxlabels = xlabels;
		//格式化X轴时间
		xlabels = formatXDateLables(xlabels,6,"hour");
		var tdatas = [];

		//格式化Y轴数据（流量）
		var lobj = formatDataBy1024(datas1,"KB");
		var lunit = "流量";
		if (lobj.unit != "") {
			lunit = lunit + "@@" + lobj.unit;
		}
		tdatas[0] = lobj.datas;
		//格式化Y轴数据（用户数）
		var robj = formatNumberByWan(datas2);
		var runit = "用户数";
		if (robj.unit != "") {
			runit = runit + "@@" + robj.unit;
		}
		tdatas[1] = robj.datas;

		//如果有单位  则label与单位间用@@分开
		var labels = [ lunit, runit ];

		tdatas[0] = buildLineChartTip(lobj.datas, showxlabels, lunit);
		tdatas[1] = buildLineChartTip(robj.datas, showxlabels, runit);

		var jsons1 = buildMutileLineChartData(title, labels, xlabels, tdatas);

		var str1 = JSON.stringify(jsons1);
		//alert("-str1-"+str1);

			document.getElementById("chartjsonxdatax30").value = str1;
			swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=xdatax30",
					chartid, "100%", "100%", "9.0.0", "expressInstall.swf",
					'', {"wmode" : "opaque"});						
	}
	/*初始化图表*/
	function open_flash_chart_data(id) {
		var ssid = id+"";
		if(ssid.indexOf('xdatax')!=-1){
			return document.getElementById("chartjson"+id).value;
		}else if(id=="init"){
			return document.getElementById("defaultchartjson").value;
		}else{
			return document.getElementById("chartxmldata"+id).value;
		}		
	}	
/*图表回调函数*/
function callback(label,typeTemp){
	activebusinn = label;
	type = typeTemp;
	getAjaxLineChartData(label);
	getAjaxSpaceXmlData(label);
}
//查询数据
function getAjaxFlushData(datatype,divid){
	$.ajax({
		type : "post",
		url:'/mainIndex_query.do?dataType='+datatype,
		datatype : 'json', 
		data : {
			searchDateStart:ydate,
			totalFlushDate:totalFlushDateF
			//totalFlushDateF+'=='+ydate
		},
		success : function(data) {
			if('total' == datatype && 9 == divid){
				showunit = getShowUnit(data.datalist);
				buildMyBarChartData(data.datalist,divid);
			}else {
				buildMyPercentChartData(data.datalist,divid);
			}
		},
		error : function() {
			alert('获取数据失败!');
		}
	});
}

function getShowUnit(datalist){
	var max = 0;
	var showUnit = 'MB';
	if (datalist != '' && datalist.length > 0) {
	for (var i = 0; i < datalist.length; i++) {
		var record = datalist[i];
		var fl = record.flushKB;
		if (fl > max) {
			max = fl;
		}
	}
	var level = getViewLevel(max);

	if(1 == level){
		showUnit = "KB";
	}else if( 2 == level){
		showUnit = "MB";
	}else {
		showUnit = "GB";
	}
	}

	return showUnit;
}

//判断页面的流量显示单位(KB or MB or GB)
function getViewLevel(max) {
	var level = 0;
	if (max <= 1024 * 10) {//KB
		level = 1;
	} else {
		if (1024 * 10 < max && max <= 1024 * 1024 * 10) {//MB
			level = 2;
		} else {//GB
			level = 3;
		}
	}
	return level;
}

var totalFlushDate= totalFlushDateF/*+'-01'*/;

//查询走势数据
function getAjaxLineChartData(businame){
	if('全网' != type){
		$('#subFlushTrend').html(type+"—＞"+businame+' 日时段走势');
	}else{
		$('#subFlushTrend').html(businame+'业务—＞日时段走势');
	}

	//alert(ydate+'--ydate---'+totalFlushDate+'---totalFlushDate----'+businame+'---businame');
	$.ajax({
		type : "post",
		url : "/mainIndex_queryHourData.do",
		dataType: 'json',
		data : {
			searchDateStart:ydate,
			totalFlushDate:totalFlushDate,
			busiName:businame
		},
		success : function(data) {
			buildMyLineChartData(data,businame);
		},
		error : function() {
			alert('获取数据失败!');
		}
	});
}
//获取地图数据
function getAjaxSpaceXmlData(businame){
	//showAreaType($('#space'+gridid).val());
	if('全网' != type){
		$('#subFlushSpace').html(type+"—＞"+businame+' 日空间分布');
	}else{
		$('#subFlushSpace').html(businame+'业务—＞日空间分布');
	}
	$.ajax({
		type : "post",
		url : "/mainIndex_querySpaceData.do",
		data : {
			searchDateStart:ydate,
			totalFlushDate:totalFlushDate,
			showunit:showunit,
			busiName:businame,
			spaceType:$('#space40').val()
		},
		success : function(xml) {
			$('#spacexml40').attr('value',xml)	;	
			var s1 = document.frm40.MantoEye_Mini.changeData(chartFlag);
			//var s2 = document.frm41.MantoEye_Mini.changeData(chartFlag);
		},
		error : function() {
			alert('服务器出错,请联系管理员!');
		}
	});
}
function loadDataString(){
	var xml ="  ";	
	xml = document.getElementById("spacexml40").value;			
	return xml;
}
function spaceTypeChange(){
	showAreaType($('#space40').val());
	getAjaxSpaceXmlData(activebusinn);
}
function dataTypeChange(){
	var dtp = $('#chdata40').val();
	if(dtp==1){
		document.frm40.MantoEye_Mini.changeView('total','流量');
	}else{
		document.frm40.MantoEye_Mini.changeView('imsis','用户数');
	}
}
//显示的区域维度
function showAreaType(splevel){
	switch(splevel)
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
/////////////////////////////////////////////////////////////////////
jQuery("#list20").jqGrid({
		/*url:'/eventLog_queryForJq.do?1=1',
		datatype : 'json',*/ 
		data: maindata,
		datatype: "local",
		height : 192,
		width : 492,
		hidegrid : false, 				     
		colNames : [ ' ', '内容', '发布时间'],
		colModel : [ {name : 'id',index : 'id',width : 12},
				 	 {name : 'vcEventAffect',index : 'vcEventAffect',width : 360,sortable : false}, 
				 	 {name : 'dtRecordTime',index : 'dtRecordTime',width : 80}],
		/*jsonReader: {
				     	root: "datalist",
				        repeatitems : false
				     },*/
		viewrecords:true,
		sortorder: "desc",
		sortname : 'id',
		pager: "#plist20",
		gridComplete:function(){
				var x=window.screen.width;				
				if(x=='1280'){
					jQuery("#list20").setGridWidth(612);
					jQuery("#list20").setGridHeight(217);
				}			
		},
		rowNum:6,
   		rowList:[2,6],
		caption : "<div style='height:12px;padding-top:8px;'>系统公告<div>"
	});

$(document).ready(function(){
	$('#subFlushTrend').html('全网业务—＞日时段走势');
//	$('#subFlushTrend').html('天流量用户时段走势-[全网]-业务');
	$('#subFlushSpace').html('全网业务—＞日空间分布');	
//	$('#subFlushSpace').html('天流量日空间分布-[全网]-业务');	
});
var showunit='MB';
function buildMyBarChartData(json,divid){
	//totalFlushDateF+'=='+ydate
	var title = /*"全网业务  "+*/totalFlushDateF+"至"+ydate+"  流量|用户数走势图";
	busiNameOut = "全网";	
	
	var xlabels = [] ;
	var times = [] ;
	var datas1 = [] ;
	var datas2 = [] ;
	var showxlabels = [];
	if(json!=''&&json.length>0){
		for(var i = 0 ; i<json.length;i++){	
			var record = json[i];
		 	xlabels[i] = record.fulldate; 	
		 	times[i] = record.fulldate; 	

		 	if( 'MB' == showunit){ 
				datas1[i] = StringToFloat(record.flushMB);
		 	}else if( 'KB' == showunit){
				datas1[i] = StringToFloat(record.flushKB);
		 	}else if( 'GB' == showunit){
				datas1[i] = StringToFloat(record.flushGB);
		 	}
		 	
		  	datas1[i] = {"top":datas1[i], "on-click": "callback_totalFlush('"+record.fulldate+"')"};

		 	//datas2[i] = StringToFloat(record.imsis);		
			datas2[i] = {"value":StringToFloat(record.imsis), "on-click": "callback_totalFlush('"+record.fulldate+"')"};	
		}		
	}else{

		var str1 = JSON.stringify(buildEmptyAxisChart());				
		if(!chartinit){
			chartinit = true;
			document.getElementById("chartxmldata9").value=str1;
			swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=9", "imgarea9", "100%", "100%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
		}else{
	  		var tmp1 = findSWF("imgarea9");	  	
  			tmp1.load(str1);	
		}

		return;
	}
	showxlabels = xlabels;
	//格式化X轴时间
	 xlabels = formatXDateLables(xlabels,8,"day");
	 var tdatas = [];
	 
	  //格式化Y轴数据（流量）
	var obj = new Object();
    obj.datas = datas1;
	obj.unit = showunit;
	var lobj = obj;
	var lunit="流量";
	if(lobj.unit!=""){
		lunit = lunit +"@@"+lobj.unit;
	}

 	tdatas[0] = buildBarChartTip(lobj.datas,showxlabels,lunit);
	 //格式化Y轴数据（用户数）
	var robj = formatNumberByWan2_2(datas2,times);
 	var runit="用户数";
	if(robj.unit!=""){
		runit = runit +"@@"+robj.unit;
	}
	//alert(robj.datas);
	tdatas[1] = buildLineChartTip(robj.datas,showxlabels,runit);
	
	 //如果有单位  则label与单位间用@@分开
	var labels = [lunit,runit];	
	 
	var jsons1 = buildMutileChartData(title,labels,xlabels,tdatas);

	var str1 = JSON.stringify(jsons1);
	//alert("-str1-"+str1);
	if(!chartinit){
		chartinit = true;
		document.getElementById("chartxmldata9").value=str1;
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=9", "imgarea9", "100%", "100%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
	}else{
	  	var tmp1 = findSWF("imgarea9");	  	
  		tmp1.load(str1);	
	}

	/*
	if(divid=='9'){
		 activebusinn = "全网";
		 getAjaxLineChartData('全网');
		 getAjaxSpaceXmlData('全网');
	}	
	*/
}

function callback_totalFlush(time){
	totalFlushDate=time;
	type = '全网';
	getAjaxLineChartData("全网");
	getAjaxSpaceXmlData("全网");
}
var type = "全网";
var chartinit = false;
function formatNumberByWan2_2(datas,times){
	var obj = new Object();
	var unit = "";
	var max ;
	if(datas.length>0){
		max = datas[0].value;
		for(var i=1;i<datas.length;i++){
			if(datas[i]>max){
				max = datas[i].value;
			}
		}
	}
	
	var formatdates = [];
	if(max>100000){//十万以上
		for(var i=0;i<datas.length;i++){
			formatdates[i] = {"value":(parseInt((datas[i].value/10000)*100+0.5))/100, "on-click": "callback_totalFlush('"+times[i]+"')"};
			unit = "万";
		}
	}else if(max>1000000000){//十亿以上
		for(var i=0;i<datas.length;i++){
			formatdates[i] = {"value":(parseInt((datas[i].value/100000000)*100+0.5))/100, "on-click": "callback_totalFlush('"+times[i]+"')"};
			unit = "亿";
		}
	}else{
		formatdates = datas;
	}

	obj.datas = formatdates;
	obj.unit = unit;
	return obj;
}

</script>




