<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>用户归属分布</title><meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" type="text/css"
			href="/tools/gt-grid/gt_grid.css" />
		<link type="text/css" rel="stylesheet"
			href="/skin//Default/css/maincontent.css" />
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
			type="text/css"></link>
		<script type="text/javascript" src="/js/common.js"></script>
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
												cellpadding="0px;" border="0" >
												<tr valign="middle" >
													<td class="condition_name">
														时间粒度：
													</td>
													<td>
														<div class="select">
															<div>
																<select name="d_timeLevel" id="d_timeLevel"
																 style="height: 21px"  onchange="selChange();"> 
																	<!-- <option value="1"
																		<c:if test="${fn:contains(timeLevel,'1') }">selected</c:if>>
																		小时
																	</option> -->
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
													<td class="condition_name">
														时间：
													</td>
													<td>
														<input type="text" class="Wdate" style="height: 15px;"
															onclick="preStartDate();"
															name="searchDateStart" value="${searchDateStart}"
															id="d_searchDateStart" />
																<input type="hidden" id="d_searchDateEnd" />
																											
													</td>
													
													<td width="*"></td>
													<td width="200px">
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
												黑莓用户归属分布图形
											</div>
											<input type="hidden" value="" id="chartxmldata1" />
											<input type="hidden" value="" id="chartxmldata2" />
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
								<%--<div id="imgarea1" style="width: 100%; height: 100%">
									<div id="imgarea1" style="width: 100%; height: 100%">
									<iframe name="frm"
										src="/flash/DoublePieChart.html?getdata=js&dataflag=2"
										scrolling="no" id="frm"
										style="HEIGHT: 250px; VISIBILITY: inherit; width: 100%; Z-INDEX: 1"
										marginwidth="1" marginheight="1" frameborder="0"
										align="middle"></iframe>
								</div>
								</div>
							--%><div id="imgareas" style="width: 100%; height: 100%;border: 2px solid #008BD1;">
							
							<div id="imgarea1" style="width: 49%; height: 100%"></div>
							<div id="imgarea2" style="width: 49%; height: 100%"></div>
							
							<!--<table border="0"><tr><td>
							
							</td><td>
							</td></tr></table>
							--></div>
							</td>
						</tr>

						<tr>
							<td height="5px" bgcolor="#D3E0F2" width="100%"></td>
						</tr>
						<tr>
							<td>
								<table cellspacing="0" cellpadding="0" border="0" width="100%">
									<tr>
										<td width="4px" height="24px;">
											<img
												src="/skin/Default/images/MantoEye/maincontent/lefttitle.gif" />
										</td>
										<td width="100%"
											style="background: url(/skin/Default/images/MantoEye/maincontent/middletitle.gif) repeat-x; padding-left: 10px; font-size: 12px; font-weight: bold; color: #FFFFFF; vertical-align: middle;">
											黑莓用户归属分布列表
										</td>
										<td width="4px">
											<img
												src="/skin/Default/images/MantoEye/maincontent/righttitle.gif" />
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
				<td width="2"></td>
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
var timeLevel_search ='2';//查询时间粒度
var time_search =date.getYear()+'-'+((date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1)))+'-'+''+(date.getDate()>9?date.getDate():("0"+date.getDate())) ;//查询开始时间

//重置查询条件
function reset(){
	$('#d_searchDateStart').attr("value","");
	$("#d_timeLevel").get(0).selectedIndex=0;
}

//初始化查询时间
$(document).ready(function(){
	$('#d_searchDateStart').attr('value',time_search);
});

var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :[
		{name : 'fullDate'        },
		{name : 'countryName'      },
		{name : 'totalFlushMb'       },
		{name : 'averageFlushKb'     },
		{name : 'upFlushMb'     },
		{name : 'downFlushMb'  },
		{name : 'imsis'  }
	]
};
var colsConfig = [
		{ id : 'fullDate'      , header : "时间"   ,sortable:false   },
		{ id : 'countryName'    , header : "归属国家(地区)"  ,sortable:false   },
		{ id : 'totalFlushMb' , header : "流量(MB)"  ,headAlign:'right' ,align:'right'     ,exportnumber:true ,renderer:renderFormatDataInit2 },
		{ id : 'imsis' , header : "用户数"   ,headAlign:'right' ,align:'right'   ,exportnumber:true  },
		{ id : 'averageFlushKb'      , header : "平均流量(KB/人)"  ,headAlign:'right' ,align:'right'  ,exportnumber:true,renderer:renderFormatDataInit2   }
];

var gridConfig={
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/blackBerryCountry_query.do?1=1',
	exportURL :'/blackBerryCountry_export.do?1=1' ,
	dataset : dsConfig ,
	columns : colsConfig ,
	resizable : false,
	showGridMenu : false,
	container : 'data_container', 
	beforeLoad:checkLogon,
	toolbarPosition : 'bottom',
	toolbarContent : 'nav | goto | pagesize | state' ,
	pageSize : getDispalyPageSize(2,2) ,
	remoteSort : false ,
	remotePaging:false,
	pageSizeList :  [10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
	onComplete:loadComplate
};

var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
} );
var queryed = false;
function loadComplate(){
	//grid列表初始化信息
	var obj = new Object ();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.hideColumn = 0 ;//隐藏的列数目
	obj.isCheckbox = false;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度
	
	//判断是否查询到数据
	judgeData(datagrid);
	
	//初始化grid
	initGridInfo(obj);
	//组装图形数据
//	if(!chartinit||queryed){
		buildChartData(datagrid.getAllRows().length);	
//	}	
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
	var showlabel="";
	
	var text2 = '';
	var xlabels2 = [] ;
	var label2 = '占比@@%';
	var label22 = '用户数';
	var showxdatas = [];
	var showxlabels = [];
	var datas2 = [] ;
	
	var otherflush = 0 ;
	var otherimsis = 0 ;
	var otherRate = 0 ;
	
	var totaldataa = datagrid.dataset.data;
	
	if(totaldataa.length>0){
		for(var i = 0 ; i<totaldataa.length;i++){	
			var record = datagrid.getRecord(i);	 	
		 	if(text1==''){
		 		chartTime = record.fullDate;
		 		text1 = chartTime+' 流量占比图';
		 		text2 = chartTime+' 用户数占比图';
		 	}	
		 	if(i<10){ 	
			datas1[i] = {"label":record.countryName,"value":StringToFloat(record.totalFlushMb+"")};
			datas2[i] = {"label":record.countryName,"value":StringToFloat(record.imsis+"")};
			
			datas2[i] = record.imsisRate;
			xlabels2[i] = record.countryName;	
			showxlabels[i]=record.countryName;
			showxdatas [i] =record.imsis;
			}else{
				otherflush = otherflush + StringToFloat(record.totalFlushMb+"");
				otherimsis = otherimsis + StringToFloat(record.imsis+"");
				otherRate = otherRate + StringToFloat(record.imsisRate+"");
			}
		}
		//数据超出10条 多余的用其他
			if(otherflush!=0){
				datas1[10] = {"label":"非前10合计","value":otherflush};
				//datas2[10] = {"label":"非前10合计","value":otherimsis};
				datas2[10] = otherRate;
				xlabels2[10] = "非前10合计";	
				showxlabels[10]="非前10合计";
				showxdatas [10] =otherimsis;
			}	
				
	datas2 = buildHBarChartTip(datas2,showxlabels,label2,showxdatas,label22);		
	}else{
		chartinit = false;
		var str1 = JSON.stringify(buildEmptyAxisChart());
		document.getElementById("chartxmldata1").value=str1;
		document.getElementById("chartxmldata2").value=str1;
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=imsis", "imgarea2", "99%", "99%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=flush", "imgarea1", "0%", "0%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
		return;
	}
	var jsons1 = buildPieChartData(text1,label1,datas1);
	var str1 = JSON.stringify(jsons1);
	var jsons2 = buildPercentChartData(text2,[label2],xlabels2,[datas2]);
	var str2 = JSON.stringify(jsons2);
	if(!chartinit){
		chartinit = true;
		document.getElementById("chartxmldata1").value=str1;
		document.getElementById("chartxmldata2").value=str2;
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=flush", "imgarea1", "40%", "100%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
		swfobject.embedSWF("/flash/open-flash-chart-mw.swf?id=imsis", "imgarea2", "59%", "100%", "9.0.0","expressInstall.swf",'',{"wmode":"opaque"});
	}else{
	  	var tmp1 = findSWF("imgarea1");
	  	var tmp2 = findSWF("imgarea2");
  		tmp1.load(str1);
  		tmp2.load(str2);
	}
}
/*初始化图表*/
function open_flash_chart_data(id)
{
	if(id=='flush'){
		return document.getElementById("chartxmldata1").value;
	}else{
		return document.getElementById("chartxmldata2").value;
	}
}
/*图表回调函数*/
function callback(label){
	//alert("label:"+label);
}

///////////////////////////////////////////



//// 查询表单的查询函数 ////
function query() {
queryed = true;
//重置分页数据
	var pageinfo = datagrid.getPageInfo();
	pageinfo.pageNum = 1;
	datagrid.setPageInfo(pageinfo);
	timeLevel_search = GT.U.getValue(GT.$('d_timeLevel'));
	 time_search = GT.U.getValue(GT.$('d_searchDateStart'));
	var param={
		searchdate : time_search,
		stattype :timeLevel_search
	};
	GT.$grid('datagrid').query( param );
}

$(document).ready(function(){
	var $inputObj=$("input[type='text']").not( $(".Wdate")[0] );
	$inputObj.each(function(){
		$obj=$(this);
		$obj.bind("focus",function(){
			$(this).css("background","#F5E5E1");
		});
		$obj.bind("blur",function(){
			$(this).css("background","#ffffff");
		});
	})
})
function changeDataFormat(value){
	if(value=="hour"){
	 	document.getElementById("d_searchDateStart").onclick="new WdatePicker({dateFmt:'yyyy-MM-dd HH:00:00'})";
	}else{
		document.getElementById("d_searchDateStart").onclick="new WdatePicker({dateFmt:'yyyy-MM-dd'})";
	}
}
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
          handler :exportToXls
        }]
      });
	  toolbar.render();
});

function exportToXls(){
 	var params={
		searchdate : time_search,
		stattype :timeLevel_search
	};
			//导出
	var fileObj = new Object();
	fileObj.fileName='黑莓用户归属数据列表';//('+startTime_search+'至'+endTime_search+')';//文件名称
	fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
	fileObj.gridObj = datagrid;//当前grid对象
	fileObj.params = params;
	exportFile(fileObj);
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


