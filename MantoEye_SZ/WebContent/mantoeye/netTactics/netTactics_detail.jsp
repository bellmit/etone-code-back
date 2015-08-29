<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow-x: hidden">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>网络内容策略输出结果</title>
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
													
													<td class="condition_name">
														时间跨度：
													</td>
													<td class="condition_value" id="time_space_start">
														<input type="text" class="Wdate"
															style="display: block; height: 17px;"
															onclick="selectStartTime()" id="startTime_search"
															name="startTime_search" />
															
															<input type="hidden" class="Wdate"
															style="display: block; height: 17px;"
															id="startTime"
															name="startTime" />
															<input type="hidden" class="Wdate"
															style="display: block; height: 17px;"
															id="endTime"
															name="endTime" />
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
													<td width="200px">
														<div id="mainbtn" style="margin-left:-6px">
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
												网络内容策略输出结果集数据表
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
var obj = window.dialogArguments ;
var startTime = obj.startTime;
var endTime1 = obj.endTime;	
var endTime = obj.startTime1;	
var nmId = obj.id;
var height1 = obj.height;
var width1 = obj.width;	
$('#startTime_search').attr('value',startTime);
$('#endTime_search').attr('value',endTime);
$('#startTime').attr('value',startTime);
$('#endTime').attr('value',endTime1);	
	
var dsConfig= {
	uniqueField : 'no' ,
	fields :[
		{name : 'fullDate'     },
		{name : 'nmMsisdn'      },
		{name : 'vcSoName'       },
		{name : 'vcParentGroupName'       }
		]
};
var colsConfig = [
		{ id : 'fullDate'      , header : "用户时间",sortable:true  ,exportnumber:true   },
		{ id : 'nmMsisdn'      , header : "号码"   ,sortable:true ,headAlign:'right' ,align:'right' ,exportnumber:true  },
		{ id : 'vcParentGroupName'    , header : "一级分类"   ,sortable:true ,exportnumber:true},
		{ id : 'vcSoName'    , header : "二级分类"   ,sortable:true ,exportnumber:true}
];

var gridConfig={
	id : "datagrid",
	loadURL :'/modules/netTactics/net-tactics!queryKey.do?id='+nmId+'&startTime='+startTime+'&endTime='+endTime,
	exportURL :'/modules/netTactics/net-tactics!exportKey.do?1=1',
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
	pageSize : 20 ,
	remoteSort : true ,
	pageSizeList : [10,20,50,100],
	onComplete:loadComplate
};

var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
} );


function comptime(startTime,endTime) {
    var beginTimes = startTime.substring(0, 10).split('-');
    var endTimes = endTime.substring(0, 10).split('-');
    beginTime = beginTimes[1] + '-' + beginTimes[2] + '-' + beginTimes[0] + ' ' + startTime.substring(10, 19);
    endTime = endTimes[1] + '-' + endTimes[2] + '-' + endTimes[0] + ' ' + endTime.substring(10, 19);
    var a = (Date.parse(endTime) - Date.parse(beginTime)) / 3600 / 1000;
    return a;
}

//grid查询
function query() {
	//重置分页数据
	var pageinfo = datagrid.getPageInfo();
	pageinfo.pageNum = 1;
	datagrid.setPageInfo(pageinfo);
	//查询条件

	startTime = $('#startTime_search').attr("value");
	endTime = $('#endTime_search').attr("value");	//设置图形标志
	if(comptime(startTime,endTime)<0){
		alert('时间跨度选择不正确！');
		return ;
	}
	if(startTime=='' || endTime==''){
		alert('请选择时间跨度!');
		return ;
	}

	//传递给后台参数
	datagrid.loadURL = '/modules/netTactics/net-tactics!queryKey.do?id='+nmId+'&startTime='+startTime+'&endTime='+endTime;
	GT.$grid('datagrid').query(  );
}

//根据分辨率初始化grid列宽
function initColumnWidthWithPop1(grid, width, hideColumn, checkWidth) {//checkWidth  表示checkBox宽度,表示存在checkBox操作
	var cs = grid.columns.length - hideColumn;//如有隐藏列,则减去隐藏列
	var $toolbarObj = $("#toolbar");//toolbar对象
	if (typeof (checkWidth) != "undefined") {
		width = width - 40;//为checkbox预留空间
		cs = cs - 1;
	}
	var totalRecords = grid.getAllRows().length;
	var dWidth = (totalRecords) * 22 + 70;
	if (checkIE() == "IE6") {
		if (window.screen.availWidth == 1024) {
			//alert(width);
			datagrid.setSize(width, (parseInt(height1)-120)+"px");
		} else {
			datagrid.setSize(width, (parseInt(height1)-120)+"px");
		}
		$toolbarObj.css("width", width + "px");
		width = width - 5;
	} else {
		if (checkIE() == "IE7") {
			width = width + 5;
			datagrid.setSize(width + 5, (parseInt(height1)-120)+"px");
			$toolbarObj.css("width", (width + 5) + "px");
			width = width - 11;
		} else {
			width = width + 5;
			datagrid.setSize(width, (parseInt(height1)-120)+"px");
			$toolbarObj.css("width", width + "px");
			width = width - 15;
		}
	}
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

var exporting = true;

function loadComplate(){
	exporting = false;
	//判断是否查询到数据
	judgeData(datagrid,1);
	
	var totalRecords = datagrid.getAllRows().length;//获取加载到的数据记录数目
	var width=window.screen.availWidth;
	//根据分辨率初始化grid列宽
	if(!columninit){
		if(width==1024){
			initColumnWidthWithPop1(datagrid,792,0);
			
		}else{
			initColumnWidthWithPop1(datagrid,973,0);
			
		}
	}
}


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
			if(exporting){
				return;
			}
			exporting=true;
			alert("正在下载数据，由于数据量比较大，请稍后……");
			var params={
				id:nmId,
				startTime:startTime,
				endTime:endTime
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName='网络访问内容策略数据';
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



//时间选择事件
function selectStartTime(){
	new WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'#F{$dp.$D(\'endTime\')}',dateFmt:'yyyy-MM-dd HH:00:00'});
}
function selectEndTime(){
	new WdatePicker({minDate:'#F{$dp.$D(\'startTime_search\')}',maxDate:'#F{$dp.$D(\'endTime\')}',dateFmt:'yyyy-MM-dd HH:00:00'});
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



