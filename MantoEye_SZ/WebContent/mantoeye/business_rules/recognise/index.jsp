<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>全量业务自发现数据列表</title>
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
														发现时间：
													</td>
													<td class="condition_value" id="time_space_start">
														<input type="text" class="Wdate"
															style="display: block; height: 17px;"
															onclick="new WdatePicker({maxDate:'#F{$dp.$D(\'endTime_search\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
															id="startTime_search"
															name="startTime_search" />
													</td>
													<td style="width: 5px;">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;到&nbsp;
													</td>
													<td class="condition_value" id="time_space_end">
														<input type="text" class="Wdate"
															style="display: block; height: 17px;"
															onclick="new WdatePicker({minDate:'#F{$dp.$D(\'startTime_search\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
															id="endTime_search"
															name="endTime_search" />
													</td>
													<%--<td class="condition_name">
														URL：
													</td>
													<td class="condition_value">
															<input type="text"
															name="d_url" value=""
															id="d_url" />
													</td>--%>
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
																	<a href="javascript:myreset();" title="重置"><span>重置</span>
																	</a>
																</li>
																-->
															</ul>
														</div>
													</td>
													</tr><tr>
													<td class="condition_name">
														IP：
													</td>
													<td class="condition_value">
															<input type="text"
															name="d_ip" value=""
															id="d_ip" />
													</td>
													<td class="condition_name">
														APN：
													</td>
													<td class="condition_value">
															<input type="text"
															name="d_apn" value=""
															id="d_apn" />
													</td><td class="condition_name">
														URL：
													</td>
													<td class="condition_value">
															<input type="text"
															name="d_url" value=""
															id="d_url" />
													</td>
													<%-- <td class="condition_name">
														UserAgent：
													</td>
													<td class="condition_value">
															<input type="text"
															name="d_UserAgent" value=""
															id="d_UserAgent" />
													</td>--%>
													
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
												全量业务自发现数据
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
var date=new Date();
date=date.valueOf();
date=date-24*60*60*1000;
date=new Date(date); 
var yesterday=date.getYear()+'-'+''+((date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1)))+'-'+''+(date.getDate()>9?date.getDate():("0"+date.getDate())); //查询开始时间
var start_d = yesterday+" 00:00:00"
var end_d = yesterday+" 23:59:59"
var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :[
		{name : 'getNmAssistVetorRecogniseId'        },
		{ name : 'vcBussinessName'       },
		{ name : 'firstTime'       },
		{ name : 'lastTime'       },
		{name : 'ip'    },
		{name : 'port'       },
		{name : 'apn'      },
		{name : 'url'    },
		{name : 'userAgent'    }
	]
};
/*
function renderInit(value ,record,columnObj,grid,colNo,rowNo){
	var date = record.fullDate.toString();
	return '<img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/space.png" alt="查看空间分布" onclick="openSapceDistribute(\''+date+'\');" />';
}
*/
var colsConfig = [
		//{ id : 'getNmAssistVetorRecogniseId'      , header : "ID"   ,hidden:true,exportable:false },
		{ id : 'vcBussinessName'      , header : "业务名称"  ,sortable:false  },
		{ id : 'firstTime'    , header : "初次发现时间"   ,sortable:false  },
		{ id : 'lastTime'      , header : "最后更新时间"  ,sortable:false },
		{ id : 'url'     , header : "URL"   ,sortable:false},
		{ id : 'ip'      , header : "IP"   ,sortable:false  },
		{ id : 'port'    , header : "Port"  ,sortable:false },
		{ id : 'apn'     , header : "APN"   ,sortable:false },
		{ id : 'userAgent'     , header : "UserAgent"    ,sortable:false}
		
	//	{ id : 'detail'   , header : "操作" , sortable:false, 
	//		renderer:renderInit
	//	}
];

var gridConfig={
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/assistVetorRecognise_list.do?1=1',
	exportURL :'/assistVetorRecognise_export.do?1=1' ,
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
	pageSize : getDispalyPageSize(0,2)  ,
	remoteSort : true ,
	pageSizeList :[10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
	//customHead : 'myHead',
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
	if($('#startTime_search').attr('value')==''){
		$('#startTime_search').attr('value',start_d);
		$('#endTime_search').attr('value',end_d);
	}
}
function myreset(){
	$('#d_busiName').attr('value','');
	$('#startTime_search').attr('value','');
	$('#endTime_search').attr('value','');
	$('#d_url').attr('value','');
	$('#d_apn').attr('value','');
	$('#d_ip').attr('value','');
}
//// 查询表单的查询函数 ////
function query() {
	/*var sdate =  GT.U.getValue(GT.$('startTime_search'));
	var edate = GT.U.getValue(GT.$('endTime_search'));
	if(sdate==''||edate==''){
		alert('请选择时间!');
		return ;
	}*/
	var param={		
		'filter_LIKE_dtbBusinessSite.vcBussinessName' : GT.U.getValue(GT.$('d_busiName')),
		startDate : GT.U.getValue(GT.$('startTime_search')),
		endDate : GT.U.getValue(GT.$('endTime_search')),
		'filter_LIKE_vcUrl' : GT.U.getValue(GT.$('d_url')),
		'filter_LIKE_vcServerIp' : GT.U.getValue(GT.$('d_ip')),
		'filter_LIKE_vcApn' : GT.U.getValue(GT.$('d_apn'))
		//'filter_LIKE_vcUserAgent' : GT.U.getValue(GT.$('d_UserAgent'))
	};
	GT.$grid('datagrid').query( param );
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
	/*$.ajax({
		type : "post",
		url : "assistVetorRecognise_getDataCount.do",
		data : {
			'filter_LIKE_dtbBusinessSite.vcBussinessName' : GT.U.getValue(GT.$('d_busiName')),
			'startDate' : GT.U.getValue(GT.$('startTime_search')),
			'endDate' : GT.U.getValue(GT.$('endTime_search')),
			'filter_LIKE_vcUrl' : GT.U.getValue(GT.$('d_url')),
			'filter_LIKE_vcServerIp' : GT.U.getValue(GT.$('d_ip')),
			'filter_LIKE_vcApn' : GT.U.getValue(GT.$('d_apn'))
		},
		success : function(size) {
			var s = parseInt(size);
			alert(size);
			if(s>25000){
				alert("数据太多，请重新设置条件。");
				return;
			}
			
		},
		error : function() {
			alert('查询数据出错!');
			return;
		}
	});*/
 	//参数
	var params={		
		'filter_LIKE_dtbBusinessSite.vcBussinessName' : GT.U.getValue(GT.$('d_busiName')),
		'startDate' : GT.U.getValue(GT.$('startTime_search')),
		'endDate' : GT.U.getValue(GT.$('endTime_search')),
		'filter_LIKE_vcUrl' : GT.U.getValue(GT.$('d_url')),
		'filter_LIKE_vcServerIp' : GT.U.getValue(GT.$('d_ip')),
		'filter_LIKE_vcApn' : GT.U.getValue(GT.$('d_apn'))
		//'filter_LIKE_vcUserAgent' : GT.U.getValue(GT.$('d_UserAgent'))
	};
	var ttc = datagrid.getTotalRowNum()
           	if(ttc>65500){
           		alert('结果数据太多，请重新设置查询条件!');
           		return;
           	}
			//导出
	var fileObj = new Object();
	fileObj.fileName='全量业务自发现数据列表';//('+startTime_search+'至'+endTime_search+')';//文件名称
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



