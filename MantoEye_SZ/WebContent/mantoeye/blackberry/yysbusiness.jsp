<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>运营商分布</title><meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
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
		<script type="text/javascript" src="/js/common.js"></script>
		<script type="text/javascript" src="/js/common_grid.js"></script>
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
						<tr valign="top" style="font-size:1px;">
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
													<!--  <td style="width: 60px">
														<input type="radio" name="dataType" id="d_dataType1"
															value="0" checked style="width: 20px; border: 0px;" />
														GPRS
													</td>
													<td style="width: 60px">
														<input type="radio" name="dataType" value="1"
															id="d_dataType2" style="width: 20px; border: 0px;" />
														TD
													</td>-->
													<td class="condition_name">
														时间粒度：
													</td>
													<td class="condition_value">
														<div class="select">
															<div>
																<select name="timeLevel" id="d_timeLevel"
																	style="height: 21px" onchange="selChange();">
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
															</div>
														</div>
													</td>
													<td class="condition_name">
														时间：
													</td>
													<td class="condition_value">
														<input type="text" class="Wdate" style="height: 15px;"
															onclick="preStartDate();" name="searchDateStart"
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
												黑莓运营商分布图形
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
								<table cellspacing="0" cellpadding="0" border="0" width="100%"
									class="menubar">
									<tr valign="top">
										<td width="4px" height="24px">
											<div class="lefttitle"></div>
										</td>
										<td width="100%" height="24px">
											<div class="middletitle">
												黑莓运营商分布列表
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
<script type="text/javascript"><!--
var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :[
		{name : 'fullDate'        },
		{name : 'ldcName'      },
		{name : 'countryName'      },
		{name : 'upFlushMB'     },
		{name : 'downFlushMB'   },
		{name : 'totalFlushMB'   },
		{name : 'intImsis' },
		{name : 'nmAveFlushKB'}
	]
};
var colsConfig = [
		{ id : 'fullDate'      , header : "时间"  ,sortable:false   },
		{ id : 'ldcName'      , header : "运营商"    ,sortable:false  },
		{ id : 'countryName'      , header : "所属国家(地区)"  ,sortable:false    },
		{ id : 'upFlushMB'    , header : "上行流量(MB)",headAlign:'right' ,align:'right'  ,exportnumber:true  ,renderer:renderFormatDataInit2 },
		{ id : 'downFlushMB'     , header : "下行流量(MB)",headAlign:'right' ,align:'right'  ,exportnumber:true  ,renderer:renderFormatDataInit2  },
		{ id : 'totalFlushMB'  , header : "总流量(MB)",headAlign:'right' ,align:'right'  ,exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'intImsis' , header : "用户数",headAlign:'right' ,align:'right'   ,exportnumber:true  },
		{ id : 'nmAveFlushKB' , header : "平均流量(KB/人)" ,headAlign:'right' ,align:'right'  ,exportnumber:true  ,renderer:renderFormatDataInit2 }
];

var gridConfig={
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/blackberryyysbusiness_query.do?1=1',
	exportURL :'/blackberryyysbusiness_export.do?1=1' ,
	//saveURL : '/flexiGridTest_list1.do?1=1' ,
	dataset : dsConfig ,
	columns : colsConfig ,
	width:780,
	height:500,
	resizable : true,
	showGridMenu : false,
	allowCustomSkin : true,
	allowFreeze : true,
	allowGroup : true,
	allowHide : true,
	container : 'data_container', 
	beforeLoad:checkLogon,
	toolbarContent : 'nav | goto | pagesize | state' ,
	pageSize : getDispalyPageSize(1,1) ,
	remoteSort : false ,
	remotePaging:false,
	pageSizeList :  [10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
	onComplete:loadComplate
};

var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
} );
var hasinit = false;
var firstInit=true;

	var d_searchDateStart=new Date();	
	d_searchDateStart=d_searchDateStart.valueOf();
	d_searchDateStart=d_searchDateStart-24*60*60*1000;
	d_searchDateStart=new Date(d_searchDateStart); 
	var month=d_searchDateStart.getMonth()+1;
	var month=((d_searchDateStart.getMonth()+1)>9?(d_searchDateStart.getMonth()+1):("0"+(d_searchDateStart.getMonth()+1)));
	var day=(d_searchDateStart.getDate()>9?d_searchDateStart.getDate():("0"+d_searchDateStart.getDate()));
	d_searchDateStart=d_searchDateStart.getYear()+"-"+month+"-"+day;
	var hasinit = false;//第一次初始化图形数据标识
	var datachange = false;//第N次查询初始化数据标识
	var timeLevel_search="2";

function loadComplate(){
	var  obj = new Object ();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.hideColumn = 0 ;//隐藏的列数目
	obj.isCheckbox = false;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度
	//设置宽度
	var totalRecords = datagrid.getAllRows().length;//获取加载到的数据记录数目
	var width=window.screen.availWidth;
	//判断是否查询到数据
	judgeData(datagrid);
	initGridInfo(obj);
	if(firstInit==true){
		var pageSize=getDispalyPageSize(1,1);
		var index=0;
		$(".gt-pagesize-select option").each(function(i){
			if(this.text==pageSize){
				index=i;
			}
		})
		firstInit=false;
		$(".gt-pagesize-select").get(0).selectedIndex=index;
	}
	var date=$("#d_searchDateStart").val();
			if(date==''){
				$('#d_searchDateStart').val(d_searchDateStart);
			}
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
				datas1[i] = {"label":record.ldcName,"value":StringToFloat(record.totalFlushMB)};
				//datas2[i] = {"label":record.ldcName,"value":record.intImsis};
			
				datas2[i] = record.imsisRate;
				xlabels2[i] = record.ldcName;	
				showxlabels[i]=record.ldcName;
				showxdatas [i] =record.intImsis;
			}else{
				otherflush = otherflush + StringToFloat(record.totalFlushMB);
				otherimsis = otherimsis + record.intImsis;
				otherRate = otherRate + StringToFloat(record.imsisRate+"");
			}
			
			
		}
		//数据超出10条 多余的用其他
			if(otherflush!=0){
				datas1[10] = {"label":"本页非前10合计","value":otherflush};
				//datas2[10] = {"label":"本页非前10合计","value":otherimsis};
				
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
	//重置分页数据
	var pageinfo = datagrid.getPageInfo();
	pageinfo.pageNum = 1;
	datagrid.setPageInfo(pageinfo);
	timeLevel_search = $("#d_timeLevel option:selected").val();
	d_searchDateStart=$("#d_searchDateStart").val();
	var param={
		searchDateStart : d_searchDateStart,
		timeLevel_search :timeLevel_search
		
	};
	if(d_searchDateStart==''){
		alert('请选择时间!');
		return ;
	}
	hasinit = false;
	datachange = true;
	GT.$grid('datagrid').query( param );
}

function reset(){
	$('#d_searchDateStart').attr("value","");
	$("#d_timeLevel").get(0).selectedIndex=1;
	//$('#d_dataType1').attr("checked","true");//设置radio默认值
	
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

//获取服务器端图形数据
function getAjaxChartXmlData(){
	$.ajax({
		type : "post",
		url : "blackberryyysbusiness_getAjaxChartXmlData.do?1=1",
		data : {
			searchDateStart:d_searchDateStart,//时间
			timeLevel_search:timeLevel_search//时间粒度
		},
		success : function(xml) {
			
			if(!hasinit && datachange){
				document.frm.DoublePieChart.jsChangeData(xml);
				datachange = false;
			}else{
				document.getElementById("chartxmldata").value=xml;
			}
			hasinit = true;
		},
		error : function() {
			alert('服务器出错,请联系管理员!');
		}
	});
}
function initChartWidth(){
 	var width=window.screen.availWidth;
	var availW=1014;
	var availH=230;
	if(width==1024){
		availW=760;
	}else{
		availW=1014;
	}
 	return availW+"|250";
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
          handler : function(){
           //参数
			var params={
				searchDateStart : d_searchDateStart,
				timeLevel_search :timeLevel_search
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName='BB业务分布数据';//('+startTime_search+'至'+endTime_search+')';//文件名称
			fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
			fileObj.gridObj = datagrid;//当前grid对象
			fileObj.params = params;
			exportFile(fileObj);
          }
        }]
      });
	  toolbar.render();
});
//-->
</script>



