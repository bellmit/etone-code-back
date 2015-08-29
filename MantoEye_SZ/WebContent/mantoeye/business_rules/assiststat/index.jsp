<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>检验规则异常统计</title>
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
														业务：
													</td>
													<td class="condition_value">
															<input type="text"
															name="d_busiName" value=""
															id="d_busiName" />
													</td>
													<td class="condition_name">
														时间跨度：
													</td>
													<td class="condition_value">
														<input type="text" class="Wdate" style="height: 17px;"
															onclick="new WdatePicker({dateFmt:'yyyy-MM-dd'})"
															name="searchDateStart" value="${startDate}"
															id="d_searchDateStart" />
													</td>
													<td class="condition_name" style="width:20px">
														到
													</td>
													<td class="condition_value">
														<input type="text" 
															class="Wdate" style="height: 17px;"
															onclick="new WdatePicker({dateFmt:'yyyy-MM-dd'})"
															name="searchDateEnd" value="${endDate}"
															id="d_searchDateEnd" />	
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
												业务数据列表
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
<!-- --><table id="myHead" style="display:none">
<tr>
	<td colspan="1" >翻译规则</td>
	<!-- <td colspan="4">检验/自发现规则</td> -->
	<td rowspan="2" columnId='count'>不匹配次数</td>
	<td rowspan="2" columnId='vcBussinessName'>所属业务</td>
</tr>
<tr>
	<td columnId='mainruleIp'>IP</td>
	<!-- <td columnId='assistruleUrl'>URL</td>
	<td columnId='assistrulePort'>Port</td>	
	<td columnId='assistruleApn'>APN</td>
	<td columnId='assistruleUserAgent'>UserAgent</td> -->
</tr>
</table>
							
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
var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :[
		{name : 'nmAssistRuleId'        },
		{ name : 'vcBussinessName'       },
		{ name : 'count'       },
		{ name : 'mainruleIp'       },
		{name : 'mainrulePort'      },
		{name : 'mainruleApn'    },
		{name : 'mainruleUserAgent'    },
		{name : 'mainruleUrl'    }
	/*	,{name : 'assistruleIp'       },
		{name : 'assistrulePort'      },
		{name : 'assistruleApn'    },
		{name : 'assistruleUserAgent'    },
		{name : 'assistruleUrl'    }*/
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
		
		{ id : 'mainruleIp'      , header : "翻译规则(IP)" ,sortable:false },
	//	{ id : 'assistruleUrl'     , header : "URL" },
	//	{ id : 'assistrulePort'    , header : "Port"  },
	//	{ id : 'assistruleApn'     , header : "APN"   },
	//	{ id : 'assistruleUserAgent'     , header : "UserAgent"  },
		{ id : 'vcBussinessName'      , header : "所属业务"  ,sortable:false},
		{ id : 'count'    , header : "不匹配次数"  ,exportnumber:true }
		
		
	//	{ id : 'detail'   , header : "操作" , sortable:false, 
	//		renderer:renderInit
	//	}
];

var gridConfig={
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/assistRule_list.do?1=1',
	exportURL :'/assistRule_export.do?1=1' ,
	//saveURL : '/flexiGridTest_list1.do?1=1' ,
	dataset : dsConfig ,
	columns : colsConfig ,
//	width:"100%",
//	height:420,
	resizable : false,
	container : 'data_container',
	beforeLoad:checkLogon,
	toolbarPosition : 'bottom', 
	toolbarContent : '| nav | goto | pagesize | state',
	beforeSave : function(reqParam){
	},
	pageSize :getDispalyPageSize(0,1)  ,
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
	obj.hideColumn = 0 ;//隐藏的列数目
	obj.isCheckbox = false;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度
	//判断是否查询到数据
	judgeData(datagrid);
	
	//初始化grid
	initGridInfo(obj);
	
	if(firstInit==true){
		var pageSize=getDispalyPageSize(0,1);
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
var date=new Date();
date=new Date(date-24*60*60*1000); 
var startDate = date.getYear()+'-'+''+((date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1)))+'-'+''+(date.getDate()>9?date.getDate():("0"+date.getDate()));
var endDate = date.getYear()+'-'+''+((date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1)))+'-'+''+(date.getDate()>9?date.getDate():("0"+date.getDate()));
var busiName;
//// 查询表单的查询函数 ////
function query() {
busiName=GT.U.getValue(GT.$('d_busiName'));
startDate=GT.U.getValue(GT.$('d_searchDateStart'));
endDate = GT.U.getValue(GT.$('d_searchDateEnd'));
if(startDate==''||endDate==''){
	alert('请选择时间!');
	return ;
}
	var param={		
		'busiName' : busiName,
		startDate : startDate,
		endDate : endDate
	};
	GT.$grid('datagrid').query( param );
}
function reset(){
	$('#d_busiName').attr('value','');
	$('#d_searchDateStart').attr('value','')
	$('#d_searchDateEnd').attr('value','')
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
	$('#d_searchDateStart').attr('value',startDate);
	$('#d_searchDateEnd').attr('value',endDate)
})

$(document).ready(function(){
	displayOrHiddenTd();
      var toolbar = new Toolbar({
        renderTo : 'toolbar',
		//border: 'top',
        items : [{
          type : 'button',
          text : '导出',
          bodyStyle : 'xls',
          useable : 'T',
          handler : exportToXls
        }]
      });
	  toolbar.render();
    });
function exportToXls(){
 	//参数
	var params={		
		'busiName' : busiName,
		startDate : startDate,
		endDate : endDate
	};
	if(startDate==''||endDate==''){
	alert('请选择时间!');
	return;
	}
			//导出
	var fileObj = new Object();
	fileObj.fileName='检验规则异常数据列表';//('+startTime_search+'至'+endTime_search+')';//文件名称
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



