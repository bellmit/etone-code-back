<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>流量TOP100小区分布</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" type="text/css"
			href="/tools/gt-grid/gt_grid.css" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/maincontent.css" />
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
			type="text/css"></link>
		<script type="text/javascript" src="/js/common.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
			<script type="text/javascript" src="/js/common_grid.js"></script>
			<!-- 列表工具栏 -->
		<script type="text/javascript"
			src="/js/jquery/extend.tab/js/Toolbar.js"></script>
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
													<td style="width: 72px" style="display:none">
														<input type="radio" name="d_raittype" id="d_raittype"
															value="1" checked style="width: 20px; border: 0px;" />
														GPRS
													</td>
													<td style="width: 72px" style="display:none">
														<input type="radio" name="d_raittype" value="2"
															id="d_raittype1" style="width: 20px; border: 0px;" />
														TD
													</td>
													<td class="condition_name">
														时间粒度：
													</td>
													<td>
														<div class="select">
															<div>
																<select name="d_timeLevel" id="d_timeLevel"
																 style="height: 21px"  onchange="selChange();"> 
																	<option value="1"
																		<c:if test="${fn:contains(timeLevel,'1') }">selected</c:if>>
																		小时
																	</option>
																	<option value="2" selected
																		<c:if test="${fn:contains(timeLevel,'2') }">selected</c:if>>
																		天
																	</option>

																<!-- 	<option value="3"
																		<c:if test="${fn:contains(timeLevel,'3') }">selected</c:if>>
																		周（周日~周六）
																	</option>
																	<option value="4"
																		<c:if test="${fn:contains(timeLevel,'4') }">selected</c:if>>
																		月
																	</option> -->
																</select>
															</div>
														</div>
													</td>
													<td class="condition_name">
														时间：
													</td>
													<td>
														<input type="text" class="Wdate" style="height: 17px;"
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
								<table cellspacing="0" cellpadding="0" border="0" width="100%">
									<tr>
										<td width="4px" height="24px;">
											<img
												src="/skin/Default/images/MantoEye/maincontent/lefttitle.gif" />
										</td>
										<td width="100%"
											style="background: url(/skin/Default/images/MantoEye/maincontent/middletitle.gif) repeat-x; padding-left: 10px; font-size: 12px; font-weight: bold; color: #FFFFFF; vertical-align: middle;">
											流量TOP100小区分布数据
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
var time_search =date.getYear()+'-'+''+((date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1)))+'-'+''+(date.getDate()>9?date.getDate():("0"+date.getDate())); //查询开始时间

//重置查询条件
function reset(){
	$('#d_searchDateStart').attr("value","");
	$("#d_timeLevel").get(0).selectedIndex=1;
	$('#d_raittype').attr("checked","true");//设置radio默认值
}

//初始化查询时间
$(document).ready(function(){
	$('#d_searchDateStart').attr('value',time_search);
});

var dsConfig= {
	//data : data1 ,
	uniqueField : 'top' ,
	fields :[
		{name : 'top'        },
		{name : 'viewdata'        },
		{name : 'cellName'      },
		{name : 'street'      },
		{name : 'subsidiaryCompany'      },
		{name : 'bsc'      },
		{name : 'sgsn'      },
		{name : 'area'      },
		{name : 'saleArea'      },
		{name : 'cgi'      },
		{name : 'cellName'      },
		{name : 'formatFlushMb'  ,type:'float' },
		{name : 'nmAveFlushKB'   },
		{name : 'formatImsis'  ,type:'float' }
	]
};
var colsConfig = [
		{ id : 'top'      , header : "序号"     },
		{ id : 'viewdata'      , header : "时间"     },
		{ id : 'cgi'    , header : "CGI"    },	
		{ id : 'cellName'    , header : "小区名"    },	
		{ id : 'bsc' , header : "BSC"   },
		{ id : 'sgsn'      , header : "SGSN"     },
		{ id : 'subsidiaryCompany' , header : "分公司"   },
		{ id : 'saleArea' , header : "营销片区"   },
		{ id : 'street' , header : "街道"   },
		{ id : 'formatFlushMb' , header : "流量(MB)"  ,headAlign:'right' ,align:'right'  ,exportnumber:true  ,renderer:renderFormatDataInit2   },
		{ id : 'formatImsis' , header : "用户数"   ,headAlign:'right' ,align:'right'   ,exportnumber:true  },
		{ id : 'nmAveFlushKB'  , header : "平均流量(KB/人)",headAlign:'right' ,align:'right',exportnumber:true,renderer:renderFormatDataInit2 }
	
];

function renderFormatMyDate(value ,record,columnObj,grid,colNo,rowNo){
	var sp1 = value.split(' ');
	var sp11 = sp1[0].split('-');
	if(sp1.length > 1){
		var sp12 = sp1[1].split(':');
		return sp11[0] +  "-" + fillZero(sp11[1]) + "-" + fillZero(sp11[2]) + " " + sp12[0] + ":" + sp12[1];
	}
	return sp11[0] + "-" + fillZero(sp11[1]) + "-" + fillZero(sp11[2]);
}

function fillZero(inner){
	if(parseInt(inner)<10){
		return '0'+inner;
	}
	return inner;
}

var gridConfig={
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/topFlushDistribute_query.do?1=1',
	exportURL :'/topFlushDistribute_export.do?1=1' ,
	dataset : dsConfig ,
	columns : colsConfig ,
	width:780,
	height:500,
	resizable : false,
	showGridMenu : false,
	container : 'data_container', 
	beforeLoad:checkLogon,
	toolbarPosition : 'bottom',
	toolbarContent : 'state' ,
	pageSize : 100 ,
	remoteSort : false ,
	pageSizeList : [10,20,50,100],
	onComplete:loadComplate
};

var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
} );


function loadComplate(){
	//grid列表初始化信息
	var obj = new Object ();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.hideColumn = 0 ;//隐藏的列数目
	obj.isCheckbox = false;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度
	//obj.otherWidth = 30;//序号列
	//判断是否查询到数据
	judgeData(datagrid);
	
	//初始化grid(不设置行宽度)
	initGridInfoExColumn(obj);
	
	var width=window.screen.availWidth;
	var ttotalwidth;
	if (width == 1024) {
		ttotalwidth = 800;
	//重新设置宽度11
	datagrid.getColumn('top').setWidth(10);
	datagrid.getColumn('viewdata').setWidth(60);
	datagrid.getColumn('cgi').setWidth(100);
	datagrid.getColumn('cellName').setWidth(60);
	datagrid.getColumn('bsc').setWidth(60);
	datagrid.getColumn('sgsn').setWidth(80);
	datagrid.getColumn('subsidiaryCompany').setWidth(40);
	datagrid.getColumn('saleArea').setWidth(50);
	datagrid.getColumn('street').setWidth(60);
	datagrid.getColumn('formatImsis').setWidth(55);
	datagrid.getColumn('formatFlushMb').setWidth(65);
	datagrid.getColumn('nmAveFlushKB').setWidth(100);
	} else {	
		ttotalwidth = 1056;
		//重新设置宽度11
	datagrid.getColumn('top').setWidth(20);
	datagrid.getColumn('viewdata').setWidth(60);
	datagrid.getColumn('cgi').setWidth(140);
	datagrid.getColumn('cellName').setWidth(100);
	datagrid.getColumn('bsc').setWidth(90);
	datagrid.getColumn('sgsn').setWidth(120);
	datagrid.getColumn('subsidiaryCompany').setWidth(60);
	datagrid.getColumn('saleArea').setWidth(70);
	datagrid.getColumn('street').setWidth(80);
	datagrid.getColumn('formatImsis').setWidth(70);
	datagrid.getColumn('formatFlushMb').setWidth(70);
	datagrid.getColumn('nmAveFlushKB').setWidth(120);
	}	
	
}

//// 查询表单的查询函数 ////
function query() {
	var istd = GT.U.getValue(GT.$('d_raittype'));
	var istd1 = GT.U.getValue(GT.$('d_raittype1'));
	var ddate = GT.U.getValue(GT.$('d_searchDateStart'));
	if(ddate==''){
		alert('请选择时间!');
		return ;
	}
	var param={
		searchdate : GT.U.getValue(GT.$('d_searchDateStart')),
		stattype : GT.U.getValue(GT.$('d_timeLevel')),
		raittype : istd||istd1
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
 //参数
	var istd = GT.U.getValue(GT.$('d_raittype'));
	var istd1 = GT.U.getValue(GT.$('d_raittype1'));
	var params={
		searchdate : GT.U.getValue(GT.$('d_searchDateStart')),
		stattype : GT.U.getValue(GT.$('d_timeLevel')),
		raittype : istd||istd1
	};
			//导出
	var fileObj = new Object();
	fileObj.fileName='流量TOP100小区分布数据';//('+startTime_search+'至'+endTime_search+')';//文件名称
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


