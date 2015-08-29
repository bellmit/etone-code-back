<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>流量碰撞率统计</title>
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
												cellpadding="0px;boder="0">
												<tr valign="middle">
													
													<td class="condition_name">
														时间：
													</td>
													<td class="condition_value">
														<input type="text" class="Wdate" style="height: 17px;"
															onclick="new WdatePicker({dateFmt:'yyyy-MM-dd'})"
															name="searchDateStart" value="${startDate}"
															id="d_searchDateStart" />
													</td>
													<td class="condition_name" >
														所属业务：
													</td>
													<td class="condition_value" >
															<input type="text"
															name="d_busiName" value=""
															id="d_busiName" />
													</td>
													<td class="condition_name" >
														IP：
													</td>
													<td class="condition_value" >
															<input type="text"
															name="d_ip" value=""
															id="d_ip" />
													</td>
													<td id="spaceTd" width="*"></td>
													<td width="120px">
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
													<%-- 通过阀值过滤 未实现 --%>
													<tr style="display:none">
													<td class="condition_name" style="width:100px">
														流量环比阀值：
													</td>
													<td class="condition_value" >
														<input type="text" id="flush_rate" />
													</td>
													<td class="condition_name" style="width:120px">
														碰撞率环比阀值：
													</td>
													<td class="condition_value" >
														<input type="text" id="conflict_rate" />
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
												流量碰撞率统计
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
							<!-- 自定义列表头. -->
<!-- -->
							
								<div id="toolbar" style="height: 25px"></div>
								<div class="gt-panel"
									style="width: 100%; margin: 0px; margin-bottom: 2px;">
									<div id="data_container">
<table id="myHead" style="display:none">
<tr>

	<td rowspan="2" columnId='fulldate'>时间</td>
	<td rowspan="2" columnId='vcBussinessName'>所属业务</td>
	<td colspan="1" >翻译规则</td>
	<td colspan="3">流量</td>
	<td colspan="3">碰撞次数</td>
</tr>
<tr>
	<td columnId='ip'>IP</td>
	<td columnId='flush'>当天(MB)</td>
	<td columnId='lastflush'>上周同一天(MB)</td>	
	<td columnId='flushRate'>环比(%)</td>
	<td columnId='conflictCount'>当天</td>
	<td columnId='lastconflictCount'>上周同一天</td>
	<td columnId='conflictCountRate'>环比(%)</td>	
</tr>
</table>
									</div>
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

var searchDateStart=new Date();//时间
searchDateStart=searchDateStart.valueOf();
searchDateStart=searchDateStart-24*60*60*1000;
searchDateStart=new Date(searchDateStart); 
var mm=searchDateStart.getMonth()+1;
//searchDateStart=searchDateStart.getYear()+"-"+mm+"-"+searchDateStart.getDate();//格式不对，后台导出出错
searchDateStart=searchDateStart.getYear()+'-'+''+((searchDateStart.getMonth()+1)>9?(searchDateStart.getMonth()+1):("0"+(searchDateStart.getMonth()+1)))+'-'+''+(searchDateStart.getDate()>9?searchDateStart.getDate():("0"+searchDateStart.getDate())); //查询开始时间;
var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :[
		{ name : 'vcBussinessName'       },
		{ name : 'flush'       },
		{ name : 'lastflush'       },
		{ name : 'flushRate'       },
		{name : 'conflictCount'      },
		{name : 'lastconflictCount'    },
		{name : 'conflictCountRate'    },
		{name : 'ip'    },
		{name : 'fulldate'    }
	]
};
/*
function renderInit(value ,record,columnObj,grid,colNo,rowNo){
	var date = record.fullDate.toString();
	return '<img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/space.png" alt="查看空间分布" onclick="openSapceDistribute(\''+date+'\');" />';
}
*/
var colsConfig = [
		//{ id : 'nmAssistRuleId'      , header : "ID"   ,hidden:true,exportable:false },
		{ id : 'fulldate'     , header : "时间"  , sortable:false  },
		{ id : 'vcBussinessName'      , header : "所属业务"   , sortable:false  },
		{ id : 'ip'      , header : "翻译规则(IP)"   , sortable:false  },
		{ id : 'flush'    , header : "当天流量(MB)"  ,exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'lastflush'      , header : "上周同天流量(MB)"  ,exportnumber:true,renderer:renderFormatDataInit2 },
		{ id : 'flushRate'    , header : "流量环比(%)"   ,exportnumber:true ,renderer:renderFormatDataInit2 },
		{ id : 'conflictCount'     , header : "当天碰撞次数"  ,exportnumber:true},
		{ id : 'lastconflictCount'     , header : "上周同天碰撞次数" ,exportnumber:true},
		{ id : 'conflictCountRate'     , header : "碰撞次数环比(%)"    ,exportnumber:true,renderer:renderFormatDataInit2 }
	//	{ id : 'detail'   , header : "操作" , sortable:false, 
	//		renderer:renderInit
	//	}
];

var gridConfig={
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/businessMainFlush_list.do?1=1',
	exportURL :'/businessMainFlush_export.do?1=1' ,
	//saveURL : '/flexiGridTest_list1.do?1=1' ,
	dataset : dsConfig ,
	columns : colsConfig ,
	width:"100%",
	height:420,
	resizable : false,
	container : 'data_container',
	beforeLoad:checkLogon,
	toolbarPosition : 'bottom', 
	toolbarContent : '| nav | goto | pagesize | state',
	beforeSave : function(reqParam){
	},
	pageSize : getDispalyPageSize(0,2) ,
	remoteSort : true ,
	pageSizeList :  [10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
//	customHead : 'myHead',
	onComplete:loadComplate
};

var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
});

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

	var date=$("#d_searchDateStart").val();
	if(date==null||date==''){
    	$('#d_searchDateStart').val(searchDateStart);
    
    }
	
	if(firstInit==true){
		var pageSize=getDispalyPageSize(0,2);
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

//// 查询表单的查询函数 ////
function query() {
	var param={		
		//'filter_LIKE_ftbBusinessMainVetor.dtbBusinessSite.vcBussinessName' : GT.U.getValue(GT.$('d_busiName')),
		searchdate : GT.U.getValue(GT.$('d_searchDateStart')),
		sip : GT.U.getValue(GT.$('d_ip')),
		businame : GT.U.getValue(GT.$('d_busiName')),
		flushRate : GT.U.getValue(GT.$('flush_rate')),
		conflictCountRate : GT.U.getValue(GT.$('conflict_rate'))
	};
	GT.$grid('datagrid').query( param );
}
function reset(){
	$('#d_searchDateStart').attr('value','');
	$('#d_ip').attr('value','');
	$('#d_busiName').attr('value','');
	$('#flush_rate').attr('value','');
	$('#conflict_rate').attr('value','');
}
$(document).ready(function(){
	var $inputObj=$("input[class!='Wdate']");
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

$(document).ready(function(){
	//displayOrHiddenTd();
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
	var params={		
		//'filter_LIKE_ftbBusinessMainVetor.dtbBusinessSite.vcBussinessName' : GT.U.getValue(GT.$('d_busiName')),
		searchdate : GT.U.getValue(GT.$('d_searchDateStart')),
		flushRate : GT.U.getValue(GT.$('flush_rate')),
		sip : GT.U.getValue(GT.$('d_ip')),
		businame : GT.U.getValue(GT.$('d_busiName')),
		conflictCountRate : GT.U.getValue(GT.$('conflict_rate'))
	};
			//导出
	var fileObj = new Object();
	fileObj.fileName='流量碰撞率统计数据列表';//('+startTime_search+'至'+endTime_search+')';//文件名称
	fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
	fileObj.gridObj = datagrid;//当前grid对象
	fileObj.params = params;
	exportFile(fileObj);
 }
$(document).ready(function(){
	window.onerror = killErrors;
})
function killErrors() {
	return true;
} 
</script>



