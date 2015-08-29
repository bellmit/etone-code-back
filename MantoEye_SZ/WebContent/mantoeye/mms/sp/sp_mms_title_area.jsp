<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>SP彩信按主题区域统计</title>
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
		<script type="text/javascript" src="/js/common_grid.js"></script>
		<style>
.dialog_main_class {
	text-align: left;
	display: none;
	background: #eceef3;
	border: 1px solid #336699;
	z-index: 99;
	position: absolute;
	width: 347px;
	height: 230px;
	display: none;
	overflow-x: hidden;
	overflow-y: hidden;
}

.dialog_head_class {
	padding: 0px;
	position: relative;
	height: 20px;
	width: 100%;
	border-bottom: 1px solid #b0c7d7;
	background-image: url(/mantoeye/dialog/toolbar_bg.gif);
}

.dialog_table_class input {
	margin: 15px 0 0 0px;
	height: 14px;
	width: 180px;
}

.dialog_table_class tr td {
	padding-left: 20px;
}

.dialog_table_class select {
	height: 20px;
	width: 185px;
}

.dialog_head_icon_class {
	float: left;
	display: block;
	margin: 2px;
}

.dialog_head_title_class {
	float: left;
	margin-top: 3px;
}

.dialog_button_class {
	width: 99%;
	text-align: center;
}

.dialog_button_class button {
	margin: 8px;
}

.dialog_button_class .dialog_button {
	vertical-align: middle;
	line-height: 18px;
	height: 21px;
	border: 1px solid #336699;
	background: url(/mantoeye/dialog/input_button_bg.gif) repeat-x 0px -2px;
}

.dialog_msg_class {
	height: 55px;
	width: 220px;
	background: #eceef3;
	border: 1px solid #336699;
	position: absolute;
	z-index: 100;
	display: none;
}

.dialog_msg_class img {
	margin-left: 10px;
	margin-top: 8px;
}
</style>
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
													<td>
														<select class="condition_value" name="spaceLevel_search"
															style="height: 21px" id="spaceLevel_search"
															onchange="showAreaType()">
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
											<table style="margin-left: -1px">
												<tr valign="middle">
													<td
														style="width: 72px; height: 22px; cursor: default; padding: 0px 1px 0px 18px; color: #ffffff; font-size: 11px;">
														彩信主题：
													</td>
													<td style="width: 2px"></td>
													<td height="22px;">
														<input type="text" value="" id="mms_title" readonly
															onclick="showDialog()"
															style="width: 345px; height: 16px; border: 1px solid #FFDDEE; font-size: 11px; color: #163877; display: block;" />
														<div id="mms_title_dialog" class="dialog_main_class"
															style="display: none;">
															<div class="dialog_head_class">
																<div class="dialog_head_icon_class">

																</div>
																<div class="dialog_head_title_class">
																	彩信主题选择对话框
																</div>
															</div>
															<table class="dialog_table_class">
																<tr>
																	<td>
																		查询主题：
																		<input id="mms_title_search" />
																		<img src="/skin/Default/images/form/16/search.gif"
																			alt="点击查询彩信主题" onclick="searchMmsTitle()"></img>
																	</td>
																</tr>
																<tr>
																	<td>
																		<span><font color="red">注：</font><font
																			color="blue">由于数据过多，请输入查询关键字过滤数据!</font>
																	</td>
																</tr>
																<tr>
																	<td style="height: 20px"></td>
																</tr>
																<tr>
																	<td>
																		可选主题：
																		<select id="mms_title_select" style="">

																		</select>
																	</td>
																</tr>
																<tr>
																	<td style="height: 40px"></td>
																</tr>
															</table>
															<div class="dialog_button_class">
																<button class="dialog_button" onclick="confirmDialog()">
																	确定
																</button>
																<button class="dialog_button" onclick="closeDialog()">
																	关闭
																</button>
															</div>
														</div>
														<div class="dialog_msg_class" id="dialog_div_id">
															<img src="/skin/Default/images/icon/16/loading.gif"></img>
															操作进行中,请稍后...
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
												SP彩信主题区域统计图形
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
							<td height="350px" width="100%"
								style="padding-left: 5px;padding-top:15px; background: #ffffff">
								<div id="imgarea1" style="width: 100%; height: 100%;margin-left:-13px;">
									<iframe name="frm"
										src="/flash/MantoEye_Main.html?mapflag=bsc&dataflag=mms&dataname=彩信发送量"
										scrolling="no" id="frm"
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
								<table cellspacing="0" cellpadding="0" border="0" width="100%"
									class="menubar">
									<tr valign="top">
										<td width="4px" height="24px">
											<div class="lefttitle"></div>
										</td>
										<td width="100%" height="24px">
											<div class="middletitle">
												SP彩信主题区域统计列表
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
var hasinit = false;//第一次初始化图形数据标识
var datachange = false;//第N次查询初始化数据标识
var dataType_search = 1 ; //页面显示数据类型
var timeLevel_search ='2';//查询时间粒度
var time_search =date.getYear()+'-'+((date.getMonth()+1)>10?(date.getMonth()+1):("0"+(date.getMonth()+1)))+'-'+(date.getDate()>10?date.getDate():("0"+date.getDate()));//查询开始时间
var spaceLevel_search = '1';//区域维度(BSC:1 SGSN:2 街道办:3 营销片区:4 分公司:5	)
var mms_title_search='';

var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :[
		{name : 'fullDate'        },
		{name : 'port'        },
		{name : 'title'        },
		{name : 'businessName'    },
		{name : 'totalSendFlush'      },
		{name : 'flushRate'    },
		{name : 'allFlushRate' }
	]
};

var colsConfig = [
		{ id : 'fullDate'      , header : "时间" ,sortable:false    },
		{ id : 'port'      , header : "端口"    ,sortable:false },
		{ id : 'title'    , header : "主题"  ,sortable:false },
		{ id : 'businessName'     , header : "区域",sortable:false    },
		{ id : 'totalSendFlush'  , header : "发送量(条)"  ,headAlign:'right' ,align:'right',exportnumber:true },
		{ id : 'flushRate' , header : "SP彩信占比(%)"   ,headAlign:'right' ,align:'right' ,renderer:renderFormatDataInit4   },
		{ id : 'allFlushRate'  , header : "全网占比(%)"   ,headAlign:'right' ,align:'right',renderer:renderFormatDataInit4 }
];

var gridConfig={
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/spMmsTitleArea_query.do',
	exportURL :'/spMmsTitleArea_export.do?1=1' ,
	dataset : dsConfig ,
	columns : colsConfig ,
	width:780,
	height:500,
	resizable : false,
	showGridMenu : false,
	allowCustomSkin : false,
	allowFreeze : false,
	allowGroup : false,
	allowHide : false,
	container : 'data_container', 
	beforeLoad:checkLogon,
	toolbarContent : 'nav | goto | pagesize | state' ,
	pageSize : getDispalyPageSize(2,2) ,
	remoteSort : true ,
	pageSizeList : [10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
	defaultRecord :  { no : 111 , name :'', age : '', gender : 'U' , english : 50 , math :50 },
	onComplete:loadComplate,
	onCellClick : showMapArea
};
//点击区域时，在地图上加亮显示该区域
function showMapArea(value, record , cell,row, colNO, rowNO,column,event){
	if(value==record.businessName){
		//alert(record.businessName);
		document.frm.MantoEye_Main.areaActive(value);
	}else{
		document.frm.MantoEye_Main.areaActive("-");
	}
}
var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
} );

var firstInit=true;

function loadComplate(){
	//grid列表初始化信息
	var obj = new Object ();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.hideColumn = 0;//隐藏的列数目
	obj.isCheckbox = false;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度
	//判断是否查询到数据
	judgeData(datagrid);
	//初始化grid
	initGridInfo(obj);
	
		if(firstInit==true){
		var pageSize=getDispalyPageSize(2,2);
		var index=0;
		$(".gt-pagesize-select option").each(function(i){
			if(this.text==pageSize){
				index=i;
			}
		})
		firstInit=false;
		$(".gt-pagesize-select").get(0).selectedIndex=index;
	}
	
	//图形数据
	if(!hasinit){
		getAjaxChartXmlData();
	}
	
}


/*****************地图定义******************/
var chartFlag = 'bsc';//地图显示标识
function flashAreaDbClick(areaName){
	//alert('双击地图');
}

//加载图形数据入口
var hasgetdata = false;
function loadDataString(){
	var xml =document.getElementById("chartxmldata").value;	
	if(xml!=null&&xml!=''){
		hasgetdata = true;
	}
	return xml;
}
//显示的区域维度
function showAreaType(){
	switch($("#spaceLevel_search option:selected").val())
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


//获取服务器端图形数据
function getAjaxChartXmlData(){
	$.ajax({
		type : "post",
		url : "spMmsTitleArea_getAjaxChartXmlData.do",
		data : {
			dataType_search:dataType_search,
			timeLevel_search:timeLevel_search,
			time_search:time_search,
			spaceLevel_search:spaceLevel_search,
			mms_title_search:$('#mms_title').attr('value')
		},
		success : function(xml) {
			document.getElementById("chartxmldata").value=xml;
			if(!hasinit && datachange){
				document.frm.MantoEye_Main.changeData(chartFlag);
			}else if(!hasgetdata){
				document.frm.MantoEye_Main.changeData(chartFlag);
			}
			hasinit = true;
		},
		error : function() {
			alert('服务器出错,请联系管理员!');
		}
	});
}

/**************************地图定义结束***********/

//grid查询
function query() {
	//重置分页数据
	datagrid.setTotalRowNum(-1);

	//查询条件
	timeLevel_search = $("#timeLevel_search option:selected").val();
	time_search = $('#time_search').attr("value");
	spaceLevel_search = $('#spaceLevel_search').attr("value");
	mms_title_search = $('#mms_title').attr('value');
	if(time_search=='' ){
		alert('请选择时间!');
		return ;
	}
	/*if(mms_title_search==''){
		alert('请确定彩信主题!');
		return ;
	}*/
	
	hasinit = false;
	datachange = true;
	var param={
		hasinit:hasinit,
		dataType_search:dataType_search,
		timeLevel_search:timeLevel_search,
		time_search:time_search,
		spaceLevel_search:spaceLevel_search,
		mms_title_search:mms_title_search
	};
	GT.$grid('datagrid').query( param );
}

//设置页面显示的数据类型
function showDataType(type){
	dataType_search = type ;
}

//重置查询条件
function reset(){
	$('#time_search').attr("value","");
	$("#timeLevel_search").get(0).selectedIndex=0;
	$('#dataType_search_td_id input:first').attr("checked","true");//设置radio默认值
	$("#spaceLevel_search").get(0).selectedIndex=0;//区域维度
	$('#mms_title').attr('value','');
}

//初始化查询时间
$(document).ready(function(){
	$('#time_search').attr('value',time_search);
});

//时间选择事件
function selectTime(){
	switch($("#timeLevel_search option:selected").val())
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
				mms_title_search:mms_title_search
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName='SP彩信区域主题数据';
			fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
			fileObj.gridObj = datagrid;//当前grid对象
			fileObj.params = params;
			exportFile(fileObj);
          }
        }]
      });
	  toolbar.render();
    });
    


String.prototype.trim = function () {
	return this.replace(/(^\s*)|(\s*$)/g, "");
};

//彩信主题事件
function searchMmsTitle(){
	if($('#time_search').attr("value")=='' ){
		alert('请选择时间!');
		return ;
	}
	var like_mms_title_search=$('#mms_title_search').attr('value');
	like_mms_title_search = like_mms_title_search.trim();
	/*if(like_mms_title_search==''){
		alert('请输入查询关键字!');
		return ;
	}*/
	
	var $msgObj = $('#dialog_div_id');
	$msgObj.css({'display':'block','margin-left':'50px','margin-top':'40px'});
	$('#mms_title_select option').remove();
	$.ajax({
		type:"post", 
		url:"spMmsTitleArea_listMmsTitle.do", 
		data:{
			dataType_search:dataType_search, 
			timeLevel_search:$("#timeLevel_search option:selected").val(), 
			time_search:time_search = $("#time_search").attr("value"), 
			spaceLevel_search:$("#spaceLevel_search option:selected").val(),
			like_mms_title_search:like_mms_title_search
		}, 
		success:function (message) {
			if (message != null && message != "") {
				var ts = message.split("&&&&&");
				if (ts.length > 0) {
					for (var i = 0; i < ts.length; i++) {
						$("#mms_title_select").append("<option value='" + ts[i] + "'>" + ts[i] + "</option>");
					}
				}
			}else{
				$msgObj.css({'display':'none'});
				alert('没有查询到彩信主题!');
			}
			$msgObj.css({'display':'none'});
		}, error:function () {
			$msgObj.css({'display':'none'});
			alert("服务器出错,请联系管理员!");
		}
	});
}

function showDialog(){
	$('#mms_title_dialog').css({'display':'block'});
	
}
    
function confirmDialog(){
	$('#mms_title').attr('value',$("#mms_title_select option:selected").val());
	$('#mms_title_dialog').css({'display':'none'});
}

function closeDialog(){
	$('#mms_title_dialog').css({'display':'none'});
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



