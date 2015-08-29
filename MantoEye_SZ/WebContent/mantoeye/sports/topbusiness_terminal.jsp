<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>终端排名</title>
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
												终端排名前十位
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
var terminal_time_search = obj.terminal_time_search;	//连接进来的时间点
var terminal_protected_area = obj.terminal_protected_area;	//当前保障区域内容
	
var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :[
		{name : 'statdate'        },
		{name : 'context1'        },
		{name : 'context2'        }
	]
};




	
var colsConfig = [
		{ id : 'statdate'      , header : "时间"    ,exportnumber:true},
		{ id : 'context1'      , header : "保障区域",exportnumber:true,sortable: false},
		{ id : 'context2'    , header : "终端排名前十名" ,exportnumber:false,sortable: false}
];

var gridConfig={
	id : "datagrid",
	loadURL :'/topBusiness_queryTerminalByBusiness.do?intType=14&terminalTimeSearch=' + terminal_time_search + '&terminalProtectedArea=' + terminal_protected_area,
	exportURL :'/topBusiness_exportTerminalByBusiness.do?intType=14&terminalTimeSearch=' + terminal_time_search + '&terminalProtectedArea=' + terminal_protected_area ,
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
	initGridInfo2(datagrid);
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
			 }else{
				 datagrid.getColumn(j).setWidth(w);
			 }	
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
          handler : function(){
           //参数
			var params={		
				terminal_time_search:terminal_time_search,
				terminal_protected_area:terminal_protected_area
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName='终端排名';
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


