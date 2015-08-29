<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>业务排名</title>
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


		<script src="/js/nav.js">
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
												cellpadding="0px" boder="0">
												<tr valign="middle">
													<td class="condition_name" style="display: none">
														时间粒度：
													</td>
													<td class="condition_value" style="display: none">
														<select name="timeLevel_search" id="timeLevel_search"
															onchange="changeTimeLevel()" style="height: 21px">
															<option value="1" selected>
																小时
															</option>
															<option value="2" >
																天
															</option>
														</select>
													</td>
													<td class="condition_name">
														时间跨度：
													</td>
													<td class="condition_value" id="time_space_start">
														<input type="text" class="Wdate"
															style="display: block; height: 17px;"
															onclick="selectStartTime()" id="startTime_search"
															name="startTime_search" />
													</td>
													<td style="width: 5px;">
														&nbsp;到&nbsp;
													</td>
													<td class="condition_value" id="time_space_end">
														<input type="text" class="Wdate"
															style="display: block; height: 17px;"
															onclick="selectEndTime()" id="endTime_search"
															name="endTime_search" />
													</td>
												<td class="condition_name">
														保障区域：
													</td>
													<td class="condition_value">
														<input type="text" name="common_search" id="common_search"
															style="height: 17px; width: 115px" />
														<input type="hidden" name="dataType" id="dataType"
															value="1" />
													</td>
												
												<td width="200px">
														<div id="mainbtn" style="width:300px">
															<ul>
																<li>
																	<a href="javascript:query();" 　title="查询"><span>查询</span>
																	</a>
																</li>
																<li>
																	<a href="javascript:parent.openTab1('/mantoeye/sports/area_map_cell/area_map_cell_index.jsp?permId=AREA_MAP_CELL','AREA_MAP_CELL','保障区域');" 
																		style="width:80px;"  title="更新保障区域"><span>保障区域</span>
																	</a>
																</li>
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
												业务排名前五位
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
var date = new Date();
var timeLevel_search ='1';//查询时间粒度
var dataType = 1;
date = date.valueOf();
date = date - 24 * 60 * 60 * 1000;
date = new Date(date);
var yesterday = date.getYear()
		+ '-'
		+ ''
		+ ((date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : ("0" + (date
				.getMonth() + 1))) + '-' + ''
		+ (date.getDate() > 9 ? date.getDate() : ("0" + date.getDate())); //查询开始时间
var endTime_search = yesterday + " 23:00";//查询结束时间

var startTime_search = yesterday + " 00:00"//查询开始时间
var common_search='';

var w="1052px";
var h="700px";
if(width==1024){
	w="810px";
	h="600px";
}
	
var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :[
		{name : 'statdate'        },
		{name : 'context1'        },
		{name : 'context2'        },
		{name : 'detail' }
	]
};




	
var colsConfig = [
		{ id : 'statdate'      , header : "时间"    ,exportnumber:true},
		{ id : 'context1'      , header : "保障区域",exportnumber:true,sortable: false},
		{ id : 'context2'    , header : "业务排名前5名" ,exportnumber:false,sortable: false},
		{ id : 'detail'   , header : "操作" , sortable:false, width:50,exportable:false,
			renderer:renderInit
		}
];

var gridConfig={
	id : "datagrid",
	loadURL :'/topBusiness_query.do?intType=11',
	exportURL :'/topBusiness_export.do?intType=11' ,
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
	pageSize :  getLongPageSize(),
	remoteSort : true ,
	remotePaging:true,
	pageSizeList :   [10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
	onComplete:loadComplate
};
function getLongPageSize(){
	var size = getDispalyPageSize(1,1);
	if(size<25){
		size = 25;
	}
	return size;
}
var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
});

function renderInit(value ,record,columnObj,grid,colNo,rowNo){
	var context1 = record.context1.toString();	//当前记录保障区域
	var date = record.statdate.toString();	//当前记录时间点
	return '<img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/business.png" alt="查看终端前十数据" onclick="openTop5Terminal(\''+date+'\',\''+context1+'\');" />';
}

function openTop5Terminal(date,context1){
	var obj = new Object();
	obj.terminal_time_search = date ;
	obj.terminal_protected_area = context1;
	var value = window.showModalDialog("/mantoeye/sports/topbusiness_terminal.jsp",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
}

var firstInit=true;
//gird回调函数
function loadComplate(){
	var obj = new Object ();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.hideColumn = 0 ;//隐藏的列数目
	obj.isCheckbox = false;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度
	
	//判断是否查询到数据
	judgeData(datagrid);
	
	
	//初始化grid
	//initGridInfo(obj);
	initGridInfo2(datagrid);
/*	*/
	if(firstInit==true){
		//var pageSize=getDispalyPageSize(1,1);
		var pageSize=25;
		var index=0;
		$(".gt-pagesize-select option").each(function(i){
			if(this.text==pageSize){
				index=i;
			}
		})
		firstInit=false;
		$(".gt-pagesize-select").get(0).selectedIndex=index;
	}
	
}

function initGridInfo2(datagrid) {
		var width = window.screen.availWidth-224; 
		width = width - 10;
		//var cs = datagrid.columns.length;
		var cs = 2;
		
		var totalRecords = datagrid.getAllRows().length;
		var dWidth = (totalRecords) * 22 + 70;
		var parentHeight = parent.document.getElementById("container_content").offsetHeight;//整个编辑区的高度
		var yy = window.screen.availHeight;
		var yyy = window.screenTop;
		var otherHeight = 0;
		var parentHeight = yy - yyy;
		var $toolbarObj = $("#toolbar");//toolbar对象
		var tHeight = $toolbarObj.offset().top + 25;//toolbar相对位置
		var IEHeight = 32;
		if (otherHeight != undefined) {
			IEHeight = IEHeight - otherHeight;
		}
		var totalRecords = datagrid.getAllRows().length;
		var dWidth = (totalRecords) * 22 + 55;
		if (checkIE() == "IE8") {
			dWidth = (totalRecords) * 22 + 70;
		}
		if (width == 1024) {
			var width_1024 = parentHeight - tHeight - IEHeight - 3;
			datagrid.setSize(800, dWidth > width_1024 ? dWidth : width_1024);//设置列表的宽高
			$toolbarObj.css("width", "800px");//设置列表toolbar的宽高
			realWidth = 800;
		} else {
			var width_1280 = parentHeight - tHeight - IEHeight;//满屏高度
			datagrid.setSize(1056, dWidth > width_1280 ? dWidth : width_1280);
			$toolbarObj.css("width", "1056px");
			realWidth = 1056;
		}

		width = width - 380;
		cs = 1;
		var w = parseInt(width / cs);
		for (var j in datagrid.columnMap) {
			//nmNoteId,vcTitle,dtDate,vcContent
			 if( 'statdate' == j){
				 datagrid.getColumn(j).setWidth(120);
			 }else if( 'context1' == j){
				 datagrid.getColumn(j).setWidth(220);
			 }else if( 'context2' == j){
				 datagrid.getColumn(j).setWidth(1600);
			 }else if( 'detail' == j){
				 datagrid.getColumn(j).setWidth(60);
			 }else{
				 datagrid.getColumn(j).setWidth(w);
			 }	
		}
	}

//grid查询
function query() {
	//重置分页数据
	var pageinfo = datagrid.getPageInfo();
	pageinfo.pageNum = 1;
	datagrid.setPageInfo(pageinfo);
	//查询条件
	timeLevel_search = $("#timeLevel_search option:selected").val();
	startTime_search = $('#startTime_search').attr("value");
	endTime_search = $('#endTime_search').attr("value");	//设置图形标志
	common_search = $('#common_search').attr("value");
	dataType = $('#dataType').attr("value");
	if(startTime_search=='' || endTime_search=='' || startTime_search==null || endTime_search==null){
		alert('请选择时间跨度!');
		return ;
	}
	//传递给后台参数
	var param={
		common_search:common_search,
		timeLevel_search:timeLevel_search,//时间粒度  1:小时 2:天 3:周 4:月
		startTime_search:startTime_search,//开始时间
		endTime_search:endTime_search,//结束时间
		dataType:dataType
	};
	GT.$grid('datagrid').query( param );
}

//设置页面显示的数据类型
function showDataType(type){
	dataType_search = type ;
}
//时间改变事件
function changeTimeLevel(){
	$('#startTime_search').attr("value","");
	$('#endTime_search').attr("value","");
}
//初始化查询时间
$(document).ready(function() {
	$('#startTime_search').attr('value', startTime_search);
	$('#endTime_search').attr('value', endTime_search);
});

//时间选择事件
function selectStartTime(){
	var ts = $("#timeLevel_search option:selected").val();
	switch(ts)
	   {
	   case '1':
	   	 var a = new WdatePicker({maxDate:'#F{$dp.$D(\'endTime_search\')}',dateFmt:'yyyy-MM-dd HH:00'});
	     break;
	   case '2':
	   	 new WdatePicker({maxDate:'#F{$dp.$D(\'endTime_search\')}',dateFmt:'yyyy-MM-dd'});
	     break;
	   case '3':
	     new WdatePicker({maxDate:'#F{$dp.$D(\'endTime_search\')}',dateFmt:'yyyy-MM-dd'});
	     break;
	   case '4':
	   	  new WdatePicker({maxDate:'#F{$dp.$D(\'endTime_search\')}',dateFmt:'yyyy-MM'});
	     break;
	 }
}
function selectEndTime(){
	var ts1 = $("#timeLevel_search option:selected").val();
	switch(ts1)
	   {
	   case '1':
	   	 new WdatePicker({minDate:'#F{$dp.$D(\'startTime_search\')}',dateFmt:'yyyy-MM-dd HH:00'});
	     break;
	   case '2':
	   	 new WdatePicker({minDate:'#F{$dp.$D(\'startTime_search\')}',dateFmt:'yyyy-MM-dd'});
	     break;
	   case '3':
	   	 //new WdatePicker({isShowWeek:true,onpicked:function(){$dp.$D('time_search').value=$dp.cal.getP('W','第'+'W'+'周');}})
	     new WdatePicker({minDate:'#F{$dp.$D(\'startTime_search\')}',dateFmt:'yyyy-MM-dd'});
	     break;
	   case '4':
	   	 new WdatePicker({minDate:'#F{$dp.$D(\'startTime_search\')}',dateFmt:'yyyy-MM'});
	     break;
	 }
}
//重置查询条件
function reset(){
	$('#common_search').attr("common_search","");//设置radio默认值
	$("#timeLevel_search").get(0).selectedIndex=1;
	$('#startTime_search').attr("value","");
	$('#endTime_search').attr("value","");
}


//初始化grid工具栏
$(document).ready(function(){
	 //按钮权限设置	  
	  var permflag = '<%=btnperm%>';
	  
	   /*第一个参数为下面按钮数据库对应按钮标识的部分或全组成的集合
	  	*（如果是部分，该部分必须在下面的所有按钮中能唯一标识）
	  	*顺序和数量应该与下面的按钮一致
	  	*/
	  var bflags = initBtnPerm('BTN_IMPORT',permflag);
	
      var toolbar = new Toolbar({
        renderTo : 'toolbar',
		//border: 'top',
        items : [{
          type : 'button',
          text : '切换信息',
          useable : 'T',
          handler : function(){
           		if(dataType==1){
           			$('#dataType').attr("value",2);
           		}else{
           			$('#dataType').attr("value",1);
           		}	
           		query();
          }
        },{
          type : 'button',
          text : '导出',
          bodyStyle : 'xls',
          useable : 'T',
          handler : function(){
           //参数
			var params={		
				common_search:common_search,
				timeLevel_search:timeLevel_search,//时间粒度  1:小时 2:天 3:周 4:月
				startTime_search:startTime_search,//开始时间
				endTime_search:endTime_search//结束时间
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName='业务排名';//('+startTime_search+'至'+endTime_search+')';//文件名称
			fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
			fileObj.gridObj = datagrid;//当前grid对象
			fileObj.params = params;
			exportFile(fileObj);
          }
        }]
      });
	  toolbar.render();
});



$(document).ready(function(){
	window.onerror = killErrors;
})
function killErrors() {
	return true;
}


</script>


