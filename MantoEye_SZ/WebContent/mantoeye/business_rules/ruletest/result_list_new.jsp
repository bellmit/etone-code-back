<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>新业务拨测记录列表</title>		
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />	
		<link rel="stylesheet" type="text/css" href="/tools/gt-grid/gt_grid.css" />
		<link type="text/css" rel="stylesheet" href="/skin/Default/css/maincontent.css" />
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css" type="text/css"></link>
		<script type="text/javascript" src="/js/nav.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js"></script>
		<script type="text/javascript" src="/tools/datepicker/WdatePicker.js"></script>
		<script type="text/javascript" src="/js/common_grid.js"></script>
		<script type="text/javascript" src="/js/jquery/extend.tab/js/Toolbar.js"></script>
		<base target="_self" />
	</head>
	<body style="overflow-x: hidden">
		<table id="maincontent" cellspacing="0" cellpadding="0" border="0"
			bgcolor="#D3E0F2" width="780px" height="100%">
			<tr>
				<td colspan="2" height="5px"></td>
			</tr>

			<tr>
				<td>
					<table cellspacing="0" cellpadding="0" border="0" bgcolor="#ffffff"
						style="width: 780px;">
						<tr valign="top">
							<td>
								<table cellspacing="0" cellpadding="0" border="0" width="780px">
									<tr valign="top" style="font-size:1px;">
									<td>
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
									</td>
									</tr>
									<tr valign="top">
										<td width="100%" class="condition_down">
											<table id="query_condition" cellspacing="0px"
												cellpadding="0px;boder="0">
												<tr valign="middle">
													<td class="condition_name">
														URL：
													</td>
													<td class="condition_value">
													<input type="hidden" name="d_busiName" value="" id="d_busiName"/>
														<input type="text"
															name="d_url" value=""
															id="d_url" style="width:160px"/>
													</td>
													<td class="condition_name">
														IP：
													</td>
													<td class="condition_value">
														<input type="text"
															name="d_ip" value=""
															id="d_ip" style="width:160px"/>
													</td>
													<td class="condition_name">
														Port：
													</td>
													<td class="condition_value">
														<input type="text"
															name="d_port" value=""
															id="d_port" style="width:160px"/>
													</td>	
													<td class="condition_name">&nbsp;</td><td class="condition_value">&nbsp;</td>
													</tr><tr>												
													<td class="condition_name">
														APN：
													</td>
													<td class="condition_value">
														<input type="text"
															name="d_apn" value=""
															id="d_apn" style="width:160px"/>
													</td>
													
													<td class="condition_name">
														UserAgent：
													</td>
													<td class="condition_value">
														<input type="text"
															name="d_useragent" value=""
															id="d_useragent" style="width:160px"/>
													</td>
													
													<td class="condition_name">
														&nbsp;
													</td>
													<td >
														<div id="mainbtn" style="width:160px;text-align: right;">
															<ul>
																<li>
																	<a href="javascript:query('3');" 　title="查询"><span>查询</span></a>
																</li>
																<!--  
																<li>
																	<a href="javascript:reset();" title="重置"><span>重置</span></a>
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
							<td height="5px" bgcolor="#D3E0F2" width="780px">
							<input id="busiid" type="hidden" value="0"></input>
							<input id="taskid" type="hidden" value="${taskid }"></input>
							</td>
						</tr>

						

						<tr>
							<td>							
								<table cellspacing="0" cellpadding="0" border="0" width="780px"
									class="menubar">
									<tr valign="top">
										<td width="4px" height="24px">
											<div class="lefttitle"></div>
										</td>
										<td width="100%" height="24px">
											<div class="middletitle">
												新业务拨测结果数据
											</div>
										</td>
										<td width="4px">
											<div class="righttitle"></div>
										</td>
									</tr>
								</table>
							</td>
						</tr>	
						<!-- 业务拨测结果数据<-->
						<tr >
							<td style="display:block" id="maincontent_result">
								<div id="toolbar_result" style="height: 25px;width:780px"></div>
								<div class="gt-panel"
									style="width: 100%; margin: 0px; margin-bottom: 2px;">
									<div id="data_container_result">
									</div>
								</div>
							</td>
						</tr>	
						<tr>
							<td>							
								<table cellspacing="0" cellpadding="0" border="0" width="780px"
									class="menubar">
									<tr valign="top">
										<td width="4px" height="24px">
											<div class="lefttitle"></div>
										</td>
										<td width="100%" height="24px">
											<div class="middletitle">
												可能业务主规则数据
												</div>
										</td>
										<td width="4px">
											<div class="righttitle"></div>
										</td>
									</tr>
								</table>
							</td>
						</tr>	
						<!-- 业务主规则数据 -->
						<tr >
							<td style="display:block" id="maincontent_main">
								<%--<div id="toolbar_main" style="height: 25px;width:780px"></div>--%>
								<div class="gt-panel"
									style="width: 100%; margin: 0px; margin-bottom: 2px;">
									<div id="data_container_main">
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
<SCRIPT>
/**
*
*/
$(document).ready(function(){
	window.onerror = killErrors;
})
function killErrors() {
	return true;
}
var init_result =false;
var init_main =false;
var busiid = 0;	
var taskid = document.getElementById("taskid").value;
//业务主规则数据
var dsConfig_main= {
	//data : data1 ,
	uniqueField : 'nmBusinessMainVetorId' ,
	fields :[
		{name : 'nmBusinessMainVetorId'        },
		{ name : 'businessName'       },
		{ name : 'mainruleIp'       },
		{name : 'mainrulePort'      },
		{name : 'mainruleApn'    },
		{name : 'mainruleUserAgent'    },
		{name : 'mainruleUrl'    }
	]
};
var colsConfig_main = [
		{ id : 'businessName'      , header : "业务名称" ,width:120 },
		{ id : 'mainruleUrl'     , header : "URL" ,width:150},
		{ id : 'mainruleIp'      , header : "IP" ,width:100 },
		{ id : 'mainrulePort'    , header : "Port" ,width:80 },
		{ id : 'mainruleApn'     , header : "APN"  ,width:120 },
		{ id : 'mainruleUserAgent'     , header : "UserAgent" ,width:200  }
		
];
//拨测结果数据
var dsConfig_result= {
	//data : data1 ,
	uniqueField : 'nmDataGetterBusinessId' ,
	fields :[
		{name : 'nmImsi'        },
		{ name : 'nmMsisdn'       },
		{ name : 'vcUrl'       },
		{name : 'vcServerIp'      },
		{name : 'intPort'    },
		{name : 'vcApn'    },
		{name : 'vcUserAgent'    },
		{name : 'dtTime'    },
		{name : 'businessName'    },
		{name : 'businessId'    },
		{name : 'nmUpFlush'    },
		{name : 'nmDownFlush'    },
		{name : 'nmTotalFlush'    }
	]
};
var colsConfig_result = [
		{ id : 'businessName'      , header : "业务名称" ,width:120 },
		{ id : 'businessId'      , header : "业务Id",hidden:true ,width:120 },
		{ id : 'vcUrl'     , header : "URL",width:130 },
		{ id : 'vcServerIp'      , header : "IP" ,width:80 },
		{ id : 'intPort'    , header : "Port" ,width:60 },
		{ id : 'vcApn'     , header : "APN"  ,width:80 },
		{ id : 'vcUserAgent'     , header : "UserAgent" ,width:80  },
		{ id : 'nmTotalFlush'     , header : "流量(KB)" ,width:80 ,exportnumber:true ,renderer:renderFormatDataInit2 },
		{ id : 'nmMsisdn'      , header : "号码" , width:100},
		{ id : 'detail'   , header : "操作" , sortable:false,  width:40,exportable:false,
			renderer:renderManager
		}
];
function renderManager(value ,record,columnObj,grid,colNo,rowNo){
	var businessId = record.businessId.toString();
	//alert(businessId+"--");
	return '<table><tr><td><img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/mainrules.gif" alt="查看可能业务主规则" onclick="showBusinessMainRules(\''+businessId+'\');" /></td>'
			+'</tr></table>';
}
function showBusinessMainRules(businessId){
	busiid = businessId;
	//alert(busiid);
	query("1");
}
var gridConfig_main={
	id : "datagrid_main",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/businessMainVetor_query.do?flag=1',
	exportURL :'/businessMainVetor_export.do?flag=1',
	dataset : dsConfig_main ,
	columns : colsConfig_main ,
	width:780,
	height:212,
	resizable : false,
	container : 'data_container_main',
	toolbarPosition : 'bottom', 
	toolbarContent : '| nav | goto | pagesize | state',
	pageSize : 10 ,
	remoteSort : true ,
	pageSizeList : [10,20,50,100],
	onComplete:loadComplate_main
};
var gridConfig_result={
	id : "datagrid_result",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/dataGetterBusiness_query.do?taskid='+taskid,
	exportURL :'/dataGetterBusiness_export.do?taskid='+taskid,
	dataset : dsConfig_result ,
	columns : colsConfig_result ,
	width:780,
	height:212,
	resizable : false,
	container : 'data_container_result',
	toolbarPosition : 'bottom', 
	toolbarContent : '| nav | goto | pagesize | state',
	pageSize : 10 ,
	remoteSort : true ,
	pageSizeList : [10,20,50,100],
	onComplete:loadComplate_result
};

var datagrid_result=new GT.Grid( gridConfig_result );
var datagrid_main=new GT.Grid( gridConfig_main );
//载入拨测结果数据
GT.Utils.onLoad( function(){
	datagrid_result.render();
	datagrid_main.render();
});

function loadComplate_result(){
	
		//判断是否查询到数据
		judgeThisData(datagrid_result);

}
function loadComplate_main(){
	
		//判断是否查询到数据
		judgeThisData(datagrid_main);
	
	
}
//判断grid查询是否有数据，没有则提示
function judgeThisData(datagrid){
	var totalRecords = datagrid.getAllRows().length;//获取加载到的数据记录数目
	var tdWidth="760px"
	if(typeof(datagrid.getRecord(0))=="undefined"){
		var $o = $('#'+datagrid.id+'_bodyDiv').find('table').eq(0).find('tbody').eq(0);//获取对象
		$o.find('tr').eq(0).remove();//由于控件存在bug，所以需要删除第一行
		var $tr = $('<tr><td height="20px" width='+tdWidth+' style="font-size:14px" align="center" bgcolor="#EEEEEE">没有数据显示!</td></tr>');
		$tr.appendTo($o);
	}
}
//// 查询表单的查询函数 ////
function query(flag) {
	if(flag="1"){
		var param_main={
		'filter_LIKE_vcServerIp' : GT.U.getValue(GT.$('d_ip')),
		'filter_LIKE_vcUrl' : GT.U.getValue(GT.$('d_url')),
		'filter_LIKE_vcApn' : GT.U.getValue(GT.$('d_apn')),
		'filter_EQ|INTEGER_intPort' : GT.U.getValue(GT.$('d_port')),
		'filter_LIKE_vcUserAgent' : GT.U.getValue(GT.$('d_useragent')),
		busiid:busiid
		};	
		GT.$grid('datagrid_main').query( param_main );
	}else{
	var param_result={
		'filter_LIKE_dtbBusinessSite.vcBussinessName' : GT.U.getValue(GT.$('d_port')),
		'filter_LIKE_vcServerIp' : GT.U.getValue(GT.$('d_ip')),
		'filter_LIKE_vcUrl' : GT.U.getValue(GT.$('d_url')),
		'filter_LIKE_vcApn' : GT.U.getValue(GT.$('d_apn')),
		'filter_EQ|INTEGER_intPort' : GT.U.getValue(GT.$('d_port')),
		'filter_LIKE_vcUserAgent' : GT.U.getValue(GT.$('d_useragent'))
	};
	var param_main={
		'filter_LIKE_vcServerIp' : GT.U.getValue(GT.$('d_ip')),
		'filter_LIKE_vcUrl' : GT.U.getValue(GT.$('d_url')),
		'filter_LIKE_vcApn' : GT.U.getValue(GT.$('d_apn')),
		'filter_EQ|INTEGER_intPort' : GT.U.getValue(GT.$('d_port')),
		'filter_LIKE_vcUserAgent' : GT.U.getValue(GT.$('d_useragent')),
		busiid:busiid
	};	
	GT.$grid('datagrid_main').query( param_main );
	GT.$grid('datagrid_result').query( param_result );
	}
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
function reset(){
	document.getElementById('d_ip').value='';
	document.getElementById('d_url').value='';
	document.getElementById('d_apn').value='';
	document.getElementById('d_port').value='';
	document.getElementById('d_useragent').value='';
}
$(document).ready(function(){
    /*	  var toolbar_main = new Toolbar({
        renderTo : 'toolbar_main',
		//border: 'top',
        items : [{
          type : 'button',
          text : '导出',
          bodyStyle : 'xls',
          useable : 'T',
          handler : function(){
           //GT.$grid('datagrid').exportGrid();
          }
        },'-',]
      });    
	  toolbar_main.render();*/
  var toolbar_result = new Toolbar({
        renderTo : 'toolbar_result',
		//border: 'top',
        items : [
       {
          type : 'button',
          text : '导出拨测结果',
          bodyStyle : 'xls',
          useable : 'T',
          handler :exportToXls
        } ,'-',]
      });      
	  toolbar_result.render();
    });
 function exportToXls(){
 //参数
	var params={
		'filter_LIKE_dtbBusinessSite.vcBussinessName' : GT.U.getValue(GT.$('d_port')),
		'filter_LIKE_vcServerIp' : GT.U.getValue(GT.$('d_ip')),
		'filter_LIKE_vcUrl' : GT.U.getValue(GT.$('d_url')),
		'filter_LIKE_vcApn' : GT.U.getValue(GT.$('d_apn')),
		'filter_EQ|INTEGER_intPort' : GT.U.getValue(GT.$('d_port')),
		'filter_LIKE_vcUserAgent' : GT.U.getValue(GT.$('d_useragent'))
	};
			//导出
	var fileObj = new Object();
	fileObj.fileName='拨测结果数据列表';//('+startTime_search+'至'+endTime_search+')';//文件名称
	fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
	fileObj.gridObj = datagrid_result;//当前grid对象
	fileObj.params = params;
	exportFile(fileObj);
 }
</SCRIPT>



