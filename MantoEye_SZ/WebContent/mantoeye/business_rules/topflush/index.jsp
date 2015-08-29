<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>业务流量排名</title>
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
														类别：
													</td>
													<td class="condition_value">
														<select name="busitype" id="d_busitype"
															style="height: 20px">
															<option value=""
																<c:if test="${fn:contains(busitype,'all') }">selected</c:if>>
																全部业务
															</option>
															<option value=1>
																翻译规则识别业务
															</option>
															<%-- <option value="2">
																自发现规则识别业务
															</option>--%>
															<option value="0">
																未识别业务
															</option>
														</select>
													</td>
													<td class="condition_name">
														时间：
													</td>
													<td class="condition_value">
														<input type="text" class="Wdate" style="height: 15px;"
															onclick="new WdatePicker({dateFmt:'yyyy-MM-dd'})"
															name="searchDateStart" value="${searchdate}"
															id="d_searchDateStart" />
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
var searchDateStart=new Date();//时间
searchDateStart=searchDateStart.valueOf();
searchDateStart=searchDateStart-24*60*60*1000;
searchDateStart=new Date(searchDateStart); 
var mm=searchDateStart.getMonth()+1;
searchDateStart=searchDateStart.getYear()+"-"+mm+"-"+searchDateStart.getDate()+"";
var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :[
		{name : 'nmBusFlushOrderId'        },
		{ name : 'flushOrder'       },
		{ name : 'bussinessNames'       },
		{ name : 'ip'       },
		{name : 'port'      },
		{name : 'flushMb'    },
		{name : 'isFind'    }
	]
};
var colsConfig = [
		{ id : 'nmBusFlushOrderId'      , header : "ID"   ,hidden:true,exportable:false },
		{ id : 'flushOrder'      , header : '流量排名'    },
		{ id : 'bussinessNames'      , header : '业务名称'  ,sortable:false  },
		{ id : 'ip'    , header : 'IP' ,sortable:false  },
		{ id : 'port'      , header : 'Port'  ,sortable:false   },
	//	{ id : 'isFind'      , header : '识别方式'  ,sortable:false  , render:renderFType},
		{ id : 'flushMb'    , header : '流量(MB)' ,exportnumber:true ,renderer:renderFormatDataInit2 }
];
function renderFType(value ,record,columnObj,grid,colNo,rowNo){
	//alert(value);
	if(value=='0'){
		return '未识别业务';
	}else if(value =='1'){
		return '翻译规则识别业务';
	}else if(value =='2'){
		return '自发现规则识别业务';
	} 
}
var gridConfig={
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/businessTopFlush_list.do?1=1',
	exportURL :'/businessTopFlush_export.do?1=1' ,
	dataset : dsConfig ,
	columns : colsConfig ,
//	width:"100%",
//	height:420,
//	resizable : false,
//	transparentMask:true,
	container : 'data_container',
	beforeLoad:checkLogon,
	toolbarPosition : 'bottom', 
	toolbarContent : '| nav | goto | pagesize | state',
	beforeSave : function(reqParam){
	},
	pageSize : getDispalyPageSize(0,1)  ,
	remoteSort : true ,
	pageSizeList :  [10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
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
	obj.hideColumn = 1 ;//隐藏的列数目
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

//// 查询表单的查询函数 ////
function query() {
	//alert(GT.U.getValue(GT.$('d_busitype')));
	if( GT.U.getValue(GT.$('d_searchDateStart'))==''){
		alert('请选择时间!');
		return;
	}
	var param={
		searchdate : GT.U.getValue(GT.$('d_searchDateStart')),
		'filter_EQ_isFind' : GT.U.getValue(GT.$('d_busitype'))
	};
	GT.$grid('datagrid').query( param );
}
function reset(){
	$('#d_searchDateStart').attr('value','');
	$('#d_busitype').attr('value','');
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
	if( GT.U.getValue(GT.$('d_searchDateStart'))==''){
		alert('请选择时间!');
		return ;
	}
 	//参数
	var params={
		searchdate : GT.U.getValue(GT.$('d_searchDateStart')),
	//	'filter_EQ|INTEGER_isFind' : GT.U.getValue(GT.$('d_busitype'))
		findType: GT.U.getValue(GT.$('d_busitype'))
	};
			//导出
	var fileObj = new Object();
	fileObj.fileName='业务流量排名数据列表';//('+startTime_search+'至'+endTime_search+')';//文件名称
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



