<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>区域分析</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" type="text/css"
			href="/tools/gt-grid/gt_grid.css" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/maincontent.css" />
		<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js">
</script>
		<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js">
</script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js">
</script>
		<!-- 列表工具栏 -->
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
			type="text/css"></link>
		<script type="text/javascript"
			src="/js/jquery/extend.tab/js/Toolbar.js">
</script>
		<script type="text/javascript" src="/js/common_grid.js">
</script>
		<!-- OFC图表 -->
		<script type="text/javascript" src="/flash/js/swfobject.js">
</script>
		<script type="text/javascript" src="/flash/js/json/json2.js">
</script>
		<script type="text/javascript" src="/flash/js/BarLineChartUtil.js">
</script>
		<script type="text/javascript" src="/flash/js/ParseUtils.js">
</script>

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
						style="width: 100%">
						<tr valign="top">
										<td width="100%" class="condition_down">
											<table id="query_condition" cellspacing="0px"
												cellpadding="0px;boder="0">
												<tr valign="middle">
													<td class="condition_name">
														区域维度：
													</td>
													<td class="condition_value">
														<select name="areaType" id="areaType" style="height: 21px">
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
											
											</table>
										</tr>
										<tr>
										<td height="5px" bgcolor="#D3E0F2" width="100%"></td>
									</tr>
<tr>
								<table cellspacing="0" cellpadding="0" border="0" width="100%"
									class="menubar">
									<tr valign="top">
										<td width="4px" height="24px">
											<div class="lefttitle"></div>
										</td>
										<td width="100%" height="24px">
											<div class="middletitle" id="headTitle">
												区域分析图形
											</div>
											<input type="hidden" value="" id="chartxmldata" />
										</td>
										<td width="4px">
											<div class="righttitle"></div>
										</td>
									</tr>
								</table>
						</tr>
						<tr>
							<td height="220px" width="100%"
											style="padding: 15px 10px; padding-top: 1px; background: #ffffff">

								<div id="imgareas"
									style="width: 98%; height: 100%; border: 2px solid #008BD1;">
									<div id="imgarea" style="width: 100%; height: 100%"></div>
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
															区域分析数据
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
				</td>
			</tr>
		</table>
	</body>
</html>

<script type="text/javascript">
var obj = window.dialogArguments ;
	var nmStatFlushLayeringId = obj.nmStatFlushLayeringId;
	var timeLevel_search = obj.timeLevel_search;
	var time_search = obj.time_search;
	var flushlevel = obj.flushlevel;
	var areaType=1;
$('#distribute_data_div').html("（"+flushlevel+'）KB的区域分析数据');
$('#headTitle').html("("+flushlevel+')KB的区域分析图形');
var sortnmFlush = false;
var dsConfig = {
	uniqueField : 'null',
	fields : [
		{name : 'fullDate'        },
		{name : 'vcAreaName'       },
		{name : 'totalFlushMB'     },
		{name : 'totalFlushKB'    },
		{name : 'totalFlushGB'   },
		{name : 'intImsis'  },
		{name : 'nmAveFlushKB'   }

	]
};

var colsConfig = [ { id : 'fullDate'      , header : "时间",sortable:false     },
					{ id : 'vcAreaName'      , header : "区域" ,sortable:true     },
					{ id : 'totalFlushMB'  , header : "总流量(MB)"  ,sortable:true ,headAlign:'right' ,align:'right' ,exportnumber:true,renderer:renderFormatDataInit2 },
					{ id : 'totalFlushKB'  , header : "总流量(KB)" ,sortable:true ,headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 },
					{ id : 'totalFlushGB'  , header : "总流量(GB)" ,sortable:true ,headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 },
					{ id : 'intImsis' , header : "用户数"      ,sortable:true ,headAlign:'right' ,align:'right',exportnumber:true },
					{ id : 'nmAveFlushKB'  , header : "平均流量(KB/人)",headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2  ,sortable:true}
		];


var gridConfig = {
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL : '/flushLevelAreaAnalyse_query.do?nmStatFlushLayeringId='+obj.nmStatFlushLayeringId+'&time_search='+time_search+'&timeLevel_search='+timeLevel_search,
	exportURL :'/flushLevelAreaAnalyse_export.do?1=1' ,
	dataset : dsConfig,
	columns : colsConfig,
	width : 780,
	height : 400,
	resizable : false,
	beforeLoad : checkLogon,
	container : 'data_container',
	beforeLoad : checkLogon,
	toolbarContent : 'nav | goto | pagesize | state',
	pageSize : getLongPageSize(),
	remoteSort : true,
	remotePaging : true,
	pageSizeList : [ 5, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,
			24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
			45, 50, 100 ],
	onComplete : loadComplate
};
function getLongPageSize() {
	var size = getDispalyPageSize(1, 1);
	if (size < 10) {
		size = 10;
	}
	return size;
}



var datagrid = new GT.Grid(gridConfig);
GT.Utils.onLoad(function() {
	datagrid.render();
});



function buildUnit() {
	var totalRecords = datagrid.getAllRows().length;
	var max = 0;
	if (typeof (datagrid.getRecord(0)) != "undefined") {
		for (var i = 0; i < totalRecords; i++) {
			var record = datagrid.getRecord(i);
			var fl = record.totalFlushKB;
			if (fl > max) {
				max = fl;
			}
		}
		var level = getViewLevel(max);
		if (level == 1) {//KB
			datagrid.getColumn("totalFlushKB").show();
			datagrid.getColumn("totalFlushGB").hide();
			datagrid.getColumn("totalFlushMB").hide();
			showUnit = "KB";
		} else {
			if (level == 3) {//GB
				datagrid.getColumn("totalFlushKB").hide();
				datagrid.getColumn("totalFlushGB").show();
				datagrid.getColumn("totalFlushMB").hide();
				showUnit = "GB";
			} else {//MB
				datagrid.getColumn("totalFlushKB").hide();
				datagrid.getColumn("totalFlushGB").hide();
				datagrid.getColumn("totalFlushMB").show();
				showUnit = "MB";
			}
		}
	}
	return showUnit;
}


//页面呈现的单位
var showunit = 'MB';
var firstInit=true;
function loadComplate(){
	buildUnit();
	//判断是否查询到数据
	judgeData(datagrid,1);
	var width=window.screen.availWidth;
	//根据分辨率初始化grid列宽
	if(!columninit){
		if(width==1024){
			initColumnWidthWithPop(datagrid,792,2);
			
		}else{
			initColumnWidthWithPop(datagrid,973,2);
			
		}
	}
	
	if(!initHasData){
		sortInit = true;
	}
	
	if(sortInit && initHasData){
		showunit = buildUnit();
		sortInit = false;
	}
	buildChartData(datagrid.getAllRows().length);
}


/*图表是否已经初始化*/
var chartinit = false;
/*组装图形数据*/
function buildChartData(totalRecords){
		
	var title = flushlevel+"KB的区域流量|用户数分析图";
	
	var xlabels = [] ;
	var datas1 = [] ;
	var datas2 = [] ;
	if(typeof(datagrid.getRecord(0))!="undefined"){
		for(var i = 0 ; i<totalRecords;i++){	
			var record = datagrid.getRecord(i);	 
			if(i<10){//图形只显示前20 的数据
				xlabels[i] = record.vcAreaName;
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

//grid查询
function query() {
	//重置分页数据
	var pageinfo = datagrid.getPageInfo();
	pageinfo.pageNum = 1;
	datagrid.setPageInfo(pageinfo);
	areaType = $("#areaType option:selected").val();
	var param={
		areaType:areaType
	};
	GT.$grid('datagrid').query( param );
}

//初始化grid工具栏
$(document).ready(function() {
	var toolbar = new Toolbar( {
		renderTo : 'toolbar',
		//border: 'top',
		items : [ {
			type : 'button',
			text : '导出',
			bodyStyle : 'xls',
			useable : 'T',
			handler : function() {

				var params = {
					areaType:areaType,
					nmStatFlushLayeringId:nmStatFlushLayeringId,
				timeLevel_search:timeLevel_search,
				time_search:time_search
				};
				//导出

			var fileObj = new Object();
			fileObj.fileName = '区域分析流量数据';
			fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
			fileObj.gridObj = datagrid;//当前grid对象
			fileObj.params = params;
			exportFile(fileObj);
		}
		} ]
	});
	toolbar.render();

});

$(document).ready(function() {
	window.onerror = killErrors;
})
function killErrors() {
	return true;
}
</script>


