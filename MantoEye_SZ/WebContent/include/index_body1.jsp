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
		<style>
html,body {
	margin: 0; /* Remove body margin/padding */
	padding: 0;
	overflow: hidden; /* Remove scroll bars on browser window */
	/*font-size: 70%;*/
}

.myfullinsert {
	width: 100%;
	height: 2px;
	font-size: 0px;
	background-color: #DEECFD;
	border-bottom: 1px solid #8DB2E3;
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
	filter: progid : DXImageTransform.Microsoft.BasicImage ( rotation = 3 );
}
</style>

		<script src="/tools/jqgrid/js/jquery-1.4.2.min.js"
			type="text/javascript"></script>
		<script src="/tools/jqgrid/js/jquery-ui-1.8.2.custom.min.js"
			type="text/javascript"></script>
		<script src="/tools/jqgrid/js/jquery.layout.js" type="text/javascript"></script>
		<script src="/tools/jqgrid/js/i18n/grid.locale-en.js"
			type="text/javascript"></script>
		<script src="/tools/jqgrid/js/grid.base.js" type="text/javascript"></script>
		<script src="/tools/floatbox/jquery.floatbox.1.0.8.js" type="text/javascript"></script>

		<!-- 图标 -->
		<!-- OFC图表 -->
		<script type="text/javascript" src="/flash/js/swfobject.js"></script>
		<script type="text/javascript" src="/flash/js/json/json2.js"></script>
		<script type="text/javascript" src="/flash/js/DoubleLineChartUtil.js"></script>
		<script type="text/javascript" src="/flash/js/PercentBarChartUtils.js"></script>
		<script type="text/javascript" src="/flash/js/ParseUtils.js"></script>
	</head>
	<body id="master">
		<div id="mainarea">
			<div class="myfullinsert"></div>
			<table>
				<tr>
					<td id="td11"
						style="width: 500px; height: 278px; border: 1px solid #8DB2E3">
						<table>
							<tr>
								<td style="width: 174px;">
									<table id="list10"></table>
								</td>
								<td>
									<div id="RightPane1">
										<!-- Tabs pane -->
										<div id="tabs1">
											<ul>
												<li>
													<a href="#imgareas10">占比</a>
												</li>
												<li>
													<a href="#imgareas11">走势</a>
												</li>
												<li>
													<a href="#imgareas12">地图</a>
												</li>
												<select id="space10" onchange="spaceTypeChange('10');" style="height:24px;display:none">
												<option value="1" selected>BSC</option>
												<option value="2">SGSN</option>
												<option value="3">街道办</option>
												<option value="4">营销片区</option>
												<option value="5">分公司</option>
												</select>
											</ul>
											<div id="imgareas10" style="width: 260px; height: 220px;">
												<input type="hidden" value="" id="chartxmldata10" />
												<div id="imgarea101" style="width: 0%; height: 0%"></div>
											</div>
											<div id="imgareas11" style="width: 260px; height: 220px;">
												<input type="hidden" value="" id="chartjsonxdatax10" />
												<div id="imgarea102" style="width: 0%; height: 0%"></div>
											</div>
											<div id="imgareas12" style="width: 260px; height: 220px;">
												<input type="hidden" value="" id="spacexml10" />
												<iframe name="frm10"
													src="/flash/MantoEye_Mini.html?mapflag=bsc&dataflag=total&dataname=总流量"
													scrolling="no" id="frm10"
													style="HEIGHT: 220px; VISIBILITY: inherit; width: 260px; Z-INDEX: 1"
													marginwidth="1" marginheight="1" frameborder="0"
													align="middle"></iframe>
											</div>
										</div>
									</div>
								</td>
							</tr>
						</table>
					</td>
					<td id="td12"
						style="width: 500px; height: 278px; border: 1px solid #8DB2E3">
						<table id="list20"></table>
						
							<div id="ffcc">ccccc</div>
					</td>
				</tr>
				<tr>
					<td id="td21"
						style="width: 500px; height: 278px; border: 1px solid #8DB2E3">
						<table>
							<tr>
								<td style="width: 174px;">
									<table id="list30"></table>
								</td>
								<td>
									<div id="RightPane3">
										<!-- Tabs pane -->
										<div id="tabs3">
											<ul>
												<li>
													<a href="#imgareas30">占比</a>
												</li>
												<li>
													<a href="#imgareas31">走势</a>
												</li>
												<li>
													<a href="#imgareas32">地图</a>
												</li>
												<select id="space30" onchange="spaceTypeChange('30');" style="height:22px;display:none">
												<option value="1" selected>BSC</option>
												<option value="2">SGSN</option>
												<option value="3">街道办</option>
												<option value="4">营销片区</option>
												<option value="5">分公司</option>
												</select>
											</ul>
											<div id="imgareas30" style="width: 260px; height: 220px;">
												<input type="hidden" value="" id="chartxmldata30" />
												<div id="imgarea301" style="width: 0%; height: 0%"></div>
											</div>
											<div id="imgareas31" style="width: 260px; height: 220px;">
												<input type="hidden" value="" id="chartjsonxdatax30" />
												<div id="imgarea302" style="width: 0%; height: 0%"></div>
											</div>
											<div id="imgareas32" style="width: 260px; height: 220px;">
												<input type="hidden" value="" id="spacexml30" />
												<iframe name="frm30"
													src="/flash/MantoEye_Mini.html?mapflag=bsc&dataflag=total&dataname=总流量"
													scrolling="no" id="frm30"
													style="HEIGHT: 220px; VISIBILITY: inherit; width: 260px; Z-INDEX: 1"
													marginwidth="1" marginheight="1" frameborder="0"
													align="middle"></iframe>
											</div>
										</div>
									</div>
								</td>
							</tr>
						</table>
					</td>
					<td id="td22"
						style="width: 500px; height: 278px; border: 1px solid #8DB2E3">
						<table>
							<tr>
								<td style="width: 174px;">
									<table id="list40"></table>
								</td>
								<td>
									<div id="RightPane4">
										<!-- Tabs pane -->
										<div id="tabs4">
											<ul>
												<li>
													<a href="#imgareas40">占比</a>
												</li>
												<li>
													<a href="#imgareas41">走势</a>
												</li>
												<li>
													<a href="#imgareas42">地图</a>
												</li>
												<select id="space40" onchange="spaceTypeChange('40');" style="height:22px;display:none">
												<option value="1" selected>BSC</option>
												<option value="2">SGSN</option>
												<option value="3">街道办</option>
												<option value="4">营销片区</option>
												<option value="5">分公司</option>
												</select>
											</ul>
											<div id="imgareas40" style="width: 260px; height: 220px;">
												<input type="hidden" value="" id="chartxmldata40" />
												<div id="imgarea401" style="width: 0%; height: 0%"></div>
											</div>
											<div id="imgareas41" style="width: 260px; height: 220px;">
												<input type="hidden" value="" id="chartjsonxdatax40" />
												<div id="imgarea402" style="width: 0%; height: 0%"></div>
											</div>
											<div id="imgareas42" style="width: 260px; height: 220px;">
												<input type="hidden" value="" id="spacexml40" />
												<iframe name="frm40"
													src="/flash/MantoEye_Mini.html?mapflag=bsc&dataflag=total&dataname=总流量"
													scrolling="no" id="frm40"
													style="HEIGHT: 220px; VISIBILITY: inherit; width: 260px; Z-INDEX: 1"
													marginwidth="1" marginheight="1" frameborder="0"
													align="middle"></iframe>
											</div>
										</div>
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
	var ydate = '2010-08-01';
	var chartFlag = 'bsc';
	var businn10 ="";
	var businn30 ="";
	var businn40 ="";
	
	var changedSpace = "10";
	
	jQuery("#list10").jqGrid( {
		url:'/mainIndex_query.do?dataType=flush',
		datatype : 'json', 
		height : '100%',
		width : '100%',
		hidegrid : false, 				     
		colNames : [ ' ', '业务', '流量(M)', '用户数' ,'流量占比','用户数占比'],
		colModel : [ {name : 'top',index : 'top',width : 12,sorttype : "int"},
				 	 {name : 'name',index : 'name',width : 48,sortable : false}, 
				 	 {name : 'flushGB',index : 'flushGB',width : 50,align : "right",sorttype : "float",formatter : "number"},
				 	 {name : 'imsis',index : 'imsis',width : 56,align : "right",sorttype : "int"},
				 	 {name : 'flushRate',index : 'flushRate',hidden:true},
				 	 {name : 'imsisRate',index : 'imsisRate',hidden:true} ],
		jsonReader: {
				     	root: "datalist",
				        repeatitems : false
				     },
		viewrecords:true,
		sortname : 'id',
		onCellSelect:function(rowid,icol,cellcontent,e){
			var record = jQuery("#list10").jqGrid('getRowData',rowid);		
			businn10 = 	record.name;	
			getAjaxLineChartData(businn10,'10');
			getAjaxSpaceXmlData(businn10,'10');
		},
		gridComplete:function(){
			var record = jQuery("#list10").jqGrid('getRowData',1);		
			businn10 = 	record.name;	
			getAjaxLineChartData(businn10,'10');
			buildMyPercentChartData('10');
			getAjaxSpaceXmlData(businn10,'10');
		},
		caption : "十大业务"
	});
	jQuery("#list30").jqGrid( {
		url:'/mainIndex_query.do?dataType=wap',
		datatype : 'json', 
		height : '100%',
		width : '100%',
		hidegrid : false, 				     
		colNames : [ ' ', '业务', '流量(M)', '用户数' ,'流量占比','用户数占比'],
		colModel : [ {name : 'top',index : 'top',width : 12,sorttype : "int"},
				 	 {name : 'name',index : 'name',width : 48,sortable : false}, 
				 	 {name : 'flushGB',index : 'flushGB',width : 50,align : "right",sorttype : "float",formatter : "number"},
				 	 {name : 'imsis',index : 'imsis',width : 56,align : "right",sorttype : "int"},
				 	 {name : 'flushRate',index : 'flushRate',hidden:true},
				 	 {name : 'imsisRate',index : 'imsisRate',hidden:true} ],
		jsonReader: {
				     	root: "datalist",
				        repeatitems : false
				     },
		viewrecords:true,
		sortname : 'id',
		onCellSelect:function(rowid,icol,cellcontent,e){
			var record = jQuery("#list30").jqGrid('getRowData',rowid);	
			businn30 = 	record.name;	
			getAjaxLineChartData(businn30,'30');
			getAjaxSpaceXmlData(businn30,'30');
		},
		gridComplete:function(){
			var record = jQuery("#list30").jqGrid('getRowData',1);	
			businn30 = 	record.name;
			getAjaxLineChartData(businn30,'30');
			buildMyPercentChartData('30');
			getAjaxSpaceXmlData(businn30,'30');
		},
		caption : "十大WAP网站"
	});
	jQuery("#list40").jqGrid( {
		url:'/mainIndex_query.do?dataType=cmcc',
		datatype : 'json', 
		height : '100%',
		width : '100%',
		hidegrid : false, 				     
		colNames : [ ' ', '业务', '流量(M)', '用户数' ,'流量占比','用户数占比'],
		colModel : [ {name : 'top',index : 'top',width : 12,sorttype : "int"},
				 	 {name : 'name',index : 'name',width : 48,sortable : false}, 
				 	 {name : 'flushGB',index : 'flushGB',width : 50,align : "right",sorttype : "float",formatter : "number"},
				 	 {name : 'imsis',index : 'imsis',width : 56,align : "right",sorttype : "int"},
				 	 {name : 'flushRate',index : 'flushRate',hidden:true},
				 	 {name : 'imsisRate',index : 'imsisRate',hidden:true} ],
		jsonReader: {
				     	root: "datalist",
				        repeatitems : false
				     },
		viewrecords:true,
		sortname : 'id',
		onCellSelect:function(rowid,icol,cellcontent,e){
			var record = jQuery("#list40").jqGrid('getRowData',rowid);		
			businn40 = 	record.name;	
			getAjaxLineChartData(businn40,'40');
			getAjaxSpaceXmlData(businn40,'40');
		},
		gridComplete:function(){
			var record = jQuery("#list40").jqGrid('getRowData',1);		
			businn40 = 	record.name;	
			getAjaxLineChartData(businn40,'40');
			buildMyPercentChartData('40');
			getAjaxSpaceXmlData(businn40,'40');
		},
		caption : "十大移动自有业务"
	});
	$(document).ready(function() {
		//buildMyPercentChartData();
		//buildMyLineChartData();
	});
	///////////////////图表(1)////////////////////////
	/*组装图形数据*/
	function buildMyPercentChartData(gridid) {
		var chartid= "imgarea"+gridid+"1";
	
		var label1 = '流量占比@@%';
		var datas1 = [];
	
		var label2 = '用户数占比@@%';		
		var datas2 = [];
		
		var text = '';	
		var xlabels = [];
		var showxlabels = [];
		
		var ids = jQuery("#list"+gridid).jqGrid('getDataIDs');

		var showlabel = "";
		if (ids.length>0) {
			for ( var i = 0; i < ids.length; i++) {
				var record = jQuery("#list"+gridid).jqGrid('getRowData',i+1);
				if (text == '') {
					text = ydate + ' 全网占比图';
				}
				datas1[i] = record.flushRate;
				datas2[i] = record.imsisRate;				
				
				xlabels[i] = record.name;
				showxlabels[i] = record.name;				
			}
			datas1 = buildBarChartTip(datas1, showxlabels, label1);
			datas2 = buildBarChartTip(datas2, showxlabels, label2);

			var jsons1 = buildPercentChartData(text, [ label1, label2 ],
					xlabels, [ datas1, datas2 ]);
			var str1 = JSON.stringify(jsons1);

				document.getElementById("chartxmldata"+gridid).value = str1;
				swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id="+gridid,
						chartid, "100%", "100%", "9.0.0",
						"expressInstall.swf", '', {
							"wmode" : "opaque"
						});			
		} else {
			var str1 = JSON.stringify(buildEmptyAxisChart());
			document.getElementById("chartxmldata"+gridid).value = str1;
			swfobject.embedSWF("/flash/open-flash-chart.swf?id="+gridid, chartid,
					"99%", "99%", "9.0.0", "expressInstall.swf", '', {
						"wmode" : "opaque"
					});
			return;
		}
	}
	///////////////////图表(2)////////////////////////
	/*组装图形数据*/
	function buildMyLineChartData(json,busiName,gridid) {
		var chartid= "imgarea"+gridid+"2";
		var title = busiName + ydate+"小时走势图";

		var xlabels = [];
		var datas1 = [];
		var datas2 = [];
		var dddatas = json.datalist;
		if (dddatas != '' && dddatas.length > 0) {
			for ( var i = 0; i < dddatas.length; i++) {
				var record = dddatas[i];
				xlabels[i] = record.fulldate;
				datas1[i] = record.flushKB;
				datas2[i] = record.imsis;
			}
		} else {
			var str1 = JSON.stringify(buildEmptyAxisChart());

				document.getElementById("chartxmldata"+gridid).value = str1;
				swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=3",
						chartid, "100%", "100%", "9.0.0",
						"expressInstall.swf", '', {
							"wmode" : "opaque"
						});			
			return;
		}
		var showxlabels = xlabels;
		//格式化X轴时间
		xlabels = formatXDateLables(xlabels,4,"hour");
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

			document.getElementById("chartjsonxdatax"+gridid).value = str1;
			swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=xdatax"+gridid,
					chartid, "100%", "100%", "9.0.0", "expressInstall.swf",
					'', {"wmode" : "opaque"});						
	}
	/*初始化图表*/
	function open_flash_chart_data(id) {
		var ssid = id+"";
		if(ssid.indexOf('xdatax')!=-1){
			return document.getElementById("chartjson"+id).value;
		}else{			
			return document.getElementById("chartxmldata"+id).value;
		}		
	}	
//查询走势数据
function getAjaxLineChartData(businame,gridid){
	$.ajax({
		type : "post",
		url : "/mainIndex_queryHourData.do",
		dataType: 'json',
		data : {
			searchDateStart:ydate,
			busiName:businame
		},
		success : function(data) {
			buildMyLineChartData(data,businame,gridid);
		},
		error : function() {
			alert('获取归属地数据失败!');
		}
	});
}
//获取地图数据
function getAjaxSpaceXmlData(businame,gridid){
	showAreaType($('#space'+gridid).val());
	$.ajax({
		type : "post",
		url : "/mainIndex_querySpaceData.do",
		data : {
			searchDateStart:ydate,
			busiName:businame,
			spaceType:$('#space'+gridid).val()
		},
		success : function(xml) {
			$('#spacexml'+gridid).attr('value',xml)	;	
			if(gridid=='10'){
				document.frm10.MantoEye_Mini.changeData(chartFlag);				
			}else if(gridid=='30'){
				document.frm30.MantoEye_Mini.changeData(chartFlag);
			}else{
				document.frm40.MantoEye_Mini.changeData(chartFlag);
			}
		},
		error : function() {
			alert('服务器出错,请联系管理员!');
		}
	});
}
function loadDataString(){
	var xml ="  ";	
	if(changedSpace=='10'){
		xml = document.getElementById("spacexml10").value;		
	}else if(changedSpace=='30'){
		xml = document.getElementById("spacexml30").value;		
	}else{
		xml = document.getElementById("spacexml40").value;
	}	
	return xml;
}
function spaceTypeChange(gridid){
	if(gridid=='10'){
			getAjaxSpaceXmlData(businn10,gridid);			
	}else if(gridid=='30'){
			getAjaxSpaceXmlData(businn30,gridid);
	}else{
			getAjaxSpaceXmlData(businn40,gridid);
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
/////////////////////////////TAB标签页///////////////////////////////////////
jQuery(document).ready(function(){
    //$('#switcher').themeswitcher();
	var maintab1 =jQuery('#tabs1','#RightPane1').tabs({
          select: function(event, ui) {
          	if(ui.panel.id=='imgareas12'){
          		$('#space10').css('display','block');
          	}else{
          		$('#space10').css('display','none')
          	}
          }
    });
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
          		$('#space40').css('display','block');
          	}else{
          		$('#space40').css('display','none')
          	}
        }
    });
});	
/////////////////////////////////////////////////////////////////////
jQuery("#list20").jqGrid( {
		url:'/mainIndex_query.do?dataType=flush',
		datatype : 'json', 
		height : '240',
		width : '480',
		hidegrid : false, 				     
		colNames : [ ' ', '内容', '发布时间'],
		colModel : [ {name : 'top',index : 'top',width : 12,sorttype : "int"},
				 	 {name : 'name',index : 'name',width : 300,sortable : false}, 
				 	 {name : 'flushGB',index : 'flushGB',width : 100,align : "right",sorttype : "float",formatter : "number"}],
		jsonReader: {
				     	root: "datalist",
				        repeatitems : false
				     },
		viewrecords:true,
		sortname : 'id',
		/*grouping:true,
   		groupingView : {
   			groupField : ['name']
   		},*/
		caption : "系统公告"
	});
$("#ffcc").click(function () {
$.floatbox({
        content: '<a  href="javascript:parent.parent.location.href=\'/welcome.jsp\'"><span style="font-size: 12px">进入系统</span> </a>',
        fade: true
    });
});
</script>



